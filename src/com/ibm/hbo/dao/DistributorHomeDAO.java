package com.ibm.hbo.dao;

import java.util.ArrayList;

import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;

public interface DistributorHomeDAO {
	public ArrayList findByPrimaryKey(long distId)throws HBOException, DAOException;

	public ArrayList findByPrimaryKey(long id, String string)throws HBOException, DAOException;
}
