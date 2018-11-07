/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * Exception thrown when the password for the login isn't correct
 * 
 * @author Julen
 */
public class PasswordNotOkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordNotOkException(String msg) {

		super(msg);

	}

	public PasswordNotOkException() {
		// TODO Auto-generated constructor stub
	}

}