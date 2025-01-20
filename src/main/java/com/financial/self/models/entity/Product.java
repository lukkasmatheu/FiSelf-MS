package com.financial.self.models.entity;

import com.financial.self.models.request.ProductCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private static final String ENTITY_NAME = "product";

	public static String name() {
		return ENTITY_NAME;
	}


	private String productId;

	private String companyId;

	private LocalDate creationDate;

	private List<String> category;

	private String productName;

	private String description;

	private BigDecimal cost;

	private byte[] image;

	private BigDecimal salePrice;

	private LocalDate expirationDate;

	private Status status;

	private Integer quantity;

	private Supplier supplier;

	private String createByOwner;

	public static Product fromRequest(ProductCreateRequest request) {
		return Product.builder()
				.productId(request.getIdProduct())
				.companyId(request.getIdCompany())
				.creationDate(request.getCreationDate())
				.category(request.getCategory())
				.productName(request.getProductName())
				.description(request.getDescription())
				.cost(request.getCost())
				.image(request.getImage())
				.salePrice(request.getSalePrice())
				.expirationDate(request.getExpirationDate())
				.status(request.getStatus())
				.quantity(request.getQuantity())
				.supplier(request.getSupplier())
				.build();
	}

}
