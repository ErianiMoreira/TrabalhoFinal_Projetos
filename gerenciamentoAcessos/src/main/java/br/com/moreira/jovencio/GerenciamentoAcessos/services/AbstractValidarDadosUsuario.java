
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marlan
 */
public abstract class AbstractValidarDadosUsuario {

	protected IUsuarioDAO usuarioDao;

	protected Usuario usuario;
	protected boolean isValidarId;
	protected boolean isValidarNome;
	protected boolean isValidarSobrenome;
	protected boolean isValidarLogin;
	protected boolean isValidarEmail;
	protected boolean isValidarSenha;

	protected AbstractValidarDadosUsuario( Usuario usuario ) throws Exception {
		this.usuario = usuario;
		this.isValidarId = true;
		this.isValidarNome = true;
		this.isValidarSobrenome = true;
		this.isValidarLogin = true;
		this.isValidarEmail = true;
		this.isValidarSenha = true;
		usuarioDao = DAOFactory.getDAOFactory().getUsuarioDAO();
	}

	public AbstractValidarDadosUsuario( Usuario usuario, boolean isValidarId, boolean isValidarNome, boolean isValidarSobrenome, boolean isValidarLogin, boolean isValidarEmail, boolean isValidarSenha ) throws Exception {
		this.usuario = usuario;
		this.isValidarId = isValidarId;
		this.isValidarNome = isValidarNome;
		this.isValidarSobrenome = isValidarSobrenome;
		this.isValidarLogin = isValidarLogin;
		this.isValidarEmail = isValidarEmail;
		this.isValidarSenha = isValidarSenha;
		usuarioDao = DAOFactory.getDAOFactory().getUsuarioDAO();
	}

	public final ControllerRetorno validar() throws Exception {
		var retorno = new ControllerRetorno( 200 );

		if( usuario == null ) {
			retorno.setCodigo( 400 );
			retorno.setMensagem( "Usuário não informado" );
			return retorno;
		}
		var erros = new ArrayList<String>();
		if( isValidarId && ( usuario.getId() == null || usuario.getId() == 0 ) ) {
			erros.add( "Campo '" + usuario.getId() + "' não preenchido" );
		}

		if( isValidarNome ) {
			tratarCampo( ValidarCampo.asString( "Nome", usuario.getNome(), 50 ), erros );
		}

		if( isValidarSobrenome ) {
			tratarCampo( ValidarCampo.asString( "Sobrenome", usuario.getSobrenome(), 100 ), erros );
		}

		if( isValidarLogin ) {
			var erro = ValidarCampo.asString( "Login", usuario.getLogin(), 50 );
			if( erro == null ) {
				if( usuario.getLogin().contains( " " ) ) {
					erros.add( "O login não pode conter espaços" );
				} else {
					var usuarioComMesmoLogin = usuarioDao.findUsuarioComLogin( usuario.getLogin() );
					if( usuarioComMesmoLogin != null ) {
						erros.add( "O login escolhido não está disponível" );
					}
				}
			} else {
				erros.add( erro );
			}
		}

		if( isValidarEmail && ValidarCampo.isNotNullVazioOrEspacos( usuario.getEmail() ) ) {
			var erro = ValidarCampo.asString( "E-mail", usuario.getEmail(), 150 );
			if( erro == null ) {
				tratarCampo( ValidarCampo.asEmail( "E-mail", usuario.getEmail(), 150 ), erros );
			} else {
				erros.add( erro );
			}
		}

		if( isValidarSenha ) {
			var erro = ValidarCampo.asString( "Senha", usuario.getSenha(), 75 );
			if( erro == null ) {
				var erroSenha = ValidarCampo.asSenha( "Senha", usuario.getSenha(), 75 );
				if( erroSenha == null ) {
					if( !usuario.getSenha().equals( usuario.getSenhaConfirmacao() ) ) {
						erros.add( "As senha e a sua confirmação precisao ser iguais" );
					}
				} else {
					erros.add( erroSenha );
				}
			} else {
				erros.add( erro );
			}
		}

		outrasValidacoes( erros );

		if( !erros.isEmpty() ) {
			retorno.setCodigo( 400 );
			retorno.setMensagem( String.join( ", ", erros ) );
		}

		return retorno;
	}

	protected void tratarCampo( String erro, List<String> erros ) {
		if( erro != null ) {
			erros.add( erro );
		}
	}

	protected void outrasValidacoes( List<String> erros ) {
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario( Usuario usuario ) {
		this.usuario = usuario;
	}

}
