package pt.utils.observer;

import pt.tooyummytogo.domain.Seller;
import pt.tooyummytogo.domain.Reservation;

public abstract class Observable {
	
	protected Seller observer;
	
	public void addObserver(Seller c) {
		this.observer = c;
	}
	
	public void notifyObserver(Reservation reserva) {
		this.observer.update(reserva);
	}

}
