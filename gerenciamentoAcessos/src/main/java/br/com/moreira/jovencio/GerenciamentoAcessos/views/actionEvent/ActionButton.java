
package br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author marlan
 */
public class ActionButton extends JButton {

	private boolean mousePress;

	public ActionButton() {
		setContentAreaFilled( false );
		setBorder( new EmptyBorder( 3, 3, 3, 3 ) );
		addMouseListener( new MouseAdapter() {

			@Override
			public void mousePressed( MouseEvent e ) {
				mousePress = true;
			}

			@Override
			public void mouseReleased( MouseEvent e ) {
				mousePress = false;
			}

		} );
	}
}
