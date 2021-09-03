package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pt.tooyummytogo.estrategias.PesquisarComerciantesPorLocalizacao;
import pt.tooyummytogo.estrategias.PesquisarComerciantesPorPeriodo;
import pt.tooyummytogo.exceptions.ComercianteJaExisteException;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class CatComerciante{
	
	private ArrayList<Comerciante> listaComerciantes;
	
	public CatComerciante() {
		 this.listaComerciantes = new ArrayList<>();
	}


	public void registaComerciante(String username, String password, PosicaoCoordenadas localizacaoComerciante) throws ComercianteJaExisteException {
		for(Comerciante c : listaComerciantes) {
			if(c.getName().contentEquals(username)) {
				throw new ComercianteJaExisteException();
			}
		}
		Comerciante comerciante = new Comerciante(username, password, localizacaoComerciante);
		listaComerciantes.add(comerciante);
	}
	
	public Optional<Comerciante> tryLogin(String username, String password) {
		return Optional.ofNullable(listaComerciantes.stream().filter(u -> (u.hasPassword(password) && u.hasUsername(username))).findAny().orElse(null));
	}
	
	public boolean comercianteExiste(String username) {
		for(Comerciante c : listaComerciantes) {
			if(c.getName().contentEquals(username)) {
				return true;
			}
		}
		return false;
	}

	public List<Comerciante> getListaComerciantes() {
		return new ArrayList<Comerciante> (listaComerciantes);
	}


	public List<Comerciante> pesquisaComerciantesPorLocal(PosicaoCoordenadas localizacao, int raio) {
		PesquisarComerciantesPorLocalizacao pesquisar = new PesquisarComerciantesPorLocalizacao(localizacao, raio);
		return pesquisar.pesquisaComerciantes(getListaComerciantes());
	}


	public List<Comerciante> pesquisaComerciantesPorPeriodo(PosicaoCoordenadas localizacao, int raio,
			LocalDateTime horaInicio, LocalDateTime horaFim) {
		PesquisarComerciantesPorPeriodo pesquisar = new PesquisarComerciantesPorPeriodo(localizacao, raio, horaInicio, horaFim);
		return pesquisar.pesquisaComerciantes(getListaComerciantes());
	}

	
}

