package com.financial.self.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirebaseSignInRequestDto {

	private String email;
	private String password;
	private boolean returnSecureToken;

}
