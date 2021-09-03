package pt.tooyummytogo.domain;

import java.time.LocalDateTime;

public class Produto implements Cloneable {
	 
	private LocalDateTime horaInicio;
	private LocalDateTime horaFim;
	private int quantidade;
	private TipoDeProduto tp;
	
	public Produto(TipoDeProduto tp, int quantidade) {
		this.quantidade = quantidade;
		this.tp = tp;
	}
	
	public String getCodigo() {
		return this.tp.getNome() + this.horaInicio + this.horaFim;
	}
	
	public int getQuantidade() {
		return this.quantidade;
	}
	
	/**
	 * Devolve a hora de inicio da recolha
	 * @return {@code horaInicio}
	 */
	public LocalDateTime getHoraInicio() {
		return this.horaInicio;
	}
	
	public LocalDateTime getHoraFim() {
		return this.horaFim;
	}

	public void setHoraInicio(LocalDateTime now) {
		this.horaInicio = now;
	}

	public void setHoraFim(LocalDateTime plusHours) {
		this.horaFim = plusHours;
		
	}
	
	public String getNome() {
		return this.tp.getNome();
	}

	public double getPreco() {
		return tp.getPreco();
	}

	public void diminuiQuantidade(int quantidade) {
		this.quantidade -= quantidade;
	}
	
	public void devolveQuantidade(int quantidade) {
		this.quantidade = quantidade;	
	}

	/**
	 * Verifica se o produto esta disponivel dentro do horario dado
	 * @param horaInicio
	 * @param horaFim
	 * @return
	 */
	public boolean verificaPeriodo(LocalDateTime horaInicio, LocalDateTime horaFim) {
		return this.horaInicio.isEqual(horaInicio) && this.horaFim.isEqual(horaFim) || estaContido(horaInicio, horaFim) || sobrepoe(horaInicio, horaFim);
		
	}
	
	private boolean estaContido(LocalDateTime hI, LocalDateTime hF) {
		return hI.isAfter(this.horaInicio) && (this.horaFim.isAfter(hF) || this.horaFim.isAfter(hI));
	}

	private boolean sobrepoe(LocalDateTime hI, LocalDateTime hF) {
		return this.horaInicio.isAfter(hI) && (hF.isAfter(this.horaFim) || hF.isAfter(this.horaInicio));
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Produto)) {
			return false;
		}
		Produto other = (Produto) obj;
		return horaInicio == other.horaInicio && horaInicio == other.horaFim && tp.equals(other.tp);
	}
}
