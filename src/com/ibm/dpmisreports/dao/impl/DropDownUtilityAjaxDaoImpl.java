package com.ibm.dpmisreports.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.ibm.ObjectQuery.crud.util.Array;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.dao.DropDownUtilityAjaxDao;
import com.ibm.dpmisreports.exception.DAOException;
import org.apache.log4j.Logger;
import org.apache.xalan.xsltc.util.IntegerArray;

import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.dpmisreports.common.DropDownUtility;

public class DropDownUtilityAjaxDaoImpl extends BaseDaoRdbms implements DropDownUtilityAjaxDao 
{
	Logger logger = Logger.getLogger(DropDownUtilityAjaxDaoImpl.class.getName());
	
	public DropDownUtilityAjaxDaoImpl(Connection connection) 
	{
		super(connection);
	}

	public List<SelectionCollection> getAllDCListDao() throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllCircles();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Circles List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	
	public List<SelectionCollection> getAllDCListDaoForDTHAdmin() throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllCirclesForDTHAdmin();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Circles List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	
	public List<SelectionCollection> getTsmCircles(long id) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getTsmCircles(id);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Circles List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	
	

	public List<SelectionCollection> getAllAccountsUnderSingleAccountDao( String strAccountID) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllAccountsUnderSingleAccount(strAccountID,connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Account under a single account List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	
	public List<SelectionCollection> getParentDao( String strAccountID) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getParent(strAccountID,connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting parent List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}

	public List<SelectionCollection> getAllProductsSingleCircleDao(String strBusinessCat, String strCircleID) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllProductsSingleCircle(strBusinessCat, strCircleID);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Product in a single Circle List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	public List<SelectionCollection> getAllAccounts(String strAccountLevel) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn =DropDownUtility.getInstance().getAllAccounts(strAccountLevel);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Accounts For a account level  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	
	public List<SelectionCollection> getAllAccountsSingleCircle(String strAccountLevel, String strCircleID) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllAccountsSingleCircle(strAccountLevel, Integer.parseInt(strCircleID),connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Accounts For a Circle  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	public List<SelectionCollection> getAllAccountsMultipleCircle(String strAccountLevel, long accountId) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllAccountsMultipleCircle(strAccountLevel, (int)accountId,connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Accounts For a Circle  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	
	
	public List<SelectionCollection> getAllAccountsMultipleCircles(String strAccountLevel, String strCircleID) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllAccountsMultipleCircles(strAccountLevel, strCircleID,connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Accounts For multiple Circle  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}	
	
	public List<SelectionCollection> getAllAccountsUnderMultipleAccounts( String strAccountID) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllAccountsUnderMultipleAccounts(strAccountID,connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Account under Multiple account List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}

	public List<SelectionCollection> getAllCollectionTypes() throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllCollectionTypes();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Collection Types List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
		
	public List<DpProductCategoryDto> getProductCategoryLst() throws DAOException 
	{
		List<DpProductCategoryDto> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getProductCategoryLst();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Collection Types List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	public List<DpProductCategoryDto> getProductCategoryLst1() throws DAOException 
	{
		List<DpProductCategoryDto> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getProductCategoryLst1();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Collection Types List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	public List<DpProductCategoryDto> getProductCategoryLstReco() throws DAOException 
	{
		List<DpProductCategoryDto> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getProductCategoryLstReco();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Collection Types List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	
	
	public List<SelectionCollection> getProductCategoryLstAjax(String businessCat,String reportId) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getProductCategoryLstAjax(businessCat,reportId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Collection Types List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}

	public List<SelectionCollection> getAllDist(String selectedCircle, String selectedTSM,String accLevel,String loginId,String businessCat) throws DAOException {
		// TODO Auto-generated method stub
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllDist(selectedCircle,selectedTSM,accLevel,loginId,businessCat,connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Collection Types List  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	public List<SelectionCollection> getAllTSM(String selectedCircle , String businessCat,String accLevel,String loginId) throws DAOException {
		// TODO Auto-generated method stub
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllTSM(selectedCircle,businessCat,accLevel,loginId,connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getAllTSM ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}

	

	public List<SelectionCollection> getAllRet(String selectedFse, String distID) throws DAOException {
		// TODO Auto-generated method stub
List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllRet(selectedFse,distID, connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting retailer  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}

	public List<SelectionCollection> getAccountNames(String distID, String type) throws DAOException {
		// TODO Auto-generated method stub
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAccountNames(distID, type, connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting retailer  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;

	}
	public List getCircles(long id) throws DAOException {
		// TODO Auto-generated method stub
		List circles = null;
		
		try
		{
			circles = DropDownUtility.getInstance().getCircles(id, connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting circles  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return circles;

	}
	public List<SelectionCollection> getAllAccountsCircleAdmin(String strAccountLevel, long accountId,String strCircleID) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllAccountsCircleAdmin(strAccountLevel,accountId, strCircleID,connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All Accounts For multiple Circle  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	
	public List<SelectionCollection> getAllZones(Connection connection) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		
		try
		{
			listReturn = DropDownUtility.getInstance().getAllZones(connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All zones   ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
	public List<SelectionCollection> getAllCirclesAsperZone(Connection connection,String zone) throws DAOException 
	{
		List<SelectionCollection> listReturn = null;
		//logger.info("-- getAllCirclesAsperZone -- "+zone);
		try
		{
			listReturn = DropDownUtility.getInstance().getAllCirclesAsperZone(connection,zone);
			//logger.info("-- getAllCirclesAsperZone --Exit  "+zone);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured in getting All zones   ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listReturn;
	}
}
