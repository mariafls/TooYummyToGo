package pt.tooyummytogo.facade.handlers;

import java.time.LocalDateTime;
import java.util.List;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.exceptions.CodigoNaoExisteException;
 
public class ColocarProdutoHandler {
	
	private Comerciante comerciante;
 
	public ColocarProdutoHandler(Comerciante currentComerciante) {
		this.comerciante = currentComerciante;
	}

	public List<String> inicioDeProdutosHoje() {
		return comerciante.getListaTP();
	}

	public void indicaProduto(String string, int i) throws CodigoNaoExisteException {
		comerciante.indicaProduto(string, i);
		
	}

	public void confirma(LocalDateTime now, LocalDateTime plusHours) {
		comerciante.confirma(now, plusHours);
	}

}
