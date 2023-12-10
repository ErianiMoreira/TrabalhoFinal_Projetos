
package br.com.moreira.jovencio.GerenciamentoAcessos.services.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.INotificacaoDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Notificacao;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.AbstractLogService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marlan/eriani
 */
public class NotificarUsuarioService extends AbstractLogService implements INotificarUsuarioService {

	private final IUsuarioDAO usuarioDao;
	private final INotificacaoDAO notificacaoDAO;

	public NotificarUsuarioService() throws Exception {
		super( LoggerFactory.getLogger( NotificarUsuarioService.class ) );
		usuarioDao = DAOFactory.getDAOFactory().getUsuarioDAO();
		notificacaoDAO = DAOFactory.getDAOFactory().getNotificacaoDAO();
	}

	@Override
	public ControllerRetorno notificar( int usuarioOrigemId, int usuarioDestinoId, String mensagem ) {
		try {
			var validar = validar( usuarioOrigemId, usuarioDestinoId, mensagem );
			if( !validar.isSuccess() ) {
				return validar;
			}
			return inserir( usuarioOrigemId, usuarioDestinoId, mensagem );
		} catch ( Exception ex ) {
			return super.tratarErro( ex );
		}
	}

	@Override
	public ControllerRetorno notificar( int usuarioDestinoId, String mensagem ) {
		try {
			var validar = validar( usuarioDestinoId, mensagem );
			if( !validar.isSuccess() ) {
				return validar;
			}
			return inserir( null, usuarioDestinoId, mensagem );
		} catch ( Exception ex ) {
			return super.tratarErro( ex );
		}
	}

	@Override
	public List<Notificacao> findAllByParaUsuarioId( int id ) throws Exception {
		return notificacaoDAO.findAllByParaUsuarioId( id );
	}

	@Override
	public ControllerRetorno marcar( int id, boolean lida ) {
		try {
			notificacaoDAO.marcar( id, lida );
			return new ControllerRetorno( 200 );
		} catch ( Exception e ) {
			return tratarErro( e );
		}
	}

	@Override
	public ControllerRetorno validar( int usuarioDestinoId, String mensagem ) throws Exception {
		return _validar( null, usuarioDestinoId, mensagem );
	}

	public ControllerRetorno deletarNotificacoesByUsuario( int usuarioId ) {
		try {
			notificacaoDAO.deletarNotificacoesByUsuario( usuarioId );
			return new ControllerRetorno( 200 );
		} catch ( Exception e ) {
			return tratarErro( e );
		}
	}

	@Override
	public ControllerRetorno validar( int usuarioOrigemId, int usuarioDestinoId, String mensagem ) throws Exception {
		return _validar( usuarioOrigemId, usuarioDestinoId, mensagem );
	}

	private ControllerRetorno _validar( Integer usuarioOrigemId, int usuarioDestinoId, String mensagem ) throws Exception {
		var validar = new ControllerRetorno( 200 );
		List<String> erros = new ArrayList<>();
		if( usuarioOrigemId != null ) {
			var usuarioOrigem = usuarioDao.get( usuarioOrigemId );
			if( usuarioOrigem == null ) {
				erros.add( "Usuário de origem informado não existe" );
				validar.setCodigo( 404 );
			}
		}
		var usuarioDestino = usuarioDao.get( usuarioDestinoId );
		if( usuarioDestino == null ) {
			erros.add( "Usuário de destino informado não existe" );
			validar.setCodigo( 404 );
		}

		var erroMensagem = ValidarCampo.asString( "Mensagem", mensagem, 5000 );
		if( erroMensagem != null ) {
			validar.setCodigo( 400 );
			erros.add( erroMensagem );
		}

		if( !erros.isEmpty() ) {
			validar.setMensagem( String.join( ", ", erros ) );
		}
		return validar;
	}

	private ControllerRetorno inserir( Integer usuarioOrigemId, int usuarioDestinoId, String mensagem ) throws Exception {
		var validar = new ControllerRetorno( 201 );
		var entity = new Notificacao();
		entity.setMensagem( mensagem );
		entity.setDeUsuario( usuarioOrigemId );
		entity.setParaUsuario( usuarioDestinoId );
		entity = notificacaoDAO.insert( entity );
		if( entity == null ) {
			validar.setCodigo( 505 );
			validar.setMensagem( "Não foi possível enviar a notificação, por favor tente novamente" );
			return validar;
		}
		validar.setCodigo( 201 );
		validar.setEntidadeId( entity.getId() );
		return validar;
	}

}
