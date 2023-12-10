
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs.LogFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioGridDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.notificacao.INotificacaoDAOObservador;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.notificacao.INotificacaoDAOObservavel;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.usuario.IUsuarioDAOObervador;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.usuario.IUsuarioDAOObservavel;
import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.FormularioUsuarioPresenter;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ICadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.CadastrarUsuarioService;
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
public class ManterUsuariosPresenter implements IUsuarioDAOObervador, INotificacaoDAOObservador {

	private int usuarioLogadoId;

	private final List<NotificarUsuarioPresenter> notificarUsuarioPresenters;

	private final ManterUsuariosView view;

	private FormularioUsuarioPresenter formularioUsuarioPresenter;

	private final INotificarPoPopupService notificarPopUp;

	private final IUsuarioRepository usuarioRepository;

	private final ICadastrarUsuarioService cadastrarUsuarioService;

	public ManterUsuariosPresenter( int usuarioLogadoId ) throws Exception {
		this.notificarUsuarioPresenters = new ArrayList<>();
		this.usuarioLogadoId = usuarioLogadoId;
		view = new ManterUsuariosView();
		notificarPopUp = new NotificarPoPopupService( view );
		usuarioRepository = new UsuarioRepository();
		cadastrarUsuarioService = new CadastrarUsuarioService();
		DAOFactory.getDAOFactory().getNotificacaoDAO().adicionarObservador( this );
		DAOFactory.getDAOFactory().getUsuarioDAO().adicionarObservador( this );
		configurarView();
	}

	public void show( int usuarioLogadoId ) {
		this.usuarioLogadoId = usuarioLogadoId;
		gerarLinhasTabela( null, null );
		view.setVisible( true );
	}

	private void gerarLinhasTabela( String nome, Boolean possuiNotificacoes ) {
		DefaultTableModel model = ( DefaultTableModel ) view.getTblListaUsuarios().getModel();
		for( int i = 0; i < model.getRowCount(); i++ ) {
			model.removeRow( i );
		}
		model.setNumRows( 0 );
		view.getTblListaUsuarios().getColumnModel().getColumn( view.getTblListaUsuarios().getColumnModel().getColumnCount() - 1 ).setCellRenderer( null );
		view.getTblListaUsuarios().getColumnModel().getColumn( view.getTblListaUsuarios().getColumnModel().getColumnCount() - 1 ).setCellEditor( null );
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
		DefaultTableModel model = ( DefaultTableModel ) view.getTblListaUsuarios().getModel();
		model.setNumRows( 0 );
	}

	private void filtrar() {
		String nome = view.getTxtNome().getText();
		Boolean possuiNotificacaoNaoLida = converterPossuiNotificacaoNaoLida( String.valueOf( view.getCboxPossuiNotificacaoNaoLida().getSelectedItem() ) );
		gerarLinhasTabela( nome, possuiNotificacaoNaoLida );
	}

	private void novo() {
		try {
			if( formularioUsuarioPresenter != null ) {
				formularioUsuarioPresenter.close();
			}
			formularioUsuarioPresenter = new FormularioUsuarioPresenter( usuarioLogadoId, null, "Inclusao" );
			formularioUsuarioPresenter.show();
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "novo da ManterUsuariosPresenter", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			tratarErro( ex );
		}
	}

	private void autorizarByLinha( int linha ) {
		var id = findIdByLinha( linha );
		if( id == 0 ) {
			notificarPopUp.showPopupOk( "Erro", "Não foi possivel encontrar este usuário", "Erro" );
			return;
		}
		if( usuarioLogadoId == id ) {
			notificarPopUp.showPopupOk( "Autorizar usuário", "Usuários só podem ser autorizados por outros adiminstradores", "Erro" );
			return;
		}
		boolean confirmou = notificarPopUp.showPopupConfirm( "Ao confirmar este usuário será autorizado a usar as funcionálidades do sistema.", "Certeza que deseja autorizar este usuário?" );
		if( confirmou ) {
			var retorno = cadastrarUsuarioService.autorizar( id );
			if( !retorno.isSuccess() ) {
				notificarPopUp.showPopupOk( retorno.isError() ? "Erro" : "Alerta", retorno.getMensagem(), retorno.isError() ? "Erro" : "Alerta" );
			}
		}
	}

