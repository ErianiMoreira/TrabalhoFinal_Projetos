
package br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioGridDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import java.util.List;

/**
 *
 * @author marlan
 */
public interface IUsuarioRepository {

	public List<UsuarioGridDTO> search( String nome, Boolean possuiNotificacoes );

	public Usuario get( int id ) throws Exception;

	public boolean isAutorizado( int id ) throws Exception;

}
