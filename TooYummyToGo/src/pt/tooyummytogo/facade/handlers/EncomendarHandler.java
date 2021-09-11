package pt.tooyummytogo.facade.handlers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import pt.tooyummytogo.domain.CatComerciante;
import pt.tooyummytogo.domain.Product;
import pt.tooyummytogo.domain.Reservation;
import pt.tooyummytogo.domain.Seller;
import pt.tooyummytogo.domain.User;
import pt.tooyummytogo.exception.QuantityNotAvailableException;
import pt.tooyummytogo.exceptions.ListaVaziaException;
import pt.tooyummytogo.exceptions.PagamentoRecusadoException;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.CoordinatesPosition;
import pt.tooyummytogo.facade.dto.ProdutoInfo;

public class EncomendarHandler {

	private CatComerciante catComerciantes;
	private CoordinatesPosition localizacaoAtual;
	private List<Seller>listaComerciantesAtual;
	private LocalDateTime horaInicio;
	private LocalDateTime horaFim;
	private Seller currentSeller;
	private User utilizadorAtual;
	private static final int RAIO = 5;


	public EncomendarHandler(CatComerciante catComerciantes, User currentUtilizador) {
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
	public List<ComercianteInfo> indicaLocalizacaoActual(CoordinatesPosition coordenadas) throws ListaVaziaException {
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

		for(Seller c : listaComerciantesAtual) {
			if(c.getName().contentEquals(comercianteInfo.toString())) {
				this.currentSeller = c;
			}
		}
		this.utilizadorAtual.beginPurchase();
		if(this.horaFim != null && this.horaInicio != null) {
			return this.currentSeller.getProductListInPeriod(this.horaInicio, this.horaFim).stream().map(c -> new ProdutoInfo(c.getName(), c.getStartingTime(), c.getEndingTime())).collect(Collectors.toList());

		}
		return this.currentSeller.getProductsList().stream().map(c -> new ProdutoInfo(c.getName(), c.getStartingTime(), c.getEndingTime())).collect(Collectors.toList());
	}

	/**
	 * Adiciona produto a lista de compras e reduz a quantidade disponivel desse produto
	 * @throws QuantityNotAvailableException 
	 */
	public void indicaProduto(ProdutoInfo p, int quantidade) throws CloneNotSupportedException, QuantityNotAvailableException {
		if(this.currentSeller.productAvailable(p.getCodigo(), quantidade)) {
			Product produto = this.currentSeller.getProduct(p.getCodigo());
			this.utilizadorAtual.addProduct(produto, quantidade);
			this.currentSeller.decreaseQuantity(produto, quantidade); 
		}

	}

	/**
	 * Valida o pagamento. Cria uma reserva e devolve o codigo da mesma, adicionando essa reserva a
	 * lista do utilizador. Reverte as quantidades disponiveis dos produtos
	 * comprados pelo utilizador se o pagamento nao processar
	 */
	public String indicaPagamento(String cartao, String validade, String cvv) throws PagamentoRecusadoException {
		String codigo = "";

		if(this.utilizadorAtual.payment(cartao, validade, cvv)) {
			Reservation r = new Reservation(this.utilizadorAtual, this.currentSeller, this.utilizadorAtual.getTotalCompras());
			this.utilizadorAtual.criarReserva(r);
			codigo = r.getCodigo();
		} else {
			this.currentSeller.addQuantity(this.utilizadorAtual.getListaCompras());
			throw new PagamentoRecusadoException();
		}

		return codigo;
	}

}
