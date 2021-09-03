package pt.tooyummytogo.domain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pt.tooyummytogo.pagamento.Configuration;
import pt.tooyummytogo.pagamento.MetodoDePagamento;

public class Utilizador extends NovoUtilizador {

	private Map<Produto, Integer> listaCompras;
	private ArrayList<Reserva> listaReservas = new ArrayList<>();

	public Utilizador(String username, String password) {
		super(username, password);
	}

	public void iniciaCompra() {
		this.listaCompras = new HashMap<>();
	}

	public void adicionaProduto(Produto produto, int quantidade) throws CloneNotSupportedException {
		listaCompras.put((Produto) produto.clone(), quantidade);
	}

	public Reserva criarReserva(Comerciante comercianteAtual, double total) {
		Reserva r = new Reserva(this, comercianteAtual, new HashMap<> (this.listaCompras), total);
		listaReservas.add(r);
		r.notifyObserver(r);
		return r;
	}

	public boolean pagamento(String cartao, String validade, String ccv) {
		boolean valida = false;
		double total = getTotalCompras();

		MetodoDePagamento m = Configuration.getInstance().getMetodoDePagamento();
		if(m.pagamento(cartao, validade, ccv, total)) {
			valida = true;
	}
		return valida;
	}

	public double getTotalCompras() {
		Double contador = 0.0;

		for (Entry<Produto, Integer> entry : listaCompras.entrySet()) {
			Double preco = entry.getKey().getPreco();
			int quantidade = entry.getValue();
			contador += preco*quantidade;
		}
		return contador;
	}

	public Map<Produto, Integer> getListaCompras() {
		return listaCompras;
	}
}
