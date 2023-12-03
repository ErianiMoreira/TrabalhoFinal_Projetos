
package br.com.moreira.jovencio.GerenciamentoAcessos.daos;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;

/**
 *
 * @author marlan
 */
public interface IUsuarioDAO {

	public Usuario insert( Usuario entity );

	public Usuario get( int id );

	public void update( int id, Usuario entity );

	public void delete( int id );

	public Usuario findByLoginAndSenha( String login, String senha );

	public boolean existeUsuarioComLogin( String login );

}
