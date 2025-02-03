package com.financial.self.controller;

import com.financial.self.dto.ExceptionResponseDto;
import com.financial.self.models.request.ProductCreateRequest;
import com.financial.self.models.request.SaleProductRequest;
import com.financial.self.models.response.ProductResponse;
import com.financial.self.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
@Tag(name = "Product Management", description = "Endpoints for managing Product.")
public class ProductController {
	private final ProductService productService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Creates a new Product record", description = "Creates a new Product with provided details")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Product created successfully",
					content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "401", description = "Authentication failure: Invalid access token",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Invalid request body",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))) })
	public ResponseEntity<HttpStatus> create(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
		productService.create(productCreateRequest);
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/sale", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Register Sale Products", description = "Register sale Products")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product sale successfully",
					content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "404", description = "No product exists in the system with provided-id",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
			@ApiResponse(responseCode = "401", description = "Authentication failure: Invalid access token",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
			@ApiResponse(responseCode = "403", description = "Access denied: Insufficient permissions",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))) })
	public ResponseEntity<HttpStatus> saleProduct(@Valid @RequestBody List<SaleProductRequest> saleProductListRequest) {
		saleProductListRequest.forEach(productService::saleProduct);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Retrieves task details", description = "Retrieve details of a specific task by its ID")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Product details retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "No task exists in the system with provided-id",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
			@ApiResponse(responseCode = "401", description = "Authentication failure: Invalid access token",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
			@ApiResponse(responseCode = "403", description = "Access denied: Insufficient permissions",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))) })
	public ResponseEntity<ProductResponse> getProductById(
			@PathVariable(required = true, name = "productId") final String productId) {
		final var response = productService.getById(productId);
		return ResponseEntity.ok(response);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Retrieves all tasks of authenticated user", description = "Retrieve details of all tasks corresponding to authenticated user")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Product details retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Authentication failure: Invalid access token",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))) })
	public ResponseEntity<List<ProductResponse>> getAll() {
		final var response = productService.getAll();
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Updates task details", description = "Update details of a specified task by its ID")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Product details updated successfully",
					content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "404", description = "No task exists in the system with provided-id",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
			@ApiResponse(responseCode = "401", description = "Authentication failure: Invalid access token",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
			@ApiResponse(responseCode = "403", description = "Access denied: Insufficient permissions",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))) })
	public ResponseEntity<HttpStatus> update(@PathVariable(required = true, name = "productId") final String productId,
			@Valid @RequestBody ProductCreateRequest productCreateRequest) {
		productService.update(productId, productCreateRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(value = "/{productId}")
	@Operation(summary = "Deletes a task record", description = "Delete a specific task by its ID")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Product deleted successfully",
					content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "404", description = "No task exists in the system with provided-id",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
			@ApiResponse(responseCode = "401", description = "Authentication failure: Invalid access token",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
			@ApiResponse(responseCode = "403", description = "Access denied: Insufficient permissions",
					content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))) })
	public ResponseEntity<HttpStatus> delete(@PathVariable(required = true, name = "productId") final String productId) {
		productService.delete(productId);
		return ResponseEntity.ok().build();
	}

}