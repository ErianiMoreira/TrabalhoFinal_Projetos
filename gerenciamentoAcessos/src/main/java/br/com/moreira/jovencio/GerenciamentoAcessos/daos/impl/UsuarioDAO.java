
package br.com.moreira.jovencio.GerenciamentoAcessos.daos.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.daos.conexoes.SQLiteBancoDadosConexao;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioGridDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.usuario.IUsuarioDAOObervador;
import br.com.moreira.jovencio.GerenciamentoAcessos.observers.usuario.IUsuarioDAOObservavel;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.ValidarCampo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marlan/eriani
 */
public class UsuarioDAO extends SQLiteBancoDadosConexao implements IUsuarioDAO, IUsuarioDAOObservavel {

	protected final Logger log = LoggerFactory.getLogger( UsuarioDAO.class );
	private List<IUsuarioDAOObervador> observadores;
	private static UsuarioDAO instancia = null;

	private UsuarioDAO() {
	}

	public static UsuarioDAO getInstancia() {
		if( instancia == null ) {
			instancia = new UsuarioDAO();
		}
		return instancia;
	}

	@Override
	public void adicionarObservador( IUsuarioDAOObervador observador ) {
		if( observadores == null ) {
			observadores = new ArrayList<>();
		}
		observadores.add( observador );
	}

	@Override
	public void removerObservador( IUsuarioDAOObervador observador ) {
		if( observadores == null ) {
			return;
		}

		if( observadores.contains( observador ) ) {
			observadores.remove( observador );
		}
	}

	@Override
	public void notificarObservadores() {
		if( observadores == null || observadores.isEmpty() ) {
			return;
		}

		observadores.forEach( observador -> observador.atualizar( this ) );

	}

	@Override
	public void createTable() throws Exception {
		var sql = new StringBuilder();

		sql.append( " create table if not exists usuarios( " );
		sql.append( "     id integer primary key autoincrement, " );
		sql.append( "     nome varchar(50) not null, " );
		sql.append( "     sobrenome varchar(100) not null, " );
		sql.append( "     email varchar(150) null, " );
		sql.append( "     login varchar(50) not null, " );
		sql.append( "     senha varchar(75) null, " );
		sql.append( "     permissoes text null, " );
		sql.append( "     dataCadastro datetime null, " );
		sql.append( "     dataAutorizado datetime null " );
		sql.append( " ); " );

		log.info( sql.toString() );

		var statement = openConnection().createStatement();
		statement.execute( sql.toString() );
		closeConnection( statement.getConnection() );
	}

	@Override
	public Usuario findByLoginAndSenha( String login, String senha ) throws Exception {
		var sql = new StringBuilder();

		sql.append( " select " );
		sql.append( appendTodasColunas() );
		sql.append( " from usuarios u " );
		sql.append( " where u.login = '" ).append( login ).append( "' " );
		sql.append( "   and u.senha = '" ).append( senha ).append( "' " );

		var statement = openConnection().createStatement();
		log.info( sql.toString() );
		var result = statement.executeQuery( sql.toString() );
		result.next();
		var usuario = recuperaTodasColunas( result );
		closeConnection( statement.getConnection() );
		if( Integer.compare( usuario.getId(), 0 ) == 0 ) {
			return null;
		}
		return usuario;
	}

	@Override
	public Usuario findUsuarioComLogin( String login ) throws SQLException {
		var sql = new StringBuilder();

		sql.append( " select " );
		sql.append( appendTodasColunas() );
		sql.append( " from usuarios u " );
		sql.append( " where u.login = '" ).append( login ).append( "' " );

		var statement = openConnection().createStatement();
		log.info( sql.toString() );
		var result = statement.executeQuery( sql.toString() );
		result.next();
		var usuario = recuperaTodasColunas( result );
		closeConnection( statement.getConnection() );
		if( Integer.compare( usuario.getId(), 0 ) == 0 ) {
			return null;
		}
		return usuario;
	}

