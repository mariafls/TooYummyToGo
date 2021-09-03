package pt.tooyummytogo.exceptions;

public class QuantidadeIndisponivelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int quantidade;
	private String nome;

	public QuantidadeIndisponivelException(int quantidade, String nome) {
		this.quantidade = quantidade;
		this.nome = nome;
	}

	public String getProduto() {
		return this.nome;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

}
