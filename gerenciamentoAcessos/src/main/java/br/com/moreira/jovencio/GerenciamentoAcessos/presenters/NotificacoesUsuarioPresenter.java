
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs.LogFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Notificacao;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.notificacao.INotificacaoDAOObservador;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.notificacao.INotificacaoDAOObservavel;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.NotificacoesUsuarioView;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes.IAcoesTabelaNotificacoes;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes.TableActionCellEditor;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaNotificacoes.TableActionCellRender;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marlan/eriani
 */
public class NotificacoesUsuarioPresenter implements INotificacaoDAOObservador {

	private int usuarioLogadoId;
	private final NotificacoesUsuarioView view;
	private final INotificarPoPopupService notificarPopUp;
	private final INotificarUsuarioService notificarUsuarioService;

	public NotificacoesUsuarioPresenter( int usuarioLogadoId ) throws Exception {
		view = new NotificacoesUsuarioView();
		this.notificarPopUp = new NotificarPoPopupService( view );
		this.usuarioLogadoId = usuarioLogadoId;
		notificarUsuarioService = new NotificarUsuarioService();
		DAOFactory.getDAOFactory().getNotificacaoDAO().adicionarObservador( this );
		configurarView();
		PrincipalPresenter.getInstancia().add( this.getClass().getSimpleName(), view );
	}

	public void show( int usuarioLogaoId ) throws Exception {
		this.usuarioLogadoId = usuarioLogaoId;
		configurarView();
		view.setVisible( true );
		view.setSize( view.getPreferredSize() );
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
			model.addRow( new Object[]{ //
					notificacao.getId(), //
					notificacao.isLida() ? "Sim" : "Não", //
					notificacao.getDataEnvio().format( DateTimeFormatter.ofPattern( "dd/MM/yyyy H:mm:ss" ) ), //
					notificacao.getMensagem() } );
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
			notificarPopUp.showPopupOk( retorno.isError() ? "Erro" : "Alerta", retorno.getMensagem(), retorno.isError() ? "Erro" : "Alerta" );
		} else {
			view.getTblNotificacoes().getModel().setValueAt( "Sim".equalsIgnoreCase( lida ) ? "Não" : "Sim", linha, map.get( "Lida?" ) );
		}
	}

	private void configurarView() throws Exception {
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		view.getLblNotificarUsuario().setText( String.format( "Notificações do Usuário %s", "#" + this.usuarioLogadoId ) );
		gerarLinhasTabela();
	}

	@Override
	public void atualizar( INotificacaoDAOObservavel observavel ) {
		try {
			gerarLinhasTabela();
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "atualizar observador NotificacoesUsuarioPresenter notificado pela INotificacaoDAOObservavel", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
}
