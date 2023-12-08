
package br.com.moreira.jovencio.GerenciamentoAcessos.models.entities;

import java.time.LocalDateTime;

/**
 *
 * @author marlan
 */
public class Notificacao {

	private Integer id;

	private String mensagem;

	private boolean lida;

	private final LocalDateTime dataEnvio;

	private Usuario deUsuario;

	private Usuario paraUsuario;

	public Notificacao() {
		this.dataEnvio = LocalDateTime.now();
	}

	public Notificacao( int id, LocalDateTime dataEnvio ) {
		this.id = id;
		this.dataEnvio = dataEnvio;
	}

	public Notificacao( int id ) {
		this.id = id;
		this.dataEnvio = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem( String mensagem ) {
		this.mensagem = mensagem;
	}

	public boolean isLida() {
		return lida;
	}

	public void setLida( boolean lida ) {
		this.lida = lida;
	}

	public LocalDateTime getDataEnvio() {
		return dataEnvio;
	}

	public Usuario getDeUsuario() {
		return deUsuario;
	}

	public Integer getDeUsuarioId() throws Exception {
		return deUsuario == null ? null : deUsuario.getId();
	}

	public void setDeUsuario( Usuario deUsuario ) {
		this.deUsuario = deUsuario;
	}

	public void setDeUsuario( Integer deUsuarioId ) {
		this.deUsuario = deUsuarioId == null || deUsuarioId == 0 ? null : new Usuario( deUsuarioId );
	}

	public Usuario getParaUsuario() {
		return paraUsuario;
	}

	public int getParaUsuarioId() throws Exception {
		if( paraUsuario == null ) {
			throw new Exception( "atributo vazio" );
		}
		return paraUsuario.getId();
	}

	public void setParaUsuario( Usuario paraUsuario ) {
		this.paraUsuario = paraUsuario;
	}

	public void setParaUsuario( int paraUsuarioId ) {
		this.paraUsuario = new Usuario( paraUsuarioId );
	}

}
