/**
 * 
 */
package com.ibm.dp.cpedp;

import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * @author Harbans Singh Bisht
 *
 */
public interface DPCPEDBInteraction 
{
	public boolean updateAssignedStatus() throws Exception;
	public boolean updateRepairedStatus() throws Exception;
	public boolean updateUnAssignedStatus() throws Exception;
	public String getControlParameterFlag(String paramName) throws DAOException;
}
