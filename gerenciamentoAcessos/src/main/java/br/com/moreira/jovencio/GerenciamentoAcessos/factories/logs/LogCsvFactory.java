
package br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs;

import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.ILogService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.impl.CsvLogService;

/**
 *
 * @author marlan/eriani
 */
public class LogCsvFactory extends LogFactory {

	@Override
	public String getLog() {
		return "CSV";
	}

	@Override
	public ILogService getLogService() {
		return new CsvLogService();
	}

}
