package com.socialbetting.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.socialbetting.objectmodel.User;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

	public final static String APP_NAME = "SocialBetting";

	public final static String SECRET = "queenelisabeth";

	public final static int EXPIRES_IN = 300;

	public final static String AUTH_HEADER = "Authorization";

	static final String AUDIENCE_WEB = "web";

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getIssuedAtDateFromToken(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			audience = claims.getAudience();
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	public String refreshToken(String token) {
		String refreshedToken;
		Date a = new Date();
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			claims.setIssuedAt(a);
			refreshedToken = Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public String generateToken(String username) {
		// Comment out .setExpiration(generateExpirationDate(device)) for no
		// expiration
		String audience = generateAudience();
		return Jwts.builder().setIssuer(APP_NAME).setSubject(username).setAudience(audience).setIssuedAt(new Date())
				.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
	}

	private String generateAudience() {
		return AUDIENCE_WEB;
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Date generateExpirationDate() {
		long expiresIn = EXPIRES_IN;
		return new Date(new Date().getTime() + expiresIn * 1000);
	}

	public int getExpiredIn() {
		return EXPIRES_IN;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		User user = (User) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);
		// Check created
		return username != null && username.equals(userDetails.getUsername());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	public String getToken(HttpServletRequest request) {
		/**
		 * Getting the token from Authentication header e.g Bearer your_token
		 */
		String authHeader = getAuthHeaderFromHeader(request);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}

}