
package br.com.moreira.jovencio.GerenciamentoAcessos.daos.conexoes;

import java.sql.Connection;

/**
 *
 * @author marlan/eriani
 */
public interface IConexaoBancoDados {

	public Connection openConnection() throws Exception;

	public void closeConnection( Connection connection ) throws Exception;

}
