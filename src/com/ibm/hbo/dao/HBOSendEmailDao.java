package com.ibm.hbo.dao;

import java.util.ArrayList;

import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.hbo.exception.HBOException;

public interface HBOSendEmailDao {
	
	public ArrayList getSubjectList()throws HBOException, DAOException;
	
	public String getTSMEmailId(long loginId)throws HBOException, DAOException;
}
