package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pt.utils.observer.Observable;

public class Reserva extends Observable {
	 
	private String codigo;
	private Utilizador utilizador;
	private String estado;
	private Map<Produto, Integer> listaCompras;
	private double total;
	
	public Reserva(Utilizador utilizador, Comerciante comercianteAtual, Map<Produto, Integer> listaCompras, double total) {
		super.addObserver(comercianteAtual);
		this.utilizador = utilizador;
		this.codigo = this.utilizador.getName() + comercianteAtual.getName() + LocalDateTime.now().toString();
		this.estado = "pendente";
		this.listaCompras = listaCompras;
		this.total = total;
	}

	public String getCodigo() {
		return this.codigo;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public void setEstado(String codigo) {
		if(this.codigo.contentEquals(codigo)){
			this.estado = "concluido";
		}
	}
		
	public Map<Produto, Integer> devolveLista(){
		return new HashMap<>(listaCompras);
	}
	
	public double getTotal() {
		return this.total;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Encomenda: " + this.getCodigo() + "\n");
		sb.append("Cliente: " + this.utilizador.getName() + "\nComerciante: " + super.observer.getName() +  "\n");
		for (Entry<Produto, Integer> entry : listaCompras.entrySet()) {
			sb.append(entry.getKey().getNome() + " " + entry.getValue().toString() + "\n");
		}
		sb.append("Estado: " + this.estado + "\n");
		return sb.toString();
	}

}
