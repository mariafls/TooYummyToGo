package pt.tooyummytogo.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pt.tooyummytogo.exceptions.UtilizadorJaExisteException;

public class CatUtilizador {
	
	private List<Utilizador> listaUtilizadores;
	
	public CatUtilizador() {
		 this.listaUtilizadores = new ArrayList<>();
	}

	/**
	 * Regista um utilizador normal.
	 * @param Username
	 * @param Password
	 * @throws UtilizadorJaExisteException 
	 * @ensures existe um utilizador com esse username
	 */
	public void registaUtilizador(String username, String password) throws UtilizadorJaExisteException {
		for(Utilizador u : listaUtilizadores) {
			if(u.getName().contentEquals(username)) {
				throw new UtilizadorJaExisteException();
			}
		}
		Utilizador utilizador = new Utilizador(username, password);
		listaUtilizadores.add(utilizador);	
	}
	
	public Optional<Utilizador> tryLogin(String username, String pw) {
		return Optional.ofNullable(listaUtilizadores.stream().filter(u -> (u.hasPassword(pw) && u.hasUsername(username))).findAny().orElse(null));
	}
	
	public boolean utilizadorExiste(String username, String password) {
		for(Utilizador u : listaUtilizadores) {
			if(u.getName().contentEquals(username)) {
				return true;
			}
		}
		return false;
	}

}
