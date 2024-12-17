package com.financial.self.utility;

import java.util.Optional;

import com.financial.self.filter.JwtAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class dedicated to provide authenticated user's ID as saved in the
 * Firebase Authentication Service which uniquely identifies the user in the system.
 * This is fetched from the principal in security context, where it is stored in
 * by the {@link JwtAuthenticationFilter} during HTTP
 * request evaluation through the filter chain.
 * 
 * @see JwtAuthenticationFilter
 */
@Component
public class AuthenticatedUserIdProvider {
	
	/**
	 * Retrieves user ID corresponding to the authenticated user from the security
	 * context.
	 * 
	 * @return Unique ID corresponding to the authenticated user.
	 * @throws IllegalStateException if the method is invoked when a request was
	 *                               destined to a public API endpoint and did not pass
	 *                               the JwtAuthenticationFilter
	 */
	public String getUserId() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
		        .map(Authentication::getPrincipal)
		        .filter(String.class::isInstance)
		        .map(String.class::cast)
		        .orElseThrow(IllegalStateException::new);
	}

}