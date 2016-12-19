/**************************************************************************
 * File     : MisReportAction.java
 * Author   : Aalok Sharma
 * Created  : Oct 3, 2008
 * Modified : Oct 3, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 3, 2008 	Aalok Sharma	First Cut.
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

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
import org.apache.struts.util.LabelValueBean;

import com.ibm.core.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.beans.MisReportBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
import com.ibm.virtualization.ussdactivationweb.reports.dao.MisReportDaoImpl;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This action class contains methods for MisReportAction
 * 
 * @author Aalok Sharma
 * @version 1.0
 ******************************************************************************/
public class MisReportAction extends DispatchAction {

	/** getting logger refrence * */
	private static final Logger logger = Logger.getLogger(MisReportAction.class
			.toString());

	/**
	 * This method is used to initialize the Mis report page.
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 */

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Entering method-init()");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		MisReportBean misReportBean = (MisReportBean) form;
		UserDetailsBean userBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		MisReportDaoImpl misReportDaoImpl = new MisReportDaoImpl();

		try {
			setInitRequestData(request, misReportBean, userBean,
					misReportDaoImpl);
		} catch (DAOException e) {
			logger.error("Exception occured in init() method : " + e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
		}
		forward = mapping.findForward(Constants.INIT);
		logger.info("Exiting method- init()");
		return forward;
	}

	/**
	 * This method get the data by file information by month on the basis of
	 * circleCode, firstDateOfMonth, lastDateOfMonth, reportId
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 */

	public ActionForward getFileInfoData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Entering method-getFileInfoData()");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		MisReportBean misReportBean = (MisReportBean) form;

		UserDetailsBean userBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		MisReportDaoImpl misReportDaoImpl = new MisReportDaoImpl();
		Calendar calendar = Calendar.getInstance();
		String selectedReportMonth;
		String firstDateOfMonth = "";
		String lastDateOfMonth = "";
		ArrayList fileInfoDataList = null;

