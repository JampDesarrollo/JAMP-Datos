package codes;

import java.io.Serializable;

import model.UserBean;
/**
*
* @author Ander
*/
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int USER_SIGNUP = 1;
    private static final int USER_SIGNUP_LOGIN_EXISTS = 11;
    private static final int USER_SIGNUP_ERROR = -1;
    private static final int USER_LOGIN = 2;
    private static final int USER_LOGIN_PASSW_NOT_OK = 21;
    private static final int USER_LOGIN_LOGIN_NOT_OK = 22;
    private static final int USER_LOGIN_ERROR = -2;
	
	private int code;
	private Object user;
	
	public Message(int code, UserBean user) {
		super();
		this.code = code;
		this.user = user;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

}
