
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import java.util.UUID;
import org.slf4j.Logger;

/**
 *
 * @author marlan
 */
public abstract class AbstractLogService {

	protected Logger log;

	public AbstractLogService( Logger log ) {
		this.log = log;
	}

	protected ControllerRetorno tratarErro( Exception e ) {
		var mensagem = "Erro inesperado, favor contatar o administrador do sistema com o código '" + UUID.randomUUID().toString() + "'";
		log.error( mensagem );
		var retorno = new ControllerRetorno();
		retorno.setCodigo( 505 );
		retorno.setMensagem( mensagem );
		e.printStackTrace();
		return retorno;
	}
}
