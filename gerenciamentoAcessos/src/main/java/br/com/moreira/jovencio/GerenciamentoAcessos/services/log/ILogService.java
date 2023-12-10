
package br.com.moreira.jovencio.GerenciamentoAcessos.services.log;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;

/**
 *
 * @author marlan
 */
public interface ILogService {

	public void registrarFalha( String falha, String operacao, Usuario usuario );

	public void registrarFalha( String falha, String operacao, int usuarioId );

}
