package pt.tooyummytogo.domain;

public class TypeOfProduct {
	 
	private String name;
	private double price;

	public TypeOfProduct(String tp, double d) {
		this.name = tp;
		this.price = d;
	}
	
	public TypeOfProduct(String tp) {
		this.name = tp;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TypeOfProduct)) {
			return false;
		}
		TypeOfProduct other = (TypeOfProduct) obj;
		return name == other.name && price == other.price;
	}

}
