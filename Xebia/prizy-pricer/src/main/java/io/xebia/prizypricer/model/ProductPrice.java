package io.xebia.prizypricer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_prices")
public class ProductPrice implements Comparable<ProductPrice> {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="product_bar_code")
	private int productBarCode;
	
	@Column(name="store_id")
	private int storeId;
	
	@Column(name="price")
	private double price;
	
	@Column(name="notes")
	private String notes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductBarCode() {
		return productBarCode;
	}

	public void setProductBarCode(int productBarCode) {
		this.productBarCode = productBarCode;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public int compareTo(ProductPrice obj) {
		if (this.price > obj.price) {
			return 1;
		} else if (this.price < obj.price) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
