package com.ibm.dp.webServices.local;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.dto.WarehouseMstFrmBotreeDTO;
import com.ibm.dp.service.WarehouseMstFrmBotreeService;
import com.ibm.dp.service.impl.WarehouseMstFrmBotreeServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;

public class WarehouseMstFrmBotreeWebService {
	public static Logger logger = Logger.getLogger(WarehouseMstFrmBotreeWebService.class
			.getName());
	
	public String[] wareHouseDataFrmBotree(
			String whCode, String whName,String whAddress1,String whAddress2,String whPhone,
			String whFax,String tinNumber,int whType,String circleCode, String employee,
			String compCode,String area ,String subArea,String srcType,
			String defCompCode,String defArea,String defSubArea ,String defSrcType,
			String chrunCompCode,String chrunArea,String chrunSubArea, String chrunSrcType,
			String inTransCompCode,String inTransArea,String inTransSubArea, String inTransSrcType,
			String nonRepCompCode, String nonRepArea,String nonRepSubArea,String nonRepSrcType,
			String isActive,String isEdit, String userid, String password) 
	{
		logger
				.info("********** WarehouseMstFrmBotreeWebService  Started for \n warehouse code  :::  "
						+ whCode + "\n Warehouse Name      :::  " + whName);

		String[] serviceMsg = new String[2]; // [0] = header ,[1] = message
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try {

			encPassword = crypt.encrypt(password);
			if (webserviceResourceBundle == null) {
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}

			if (!userid.equals(webserviceResourceBundle
					.getString("dpwebservice.wareHouseMaster.userid"))) {
				logger.info("**********INVALID USER NAME**********");
				serviceMsg[0] = "Failure";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_USER_NAME;
				return serviceMsg;
			} else if (!encPassword.equals(webserviceResourceBundle
					.getString("dpwebservice.wareHouseMaster.password"))) {
				logger.info("**********INVALID PASSWORD**********");
				serviceMsg[0] = "Failure";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_PASSWORD;
				return serviceMsg;

			}
			else
			{
				WarehouseMstFrmBotreeDTO whDTO = new WarehouseMstFrmBotreeDTO();
				
				whDTO.setArea(area);
				whDTO.setChrunArea(chrunArea);
				whDTO.setChrunCompCode(chrunCompCode);
				whDTO.setChrunSrcType(chrunSrcType);
				whDTO.setChrunSubArea(chrunSubArea);
				System.out.println("circleCode**************"+circleCode);
				whDTO.setCircleCode(circleCode);
				whDTO.setCompCode(compCode);
				whDTO.setDefArea(defArea);
				whDTO.setDefCompCode(defCompCode);
				whDTO.setDefSrcType(defSrcType);
				whDTO.setDefSubArea(defSubArea);
				whDTO.setEmployee(employee);
				whDTO.setInTransArea(inTransArea);
				whDTO.setInTransCompCode(inTransCompCode);
				whDTO.setInTransSrcType(inTransSrcType);
				whDTO.setInTransSubArea(inTransSubArea);
				whDTO.setIsActive(isActive);
				whDTO.setIsEdit(isEdit);
				whDTO.setNonRepArea(nonRepArea);
				whDTO.setNonRepCompCode(nonRepCompCode);
				whDTO.setNonRepSrcType(nonRepSrcType);
				whDTO.setNonRepSubArea(nonRepSubArea);
				whDTO.setSrcType(srcType);
				whDTO.setSubArea(subArea);
				whDTO.setTinNumber(tinNumber);
				whDTO.setWhAddress1(whAddress1);
				whDTO.setWhAddress2(whAddress2);
				whDTO.setWhCode(whCode);
				whDTO.setWhFax(whFax);
				whDTO.setWhName(whName);
				whDTO.setWhPhone(whPhone);
				whDTO.setWhType(whType);
				
				
				WarehouseMstFrmBotreeService whmstService = new WarehouseMstFrmBotreeServiceImpl();
				serviceMsg = whmstService.updateWhtMasterData(whDTO);
		//logger.info("********** service msg == " + serviceMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger
					.error(
							"************ Exception in WarehouseMstFrmBotreeWebService WebService -------->",
							e);
			e.printStackTrace();
			serviceMsg[0] = "OTHERS";
			serviceMsg[1] = "ssss";
		}
		logger
				.info("********** WarehouseMstFrmBotreeWebService WebService msg returned == ");
		return serviceMsg;
	}
}
