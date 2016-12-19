package com.ibm.dpmisreports.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DropDownUtility
{
	private static Logger logger = Logger.getLogger(DropDownUtility.class.getName());
	
	private static ApplicationContext context = AppContext
			.getApplicationContext();

	private SpringCacheUtility springCacheUtility = (SpringCacheUtility) context
			.getBean("springCacheUtility");
	
	private DropDownUtility()
	{
		//Constructor is intentionally made private so that the Class can not be instantiated
	}
	
	
	private static DropDownUtility dropDownUtility = new DropDownUtility();
	// private constructor. This class cannot be instantiated from outside and
	// prevents subclassing.

	
	public static DropDownUtility getInstance() {
		return dropDownUtility;
	}
	
	/**
	 * @param 
	 * @param strAccountLevel
	 * @param intCircleID
	 * @return
	 * @throws Exception
	 */
	/*public List<SelectionCollection> getAllTsmSingleCircle(Integer intCircleID,Connection connection)
			throws Exception {
		System.out.println("Before getAllTsmSingleCircle() call...........................");
		List<SelectionCollection> allTsmSingleCircleList = springCacheUtility
				.getAllTsmSingleCircle(intCircleID,connection);
		System.out.println("After getAllTsmSingleCircle() call...........................");
		return allTsmSingleCircleList;
	}*/

	/**
	 * @param 
	 * @param strAccountID
	 * @return
	 * @throws Exception
	 */
	/*public List<SelectionCollection> getAllDistributorsUnderSingleTsm(String strAccountID,Connection connection) throws Exception {
		System.out.println("Before getAllDistributorsUnderSingleTsm() call...........................");
		List<SelectionCollection> allDistributorsUnderSingleTsmList = springCacheUtility
				.getAllDistributorsUnderSingleTsm(strAccountID,connection);
		System.out.println("After getAllDistributorsUnderSingleTsm() call...........................");
		return allDistributorsUnderSingleTsmList;
	}*/
	
	/**
	 * @author Nazim Hussain
	 * 
	 * This method can be used to fetch all the Active Circles created in the Application
	 * 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<SelectionCollection> getAllCircles() throws Exception
	{
		Connection connection=DBConnectionManager.getDBConnection();
		List<SelectionCollection> allCirclesList = springCacheUtility
		.getAllCircles(connection);
		return allCirclesList;
	}
	
	public List<SelectionCollection> getAllCirclesForDTHAdmin() throws Exception
	{
		Connection connection=DBConnectionManager.getDBConnection();
		List<SelectionCollection> allCirclesList = springCacheUtility
		.getAllCirclesForDTHAdmin(connection);
		return allCirclesList;
	}
	
	public List<SelectionCollection> getTsmCircles(long id) throws Exception
	{
		Connection connection=DBConnectionManager.getDBConnection();
		List<SelectionCollection> allCirclesList = springCacheUtility.getTsmCircles(connection,id);
		return allCirclesList;
	}
	
	
	
	/**
	 * @author Nazim Hussain
	 * 
	 * This method can be used to fetch all the Active Accounts of a level created in the Application
	 * 
	 * @param 
	 * @param strAccountLevel
	 * @return
	 * @throws Exception
	 */
	public  List<SelectionCollection> getAllAccounts(String strAccountLevel) throws Exception
	{
		Connection connection=DBConnectionManager.getDBConnection();
		List<SelectionCollection> allAccountsList = springCacheUtility
		.getAllAccounts(strAccountLevel, connection);
		return allAccountsList;
	}
	
	
	/**
	 * @author Nazim Hussain
	 * 
	 * This method can be used to fetch all the Active Accounts of a level created in a circle
	 * 
	 * @param 
	 * @param strAccountLevel
	 * @param intCircleID
	 * @param connection 
	 * @return
	 * @throws Exception
	 */
	public  List<SelectionCollection> getAllAccountsSingleCircle(String strAccountLevel, Integer intCircleID, Connection connection) throws Exception
	{
		List<SelectionCollection> allAccountsList = springCacheUtility
		.getAllAccountsSingleCircle(strAccountLevel,intCircleID,connection);
		return allAccountsList;
	}
	
	public  List<SelectionCollection> getAllAccountsMultipleCircle(String strAccountLevel, Integer accountId, Connection connection) throws Exception
	{
		List<SelectionCollection> allAccountsList = springCacheUtility
		.getAllAccountsMultipleCircle(strAccountLevel,accountId,connection);
		return allAccountsList;
	}
	
	/**
	 * @author Nazim Hussain
	 * 
	 * This method can be used to fetch all the Active Accounts of a level created in multiple circles
	 * 
	 * @param 
	 * @param strAccountLevel
	 * @param intCircleID
	 * @return
	 * @throws Exception
	 */
	public  List<SelectionCollection> getAllAccountsMultipleCircles(String strAccountLevel, String arrCircleID,Connection connection) throws Exception
	{
		
		List<SelectionCollection> allAccountsList= new ArrayList<SelectionCollection>();
		int count=0;
		String arrCircleIDs[] = arrCircleID.trim().split(",");
		for (String strCircleID : arrCircleIDs) {
			List<SelectionCollection> accountsList= springCacheUtility.getAllAccountsSingleCircle(strAccountLevel, Integer.valueOf(strCircleID), connection);
			for (SelectionCollection collection : accountsList) {
				if(collection.getStrValue()!="-1" || count==0)
					{
						allAccountsList.add(collection);
						count++;
					}
				
			}
			
		}
		
		return allAccountsList;
	}
	
	/**
	 * @author Nazim Hussain
	 * 
	 * This method can be used to fetch all the Active Accounts under a single account
	 * 
	 * @param 
	 * @param strAccountID
	 * @return
	 * @throws Exception
	 */
	public  List<SelectionCollection> getAllAccountsUnderSingleAccount(String strAccountID,Connection connection) throws Exception
	{
		List<SelectionCollection> allAccountsList = springCacheUtility
		.getAllAccountsUnderSingleAccount(strAccountID,connection);
		return allAccountsList;
	}
	
	public  List<SelectionCollection> getParent(String strAccountID,Connection connection) throws Exception
	{
		List<SelectionCollection> allAccountsList = springCacheUtility
		.getParent(strAccountID,connection);
		return allAccountsList;
	}
	
	/**
	 * @author Nazim Hussain
	 * 
	 * This method can be used to fetch all the Active Accounts under a Multiple accounts
	 * 
	 * @param 
	 * @param arrAccountID
	 * @return
	 * @throws Exception
	 */
	public  List<SelectionCollection> getAllAccountsUnderMultipleAccounts(String arrAccountID,Connection connection) throws Exception
	{
		List<SelectionCollection> allAccountsList= new ArrayList<SelectionCollection>();
		int count=0;
		String arrAccountIDs[] = arrAccountID.trim().split(",");
		for (String strAccountID : arrAccountIDs) {
			List<SelectionCollection> accountsList= springCacheUtility.getAllAccountsUnderSingleAccount(strAccountID, connection);
			
			
			for (SelectionCollection collection : accountsList) {
				if(collection.getStrValue()!="-1" || count==0)
				{
					allAccountsList.add(collection);
					count++;
				}
			}
		
		}
		
		return allAccountsList;
		
		
		
		
	}
	
	/**
	 * @author Nazim Hussain
	 * 
	 * This method can be used to fetch all the Active Products based on business category in a circle
	 * 
	 * @param 
	 * @param strBusinessCategory
	 * @param intCircleID
	 * @return
	 * @throws Exception
	 */
	public  List<SelectionCollection> getAllProductsSingleCircle(String strBusinessCategory, String strCircleID) throws Exception
	{
		
		List<SelectionCollection> allProductsList = springCacheUtility
		.getAllProductsSingleCircle(strBusinessCategory,strCircleID);
		return allProductsList;
	}
	
	/**
	 * @author Nazim Hussain
	 * 
	 * This method can be used to fetch all the Active Collection Types in Reverse Logistics
	 * 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public  List<SelectionCollection> getAllCollectionTypes() throws Exception
	{
		List<SelectionCollection> allProductsList = springCacheUtility
		.getAllCollectionTypes();
		
		return allProductsList;
	}


	public  List<SelectionCollection> getAllProductTypes() throws Exception
	{
		List<SelectionCollection> allProductsList = springCacheUtility
		.getAllProductTypes();
		
		return allProductsList;
	}
	public  List<DpProductCategoryDto> getTransactionType(int reportId) throws Exception 
	{
		
		List<DpProductCategoryDto> allTransactionTypeList = springCacheUtility
		.getTransactionType(reportId);
		
		return allTransactionTypeList;
	}
	
	public  List<DpProductCategoryDto> getProductCategoryLst() throws DAOException 
	{
		
		List<DpProductCategoryDto> allProductCategoryList = springCacheUtility
		.getProductCategoryLst();
		
		return allProductCategoryList;
	}
	
	public  List<DpProductCategoryDto> getProductCategoryLst1() throws DAOException 
	{
		
		List<DpProductCategoryDto> allProductCategoryList = springCacheUtility
		.getProductCategoryLst1();
		
		return allProductCategoryList;
	}
	public  List<DpProductCategoryDto> getProductCategoryLstReco() throws DAOException 
	{
		
		List<DpProductCategoryDto> allProductCategoryList = springCacheUtility
		.getProductCategoryLstReco();
		
		return allProductCategoryList;
	}
	
	
	
	

	public  List<SelectionCollection> getProductCategoryLstAjax(String businessCat,String reportId) throws DAOException 
	{
		
		List<SelectionCollection> allProductCategoryList = springCacheUtility
		.getProductCategoryLstAjax(businessCat,reportId);
		
		return allProductCategoryList;
	}
	public  String getProductIds(Connection connection,String slectedProd,String circleId) throws DAOException 
	{
		logger.info("HI In getProductIds Function of DropdownUtility ----- "+circleId);
		String productIds ="-1";
		String productId ="";
		String strProdType="";
		String strProdCategory="";
		Integer prodId =-1;
		String andCond ="";
		String andNOrCond="";
		PreparedStatement pstmt = null;
		ResultSet rsetProduct = null;
		logger.info(" in  &&&&&&&&&&&&&&&&&&&&& getRecoSummaryExcel::"+slectedProd);
		try
		{
		String[] strProdArray = slectedProd.split(",");
		for(int count=0;count<strProdArray.length;count++){
			//logger.info("Selected product == "+strProdArray[count]);
			prodId=Integer.parseInt(strProdArray[count].split("#")[0]);
			//System.out.println("prodId::::::"+prodId);
			
			
			if((prodId%10) == 0){
				strProdType ="0";
			}
			else if((prodId%15) == 0){  //for AV
				strProdType ="2";
			}else{
				strProdType ="1";
			}
			//logger.info("Prod Type == "+strProdType);
			if(strProdType.equals("1") || strProdType.equals("2")){
				strProdCategory = strProdArray[count].split("#")[1];
			}else if(strProdType.equals("0")){
				String[] categoryArr = strProdArray[count].split("#")[1].split(" ");
				strProdCategory = categoryArr[0];
				if(categoryArr.length>2){
					for(int i=1;i<categoryArr.length-1;i++){
						strProdCategory += " "+categoryArr[i];
					}
				}
				
			}
			//logger.info("strProdCategory == "+strProdCategory);
			andCond = "(PRODUCT_TYPE = '"+strProdType+"' and PRODUCT_CATEGORY='"+strProdCategory+"')";
			if(count!=0){
				andNOrCond += " or ";
			}
			andNOrCond += andCond;
		}
		logger.info("andNOrCond Query == "+andNOrCond);
		String query ="Select PRODUCT_ID from DP_PRODUCT_MASTER where ("+ andNOrCond +") ";
		if(!circleId.equals("0") && !circleId.equals("")){
			query += " and CIRCLE_ID in("+circleId+")";
		}
		query += " with ur";
		logger.info("query  to get Product Ids == "+query);
		pstmt =  connection.prepareStatement(query);
		rsetProduct= pstmt.executeQuery();
		while(rsetProduct.next()){
			if(!productId.equals("")){
				productId += ",";
				productId += rsetProduct.getString("PRODUCT_ID");
			}
			else{
				productId += rsetProduct.getString("PRODUCT_ID");
				
			}
			
		}
		if(!productId.equals("")){			
				productIds=productId;
		}
		logger.info("Product Ids == "+productIds);
		
	}catch (Exception e) 
	{
		e.printStackTrace();
		throw new DAOException(e.getMessage());
	}
	finally
	{
		DBConnectionManager.releaseResources(pstmt ,rsetProduct );
	}
	return productIds;
	}
	
	public List<SelectionCollection> getAllTSM(String selectedCircle,String businessCat,String accLevel,String loginId, Connection connection)  throws Exception  {
		// TODO Auto-generated method stub
		List<SelectionCollection> allTsmList = springCacheUtility
		.getAllTSM(selectedCircle,businessCat,accLevel,loginId,connection);
		
		return allTsmList;
	}
	public List<SelectionCollection> getAllDist(String selectedCircle,String selectedTSM,String accLevel,String loginId ,String businessCat,  Connection connection)  throws Exception  {
		// TODO Auto-generated method stub
		List<SelectionCollection> alldistList = springCacheUtility
		.getAllDist(selectedCircle,selectedTSM,accLevel,loginId ,businessCat,connection);
		
		return alldistList;
	}

	public List<SelectionCollection> getAllRet(String selectedFse,String distID,Connection connection) throws Exception {
		// TODO Auto-generated method stub
		
		List<SelectionCollection> alldistList = springCacheUtility
		.getAllRet(selectedFse,distID,connection);
		
		return alldistList;
	}

	public List<SelectionCollection> getAccountNames(String distID, String type, Connection connection) throws Exception {
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		String strQuery =null;
		ResultSet rset = null;
		//int intCircle=(Integer.valueOf(selectedCircle)).intValue();
		strQuery = DBQueries.GET_ALL_ACCOUNT;
		//int intTSM=(Integer.valueOf(selectedTSM)).intValue();
		try
		{
			int userType = Integer.parseInt(type);
			if(userType == -1) {
				strQuery = strQuery+" and parent_account in ("+distID +")";
				strQuery = strQuery+ " UNION ";
				strQuery = strQuery+DBQueries.GET_ALL_ACCOUNT + " and parent_account in (select ACCOUNT_ID from VR_ACCOUNT_DETAILS where (IS_DISABLE='N' or IS_DISABLE is null) and parent_account in ("+distID +"))";
				strQuery=strQuery+" ORDER BY ACCOUNT_NAME with ur";
			} else if (userType == 2) {
				strQuery = strQuery+" and parent_account in ("+distID +")";
				strQuery=strQuery+" ORDER BY ACCOUNT_NAME with ur";
			} else if (userType == 3) {
				strQuery = strQuery+" and parent_account in (select ACCOUNT_ID from VR_ACCOUNT_DETAILS where (IS_DISABLE='N' or IS_DISABLE is null) and parent_account in ("+distID +"))";
				strQuery=strQuery+" ORDER BY ACCOUNT_NAME with ur";
			} 
			
			pstmt = connection.prepareStatement(strQuery);
			
			logger.info("strQuery for USER TYPE "+userType+" ::  "+strQuery);
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
				throw new Exception("Error in getting Distributor");
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rset);
			}
		
		return listReturn;

	}
	//Added by neetika on 20 oct for getcircles
	public List getCircles(long id,  Connection connection) throws Exception {
		List listReturn = new ArrayList();
		PreparedStatement pstmt = null;
		String strQuery =null;
		ResultSet rset = null;
		//int intCircle=(Integer.valueOf(selectedCircle)).intValue();
		strQuery = DBQueries.GET_ALL_ACCOUNT_CIRCLES;
		//int intTSM=(Integer.valueOf(selectedTSM)).intValue();
		try
		{
			
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setLong(1, id);
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
					listReturn.add(rset.getInt(1));
			}

		}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new Exception("Error in getting circles");
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rset);
			}
		
		return listReturn;

	}
	

	public  List<SelectionCollection> getAllAccountsCircleAdmin(String strAccountLevel, long accountId, String circlesr,Connection connection) throws Exception
	{
		List<SelectionCollection> allAccountsList = springCacheUtility
		.getAllAccountsCircleAdmin(strAccountLevel,accountId,circlesr,connection);
		return allAccountsList;
	}
	//Added by neetika on 31 July for getZones
	public List<SelectionCollection>  getAllZones(Connection connection) throws Exception {
		List listReturn = new ArrayList();
		PreparedStatement pstmt = null;
		String strQuery =null;
		ResultSet rset = null;
		//int intCircle=(Integer.valueOf(selectedCircle)).intValue();
		strQuery = DBQueries.GET_ALL_ZONES;
		//int intTSM=(Integer.valueOf(selectedTSM)).intValue();
		try
		{
			
			pstmt = connection.prepareStatement(strQuery);
			
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
				throw new Exception("Error in getting zonessssssssss");
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rset);
			}
		
		return listReturn;

	}
	
	//Added by neetika on 1 aug for getZones
	public List<SelectionCollection>  getAllCirclesAsperZone(Connection connection,String zone) throws Exception {
		List listReturn = new ArrayList();
		PreparedStatement pstmt = null;
		String strQuery =null;
		ResultSet rset = null;
		
		strQuery = DBQueries.GET_ALL_CIRCLES_ZONE;

		try
		{
			
			strQuery=strQuery.replace("?",""+zone);
			//logger.info( strQuery+ "zone in drop down utility "+zone);
			pstmt = connection.prepareStatement(strQuery);
			
			rset = pstmt.executeQuery();
			SelectionCollection selCol = null;
			int count=0;
			while(rset.next())
			{
				if(count==0){
					selCol = new SelectionCollection();
					selCol.setStrValue("0");
					selCol.setStrText("--All--");
					listReturn.add(selCol);
				}
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(2));
				selCol.setStrText(rset.getString(1));
				count++;
				listReturn.add(selCol);
			}

		}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new Exception("Error in getting zonessssssssss");
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rset);
			}
		
		return listReturn;

	}
	
	
}