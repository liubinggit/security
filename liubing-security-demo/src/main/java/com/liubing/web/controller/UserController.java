package com.liubing.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.liubing.dto.User;
import com.liubing.dto.User.UserDetaView;
import com.liubing.dto.User.UserSimpleView;
import com.liubing.security.core.properties.SecurityProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@GetMapping("/me")
	public Object getMe() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@GetMapping("/jwtme")
	public Object getJWTMe(Authentication user, HttpServletRequest request) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException  {
		securityProperties.getOauth2().getJwtSignKey();
		String token = StringUtils.substringAfter(request.getHeader("Authorization"), "bearer ");
		Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSignKey().getBytes("UTF-8"))
					.parseClaimsJws(token).getBody();
		
		String country = (String) claims.get("country");
		
		System.out.println(country);
		
		return user;
	}
	
	@GetMapping("/me2")
	public Object getMe(Authentication authentication) {
		return authentication;
	}

	@GetMapping
	@JsonView(UserSimpleView.class)
	public List<User> getAllUser(@PageableDefault(page = 0, size = 10, sort = "username, desc")Pageable pageable) {
		List<User> list = new ArrayList<User>();
		list.add(new User(1, "liubing1", "123456"));
		list.add(new User(2, "liubing2", "123456"));
		list.add(new User(3, "liubing3", "123456"));
		System.out.println(list);
		return list;
	}
	
	@GetMapping("/{id:\\d+}")
	@ApiOperation(value="查询用户服务")
	public User getOneUser(@ApiParam(value="用户id")@PathVariable Integer id) {
		System.out.println("UserController getOneUser");
		
		boolean result = false;
		if(result) {
			//会被控制器异常拦截,会进入interceptor的postHandle
//			throw new UserNoExistException(id);
			throw new RuntimeException("user id not exist");
		}else {
			User user = new User();
			user.setId(id);
			user.setUsername("Tom");
			user.setPassword("password");
			return user;
		}
	}
	
	
	@PostMapping
	@JsonView(UserDetaView.class)
	public User createUser(@Valid @RequestBody User user) {
		System.out.println(user);
		user.setId(1);
		return user;
	}
	
	@DeleteMapping("/{id:\\d+}")
	public int deleteUser(@PathVariable Integer id) {
		return 1;
	}
	
	@PutMapping("/{id:\\d+}")
	public User updateUser(@RequestBody User user,@PathVariable Integer id) {
		System.out.println(user);
		user.setId(id);
		return user;
	}
}
