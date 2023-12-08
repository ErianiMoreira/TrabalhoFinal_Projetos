
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

	private boolean isCompleta;

	private Integer id;

	private String nome;

	private String sobrenome;

	private String login;

	private String email;

	private String senha;

	private Set<String> permissoes;

	private final LocalDateTime dataCadastro;

	private LocalDateTime dataAutorizado;

	public Usuario() {
		this.dataCadastro = LocalDateTime.now();
		isCompleta = false;
	}

	public Usuario( int id ) {
		this.dataCadastro = null;
		this.id = id;
		isCompleta = false;
	}

	public Usuario( int id, LocalDateTime dataCadastro ) {
		this.dataCadastro = dataCadastro;
		this.id = id;
		isCompleta = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
		updateIsCompleta();
	}

	public String getNome() {
		return nome;
	}

	public void setNome( String nome ) {
		this.nome = nome;
		updateIsCompleta();
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome( String sobrenome ) {
		this.sobrenome = sobrenome;
		updateIsCompleta();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin( String login ) {
		this.login = login;
		updateIsCompleta();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
		updateIsCompleta();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha( String senha ) {
		this.senha = senha;
		updateIsCompleta();
	}

	public Set<String> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes( Set<String> permissoes ) {
		this.permissoes = permissoes;
		updateIsCompleta();
	}

	public void addPermissao( String permissao ) {
		if( ValidarCampo.isNullVazioOrEspacos( permissao ) ) {
			return;
		}
		if( this.permissoes == null || this.permissoes.isEmpty() ) {
			this.permissoes = new HashSet<>();
		}
		this.permissoes.add( permissao );
		updateIsCompleta();
	}

	public void addPermissoes( String... permissoes ) {
		if( permissoes == null || permissoes.length <= 0 ) {
			return;
		}
		if( this.permissoes == null || this.permissoes.isEmpty() ) {
			this.permissoes = new HashSet<>();
		}
		Arrays.stream( permissoes ).forEach( permissao -> this.addPermissao( permissao ) );
		updateIsCompleta();
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

	private void updateIsCompleta() {
		isCompleta = id != null && nome != null && sobrenome != null && login != null && senha != null && permissoes != null && dataCadastro != null;
	}
}
