/**
 * 
 */
package com.sivalabs.bookstore.catalog.domain;

/**
 * 
 */
 class ProductMapper {
	
	 Product toProduct(ProductEntity prodEntity) {
		
		return new Product(prodEntity.getCode(),
				prodEntity.getName(),
				prodEntity.getDescription(),prodEntity.getImageUrl(),prodEntity.getPrice());
	}

}
