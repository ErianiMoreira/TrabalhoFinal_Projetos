
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.estados;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs.LogFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.FormularioUsuarioPresenter;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ICadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.CadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.ValidarDadosUsuarioAtualizacao;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.IUsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.impl.UsuarioRepository;

/**
 *
 * @author marlan/eriani
 */
public class EstadoEdicaoFormularioUsuarioPresenter extends EstadoFormularioUsuarioPresenter {

	private final ICadastrarUsuarioService cadastrarUsuarioService;

	private final IUsuarioRepository usuarioRepository;

	public EstadoEdicaoFormularioUsuarioPresenter( FormularioUsuarioPresenter presenter ) throws Exception {
		super( presenter );
		cadastrarUsuarioService = new CadastrarUsuarioService(presenter.getUsuarioLogadoId());
		usuarioRepository = new UsuarioRepository();
		configurarDados();
                presenter.desabilitarCampos( "login" );
		presenter.habilitarCampos( "nome", "sobrenome", "email" );
                
		if( presenter.getUsuarioId() == presenter.getUsuarioLogadoId() ) {
                        presenter.desabilitarBotoes( "todos" );
			presenter.habilitarBotoes( "resetar senha", "salvar" );
		}else{
                    presenter.habilitarBotoes( "todos" );
                    presenter.desabilitarBotoes( "editar" );
                }

	}

	@Override
	public void autorizarUsuario() {
		if( presenter.getUsuarioId() == presenter.getUsuarioLogadoId() ) {
			var retorno = new ControllerRetorno();
			retorno.setMensagem( "O usuário não pode ser autorizado por ele mesmo" );
			presenter.showPopUpOk( retorno );
			return;
		}
		var retorno = cadastrarUsuarioService.autorizar( presenter.getUsuarioId() );
		if( retorno.isSuccess() ) {
			presenter.showPopUpOk( retorno );
			presenter.close();
		} else {
			presenter.showPopUpOk( retorno );
		}
	}

	@Override
	public void resetarSenhaUsuario() {
		var retorno = cadastrarUsuarioService.resetarSenha( presenter.getUsuarioId() );
		if( retorno.isSuccess() ) {
			presenter.showPopUpOk( retorno );
			presenter.close();
		} else {
			presenter.showPopUpOk( retorno );
		}
	}

	@Override
	public void salvarUsuario() {
		try {
			ControllerRetorno retorno = new ControllerRetorno();
			cadastrarUsuarioService.setValidador( new ValidarDadosUsuarioAtualizacao() );
			retorno = cadastrarUsuarioService.atualizar( gerarUsuarioAtualizacao() );
			if( retorno.isSuccess() ) {
				presenter.setUsuarioId( retorno.getEntidadeId() );
				presenter.setEstado( new EstadoVisualizacaoFormularioUsuarioPresenter( presenter ) );
			} else {
				presenter.showPopUpOk( retorno );
			}

		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "salvarUsuario da EstadoEdicaoFormularioUsuarioPresenter", presenter.getUsuarioLogadoId() );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}

	@Override
	public void excluirUsuario() {
		if( presenter.getUsuarioId() == presenter.getUsuarioLogadoId() ) {
			var retorno = new ControllerRetorno();
			retorno.setMensagem( "O usuário não pode ser excluido por ele mesmo" );
			presenter.showPopUpOk( retorno );
			return;
		}
		var retorno = cadastrarUsuarioService.excluir( presenter.getUsuarioId() );
		if( retorno.isSuccess() ) {
			presenter.showPopUpOk( retorno );
			presenter.close();
		} else {
			presenter.showPopUpOk( retorno );
		}
	}

	private Usuario gerarUsuarioAtualizacao() {
		Usuario dto = new Usuario( presenter.getUsuarioId() );
		dto.setNome( presenter.getValorTxtNome() );
		dto.setSobrenome( presenter.getValorTxtSobrenome() );
		dto.setEmail( presenter.getValorTxtEmail() );
		return dto;
	}

	private void configurarDados() throws Exception {
		var usuario = usuarioRepository.get( presenter.getUsuarioId() );
		presenter.setValorTxtNome( usuario.getNome() );
		presenter.setValorTxtSobrenome( usuario.getSobrenome() );
		presenter.setValorTxtEmail( usuario.getEmail() );
		presenter.setValorTxtLogin( usuario.getLogin() );
	}

}
