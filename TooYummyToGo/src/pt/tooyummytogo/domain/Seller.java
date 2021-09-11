package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import pt.tooyummytogo.exception.CodeDoesNotExistException;
import pt.tooyummytogo.exception.QuantityNotAvailableException;
import pt.tooyummytogo.exceptions.ProductTypeAlreadyExistsException;
import pt.tooyummytogo.facade.dto.CoordinatesPosition;
import pt.utils.observer.Observer;

public class Seller extends NewUser implements Observer {

	private CoordinatesPosition sellerLocation;
	private List<TypeOfProduct> productTypesList = new ArrayList<>();
	private List<Reservation> sellerReservations = new ArrayList<>();
	private List<Product> productsList = new ArrayList<>();

	public Seller(String username, String password, CoordinatesPosition sellerLocation) {
		super(username, password);
		this.sellerLocation = sellerLocation; 
	}

	/**
	 * Adds a new type of product that the seller can sell
	 * @param name - product type name
	 * @param price - product type price
	 * @throws TipoDeProdutoJaExisteException //ProductTypeAlreadyExistsException
	 */
	public void addProductType(String name, double price) throws ProductTypeAlreadyExistsException {
		for(TypeOfProduct productType : productTypesList) {
			if(productType.getName().contentEquals(name)) {
				throw new ProductTypeAlreadyExistsException();
			}
		}

		TypeOfProduct productType = new TypeOfProduct(name, price);
		productTypesList.add(productType);

	}

	public List<String> getListProductTypes() {
		return productTypesList.stream().map(u -> u.getName()).collect(Collectors.toList());
	}


	/**
	 * Creates a product if it has a valid type
	 * @param name
	 * @param quantity
	 * @throws CodigoNaoExisteException //CodeDoesNotExistException
	 */
	public void newProduct(String name, int quantity) throws CodeDoesNotExistException {
		boolean check = false;
		for(TypeOfProduct productType : productTypesList) {
			if(productType.getName().contentEquals(name)) {
				Product product = new Product(productType, quantity);
				productsList.add(product);
				check = true;
			}
		}

		if(!check) {
			throw new CodeDoesNotExistException();
		}
	}

	/**
	 * Set the time on products that where last made available  
	 * @param startTime
	 * @param endTime
	 */
	public void setTime(LocalDateTime startTime, LocalDateTime endTime) {
		for(Product p : productsList) {
			if(p.getStartingTime() == null && p.getEndingTime() == null) {
				p.setStartingTime(startTime);
				p.setEndingTime(endTime);
			}
		}
	}

	/**
	 * Checks if the seller is in the radius of the user
	 * @param userLocation
	 * @param radius
	 * @return
	 */
	public boolean isInUserRadius(CoordinatesPosition userLocation, int radius) {

		double distance = sellerLocation.distanceInMeters(userLocation);
		return distance <= radius*1000;
	}


	/**
	 * Checks if the seller has products in the selected period
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean isInPeriod(LocalDateTime startTime, LocalDateTime endTime) {
		for(Product p: productsList) {
			if(p.verificaPeriodo(startTime, endTime)) {
				return true;	
			}
		}
		return false;
	}

	public List<Product> getProductsList() {
		return new ArrayList<>(productsList);
	}

	/**
	 * Checks if product is available. If not, throws an exception with the max quantity available at the time
	 * @param name
	 * @param quantity
	 * @return
	 * @throws QuantityNotAvailableException
	 */
	public boolean productAvailable(String name, int quantity) throws QuantityNotAvailableException {
		int quant = 0;
		boolean check = false;
		for(Product p : productsList) {
			if(p.getCode().equals(name)) {
				check = p.getQuantity() >= quantity;
				if(!check && quant < p.getQuantity()) {
					quant = p.getQuantity();
				}
			}
		}
		if(!check) {
			throw new QuantityNotAvailableException(quant, name);
		}
		return check;	
	}
	/**
	 * 
	 * @param name
	 * @return
	 * @requires product exists
	 */
	public Product getProduct(String name) {
		return  productsList.stream()
				.filter(p -> p.getCode().contentEquals(name))
				.reduce((a, b) -> {
					throw new IllegalStateException("Multiple elements: " + a + ", " + b);
				})
				.get();
	}

	public void addReservation(Reservation r) {
		sellerReservations.add(r);
	}

	/**
	 * Returns a list of products available in the intended time
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Product> getProductListInPeriod(LocalDateTime startTime, LocalDateTime endTime) {

		ArrayList<Product> productListInPeriod = new ArrayList<>();
		for(Product p: productsList) {
			if(p.verificaPeriodo(startTime,endTime)) {
				productListInPeriod.add(p);
			}
		}
		return productListInPeriod;
	}

	public void update(Reservation reservation) {
		this.addReservation(reservation);
		System.out.println(reservation.toString());
	}

	/**
	 * Reduce the amount of available products purchased 
	 */
	public void decreaseQuantity(Product product, int quantity) {
		product.decreaseQuantity(quantity);
		checkQuantity(product);
	}

	private void checkQuantity(Product product) {
		List<Product> productList = this.productsList;
		if(product.getQuantity() == 0) {
			for(int i = 0; i < productList.size(); i++) {
				if(product.equals(productList.get(i))) {
					this.productsList.remove(i);
				}
			}
		}
	}

	public void addQuantity(Map<Product, Integer> map) {
		for(Product p : productsList) {
			for(Entry<Product, Integer> pr : map.entrySet()) {
				if(p.equals(pr.getKey())) {
					p.devolveQuantidade(pr.getKey().getQuantity());
				}
			}
		}
	}

}
