package pt.tooyummytogo.domain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pt.tooyummytogo.pagamento.Configuration;
import pt.tooyummytogo.pagamento.MetodoDePagamento;

public class User extends NovoUtilizador {

	private Map<Product, Integer> shoppingList;
	private ArrayList<Reservation> reservationList = new ArrayList<>();

	public User(String username, String password) {
		super(username, password);
	}

	public void beginPurchase() {
		this.shoppingList = new HashMap<>();
	}

	public void addProduct(Product product, int quantidade) throws CloneNotSupportedException {
		shoppingList.put((Product) product.clone(), quantidade);
	}

	public Reservation criarReserva(Comerciante comercianteAtual, double total) {
		Reservation r = new Reservation(this, comercianteAtual, new HashMap<> (this.shoppingList), total);
		reservationList.add(r);
		r.notifyObserver(r);
		return r;
	}

	public boolean payment(String cartao, String validade, String ccv) {
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

		for (Entry<Product, Integer> entry : shoppingList.entrySet()) {
			Double preco = entry.getKey().getPrice();
			int quantidade = entry.getValue();
			contador += preco*quantidade;
		}
		return contador;
	}

	public Map<Product, Integer> getListaCompras() {
		return shoppingList;
	}
}
