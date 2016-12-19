package com.ibm.dpmisreports.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class SpringCacheUtility {

	private static Logger logger = Logger.getLogger(SpringCacheUtility.class
			.getName());

	private static String TSM_ACCOUNT_LEVEL = "5";


	/*@Cacheable(value = {"allTsmSingleCircleCache","allAccountsSingleCircleCache"}, key="#intCircleID")
	public List<SelectionCollection> getAllTsmSingleCircle(Integer intCircleID,Connection connection) throws Exception {

		
		logger.info("--------  getAllTsmSingleCircle method of SpringCacheUtility class called  --------");
		List<SelectionCollection> allTsmSingleCircleList = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.GET_ALL_ACCOUNTS_SINGLE_CIRCLE;
		ResultSet rset = null;
		try {
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, TSM_ACCOUNT_LEVEL);
			pstmt.setInt(2, intCircleID);

			rset = pstmt.executeQuery();
			int count = 0;
			SelectionCollection selCol = null;
			while (rset.next()) {
				if (count == 0) {
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					allTsmSingleCircleList.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				allTsmSingleCircleList.add(selCol);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error in getting Accounts of a Single Circle");
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return allTsmSingleCircleList;
	}

	@Cacheable(value = "allDistributorsUnderSingleTsmCache", key="#strTsmAccountID")
	public List<SelectionCollection> getAllDistributorsUnderSingleTsm(String strTsmAccountID,Connection connection) throws Exception {

		
		logger.info("--------  getAllDistributorsUnderSingleTsm method of SpringCacheUtility class called  --------");
		List<SelectionCollection> allDistributorsUnderSingleTsmList = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.GET_ALL_ACCOUNTS_UNDER_SINGLE_ACCOUNT;
		ResultSet rset = null;
		try {
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, strTsmAccountID);

			rset = pstmt.executeQuery();
			int count = 0;
			SelectionCollection selCol = null;
			while (rset.next()) {
				if (count == 0) {
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					allDistributorsUnderSingleTsmList.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				allDistributorsUnderSingleTsmList.add(selCol);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(
					"Error in getting All Accounts under a Single Account");
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return allDistributorsUnderSingleTsmList;
	}
*/
	//@Cacheable(value = "allCirclesCache")
	public  List<SelectionCollection> getAllCirclesForDTHAdmin(Connection connection) throws Exception
	{
		
		logger.info("getAllCircles method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		//String strQuery = DBQueries.GET_ALL_CIRCLES;
		//Changed by Yash: To provide ordering of circles alphabatically and keeping Pan on top
		String strQuery = "SELECT CIRCLE_ID, CIRCLE_NAME FROM VR_CIRCLE_MASTER where STATUS=? AND CIRCLE_ID !=0 ORDER BY CIRCLE_NAME";
		ResultSet rset = null;
		try
		{
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, Constants.ACCOUNT_ACTIVE_STATUS);
			rset = pstmt.executeQuery();
			
			SelectionCollection selCol = null;
			while(rset.next())
			{
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Circles");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	public  List<SelectionCollection> getAllCircles(Connection connection) throws Exception
	{
		
		logger.info("getAllCircles method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		//String strQuery = DBQueries.GET_ALL_CIRCLES;
		//Changed by Yash: To provide ordering of circles alphabatically and keeping Pan on top
		String strQuery = "SELECT CIRCLE_ID, CIRCLE_NAME FROM VR_CIRCLE_MASTER where STATUS=? AND CIRCLE_ID !=0 ORDER BY CIRCLE_NAME";
		ResultSet rset = null;
		try
		{
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, Constants.ACCOUNT_ACTIVE_STATUS);
			rset = pstmt.executeQuery();
			SelectionCollection defaultCircle = new SelectionCollection();
			defaultCircle.setStrValue("0");
			defaultCircle.setStrText("Pan India");
			listReturn.add(defaultCircle);
			
			SelectionCollection selCol = null;
			while(rset.next())
			{
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Circles");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	public  List<SelectionCollection> getTsmCircles(Connection connection , long id) throws Exception
	{
		
		logger.info("getTsmCircles method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		//String strQuery = DBQueries.GET_ALL_CIRCLES;
		//Changed by Yash: To provide ordering of circles alphabatically and keeping Pan on top
		String strQuery = "select b.CIRCLE_ID,b.CIRCLE_NAME from DP_ACCOUNT_CIRCLE_MAP a,VR_CIRCLE_MASTER b where a.CIRCLE_ID=b.CIRCLE_ID and b.STATUS=?  and ACCOUNT_ID=? ORDER BY CIRCLE_NAME with ur";
		ResultSet rset = null;
		try
		{
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, Constants.ACCOUNT_ACTIVE_STATUS);
			pstmt.setLong(2, id);
			rset = pstmt.executeQuery();
			//SelectionCollection defaultCircle = new SelectionCollection();
			//defaultCircle.setStrValue("0");
			//defaultCircle.setStrText("Pan India");
			//listReturn.add(defaultCircle);
			
			SelectionCollection selCol = null;
			while(rset.next())
			{
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Circles");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	
	
	
	public  List<SelectionCollection> getAllAccounts(String strAccountLevel,Connection connection) throws Exception
	{
		
		logger.info("getAllAccounts method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.GET_ALL_ACCOUNTS;
		ResultSet rset = null;
		try
		{
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, strAccountLevel);
			rset = pstmt.executeQuery();
			
			SelectionCollection selCol = null;
			int count=0;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting All Accounts");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	
	//@Cacheable(value = "allAccountsSingleCircleCache", key="#intCircleID")
	public  List<SelectionCollection> getAllAccountsSingleCircle(String strAccountLevel, Integer intCircleID, Connection connection) throws Exception
	{
		
		logger.info("getAllAccounts of a Single Circle method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery =null;
		int i= intCircleID.intValue();
		System.out.println("intCircleID::"+intCircleID);
		System.out.println("strAccountLevel::"+strAccountLevel);
		if(intCircleID.intValue()!=0 && intCircleID.intValue()!=-1)
		{
			if(strAccountLevel.equalsIgnoreCase("5")  )
			{
				strQuery = DBQueries.GET_ALL_ACCOUNTS_SINGLE_CIRCLE_MULTIPLE_CIRCLE;
			}
			else
			{
				strQuery = DBQueries.GET_ALL_ACCOUNTS_SINGLE_CIRCLE;
			}
		}
		else
		{
			strQuery = DBQueries.GET_ALL_ACCOUNTS;
		}
		ResultSet rset = null;
		try
		{
			System.out.println(strQuery);
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, strAccountLevel);
			if(!(intCircleID.intValue()==0 || intCircleID.intValue()==-1))
				pstmt.setInt(2, intCircleID);
			
			rset = pstmt.executeQuery();
			int count=0;
			SelectionCollection selCol = null;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Accounts of a Single Circle");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	
	public  List<SelectionCollection> getAllAccountsMultipleCircle(String strAccountLevel, Integer accountId, Connection connection) throws Exception
	{
		
		logger.info("getAllAccounts of a multiple Circle method called"+accountId+ " strAccountLevel"+strAccountLevel);
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery =null;
		//int i= intCircleID.intValue();
		//System.out.println("intCircleID::"+intCircleID);
		//System.out.println("strAccountLevel::"+strAccountLevel);
	
			if(strAccountLevel.equalsIgnoreCase("5")  )
			{
				strQuery = DBQueries.GET_ALL_ACCOUNTS_MULTIPLE_CIRCLE;
			}
			else
			{
				strQuery = DBQueries.GET_ALL_ACCOUNTS_SINGLE_CIRCLE;
			}
		
		ResultSet rset = null;
		try
		{
			System.out.println(strQuery);
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, strAccountLevel);
			pstmt.setInt(2, accountId);
			//if(!(intCircleID.intValue()==0 || intCircleID.intValue()==-1))
			//	pstmt.setInt(2, intCircleID);
			
			rset = pstmt.executeQuery();
			int count=0;
			SelectionCollection selCol = null;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Accounts of a Single Circle");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	
	
	
	
	
	
	
/*	@Cacheable(value = "allAccountsMultipleCircleCache")
	
	public  List<SelectionCollection> getAllAccountsMultipleCircles(String strAccountLevel, String arrCircleID) throws Exception
	{
		Connection connection = DBConnectionManager.getDBConnection();
		
		logger.info("getAllAccounts of Multiple Circles method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.GET_ALL_ACCOUNTS_MULTIPLE_CIRCLES;
		strQuery = strQuery.replace("?2", arrCircleID);
		ResultSet rset = null;
		try
		{
			
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, strAccountLevel);
			
			
			rset = pstmt.executeQuery();
			int count =0;
			SelectionCollection selCol = null;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				count ++;
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				
				listReturn.add(selCol);
			}
			logger.info("in Utility Count == "+count);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Accounts of multiple circles");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	*/
	//@Cacheable(value = "allAccountsUnderSingleAccountCache", key="#strAccountID")
	
	public List<SelectionCollection> getAllAccountsUnderSingleAccount(String strAccountID,Connection connection) throws Exception
	{
		
		logger.info("getAllAccounts under a Single Account method called"+strAccountID);
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtlevel = null;
		String strQuery =null;
		String checklevel=null;
		int grp=0;
		checklevel=DBQueries.SQL_SELECT_DIST_ACC_LEVEL;
		
		//logger.info("strAccountID : "+strAccountID);
		/*if((Integer.valueOf(strAccountID)).intValue()!=-1)
			strQuery = DBQueries.GET_ALL_ACCOUNTS_UNDER_SINGLE_ACCOUNT;
		else
			strQuery = DBQueries.GET_ALL_ACCOUNTS;*/
		//logger.info(strQuery+"  =  strAccountID  "+strAccountID);
		ResultSet rset = null;
		try
		{
			
			pstmtlevel=connection.prepareStatement(checklevel);
			pstmtlevel.setString(1, strAccountID);
			rset=pstmtlevel.executeQuery();
			while(rset.next())
			{
				grp=rset.getInt("GROUP_ID");
				
			}
			if(grp==6) //if parent is tsm then check dp distributor mapping
			{
				if((Integer.valueOf(strAccountID)).intValue()!=-1)
					strQuery = DBQueries.GET_ALL_ACCOUNTS_UNDER_SINGLE_ACCOUNT_DIST;
				else
					strQuery = DBQueries.GET_ALL_ACCOUNTS;
			}
			else
			{
				if((Integer.valueOf(strAccountID)).intValue()!=-1)
					strQuery = DBQueries.GET_ALL_ACCOUNTS_UNDER_SINGLE_ACCOUNT;
				else
					strQuery = DBQueries.GET_ALL_ACCOUNTS;
			}
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, strAccountID);
			
			rset = pstmt.executeQuery();
			int count=0;
			SelectionCollection selCol = null;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting All Accounts under a Single Account");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(pstmtlevel, null);
		}
		return listReturn;
	}
	
	/*@Cacheable(value = "allAccountsUnderMultipleAccountCache", key="#arrAccountID")
	public List<SelectionCollection> getAllAccountsUnderMultipleAccounts(String arrAccountID) throws Exception
	{
		Connection connection = DBConnectionManager.getDBConnection();
		
		logger.info("getAllAccounts under Multiple Accounts method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.GET_ALL_ACCOUNTS_UNDER_MULTIPLE_ACCOUNTS;
		ResultSet rset = null;
		
		try
		{
			
			strQuery =strQuery.replace("?1", arrAccountID);
			pstmt = connection.prepareStatement(strQuery);
			
			rset = pstmt.executeQuery();
			int count=0;
			SelectionCollection selCol = null;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting All Accounts under a Multiple Accounts");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	*/
	
	public List<SelectionCollection> getAllProductsSingleCircle(String strBusinessCategory, String strCircleID) throws Exception
	{
		Connection connection = DBConnectionManager.getDBConnection();
		
		logger.info("getAllProductsSingleCircle method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.GET_ALL_PRODUCTS_SINGLE_CIRCLE;
		ResultSet rset = null;
		try
		{
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, strBusinessCategory);
			pstmt.setString(2, strCircleID);
			
			rset = pstmt.executeQuery();
			
			SelectionCollection selCol = null;
			
			SelectionCollection selColDef = new SelectionCollection();
			selColDef.setStrValue(Constants.SEL_DEF_VAL);
			selColDef.setStrText(Constants.SEL_DEF_TXT);
			
			listReturn.add(selColDef);
			
			while(rset.next())
			{
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Products of a Single Circle");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	
	public List<DpProductCategoryDto> getTransactionType(int reportId) throws Exception
	{

		Connection connection = DBConnectionManager.getMISReportDBConnection();
		
		logger.info("getTransactionType method called");
		List<DpProductCategoryDto> listReturn = new ArrayList<DpProductCategoryDto>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.GET_TRANSACTION_TYPE_LIST;
		ResultSet rset = null;
		String reportType="";
		try
		{
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, String.valueOf(reportId));
			rset = pstmt.executeQuery();
			
			DpProductCategoryDto selCol = null;
			while(rset.next())
			{
				reportType= rset.getString("REPORT_TYPE");
				if(reportType !=null && reportType.equalsIgnoreCase("0")) // for All 
				{
					selCol = new DpProductCategoryDto();
					selCol.setStrValue(rset.getString(1));
					selCol.setStrText(rset.getString(2));
					listReturn.add(selCol);
					selCol=null;
				}
				else if(reportType !=null && reportType.equalsIgnoreCase("1")) // for Sales only
				{
					if(!rset.getString(2).equalsIgnoreCase("CPE")) {
						selCol = new DpProductCategoryDto();
						selCol.setStrValue(rset.getString(1));
						selCol.setStrText(rset.getString(2)); 
						listReturn.add(selCol);
						selCol=null;
					}
				}
				else if(reportType !=null && reportType.equalsIgnoreCase("2")) // for Deposit only
				{
					if(rset.getString(2).equalsIgnoreCase("CPE")) {
						selCol = new DpProductCategoryDto();
						selCol.setStrValue(rset.getString(1));
						selCol.setStrText(rset.getString(2));
						listReturn.add(selCol);
						selCol=null;
					}
				}
				
				else if(reportType !=null && reportType.equalsIgnoreCase("4")) // for AV secodary report
				{
					if(rset.getString(2).equalsIgnoreCase("Activation Voucher(AV)")) {
						selCol = new DpProductCategoryDto();
						selCol.setStrValue(rset.getString(1));
						selCol.setStrText(rset.getString(2));
						listReturn.add(selCol);
						selCol=null;
					}
				}
				
				
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting getTransactionType");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	
	}
	
	
	public List<SelectionCollection> getAllCollectionTypes() throws Exception
	{
		Connection connection = DBConnectionManager.getDBConnection();
		
		logger.info("getAllCollectionTypes method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.SQL_COLLECTION_MST;
		ResultSet rset = null;
		try
		{
			pstmt = connection.prepareStatement(strQuery);
			
			rset = pstmt.executeQuery();
			
			SelectionCollection selCol = null;
			while(rset.next())
			{
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Collection Types");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}

	
	public List<SelectionCollection> getAllProductTypes() throws Exception
	{
		Connection connection = DBConnectionManager.getDBConnection();
		
		logger.info("getAllCollectionTypes method called");
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.SQL_COLLECTION_MST;
		ResultSet rset = null;
		try
		{
			pstmt = connection.prepareStatement(strQuery);
			
			rset = pstmt.executeQuery();
			
			SelectionCollection selCol = null;
			while(rset.next())
			{
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Collection Types");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
		
	public List<SelectionCollection> getParent(String strAccountID,Connection connection) throws Exception
	{
		
		logger.info("getAllAccounts under a Single Account method called:: strAccountID::"+strAccountID);
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery = DBQueries.SQL_COLLECTION_PARENT_GENERIC;
		ResultSet rset = null;
		try
		{
			pstmt = connection.prepareStatement(strQuery);
			System.out.println("connection::"+connection.getMetaData().getURL());
			pstmt.setString(1, strAccountID);
			
			rset = pstmt.executeQuery();
			int count=0;
			SelectionCollection selCol = null;
			while(rset.next())
			{
				/*if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}*/
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Parent a Single Account");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	
	public List<DpProductCategoryDto> getProductCategoryLst() throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		
		logger.info("in getProductCategoryLst() Dao IML  ......Started");
		List<DpProductCategoryDto> dcProductCategListDTO	= new ArrayList<DpProductCategoryDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpProductCategoryDto  dcProducCatetDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(DBQueries.GET_PRODUCT_CATEGORY_LIST);
			pstmt.setInt(1, 8);
			rset = pstmt.executeQuery();
			dcProductCategListDTO = new ArrayList<DpProductCategoryDto>();
			String prodCatId ="";
			while(rset.next())
			{
				dcProducCatetDto = new DpProductCategoryDto();
				dcProducCatetDto.setProductCategory(rset.getString("PRODUCT_CATEGORY"));
				prodCatId = rset.getString("ID")+"#"+ rset.getString("PRODUCT_CATEGORY");
				dcProducCatetDto.setProductCategoryId(prodCatId);
				dcProductCategListDTO.add(dcProducCatetDto);
			}
			logger.info("in getProductCategoryLst() Dao IML  ......Ended Success Fully");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			//Updaeted by Shilpa
			DBConnectionManager.releaseResources(pstmt ,rset);
			/*pstmt = null;
			rset = null;*/
		}
		return dcProductCategListDTO;
}
	public List<DpProductCategoryDto> getProductCategoryLst1() throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		
		logger.info("in getProductCategoryLst() Dao IML  ......Started");
		List<DpProductCategoryDto> dcProductCategListDTO	= new ArrayList<DpProductCategoryDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpProductCategoryDto  dcProducCatetDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(DBQueries.GET_PRODUCT_CATEGORY_LIST);
			pstmt.setInt(1, 8);
			rset = pstmt.executeQuery();
			dcProductCategListDTO = new ArrayList<DpProductCategoryDto>();
			String prodCatId ="";
			while(rset.next())
			{
				dcProducCatetDto = new DpProductCategoryDto();
				dcProducCatetDto.setProductCategory(rset.getString("PRODUCT_CATEGORY"));
				prodCatId = rset.getString("ID");
				dcProducCatetDto.setProductCategoryId(prodCatId);
				dcProductCategListDTO.add(dcProducCatetDto);
			}
			logger.info("in getProductCategoryLst() Dao IML  ......Ended Success Fully");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			//Updaeted by Shilpa
			DBConnectionManager.releaseResources(pstmt ,rset);
			/*pstmt = null;
			rset = null;*/
		}
		return dcProductCategListDTO;
}
	public List<DpProductCategoryDto> getProductCategoryLstReco() throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		
		logger.info("in getProductCategoryLst() Dao IML  ......Started");
		List<DpProductCategoryDto> dcProductCategListDTO	= new ArrayList<DpProductCategoryDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpProductCategoryDto  dcProducCatetDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(DBQueries.GET_PRODUCT_CATEGORY_LIST_RECO);
			rset = pstmt.executeQuery();
			dcProductCategListDTO = new ArrayList<DpProductCategoryDto>();
			String prodCatId ="";
			while(rset.next())
			{
				dcProducCatetDto = new DpProductCategoryDto();
				dcProducCatetDto.setProductCategory(rset.getString("PRODUCT_CATEGORY"));
				prodCatId = rset.getString("ID")+"#"+ rset.getString("PRODUCT_CATEGORY");
				dcProducCatetDto.setProductCategoryId(prodCatId);
				dcProductCategListDTO.add(dcProducCatetDto);
			}
			logger.info("in getProductCategoryLst() Dao IML  ......Ended Success Fully");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			//Updaeted by Shilpa
			DBConnectionManager.releaseResources(pstmt ,rset);
			/*pstmt = null;
			rset = null;*/
		}
		return dcProductCategListDTO;
}
	
	
	
	
	
	public List<SelectionCollection> getProductCategoryLstAjax(String businessCat,String reportId) throws DAOException 
	{
		
	Connection connection = DBConnectionManager.getDBConnection();
	
	logger.info("in getProductCategoryLst() Dao IML  ......Started");
	List<SelectionCollection> dcProductCategListDTO	= new ArrayList<SelectionCollection>();
	PreparedStatement pstmt = null;
	ResultSet rset			= null;
	SelectionCollection  dcProducCatetDto = null;
	Integer configId =0;	
	String configIds="";
	StringTokenizer st = null;
	String cat="";
	try
	{
		
		
		st = new StringTokenizer(businessCat,",");
		while(st.hasMoreTokens())
		{
			cat =(String) st.nextToken();
			System.out.println("cat::"+cat);
			
			if(cat.equalsIgnoreCase("1"))
			{
				configIds = configIds + "8" + ",";
			}
			if(cat.equalsIgnoreCase("4"))
			{
				configIds = configIds + "22" + ",";
			}
			else
			{
				configIds = configIds + "1000" + ",";
			}
		}
		System.out.println("configIds:::"+configIds);
		if(configIds != null && configIds.indexOf(",") > -1 )
		{
			configIds =configIds.substring(0,configIds.length()-1);
		}
		/*if(businessCat != null && businessCat.equals("1")) //CPE
			configId= 8 ;// For CPE products only
		if(businessCat != null && !businessCat.equals("1")) //AV
			configId= 22 ;// For AV products only
*/			
		
		System.out.println("configIds:::"+configIds);
		pstmt = connection.prepareStatement("SELECT VALUE as PRODUCT_CATEGORY, ID FROM DP_CONFIGURATION_DETAILS WHERE CONFIG_ID in ("+configIds+") with ur");//Neetika IN2046752
		//pstmt.setString(1, configIds);
		rset = pstmt.executeQuery();
		dcProductCategListDTO = new ArrayList<SelectionCollection>();
		String prodCatId ="";
		
		int i=0;
		while(rset.next())
		{
			if(i==0)
			{
				if(reportId != null && reportId.equals("47"))
				{
					System.out.println("This is AV Stock Detail report----");
					
					dcProducCatetDto = new SelectionCollection();
					dcProducCatetDto.setStrText("--All--");
					prodCatId = "-1#ALL";
					dcProducCatetDto.setStrValue(prodCatId);
					dcProductCategListDTO.add(dcProducCatetDto);
					
					dcProducCatetDto = new SelectionCollection();
					dcProducCatetDto.setStrText("--ALL PRODUCTS--");
					prodCatId = "-999#ALL PRODUCTS";
					dcProducCatetDto.setStrValue(prodCatId);
					dcProductCategListDTO.add(dcProducCatetDto);
				}
				else
				{	
					dcProducCatetDto = new SelectionCollection();
					dcProducCatetDto.setStrText("--All--");
					prodCatId = "-1#ALL";
					dcProducCatetDto.setStrValue(prodCatId);
					dcProductCategListDTO.add(dcProducCatetDto);
				}
			}
			i++;
			dcProducCatetDto = new SelectionCollection();
			dcProducCatetDto.setStrText(rset.getString("PRODUCT_CATEGORY"));
			prodCatId = rset.getString("ID")+"#"+ rset.getString("PRODUCT_CATEGORY");
			dcProducCatetDto.setStrValue(prodCatId);
			dcProductCategListDTO.add(dcProducCatetDto);
		}
		logger.info("in getProductCategoryLst() Dao IML  ......Ended Success Fully");
		
	}
	catch (Exception e) 
	{
		e.printStackTrace();
		throw new DAOException(e.getMessage());
	}
	finally
	{
		//Updaeted by Shilpa
		DBConnectionManager.releaseResources(pstmt ,rset);
		/*pstmt = null;
		rset = null;*/
	}
	return dcProductCategListDTO;
}
	
	//@CacheEvict(value = "allAccountsUnderSingleAccountCache", key="#strAccountID")
	public void updateAccountUnderSingleAccount(String strAccountID) throws ServletException {
		
	}
	//@CacheEvict(value ="allAccountsSingleCircleCache", key="#intCircleID")
	public void updateAccountsSingleCircle(int intCircleID) throws ServletException {
		
	}
	public List<SelectionCollection> getAllTSM(String selectedCircle,String businessCat,String accLevel,String loginId,  Connection connection) throws Exception{
		// TODO Auto-generated method stub
		logger.info("getAllTSM method called");
		System.out.println("businessCat:::::::::"+businessCat);
	
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery =null;
		ResultSet rset = null;
		//int intCircle=(Integer.valueOf(selectedCircle)).intValue();
		
		
		if(businessCat != null && !"".equalsIgnoreCase(businessCat)) {
			if(selectedCircle.equals("-1") || selectedCircle.equals("0"))
			{
				strQuery = DBQueries.GET_ALL_ACCOUNT_GENERIC_REPORT+" and account_level=5 and a.TRANSACTION_TYPE in(select distinct TRANSACTION_TYPE from DP_BUSINESS_CATEGORY_MASTER where CATEGORY_CODE in("+businessCat+" ) ) ";
			}
			else
			{
				strQuery = DBQueries.GET_ALL_ACCOUNT_GENERIC_REPORT+" and b.circle_id in ("+selectedCircle+") and account_level=5 and a.TRANSACTION_TYPE in(select distinct TRANSACTION_TYPE from DP_BUSINESS_CATEGORY_MASTER where CATEGORY_CODE in("+businessCat+" ) ) ";
			}
			if(accLevel != null && accLevel.equalsIgnoreCase("5"))
			{
				strQuery = strQuery +" and  a.ACCOUNT_ID = "+loginId;
			}
		}
		else
		{
			if(selectedCircle.equals("-1") || selectedCircle.equals("0"))
			{
				strQuery = DBQueries.GET_ALL_ACCOUNT_GENERIC_REPORT+" and account_level=5  ";
			}
			else
			{
				strQuery = DBQueries.GET_ALL_ACCOUNT_GENERIC_REPORT+" and b.circle_id in ("+selectedCircle+") and account_level=5  ";
			}
			if(accLevel != null && accLevel.equalsIgnoreCase("5"))
			{
				strQuery = strQuery +" and  a.ACCOUNT_ID = "+loginId;
			}
		}
		strQuery = strQuery +" ORDER BY ACCOUNT_NAME with ur ";
		try
		{
			System.out.println("strQuery::::::"+strQuery);
			pstmt = connection.prepareStatement(strQuery);
			
			
			
			rset = pstmt.executeQuery();
			int count=0;
			SelectionCollection selCol = null;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting TSM");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
		
	}
	public List<SelectionCollection> getAllDist(String selectedCircle,String selectedTSM,String accLevel,String loginId ,String businessCat, Connection connection) throws Exception{
		// TODO Auto-generated method stub
		logger.info("getAllAccountList method called");
		
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery =null;
		ResultSet rset = null;
		//int intCircle=(Integer.valueOf(selectedCircle)).intValue();
		strQuery = DBQueries.GET_ALL_ACCOUNT_GENERIC;
		//int intTSM=(Integer.valueOf(selectedTSM)).intValue();
		
		//System.out.println("111111111111111");
		System.out.println("selectedCircle::"+selectedCircle);
		System.out.println("selectedTSM::"+selectedTSM);
		
		 
		
		if((!(selectedCircle.equals("0")))&& (!(selectedCircle.equals("-1"))))
			strQuery = strQuery+"and VAD.CIRCLE_ID in ("+selectedCircle+")";
		if(!(selectedTSM.equals("-1")) )
			strQuery = strQuery+"and DDM.parent_account in ("+selectedTSM +")";
		
		
		if((selectedTSM.equals("-1")) && accLevel != null && accLevel.equals("5"))
			strQuery = strQuery+"and DDM.parent_account in ("+loginId +")";
		
		
		if(businessCat != null && !"".equalsIgnoreCase(businessCat)) {
			if((selectedTSM.equals("-1")) && accLevel != null && Integer.parseInt(accLevel) < 5 )
			{
				strQuery = strQuery +" and account_id in(select DP_DIST_ID from DP_DISTRIBUTOR_MAPPING where TRANSACTION_TYPE_ID " 
									  +" in(select TRANSACTION_TYPE from DP_BUSINESS_CATEGORY_MASTER where CATEGORY_CODE in( "+businessCat+" ) ) )";
				
			}
		}
			strQuery=strQuery+" and VAD.account_level=6 ORDER BY ACCOUNT_NAME with ur";
			
			System.out.println(strQuery);
			try
			{
				pstmt = connection.prepareStatement(strQuery);
				rset = pstmt.executeQuery();
				int count=0;
				SelectionCollection selCol = null;
				while(rset.next())
				{
					if(count==0){
						selCol = new SelectionCollection();
						selCol.setStrValue("-1");
						selCol.setStrText("--All--");
						listReturn.add(selCol);
					}
					selCol = new SelectionCollection();
					selCol.setStrValue(rset.getString(1));
					selCol.setStrText(rset.getString(2));
					count++;
					listReturn.add(selCol);
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new Exception("Error in getting TSM");
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rset);
			}
			
			
		
		
		
		
		return listReturn;
	}

	public List<SelectionCollection> getAllRet(String selectedFse,String distID,Connection connection) throws Exception {
		// TODO Auto-generated method stub
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery =null;
		ResultSet rset = null;
		//int intCircle=(Integer.valueOf(selectedCircle)).intValue();
		strQuery = DBQueries.GET_ALL_ACCOUNT;
		//int intTSM=(Integer.valueOf(selectedTSM)).intValue();
		try
		{
		if(!(selectedFse.equals("-1"))){
			strQuery = strQuery+"and parent_account in ("+selectedFse +")";
			strQuery=strQuery+"ORDER BY ACCOUNT_NAME with ur";	
			pstmt = connection.prepareStatement(strQuery);
			rset = pstmt.executeQuery();
			int count=0;
			SelectionCollection selCol = null;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				listReturn.add(selCol);
			}
		}
		else
		{
			List<SelectionCollection> fseList= new ArrayList<SelectionCollection>();
			List<SelectionCollection> retList= new ArrayList<SelectionCollection>();
			fseList=getAllAccountsUnderSingleAccount(distID, connection);
			int count=0;
			for (SelectionCollection fse : fseList) {
							
							retList= getAllAccountsUnderSingleAccount(fse.getStrValue(), connection);
							for (SelectionCollection collection : retList) {
								if(collection.getStrValue()!="-1" || count==0)
								{
									listReturn.add(collection);
									count++;
								}
							}
						}
					
		}
		}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new Exception("Error in getting TSM");
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rset);
			}
			
			
		
		
		
		
		return listReturn;
	}
	
	public  List<SelectionCollection> getAllAccountsCircleAdmin(String strAccountLevel, long accountId, String circlesr,Connection connection) throws Exception
	{
		
		logger.info("getAllAccounts of a multiple Circle method called"+accountId+ " strAccountLevel"+strAccountLevel);
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery =null;
		//int i= intCircleID.intValue();
		//System.out.println("intCircleID::"+intCircleID);
		//System.out.println("strAccountLevel::"+strAccountLevel);
	
			
			if(Integer.parseInt(circlesr)!=0 && Integer.parseInt(circlesr)!=-1)
			{
				if(strAccountLevel.equalsIgnoreCase("5")  )
				{
					strQuery = DBQueries.GET_ALL_ACCOUNTS_MULTIPLE_CIRCLE;
				}
				else
				{
					strQuery = DBQueries.GET_ALL_ACCOUNTS_SINGLE_CIRCLE;
				}
				
				pstmt = connection.prepareStatement(strQuery);
				pstmt.setString(1, strAccountLevel);
				pstmt.setInt(2,(int) accountId);
			}
			else
			{
				strQuery = DBQueries.GET_ALL_ACCOUNTS;
				
				pstmt = connection.prepareStatement(strQuery);
				pstmt.setString(1, strAccountLevel);
		
			}
		ResultSet rset = null;
		try
		{
			logger.info(strQuery);
		
			//if(!(intCircleID.intValue()==0 || intCircleID.intValue()==-1))
			//	pstmt.setInt(2, intCircleID);
			
			rset = pstmt.executeQuery();
			int count=0;
			SelectionCollection selCol = null;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("-1");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				count++;
				listReturn.add(selCol);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("Error in getting Accounts of a Circle admin ");
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	
	
	
}