package com.ibm.virtualization.recharge.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPCreateAccountDao;
import com.ibm.dp.dao.DPDcCreationDao;
import com.ibm.dp.dto.CardMstrDto;
import com.ibm.dp.dto.DPProductDto;
import com.ibm.dp.dto.DpDcCreationDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.beans.DPCreateProductFormBean;
import com.ibm.virtualization.recharge.beans.DPCreateWebServiceInfo;
import com.ibm.virtualization.recharge.beans.DPEditProductFormBean;
import com.ibm.virtualization.recharge.beans.DPEditWarrantyFormBean;
import com.ibm.virtualization.recharge.beans.DPViewProductFormBean;
import com.ibm.virtualization.recharge.beans.DPViewWarrantyFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.DPMasterDAO;
import com.ibm.virtualization.recharge.dao.rdbms.DPMasterDAOImplDB2;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.DPEditProductDTO;
import com.ibm.virtualization.recharge.dto.DPEditWarrantyDTO;
import com.ibm.virtualization.recharge.dto.DPUserBean;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;
import com.ibm.virtualization.recharge.dto.DPViewWarrantyDTO;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.DPMasterService;

/**
 * @author Vivek
 * @version 2.0
 * 
 */

public class DPMasterServiceImpl implements DPMasterService {
	private Logger logger = Logger.getLogger(DPMasterServiceImpl.class
			.getName());

