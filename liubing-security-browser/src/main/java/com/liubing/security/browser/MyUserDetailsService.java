package com.liubing.security.browser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {

	private static final Logger log = Logger.getLogger(MyUserDetailsService.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername username:" + username);
		// 查询用户信息

		// 第三个参数模拟用户权限

		// 密码加密
		String password = passwordEncoder.encode("123456"); // 数据查询出来的密码
		User user = new User(username, password, true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		
		log.info("loadUserByUsername user:" + user);
		return user;
	}

}
