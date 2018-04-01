package io.xebia.prizypricer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.xebia.prizypricer.model.Product;
import io.xebia.prizypricer.model.Response;
import io.xebia.prizypricer.service.ProductService;

@RestController
@RequestMapping(path="/prizy/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(path="/all")
	public Response getAllProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping(path="/{productId}")
	public Response getProduct(@PathVariable("productId") int productId) {
		return productService.getProductDetails(productId);
	}
	
	@PostMapping(path="/update")
	public Response insertProduct(@RequestBody Product product) {
		return productService.insertProduct(product);
	}
	
	@GetMapping(path="/delete/{productId}")
	public Response deleteProduct(@PathVariable("productId") int productId) {
		return productService.deleteProduct(productId);
	}

}
