
package br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaManterUsuarios;

import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaManterUsuarios.PanelActionTabelaManterUsuarios;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author marlan
 */
public class TableActionCellRender extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		var component = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		PanelActionTabelaManterUsuarios action = new PanelActionTabelaManterUsuarios();
		if( isSelected && row % 2 == 0 ) {
			action.setBackground( Color.white );
		} else {
			action.setBackground( component.getBackground() );
		}

		return action;
	}

}
