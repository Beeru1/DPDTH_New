package com.ibm.dpmisreports.dao;

import java.sql.Connection;
import java.util.List;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.exception.DAOException;

public interface DropDownUtilityAjaxDao 
{

	List<SelectionCollection> getAllDCListDao()throws DAOException;
	
	List<SelectionCollection> getAllDCListDaoForDTHAdmin()throws DAOException;
	
	List<SelectionCollection> getTsmCircles(long id)throws DAOException;
	

	List<SelectionCollection> getAllAccountsUnderSingleAccountDao( String strAccountID)throws DAOException;
	
	List<SelectionCollection> getParentDao( String strAccountID)throws DAOException;

	List<SelectionCollection> getAllProductsSingleCircleDao(String strBusinessCat, String strCircleID)throws DAOException;
	
	List<SelectionCollection> getAllAccounts(String strAccountLevel)throws DAOException;
	
	List<SelectionCollection> getAllAccountsSingleCircle(String strAccountLevel, String strCircleID) throws DAOException;
	
	List<SelectionCollection> getAllAccountsMultipleCircle(String strAccountLevel, long accountId) throws DAOException;
	

	List<SelectionCollection> getAllAccountsMultipleCircles(String strAccountLevel, String strCircleID) throws DAOException;
	
	List<SelectionCollection> getAllAccountsUnderMultipleAccounts(String strAccountID)throws DAOException;

	List<SelectionCollection> getAllCollectionTypes()throws DAOException;
	
	List<DpProductCategoryDto> getProductCategoryLst() throws DAOException;
	
	List<DpProductCategoryDto> getProductCategoryLst1() throws DAOException;
	
	List<DpProductCategoryDto> getProductCategoryLstReco() throws DAOException;
	
	
	List<SelectionCollection> getProductCategoryLstAjax(String businessCat,String reportId) throws DAOException;

	List<SelectionCollection> getAllDist(String selectedCircle, String selectedTSM,String accLevel,String loginId,String businessCat) throws DAOException;
	List<SelectionCollection> getAllTSM(String selectedCircle , String businessCat,String accLevel,String loginId) throws DAOException;

	List<SelectionCollection> getAllRet(String selectedFse, String distID) throws DAOException;

	List<SelectionCollection> getAccountNames(String distID, String type) throws DAOException;
	List getCircles(long id) throws DAOException;
	List getAllAccountsCircleAdmin(String strAccountLevel, long accountId,String circlesr)throws DAOException;
	// Added by NEetika for BFR 3 on 31-July 2014
	List<SelectionCollection>  getAllZones(Connection connection) throws Exception ;
	List<SelectionCollection>  getAllCirclesAsperZone(Connection connection,String zone) throws Exception ;

}
