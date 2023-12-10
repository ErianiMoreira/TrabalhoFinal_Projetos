
package br.com.moreira.jovencio.GerenciamentoAcessos.services.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.AbstractLogService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marlan/eriani
 */
public class LoginService extends AbstractLogService implements ILoginService {

	private final String SENHA_PADRAO = "Senha@135";

	protected final Logger log = LoggerFactory.getLogger( LoginService.class );

	private final IUsuarioDAO usuarioDao;

	public LoginService() throws Exception {
		super( LoggerFactory.getLogger( LoginService.class ) );
		usuarioDao = DAOFactory.getDAOFactory().getUsuarioDAO();
	}

	@Override
	public ControllerRetorno logar( String login, String senha ) {
		try {
			Usuario usuario;
			usuario = usuarioDao.findByLoginAndSenha( login, senha );
			var retorno = new ControllerRetorno();
			if( usuario != null ) {
				retorno.setCodigo( 200 );
				retorno.setMensagem( usuario.getId().toString() );
				retorno.setEntidadeId( usuario.getId() );
				return retorno;
			}
			usuario = usuarioDao.findUsuarioComLogin( login );
			if( usuario != null && usuario.getSenha() != null && !usuario.getSenha().isEmpty() ) {
				retorno.setCodigo( 400 );
				retorno.setMensagem( "Login ou senha inválidos" );
				return retorno;
			} else if( usuario != null && ( usuario.getSenha() == null || usuario.getSenha().isEmpty() ) ) {
				retorno.setCodigo( 426 );
				retorno.setMensagem( "Senha resetada, sua nova senha é '" + SENHA_PADRAO + "' e recomendamos que seja alterada o quanto antes!" );
				usuarioDao.alterarSenha( usuario.getId(), SENHA_PADRAO );
				return retorno;
			}
			retorno.setMensagem( "Nenhum usuário encontratdo!" );
			return retorno;
		} catch ( Exception ex ) {
			return tratarErro( ex );
		}
	}

}
