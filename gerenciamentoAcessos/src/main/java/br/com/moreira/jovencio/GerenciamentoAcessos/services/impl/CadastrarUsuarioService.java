
package br.com.moreira.jovencio.GerenciamentoAcessos.services.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioCadastroDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.AbstractLogService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.IUsuarioCadastroService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marlan
 */
public class CadastrarUsuarioService extends AbstractLogService implements IUsuarioCadastroService {

	private final IUsuarioDAO usuarioDao;
	private final INotificarUsuarioService notificarUsuarioService;

	public CadastrarUsuarioService() throws Exception {
		usuarioDao = DAOFactory.getDAOFactory().getUsuarioDAO();
		notificarUsuarioService = new NotificarUsuarioService();
	}

	@Override
	public ControllerRetorno cadastrar( UsuarioCadastroDTO dto ) {
		try {
			var validar = validar( dto );
			if( !validar.isSuccess() ) {
				return validar;
			}
			var usuario = tratarPermissoes( map( dto ) );
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
		} catch ( Exception ex ) {
			return tratarErro( ex );
		}
	}

	@Override
	public ControllerRetorno validar( UsuarioCadastroDTO usuario ) throws Exception {
		var retorno = new ControllerRetorno();
		if( usuario == null ) {
			retorno.setCodigo( 505 );
			retorno.setMensagem( "Necessário infomar dados para cadastro" );
		}

		List<String> erros = new ArrayList<>();
		erros.add( ValidarCampo.asString( "Nome", usuario.getNome(), 50 ) );
		erros.add( ValidarCampo.asString( "Sobrenome", usuario.getSobrenome(), 100 ) );

		var erroLogin = ValidarCampo.asString( "Login", usuario.getLogin(), 50 );
		erros.add( erroLogin );
		if( erroLogin == null ) {
			if( usuario.getLogin().contains( " " ) ) {
				erros.add( "O login não pode conter espaços" );
			} else if( usuarioDao.existeUsuarioComLogin( usuario.getLogin() ) ) {
				erros.add( "O login escolhido não está disponível" );
			}
		}
		if( ValidarCampo.isNotNullVazioOrEspacos( usuario.getEmail() ) ) {
			var erroEmail = ValidarCampo.asString( "E-mail", usuario.getEmail(), 150 );
			erros.add( erroEmail );
			if( erroEmail == null ) {
				erros.add( ValidarCampo.asEmail( "E-mail", usuario.getEmail(), 150 ) );
			}
		}

		var erroSenha = ValidarCampo.asString( "Senha", usuario.getSenha(), 75 );
		erros.add( erroSenha );
		if( erroSenha == null ) {
			erros.add( ValidarCampo.asSenha( "Senha", usuario.getSenha(), 75 ) );
		}

		erros = erros.stream().filter( ValidarCampo::isNotNullVazioOrEspacos ).toList();
		retorno.setCodigo( 200 );
		if( !erros.isEmpty() ) {
			retorno.setCodigo( 400 );
			retorno.setMensagem( String.join( ", ", erros ) );
		}
		return retorno;
	}

	public ControllerRetorno autenticar( int usuarioId ) {
		var retorno = new ControllerRetorno( 200 );
		if( usuarioId == 0 ) {

		}
		return retorno;
	}

	private Usuario map( UsuarioCadastroDTO dto ) {
		var entity = new Usuario();
		entity.setNome( dto.getNome() );
		entity.setSobrenome( dto.getSobrenome() );
		entity.setLogin( dto.getLogin() );
		entity.setEmail( dto.getEmail() );
		entity.setSenha( dto.getSenha() );
		return entity;
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

}
