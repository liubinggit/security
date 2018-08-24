package com.liubing.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	/**
	 * 伪造mvc的环境
	 */
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void whenQuerySuccess() throws Exception {
		 String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
				// .param(name, values)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk())
				// jsonPath 使用可以网上查询
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
				.andReturn().getResponse().getContentAsString();
		 System.out.println(result);
	}

	@Test
	public void whenGetInfoSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Tom"));
	}
	
	@Test
	public void whenCreateUserSuccess() throws Exception {
		String content = "{ \"username\": \"liubing\", \"password\":\"123123\", \"birthady\":"+new Date().getTime()+"}";
		String result = mockMvc.perform(MockMvcRequestBuilders.post("/user")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andReturn().getResponse().getContentAsString();
		 System.out.println(result);
	}
	
	@Test
	public void whenUpdateSuccess() throws UnsupportedEncodingException, Exception {
		String content = "{ \"username\": \"liubing\", \"password\":\"123123\", \"birthady\":"+new Date().getTime()+"}";
		String result = mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andReturn().getResponse().getContentAsString();
		 System.out.println(result);
	}
	
	@Test
	public void whenDeleteSuccess() throws UnsupportedEncodingException, Exception {
		String result = mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		 System.out.println(result);
	}
}
