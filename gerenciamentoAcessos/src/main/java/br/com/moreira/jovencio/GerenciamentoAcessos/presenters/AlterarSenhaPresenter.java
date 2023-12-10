
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ICadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.CadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.AlterarSenhaView;
import javax.swing.WindowConstants;

/**
 *
 * @author marlan/eriani
 */
public class AlterarSenhaPresenter {

	private int usuarioLogadoId;
	private final AlterarSenhaView view;
	private final ICadastrarUsuarioService cadastrarUsuarioService;
	private final INotificarPoPopupService notificarPopUp;

	public AlterarSenhaPresenter( int usuarioLogadoId ) throws Exception {
		this.usuarioLogadoId = usuarioLogadoId;
		cadastrarUsuarioService = new CadastrarUsuarioService();
		view = new AlterarSenhaView();
		notificarPopUp = new NotificarPoPopupService( view );
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
		view.getBtnAlterarSenha().addActionListener( a -> alterarSenha() );
		PrincipalPresenter.getInstancia().add( this.getClass().getSimpleName(), view );
	}

	public void show( int usuarioLogadoId ) {
		this.usuarioLogadoId = usuarioLogadoId;
		view.setVisible( true );
		view.setSize( view.getPreferredSize() );
	}

	private void close() {
		view.setVisible( false );
	}

	private void alterarSenha() {
		var senha = String.valueOf( view.getPswNovaSenha().getPassword() );
		var confirmacao = String.valueOf( view.getPswConfirmarNovaSenha().getPassword() );
		ControllerRetorno validar = cadastrarUsuarioService.alterarSenha( usuarioLogadoId, senha, confirmacao );
		if( validar.isSuccess() ) {
			notificarPopUp.showPopupOk( "Alteração de senha", "Senha Alterada com sucesso", null );
			close();
		} else {
			notificarPopUp.showPopupOk( validar.isError() ? "Erro" : "Alerta", validar.getMensagem(), validar.isError() ? "Erro" : "Alerta" );
		}
	}

}
