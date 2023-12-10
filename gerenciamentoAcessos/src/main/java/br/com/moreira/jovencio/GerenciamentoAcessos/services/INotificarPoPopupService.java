
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;

/**
 *
 * @author marlan/eriani
 */
public interface INotificarPoPopupService {

	public int showPopupOk( String titulo, String mensagem, String tipoMensagem );

	public boolean showPopupConfirm( String mensagem, String titulo );

	public void showPopupOk( ControllerRetorno retorno );

}
