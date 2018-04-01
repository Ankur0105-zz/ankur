package io.xebia.prizypricer.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import io.xebia.prizypricer.model.ProductPrice;

@Service
public class ProductPriceServiceImpl extends ProductPriceService {
	
	public Double getIdealPrice(int productBarCode) {
		List<ProductPrice> productPrices = productPriceReposiory.findByProductBarCode(productBarCode);
		Collections.sort(productPrices);
		if (productPrices.size() < 5) {
			return 0.0;
		} else {
			int size = productPrices.size();
			Double pricesTotal = productPrices.stream().map(productPrice -> productPrice.getPrice()).reduce((x, y) -> x+y).get();
			pricesTotal =  pricesTotal - productPrices.get(0).getPrice() - productPrices.get(1).getPrice() - 
					productPrices.get(size-1).getPrice() - productPrices.get(size-2).getPrice();
			return (pricesTotal/size)*1.20;
		}
	}
	
}