	@Override
	public Usuario insert( Usuario entity ) throws SQLException {
		var sql = new StringBuilder();

		sql.append( " insert into usuarios( " );
		sql.append( "     nome, " );
		sql.append( "     sobrenome, " );
		sql.append( "     login, " );
		sql.append( "     email, " );
		sql.append( "     senha, " );
		sql.append( "     permissoes, " );
		sql.append( "     dataCadastro, " );
		sql.append( "     dataAutorizado " );
		sql.append( " ) " );
		sql.append( " values ( " );
		sql.append( "     '" ).append( entity.getNome() ).append( "', " );
		sql.append( "     '" ).append( entity.getSobrenome() ).append( "', " );
		sql.append( "     '" ).append( entity.getLogin() ).append( "', " );
		if( ValidarCampo.isNotNullVazioOrEspacos( entity.getEmail() ) ) {
			sql.append( "     '" ).append( entity.getEmail() ).append( "', " );
		} else {
			sql.append( "     null, " );
		}
		if( entity.getSenha() != null && !entity.getSenha().isEmpty() && !entity.getSenha().isBlank() ) {
			sql.append( "     '" ).append( entity.getSenha() ).append( "', " );
		} else {
			sql.append( "     null, " );
		}
		if( entity.getPermissoes() != null && !entity.getPermissoes().isEmpty() ) {
			sql.append( "     '" ).append( String.join( ";", entity.getPermissoes() ) ).append( "', " );
		} else {
			sql.append( "     null, " );
		}
		sql.append( "     '" ).append( entity.getDataCadastro().format( DATE_TIME_PATTERN ) ).append( "', " );
		if( entity.getDataAutorizado() != null ) {
			sql.append( "     '" ).append( entity.getDataAutorizado().format( DATE_TIME_PATTERN ) ).append( "' " );
		} else {
			sql.append( "     null " );
		}
		sql.append( " ); " );

		var id = super.insert( sql.toString() );
		entity.setId( id );
		notificarObservadores();
		return entity;
	}

	@Override
	public Usuario get( int id ) throws SQLException {
		var sql = new StringBuilder();

		sql.append( " select " );
		sql.append( appendTodasColunas() );
		sql.append( " from usuarios u " );
		sql.append( " where u.id = " ).append( id ).append( " " );

		log.info( sql.toString() );
		var statement = openConnection().createStatement();
		var result = statement.executeQuery( sql.toString() );
		result.next();
		var usuario = recuperaTodasColunas( result );
		closeConnection( statement.getConnection() );
		if( Integer.compare( usuario.getId(), 0 ) == 0 ) {
			return null;
		}
		return usuario;
	}

	@Override
	public void update( int id, Usuario entity ) throws SQLException {
		var sql = new StringBuilder();

		sql.append( " update usuarios set " );
		sql.append( "     nome = '" ).append( entity.getNome() ).append( "', " );
		sql.append( "     sobrenome = '" ).append( entity.getSobrenome() ).append( "', " );
		sql.append( "     email = '" ).append( entity.getEmail() ).append( "' " );
		sql.append( " where id = " ).append( id );
		sql.append( " ; " );

		log.info( sql.toString() );
		var statement = openConnection().createStatement();
		statement.executeUpdate( sql.toString() );
		closeConnection( statement.getConnection() );
		notificarObservadores();
	}

	@Override
	public void delete( int id ) throws SQLException {
		var sql = new StringBuilder();

		sql.append( " delete from usuarios " );
		sql.append( " where id = " ).append( id );

		log.info( sql.toString() );

		var statement = openConnection().createStatement();
		statement.executeUpdate( sql.toString() );
		closeConnection( statement.getConnection() );
		notificarObservadores();
	}

	@Override
	public List<UsuarioGridDTO> search( String nome, Boolean possuiNotificacoes ) throws Exception {
		var sql = new StringBuilder();
		sql.append( " select " );
		sql.append( "     u.id, " );
		sql.append( "     concat(u.nome, ' ', u.sobrenome) as nomeCompleto, " );
		sql.append( "     u.dataCadastro, " );
		sql.append( "     count(n.id) as notificacoesEnviadas, " );
		sql.append( "     count(n.id) filter (where n.lida is true) as notificacoesLidas, " );
		sql.append( "     u.dataAutorizado " );
		sql.append( " from usuarios u " );
		sql.append( " left join notificacoes n on n.paraUsuarioId = u.id " );
		sql.append( " where 1=1 " );
		if( ValidarCampo.isNotNullVazioOrEspacos( nome ) ) {
			sql.append( "   and (upper(u.nome) like '%' || '" ).append( nome.toUpperCase() ).append( "' || '%' " );
			sql.append( "     or upper(u.sobrenome) like '%' || '" ).append( nome.toUpperCase() ).append( "' || '%' ) " );
		}

		if( possuiNotificacoes != null ) {
			sql.append( "   and " ).append( !possuiNotificacoes ? "not" : "" ).append( " exists (select 1 from notificacoes n2 where n2.paraUsuarioId = u.id and n2.lida is false) " );
		}
		sql.append( " group by 1 " );

		log.info( sql.toString() );
		var statement = openConnection().createStatement();
		var result = statement.executeQuery( sql.toString() );
		List<UsuarioGridDTO> linhas = new ArrayList<>();
		while( result.next() ) {
			var linha = mapUsuarioGridDTO( result );
			if( linha != null && Integer.compare( linha.getId(), 0 ) != 0 ) {
				linhas.add( linha );
			}
		}
		closeConnection( statement.getConnection() );
		return linhas;
	}

