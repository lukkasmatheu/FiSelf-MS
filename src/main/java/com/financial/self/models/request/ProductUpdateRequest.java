package com.financial.self.models.request;

import java.time.LocalDate;

import com.financial.self.models.entity.Status;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@Schema(title = "ProductUpdateRequest", accessMode = Schema.AccessMode.WRITE_ONLY)
public class ProductUpdateRequest {

	@NotBlank(message = "Description must not be empty")
	@Schema(requiredMode = RequiredMode.REQUIRED, description = "description of task", example = "Resolve user authentication issue detailed in Jira#9051")
	private String description;

	@NotNull(message = "Status must not be empty")
	@Schema(requiredMode = RequiredMode.REQUIRED, description = "status of the task", example = "COMPLETED")
	private Status status;

	@FutureOrPresent(message = "DueDate must be a future date")
	@NotNull(message = "DueDate must not be empty")
	@Schema(requiredMode = RequiredMode.REQUIRED, description = "due-date of the task")
	private LocalDate dueDate;

}