	public String insert(DPCreateProductFormBean prodDPBean)
			throws DAOException, NumberFormatException,
			VirtualizationServiceException {

		Connection connection = null;

		String message = "success";
		try {
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO masterDAO = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getDPMasterDAO(connection);
			int productId =-1;// masterDAO.getExistingCardGroup(prodDPBean.getCardgroup(), prodDPBean.getCircleid());
			if(productId != -1){
				logger.info("Card Group Already Exists in the System.");
				throw new VirtualizationServiceException("Error.Card.Group");
			}
			masterDAO.insert(prodDPBean);
//			int inLength=0;
//			inLength = masterDAO.insert(prodDPBean);
//			if(inLength != 0){
//				message = "success";
//			}
//			else throw new VirtualizationServiceException("exception.insert");
		} 
		catch(DAOException dex)
		{
			System.out.println("***********Inside DAOException handling in Service Impl class DAOException**********");
			throw new VirtualizationServiceException(dex.getMessage());
		}
		
		
		catch (Exception e) {
			System.out.println("***********Inside exception handling in Service Impl class Exception**********");
			e.printStackTrace();
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getMasterist():"
						+ e.getMessage());
				message = "failure";
				logger.error("" + e.fillInStackTrace());
				new VirtualizationServiceException(e.getMessage());
				} else {
				logger.error("Exception occured in getMasterList():"
						+ e.getMessage());
				message = "failure";
				throw new VirtualizationServiceException(e.getMessage());
			}
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DPMasterService#insertWebservice(com.ibm.virtualization.recharge.beans.DPCreateWebServiceInfo)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DPMasterService#insertWebservice(com.ibm.virtualization.recharge.beans.DPCreateWebServiceInfo)
	 */
	/*
	 * public DPCreateWebServiceInfo insertWebservice(DPCreateWebServiceInfo
	 * prodDPBean) throws DAOException, NumberFormatException,
	 * VirtualizationServiceException {
	 * 
	 * Connection connection = null;
	 * 
	 * 
	 * DPCreateWebServiceInfo message = new DPCreateWebServiceInfo(); try{
	 * connection = DBConnectionManager.getDBConnection(); DPMasterDAO masterDAO =
	 * DAOFactory.getDAOFactory(
	 * Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPMasterDAO(connection);
	 * System.err.println("here in impl");
	 * masterDAO.insertWebservice(prodDPBean);
	 *  } catch (Exception e) { if (e instanceof DAOException) {
	 * logger.error("DAOException occured in getMasterist():" + e.getMessage());
	 * 
	 * logger.error(""+e.fillInStackTrace()); throw new
	 * DAOException(e.getMessage()); } else { logger.error("Exception occured in
	 * getMasterList():" + e.getMessage()); }
	 *  } return message; }
	 */
	public ArrayList getMasterList(DPUserBean dpUserBean, String master,
			String condition) throws DAOException {
		ArrayList masterList = null;

		try {
			logger.info("masterList-----------" + masterList.size());
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getMasterList():"
						+ e.getMessage());
				throw new DAOException(e.getMessage());
			} else {
				logger.error("Exception occured in getMasterList():"
						+ e.getMessage());
			}
		}
		return masterList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DPMasterService#getBusinessCategory(java.lang.String)
	 *      This method is for getting Bussiness Category list from the table.
	 */
	public ArrayList getBusinessCategory(String userId) {
		java.sql.Connection connection = null;
		ArrayList bcAList = null;
		try {

			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO dpmdao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getBusinessCategoryDao(connection);
			bcAList = dpmdao.getBusinessCategoryDao(userId);
			if (bcAList.size() == 0) {
				logger.error(" No Business Category present ! ");
			}

		} catch (Exception ex) {
			logger
					.error("ERROR IN FETCHING BUSINESS CATEGORY LIST [getBusinessCategory(String userId)] function of  DPPurchaseOrderServiceImpl ");
		}

		logger
				.info(" **: EXIT from getBusinesCategory -> DPMasterServiceImpl class :** ");
		return bcAList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DPMasterService#select(com.ibm.virtualization.recharge.dto.DPViewProductDTO)
	 */
	public ArrayList select(DPViewProductDTO dpvpDTO, int lowerBound, int upperBound)
			throws NumberFormatException, VirtualizationServiceException,
			DAOException {
		Connection connection = null;
		ArrayList productList = new ArrayList();
		try {
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO masterDAO = DAOFactory
					.getDAOFactory(
							Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getDPMasterDAO(connection);
			productList = masterDAO.select(dpvpDTO, lowerBound, upperBound);

		} catch (Exception e) {

			if (e instanceof DAOException) {
				logger
						.error("DAOException occured in view product list services:"
								+ e.getMessage());
				logger.error("" + e.fillInStackTrace());
				throw new DAOException(e.getMessage());

			} else {
				logger.error("Exception occured in getMasterList():"
						+ e.getMessage());
			}
			return productList;
		}

		return productList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DPMasterService#getCircleID(java.lang.String)
	 *      This method is for getting Circle list from the table
	 * 
	 */
	public ArrayList getCircleID(String userId) {
		java.sql.Connection connection = null;
		ArrayList ciList = null;
		try {

			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO dpmdao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getBusinessCategoryDao(connection);
			ciList = dpmdao.getCircleListDao(userId);
			if (ciList.size() == 0) {
				logger.error(" No Circle Category present ! ");
			}
		} catch (Exception ex) {
			logger.error("ERROR IN FETCHING CIRCLE LIST ");
		}
		return ciList;
	}
	public ArrayList getTsmID(String userId) {
		java.sql.Connection connection = null;
		ArrayList ciList = null;
		try {

			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO dpmdao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getBusinessCategoryDao(connection);
			ciList = dpmdao.getTsmID(userId);
			if (ciList.size() == 0) {
				logger.error(" No Circle Category present ! ");
			}
		} catch (Exception ex) {
			logger.error("ERROR IN FETCHING CIRCLE LIST ");
		}
		return ciList;
	}
	public ArrayList getUnitList() {
		java.sql.Connection connection = null;
		ArrayList ciList = null;
		try {

			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO dpmdao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getBusinessCategoryDao(connection);
			ciList = dpmdao.getUnitList();
			if (ciList.size() == 0) {
				logger.error(" No Circle Category present ! ");
			}
		} catch (Exception ex) {
			logger.error("ERROR IN FETCHING CIRCLE LIST ");
		}
		return ciList;
	}

	public DPEditProductFormBean getDataForEdit(int productid)
			throws DAOException {
		java.sql.Connection connection = null;
		DPEditProductFormBean piList = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO dpmdao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getBusinessCategoryDao(connection);

			// piList = dpmdao.getDataForEdit(productid);
		} catch (Exception ex) {
			logger.error("ERROR IN FETCHING CIRCLE LIST ");
		}
		return piList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DPMasterService#edit(com.ibm.virtualization.recharge.beans.DPViewProductFormBean)
	 */
	// public String edit(DPViewProductFormBean prodDPBean)
	public String edit(DPViewProductFormBean prodDPBean)

	throws DAOException, NumberFormatException, VirtualizationServiceException 
	{
		Connection connection = null;
		String message = "success";
		try {
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO masterDAO = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getDPMasterDAO(connection);
			
			/*
			int productId = masterDAO.getExistingCardGroup(prodDPBean.getCardgroup(), prodDPBean.getCircleId());
			if(productId != -1 && productId != prodDPBean.getProdId()){
				logger.info("Card Group Already Exists in the System.");
				throw new VirtualizationServiceException("Error.Card.Group");
			}*/
			masterDAO.update(prodDPBean);
		}catch(DAOException dex)
		{
			System.out.println("***********Inside DAOException handling in Service Impl class DAOException**********");
			throw new VirtualizationServiceException(dex.getMessage());
		} 
		
		catch (Exception e) {
			System.out.println("***********Inside exception handling in Service Impl class Exception**********");
			e.printStackTrace();
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getMasterist():"
						+ e.getMessage());
				message = "failure";
				logger.error("" + e.fillInStackTrace());
				new VirtualizationServiceException(e.getMessage());
				} else {
				logger.error("Exception occured in getMasterList():"
						+ e.getMessage());
				message = "failure";
				throw new VirtualizationServiceException(e.getMessage());
			}
		}
	
		return message;
	}

	public ArrayList select1(DPViewWarrantyDTO dpvpDTO)
			throws NumberFormatException, VirtualizationServiceException,
			DAOException {
		Connection connection = null;
		ArrayList productList = new ArrayList();
		DPMasterDAO viewdao = new DPMasterDAOImplDB2(connection);
		String message = "success";
		try {
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO masterDAO = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.select1(connection);
			productList = viewdao.select1(dpvpDTO);
		} catch (Exception e) {

			if (e instanceof DAOException) {
				logger
						.error("DAOException occured in view warranty list services:"
								+ e.getMessage());
				message = "failure";
				logger.error("" + e.fillInStackTrace());
				throw new DAOException(e.getMessage());

			} else {
				logger.error("Exception occured in getMasterList():"
						+ e.getMessage());
			}
			return productList;
		}
		return productList;
	}

	public String editwarranty(DPViewWarrantyFormBean prodDPBean)
			throws NumberFormatException, VirtualizationServiceException,
			DAOException {

		Connection connection = null;
		String message = "successful";
		try {
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO masterDAO = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getDPMasterDAO(connection);
			masterDAO.updatewarranty(prodDPBean);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException in Updation:" + e.getMessage());
				message = "failure";
				logger.error("" + e.fillInStackTrace());
				throw new DAOException(e.getMessage());

			} else {
				logger.error("Exception occured in Updation:" + e.getMessage());
			}

			message = "failure";
		}

		return message;
	}

	/*
	 * public DPEditWarrantyFormBean getDataForWarranty(int prodId) throws
	 * DAOException { java.sql.Connection connection = null;
	 * DPEditWarrantyFormBean piList = null; try { connection =
	 * DBConnectionManager.getDBConnection(); DPMasterDAO dpmdao = DAOFactory
	 * .getDAOFactory( Integer .parseInt(ResourceReader
	 * .getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getWarrantyDao(connection);
	 * 
	 * piList = dpmdao.getDataForWarranty(prodId); } catch (Exception ex) {
	 * logger .error("ERROR IN FETCHING CIRCLE LIST "); } return piList;
	 *  }
	 */
	public DPEditProductDTO edit(int productid)
			throws VirtualizationServiceException {
		logger.info("Starting in service Impl productid" + productid);
		Connection connection = null;
		DPEditProductDTO product = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO productDao = DAOFactory
					.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewProductDAO(connection);
			product = productDao.getProduct(productid);
			if (product == null) {
				logger.error("  Product Not found with Product Id = "
						+ productid);
				throw new VirtualizationServiceException(
						ExceptionCode.Product.ERROR_PRODUCT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
			logger.error("Exception occured : Message : " + de.getMessage());
			de.printStackTrace();
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}

		logger
				.info(" Executed :::: Successfully retreived Product Information  ");
		return product;
	}

	public DPEditWarrantyDTO editproductwarranty(int productid)
			throws VirtualizationServiceException {
		logger.info("Starting in service Impl productid warranty" + productid);
		Connection connection = null;
		DPEditWarrantyDTO product = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO productDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewProductDAO(connection);
			product = productDao.getProductwarranty(productid);
			if (product == null) {
				logger.error("  Product Not found with Product Id = "
						+ productid);
				throw new VirtualizationServiceException(
						ExceptionCode.Product.ERROR_PRODUCT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
			logger.error("Exception occured : Message : " + de.getMessage());
			de.printStackTrace();
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}

		logger
				.info(" Executed :::: Successfully retreived Product Information  ");
		return product;
	}

	public String updateWarranty(DPEditWarrantyFormBean warrantyBean)
			throws NumberFormatException, VirtualizationServiceException,
			DAOException {
		{
			Connection connection = null;
			String message = "success";
			try {
				connection = DBConnectionManager.getDBConnection();
				DPMasterDAO masterDAO = DAOFactory
						.getDAOFactory(
								Integer
										.parseInt(ResourceReader
												.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
						.getDPMasterDAO(connection);
				masterDAO.updateWarranty(warrantyBean);
			} catch (Exception e) {
				if (e instanceof DAOException) {
					logger.error("DAOException in Updation:" + e.getMessage());
					message = "failure";
					logger.error("" + e.fillInStackTrace());
					throw new DAOException(e.getMessage());
				} else {
					logger.error("Exception occured in Updation:"
							+ e.getMessage());
				}
				message = "failure";
			} finally {
				DBConnectionManager.releaseResources(connection);
			}
			return message;
		}

	}

	public  List<SelectionCollection> getAllCircles() throws Exception
	{
		Connection connection = null;
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		
		try {
			/* get the database connection */
			listReturn=DropDownUtility.getInstance().getAllCircles();
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				return listReturn;
			}
	}
	public DPViewWarrantyDTO editWarranty(String imeino, long userId)
			throws VirtualizationServiceException {
		logger.info("Starting in service Impl productid" + imeino);
		Connection connection = null;
		DPViewWarrantyDTO warranty = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO warrantyDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getWarranty(connection);
			warranty = warrantyDao.getWarranty(imeino, userId);
		} catch (DAOException de) {
			logger.error("Exception occured : Message : " + de.getMessage());
			de.printStackTrace();
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info(" Executed :::: Successfully retreived Product Information  ");
		return warranty;
	}
	
	public int getProductListCount(DPViewProductDTO dpvpDTO)throws  NumberFormatException, VirtualizationServiceException,DAOException{
		logger.info("Started...getUsersCountList()");
		/* get sessionContext */
		Connection connection = null;
		int noOfPages = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO masterDAO = DAOFactory
			.getDAOFactory(
					Integer
							.parseInt(ResourceReader
									.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDPMasterDAO(connection);
			if (null != dpvpDTO) {
				// Check if user type is Internal
				noOfPages = masterDAO.getProductListCount(dpvpDTO);
				}
			} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While counting number of rows" + noOfPages);
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: successfully count no of rows " + noOfPages);
		return noOfPages;
	}
	//added by vishwas
	public int getProductListCount(CardMstrDto crdmDTO)throws  NumberFormatException, VirtualizationServiceException,DAOException{
		logger.info("Started...getUsersCountList()");
		/* get sessionContext */
		Connection connection = null;
		int noOfPages = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO masterDAO = DAOFactory
			.getDAOFactory(
					Integer
							.parseInt(ResourceReader
									.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDPMasterDAO(connection);
			if (null != crdmDTO) {
				// Check if user type is Internal
				noOfPages = masterDAO.getProductListCount(crdmDTO);
				}
			} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While counting number of rows" + noOfPages);
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: successfully count no of rows " + noOfPages);
		return noOfPages;
	}

	
	//end by vishwas
	public ArrayList getCardGroups(String circleId){
		java.sql.Connection connection = null;
		ArrayList cgList = null;
		try {

			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO dpmdao = DAOFactory	.getDAOFactory(
							Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCardGroupDao(connection);
			cgList = dpmdao.getCardGroups(circleId);
			if (cgList.size() == 0) {
				logger.error(" No Business Category present ! ");
			}

		} catch (Exception ex) {
			logger
					.error("ERROR IN FETCHING BUSINESS CATEGORY LIST [getBusinessCategory(String userId)] function of  DPPurchaseOrderServiceImpl ");
		}

		logger
				.info(" **: EXIT from getBusinesCategory -> DPMasterServiceImpl class :** ");
		return cgList;
		
	}

	public List<DpProductTypeMasterDto> getProductTypeMaster() throws DPServiceException
	{
		logger.info("********************** getProductTypeMaster() Service Impl**************************************");
		Connection connection = null;
		List<DpProductTypeMasterDto> dcProductListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			
			DPMasterDAO masterDAO = DAOFactory
			.getDAOFactory(
					Integer
							.parseInt(ResourceReader
									.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDPMasterDAO(connection);
			
			dcProductListDTO =  masterDAO.getProductTypeMaster();
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
		return dcProductListDTO;
		
		
	}
	
	
	public List<DpProductTypeMasterDto> getAllCommercialProducts(int circleId) throws DPServiceException
	{
		logger.info("********************** getAllCommercialProducts() Service Impl**************************************");
		Connection connection = null;
		List<DpProductTypeMasterDto> reverseProductListDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			
			DPMasterDAO masterDAO = DAOFactory
			.getDAOFactory(
					Integer
							.parseInt(ResourceReader
									.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDPMasterDAO(connection);
			
			reverseProductListDTO =  masterDAO.getAllCommercialProducts(circleId);
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
		return reverseProductListDTO;
		
		
	}
	
	//Added by Shilpa Khanna for product category list
	
	public List<DpProductCategoryDto> getProductCategoryLst(String businessCategory) throws DPServiceException
	{
		logger.info("********************** getProductCategoryLst() Service Impl**************************************");
		Connection connection = null;
		List<DpProductCategoryDto> dcProductCategDTO = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			
			DPMasterDAO masterDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDPMasterDAO(connection);
			
			dcProductCategDTO =  masterDAO.getProductCategoryLst(businessCategory);
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
		return dcProductCategDTO;
		
		
	}
	//Added by sugandha to check there is only one parentproduct of a circle 

	public String checkProductName(String productCategory,int circleId) throws DPServiceException {
		logger.info("********************** getProductCategoryLst() Service Impl**************************************");
		Connection connection = null;
		String strReturn = "";
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPMasterDAO masterDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPMasterDAO(connection);
			strReturn =  masterDAO.checkProductName(productCategory,circleId);
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
		return strReturn;
	}
	//aman
	
	public List<DPProductDto> getProductList(String dpProdType) throws DPServiceException
	{
		
		logger.info("********************** getProductList() Service Impl**************************************");
		Connection connection = null;
		List<DPProductDto> dpProductDto = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			
			DPMasterDAO masterDAO = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDPMasterDAO(connection);
			
			dpProductDto =  masterDAO.getProductList(dpProdType);
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
		return dpProductDto;
		
		
	}
}
