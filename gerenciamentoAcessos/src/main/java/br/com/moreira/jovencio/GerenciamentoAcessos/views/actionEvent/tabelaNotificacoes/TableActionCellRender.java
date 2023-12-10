
package br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author marlan/eriani
 */
public class TableActionCellRender extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		var component = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		PanelActionTabelaNotificacoes action = new PanelActionTabelaNotificacoes();
		if( isSelected && row % 2 == 0 ) {
			action.setBackground( Color.white );
		} else {
			action.setBackground( component.getBackground() );
		}

		return action;
	}

}
