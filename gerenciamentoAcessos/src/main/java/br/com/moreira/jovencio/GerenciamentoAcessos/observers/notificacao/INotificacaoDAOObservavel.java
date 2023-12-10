
package br.com.moreira.jovencio.GerenciamentoAcessos.observers.notificacao;

/**
 *
 * @author marlan/eriani
 */
public interface INotificacaoDAOObservavel {

	public void adicionarObservador( INotificacaoDAOObservador observador );

	public void removerObservador( INotificacaoDAOObservador observador );

	public void notificarObservadores();

}
