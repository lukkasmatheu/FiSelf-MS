package com.financial.self.models.entity;

import com.financial.self.models.request.ProductCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.financial.self.models.entity.User.ZONE_BRASIL;

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

	private Date creationDate;

	private String category;

	private String productName;

	private String description;

	private BigDecimal cost;

	private String image;

	private BigDecimal salePrice;

	private Date expirationDate;

	private Integer quantity;

//	private Supplier supplier;

	private String createByOwner;

	public static Product fromRequest(ProductCreateRequest request) {
		return Product.builder()
				.productId(request.getIdProduct())
				.companyId(request.getIdCompany())
				.creationDate(Date.from(request.getCreationDate().atStartOfDay(ZONE_BRASIL).toInstant()))
				.category(request.getCategory())
				.productName(request.getProductName())
				.description(StringUtils.defaultIfEmpty(request.getDescription(),"Descrição padrão do produto"))
				.cost(request.getCost())
				.image(StringUtils.defaultString(request.getImage()))
				.salePrice(request.getSalePrice())
				.expirationDate(request.getExpirationDate() != null ? Date.from(request.getExpirationDate().atStartOfDay(ZONE_BRASIL).toInstant()): null)
				.quantity(request.getQuantity())
				.build();
	}

}
