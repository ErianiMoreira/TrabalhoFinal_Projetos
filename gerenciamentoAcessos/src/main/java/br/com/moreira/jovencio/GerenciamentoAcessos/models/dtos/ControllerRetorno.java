
package br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos;

/**
 *
 * @author marlan/eriani
 */
public class ControllerRetorno {

	private int codigo;

	private Integer entidadeId;

	private String mensagem;

	public ControllerRetorno() {
		codigo = 500;
	}

	public ControllerRetorno( int codigo ) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo( int codigo ) {
		this.codigo = codigo;
	}

	public Integer getEntidadeId() {
		return entidadeId;
	}

	public void setEntidadeId( Integer entidadeId ) {
		this.entidadeId = entidadeId;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem( String mensagem ) {
		this.mensagem = mensagem;
	}

	public boolean isError() {
		return codigo >= 500 && codigo <= 600;
	}

	public boolean isWarning() {
		return codigo >= 400 && codigo <= 500;
	}

	public boolean isSuccess() {
		return codigo >= 200 && codigo <= 300;
	}

}
