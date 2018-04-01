package io.xebia.prizypricer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.xebia.prizypricer.model.ProductPrice;

public interface ProductPriceRepository extends CrudRepository<ProductPrice, Integer> {
	List<ProductPrice> findByProductBarCode(int productBarCode);
}
