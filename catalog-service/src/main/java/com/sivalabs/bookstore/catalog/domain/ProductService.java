/**
 * 
 */
package com.sivalabs.bookstore.catalog.domain;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.bookstore.catalog.ApplicationProperties;

/**
 * 
 */
@Service
@Transactional
public class ProductService {
	@Value("${display.pageSize}")
	private int pageSize;
	private ProductRepository productRepository;
	private ApplicationProperties appProp;

	 ProductService(ProductRepository productRepository,ApplicationProperties appProp) {
		this.productRepository = productRepository;
		this.appProp=appProp;
	}

	public PagedResult<Product> getAllProducts(int pageNo){
		Sort sort=Sort.by("name").ascending();
		 pageNo = pageNo <= 1 ? 0 : pageNo - 1;
		Pageable pageable = PageRequest.of(pageNo, appProp.pageSize(), sort);
	Page<Product> productsPage = productRepository.findAll(pageable).map(entity-> new ProductMapper().toProduct(entity));
		 return new PagedResult<>(
				 
				 productsPage.getTotalElements(),
				 productsPage.getNumber() + 1,
				 productsPage.getTotalPages(),
				 productsPage.isFirst(),
	                productsPage.isLast(),
	                productsPage.hasNext(),
	                productsPage.hasPrevious(),productsPage.getContent());
	}
	
	public Optional<Product> getProductByCode(String code) {
		return  productRepository.findByCode(code).map(entity-> new ProductMapper().toProduct(entity));
	
	}

}
