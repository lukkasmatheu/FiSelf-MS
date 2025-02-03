package com.financial.self.service;

import com.financial.self.client.FirebaseAuthClient;
import com.financial.self.models.response.TokenSuccessResponse;
import com.financial.self.models.request.UserLoginRequest;
import com.financial.self.exception.AccountAlreadyExistsException;
import com.financial.self.models.entity.User;
import com.financial.self.models.request.UserCreationRequest;
import com.financial.self.models.response.UserResponse;
import com.financial.self.utility.AuthenticatedUserIdProvider;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static com.financial.self.models.entity.User.ZONE_BRASIL;

@Service
@RequiredArgsConstructor
public class UserService {

	private final FirebaseAuth firebaseAuth;
	private final Firestore firestore;
	private final FirebaseAuthClient firebaseAuthClient;
	private final AuthenticatedUserIdProvider authenticatedUserIdProvider;

	@SneakyThrows
	public void create(@NonNull final UserCreationRequest userCreationRequest) {
		final var request = new CreateRequest();
		request.setEmail(userCreationRequest.getEmail());
		request.setPassword(userCreationRequest.getPassword());
		request.setEmailVerified(Boolean.TRUE);
		try {
			var user = firebaseAuth.createUser(request);
			userCreationRequest.setIdUser(user.getUid());
			firestore.collection(User.name()).document().set(User.fromRequest(userCreationRequest));

		} catch (final FirebaseAuthException exception) {
			if (exception.getMessage().contains("EMAIL_EXISTS")) {
				throw new AccountAlreadyExistsException("Account with provided email-id already exists");
			}
			throw exception;
		}
	}

	public UserResponse login(@NonNull final UserLoginRequest userLoginRequest) throws ExecutionException, InterruptedException {
		var loginFirebase = firebaseAuthClient.login(userLoginRequest);
		var userResponse = firestore.collection(User.name()).whereEqualTo("email", userLoginRequest.getEmail()).get().get().getDocuments();
		var user = userResponse.getFirst().toObject(User.class);
		return UserResponse.builder()
				.idUser(user.getIdUser())
				.name(user.getName())
				.phone(user.getPhone())
				.cpf(user.getCpf())
				.birthDate(user.getBirthDate().toInstant().atZone(ZONE_BRASIL).toLocalDate())
				.email(user.getEmail())
				.accessToken(loginFirebase.getAccessToken())
				.refreshToken(loginFirebase.getRefreshToken())
				.expiresIn(loginFirebase.getExpiresIn())
				.build();

	}

}
