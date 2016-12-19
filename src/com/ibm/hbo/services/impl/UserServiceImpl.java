/*
 * Created on Oct 23, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.Message;

import org.apache.log4j.Logger;

import com.ibm.hbo.dao.HBOActorMstrDao;
import com.ibm.hbo.dao.HBOCircleMstrDao;
import com.ibm.hbo.dao.HBOConnectMstrDao;
import com.ibm.hbo.dao.HBOUserMstrDao;
import com.ibm.hbo.dao.HBOZoneMstrDao;
import com.ibm.hbo.dao.impl.HBOActorMstrDaoImpl;
import com.ibm.hbo.dao.impl.HBOCircleMstrDaoImpl;
import com.ibm.hbo.dao.impl.HBOConnectMstrDaoImpl;
import com.ibm.hbo.dao.impl.HBOUserMstrDaoImpl;
import com.ibm.hbo.dao.impl.HBOZoneMstrDaoImpl;
import com.ibm.hbo.dto.HBOActorMstr;
import com.ibm.hbo.dto.HBOCircleDTO;
import com.ibm.hbo.dto.HBOConnectMstr;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.dto.HBOZoneMstr;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.services.UserService;
import com.ibm.utilities.PropertyReader;

/**
 * @author Sachin Kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author avanagar
 * @version 2.0
 * 
 */
public class UserServiceImpl implements UserService {

	public static Logger logger = Logger.getLogger(UserServiceImpl.class);

