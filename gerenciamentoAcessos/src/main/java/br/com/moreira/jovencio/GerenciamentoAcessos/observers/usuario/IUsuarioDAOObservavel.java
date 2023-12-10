
package br.com.moreira.jovencio.GerenciamentoAcessos.observers.usuario;

/**
 *
 * @author marlan
 */
public interface IUsuarioDAOObservavel {

	public void adicionarObservador( IUsuarioDAOObervador observador );

	public void removerObservador( IUsuarioDAOObervador observador );

	public void notificarObservadores();

}
