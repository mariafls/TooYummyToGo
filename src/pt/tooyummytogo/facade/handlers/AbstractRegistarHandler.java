package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.CatComerciante;
import pt.tooyummytogo.domain.CatUtilizador;

public abstract class AbstractRegistarHandler {

	protected CatComerciante catComerciante;
	protected CatUtilizador catUtilizador;
	 
	public AbstractRegistarHandler(CatComerciante catComerciante, CatUtilizador catUtilizador) {
		this.catComerciante = catComerciante;
		this.catUtilizador = catUtilizador;
	}
	
}
