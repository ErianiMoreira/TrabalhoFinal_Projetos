
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.IUsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.impl.UsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.NotificarUsuarioView;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author marlan
 */
public class NotificarUsuarioPresenter {

	private final int deUsuarioId;
	private final int paraUsuarioId;
	private Usuario paraUsuario;
	private final NotificarUsuarioView view;
	private final IUsuarioRepository usuarioRepository;
	private final INotificarUsuarioService notificarUsuarioService;
	private final ManterUsuariosPresenter presenterParent;

	public NotificarUsuarioPresenter( ManterUsuariosPresenter presenterParent, int deUsuarioId, int paraUsuarioId ) throws Exception {
		usuarioRepository = new UsuarioRepository();
		notificarUsuarioService = new NotificarUsuarioService();
		this.presenterParent = presenterParent;
		this.deUsuarioId = deUsuarioId;
		this.paraUsuarioId = paraUsuarioId;
		this.view = new NotificarUsuarioView();
		configurarDados();
		view.setVisible( false );
		view.getBtnEnviar().addActionListener( ( ActionEvent a ) -> enviarNotificacao() );
		PrincipalPresenter.getInstancia().add( this.getClass().getSimpleName() + paraUsuarioId, view );
		view.setVisible( true );
		view.addInternalFrameListener( onClose() );
	}

	private void configurarDados() throws Exception {
		paraUsuario = usuarioRepository.get( paraUsuarioId );
		view.getLblNotificarUsuarioTal().setText( String.format( view.getLblNotificarUsuarioTal().getText(), paraUsuario.getNome() + " " + paraUsuario.getSobrenome() ) );
	}

	private void enviarNotificacao() {
		INotificarPoPopupService notificar = new NotificarPoPopupService( view );
		var mensagem = view.getTxtMensagem().getText();
		boolean confirmou = notificar.showPopupConfirm( "Confirmar envio da mensagem \"" + mensagem + "\" para o usuário?", view.getLblNotificarUsuarioTal().getText() );
		if( confirmou ) {
			var retorno = notificarUsuarioService.notificar( deUsuarioId, paraUsuarioId, mensagem );
			if( retorno.isSuccess() ) {
				notificar.showPopupOk( "Sucesso", "Notificação enviada com sucesso", null );
				view.dispose();
			} else {
				notificar.showPopupOk( retorno.isError() ? "Erro" : "Alerta", retorno.getMensagem(), retorno.isError() ? "Erro" : "Alerta" );
			}
		}
	}

	void expandir() {
		try {
			view.setVisible( true );
			view.setSelected( true );
		} catch ( PropertyVetoException ex ) {
		}
	}

	public boolean isParaUsuarioId( int paraUsuarioId ) {
		return this.paraUsuarioId == paraUsuarioId;
	}

	private InternalFrameAdapter onClose() {
		return new InternalFrameAdapter() {

			@Override
			public void internalFrameClosing( InternalFrameEvent e ) {
				presenterParent.removeNotificarPara( paraUsuarioId );
			}
		};
	}
}