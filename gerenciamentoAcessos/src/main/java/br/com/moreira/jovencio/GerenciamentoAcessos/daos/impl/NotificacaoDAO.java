
package br.com.moreira.jovencio.GerenciamentoAcessos.daos.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.INotificacaoDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.daos.conexoes.SQLiteBancoDadosConexao;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Notificacao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marlan
 */
public class NotificacaoDAO extends SQLiteBancoDadosConexao implements INotificacaoDAO {

	protected final Logger log = LoggerFactory.getLogger( NotificacaoDAO.class );

	@Override
	public void createTable() throws Exception {
		var sql = new StringBuilder();

		sql.append( " create table if not exists notificacoes ( " );
		sql.append( "     id integer primary key autoincrement, " );
		sql.append( "     mensagem text not null, " );
		sql.append( "     lida boolean default (false) not null, " );
		sql.append( "     dataEnvio datetime not null, " );
		sql.append( "     deUsuarioId integer, " );
		sql.append( "     paraUsuarioId integer not null, " );
		sql.append( "     constraint fk_notificacoes_usuarios_deUsuarioId foreign key (deUsuarioId) references usuarios(id), " );
		sql.append( "     constraint fk_notificacoes_usuarios_paraUsuarioId foreign key (paraUsuarioId) references usuarios(id) " );
		sql.append( " ); " );

		log.info( sql.toString() );

		var statement = openConnection().createStatement();
		statement.execute( sql.toString() );
		closeConnection( statement.getConnection() );
	}

	@Override
	public Notificacao insert( Notificacao entity ) throws Exception {
		var sql = new StringBuilder();

		sql.append( " insert into notificacoes( " );
		sql.append( "     mensagem, " );
		sql.append( "     lida, " );
		sql.append( "     dataEnvio, " );
		sql.append( "     deUsuarioId, " );
		sql.append( "     paraUsuarioId " );
		sql.append( " ) " );
		sql.append( " values( " );
		sql.append( "     '" ).append( entity.getMensagem() ).append( "', " );
		sql.append( "     " ).append( entity.isLida() ).append( ", " );
		sql.append( "     '" ).append( entity.getDataEnvio().format( DATE_TIME_PATTERN ) ).append( "', " );

		sql.append( "     " ).append( entity.getDeUsuarioId() ).append( ", " );
		sql.append( "     " ).append( entity.getParaUsuarioId() ).append( " " );
		sql.append( " ) " );

		var id = super.insert( sql.toString() );
		entity.setId( id );
		return entity;
	}

	@Override
	public List<Notificacao> findAllByParaUsuarioId( int id ) throws Exception {
		var sql = new StringBuilder();

		sql.append( " select " );
		sql.append( appendTodasColunas() );
		sql.append( " from notificacoes n " );
		sql.append( " where n.paraUsuarioId = " ).append( id ).append( ";" );

		log.info( sql.toString() );
		var statement = openConnection().createStatement();
		var result = statement.executeQuery( sql.toString() );
		List<Notificacao> entities = new ArrayList<>();
		while( result.next() ) {
			var entity = recuperaTodasColunas( result );
			if( Integer.compare( entity.getId(), 0 ) != 0 ) {
				entities.add( entity );
			}
		}
		closeConnection( statement.getConnection() );
		return entities;
	}

	@Override
	public void marcar( int id, boolean lida ) throws Exception {
		var sql = new StringBuilder();

		sql.append( " update notificacoes set " );
		sql.append( "     lida = " ).append( lida );
		sql.append( " where id = " ).append( id ).append( "; " );

		log.info( sql.toString() );
		var statement = openConnection().createStatement();
		statement.executeUpdate( sql.toString() );
		closeConnection( statement.getConnection() );
	}

	private String appendTodasColunas() {
		var sql = new StringBuilder();
		sql.append( "     n.id, " );
		sql.append( "     n.mensagem, " );
		sql.append( "     n.lida, " );
		sql.append( "     n.dataEnvio, " );
		sql.append( "     n.deUsuarioId, " );
		sql.append( "     n.paraUsuarioId " );
		return sql.toString();
	}

	private Notificacao recuperaTodasColunas( ResultSet result ) throws SQLException {
		var id = result.getInt( "id" );
		if( id == 0 ) {
			return new Notificacao( id );
		}
		var dataEnvio = LocalDateTime.parse( result.getString( "dataEnvio" ), DATE_TIME_PATTERN );
		Notificacao notificacao = new Notificacao( id, dataEnvio );
		notificacao.setMensagem( result.getString( "mensagem" ) );
		notificacao.setLida( result.getBoolean( "lida" ) );
		notificacao.setDeUsuario( result.getInt( "deUsuarioId" ) );
		notificacao.setParaUsuario( result.getInt( "paraUsuarioId" ) );
		return notificacao;
	}

}
