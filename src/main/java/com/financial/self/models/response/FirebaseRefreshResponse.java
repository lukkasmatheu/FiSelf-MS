package com.financial.self.models.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FirebaseRefreshResponse {

	private String id_token;
	private String user_id;
	private String refresh_token;
	private String expires_in;

}
