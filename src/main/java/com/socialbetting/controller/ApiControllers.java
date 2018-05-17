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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiControllers {

	@Autowired
	private TokenUtils tokenUtils;

	private AuthenticationManager authenticationManager;

	@Autowired
	private SocialBettingUserDetailsService userDetailsService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public ApiControllers(TokenUtils tokenUtils, SocialBettingUserDetailsService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.tokenUtils = tokenUtils;
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@CrossOrigin
	@RequestMapping(value = "/users/me", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> currentUserName(Principal principal) {
		String currentPrincipalName = principal.getName();
		User user = (User) userDetailsService.loadUserByUsername(currentPrincipalName);
		return ResponseEntity.ok(user);
	}

}