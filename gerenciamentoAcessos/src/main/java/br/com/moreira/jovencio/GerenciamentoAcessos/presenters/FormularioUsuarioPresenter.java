
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.views.FormularioUsuarioView;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.WindowConstants;

/**
 *
 * @author marlan
 */
public class FormularioUsuarioPresenter {

	private Integer usuarioId;

	private FormularioUsuarioView view;

	public FormularioUsuarioPresenter() {
		view = new FormularioUsuarioView();
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		view.getBtnVoltar().addActionListener( a -> voltar() );
		view.getBtnAutorizar().addActionListener( a -> autorizar() );
		view.getBtnExcluir().addActionListener( a -> excluir() );
		view.getBtnEditar().addActionListener( a -> editar() );
		view.getBtnSalvar().addActionListener( a -> salvar() );
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
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.FormularioUsuarioPresenter.voltar()" );
	}

	private void autorizar() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.FormularioUsuarioPresenter.autorizar()" );
	}

	private void excluir() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.FormularioUsuarioPresenter.excluir()" );
	}

	private void editar() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.FormularioUsuarioPresenter.editar()" );
	}

	private void salvar() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.FormularioUsuarioPresenter.salvar()" );
	}

}
