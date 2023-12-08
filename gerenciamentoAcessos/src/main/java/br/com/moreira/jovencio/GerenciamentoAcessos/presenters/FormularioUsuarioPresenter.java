
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.IUsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.impl.UsuarioRepository;
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

	private int usuarioId;
	private Usuario usuario;

	private FormularioUsuarioView view;

	private IUsuarioRepository usuarioRepository;

	public FormularioUsuarioPresenter( int usuarioId ) throws Exception {
		view = new FormularioUsuarioView();
		usuarioRepository = new UsuarioRepository();
		this.usuarioId = usuarioId;
		configurarDados();
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		view.getBtnResetarSenha().addActionListener( a -> resetarSenha() );
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

	public void close() {
		view.setVisible( false );
		try {
			view.setClosed( true );
		} catch ( PropertyVetoException ex ) {
			Logger.getLogger( FormularioUsuarioPresenter.class.getName() ).log( Level.SEVERE, null, ex );
		}
	}

	private void resetarSenha() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.FormularioUsuarioPresenter.resetarSenha()" );
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

	private void configurarDados() throws Exception {
		usuario = usuarioRepository.get( usuarioId );
		view.getTxtNome().setText( usuario.getNome() );
		view.getTxtSobrenome().setText( usuario.getSobrenome() );
		view.getTxtEmail().setText( usuario.getEmail() );
		view.getTxtLogin().setText( usuario.getLogin() );
	}
}
