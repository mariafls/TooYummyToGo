package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.exceptions.TipoDeProdutoJaExisteException;

public class AdicionarTipoDeProdutoHandler {
	 
	private Comerciante comerciante;

	public AdicionarTipoDeProdutoHandler(Comerciante currentComerciante) {
		this.comerciante = currentComerciante;
	}

	public void registaTipoDeProduto(String tp, double preco) throws TipoDeProdutoJaExisteException {
		comerciante.adicionarTipoProduto(tp, preco);
	}
	
}
