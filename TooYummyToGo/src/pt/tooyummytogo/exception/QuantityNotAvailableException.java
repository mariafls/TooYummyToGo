package pt.tooyummytogo.exception;

public class QuantityNotAvailableException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int quantity;
	private String name;

	public QuantityNotAvailableException(int quantity, String name) {
		this.quantity = quantity;
		this.name = name;
	}

	public String getProduct() {
		return this.name;
	}

	public int getQuantity() {
		return this.quantity;
	}

}
