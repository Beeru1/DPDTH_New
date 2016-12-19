package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.NSBulkUploadDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface NSBulkUploadDao {
	List uploadExcel(InputStream inputstream) throws DAOException;
	String updateStockQty(List list) throws DAOException;
	List<NSBulkUploadDto> getALLStockLevelList() throws DAOException;
}
