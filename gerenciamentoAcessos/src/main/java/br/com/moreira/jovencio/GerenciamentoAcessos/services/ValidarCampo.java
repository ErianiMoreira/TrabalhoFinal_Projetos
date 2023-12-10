
package br.com.moreira.jovencio.GerenciamentoAcessos.services;

import br.com.moreira.jovencio.GerenciamentoAcessos.services.adapters.ValidadorSenhaClaytonAdapter;

/**
 *
 * @author marlan
 */
public class ValidarCampo {

	public static String asString( String label, String campo, int tamanhoLimite ) {
		if( isNullVazioOrEspacos( campo ) ) {
			return "Campo '" + label + "' não preenchido";
		}

		return campo.length() > tamanhoLimite ? "Campo '" + label + "' ultrapassou o limite de '" + tamanhoLimite + "' caracteres" : null;
	}

	public static boolean isNotNullVazioOrEspacos( String valor ) {
		return !isNullVazioOrEspacos( valor );
	}

	public static boolean isNullVazioOrEspacos( String valor ) {
		return ( valor == null || valor.isBlank() || valor.isEmpty() || valor.replace( " ", "" ).isEmpty() );
	}

	public static String asEmail( String label, String campo, int tamanhoLimite ) {
		var regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@" + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
		if( !campo.matches( regexPattern ) ) {
			return "E-mail informado no campo '" + label + "' não é válido";
		}
		return null;
	}

	public static String asSenha( String label, String campo, int tamanhoLimite ) {
		IValidadorSenha validadorSenha = new ValidadorSenhaClaytonAdapter();
		return validadorSenha.validar( campo, tamanhoLimite );
	}

}
