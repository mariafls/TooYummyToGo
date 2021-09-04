package pt.tooyummytogo.domain;

public class TipoDeProduto {
	 
	private String nome;
	private double preco;

	public TipoDeProduto(String tp, double d) {
		this.nome = tp;
		this.preco = d;
	}
	
	public TipoDeProduto(String tp) {
		this.nome = tp;
	}
	
	public double getPreco() {
		return preco;
	}
	
	public String getNome() {
		return nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TipoDeProduto)) {
			return false;
		}
		TipoDeProduto other = (TipoDeProduto) obj;
		return nome == other.nome && preco == other.preco;
	}

}
