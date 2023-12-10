
package br.com.moreira.jovencio.GerenciamentoAcessos.services.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.ControllerRetorno;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.INotificarPoPopupService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author marlan/eriani
 */
public class NotificarPoPopupService implements INotificarPoPopupService {

	private final String html = "<html><body style='width: %1spx'>%1s";
	private final Component parent;
	private int tamanho = 300;

	public NotificarPoPopupService( Component parent ) {
		this.parent = parent;
	}

	public NotificarPoPopupService( Component parent, int tamanho ) {
		this.parent = parent;
		this.tamanho = tamanho;
	}

	@Override
	public int showPopupOk( String titulo, String mensagem, String tipoMensagem ) {
		return JOptionPane.showConfirmDialog( parent, String.format( html, tamanho, mensagem ), titulo, JOptionPane.DEFAULT_OPTION, converterParaJOptionMessageType( tipoMensagem ) );
	}

	@Override
	public boolean showPopupConfirm( String mensagem, String titulo ) {
		String[] botoes = { "Cancelar", "Sim" };
		int retorno = JOptionPane.showOptionDialog( parent, String.format( html, tamanho, mensagem ), titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoes, botoes[1] );
		return retorno == 1;
	}

	private int converterParaJOptionMessageType( String tipoMensagem ) {
		if( ValidarCampo.isNullVazioOrEspacos( tipoMensagem ) ) {
			return JOptionPane.PLAIN_MESSAGE;
		}
		tipoMensagem = tipoMensagem.toLowerCase();

		if( tipoMensagem.contains( "erro" ) ) {
			return JOptionPane.ERROR_MESSAGE;
		}

		if( tipoMensagem.contains( "informativa" ) ) {
			return JOptionPane.INFORMATION_MESSAGE;
		}

		if( tipoMensagem.contains( "pergunta" ) ) {
			return JOptionPane.QUESTION_MESSAGE;
		}

		if( tipoMensagem.contains( "alerta" ) ) {
			return JOptionPane.WARNING_MESSAGE;
		}

		return JOptionPane.PLAIN_MESSAGE;
	}

	@Override
	public void showPopupOk( ControllerRetorno retorno ) {
		if( retorno.isSuccess() ) {
			showPopupOk( "Sucesso", retorno.getMensagem() != null ? retorno.getMensagem() : "Função realizada com sucesso", "informativa" );
		} else if( retorno.isWarning() ) {
			showPopupOk( "Alerta", retorno.getMensagem() != null ? retorno.getMensagem() : "Função não realizada ou realizada parcialmente", "alerta" );
		} else {
			showPopupOk( "Erro", retorno.getMensagem() != null ? retorno.getMensagem() : "Função não realizada", "erro" );
		}
	}

}
