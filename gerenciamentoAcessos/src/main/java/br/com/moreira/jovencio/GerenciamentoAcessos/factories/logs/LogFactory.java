
package br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs;

import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.ILogService;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;

/**
 *
 * @author marlan/eriani
 */
public abstract class LogFactory {

	private static final List<LogFactory> factories = List.of( //
			new LogCsvFactory(), //
			new LogTxtFactory(), //
			new LogJsonFactory() //
	);

	public abstract String getLog();

	public static LogFactory getLOGFactory() throws Exception {
		Dotenv dotenv = Dotenv.configure().load();
		var optionalFactory = factories.stream().filter( f -> f.getLog().equalsIgnoreCase( dotenv.get( "LOG", "CSV" ) ) ).findFirst();
		if( optionalFactory.isEmpty() ) {
			throw new Exception( "ENV 'LOG' não fornecida ou LOGFactory não implementada para o tipo de  informado" );
		}
		return optionalFactory.get();
	}

	public abstract ILogService getLogService();
}
