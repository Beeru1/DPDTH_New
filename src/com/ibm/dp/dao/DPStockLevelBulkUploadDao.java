package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.DPStockLevelBulkUploadDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPStockLevelBulkUploadDao {

	List uploadExcel(InputStream inputstream) throws DAOException;
	String addStockLevel(List list) throws DAOException;
	List<DPStockLevelBulkUploadDto> getALLStockLevelList() throws DPServiceException;
}
