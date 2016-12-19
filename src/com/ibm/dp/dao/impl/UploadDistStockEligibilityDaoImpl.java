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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.db2.jcc.a.e;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.UploadDistStockEligibilityDao;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.hbo.common.DBConnection;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class UploadDistStockEligibilityDaoImpl extends BaseDaoRdbms  implements
		UploadDistStockEligibilityDao {
	private static Logger logger = Logger.getLogger(ViewStockEligibilityDaoImpl.class.getName());
	public static final String FETCH_REGION_LIST="SELECT REGION_ID,REGION_NAME FROM VR_REGION_MASTER WHERE STATUS='A' AND REGION_ID !=0 with ur";
	public static final String FETCH_CIRCLE_LIST = "SELECT CIRCLE_ID,CIRCLE_CODE,CIRCLE_NAME FROM VR_CIRCLE_MASTER WHERE REGION_ID=? and STATUS = 'A' with ur";
	public static final String FETCH_CIRCLE_AND_ACCOUNTNAME = "SELECT CM.CIRCLE_NAME,AD.ACCOUNT_NAME,RM.REGION_NAME FROM VR_LOGIN_MASTER LM,VR_ACCOUNT_DETAILS AD,VR_CIRCLE_MASTER CM,VR_REGION_MASTER RM "+
	 "WHERE LM.LOGIN_NAME= ? and LM.LOGIN_ID = AD.ACCOUNT_ID and AD.CIRCLE_ID = CM.CIRCLE_ID and RM.REGION_ID = CM.REGION_ID  with ur"; //query changed by neetika  CM.REGION_ID 
	//Addition by Neetika for BFR 1 of Upload Eligbility
	public static final String INSERT_FOR_COMMERCIAL_SF = "INSERT INTO DPDTH.DP_ELIGIBILITY_COMMERCIAL_SF(ZONE_NAME, CIRCLE_NAME, LOGIN_NAME, ACCOUNT_NAME, ACT_SD_CURRENT, ACT_HD_CURRENT, ACT_HDDVR_CURRENT, SD_CLOSING, HD_CLOSING, HDDVR_CLOSING, DAYS_SD, DAYS_HD, MAX_ELIG_SD, MAX_ELIG_HD, SD_CURRENT_AVG, HD_CURRENT_AVG, SD_PREV_ACT, HD_PREV_ACT, SD_PREV_ACT_1, HD_PREV_ACT_1, AVG_SD_ACT, AVG_HD_ACT, FINAL_SD, FINAL_HD, CREATEDDATE, CREATEDBY,SECURITY_ELIGIBILITY, SECURITY_BALANCE, SD_ELIG, HD_ELIG, HDDVR_ELIG, PROD_ELIG_1, PROD_ELIG_2, PROD_ELIG_3, PROD_ELIG_4, PROD_ELIG_5, COLUMN_NEW1, COLUMN_NEW2, COLUMN_NEW3, COLUMN_NEW4, COLUMN_NEW5, COLUMN_NEW6, COLUMN_NEW7, COLUMN_NEW8, COLUMN_NEW9, COLUMN_NEW10, COLUMN_NEW11, COLUMN_NEW12, COLUMN_NEW13, COLUMN_NEW14, COLUMN_NEW15, COLUMN_NEW16, COLUMN_NEW17, COLUMN_NEW18, COLUMN_NEW19, COLUMN_NEW20)" + 
   " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current timestamp, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	
	public static final String INSERT_FOR_SWAP_SF = "INSERT INTO DPDTH.DP_ELIGIBILITY_SWAP_SF(ZONE_NAME, CIRCLE_NAME, LOGIN_NAME, ACCOUNT_NAME,SD_ELIG, HD_ELIG, PVR_ELIG, HDDVR_ELIG, SDPLUS_ELIG, PROD_ELIG_1, PROD_ELIG_2, PROD_ELIG_3, PROD_ELIG_4, PROD_ELIG_5, SD_CLOSING, HD_CLOSING, PVR_CLOSING, HDDVR_CLOSING, SDPLUS_CLOSING, SD_CONSUMPTION, HD_CONSUMPTION, PVR_CONSUMPTION, HDDVR_CONSUMPTION, SDPLUS_CONSUMPTION, SD_ELIGIBILITY, HD_ELIGIBILITY, PVR_ELIGIBILITY, HDDVR_ELIGBILITY, SDPLUS_ELIBILITY, CREATEDDATE, CREATEDBY,COLUMN_NEW1, COLUMN_NEW2, COLUMN_NEW3, COLUMN_NEW4, COLUMN_NEW5, COLUMN_NEW6, COLUMN_NEW7, COLUMN_NEW8, COLUMN_NEW9, COLUMN_NEW10, COLUMN_NEW11, COLUMN_NEW12, COLUMN_NEW13, COLUMN_NEW14, COLUMN_NEW15, COLUMN_NEW16, COLUMN_NEW17, COLUMN_NEW18, COLUMN_NEW19, COLUMN_NEW20) "+
   " VALUES(?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current timestamp,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String DELETE_FOR_SWAP_SF = " delete from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME=? and CREATEDDATE < ( select CREATEDDATE from ( "+
" select rownumber() over(order by CREATEDDATE desc) as rid,CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME=? ) where rid=2 ) with ur";
	
	public static final String DELETE_FOR_COMM_SF= " delete from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME=? and CREATEDDATE < ( select CREATEDDATE from ( "+
	" select rownumber() over(order by CREATEDDATE desc) as rid,CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME=? ) where rid=2 ) with ur";
	
	//Addition by Neetika for BFR 1 of Upload Eligbility
	public static final String INSERT_FOR_COMMERCIAL_SSD = "INSERT INTO DPDTH.DP_ELIGIBILITY_COMMERCIAL_SSD(ZONE_NAME, CIRCLE_NAME, LOGIN_NAME, ACCOUNT_NAME, ACT_SD_CURRENT, ACT_HD_CURRENT, ACT_HDDVR_CURRENT, SD_CLOSING, HD_CLOSING, HDDVR_CLOSING, SD_AVAILABLE, BALANCE, SD_ELIGIBILITY, DAYS_SD, DAYS_HD, MAX_ELIG_SD, MAX_ELIG_HD, SD_CURRENT_AVG, HD_CURRENT_AVG, SD_PREV_ACT, HD_PREV_ACT, SD_PREV_ACT_1, HD_PREV_ACT_1, AVG_SD_ACT, AVG_HD_ACT, FINAL_SD, FINAL_HD, CREATEDDATE, CREATEDBY, SECURITY_ELIGIBILITY, SECURITY_BALANCE, SD_ELIG, HD_ELIG, HDDVR_ELIG, PROD_ELIG_1, PROD_ELIG_2, PROD_ELIG_3, PROD_ELIG_4, PROD_ELIG_5, COLUMN_NEW4, COLUMN_NEW5, COLUMN_NEW6, COLUMN_NEW7, COLUMN_NEW8, COLUMN_NEW9, COLUMN_NEW10, COLUMN_NEW11, COLUMN_NEW12, COLUMN_NEW13, COLUMN_NEW14, COLUMN_NEW15, COLUMN_NEW16, COLUMN_NEW17, COLUMN_NEW18, COLUMN_NEW19, COLUMN_NEW20, COLUMN_NEW1, COLUMN_NEW2, COLUMN_NEW3) "+
    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current timestamp, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	
	public static final String INSERT_FOR_SWAP_SSD = "INSERT INTO DPDTH.DP_ELIGIBILITY_SWAP_SSD(ZONE_NAME, CIRCLE_NAME, LOGIN_NAME, ACCOUNT_NAME, SD_CLOSING, HD_CLOSING, PVR_CLOSING, HDDVR_CLOSING, SDPLUS_CLOSING, ISSUANCE_AMOUNT, CONVERT_COMMERCIAL, BALANCE, SD_CONSUMPTION_M, HD_CONSUMPTION_M, PVR_CONSUMPTION_M, HDDVR_CONSUMPTION_M, SDPLUS_CONSUMPTION_M, SECURITY, SD_CONSUMPTION, HD_CONSUMPTION, PVR_CONSUMPTION, HDDVR_CONSUMPTION, SDPLUS_CONSUMPTION, DEBIT, CREATEDDATE, CREATEDBY,SECURITY_ELIGIBILITY, SD_ELIG, HD_ELIG, PVR_ELIG, HDDVR_ELIG, SDPLUS_ELIG, PROD_ELIG_1, PROD_ELIG_2, PROD_ELIG_3, PROD_ELIG_4, PROD_ELIG_5,SECURITY_DEPOSIT, COLUMN_NEW1, COLUMN_NEW2, COLUMN_NEW3, COLUMN_NEW4, COLUMN_NEW5, COLUMN_NEW6, COLUMN_NEW7, COLUMN_NEW8, COLUMN_NEW9, COLUMN_NEW10, COLUMN_NEW11, COLUMN_NEW12, COLUMN_NEW13, COLUMN_NEW14, COLUMN_NEW15, COLUMN_NEW16, COLUMN_NEW17, COLUMN_NEW18, COLUMN_NEW19, COLUMN_NEW20) "+
  "  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current timestamp, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
	
	public static final String DELETE_FOR_SWAP_SSD = " delete from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME=? and CREATEDDATE < ( select CREATEDDATE from ( "+
" select rownumber() over(order by CREATEDDATE desc) as rid,CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME=? ) where rid=2 ) with ur";
	
	public static final String DELETE_FOR_COMM_SSD= " delete from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME=? and CREATEDDATE < ( select CREATEDDATE from ( "+
	" select rownumber() over(order by CREATEDDATE desc) as rid,CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME=?) where rid=2 ) with ur";
	
	public static final String SQL_SELECT_OLMID 	= DBQueries.SQL_SELECT_OLMID;
	public static final String SQL_SELECT_OLMID_ZONE 	= DBQueries.SQL_SELECT_OLMID_ZONE;
	public static final String SQL_SELECT_OLMID_BY_ACCOUNT_NAME 	= DBQueries.SQL_SELECT_OLMID_BY_ACCOUNT_NAME;
	public List<ViewStockEligibilityDTO> getRegionList()
			 throws DAOException {
		// TODO Auto-generated method stub
		List<ViewStockEligibilityDTO> regionList = new ArrayList<ViewStockEligibilityDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		ViewStockEligibilityDTO viewStockDto=null;
		Connection con = null;
		try
		{
			con = DBConnectionManager.getDBConnection(); // db2
			pstmt = con.prepareStatement(FETCH_REGION_LIST);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				viewStockDto = new ViewStockEligibilityDTO();
				viewStockDto.setRegionId(rset.getInt("REGION_ID"));
				viewStockDto.setRegionName(rset.getString("REGION_NAME"));
				regionList.add(viewStockDto);
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rset != null)
					rset.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in getRegionList of UploadDistStockEligibilityDaoimpl "+e.getMessage());
			}
		}
		logger.info("getRegionListtreturn::::"+regionList.size());
		
		return regionList;
	}

	
	public List<ViewStockEligibilityDTO> getCircleList(int regionId)
			 throws DAOException {
		// TODO Auto-generated method stub
		List<ViewStockEligibilityDTO> circleList = new ArrayList<ViewStockEligibilityDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		ViewStockEligibilityDTO viewStockDto=null;
		Connection con = null;
		try
		{
			con = DBConnectionManager.getDBConnection(); // db2
			pstmt = con.prepareStatement(FETCH_CIRCLE_LIST);
			pstmt.setInt(1, regionId);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				viewStockDto = new ViewStockEligibilityDTO();
				viewStockDto.setCircleId(rset.getInt("CIRCLE_ID"));
				viewStockDto.setCircleCode(rset.getString("CIRCLE_CODE"));
				viewStockDto.setCircleName(rset.getString("CIRCLE_NAME"));
				circleList.add(viewStockDto);
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rset != null)
					rset.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in getCircleList of UploadDistStockEligibilityDaoimpl "+e.getMessage());
			}
		}
		logger.info("getCircleListtreturn::::"+circleList.size());
		
		return circleList;
	}
	public String uploadExcel(InputStream inputstream,int productType,int distType,long dthadminid,int regionId) throws DAOException 
	{
		logger.info("*************** in uploadExcel method of UploeadDistStockEli");
		List<ViewStockEligibilityDTO> eligibilityStocklist = new ArrayList<ViewStockEligibilityDTO>();
		ViewStockEligibilityDTO stockEligibilityDto = null;
		String distOlmId ="";
		String error_Msg = "";
		boolean validateFlag =true;
		String temps="";
		int rowNumber = 1;
		try
		{
			  HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			  HSSFSheet sheet = workbook.getSheetAt(0);
			  Iterator rows = sheet.rowIterator();
			  int totalrows = sheet.getLastRowNum()+1;
			  logger.info("Total Rows == "+sheet.getLastRowNum());
			  
			
				if(productType == 2 &&   distType == 1 )
    			{
        			
        			temps=": SWAP SF File";
    			}
    			else if(productType == 2 &&   distType == 2)
    			{
    				
    				temps=": SWAP SSD File";
        		}
    			else if(productType == 1 &&   distType == 1) //comm
    			{
    				
    				temps=": Commercial SF File";
        		}	
    			else if(productType == 1 &&   distType == 2) //comm
    			{
    				
    				temps=": Commercial SSD File";
        		}	
			  if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_FOR_DIST_STOCK_ELIGIBILITY_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			  {
				  	error_Msg = "Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_FOR_DIST_STOCK_ELIGIBILITY_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" rows are allowed in a file.";
        		   return error_Msg+""+temps;
			  }
			   rowNumber = 1;
	          int k=1;
	          while (rows.hasNext()) {
	        	  HSSFRow row = (HSSFRow) rows.next();
	        	  Iterator cells = row.cellIterator();
	        	 // logger.info("POP************rowNumber"+rowNumber+"cells*********"+cells);
	        	  
	        	  if(rowNumber>1){
	        		  stockEligibilityDto = new ViewStockEligibilityDTO();
	        	  int columnIndex = 0;
	              int cellNo = 0;
	              int i=0;
	              int temp[]=new int[100];//to chck if some column is blank
	              if(cells != null)
	              {
	            	distOlmId ="";
	            	 // logger.info("productType :"+productType +" distType :"+distType);
	            	 //logger.info("if loop.............");
	    			while (cells.hasNext()) {
	    				//**************validating the template as per product type and dist type************************
	    				 //logger.info("columnIndex**************++++++++++++");
	        			  HSSFCell cell = (HSSFCell) cells.next();
	        			  columnIndex = cell.getColumnIndex();
	        			  temp[i]=columnIndex;
	        			  i++;
	        			
	        			 
		              		String cellValue = null;
		            		switch(cell.getCellType()) {
			            		case HSSFCell.CELL_TYPE_NUMERIC:
			            		{
			            			//logger.info((long)cell.getNumericCellValue()+"cellValue"+cellValue);
			            			
			            			if(productType == 2 &&   distType == 1 )
			            			{
				            			//if(columnIndex>=31 && columnIndex<=47) //double
				            			//cellValue = String.valueOf(cell.getNumericCellValue());
				            			//else
				            			//{
				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
				            			//}
				            			
			            			}
			            			else if(productType == 2 &&   distType == 2)
			            			{
			            				//if((columnIndex>=38 && columnIndex<=54)) //double || (columnIndex==4 || columnIndex==21 || columnIndex==22 || columnIndex==23 || columnIndex==30 || columnIndex==37) // as per UAT all double values should be rounded to lower integer value
			            				//cellValue = String.valueOf(cell.getNumericCellValue());
			            				//else
				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
			            				
				            		}
			            			else if(productType == 1 &&   distType == 1) //comm
			            			{
			            				//if((columnIndex>=38 && columnIndex<=52) ) //double// || (columnIndex==4)
			            				//cellValue = String.valueOf(cell.getNumericCellValue());
			            				//else
				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
			            				
				            		}	
			            			else if(productType == 1 &&   distType == 2) //comm
			            			{
			            				//if((columnIndex>=41 && columnIndex<=55) ) //double || (columnIndex==4) || (columnIndex==22)
			            				//cellValue = String.valueOf(cell.getNumericCellValue());
			            				//else
				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
			            				
				            		}	
			            			//logger.info((long)cell.getNumericCellValue()+"cellValue"+cellValue+"cell.getNumericCellValue()"+cell.getNumericCellValue());
			            		}
			            		break;
			            		case HSSFCell.CELL_TYPE_STRING:
			            			cellValue = cell.getStringCellValue();
			            	
				            	break;
		            		}
		            	//	logger.info(columnIndex+ " Remove these logs "+cellValue);
		            		if(cellValue == null || cellValue.trim().equals(""))
		            		{
		            			validateFlag =false;
		            			error_Msg = "File should contain All Required values at row no :"+rowNumber;
		            			stockEligibilityDto = null;
		            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		              			 return error_Msg+""+temps;
		            		}
		            		else
		            		{
		            			//logger.info("cellValue"+cellValue+" columnIndex"+columnIndex);
		            			if(columnIndex == 0)
		            			{
		            				stockEligibilityDto.setCircleName(cellValue);
		            			}
		            			if(columnIndex == 1)
		            			{
		            				if(validateOlmId(cellValue))
		            				{

			            				if(validateOlmdistId(cellValue,regionId)){
			            					distOlmId=cellValue;	
			            					//logger.info("distOlmId"+distOlmId);
			            					stockEligibilityDto.setOlmId(distOlmId);
			            				}else
			            				{
			            					validateFlag =false;
			            					error_Msg = "Invalid OLM Id for the selected zone at row No:  "+(rowNumber);
			            					stockEligibilityDto = null;
			            					return error_Msg+""+temps;
			            				}
		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					error_Msg = "Invalid OLM Id at row No: "+(rowNumber)+". It should be 8 character.";
		            					stockEligibilityDto = null;
				            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
				              			return error_Msg+""+temps;
		            				}
		            			}
		            			/**********validating dist id with account name***************/
		            			/*if(columnIndex == 2)
		            			{
		            				if(validateOlmdistIdWithAccountName(distOlmId,cellValue))
		            				{
		            					stockEligibilityDto.setAccountName(cellValue);
		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					error_Msg = "Invalid Account Name at row No:  "+(rowNumber);
		            					stockEligibilityDto = null;
		            					return error_Msg;	
		            				}
		            			}*/
		            			/**********End of validating dist id with account name***************/
		            		
			        			  if(productType == 1 &&   distType == 1 ) // Commercial with SF
			        			  {
			        				  if(columnIndex < com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSF) //30 new columns added //53 is total
			    	        		  {
			        					  
			        					  //new 10 columns
			        					  if(columnIndex == 3)
			        					  {
			        						  stockEligibilityDto.setSecurityDeposit(Integer.parseInt(cellValue)); //security eligbility integer field
			        					  }
			        					  if(columnIndex == 4)//security balance double field
			        					  {
			        						  stockEligibilityDto.setSecuritybalance(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 5)
			        					  {
			        						  stockEligibilityDto.setStockEligSD(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 6)
			        					  {
			        						  stockEligibilityDto.setStockEligHD(Integer.parseInt(cellValue));
			        					  }
			        					
			        					  if(columnIndex == 7)
			        					  {
			        						  stockEligibilityDto.setStockEligHDDVR(Integer.parseInt(cellValue));
			        					  }
			        					  
			        					  if(columnIndex == 8)
			        					  {
			        						  stockEligibilityDto.setStockEligP1(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 9)
			        					  {
			        						  stockEligibilityDto.setStockEligP2(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 10)
			        					  {
			        						  stockEligibilityDto.setStockEligP3(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 11)
			        					  {
			        						  stockEligibilityDto.setStockEligP4(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 12)
			        					  {
			        						  stockEligibilityDto.setStockEligP5(Integer.parseInt(cellValue));
			        					  }
			        				
			        					  
			        					  
			        					  if(columnIndex == 13)
			        					  {
			        						  stockEligibilityDto.setActivationSDCurrentMonth(Integer.parseInt(cellValue)); 
			        					  }
			        					
			        					  if(columnIndex == 14)
			        					  {
			        						  stockEligibilityDto.setActivationAndUpgradeHDCurentMonth(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 15)
			        					  {
			        						  stockEligibilityDto.setActivationHDDVRcurrentMonth(Integer.parseInt(cellValue)); 
			        					  }
			        					  
			        					  if(columnIndex == 16)
			        					  {
			        						  stockEligibilityDto.setDummy1(Integer.parseInt(cellValue));
			        					  }
		
			        					  if(columnIndex == 17)
			        					  {
			        						  stockEligibilityDto.setClosingStockSDandPlus(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 18)
			        					  {
			        						  stockEligibilityDto.setClosingStockHD(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 19)
			        					  {
			        						  stockEligibilityDto.setClosingStockHDDVR(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 20)
			        					  {
			        						  stockEligibilityDto.setDummy2(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 21)
			        					  {
			        						  stockEligibilityDto.setNosofdayStockSD(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 22)
			        					  {
			        						  stockEligibilityDto.setNosofdaystockHD(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 23)
			        					  {
			        						  stockEligibilityDto.setDummy3(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 24)
			        					  {
			        						  stockEligibilityDto.setMaxOrderEligibilitySD(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 25)
			        					  {
			        						  stockEligibilityDto.setMaxOrderEligibilityHD(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 26)
			        					  {
			        						  stockEligibilityDto.setDummy4(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 27)
			        					  {
			        						  stockEligibilityDto.setAveragecurrentMonthActivationSD(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 28)
			        					  {
			        						  stockEligibilityDto.setAveragecurrentMonthActivationHD(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 29)
			        					  {
			        						  stockEligibilityDto.setDummy5(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 30)
			        					  {
			        						  stockEligibilityDto.setActivationSDcurrentMonthOne(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 31)
			        					  {
			        						  stockEligibilityDto.setActivationAndUpgradeHDcurrentMonthOne(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 32)
			        					  {
			        						  stockEligibilityDto.setActivationSDcurrentMonthTwo(Integer.parseInt(cellValue)); 
			        					  }
			        					  
			        					  if(columnIndex == 33)
			        					  {
			        						  stockEligibilityDto.setActivationAndUpgradeHDcurrentMonthTwo(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 34)
			        					  {
			        						  stockEligibilityDto.setAverageActivationNormal(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 35)
			        					  {
			        						  stockEligibilityDto.setAverageActivationHD(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 36)
			        					  {
			        						  stockEligibilityDto.setCurrentmonthorAveragewhichislessSD(Integer.parseInt(cellValue)); 
			        					  }
			        					  if(columnIndex == 37)
			        					  {
			        						  stockEligibilityDto.setCurrentmonthorAveragewhichislessHD(Integer.parseInt(cellValue)); 
			        					  }
			        					  
			        					 
			        					  if(columnIndex == 38 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy6(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 39 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy7(Double.parseDouble(cellValue));
			        					  }
			        					  //logger.info(columnIndex+"cellValue"+cellValue);
			        					  if(columnIndex == 40 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy8(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 41 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy9(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 42 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy10(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 43 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy11(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 44 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy12(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 45 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy13(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 46 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy14(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 47 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy15(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 48 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy16(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 49 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy17(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 50 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy18(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 51 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy19(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 52 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy20(Double.parseDouble(cellValue));
			        					  }  
			    	        		  }
			        				  else
			        				  {
			        					  validateFlag =false;
			    	        			  error_Msg = "File should contain only 53 data columns.";
			    	        			  stockEligibilityDto = null;
			    	        			  logger.info("*************** error_Msg in daoimpl :"+error_Msg);
			                  			 return error_Msg+""+temps;
			        				  }
			        			  }
			        			  
			        			  else if(productType == 1 &&   distType == 2 ) // Commercial with SSD
			        			  {
			        				  if(columnIndex < com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSSD) //30 new columns added //56 is total
			    	        		  {
			        					  
			        					  //new 10 columns
			        					  if(columnIndex == 3)
			        					  {
			        						  stockEligibilityDto.setSecurityDeposit(Integer.parseInt(cellValue)); //security eligbility integer field
			        					  }
			        					  if(columnIndex == 4)//security balance double field
			        					  {
			        						  stockEligibilityDto.setSecuritybalance(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 5)
			        					  {
			        						  stockEligibilityDto.setStockEligSD(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 6)
			        					  {
			        						  stockEligibilityDto.setStockEligHD(Integer.parseInt(cellValue));
			        					  }
			        					
			        					  if(columnIndex == 7)
			        					  {
			        						  stockEligibilityDto.setStockEligHDDVR(Integer.parseInt(cellValue));
			        					  }
			        					  
			        					  if(columnIndex == 8)
			        					  {
			        						  stockEligibilityDto.setStockEligP1(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 9)
			        					  {
			        						  stockEligibilityDto.setStockEligP2(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 10)
			        					  {
			        						  stockEligibilityDto.setStockEligP3(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 11)
			        					  {
			        						  stockEligibilityDto.setStockEligP4(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 12)
			        					  {
			        						  stockEligibilityDto.setStockEligP5(Integer.parseInt(cellValue));
			        					  }
			        				
			        					  
			        					  	  
			        					  
			        					  
			    	        		  if(columnIndex == 13)
			    	        		  {
			    	        			  stockEligibilityDto.setActivationSDCurrentMonth(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 14)
			    	        		  {
			    	        			  stockEligibilityDto.setActivationAndUpgradeHDCurentMonth(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 15)
			    	        		  {
			    	        			  stockEligibilityDto.setActivationHDDVRcurrentMonth(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  
			    	        		  
			    	        		  if(columnIndex == 16)
		        					  {
		        						  stockEligibilityDto.setDummy1(Integer.parseInt(cellValue));
		        					  }
			    	        		  
			    	        		  if(columnIndex == 17)
			    	        		  {
			    	        			  stockEligibilityDto.setClosingStockNormalSDBOX(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 18)
			    	        		  {
			    	        			  stockEligibilityDto.setClosingStockHDBox(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 19)
			    	        		  {
			    	        			  stockEligibilityDto.setClosingStockHDDVR(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  
			    	        		  if(columnIndex == 20)
		        					  {
		        						  stockEligibilityDto.setDummy2(Integer.parseInt(cellValue));
		        					  }
			    	        		  
			    	        		  if(columnIndex == 21)
			    	        		  {
			    	        			  stockEligibilityDto.setSDAvaliable(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 22)
			    	        		  {
			    	        			  stockEligibilityDto.setBalanceAvailable(Double.parseDouble(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 23)
			    	        		  {
			    	        			  stockEligibilityDto.setSDEligibility(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 24)
			    	        		  {
			    	        			  stockEligibilityDto.setNosofdayStockSD(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 25)
			    	        		  {
			    	        			  stockEligibilityDto.setNosofdaystockHD(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 26)
		        					  {
		        						  stockEligibilityDto.setDummy3(Integer.parseInt(cellValue));
		        					  }
			    	        		  if(columnIndex == 27)
			    	        		  {
			    	        			  stockEligibilityDto.setMaxOrderEligibilitySD(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 28)
			    	        		  {
			    	        			  stockEligibilityDto.setMaxOrderEligibilityHD(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 29)
		        					  {
		        						  stockEligibilityDto.setDummy4(Integer.parseInt(cellValue));
		        					  }
			    	        		  if(columnIndex == 30)
			    	        		  {
			    	        			  stockEligibilityDto.setAveragecurrentMonthActivationSD(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 31)
			    	        		  {
			    	        			  stockEligibilityDto.setAveragecurrentMonthActivationHD(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 32)
		        					  {
		        						  stockEligibilityDto.setDummy5(Integer.parseInt(cellValue));
		        					  }
			    	        		  if(columnIndex == 33)
			    	        		  {
			    	        			  stockEligibilityDto.setAverageSDCurrentMonthOneActivations(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 34)
			    	        		  {
			    	        			  stockEligibilityDto.setAverageHDCurrentMonthOneActivations(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 35)
			    	        		  {
			    	        			  stockEligibilityDto.setActivationSDCurrentMonthTwo(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 36)
			    	        		  {
			    	        			  stockEligibilityDto.setActivationAndUpgradeHDCurrentMonthTwo(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 37)
			    	        		  {
			    	        			  stockEligibilityDto.setAverageActivationSD(Integer.parseInt(cellValue));
			    	        		  } 
			    	        		  if(columnIndex == 38)
			    	        		  {
			    	        			  stockEligibilityDto.setAverageActivationHD(Integer.parseInt(cellValue));
			    	        		  } 
			    	        		  if(columnIndex == 39)
			    	        		  {
			    	        			  stockEligibilityDto.setCurrentmonthorAveragewhichislessNormalSD(Integer.parseInt(cellValue));
			    	        		  }
			    	        		  if(columnIndex == 40)
			    	        		  {
			    	        			  stockEligibilityDto.setCurrentmonthorAveragewhichislessHD(Integer.parseInt(cellValue));
			    	        		  }

		        					  
		        					  if(columnIndex == 41 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy6(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 42 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy7(Double.parseDouble(cellValue));
		        					  }
		        					  //logger.info(columnIndex+"cellValue"+cellValue);
		        					  if(columnIndex == 43 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy8(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 44 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy9(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 45 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy10(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 46 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy11(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 47 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy12(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 48 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy13(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 49 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy14(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 50 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy15(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 51 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy16(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 52 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy17(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 53 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy18(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 54 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy19(Double.parseDouble(cellValue));
		        					  }
		        					  if(columnIndex == 55 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
		        					  {
		        						  stockEligibilityDto.setDummy20(Double.parseDouble(cellValue));
		        					  }  
		        					  
		        					  
			    	        		  }
			        				  else
			        				  {
			        					  validateFlag =false;
				  		        			error_Msg = "File should contain only 56 data columns.";
				  		        			 stockEligibilityDto = null;
				  		        			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
				  		        			 return error_Msg+""+temps;
			        				  }
			        			  }
			        			  
			        			  else if(productType == 2 &&   distType == 1 ) // Swap with SF
			        			  {
			        				  
			        				  if(columnIndex < 48) //changed to 48 (0 to 47 columns index can be there)  31 are relevant columns
			    	        		  {
			        					  //change in format of business
			        					  
			        					  
			        					  if(columnIndex == 3)
			        					  {
			        						  stockEligibilityDto.setStockEligSD(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 4)
			        					  {
			        						  stockEligibilityDto.setStockEligHD(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 5)
			        					  {
			        						  stockEligibilityDto.setStockEligPVR(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 6)
			        					  {
			        						  stockEligibilityDto.setStockEligHDDVR(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 7)
			        					  {
			        						  stockEligibilityDto.setStockEligSDPLUS(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 8)
			        					  {
			        						  stockEligibilityDto.setStockEligP1(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 9)
			        					  {
			        						  stockEligibilityDto.setStockEligP2(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 10)
			        					  {
			        						  stockEligibilityDto.setStockEligP3(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 11)
			        					  {
			        						  stockEligibilityDto.setStockEligP4(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 12)
			        					  {
			        						  stockEligibilityDto.setStockEligP5(Integer.parseInt(cellValue));
			        					  }
			        					  
			        					  if(columnIndex == 13)
			        					  {
			        						  stockEligibilityDto.setSwapSDClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 14)
			        					  {
			        						  stockEligibilityDto.setSwapHDClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 15)
			        					  {
			        						  stockEligibilityDto.setSwapDVRClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 16)
			        					  {
			        						  stockEligibilityDto.setSwapHDDVRClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 17)
			        					  {
			        						  stockEligibilityDto.setSwapSDPlusClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 18 )
			        					  {
			        						  stockEligibilityDto.setDummy1(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 19)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionSDStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 20)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionHDStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 21)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionPVRStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 22)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionHDDVRStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 23)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionSDPLUSStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 24)
			        					  {
			        						  stockEligibilityDto.setDummy2(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 25)
			        					  {
			        						  stockEligibilityDto.setConsumptionStockEligibilitySD(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 26)
			        					  {
			        						  stockEligibilityDto.setConsumptionStockEligibilityHD(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 27)
			        					  {
			        						  stockEligibilityDto.setConsumptionStockEligibilityPVR(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 28)
			        					  {
			        						  stockEligibilityDto.setConsumptionStockEligibilityHDDVR(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 29)
			        					  {
			        						  stockEligibilityDto.setConsumptionStockEligibilitySDPLUS(Integer.parseInt(cellValue));
			        					  }
			        					  
			        					 
			        					  if(columnIndex == 30)
			        					  {
			        						  stockEligibilityDto.setDummy3(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 31 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy4(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 32 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy5(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 33 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy6(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 34 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy7(Double.parseDouble(cellValue));
			        					  }
			        					  //logger.info(columnIndex+"cellValue"+cellValue);
			        					  if(columnIndex == 35 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy8(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 36 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy9(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 37 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy10(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 38 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy11(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 39 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy12(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 40 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy13(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 41 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy14(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 42 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy15(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 43 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy16(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 44 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy17(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 45 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy18(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 46 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy19(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 47 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy20(Double.parseDouble(cellValue));
			        					  }
			    	        		  }
			        				  else
			        				  {
			        					  validateFlag =false;
				  		        			error_Msg = "File should contain only 48 data columns.";
				  		        			 stockEligibilityDto = null;
				  		        			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
				  		        			 return error_Msg+""+temps;
			        				  }
			        				  //logger.info("Before else "+stockEligibilityDto.getOlmId());
			        			  }
			        			  
			        			  else if(productType == 2 &&   distType == 2 ) // Swap with SSD
			        			  {
			        				  if(columnIndex < 55) //total columns is 56 (column index 0-55 and relevant columns are till index 35
			    	        		  {
			        					  if(columnIndex == 3)
			        					  {
			        						  stockEligibilityDto.setSecurityDeposit(Integer.parseInt(cellValue)); //security eligbility integer field
			        					  }
			        					  if(columnIndex == 4)//security balance double field
			        					  {
			        						  stockEligibilityDto.setSecuritybalance(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 5)
			        					  {
			        						  stockEligibilityDto.setStockEligSD(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 6)
			        					  {
			        						  stockEligibilityDto.setStockEligHD(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 7)
			        					  {
			        						  stockEligibilityDto.setStockEligPVR(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 8)
			        					  {
			        						  stockEligibilityDto.setStockEligHDDVR(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 9)
			        					  {
			        						  stockEligibilityDto.setStockEligSDPLUS(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 10)
			        					  {
			        						  stockEligibilityDto.setStockEligP1(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 11)
			        					  {
			        						  stockEligibilityDto.setStockEligP2(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 12)
			        					  {
			        						  stockEligibilityDto.setStockEligP3(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 13)
			        					  {
			        						  stockEligibilityDto.setStockEligP4(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 14)
			        					  {
			        						  stockEligibilityDto.setStockEligP5(Integer.parseInt(cellValue));
			        					  }
			        				
			        					  
			        					  
			        					  if(columnIndex == 15)
			        					  {
			        						  stockEligibilityDto.setSwapSDClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 16)
			        					  {
			        						  stockEligibilityDto.setSwapHDClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 17)
			        					  {
			        						  stockEligibilityDto.setSwapDVRClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 18)
			        					  {
			        						  stockEligibilityDto.setSwapHDDVRClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 19)
			        					  {
			        						  stockEligibilityDto.setSwapSDPlusClosingStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 20)
			        					  {
			        						  stockEligibilityDto.setDummy1(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 21)
			        					  {
			        						  stockEligibilityDto.setAmountUsedinSwapBoxIssuance(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 22)
			        					  {
			        						  stockEligibilityDto.setSDConvertFromCommercial(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 23)
			        					  {
			        						  stockEligibilityDto.setBalanceAvailableDebitCredit(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 24)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionSDStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 25)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionHDStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 26)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionPVRStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 27)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionHDDVRStock(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 28)
			        					  {
			        						  stockEligibilityDto.setMonthlyConsumptionSDPLUSStock(Integer.parseInt(cellValue));
			        					  }
			        					 
			        					  if(columnIndex == 29)
			        					  {
			        						  stockEligibilityDto.setDummy2(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 30)
			        					  {
			        						  stockEligibilityDto.setSecurityDepositAvaliableinSDBoxesRs350(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 31)
			        					  {
			        						  stockEligibilityDto.setConsumptionSWAPSD(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 32)
			        					  {
			        						  stockEligibilityDto.setConsumptionSWAPHD700(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 33)
			        					  {
			        						  stockEligibilityDto.setConsumptionSWAPPVR1050(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 34)
			        					  {
			        						  stockEligibilityDto.setConsumptionSWAPHDDVR1050(Integer.parseInt(cellValue));
			        					  }
			        					  if(columnIndex == 35)
			        					  {
			        						  stockEligibilityDto.setConsumptionSWAPSDPlus(Integer.parseInt(cellValue));
			        					  }
			        					
			        					  if(columnIndex == 36 )
			        					  {
			        						  stockEligibilityDto.setDummy3(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 37)
			        					  {
			        						  stockEligibilityDto.setDebitBalanceExcessIssuanceinSwap(Double.parseDouble(cellValue));
			        					  }
			        					
			        					  if(columnIndex == 38 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy4(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 39 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy5(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 40 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy6(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 41 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy7(Double.parseDouble(cellValue));
			        					  }
			        					  //logger.info(columnIndex+"cellValue"+cellValue);
			        					  if(columnIndex == 42 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy8(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 43 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy9(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 44 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy10(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 45 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy11(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 46 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy12(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 47 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy13(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 48 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy14(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 49 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy15(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 50 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy16(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 51 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy17(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 52 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy18(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 53 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy19(Double.parseDouble(cellValue));
			        					  }
			        					  if(columnIndex == 54 && (cellValue!=null && !cellValue.trim().equalsIgnoreCase("")))
			        					  {
			        						  stockEligibilityDto.setDummy20(Double.parseDouble(cellValue));
			        					  }
			    	        		  }
			        				  else
			        				  {
			        					  validateFlag =false;
				  		        			error_Msg = "File should contain only 55 data columns.";
				  		        			 stockEligibilityDto = null;
				  		        			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
				  		              		 return error_Msg+""+temps;
			        				  }
			        			  }
			        				  
		            		}
	        			  
	        				  /*#########################################*/
		              		
		            		//logger.info("Row Number ========================================================= "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
		            		if(cellNo==1){
		            			boolean validate = validateOlmId(cellValue);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				error_Msg = "Invalid OLM Id at row No: "+(rowNumber)+". It should be 8 character.";
		            			}else{
		            				if(validateOlmdistId(cellValue,regionId)){
		            				distOlmId=cellValue;
		            				}else
		            				{
		            					validateFlag =false;
		            					stockEligibilityDto = new ViewStockEligibilityDTO(); 
		            					error_Msg = "Invalid Distributor OLM Id for the selected zone at row No:  "+(rowNumber);
		            				}
			            		}
		            		}
		            		/* }
	        		  else
	        		  {
	        			  validateFlag =false;
	        			  error_Msg = "File should contain only two data column at row No: "+(rowNumber)+".";
	        			  logger.info("*************** error_Msg in daoimpl :"+error_Msg);
              			 return error_Msg;
	        		  }*/
	        	//**************Above code to validate the template as per product type and dist type************************	  
	        		  
	            	}
	    			
	    			
	    		
	    				if(productType == 2 &&   distType == 1 ) // Swap with SF
	        			  {
	        				  logger.info(i+" is i");
	        				  for(int j=0;j<i;j++)
		        			  {
	        					  if(temp[j]!=j && j<=30 ) //0 to 30 are mandatory columns
	        					  {
	        						logger.info(j+" is j Value "+temp[j]);
	        					  	validateFlag =false;
			            			error_Msg = "File should contain All Required values at row no :"+rowNumber;
			            			stockEligibilityDto = null;
			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
			              			return error_Msg+""+temps;
	        					  }
		        			  }
	        				  if(i<=30)
	        				  {
	        						validateFlag =false;
			            			error_Msg = "File should contain All Required values at row no :"+rowNumber;
			            			stockEligibilityDto = null;
			            			logger.info("************************** error_Msg in daoimpl :"+error_Msg); 
			            			 return error_Msg+""+temps;
	        				  }
	        			  }
	    			
	    				else if(productType == 2 &&   distType == 2 ) // Swap with SSD
	        			  {
	        				  logger.info(i+" is i");
	        				  for(int j=0;j<i;j++)
		        			  {
	        					  if(temp[j]!=j && j<=37 ) //0 to 37 are mandatory columns total 38 are relevant 
	        					  {
	        						  logger.info(j+" is j Value "+temp[j]);
	        					  	validateFlag =false;
			            			error_Msg = "File should contain All Required values at row no :"+rowNumber;
			            			stockEligibilityDto = null;
			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
			              			 return error_Msg+""+temps;
	        					  }
		        			  }
	        				  if(i<=37)
	        				  {
	        						validateFlag =false;
			            			error_Msg = "File should contain All Required values at row no :"+rowNumber;
			            			stockEligibilityDto = null;
			            			logger.info("************************** error_Msg in daoimpl :"+error_Msg); 
			            			 return error_Msg+""+temps;
	        				  }
	        			  }
	    				else if(productType == 1 &&   distType == 1 ) // comm with SF
	        			  {
	        				// logger.info(i+" is i");
	        				  for(int j=0;j<i;j++)
		        			  {
	        					  if(temp[j]!=j && j<=(com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSF-16)) //0 to 37 are mandatory columns total 38 are relevant 
	        					  {
	        						  logger.info(j+" is j Value "+temp[j]);
	        					  	validateFlag =false;
			            			error_Msg = "File should contain All Required values at row no :"+rowNumber;
			            			stockEligibilityDto = null;
			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
			              			 return error_Msg+""+temps;
	        					  }
		        			  }
	        				  if(i<=(com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSF-16))
	        				  {
	        						validateFlag =false;
			            			error_Msg = "File should contain All Required values at row no :"+rowNumber;
			            			stockEligibilityDto = null;
			            			logger.info("************************** error_Msg in daoimpl :"+error_Msg); 
			            			 return error_Msg+""+temps;
	        				  }
	        			  }
	    				else if(productType == 1 &&   distType == 2 ) // comm with SSD
	        			  {
	        				 // logger.info(i+" is i");
	        				  for(int j=0;j<i;j++)
		        			  {
	        					  if(temp[j]!=j && j<=(com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSSD-16)) //0 to 37 are mandatory columns total 38 are relevant 
	        					  {
	        						 // logger.info(j+" is j Value "+temp[j]);
	        					  	validateFlag =false;
			            			error_Msg = "File should contain All Required values at row no :"+rowNumber;
			            			stockEligibilityDto = null;
			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
			              			 return error_Msg+""+temps;
	        					  }
		        			  }
	        				  if(i<=(com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSSD-16))
	        				  {
	        						validateFlag =false;
			            			error_Msg = "File should contain All Required values at row no :"+rowNumber;
			            			stockEligibilityDto = null;
			            			logger.info("************************** error_Msg in daoimpl :"+error_Msg); 
			            			 return error_Msg+""+temps;
	        				  }
	        			  }
	    			
	        	  rowNumber++;
	            }
	            else
	            {
	            	error_Msg = "File should contain only two data column";
	            	logger.info("*************** error_Msg in daoimpl :"+error_Msg);
         			 return error_Msg+""+temps;
	            }
 	             // logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  ");
	        	  if(validateFlag){
	        		  error_Msg = "SUCCESS";
	        		 // logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	        	  }
	             
	        	  } 
	        	  
	        	  
	        	  else{
	        		 // logger.info("***********************for headers (the file is related or not )validation**************************************");
	        		  logger.info("********** checking header validation *****************");
	  	        	//For Header Checking  
	  	        	int columnIndex = 0;
	  	              int cellNo = 0;
	  	              if(cells != null)
	  	              {
	  	            	  while (cells.hasNext()) {
	  	            		if(productType == 1 &&   distType == 1 ) // Commercial with SF
		        			  {
	  	            		  if(cellNo < com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSF ) // validate the fields value(header only) in this block
	  		        		  {
	  		        			  cellNo++;
	  		        			  HSSFCell cell = (HSSFCell) cells.next();
	  		        			  columnIndex = cell.getColumnIndex();
	  		        			 // logger.info("productType :"+productType +" distType :"+distType);
	  		        			  //logger.info("columnIndex**************"+columnIndex+" POP");
	  		        				  if(columnIndex < com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSF)
	  		    	        		  {
	  		        					  // do column header validations here
	  		        					String cellValue = null;
		  			            		switch(cell.getCellType()) {
		  				            		case HSSFCell.CELL_TYPE_NUMERIC:
		  				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
		  				            		break;
		  				            		case HSSFCell.CELL_TYPE_STRING:
		  				            			cellValue = cell.getStringCellValue();
		  					            	break;
		  			            		}
		  			            		//logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
		  			            		if(columnIndex<com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSF && (cellValue ==  null || "".equalsIgnoreCase(cellValue.trim())))
		  			            		{
		  			            			validateFlag =false;
		  			            			error_Msg = "File should contain All Required column headers";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			               		}
		  			            		else if(columnIndex == 0 && !cellValue.trim().equalsIgnoreCase("circle"))
		  			            		{
		  			            			error_Msg = "Not a valid file format";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 1 && !cellValue.trim().equalsIgnoreCase("OLMID"))
		  			            		{
		  			            			error_Msg = "Not a valid file format";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 2 && !cellValue.trim().equalsIgnoreCase("Account Name"))
		  			            		{
		  			            			error_Msg = "Not a valid file format";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            	
		  			            	//New changes
		  			            		else if(columnIndex == 3 && !cellValue.trim().equalsIgnoreCase("Security Deposit Eligibility  ( Sample For SD BOX )"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 4 && !cellValue.trim().equalsIgnoreCase("Security Eligibility Balance"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 5 && !cellValue.trim().equalsIgnoreCase("SF Consumption SD Box Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 6 && !cellValue.trim().equalsIgnoreCase("SF Consumption HD Box Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 7 && !cellValue.trim().equalsIgnoreCase("SF Consumption HD DVR Box Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}else if(columnIndex == 8 && !cellValue.trim().equalsIgnoreCase("SF Consumption CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 9 && !cellValue.trim().equalsIgnoreCase("SF Consumption New Product2 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 10 && !cellValue.trim().equalsIgnoreCase("SF Consumption New Product3 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 11 && !cellValue.trim().equalsIgnoreCase("SF Consumption New Product 4 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 12 && !cellValue.trim().equalsIgnoreCase("SF Consumption New Product 5 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		//end
		  			            		
		  			            		else if(columnIndex == 13 && !cellValue.trim().equalsIgnoreCase("Activation SD Current Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 14 && !cellValue.trim().equalsIgnoreCase("Activation & Upgrade HD Curent Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 15 && !cellValue.trim().equalsIgnoreCase("Activation HD  DVR  Current Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 16 && !cellValue.trim().equalsIgnoreCase("Activation CAM  Current Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 17 && !cellValue.trim().equalsIgnoreCase("Closing Stock SD  & Plus"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 18 && !cellValue.trim().equalsIgnoreCase("Closing Stock HD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 19 && !cellValue.trim().equalsIgnoreCase("Closing Stock HD DVR"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 20 && !cellValue.trim().equalsIgnoreCase("Closing Stock CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 21 && !cellValue.trim().equalsIgnoreCase("Nos of day Stock SD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 22 && !cellValue.trim().equalsIgnoreCase("Nos of day stock HD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 23 && !cellValue.trim().equalsIgnoreCase("Nos of day stock CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 24 && !cellValue.trim().equalsIgnoreCase("Max Order Eligibility SD  ( On  21 days Stock )  Based on Average 3 Month or /  current month Which ever is less Activation  ( Normal Box )"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 25 && !cellValue.trim().equalsIgnoreCase("Max Order Eligibility HD  ( On  30 days Stock )  Based on Average 3 Month or / current month Which ever is less Activation  (  HD  Boxes )"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 26 && !cellValue.trim().equalsIgnoreCase("Max Order Eligibility CAM  ( On  30 days Stock )  Based on Average 3 Month or / current month Which ever is less Activation  (  CAm Boxes )"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 27 && !cellValue.trim().equalsIgnoreCase("Average current Month Activation  SD"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 28 && !cellValue.trim().equalsIgnoreCase("Average current Month Activation HD"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 29 && !cellValue.trim().equalsIgnoreCase("Average CAM  Current Month Activations"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 30 && !cellValue.trim().equalsIgnoreCase("Average SD Last Month Activations"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 31 && !cellValue.trim().equalsIgnoreCase("Average HD Last Month Activations"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 32 && !cellValue.trim().equalsIgnoreCase("Activation SD Last 2nd Month"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 33 && !cellValue.trim().equalsIgnoreCase("Activation HD Last 2nd Month"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 34 && !cellValue.trim().equalsIgnoreCase("Average Activation SD ( 3 Month )"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 35 && !cellValue.trim().equalsIgnoreCase("Average Activation HD ( 3 Month )"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 36 && !cellValue.trim().equalsIgnoreCase("Current month or Average which is less  SD"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 37 && !cellValue.trim().equalsIgnoreCase("Current month or Average which is less  HD"))
		  			            		{
		  			            			error_Msg =  "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            
		  			            		else if(columnIndex == 38 && !cellValue.trim().equalsIgnoreCase("Column New 6"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 39 && !cellValue.trim().equalsIgnoreCase("Column New 7"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 40 && !cellValue.trim().equalsIgnoreCase("Column New 8"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 41 && !cellValue.trim().equalsIgnoreCase("Column New 9"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 42 && !cellValue.trim().equalsIgnoreCase("Column New 10"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 43 && !cellValue.trim().equalsIgnoreCase("Column New 11"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 44 && !cellValue.trim().equalsIgnoreCase("Column New 12"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 45 && !cellValue.trim().equalsIgnoreCase("Column New 13"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 46 && !cellValue.trim().equalsIgnoreCase("Column New 14"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 47 && !cellValue.trim().equalsIgnoreCase("Column New 15"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 48 && !cellValue.trim().equalsIgnoreCase("Column New 16"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 49 && !cellValue.trim().equalsIgnoreCase("Column New 17"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 50 && !cellValue.trim().equalsIgnoreCase("Column New 18"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 51 && !cellValue.trim().equalsIgnoreCase("Column New 19"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 52 && !cellValue.trim().equalsIgnoreCase("Column New 20"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		
		  			            		}
	  		        				  else
	  		        				  {
	  		        					  validateFlag =false;
	  		    	        			//  error_Msg = "File should contain only 23 data headers.";
	  		    	        			error_Msg = "Not a valid file format.";
	  		    	        			  logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	  		                  			  return error_Msg+""+temps;
	  		        				  }
	  		        		  }else{ 
	  		        			  validateFlag =false;
	  		        			//error_Msg = "File should contain only 23 data Headers";
	  		        			error_Msg = "Not a valid file format.";
	  		        			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	  		              		 return error_Msg+""+temps;
	  		        		  }
	  	            	  }
	  	            		/********************valideting file (header) for commercial SSD***********************/
	  	            		else if(productType == 1 &&   distType == 2 ) // Commercial with SSD
		        			  {
	  	            		  if(cellNo < com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSSD ) // validate the fields value(header only) in this block
	  		        		  {
	  		        			  cellNo++;
	  		        			  HSSFCell cell = (HSSFCell) cells.next();
	  		        			  columnIndex = cell.getColumnIndex();
	  		        			  //logger.info("productType :"+productType +" distType :"+distType);
	  		        			  //logger.info("columnIndex**************"+columnIndex+" POP");
	  		        				  if(columnIndex < com.ibm.virtualization.recharge.common.Constants.total_columns_COMMSSD)
	  		    	        		  {
	  		        					  // do column header validations here
	  		        					String cellValue = null;
		  			            		switch(cell.getCellType()) {
		  				            		case HSSFCell.CELL_TYPE_NUMERIC:
		  				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
		  				            		break;
		  				            		case HSSFCell.CELL_TYPE_STRING:
		  				            			cellValue = cell.getStringCellValue();
		  					            	break;
		  			            		}
		  			            	//	logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo );
		  			            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
		  			            		{
		  			            			validateFlag =false;
		  			            			error_Msg = "File should contain All Required column headers";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			               		}
		  			            		else if(columnIndex == 0 && !cellValue.trim().equalsIgnoreCase("circle"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 1 && !cellValue.trim().equalsIgnoreCase("OLM ID"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 2 && !cellValue.trim().equalsIgnoreCase("Account Name"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            	//New changes
		  			            		else if(columnIndex == 3 && !cellValue.trim().equalsIgnoreCase("Security Deposit Eligibility  ( Sample For SD BOX )"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 4 && !cellValue.trim().equalsIgnoreCase("Security Eligibility Balance"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 5 && !cellValue.trim().equalsIgnoreCase("SSD Consumption SD Box Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 6 && !cellValue.trim().equalsIgnoreCase("SSD Consumption HD Box Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 7 && !cellValue.trim().equalsIgnoreCase("SSD Consumption HD DVR Box Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}else if(columnIndex == 8 && !cellValue.trim().equalsIgnoreCase("SSD Consumption CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 9 && !cellValue.trim().equalsIgnoreCase("SSD Consumption New Product2 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 10 && !cellValue.trim().equalsIgnoreCase("SSD Consumption New Product3 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 11 && !cellValue.trim().equalsIgnoreCase("Consumption New Product 4 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 12 && !cellValue.trim().equalsIgnoreCase("Consumption New Product 5 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		//end
		  			            		
		  			            		
		  			            		else if(columnIndex == 13 && !cellValue.trim().equalsIgnoreCase("Activation SD Current Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format  - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 14 && !cellValue.trim().equalsIgnoreCase("Activation & Upgrade HD Current Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 15 && !cellValue.trim().equalsIgnoreCase("Activation HD  DVR Current Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 16 && !cellValue.trim().equalsIgnoreCase("Activation CAM Current Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 17 && !cellValue.trim().equalsIgnoreCase("Closing Stock Normal SD BOX"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 18 && !cellValue.trim().equalsIgnoreCase("Closing Stock HD Box"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 19 && !cellValue.trim().equalsIgnoreCase("Closing Stock HD  DVR"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 20 && !cellValue.trim().equalsIgnoreCase("Closing Stock CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 21 && !cellValue.trim().equalsIgnoreCase("SD Available"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 22 && !cellValue.trim().equalsIgnoreCase("Balance Available"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 23 && !cellValue.trim().equalsIgnoreCase("SD Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 24 && !cellValue.trim().equalsIgnoreCase("Nos of Day Stock  SD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 25 && !cellValue.trim().equalsIgnoreCase("Nos of Day Stock  HD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 26 && !cellValue.trim().equalsIgnoreCase("Nos of Day Stock  CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 27 && !cellValue.trim().equalsIgnoreCase("Max Order Eligibility SD  ( On  45 days Stock )  Based on Average 3 Month Or /  Current Month Which ever is High Activation_Normal SD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 28 && !cellValue.trim().equalsIgnoreCase("Max Order Eligibility HD  ( On  90 days Stock )  Based on Average 3 Month Or / Current Month Which ever is High Activation_ HD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 29 && !cellValue.trim().equalsIgnoreCase("Max Order Eligibility CAM  ( On  90 days Stock )  Based on Average 3 Month Or / Current Month Which ever is High Activation_ CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 30 && !cellValue.trim().equalsIgnoreCase("Average SD Current Month Activations"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 31 && !cellValue.trim().equalsIgnoreCase("Average HD  Current Month Activations"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 32 && !cellValue.trim().equalsIgnoreCase("Average CAM Current Month Activations"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 33 && !cellValue.trim().equalsIgnoreCase("Average SD Last Month Activations"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 34 && !cellValue.trim().equalsIgnoreCase("Average HD Last Month Activations"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 35 && !cellValue.trim().equalsIgnoreCase("Activation SD Last 2nd Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 36 && !cellValue.trim().equalsIgnoreCase("Activation HD Last 2nd Month"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 37 && !cellValue.trim().equalsIgnoreCase("Average Activation SD ( 3 Month )"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 38 && !cellValue.trim().equalsIgnoreCase("Average Activation HD ( 3 Month )"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 39 && !cellValue.trim().equalsIgnoreCase("Current month or Average which is less  Normal SD Activation"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 40 && !cellValue.trim().equalsIgnoreCase("Current month or Average which is less  HD Activation"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		
		  			            	//Added for dummy columns
		  			            		
		  			            		else if(columnIndex == 41 && !cellValue.trim().equalsIgnoreCase("Column New 6"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 42 && !cellValue.trim().equalsIgnoreCase("Column New 7"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 43 && !cellValue.trim().equalsIgnoreCase("Column New 8"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 44 && !cellValue.trim().equalsIgnoreCase("Column New 9"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 45 && !cellValue.trim().equalsIgnoreCase("Column New 10"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 46 && !cellValue.trim().equalsIgnoreCase("Column New 11"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 47 && !cellValue.trim().equalsIgnoreCase("Column New 12"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 48 && !cellValue.trim().equalsIgnoreCase("Column New 13"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 49 && !cellValue.trim().equalsIgnoreCase("Column New 14"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 50 && !cellValue.trim().equalsIgnoreCase("Column New 15"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 51 && !cellValue.trim().equalsIgnoreCase("Column New 16"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 52 && !cellValue.trim().equalsIgnoreCase("Column New 17"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 53 && !cellValue.trim().equalsIgnoreCase("Column New 18"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 54 && !cellValue.trim().equalsIgnoreCase("Column New 19"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 55 && !cellValue.trim().equalsIgnoreCase("Column New 20"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		}
	  		        				  else
	  		        				  {
	  		        					  validateFlag =false;
	  		    	        			//  error_Msg = "File should contain only 26 data headers.";
	  		    	        			error_Msg = "Not a valid file format.";
	  		    	        			  logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	  		                  			  return error_Msg+""+temps;
	  		        				  }
	  		        		  }else{ 
	  		        			  validateFlag =false;
	  		        			//error_Msg = "File should contain only 26 data Headers";
	  		        			error_Msg = "Not a valid file format.";
	  		        			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	  		              		 return error_Msg+""+temps;
	  		        		  }
	  	            	  }
	  	            		
	  	            		/********************valideting file (header) for Swap SF***********************/
	  	            		else if(productType == 2 &&   distType == 1 ) // SWAP with SF
		        			  {
	  	            		//  logger.info("cellNo ::::::::::::::::::"+cellNo);
	  	            		  if(cellNo < 48 ) // validate the fields value(header only) in this block 
	  		        		  {
	  		        			  cellNo++;
	  		        			  HSSFCell cell = (HSSFCell) cells.next();
	  		        			  columnIndex = cell.getColumnIndex();
	  		        			 // logger.info("productType :"+productType +" distType :"+distType);
	  		        			 // logger.info("columnIndex**************"+columnIndex+" POP");
	  		        				  if(columnIndex < 48)
	  		    	        		  {
	  		        					  // do column header validations here
	  		        					String cellValue = null;
		  			            		switch(cell.getCellType()) {
		  				            		case HSSFCell.CELL_TYPE_NUMERIC:
		  				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
		  				            		break;
		  				            		case HSSFCell.CELL_TYPE_STRING:
		  				            			cellValue = cell.getStringCellValue();
		  					            	break;
		  			            		}
		  			            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo+"cellValue " +cellValue);
		  			            		if(columnIndex<=27 && (cellValue ==  null || "".equalsIgnoreCase(cellValue.trim())))
		  			            		{
		  			            			validateFlag =false;
		  			            			error_Msg = "File should contain All Required column headers";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			               		}
		  			            	
		  			            		else if(columnIndex == 0 && !cellValue.trim().equalsIgnoreCase("circle"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 1 && !cellValue.trim().equalsIgnoreCase("OLM ID"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 2 && !cellValue.trim().equalsIgnoreCase("Account Name"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		//change in formats of business
		  			            	
		  			            		else if(columnIndex == 3 && !cellValue.trim().equalsIgnoreCase("SF Consumption SWAP SD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 4 && !cellValue.trim().equalsIgnoreCase("SF Consumption SWAP HD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 5 && !cellValue.trim().equalsIgnoreCase("SF Consumption SWAP PVR"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 6 && !cellValue.trim().equalsIgnoreCase("SF Consumption SWAP HD DVR"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 7 && !cellValue.trim().equalsIgnoreCase("SF Consumption SWAP  SD Plus"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 8 && !cellValue.trim().equalsIgnoreCase("SF Consumption SWAP  CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 9 && !cellValue.trim().equalsIgnoreCase("Consumption New Product2 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 10 && !cellValue.trim().equalsIgnoreCase("Consumption New Product3 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 11 && !cellValue.trim().equalsIgnoreCase("Consumption New Product 4 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 12 && !cellValue.trim().equalsIgnoreCase("Consumption New Product 5 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		//
		  			            		else if(columnIndex == 13 && !cellValue.trim().equalsIgnoreCase("Swap SD Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 14 && !cellValue.trim().equalsIgnoreCase("Swap HD Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 15 && !cellValue.trim().equalsIgnoreCase("Swap PVR  Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 16 && !cellValue.trim().equalsIgnoreCase("Swap HD DVR   Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 17 && !cellValue.trim().equalsIgnoreCase("Swap SD Plus Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 18 && !cellValue.trim().equalsIgnoreCase("Swap CAm Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 19 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  SD  Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 20 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  HD  Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 21 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  PVR   Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 22 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  HD DVR   Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect" ;
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 23 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  SD PLUS  Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 24 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  CAM Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 25 && !cellValue.trim().equalsIgnoreCase("Consumption  Stock Eligibility SD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 26 && !cellValue.trim().equalsIgnoreCase("Consumption  Stock Eligibility HD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 27 && !cellValue.trim().equalsIgnoreCase("Consumption  Stock Eligibility  PVR"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 28 && !cellValue.trim().equalsIgnoreCase("Consumption  Stock Eligibility HD DVR"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 29 && !cellValue.trim().equalsIgnoreCase("Consumption  Stock Eligibility SD  PLUS"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		//Added for dummy columns
		  			            	
		  			            		else if(columnIndex == 30 && !cellValue.trim().equalsIgnoreCase("Consumption  Stock Eligibility CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 31 && !cellValue.trim().equalsIgnoreCase("Column New 4"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 32 && !cellValue.trim().equalsIgnoreCase("Column New 5"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 33 && !cellValue.trim().equalsIgnoreCase("Column New 6"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 34 && !cellValue.trim().equalsIgnoreCase("Column New 7"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 35 && !cellValue.trim().equalsIgnoreCase("Column New 8"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 36 && !cellValue.trim().equalsIgnoreCase("Column New 9"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 37 && !cellValue.trim().equalsIgnoreCase("Column New 10"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 38 && !cellValue.trim().equalsIgnoreCase("Column New 11"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 39 && !cellValue.trim().equalsIgnoreCase("Column New 12"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 40 && !cellValue.trim().equalsIgnoreCase("Column New 13"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 41 && !cellValue.trim().equalsIgnoreCase("Column New 14"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 42 && !cellValue.trim().equalsIgnoreCase("Column New 15"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 43 && !cellValue.trim().equalsIgnoreCase("Column New 16"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 44 && !cellValue.trim().equalsIgnoreCase("Column New 17"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 45 && !cellValue.trim().equalsIgnoreCase("Column New 18"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 46 && !cellValue.trim().equalsIgnoreCase("Column New 19"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 47 && !cellValue.trim().equalsIgnoreCase("Column New 20"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		//logger.info("*************** error_Msg in daoimpl check Neetika:"+error_Msg+"columnIndex"+columnIndex);
		  			            			}
	  		        				  else
	  		        				  {
	  		        					  validateFlag =false;
	  		    	        			//  error_Msg = "File should contain only 26 data headers.";
	  		    	        			error_Msg = "Not a valid file format.";
	  		    	        			  logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	  		                  			  return error_Msg+""+temps;
	  		        				  }
	  		        		  }else{ 
	  		        			  validateFlag =false;
	  		        			//error_Msg = "File should contain only 26 data Headers";
	  		        			error_Msg = "Not a valid file format.";
	  		        			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	  		              		 return error_Msg+""+temps;
	  		        		  }
	  	            		  
	  	            		 // logger.info("*************** error_Msg in daoimpl NEetika:"+error_Msg);
	  	            	  }
	  	            		
	  	            		/********************valideting file (header) for Swap SSD***********************/
	  	            		else if(productType == 2 &&   distType == 2 ) // SWAP with SSD
		        			  {
	  	            		  if(cellNo < 55 ) // validate the fields value(header only) in this block//chnaged to 34 by neetika
	  		        		  {
	  		        			  cellNo++;
	  		        			  HSSFCell cell = (HSSFCell) cells.next();
	  		        			  columnIndex = cell.getColumnIndex();
	  		        			  //logger.info("productType :"+productType +" distType :"+distType);
	  		        			  //logger.info("columnIndex**************"+columnIndex+" POP");
	  		        				  if(columnIndex < 55)
	  		    	        		  {
	  		        					  // do column header validations here
	  		        					String cellValue = null;
		  			            		switch(cell.getCellType()) {
		  				            		case HSSFCell.CELL_TYPE_NUMERIC:
		  				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
		  				            		break;
		  				            		case HSSFCell.CELL_TYPE_STRING:
		  				            			cellValue = cell.getStringCellValue();
		  					            	break;
		  			            		}
		  			            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim() );
		  			            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
		  			            		{
		  			            			validateFlag =false;
		  			            			error_Msg = "File should contain All Required column headers";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			               		}
		  			            		else if(columnIndex == 0 && !cellValue.trim().equalsIgnoreCase("circle"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 1 && !cellValue.trim().equalsIgnoreCase("OLM ID"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 2 && !cellValue.trim().equalsIgnoreCase("Account Name"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		//New changes
		  			            		else if(columnIndex == 3 && !cellValue.trim().equalsIgnoreCase("Security Deposit Avaliable  Qty ( in SD  Boxes ) @ Rs 350/-"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 4 && !cellValue.trim().equalsIgnoreCase("Security Eligibility Balance"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 5 && !cellValue.trim().equalsIgnoreCase("SSD Consumption SWAP SD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 6 && !cellValue.trim().equalsIgnoreCase("SSD Consumption SWAP HD @ 700"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 7 && !cellValue.trim().equalsIgnoreCase("SSD Consumption SWAP PVR @ 1050"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}else if(columnIndex == 8 && !cellValue.trim().equalsIgnoreCase("SSD Consumption SWAP HD DVR @ 1050"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 9 && !cellValue.trim().equalsIgnoreCase("SSD Consumption SWAP  SD Plus"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 10 && !cellValue.trim().equalsIgnoreCase("SSD Consumption SWAP  CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 11 && !cellValue.trim().equalsIgnoreCase("Consumption New Product2 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 12 && !cellValue.trim().equalsIgnoreCase("Consumption New Product3 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 13 && !cellValue.trim().equalsIgnoreCase("Consumption New Product 4 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 14 && !cellValue.trim().equalsIgnoreCase("Consumption New Product 5 Eligibility"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		//end
		  			            		else if(columnIndex == 15 && !cellValue.trim().equalsIgnoreCase("Swap SD Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 16 && !cellValue.trim().equalsIgnoreCase("Swap HD Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 17 && !cellValue.trim().equalsIgnoreCase("Swap DVR  Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 18 && !cellValue.trim().equalsIgnoreCase("Swap HD DVR   Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 19 && !cellValue.trim().equalsIgnoreCase("Swap SD Plus Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            	
		  			            		else if(columnIndex == 20 && !cellValue.trim().equalsIgnoreCase("Swap CAM Closing Stock"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 21 && !cellValue.trim().equalsIgnoreCase("Amount Used  in  Swap Box Issuance"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 22 && !cellValue.trim().equalsIgnoreCase("SD  ( Convert From Commercial )"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 23 && !cellValue.trim().equalsIgnoreCase("Balance Available Debit  / Credit"))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 24 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  SD  Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 25 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  HD  Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 26 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  PVR   Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 27 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  HD DVR   Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 28 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  SD PLUS  Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 29 && !cellValue.trim().equalsIgnoreCase("Monthly Consumption  CAM  Stock."))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		else if(columnIndex == 30 && !cellValue.trim().equalsIgnoreCase("Security Deposit Avaliable  ( in SD  Boxes ) @ Rs 350/-"))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 31 && !cellValue.trim().equalsIgnoreCase("Consumption SWAP SD"))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 32 && !cellValue.trim().equalsIgnoreCase("Consumption SWAP HD @ 700"))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 33 && !cellValue.trim().equalsIgnoreCase("Consumption SWAP PVR @ 1050"))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 34 && !cellValue.trim().equalsIgnoreCase("Consumption SWAP HD DVR @ 1050"))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 35 && !cellValue.trim().equalsIgnoreCase("Consumption SWAP  SD Plus"))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 36 && !cellValue.trim().equalsIgnoreCase("Consumption SWAP  CAM"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 37 && !cellValue.trim().equalsIgnoreCase("Debit Balance ( Excess Issuance in Swap )"))
		  			            		{
		  			            			error_Msg = "Not a valid file format- Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		//dummy
		  			            	//Added for dummy columns
		  			            		
		  			            		
		  			            		else if(columnIndex == 38 && !cellValue.trim().equalsIgnoreCase("Column New 4"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 39 && !cellValue.trim().equalsIgnoreCase("Column New 5"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 40 && !cellValue.trim().equalsIgnoreCase("Column New 6"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 41 && !cellValue.trim().equalsIgnoreCase("Column New 7"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 42 && !cellValue.trim().equalsIgnoreCase("Column New 8"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 43 && !cellValue.trim().equalsIgnoreCase("Column New 9"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 44 && !cellValue.trim().equalsIgnoreCase("Column New 10"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 45 && !cellValue.trim().equalsIgnoreCase("Column New 11"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 46 && !cellValue.trim().equalsIgnoreCase("Column New 12"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 47 && !cellValue.trim().equalsIgnoreCase("Column New 13"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 48 && !cellValue.trim().equalsIgnoreCase("Column New 14"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 49 && !cellValue.trim().equalsIgnoreCase("Column New 15"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 50 && !cellValue.trim().equalsIgnoreCase("Column New 16"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 51 && !cellValue.trim().equalsIgnoreCase("Column New 17"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 52 && !cellValue.trim().equalsIgnoreCase("Column New 18"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 53 && !cellValue.trim().equalsIgnoreCase("Column New 19"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		else if(columnIndex == 54 && !cellValue.trim().equalsIgnoreCase("Column New 20"))
		  			            		{
		  			            			error_Msg = "Not a valid file format - Header Incorrect";
		  			            			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
		  			              			 return error_Msg+""+temps;
		  			            		}
		  			            		
		  			            		
		  			            			}
	  		        				  else
	  		        				  {
	  		        					  validateFlag =false;
	  		    	        			//  error_Msg = "File should contain only 26 data headers.";
	  		    	        			error_Msg = "Not a valid file format.";
	  		    	        			  logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	  		                  			  return error_Msg+""+temps;
	  		        				  }
	  		        		  }else{ 
	  		        			  validateFlag =false;
	  		        			//error_Msg = "File should contain only 26 data Headers";
	  		        			error_Msg = "Not a valid file format.";
	  		        			logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	  		              		 return error_Msg+""+temps;
	  		        		  }
	  	            	  }
	  	            	  }
	  	              } 
	  	        	rowNumber ++;
	  	        //	logger.info("rowNumber"+rowNumber);
	  	         }  
	        	  k++;
	        	 // logger.info("Before adding"+stockEligibilityDto);
	        	 // logger.info("Before adding"+stockEligibilityDto.getAccountName());
	        	  if(stockEligibilityDto!=null)//Added by neetika
	        	  {
	        	  logger.info("Before adding=="+stockEligibilityDto.getOlmId());
	        	  eligibilityStocklist.add(stockEligibilityDto);
	        	  }
	        }
	         logger.info("K == "+k);
	        if(k<3){ //changed to <3
	        	 error_Msg = "Blank File can not be uploaded!!";
	        	 logger.info("*************** error_Msg in daoimpl :"+error_Msg);
	        }
	        
	        /*************inserting in database*********************/
	        if(eligibilityStocklist !=null && eligibilityStocklist.size() >= 1)
	        {
	        	error_Msg = uploadDistStockEligibility(eligibilityStocklist, productType, distType,dthadminid);
	        }
	        //logger.info("error_Msg="+error_Msg);
	        
	    }
		catch (IOException ioe) {
        	logger.info(ioe);
        	ioe.printStackTrace();
        	error_Msg = "Please Upload a Valid Excel File.";
          	return error_Msg+""+temps;
        }
		catch (NumberFormatException e) {
        	logger.info(e);
        	e.printStackTrace();
        	error_Msg = "Please Check the Values in the file at row number "+rowNumber;
        	 return error_Msg+""+temps;
        //	throw new DAOException("Please Upload a valid Excel File Or Check the values in the file" );
        }
		catch (Exception e) {
        	logger.info(e);
        	e.printStackTrace();
        	error_Msg = "Please Upload a valid Excel File.";
        	return error_Msg+""+temps;
        }
		 return error_Msg+""+temps;
	}
	/*
	 * productType :1 (SF) distType : 1 (Commercial)
	 * productType :1 (SF) distType : 2 (SWAP)
	 * productType :2 (SSD) distType : 1 (Commercial)
	 * productType :2 (SSD) distType : 2 (SWAP)
	 * 
	 * */
	public String uploadDistStockEligibility(List<ViewStockEligibilityDTO> stockList,int productType,int distType,long dthadminid)
	{
		Connection connection =null;
		PreparedStatement pstmtcssd =  null;
		PreparedStatement pstmtcsf =  null;
		PreparedStatement pstmtssd =  null;
		PreparedStatement pstmtssf =  null;
		PreparedStatement pstmtdelssf=null;
		PreparedStatement pstmtdelcsf=null;
		PreparedStatement pstmtdelssd=null;
		PreparedStatement pstmtdelcsd=null;
		String err_Msg= "SUCCESSFULLY UPLOADED";
		List<ViewStockEligibilityDTO> stockListDetails = stockList;
		ViewStockEligibilityDTO stockDto = null;
		String circleNameAccountNameAndZone = "";
		String circleName = "";
		String accountName = "";
		String zoneName = "";
		String olmId = null;
		int counter = 1;
		int insertedRecords[];
		int deletedRecords[];
		try
		{
			//logger.info(" uploadDistStockEligibility:");
			connection =  DBConnectionManager.getDBConnection(); // db2
			connection.setAutoCommit(false);
			//Prepared stmt moved outside by Neetika
			Iterator<ViewStockEligibilityDTO> itr= stockListDetails.iterator();
			pstmtssf = connection.prepareStatement(INSERT_FOR_SWAP_SF);
			pstmtdelssf = connection.prepareStatement(DELETE_FOR_SWAP_SF);
			pstmtcsf = connection.prepareStatement(INSERT_FOR_COMMERCIAL_SF);
			pstmtdelcsf = connection.prepareStatement(DELETE_FOR_COMM_SF);
			
			pstmtssd = connection.prepareStatement(INSERT_FOR_SWAP_SSD);
			pstmtdelssd = connection.prepareStatement(DELETE_FOR_SWAP_SSD);
			pstmtcssd = connection.prepareStatement(INSERT_FOR_COMMERCIAL_SSD);
			pstmtdelcsd = connection.prepareStatement(DELETE_FOR_COMM_SSD);
			if(productType == 1 &&   distType == 1 )
			{
				while(itr.hasNext())
				{
					
					stockDto =	itr.next();
					if(counter == 1) //its OLM id in field
					{
						olmId = stockDto.getOlmId();
						logger.info(" OLM ID :"+olmId);
						circleNameAccountNameAndZone = getCircleNameAndAccountName(olmId, connection);
						if(circleNameAccountNameAndZone != null)
						{
						circleName = circleNameAccountNameAndZone.split("#")[0]; 
						accountName =  circleNameAccountNameAndZone.split("#")[1];
						zoneName = circleNameAccountNameAndZone.split("#")[2];
						}
						else
						{
							throw new Exception("Circle name and Account name not found for OLM Id :"+olmId);
						}
						counter=2; //Neetika
					}
					//else
					//{
					if(counter == 2) //its OLM id in field
					{
						pstmtcsf.setString(1,zoneName);
						pstmtcsf.setString(2,circleName);
						pstmtcsf.setString(3,stockDto.getOlmId());
						pstmtcsf.setString(4,accountName);
						pstmtcsf.setInt(5,stockDto.getActivationSDCurrentMonth());
						pstmtcsf.setInt(6,stockDto.getActivationAndUpgradeHDCurentMonth());
						pstmtcsf.setInt(7,stockDto.getActivationHDDVRcurrentMonth());
						pstmtcsf.setInt(8,stockDto.getClosingStockSDandPlus());
						pstmtcsf.setInt(9,stockDto.getClosingStockHD());
						//pstmtcsf.setInt(8,stockDto.getClosingStockNormalSDBOX());
						//pstmtcsf.setInt(9,stockDto.getClosingStockHDBox());
						pstmtcsf.setInt(10,stockDto.getClosingStockHDDVR());
						pstmtcsf.setInt(11,stockDto.getNosofdayStockSD());
						pstmtcsf.setInt(12,stockDto.getNosofdaystockHD());
						pstmtcsf.setInt(13,stockDto.getMaxOrderEligibilitySD());
						pstmtcsf.setInt(14,stockDto.getMaxOrderEligibilityHD());
						pstmtcsf.setInt(15,stockDto.getAveragecurrentMonthActivationSD());
						pstmtcsf.setInt(16,stockDto.getAveragecurrentMonthActivationHD());
						pstmtcsf.setInt(17,stockDto.getActivationSDcurrentMonthOne());
						pstmtcsf.setInt(18,stockDto.getActivationAndUpgradeHDcurrentMonthOne());
						pstmtcsf.setInt(19,stockDto.getActivationSDcurrentMonthTwo());
						pstmtcsf.setInt(20,stockDto.getActivationAndUpgradeHDcurrentMonthTwo());
						pstmtcsf.setInt(21,stockDto.getAverageActivationNormal());
						pstmtcsf.setInt(22,stockDto.getAverageActivationHD());
						pstmtcsf.setInt(23,stockDto.getCurrentmonthorAveragewhichislessSD());
						pstmtcsf.setInt(24,stockDto.getCurrentmonthorAveragewhichislessHD());
						pstmtcsf.setString(25,dthadminid+"");
						
						
						pstmtcsf.setInt(26,stockDto.getSecurityDeposit());//in term of elgibility so integer value in security eligibility of DB
						pstmtcsf.setDouble(27,stockDto.getSecuritybalance());
						pstmtcsf.setInt(28,stockDto.getStockEligSD());
						pstmtcsf.setInt(29,stockDto.getStockEligHD());
						
						pstmtcsf.setInt(30,stockDto.getStockEligHDDVR());
					
						pstmtcsf.setInt(31,stockDto.getStockEligP1());
						pstmtcsf.setInt(32,stockDto.getStockEligP2());
						pstmtcsf.setInt(33,stockDto.getStockEligP3());
						pstmtcsf.setInt(34,stockDto.getStockEligP4());
						pstmtcsf.setInt(35,stockDto.getStockEligP5());
						//New 
						
						pstmtcsf.setDouble(36,stockDto.getDummy1());
						pstmtcsf.setDouble(37,stockDto.getDummy2());
						pstmtcsf.setDouble(38,stockDto.getDummy3());
						pstmtcsf.setDouble(39,stockDto.getDummy4());
						pstmtcsf.setDouble(40,stockDto.getDummy5());
						pstmtcsf.setDouble(41,stockDto.getDummy6());
						pstmtcsf.setDouble(42,stockDto.getDummy7());
						pstmtcsf.setDouble(43,stockDto.getDummy8());
						pstmtcsf.setDouble(44,stockDto.getDummy9());
						pstmtcsf.setDouble(45,stockDto.getDummy10());
						pstmtcsf.setDouble(46,stockDto.getDummy11());
						pstmtcsf.setDouble(47,stockDto.getDummy12());
						pstmtcsf.setDouble(48,stockDto.getDummy13());
						pstmtcsf.setDouble(49,stockDto.getDummy14());
						pstmtcsf.setDouble(50,stockDto.getDummy15());
						pstmtcsf.setDouble(51,stockDto.getDummy16());
						pstmtcsf.setDouble(52,stockDto.getDummy17());
						pstmtcsf.setDouble(53,stockDto.getDummy18());
						pstmtcsf.setDouble(54,stockDto.getDummy19());
						pstmtcsf.setDouble(55,stockDto.getDummy20());
						
						pstmtcsf.addBatch();

						
						pstmtdelcsf.setString(1,stockDto.getOlmId());
						pstmtdelcsf.setString(2,stockDto.getOlmId());
								
					
						pstmtdelcsf.addBatch();
					}
					counter=1;
				}
				insertedRecords = pstmtcsf.executeBatch();
				deletedRecords=pstmtdelcsf.executeBatch();
				logger.info("Total no of records inserted succusefully are :"+insertedRecords.length +" deletedRecords "+deletedRecords.length);
				
				
			}
			//Neetika for BFR 1 Upload elgibility
			else if(productType == 1 &&   distType == 2 )
			{

				while(itr.hasNext())
				{
					
					stockDto =	itr.next();
					if(counter == 1) //its OLM id in field
					{
						olmId = stockDto.getOlmId();
						//logger.info(" OLM ID :"+olmId);
						circleNameAccountNameAndZone = getCircleNameAndAccountName(olmId, connection);
						if(circleNameAccountNameAndZone != null)
						{
						circleName = circleNameAccountNameAndZone.split("#")[0];
						accountName =  circleNameAccountNameAndZone.split("#")[1];
						zoneName = circleNameAccountNameAndZone.split("#")[2];
						}
						else
						{
							throw new Exception("Circle name and Account name not found for OLM Id :"+olmId);
						}
						counter=2; //Neetika
					}
					//else
					//{
					if(counter == 2) //its OLM id in field
					{
						pstmtcssd.setString(1,zoneName);
						pstmtcssd.setString(2,circleName);
						pstmtcssd.setString(3,stockDto.getOlmId());
						pstmtcssd.setString(4,accountName);
						pstmtcssd.setInt(5,stockDto.getActivationSDCurrentMonth());
						pstmtcssd.setInt(6,stockDto.getActivationAndUpgradeHDCurentMonth());
						pstmtcssd.setInt(7,stockDto.getActivationHDDVRcurrentMonth());
						pstmtcssd.setInt(8,stockDto.getClosingStockNormalSDBOX());
						pstmtcssd.setInt(9,stockDto.getClosingStockHDBox());
						pstmtcssd.setInt(10,stockDto.getClosingStockHDDVR());
						pstmtcssd.setInt(11,stockDto.getSDAvaliable());
						pstmtcssd.setDouble(12,stockDto.getBalanceAvailable());
						pstmtcssd.setInt(13,stockDto.getSDEligibility());
						pstmtcssd.setInt(14,stockDto.getNosofdayStockSD());
						pstmtcssd.setInt(15,stockDto.getNosofdaystockHD());
						pstmtcssd.setInt(16,stockDto.getMaxOrderEligibilitySD());
						pstmtcssd.setInt(17,stockDto.getMaxOrderEligibilityHD());
						pstmtcssd.setInt(18,stockDto.getAveragecurrentMonthActivationSD());
						pstmtcssd.setInt(19,stockDto.getAveragecurrentMonthActivationHD());
						
						pstmtcssd.setInt(20,stockDto.getAverageSDCurrentMonthOneActivations());
						pstmtcssd.setInt(21,stockDto.getAverageHDCurrentMonthOneActivations());
						pstmtcssd.setInt(22,stockDto.getActivationSDCurrentMonthTwo());
						pstmtcssd.setInt(23,stockDto.getActivationAndUpgradeHDCurrentMonthTwo());
						pstmtcssd.setInt(24,stockDto.getAverageActivationSD());
						pstmtcssd.setInt(25,stockDto.getAverageActivationHD());
						pstmtcssd.setInt(26,stockDto.getCurrentmonthorAveragewhichislessNormalSD());
						pstmtcssd.setInt(27,stockDto.getCurrentmonthorAveragewhichislessHD());
						pstmtcssd.setString(28,dthadminid+"");

						pstmtcssd.setInt(29,stockDto.getSecurityDeposit());//in term of elgibility so integer value in security eligibility of DB
						pstmtcssd.setDouble(30,stockDto.getSecuritybalance());
						pstmtcssd.setInt(31,stockDto.getStockEligSD());
						pstmtcssd.setInt(32,stockDto.getStockEligHD());
						
						pstmtcssd.setInt(33,stockDto.getStockEligHDDVR());
					
						pstmtcssd.setInt(34,stockDto.getStockEligP1());
						pstmtcssd.setInt(35,stockDto.getStockEligP2());
						pstmtcssd.setInt(36,stockDto.getStockEligP3());
						pstmtcssd.setInt(37,stockDto.getStockEligP4());
						pstmtcssd.setInt(38,stockDto.getStockEligP5());
						//New 
						
					
						pstmtcssd.setDouble(39,stockDto.getDummy4());
						pstmtcssd.setDouble(40,stockDto.getDummy5());
						pstmtcssd.setDouble(41,stockDto.getDummy6());
						pstmtcssd.setDouble(42,stockDto.getDummy7());
						pstmtcssd.setDouble(43,stockDto.getDummy8());
						pstmtcssd.setDouble(44,stockDto.getDummy9());
						pstmtcssd.setDouble(45,stockDto.getDummy10());
						pstmtcssd.setDouble(46,stockDto.getDummy11());
						pstmtcssd.setDouble(47,stockDto.getDummy12());
						pstmtcssd.setDouble(48,stockDto.getDummy13());
						pstmtcssd.setDouble(49,stockDto.getDummy14());
						pstmtcssd.setDouble(50,stockDto.getDummy15());
						pstmtcssd.setDouble(51,stockDto.getDummy16());
						pstmtcssd.setDouble(52,stockDto.getDummy17());
						pstmtcssd.setDouble(53,stockDto.getDummy18());
						pstmtcssd.setDouble(54,stockDto.getDummy19());
						pstmtcssd.setDouble(55,stockDto.getDummy20());
						pstmtcssd.setDouble(56,stockDto.getDummy1());
						pstmtcssd.setDouble(57,stockDto.getDummy2());
						pstmtcssd.setDouble(58,stockDto.getDummy3());
						pstmtcssd.addBatch();

						
						pstmtdelcsd.setString(1,stockDto.getOlmId());
						pstmtdelcsd.setString(2,stockDto.getOlmId());
								
					
						pstmtdelcsd.addBatch();
					}
					counter=1;
				}
				insertedRecords = pstmtcssd.executeBatch();
				deletedRecords=pstmtdelcsd.executeBatch();
				logger.info("Total no of records inserted succusefully are :"+insertedRecords.length +" deletedRecords "+deletedRecords.length);
				
				
			
			}
			//Neetika
			else if(productType == 2 &&   distType == 1 ) //SWAP SF
			{
				while(itr.hasNext())
				{
					stockDto =	itr.next();
					if(counter == 1) //its OLM id in field
					{
						olmId = stockDto.getOlmId();
						//logger.info(" OLM ID :"+olmId);
						circleNameAccountNameAndZone = getCircleNameAndAccountName(olmId, connection);
						if(circleNameAccountNameAndZone != null)
						{
						counter=2;
						circleName = circleNameAccountNameAndZone.split("#")[0];
						accountName =  circleNameAccountNameAndZone.split("#")[1];
						zoneName = circleNameAccountNameAndZone.split("#")[2];
						}
						else
						{
							throw new Exception("Circle name and Account name not found for OLM Id :"+olmId);
						}
						
					}
					//else
					//{
						//pstmt = connection.prepareStatement(INSERT_FOR_SWAP_SF);
						if(counter==2)
						{
						pstmtssf.setString(1,zoneName);
						pstmtssf.setString(2,circleName);
						pstmtssf.setString(3,stockDto.getOlmId());
						pstmtssf.setString(4,accountName);
						//
						pstmtssf.setInt(5,stockDto.getStockEligSD());
						pstmtssf.setInt(6,stockDto.getStockEligHD());
						pstmtssf.setInt(7,stockDto.getStockEligPVR());
						pstmtssf.setInt(8,stockDto.getStockEligHDDVR());
						pstmtssf.setInt(9,stockDto.getStockEligSDPLUS());
						pstmtssf.setInt(10,stockDto.getStockEligP1());
						pstmtssf.setInt(11,stockDto.getStockEligP2());
						pstmtssf.setInt(12,stockDto.getStockEligP3());
						pstmtssf.setInt(13,stockDto.getStockEligP4());
						pstmtssf.setInt(14,stockDto.getStockEligP5());
						//
						pstmtssf.setInt(15,stockDto.getSwapSDClosingStock());
						pstmtssf.setInt(16,stockDto.getSwapHDClosingStock());
						pstmtssf.setInt(17,stockDto.getSwapDVRClosingStock());
						pstmtssf.setInt(18,stockDto.getSwapHDDVRClosingStock());
						pstmtssf.setInt(19,stockDto.getSwapSDPlusClosingStock());
						
						pstmtssf.setInt(20,stockDto.getMonthlyConsumptionSDStock());
						pstmtssf.setInt(21,stockDto.getMonthlyConsumptionHDStock());
						pstmtssf.setInt(22,stockDto.getMonthlyConsumptionPVRStock());
						pstmtssf.setInt(23,stockDto.getMonthlyConsumptionHDDVRStock());
						pstmtssf.setInt(24,stockDto.getMonthlyConsumptionSDPLUSStock());
						pstmtssf.setInt(25,stockDto.getConsumptionStockEligibilitySD());
						pstmtssf.setInt(26,stockDto.getConsumptionStockEligibilityHD());
						pstmtssf.setInt(27,stockDto.getConsumptionStockEligibilityPVR());
					
						pstmtssf.setInt(28,stockDto.getConsumptionStockEligibilityHDDVR());
						pstmtssf.setInt(29,stockDto.getConsumptionStockEligibilitySDPLUS());
						pstmtssf.setString(30,(dthadminid+""));
						//logger.info("8 is "+stockDto.getDummy8()+" 9 is "+stockDto.getDummy9());
						pstmtssf.setDouble(31,stockDto.getDummy1());
						pstmtssf.setDouble(32,stockDto.getDummy2());
						pstmtssf.setDouble(33,stockDto.getDummy3());
						pstmtssf.setDouble(34,stockDto.getDummy4());
						pstmtssf.setDouble(35,stockDto.getDummy5());
						pstmtssf.setDouble(36,stockDto.getDummy6());
						pstmtssf.setDouble(37,stockDto.getDummy7());
						pstmtssf.setDouble(38,stockDto.getDummy8());
						pstmtssf.setDouble(39,stockDto.getDummy9());
						pstmtssf.setDouble(40,stockDto.getDummy10());
						pstmtssf.setDouble(41,stockDto.getDummy11());
						pstmtssf.setDouble(42,stockDto.getDummy12());
						pstmtssf.setDouble(43,stockDto.getDummy13());
						pstmtssf.setDouble(44,stockDto.getDummy14());
						pstmtssf.setDouble(45,stockDto.getDummy15());
						pstmtssf.setDouble(46,stockDto.getDummy16());
						pstmtssf.setDouble(47,stockDto.getDummy17());
						pstmtssf.setDouble(48,stockDto.getDummy18());
						pstmtssf.setDouble(49,stockDto.getDummy19());
						pstmtssf.setDouble(50,stockDto.getDummy20());
						pstmtssf.addBatch();
						
						
						
						pstmtdelssf.setString(1,stockDto.getOlmId());
						pstmtdelssf.setString(2,stockDto.getOlmId());
								
					
						pstmtdelssf.addBatch();
						}
					//}
					counter=1;
				}
				insertedRecords = pstmtssf.executeBatch();
				deletedRecords=pstmtdelssf.executeBatch();
				logger.info("Total no of records inserted succusefully are :"+insertedRecords.length +" deletedRecords "+deletedRecords.length);
				
			}
			else if(productType == 2 &&   distType == 2 )
			{

				while(itr.hasNext())
				{
					stockDto =	itr.next();
					if(counter == 1) //its OLM id in field
					{
						olmId = stockDto.getOlmId();
						logger.info(" OLM ID :"+olmId);
						circleNameAccountNameAndZone = getCircleNameAndAccountName(olmId, connection);
						if(circleNameAccountNameAndZone != null)
						{
						counter=2;
						circleName = circleNameAccountNameAndZone.split("#")[0];
						accountName =  circleNameAccountNameAndZone.split("#")[1];
						zoneName = circleNameAccountNameAndZone.split("#")[2];
						}
						else
						{
							throw new Exception("Circle name and Account name not found for OLM Id :"+olmId);
						}
						
					}
					//else
					//{
						
						if(counter==2)
						{
						pstmtssd.setString(1,zoneName);
						pstmtssd.setString(2,circleName);
						pstmtssd.setString(3,stockDto.getOlmId());
						pstmtssd.setString(4,accountName);
						pstmtssd.setInt(5,stockDto.getSwapSDClosingStock());
						pstmtssd.setInt(6,stockDto.getSwapHDClosingStock());
						pstmtssd.setInt(7,stockDto.getSwapDVRClosingStock());
						pstmtssd.setInt(8,stockDto.getSwapHDDVRClosingStock());
						pstmtssd.setInt(9,stockDto.getSwapSDPlusClosingStock());
						pstmtssd.setDouble(10,stockDto.getAmountUsedinSwapBoxIssuance());
						pstmtssd.setDouble(11,stockDto.getSDConvertFromCommercial());
						pstmtssd.setDouble(12,stockDto.getBalanceAvailableDebitCredit());
						pstmtssd.setInt(13,stockDto.getMonthlyConsumptionSDStock());
						pstmtssd.setInt(14,stockDto.getMonthlyConsumptionHDStock());
						pstmtssd.setInt(15,stockDto.getMonthlyConsumptionPVRStock());
						pstmtssd.setInt(16,stockDto.getMonthlyConsumptionHDDVRStock());
						pstmtssd.setInt(17,stockDto.getMonthlyConsumptionSDPLUSStock());
						
						pstmtssd.setDouble(18,stockDto.getSecurityDepositAvaliableinSDBoxesRs350());
						pstmtssd.setInt(19,stockDto.getConsumptionSWAPSD());
						pstmtssd.setInt(20,stockDto.getConsumptionSWAPHD700());
					
						pstmtssd.setInt(21,stockDto.getConsumptionSWAPPVR1050());
						pstmtssd.setInt(22,stockDto.getConsumptionSWAPHDDVR1050());
						pstmtssd.setInt(23,stockDto.getConsumptionSWAPSDPlus());
						pstmtssd.setDouble(24,stockDto.getDebitBalanceExcessIssuanceinSwap());
						pstmtssd.setString(25,(dthadminid+""));
						
						pstmtssd.setInt(26,stockDto.getSecurityDeposit());//in term of elgibility so integer value in security eligibility of DB
						pstmtssd.setInt(27,stockDto.getStockEligSD());
						pstmtssd.setInt(28,stockDto.getStockEligHD());
						pstmtssd.setInt(29,stockDto.getStockEligPVR());
						pstmtssd.setInt(30,stockDto.getStockEligHDDVR());
						pstmtssd.setInt(31,stockDto.getStockEligSDPLUS());
						pstmtssd.setInt(32,stockDto.getStockEligP1());
						pstmtssd.setInt(33,stockDto.getStockEligP2());
						pstmtssd.setInt(34,stockDto.getStockEligP3());
						pstmtssd.setInt(35,stockDto.getStockEligP4());
						pstmtssd.setInt(36,stockDto.getStockEligP5());
						//New 
						
						pstmtssd.setDouble(37,stockDto.getSecuritybalance()); //in security deposit of DB field
						pstmtssd.setDouble(38,stockDto.getDummy1());
						pstmtssd.setDouble(39,stockDto.getDummy2());
						pstmtssd.setDouble(40,stockDto.getDummy3());
						pstmtssd.setDouble(41,stockDto.getDummy4());
						pstmtssd.setDouble(42,stockDto.getDummy5());
						pstmtssd.setDouble(43,stockDto.getDummy6());
						pstmtssd.setDouble(44,stockDto.getDummy7());
						pstmtssd.setDouble(45,stockDto.getDummy8());
						pstmtssd.setDouble(46,stockDto.getDummy9());
						pstmtssd.setDouble(47,stockDto.getDummy10());
						pstmtssd.setDouble(48,stockDto.getDummy11());
						pstmtssd.setDouble(49,stockDto.getDummy12());
						pstmtssd.setDouble(50,stockDto.getDummy13());
						pstmtssd.setDouble(51,stockDto.getDummy14());
						pstmtssd.setDouble(52,stockDto.getDummy15());
						pstmtssd.setDouble(53,stockDto.getDummy16());
						pstmtssd.setDouble(54,stockDto.getDummy17());
						pstmtssd.setDouble(55,stockDto.getDummy18());
						pstmtssd.setDouble(56,stockDto.getDummy19());
						pstmtssd.setDouble(57,stockDto.getDummy20());
						pstmtssd.addBatch();
						
						
						
						pstmtdelssd.setString(1,stockDto.getOlmId());
						pstmtdelssd.setString(2,stockDto.getOlmId());
								
					
						pstmtdelssd.addBatch();
						}
					//}
					counter=1;
				}
				insertedRecords = pstmtssd.executeBatch();
				deletedRecords=pstmtdelssd.executeBatch();
				logger.info("Total no of records inserted succusefully are :"+insertedRecords.length +" deletedRecords "+deletedRecords.length);
				
				
			}
			connection.commit();
		}
		//Following catch to handle same entries in one file added by neetika on 9 Aug 2014
		catch (SQLException e) {
			   SQLException current = e;
			   SQLException temp = e;
			   do {
				   e.printStackTrace();
				   temp=current.getNextException();
				   if(temp!=null){
				   boolean containerContainsContent = StringUtils.contains(temp.getMessage(), "unique constraint or unique index identified by \"1\" constrains table");
				  //logger.info("Exception Next Message is : "+current.getNextException().getMessage()+"class  SQL code"+current.getErrorCode());
				   if(containerContainsContent==true)
				   {
					   err_Msg =" Please check the file for any repetitions of partner or Please contact admin!!!";
					   
						try
						{
							if(connection!=null)
							connection.rollback();
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
						logger.info("******Some DB error occured, transaction roll backed,Plz try again!!!!Please check the file for any repetitions of partner or Please contact admin!!! ");
						temp.printStackTrace();
				   }
				   else
				   {
					   err_Msg =" Some Database error, Please contact admin!!!";
						try
						{
							if(connection!=null)
							connection.rollback();
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
						logger.info("******Some Database error, Please contact admin!!!");
						temp.printStackTrace();
				   }
				  }
			   } while ((current = current.getNextException()) != null);
			   
			 //  if(e.getClass().equals())
		}
		catch(Exception ex)
		{
			
			err_Msg =ex.getMessage()+" Unable to upload, Please contact admin!!!";
			try
			{	
				if(connection!=null)
				connection.rollback();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			logger.info("******Some DB error occured, transaction roll backed,Plz try again!!!! ");
			ex.printStackTrace();
		}
		finally
		{
			try
			{
			DBConnection.releaseResources(connection, pstmtssf, null);
			DBConnection.releaseResources(connection, pstmtssd, null);
			DBConnection.releaseResources(connection, pstmtcsf, null);
			DBConnection.releaseResources(connection, pstmtcssd, null);
			DBConnection.releaseResources(connection, pstmtdelssf, null);
			DBConnection.releaseResources(connection, pstmtdelssd, null);
			DBConnection.releaseResources(connection, pstmtdelcsf, null);
			DBConnection.releaseResources(connection, pstmtdelcsd, null);
			if(connection!=null)
			{
				connection.close();
			}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		logger.info("err_Msg  is message returned"+err_Msg);
		return err_Msg;
	}
	public boolean validateOlmId(String str)
	{
		if(str.trim().length() != 8)
		{
			return false;
		}
	
		
		return true;
	}
	
	
	public boolean validateOlmdistId(String distOlmId, int regionId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		Connection  connection = null;
		boolean flag = false;
		try{
			connection =  DBConnectionManager.getDBConnection(); // db2
			//logger.info("inside --- validateOlmId dist OLM id == "+distOlmId+"regionId"+regionId);
			if(regionId!=-1)
			{
				pstmt = connection.prepareStatement(SQL_SELECT_OLMID_ZONE); //query changed by neetika on 13 Aug
				pstmt.setString(1,distOlmId);
				pstmt.setInt(2,regionId);
			}
			else
			{
				pstmt = connection.prepareStatement(SQL_SELECT_OLMID); //query changed by neetika on 13 Aug
				pstmt.setString(1,distOlmId);
				
			}
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
		finally //Neetika for closing connections added the code (load test fail issue)
		{
			try{
			if(pstmt!=null)
			{
				pstmt.close();
				
			}
			if(rs!=null)
			{
				rs.close();
				
			}
			if(connection!=null)
			{
				connection.close();
				connection=null;
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("Error in closing connection rsrcs");
			}
			
		}
		return flag;
	}
	
	public boolean validateOlmdistIdWithAccountName(String distOlmId, String accountName)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		boolean flag = false;
		try{
			
			logger.info("inside --- validateOlmId dist OLM id with account name== "+distOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_OLMID_BY_ACCOUNT_NAME);
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
		finally
		{
			try{
			if(pstmt!=null)
			{
				pstmt.close();
				
			}
			if(rs!=null)
			{
				rs.close();
				
			}
			if(connection!=null)
			{
				connection.close();
				connection=null;
				
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("Error in closing connection rsrcs");
			}
			
		}
		return flag;
	}

	public String getCircleNameAndAccountName(String olmId, Connection connection)
	{
		PreparedStatement pstmt =null;
		ResultSet rset =  null;
		String circleNameAccountNameAndZone = ""; 
		try
		{
			pstmt = connection.prepareStatement(FETCH_CIRCLE_AND_ACCOUNTNAME);
			pstmt.setString(1,olmId);
			rset = pstmt.executeQuery();
			if(rset.next())
			{
				circleNameAccountNameAndZone = rset.getString("CIRCLE_NAME")+"#"+rset.getString("ACCOUNT_NAME")+"#"+rset.getString("REGION_NAME");
			}
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		finally
		{
			try{
			if(pstmt!=null)
			{
				pstmt.close();
				
			}
			if(rset!=null)
			{
				rset.close();
				
			}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("Error in closing connection rsrcs");
			}
			
		}
		return circleNameAccountNameAndZone;
	}
}
