package io.xebia.prizypricer.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import io.xebia.prizypricer.constants.PrizyPricerConstants;
import io.xebia.prizypricer.model.ProductPrice;
import io.xebia.prizypricer.model.Response;
import io.xebia.prizypricer.repository.ProductPriceRepository;

abstract public class ProductPriceService {
	
	@Autowired
	protected ProductPriceRepository productPriceReposiory;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private StoreService storeService;
	
	public Response insertPrice(ProductPrice productPrice) {
		if (!storeService.storeExists(productPrice.getStoreId())) {
			return new Response(PrizyPricerConstants.NOT_FOUND, PrizyPricerConstants.NO_STORE_ERROR_MSG);
		} else if (!productService.productExists(productPrice.getProductBarCode())) {
			return new Response(PrizyPricerConstants.NOT_FOUND, PrizyPricerConstants.NO_PRODUCT_ERROR_MSG);
		} else {
			insertPriceAndUpdateProductDetails(productPrice);
			return new Response(PrizyPricerConstants.SUCCESS, PrizyPricerConstants.NEW_PRICE_ADDED);
		}
	}
	
	@Transactional
	public void insertPriceAndUpdateProductDetails(ProductPrice productPrice) {
		productPriceReposiory.save(productPrice);
		productService.updateProduct(productPrice.getProductBarCode(), productPrice.getPrice(), getIdealPrice(productPrice.getProductBarCode()));
	}
	
	public abstract Double getIdealPrice(int productBarCode);
}
