
package br.com.moreira.jovencio.GerenciamentoAcessos.observers.usuario;

/**
 *
 * @author marlan/eriani
 */
public interface IUsuarioDAOObservavel {

	public void adicionarObservador( IUsuarioDAOObervador observador );

	public void removerObservador( IUsuarioDAOObervador observador );

	public void notificarObservadores();

}
