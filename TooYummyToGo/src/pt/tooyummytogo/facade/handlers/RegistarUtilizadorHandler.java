package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.CatComerciante;
import pt.tooyummytogo.domain.CatUtilizador;
import pt.tooyummytogo.exceptions.ComercianteJaExisteException;
import pt.tooyummytogo.exceptions.UtilizadorJaExisteException;
 
public class RegistarUtilizadorHandler extends AbstractRegistarHandler {
	
	public RegistarUtilizadorHandler(CatUtilizador catUtilizador, CatComerciante catComerciante) {
		super(catComerciante, catUtilizador);
	}

	/**
	 * Regista um utilizador normal.
	 * @param Username
	 * @param Password
	 * @throws UtilizadorJaExisteException 
	 * @throws ComercianteJaExisteException 
	 * @throws ContaJaExisteException 
	 * @ensures existe um utilizador com esse username
	 */
	public void registarUtilizador(String username, String password) throws UtilizadorJaExisteException, ComercianteJaExisteException {
		if(!this.catComerciante.comercianteExiste(username)) {
			this.catUtilizador.registaUtilizador(username, password);
		} else {
			throw new ComercianteJaExisteException();
		}
	}

}
