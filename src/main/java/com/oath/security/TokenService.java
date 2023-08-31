package com.oath.security;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.stream.Collectors;

import org.hibernate.grammars.hql.HqlParser.HourContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtClaimsSet.Builder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.nimbusds.jwt.JWTClaimsSet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {

	
	
	private final JwtEncoder jwtEncoder;
	
	public String generateToken(Authentication auth) {
		
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
		.subject(auth.getName())
		.issuedAt(Instant.now())
		.expiresAt(Instant.now().plus(5,ChronoUnit.MINUTES))
		.claim("scope", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" ")))
		.build();
		
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		
	}
}
