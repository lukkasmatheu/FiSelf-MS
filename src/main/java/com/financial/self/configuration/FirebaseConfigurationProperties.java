package com.financial.self.configuration;

import com.financial.self.client.FirebaseAuthClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "com.financial.self")
public class FirebaseConfigurationProperties {

	@Valid
	private FireBase firebase = new FireBase();

	@Getter
	@Setter
	public class FireBase {

		@NotBlank(message = "Firestore private key must be configured")
		private String privateKey;

		@NotBlank(message = "Firebase Web API key must be configured")
		private String webApiKey;
		
	}

}