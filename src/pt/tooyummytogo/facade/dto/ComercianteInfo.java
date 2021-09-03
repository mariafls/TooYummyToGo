package pt.tooyummytogo.facade.dto;

public class ComercianteInfo {
	 
	private String name;
	
	public ComercianteInfo(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
