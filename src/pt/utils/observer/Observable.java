package pt.utils.observer;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Reserva;

public abstract class Observable {
	
	protected Comerciante observer;
	
	public void addObserver(Comerciante c) {
		this.observer = c;
	}
	
	public void notifyObserver(Reserva reserva) {
		this.observer.update(reserva);
	}

}
