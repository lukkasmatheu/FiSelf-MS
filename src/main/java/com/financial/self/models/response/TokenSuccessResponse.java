package com.financial.self.models.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Schema(title = "TokenSuccessResponse", accessMode = Schema.AccessMode.READ_ONLY)
public class TokenSuccessResponse {

	private String accessToken;
	private String refreshToken;
	private String expiresIn;

}