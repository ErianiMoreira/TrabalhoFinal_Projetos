
package br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.INotificacaoDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;

/**
 *
 * @author marlan
 */
public abstract class DAOFactory {

	private static final List<DAOFactory> factories = List.of( //
			new SQLiteDAOFactory() //
	);

	public abstract String getDatabase();

	public abstract void updateDatabase() throws Exception;

	public static DAOFactory getDAOFactory() throws Exception {
		Dotenv dotenv = Dotenv.configure().load();
		var optionalFactory = factories.stream().filter( f -> f.getDatabase().equals( dotenv.get( "DATABASE" ) ) ).findFirst();
		if( optionalFactory.isEmpty() ) {
			throw new Exception( "ENV 'DATABASE' não fornecida ou DAOFactory não implementada para o banco informado" );
		}
		var factory = optionalFactory.get();
		factory.updateDatabase();
		return optionalFactory.get();
	}

	public abstract IUsuarioDAO getUsuarioDAO();

	public abstract INotificacaoDAO getNotificacaoDAO();

}