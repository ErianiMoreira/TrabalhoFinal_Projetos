
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.views.AlterarSenhaView;
import javax.swing.WindowConstants;

/**
 *
 * @author marlan
 */
public class AlterarSenhaPresenter {

	private AlterarSenhaView view;

	public AlterarSenhaPresenter() {
		view = new AlterarSenhaView();
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
		view.getBtnAlterarSenha().addActionListener( a -> alterarSenha() );
		PrincipalPresenter.getInstancia().add( this.getClass().getSimpleName(), view );
	}

	public void show() {
		view.setVisible( true );
		view.setSize( view.getPreferredSize() );
	}

	private void alterarSenha() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.AlterarSenhaPresenter.alterarSenha()" );
	}

}
