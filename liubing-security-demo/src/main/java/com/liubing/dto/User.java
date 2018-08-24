package com.liubing.dto;

import java.sql.Date;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonView;

public class User {

	/**
	 * 指定视图, 使用对应的方法时, 有些数据不显示
	 * 写多个接口 使用 @JsonView
	 * 校验的使用(hibernate validator)
	 * 属性上加例如@NotBlank  方法使用上@Valid,方法会拦截不成功的,不让进入方
	 * 法体,如果还让进入 BindingResult
	 * 
	 * @author lb
	 *
	 */
	public interface UserSimpleView {};

	public interface UserDetaView extends UserSimpleView {};

	private int id;
	private String username;
	@NotBlank
	private String password;
	private Date birthady; //传给前台用时间戳,由前台做处理

	public User() {
	}

	public User(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	@JsonView(UserSimpleView.class)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonView(UserDetaView.class)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonView(UserSimpleView.class)
	public Date getBirthady() {
		return birthady;
	}

	public void setBirthady(Date birthady) {
		this.birthady = birthady;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", birthady=" + birthady + "]";
	}

}
