/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import messageuserbean.UserBean;

/**
 * DAO interface encapsulating methods for server connections
 * 
 * @author Markel
 * @author Julen
 * @author Ander
 * @author Paula
 */

public interface IData {
	/**
	 * This method signs up a new user on the data base
	 * 
	 * @param user The UserBean object to be added.
	 * @throws UserLoginExistException The Exception thrown in case login already
	 *                                 exists
	 * @throws Exception               If there is any error while processing.
	 */
	public void userSignUp(UserBean user) throws UserLoginExistException, Exception;

	/**
	 * This method returns the information of the UserBean
	 * 
	 * @param user The UserBean that want to log in.
	 * @return The whole UserBean
	 * @throws UserNotExistException  The Exception thrown in case login doesn't
	 *                                exist
	 * @throws PasswordNotOkException The Exception thrown in case password typed
	 *                                for this login is incorrect
	 * @throws Exception              If there is any error while processing.
	 */
	public UserBean userLogin(UserBean user) throws UserNotExistException, PasswordNotOkException, Exception;

}
