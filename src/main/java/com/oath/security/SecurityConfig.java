package com.oath.security;

import java.security.spec.RSAPublicKeySpec;

import org.apache.coyote.http11.Http11InputBuffer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.oath.records.RsaKeys;
import com.oath.security.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    
    private final OAuth2FailureHandler oAuth2FailureHandler;

    private final CustomOauth2UserService customOauth2UserService;

	private final RsaKeys rsaKeys;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authManager() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(daoAuthenticationProvider);
	}

	@Bean
	SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

		return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
				// Spring adds authorities to the principal based on the scopes it received from
				// the provider,
				// prefixed with “SCOPE_
				// Why ? until now i think authorities are typically derived from the OAuth2 (no
				// idea hh)

				authorize -> authorize.requestMatchers("/token","/oauth2/**").permitAll().requestMatchers("/users")
						.hasAuthority("SCOPE_WRITER").anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.oauth2ResourceServer(
						(oauth2ResourceServer) -> oauth2ResourceServer.jwt((jwt) -> jwt.decoder(jwtDecoder())))

				.oauth2Login(oath -> oath
						.authorizationEndpoint(authEndP -> authEndP.baseUri("/oauth2"))
						.redirectionEndpoint(redirectEndP -> redirectEndP.baseUri("/oauth2/callback/*"))
						.userInfoEndpoint(userInfo -> userInfo.userService(customOauth2UserService))
						.successHandler(oAuth2SuccessHandler)
						.failureHandler(oAuth2FailureHandler)
						)

				// .httpBasic(withDefaults())
				.build();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
	}

	@Bean
	JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

}