	@Override
	public int getCountUsuarios() throws Exception {
		var sql = new StringBuilder();

		sql.append( " select count(u.id) as countUsuarios " );
		sql.append( " from usuarios u " );
		var statement = openConnection().createStatement();
		log.info( sql.toString() );
		var result = statement.executeQuery( sql.toString() );
		result.next();
		var countUsuarios = result.getInt( "countUsuarios" );
		closeConnection( statement.getConnection() );
		return countUsuarios;
	}

	@Override
	public void autorizar( int id ) throws Exception {
		var sql = new StringBuilder();

		sql.append( " update usuarios set " );
		sql.append( "     dataAutorizado = '" ).append( LocalDateTime.now().format( DATE_TIME_PATTERN ) ).append( "' " );
		sql.append( " where id = " ).append( id );
		sql.append( " ; " );

		log.info( sql.toString() );
		var statement = openConnection().createStatement();
		statement.executeUpdate( sql.toString() );
		closeConnection( statement.getConnection() );
		notificarObservadores();
	}

	@Override
	public void alterarSenha( int id, String senha ) throws Exception {
		var sql = new StringBuilder();

		sql.append( " update usuarios set " );
		sql.append( "     senha = '" ).append( senha ).append( "' " );
		sql.append( " where id = " ).append( id );
		sql.append( " ; " );

		log.info( sql.toString() );
		var statement = openConnection().createStatement();
		statement.executeUpdate( sql.toString() );
		closeConnection( statement.getConnection() );
	}

	@Override
	public void resetarSenha( int id ) throws Exception {
		var sql = new StringBuilder();

		sql.append( " update usuarios set " );
		sql.append( "     senha = null " );
		sql.append( " where id = " ).append( id );
		sql.append( " ; " );

		log.info( sql.toString() );
		var statement = openConnection().createStatement();
		statement.executeUpdate( sql.toString() );
		closeConnection( statement.getConnection() );
	}

	private String appendTodasColunas() {
		var sql = new StringBuilder();
		sql.append( "     u.id, " );
		sql.append( "     u.nome, " );
		sql.append( "     u.sobrenome, " );
		sql.append( "     u.email, " );
		sql.append( "     u.login, " );
		sql.append( "     u.senha, " );
		sql.append( "     u.permissoes, " );
		sql.append( "     u.dataCadastro, " );
		sql.append( "     u.dataAutorizado " );
		return sql.toString();
	}

	private Usuario recuperaTodasColunas( ResultSet result ) throws SQLException {
		var id = result.getInt( "id" );
		if( id == 0 ) {
			return new Usuario( id );
		}
		var dataCadastro = LocalDateTime.parse( result.getString( "dataCadastro" ), DATE_TIME_PATTERN );
		var usuario = new Usuario( id, dataCadastro );
		usuario.setNome( result.getString( "nome" ) );
		usuario.setSobrenome( result.getString( "sobrenome" ) );
		usuario.setEmail( result.getString( "email" ) );
		usuario.setLogin( result.getString( "login" ) );
		usuario.setSenha( result.getString( "senha" ) );
		var permissoesString = result.getString( "permissoes" );
		usuario.setPermissoes( permissoesString != null ? Set.of( permissoesString.split( ";" ) ) : null );
		var dataAutorizadoString = result.getString( "dataAutorizado" );
		usuario.setDataAutorizado( dataAutorizadoString == null ? null : LocalDateTime.parse( dataAutorizadoString, DATE_TIME_PATTERN ) );
		return usuario;
	}

	private UsuarioGridDTO mapUsuarioGridDTO( ResultSet result ) throws SQLException {
		var id = result.getInt( "id" );
		if( id == 0 ) {
			return null;
		}
		var usuario = new UsuarioGridDTO();
		usuario.setId( id );
		usuario.setNomeCompleto( result.getString( "nomeCompleto" ) );
		usuario.setDataCadastro( LocalDateTime.parse( result.getString( "dataCadastro" ), DATE_TIME_PATTERN ) );
		usuario.setNotificacoesEnviadas( result.getInt( "notificacoesEnviadas" ) );
		usuario.setNotificacoesLidas( result.getInt( "notificacoesLidas" ) );
		var dataAutorizadoString = result.getString( "dataAutorizado" );
		usuario.setDataAutorizado( dataAutorizadoString == null ? null : LocalDateTime.parse( dataAutorizadoString, DATE_TIME_PATTERN ) );
		return usuario;
	}
}
