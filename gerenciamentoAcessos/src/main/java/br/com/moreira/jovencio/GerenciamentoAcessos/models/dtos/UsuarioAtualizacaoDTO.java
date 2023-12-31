
package br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos;

/**
 *
 * @author marlan/eriani
 */
public class UsuarioAtualizacaoDTO {

	private int id;

	private String nome;

	private String sobrenome;

	private String email;

	public UsuarioAtualizacaoDTO( int id ) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome( String nome ) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome( String sobrenome ) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

}
