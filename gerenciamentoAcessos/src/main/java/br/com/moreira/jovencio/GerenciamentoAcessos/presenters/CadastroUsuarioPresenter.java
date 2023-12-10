
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs.LogFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.CadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.ValidarDadosUsuarioCadastroLogin;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.CadastroUsuarioView;

/**
 *
 * @author marlan/eriani
 */
public class CadastroUsuarioPresenter {

	private final LogarPresenter parent;
	private final CadastroUsuarioView view;
	private final INotificarPoPopupService popup;

	public CadastroUsuarioPresenter( LogarPresenter parent ) {
		this.parent = parent;
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
		view.getBtnCancelar().addActionListener( ae -> cancelar() );
	}

	private void cadastrar() {
		var dto = new Usuario();
		dto.setNome( view.getTxtNome().getText() );
		dto.setSobrenome( view.getTxtSobrenome().getText() );
		dto.setEmail( view.getTxtEmail().getText() );
		dto.setLogin( view.getTxtLogin().getText() );
		dto.setSenha( String.valueOf( view.getPswConfirmarSenha().getPassword() ) );
		dto.setSenhaConfirmacao( String.valueOf( view.getPswConfirmarSenha().getPassword() ) );
		ControllerRetorno retorno;
		try {
			retorno = new CadastrarUsuarioService( new ValidarDadosUsuarioCadastroLogin() ).cadastrar( dto );
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "cadastrar da CadastroUsuarioPresenter", 0 );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			return;
		}
		if( !retorno.isSuccess() ) {
			popup.showPopupOk( "Campos com erros", retorno.getMensagem(), retorno.isError() ? "Erro" : "Alerta" );
			return;
		}
		view.setVisible( false );
		PrincipalPresenter.getInstancia().show( retorno.getEntidadeId() );
	}

	private void cancelar() {
		if( !temCampoPreenchido() || popup.showPopupConfirm( "Alterações não salvas", "Há alterações não salvas, deseja realmente sair da tela?" ) ) {
			view.setVisible( false );
			parent.show();
		}
	}

	private boolean temCampoPreenchido() {
		return ValidarCampo.isNotNullVazioOrEspacos( view.getTxtNome().getText() ) //
				|| ValidarCampo.isNotNullVazioOrEspacos( view.getTxtSobrenome().getText() ) //
				|| ValidarCampo.isNotNullVazioOrEspacos( view.getTxtEmail().getText() ) //
				|| ValidarCampo.isNotNullVazioOrEspacos( view.getTxtLogin().getText() ) //
				|| ValidarCampo.isNotNullVazioOrEspacos( String.valueOf( view.getPswSenha().getPassword() ) ) //
				|| ValidarCampo.isNotNullVazioOrEspacos( String.valueOf( view.getPswConfirmarSenha().getPassword() ) );
	}

	void show() {
		view.setVisible( true );
	}

}
