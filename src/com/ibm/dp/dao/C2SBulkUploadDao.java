package com.ibm.dp.dao;

import java.io.InputStream;

import com.ibm.virtualization.recharge.exception.DAOException;

public interface C2SBulkUploadDao {

	
	String uploadExcel(InputStream inputstream) throws DAOException;
}
