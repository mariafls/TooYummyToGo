package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import pt.tooyummytogo.exceptions.CodigoNaoExisteException;
import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;
import pt.tooyummytogo.exceptions.TipoDeProdutoJaExisteException;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.utils.observer.Observer;

public class Comerciante extends NovoUtilizador implements Observer {

	private PosicaoCoordenadas localizacaoComerciante;
	private List<TypeOfProduct> ltp = new ArrayList<>(); //lista tipos de produto
	private List<Reserva> listaReservasC = new ArrayList<>();
	private List<Produto> lp = new ArrayList<>();

	public Comerciante(String username, String password, PosicaoCoordenadas localizacaoComerciante) {
		super(username, password);
		this.localizacaoComerciante = localizacaoComerciante; 
	}

	/**
	 * Adiciona um novo tipo de produto que o comerciante pode vender
	 * @param nome - nome do tipo de produto
	 * @param preco - preco do tipo de produto
	 * @throws TipoDeProdutoJaExisteException
	 */
	public void adicionarTipoProduto(String nome, double preco) throws TipoDeProdutoJaExisteException {
		for(TypeOfProduct tipop : ltp) {
			if(tipop.getName().contentEquals(nome)) {
				throw new TipoDeProdutoJaExisteException();
			}
		}

		TypeOfProduct tipoProduto = new TypeOfProduct(nome, preco);
		ltp.add(tipoProduto);

	}

	public List<String> getListaTP() {
		return ltp.stream().map(u -> u.getName()).collect(Collectors.toList());
	}


	/**
	 * Cria um produto se existir o seu tipo de produto correspondente
	 * @param nome
	 * @param quantidade
	 * @throws CodigoNaoExisteException
	 */
	public void indicaProduto(String nome, int quantidade) throws CodigoNaoExisteException {
		boolean verifica = false;
		for(TypeOfProduct tp : ltp) {
			if(tp.getName().contentEquals(nome)) {
				Produto produto = new Produto(tp, quantidade);
				lp.add(produto);
				verifica = true;
			}
		}

		if(!verifica) {
			throw new CodigoNaoExisteException();
		}
	}

	/**
	 * Coloca as horas nos produtos que foram disponibilizados pela ultima vez
	 * @param horaInicio
	 * @param horaFim
	 */
	public void confirma(LocalDateTime horaInicio, LocalDateTime horaFim) {
		for(Produto p : lp) {
			if(p.getStartingTime() == null && p.getEndingTime() == null) {
				p.setStartingTime(horaInicio);
				p.setEndingTime(horaFim);
			}
		}
	}

	/**
	 * Verifica se o comerciante esta a um raio {@code raio} do utilizador
	 * @param localizacaoUtilizador
	 * @param raio
	 * @return
	 */
	public boolean estaEmRaio(PosicaoCoordenadas localizacaoUtilizador, int raio) {

		double distancia = localizacaoComerciante.distanciaEmMetros(localizacaoUtilizador);
		return distancia <= raio*1000;
	}


	/**
	 * Verifica se o comerciante tem produtos no periodo de tempo selecionado
	 * @param horaInicio
	 * @param horaFim
	 * @return
	 */
	public boolean estaEmPeriodo(LocalDateTime horaInicio, LocalDateTime horaFim) {
		for(Produto p: lp) {
			if(p.verificaPeriodo(horaInicio, horaFim)) {
				return true;	
			}
		}
		return false;
	}

	public List<Produto> getListaProdutos() {
		return new ArrayList<>(lp);
	}

	/**
	 * Verifica se o produto esta disponivel. Se nao estiver, lanca excepcao a indicar e com a
	 * quantidade maxima disponivel desse produto a qualquer hora.
	 * @param nome
	 * @param quantidade
	 * @return
	 * @throws QuantidadeIndisponivelException
	 */
	public boolean produtoDisponivel(String nome, int quantidade) throws QuantidadeIndisponivelException {
		int quant = 0;
		boolean verifica = false;
		for(Produto p : lp) {
			if(p.getCodigo().equals(nome)) {
				verifica = p.getQuantity() >= quantidade;
				if(!verifica && quant < p.getQuantity()) {
					quant = p.getQuantity();
				}
			}
		}
		if(!verifica) {
			throw new QuantidadeIndisponivelException(quant, nome);
		}
		return verifica;	
	}
	/**
	 * 
	 * @param nome
	 * @return
	 * @requires produto existe
	 */
	public Produto getProduto(String nome) {
		return  lp.stream()
				.filter(p -> p.getCodigo().contentEquals(nome))
				.reduce((a, b) -> {
					throw new IllegalStateException("Multiple elements: " + a + ", " + b);
				})
				.get();
	}

	public void adicionaReserva(Reserva r) {
		listaReservasC.add(r);
	}

	/**
	 * Devolve uma lista de produtos que estao disponiveis dentro do horario pretendido
	 * @param horaInicio
	 * @param horaFim
	 * @return
	 */
	public List<Produto> getListaProdutosPeriodo(LocalDateTime horaInicio, LocalDateTime horaFim) {

		ArrayList<Produto> listaProdutosPeriodo = new ArrayList<>();
		for(Produto p: lp) {
			if(p.verificaPeriodo(horaInicio,horaFim)) {
				listaProdutosPeriodo.add(p);
			}
		}
		return listaProdutosPeriodo;
	}

	public void update(Reserva reserva) {
		this.adicionaReserva(reserva);
		System.out.println(reserva.toString());
	}

	/**
	 * Reduz as quantidades disponiveis dos produtos adquiridos na ultima compra
	 */
	public void reduzirQuantidades(Produto produto, int quantidade) {
		produto.decreaseQuantity(quantidade);
		verificaQuantidade(produto);
	}

	private void verificaQuantidade(Produto produto) {
		List<Produto> lista = this.lp;
		if(produto.getQuantity() == 0) {
			for(int i = 0; i < lista.size(); i++) {
				if(produto.equals(lista.get(i))) {
					this.lp.remove(i);
				}
			}
		}
	}

	public void adicionarQuantidades(Map<Produto, Integer> map) {
		for(Produto p : lp) {
			for(Entry<Produto, Integer> pr : map.entrySet()) {
				if(p.equals(pr.getKey())) {
					p.devolveQuantidade(pr.getKey().getQuantity());
				}
			}
		}
	}
}
