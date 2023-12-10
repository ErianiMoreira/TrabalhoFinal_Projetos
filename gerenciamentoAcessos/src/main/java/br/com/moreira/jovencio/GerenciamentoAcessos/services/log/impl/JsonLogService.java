
package br.com.moreira.jovencio.GerenciamentoAcessos.services.log.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.log.ILogService;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marlan/eriani
 */
public class JsonLogService implements ILogService {

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
			usuarioString.append( "{\"usuario\":{ \"id\":" ).append( usuario.getId() ).append( ", \"nomeCompleto\":\"" ).append( usuario.getNomeCompleto() ).append( "\"}} " );
		} else {
			usuarioString.append( "{\"usuario\":{\"id\":0, \"nomeCompleto\":\"usuário não logado ou não identificado\"}}" );
		}

		try {
			escrever( agora, falha, "", operacao, usuarioString.toString() );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private void escrever( LocalDateTime hora, String falha, String acao, String operacao, String usuario ) throws IOException {
		Path currentRelativePath = Paths.get( "" );
		String s = currentRelativePath.toAbsolutePath().toString();
		Dotenv dotenv = Dotenv.configure().load();
		var path = dotenv.get( "log_output_path", s + "/src/main/resource/logs/" );
		if( !path.endsWith( "/" ) ) {
			path = path.substring( 0, path.lastIndexOf( "/" ) );
		}
		var fileName = hora.format( DateTimeFormatter.ofPattern( "yyyyMMdd" ) ) + ".json";

		var ele = new StringBuilder();
		ele.append( "{" );
		ele.append( "\"falha\":\"" ).append( falha ).append( "\"," );
                ele.append( "\"acao\":\"" ).append( acao ).append( "\"," );
		ele.append( "\"operacao\":\"" ).append( operacao ).append( "\"," );
		ele.append( "\"usuario\":" ).append( usuario ).append( "," );
		ele.append( "\"hora\":\"" ).append( hora.format( DateTimeFormatter.ofPattern( "dd/MM/yyyy H:mm:ss" ) ) ).append( "\"" );
		ele.append( "}" );

//				hora.format( DateTimeFormatter.ofPattern( "dd/MM/yyyy H:mm:ss" ) )
		writeDataLineByLine( path + fileName, ele.toString() );
	}

	public static void writeDataLineByLine( String filePath, String elemento ) {
		try {
			String check = "]";
			String contents = "";
			File file = new File( filePath );
			List<String> linhas = new ArrayList<>();
			var isNew = file.createNewFile();
			if( !isNew ) {
				BufferedReader in = new BufferedReader( new FileReader( file ) );
				String temp;
				while( ( temp = in.readLine() ) != null ) {
					linhas.add( temp );
				}

				var lastItem = linhas.get( linhas.size() - 1 );
				if( lastItem.lastIndexOf( check ) >= 0 ) {
					while( lastItem.lastIndexOf( check ) >= 0 ) {
						lastItem = lastItem.substring( 0, lastItem.lastIndexOf( check ) );
					}
				}
				if( !lastItem.isEmpty() && !lastItem.isBlank() ) {
					linhas.set( linhas.size() - 1, lastItem );
				} else {
					linhas = linhas.subList( 0, linhas.size() - 1 );
				}

			}
			if( file.delete() ) {
				file.createNewFile();
			}
			BufferedWriter out = new BufferedWriter( new FileWriter( file ) );
			if( !isNew ) {
				for( var linha : linhas ) {
					out.write( linha );
					out.newLine();
				}
			}
			if( isNew ) {
				out.write( "[" );
				out.newLine();
			} else {
				out.write( "," );
			}
			out.write( elemento );
			out.newLine();
			out.write( "]" );
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

        @Override
        public void registrarAcao(String acao, String operacao, Usuario usuario){
            var agora = LocalDateTime.now();
		var usuarioString = new StringBuilder();
		if( usuario != null ) {
			usuarioString.append( "{\"usuario\":{ \"id\":" ).append( usuario.getId() ).append( ", \"nomeCompleto\":\"" ).append( usuario.getNomeCompleto() ).append( "\"}} " );
		} else {
			usuarioString.append( "{\"usuario\":{\"id\":0, \"nomeCompleto\":\"usuário não logado ou não identificado\"}}" );
		}

		try {
			escrever( agora, "", acao, operacao, usuarioString.toString() );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
        }

}
