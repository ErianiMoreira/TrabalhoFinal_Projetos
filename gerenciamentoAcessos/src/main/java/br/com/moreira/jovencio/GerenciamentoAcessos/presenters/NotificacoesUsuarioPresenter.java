
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.views.NotificacoesUsuarioView;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes.IAcoesTabelaNotificacoes;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes.TableActionCellEditor;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes.TableActionCellRender;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marlan
 */
public class NotificacoesUsuarioPresenter {

	private final NotificacoesUsuarioView view;

	public NotificacoesUsuarioPresenter() {
		view = new NotificacoesUsuarioView();
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		gerarLinhasTabela();
		view.getBtnVoltar().addActionListener( a -> voltar() );
		PrincipalPresenter.getInstancia().add( this.getClass().getSimpleName(), view );
	}

	public void show() {
		view.setVisible( true );
		view.setSize( view.getPreferredSize() );
	}

	public void show( Integer usuarioId ) {
		view.setVisible( true );
		view.setSize( view.getPreferredSize() );
	}

	public void close() {
		view.setVisible( false );
		try {
			view.setClosed( true );
		} catch ( PropertyVetoException ex ) {
			Logger.getLogger( FormularioUsuarioPresenter.class.getName() ).log( Level.SEVERE, null, ex );
		}
	}

	private void voltar() {
	}

	private void gerarLinhasTabela() {
		DefaultTableModel model = ( DefaultTableModel ) view.getTblNotificacoes().getModel();
		model.setNumRows( 0 );
		IAcoesTabelaNotificacoes event = ( int linha ) -> {
			lidaNaoLidaByLinha( linha );
		};
		view.getTblNotificacoes().getColumnModel().getColumn( view.getTblNotificacoes().getColumnModel().getColumnCount() - 1 ).setCellRenderer( new TableActionCellRender() );
		view.getTblNotificacoes().getColumnModel().getColumn( view.getTblNotificacoes().getColumnModel().getColumnCount() - 1 ).setCellEditor( new TableActionCellEditor( event ) );
		model.addRow( new Object[]{ "Não", "30/11/2023 22:10:05" } );
		model.addRow( new Object[]{ "Não", "30/11/2023 22:10:05" } );
		model.addRow( new Object[]{ "Não", "30/11/2023 22:10:05" } );
		model.addRow( new Object[]{ "Não", "30/11/2023 22:10:05" } );
		model.addRow( new Object[]{ "Não", "30/11/2023 22:10:05" } );
		view.getTblNotificacoes().setPreferredSize( new java.awt.Dimension( 1014, ( model.getRowCount() + 1 ) * 50 ) );
	}

	private void lidaNaoLidaByLinha( int linha ) {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.NotificacoesUsuarioPresenter.lidaNaoLidaByLinha( " + linha + " )" );
	}

}
