
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.views.PrincipalView;
import java.util.Optional;
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

	private int usuarioLogadoId;
	private PrincipalView view;
	private ManterUsuariosPresenter manterUsuariosPresenter;
	private NotificacoesUsuarioPresenter notificacoesPresenter;
	private AlterarSenhaPresenter alterarSenhaPresenter;

	private PrincipalPresenter() {
		try {
			new LogarPresenter();
		} catch ( Exception ex ) {
			ex.printStackTrace();
			// TODO: tratar erro
		}
	}

	public void show( Integer usuarioLogadoId ) {
		this.usuarioLogadoId = usuarioLogadoId;
		if( view == null ) {
			configurarView();
		}
		view.setVisible( true );
	}

	public void add( String name, JInternalFrame component ) {
		view.getDesktopPane().add( name, component );
	}

	private void hide() {
		configurarView();
		view.setVisible( false );
	}

	private void configurarView() {
		view = Optional.ofNullable( view ).orElse( new PrincipalView() );
		view.getExitMenuItem().addActionListener( a -> sair() );
		view.getBtnManterUsuarios().addActionListener( a -> {
			try {
				manterUsuarios();
			} catch ( Exception ex ) {
				ex.printStackTrace();
			}
		} );
		view.getBtnNotificacoes().addActionListener( a -> {
			try {
				gerenciarNotificacoes();
			} catch ( Exception ex ) {
				ex.printStackTrace();
			}
		} );
		view.getBtnAlteracaoSenha().addActionListener( a -> alterarSenha() );
	}

	private void sair() {
		hide();
		try {
			new LogarPresenter();
		} catch ( Exception ex ) {
			// TODO: tratar erro
		}
	}

	private void manterUsuarios() throws Exception {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.PrincipalPresenter.manterUsuarios()" );
		if( manterUsuariosPresenter == null ) {
			manterUsuariosPresenter = new ManterUsuariosPresenter( usuarioLogadoId );
		}
		manterUsuariosPresenter.show();
	}

	private void gerenciarNotificacoes() throws Exception {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.PrincipalPresenter.gerenciarNotificacoes()" );
		if( notificacoesPresenter == null ) {
			notificacoesPresenter = new NotificacoesUsuarioPresenter( usuarioLogadoId );
		}
		notificacoesPresenter.show();
	}

	private void alterarSenha() {
		System.out.println( "br.com.moreira.jovencio.GerenciamentoAcessos.presenters.PrincipalPresenter.alterarSenha()" );
		if( alterarSenhaPresenter == null ) {
			alterarSenhaPresenter = new AlterarSenhaPresenter( usuarioLogadoId );
		}
		alterarSenhaPresenter.show();
	}
}
