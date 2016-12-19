package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPInterSSDTransferBulkUploadDao {

	List uploadExcel(InputStream inputstream) throws DAOException;
	String updateInterSSDListTransfer(List list, String userId) throws DAOException;
	
}
