package pt.utils.observer;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Reservation;

public abstract class Observable {
	
	protected Comerciante observer;
	
	public void addObserver(Comerciante c) {
		this.observer = c;
	}
	
	public void notifyObserver(Reservation reserva) {
		this.observer.update(reserva);
	}

}
