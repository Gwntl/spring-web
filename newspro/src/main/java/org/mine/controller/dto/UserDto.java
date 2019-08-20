package org.mine.controller.dto;

public class UserDto {

	private String username;
	private String password;
	private String id;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "UserDto [username=" + username + ", password=" + password + ", id=" + id + "]";
	}
}