	/**
	 * 
	 * @return
	 * @throws ArcException
	 */
	public List getUserTypeService(String roleId) throws HBOException {
		HBOActorMstrDao actorData = new HBOActorMstrDaoImpl();
		List actorList = null;
		HBOActorMstr actorDto = null;
		try {
			HBOActorMstr arcActorMstr[] = actorData.getActorName(roleId);
			actorList = new ArrayList();
			HashMap actorMap = null;
			for (int i = 0; i < arcActorMstr.length; i++) {
				actorDto = (HBOActorMstr) arcActorMstr[i];
				actorMap = new HashMap();
				
						actorMap.put("ACTOR_ID", actorDto.getActorId());
						actorMap.put("ACTOR_NAME", actorDto.getActorName());
						actorList.add(actorMap);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getUserTypeService():" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getUserTypeService():" + e.getMessage());
			}
		}
		return actorList;

	}
	
	/**
	 * To check duplicate user service
	 * @param userLogingId
	 * @return boolean
	 * @throws ArcException
	 */

	public boolean checkDuplicateUserLogin(String userLogingId) throws HBOException {
		HBOUserMstrDao userMstrDao = null;
		boolean exist = false;
		try {
			userMstrDao = new HBOUserMstrDaoImpl();
			if (userMstrDao.CheckUserId(userLogingId)) {
				exist = true;
			} else {
				exist = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DAOException) {
				logger.error("DAOException occured in checkDuplicateUserLogin():" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in checkDuplicateUserLogin():" + e.getMessage());
			}
		}
		return exist;

	}
	public List getCircleDataService() throws HBOException {
		HBOCircleMstrDao circleData = new HBOCircleMstrDaoImpl();
		List circleList = null;
		HBOCircleDTO circleDto = null;
		try {
			logger.info("comingggg");
			HBOCircleDTO arcCircleMstr[] = circleData.getCircleData();
			
			logger.info("comingggg"+arcCircleMstr.length);
			circleList = new ArrayList();
			HashMap circleMap = null;
			for (int i = 0; i < arcCircleMstr.length; i++) {
				circleDto = (HBOCircleDTO) arcCircleMstr[i];
				circleMap = new HashMap();
				circleMap.put("CIRCLE_ID", circleDto.getCircleId());
				circleMap.put("CIRCLE_NAME", circleDto.getCircleName());
				circleList.add(circleMap);
				
				
			}

		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getCircleDataService():" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getCircleDataService():" + e.getMessage());
			}
		}
		return circleList;

	}

	
	
	public String createUserService(HBOUserMstr userMstrDto, HBOUserBean sessionUserBean)
		throws HBOException {
			String message="";
		try {
			String userID = "";
			if(sessionUserBean != null) {
				userMstrDto.setCreatedBy(sessionUserBean.getUserLoginId());
			}
			//For Super Admin/National User
			if (sessionUserBean.getActorId().equals("0")) {
				userMstrDto.setCircleId("0");
				userMstrDto.setZonalId("0");
				userMstrDto.setConnectId("0");
			}
			//For Circle Adminfs
			if (sessionUserBean.getActorId().equals("1")) {
				userMstrDto.setCircleId(sessionUserBean.getCircleId());
				userMstrDto.setZonalId("0");
				userMstrDto.setConnectId("0");
			}
			//For Zonal Admin
			if (sessionUserBean.getActorId().equals("2")) {
				userMstrDto.setCircleId(sessionUserBean.getCircleId());
				userMstrDto.setZonalId(sessionUserBean.getZonalId());
				userMstrDto.setConnectId("0");
			}
			//For ARC User
			if (sessionUserBean.getActorId().equals("3")) {
				userMstrDto.setCircleId("0");
				userMstrDto.setZonalId("0");
				userMstrDto.setConnectId("0");
			}
			HBOUserMstrDao userMstrDao = new HBOUserMstrDaoImpl();
			logger.info("I am insertUserDetails");
			// Added on 7 May for Security UAT
			
			if ((sessionUserBean.getActorId().equals("1") && (userMstrDto.getActorId().equals("1") || userMstrDto.getActorId().equals("2") || userMstrDto.getActorId().equals("3"))) ||
			 (sessionUserBean.getActorId().equals("2") && (userMstrDto.getActorId().equals("6") || userMstrDto.getActorId().equals("7"))) || 
				(sessionUserBean.getActorId().equals("3") && (userMstrDto.getActorId().equals("4") || userMstrDto.getActorId().equals("5"))) )
			{
				int insertCount = userMstrDao.insertUserDetails(userMstrDto);

			}else
			{
				
				logger.error("Logged -In user is not authorized to create this User!!!!");			
				
			}
			message = "success";
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DAOException) {
				logger.error("DAOException occured in createUserService():" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in createUserService():" + e.getMessage());
			}
			message = "failure";
		}
		return message;
	}
	public int editUserService(HBOUserMstr userMstrDto) throws HBOException {
		int insertCount = 0;
		try {
			HBOUserMstrDao userMstrDao = new HBOUserMstrDaoImpl();

			/*Updating into the table ARC_USER_MSTR*/
			insertCount = userMstrDao.updateUser(userMstrDto);

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DAOException) {
				logger.error("DAOException occured in editUserService():" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in editUserService():" + e.getMessage());
			}
		}
		return insertCount;
	}

	
	public int changePasswordService(HBOUserMstr dto) throws HBOException {
		HBOUserMstrDao userDataDao = new HBOUserMstrDaoImpl();
		int updateCnt = 0;
		try {
			//Update password dao called
			updateCnt = userDataDao.updatePassword(dto);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in changePasswordService():"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in changePasswordService():"
						+ e.getMessage());
			}
		}
		return updateCnt;
	}

	/**
			 * Returns email id for userLogin Id
			 * @param userLogingId
			 * @return boolean
			 * @throws ArcException
			 */
	
	//-----------by sachin on 13th feb-----------
	public HBOUserMstr userLoginEmail(String userLogingId) throws HBOException {
				HBOUserMstrDao userMstrDao = null;
				String userMailId = null;
				HBOUserMstr hboUserMstrdto = null;
				try {
					userMstrDao = new HBOUserMstrDaoImpl();
					hboUserMstrdto = userMstrDao.loginEmailId(userLogingId);
					if (null == hboUserMstrdto) {
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof DAOException) {
						logger.error("DAOException occured in userLoginEmail():" + e.getMessage());
						throw new HBOException(e.getMessage());
					} else {
						logger.error("Exception occured in userLoginEmail():" + e.getMessage());
					}
				}
				return hboUserMstrdto;

			}

	public String pwdMailingService(String userLogingId, String userEmail)
		throws HBOException {
		StringBuffer sbMessage = new StringBuffer();
		String strMessage = null;
		String txtMessage = null;
		String strSubject = "Your HBO System Login Password";
		sbMessage.append("Dear " + userLogingId + ", \n\n");
		sbMessage.append("Your new password is : ");
		strMessage =
			userLogingId.substring(0, 1)
				+ Math.abs(new Random().nextInt())
				+ userLogingId.substring(2, 3);

		sbMessage.append(strMessage + "\n");
		sbMessage.append("\nRegards ");
		sbMessage.append("\nHBO System Administartor ");
		sbMessage.append(
			"\n\n/** This is an Auto generated email.Please do not reply to this.**/");
		txtMessage = sbMessage.toString();

		String strHost = PropertyReader.getValue("LOGIN.SMTP");
		String strFromEmail = PropertyReader.getValue("LOGIN.EMAIL");
		try {
			Properties prop = System.getProperties();
			prop.put("mail.smtp.host", strHost);
			Session ses = Session.getDefaultInstance(prop, null);
			MimeMessage msg = new MimeMessage(ses);
			msg.setFrom(new InternetAddress(strFromEmail));
			msg.addRecipient(
				Message.RecipientType.TO,
				new InternetAddress(userEmail));
			msg.setSubject(strSubject);
			msg.setText(txtMessage);
			msg.setSentDate(new Date());
			Transport.send(msg);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured in pwdMailingService():" + e.getMessage());
			throw new HBOException(e.getMessage());

		}
		return strMessage;
	}

