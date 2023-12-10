
package br.com.moreira.jovencio.GerenciamentoAcessos.views.actionEvent.tabelaManterUsuarios;

/**
 *
 * @author marlan/eriani
 */
public interface IAcoesTabelaManterUsuario {

	public void autorizar( int row );

	public void editar( int row );

	public void notificar( int row );

	public void visualizar( int row );

}
