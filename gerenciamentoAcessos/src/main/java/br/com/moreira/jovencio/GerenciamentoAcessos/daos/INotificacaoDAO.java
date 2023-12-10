
package br.com.moreira.jovencio.GerenciamentoAcessos.daos;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Notificacao;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.notificacao.INotificacaoDAOObservavel;
import java.util.List;

/**
 *
 * @author marlan
 */
public interface INotificacaoDAO extends INotificacaoDAOObservavel {

	public void createTable() throws Exception;

	public Notificacao insert( Notificacao entity ) throws Exception;

	public List<Notificacao> findAllByParaUsuarioId( int id ) throws Exception;

	public void marcar( int id, boolean lida ) throws Exception;

	public void deletarNotificacoesByUsuario( int usuarioId ) throws Exception;

}
