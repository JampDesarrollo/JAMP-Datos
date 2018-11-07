/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * This class is a factory for IData interface implementing objects.
 * 
 * @author Julen
 */

public class IDataFactory {
	/**
	 * It returns IData interface implementing object
	 * 
	 * @return An object implementing IData.
	 */
	public static IData getIData() {
		// TODO Auto-generated method stub
		return new IDataImplementation();
	}
}
