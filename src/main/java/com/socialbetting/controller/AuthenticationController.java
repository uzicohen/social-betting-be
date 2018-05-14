package com.socialbetting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.socialbetting.objectmodel.User;
import com.socialbetting.objectmodel.security.UserTokenState;
import com.socialbetting.security.JwtAuthenticationRequest;
import com.socialbetting.security.TokenUtils;
import com.socialbetting.security.service.SocialBettingUserDetailsService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	private AuthenticationManager authenticationManager;

	@Autowired
	private SocialBettingUserDetailsService userDetailsService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public AuthenticationController(TokenUtils tokenUtils, SocialBettingUserDetailsService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.tokenUtils = tokenUtils;
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword());

		// Perform the security
		final Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		// Inject into security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// token creation
		User user = (User) authentication.getPrincipal();
		String jws = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();
		// Return the token
		return ResponseEntity.ok(new UserTokenState(jws, expiresIn));
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signUp(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		this.userDetailsService.saveUser(user);
		return ResponseEntity.ok("Created the user");
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response,
			Principal principal) {

		String authToken = tokenUtils.getToken(request);

		if (authToken != null && principal != null) {

			// TODO check user password last update
			String refreshedToken = tokenUtils.refreshToken(authToken);
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.accepted().body(userTokenState);
		}
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}

	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}