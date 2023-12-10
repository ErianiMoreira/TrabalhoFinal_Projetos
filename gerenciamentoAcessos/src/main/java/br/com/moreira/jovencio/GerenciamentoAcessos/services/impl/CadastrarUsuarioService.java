
package br.com.moreira.jovencio.GerenciamentoAcessos.services.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.AbstractLogService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.AbstractValidarDadosUsuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ICadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marlan
 */
public class CadastrarUsuarioService extends AbstractLogService implements ICadastrarUsuarioService {

	private final IUsuarioDAO usuarioDao;
	private final INotificarUsuarioService notificarUsuarioService;
	private AbstractValidarDadosUsuario validador;

	public CadastrarUsuarioService( AbstractValidarDadosUsuario validador ) throws Exception {
		super( LoggerFactory.getLogger( AbstractLogService.class ) );
		usuarioDao = DAOFactory.getDAOFactory().getUsuarioDAO();
		notificarUsuarioService = new NotificarUsuarioService();
		this.validador = validador;
	}

	public CadastrarUsuarioService() throws Exception {
		super( LoggerFactory.getLogger( AbstractLogService.class ) );
		usuarioDao = DAOFactory.getDAOFactory().getUsuarioDAO();
		notificarUsuarioService = new NotificarUsuarioService();
	}

	@Override
	public ControllerRetorno cadastrar( Usuario usuario ) {
		try {
			validador.setUsuario( usuario );
			var validar = validador.validar();
			if( !validar.isSuccess() ) {
				return validar;
			}
			tratarPermissoes( usuario );
			usuario = usuarioDao.insert( usuario );
			if( usuario == null ) {
				validar.setCodigo( 505 );
				validar.setMensagem( "Não foi possível cadastrar o usuário, por favor tente novamente" );
				return validar;
			}
			validar.setCodigo( 201 );
			validar.setEntidadeId( usuario.getId() );
			notificarUsuarioService.notificar( usuario.getId(), "Bem vindo ao sistema" );
			return validar;
		} catch ( Exception e ) {
			return tratarErro( e );
		}
	}

	@Override
	public ControllerRetorno atualizar( Usuario usuario ) {
		try {
			validador.setUsuario( usuario );
			var validar = validador.validar();
			if( !validar.isSuccess() ) {
				return validar;
			}
			usuarioDao.update( usuario.getId(), usuario );
			validar.setCodigo( 200 );
			validar.setEntidadeId( usuario.getId() );
			return validar;
		} catch ( Exception e ) {
			return tratarErro( e );
		}
	}

	@Override
	public ControllerRetorno validar( Usuario usuario ) {
		try {
			validador.setUsuario( usuario );
			return validador.validar();
		} catch ( Exception ex ) {
			return tratarErro( ex );
		}
	}

	@Override
	public void setValidador( AbstractValidarDadosUsuario validador ) {
		this.validador = validador;
	}

	@Override
	public ControllerRetorno alterarSenha( int usuarioId, String senha, String confirmacao ) {
		try {
			var validar = validarSenha( senha, confirmacao );
			if( !validar.isSuccess() ) {
				return validar;
			}
			usuarioDao.alterarSenha( usuarioId, senha );
			return validar;
		} catch ( Exception ex ) {
			return tratarErro( ex );
		}
	}

	@Override
	public ControllerRetorno validarSenha( String senha, String confirmacao ) {
		var retorno = new ControllerRetorno( 200 );

		List<String> erros = new ArrayList<>();
		var erroSenha = ValidarCampo.asString( "Senha", senha, 75 );
		if( erroSenha == null ) {
			erros.add( ValidarCampo.asSenha( "Senha", senha, 75 ) );
		}
		if( !senha.equals( confirmacao ) ) {
			erros.add( "As senha e a sua confirmação precisao ser iguais" );
		}

		erros = erros.stream().filter( ValidarCampo::isNotNullVazioOrEspacos ).toList();
		if( !erros.isEmpty() ) {
			retorno.setCodigo( 400 );
			retorno.setMensagem( String.join( ", ", erros ) );
		}
		return retorno;
	}

	@Override
	public ControllerRetorno autorizar( int usuarioId ) {
		try {
			var retorno = new ControllerRetorno( 200 );
			if( usuarioId == 0 ) {
				retorno.setCodigo( 400 );
				retorno.setMensagem( "Usuário não informado" );
				return retorno;
			}

			var usuario = usuarioDao.get( usuarioId );
			if( usuario == null || usuario.getId() == null || usuario.getId() == 0 ) {
				retorno.setCodigo( 404 );
				retorno.setMensagem( "Usuário não encontrado" );
				return retorno;
			}
			if( usuario.getDataAutorizado() != null ) {
				retorno.setCodigo( 400 );
				retorno.setMensagem( "Usuário já autorizado" );
				return retorno;
			}
			usuarioDao.autorizar( usuarioId );
			return retorno;
		} catch ( Exception ex ) {
			return tratarErro( ex );
		}
	}

	private Usuario tratarPermissoes( Usuario usuario ) throws Exception {
		if( usuarioDao.getCountUsuarios() > 0 ) {
			usuario.addPermissao( "usuario" );
		} else {
			usuario.addPermissao( "admin" );
			usuario.setDataAutorizado( usuario.getDataCadastro() );
		}
		return usuario;
	}

	@Override
	public ControllerRetorno resetarSenha( int usuarioId ) {
		try {
			usuarioDao.resetarSenha( usuarioId );
			return new ControllerRetorno( 200 );
		} catch ( Exception ex ) {
			return tratarErro( ex );
		}
	}

	@Override
	public ControllerRetorno excluir( int usuarioId ) {
		try {
			notificarUsuarioService.deletarNotificacoesByUsuario( usuarioId );
			usuarioDao.delete( usuarioId );
			return new ControllerRetorno( 200 );
		} catch ( Exception ex ) {
			return tratarErro( ex );
		}
	}

}
