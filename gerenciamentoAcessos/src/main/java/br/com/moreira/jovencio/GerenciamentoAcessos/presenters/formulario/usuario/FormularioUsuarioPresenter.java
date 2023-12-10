
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs.LogFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.PrincipalPresenter;
import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.estados.EstadoEdicaoFormularioUsuarioPresenter;
import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.estados.EstadoFormularioUsuarioPresenter;
import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.estados.EstadoInclusaoFormularioUsuarioPresenter;
import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.estados.EstadoVisualizacaoFormularioUsuarioPresenter;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ICadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.CadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.NotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.IUsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.impl.UsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.views.FormularioUsuarioView;
import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.swing.WindowConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marlan/eriani
 */
public class FormularioUsuarioPresenter {

	private final Logger log = LoggerFactory.getLogger( FormularioUsuarioPresenter.class );

	private int usuarioLogadoId;
	private Integer usuarioId;
	private Usuario usuario;
	private EstadoFormularioUsuarioPresenter estado;

	private final FormularioUsuarioView view;

	private IUsuarioRepository usuarioRepository;

	private ICadastrarUsuarioService cadastrarUsuarioService;

	private final INotificarPoPopupService popup;

	public FormularioUsuarioPresenter( int usuarioLogadoId, Integer usuarioId, String estado ) throws Exception {
		view = new FormularioUsuarioView();
		usuarioRepository = new UsuarioRepository();
		cadastrarUsuarioService = new CadastrarUsuarioService(usuarioLogadoId);
		popup = new NotificarPoPopupService( view );
		this.usuarioLogadoId = usuarioLogadoId;
		this.usuarioId = usuarioId;
		this.estado = findEstado( estado );
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		view.getBtnResetarSenha().addActionListener( a -> resetarSenha() );
		view.getBtnAutorizar().addActionListener( a -> autorizar() );
		view.getBtnExcluir().addActionListener( a -> excluir() );
		view.getBtnEditar().addActionListener( a -> editar() );
		view.getBtnSalvar().addActionListener( a -> salvar() );
		PrincipalPresenter.getInstancia().add( this.getClass().getSimpleName(), view );
	}

