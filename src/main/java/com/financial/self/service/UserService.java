package com.financial.self.service;

import com.financial.self.client.FirebaseAuthClient;
import com.financial.self.dto.TokenSuccessResponseDto;
import com.financial.self.dto.UserLoginRequestDto;
import com.financial.self.exception.AccountAlreadyExistsException;
import com.financial.self.models.entity.User;
import com.financial.self.models.request.UserCreationRequest;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final FirebaseAuth firebaseAuth;
	private final Firestore firestore;
	private final FirebaseAuthClient firebaseAuthClient;

	@SneakyThrows
	public void create(@NonNull final UserCreationRequest userCreationRequest) {
		final var request = new CreateRequest();
		request.setEmail(userCreationRequest.getEmail());
		request.setPassword(userCreationRequest.getPassword());
		request.setEmailVerified(Boolean.TRUE);
		try {
			firebaseAuth.createUser(request);
			firestore.collection(User.name()).document().set(User.fromRequest(userCreationRequest));

		} catch (final FirebaseAuthException exception) {
			if (exception.getMessage().contains("EMAIL_EXISTS")) {
				throw new AccountAlreadyExistsException("Account with provided email-id already exists");
			}
			throw exception;
		}
	}

	public TokenSuccessResponseDto login(@NonNull final UserLoginRequestDto userLoginRequest) {
		return firebaseAuthClient.login(userLoginRequest);
	}

}
