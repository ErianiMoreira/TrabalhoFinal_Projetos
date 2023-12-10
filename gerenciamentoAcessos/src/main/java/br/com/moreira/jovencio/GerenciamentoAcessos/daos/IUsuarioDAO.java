
package br.com.moreira.jovencio.GerenciamentoAcessos.daos;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioGridDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.usuario.IUsuarioDAOObservavel;
import java.util.List;

/**
 *
 * @author marlan/eriani
 */
public interface IUsuarioDAO extends IUsuarioDAOObservavel {

	public void createTable() throws Exception;

	public Usuario insert( Usuario entity ) throws Exception;

	public Usuario get( int id ) throws Exception;

	public void update( int id, Usuario entity ) throws Exception;

	public void delete( int id ) throws Exception;

	public Usuario findByLoginAndSenha( String login, String senha ) throws Exception;

	public Usuario findUsuarioComLogin( String login ) throws Exception;

	public int getCountUsuarios() throws Exception;

	public List<UsuarioGridDTO> search( String nome, Boolean possuiNotificacoes ) throws Exception;

	public void autorizar( int id ) throws Exception;

	public void alterarSenha( int id, String senha ) throws Exception;

	public void resetarSenha( int id ) throws Exception;

}
