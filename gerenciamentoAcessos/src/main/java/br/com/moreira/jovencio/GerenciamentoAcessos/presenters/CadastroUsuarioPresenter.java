
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioCadastroDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.UsuarioCadastroService;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.CadastroUsuarioView;

/**
 *
 * @author marlan
 */
public class CadastroUsuarioPresenter {

	private final CadastroUsuarioView view;

	public CadastroUsuarioPresenter() {
		view = new CadastroUsuarioView();
		configurarView();
		view.setVisible( true );
	}

	private void configurarView() {
		if( view.getBtnCadastrar().getActionListeners() != null ) {
			for( var listener : view.getBtnCadastrar().getActionListeners() ) {
				view.getBtnCadastrar().removeActionListener( listener );
			}
		}
		view.getBtnCadastrar().addActionListener( ae -> cadastrar() );
	}

	private void cadastrar() {
		if( !validarSenhas() ) {
			return;
		}
		var dto = new UsuarioCadastroDTO();
		dto.setNome( view.getTxtNome().getText() );
		dto.setSobrenome( view.getTxtSobrenome().getText() );
		dto.setEmail( view.getTxtEmail().getText() );
		dto.setLogin( view.getTxtLogin().getText() );
		dto.setSenha( String.valueOf( view.getPswConfirmarSenha().getPassword() ) );
		var retorno = new UsuarioCadastroService().cadastrar( dto );
		if( !retorno.isSuccess() ) {
			new ConfirmarDialogPresenter( view, "Campos com erros", retorno.getMensagem() );
			return;
		}
		view.setVisible( false );
		PrincipalPresenter.getInstancia().show( retorno.getEntidadeId() );
	}

	private boolean validarSenhas() {
		var senha = String.valueOf( view.getPswSenha().getPassword() );
		var confirmarSenha = String.valueOf( view.getPswConfirmarSenha().getPassword() );
		if( senha.compareTo( confirmarSenha ) != 0 ) {
			new ConfirmarDialogPresenter( view, "Senhas incompativeis", "O Campo 'Senha' e o campo 'Confirmar Senha' não estão preenchidos com o mesmo valor!" );
			return false;
		}
		return true;
	}

}
