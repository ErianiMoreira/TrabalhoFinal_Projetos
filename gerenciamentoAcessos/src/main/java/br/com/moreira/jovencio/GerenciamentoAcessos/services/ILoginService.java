
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;

/**
 *
 * @author marlan/eriani
 */
public interface ILoginService {

	public ControllerRetorno logar( String login, String senha );

}
