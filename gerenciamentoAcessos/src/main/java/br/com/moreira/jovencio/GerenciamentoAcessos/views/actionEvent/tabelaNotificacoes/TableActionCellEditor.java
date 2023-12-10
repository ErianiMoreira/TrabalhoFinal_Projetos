
package br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author marlan/eriani
 */
public class TableActionCellEditor extends DefaultCellEditor {

	private final IAcoesTabelaNotificacoes event;

	public TableActionCellEditor( IAcoesTabelaNotificacoes event ) {
		super( new JCheckBox() );
		this.event = event;
	}

	@Override
	public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int column ) {
		var action = new PanelActionTabelaNotificacoes();
		action.initEvent( event, row );
		action.setBackground( table.getSelectionBackground() );
		return action;
	}

}
