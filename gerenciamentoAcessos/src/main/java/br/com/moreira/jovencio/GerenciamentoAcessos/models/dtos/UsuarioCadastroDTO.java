
package br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos;

/**
 *
 * @author marlan/eriani
 */
public class UsuarioCadastroDTO {

	private String nome;

	private String sobrenome;

	private String login;

	private String email;

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

	public String getLogin() {
		return login;
	}

	public void setLogin( String login ) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

}
