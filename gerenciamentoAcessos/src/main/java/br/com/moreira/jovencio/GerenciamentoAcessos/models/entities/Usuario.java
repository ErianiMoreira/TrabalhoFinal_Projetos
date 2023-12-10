
package br.com.moreira.jovencio.GerenciamentoAcessos.models.entities;

import br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author marlan
 */
public class Usuario {

	private Integer id;

	private String nome;

	private String sobrenome;

	private String login;

	private String email;

	private String senha;

	private String senhaConfirmacao;

	private Set<String> permissoes;

	private final LocalDateTime dataCadastro;

	private LocalDateTime dataAutorizado;

	public Usuario() {
		this.dataCadastro = LocalDateTime.now();
	}

	public Usuario( int id ) {
		this.dataCadastro = null;
		this.id = id;
	}

	public Usuario( int id, LocalDateTime dataCadastro ) {
		this.dataCadastro = dataCadastro;
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
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

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao( String senhaConfirmacao ) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	public Set<String> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes( Set<String> permissoes ) {
		this.permissoes = permissoes;
	}

	public void addPermissao( String permissao ) {
		if( ValidarCampo.isNullVazioOrEspacos( permissao ) ) {
			return;
		}
		if( this.permissoes == null || this.permissoes.isEmpty() ) {
			this.permissoes = new HashSet<>();
		}
		this.permissoes.add( permissao );
	}

	public void addPermissoes( String... permissoes ) {
		if( permissoes == null || permissoes.length <= 0 ) {
			return;
		}
		if( this.permissoes == null || this.permissoes.isEmpty() ) {
			this.permissoes = new HashSet<>();
		}
		Arrays.stream( permissoes ).forEach( permissao -> this.addPermissao( permissao ) );
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public boolean isAutorizado() {
		return dataAutorizado != null;
	}

	public LocalDateTime getDataAutorizado() {
		return dataAutorizado;
	}

	public void setDataAutorizado( LocalDateTime dataAutorizado ) {
		this.dataAutorizado = dataAutorizado;
	}

	public String getNomeCompleto() {
		if( nome != null && sobrenome != null )
			return nome + " " + sobrenome;
		else if( nome != null )
			return nome;
		else if( sobrenome != null )
			return sobrenome;
		return "nome não preenchido";
	}

}
