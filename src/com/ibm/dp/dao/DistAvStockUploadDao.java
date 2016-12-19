package com.ibm.dp.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.NonSerializedToSerializedDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DistAvStockUploadDao 
{
	//public List uploadExcel(FormFile myXls)throws DAOException;
	//public String insertAVStockinDP(Connection conDP, List list)throws DAOException;
	public ArrayList<DuplicateSTBDTO> uploadData(String stbSLNos)throws DAOException;
	public String insertAVStockinDP(Connection connection, List list, int intCatCode, long lnDistID, int intCircleID, int intProductID)throws DAOException;
	public List<List<CircleDto>> getInitDataDAO(Connection connection, int intCircleID)throws DAOException;
	public List uploadExcel(FormFile myXls, int intCatCode)throws DAOException;
	//added by aman
	public List<NonSerializedToSerializedDTO> serializedConversion(Connection con,String strOLMId)throws DAOException;
	
	public List<NonSerializedToSerializedDTO> getALLProdSerialData(Connection connection, String circleId)throws DAOException ;
	//end of changes by aman
	public List uploadSerializedExcel(InputStream myxls, String distOlmID, String circleId)throws DAOException ;
	public String convertNserToSerStock(Connection connection, List list, String distOlmID, String circleId)throws DAOException ;
}
