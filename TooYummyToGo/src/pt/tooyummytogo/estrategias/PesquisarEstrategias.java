package pt.tooyummytogo.estrategias;

import java.util.List;

import pt.tooyummytogo.domain.Comerciante;

public interface PesquisarEstrategias {
	List<Comerciante> pesquisaComerciantes(List<Comerciante> listaComerciantes);
}
