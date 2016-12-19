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
import com.ibm.dp.dao.NSBulkUploadDao;
import com.ibm.dp.dto.C2SBulkUploadDTO;
import com.ibm.dp.dto.DPStockLevelBulkUploadDto;
import com.ibm.dp.dto.NSBulkUploadDto;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class NSBulkUploadDaoImpl extends BaseDaoRdbms implements NSBulkUploadDao{
	
	public static Logger logger = Logger.getLogger(NSBulkUploadDaoImpl.class.getName());

	public static final String SQL_SELECT_DISTID 	= DBQueries.SQL_SELECT_DISTID;
	public static final String SQL_UPDATE_OPEN_STOCK 	= DBQueries.SQL_UPDATE_OPEN_STOCK;
	public static final String SQL_SELECT_OPEN_STOCK 	= DBQueries.SQL_SELECT_OPEN_STOCK;
	public static final String SQL_INSERT_OPEN_STOCK 	= DBQueries. SQL_INSERT_OPEN_STOCK;
	public static final String SQL_SELECT_BULKUPLOAD_STOCK 	= DBQueries. SQL_SELECT_BULKUPLOAD_STOCK;	
		public NSBulkUploadDaoImpl(Connection connection) 
		{
			super(connection);
		}
		public List uploadExcel(InputStream inputstream) 
		{
			List list = new ArrayList();
			NSBulkUploadDto nSBulkUploadErrDTO = null;
			String distProdComb ="";
			String distOlmId ="";
			String productName="";
			String distId="";
			String productId="";
			ResultSet rst =null;
			boolean validateFlag =true;
			NSBulkUploadDto nSBulkUploadDTO = null;
			try
			{
				  HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
				  HSSFSheet sheet = workbook.getSheetAt(0);
				  Iterator rows = sheet.rowIterator();
				  int totalrows = sheet.getLastRowNum()+1;
				  logger.info("Total Rows == "+sheet.getLastRowNum());
				  if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
				  {
					 
					  nSBulkUploadErrDTO = new NSBulkUploadDto();
					  nSBulkUploadErrDTO.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
	        		  list.add(nSBulkUploadErrDTO);
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
		        	  nSBulkUploadDTO = new NSBulkUploadDto();
		        	  HSSFRow row = (HSSFRow) rows.next();
		        	  Iterator cells = row.cellIterator();
		        	  
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
		        		  if(cellNo < 3)
		        		  {
		        			  cellNo++;
		        			  HSSFCell cell = (HSSFCell) cells.next();
		        			  
		        			  	columnIndex = cell.getColumnIndex();
			              		if(columnIndex > 2)
			              		{
			              			validateFlag =false;
			              			nSBulkUploadErrDTO = new NSBulkUploadDto();
			              			nSBulkUploadErrDTO.setErr_msg("File should contain only three data column");
			              			list.add(nSBulkUploadErrDTO);
			              			return list;
			              		}
			              		String cellValue = null;
			        		  
			            		switch(cell.getCellType()) {
				            		case HSSFCell.CELL_TYPE_NUMERIC:
				            			cellValue = String.valueOf((double)cell.getNumericCellValue());
				            			
				            		break;
				            		case HSSFCell.CELL_TYPE_STRING:
				            			cellValue = cell.getStringCellValue();
					            	break;
			            		}
			            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
			            		{
			            			nSBulkUploadErrDTO = new NSBulkUploadDto(); 
			            			nSBulkUploadErrDTO.setErr_msg("File should contain All Required Values");
			              			 list.add(nSBulkUploadErrDTO);
			              			 return list;
			            			
			            		}
			            		
			            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
			            		if(cellNo==1){
			            			boolean validate = validateOlmId(cellValue.trim());
			            			if(!validate)
				            		{
			            				validateFlag =false;
			            				nSBulkUploadErrDTO = new NSBulkUploadDto(); 
			            				nSBulkUploadErrDTO.setErr_msg("Invalid OLM Id at row No: "+(rowNumber)+". It should be 8 character.");
				              			list.add(nSBulkUploadErrDTO);
			            				
			            			}else{
			            				distOlmId=cellValue;
				            			distProdComb = cellValue;
				            			nSBulkUploadDTO.setDistOlmId(cellValue);
				            		}
			            		}else if(cellNo==2){
			            			productName=cellValue;
			            			distProdComb += "#"+cellValue;
			            			nSBulkUploadDTO.setProductName(cellValue);
			            		}else if(cellNo==3){
			            			logger.info("in cell no 3 == "+cellValue);
			            			boolean validate = validateStockQty(cellValue.trim());
			            			if(!validate)
				            		{
			            				validateFlag =false;
			            				nSBulkUploadErrDTO = new NSBulkUploadDto(); 
			            				nSBulkUploadErrDTO.setErr_msg("Invalid Stock Qty at row No: "+(rowNumber)+". It should be only Positive, non decimal Integers.");
				              			list.add(nSBulkUploadErrDTO);
			            				
			            			}else{
			            				logger.info("Success valid stock Qty == "+cellValue.split("\\.")[0]);
				            			nSBulkUploadDTO.setStockQty(cellValue.split("\\.")[0]);
				            		}
			            			
			            		}
			            		
			            		
		        		  }
		        		  else
		        		  {
		        			  validateFlag =false;
		        			  nSBulkUploadErrDTO = new NSBulkUploadDto(); 
		        			  nSBulkUploadErrDTO.setErr_msg("File should contain only three data column");
	              			 list.add(nSBulkUploadErrDTO);
	              			 return list;
		        		  }
		        		  
		        		  
		            	}
		            	if(cellNo !=3){
		            		validateFlag =false;
		            		nSBulkUploadErrDTO = new NSBulkUploadDto(); 
		            		nSBulkUploadErrDTO.setErr_msg("File should contain three data column");
		            		List list2 = new ArrayList();
		            		list2.add(nSBulkUploadErrDTO);
	              			return list2;
		            	}
		        	  	if(checkDuplicate.contains(distProdComb))
	            		{
		        	  		validateFlag =false;
		        	  		nSBulkUploadErrDTO = new NSBulkUploadDto();
		        	  		nSBulkUploadErrDTO.setErr_msg("File contains duplicate Entries For Distributor : "+distProdComb.split("#")[0]);
	              			list.add(nSBulkUploadErrDTO);
		        	  		
		        	 	}else{
	            			checkDuplicate.add(distProdComb);
	            		}
		        	  	rst = validateDistOlmId(productName,distOlmId);
            			if(rst.next())
	            		{
            				distId = rst.getString("ACCOUNT_ID");
            				productId = rst.getString("PRODUCT_ID");
            				if(productId==null || productId.equals("")){
            					validateFlag =false;
            					nSBulkUploadErrDTO = new NSBulkUploadDto();
            					nSBulkUploadErrDTO.setErr_msg("Invalid Product Name at row no.: "+(rowNumber));
                				list.add(nSBulkUploadErrDTO);
            				}else{
            					
            					nSBulkUploadDTO.setDistId(distId);
            					nSBulkUploadDTO.setProductId(productId);
            					nSBulkUploadDTO.setDistOracleLocatorCode(rst.getString("DISTRIBUTOR_LOCATOR"));
            					nSBulkUploadDTO.setOracleProductCode(rst.getString("ORACLE_ITEM_CODE"));
            					nSBulkUploadDTO.setProductType(rst.getString("PRODUCT_TYPE"));
            				}
            			}else{
            				validateFlag =false;
            				nSBulkUploadErrDTO = new NSBulkUploadDto();
            				nSBulkUploadErrDTO.setErr_msg("Invalid Distributor OLM Id at row No: "+(rowNumber)+". No OLM Id exists in records.");;
	              			list.add(nSBulkUploadErrDTO);
            				
            			}
		        	  	
		        	  
		        	  rowNumber++;
		            }
		            else
		            {
		            	nSBulkUploadErrDTO = new NSBulkUploadDto();
		            	nSBulkUploadErrDTO.setErr_msg("File should contain only three data column");
             			list.add(nSBulkUploadErrDTO);
             			return list;
		            }
		              logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  ");
		        	  if(validateFlag){
		        		  nSBulkUploadDTO.setErr_msg("SUCCESS");
		        		  list.add(nSBulkUploadDTO);  		        		  
		        	  }
		              k++;
		        }else{
		        	//For Header Checking  
		        	int columnIndex = 0;
		              int cellNo = 0;
		              if(cells != null)
		              {
		            	  while (cells.hasNext()) {
			        		  if(cellNo < 3)
			        		  {
			        			  cellNo++;
			        			  HSSFCell cell = (HSSFCell) cells.next();
			        			  
			        			  	columnIndex = cell.getColumnIndex();
				              		if(columnIndex > 2)
				              		{
				              			validateFlag =false;
				              			nSBulkUploadErrDTO = new NSBulkUploadDto();
				              			nSBulkUploadErrDTO.setErr_msg("File should contain only three data column");
				              			List list2 = new ArrayList();
					            		list2.add(nSBulkUploadErrDTO);
				              			return list2;
				              		}
				              		String cellValue = null;
				        		  
				            		switch(cell.getCellType()) {
					            		case HSSFCell.CELL_TYPE_NUMERIC:
					            			cellValue = String.valueOf((double)cell.getNumericCellValue());
					            		break;
					            		case HSSFCell.CELL_TYPE_STRING:
					            			cellValue = cell.getStringCellValue();
						            	break;
				            		}
				            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
				            		{
				            			validateFlag =false;
				              			nSBulkUploadErrDTO = new NSBulkUploadDto();
				              			nSBulkUploadErrDTO.setErr_msg("File should contain All Required Values");
				              			List list2 = new ArrayList();
					            		list2.add(nSBulkUploadErrDTO);
				              			return list2;
				               		}
				            		
				            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
				            		if(cellNo==1){
				            			if(!"distributor olm id".equals(cellValue.trim().toLowerCase()))
					            		{
				            				validateFlag =false;
				            				nSBulkUploadErrDTO = new NSBulkUploadDto(); 
				            				nSBulkUploadErrDTO.setErr_msg("Invalid Header For Distributor OLM ID");
					              			list.add(nSBulkUploadErrDTO);
				            				
				            			}
				            		}else if(cellNo==2){
				            			if(!"product name".equals(cellValue.trim().toLowerCase()))
					            		{
				            				validateFlag =false;
				            				nSBulkUploadErrDTO = new NSBulkUploadDto(); 
				            				nSBulkUploadErrDTO.setErr_msg("Invalid Header For Product Name");
					              			list.add(nSBulkUploadErrDTO);
				            				
				            			}
				            		}else if(cellNo==3){
				            			if(!"stb quantity".equals(cellValue.trim().toLowerCase()))
					            		{
				            				validateFlag =false;
				            				nSBulkUploadErrDTO = new NSBulkUploadDto(); 
				            				nSBulkUploadErrDTO.setErr_msg("Invalid Header For STB Quantity");
					              			list.add(nSBulkUploadErrDTO);
				            				
				            			}
				            		}
			        		  }else{
			        			  validateFlag =false;
			        			  nSBulkUploadErrDTO = new NSBulkUploadDto(); 
			        			  nSBulkUploadErrDTO.setErr_msg("File should contain only three data Headers");
			        			  List list2 = new ArrayList();
				            	list2.add(nSBulkUploadErrDTO);
			              		return list2;
			        		  }
		            	  }
		            	  if(cellNo !=3){
			            		validateFlag =false;
			            		nSBulkUploadErrDTO = new NSBulkUploadDto(); 
			            		nSBulkUploadErrDTO.setErr_msg("File should contain three data Headers");
			            		List list2 = new ArrayList();
			            		list2.add(nSBulkUploadErrDTO);
		              			return list2;
			            	}
		              } else
			            {
			            	nSBulkUploadErrDTO = new NSBulkUploadDto();
			            	nSBulkUploadErrDTO.setErr_msg("File should contain three data column");
			            	  List list2 = new ArrayList();
				            	list2.add(nSBulkUploadErrDTO);
			              		return list2;
			            }
		        	rowNumber ++;
		        }
		     }
		         logger.info("K == "+k);
		    if(k==1){
		        	 nSBulkUploadDTO = new NSBulkUploadDto();
		        	 nSBulkUploadDTO.setErr_msg("Blank File has been uploaded!!");
	        		  list.add(nSBulkUploadDTO);  	
		        }
		      }
		    
			catch (Exception e) {
				nSBulkUploadErrDTO = new NSBulkUploadDto();
            	nSBulkUploadErrDTO.setErr_msg("Invalid File Uploaded.");
            	List list2 = new ArrayList();
	            list2.add(nSBulkUploadErrDTO);
	            logger.info(e);
              		return list2;
	        	
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
		
		public boolean validateStockQty(String str)
		{
			String[] strArr = str.split("\\.");
			if(strArr.length>1){
				if(strArr[1].length()>1){
					return false;
				}else if(!strArr[1].equals("0")){
					return false;
				}
				if(!ValidatorUtility.isValidNumber(strArr[0])) 
					//if(!ValidatorUtility.isValidPosNegNumber(str)) 
					{
						return false;
					}
			}else{
				if(!ValidatorUtility.isValidNumber(strArr[0])) 
					//if(!ValidatorUtility.isValidPosNegNumber(str)) 
					{
						return false;
					}
			}
			
			
			
			return true;
		}
		
		public ResultSet validateDistOlmId(String productName,String distOlmId)
		{
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try{
				
				logger.info("inside --- validateDistOlmId Product name == "+productName +" and dist OLM id == "+distOlmId);
				pstmt = connection.prepareStatement(SQL_SELECT_DISTID);
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
		
		
		public String updateStockQty(List list)
		{
			PreparedStatement pstmt = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			ResultSet rst=null;
			String returnstr="";
			logger.info("Size--"+list.size());
			String distId ="";
			String productId="";
			Integer openingStock=0;
			Integer saleQty=0;
			Integer uploadedStockQty=0;
			Integer recoQty =0;
			NSBulkUploadDto nsBulkUploadDTO = null;
		try
		{	
			pstmt = connection.prepareStatement(SQL_SELECT_OPEN_STOCK);
			pstmt1 = connection.prepareStatement(SQL_UPDATE_OPEN_STOCK);
			pstmt2 = connection.prepareStatement(SQL_INSERT_OPEN_STOCK);
			Iterator itr = list.iterator();
			int updaterows = 0;
			
			while(itr.hasNext())
			{
				nsBulkUploadDTO = (NSBulkUploadDto) itr.next();
				distId = nsBulkUploadDTO.getDistId();
				productId = nsBulkUploadDTO.getProductId();
				pstmt.setString(1, distId);
				pstmt.setString(2, productId);
				rst = pstmt.executeQuery();
				uploadedStockQty = Integer.parseInt(nsBulkUploadDTO.getStockQty());
				if(rst.next()){
					openingStock = rst.getInt("OPENING_STOCK");
					saleQty = rst.getInt("SALE");
					// Actula Closing= OpeningStock-Sale+Reco Quantity
					recoQty = uploadedStockQty - openingStock+ saleQty;
					logger.info("in if Condition  --- OpenStock== "+openingStock + " and  Sale == "+saleQty+" and uploaded qty == "+uploadedStockQty+  " and reco =="+recoQty );
					pstmt1.setString(1, recoQty.toString());
					pstmt1.setString(2, uploadedStockQty.toString());
					pstmt1.setString(3, distId);
					pstmt1.setString(4, productId);
					pstmt1.executeUpdate();
				}else{
					pstmt2.setString(1, distId);
					pstmt2.setString(2, nsBulkUploadDTO.getDistOracleLocatorCode());
					pstmt2.setString(3, productId);
					pstmt2.setString(4, uploadedStockQty.toString());
					pstmt2.setString(5, "0");
					pstmt2.setString(6, uploadedStockQty.toString());
					pstmt2.setString(7, nsBulkUploadDTO.getOracleProductCode());
					pstmt2.setString(8, nsBulkUploadDTO.getProductType());
					pstmt2.setString(9, "0");
					
					pstmt2.executeUpdate();
					
					
				}
				
				updaterows++;
				
			}
			returnstr = "Total "+updaterows+" Records have been Uploaded Successfully";
			
			
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

		public List<NSBulkUploadDto> getALLStockLevelList() {
			PreparedStatement ps = null;
			Connection db2conn = null; 
			ResultSet rs = null; 
		
			List<NSBulkUploadDto> stockLevelBulkUploadList= new ArrayList<NSBulkUploadDto>();
			
			try {
				
				//String sql = "SELECT DIST_ID, DIST_OLM_ID, SECURITY_AMOUNT, LOAN_AMOUNT   FROM DP_DIST_SECURITY_LOAN" ;
				db2conn = DBConnectionManager.getDBConnection();
				ps = db2conn.prepareStatement(SQL_SELECT_BULKUPLOAD_STOCK);
				rs = ps.executeQuery();
				
				while (rs.next()) {
					NSBulkUploadDto recDTO = new NSBulkUploadDto();
					
					recDTO.setDistOlmId(rs.getString("LOGIN_NAME"));
					recDTO.setProductName(rs.getString("PRODUCT_NAME"));
					recDTO.setStockQty(rs.getString("CLOSING_STOCK"));
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
