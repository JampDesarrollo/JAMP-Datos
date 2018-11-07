/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * Exception thrown when the user login exists on database
 * 
 * @author Ander
 */
public class UserLoginExistException extends Exception {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public UserLoginExistException(String msg) {
		super(msg);
	}

	public UserLoginExistException() {
		// TODO Auto-generated constructor stub
	}
}