package com.ibm.virtualization.recharge.service;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.dto.CardMstrDto;
import com.ibm.dp.dto.DPProductDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.beans.DPCreateProductFormBean;
import com.ibm.virtualization.recharge.beans.DPCreateWebServiceInfo;
import com.ibm.virtualization.recharge.beans.DPEditProductFormBean;
import com.ibm.virtualization.recharge.beans.DPEditWarrantyFormBean;
import com.ibm.virtualization.recharge.beans.DPViewProductFormBean;
import com.ibm.virtualization.recharge.beans.DPViewWarrantyFormBean;
import com.ibm.virtualization.recharge.dto.DPEditProductDTO;
import com.ibm.virtualization.recharge.dto.DPEditWarrantyDTO;
import com.ibm.virtualization.recharge.dto.DPUserBean;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;
import com.ibm.virtualization.recharge.dto.DPViewWarrantyDTO;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;




/**
 * @author vivek kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface DPMasterService {

	
		public ArrayList getMasterList(DPUserBean dpUserBean,String master,String condition) throws DAOException,VirtualizationServiceException;
		
		public String insert(DPCreateProductFormBean prodDPBean)throws  NumberFormatException, VirtualizationServiceException,DAOException;
		//public DPCreateWebServiceInfo insertWebservice(DPCreateWebServiceInfo prodDPBean)throws  NumberFormatException, VirtualizationServiceException,DAOException;
		
		public  List<SelectionCollection> getAllCircles() throws Exception;
		
		public ArrayList select(DPViewProductDTO dpvpDTO, int lowerBound, int upperBound)throws  NumberFormatException, VirtualizationServiceException,DAOException;
		
		public String edit (DPViewProductFormBean prodDPBean)throws  NumberFormatException, VirtualizationServiceException,DAOException;
		/**
		 * @param Id
		 * @param cond
		 * @param cond2
		 * @param warehouseId
		 * @param object
		 * @return
		 */

		
		public ArrayList getBusinessCategory(String userId);
		
		public ArrayList getCircleID(String userId);
		
		public ArrayList getTsmID(String userId);
		
		public ArrayList getUnitList();
		
		public DPEditProductFormBean getDataForEdit(int productid) throws DAOException;
		
		public ArrayList select1(DPViewWarrantyDTO dpvpDTO)throws  NumberFormatException, VirtualizationServiceException,DAOException;

		//public DPEditWarrantyFormBean getDataForWarranty(int prodId) throws DAOException;

		public String editwarranty(DPViewWarrantyFormBean productFormBean)throws  NumberFormatException, VirtualizationServiceException,DAOException;

		public DPEditProductDTO edit(int productid)
		throws VirtualizationServiceException;
		public DPViewWarrantyDTO editWarranty(String imeino,long userId) throws VirtualizationServiceException;
		
		
		public DPEditWarrantyDTO editproductwarranty(int productid)
		throws VirtualizationServiceException;
		public String updateWarranty (DPEditWarrantyFormBean warrantyBean)throws  NumberFormatException, VirtualizationServiceException,DAOException;
		
		public int getProductListCount(DPViewProductDTO dpvpDTO)throws  NumberFormatException, VirtualizationServiceException,DAOException;
		//added by vishwas
		public int getProductListCount(CardMstrDto dpvpDTO)throws  NumberFormatException, VirtualizationServiceException,DAOException;
		//end by vishwas
		public ArrayList getCardGroups(String circleId);
		//Added by Shilpa
		public List<DpProductTypeMasterDto> getProductTypeMaster() throws DPServiceException;
		
//		 Add by harbans on Reservation Obserbation 30th June
		public List<DpProductTypeMasterDto> getAllCommercialProducts(int circleId) throws DPServiceException;
		
		//Added by Shilpa
		public List<DpProductCategoryDto> getProductCategoryLst(String businessCategory) throws DPServiceException;
		//Added by Sugandha
		public String checkProductName(String productCategory, int circleId)throws DPServiceException;
		//aman
		
		public List<DPProductDto> getProductList(String dpProdType) throws DPServiceException;
}
