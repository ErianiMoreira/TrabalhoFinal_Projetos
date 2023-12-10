
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs.LogFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.notificacao.INotificacaoDAOObservador;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.notificacao.INotificacaoDAOObservavel;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.usuario.IUsuarioDAOObervador;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.usuario.IUsuarioDAOObservavel;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.impl.CsvLogService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.IUsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.impl.UsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.PrincipalView;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.swing.JInternalFrame;

/**
 *
 * @author marlan
 */
public class PrincipalPresenter implements INotificacaoDAOObservador, IUsuarioDAOObervador {

	private static PrincipalPresenter instancia = null;

	public static PrincipalPresenter getInstancia() {
		if( instancia == null ) {
			instancia = new PrincipalPresenter();
		}
		return instancia;
	}

	private int usuarioLogadoId;
	private Usuario usuarioLogado;
	private final String TEXTO_NOTIFICACOES = "Notificações (%s)";
	private final String TEXTO_USUARIO_LOGADO = "Usuário: %s - %s";
	private IUsuarioRepository usuarioRepository;
	private LogarPresenter logarPresenter;
	private PrincipalView view;
	private ManterUsuariosPresenter manterUsuariosPresenter;
	private NotificacoesUsuarioPresenter notificacoesPresenter;
	private AlterarSenhaPresenter alterarSenhaPresenter;

	private PrincipalPresenter() {
		try {
			logarPresenter = new LogarPresenter();
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "construir PrincipalPresenter", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}

			ex.printStackTrace();
		}
	}

	public void show( Integer usuarioLogadoId ) {
		this.usuarioLogadoId = usuarioLogadoId;
		try {
			DAOFactory.getDAOFactory().getNotificacaoDAO().adicionarObservador( instancia );
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "show PrincipalPresenter", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
		configurarView();
		view.setVisible( true );
	}

	private Map<String, JInternalFrame> componentes = new HashMap<>();

	public void add( String name, JInternalFrame component ) {
		view.getDesktopPane().add( name, component );
		componentes.put( name, component );
	}

	private void hide() {
		componentes.entrySet().forEach( e -> e.getValue().hide() );
		try {
			if( logarPresenter == null ) {
				logarPresenter = new LogarPresenter();
			}
			logarPresenter.show();
			view.setVisible( false );
		} catch ( Exception ex ) {
			ex.printStackTrace();
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "hide() da PrincipalPresenter", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex.printStackTrace();
			}
			System.exit( 0 );
		}
	}

	private void configurarView() {
		view = Optional.ofNullable( view ).orElse( new PrincipalView() );
		componentes.entrySet().forEach( e -> e.getValue().hide() );
		view.getExitMenuItem().addActionListener( a -> sair() );
		view.getBtnManterUsuarios().addActionListener( a -> {
			try {
				manterUsuarios();
			} catch ( Exception ex ) {
				try {
					LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "addActionListener em btnManterUsuarios da PrincipalPresenter", usuarioLogadoId );
				} catch ( Exception ex1 ) {
					ex1.printStackTrace();
				}
				ex.printStackTrace();
			}
		} );
		view.getBtnNotificacoes().addActionListener( a -> {
			try {
				gerenciarNotificacoes();
			} catch ( Exception ex ) {
				new CsvLogService().registrarFalha( ex.getMessage(), "addActionListener em btnNotificacoes da PrincipalPresenter", usuarioLogadoId );
				ex.printStackTrace();
			}
		} );
		view.getBtnAlteracaoSenha().addActionListener( a -> {
			try {
				alterarSenha();
			} catch ( Exception ex ) {
				new CsvLogService().registrarFalha( ex.getMessage(), "addActionListener em btnAlteracaoSenha da PrincipalPresenter", usuarioLogadoId );
				ex.printStackTrace();
			}
		} );
		atualizarContadorNotificacaoes();
		try {
			if( !usuarioLogadoIsAutenticado() ) {
				view.getBtnAlteracaoSenha().setEnabled( false );
				view.getBtnManterUsuarios().setEnabled( false );
			} else {
				view.getBtnAlteracaoSenha().setEnabled( true );
				view.getBtnManterUsuarios().setEnabled( true );
			}
		} catch ( Exception ex ) {
			new CsvLogService().registrarFalha( ex.getMessage(), "configurarView da PrincipalPresenter", usuarioLogadoId );
			ex.printStackTrace();
		}
	}

	private void sair() {
		hide();
	}

	private void manterUsuarios() throws Exception {
		if( manterUsuariosPresenter == null ) {
			manterUsuariosPresenter = new ManterUsuariosPresenter( usuarioLogadoId );
		}
		manterUsuariosPresenter.show( usuarioLogadoId );
	}

	private void gerenciarNotificacoes() throws Exception {
		if( notificacoesPresenter == null ) {
			notificacoesPresenter = new NotificacoesUsuarioPresenter( usuarioLogadoId );
		}
		notificacoesPresenter.show( usuarioLogadoId );
	}

	private void alterarSenha() throws Exception {
		if( alterarSenhaPresenter == null ) {
			alterarSenhaPresenter = new AlterarSenhaPresenter( usuarioLogadoId );
		}
		alterarSenhaPresenter.show( usuarioLogadoId );
	}

	private boolean usuarioLogadoIsAutenticado() throws Exception {
		usuarioLogado = Optional.ofNullable( usuarioRepository ).orElse( new UsuarioRepository() ).get( usuarioLogadoId );
		view.getLblUsuarioLogado().setText( String.format( TEXTO_USUARIO_LOGADO, usuarioLogado.getNomeCompleto(), usuarioLogado.getPermissoes() ) );
		return usuarioLogado.isAutorizado();
	}

	private void atualizarContadorNotificacaoes() {
		try {
			var notificacoes = DAOFactory.getDAOFactory().getNotificacaoDAO().findAllByParaUsuarioId( usuarioLogadoId ).stream().filter( n -> !n.isLida() ).toList();
			view.getBtnNotificacoes().setText( String.format( TEXTO_NOTIFICACOES, notificacoes.size() ) );
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "atualizarContadorNotificacaoes da PrincipalPresenter notificado", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}

	@Override
	public void atualizar( INotificacaoDAOObservavel observavel ) {
		try {
			var notificacoes = DAOFactory.getDAOFactory().getNotificacaoDAO().findAllByParaUsuarioId( usuarioLogadoId ).stream().filter( n -> !n.isLida() ).toList();
			view.getBtnNotificacoes().setText( String.format( TEXTO_NOTIFICACOES, notificacoes.size() ) );
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "atualizar observador PrincipalPresenter notificado pela INotificacaoDAOObservavel", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}

	@Override
	public void atualizar( IUsuarioDAOObservavel observavel ) {
		try {
			usuarioLogado = Optional.ofNullable( usuarioRepository ).orElse( new UsuarioRepository() ).get( usuarioLogadoId );
			view.getLblUsuarioLogado().setText( String.format( TEXTO_USUARIO_LOGADO, usuarioLogado.getNomeCompleto(), usuarioLogado.getPermissoes() ) );
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "atualizar observador PrincipalPresenter notificado pela IUsuarioDAOObservavel", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
}
