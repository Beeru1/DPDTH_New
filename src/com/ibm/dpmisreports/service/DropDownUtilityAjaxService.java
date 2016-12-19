package com.ibm.dpmisreports.service;

import java.util.List;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;

public interface DropDownUtilityAjaxService 
{

	List<SelectionCollection> getAllCircles()throws DPServiceException;
	
	List<SelectionCollection> getAllCirclesForDTHAdmin()throws DPServiceException;
	
	List<SelectionCollection> getTsmCircles(long id)throws DPServiceException;
	

	List<SelectionCollection> getAllAccountsUnderSingleAccount(String strAccountID)throws DPServiceException;

	List<SelectionCollection> getAllProductsSingleCircle(String strBusinessCat, String strCircleID)throws DPServiceException;

	List<SelectionCollection> getAllAccounts(String strAccountLevel)throws DPServiceException;
	
	List<SelectionCollection> getAllAccountsSingleCircle(String strAccountLevel, String strCircleID)throws DPServiceException;
	
	List<SelectionCollection> getAllAccountsMultipleCircle(String strAccountLevel, long accountId)throws DPServiceException;
	
	
	List<SelectionCollection> getParent(String strAccountID)throws DPServiceException;

	List<SelectionCollection> getAllAccountsMultipleCircles(String strAccountLevel, String strCircleID)throws DPServiceException;
	
	List<SelectionCollection> getAllAccountsUnderMultipleAccounts(String strAccountID)throws DPServiceException;

	List<SelectionCollection> getAllCollectionTypes()throws DPServiceException;
	
	List<DpProductCategoryDto> getProductCategoryLst() throws DPServiceException;
	
	List<DpProductCategoryDto> getProductCategoryLst1() throws DPServiceException;
	
	List<DpProductCategoryDto> getProductCategoryLstReco() throws DPServiceException;
	
	
	List<SelectionCollection> getProductCategoryLstAjax(String businessCat,String reportId) throws DPServiceException;
	

	List<SelectionCollection> getAllTSM(String selectedCircle, String businessCat,String accLevel,String loginId) throws DPServiceException;
	
	List<SelectionCollection> getAllDist(String selectedCircle, String selectedTSM,String accLevel,String loginId,String businessCat) throws DPServiceException;

	List<SelectionCollection> getAllRet(String selectedFse, String distID) throws DPServiceException;
	
	List<SelectionCollection> getAccountNames(String distID, String type) throws DPServiceException;

	List getCircles(long id) throws DPServiceException;
	List getAllAccountsCircleAdmin(String strAccountLevel, long accountId,String circlesr)throws DPServiceException;
	
	List<SelectionCollection> getAllZones()throws DPServiceException;
	List<SelectionCollection> getAllCirclesAsperZone(String cval)throws DPServiceException;
	
}
