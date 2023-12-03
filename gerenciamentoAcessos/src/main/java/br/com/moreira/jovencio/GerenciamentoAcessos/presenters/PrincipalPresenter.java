
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.views.PrincipalView;
import javax.swing.JInternalFrame;

/**
 *
 * @author marlan
 */
public class PrincipalPresenter {

	private static PrincipalPresenter instancia = null;

	public static PrincipalPresenter getInstancia() {
		if( instancia == null ) {
			instancia = new PrincipalPresenter();
		}
		return instancia;
	}

	private PrincipalView view;
	private ManterUsuariosPresenter manterUsuariosPresenter;
	private NotificacoesUsuarioPresenter notificacoesPresenter;
	private AlterarSenhaPresenter alterarSenhaPresenter;

	private PrincipalPresenter() {
		new LogarPresenter();
	}

	public void show( Integer usuarioLogadoId ) {
		if( view == null ) {
			configurarView();
		}
		view.setVisible( true );
	}

	public void add( String name, JInternalFrame component ) {
		view.getDesktopPane().add( name, component );
	}

	private void hide() {
		if( view == null ) {
			configurarView();
		}
		view.setVisible( false );
	}

	private void configurarView() {
		view = new PrincipalView();
		view.getExitMenuItem().addActionListener( a -> sair() );
		view.getBtnManterUsuarios().addActionListener( a -> manterUsuarios() );
		view.getBtnNotificacoes().addActionListener( a -> gerenciarNotificacoes() );
		view.getBtnAlteracaoSenha().addActionListener( a -> alterarSenha() );
	}

	private void sair() {
		hide();
		new LogarPresenter();
	}

	private void manterUsuarios() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.PrincipalPresenter.manterUsuarios()" );
		if( manterUsuariosPresenter == null ) {
			manterUsuariosPresenter = new ManterUsuariosPresenter();
		}
		manterUsuariosPresenter.show();
	}

	private void gerenciarNotificacoes() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.PrincipalPresenter.gerenciarNotificacoes()" );
		if( notificacoesPresenter == null ) {
			notificacoesPresenter = new NotificacoesUsuarioPresenter();
		}
		notificacoesPresenter.show();
	}

	private void alterarSenha() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.PrincipalPresenter.alterarSenha()" );
		if( alterarSenhaPresenter == null ) {
			alterarSenhaPresenter = new AlterarSenhaPresenter();
		}
		alterarSenhaPresenter.show();
	}
}
