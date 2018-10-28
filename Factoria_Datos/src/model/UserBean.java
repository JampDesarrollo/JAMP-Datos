

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 *
 * @author 2dam
 */
public class UserBean implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String login;
    private String email;
    private String fullName;
    private String password;
    private enum status{DISABLED, ENABLED};
    private enum privilege{USER, ADMIN};
    private Timestamp lastAccess;
    private Timestamp lastPasswordChange;
    
    UserBean(String login, String email, String fullName, String passw, 
            Timestamp lastAccess, Timestamp lasPasswChange) {
        this.login = login;
        this.email = email;
        this.fullName = fullName;
        this.password = passw;
        this.lastAccess = lastAccess;
        this.lastPasswordChange = lasPasswChange;        
    }

    public UserBean() {
		
	}

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

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Timestamp lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Timestamp getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Timestamp lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }
    
    
}
	
	/*public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("NIF: ").append(nif).append("\n");
		sb.append("Name: ").append(name).append("\n");
		sb.append("Surname: ").append(surname).append("\n");
		sb.append("Date of birth: ").append(this.getDateOfBirthStr()).append("\n");
		sb.append("Gender: ").append(gender).append("\n");		
		return sb.toString();
	}*/

