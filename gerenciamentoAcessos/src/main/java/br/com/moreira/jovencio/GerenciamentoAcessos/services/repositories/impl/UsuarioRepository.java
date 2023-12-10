
package br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.impl;

import br.com.moreira.jovencio.GerenciamentoAcessos.daos.IUsuarioDAO;
import br.com.moreira.jovencio.GerenciamentoAcessos.factories.daos.DAOFactory;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.dtos.UsuarioGridDTO;
import br.com.moreira.jovencio.GerenciamentoAcessos.models.entities.Usuario;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.AbstractLogService;
import br.com.moreira.jovencio.GerenciamentoAcessos.services.repositories.IUsuarioRepository;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marlan/eriani
 */
public class UsuarioRepository extends AbstractLogService implements IUsuarioRepository {

	private IUsuarioDAO usuarioDao;

	public UsuarioRepository() throws Exception {
		super( LoggerFactory.getLogger( UsuarioRepository.class ) );
		usuarioDao = DAOFactory.getDAOFactory().getUsuarioDAO();
	}

	@Override
	public List<UsuarioGridDTO> search( String nome, Boolean possuiNotificacoes ) {
		try {
			return usuarioDao.search( nome, possuiNotificacoes );
		} catch ( Exception ex ) {
			tratarErro( ex );
		}
		return Collections.emptyList();
	}

	@Override
	public Usuario get( int id ) throws Exception {
		return usuarioDao.get( id );
	}

	@Override
	public boolean isAutorizado( int id ) throws Exception {
		var usuario = get( id );
		return usuario.isAutorizado();
	}
}
