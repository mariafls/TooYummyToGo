package pt.tooyummytogo.facade.dto;

import java.time.LocalDateTime;

public class ProdutoInfo {
	
	private String nome;
	private LocalDateTime horaInicio;
	private LocalDateTime horaFim;
	 
	public ProdutoInfo(String nome, LocalDateTime horaInicio, LocalDateTime horaFim) {
		this.nome = nome;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
	}


	public String getNome() {
		return nome;
	}
	
	public String getCodigo() {
		return this.nome + this.horaInicio + this.horaFim;
	}
	
	public String toString() {
		return nome + " disponivel das " + horaInicio + " ate " + horaFim;
		
	}
	
}
