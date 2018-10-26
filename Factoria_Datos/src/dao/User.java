package dao;

import java.time.LocalDate;

public class User {
	private int id;
	private String login;
	private String email;
	private String fullName;
	private String status;
	private String privileges;
	private String password;
	private LocalDate lastAccess;
	private LocalDate lastPasswordChange;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public LocalDate getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(LocalDate lastAccess) {
		this.lastAccess = lastAccess;
	}
	
	public LocalDate getLastPasswordChange() {
		return lastPasswordChange;
	}
	public void setLastPasswordChange(LocalDate lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}
	
}
