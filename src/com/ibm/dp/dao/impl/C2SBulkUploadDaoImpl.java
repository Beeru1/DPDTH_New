package com.ibm.dp.dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.C2SBulkUploadDao;
import com.ibm.dp.dto.C2SBulkUploadDTO;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

 
public class C2SBulkUploadDaoImpl extends BaseDaoRdbms implements C2SBulkUploadDao{
	
public static Logger logger = Logger.getLogger(C2SBulkUploadDaoImpl.class.getName());

public static final String SQL_INSERT_SERIAL_NUMER 	= DBQueries.SQL_INSERT_SERIAL_NUMER;
public static final String SQL_SELECT_SERIAL_NUMER 	= DBQueries.SQL_SELECT_SERIAL_NUMER;
public static final String SQL_SELECT_UPLOADED_COUNT 	= DBQueries.SQL_SELECT_UPLOADED_COUNT;
public static final String SQL_C2S_UPDATE_CONFIG_MASTER 	= DBQueries.SQL_C2S_UPDATE_CONFIG_MASTER;
public static final String SQL_C2S_UPDATE_CONFIG_MASTER_NEW 	= DBQueries.SQL_C2S_UPDATE_CONFIG_MASTER_NEW;


	
	public C2SBulkUploadDaoImpl(Connection connection) 
	{
		super(connection);
	}
	public String uploadExcel(InputStream inputstream) 
	{
		String str  = "";
		try
		{
			  HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			  HSSFSheet sheet = workbook.getSheetAt(0);
			  Iterator rows = sheet.rowIterator();
			  int totalrows = sheet.getLastRowNum()+1;
			  if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			  {
				  return "Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.";
			  }
			  String checkuploadedFlag = validateAlreadyUploadedCount(totalrows);
			  
			  if((checkuploadedFlag.equalsIgnoreCase("false")))
			  {
				  return "Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed to upload in a day.";
			  }
			  
			  int rowNo = 0;
	          int rowNumber = 1;
	          
	          C2SBulkUploadDTO c2SBulkUploadDTO = null;
	          List<C2SBulkUploadDTO> arrList = new ArrayList<C2SBulkUploadDTO>();
	          ArrayList checkDuplicate = new ArrayList();
	          int k=1;
	          
	          while (rows.hasNext()) {
	        	  c2SBulkUploadDTO = new C2SBulkUploadDTO();
	        	  HSSFRow row = (HSSFRow) rows.next();
	        	  System.out.println("************"+row);
	        	  Iterator cells = row.cellIterator();
	        	  int columnno = 0;
	        	  
	        	/*  if(rowNo == 0){
	        		    while (cells.hasNext()) {
	        		    	if(columnno == 0)
	        		    	{
		                        HSSFCell cell = (HSSFCell) cells.next();
		                        logger.info("----"+cell.toString());
		                        if(!cell.toString().equalsIgnoreCase(Constants.C2S_BULK_UPLOAD_HEADER))
		                        {
		                        	return "Invalid File as header is not correct";
		                        }
		                        columnno++;
	        		    	}
	        		    	else
	        		    	{
	        		    		return "Invalid File as file have more then one column";
	        		    	}
	                        //headerList.add(cell.toString());
	                    }
	                    //excelParseValidator.validateCellHeaders(headerList);
	                	rowNo++;
	                	continue;
	            	}*/
	        	  
	        	 int columnIndex = 0;
	             int cellNo = 0;
	            if(cells != null)
	            {
	        	  while (cells.hasNext()) {
	        		  if(cellNo == 0)
	        		  {
	        			  cellNo++;
	        			  HSSFCell cell = (HSSFCell) cells.next();
	        			  
	        			  	columnIndex = cell.getColumnIndex();
		              		if(columnIndex > 0)
		              		{
		              			return "File should contain only one data column";
		              		}
		              		String cellValue = null;
		        		  
		            		switch(cell.getCellType()) {
			            		case HSSFCell.CELL_TYPE_NUMERIC:
			            			cellValue = String.valueOf((long)cell.getNumericCellValue());
			            		break;
			            		case HSSFCell.CELL_TYPE_STRING:
			            			cellValue = cell.getStringCellValue();
				            	break;
		            		}
		            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue))
		            		{
		            			return "File should contain only one data column";
		            		}
		            		boolean validate = validateCell(cellValue);
		            		if(checkDuplicate.contains(cellValue))
		            		{
		            			return "File contains duplicate serial Number:"+cellValue;
		            		}
		            		
		            		if(!validate)
		            		{
		            			return "Invalid Serial Number at row No: "+(rowNumber)+". It should be 11 digit number.";
		            		}
		            		else
		            		{
		            			c2SBulkUploadDTO.setSrno(cellValue);
		            			checkDuplicate.add(cellValue);
		            		}
	        		  }
	        		  else
	        		  {
	        			  return "File should contain only one data column";
	        		  }
	        		  rowNumber++;
	            	}
	            }
	            else
	            {
	            	 return "File should contain only one data column";
	            }
	        	  arrList.add(c2SBulkUploadDTO);
	        	  k++;
	        }
	          str = insertSerialNumbers(arrList,checkuploadedFlag);
	          
	    }
		catch (Exception e) {
        	
        	logger.info(e);
        	
        }
		return str;
	}
	public boolean validateCell(String str)
	{
		if(str.length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH)
		{
			return false;
		}
		else if(!ValidatorUtility.isValidNumber(str))
		{
			return false;
		}
		
		
		return true;
	}
	public String insertSerialNumbers(List<C2SBulkUploadDTO> list , String checkuploadedFlag)
	{
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String returnstr="";
		logger.info("Size--"+list.size());
		String srno="";
	try
	{	
		pstmt = connection.prepareStatement(SQL_INSERT_SERIAL_NUMER);
		Iterator<C2SBulkUploadDTO> itt = list.iterator();
		
		int totalno=0;
		int updaterows = 0;
		while(itt.hasNext())
		{
			srno = ((C2SBulkUploadDTO) itt.next()).getSrno();
			pstmt.setString(1, srno);
			updaterows = pstmt.executeUpdate();
			totalno = totalno + updaterows;
		}
		returnstr = "Total "+totalno+" Serial Numbers have been Uploaded Successfully";
		
		if(checkuploadedFlag.equalsIgnoreCase(Constants.C2S_BULK_UPLOAD_ALREADY_EXIST_TODAY))
		{
			pstmt1  =  connection.prepareStatement(SQL_C2S_UPDATE_CONFIG_MASTER);
		}
		else
		{
			pstmt1  =  connection.prepareStatement(SQL_C2S_UPDATE_CONFIG_MASTER_NEW);
		}
		pstmt1.setString(1, (totalno+""));
		pstmt1.executeUpdate();
	}
	/*catch(com.ibm.websphere.ce.cm.DuplicateKeyException sqlex)
	{
		logger.info("Inside Duplicate Key Exception----");
		try
		{
			connection.rollback();
			if(sqlex.getErrorCode() == -803)
			{
				return "Serial Number "+ srno+" has already been uploaded";
			}
			else
			{
				return "Internal Error Occured.Please try later";
			}
		}*/
		//catch(Exception ex)
		//{
			//logger.error(ex);
		//}
	//}
	catch (Exception e) 
	{
		try
		{
			e.printStackTrace();
			connection.rollback();
			logger.info("Inside main Exception");
			return "Internal Error Occured.Please try later";
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
	}
	finally
	{
		DBConnectionManager.releaseResources(pstmt, null);
		DBConnectionManager.releaseResources(pstmt1, null);
	}
		
		return returnstr;
	}

	public String validateAlreadyUploadedCount(int rows)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String returnFlag="";
		
		int maxlimit = Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE));
		
		try
		{	
			pstmt = connection.prepareStatement(SQL_SELECT_UPLOADED_COUNT);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				returnFlag=Constants.C2S_BULK_UPLOAD_ALREADY_EXIST_TODAY;
				count = rs.getInt("CONFIG_VALUE");
				if((rows + count) > maxlimit)
				{
					return "false";
				}
			}
			else
			{
				returnFlag=Constants.C2S_BULK_UPLOAD_ALREADY_NOT_EXIST_TODAY;
				if(rows > maxlimit)
				{
					return "false";
				}
			}
		}
		catch (Exception e) 
		{
			logger.info("Inside main Exception of validateAlreadyUploadedCount method--"+e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rs);
		}
			return returnFlag;
		}
	}

