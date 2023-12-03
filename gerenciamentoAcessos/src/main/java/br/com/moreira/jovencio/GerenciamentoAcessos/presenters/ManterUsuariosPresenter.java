
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.views.ManterUsuariosView;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaManterUsuarios.TableActionCellEditor;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaManterUsuarios.TableActionCellRender;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaManterUsuarios.IAcoesTabelaManterUsuario;

/**
 *
 * @author marlan
 */
public class ManterUsuariosPresenter {

	private final ManterUsuariosView view;

	private FormularioUsuarioPresenter formularioUsuarioPresenter;

	public ManterUsuariosPresenter() {
		view = new ManterUsuariosView();
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
		gerarLinhasTabela();
		view.getBtnLimpar().addActionListener( a -> limparFiltros() );
		view.getBtnFiltrar().addActionListener( a -> filtrar() );
		PrincipalPresenter.getInstancia().add( this.getClass().getSimpleName(), view );
	}

	public void show() {
		view.setVisible( true );
		view.setSize( view.getPreferredSize() );
	}

	private void gerarLinhasTabela() {
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
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		model.addRow( new Object[]{ "Marlan", "30/11/2023 22:10:05", 0, 0 } );
		view.getTblListaUsuarios().setPreferredSize( new java.awt.Dimension( 1014, ( model.getRowCount() + 1 ) * 50 ) );
	}

	private void limparFiltros() {
		view.getTxtNome().setText( null );
		view.getCboxPossuiNotificacaoNaoLida().setSelectedIndex( 0 );
	}

	private void filtrar() {
		String nome = view.getTxtNome().getText();
		Boolean possuiNotificacaoNaoLida = converterPossuiNotificacaoNaoLida( String.valueOf( view.getCboxPossuiNotificacaoNaoLida().getSelectedItem() ) );
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.ManterUsuariosPresenter.filtrar( " + nome + ", " + possuiNotificacaoNaoLida + " )" );
	}

	private void autorizarByLinha( int linha ) {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.ManterUsuariosPresenter.autorizarByLinha( " + linha + " )" );
	}

	private void editarByLinha( int linha ) {
		if( formularioUsuarioPresenter != null ) {
			formularioUsuarioPresenter.close();
		}
		formularioUsuarioPresenter = new FormularioUsuarioPresenter();
		formularioUsuarioPresenter.show( linha );
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.ManterUsuariosPresenter.editarByLinha( " + linha + " )" );
	}

	private void notificarByLinha( int linha ) {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.ManterUsuariosPresenter.notificarByLinha(  " + linha + "  )" );
	}

	private void visualizarByLinha( int linha ) {
		if( formularioUsuarioPresenter != null ) {
			formularioUsuarioPresenter.close();
		}
		formularioUsuarioPresenter = new FormularioUsuarioPresenter();
		formularioUsuarioPresenter.show( linha );
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.ManterUsuariosPresenter.visualizarByLinha( " + linha + " )" );
	}

	private Boolean converterPossuiNotificacaoNaoLida( String valorSelecionado ) {
		return switch ( valorSelecionado ) {
			case "Sim" -> true;
			case "Não" -> false;
			default -> null;
		};
	}

}
