
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Notificacao;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.NotificacoesUsuarioView;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes.IAcoesTabelaNotificacoes;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes.TableActionCellEditor;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes.TableActionCellRender;
import java.beans.PropertyVetoException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marlan
 */
public class NotificacoesUsuarioPresenter {

	private int usuarioLogadoId;
	private final NotificacoesUsuarioView view;
	private final INotificarUsuarioService notificarUsuarioService;

	public NotificacoesUsuarioPresenter( int usuarioLogadoId ) throws Exception {
		view = new NotificacoesUsuarioView();
		this.usuarioLogadoId = usuarioLogadoId;
		notificarUsuarioService = new NotificarUsuarioService();
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

	private void gerarLinhasTabela() throws Exception {
		DefaultTableModel model = ( DefaultTableModel ) view.getTblNotificacoes().getModel();
		model.setNumRows( 0 );

		IAcoesTabelaNotificacoes event = ( int linha ) -> {
			lidaNaoLidaByLinha( linha );
		};
		view.getTblNotificacoes().getColumnModel().getColumn( view.getTblNotificacoes().getColumnModel().getColumnCount() - 1 ).setCellRenderer( new TableActionCellRender() );
		view.getTblNotificacoes().getColumnModel().getColumn( view.getTblNotificacoes().getColumnModel().getColumnCount() - 1 ).setCellEditor( new TableActionCellEditor( event ) );

		List<Notificacao> notificacoes = notificarUsuarioService.findAllByParaUsuarioId( usuarioLogadoId );
		for( var notificacao : notificacoes ) {
			model.addRow( new Object[]{ notificacao.getId(), notificacao.isLida() ? "Sim" : "Não", notificacao.getDataEnvio().format( DateTimeFormatter.ofPattern( "dd/MM/yyyy H:mm:ss" ) ) } );
		}
		view.getTblNotificacoes().setPreferredSize( new java.awt.Dimension( 1014, ( model.getRowCount() + 1 ) * 50 ) );
	}

	private void lidaNaoLidaByLinha( int linha ) {
		var map = new HashMap<String, Integer>();
		for( int i = 0; i < view.getTblNotificacoes().getModel().getColumnCount(); i++ ) {
			map.put( view.getTblNotificacoes().getModel().getColumnName( i ), i );
		}
		int id = ( int ) view.getTblNotificacoes().getModel().getValueAt( linha, map.get( "Id" ) );
		String lida = ( String ) view.getTblNotificacoes().getModel().getValueAt( linha, map.get( "Lida?" ) );
		var retorno = notificarUsuarioService.marcar( id, !"Sim".equalsIgnoreCase( lida ) );
		if( !retorno.isSuccess() ) {
			INotificarPoPopupService notificar = new NotificarPoPopupService( view );
			notificar.showPopupOk( retorno.isError() ? "Erro" : "Alerta", retorno.getMensagem(), retorno.isError() ? "Erro" : "Alerta" );
		} else {
			view.getTblNotificacoes().getModel().setValueAt( "Sim".equalsIgnoreCase( lida ) ? "Não" : "Sim", linha, map.get( "Lida?" ) );
		}
	}

}
