package pt.tooyummytogo;

import pt.tooyummytogo.domain.CatComerciante;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.User;
import pt.tooyummytogo.exceptions.NaoEhComercianteException;
import pt.tooyummytogo.exceptions.NaoEhUtilizadorException;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;

public class Sessao {

	private User currentUtilizador;
	private CatComerciante currentCatComerciante;
	private Comerciante currentComerciante;

	public Sessao(User u, CatComerciante catComerciante) {
		this.currentUtilizador = u;
		this.currentCatComerciante = catComerciante;
	}

	public Sessao(Comerciante currentComerciante, CatComerciante catComerciante) {
		this.currentComerciante = currentComerciante;
		this.currentCatComerciante = catComerciante;
	}

	// UC4
	public AdicionarTipoDeProdutoHandler adicionarTipoDeProdutoHandler() throws NaoEhComercianteException {
		if(currentComerciante != null) {
		return new AdicionarTipoDeProdutoHandler(currentComerciante);
		}
		throw new NaoEhComercianteException();
	}

	// UC5
	public ColocarProdutoHandler getColocarProdutoHandler() throws NaoEhComercianteException {
		if(currentComerciante != null) {
		return new ColocarProdutoHandler(currentComerciante);
		}
		throw new NaoEhComercianteException();
	}

	public EncomendarHandler getEncomendarComerciantesHandler() throws NaoEhUtilizadorException {
		if(currentUtilizador != null) {
		return new EncomendarHandler(currentCatComerciante, currentUtilizador);	
		}
		throw new NaoEhUtilizadorException();
	}
}
