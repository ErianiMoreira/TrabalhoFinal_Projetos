
package br.com.moreira.jovencio.GerenciamentoAcessos.daos.conexoes;

import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marlan/eriani
 */
public class SQLiteBancoDadosConexao implements IConexaoBancoDados {

	private final Logger log = LoggerFactory.getLogger( SQLiteBancoDadosConexao.class );

	protected final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern( "yyyy-MM-dd H:mm:ss" );

	@Override
	public Connection openConnection() throws SQLException {
		Dotenv env = Dotenv.load();
		String url = env.get( "jdbc_url" );
		if( url == null || url.isEmpty() || url.isBlank() ) {
			Path currentRelativePath = Paths.get( "" );
			String s = currentRelativePath.toAbsolutePath().toString();
			url = "jdbc:sqlite:" + s + "/src/main/resource/db/gerenciamento-acessos-db.db";

		}

		log.info( "Driver Connected to '" + url + "'" );
		return DriverManager.getConnection( url );
	}

	@Override
	public void closeConnection( Connection connection ) throws SQLException {
		connection.close();
	}

	protected int insert( String sql ) throws SQLException {
		log.info( sql );
		var con = openConnection();
		var statement = con.createStatement();
		statement.execute( sql );
		var result = statement.executeQuery( "SELECT LAST_INSERT_ROWID()" );
		var id = result.getInt( 1 );
		closeConnection( con );
		return id;
	}
}
