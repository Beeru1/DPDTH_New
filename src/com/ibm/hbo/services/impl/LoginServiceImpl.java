package com.ibm.hbo.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.hbo.actions.HBOMasterAction;
import com.ibm.hbo.dao.HBOCategoryMstrDao;
import com.ibm.hbo.dao.HBOPopulateUserDao;
import com.ibm.hbo.dao.impl.HBOCategoryMstrDaoImpl;
import com.ibm.hbo.dao.impl.HBOPopulateUserDaoImpl;
import com.ibm.hbo.dao.impl.HBOUploadStatusDaoImpl;
import com.ibm.hbo.dto.HBOCategoryMstr;
import com.ibm.hbo.dto.HBOLogin;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.services.LoginService;
/*
 * Created on Oct 16, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author avanagar
 * @version 2.0
 * 
 */
public class LoginServiceImpl implements LoginService {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(LoginServiceImpl.class);
		
	}

	/**
	 * 
	 * @param dto
	 */

	public HBOUserBean populateUserDetails(HBOLogin login) throws HBOException {
		logger.info("Populating user credentials");
		HBOPopulateUserDao populateDao = new HBOPopulateUserDaoImpl();
		HBOUserBean userBean = null;
		
		List dataList = null;
		try {
			dataList = new ArrayList();
			dataList = populateDao.populateValues(login);
			if(dataList != null) {
			userBean = new HBOUserBean();
			userBean = setUserBeanData(dataList, userBean);
			}
			
		} catch (Exception ex) {
			if (ex instanceof DAOException) {
				throw new HBOException(ex.getMessage());
			} else {
				logger.error(" DAOException occured in populateUserDetails():" + ex.getMessage());
			}

		}
		logger.info("Populating user credentials successfully");
		return userBean; 
	}

	private HBOUserBean setUserBeanData(List dataList, HBOUserBean userBean) throws HBOException {
		logger.debug("Setting user bean");
		HashMap categoryMap = null;
		ArrayList categoryDesc = null;
		try {
			int count = 0;
			ArrayList moduleList = (ArrayList) dataList.get(count);
			userBean.setModuleList(moduleList);
			moduleList = null;
			HBOCategoryMstrDao categoryDao = new HBOCategoryMstrDaoImpl();
			HBOCategoryMstr retValue[] = categoryDao.getCategory();
			categoryMap = new HashMap();
			logger.debug("1");
			for (int i = 0; i < retValue.length; i++) {
				categoryDesc = new ArrayList();
				HBOCategoryMstr catDto = (HBOCategoryMstr) retValue[i];
				categoryDesc.add(catDto.getCategoryName());
				categoryDesc.add(catDto.getCategoryDescription());
				categoryMap.put(catDto.getCategoryId(), categoryDesc);

			}
			logger.debug("2");
			userBean.setCategoryList(categoryMap);
			List userDataList = (ArrayList) dataList.get(++count);
			logger.debug("21");
			HashMap userData = (HashMap) userDataList.get(0);
			logger.debug("CIRCLE_ID:"+checkNull(userData.get("CIRCLE_ID")));
			
			userBean.setCircleId(checkNull(userData.get("CIRCLE_ID")));
			logger.info("Actor id ::"+userData.get("ACTOR_ID"));
			userBean.setActorId(checkNull(userData.get("ACTOR_ID")));
			userBean.setConnectId(checkNull(userData.get("CONNECT_ID")));
			userBean.setCreatedBy(checkNull(userData.get("CREATED_BY")));
			userBean.setCreatedDt(checkNull(userData.get("CREATED_DT")));
			userBean.setUserID(checkNull(userData.get("USER_ID")));
			if (null == userData.get("LAST_LOGIN_IP")) {
				userBean.setLastLoginIp("");
			} else {
				userBean.setLastLoginIp(checkNull(userData.get("LAST_LOGIN_IP")));
			}
			if (null == userData.get("LAST_LOGIN_IP")) {
				userBean.setLastLoginTime("");
			} else {
				userBean.setLastLoginTime(checkNull(userData.get("LAST_LOGIN_TIME")));
			}
			logger.debug("3");
			userBean.setLoginAttempted(checkNull(userData.get("LOGIN_ATTEMPTED")));
			userBean.setStatus(checkNull(userData.get("STATUS")));
			userBean.setWarehouseID(checkNull(userData.get("WAREHOUSE_ID")));
			logger.debug("31");
			userBean.setUpdatedDt(checkNull(userData.get("UPDATED_DT")));
			userBean.setUpdatedBy(checkNull(userData.get("UPDATED_BY")));
			userBean.setUserLoginId(checkNull(userData.get("USER_LOGIN_ID")));			
			userBean.setUserFname(checkNull(userData.get("USER_FNAME")));
			userBean.setUserMname(checkNull(userData.get("USER_MNAME")));
			userBean.setUserLname(checkNull(userData.get("USER_LNAME")));
			userBean.setUserPassword(checkNull(userData.get("USER_PASSWORD")));
			userBean.setUserMobileNumber(checkNull(userData.get("USER_MOBILE_NUMBER")));
			userBean.setUserAddress(checkNull(userData.get("USER_ADDRESS")));
			userBean.setUserCity(checkNull(userData.get("USER_CITY")));
			userBean.setUserState(checkNull(userData.get("USER_STATE")));
			userBean.setUserEmailid(checkNull(userData.get("USER_EMAILID")));
			logger.debug("33");
			userBean.setUserActiveFlag(checkNull(userData.get("STATUS"))); 
			userBean.setUserStartDate(checkNull(userData.get("USER_START_DATE")));
			userBean.setUserPsswrdExpryDt(checkNull(userData.get("USER_PSSWRD_EXPRY_DT")));
			userBean.setZonalId(checkNull(userData.get("CIRCLE_ID")));
			List actorList = (ArrayList) dataList.get(++count);
			HashMap actorData = (HashMap) actorList.get(0);
			userBean.setActorName(checkNull(actorData.get("ROLE_NAME")));
			ArrayList actionMappingList = (ArrayList) dataList.get(++count);
			HashMap actionMappingData = (HashMap) actionMappingList.get(0);
			//List circleList = (ArrayList) dataList.get(++count);
			//HashMap circleData = (HashMap) circleList.get(0);
			//userBean.setCircleName(circleData.get("CIRCLE_NAME")));
			logger.debug("8");
			ArrayList modules = userBean.getModuleList();
			categoryMap = null;
			categoryMap = new HashMap();
			categoryMap = (HashMap) userBean.getCategoryList();
			Iterator iterator = (categoryMap.keySet()).iterator();
			HashMap moduleData = new HashMap();
			String key = null;
			ArrayList moduleGroup = null;
			HashMap moduleMap  = null;
			while (iterator.hasNext()) {

				key = iterator.next().toString();
				moduleGroup = new ArrayList();

				for (int i = 0; i < modules.size(); i++) {
					if ((((HashMap) modules.get(i)).get("MODULE_CATEGORY"))
						.toString()
						.equals(key)) {
							moduleMap = (HashMap) modules.get(i);
							actionMappingData = (HashMap) actionMappingList.get(i);
							moduleMap.put("MAPPING_CONDITION",checkNull(actionMappingData.get("ACTION_ALLOWED")));
							moduleGroup.add(moduleMap);
					}

				}
				moduleData.put(key, moduleGroup);
			}
			logger.debug("10");
			userBean.setModuleData(moduleData);
			logger.debug("11");
		} catch (Exception ex) {
			if (ex instanceof DAOException) {
				throw new HBOException(ex.getMessage());
			} else {
				logger.error(" Exception occured in setUserBeanData():" + ex.getMessage());
			}

		}
		logger.info(" setUserBeanData:user information successfully set  ");
		return userBean;
	}
	private String checkNull(Object value) {
		String strvalue = value==null?"":value.toString();
		return strvalue;
	}
	
	public String logoutUser(String userID) throws DAOException {
			HBOPopulateUserDao populateDao = new HBOPopulateUserDaoImpl();
			String returnMessage="";
			try {
				returnMessage=populateDao.logoutUser(userID);
			} catch(Exception e) {
				throw new DAOException("Exception: " + e.getMessage(), e);
			}
			return returnMessage;
		}
	
}
