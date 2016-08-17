package com.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.exception.ErrorLoggingInException;
import com.exception.GeneralError;
import com.model.User;
import com.service.FacebookService;
import com.service.TokenService;
import com.service.UserService;
import com.utils.Constants;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SecurityProperties security;

	@Autowired
	Environment env;

	@Autowired
	DataSource dataSource;

	@Autowired
	UserService userService;

	@Autowired
	TokenService tokenService;

	@Autowired
	FacebookService facebookService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().anyRequest().permitAll();
		if (env.getActiveProfiles()[0].equals("prod"))
			http.requiresChannel().anyRequest().requiresSecure();
		http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new AccountKitAuthenticationProvider());
		auth.authenticationProvider(new TokenAuthenticationProvider());
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		CustomLoginSuccessHandler successHandler = new CustomLoginSuccessHandler("/aroundMe");
		successHandler.setAlwaysUseDefaultTargetUrl(true);
		return successHandler;
	}

	/**
	 * 
	 * @author crivera
	 *
	 */
	public class AuthenticationFilter extends GenericFilterBean {

		AuthenticationManager authenticationManager;

		public AuthenticationFilter(AuthenticationManager authenticationManager) {
			this.authenticationManager = authenticationManager;
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			Optional<String> token = getAccessToken(httpRequest);
			Optional<String> accountKitCode = Optional.ofNullable(httpRequest.getParameter("code"));

			String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);

			// check if we should log in with token
			if (Constants.AUTH_URL_ACCOUNT_KIT.equalsIgnoreCase(resourcePath) && httpRequest.getMethod().equals("POST")
					&& accountKitCode.isPresent()) {

				AuthenticationWithAccountKit requestAuthentication = new AuthenticationWithAccountKit(accountKitCode);
				Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
				if (responseAuthentication != null && responseAuthentication.isAuthenticated()) {
					SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
					successHandler().onAuthenticationSuccess((HttpServletRequest) request,
							(HttpServletResponse) response, responseAuthentication);
					return;
				} else {
					throw new InternalAuthenticationServiceException(
							"Unable to authenticate Domain User for provided credentials");
				}
			} else if (token.isPresent()) {
				PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(
						token, null);
				Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
				if (responseAuthentication != null && responseAuthentication.isAuthenticated()) {
					SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
					((HttpServletResponse) response).addCookie(
							new Cookie(Constants.TOKEN, ((User) responseAuthentication.getPrincipal()).getToken()));
				} else {
					throw new InternalAuthenticationServiceException(
							"Unable to authenticate Domain User for provided credentials");
				}

			}
			chain.doFilter(request, response);

		}
	}

	/**
	 * 
	 * @author crivera
	 *
	 */
	public class TokenAuthenticationProvider implements AuthenticationProvider {

		@Override
		@SuppressWarnings("unchecked")
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			Optional<String> token = (Optional<String>) authentication.getPrincipal();
			if (!token.isPresent() || token.get().isEmpty()) {
				throw new BadCredentialsException("Invalid token");
			}

			try {
				String[] payload = tokenService.getUserFromToken(token.get()).split(":");
				// TODO: expire token
				String userId = payload[0];
				User user = userService.getUserById(Long.valueOf(userId));
				if (user == null)
					throw new BadCredentialsException("Invalid Token");
				if (!user.isEnabled())
					throw new BadCredentialsException("This user account is not enabled.");

				AuthenticationWithToken resultOfAuthentication = new AuthenticationWithToken(user, null,
						user.getAuthorities());
				resultOfAuthentication.setToken(token.get());
				return resultOfAuthentication;
			} catch (JoseException | NumberFormatException | GeneralError e) {
				throw new BadCredentialsException(e.getMessage());
			}
		}

		@Override
		public boolean supports(Class<?> authentication) {
			return authentication.equals(PreAuthenticatedAuthenticationToken.class);
		}
	}

	/**
	 * 
	 * @author crivera
	 *
	 */
	public class AuthenticationWithToken extends PreAuthenticatedAuthenticationToken {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AuthenticationWithToken(Object aPrincipal, Object aCredentials,
				Collection<? extends GrantedAuthority> anAuthorities) {
			super(aPrincipal, aCredentials, anAuthorities);
		}

		public void setToken(String token) {
			setDetails(token);
		}

		public String getToken() {
			return (String) getDetails();
		}
	}

	/**
	 * 
	 * @author crivera
	 *
	 */
	public class AccountKitAuthenticationProvider implements AuthenticationProvider {

		@Override
		@SuppressWarnings("unchecked")
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {

			Optional<String> accountKitAccessCode = (Optional<String>) authentication.getPrincipal();

			if (!accountKitAccessCode.isPresent()) {
				throw new BadCredentialsException("Invalid Account Kit Code");
			}

			User user = null;
			try {
				user = facebookService.loginWithAccountKit(accountKitAccessCode.get());
				if (!user.isEnabled())
					throw new BadCredentialsException("This user account is not enabled.");

				AuthenticationWithToken resultOfAuthentication = new AuthenticationWithToken(user, null,
						user.getAuthorities());
				resultOfAuthentication.setToken(user.getToken());

				return resultOfAuthentication;
			} catch (ErrorLoggingInException e) {
				throw new BadCredentialsException("Could not log you in.", e);
			}

		}

		@Override
		public boolean supports(Class<?> authentication) {
			return authentication.equals(AuthenticationWithAccountKit.class);
		}

	}

	/**
	 * 
	 * @author crivera
	 *
	 */
	public class AuthenticationWithAccountKit extends AbstractAuthenticationToken {

		private Optional<String> accountKitCode;

		public AuthenticationWithAccountKit(Optional<String> accountKitCode) {
			super(AuthorityUtils.createAuthorityList("ROLE_USER"));
			this.accountKitCode = accountKitCode;
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Object getCredentials() {
			return this.accountKitCode;
		}

		@Override
		public Object getPrincipal() {
			return this.accountKitCode;
		}

	}

	public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
		public CustomLoginSuccessHandler(String defaultTargetUrl) {
			setDefaultTargetUrl(defaultTargetUrl);
		}

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws ServletException, IOException {
			HttpSession session = request.getSession();
			if (session != null) {
				String redirectUrl = (String) session.getAttribute("url_prior_login");
				if (redirectUrl != null) {
					// we do not forget to clean this attribute from session
					session.removeAttribute("url_prior_login");
					// then we redirect
					getRedirectStrategy().sendRedirect(request, response, redirectUrl);
				} else {
					super.onAuthenticationSuccess(request, response, authentication);
				}
			} else {
				super.onAuthenticationSuccess(request, response, authentication);
			}
		}

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private Optional<String> getAccessToken(HttpServletRequest request) {
		Optional<String> authHeader = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));

		if (authHeader.isPresent()) {
			String header = authHeader.get();
			if (header.startsWith("Bearer ")) {
				// NOTE: This is a simple implementation of the OAuth Bearer
				// parser. This does not take into
				// account for things like realms and scopes.
				String accessToken = header.substring("Bearer ".length());
				return Optional.of(accessToken);
			}
		}
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(Constants.TOKEN)) {
				return Optional.of(cookie.getValue());
			}
		}
		return Optional.empty();
	}

}
