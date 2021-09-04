package pt.tooyummytogo.facade.handlers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import pt.tooyummytogo.domain.CatComerciante;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Produto;
import pt.tooyummytogo.domain.Reserva;
import pt.tooyummytogo.domain.Utilizador;
import pt.tooyummytogo.exceptions.ListaVaziaException;
import pt.tooyummytogo.exceptions.PagamentoRecusadoException;
import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;

public class EncomendarHandler {

	private CatComerciante catComerciantes;
	private PosicaoCoordenadas localizacaoAtual;
	private List<Comerciante>listaComerciantesAtual;
	private LocalDateTime horaInicio;
	private LocalDateTime horaFim;
	private Comerciante comercianteAtual;
	private Utilizador utilizadorAtual;
	private static final int RAIO = 5;


	public EncomendarHandler(CatComerciante catComerciantes, Utilizador currentUtilizador) {
		this.catComerciantes = catComerciantes;
		this.utilizadorAtual = currentUtilizador;
	}

	/**
	 * Devolve lista de comerciantes dentro de um raio de 5km e com produtos disponiveis na proxima hora
	 * @param coordenadas
	 * @return
	 * @throws ListaVaziaException 
	 * @requires coordenadas != null
	 */
	public List<ComercianteInfo> indicaLocalizacaoActual(PosicaoCoordenadas coordenadas) throws ListaVaziaException {
		this.localizacaoAtual = coordenadas;

		return pesquisa(RAIO);
	}

	/**
	 * Devolve lista de comerciantes dentro de um raio definido pelo utilizador e usando a localizacao dada anteriormente
	 * e cujos comerciantes tenham produtos na proxima hora
	 * @param raio
	 * @return
	 * @throws ListaVaziaException
	 * @requires raio >= 0
	 */
	public List<ComercianteInfo> redefineRaio(int raio) throws ListaVaziaException {
		return pesquisa(raio);
	}

	/**
	 * Devolve a lista de comerciantes com produtos dentro do periodo dado pelo utilizador e dentro do raio default
	 */
	public List<ComercianteInfo> redefinePeriodo(LocalDateTime horaInicio, LocalDateTime horaFim) throws ListaVaziaException {
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		listaComerciantesAtual = catComerciantes.pesquisaComerciantesPorPeriodo(this.localizacaoAtual, RAIO, horaInicio, horaFim);
		if(listaComerciantesAtual.isEmpty()) {
			throw new ListaVaziaException();
		}
		return listaComerciantesAtual.stream().map(c -> new ComercianteInfo(c.getName())).collect(Collectors.toList());
	}


	/**
	 * Faz a pesquisa por localizacao e por raio
	 */
	private List<ComercianteInfo> pesquisa(int raio) throws ListaVaziaException{
		listaComerciantesAtual = catComerciantes.pesquisaComerciantesPorLocal(this.localizacaoAtual, raio);
		if(listaComerciantesAtual.isEmpty()) {
			throw new ListaVaziaException();
		}
		return listaComerciantesAtual.stream().map(c -> new ComercianteInfo(c.getName())).collect(Collectors.toList());
	}

	/**
	 * Devolve a lista de produtos do comerciante ou a lista de produtos do comerciante dentro do horario
	 * selecionado anteriormente se tiver sido dado
	 */
	public List<ProdutoInfo> escolheComerciante(ComercianteInfo comercianteInfo) {

		for(Comerciante c : listaComerciantesAtual) {
			if(c.getName().contentEquals(comercianteInfo.toString())) {
				this.comercianteAtual = c;
			}
		}
		this.utilizadorAtual.iniciaCompra();
		if(this.horaFim != null && this.horaInicio != null) {
			return this.comercianteAtual.getListaProdutosPeriodo(this.horaInicio, this.horaFim).stream().map(c -> new ProdutoInfo(c.getName(), c.getStartingTime(), c.getEndingTime())).collect(Collectors.toList());

		}
		return this.comercianteAtual.getListaProdutos().stream().map(c -> new ProdutoInfo(c.getName(), c.getStartingTime(), c.getEndingTime())).collect(Collectors.toList());
	}

	/**
	 * Adiciona produto a lista de compras e reduz a quantidade disponivel desse produto
	 */
	public void indicaProduto(ProdutoInfo p, int quantidade) throws QuantidadeIndisponivelException, CloneNotSupportedException {
		if(this.comercianteAtual.produtoDisponivel(p.getCodigo(), quantidade)) {
			Produto produto = this.comercianteAtual.getProduto(p.getCodigo());
			this.utilizadorAtual.adicionaProduto(produto, quantidade);
			this.comercianteAtual.reduzirQuantidades(produto, quantidade); 
		}

	}

	/**
	 * Valida o pagamento. Cria uma reserva e devolve o codigo da mesma, adicionando essa reserva a
	 * lista do utilizador. Reverte as quantidades disponiveis dos produtos
	 * comprados pelo utilizador se o pagamento nao processar
	 */
	public String indicaPagamento(String cartao, String validade, String cvv) throws PagamentoRecusadoException {
		String codigo = "";

		if(this.utilizadorAtual.pagamento(cartao, validade, cvv)) {
			Reserva r = this.utilizadorAtual.criarReserva(this.comercianteAtual, this.utilizadorAtual.getTotalCompras());
			codigo = r.getCodigo();
		} else {
			this.comercianteAtual.adicionarQuantidades(this.utilizadorAtual.getListaCompras());
			throw new PagamentoRecusadoException();
		}

		return codigo;
	}

}
