
package br.com.moreira.jovencio.GerenciamentoAcessos.services.log;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;

/**
 *
 * @author marlan/eriani
 */
public interface ILogService {

	public void registrarFalha( String falha, String operacao, Usuario usuario );
        
        public void registrarFalha( String falha, String operacao, int usuarioId );
        
        public void registrarAcao ( String acao, String operacao, int usuarioId );
        
        public void registrarAcao(String acao, String operacao, Usuario usuario);

}
