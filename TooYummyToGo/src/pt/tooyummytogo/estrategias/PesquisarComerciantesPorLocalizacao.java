package pt.tooyummytogo.estrategias;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class PesquisarComerciantesPorLocalizacao implements PesquisarEstrategias{

	private PosicaoCoordenadas localizacao;
	private int raio;

	public PesquisarComerciantesPorLocalizacao(PosicaoCoordenadas coordenadas, int raio) {
		this.localizacao = coordenadas;
		this.raio = raio;
	}

	@Override
	public List<Comerciante> pesquisaComerciantes(List<Comerciante> listaComerciantes) {
		List<Comerciante>listaFiltrada = new ArrayList<>();
		for(Comerciante c: listaComerciantes) {
			if(c.estaEmRaio(localizacao, raio) && c.estaEmPeriodo(LocalDateTime.now(), LocalDateTime.now().plusHours(1))) {
				listaFiltrada.add(c);
			}
		}
		return listaFiltrada;
	}


}
