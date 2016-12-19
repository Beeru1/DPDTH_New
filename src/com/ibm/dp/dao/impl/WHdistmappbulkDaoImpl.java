package com.ibm.dp.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.WHdistmappbulkDao;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

 
public class WHdistmappbulkDaoImpl extends BaseDaoRdbms implements WHdistmappbulkDao{
	
public static Logger logger = Logger.getLogger(WHdistmappbulkDaoImpl.class.getName());

public static final String SQL_SELECT_WH_DISTID 	= DBQueries.SQL_SELECT_WH_DISTID;
public static final String SQL_SELECT_OLMID 	= DBQueries.SQL_SELECT_OLMID;
protected static final String SQL_INSERT_WH_DIST_MAP = DBQueries.SQL_INSERT_WH_DIST_MAP;
protected static final String SQL_CHECK_WAREHOUSE_CODE = DBQueries.SQL_CHECK_WAREHOUSE_CODE;
protected static final String SQL_UPDATE_WAREHOUSE_CODE = DBQueries.SQL_UPDATE_WAREHOUSE_CODE;
protected static final String SQL_SELECT_WAREHOUSE_CODE = DBQueries.SQL_SELECT_WAREHOUSE_CODE;


	
	public WHdistmappbulkDaoImpl(Connection connection) 
	{
		super(connection);
	}
	public List uploadExcel(InputStream inputstream) throws DAOException 
	{
		List list = new ArrayList();
		WHdistmappbulkDto whdistmappbulkErrDto = null;
		String distProdComb ="";
		String distOlmId ="";
		String wareHouseCode="";
		String distId="";
		String warehouseId="";
		ResultSet rst =null;
		boolean validateFlag =true;
		try
		{
			  HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			  HSSFSheet sheet = workbook.getSheetAt(0);
			  Iterator rows = sheet.rowIterator();
			  int totalrows = sheet.getLastRowNum()+1;
			  logger.info("Total Rows == "+sheet.getLastRowNum());
			  if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			  {
				  whdistmappbulkErrDto = new WHdistmappbulkDto();
				  whdistmappbulkErrDto.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
        		  list.add(whdistmappbulkErrDto);
        		  return list;
			  }
			  int rowNumber = 1;
	          WHdistmappbulkDto wHdistmappbulkDto = null;
	          ArrayList checkDuplicate = new ArrayList();
	          ArrayList checkDuplicate1 = new ArrayList();
	          int k=1;
	          while (rows.hasNext()) {
	        	  wHdistmappbulkDto = new WHdistmappbulkDto();
	        	  
	        	  HSSFRow row = (HSSFRow) rows.next();
	        	  Iterator cells = row.cellIterator();
	        	  System.out.println("ARTI************rowNumber"+rowNumber+"cells*********"+cells);
	        	  if(rowNumber>1){
	        	  int columnIndex = 0;
	              int cellNo = 0;
	              if(cells != null)
	              {
	            	distProdComb ="";
	            	distOlmId ="";
	            	wareHouseCode="";
	    			distId ="";
	    			warehouseId="";
	    			while (cells.hasNext()) {
	        		  if(cellNo < 2)
	        		  {
	        			  cellNo++;
	        			  HSSFCell cell = (HSSFCell) cells.next();
	        			  columnIndex = cell.getColumnIndex();
	        			  System.out.print("columnIndex**************"+columnIndex+"ARTI");
		              		if(columnIndex > 1)
		              		{
		              			whdistmappbulkErrDto = new WHdistmappbulkDto();
		              			whdistmappbulkErrDto.setErr_msg("File should contain only two data column");
		              			list.add(whdistmappbulkErrDto);
		              			return list;
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
		            	/*	if(cellValue ==  null || "".equalsIgnoreCase(cellValue))
		            		{
		            			whdistmappbulkErrDto = new WHdistmappbulkDto(); 
		            			whdistmappbulkErrDto.setErr_msg("File should contain All Required Values");
		              			 list.add(whdistmappbulkErrDto);
		              			 return list;
		            			
		            		}
		            		*/
		            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
		            		if(cellNo==1){
		            			boolean validate = validateOlmId(cellValue);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				whdistmappbulkErrDto = new WHdistmappbulkDto(); 
		            				whdistmappbulkErrDto.setErr_msg("Invalid OLM Id at row No: "+(rowNumber)+". It should be 8 character.");
			              			list.add(whdistmappbulkErrDto);
		            				
		            			}else{
		            				if(validateOlmdistId(cellValue)){
		            				distOlmId=cellValue;
			            			//distProdComb = cellValue;
		            				wHdistmappbulkDto.setDistOlmId(cellValue);
		            				}else
		            				{
		            					validateFlag =false;
		            					whdistmappbulkErrDto = new WHdistmappbulkDto(); 
			            				whdistmappbulkErrDto.setErr_msg("Invalid Distributor OLM Id at row No:  "+(rowNumber));
				              			list.add(whdistmappbulkErrDto);
		            					
		            				}
			            			
			            		}
		            		}else if(cellNo==2){
		            			if(validateWareHouseCode(cellValue).equalsIgnoreCase("NWH")){
		            			
		            				validateFlag =false;
		            				whdistmappbulkErrDto = new WHdistmappbulkDto(); 
		            				whdistmappbulkErrDto.setErr_msg("Invalid WareHouse Code at row No:"+(rowNumber));
			              			list.add(whdistmappbulkErrDto);
		            			
		            			}else if(validateWareHouseCode(cellValue).equalsIgnoreCase("0")){
		            				
		            				validateFlag =false;
		            				whdistmappbulkErrDto = new WHdistmappbulkDto(); 
		            				whdistmappbulkErrDto.setErr_msg("Inactive WareHouse Code at row No:"+(rowNumber));
			              			list.add(whdistmappbulkErrDto);
		            			}else{
		            			wareHouseCode=cellValue;
		            			distProdComb += wHdistmappbulkDto.getDistOlmId()+"#"+cellValue;
		            			wHdistmappbulkDto.setWareHouseId(cellValue);
		            			System.out.println("distProdComb1111111111111111"+distProdComb);
		            			rst = validateDistOlmId(wareHouseCode,distOlmId);
		            			//validateFlag =true;
		            			if(rst.next())
		                		{
		            				distId = rst.getString("OLM_ID");
		            				warehouseId = rst.getString("WAREHOUSE_CODE");
		            				wHdistmappbulkDto.setDistOlmId(distId);
		        					wHdistmappbulkDto.setWareHouseId(warehouseId);
		        					
		            				
		            			}else{
		            				validateFlag =false;
		            				whdistmappbulkErrDto = new WHdistmappbulkDto();
		            				whdistmappbulkErrDto.setErr_msg("Warehouse Code does not belong to the Circle of Distributor at row No: "+(rowNumber)+".");
		                  			list.add(whdistmappbulkErrDto);
		            			}
		            			}
			            		
		            		}
		            		
	        		  }
	        		  else
	        		  {
	        			  validateFlag =false;
	        			  whdistmappbulkErrDto = new WHdistmappbulkDto(); 
	        			  whdistmappbulkErrDto.setErr_msg("File should contain only two data column at row No: "+(rowNumber)+".");
              			 list.add(whdistmappbulkErrDto);
              			 return list;
	        		  }
	        		  
	        		  
	            	}
	    			if(cellNo !=2){
		            		validateFlag =false;
		            		whdistmappbulkErrDto = new WHdistmappbulkDto(); 
		            		whdistmappbulkErrDto.setErr_msg("File should contain two data Headers");
		            		List list2 = new ArrayList();
		            		list2.add(whdistmappbulkErrDto);
	              			return list2;
		            	}
	            	
	            	System.out.println("distOlmId*********"+distOlmId);
	            	if(distOlmId !=null && !distOlmId.equalsIgnoreCase("null") && !distOlmId.equalsIgnoreCase("")){
	            	
	            	if(checkDuplicate.contains(distOlmId))
            		{
	        	  		validateFlag =false;
	        	  		whdistmappbulkErrDto = new WHdistmappbulkDto();
	        	  		whdistmappbulkErrDto.setErr_msg("File contains duplicate Entries For Distributor : "+distOlmId.split("#")[0]);
              			list.add(whdistmappbulkErrDto);
	        	  		
	        	 	}else{
	        	 		//validateFlag =true;
            			checkDuplicate.add(distOlmId);
            		}
	            	}
	            	
	            	System.out.println("distProdComb*********"+distProdComb);
	            	if(distProdComb !=null && !distProdComb.equalsIgnoreCase("null") && !distProdComb.equalsIgnoreCase("")){
	        	  	if(checkDuplicate1.equals(distProdComb))
            		{
	        	  		validateFlag =false;
	        	  		whdistmappbulkErrDto = new WHdistmappbulkDto();
	        	  		whdistmappbulkErrDto.setErr_msg("File contains duplicate Entries For Distributor and warehouse code : "+distProdComb.split("#")[0]);
              			list.add(whdistmappbulkErrDto);
	        	  		
	        	 	}else{
	        	 		//validateFlag =true;
	        	 		checkDuplicate1.add(distProdComb);
            		}
	            	}
	        	  	
	        	  rowNumber++;
	            }
	            else
	            {
	            	whdistmappbulkErrDto = new WHdistmappbulkDto();
	            	whdistmappbulkErrDto.setErr_msg("File should contain only two data column");
         			list.add(whdistmappbulkErrDto);
         			return list;
	            }
 	              logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  ");
	        	  if(validateFlag){
	        		  wHdistmappbulkDto.setErr_msg("SUCCESS");
	        		  list.add(wHdistmappbulkDto);  		        		  
	        	  }
	             
	        	  }  else{

	  	        	//For Header Checking  
	  	        	int columnIndex = 0;
	  	              int cellNo = 0;
	  	              if(cells != null)
	  	              {
	  	            	  while (cells.hasNext()) {
	  		        		  if(cellNo < 2)
	  		        		  {
	  		        			  cellNo++;
	  		        			  HSSFCell cell = (HSSFCell) cells.next();
	  		        			  
	  		        			  	columnIndex = cell.getColumnIndex();
	  			              		if(columnIndex > 3)
	  			              		{
	  			              			validateFlag =false;
	  			              		whdistmappbulkErrDto = new WHdistmappbulkDto();
	  			              	whdistmappbulkErrDto.setErr_msg("File should contain only two data column");
	  			              			List list2 = new ArrayList();
	  				            		list2.add(whdistmappbulkErrDto);
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
	  			            			whdistmappbulkErrDto = new WHdistmappbulkDto();
	  			            			whdistmappbulkErrDto.setErr_msg("File should contain All Required Values");
	  			              			List list2 = new ArrayList();
	  				            		list2.add(whdistmappbulkErrDto);
	  			              			return list2;
	  			               		}
	  			            		
	  			            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
	  			            		if(cellNo==1){
	  			            			if(!"distributor olm id".equals(cellValue.trim().toLowerCase()))
	  				            		{
	  			            				validateFlag =false;
	  			            				whdistmappbulkErrDto = new WHdistmappbulkDto(); 
	  			            				whdistmappbulkErrDto.setErr_msg("Invalid Header For Distributor OLM ID");
	  				              			list.add(whdistmappbulkErrDto);
	  			            				
	  			            			}
	  			            		}else if(cellNo==2){
	  			            			if(!"warehouse code".equals(cellValue.trim().toLowerCase()))
	  				            		{
	  			            				validateFlag =false;
	  			            				whdistmappbulkErrDto = new WHdistmappbulkDto(); 
	  			            				whdistmappbulkErrDto.setErr_msg("Invalid Header For WareHouse Code");
	  				              			list.add(whdistmappbulkErrDto);
	  			            				
	  			            			}
	  			            		}
	  		        		  }else{
	  		        			  validateFlag =false;
	  		        			whdistmappbulkErrDto = new WHdistmappbulkDto(); 
	  		        			whdistmappbulkErrDto.setErr_msg("File should contain only two data Headers");
	  		        			  List list2 = new ArrayList();
	  			            	list2.add(whdistmappbulkErrDto);
	  		              		return list2;
	  		        		  }
	  	            	  }
	  	            	  if(cellNo !=2){
	  		            		validateFlag =false;
	  		            		whdistmappbulkErrDto = new WHdistmappbulkDto(); 
	  		            		whdistmappbulkErrDto.setErr_msg("File should contain two data Headers");
	  		            		List list2 = new ArrayList();
	  		            		list2.add(whdistmappbulkErrDto);
	  	              			return list2;
	  		            	}
	  	              } else
	  		            {
	  	            	whdistmappbulkErrDto = new WHdistmappbulkDto();
	  	            	whdistmappbulkErrDto.setErr_msg("File should contain two data column");
	  		            	  List list2 = new ArrayList();
	  			            	list2.add(whdistmappbulkErrDto);
	  		              		return list2;
	  		            }
	  	        	rowNumber ++;
	  	         }  
	        	  k++;
	              
	        }
	         logger.info("K == "+k);
	        if(k==1){
	        	 wHdistmappbulkDto = new WHdistmappbulkDto();
	        	 wHdistmappbulkDto.setErr_msg("Blank File can not be uploaded!!");
        		  list.add(wHdistmappbulkDto);  	
	        }
	        
	    }
		catch (IOException ioe) {
        	
        	logger.info(ioe);
        	throw new DAOException("Please Upload a valid Excel Format File");
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
	
	
	
	public boolean validateWareHouse(String str)
	{
		if(str.length() >= 1)
		{
			return false;
		}
	
		
		return true;
	}
	
	
	public ResultSet validateDistOlmId(String wareHouseCode,String distOlmId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			logger.info("inside --- validateDistOlmId wareHouseCode  == "+wareHouseCode +" and dist OLM id == "+distOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_WH_DISTID);
			pstmt.setString(1,distOlmId);
			pstmt.setString(2,wareHouseCode.toUpperCase());
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
	
	public String validateWareHouseCode(String wareHouseCode)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		boolean flag = false;
		String activeFlag = "";
		try{
			
			logger.info("inside --- validateWareHouseCode == "+wareHouseCode);
			pstmt = connection.prepareStatement(SQL_SELECT_WAREHOUSE_CODE);
			pstmt.setString(1,wareHouseCode);
			rs =pstmt.executeQuery();
			if(rs.next()){
				
				activeFlag = rs.getString(1);
					
			}else{
				activeFlag = "NWH";
				
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateWareHouseCode method--"+e.getMessage());
		}
		return activeFlag;
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
	
	
	public String addDistWarehouseMap(List list)
	{
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rst=null;
		String returnstr="";
		logger.info("Size--"+list.size());
		String olmId ="";
		String wareHouseId="";
		WHdistmappbulkDto whdistmappbulkDto = null;
	try
	{	
		System.out.println("calling addDistWarehouseMAp ************"+SQL_INSERT_WH_DIST_MAP);
		pstmt = connection.prepareStatement(SQL_INSERT_WH_DIST_MAP);
		pstmt1 = connection.prepareStatement(SQL_UPDATE_WAREHOUSE_CODE);
		pstmt2 = connection.prepareStatement(SQL_CHECK_WAREHOUSE_CODE);
		Iterator itr = list.iterator();
		int updaterows = 0;
		
		while(itr.hasNext())
		{
			whdistmappbulkDto = (WHdistmappbulkDto) itr.next();
			pstmt2.setString(1, whdistmappbulkDto.getDistOlmId());
			System.out.println("calling addDistWarehouseMAp **select**olmId********"+whdistmappbulkDto.getDistOlmId());
			rst = pstmt2.executeQuery();
			if(rst.next()){
				if(rst.getInt(1)>=1){
					
					olmId = whdistmappbulkDto.getDistOlmId();
					wareHouseId = whdistmappbulkDto.getWareHouseId();
					System.out.println("calling addDistWarehouseMAp **update**olmId********"+olmId);
					System.out.println("calling addDistWarehouseMAp ****wareHouseId********"+wareHouseId);
					pstmt1.setString(1, wareHouseId);
					pstmt1.setString(2, olmId);
					pstmt1.executeUpdate();
					
				}
					
				else{
					
					
					olmId = whdistmappbulkDto.getDistOlmId();
					wareHouseId = whdistmappbulkDto.getWareHouseId();
					System.out.println("calling addDistWarehouseMAp **insert**olmId********"+olmId);
					System.out.println("calling addDistWarehouseMAp ****wareHouseId********"+wareHouseId);
					pstmt.setString(1, wareHouseId);
					pstmt.setString(2, olmId);
					pstmt.executeUpdate();			
					
				}
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
		try {
			if (rst != null)
				rst.close();
			if (pstmt != null)
				pstmt.close();
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			
		} catch (Exception e) {
		}
		DBConnectionManager.releaseResources(pstmt, rst);
		DBConnectionManager.releaseResources(pstmt1, rst);
		DBConnectionManager.releaseResources(pstmt2, rst);
		
	}
		
		return returnstr;
	}

	public List<WHdistmappbulkDto> getALLWhDistMapData() {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
	
		List<WHdistmappbulkDto> whDistmapList= new ArrayList<WHdistmappbulkDto>();
		
		try {
			
			String sql = "select (select circle_name from vr_circle_master vcm where vcm.CIRCLE_ID = dwm.circle_id) circle_name , "+ 
						 " WH_CODE,WH_NAME,WH_ADDRESS1|| WH_ADDRESS2 as WH_ADDRESS "+
						" from DP_WH_MASTER dwm  where dwm.is_active = '1'" ;
			db2conn = DBConnectionManager.getDBConnection();
			ps = db2conn.prepareStatement(sql);
			
			System.out.println(sql);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				WHdistmappbulkDto recDTO = new WHdistmappbulkDto();
				recDTO.setCircleName(rs.getString("circle_name"));
				recDTO.setWareName(rs.getString("WH_NAME"));
				recDTO.setWarehouseAddress(rs.getString("WH_ADDRESS"));
				//recDTO.setDistOlmId(rs.getString("OLM_ID"));
				recDTO.setWareHouseId(rs.getString("WH_CODE"));
				whDistmapList.add(recDTO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting WareHouse Distributor Mapping Exception message: "
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
		return whDistmapList;
		
	}
	
	}

