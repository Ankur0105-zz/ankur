package io.xebia.prizypricer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.xebia.prizypricer.constants.PrizyPricerConstants;
import io.xebia.prizypricer.model.Response;
import io.xebia.prizypricer.model.Store;
import io.xebia.prizypricer.repository.StoreRepository;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepository;
	
	public Response getAllStores() {
		return new Response(PrizyPricerConstants.SUCCESS, storeRepository.findAll());
	}
	
	public Response getStoreDetails(int storeId) {
		Optional<Store> storeOptional = storeRepository.findById(storeId);
		if (storeOptional.isPresent()) {
			return new Response(PrizyPricerConstants.SUCCESS, storeOptional.get());
		}
		return new Response(PrizyPricerConstants.NOT_FOUND, PrizyPricerConstants.NO_STORE_ERROR_MSG);
	}
	
	public Response insertStore(Store store) {
		storeRepository.save(store);
		return new Response(PrizyPricerConstants.SUCCESS, PrizyPricerConstants.STORE_SUCCESS);
	}
	
	public Response deleteStore(int storeId) {
		if (storeExists(storeId)) {
			storeRepository.deleteById(storeId);
			return new Response(PrizyPricerConstants.SUCCESS, PrizyPricerConstants.STORE_DELETED);
		}
		return new Response(PrizyPricerConstants.NOT_FOUND, PrizyPricerConstants.NO_STORE_ERROR_MSG);
	}
	
	public boolean storeExists(int storeId) {
		return storeRepository.existsById(storeId);
	}
}
