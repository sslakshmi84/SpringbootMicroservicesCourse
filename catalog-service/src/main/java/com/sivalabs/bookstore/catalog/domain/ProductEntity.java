/**
 * 
 */
package com.sivalabs.bookstore.catalog.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

/**
 * 
 */
@Entity
@Table(name="productst")
 class ProductEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;
	@NotBlank(message="Product code is required")
	@Column(nullable=false,unique=true)
    private String code ;
	@NotBlank( message="Product name is required")
	@Column(nullable=false)
	private String name;
	private String description;
    private String imageUrl;
    @NotBlank(message="Product price is required")
    @Column(nullable=false)
    @DecimalMin("0.1")
    private BigDecimal price;
    
    
    
    
    
	/**
	 * 
	 */
	public ProductEntity() {
		super();
	}
	/**
	 * @param id
	 * @param code
	 * @param name
	 * @param description
	 * @param imageUrl
	 * @param price
	 */
	 ProductEntity(Long id, @NotBlank(message = "Product code is required") String code,
			@NotBlank(message = "Product name is required") String name, String description, String imageUrl,
			@NotBlank(message = "Product price is required") @DecimalMin("0.1") BigDecimal price) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ProductEntity [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description
				+ ", imageUrl=" + imageUrl + ", price=" + price + ", getId()=" + getId() + ", getCode()=" + getCode()
				+ ", getName()=" + getName() + ", getDescription()=" + getDescription() + ", getImageUrl()="
				+ getImageUrl() + ", getPrice()=" + getPrice() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
    
    

}