//----------end of method-------------------
	/**
	 * Set User List based on the users actor ID
	 */
	public List viewUserService(String actorId, String userId) throws HBOException {
		HBOUserMstrDao userData = new HBOUserMstrDaoImpl();
		List userList = null;
		HBOUserMstr userDto = null;
		try {
			//getUserData() method called of HBOUserMstrDao
			HBOUserMstr HBOUserMstr[] = userData.getUserData(actorId,userId);

			userList = new ArrayList();
			HashMap userMap = null;
			for (int i = 0; i < HBOUserMstr.length; i++) {
				userDto = (HBOUserMstr) HBOUserMstr[i];
				logger.info("address==="+userDto.getUserAddress());
				userMap = new HashMap();
				//				For Super Admin/National User
				userMap.put("USER_ID", userDto.getUserId());
				userMap.put("USER_LOGIN_ID", userDto.getUserLoginId());
				userMap.put("USER_FNAME", userDto.getUserFname());
				userMap.put("USER_MNAME", userDto.getUserMname());
				userMap.put("USER_LNAME", userDto.getUserLname());
				userMap.put("USER_MOBILE_NUMBER", userDto.getUserMobileNumber());
				userMap.put("USER_EMAILID", userDto.getUserEmailid());
				userMap.put("USER_TYPE", userDto.getUserActorName());
				userMap.put("STATUS", userDto.getStatus());
				userMap.put("FINALSTATUS", userDto.getFinalStatus());
				userMap.put("USER_ADDRESS", userDto.getUserAddress());
				userMap.put("USER_CITY", userDto.getUserCity());
				userMap.put("USER_STATE", userDto.getUserState());
				userMap.put("USER_WAREHOUSE", userDto.getWarehouseName());
				userMap.put("USER_ACTOR_ID", userDto.getActorId());
				userList.add(userMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DAOException) {
				logger.error("DAOException occured in viewUserService():" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in viewUserService():" + e.getMessage());
			}
		}
		return userList;

	}

	public HBOUserMstr getEditUserDetails(String userId, ArrayList userList) throws HBOException {
			HBOUserMstr userDto = new HBOUserMstr();
			try {
				HashMap userMap = null;
				for (int i = 0; i < userList.size(); i++) {

					userMap = new HashMap();
					userMap = (HashMap) userList.get(i);
					if (userId.equals(userMap.get("USER_LOGIN_ID"))) {
						userDto.setUserId((String) userMap.get("USER_ID"));
						userDto.setUserLoginId((String) userMap.get("USER_LOGIN_ID"));
						userDto.setCircleId((String) userMap.get("CIRCLE_ID"));
						userDto.setUserFname((String) userMap.get("USER_FNAME"));
						userDto.setUserMname((String) userMap.get("USER_MNAME"));
						userDto.setUserLname((String) userMap.get("USER_LNAME"));
						userDto.setUserEmailid((String) userMap.get("USER_EMAILID"));
						userDto.setUserMobileNumber((String) userMap.get("USER_MOBILE_NUMBER"));
						userDto.setStatus((String) userMap.get("STATUS"));
						userDto.setFinalStatus((String) userMap.get("FINALSTATUS"));
						userDto.setUserAddress((String) userMap.get("USER_ADDRESS"));
						userDto.setUserCity((String) userMap.get("USER_CITY"));
						userDto.setUserState((String) userMap.get("USER_STATE"));
						userDto.setWarehouseName((String) userMap.get("USER_WAREHOUSE"));
						userDto.setActorId((String) userMap.get("USER_ACTOR_ID"));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof DAOException) {
					logger.error("DAOException occured in getEditUserDetails():" + e.getMessage());
					throw new HBOException(e.getMessage());
				} else {
					logger.error("Exception occured in getEditUserDetails():" + e.getMessage());
				}
			}
			return userDto;

		}


}
