package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPProductSecurityListDao {

	List uploadExcel(InputStream inputstream) throws DAOException;
	String updateProductSecurityList(List list,String userId) throws DAOException;
	List<DPProductSecurityListDto> getALLProuductSecurityList() throws DPServiceException;
}
