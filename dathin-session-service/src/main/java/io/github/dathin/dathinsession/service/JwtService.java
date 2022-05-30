package io.github.dathin.dathinsession.service;

import io.github.dathin.boot.dathinstarterauthorizer.model.exception.UnauthorizedException;
import io.github.dathin.boot.dathinstarterauthorizer.model.user.UserToken;
import io.github.dathin.dathinsession.model.user.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

	private final Key key;

	public JwtService(@Value("${jwt.key}") String jwtKey) {
		this.key = new SecretKeySpec((jwtKey).getBytes(StandardCharsets.UTF_8), "HmacSHA256");
	}

	public String issueToken(User user) {
		long currentTimeInMs = System.currentTimeMillis();
		long oneDayInMs = 86400000;
		return Jwts.builder().setIssuedAt(new Date(currentTimeInMs))
				.setExpiration(new Date(currentTimeInMs + oneDayInMs)).claim("userId", user.getId()).signWith(key)
				.compact();
	}

	public UserToken validateToken(String token) {
		try {
			var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(removeBearer(token)).getBody();
			var userToken = new UserToken();
			userToken.setId(claims.get("userId", Integer.class));
			return userToken;
		}
		catch (JwtException | NullPointerException ex) {
			throw new UnauthorizedException();
		}
	}

	public String removeBearer(String tokenWithBearerPrefix) {
		return tokenWithBearerPrefix.replace("Bearer ", "");
	}

}