		try {
			setInitRequestData(request, misReportBean, userBean,
					misReportDaoImpl);
			if ((Utility.getValueFromBundle(Constants.DAILY,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
					.equalsIgnoreCase(misReportBean.getReportType())) {
				if ((Utility.getValueFromBundle(
						Constants.MIS_REPORT_LAST_SEVEN_DAYS,
						Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
						.equalsIgnoreCase(misReportBean.getReportDate())) {
					if ("N".equalsIgnoreCase(Utility.getValueFromBundle(
							Constants.DAILY_REPORT_INCLUDE_CURRENT_DAY,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						// This line added because current day is not required.
						calendar.add(Calendar.DAY_OF_MONTH, -1);
					}
					lastDateOfMonth = Utility.getDateAsString(calendar
							.getTime(), "dd/MM/yyyy");
					calendar.add(Calendar.DAY_OF_MONTH, -6);
					firstDateOfMonth = Utility.getDateAsString(calendar
							.getTime(), "dd/MM/yyyy");

					fileInfoDataList = null;/*misReportDaoImpl.getFileInfoDataByMonth(
							misReportBean.getCircleCode(), firstDateOfMonth,
							lastDateOfMonth, misReportBean.getReportId());*/
					// used for DEMO //fileInfoDataList =
					// misReportDaoImpl.getFileInfoDataByMonth("AS",
					// "1/10/2008","7/10/2008", "8");
				} else {

					fileInfoDataList = null;/*misReportDaoImpl.getFileInfoData(
							misReportBean.getCircleCode(), misReportBean
									.getReportDate(), misReportBean
									.getReportId());*/
					// used for DEMO//fileInfoDataList=
					// misReportDaoImpl.getFileInfoData("AS", "7/10/2008",
					// misReportBean.getReportId());
				}
			}
			if ((Utility.getValueFromBundle(Constants.MONTHLY,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
					.equalsIgnoreCase(misReportBean.getReportType())) {
				selectedReportMonth = misReportBean.getReportDate();

				// Calculating the first day of the selected month
				calendar.setTime(Utility.getDateAsDate(selectedReportMonth, "MM/yyyy"));
				
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				firstDateOfMonth = Utility.getDateAsString(calendar.getTime(),
						"dd/MM/yyyy");

				// Calculating the last day of the selected month
				calendar.set(Calendar.DAY_OF_MONTH, calendar
						.getActualMaximum(Calendar.DATE));
				lastDateOfMonth = Utility.getDateAsString(calendar.getTime(),
						"dd/MM/yyyy");

				fileInfoDataList = null;/*misReportDaoImpl.getFileInfoDataByMonth(
						misReportBean.getCircleCode(), firstDateOfMonth,
						lastDateOfMonth, misReportBean.getReportId());*/
				// used for DEMO//fileInfoDataList =
				// misReportDaoImpl.getFileInfoDataByMonth("AS",
				// "1/9/2008","30/9/2008", misReportBean.getReportId() );
			}
			misReportBean.setFileInfoDataList(fileInfoDataList);
			if (fileInfoDataList.isEmpty()) {
				errors.add("msg.no.data.found", new ActionError(
						"msg.no.data.found"));
				saveErrors(request, errors);
			}
		} catch (DAOException e) {
			logger
					.error("Exception occured in getFileInfoData() method : "
							+ e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
		}
		forward = mapping.findForward(Constants.INIT);
		logger.info("Exiting method- getFileInfoData()");
		return forward;
	}

	/**
	 * It gets all the report Id then this method set the initial data to the
	 * form.
	 * 
	 * @param request :HttpServletRequest
	 * @param misReportBean MisReportBean class file object.
	 * @param userBean UserDetailsBean class file object. 
	 * @param misReportDaoImpl Dao class for mis report object.
	 * 
	 * @throws com.ibm.virtualization.ussdactivationweb.exception.DAOException
	 */
	private void setInitRequestData(HttpServletRequest request,
			MisReportBean misReportBean, UserDetailsBean userBean,
			MisReportDaoImpl misReportDaoImpl) throws Exception {
		misReportBean.setBusinessUserList(null);
		ArrayList circleList = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		LinkedHashMap dateWiseMap = new LinkedHashMap();

		if (userBean != null) {
			if (userBean.getCircleId() == null) {
				circleList = ViewEditCircleMasterDAOImpl.getCircleList();
				misReportBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				LabelValueBean lvBean = new LabelValueBean(userBean
						.getCircleId(), userBean.getCircleId()+"-"+userBean.getCircleName());
				misReportBean.setCircleCode(userBean.getCircleId());
				circleList.add(lvBean);
			}
		}
		request.setAttribute(Constants.CIRCLE_LIST, circleList);
		request.setAttribute("reportIdNameMap", misReportDaoImpl
				.getReportIdsByReportType(misReportBean.getReportType()));

		if ((Utility.getValueFromBundle(Constants.DAILY,
				Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
				.equalsIgnoreCase(misReportBean.getReportType())) {

			// Add the last seven days functionality
			dateWiseMap.put(Utility.getValueFromBundle(
					Constants.MIS_REPORT_LAST_SEVEN_DAYS,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE), Utility
					.getValueFromBundle(Constants.MIS_REPORT_LAST_SEVEN_DAYS,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));

			// 0-7 -- 7 dayas before report
			for (int i = 0; i < Integer.parseInt(Utility.getValueFromBundle(
					Constants.DAILY_REPORT_PREVIOUS_DAYS,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE)); i++) {
				if (i == 0) {
					if ("N".equalsIgnoreCase(Utility.getValueFromBundle(
							Constants.DAILY_REPORT_INCLUDE_CURRENT_DAY,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						// This line added because current day is not required.
						calendar.add(Calendar.DAY_OF_MONTH, -1);
					}
					dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
							"dd/MM/yyyy"), Utility.getDateAsString(calendar
							.getTime(), "dd-MMM-yyyy"));
				} else {
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
							"dd/MM/yyyy"), Utility.getDateAsString(calendar
							.getTime(), "dd-MMM-yyyy"));
				}
			}
		}
		if ((Utility.getValueFromBundle(Constants.MONTHLY,
				Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
				.equalsIgnoreCase(misReportBean.getReportType())) {
			// 0-3 -- 3 months before report
			for (int i = 0; i < Integer.parseInt(Utility.getValueFromBundle(
					Constants.MONTHLY_REPORT_PREVIOUS_DAYS,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE)); i++) {
				if (i == 0) {
					if ("N".equalsIgnoreCase(Utility.getValueFromBundle(
							Constants.MONTHLY_REPORT_INCLUDE_CURRENT_DAY,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						// This line added because current Month is not
						// required.
						calendar.add(Calendar.MONTH, -1);
					}
					dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
							"MM/yyyy"), Utility.getDateAsString(calendar.getTime(),
							"MMM-yyyy"));
				} else {
					calendar.add(Calendar.MONTH, -1);
					dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
							"MM/yyyy"), Utility.getDateAsString(calendar.getTime(),
							"MMM-yyyy"));
				}
			}
		}
		request.setAttribute("dateWiseMap", dateWiseMap);

	}
	
	

	/**
	 * This method is used to initialize the Mis Pending report page.
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 */

	public ActionForward initMisPendingRpt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Entering method-init()");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		MisReportBean misReportBean = (MisReportBean) form;
		UserDetailsBean userBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		
		ArrayList circleList = new ArrayList();
		try {
			if (userBean != null) {
				if (userBean.getCircleId() == null) {
					circleList = ViewEditCircleMasterDAOImpl.getCircleList();
					misReportBean.setUserRole(Constants.SUPER_ADMIN);
				} else {
					misReportBean.setCircleCode(userBean.getCircleId());
					}
			}
			
            LinkedHashMap dateWiseMap = getLastSevenDaysDate();
			request.setAttribute(Constants.CIRCLE_LIST, circleList);
			request.setAttribute("dateWiseMap", dateWiseMap);
			forward = mapping.findForward("initMisPendingRpt");
		} catch (DAOException e) {
			logger.error("Exception occured in init() method : " + e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
			request.setAttribute(Constants.CIRCLE_LIST, circleList);
			forward = mapping.findForward("initMisPendingRpt");
		}
		logger.info("Exiting method- init()");
		return forward;
	}

	/**
	 * @return
	 */
	private LinkedHashMap getLastSevenDaysDate() {
		//			 Add the last seven days functionality
					
					LinkedHashMap dateWiseMap = new LinkedHashMap();
					Calendar calendar = Calendar.getInstance();
					dateWiseMap.put(Utility.getValueFromBundle(
							Constants.MIS_REPORT_LAST_SEVEN_DAYS,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE), Utility
							.getValueFromBundle(Constants.MIS_REPORT_LAST_SEVEN_DAYS,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		
					// 0-7 -- 7 dayas before report
					for (int i = 0; i < Integer.parseInt(Utility.getValueFromBundle(
							Constants.DAILY_REPORT_PREVIOUS_DAYS,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE)); i++) {
						if (i == 0) {
							if ("N".equalsIgnoreCase(Utility.getValueFromBundle(
									Constants.DAILY_REPORT_INCLUDE_CURRENT_DAY,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
								// This line added because current day is not required.
								calendar.add(Calendar.DAY_OF_MONTH, -1);
							}
							dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
									"dd/MM/yyyy"), Utility.getDateAsString(calendar
									.getTime(), "dd-MMM-yyyy"));
						} else {
							calendar.add(Calendar.DAY_OF_MONTH, -1);
							dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
									"dd/MM/yyyy"), Utility.getDateAsString(calendar
									.getTime(), "dd-MMM-yyyy"));
						}
					}
		return dateWiseMap;
	}
	
	
	/**
	 * This method get the data by file information 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 */

	public ActionForward getPendigRptFileInfoData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Entering method-getPendigRptFileInfoData()");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		MisReportBean misReportBean = (MisReportBean) form;

		UserDetailsBean userBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		MisReportDaoImpl misReportDaoImpl = new MisReportDaoImpl();
		ArrayList fileInfoDataList = null;
		ArrayList circleList = new ArrayList();
		String beforeSevendayDateFromCurrDate = "";
		String todaysDateOfMonth = "";
		Calendar calendar = Calendar.getInstance();
		try {

			if (userBean != null) {
				if (userBean.getCircleId() == null) {
					circleList = ViewEditCircleMasterDAOImpl.getCircleList();
					misReportBean.setUserRole(Constants.SUPER_ADMIN);
				} else {
					LabelValueBean lvBean = new LabelValueBean(userBean
							.getCircleId(), userBean.getCircleId()+"-"+userBean.getCircleName());
					misReportBean.setCircleCode(userBean.getCircleId());
					circleList.add(lvBean);
					}
			}
			
			if ((Utility.getValueFromBundle(
					Constants.MIS_REPORT_LAST_SEVEN_DAYS,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
					.equalsIgnoreCase(misReportBean.getReportDate())) {
				if ("N".equalsIgnoreCase(Utility.getValueFromBundle(
						Constants.DAILY_REPORT_INCLUDE_CURRENT_DAY,
						Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
					// This line added because current day is not required.
					calendar.add(Calendar.DAY_OF_MONTH, -1);
				}
				todaysDateOfMonth = Utility.getDateAsString(calendar
						.getTime(), "dd/MM/yyyy");
				calendar.add(Calendar.DAY_OF_MONTH, -6);
				beforeSevendayDateFromCurrDate = Utility.getDateAsString(calendar
						.getTime(), "dd/MM/yyyy");

				fileInfoDataList = null;/*misReportDaoImpl.getFileInfoDataByMonth(
						misReportBean.getCircleCode(), todaysDateOfMonth,beforeSevendayDateFromCurrDate, 
						misReportBean.getReportId());*/
			} else {
				
				 /*** getting file info for selected circle and selected date from V_FILE_INFO table ***/
				fileInfoDataList = null;/*misReportDaoImpl.getFileInfoData(
						misReportBean.getCircleCode(), misReportBean
								.getReportDate(), misReportBean
								.getReportId());*/
			}
           
			if (fileInfoDataList.isEmpty()) {
				errors.add("msg.no.data.found", new ActionError(
						"msg.no.data.found"));
				saveErrors(request, errors);
			}
			request.setAttribute(Constants.CIRCLE_LIST, circleList);
			request.setAttribute("dateWiseMap", getLastSevenDaysDate());
			misReportBean.setFileInfoDataList(fileInfoDataList);
			
			forward = mapping.findForward("initMisPendingRpt");
			
		} catch (DAOException e) {
			logger
					.error("Exception occured in getPendigRptFileInfoData() method : "
							+ e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward("initMisPendingRpt");
			}
			request.setAttribute(Constants.CIRCLE_LIST, circleList);
			request.setAttribute("dateWiseMap", getLastSevenDaysDate());
             
		}
		logger.info("Exiting method- getPendigRptFileInfoData()");
		return forward;
	}

	

}