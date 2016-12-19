package com.ibm.dp.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface AvStockUploadDao 
{
	public List uploadExcel(FormFile myXls)throws DAOException;
	public ArrayList<DuplicateSTBDTO> uploadData(String stbSLNos)throws DAOException;
	public String insertAVStockinDP(Connection connection, List list) throws DAOException;
}
