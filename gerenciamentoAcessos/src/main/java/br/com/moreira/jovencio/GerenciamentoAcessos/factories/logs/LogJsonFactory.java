
package br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs;

import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.ILogService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.impl.JsonLogService;

/**
 *
 * @author marlan
 */
public class LogJsonFactory extends LogFactory {

	@Override
	public String getLog() {
		return "JSON";
	}

	@Override
	public ILogService getLogService() {
		return new JsonLogService();
	}

}
