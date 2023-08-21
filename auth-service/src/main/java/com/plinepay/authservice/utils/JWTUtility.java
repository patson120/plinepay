package com.plinepay.authservice.utils;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.plinepay.core.utils.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtility implements Serializable {
    
    private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 24 * (60 * 60);

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
    // for retrieveing any information from token we will need the secret key
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	// while creating the token -
	// 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	
	// private JwtResponse doGenerateToken(Map<String, Object> claims, String login) {
	// 	// Instant now =  Instant.now();

	// 	String access = Jwts.builder()
	// 			.setClaims(claims)
	// 			.setSubject(login)
	// 			.setIssuedAt(new Date(System.currentTimeMillis()))
	// 			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	// 			.signWith(SignatureAlgorithm.HS512, secret)
	// 			.compact();
		
	// 	String refresh = Jwts.builder()
	// 			.setClaims(claims)
	// 			.setSubject(login)
	// 			.setIssuedAt(new Date(System.currentTimeMillis()))
	// 			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000 * 2))
	// 			.signWith(SignatureAlgorithm.HS512, secret)
	// 			.compact();
			
	// 	return new JwtResponse(access, refresh );
	// }

	//validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Générer un token pour des raisons de test
	public String doGenerateToken(Map<String, Object> claims, String login) {
		// Instant now =  Instant.now();

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(login)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	
	}


	// Cette méthode permet de vérifier si un USER est SUPER_ADMIN ou non
	public static boolean checkRole(Collection<? extends GrantedAuthority> authorities, String userRole) {

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().contains(Constants.ROLE_CODE_SUPERADMIN.toString())) {
                return true;
            }
        }

        return false;
    }
}
