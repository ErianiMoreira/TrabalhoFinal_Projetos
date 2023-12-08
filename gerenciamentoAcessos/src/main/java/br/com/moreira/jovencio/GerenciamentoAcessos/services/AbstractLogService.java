
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marlan
 */
public abstract class AbstractLogService {

	protected final Logger log = LoggerFactory.getLogger( AbstractLogService.class );

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
