package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pt.utils.observer.Observable;

public class Reservation extends Observable {
	 
	private String code;
	private User user;
	private String state;
	private Map<Product, Integer> shoppingList;
	private double total;
	
	public Reservation(User user, Seller currentSeller, double total) {
		super.addObserver(currentSeller);
		this.user = user;
		this.code = this.user.getName() + currentSeller.getName() + LocalDateTime.now().toString();
		this.state = "pendente";
		this.shoppingList = null;
		this.total = total;
	}
	
	public void setShoppingList(Map<Product, Integer> shoppingList) {
		this.shoppingList = shoppingList;
	}

	public String getCodigo() {
		return this.code;
	}
	
	public String getEstado() {
		return this.state;
	}
	
	public void setEstado(String codigo) {
		if(this.code.contentEquals(codigo)){
			this.state = "concluido";
		}
	}
		
	public Map<Product, Integer> devolveLista(){
		return new HashMap<>(shoppingList);
	}
	
	public double getTotal() {
		return this.total;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Encomenda: " + this.getCodigo() + "\n");
		sb.append("Cliente: " + this.user.getName() + "\nComerciante: " + super.observer.getName() +  "\n");
		for (Entry<Product, Integer> entry : shoppingList.entrySet()) {
			sb.append(entry.getKey().getName() + " " + entry.getValue().toString() + "\n");
		}
		sb.append("Estado: " + this.state + "\n");
		return sb.toString();
	}

}
