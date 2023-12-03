
package br.com.moreira.jovencio.GerenciamentoAcessos.services.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.daos.impl.UsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ILoginService;

/**
 *
 * @author marlan
 */
public class LoginService implements ILoginService {

	private IUsuarioDAO usuarioDao = new UsuarioDAO();

	@Override
	public ControllerRetorno logar( String login, String senha ) {
		var random = Math.random() * 100 / 100 >= 0.5;
		Usuario usuario = usuarioDao.findByLoginAndSenha( login, senha );
		var retorno = new ControllerRetorno();
		if( random && usuario != null ) {
			retorno.setCodigo( 200 );
			retorno.setMensagem( usuario.getId().toString() );
			retorno.setEntidadeId( usuario.getId() );
			return retorno;
		}
		random = Math.random() * 100 / 100 >= 0.5;
		if( random && !usuarioDao.existeUsuarioComLogin( login ) ) {
			retorno.setCodigo( 400 );
			retorno.setMensagem( "Login ou senha inválidos" );
			return retorno;
		}
		retorno.setCodigo( 500 );
		retorno.setMensagem( "Nenhum usuário encontratdo!" );
		return retorno;
	}
}
