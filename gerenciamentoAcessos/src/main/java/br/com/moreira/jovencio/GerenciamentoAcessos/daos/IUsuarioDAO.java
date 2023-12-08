
package br.com.moreira.jovencio.GerenciamentoAcessos.daos;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioGridDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import java.util.List;

/**
 *
 * @author marlan
 */
public interface IUsuarioDAO {

	public void createTable() throws Exception;

	public Usuario insert( Usuario entity ) throws Exception;

	public Usuario get( int id ) throws Exception;

	public void update( int id, Usuario entity ) throws Exception;

	public void delete( int id ) throws Exception;

	public Usuario findByLoginAndSenha( String login, String senha ) throws Exception;

	public boolean existeUsuarioComLogin( String login ) throws Exception;

	public int getCountUsuarios() throws Exception;

	public List<UsuarioGridDTO> search( String nome, Boolean possuiNotificacoes ) throws Exception;

}