	private EstadoFormularioUsuarioPresenter findEstado( String estado ) throws Exception {
		if( "Edicao".equalsIgnoreCase( estado ) ) {
			return new EstadoEdicaoFormularioUsuarioPresenter( this );
		} else if( "Inclusao".equalsIgnoreCase( estado ) ) {
			return new EstadoInclusaoFormularioUsuarioPresenter( this );
		} else if( "Visualizacao".equalsIgnoreCase( estado ) ) {
			return new EstadoVisualizacaoFormularioUsuarioPresenter( this );
		}
		throw new Exception( "Estado não fornecido'" );
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
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "close da FormularioUsuarioPresenter", 0 );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			log.error( ex.getMessage() );
		}
	}

	private void resetarSenha() {
		estado.resetarSenhaUsuario();
	}

	private void autorizar() {
		estado.autorizarUsuario();
	}

	private void excluir() {
		estado.excluirUsuario();
	}

	private void editar() {
		estado.editarUsuario();
	}

	private void salvar() {
		estado.salvarUsuario();
	}

	private void configurarDados() throws Exception {
		usuario = usuarioRepository.get( usuarioId );
		view.getTxtNome().setText( usuario.getNome() );
		view.getTxtSobrenome().setText( usuario.getSobrenome() );
		view.getTxtEmail().setText( usuario.getEmail() );
		view.getTxtLogin().setText( usuario.getLogin() );
	}

	public EstadoFormularioUsuarioPresenter getEstado() {
		return estado;
	}

	public void setEstado( EstadoFormularioUsuarioPresenter estado ) {
		this.estado = estado;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId( Integer usuarioId ) {
		this.usuarioId = usuarioId;
	}

	public void removerUsuario() {
		usuario = null;
		usuarioId = null;
	}

	private final List<String> TODOS_CAMPOS = List.of( "nome", "sobrenome", "email", "login" );

	public void habilitarCampos( String... campos ) {
		var camposList = Arrays.asList( campos );
		if( camposList.contains( "todos" ) ) {
			camposList = TODOS_CAMPOS;
		}
		estadoCampos( camposList, true );
	}

	public void desabilitarCampos( String... campos ) {
		var camposList = Arrays.asList( campos );
		if( camposList.contains( "todos" ) ) {
			camposList = TODOS_CAMPOS;
		}
		estadoCampos( camposList, false );
	}

	private void estadoCampos( List<String> campos, boolean habilidado ) {
		for( var campo : campos ) {
			if( "nome".equalsIgnoreCase( campo ) ) {
				view.getTxtNome().setEnabled( habilidado );
			}
			if( "sobrenome".equalsIgnoreCase( campo ) ) {
				view.getTxtSobrenome().setEnabled( habilidado );
			}
			if( "email".equalsIgnoreCase( campo ) || "e-mail".equalsIgnoreCase( campo ) ) {
				view.getTxtEmail().setEnabled( habilidado );
			}
			if( "login".equalsIgnoreCase( campo ) ) {
				view.getTxtLogin().setEnabled( habilidado );
			}
		}
	}

	private final List<String> TODOS_BOTOES = List.of( "resetar senha", "autorizar", "excluir", "editar", "salvar" );

	public void habilitarBotoes( String... botoes ) {
		var botoesList = Arrays.asList( botoes );
		if( botoesList.contains( "todos" ) ) {
			botoesList = TODOS_BOTOES;
		}
		estadoBotoes( botoesList, true );
	}

	public void desabilitarBotoes( String... botoes ) {
		var botoesList = Arrays.asList( botoes );
		if( botoesList.contains( "todos" ) ) {
			botoesList = TODOS_BOTOES;
		}
		estadoBotoes( botoesList, false );
	}

	private void estadoBotoes( List<String> botoes, boolean habilidado ) {
		for( var botao : botoes ) {
			if( "resetar senha".equalsIgnoreCase( botao ) ) {
				view.getBtnResetarSenha().setEnabled( habilidado );
			}
			if( "autorizar".equalsIgnoreCase( botao ) ) {
				view.getBtnAutorizar().setEnabled( habilidado );
			}
			if( "excluir".equalsIgnoreCase( botao ) ) {
				view.getBtnExcluir().setEnabled( habilidado );
			}
			if( "editar".equalsIgnoreCase( botao ) ) {
				view.getBtnEditar().setEnabled( habilidado );
			}
			if( "salvar".equalsIgnoreCase( botao ) ) {
				view.getBtnSalvar().setEnabled( habilidado );
			}
		}
	}

	public String getValorTxtNome() {
		return view.getTxtNome().getText();
	}

	public void setValorTxtNome( String nome ) {
		view.getTxtNome().setText( nome );
	}

	public String getValorTxtSobrenome() {
		return view.getTxtSobrenome().getText();
	}

	public void setValorTxtSobrenome( String sobrenome ) {
		view.getTxtSobrenome().setText( sobrenome );
	}

	public String getValorTxtLogin() {
		return view.getTxtLogin().getText();
	}

	public void setValorTxtLogin( String login ) {
		view.getTxtLogin().setText( login );
	}

	public String getValorTxtEmail() {
		return view.getTxtEmail().getText();
	}

	public void setValorTxtEmail( String email ) {
		view.getTxtEmail().setText( email );
	}

	public void showPopUpErro( Exception ex ) {
		var tag = UUID.randomUUID().toString();
		var mensagem = "Erro inesperado, favor contatar o administrador do sistema com o código '" + tag + "'";
		try {
			LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "showPopUpErro da FormularioUsuarioPresenter[" + tag + "]", usuarioLogadoId );
		} catch ( Exception ex1 ) {
			ex1.printStackTrace();
		}
		log.error( mensagem );
		popup.showPopupOk( "Erro", mensagem, "Erro" );
		ex.printStackTrace();
	}

	public void showPopUpOk( ControllerRetorno retorno ) {
		popup.showPopupOk( retorno );
	}

	public int getUsuarioLogadoId() {
		return this.usuarioLogadoId;
	}

}
