package pt.tooyummytogo.domain;

public class NovoUtilizador {
	
	private String username;
	private String password;
	
	public NovoUtilizador(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getName() {
		return this.username;
	}
	
	public boolean hasPassword(String password2) {
		return this.password.contentEquals(password2);
	}

	public boolean hasUsername(String username2) {
		return this.username.contentEquals(username2);
	}
	
}
