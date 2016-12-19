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
import com.ibm.dp.dao.DPProductSecurityListDao;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

 
public class DPProductSecurityListDaoImpl extends BaseDaoRdbms implements  DPProductSecurityListDao{
	
public static Logger logger = Logger.getLogger(DPProductSecurityListDaoImpl.class.getName());



public static final String SQL_SELECT_PRODUCT_SECURITY = DBQueries.SQL_SELECT_PRODUCT_SECURITY;
public static final String SQL_ALL_SELECT_PRODUCT_SECURITY = DBQueries.SQL_ALL_SELECT_PRODUCT_SECURITY;
public static final String SQL_UPDATE_PRODUCT_SECURITY = DBQueries.SQL_UPDATE_PRODUCT_SECURITY;


	
	public DPProductSecurityListDaoImpl(Connection connection) 
	{
		super(connection);
	}
	public List uploadExcel(InputStream inputstream) throws DAOException 
	{
		List list = new ArrayList();
		DPProductSecurityListDto dpProductSecurityListErrDto  = null;
		String distProdComb ="";
		
		ResultSet rst =null;
		boolean validateFlag =true;
		DPProductSecurityListDto dpProductSecurityListDto = null;
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
				  dpProductSecurityListErrDto = new DPProductSecurityListDto();
				  dpProductSecurityListErrDto.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
	      		  list.add(dpProductSecurityListErrDto);
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
	          
	          while (rows.hasNext()) 
	          {
	        	  dpProductSecurityListDto = new DPProductSecurityListDto();
	        	  HSSFRow row = (HSSFRow) rows.next();
	        	  Iterator cells = row.cellIterator();
	        	  
	        	 if(rowNumber>1){
	        	 
	        	  int columnIndex = 0;
	              int cellNo = 0;
	              if(cells != null)
	              {
	            	  //distProdComb ="";
	            	  productName="";
	    			productId="";
	            	while (cells.hasNext()) {
	        		  if(cellNo < 2)
	        		  {
	        			  cellNo++;
	        			  HSSFCell cell = (HSSFCell) cells.next();
	        			  
	        			  	columnIndex = cell.getColumnIndex();
		              		if(columnIndex > 1)
		              		{
		              			validateFlag =false;
		              			dpProductSecurityListErrDto = new DPProductSecurityListDto();
		              			dpProductSecurityListErrDto.setErr_msg("File should contain only two data column");
		              			list.add(dpProductSecurityListErrDto);
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
		            			dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
		            			dpProductSecurityListErrDto.setErr_msg("File should contain All Required Values");
		              			 list.add(dpProductSecurityListErrDto);
		              			 return list;
		            			
		            		}*/
		            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
		            		if(cellNo==1)
		            		{
		            			String cellProduct = cellValue.trim();
		            			int intProdType = 1;
		            			String strProdName = cellProduct;
		            			
		            			if(cellProduct.indexOf("SWAP")>0)
		            			{
		            				intProdType = 0;
		            				strProdName = cellProduct.substring(0, cellProduct.indexOf("SWAP")).trim();
		            			System.out.println("cellProduct.indexOf(SWAP)).trim()"+cellProduct.indexOf("SWAP"));
		            			System.out.println("cellProduct.length()"+cellProduct.length());
		            			if((cellProduct.length()>(cellProduct.indexOf("SWAP")+4))){
		            				
		            				validateFlag =false;
		            				dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
		            				dpProductSecurityListErrDto.setErr_msg("Invalid Product Name at row No: "+(rowNumber));
			              			list.add(dpProductSecurityListErrDto);
		            				
		            			}
		            				
		            			}
		            			
		            			logger.info("PRODUCT NAME  ::  "+strProdName);
		            			logger.info("PRODUCT TYPE  ::  "+intProdType);
		            			
		            			boolean validate = validateProductName(strProdName);
		            			//.out.println("ARTI **********************validate"+validate);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
		            				dpProductSecurityListErrDto.setErr_msg("Invalid Product Name at row No: "+(rowNumber));
			              			list.add(dpProductSecurityListErrDto);
			              			distProdComb = cellValue.trim();
		            				
		            			}else {
		            				//validateFlag =true;
		            				dpProductSecurityListDto.setProductName(strProdName);
		            				dpProductSecurityListDto.setIntProducType(intProdType);
		            				distProdComb = cellValue.trim();
			            	
		            			}
		            		}else if(cellNo==2){
		            			boolean validate = validateAmountQty(cellValue.trim());
		            			//System.out.println("validate cell3 "+validate);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
		            				dpProductSecurityListErrDto.setErr_msg("Invalid Product Security  at row No: "+(rowNumber)+". It should be only Numeric or Decimal value and less than 10000000.");
			              			list.add(dpProductSecurityListErrDto);
		            				
		            			}else if(!validateMaxQty(cellValue.trim())){
			            			validateFlag =false;
			            			dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
			            			dpProductSecurityListErrDto.setErr_msg("Invalid  Product Security  at row No: "+(rowNumber)+"should be less than 10000000.");
		            				list.add(dpProductSecurityListErrDto);
			            		}else{
			            			dpProductSecurityListDto.setSecurityAmt(cellValue.trim());
			            		}
		            			
		            		}
		            		
		            		
	        		  }
	        		  else
	        		  {
	        			  validateFlag =false;
	        			  dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
	        			  dpProductSecurityListErrDto.setErr_msg("File should contain only two data column");
            			 list.add(dpProductSecurityListErrDto);
            			 return list;
	        		  }
	        		  
	        		  
	            	}
	            	if(cellNo !=2){
	            		validateFlag =false;
	            		dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
	            		dpProductSecurityListErrDto.setErr_msg("File should contain two data column");
	            		List list2 = new ArrayList();
	            		list2.add(dpProductSecurityListErrDto);
            			return list2;
	            	}
	            	distProdComb=distProdComb.trim();
	            	System.out.print("distProdComb*********"+distProdComb);
	            	
	            	if(distProdComb !=null && !distProdComb.equalsIgnoreCase("null") && !distProdComb.equalsIgnoreCase("")){
	            	Iterator pdtName =  checkDuplicate.iterator() ;
	            	while(pdtName.hasNext()){
	            		String pdname1 = pdtName.next().toString();
	            		if(pdname1.equalsIgnoreCase(distProdComb)){
	            			System.out.println("pdname is equal to produc");
		        	  		validateFlag =false;
		        	  		dpProductSecurityListErrDto = new DPProductSecurityListDto();
		        	  		dpProductSecurityListErrDto.setErr_msg("File contains duplicate Entries For Product : "+distProdComb);
		        	  		//distProdComb="";
	            			list.add(dpProductSecurityListErrDto);
	            			
	            		}else{
	            			System.out.println("pdname is not equal to produc");
	            			//validateFlag =true;
	            			//distProdComb="";
	              			
	            		}
	            		
	            	}
	            	checkDuplicate.add(distProdComb);
	            	}
	            	
	            	//System.out.println("distProdComb***********************"+distProdComb);
	            	/** if(distProdComb !=null && !distProdComb.equalsIgnoreCase("null") && !distProdComb.equalsIgnoreCase("")){
	        	  	if(checkDuplicate.contains(distProdComb))
	        	  	{
	        	  		
	        	  	//	System.out.println("distProdComb**********contains*************");
	        	  		validateFlag =false;
	        	  		dpProductSecurityListErrDto = new DPProductSecurityListDto();
	        	  		dpProductSecurityListErrDto.setErr_msg("File contains duplicate Entries For Product : "+distProdComb);
	        	  		distProdComb="";
            			list.add(dpProductSecurityListErrDto);
	        	  		
	        	 	}else{
	        	 		//System.out.println("distProdComb*********not *contains*************");
	        	 		
	        	 		//validateFlag =true;
          			checkDuplicate.add(distProdComb);
          		}
	            	} 
	            	 **/
	        	   rowNumber++;
	            }
	            else
	            {
	            	dpProductSecurityListErrDto = new DPProductSecurityListDto();
	            	dpProductSecurityListErrDto.setErr_msg("File should contain only two data column");
       			list.add(dpProductSecurityListErrDto);
       			return list;
	            }
	              logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  "+validateFlag);
	        	  if(validateFlag){
	        		  dpProductSecurityListDto.setErr_msg("SUCCESS");
	        		 list.add(dpProductSecurityListDto);  		        		  
	        	  }
	             
	        }else{

	        	//For Header Checking  
	        	int columnIndex = 0;
	              int cellNo = 0;
	              if(cells != null)
	              {
	            	  while (cells.hasNext()) {
		        		  if(cellNo < 4)
		        		  {
		        			  cellNo++;
		        			  HSSFCell cell = (HSSFCell) cells.next();
		        			  
		        			  	columnIndex = cell.getColumnIndex();
			              		if(columnIndex > 3)
			              		{
			              			validateFlag =false;
			              			dpProductSecurityListErrDto = new DPProductSecurityListDto();
			              			dpProductSecurityListErrDto.setErr_msg("File should contain only two data column");
			              			List list2 = new ArrayList();
				            		list2.add(dpProductSecurityListErrDto);
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
			            			dpProductSecurityListErrDto = new DPProductSecurityListDto();
			            			dpProductSecurityListErrDto.setErr_msg("File should contain All Required Values");
			              			List list2 = new ArrayList();
				            		list2.add(dpProductSecurityListErrDto);
			              			return list2;
			               		}
			            		
			            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
			            		if(cellNo==1){
			            			if(!"product name".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
			            				dpProductSecurityListErrDto.setErr_msg("Invalid Header For Product Name");
				              			list.add(dpProductSecurityListErrDto);
			            				
			            			}
			            		}else if(cellNo==2){
			            			if(!"security".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
			            				dpProductSecurityListErrDto.setErr_msg("Invalid Header For Security");
				              			list.add(dpProductSecurityListErrDto);
			            				
			            			}
			            		}
		        		  }else{
		        			  validateFlag =false;
		        			  dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
		        			  dpProductSecurityListErrDto.setErr_msg("File should contain only two data Headers");
		        			  List list2 = new ArrayList();
			            	list2.add(dpProductSecurityListErrDto);
		              		return list2;
		        		  }
	            	  }
	            	  if(cellNo !=2){
		            		validateFlag =false;
		            		dpProductSecurityListErrDto = new DPProductSecurityListDto(); 
		            		dpProductSecurityListErrDto.setErr_msg("File should contain two data Headers");
		            		List list2 = new ArrayList();
		            		list2.add(dpProductSecurityListErrDto);
	              			return list2;
		            	}
	              } else
		            {
	            	  dpProductSecurityListErrDto = new DPProductSecurityListDto();
	            	  dpProductSecurityListErrDto.setErr_msg("File should contain two data column");
		            	  List list2 = new ArrayList();
			            	list2.add(dpProductSecurityListErrDto);
		              		return list2;
		            }
	        	rowNumber ++;
	         }
	        	 k++;
	     }
	         logger.info("K == "+k);
	    if(k==1){
	    	dpProductSecurityListDto = new DPProductSecurityListDto();
	        	 dpProductSecurityListDto.setErr_msg("Blank File can not be uploaded!!");
      		  list.add(dpProductSecurityListDto);  	
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
	
	
	public boolean validateAmountQty(String str)
	{
		//if(!ValidatorUtility.isValidNumber(str)) 
		if(!ValidatorUtility.isValidAmount(str)) 
		{
			return false;
		}
		return true;
	}
	
	public boolean validateAlphabet(String str)
	{
		//if(!ValidatorUtility.isValidNumber(str)
		if(!ValidatorUtility.isAlfabet(str)) 
		{
			return false;
		}
		return true;
	}
	
	
	public boolean validateMaxQty(String maxQty)
	{
		if(!validateAlphabet(maxQty)){
		if(Double.parseDouble(maxQty)>9999999d)
		{
			return false;
		}
		}
		return true;
	}
	
	
	
	public boolean validateProductName(String productName)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		boolean flag = false;
		try{
			
			logger.info("inside --- validateProductName  productName == "+productName);
			
			pstmt = connection.prepareStatement(SQL_SELECT_PRODUCT_SECURITY);
			pstmt.setString(1,productName.toUpperCase());
			rs =pstmt.executeQuery();
			if(rs.next())
			{
				count = rs.getInt(1);
			}
			if(count >=1)
				flag =  true;
			else
				flag =  false;
			}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateProductName method--"+e.getMessage());
		}
	/*	finally
		{
			//DBConnectionManager.releaseResources(pstmt, null);
		}*/
		
		
		
			return flag;
	}
	
	
	public String updateProductSecurityList(List list,String userId)
	{
		PreparedStatement pstmt = null;
		String returnstr="";
		logger.info("Size--"+list.size());
		
		ResultSet rst=null;
		DPProductSecurityListDto dpProductSecurityListDto = null;
	try
	{	
		pstmt = connection.prepareStatement(SQL_UPDATE_PRODUCT_SECURITY);
		Iterator itr = list.iterator();
		int updaterows = 0;
		while(itr.hasNext())
		{
			logger.info("UPDATING PRODUCT SECURITY");
			
			dpProductSecurityListDto = (DPProductSecurityListDto) itr.next();
			logger.info("PRODUCT NAME  ::  "+dpProductSecurityListDto.getProductName());
			logger.info("PRODUCT TYPE  ::  "+dpProductSecurityListDto.getIntProducType());
			pstmt.setDouble(1, Double.parseDouble(dpProductSecurityListDto.getSecurityAmt()));
			pstmt.setInt(2, Integer.parseInt(userId));
			pstmt.setTimestamp(3, Utility.getCurrentTimeStamp());
			pstmt.setString(4, dpProductSecurityListDto.getProductName());
			pstmt.setInt(5, dpProductSecurityListDto.getIntProducType());
			pstmt.executeUpdate();
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
		
	}
		
		return returnstr;
	}
	
	
	
	public List<DPProductSecurityListDto> getALLProuductSecurityList() {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
	
		List<DPProductSecurityListDto> productSecurityList= new ArrayList<DPProductSecurityListDto>();
		
		try {
			
			//String sql = "SELECT DIST_ID, DIST_OLM_ID, SECURITY_AMOUNT, LOAN_AMOUNT   FROM DP_DIST_SECURITY_LOAN" ;
			db2conn = DBConnectionManager.getDBConnection();
			ps = db2conn.prepareStatement(SQL_ALL_SELECT_PRODUCT_SECURITY);
			
			logger.info("Query to get all product security List  :: "+SQL_ALL_SELECT_PRODUCT_SECURITY);
			
			rs = ps.executeQuery();
			
			while (rs.next()) 
			{
				DPProductSecurityListDto recDTO = new DPProductSecurityListDto();
				
				recDTO.setProductName(rs.getString("PRODUCT_CATEGORY"));
				recDTO.setSecurityAmt(rs.getString("PRODUCT_SECURITY"));
			
				productSecurityList.add(recDTO);
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
		return productSecurityList;
		
	}
	
	
	
	}

