package com.ibm.dp.dao;
import com.ibm.virtualization.recharge.exception.DAOException;
public interface STBStatusDao {
	int checkSrNoAvailibility(String strNo)throws DAOException;
	String updateStatus(String strNos,String status)throws DAOException;
}
