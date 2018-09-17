package com.liubing.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	private static final Logger log = Logger.getLogger(MyUserDetailsService.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("普通登录用户名:" + username);
		return buildUser(username);
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		log.info("设计登录用户表单登录用户名:" + userId);
		return buildUser(userId);
	}

	private SocialUserDetails buildUser(String userId) {
		String password = passwordEncoder.encode("123456"); // 数据查询出来的密码

		// 第三个参数模拟用户权限

		// 密码加密

		return new SocialUser(userId, password, true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin, ROLE_USER, ROLE_ADMIN"));

	}

}
