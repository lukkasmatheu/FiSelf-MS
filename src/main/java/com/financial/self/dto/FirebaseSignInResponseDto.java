package com.financial.self.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FirebaseSignInResponseDto {

	private String idToken;
	private String refreshToken;
	private String expiresIn;

}
