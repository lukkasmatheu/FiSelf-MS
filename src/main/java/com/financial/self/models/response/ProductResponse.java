package com.financial.self.models.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.financial.self.models.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Jacksonized
@Schema(title = "ProductResponse", accessMode = Schema.AccessMode.READ_ONLY)
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class ProductResponse {
	private String id;
	private String productName;
	private Integer quantity;
	private String category;
	private BigDecimal cost;
	private String image;
	private String description;
	private BigDecimal salePrice;
	private LocalDate expirationDate;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
