/**
 * 
 */
package com.sivalabs.bookstore.catalog.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sivalabs.bookstore.catalog.domain.ErrorResponse;
import com.sivalabs.bookstore.catalog.domain.ProductNotFoundException;

/**
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e){
		ErrorResponse errorResponse=	new ErrorResponse(500,e.getMessage(),"Internal Server Error");
		return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e){
		ErrorResponse errorResponse=	new ErrorResponse(404,e.getMessage(),"NOT_FOUND");
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				             .body(errorResponse);
	}
	 
}
