
package br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.INotificacaoDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.daos.impl.NotificacaoDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.daos.impl.UsuarioDAO;

/**
 *
 * @author marlan/eriani
 */
public class SQLiteDAOFactory extends DAOFactory {

	@Override
	public String getDatabase() {
		return "SQLite";
	}

	@Override
	public void updateDatabase() throws Exception {
		getUsuarioDAO().createTable();
		getNotificacaoDAO().createTable();
	}

	@Override
	public IUsuarioDAO getUsuarioDAO() {
		return UsuarioDAO.getInstancia();
	}

	@Override
	public INotificacaoDAO getNotificacaoDAO() {
		return NotificacaoDAO.getInstancia();
	}

}
