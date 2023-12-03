
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioCadastroDTO;

/**
 *
 * @author marlan
 */
public interface IUsuarioCadastroService {

	public ControllerRetorno cadastrar( UsuarioCadastroDTO usuario );

	public ControllerRetorno validar( UsuarioCadastroDTO usuario );
}
