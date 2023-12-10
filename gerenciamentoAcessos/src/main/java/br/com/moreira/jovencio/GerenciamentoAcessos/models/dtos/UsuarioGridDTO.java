
package br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author marlan/eriani
 */
public class UsuarioGridDTO {

	private int id;
	private String nomeCompleto;
	private String dataCadastroFormatada;
	private LocalDateTime dataCadastro;
	private int notificacoesEnviadas;
	private int notificacoesLidas;
	private LocalDateTime dataAutorizado;

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto( String nomeCompleto ) {
		this.nomeCompleto = nomeCompleto;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro( LocalDateTime dataCadastro ) {
		this.dataCadastro = dataCadastro;
		this.dataCadastroFormatada = this.dataCadastro.format( DateTimeFormatter.ofPattern( "dd/MM/yyyy H:mm:ss" ) );
	}

	public int getNotificacoesEnviadas() {
		return notificacoesEnviadas;
	}

	public void setNotificacoesEnviadas( int notificacoesEnviadas ) {
		this.notificacoesEnviadas = notificacoesEnviadas;
	}

	public int getNotificacoesLidas() {
		return notificacoesLidas;
	}

	public void setNotificacoesLidas( int notificacoesLidas ) {
		this.notificacoesLidas = notificacoesLidas;
	}

	public String getDataCadastroFormatada() {
		return dataCadastroFormatada;
	}

	public LocalDateTime getDataAutorizado() {
		return dataAutorizado;
	}

	public void setDataAutorizado( LocalDateTime dataAutorizado ) {
		this.dataAutorizado = dataAutorizado;
	}

	public String getEstaAutorizado() {
		return dataAutorizado == null ? "Não" : "Sim, em " + this.dataCadastro.format( DateTimeFormatter.ofPattern( "dd/MM/yyyy ' as ' H:mm:ss" ) );
	}

}