	private void editarByLinha( int linha ) {
		try {
			var id = findIdByLinha( linha );
			if( id == 0 ) {
				notificarPopUp.showPopupOk( "Erro", "Não foi possivel encontrar este usuário", "Erro" );
				return;
			}
			if( formularioUsuarioPresenter != null ) {
				formularioUsuarioPresenter.close();
			}
			formularioUsuarioPresenter = new FormularioUsuarioPresenter( usuarioLogadoId, id, "Edicao" );
			formularioUsuarioPresenter.show();
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "editarByLinha da ManterUsuariosPresenter", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			tratarErro( ex );
		}
	}

	private void notificarByLinha( int linha ) {
		var id = findIdByLinha( linha );
		if( id == 0 ) {
			notificarPopUp.showPopupOk( "Erro", "Não foi possivel encontrar este usuário", "Erro" );
			return;
		}
		if( usuarioLogadoId == id ) {
			notificarPopUp.showPopupOk( "Notificar usuário", "Usuários só podem ser notificados por outros usuários", "Erro" );
			return;
		}
		try {
			int paraUsuarioId = findIdByLinha( linha );
			var presenter = notificarUsuarioPresenters.stream().filter( p -> p.isParaUsuarioId( paraUsuarioId ) ).findFirst();
			if( presenter.isEmpty() ) {
				notificarUsuarioPresenters.add( new NotificarUsuarioPresenter( this, usuarioLogadoId, paraUsuarioId ) );
			} else {
				presenter.get().expandir();
			}
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "notificarByLinha da ManterUsuariosPresenter", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			tratarErro( ex );
		}
	}

	private void visualizarByLinha( int linha ) {
		try {
			var id = findIdByLinha( linha );
			if( id == 0 ) {
				notificarPopUp.showPopupOk( "Erro", "Não foi possivel encontrar este usuário", "Erro" );
				return;
			}
			if( formularioUsuarioPresenter != null ) {
				formularioUsuarioPresenter.close();
			}
			formularioUsuarioPresenter = new FormularioUsuarioPresenter( usuarioLogadoId, id, "Visualizacao" );
			formularioUsuarioPresenter.show();
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "visualizarByLinha da ManterUsuariosPresenter", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
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
		try {
			return ( int ) view.getTblListaUsuarios().getModel().getValueAt( linha, map.get( "Id" ) );
		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "findIdByLinha da ManterUsuariosPresenter", usuarioLogadoId );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
		return 0;
	}

	private void tratarErro( Exception ex ) {
		ex.printStackTrace();
		notificarPopUp.showPopupOk( "Erro", "Erro inesperado ao tentar executar a ação, favor tentar novamente.", "Erro" );
	}

	void removeNotificarPara( int paraUsuarioId ) {
		var itensRemover = notificarUsuarioPresenters.stream().filter( p -> p.isParaUsuarioId( paraUsuarioId ) ).toList();
		notificarUsuarioPresenters.removeAll( itensRemover );
	}

	private void configurarView() {
		view.setVisible( false );
		view.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
		gerarLinhasTabela( null, null );
		view.getBtnLimpar().addActionListener( a -> limparFiltros() );
		view.getBtnFiltrar().addActionListener( a -> filtrar() );
		view.getBtnNovo().addActionListener( a -> novo() );
		PrincipalPresenter.getInstancia().add( this.getClass().getSimpleName(), view );
	}

	@Override
	public void atualizar( IUsuarioDAOObservavel observavel ) {
		this.filtrar();
	}

	@Override
	public void atualizar( INotificacaoDAOObservavel observavel ) {
		this.filtrar();
	}

}
