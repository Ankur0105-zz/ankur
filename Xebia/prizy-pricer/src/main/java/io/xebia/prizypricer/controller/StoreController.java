package io.xebia.prizypricer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.xebia.prizypricer.model.Response;
import io.xebia.prizypricer.model.Store;
import io.xebia.prizypricer.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping(path="/prizy/store")
public class StoreController {
	
	@Autowired
	private StoreService storeService;
	
	@GetMapping(path="/all")
	public Response getAllProducts() {
		return storeService.getAllStores();
	}
	
	@GetMapping(path="/{storeId}")
	public Response getStore(@PathVariable("storeId") int storeId) {
		return storeService.getStoreDetails(storeId);
	}
	
	@PostMapping(path="/update")
	public Response insertProduct(@RequestBody Store store) {
		return storeService.insertStore(store);
	}
	
	@GetMapping(path="/delete/{storeId}")
	public Response deleteProduct(@PathVariable("storeId") int storeId) {
		return storeService.deleteStore(storeId);
	}
	

}
