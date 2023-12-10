
package br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.estados;

import br.com.moreira.jovencio.GerenciamentoAcessos.presenters.formulario.usuario.FormularioUsuarioPresenter;

/**
 *
 * @author marlan/eriani
 */
public abstract class EstadoFormularioUsuarioPresenter {

	protected final FormularioUsuarioPresenter presenter;

	protected EstadoFormularioUsuarioPresenter( FormularioUsuarioPresenter presenter ) {
		this.presenter = presenter;
	}

	public void autorizarUsuario() {
		throw new UnsupportedOperationException( "Função não permitida neste estado" );
	}

	public void resetarSenhaUsuario() {
		throw new UnsupportedOperationException( "Função não permitida neste estado" );
	}

	public void salvarUsuario() {
		throw new UnsupportedOperationException( "Função não permitida neste estado" );
	}

	public void excluirUsuario() {
		throw new UnsupportedOperationException( "Função não permitida neste estado" );
	}

	public void editarUsuario() {
		throw new UnsupportedOperationException( "Função não permitida neste estado" );
	}

	public void incluirUsuario() {
		throw new UnsupportedOperationException( "Função não permitida neste estado" );
	}

	public void cancelar() {
		throw new UnsupportedOperationException( "Função não permitida neste estado" );
	}

}
