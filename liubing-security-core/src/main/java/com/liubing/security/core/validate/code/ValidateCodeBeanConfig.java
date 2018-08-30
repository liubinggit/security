package com.liubing.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liubing.security.core.properties.SecurityProperties;
import com.liubing.security.core.validate.code.image.ImageCodeGenerator;
import com.liubing.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.liubing.security.core.validate.code.sms.SmsCodeGenerator;
import com.liubing.security.core.validate.code.sms.SmsCodeSender;

@Configuration
public class ValidateCodeBeanConfig {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * @ConditionalOnMissingBean 系统里面找imageCodeGenerator的bean
	 * 如果存在则不用new,不存在则会new
	 * @return
	 */
	
	@Bean
	@ConditionalOnMissingBean(name = "imageCodeGenerator")
	public ValidateCodeGenerator imageCodeGenerator() {
		ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
		imageCodeGenerator.setSecurityProperties(securityProperties);
		return imageCodeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "smsCodeGenerator")
	public ValidateCodeGenerator smsCodeGenerator() {
		SmsCodeGenerator ssmCodeGenerator = new SmsCodeGenerator();
		ssmCodeGenerator.setSecurityProperties(securityProperties);
		return ssmCodeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}

}
