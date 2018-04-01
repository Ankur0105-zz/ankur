package io.xebia.prizypricer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.xebia.prizypricer.constants.PrizyPricerConstants;
import io.xebia.prizypricer.model.Product;
import io.xebia.prizypricer.model.Response;
import io.xebia.prizypricer.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Response getAllProducts() {
		return new Response(PrizyPricerConstants.SUCCESS, productRepository.findAll());
	}
	
	public Optional<Product> fetchProduct(int productId) {
		 return productRepository.findById(productId);
	}
	
	public Response getProductDetails(int productId) {
		Optional<Product> productOptional = fetchProduct(productId);
		if (productOptional.isPresent()) {
			return new Response(PrizyPricerConstants.SUCCESS, productOptional.get());
		}
		return new Response(PrizyPricerConstants.NOT_FOUND, PrizyPricerConstants.NO_PRODUCT_ERROR_MSG);
	}
	
	public Response insertProduct(Product product) {
		productRepository.save(product);
		return new Response(PrizyPricerConstants.SUCCESS, PrizyPricerConstants.PRODUCT_SUCCESS);
	}
	
	public Response deleteProduct(int productId) {
		if (productExists(productId)) {
			productRepository.deleteById(productId);
			return new Response(PrizyPricerConstants.SUCCESS, PrizyPricerConstants.PRODUCT_DELETED);
		}
		return new Response(PrizyPricerConstants.NOT_FOUND, PrizyPricerConstants.NO_PRODUCT_ERROR_MSG);
	}
	
	public boolean productExists(int productId) {
		return productRepository.existsById(productId);
	}
	
	public void updateProduct(int productId, double price, double idealPrice) {
		Product product = fetchProduct(productId).get();
		if (product.getMaxPrice() < price) {
			product.setMaxPrice(price);
		}
		if (product.getMinPrice() == 0 || product.getMinPrice() > price) {
			product.setMinPrice(price);
		}
		int noOfPrices = product.getNoOfPrices();
		double newAvgPrice = ((product.getAvgPrice() * noOfPrices) + price)/(noOfPrices+1);
		product.setAvgPrice(newAvgPrice);
		product.setNoOfPrices(noOfPrices + 1);
		product.setIdealPrice(idealPrice);
		productRepository.save(product);
	}
}
