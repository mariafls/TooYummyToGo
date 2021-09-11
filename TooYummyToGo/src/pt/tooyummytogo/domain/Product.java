package pt.tooyummytogo.domain;

import java.time.LocalDateTime;

public class Product implements Cloneable {
	 
	private LocalDateTime startingTime;
	private LocalDateTime endingTime;
	private int quantity;
	private TypeOfProduct tp;
	
	public Product(TypeOfProduct tp, int quantity) {
		this.quantity = quantity;
		this.tp = tp;
	}
	
	public String getCode() {
		return this.tp.getName() + this.startingTime + this.endingTime;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Devolve a hora de inicio da recolha
	 * @return {@code horaInicio}
	 */
	public LocalDateTime getStartingTime() {
		return this.startingTime;
	}
	
	public LocalDateTime getEndingTime() {
		return this.endingTime;
	}

	public void setStartingTime(LocalDateTime now) {
		this.startingTime = now;
	}

	public void setEndingTime(LocalDateTime plusHours) {
		this.endingTime = plusHours;
		
	}
	
	public String getName() {
		return this.tp.getName();
	}

	public double getPrice() {
		return tp.getPrice();
	}

	public void decreaseQuantity(int quantity) {
		this.quantity -= quantity;
	}
	
	public void devolveQuantidade(int quantidade) {
		this.quantity = quantidade;	
	}

	/**
	 * Verifica se o produto esta disponivel dentro do horario dado
	 * @param horaInicio
	 * @param horaFim
	 * @return
	 */
	public boolean verificaPeriodo(LocalDateTime horaInicio, LocalDateTime horaFim) {
		return this.startingTime.isEqual(horaInicio) && this.endingTime.isEqual(horaFim) || estaContido(horaInicio, horaFim) || overlaps(horaInicio, horaFim);
		
	}
	
	private boolean estaContido(LocalDateTime hI, LocalDateTime hF) {
		return hI.isAfter(this.startingTime) && (this.endingTime.isAfter(hF) || this.endingTime.isAfter(hI));
	}

	private boolean overlaps(LocalDateTime hI, LocalDateTime hF) {
		return this.startingTime.isAfter(hI) && (hF.isAfter(this.endingTime) || hF.isAfter(this.startingTime));
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		return startingTime == other.startingTime && startingTime == other.endingTime && tp.equals(other.tp);
	}
}
