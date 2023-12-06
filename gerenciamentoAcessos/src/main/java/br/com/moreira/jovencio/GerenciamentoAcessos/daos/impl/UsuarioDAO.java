
package br.com.moreira.jovencio.GerenciamentoAcessos.daos.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;

/**
 *
 * @author marlan
 */
public class UsuarioDAO implements IUsuarioDAO {

	@Override
	public Usuario findByLoginAndSenha( String login, String senha ) {
		var usuario = new Usuario();
		usuario.setId( 1 );
		usuario.setNome( "Marlan" );
		usuario.setSobrenome( "Tonoli Jovencio" );
		usuario.setEmail( "marlan.jovencio@edu.ufes.br" );
		usuario.setLogin( "marlan.jovencio" );
		usuario.setSenha( "senha.123" );
		return usuario;
	}

	public boolean existeUsuarioComLogin( String login ) {
		return false;
	}

	@Override
	public Usuario insert( Usuario entity ) {
		return findByLoginAndSenha( "", "" );
	}

	@Override
	public Usuario get( int id ) {
		throw new UnsupportedOperationException( "Not supported yet." ); // Generated from
																			// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public void update( int id, Usuario entity ) {
		throw new UnsupportedOperationException( "Not supported yet." ); // Generated from
																			// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public void delete( int id ) {
		throw new UnsupportedOperationException( "Not supported yet." ); // Generated from
																			// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}
}
