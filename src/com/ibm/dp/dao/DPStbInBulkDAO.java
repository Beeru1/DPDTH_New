package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DPSTBHistDTO;
import com.ibm.dp.dto.DPStbInBulkDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPStbInBulkDAO {

	public List uploadExcel(FormFile myXls) throws DAOException;
	
	public List uploadExcelHistory(FormFile myXls) throws DAOException;

	public ArrayList getSerialNoList(DPStbInBulkDTO dpStbInBulkDTO)
			throws DAOException;
	
	public ArrayList getSerialNoListHistory(DPSTBHistDTO dpStbInBulkDTO)
	throws DAOException;

}
