/**
 * 
 */
package com.sivalabs.bookstore.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

/**
 * 
 */
@DataJpaTest( properties = {
		 "spring.test.database.replace=none",
         "spring.datasource.url=jdbc:tc:mysql:8.0:///db",
})
@Sql("/test-data.sql")
public class ProductRepositoryTest {

	@Autowired
	ProductRepository productRepository;
	@Test
	void shouldGetAllProducts() {
		List<ProductEntity> products = productRepository.findAll();
		assertThat(products).hasSize(15);
		                 
	}
	
	void shouldFindByCode() {
		ProductEntity product = productRepository.findByCode("P104").orElseThrow();
		assertThat(product.getCode()).isEqualTo("P104");
		assertThat(product.getName()).isEqualTo("The Fault in Our Stars");
		assertThat(product.getDescription()).isEqualTo("Despite the tumor-shrinking medical miracle that has bought her a few years, Hazel has never been anything but terminal, her final chapter inscribed upon diagnosis.");
		assertThat(product.getImageUrl()).isEqualTo("https://images.gr-assets.com/books/1360206420l/11870085.jpg");
		assertThat(product.getPrice()).isEqualTo(new BigDecimal(14.50));
		
	}
	void shouldReturnEmptyFOrNonExistingCode() {
		 assertThat(productRepository.findByCode("P1045")).isEmpty();
		
		
	}
}
