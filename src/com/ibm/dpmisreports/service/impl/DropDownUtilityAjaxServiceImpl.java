package com.ibm.dpmisreports.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.dao.DropDownUtilityAjaxDao;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DropDownUtilityAjaxServiceImpl implements DropDownUtilityAjaxService
{
	Logger logger = Logger.getLogger(DropDownUtilityAjaxServiceImpl.class.getName());
	
	public List<SelectionCollection> getAllCircles() throws DPServiceException 
	{		
		logger.info("*********************getAllCircles starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllDCListDao();
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	public List<SelectionCollection> getAllCirclesForDTHAdmin() throws DPServiceException 
	{		
		logger.info("*********************getAllCircles starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllDCListDaoForDTHAdmin();
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	public List<SelectionCollection> getTsmCircles(long id) throws DPServiceException 
	{		
		logger.info("*********************getTsmCircles starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getTsmCircles(id);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	
	public List<SelectionCollection> getAllAccountsUnderSingleAccount(String strAccountID) throws DPServiceException 
	{		
		logger.info("*********************getAllAccountsUnderSingleAccount starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllAccountsUnderSingleAccountDao( strAccountID);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	public List<SelectionCollection> getParent(String strAccountID) throws DPServiceException 
	{		
		logger.info("*********************getParent starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getParentDao( strAccountID);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}

	public List<SelectionCollection> getAllProductsSingleCircle(String strBusinessCat, String strCircleID) throws DPServiceException 
	{		
		logger.info("*********************getAllProductsSingleCircle starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllProductsSingleCircleDao(strBusinessCat, strCircleID);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	public List<SelectionCollection> getAllAccounts(String strAccountLevel) throws DPServiceException 
	{		
		logger.info("*********************getAllProductsSingleCircle starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllAccounts(strAccountLevel);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	public List<SelectionCollection> getAllAccountsSingleCircle(String strAccountLevel, String strCircleID) throws DPServiceException 
	{		
		logger.info("*********************getAllAccountsSingleCircle starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllAccountsSingleCircle(strAccountLevel, strCircleID);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}

	public List<SelectionCollection> getAllAccountsMultipleCircle(String strAccountLevel, long accountId) throws DPServiceException 
	{		
		logger.info("*********************getAllAccountsMultipleCircle starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllAccountsMultipleCircle(strAccountLevel, accountId);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	
	public List<SelectionCollection> getAllAccountsMultipleCircles(String strAccountLevel, String strCircleID) throws DPServiceException 
	{		
		logger.info("*********************getAllAccountsMultipleCircles starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllAccountsMultipleCircles(strAccountLevel, strCircleID);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	public List<SelectionCollection> getAllAccountsUnderMultipleAccounts(String strAccountID) throws DPServiceException 
	{		
		logger.info("*********************getAllAccountsUnderMultipleAccounts starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllAccountsUnderMultipleAccounts(strAccountID);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}

	public List<SelectionCollection> getAllCollectionTypes() throws DPServiceException 
	{		
		logger.info("*********************getAllCollectionTypes starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getAllCollectionTypes();
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	public List<DpProductCategoryDto> getProductCategoryLst() throws DPServiceException 
	{		
		logger.info("*********************getAllCollectionTypes starts **************************************");
		
		Connection connection = null;
		List<DpProductCategoryDto> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getProductCategoryLst();
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	public List<DpProductCategoryDto> getProductCategoryLst1() throws DPServiceException 
	{		
		logger.info("*********************getAllCollectionTypes starts **************************************");
		
		Connection connection = null;
		List<DpProductCategoryDto> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getProductCategoryLst1();
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	public List<DpProductCategoryDto> getProductCategoryLstReco() throws DPServiceException 
	{		
		logger.info("*********************getAllCollectionTypes starts **************************************");
		
		Connection connection = null;
		List<DpProductCategoryDto> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getProductCategoryLstReco();
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	public List<SelectionCollection> getProductCategoryLstAjax(String businessCat , String reportId) throws DPServiceException 
	{		
		logger.info("*********************getAllCollectionTypes starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dcDamageStatusDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dcDamageStatusDao.getProductCategoryLstAjax(businessCat,reportId);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	public List<SelectionCollection> getAllTSM(String selectedCircle,String bussCat,String accLevel,String loginId) throws DPServiceException {
		// TODO Auto-generated method stub
// TODO Auto-generated method stub
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dropDownDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dropDownDao.getAllTSM(selectedCircle,bussCat, accLevel,loginId);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	public List<SelectionCollection> getAllDist(String selectedCircle, String selectedTSM,String accLevel,String loginId,String businessCat) throws DPServiceException {
		// TODO Auto-generated method stub
// TODO Auto-generated method stub
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dropDownDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dropDownDao.getAllDist(selectedCircle,selectedTSM,accLevel,loginId,businessCat);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}

	public List<SelectionCollection> getAllRet(String selectedFse, String distID) throws DPServiceException {
		// TODO Auto-generated method stub
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dropDownDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dropDownDao.getAllRet(selectedFse,distID);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}

	public List<SelectionCollection> getAccountNames(String distID, String type) throws DPServiceException {
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dropDownDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dropDownDao.getAccountNames(distID, type);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	//Added by Neetika
	
	public List  getCircles(long id) throws DPServiceException 
	{		
		logger.info("*********************get Circles starts **************************************");
		
		Connection connection = null;
		List listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao dropdao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  dropdao.getCircles(id);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	public List getAllAccountsCircleAdmin(String strAccountLevel, long accountId,String circlesr)throws DPServiceException
	{
			logger.info("*********************getAllAccountsCircleAdmin starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao ajaxDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  ajaxDao.getAllAccountsCircleAdmin(strAccountLevel, accountId,circlesr);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	

	public List<SelectionCollection> getAllZones() throws DPServiceException 
	{		
		logger.info("*********************getAllZones starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao ajaxDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  ajaxDao.getAllZones(connection);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
	public List<SelectionCollection> getAllCirclesAsperZone(String cval) throws DPServiceException 
	{		
		//logger.info("*********************getAllZones starts **************************************");
		
		Connection connection = null;
		List<SelectionCollection> listReturn = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			DropDownUtilityAjaxDao ajaxDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDropDownUtilityAjaxDaoConnection(connection);
			
			listReturn =  ajaxDao.getAllCirclesAsperZone(connection,cval);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	
}
