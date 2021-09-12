package pt.tooyummytogo.domain;

public class NewUser {
	
	private String username;
	private String password;
	
	public NewUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getName() {
		return this.username;
	}
	
	public boolean hasPassword(String password) {
		return this.password.contentEquals(password);
	}

	public boolean hasUsername(String username) {
		return this.username.contentEquals(username);
	}
	
}
