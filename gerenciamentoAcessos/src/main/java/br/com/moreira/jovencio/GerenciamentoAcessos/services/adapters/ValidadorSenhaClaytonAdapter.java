
package br.com.moreira.jovencio.GerenciamentoAcessos.services.adapters;

import br.com.moreira.jovencio.GerenciamentoAcessos.services.IValidadorSenha;
import static br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo.isNullVazioOrEspacos;
import com.pss.senha.validacao.ValidadorSenha;

/**
 *
 * @author marlan
 */
public class ValidadorSenhaClaytonAdapter implements IValidadorSenha {

	@Override
	public String validar( String senha, int tamanhoLimite ) {
		if( isNullVazioOrEspacos( senha ) ) {
			return "Campo 'Senha' não preenchido";
		}
		var erros = new ValidadorSenha().validar( senha );
		erros = erros.stream().filter( validacao -> !validacao.contains( "A senha deve ter no maximo 10 caracteres" ) ).map( validacao -> {
			if( validacao.lastIndexOf( ";" ) > 0 ) {
				validacao = validacao.substring( 0, validacao.lastIndexOf( ";" ) );
			}
			return validacao;
		} ).toList();

		if( senha.length() > 75 ) {
			erros.add( "A senha deve ter no maximo 75 caracteres" );
		}

		if( erros.isEmpty() ) {
			return null;
		}
		return String.join( ", ", erros );
	}

}
