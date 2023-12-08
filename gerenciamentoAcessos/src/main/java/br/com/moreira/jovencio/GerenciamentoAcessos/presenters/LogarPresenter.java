
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.services.ILoginService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.LoginService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.LogarView;

/**
 *
 * @author marlan
 */
public class LogarPresenter {

	private final LogarView view;
	private CadastroUsuarioPresenter cadastroUsuarioPresenter;
	private ILoginService loginService;

	public LogarPresenter() throws Exception {
		view = new LogarView();
		loginService = new LoginService();
		configurarView();
		view.setVisible( true );
	}

	private void configurarView() {
		if( view.getBtnEntrar().getActionListeners() != null ) {
			for( var listener : view.getBtnEntrar().getActionListeners() ) {
				view.getBtnEntrar().removeActionListener( listener );
			}
		}
		view.getBtnEntrar().addActionListener( ae -> logar() );
		view.getBtnCadastrar().addActionListener( ae -> cadastrar() );
	}

	private void logar() {
		var retorno = loginService.logar( view.getTxtLogin().getText(), String.valueOf( view.getPswSenha().getPassword() ) );
		if( retorno.isSuccess() ) {
			view.setVisible( false );
			PrincipalPresenter.getInstancia().show( retorno.getEntidadeId() );
		} else {
			INotificarPoPopupService notificar = new NotificarPoPopupService( view );
			notificar.showPopupOk( retorno.isError() ? "Erro" : "Alerta", retorno.getMensagem(), retorno.isError() ? "Erro" : "Alerta" );
		}
	}

	private void cadastrar() {
		view.setVisible( false );
		if( cadastroUsuarioPresenter == null ) {
			cadastroUsuarioPresenter = new CadastroUsuarioPresenter();
		}
	}

}
