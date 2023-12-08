
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioCadastroDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.CadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.CadastroUsuarioView;

/**
 *
 * @author marlan
 */
public class CadastroUsuarioPresenter {

	private final CadastroUsuarioView view;
	private final INotificarPoPopupService popup;

	public CadastroUsuarioPresenter() {
		view = new CadastroUsuarioView();
		configurarView();
		popup = new NotificarPoPopupService( view );
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
		ControllerRetorno retorno;
		try {
			retorno = new CadastrarUsuarioService().cadastrar( dto );
		} catch ( Exception ex ) {
			// TODO: tratar erro
			return;
		}
		if( !retorno.isSuccess() ) {
			popup.showPopupOk( "Campos com erros", retorno.getMensagem(), retorno.isError() ? "Erro" : "Alerta" );
			return;
		}
		view.setVisible( false );
		PrincipalPresenter.getInstancia().show( retorno.getEntidadeId() );
	}

	private boolean validarSenhas() {
		var senha = String.valueOf( view.getPswSenha().getPassword() );
		var confirmarSenha = String.valueOf( view.getPswConfirmarSenha().getPassword() );
		if( ( ValidarCampo.isNotNullVazioOrEspacos( senha ) || ValidarCampo.isNotNullVazioOrEspacos( confirmarSenha ) ) && senha.compareTo( confirmarSenha ) != 0 ) {
			popup.showPopupOk( "Senhas incompativeis", "O Campo 'Senha' e o campo 'Confirmar Senha' não estão preenchidos com o mesmo valor!", "erro" );
			return false;
		}
		return true;
	}

}
