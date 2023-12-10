
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;

/**
 *
 * @author marlan/eriani
 */
public interface ICadastrarUsuarioService {

	public ControllerRetorno cadastrar( Usuario usuario );

	public ControllerRetorno atualizar( Usuario usuario );

	public ControllerRetorno validar( Usuario usuario );

	public void setValidador( AbstractValidarDadosUsuario validador );

	public ControllerRetorno validarSenha( String senha, String confirmacao );

	public ControllerRetorno autorizar( int usuarioId );

	public ControllerRetorno alterarSenha( int usuarioId, String senha, String confirmacao );

	public ControllerRetorno resetarSenha( int usuarioId );

	public ControllerRetorno excluir( int usuarioId );

}
