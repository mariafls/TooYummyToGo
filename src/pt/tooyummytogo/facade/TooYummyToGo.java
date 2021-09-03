package pt.tooyummytogo.facade;

import java.util.Optional;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.domain.CatComerciante;
import pt.tooyummytogo.domain.CatUtilizador;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Utilizador;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;
 
/**
 * Esta é a classe do sistema.
 */
public class TooYummyToGo {
	
	private CatComerciante catComerciante = new CatComerciante();
	private CatUtilizador catUtilizador = new CatUtilizador();

	// UC1
	public RegistarUtilizadorHandler getRegistarUtilizadorHandler() {
		return new RegistarUtilizadorHandler(this.catUtilizador, this.catComerciante);
	}
	
	/**
	 * Returns an optional Session representing the authenticated user.
	 * @param username
	 * @param password
	 * @return
	 * 
	 * UC2
	 */

	public Optional<Sessao> autenticar(String username, String password) {
		Optional<Utilizador> utilizador = catUtilizador.tryLogin(username, password);
		if(utilizador.isPresent()) {
			return utilizador.map(u -> new Sessao(u, catComerciante));
		}
		
		Optional<Comerciante> comerciante = catComerciante.tryLogin(username, password);
		return comerciante.map(u -> new Sessao(u, catComerciante));
	} 

	// UC3
	public RegistarComercianteHandler getRegistarComercianteHandler() {
		return new RegistarComercianteHandler(this.catUtilizador, this.catComerciante);
	}
	

}

