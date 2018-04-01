package io.xebia.prizypricer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
	
	@Id
	@Column(name="product_bar_code")
	private Integer productBarCode;
	
	@Column(name="description")
	private String description;
	
	@Column(name="avg_price")
	private Double avgPrice = 0.0;
	
	@Column(name="max_price")
	private Double maxPrice = 0.0;
	
	@Column(name="min_price")
	private Double minPrice = 0.0;
	
	@Column(name="ideal_price")
	private Double idealPrice = 0.0;
	
	@Column(name="no_of_prices")
	private Integer noOfPrices = 0;
	
	public Product() {}
	
	public Product(int productBarCode, String description) {
		this.productBarCode = productBarCode;
		this.description = description;
	}
	
	public Integer getProductBarCode() {
		return productBarCode;
	}
	
	public void setProductBarCode(Integer productBarCode) {
		this.productBarCode = productBarCode;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getAvgPrice() {
		return avgPrice;
	}
	
	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}
	
	public Double getMaxPrice() {
		return maxPrice;
	}
	
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	public Double getMinPrice() {
		return minPrice;
	}
	
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	
	public Double getIdealPrice() {
		return idealPrice;
	}
	public void setIdealPrice(Double idealPrice) {
		this.idealPrice = idealPrice;
	}
	
	public Integer getNoOfPrices() {
		return noOfPrices;
	}
	
	public void setNoOfPrices(Integer noOfPrices) {
		this.noOfPrices = noOfPrices;
	}
	
}
