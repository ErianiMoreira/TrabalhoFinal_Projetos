
package br.com.moreira.jovencio.GerenciamentoAcessos.services.log.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.ILogService;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author marlan/eriani
 */
public class TxtLogService implements ILogService {

	@Override
	public void registrarFalha( String falha, String operacao, int usuarioId ) {
		try {
			registrarFalha( falha, operacao, DAOFactory.getDAOFactory().getUsuarioDAO().get( usuarioId ) );
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
	}

	private final String TEMPLATE = "Ocorreu a falha \"%s\" ao realizar a operação \"%s\", (\"%s\", e \"%s\").";

	@Override
	public void registrarFalha( String falha, String operacao, Usuario usuario ) {
		var agora = LocalDateTime.now();
		var usuarioString = new StringBuilder();
		if( usuario != null ) {
			usuarioString.append( "Usuário{id=" ).append( usuario.getId() ).append( ", nome completo=" ).append( usuario.getNomeCompleto() ).append( "} " );
		} else {
			usuarioString.append( "Usuário não logado ou não identificado " );
		}

		try {
			escrever( agora, String.format( TEMPLATE, falha, operacao, agora.format( DateTimeFormatter.ofPattern( "dd/MM/yyyy', 'H:mm:ss" ) ), usuarioString ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private void escrever( LocalDateTime hora, String linha ) throws IOException {
		Path currentRelativePath = Paths.get( "" );
		String s = currentRelativePath.toAbsolutePath().toString();
		Dotenv dotenv = Dotenv.configure().load();
		var path = dotenv.get( "log_output_path", s + "/src/main/resource/logs/" );
		if( !path.endsWith( "/" ) ) {
			path = path.substring( 0, path.lastIndexOf( "/" ) );
		}
		var fileName = hora.format( DateTimeFormatter.ofPattern( "yyyyMMdd" ) ) + ".txt";

		writeDataLineByLine( path + fileName, linha );
	}

	public static void writeDataLineByLine( String filePath, String linha ) {
		File file = new File( filePath );
		try {
			var isNew = file.createNewFile();
			BufferedWriter out = new BufferedWriter( new FileWriter( file, !isNew ) );
			out.write( linha );
			out.newLine();
			out.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
        
        @Override
        public void registrarAcao(String acao, String operacao, int usuarioId) {
            try {
                    registrarAcao( acao, operacao, DAOFactory.getDAOFactory().getUsuarioDAO().get( usuarioId ) );
                } catch ( Exception ex ) {
                    ex.printStackTrace();
                }
        }
        
        private final String TEMPLATE_ACAO = "Ocorreu a ação \"%s\" ao realizar a operação \"%s\", (\"%s\", e \"%s\").";

        @Override
        public void registrarAcao(String acao, String operacao, Usuario usuario){
            var agora = LocalDateTime.now();
		var usuarioString = new StringBuilder();
		if( usuario != null ) {
			usuarioString.append( "Usuário{id=" ).append( usuario.getId() ).append( ", nome completo=" ).append( usuario.getNomeCompleto() ).append( "} " );
		} else {
			usuarioString.append( "Usuário não logado ou não identificado " );
		}

		try {
			escrever( agora, String.format( TEMPLATE_ACAO, acao, operacao, agora.format( DateTimeFormatter.ofPattern( "dd/MM/yyyy', 'H:mm:ss" ) ), usuarioString ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
        }

}
