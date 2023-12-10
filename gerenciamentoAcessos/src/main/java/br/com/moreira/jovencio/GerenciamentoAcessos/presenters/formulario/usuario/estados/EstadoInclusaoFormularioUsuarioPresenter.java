
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.estados;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs.LogFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.FormularioUsuarioPresenter;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ICadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.CadastrarUsuarioService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.impl.ValidarDadosUsuarioCadastroInterno;

/**
 *
 * @author marlan
 */
public class EstadoInclusaoFormularioUsuarioPresenter extends EstadoFormularioUsuarioPresenter {

	private ICadastrarUsuarioService cadastrarUsuarioService;

	public EstadoInclusaoFormularioUsuarioPresenter( FormularioUsuarioPresenter presenter ) throws Exception {
		super( presenter );
		cadastrarUsuarioService = new CadastrarUsuarioService();
		presenter.removerUsuario();
		presenter.habilitarCampos( "todos" );
		presenter.habilitarBotoes( "salvar" );
	}

	@Override
	public void salvarUsuario() {
		try {
			ControllerRetorno retorno = new ControllerRetorno();
			cadastrarUsuarioService.setValidador( new ValidarDadosUsuarioCadastroInterno() );
			retorno = cadastrarUsuarioService.cadastrar( gerarUsuarioCadastro() );
			if( retorno.isSuccess() ) {
				presenter.setUsuarioId( retorno.getEntidadeId() );
				presenter.setEstado( new EstadoVisualizacaoFormularioUsuarioPresenter( presenter ) );
			} else {
				presenter.showPopUpOk( retorno );
			}

		} catch ( Exception ex ) {
			try {
				LogFactory.getLOGFactory().getLogService().registrarFalha( ex.getMessage(), "salvarUsuario da EstadoInclusaoFormularioUsuarioPresenter", presenter.getUsuarioLogadoId() );
			} catch ( Exception ex1 ) {
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}

	private Usuario gerarUsuarioCadastro() {
		Usuario dto = new Usuario();
		dto.setNome( presenter.getValorTxtNome() );
		dto.setSobrenome( presenter.getValorTxtSobrenome() );
		dto.setEmail( presenter.getValorTxtEmail() );
		dto.setLogin( presenter.getValorTxtLogin() );
		return dto;
	}
}
