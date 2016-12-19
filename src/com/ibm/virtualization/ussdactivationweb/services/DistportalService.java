/**************************************************************************
 * File     : DistportalService.java
 * Author   : Banita
 * Created  : Oct 6, 2008
 * Modified : Oct 6, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 6, 2008 	Banita	First Cut.
 * V0.2		Oct 6, 2008 	Banita	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.services;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.dao.DistportalServicesDaoImpl;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.services.dto.BusinessUserDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.RequesterDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * All the methods in this class call their respective methods in DAO Class It
 * contain all Distportal related methods.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/

public class DistportalService {

	private static Logger logger = Logger.getLogger(DistportalService.class
			.getName());

	/**
	 * This method getRequesterDetails() gets the details of requeter on the
	 * basis of requesterRegisterNumber
	 * 
	 * @param requesterRegisterNumber
	 *            object for fetching details
	 * @return businessUserDTO dto that has all details
	 */

	public BusinessUserDTO getRequesterDetails(String requesterRegisterNumber,String userType) {
		logger
		.debug("Entering into getRequesterDetails() of DistportalService");
		BusinessUserDTO businessUserDTO = null;
		if (requesterRegisterNumber.equals(null)
				|| ("").equals(requesterRegisterNumber) || ("").equals(userType) || userType.equals(null)) {
			businessUserDTO = new BusinessUserDTO();
			businessUserDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code1,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			businessUserDTO.setErrorMessage(Utility.getValueFromBundle(
					Constants.Reg_No_Blank,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		} else {
			try {
				DistportalServicesDaoImpl distportalServicesDaoImpl = new DistportalServicesDaoImpl();
				businessUserDTO = new BusinessUserDTO();
				businessUserDTO = distportalServicesDaoImpl
				.getRequesterDetails(requesterRegisterNumber,userType);
				logger
				.info("DistportalService.getRequesterDetails(): businessUserDTO = "
						+ businessUserDTO
						+ " for requester - "
						+ requesterRegisterNumber);
				if (businessUserDTO == null) {
					businessUserDTO = new BusinessUserDTO();
					businessUserDTO.setErrorCode(Utility.getValueFromBundle(
							Constants.Error_Code2,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
					businessUserDTO.setErrorMessage(Utility.getValueFromBundle(
							Constants.No_REQ_FOR_RGENO,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));

				}
			} catch (DAOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger.debug("Exiting from getRequesterDetails() of DistportalService");
		return businessUserDTO;
	}

	/**
	 * This method getCompleteRequesterDetails() gets the complete heirarchical
	 * details of the business user on the basis of registerNumber
	 * 
	 * @param requesterRegisterNumber
	 *            object for fetching complete heirarchical details
	 * @return RequesterDTO dto that has all details
	 */

//	public RequesterDTO getCompleteRequesterDetails(
//			String requesterRegisterNumber) {
//		logger
//		.debug("Entering into getCompleteRequesterDetails() of DistportalService");
//		ProdcatService prodService = new ProdcatService();
//		String activeCircle = "";
//		RequesterDTO requesterDTO = null;
//		if (requesterRegisterNumber.equals(null)
//				|| ("").equals(requesterRegisterNumber)) {
//			requesterDTO = new RequesterDTO();
//			requesterDTO.setErrorCode(Utility.getValueFromBundle(
//					Constants.Error_Code1,
//					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
//			requesterDTO.setErrorMessage(Utility.getValueFromBundle(
//					Constants.Reg_No_Blank,
//					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
//		} else {
//			try {
//				DistportalServicesDaoImpl distportalServicesDaoImpl = new DistportalServicesDaoImpl();
//				requesterDTO = new RequesterDTO();
//				requesterDTO = distportalServicesDaoImpl
//				.getCompleteRequesterDetails(requesterRegisterNumber);
//				if (requesterDTO != null) {
//					String circleCode = requesterDTO.getCircleCode();
//					if (circleCode != null) {
//						activeCircle = prodService.getActiveCircle(requesterDTO
//								.getCircleCode());
//						if (!activeCircle.equals("") && activeCircle != null) {
//							requesterDTO.setCircleCode(activeCircle);
//						} else {
//							requesterDTO = new RequesterDTO();
//							requesterDTO
//							.setErrorCode(Utility
//									.getValueFromBundle(
//											Constants.Error_Code2,
//											Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
//							requesterDTO
//							.setErrorMessage(Utility
//									.getValueFromBundle(
//											Constants.ACTIVE_CIRCLE,
//											Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
//						}
//					} else {
//						requesterDTO.setCircleCode(null);
//					}
//				}
//
//				logger
//				.info("DistportalService.getCompleteRequesterDetails(): businessUserDTO = "
//						+ requesterDTO
//						+ " for requester - "
//						+ requesterRegisterNumber);
//				if (requesterDTO == null) {
//					requesterDTO = new RequesterDTO();
//					requesterDTO.setErrorCode(Utility.getValueFromBundle(
//							Constants.Error_Code2,
//							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
//					requesterDTO.setErrorMessage(Utility.getValueFromBundle(
//							Constants.No_REQ_FOR_RGENO,
//							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
//
//				}
//			} catch (DAOException e) {
//				logger.error(e.getMessage(), e);
//			}
//		}
//		logger
//		.debug("Exiting from getCompleteRequesterDetails() of DistportalService");
//		return requesterDTO;
//	}
	
//when there are many users with same reg number	
	public RequesterDTO getCompleteRequesterDetails(
			String requesterRegisterNumber,String userType) {
		logger
		.debug("Entering into getCompleteRequesterDetails() of DistportalService");
		ProdcatService prodService = new ProdcatService();
		String activeCircle = "";
		RequesterDTO requesterDTO = null;
		if (requesterRegisterNumber.equals(null)
				|| ("").equals(requesterRegisterNumber) || ("").equals(userType) || userType.equals(null)) {
			requesterDTO = new RequesterDTO();
			requesterDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code1,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			requesterDTO.setErrorMessage(Utility.getValueFromBundle(
					Constants.Reg_No_Blank,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		} else {
			try {
				DistportalServicesDaoImpl distportalServicesDaoImpl = new DistportalServicesDaoImpl();
				requesterDTO = new RequesterDTO();
				requesterDTO = distportalServicesDaoImpl
				.getCompleteRequesterDetails(requesterRegisterNumber,userType);
				if (requesterDTO != null) {
					String circleCode = requesterDTO.getCircleCode();
					if (circleCode != null) {
						activeCircle = prodService.getActiveCircle(requesterDTO
								.getCircleCode());
						if (!activeCircle.equals("") && activeCircle != null) {
							requesterDTO.setCircleCode(activeCircle);
						} else {
							requesterDTO = new RequesterDTO();
							requesterDTO
							.setErrorCode(Utility
									.getValueFromBundle(
											Constants.Error_Code2,
											Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
							requesterDTO
							.setErrorMessage(Utility
									.getValueFromBundle(
											Constants.ACTIVE_CIRCLE,
											Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
						}
					} else {
						requesterDTO.setCircleCode(null);
					}
				}

				logger
				.info("DistportalService.getCompleteRequesterDetails(): businessUserDTO = "
						+ requesterDTO
						+ " for requester - "
						+ requesterRegisterNumber);
				if (requesterDTO == null) {
					requesterDTO = new RequesterDTO();
					requesterDTO.setErrorCode(Utility.getValueFromBundle(
							Constants.Error_Code2,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
					requesterDTO.setErrorMessage(Utility.getValueFromBundle(
							Constants.No_REQ_FOR_RGENO,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));

				}
			} catch (DAOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger
		.debug("Exiting from getCompleteRequesterDetails() of DistportalService");
		return requesterDTO;
	}

	/**
	 * This method requesterDetails() gets the business user details by data id
	 * 
	 * @param dataId
	 *            object for fetching details
	 * @return businessUserDTO dto that has all details
	 */

	public BusinessUserDTO getBusinessUserDetailsById(String parentId) {
		logger
		.debug("Entering into getBusinessUserDetailsById() of DistportalService");
		BusinessUserDTO businessUserDTO = null;
		if (parentId.equals(null) || ("").equals(parentId)) {
			businessUserDTO = new BusinessUserDTO();
			businessUserDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code1,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			businessUserDTO.setErrorMessage(Utility.getValueFromBundle(
					Constants.ParentId_Blank,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		} else {
			try {
				DistportalServicesDaoImpl distportalServicesDaoImpl = new DistportalServicesDaoImpl();
				businessUserDTO = new BusinessUserDTO();
				businessUserDTO = distportalServicesDaoImpl
				.getBusinessUserDetailsById(Integer.parseInt(parentId));
				logger
				.info("DistportalService.getBusinessUserDetailsById(): businessUserDTO = "
						+ businessUserDTO
						+ " for requester - "
						+ parentId);
				if (businessUserDTO == null) {
					businessUserDTO = new BusinessUserDTO();
					businessUserDTO.setErrorCode(Utility.getValueFromBundle(
							Constants.Error_Code2,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
					businessUserDTO.setErrorMessage(Utility.getValueFromBundle(
							Constants.No_REQ_FOR_DATAID,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));

				}
			} catch (DAOException e) {
				logger.error(e.getMessage(), e);

			}
		}
		logger
		.debug("Exiting from getBusinessUserDetailsById() of DistportalService");
		return businessUserDTO;
	}

	/**
	 * This method getLatestBusinessUserCount() returns the total count of the
	 * business users
	 * 
	 * @param modifiedFromDate:parameter
	 *            to filter the data
	 * @param modifiedtoDate:parameter
	 *            to filter the data
	 * @return locCount:total records found
	 */

	public int getLatestBusinessUserCount(String modifiedFromDate,
			String modifiedtoDate) {
		int locCount = 0;
		logger
		.debug("Entering into getLatestBusinessUserCount() of DistportalService");
		try {
			DistportalServicesDaoImpl distportalServicesDaoImpl = new DistportalServicesDaoImpl();
			locCount = distportalServicesDaoImpl.getLatestBusinessUserCount(
					modifiedFromDate, modifiedtoDate);
			logger.info("DistportalService.getLatestBusinessUserCount()"+locCount);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}
		logger
		.debug("Exiting from getLatestBusinessUserCount() of DistportalService");
		return locCount;
	}

	/**
	 * This method getBusinessUserDetails() returns the total business users
	 * 
	 * @param modifiedFromDate:parameter
	 *            to filter the data
	 * @param modifiedtoDate:parameter
	 *            to filter the data
	 * @param intLb:lower
	 *            bound set to fetch the data
	 * @param intUb:upper
	 *            bound set to fetch the data
	 * @return RequesterDTO:that contails all the business users fetched
	 */
	public RequesterDTO getBusinessUserDetails(String modifiedFromDate,
			String modifiedtoDate, int intLb, int intUb) {
		logger
		.debug("Entering into getBusinessUserDetails() of DistportalService");
		RequesterDTO requesterDTO = null;
		if (modifiedFromDate.equals(null) || ("").equals(modifiedFromDate)
				|| modifiedtoDate.equals(null) || ("").equals(modifiedtoDate)) {
			requesterDTO = new RequesterDTO();
			requesterDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code1,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			requesterDTO.setErrorMessage(Utility.getValueFromBundle(
					Constants.Date_Blank,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		} else {
			try {
				DistportalServicesDaoImpl distportalServicesDaoImpl = new DistportalServicesDaoImpl();
				requesterDTO = new RequesterDTO();
				requesterDTO = distportalServicesDaoImpl
				.getBusinessUserDetails(modifiedFromDate,
						modifiedtoDate, intLb, intUb);
				logger
				.info("DistportalService.getBusinessUserDetails(): requesterDTO = "
						+ requesterDTO);
				if (requesterDTO == null) {
					requesterDTO = new RequesterDTO();
					requesterDTO.setErrorCode(Utility.getValueFromBundle(
							Constants.Error_Code2,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
					requesterDTO.setErrorMessage(Utility.getValueFromBundle(
							Constants.No_BizUser,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
				}
			} catch (DAOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger
		.debug("Exiting from getBusinessUserDetails() of DistportalService");
		return requesterDTO;
	}
}
