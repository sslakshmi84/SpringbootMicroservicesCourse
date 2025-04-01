
/**
 * 
 */
package com.sivalabs.bookstore.catalog;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;

/**
 * 
 */
@Validated
@ConfigurationProperties(prefix="catalog")
public record ApplicationProperties(
		@DefaultValue("10")
		@Min(1)
		int pageSize) {

}
