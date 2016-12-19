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
import com.ibm.dp.dao.DPDistSecurityDepositLoanDao;
import com.ibm.dp.dto.DPDistSecurityDepositLoanDto;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

 
public class DPDistSecurityDepositLoanDaoImpl extends BaseDaoRdbms implements DPDistSecurityDepositLoanDao{
	
public static Logger logger = Logger.getLogger(DPDistSecurityDepositLoanDaoImpl.class.getName());

public static final String SQL_SELECT_SECURITY_OLMID 	= DBQueries.SQL_SELECT_SECURITY_OLMID;
public static final String SQL_SELECT_OLMID 	= DBQueries.SQL_SELECT_OLMID;
public static final String SQL_INSERT_SECURITY_LOAN = DBQueries.SQL_INSERT_SECURITY_LOAN;
public static final String SQL_SELECT_SECURITY_LOAN = DBQueries.SQL_SELECT_SECURITY_LOAN;
public static final String SQL_UPDATE_SECURITY_LOAN = DBQueries.SQL_UPDATE_SECURITY_LOAN;
public static final String SQL_INSERT_SECURITY_LOAN_TRANS = DBQueries.SQL_INSERT_SECURITY_LOAN_TRANS;
public static final String SQL_ALL_SELECT_SECURITY = DBQueries.SQL_ALL_SELECT_SECURITY;

	
	public DPDistSecurityDepositLoanDaoImpl(Connection connection) 
	{
		super(connection);
	}
	public List uploadExcel(InputStream inputstream)throws DAOException 
	{
		List list = new ArrayList();
		DPDistSecurityDepositLoanDto dpDistSecurityDepositLoanErrDto  = null;
		String distProdComb ="";
		String distOlmId ="";
		String wareHouseCode="";
		String distId="";
		String warehouseId="";
		ResultSet rst =null;
		boolean validateFlag =true;
		DPDistSecurityDepositLoanDto dpDistSecurityDepositLoanDto = null;
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
				  dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto();
				  dpDistSecurityDepositLoanErrDto.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
      		  list.add(dpDistSecurityDepositLoanErrDto);
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
	        	  dpDistSecurityDepositLoanDto = new DPDistSecurityDepositLoanDto();
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
		              			dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto();
		              			dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain only five data column1111");
		              			list.add(dpDistSecurityDepositLoanErrDto);
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
		            	/*	if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
		            		{
		            			dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
		            			dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain All Required Values");
		              			 list.add(dpDistSecurityDepositLoanErrDto);
		              			 return list;
		            			
		            		}*/
		            		
		            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
		            		if(cellNo==1){
		            			boolean validate = validateOlmId(cellValue.trim());
		            			System.out.println("validate"+validate);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
		            				dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid OLM Id at row No: "+(rowNumber)+". It should be 8 character.");
			              			list.add(dpDistSecurityDepositLoanErrDto);
		            				
		            			}else if(validateOlmdistId(cellValue)){
		            				
			            				distOlmId=cellValue;
				            			distProdComb = cellValue;
			            				dpDistSecurityDepositLoanDto.setDistOlmId(cellValue);
			            				rst = validateDistOlmId(distOlmId);
			        	        	  	if(rst.next())
			        	          		{
			        	        	  	
			        	      				distId = rst.getString("LOGIN_ID");
			        	      				dpDistSecurityDepositLoanDto.setDistId(distId);
			        	      				
			            				}else
			            				{
			            					
			            					dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
			            					dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Distributer OLM Id  at row No. "+(rowNumber)+".");
					              			list.add(dpDistSecurityDepositLoanErrDto);
			            					
			            				}
			            	
		            		}
		            			else
		            				
	            				{  
		            				validateFlag =false;
	            					dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
	            					dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Distributer OLM Id at row No. "+(rowNumber)+".");
			              			list.add(dpDistSecurityDepositLoanErrDto);
	            					
	            				}
		            		}else if(cellNo==2){
		            			
		            		//	boolean validate = validateName(cellValue.trim());
		            			//System.out.println("validate cell3 "+validate);
		            			//if(!validate)
			            		//{
		            				//validateFlag =false;
		            			//	dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
		            				//dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Distributor Name  at row No: "+(rowNumber)+". It should be only Alphabetic value.");
			              			//list.add(dpDistSecurityDepositLoanErrDto);
		            				
		            		//	}else{
		            				//validateFlag =true;
			            			dpDistSecurityDepositLoanDto.setDistName(cellValue.trim());
			            		//}
		            			
		            		}else if(cellNo==3){
		            			boolean validate = validateAmountQty(cellValue.trim());
		            			System.out.println("validate cell3 "+validate);
		            			if(!validateMaxQty(cellValue.trim())){
			            			validateFlag =false;
			            			dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
			            			dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Amount at row No: "+(rowNumber)+"should be numeric and less than 10000000.");
		            				list.add(dpDistSecurityDepositLoanErrDto);
			            		}
		            			else if(!validate)
			            		{
		            				validateFlag =false;
		            				dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 

									dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Amount at row No: "+(rowNumber)+". It should be only Positive Numeric or Decimal value and less than 10000000..");

			              			list.add(dpDistSecurityDepositLoanErrDto);
		            				
		            			} else{
		            				//validateFlag =true;
			            			dpDistSecurityDepositLoanDto.setSecurityAmt(cellValue.trim());
			            		}
		            			
		            		}else if(cellNo==4){
		            			boolean validate = validateAmountQty(cellValue.trim());
		            			System.out.println("validate cell311111111111111111111111 "+validate);
		            			if(!validateMaxQty(cellValue.trim())){
			            			validateFlag =false;
			            			dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
			            			dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Loan at row No: "+(rowNumber)+"should be numeric and less than 10000000.");
		            				list.add(dpDistSecurityDepositLoanErrDto);
			            		}
		            			else if(!validate)
			            		{
		            				validateFlag =false;
		            				dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
		            				dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Loan at row No: "+(rowNumber)+". It should be only Positive Numeric or Decimal value and less than 10000000..");
			              			list.add(dpDistSecurityDepositLoanErrDto);
		            				
		            			} else{
		            				//validateFlag =true;
			            			dpDistSecurityDepositLoanDto.setLoanAmt(cellValue.trim());
			            		}
		            			
		            		}
		            		
		            		
	        		  }
	        		  else
	        		  {
	        			  validateFlag =false;
	        			  dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
	        			  dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain only five data column");
            			 list.add(dpDistSecurityDepositLoanErrDto);
            			 return list;
	        		  }
	        		  
	        		  
	            	}
	            	if(cellNo !=5){
	            		validateFlag =false;
	            		dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
	            		dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain five data column");
	            		List list2 = new ArrayList();
	            		list2.add(dpDistSecurityDepositLoanErrDto);
            			return list2;
	            	}
	            	if(distProdComb !=null && !distProdComb.equalsIgnoreCase("null") && !distProdComb.equalsIgnoreCase("")){
	            	
	        	  	if(checkDuplicate.contains(distProdComb))
          		{
	        	  		validateFlag =false;
	        	  		dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto();
	        	  		dpDistSecurityDepositLoanErrDto.setErr_msg("File contains duplicate Entries For Distributor : "+distProdComb);
            			list.add(dpDistSecurityDepositLoanErrDto);
	        	  		
	        	 	}else{
	        	 		//validateFlag =true;
          			checkDuplicate.add(distProdComb);
          		}
	        	  	
	            	}
	        	   rowNumber++;
	            }
	            else
	            {
	            	dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto();
	            	dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain only five data column");
       			list.add(dpDistSecurityDepositLoanErrDto);
       			return list;
	            }
	              logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  "+validateFlag);
	        	 if(validateFlag){
	        		  dpDistSecurityDepositLoanDto.setErr_msg("SUCCESS");
	        		  list.add(dpDistSecurityDepositLoanDto);  		        		  
	        	  }
	             // k++;
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
			              			dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto();
			              			dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain only five data column");
			              			List list2 = new ArrayList();
				            		list2.add(dpDistSecurityDepositLoanErrDto);
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
			            			dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto();
			            			dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain All Required Values");
			              			List list2 = new ArrayList();
				            		list2.add(dpDistSecurityDepositLoanErrDto);
			              			return list2;
			               		}
			            		
			            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
			            		if(cellNo==1){
			            			if(!"distributor olm id".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
			            				dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Header For Distributor OLM ID");
				              			list.add(dpDistSecurityDepositLoanErrDto);
			            				
			            			}
			            		}else if(cellNo==2){
			            			if(!"distributor name".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
			            				dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Header For Distributor Name");
				              			list.add(dpDistSecurityDepositLoanErrDto);
			            				
			            			}
			            		}else if(cellNo==3){
			            			if(!"security amount".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
			            				dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Header For Security Amount");
				              			list.add(dpDistSecurityDepositLoanErrDto);
			            				
			            			}
			            		}else if(cellNo==4){
			            			if(!"loan".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
			            				dpDistSecurityDepositLoanErrDto.setErr_msg("Invalid Header For Loan");
				              			list.add(dpDistSecurityDepositLoanErrDto);
			            				
			            			}
			            		}
		        		  }else{
		        			  validateFlag =false;
		        			  dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
		        			  dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain only five data Headers");
		        			  List list2 = new ArrayList();
			            	list2.add(dpDistSecurityDepositLoanErrDto);
		              		return list2;
		        		  }
	            	  }
	            	  if(cellNo !=5){
		            		validateFlag =false;
		            		dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto(); 
		            		dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain five data Headers");
		            		List list2 = new ArrayList();
		            		list2.add(dpDistSecurityDepositLoanErrDto);
	              			return list2;
		            	}
	              } else
		            {
	            	  dpDistSecurityDepositLoanErrDto = new DPDistSecurityDepositLoanDto();
	            	  dpDistSecurityDepositLoanErrDto.setErr_msg("File should contain five data column");
		            	  List list2 = new ArrayList();
			            	list2.add(dpDistSecurityDepositLoanErrDto);
		              		return list2;
		            }
	        	rowNumber ++;
	         }
	        	 k++;
	     }
	         logger.info("K == "+k);
	    if(k==1){
	        	 dpDistSecurityDepositLoanDto = new DPDistSecurityDepositLoanDto();
	        	 dpDistSecurityDepositLoanDto.setErr_msg("Blank File can not be uploaded!!");
      		  list.add(dpDistSecurityDepositLoanDto);  	
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
	
	public boolean validateAmountQty(String str)
	{
		//if(!ValidatorUtility.isValidNumber(str)) 
		if(!ValidatorUtility.isValidAmount(str)) 
		{
			return false;
		}
		return true;
	}
	
	public boolean validateName(String str)
	{
		//if(!ValidatorUtility.isValidNumber(str)) 
		if(!ValidatorUtility.isAlfabetSpace(str)) 
		{
			return false;
		}
		return true;
	}
	
	public boolean validateAlphabet(String str)
	{
		//if(!ValidatorUtility.isValidNumber(str)) 
		if(!ValidatorUtility.isValidAmount(str)) 
		{
			return false;
		}
		return true;
	}
	
	
	public boolean validateMaxQty(String maxQty)
	{
		if(validateAlphabet(maxQty)){
		if(Double.parseDouble(maxQty)>9999999d)
		{
			return false;
		}
		}
		return true;
	}
	
	
	
	public ResultSet validateDistOlmId(String distOlmId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			logger.info("inside --- validateDistOlmId  and dist OLM id == "+distOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_SECURITY_OLMID);
			pstmt.setString(1,distOlmId);
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
	
	
	public String addSecurityLoan(List list,String userId)
	{
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String returnstr="";
		logger.info("Size--"+list.size());
		String olmId ="";
		String productId="";
		String minQty = "";
		String maxQty= "";
		ResultSet rst=null;
		DPDistSecurityDepositLoanDto dpDistStyDipstLoanDto = null;
	try
	{	
		pstmt = connection.prepareStatement(SQL_SELECT_SECURITY_LOAN);
		pstmt1 = connection.prepareStatement(SQL_UPDATE_SECURITY_LOAN);
		pstmt2 = connection.prepareStatement(SQL_INSERT_SECURITY_LOAN);
		pstmt3 = connection.prepareStatement(SQL_INSERT_SECURITY_LOAN_TRANS);
		
		Iterator itr = list.iterator();
		int updaterows = 0;
		while(itr.hasNext())
		{
			dpDistStyDipstLoanDto = (DPDistSecurityDepositLoanDto) itr.next();
			pstmt.setString(1, dpDistStyDipstLoanDto.getDistId());
			rst = pstmt.executeQuery();
			if(rst.next()){
				if(rst.getInt(1)>=1){
					
					pstmt1.setDouble(1, Double.parseDouble(dpDistStyDipstLoanDto.getSecurityAmt()));
					pstmt1.setDouble(2, Double.parseDouble(dpDistStyDipstLoanDto.getLoanAmt()));
					pstmt1.setInt(3, Integer.parseInt(userId));
					pstmt1.setTimestamp(4, Utility.getCurrentTimeStamp());
					pstmt1.setString(5, dpDistStyDipstLoanDto.getDistId());
					pstmt1.executeUpdate();
			}else{
				pstmt2.setString(1, dpDistStyDipstLoanDto.getDistId());
				pstmt2.setString(2, dpDistStyDipstLoanDto.getDistOlmId());
				System.out.println("Double.parseDouble*********"+dpDistStyDipstLoanDto.getSecurityAmt());
				System.out.println("Double.parseDouble***dpDistStyDipstLoanDto.getLoanAmt()******"+dpDistStyDipstLoanDto.getLoanAmt()+userId+dpDistStyDipstLoanDto.getDistId()+dpDistStyDipstLoanDto.getDistOlmId());
				pstmt2.setDouble(3, Double.parseDouble(dpDistStyDipstLoanDto.getSecurityAmt()));
				pstmt2.setDouble(4, Double.parseDouble(dpDistStyDipstLoanDto.getLoanAmt()));
				pstmt2.setInt(5, Integer.parseInt(userId));
				pstmt2.setTimestamp(6, Utility.getCurrentTimeStamp());
				pstmt2.executeUpdate();
				
			}
				
				pstmt3.setString(1, dpDistStyDipstLoanDto.getDistId());
				pstmt3.setString(2, dpDistStyDipstLoanDto.getDistOlmId());
				pstmt3.setDouble(3,Double.parseDouble(dpDistStyDipstLoanDto.getSecurityAmt()));
				pstmt3.setDouble(4, Double.parseDouble(dpDistStyDipstLoanDto.getLoanAmt()));
				pstmt3.setInt(5, Integer.parseInt(userId));
				pstmt3.setTimestamp(6, Utility.getCurrentTimeStamp());
				pstmt3.executeUpdate();
				
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
		DBConnectionManager.releaseResources(pstmt3, null);
		
	}
		
		return returnstr;
	}
	
	
	
	public List<DPDistSecurityDepositLoanDto> getALLDistSecurityLoan() {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
	
		List<DPDistSecurityDepositLoanDto> distSecLoanList= new ArrayList<DPDistSecurityDepositLoanDto>();
		
		try {
			
			//String sql = "SELECT DIST_ID, DIST_OLM_ID, SECURITY_AMOUNT, LOAN_AMOUNT   FROM DP_DIST_SECURITY_LOAN" ;
			db2conn = DBConnectionManager.getDBConnection();
			ps = db2conn.prepareStatement(SQL_ALL_SELECT_SECURITY);
			
			System.out.println(SQL_ALL_SELECT_SECURITY);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				DPDistSecurityDepositLoanDto recDTO = new DPDistSecurityDepositLoanDto();
				
				recDTO.setDistOlmId(rs.getString("DIST_OLM_ID"));
				recDTO.setDistName(rs.getString("ACCOUNT_NAME"));
				recDTO.setSecurityAmt(rs.getString("SECURITY_AMOUNT"));
				recDTO.setLoanAmt(rs.getString("LOAN_AMOUNT"));
				recDTO.setCircleName(rs.getString("CIRCLE_NAME"));
				distSecLoanList.add(recDTO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Serialized Stock As On Date Report Details. Exception message: "
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
		return distSecLoanList;
		
	}
	
	
	
	}

