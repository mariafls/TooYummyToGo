package pt.tooyummytogo.pagamento;

public interface MetodoDePagamento {
	public boolean pagamento(String cartao, String validade, String ccv, Double total);
}
