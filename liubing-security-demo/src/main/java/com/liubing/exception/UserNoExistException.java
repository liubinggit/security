package com.liubing.exception;

public class UserNoExistException extends RuntimeException {
	private static final long serialVersionUID = 8293405892452154060L;
	private int id;

	public UserNoExistException(int id) {
		super("user not exist");
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
