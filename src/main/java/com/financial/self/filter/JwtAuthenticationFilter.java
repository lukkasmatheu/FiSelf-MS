package com.financial.self.filter;

import java.io.IOException;
import java.util.Optional;

import com.financial.self.client.FirebaseAuthClient;
import com.financial.self.configuration.FirebaseConfiguration;
import com.financial.self.configuration.SecurityConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.financial.self.utility.ApiEndpointSecurityInspector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JwtAuthenticationFilter is a custom filter registered with the spring security filter chain
 * and works in conjunction with the security configuration, as defined in {@link SecurityConfiguration}.
 *
 * It is responsible for verifying the authenticity of incoming HTTP requests to secured API endpoints
 * by verifying the received access token in the request header and verifying it using the Firebase authentication service.
 *
 * This filter is only executed for secure endpoints, and is skipped if the incoming request is destined to a non-secured public API endpoint.
 *
 * @see ApiEndpointSecurityInspector
 * @see FirebaseConfiguration
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final FirebaseAuth firebaseAuth;
	private final ApiEndpointSecurityInspector apiEndpointSecurityInspector;
	private final FirebaseAuthClient firebaseAuthClient;

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";
	private static final String REFRESH_HEADER = "x-refresh-token";
	private static final String USER_ID_CLAIM = "user_id";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (Boolean.TRUE.equals(apiEndpointSecurityInspector.isUnsecureRequest(request))) {
			filterChain.doFilter(request, response);
			return;
		}

		final var authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith(BEARER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		var token = authorizationHeader.replace(BEARER_PREFIX, StringUtils.EMPTY);
		FirebaseToken firebaseToken = null;
		String userId = null;

		try {

			firebaseToken = firebaseAuth.verifyIdToken(token);
			userId = Optional.ofNullable(firebaseToken.getClaims().get(USER_ID_CLAIM))
					.map(Object::toString)
					.orElseThrow(() -> new IllegalStateException("User ID claim not found"));
		} catch (FirebaseAuthException ex) {
			final var refreshToken = request.getHeader(REFRESH_HEADER);
			if (StringUtils.isNotEmpty(refreshToken)) {
				try {
					var refreshedTokens = firebaseAuthClient.refreshAccessToken(refreshToken);
					token = refreshedTokens.getId_token();
					response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + token);
					response.setHeader(REFRESH_HEADER, refreshedTokens.getRefresh_token());

					firebaseToken = firebaseAuth.verifyIdToken(token);
					userId = Optional.ofNullable(firebaseToken.getClaims().get(USER_ID_CLAIM))
							.map(Object::toString)
							.orElseThrow(() -> new IllegalStateException("User ID claim not found"));

				} catch (Exception refreshException) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().write("Invalid or expired refresh token");
                    throw new IllegalStateException("User ID claim not found");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid or expired access token");
                throw new IllegalStateException("User ID claim not found");
			}
		}

		final var authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}
}
