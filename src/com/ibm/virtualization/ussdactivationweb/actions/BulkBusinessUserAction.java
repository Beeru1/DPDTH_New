/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import com.ibm.virtualization.ussdactivationweb.beans.BulkBusinessUserCreationBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl.BulkBizUserCreationServiceImpl;
import com.ibm.virtualization.ussdactivationweb.dao.BulkBizUserCreationDAO;
import com.ibm.virtualization.ussdactivationweb.dao.LocationDataDAO;
import com.ibm.virtualization.ussdactivationweb.dao.RegistrationOfAllUsersDAO;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserCreationDTO;
import com.ibm.virtualization.ussdactivationweb.dto.LocationDataDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
//import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author Nitesh
 * 
 */
public class BulkBusinessUserAction extends DispatchAction {

	/** getting logger refrence * */
	private static final Logger logger = Logger
			.getLogger(BulkBusinessUserAction.class.toString());

	private BulkBusinessUserCreationBean blankAll(BulkBusinessUserCreationBean bulkUserBean) throws Exception {
		
		RegistrationOfAllUsersDAO dao = new RegistrationOfAllUsersDAO();
		bulkUserBean.setUsersRoleTypeList((ArrayList) dao
				.getUserTypeList(Constants.CIRCLE_ADMIN));		
		
		bulkUserBean.setNewUsersRoleType("-1");
		bulkUserBean.setCircle1("-1");
		bulkUserBean.setZone1("-1");
		bulkUserBean.setCity1("-1");
		bulkUserBean.setParent1("-1");
		bulkUserBean.setGrandParent1("-1");
		
		bulkUserBean.setCircleList(new ArrayList());
		bulkUserBean.setZoneList1(new ArrayList());
		bulkUserBean.setCityList1(new ArrayList());
		bulkUserBean.setParentUserList1(new ArrayList());
		bulkUserBean.setGrandParentUserList1(new ArrayList());
		
		bulkUserBean.setCircle2("-1");
		bulkUserBean.setZone2("-1");
		bulkUserBean.setCity2("-1");
		bulkUserBean.setParent2("-1");
		bulkUserBean.setGrandParent2("-1");
		
		bulkUserBean.setZoneList2(new ArrayList());
		bulkUserBean.setCityList2(new ArrayList());
		bulkUserBean.setParentUserList2(new ArrayList());
		bulkUserBean.setGrandParentUserList2(new ArrayList());
		
		bulkUserBean.setCircle3("-1");
		bulkUserBean.setZone3("-1");
		bulkUserBean.setCity3("-1");
		bulkUserBean.setParent3("-1");
		bulkUserBean.setGrandParent3("-1");
		
		bulkUserBean.setZoneList3(new ArrayList());
		bulkUserBean.setCityList3(new ArrayList());
		bulkUserBean.setParentUserList3(new ArrayList());
		bulkUserBean.setGrandParentUserList3(new ArrayList());
		
		return bulkUserBean;
	}
	
	private BulkBusinessUserCreationBean setZoneList1(BulkBusinessUserCreationBean bulkUserBean, UserDetailsBean userBean) throws Exception
	{
		LocationDataDAO locationDataDao = new LocationDataDAO();
		
		if(userBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
			ArrayList locationList= new ArrayList();
			LocationDataDTO locationDTO= new LocationDataDTO();
			locationDTO.setLocationDataName(userBean.getZoneName());
			locationDTO.setLocDataId(userBean.getZoneCode());
				locationList.add(locationDTO);
				if(!locationList.isEmpty()){
					bulkUserBean.setZoneList1(locationList);						
				}
				else
				{
					bulkUserBean.setZoneList1(new ArrayList());
				}
		}
		else
		{				
			bulkUserBean.setZoneList1(locationDataDao.getLocationList(
				bulkUserBean.getCircle1(), Constants.Zone,
				Constants.PAGE_FALSE, 0, 0));
		}
		
		return bulkUserBean;
	}
	
	private BulkBusinessUserCreationBean setZoneList2(BulkBusinessUserCreationBean bulkUserBean, UserDetailsBean userBean) throws Exception
	{
		LocationDataDAO locationDataDao = new LocationDataDAO();
		
		if(userBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
			ArrayList locationList= new ArrayList();
			LocationDataDTO locationDTO= new LocationDataDTO();
			locationDTO.setLocationDataName(userBean.getZoneName());
			locationDTO.setLocDataId(userBean.getZoneCode());
				locationList.add(locationDTO);
				if(!locationList.isEmpty()){
					bulkUserBean.setZoneList2(locationList);						
				}
				else
				{
					bulkUserBean.setZoneList2(new ArrayList());
				}
		}
		else
		{				
			bulkUserBean.setZoneList2(locationDataDao.getLocationList(
				bulkUserBean.getCircle2(), Constants.Zone,
				Constants.PAGE_FALSE, 0, 0));
		}
		
		return bulkUserBean;
	}
	
	private BulkBusinessUserCreationBean setZoneList3(BulkBusinessUserCreationBean bulkUserBean, UserDetailsBean userBean) throws Exception
	{
		LocationDataDAO locationDataDao = new LocationDataDAO();
		
		if(userBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
			ArrayList locationList= new ArrayList();
			LocationDataDTO locationDTO= new LocationDataDTO();
			locationDTO.setLocationDataName(userBean.getZoneName());
			locationDTO.setLocDataId(userBean.getZoneCode());
				locationList.add(locationDTO);
				if(!locationList.isEmpty()){
					bulkUserBean.setZoneList3(locationList);						
				}
				else
				{
					bulkUserBean.setZoneList3(new ArrayList());
				}
		}
		else
		{				
			bulkUserBean.setZoneList3(locationDataDao.getLocationList(
				bulkUserBean.getCircle3(), Constants.Zone,
				Constants.PAGE_FALSE, 0, 0));
		}
		
		return bulkUserBean;
	}
	
