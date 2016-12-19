/*
 * Created on Aug 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.virtualization.recharge.dao;
import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.dto.CardMstrDto;
import com.ibm.dp.dto.DPProductDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.beans.DPCreateProductFormBean;
import com.ibm.virtualization.recharge.beans.DPEditWarrantyFormBean;
import com.ibm.virtualization.recharge.beans.DPViewProductFormBean;
import com.ibm.virtualization.recharge.beans.DPViewWarrantyFormBean;
import com.ibm.virtualization.recharge.dto.DPEditProductDTO;
import com.ibm.virtualization.recharge.dto.DPEditWarrantyDTO;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;
import com.ibm.virtualization.recharge.dto.DPViewWarrantyDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * @author vivek kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface DPMasterDAO {
	
	public int insert(DPCreateProductFormBean prodDPBean) throws DAOException;
	/*public void insertWebservice(DPCreateWebServiceInfo prodDPBean) throws DAOException;
	
	public void updateWebservice(DPCreateWebServiceInfo prodDPBean) throws DAOException;
	public ArrayList getRequisitionData(int roleid,String condition) throws DAOException;*/

	public ArrayList getBusinessCategoryDao(String userId);
	
	public ArrayList getUnitList();
	
	public ArrayList select(DPViewProductDTO dpvpDTO, int lowerBound, int upperBound) throws DAOException;
	
	//public DPCreateWebServiceInfo selectProduct() throws DAOException;
	
	
	public ArrayList getCircleListDao(String userId); 
	
	public ArrayList getTsmID(String userId); 
	
	public void update(DPViewProductFormBean prodDPBean) throws DAOException;
	
	public void updatewarranty(DPViewWarrantyFormBean prodDPBean) throws DAOException;
	
	//public DPViewProductFormBean getDataForEdit(int productid) throws DAOException;

	public ArrayList select1(DPViewWarrantyDTO dpvpDTO)throws DAOException;

//	public DPEditWarrantyFormBean getDataForWarranty(int prodId)throws DAOException;

	public DPEditProductDTO getProduct(int productid) throws DAOException;
	
	public DPEditWarrantyDTO getProductwarranty(int productid) throws DAOException;
	public void updateWarranty(DPEditWarrantyFormBean warrantyBean) throws DAOException;
	public DPViewWarrantyDTO getWarranty(String imeino,long userId) throws DAOException;
	public int getProductListCount(DPViewProductDTO dpvpDTO) throws DAOException;
//added by vishwas
	public int getProductListCount(CardMstrDto dpvpDTO) throws DAOException;
//end by vishwas	
// Check for unique Card Group	
	public int getExistingCardGroup(String card, int circleId)throws DAOException;
	
	public ArrayList getCardGroups(String circleId);
//	Added by Shilpa
	public List<DpProductTypeMasterDto> getProductTypeMaster() throws DAOException;
	
//	 Add by harbans on Reservation Obserbation 30th June
	public ArrayList getAllCommercialProducts(int circleId) throws DAOException;
	
//	Added by Shilpa for product category list
	public List<DpProductCategoryDto> getProductCategoryLst(String businessCategory) throws DAOException;

	public String checkProductName(String productCategory,int circleId)throws DAOException;
	
	public List<DPProductDto> getProductList(String dpProdType) throws DAOException;//aman
}




