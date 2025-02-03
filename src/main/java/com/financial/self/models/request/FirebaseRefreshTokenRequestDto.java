package com.financial.self.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseRefreshTokenRequestDto {
    private String grant_type = "refresh_token";
    private String refresh_token;
}
