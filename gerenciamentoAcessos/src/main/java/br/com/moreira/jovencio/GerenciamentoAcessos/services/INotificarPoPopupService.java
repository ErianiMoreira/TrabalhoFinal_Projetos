
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

/**
 *
 * @author marlan
 */
public interface INotificarPoPopupService {

	public int showPopupOk( String titulo, String mensagem, String tipoMensagem );

	public boolean showPopupConfirm( String mensagem, String titulo );

}
