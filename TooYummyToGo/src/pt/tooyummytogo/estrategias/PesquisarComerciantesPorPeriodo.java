package pt.tooyummytogo.estrategias;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class PesquisarComerciantesPorPeriodo implements PesquisarEstrategias {

	private PosicaoCoordenadas localizacao;
	private int raio;
	private LocalDateTime horaInicio;
	private LocalDateTime horaFim;

	public PesquisarComerciantesPorPeriodo(PosicaoCoordenadas localizacao, int raio, LocalDateTime horaInicio,
			LocalDateTime horaFim) {
		this.localizacao = localizacao;
		this.raio = raio;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
	}

	@Override
	public List<Comerciante> pesquisaComerciantes(List<Comerciante> listaComerciantes) {
		List<Comerciante>listaFiltrada = new ArrayList<>();
		for(Comerciante c: listaComerciantes) {
			if(c.estaEmRaio(localizacao, raio) && c.estaEmPeriodo(horaInicio, horaFim)) {
				listaFiltrada.add(c);
			}
		}
		return listaFiltrada;
	}

}
