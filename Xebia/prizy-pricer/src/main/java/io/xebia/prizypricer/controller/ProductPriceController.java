package io.xebia.prizypricer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.xebia.prizypricer.model.ProductPrice;
import io.xebia.prizypricer.model.Response;
import io.xebia.prizypricer.service.ProductPriceService;

@RestController
@RequestMapping(path="/prizy/productPrice")
public class ProductPriceController {

	@Autowired
	private ProductPriceService productPriceService;
	
	@PostMapping(path="/insert")
	public Response insertPrice(@RequestBody ProductPrice productPrice) {
		return productPriceService.insertPrice(productPrice);
	}
	
}