	private BulkBusinessUserCreationBean resetRequestedValues(
			BulkBusinessUserCreationBean bulkUserBean, HttpServletRequest request) throws Exception {
		LocationDataDAO locationDataDao = new LocationDataDAO();
		boolean flag = true;
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession()).getAttribute(Constants.USER_INFO);
		
		if (bulkUserBean.getNewUsersRoleType() != null
				&& !bulkUserBean.getNewUsersRoleType().equals("-1")) {
			if (bulkUserBean.getNewUsersRoleType().equals(
					String.valueOf(Constants.CEO).trim())
					|| bulkUserBean.getNewUsersRoleType().equals(
							String.valueOf(Constants.SALES_HEAD).trim())) {
				flag = false;
			}
			if (bulkUserBean.getCircle1() != null
					&& !bulkUserBean.getCircle1().equals("-1") && flag) {
				
				bulkUserBean = this.setZoneList1(bulkUserBean, userBean);
				
			} else {
				bulkUserBean.setZone1("-1");
				bulkUserBean.setCity1("-1");
				bulkUserBean.setZoneList1(new ArrayList());
				bulkUserBean.setCityList1(new ArrayList());
			}
			
			if (bulkUserBean.getCircle2() != null
					&& !bulkUserBean.getCircle2().equals("-1") && flag) {
				
				bulkUserBean = this.setZoneList2(bulkUserBean, userBean);
				
			} else {
				bulkUserBean.setZone2("-1");
				bulkUserBean.setCity2("-1");
				bulkUserBean.setZoneList2(new ArrayList());
				bulkUserBean.setCityList2(new ArrayList());
			}
			
			if (bulkUserBean.getCircle3() != null
					&& !bulkUserBean.getCircle3().equals("-1") && flag) {
				
				bulkUserBean = this.setZoneList3(bulkUserBean, userBean);
				
			} else {
				bulkUserBean.setZone3("-1");
				bulkUserBean.setCity3("-1");
				bulkUserBean.setZoneList3(new ArrayList());
				bulkUserBean.setCityList3(new ArrayList());
			}
			

			if (bulkUserBean.getNewUsersRoleType().equals(
					String.valueOf(Constants.ZBM).trim())
					|| bulkUserBean.getNewUsersRoleType().equals(
							String.valueOf(Constants.ZSM).trim())) {
				flag = false;				
			}
			
			if (bulkUserBean.getZone1() != null
					&& !bulkUserBean.getZone1().equals("-1") && flag) {
				bulkUserBean.setCityList1(locationDataDao.getLocationList(
						String.valueOf(bulkUserBean.getZone1()),
						Constants.City, Constants.PAGE_FALSE, 0, 0));
			} else {
				bulkUserBean.setCity1("-1");
				bulkUserBean.setCityList1(new ArrayList());
			}
			
			if (bulkUserBean.getZone2() != null
					&& !bulkUserBean.getZone2().equals("-1") && flag) {
				bulkUserBean.setCityList2(locationDataDao.getLocationList(
						String.valueOf(bulkUserBean.getZone2()),
						Constants.City, Constants.PAGE_FALSE, 0, 0));
			} else {
				bulkUserBean.setCity2("-1");
				bulkUserBean.setCityList2(new ArrayList());
			}
			
			if (bulkUserBean.getZone3() != null
					&& !bulkUserBean.getZone3().equals("-1") && flag) {
				bulkUserBean.setCityList3(locationDataDao.getLocationList(
						String.valueOf(bulkUserBean.getZone3()),
						Constants.City, Constants.PAGE_FALSE, 0, 0));
			} else {
				bulkUserBean.setCity3("-1");
				bulkUserBean.setCityList3(new ArrayList());
			}
			
		} else {			
			bulkUserBean = this.blankAll(bulkUserBean);
			}		
		this.filterValuesForLoginUser(bulkUserBean, request);
		return bulkUserBean;
	}

	private BulkBusinessUserCreationBean setUser1(BulkBusinessUserCreationBean bulkUserBean, HttpServletRequest request)throws Exception
	{
		RegistrationOfAllUsersDAO dao = new RegistrationOfAllUsersDAO();
		bulkUserBean.setGrandParentUserList1(new ArrayList());			
		
		if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.CEO).trim())) {
			//ViewEditCircleMasterDAOImpl
			if (bulkUserBean.getCircle1() != null
					&& !bulkUserBean.getCircle1().equals("-1")) {
				ViewEditCircleMasterDAOImpl viewEditCircleMasterDAOImpl = new ViewEditCircleMasterDAOImpl();
				String hubCode = viewEditCircleMasterDAOImpl.findHubCodeForCircle(bulkUserBean.getCircle1());
				bulkUserBean.setParentUserList1(dao.getmoveBizUserList(
						Constants.ED, hubCode));
			}
			else
			{
				bulkUserBean.setParent1("-1");
				bulkUserBean.setGrandParent1("-1");
				bulkUserBean.setParentUserList1(new ArrayList());				
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.SALES_HEAD).trim())) {				
			if (bulkUserBean.getCircle1() != null
					&& !bulkUserBean.getCircle1().equals("-1")) {
				bulkUserBean.setParentUserList1(dao.getmoveBizUserList(
						Constants.CEO, bulkUserBean.getCircle1()));
			} else {
				bulkUserBean.setParent1("-1");
				bulkUserBean.setGrandParent1("-1");
				bulkUserBean.setParentUserList1(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.ZBM).trim())) {				
			if (bulkUserBean.getCircle1() != null
					&& !bulkUserBean.getCircle1().equals("-1")) {
				bulkUserBean.setParentUserList1(dao.getmoveBizUserList(
						Constants.SALES_HEAD, bulkUserBean.getCircle1()));
			} else {
				bulkUserBean.setParent1("-1");
				bulkUserBean.setGrandParent1("-1");
				bulkUserBean.setParentUserList1(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.ZSM).trim())) {				
			if (bulkUserBean.getZone1() != null
					&& !bulkUserBean.getZone1().equals("-1")) {
				bulkUserBean.setParentUserList1(dao.getmoveBizUserList(
						Constants.ZBM, bulkUserBean.getZone1()));
			} else {
				bulkUserBean.setParent1("-1");
				bulkUserBean.setGrandParent1("-1");
				bulkUserBean.setParentUserList1(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.TM).trim())) {				
			if (bulkUserBean.getZone1() != null
					&& !bulkUserBean.getZone1().equals("-1")) {
				bulkUserBean.setParentUserList1(dao.getmoveBizUserList(
						Constants.ZSM, bulkUserBean.getZone1()));
			} else {
				bulkUserBean.setParent1("-1");
				bulkUserBean.setGrandParent1("-1");
				bulkUserBean.setParentUserList1(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.DISTIBUTOR).trim())) {				
			if (bulkUserBean.getCity1() != null
					&& !bulkUserBean.getCity1().equals("-1")) {
				bulkUserBean.setParentUserList1(dao.getmoveBizUserList(
						Constants.TM, bulkUserBean.getCity1()));
			} else {
				bulkUserBean.setParent1("-1");
				bulkUserBean.setGrandParent1("-1");
				bulkUserBean.setParentUserList1(new ArrayList());					
			}

		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.FOS).trim())) {				
			if (bulkUserBean.getCity1() != null
					&& !bulkUserBean.getCity1().equals("-1")) {
				bulkUserBean.setParentUserList1(dao.getmoveBizUserList(
						Constants.DISTIBUTOR, bulkUserBean.getCity1()));
			} else {
				bulkUserBean.setParent1("-1");
				bulkUserBean.setGrandParent1("-1");
				bulkUserBean.setParentUserList1(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.DEALER).trim())) {
			if (bulkUserBean.getCity1() != null
					&& !bulkUserBean.getCity1().equals("-1")) {
				
				bulkUserBean.setGrandParentUserList1(dao
						.getmoveBizUserList(Constants.DISTIBUTOR,
								bulkUserBean.getCity1()));
				bulkUserBean.setParentUserList1(new ArrayList());
				
				if(bulkUserBean.getGrandParent1() != null && !bulkUserBean.getGrandParent1().equals("-1"))
				{
					bulkUserBean.setParentUserList1(dao.getBizUserChildernList(
							bulkUserBean.getGrandParent1(), bulkUserBean.getCity1()));
				}					
			} else {
				bulkUserBean.setParent1("-1");
				bulkUserBean.setGrandParent1("-1");
				bulkUserBean.setGrandParent1("-1");
				bulkUserBean.setParentUserList1(new ArrayList());					
			}
		}
		
		return bulkUserBean;
	}
	
	private BulkBusinessUserCreationBean setUser2(BulkBusinessUserCreationBean bulkUserBean, HttpServletRequest request)throws Exception
	{
		RegistrationOfAllUsersDAO dao = new RegistrationOfAllUsersDAO();
		bulkUserBean.setGrandParentUserList2(new ArrayList());			
		
		if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.CEO).trim())) {
			//ViewEditCircleMasterDAOImpl
			if (bulkUserBean.getCircle2() != null
					&& !bulkUserBean.getCircle2().equals("-1")) {
				ViewEditCircleMasterDAOImpl viewEditCircleMasterDAOImpl = new ViewEditCircleMasterDAOImpl();
				String hubCode = viewEditCircleMasterDAOImpl.findHubCodeForCircle(bulkUserBean.getCircle2());
				bulkUserBean.setParentUserList2(dao.getmoveBizUserList(
						Constants.ED, hubCode));
			}
			else
			{
				bulkUserBean.setParent2("-1");
				bulkUserBean.setGrandParent2("-1");
				bulkUserBean.setParentUserList2(new ArrayList());				
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.SALES_HEAD).trim())) {				
			if (bulkUserBean.getCircle2() != null
					&& !bulkUserBean.getCircle2().equals("-1")) {
				bulkUserBean.setParentUserList2(dao.getmoveBizUserList(
						Constants.CEO, bulkUserBean.getCircle2()));
			} else {
				bulkUserBean.setParent2("-1");
				bulkUserBean.setGrandParent2("-1");
				bulkUserBean.setParentUserList2(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.ZBM).trim())) {				
			if (bulkUserBean.getCircle2() != null
					&& !bulkUserBean.getCircle2().equals("-1")) {
				bulkUserBean.setParentUserList2(dao.getmoveBizUserList(
						Constants.SALES_HEAD, bulkUserBean.getCircle2()));
			} else {
				bulkUserBean.setParent2("-1");
				bulkUserBean.setGrandParent2("-1");
				bulkUserBean.setParentUserList2(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.ZSM).trim())) {				
			if (bulkUserBean.getZone2() != null
					&& !bulkUserBean.getZone2().equals("-1")) {
				bulkUserBean.setParentUserList2(dao.getmoveBizUserList(
						Constants.ZBM, bulkUserBean.getZone2()));
			} else {
				bulkUserBean.setParent2("-1");
				bulkUserBean.setGrandParent2("-1");
				bulkUserBean.setParentUserList2(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.TM).trim())) {				
			if (bulkUserBean.getZone2() != null
					&& !bulkUserBean.getZone2().equals("-1")) {
				bulkUserBean.setParentUserList2(dao.getmoveBizUserList(
						Constants.ZSM, bulkUserBean.getZone2()));
			} else {
				bulkUserBean.setParent2("-1");
				bulkUserBean.setGrandParent2("-1");
				bulkUserBean.setParentUserList2(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.DISTIBUTOR).trim())) {				
			if (bulkUserBean.getCity2() != null
					&& !bulkUserBean.getCity2().equals("-1")) {
				bulkUserBean.setParentUserList2(dao.getmoveBizUserList(
						Constants.TM, bulkUserBean.getCity2()));
			} else {
				bulkUserBean.setParent2("-1");
				bulkUserBean.setGrandParent2("-1");
				bulkUserBean.setParentUserList2(new ArrayList());					
			}

		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.FOS).trim())) {				
			if (bulkUserBean.getCity2() != null
					&& !bulkUserBean.getCity2().equals("-1")) {
				bulkUserBean.setParentUserList2(dao.getmoveBizUserList(
						Constants.DISTIBUTOR, bulkUserBean.getCity2()));
			} else {
				bulkUserBean.setParent2("-1");
				bulkUserBean.setGrandParent2("-1");
				bulkUserBean.setParentUserList2(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.DEALER).trim())) {
			if (bulkUserBean.getCity2() != null
					&& !bulkUserBean.getCity2().equals("-1")) {
				
				bulkUserBean.setGrandParentUserList2(dao
						.getmoveBizUserList(Constants.DISTIBUTOR,
								bulkUserBean.getCity2()));
				bulkUserBean.setParentUserList2(new ArrayList());
				
				if(bulkUserBean.getGrandParent2() != null && !bulkUserBean.getGrandParent2().equals("-1"))
				{
					bulkUserBean.setParentUserList2(dao.getBizUserChildernList(
							bulkUserBean.getGrandParent2(), bulkUserBean.getCity2()));
				}					
			} else {
				bulkUserBean.setParent2("-1");
				bulkUserBean.setGrandParent2("-1");
				bulkUserBean.setGrandParent2("-1");
				bulkUserBean.setParentUserList2(new ArrayList());					
			}
		}
		
		return bulkUserBean;
	}
	
	
	private BulkBusinessUserCreationBean setUser3(BulkBusinessUserCreationBean bulkUserBean, HttpServletRequest request)throws Exception
	{
		RegistrationOfAllUsersDAO dao = new RegistrationOfAllUsersDAO();
		bulkUserBean.setGrandParentUserList3(new ArrayList());			
		
		if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.CEO).trim())) {
			//ViewEditCircleMasterDAOImpl
			if (bulkUserBean.getCircle3() != null
					&& !bulkUserBean.getCircle3().equals("-1")) {
				ViewEditCircleMasterDAOImpl viewEditCircleMasterDAOImpl = new ViewEditCircleMasterDAOImpl();
				String hubCode = viewEditCircleMasterDAOImpl.findHubCodeForCircle(bulkUserBean.getCircle3());
				bulkUserBean.setParentUserList3(dao.getmoveBizUserList(
						Constants.ED, hubCode));
			}
			else
			{
				bulkUserBean.setParent3("-1");
				bulkUserBean.setGrandParent3("-1");
				bulkUserBean.setParentUserList3(new ArrayList());				
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.SALES_HEAD).trim())) {				
			if (bulkUserBean.getCircle3() != null
					&& !bulkUserBean.getCircle3().equals("-1")) {
				bulkUserBean.setParentUserList3(dao.getmoveBizUserList(
						Constants.CEO, bulkUserBean.getCircle3()));
			} else {
				bulkUserBean.setParent3("-1");
				bulkUserBean.setGrandParent3("-1");
				bulkUserBean.setParentUserList3(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.ZBM).trim())) {				
			if (bulkUserBean.getCircle3() != null
					&& !bulkUserBean.getCircle3().equals("-1")) {
				bulkUserBean.setParentUserList3(dao.getmoveBizUserList(
						Constants.SALES_HEAD, bulkUserBean.getCircle3()));
			} else {
				bulkUserBean.setParent3("-1");
				bulkUserBean.setGrandParent3("-1");
				bulkUserBean.setParentUserList3(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.ZSM).trim())) {				
			if (bulkUserBean.getZone3() != null
					&& !bulkUserBean.getZone3().equals("-1")) {
				bulkUserBean.setParentUserList3(dao.getmoveBizUserList(
						Constants.ZBM, bulkUserBean.getZone3()));
			} else {
				bulkUserBean.setParent3("-1");
				bulkUserBean.setGrandParent3("-1");
				bulkUserBean.setParentUserList3(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.TM).trim())) {				
			if (bulkUserBean.getZone3() != null
					&& !bulkUserBean.getZone3().equals("-1")) {
				bulkUserBean.setParentUserList3(dao.getmoveBizUserList(
						Constants.ZSM, bulkUserBean.getZone3()));
			} else {
				bulkUserBean.setParent3("-1");
				bulkUserBean.setGrandParent3("-1");
				bulkUserBean.setParentUserList3(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.DISTIBUTOR).trim())) {				
			if (bulkUserBean.getCity3() != null
					&& !bulkUserBean.getCity3().equals("-1")) {
				bulkUserBean.setParentUserList3(dao.getmoveBizUserList(
						Constants.TM, bulkUserBean.getCity3()));
			} else {
				bulkUserBean.setParent3("-1");
				bulkUserBean.setGrandParent3("-1");
				bulkUserBean.setParentUserList3(new ArrayList());					
			}

		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.FOS).trim())) {				
			if (bulkUserBean.getCity3() != null
					&& !bulkUserBean.getCity3().equals("-1")) {
				bulkUserBean.setParentUserList3(dao.getmoveBizUserList(
						Constants.DISTIBUTOR, bulkUserBean.getCity3()));
			} else {
				bulkUserBean.setParent3("-1");
				bulkUserBean.setGrandParent3("-1");
				bulkUserBean.setParentUserList3(new ArrayList());					
			}
		} else if (bulkUserBean.getNewUsersRoleType().equals(
				String.valueOf(Constants.DEALER).trim())) {
			if (bulkUserBean.getCity3() != null
					&& !bulkUserBean.getCity3().equals("-1")) {
				
				bulkUserBean.setGrandParentUserList3(dao
						.getmoveBizUserList(Constants.DISTIBUTOR,
								bulkUserBean.getCity3()));
				bulkUserBean.setParentUserList3(new ArrayList());
				
				if(bulkUserBean.getGrandParent3() != null && !bulkUserBean.getGrandParent3().equals("-1"))
				{
					bulkUserBean.setParentUserList3(dao.getBizUserChildernList(
							bulkUserBean.getGrandParent3(), bulkUserBean.getCity3()));
				}					
			} else {
				bulkUserBean.setParent3("-1");
				bulkUserBean.setGrandParent3("-1");
				bulkUserBean.setGrandParent3("-1");
				bulkUserBean.setParentUserList3(new ArrayList());					
			}
		}
		
		return bulkUserBean;
	}
	
	private BulkBusinessUserCreationBean setUsers(
			BulkBusinessUserCreationBean bulkUserBean, HttpServletRequest request) throws Exception {		
		
		if (bulkUserBean.getNewUsersRoleType() != null
				&& !bulkUserBean.getNewUsersRoleType().equals("-1")) {			
			
			bulkUserBean = this.setUser1(bulkUserBean, request);
			bulkUserBean = this.setUser2(bulkUserBean, request);			
			bulkUserBean = this.setUser3(bulkUserBean, request);
		}
		else
		{
			bulkUserBean = this.blankAll(bulkUserBean);
			
		}
		bulkUserBean = this.filterValuesForLoginUser(bulkUserBean, request);
		return bulkUserBean;
	}

	private BulkBusinessUserCreationBean filterValuesForLoginUser(BulkBusinessUserCreationBean bulkUserBean, HttpServletRequest request) throws Exception
	{
		RegistrationOfAllUsersDAO dao = new RegistrationOfAllUsersDAO();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession()).getAttribute(Constants.USER_INFO);
		
		if(userBean.getCircleId() == null)
		{
			bulkUserBean.setUsersRoleTypeList((ArrayList) dao
					.getUserTypeList(Constants.CIRCLE_ADMIN));
			bulkUserBean.setCircleList((ArrayList) ViewEditCircleMasterDAOImpl
					.getCircleList());
		}
		else if(userBean.getUserType().equalsIgnoreCase(Constants.CIRCLE_ADMIN_USERBEAN))
		{
			bulkUserBean.setUsersRoleTypeList((ArrayList)dao.getUserTypeList(Constants.CIRCLE_ADMIN));
			LabelValueBean lvBean = new LabelValueBean(userBean.getCircleId(), userBean.getCircleId()+"-"+userBean.getCircleName());
			ArrayList circleList = new ArrayList();
			circleList.add(lvBean);
			bulkUserBean.setCircleList(circleList);
		}
		else if(userBean.getUserType().equalsIgnoreCase(Constants.Zone_User_USERBEAN))
		{
			bulkUserBean.setUsersRoleTypeList((ArrayList)dao.getUserTypeList(Constants.Zone_User));
			LabelValueBean lvBean = new LabelValueBean(userBean.getCircleId(), userBean.getCircleId()+"-"+userBean.getCircleName());
			ArrayList circleList = new ArrayList();
			circleList.add(lvBean);
			bulkUserBean.setCircleList(circleList);
		}
		else
		{
			bulkUserBean.setUsersRoleTypeList(new ArrayList());
		}
		return bulkUserBean;
	}
	
	private void setFieldsforRole(BulkBusinessUserCreationBean bulkUserBean, HttpServletRequest request)
	{
		if (bulkUserBean.getNewUsersRoleType() != null
				&& !bulkUserBean.getNewUsersRoleType().equals("-1")) {
			if (bulkUserBean.getNewUsersRoleType().equals(
					String.valueOf(Constants.CEO).trim()) || bulkUserBean.getNewUsersRoleType().equals(
							String.valueOf(Constants.SALES_HEAD).trim()) ) {
					request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_CIRCLE);
					request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_RETAILER_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_NONE);
				}
			else if (bulkUserBean.getNewUsersRoleType().equals(
					String.valueOf(Constants.ZBM).trim()) || bulkUserBean.getNewUsersRoleType().equals(
							String.valueOf(Constants.ZSM).trim()) ) {
					request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_ZONE);
					request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_RETAILER_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_NONE);
				}
			else if(bulkUserBean.getNewUsersRoleType().equals(
					String.valueOf(Constants.DEALER).trim()) )
			{
				request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_RETAILER_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_RETAILER);
				request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_CITY);
			}
			else if( bulkUserBean.getNewUsersRoleType().equals(
					String.valueOf(Constants.TM).trim()) || bulkUserBean.getNewUsersRoleType().equals(
							String.valueOf(Constants.DISTIBUTOR).trim()) ||  bulkUserBean.getNewUsersRoleType().equals(
									String.valueOf(Constants.FOS).trim()) )
			{
				request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_CITY);
				request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_RETAILER_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_NONE);
			}
			else
			{
				request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_NONE);
				request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_RETAILER_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_NONE);
			}
		}
		else
		{
			request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_NONE);
			request.setAttribute(Constants.BULK_BIZ_USER_LEVEL_RETAILER_ATTRIBUTE, Constants.BULK_BIZ_USER_LEVEL_NONE);
		}		
	}
	
	public ActionForward initCreate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger
				.debug("Enter in to initial() method of class BulkBusinessUserAction.");
		ActionForward forward = new ActionForward();
		BulkBusinessUserCreationBean bulkUserBean = (BulkBusinessUserCreationBean) form;
		
		try {
			bulkUserBean = this.resetRequestedValues(bulkUserBean, request);
			bulkUserBean = this.setUsers(bulkUserBean, request);
			this.setFieldsforRole(bulkUserBean, request);
			
			if(bulkUserBean.getNewUsersRoleType() != null)
			{
				if ( bulkUserBean.getNewUsersRoleType().equals(
						String.valueOf(Constants.DISTIBUTOR).trim())) {				
					request.setAttribute("fileFormat","File Format: User Name,Contact No.,Registered Mobile No.,Code, All/Include/Exclude Service Class(A/I/E), Service IDs");				
				}
				else if(bulkUserBean.getNewUsersRoleType().equals("-1"))
				{
					request.setAttribute("fileFormat","");
				}
				else 
				{
					request.setAttribute("fileFormat","File Format: User Name,Contact No.,Registered Mobile No.");
				}
			}
			else
			{
				request.setAttribute("fileFormat","");
			}			

			
			forward = mapping.findForward("initCreate");
			logger.info(" Bulk User Creation Upload page Initialized ");
		} catch (Exception e) {
			logger
					.error(
							"Exception Occured in initial() method of BulkBusinessUserAction",
							e);
		}

		logger
				.debug("Exit from initial() method of class BulkBusinessUserAction.");
		
		return forward;
	}
	
	/*public ActionForward testBulkUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		BulkBizUserCreationServiceImpl service = new BulkBizUserCreationServiceImpl();
		BulkBizUserCreationDTO dto = new BulkBizUserCreationDTO();
		ArrayList fileList = service.getScheduledFilesforBulkUserCreation();
		UserDetailsBean userBean = (UserDetailsBean) request.getSession().getAttribute(Constants.USER_INFO);
		if(fileList != null && fileList.size() > 0 )
		{
			Iterator iterator = fileList.iterator();
			int i = 0;
			while(iterator.hasNext())
			{
				dto = (BulkBizUserCreationDTO)iterator.next();
				service.uploadFile(dto, Integer.parseInt(userBean
						.getUserId()));
				i++;
			}
		}
		
		return  mapping.findForward("test");
	}*/
	
	/*public ActionForward createBulkUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); 
		BulkBizUserCreationDAO dao = new BulkBizUserCreationDAO();
		HttpSession session = request.getSession();
		UserDetailsBean userBean = (UserDetailsBean) session.getAttribute(Constants.USER_INFO);
		BulkBusinessUserCreationBean bulkUserBean = (BulkBusinessUserCreationBean) form;
		
		BulkBizUserCreationDTO bulkBizUserCreationDTO = new BulkBizUserCreationDTO();
		FormFile file = null;
		String circleCode = null;
		File fileRef = null;
		BufferedWriter brWriter = null;
		MessageResources messageResources = getResources(request);
		try {
			
			if(bulkUserBean.getUploadFile1() != null && bulkUserBean.getUploadFile1().getFileSize()!=0 )
			{
				circleCode = bulkUserBean.getCircle1();
				file = bulkUserBean.getUploadFile1();
				/** validation for empty file * 
				if (file.getFileData().length == 0) {
					errors.add("error.empty.file", new ActionError(
					"error.empty.file"));
					saveErrors(request, errors);
					logger.info("The file uploaded by "+userBean.getLoginId()+" is an empty file.");
					//return initial(mapping, form, request, response);
					return this.initCreate(mapping, form, request, response);				
				}
				String fileStoragePath = messageResources.getMessage(Constants.FILE_STORAGE_PATH);
				/*******************************************************************
				 * validation for the maximum file size. maximum file size allowed
				 * to upload is 8 mb
				 *****************************************************************
				if (file.getFileSize() > Integer.parseInt(messageResources
						.getMessage(Constants.MAX_FILE_SIZE))) {
					errors.add("error.max.file.size", new ActionError(
					"error.max.file.size"));
					saveErrors(request, errors);
					logger.info("The file uploaded by "+userBean.getLoginId()+" is more then 8MB.");
					//return initial(mapping, form, request, response);
					return this.initCreate(mapping, form, request, response);
				}
				int index = file.getFileName().lastIndexOf(".");
				String fileNameDataBase = file.getFileName().substring(0, index)
				+ "-"
				+ Utility
				.getDateAsString(new Date(), "dd-MM-yyyy-hh-mm-ss")
				+ file.getFileName().substring(index);
	
				String fileFullPath = fileStoragePath + fileNameDataBase;
	
				String contents = new String(file.getFileData());
	
				fileRef = new File(fileFullPath);
				brWriter = new BufferedWriter(new FileWriter(fileRef));
				brWriter.write(contents);
				brWriter.close();
	
				bulkBizUserCreationDTO.setCircleCode(circleCode);
				bulkBizUserCreationDTO.setRelatedFileId(0);
				bulkBizUserCreationDTO.setOriginalFileName(file.getFileName());
				bulkBizUserCreationDTO.setUploadedBy(userBean.getLoginId());
				bulkBizUserCreationDTO.setIntUploadedBy(Integer.parseInt(userBean
						.getUserId()));
				
				bulkBizUserCreationDTO.setParentId(Integer.parseInt(bulkUserBean.getParent1()));
				bulkBizUserCreationDTO.setUserMasterId(Integer.parseInt(bulkUserBean.getNewUsersRoleType()));
				if(bulkUserBean.getZone1() != null )
				{
					bulkBizUserCreationDTO.setZoneId(Integer.parseInt(bulkUserBean.getZone1()));
				}
				else
				{
					bulkBizUserCreationDTO.setZoneId(-1);
				}
				if(bulkUserBean.getCity1() != null)
				{
					bulkBizUserCreationDTO.setLocationId(Integer.parseInt(bulkUserBean.getCity1()));
				}
				else
				{
					bulkBizUserCreationDTO.setLocationId(-1);
				}
				
				/** * Saving file name in the database **
				dao.fileInfoForBizUser(fileRef.getName(),
						bulkBizUserCreationDTO, Constants.FILE_STATUS_SAVED,
						Constants.FILE_FAILED_MSG_SAVED,
						Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
			}

			
			if(bulkUserBean.getUploadFile2() != null && bulkUserBean.getUploadFile2().getFileSize()!=0 )
			{
				circleCode = bulkUserBean.getCircle2();
				file = bulkUserBean.getUploadFile2();
				/** validation for empty file * 
				if (file.getFileData().length == 0) {
					errors.add("error.empty.file", new ActionError(
					"error.empty.file"));
					saveErrors(request, errors);
					logger.info("The file uploaded by "+userBean.getLoginId()+" is an empty file.");
					//return initial(mapping, form, request, response);
					return this.initCreate(mapping, form, request, response);				
				}
				String fileStoragePath = messageResources.getMessage(Constants.FILE_STORAGE_PATH);
				/*******************************************************************
				 * validation for the maximum file size. maximum file size allowed
				 * to upload is 8 mb
				 *****************************************************************
				if (file.getFileSize() > Integer.parseInt(messageResources
						.getMessage(Constants.MAX_FILE_SIZE))) {
					errors.add("error.max.file.size", new ActionError(
					"error.max.file.size"));
					saveErrors(request, errors);
					logger.info("The file uploaded by "+userBean.getLoginId()+" is more then 8MB.");
					//return initial(mapping, form, request, response);
					return this.initCreate(mapping, form, request, response);
				}
				int index = file.getFileName().lastIndexOf(".");
				String fileNameDataBase = file.getFileName().substring(0, index)
				+ "-"
				+ Utility
				.getDateAsString(new Date(), "dd-MM-yyyy-hh-mm-ss")
				+ file.getFileName().substring(index);
	
				String fileFullPath = fileStoragePath + fileNameDataBase;
	
				String contents = new String(file.getFileData());
	
				fileRef = new File(fileFullPath);
				brWriter = new BufferedWriter(new FileWriter(fileRef));
				brWriter.write(contents);
				brWriter.close();
	
				bulkBizUserCreationDTO.setCircleCode(circleCode);
				bulkBizUserCreationDTO.setRelatedFileId(0);
				bulkBizUserCreationDTO.setOriginalFileName(file.getFileName());
				bulkBizUserCreationDTO.setUploadedBy(userBean.getLoginId());
				bulkBizUserCreationDTO.setIntUploadedBy(Integer.parseInt(userBean
						.getUserId()));
				
				bulkBizUserCreationDTO.setParentId(Integer.parseInt(bulkUserBean.getParent2()));
				bulkBizUserCreationDTO.setUserMasterId(Integer.parseInt(bulkUserBean.getNewUsersRoleType()));
				if(bulkUserBean.getZone2() != null )
				{
					bulkBizUserCreationDTO.setZoneId(Integer.parseInt(bulkUserBean.getZone2()));
				}
				else
				{
					bulkBizUserCreationDTO.setZoneId(-1);
				}
				if(bulkUserBean.getCity2() != null)
				{
					bulkBizUserCreationDTO.setLocationId(Integer.parseInt(bulkUserBean.getCity2()));
				}
				else
				{
					bulkBizUserCreationDTO.setLocationId(-1);
				}
				
				/** * Saving file name in the database **
				dao.fileInfoForBizUser(fileRef.getName(),
						bulkBizUserCreationDTO, Constants.FILE_STATUS_SAVED,
						Constants.FILE_FAILED_MSG_SAVED,
						Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
			}

			
			if(bulkUserBean.getUploadFile3() != null && bulkUserBean.getUploadFile3().getFileSize()!=0 )
			{
				circleCode = bulkUserBean.getCircle3();
				file = bulkUserBean.getUploadFile3();
				/** validation for empty file * 
				if (file.getFileData().length == 0) {
					errors.add("error.empty.file", new ActionError(
					"error.empty.file"));
					saveErrors(request, errors);
					logger.info("The file uploaded by "+userBean.getLoginId()+" is an empty file.");
					//return initial(mapping, form, request, response);
					return this.initCreate(mapping, form, request, response);				
				}
				String fileStoragePath = messageResources.getMessage(Constants.FILE_STORAGE_PATH);
				/*******************************************************************
				 * validation for the maximum file size. maximum file size allowed
				 * to upload is 8 mb
				 *****************************************************************
				if (file.getFileSize() > Integer.parseInt(messageResources
						.getMessage(Constants.MAX_FILE_SIZE))) {
					errors.add("error.max.file.size", new ActionError(
					"error.max.file.size"));
					saveErrors(request, errors);
					logger.info("The file uploaded by "+userBean.getLoginId()+" is more then 8MB.");
					//return initial(mapping, form, request, response);
					return this.initCreate(mapping, form, request, response);
				}
				int index = file.getFileName().lastIndexOf(".");
				String fileNameDataBase = file.getFileName().substring(0, index)
				+ "-"
				+ Utility
				.getDateAsString(new Date(), "dd-MM-yyyy-hh-mm-ss")
				+ file.getFileName().substring(index);
	
				String fileFullPath = fileStoragePath + fileNameDataBase;
	
				String contents = new String(file.getFileData());
	
				fileRef = new File(fileFullPath);
				brWriter = new BufferedWriter(new FileWriter(fileRef));
				brWriter.write(contents);
				brWriter.close();
	
				bulkBizUserCreationDTO.setCircleCode(circleCode);
				bulkBizUserCreationDTO.setRelatedFileId(0);
				bulkBizUserCreationDTO.setOriginalFileName(file.getFileName());
				bulkBizUserCreationDTO.setUploadedBy(userBean.getLoginId());
				bulkBizUserCreationDTO.setIntUploadedBy(Integer.parseInt(userBean
						.getUserId()));
				
				bulkBizUserCreationDTO.setParentId(Integer.parseInt(bulkUserBean.getParent3()));
				bulkBizUserCreationDTO.setUserMasterId(Integer.parseInt(bulkUserBean.getNewUsersRoleType()));
				if(bulkUserBean.getZone3() != null )
				{
					bulkBizUserCreationDTO.setZoneId(Integer.parseInt(bulkUserBean.getZone3()));
				}
				else
				{
					bulkBizUserCreationDTO.setZoneId(-1);
				}
				if(bulkUserBean.getCity3() != null)
				{
					bulkBizUserCreationDTO.setLocationId(Integer.parseInt(bulkUserBean.getCity3()));
				}
				else
				{
					bulkBizUserCreationDTO.setLocationId(-1);
				}
				
				/** * Saving file name in the database **
				dao.fileInfoForBizUser(fileRef.getName(),
						bulkBizUserCreationDTO, Constants.FILE_STATUS_SAVED,
						Constants.FILE_FAILED_MSG_SAVED,
						Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
			}			
			
			errors.add("file.uploaded", new ActionError("file.uploaded"));
			errors.add("file.uploaded1", new ActionError("file.uploaded1"));
			saveErrors(request, errors);			
			forward = mapping.findForward("initCreate");
			logger.info(userBean.getLoginId() + " Bulk Business User file saved successfully");
			
			bulkUserBean = this.blankAll(bulkUserBean);
			this.setFieldsforRole(bulkUserBean, request);
			
			request.setAttribute("fileFormat","");
			
		} catch (DAOException e) {
			errors.add("name", new ActionError(Constants.ERROR_KEY, file
					.getFileName()
					+ "  "
					+ messageResources.getMessage(Constants.INVALID_FILE)));
			saveErrors(request, errors);
			logger.info(userBean.getLoginId()+" there was an exception in uploading the file.");
			closeResources(bulkUserBean, file);
			return this.initCreate(mapping, form, request, response);
		} finally {
			closeResources(bulkUserBean, file);
		}
		return forward;	
	}*/	
	
	/**
	 * This method responsible for close all IO resources
	 * 
	 * @param swap
	 *            :BulkUploadBean
	 * @param file
	 *            :FormFile
	 * @throws IOException
	 */

	private void closeResources(BulkBusinessUserCreationBean swap, FormFile file)
	throws IOException {
		if (swap != null && swap.getUploadFile1() != null) {
			swap.getUploadFile1().destroy();
		}
		if (swap != null && swap.getUploadFile2() != null) {
			swap.getUploadFile2().destroy();
		}
		if (swap != null && swap.getUploadFile3() != null) {
			swap.getUploadFile3().destroy();
		}
		if (file != null) {
			file.destroy();
		}

	}
}

