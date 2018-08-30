package com.liubing.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/me")
	public Object getMe() {
		return SecurityContextHolder.getContext().getAuthentication();
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
