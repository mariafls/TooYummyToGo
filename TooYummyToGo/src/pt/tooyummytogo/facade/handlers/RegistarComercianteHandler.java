package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.CatComerciante;
import pt.tooyummytogo.domain.CatUtilizador;
import pt.tooyummytogo.exceptions.ComercianteJaExisteException;
import pt.tooyummytogo.exceptions.UtilizadorJaExisteException;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
 
public class RegistarComercianteHandler extends AbstractRegistarHandler{
	
	public RegistarComercianteHandler(CatUtilizador catUtilizador, CatComerciante catComerciante) {
		super(catComerciante, catUtilizador);
	}

	/**
	 * Regista um Comerciante
	 * @ensures existe um e so um comerciante com esse username
	 */
	public void registarComerciante(String username, String password, PosicaoCoordenadas p) throws ComercianteJaExisteException, UtilizadorJaExisteException {
		if(!catUtilizador.utilizadorExiste(username, password)) {
		catComerciante.registaComerciante(username, password, p);
		} else {
			throw new UtilizadorJaExisteException();
		}
	}

}
