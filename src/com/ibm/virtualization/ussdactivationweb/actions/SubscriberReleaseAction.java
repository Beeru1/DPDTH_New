/**
 * 
 */
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

import com.ibm.core.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.beans.SubscriberBean;
import com.ibm.virtualization.ussdactivationweb.dao.SubscriberReleaseDAO;
import com.ibm.virtualization.ussdactivationweb.pagination.Pagination;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 *
 */
public class SubscriberReleaseAction extends DispatchAction {
	
	/** getting logger refrence * */
	private static final Logger logger = Logger.getLogger(SubscriberReleaseAction.class
			.toString());
	
	/**
	 * This method is used to initialize Released Subscriber page.
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
		ActionForward forward = new ActionForward(); // return value
		Calendar calendar = Calendar.getInstance();
		LinkedHashMap dateWiseMap = new LinkedHashMap();
		
		try {
	
//			 0-7 -- 7 days before report
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
								"MM/dd/yyyy"), Utility.getDateAsString(calendar
								.getTime(), "MMM-dd-yyyy"));
					} else {
						calendar.add(Calendar.DAY_OF_MONTH, -1);
						dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
								"MM/dd/yyyy"), Utility.getDateAsString(calendar
								.getTime(), "MMM-dd-yyyy"));
					}
				}
			
			request.setAttribute("dateWiseMap", dateWiseMap);
		
		} catch (DAOException e) {
			logger.error("Exception occured in init() method : " + e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
		}catch (Exception e) {
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
	
	public ActionForward getReleaseSubList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); 
		Calendar calendar = Calendar.getInstance();
		LinkedHashMap dateWiseMap = new LinkedHashMap();
		SubscriberReleaseDAO subscriberReleaseDAO = new SubscriberReleaseDAO();
		SubscriberBean bean = (SubscriberBean)form;
		ArrayList subList = new ArrayList();
		try {
			
			int noofpages = 0;
			String releasedDate = bean.getReleasedDate();
			
			noofpages=subscriberReleaseDAO.countReleaseSubs(releasedDate);
			request.setAttribute("PAGES", new Integer(noofpages));
			
			Pagination pagination = PaginationUtils
					.setPaginationinRequest(request);
			
			subList=subscriberReleaseDAO.getReleaseSubs(releasedDate,pagination
					.getLowerBound(),pagination.getUpperBound());
			if(subList.isEmpty()){
				bean.setMessage(Utility.getValueFromBundle(
						Constants.MESSAGE_SUB,
						Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			}else{				
				bean.setSubList(subList);
			}

			// 0-7 -- 7 days before report
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
								"MM/dd/yyyy"), Utility.getDateAsString(calendar
								.getTime(), "MMM-dd-yyyy"));
					} else {
						calendar.add(Calendar.DAY_OF_MONTH, -1);
						dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
								"MM/dd/yyyy"), Utility.getDateAsString(calendar
								.getTime(), "MMM-dd-yyyy"));
					}
				}

			request.setAttribute("dateWiseMap", dateWiseMap);
			bean.setReleasedDate(releasedDate);
			
		}catch (DAOException e) {
			logger
			.error("Exception occured in getReleaseSubList() method : "
					+ e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
		}catch (Exception e) {
			logger
			.error("Exception occured in getReleaseSubList() method : "
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
		logger.info("Exiting method- getReleaseSubList()");
		return forward;
		
	}

}
