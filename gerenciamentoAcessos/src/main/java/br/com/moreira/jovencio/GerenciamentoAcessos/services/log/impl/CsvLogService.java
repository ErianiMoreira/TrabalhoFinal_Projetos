
package br.com.moreira.jovencio.GerenciamentoAcessos.services.log.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.ILogService;
import com.opencsv.CSVWriter;
import io.github.cdimascio.dotenv.Dotenv;
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
public class CsvLogService implements ILogService {

	public CsvLogService() {
	}

	@Override
	public void registrarFalha( String falha, String operacao, int usuarioId ) {
		try {
			registrarFalha( falha, operacao, DAOFactory.getDAOFactory().getUsuarioDAO().get( usuarioId ) );
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
	}

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
			escrever( agora, falha, operacao, usuarioString.toString() );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private void escrever( LocalDateTime hora, String falha, String operacao, String usuario ) throws IOException {
		Path currentRelativePath = Paths.get( "" );
		String s = currentRelativePath.toAbsolutePath().toString();
		Dotenv dotenv = Dotenv.configure().load();
		var path = dotenv.get( "log_output_path", s + "/src/main/resource/logs/" );
		if( !path.endsWith( "/" ) ) {
			path = path.substring( 0, path.lastIndexOf( "/" ) );
		}
		var fileName = hora.format( DateTimeFormatter.ofPattern( "yyyyMMdd" ) ) + ".csv";

		writeDataLineByLine( path + fileName, falha, operacao, usuario, hora.format( DateTimeFormatter.ofPattern( "dd/MM/yyyy H:mm:ss" ) ) );
	}

	public static void writeDataLineByLine( String filePath, String falha, String operacao, String usuario, String data ) {
		File file = new File( filePath );
		try {
			var isNew = file.createNewFile();
			FileWriter outputfile = new FileWriter( file, !isNew );
			CSVWriter writer = new CSVWriter( outputfile, ';', '"', '"', "\n" );
			if( isNew ) {
				String[] header = { "Falha", "Operação", "Usuário", "Data" };
				writer.writeNext( header );
			}

			String[] data1 = { falha, operacao, usuario, data };
			writer.writeNext( data1 );
			writer.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
