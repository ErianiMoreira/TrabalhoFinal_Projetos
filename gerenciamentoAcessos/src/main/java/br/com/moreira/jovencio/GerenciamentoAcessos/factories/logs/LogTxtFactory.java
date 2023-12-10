
package br.com.moreira.jovencio.GerenciamentoAcessos.factories.logs;

import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.ILogService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.impl.TxtLogService;

/**
 *
 * @author marlan
 */
public class LogTxtFactory extends LogFactory {

	@Override
	public String getLog() {
		return "TXT";
	}

	@Override
	public ILogService getLogService() {
		return new TxtLogService();
	}

}
