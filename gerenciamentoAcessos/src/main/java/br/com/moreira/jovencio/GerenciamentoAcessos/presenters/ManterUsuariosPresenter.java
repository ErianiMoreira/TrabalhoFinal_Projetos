
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioGridDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.IUsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.impl.UsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.ManterUsuariosView;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaManterUsuarios.IAcoesTabelaManterUsuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaManterUsuarios.TableActionCellEditor;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaManterUsuarios.TableActionCellRender;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marlan
 */
public class ManterUsuariosPresenter {

	private final int usuarioLogadoId;

	private final List<NotificarUsuarioPresenter> notificarUsuarioPresenters;

	private final ManterUsuariosView view;

	private FormularioUsuarioPresenter formularioUsuarioPresenter;

	private IUsuarioRepository usuarioRepository;

	public ManterUsuariosPresenter( int usuarioLogadoId ) throws Exception {
		this.notificarUsuarioPresenters = new ArrayList<>();
		this.usuarioLogadoId = usuarioLogadoId;
		view = new ManterUsuariosView();
		usuarioRepository = new UsuarioRepository();
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
		gerarLinhasTabela( null, null );
		view.getBtnLimpar().addActionListener( a -> limparFiltros() );
		view.getBtnFiltrar().addActionListener( a -> filtrar() );
		PrincipalPresenter.getInstancia().add( this.getClass().getSimpleName(), view );
	}

	public void show() {
		view.setVisible( true );
		view.setSize( view.getPreferredSize() );
	}

	private void gerarLinhasTabela( String nome, Boolean possuiNotificacoes ) {
		DefaultTableModel model = ( DefaultTableModel ) view.getTblListaUsuarios().getModel();
		model.setNumRows( 0 );
		IAcoesTabelaManterUsuario event = new IAcoesTabelaManterUsuario() {

			@Override
			public void autorizar( int row ) {
				autorizarByLinha( row );
			}

			@Override
			public void editar( int row ) {
				editarByLinha( row );
			}

			@Override
			public void notificar( int row ) {
				notificarByLinha( row );
			}

			@Override
			public void visualizar( int row ) {
				visualizarByLinha( row );
			}
		};
		view.getTblListaUsuarios().getColumnModel().getColumn( view.getTblListaUsuarios().getColumnModel().getColumnCount() - 1 ).setCellRenderer( new TableActionCellRender() );
		view.getTblListaUsuarios().getColumnModel().getColumn( view.getTblListaUsuarios().getColumnModel().getColumnCount() - 1 ).setCellEditor( new TableActionCellEditor( event ) );

		List<UsuarioGridDTO> usuarios = usuarioRepository.search( nome, possuiNotificacoes );
		for( var usuario : usuarios ) {
			model.addRow( new Object[]{ usuario.getId(), usuario.getNomeCompleto(), usuario.getDataCadastroFormatada(), usuario.getNotificacoesEnviadas(), usuario.getNotificacoesLidas(), usuario.getEstaAutorizado() } );
		}
		view.getTblListaUsuarios().setPreferredSize( new java.awt.Dimension( 1014, ( model.getRowCount() + 1 ) * 50 ) );
	}

	private void limparFiltros() {
		view.getTxtNome().setText( null );
		view.getCboxPossuiNotificacaoNaoLida().setSelectedIndex( 0 );
	}

	private void filtrar() {
		String nome = view.getTxtNome().getText();
		Boolean possuiNotificacaoNaoLida = converterPossuiNotificacaoNaoLida( String.valueOf( view.getCboxPossuiNotificacaoNaoLida().getSelectedItem() ) );
		gerarLinhasTabela( nome, possuiNotificacaoNaoLida );
	}

	private void autorizarByLinha( int linha ) {
		INotificarPoPopupService notificar = new NotificarPoPopupService( view );
		boolean confirmou = notificar.showPopupConfirm( "Ao confirmar este usuário será autorizado a usar as funcionálidades do sistema.", "Certeza que deseja autorizar este usuário?" );
		if( confirmou ) {
			var id = findIdByLinha( linha );
		}
	}

	private void editarByLinha( int linha ) {
		try {
			var id = findIdByLinha( linha );
			if( formularioUsuarioPresenter != null ) {
				formularioUsuarioPresenter.close();
			}
			formularioUsuarioPresenter = new FormularioUsuarioPresenter( id );
			formularioUsuarioPresenter.show();
			System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.ManterUsuariosPresenter.editarByLinha( " + linha + " )" );
		} catch ( Exception ex ) {
			tratarErro( ex );
		}
	}

	private void notificarByLinha( int linha ) {
		try {
			int paraUsuarioId = findIdByLinha( linha );
			var presenter = notificarUsuarioPresenters.stream().filter( p -> p.isParaUsuarioId( paraUsuarioId ) ).findFirst();
			if( presenter.isEmpty() ) {
				notificarUsuarioPresenters.add( new NotificarUsuarioPresenter( this, usuarioLogadoId, paraUsuarioId ) );
			} else {
				presenter.get().expandir();
			}
		} catch ( Exception ex ) {
			tratarErro( ex );
		}
	}

	private void visualizarByLinha( int linha ) {
		try {
			var id = findIdByLinha( linha );
			if( formularioUsuarioPresenter != null ) {
				formularioUsuarioPresenter.close();
			}
			formularioUsuarioPresenter = new FormularioUsuarioPresenter( id );
			formularioUsuarioPresenter.show();
			System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.ManterUsuariosPresenter.visualizarByLinha( " + linha + " )" );
		} catch ( Exception ex ) {
			tratarErro( ex );
		}
	}

	private Boolean converterPossuiNotificacaoNaoLida( String valorSelecionado ) {
		return switch ( valorSelecionado ) {
			case "Sim" -> true;
			case "Não" -> false;
			default -> null;
		};
	}

	private int findIdByLinha( int linha ) {
		var map = new HashMap<String, Integer>();
		for( int i = 0; i < view.getTblListaUsuarios().getModel().getColumnCount(); i++ ) {
			map.put( view.getTblListaUsuarios().getModel().getColumnName( i ), i );
		}
		return ( int ) view.getTblListaUsuarios().getModel().getValueAt( linha, map.get( "Id" ) );
	}

	private void tratarErro( Exception ex ) {
		ex.printStackTrace();
		INotificarPoPopupService notificar = new NotificarPoPopupService( view );
		notificar.showPopupOk( "Erro", "Erro inesperado ao tentar executar a ação, favor tentar novamente.", "Erro" );

	}

	void removeNotificarPara( int paraUsuarioId ) {
		var itensRemover = notificarUsuarioPresenters.stream().filter( p -> !p.isParaUsuarioId( paraUsuarioId ) ).toList();
		notificarUsuarioPresenters.removeAll( itensRemover );
	}

}
