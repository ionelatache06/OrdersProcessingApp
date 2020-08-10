package com.ionela.sacomdemo.model;
import java.time.LocalDateTime;
import java.util.List;

public class Order {

	private Long id;
	private LocalDateTime created;
	private List<Product> products;

	public Order() {
		super();
	}

	public Order(LocalDateTime created, List<Product> products, Long id) {
		super();
		this.id = id;
		this.created = created;
		this.products = products;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> product) {
		this.products = product;
	}

	public String toString() {
		return "Order [created=" + created + ", product=" + products + "]";
	}

}
