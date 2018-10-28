/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
/**
*
* @author Julen
*/

public class IDataFactory{

	public static IData getIData() {
		// TODO Auto-generated method stub
		return new IDataImplementation();
	}
}
