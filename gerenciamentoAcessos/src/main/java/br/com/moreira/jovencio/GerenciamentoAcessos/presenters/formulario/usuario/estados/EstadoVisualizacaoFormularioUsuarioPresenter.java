
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.estados;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.FormularioUsuarioPresenter;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ICadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.CadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.IUsuarioRepository;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.impl.UsuarioRepository;

/**
 *
 * @author marlan
 */
public class EstadoVisualizacaoFormularioUsuarioPresenter extends EstadoFormularioUsuarioPresenter {

	private final ICadastrarUsuarioService cadastrarUsuarioService;

	private final IUsuarioRepository usuarioRepository;

	public EstadoVisualizacaoFormularioUsuarioPresenter( FormularioUsuarioPresenter presenter ) throws Exception {
		super( presenter );
		cadastrarUsuarioService = new CadastrarUsuarioService();
		usuarioRepository = new UsuarioRepository();
		configurarDados();
		presenter.desabilitarCampos( "todos" );
		if( presenter.getUsuarioId() == presenter.getUsuarioLogadoId() ) {
			presenter.desabilitarBotoes( "todos" );
			presenter.habilitarBotoes( "resetar senha", "editar" );
		}
	}

	@Override
	public void incluirUsuario() {
		try {
			presenter.setEstado( new EstadoInclusaoFormularioUsuarioPresenter( presenter ) );
		} catch ( Exception ex ) {
			presenter.showPopUpErro( ex );
		}
	}

	@Override
	public void editarUsuario() {
		try {
			presenter.setEstado( new EstadoEdicaoFormularioUsuarioPresenter( presenter ) );
		} catch ( Exception ex ) {
			presenter.showPopUpErro( ex );
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
	public void autorizarUsuario() {
		if( presenter.getUsuarioId() == presenter.getUsuarioLogadoId() ) {
			var retorno = new ControllerRetorno();
			retorno.setMensagem( "O usuário não pode ser atorizado por ele mesmo" );
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

	private void configurarDados() throws Exception {
		var usuario = usuarioRepository.get( presenter.getUsuarioId() );
		presenter.setValorTxtNome( usuario.getNome() );
		presenter.setValorTxtSobrenome( usuario.getSobrenome() );
		presenter.setValorTxtEmail( usuario.getEmail() );
		presenter.setValorTxtLogin( usuario.getLogin() );
	}
}
