
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.views.ConfirmarDialogView;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author marlan
 */
public class ConfirmarDialogPresenter {

	private ConfirmarDialogView view;

	public ConfirmarDialogPresenter( JFrame parent, String titulo, String mensagem ) {
		view = new ConfirmarDialogView( parent, true );
		view.setVisible( false );
		view.getLblTitulo().setText( titulo );
		view.getLblMensagem().setText( mensagem );
		var d = new Dimension( Double.valueOf( view.getLblMensagem().getPreferredSize().getWidth() ).intValue(), ( ( mensagem.length() / 50 ) + 1 ) * 40 );
		view.getLblMensagem().setSize( d );
		view.getLblMensagem().setPreferredSize( d );
		view.getBtnConfirmar().addActionListener( l -> fechar() );
		view.setVisible( true );
	}

	private void fechar() {
		view.dispose();
	}

}
