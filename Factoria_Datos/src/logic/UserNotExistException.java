/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * Exception thrown when the login doesn't exist
 * @author Julen
 */
public class UserNotExistException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotExistException(String msg){
    
        super(msg);
        
    }

	public UserNotExistException() {
		// TODO Auto-generated constructor stub
	}
    
}