
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Notificacao;
import java.util.List;

/**
 *
 * @author marlan
 */
public interface INotificarUsuarioService {

	public ControllerRetorno notificar( int usuarioOrigemId, int usuarioDestinoId, String mensagem );

	public ControllerRetorno notificar( int usuarioDestinoId, String mensagem );

	public List<Notificacao> findAllByParaUsuarioId( int id ) throws Exception;

	public ControllerRetorno marcar( int id, boolean lida );

	public ControllerRetorno validar( int usuarioDestinoId, String mensagem ) throws Exception;

	public ControllerRetorno validar( int usuarioOrigemId, int usuarioDestinoId, String mensagem ) throws Exception;
}
