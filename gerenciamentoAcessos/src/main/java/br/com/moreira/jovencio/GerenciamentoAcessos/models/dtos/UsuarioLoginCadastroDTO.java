
package br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos;

/**
 *
 * @author marlan/eriani
 */
public class UsuarioLoginCadastroDTO {

	private String nome;

	private String sobrenome;

	private String login;

	private String email;

	private String senha;

	private String confirmacaoSenhaString;

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

	public String getSenha() {
		return senha;
	}

	public void setSenha( String senha ) {
		this.senha = senha;
	}

	public String getConfirmacaoSenhaString() {
		return confirmacaoSenhaString;
	}

	public void setConfirmacaoSenhaString( String confirmacaoSenhaString ) {
		this.confirmacaoSenhaString = confirmacaoSenhaString;
	}

}
