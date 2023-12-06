
package br.com.moreira.jovencio.GerenciamentoAcessos.services.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.daos.impl.UsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioCadastroDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.IUsuarioCadastroService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marlan
 */
public class UsuarioCadastroService implements IUsuarioCadastroService {

	private IUsuarioDAO usuarioDao = new UsuarioDAO();

	@Override
	public ControllerRetorno cadastrar( UsuarioCadastroDTO dto ) {
		var validar = validar( dto );
		if( !validar.isSuccess() ) {
			return validar;
		}
		Usuario usuario = usuarioDao.insert( map( dto ) );
		if( usuario == null ) {
			validar.setCodigo( 505 );
			validar.setMensagem( "Não foi possível cadastrar o usuário, por favor tente novamente" );
		}
		validar.setCodigo( 201 );
		validar.setEntidadeId( usuario.getId() );
		return validar;
	}

	@Override
	public ControllerRetorno validar( UsuarioCadastroDTO usuario ) {
		var retorno = new ControllerRetorno();
		if( usuario == null ) {
			retorno.setCodigo( 505 );
			retorno.setMensagem( "Necessário infomar dados para cadastro" );
		}

		List<String> erros = new ArrayList<String>();
		erros.add( ValidarCampo.asString( "Nome", usuario.getNome(), 50 ) );
		erros.add( ValidarCampo.asString( "Sobrenome", usuario.getSobrenome(), 100 ) );
		erros.add( ValidarCampo.asString( "Login", usuario.getLogin(), 50 ) );
		if( !ValidarCampo.isNullVazioOrEspacos( usuario.getEmail() ) ) {
			var erroEmail = ValidarCampo.asString( "E-mail", usuario.getEmail(), 150 );
			if( erroEmail == null ) {
				erros.add( ValidarCampo.asEmail( "E-mail", usuario.getEmail(), 150 ) );
			}
		}
		var erroSenha = ValidarCampo.asString( "Senha", usuario.getSenha(), 75 );
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

	private Usuario map( UsuarioCadastroDTO dto ) {
		var entity = new Usuario();
		entity.setNome( dto.getNome() );
		entity.setSobrenome( dto.getSobrenome() );
		entity.setLogin( dto.getLogin() );
		entity.setEmail( dto.getEmail() );
		entity.setSenha( dto.getSenha() );
		return entity;
	}

}
