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
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPStockLevelBulkUploadDao;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.dto.DPStockLevelBulkUploadDto;
import com.ibm.dp.dto.NSBulkUploadDto;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

 
public class DPStockLevelBulkUploadDaoImpl extends BaseDaoRdbms implements DPStockLevelBulkUploadDao{
	
public static Logger logger = Logger.getLogger(DPStockLevelBulkUploadDaoImpl.class.getName());

public static final String SQL_SELECT_ST_DISTID 	= DBQueries.SQL_SELECT_ST_DISTID;
public static final String SQL_SELECT_OLMID 	= DBQueries.SQL_SELECT_OLMID;
protected static final String SQL_INSERT_STOCK_LEVEL = DBQueries.SQL_INSERT_STOCK_LEVEL;
protected static final String SQL_SELECT_OPEN_STOCK_LEVEL = DBQueries.SQL_SELECT_OPEN_STOCK_LEVEL;
protected static final String SQL_UPDATE_STOCK_LEVEL = DBQueries.SQL_UPDATE_STOCK_LEVEL;
protected static final String SQL_ALL_SELECT_STOCK_LEVEL = DBQueries.SQL_ALL_SELECT_STOCK_LEVEL;


	
	public DPStockLevelBulkUploadDaoImpl(Connection connection) 
	{
		super(connection);
	}
	public List uploadExcel(InputStream inputstream) throws DAOException
	{
		List list = new ArrayList();
		DPStockLevelBulkUploadDto dpStockLevelBulkErrUploadDto  = null;
		String distProdComb ="";
		String distOlmId ="";
		String wareHouseCode="";
		String distId="";
		String warehouseId="";
		ResultSet rst =null;
		boolean validateFlag =true;
		DPStockLevelBulkUploadDto dpStockLevelBulkUploadDto = null;
		String productId="";
		String productName="";
		int minQty = 0;
		int maxQty =0;
		try
		{

			  HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			  HSSFSheet sheet = workbook.getSheetAt(0);
			  Iterator rows = sheet.rowIterator();
			  int totalrows = sheet.getLastRowNum()+1;
			  logger.info("Total Rows == "+sheet.getLastRowNum());
			  if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			  {
				  dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
				  dpStockLevelBulkErrUploadDto.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
      		  list.add(dpStockLevelBulkErrUploadDto);
      		  return list;
			  }
			 /* String checkuploadedFlag = validateAlreadyUploadedCount(totalrows);
			  
			  if((checkuploadedFlag.equalsIgnoreCase("false")))
			  {
				  return "Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed to upload in a day.";
			  }*/
			  
			  int rowNumber = 1;
	          
	        
	          ArrayList checkDuplicate = new ArrayList();
	          int k=1;
	          
	          while (rows.hasNext()) {
	        	  dpStockLevelBulkUploadDto = new DPStockLevelBulkUploadDto();
	        	  HSSFRow row = (HSSFRow) rows.next();
	        	  Iterator cells = row.cellIterator();
	        	  System.out.println("ARTI************rowNumber"+rowNumber);
	        	  System.out.println("ARTI************cells"+cells.toString());
	        	 if(rowNumber>1){
	        	 
	        	  int columnIndex = 0;
	              int cellNo = 0;
	              if(cells != null)
	              {
	            	distProdComb ="";
	            	distOlmId ="";
	    			productName="";
	    			distId ="";
	    			productId="";
	            	while (cells.hasNext()) {
	        		  if(cellNo < 5)
	        		  {
	        			  cellNo++;
	        			  HSSFCell cell = (HSSFCell) cells.next();
	        			  
	        			  	columnIndex = cell.getColumnIndex();
		              		if(columnIndex > 4)
		              		{
		              			validateFlag =false;
		              			dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
		              			dpStockLevelBulkErrUploadDto.setErr_msg("File should contain only five data column");
		              			list.add(dpStockLevelBulkErrUploadDto);
		              			return list;
		              		}
		              		String cellValue = null;
		        		  
		            		switch(cell.getCellType()) {
			            		case HSSFCell.CELL_TYPE_NUMERIC:
			            			cellValue = String.valueOf((double)cell.getNumericCellValue());
			            			if(cellValue.contains(".0")){			            				
			            				cellValue=cellValue.substring(0,cellValue.indexOf(".0"));
			            			}
			            		break;
			            		case HSSFCell.CELL_TYPE_STRING:
			            			cellValue = cell.getStringCellValue();
				            	break;
		            		}
		            	/*	if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
		            		{
		            			dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
		            			dpStockLevelBulkErrUploadDto.setErr_msg("File should contain All Required Values");
		              			 list.add(dpStockLevelBulkErrUploadDto);
		              			 return list;
		            			
		            		}
		            		*/
		            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
		            		if(cellNo==1){
		            			boolean validate = validateOlmId(cellValue.trim());
		            			System.out.println("validate"+validate);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
		            				dpStockLevelBulkErrUploadDto.setErr_msg("Invalid OLM Id at row No: "+(rowNumber)+".It should be 8 character.");
			              			list.add(dpStockLevelBulkErrUploadDto);
		            				
		            			}else if(validateOlmdistId(cellValue)){
			            				distOlmId=cellValue;
				            			distProdComb = cellValue;
			            				dpStockLevelBulkUploadDto.setDistOlmId(cellValue);
			            				}else
			            				{
			            					dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
			            					dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Distributed OLM Id at row No : "+(rowNumber)+".");
					              			list.add(dpStockLevelBulkErrUploadDto);
			            					
			            				}
			            	
		            		}else if(cellNo==2){
		            			if(validateProductId(cellValue)){
		            			
		            			rst = validateDistOlmId(cellValue,distOlmId);
		            			if(rst.next()){
		            				System.out.println("ARTI&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		            				distId = rst.getString("ACCOUNT_ID");
	                  				productId = rst.getString("PRODUCT_ID");
	                  				System.out.println("ARTI&&&&&&&&&&&&&&&&&&&&&&&&&distId&&&&&&&&&"+distId+"productId"+productId);
	                  				productName=cellValue;
			            			distProdComb += "#"+cellValue;
			            			dpStockLevelBulkUploadDto.setProductName(cellValue);
	                  				//dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
	                  				dpStockLevelBulkUploadDto.setDistId(distId);
                  					dpStockLevelBulkUploadDto.setProductId(productId);
                  					//list.add(dpStockLevelBulkUploadDto);
		            				
		            				
		            			}else{
	                  				validateFlag =false;
	                  				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
	                  				dpStockLevelBulkErrUploadDto.setErr_msg("Product Name does not belong to the Circle of Distributor at row No: "+(rowNumber)+".");;
	                        			list.add(dpStockLevelBulkErrUploadDto);
	                  				
	                  			}
		            			}else{
		            				validateFlag =false;
                  					dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
                  					dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Product Name at row no.: "+(rowNumber));
                      				list.add(dpStockLevelBulkErrUploadDto);
		            				
		            			}
		            		}else if(cellNo==3){
		            			boolean validate = validateNumericQty(cellValue);
		            			System.out.println("validate cell3 "+validate);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
		            				dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Min Qty at row No: "+(rowNumber)+". It should be only numeric value.");
			              			list.add(dpStockLevelBulkErrUploadDto);
			              			dpStockLevelBulkUploadDto.setMinQty("0");
		            			}else{
		            				//validateFlag =true;
			            			dpStockLevelBulkUploadDto.setMinQty(cellValue.trim());
			            		}
		            			
		            		}else if(cellNo==4){
		            			boolean validate = validateNumericQty(cellValue);
		            			System.out.println("validate cell4 "+validate);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
		            				dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Max Day at row No: "+(rowNumber)+". It should be only numeric value.");
			              			list.add(dpStockLevelBulkErrUploadDto);
		            				
		            			}else if(!validateMinMaxQty(dpStockLevelBulkUploadDto.getMinQty(),cellValue.trim())){
		            				validateFlag =false;
		            				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
		            				dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Min Day at row No: "+(rowNumber)+" greater than Max Day.");
			              			list.add(dpStockLevelBulkErrUploadDto);
			            		}else if(!validateMaxQty(cellValue.trim())){
			            			validateFlag =false;
		            				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
		            				dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Max Day at row No: "+(rowNumber)+" should be less than 1000.");
		            				list.add(dpStockLevelBulkErrUploadDto);
		            				
			            		}else {
			            			//validateFlag =true;
			            			dpStockLevelBulkUploadDto.setMaxQty(cellValue.trim());
			            		}
		            			
		            		}
		            		
		            		
	        		  }
	        		  else
	        		  {
	        			  validateFlag =false;
	        			  dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
	        			  dpStockLevelBulkErrUploadDto.setErr_msg("File should contain only five data column");
            			 list.add(dpStockLevelBulkErrUploadDto);
            			 return list;
	        		  }
	        		  
	        		  
	            	}
	            	if(cellNo !=5){
	            		validateFlag =false;
	            		dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
	            		dpStockLevelBulkErrUploadDto.setErr_msg("File should contain five data column");
	            		List list2 = new ArrayList();
	            		list2.add(dpStockLevelBulkErrUploadDto);
            			return list2;
	            	}
	            	if(distProdComb !=null && !distProdComb.equalsIgnoreCase("null") && !distProdComb.equalsIgnoreCase("")){
	        	  	if(checkDuplicate.contains(distProdComb))
          		{
	        	  		validateFlag =false;
	        	  		dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
	        	  		dpStockLevelBulkErrUploadDto.setErr_msg("File contains duplicate Entries For Distributor : "+distProdComb.split("#")[0]);
            			list.add(dpStockLevelBulkErrUploadDto);
	        	  		
	        	 	}else{
	        	 		validateFlag = true;
          			checkDuplicate.add(distProdComb);
          		}
	            	}
	        	  	
	        	  	
	        	  
	        	  rowNumber++;
	            }
	            else
	            {
	            	dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
	            	dpStockLevelBulkErrUploadDto.setErr_msg("File should contain only five data column");
       			list.add(dpStockLevelBulkErrUploadDto);
       			return list;
	            }
	              logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  "+validateFlag);
	        	 if(validateFlag){
	        		  dpStockLevelBulkUploadDto.setErr_msg("SUCCESS");
	        		  list.add(dpStockLevelBulkUploadDto);  		        		  
	        	  }
	              
	        }else{

	        	//For Header Checking  
	        	int columnIndex = 0;
	              int cellNo = 0;
	              if(cells != null)
	              {
	            	  while (cells.hasNext()) {
		        		  if(cellNo < 5)
		        		  {
		        			  cellNo++;
		        			  HSSFCell cell = (HSSFCell) cells.next();
		        			  
		        			  	columnIndex = cell.getColumnIndex();
			              		if(columnIndex > 4)
			              		{
			              			validateFlag =false;
			              			dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
			              			dpStockLevelBulkErrUploadDto.setErr_msg("File should contain only five data column");
			              			List list2 = new ArrayList();
				            		list2.add(dpStockLevelBulkErrUploadDto);
			              			return list2;
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
			            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
			            		{
			            			validateFlag =false;
			              			dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
			              			dpStockLevelBulkErrUploadDto.setErr_msg("File should contain All Required Values");
			              			List list2 = new ArrayList();
				            		list2.add(dpStockLevelBulkErrUploadDto);
			              			return list2;
			               		}
			            		
			            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
			            		if(cellNo==1){
			            			if(!"distributor olm id".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
			            				dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Header For Distributor OLM ID");
				              			list.add(dpStockLevelBulkErrUploadDto);
			            				
			            			}
			            		}else if(cellNo==2){
			            			if(!"product name".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
			            				dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Header For Product Name");
				              			list.add(dpStockLevelBulkErrUploadDto);
			            				
			            			}
			            		}else if(cellNo==3){
			            			if(!"min day".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
			            				dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Header For Min Day");
				              			list.add(dpStockLevelBulkErrUploadDto);
			            				
			            			}
			            		}else if(cellNo==4){
			            			if(!"max day".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
			            				dpStockLevelBulkErrUploadDto.setErr_msg("Invalid Header For Max Day");
				              			list.add(dpStockLevelBulkErrUploadDto);
			            				
			            			}
			            		}
		        		  }else{
		        			  validateFlag =false;
		        			  dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
		        			  dpStockLevelBulkErrUploadDto.setErr_msg("File should contain only five data Headers");
		        			  List list2 = new ArrayList();
			            	list2.add(dpStockLevelBulkErrUploadDto);
		              		return list2;
		        		  }
	            	  }
	            	  if(cellNo !=5){
		            		validateFlag =false;
		            		dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto(); 
		            		dpStockLevelBulkErrUploadDto.setErr_msg("File should contain five data Headers");
		            		List list2 = new ArrayList();
		            		list2.add(dpStockLevelBulkErrUploadDto);
	              			return list2;
		            	}
	              } else
		            {
		            	dpStockLevelBulkErrUploadDto = new DPStockLevelBulkUploadDto();
		            	dpStockLevelBulkErrUploadDto.setErr_msg("File should contain five data column");
		            	  List list2 = new ArrayList();
			            	list2.add(dpStockLevelBulkErrUploadDto);
		              		return list2;
		            }
	        	rowNumber ++;
	         }
	        	 k++;
	     }
	         logger.info("K == "+k);
	    if(k==1){
	        	 dpStockLevelBulkUploadDto = new DPStockLevelBulkUploadDto();
	        	 dpStockLevelBulkUploadDto.setErr_msg("Blank File can not be uploaded!!");
      		  list.add(dpStockLevelBulkUploadDto);  	
	        }
	      
	    }
		catch (Exception e) {
        	
        	logger.info(e);
        	throw new DAOException("Please Upload a valid Excel Format File");
        }finally
		{
        		DBConnectionManager.releaseResources(null, rst);
        					
		}
		return list;
	}
	public boolean validateOlmId(String str)
	{
		if(str.length() != 8)
		{
			return false;
		}
	
		
		return true;
	}
	
	public boolean validateNumericQty(String str)
	{
		//if(!ValidatorUtility.isValidNumber(str)) 
		if(!ValidatorUtility.isValidNumber(str)) 
		{
			return false;
		}
		return true;
	}
	
	public boolean validateMaxQty(String maxQty)
	{
		System.out.println("maxQty"+maxQty);
		if(Integer.parseInt(maxQty)>999)
		{
			return false;
		}
		return true;
	}
	
	public boolean validateMinMaxQty(String minQty, String maxQty)
	{
		
		if(Integer.parseInt(minQty)>Integer.parseInt(maxQty))
		{
			return false;
		}
		return true;
	}
	
	public ResultSet validateDistOlmId(String productName,String distOlmId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			logger.info("inside --- validateDistOlmId Product name == "+productName +" and dist OLM id == "+distOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_ST_DISTID);
			pstmt.setString(1,productName.toUpperCase());
			pstmt.setString(2,distOlmId);
			rs =pstmt.executeQuery();
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateDistOlmId method--"+e.getMessage());
		}
	/*	finally
		{
			//DBConnectionManager.releaseResources(pstmt, null);
		}*/
		
		
		
		return rs;
	}
	
	
	
	
	public boolean validateOlmdistId(String distOlmId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		boolean flag = false;
		try{
			
			logger.info("inside --- validateOlmId dist OLM id == "+distOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_OLMID);
			pstmt.setString(1,distOlmId);
			rs =pstmt.executeQuery();
			if(rs.next()){
				
				count = rs.getInt(1);
			}
			if(count >=1)
				flag =  true;
			else
				flag =  false;
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateDistOlmId method--"+e.getMessage());
		}
		return flag;
	}
	
	public boolean validateProductId(String productName)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		boolean flag = false;
		try{
			
			logger.info("inside --- validateOlmId productId == "+productName);
			pstmt = connection.prepareStatement(DBQueries.SQL_SELECT_PRODUCTID);
			pstmt.setString(1,productName);
			rs =pstmt.executeQuery();
			if(rs.next()){
				
				count = rs.getInt(1);
			}
			if(count >=1)
				flag =  true;
			else
				flag =  false;
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateDistOlmId method--"+e.getMessage());
		}
		return flag;
	}
	
	
	public String addStockLevel(List list)
	{
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String returnstr="";
		logger.info("Size--"+list.size());
		
		ResultSet rst=null;
		DPStockLevelBulkUploadDto dpStockBulkUploadDto = null;
	try
	{	
		pstmt = connection.prepareStatement(SQL_SELECT_OPEN_STOCK_LEVEL);
		pstmt1 = connection.prepareStatement(SQL_UPDATE_STOCK_LEVEL);
		pstmt2 = connection.prepareStatement(SQL_INSERT_STOCK_LEVEL);
		Iterator itr = list.iterator();
		int updaterows = 0;
		
		while(itr.hasNext())
		{
			dpStockBulkUploadDto = (DPStockLevelBulkUploadDto) itr.next();
			pstmt.setString(1, dpStockBulkUploadDto.getDistId());
			pstmt.setString(2, dpStockBulkUploadDto.getProductId());
			rst = pstmt.executeQuery();
			if(rst.next()){
				if(rst.getInt(1)>=1){
					
					pstmt1.setInt(1, Integer.parseInt(dpStockBulkUploadDto.getMinQty()));
					pstmt1.setInt(2, Integer.parseInt(dpStockBulkUploadDto.getMaxQty()));
					pstmt1.setTimestamp(3, Utility.getCurrentTimeStamp());
					pstmt1.setString(4, dpStockBulkUploadDto.getDistId());
					pstmt1.setString(5, dpStockBulkUploadDto.getProductId());
					pstmt1.executeUpdate();
					
			}else{
				pstmt2.setString(1, dpStockBulkUploadDto.getDistId());
				pstmt2.setString(2, dpStockBulkUploadDto.getDistOlmId());
				pstmt2.setString(3, dpStockBulkUploadDto.getProductId());
				pstmt2.setInt(4, Integer.parseInt(dpStockBulkUploadDto.getMinQty()));
				pstmt2.setInt(5, Integer.parseInt(dpStockBulkUploadDto.getMaxQty()));
				pstmt2.setTimestamp(6, Utility.getCurrentTimeStamp());
				pstmt2.executeUpdate();
				
			}
				
		}
			updaterows++;	
		}
		returnstr = "Total "+updaterows+" Records Have Been Uploaded Successfully";
		
		
	}
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
		DBConnectionManager.releaseResources(pstmt, rst);
		DBConnectionManager.releaseResources(pstmt1, null);
		DBConnectionManager.releaseResources(pstmt2, null);
		
	}
		
		return returnstr;
	}
	
	
	public List<DPStockLevelBulkUploadDto> getALLStockLevelList() {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
	
		List<DPStockLevelBulkUploadDto> stockLevelBulkUploadList= new ArrayList<DPStockLevelBulkUploadDto>();
		
		try {
			
			//String sql = "SELECT DIST_ID, DIST_OLM_ID, SECURITY_AMOUNT, LOAN_AMOUNT   FROM DP_DIST_SECURITY_LOAN" ;
			db2conn = DBConnectionManager.getDBConnection();
			ps = db2conn.prepareStatement(SQL_ALL_SELECT_STOCK_LEVEL);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				DPStockLevelBulkUploadDto recDTO = new DPStockLevelBulkUploadDto();
				
				recDTO.setDistOlmId(rs.getString("DIST_OLM_ID"));
				recDTO.setProductName(rs.getString("product_name"));
				recDTO.setMinQty(rs.getString("MIN_DAYS"));
				recDTO.setMaxQty(rs.getString("MAX_DAYS"));
				recDTO.setCircleName(rs.getString("CIRCLE_NAME"));
				stockLevelBulkUploadList.add(recDTO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Product Security  Exception message: "
					+ e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (db2conn != null) {
					db2conn.close();
				}
			} catch (Exception e) {
			}
		}
		return stockLevelBulkUploadList;
		
	}
	
	
	
	}

