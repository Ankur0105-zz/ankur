package io.xebia.prizypricer.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import io.xebia.prizypricer.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	@SuppressWarnings("unchecked")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Product save(Product product);
}
