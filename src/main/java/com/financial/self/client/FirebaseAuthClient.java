package com.financial.self.client;

import com.financial.self.models.request.FirebaseRefreshTokenRequestDto;
import com.financial.self.models.response.FirebaseRefreshResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.financial.self.configuration.FirebaseConfigurationProperties;
import com.financial.self.dto.FirebaseSignInRequestDto;
import com.financial.self.dto.FirebaseSignInResponseDto;
import com.financial.self.models.response.TokenSuccessResponse;
import com.financial.self.models.request.UserLoginRequest;
import com.financial.self.exception.InvalidLoginCredentialsException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(FirebaseConfigurationProperties.class)
public class FirebaseAuthClient {

	private final FirebaseConfigurationProperties firebaseConfigurationProperties;
	private static final String REFRESH_URL = "https://securetoken.googleapis.com/v1/token";
	private static final String BASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";
	private static final String API_KEY_PARAM = "key";
	private static final String INVALID_CREDENTIALS_ERROR = "INVALID_LOGIN_CREDENTIALS";
    

	public TokenSuccessResponse login(@NonNull final UserLoginRequest userLoginRequest) {
		final var requestBody = prepareRequestBody(userLoginRequest);
		final var response = sendSignInRequest(requestBody);
		return TokenSuccessResponse.builder()
				.accessToken(response.getIdToken())
				.refreshToken(response.getRefreshToken())
				.expiresIn(response.getExpiresIn())
				.build();
	}
	
	private FirebaseSignInResponseDto sendSignInRequest(@NonNull final FirebaseSignInRequestDto request) {
		final var webApiKey = firebaseConfigurationProperties.getFirebase().getWebApiKey();
		final FirebaseSignInResponseDto response;
		try {
			response = RestClient.create(BASE_URL)
					.post()
					.uri(uriBuilder -> uriBuilder
							.queryParam(API_KEY_PARAM, webApiKey)
							.build())
					.body(request)
					.contentType(MediaType.APPLICATION_JSON)
					.retrieve()
					.body(FirebaseSignInResponseDto.class);
		} catch (HttpClientErrorException exception) {
			if (exception.getResponseBodyAsString().contains(INVALID_CREDENTIALS_ERROR)) {
				throw new InvalidLoginCredentialsException();	
			}
			throw exception;
		}
		return response;
	}
	
	private FirebaseSignInRequestDto prepareRequestBody(@NonNull final UserLoginRequest userLoginRequest) {
		final var request = new FirebaseSignInRequestDto();
		request.setEmail(userLoginRequest.getEmail());
		request.setPassword(userLoginRequest.getPassword());
		request.setReturnSecureToken(Boolean.TRUE);
		return request;
	}
public FirebaseRefreshResponse refreshAccessToken(@NonNull final String refreshToken) {
	final var requestBody = new FirebaseRefreshTokenRequestDto();
	requestBody.setRefresh_token(refreshToken);

	final var webApiKey = firebaseConfigurationProperties.getFirebase().getWebApiKey();
	final FirebaseRefreshResponse response;

	try {
		response = RestClient.create(REFRESH_URL)
				.post()
				.uri(uriBuilder -> uriBuilder
						.queryParam(API_KEY_PARAM, webApiKey)
						.build())
				.body(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(FirebaseRefreshResponse.class);
	} catch (HttpClientErrorException exception) {
		throw new InvalidLoginCredentialsException();
	}

	return response;
}


}
