/*
 * Created on July 22, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.virtualization.ussdactivationweb.pagination;
/**************************************************************************
 * File     : PaginationUtils.java
 * Author   : aalok
 * Created  : Jul 24, 2008
 * Modified : Jul 24, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Jul 24, 2008	aalok	First Cut.
 * V0.2		Jul 24, 2008  	aalok	First modification
 **************************************************************************
 *
 * Copyright @ 2008 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.PropertyReader;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class is used for providing Pagination in Reports Generated under 
 * Transaction Report
 * 
 * 
 * @author aalok
 * @version 1.0
 ******************************************************************************/

public class PaginationUtils {
	private static Logger logger = Logger.getLogger(PaginationUtils.class
			.getName());
	
	/**
	 * setting the lower and upper bound for Pagination
	 * 
	 * @param request HttpServletRequest
	 * @param noofpages 
	 */
	public static Pagination setPaginationinRequest(HttpServletRequest request) {
		Pagination pagination = new Pagination();
		int lb = 0, ub = 0, pg = 0;

		/* initialize pagination size */
		int recordsPerPage = initializePaginationSize();
		int linksPerPage=initializeLinkSize();

		/* variables used in paging */
		String selected_page = request.getParameter("page1");
		if (selected_page == null || selected_page.equals("")) {
			// page not selected
			selected_page = "";
			lb = 0;
			ub = recordsPerPage;
		} else {
			// page is selected
			pg = Integer.valueOf(selected_page).intValue();
			lb = recordsPerPage * (pg - 1);
			ub = lb + recordsPerPage;
			//lb++;
			logger.info("test lb=" + lb);
		}
		int nextPage;
		int prevPage;
		
		if (request.getParameter("page1") != null && !request.getParameter("page1").equalsIgnoreCase("")) {
			nextPage = (Integer.parseInt(request.getParameter("page1")) + 1);
			prevPage = (Integer.parseInt(request.getParameter("page1")) - 1);
		} else {
			nextPage = 2;
			prevPage = 0;
		}
		request.setAttribute("NEXT", new Integer(nextPage));
		request.setAttribute("PRE", new Integer(prevPage));
		request.setAttribute("SELECTED_PAGE", selected_page);
		request.setAttribute("LinksPerPage", new Integer(linksPerPage));
		
		pagination.setUpperBound(ub);
		pagination.setLowerBound(lb);

		return pagination;
	}
	
	/**
	 * This method take the number of row and 
	 * return the number of pages.
	 * 
	 * @param noofRow int Number of rows
	 * @return noofpages - return the number of pages.
	 */
	public static int getPaginationSize(int noofRow) {
		int recordsPerPage = initializePaginationSize();
		int noofpages;
		if (noofRow % recordsPerPage == 0)
			noofpages = noofRow / recordsPerPage;
		else
			noofpages = noofRow / recordsPerPage + 1;
		return noofpages;
	}
	/**
	 * This method initilize the pagination size.
	 * 
	 * @return recordsPerPage - Return record per page.
	 */
	private static int initializePaginationSize() {
		int recordsPerPage = 0;
		try {
			recordsPerPage = Integer.parseInt(Utility.getValueFromBundle(
					Constants.PAGINATION_RECORDS_PER_PAGE, Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		} catch (NumberFormatException e) {
			logger.error("The number is not a valid number format."
					+ "Exception Message: ", e);
			/** if any exception occurs then assign the default value */
			recordsPerPage = 10;
		} catch (Exception e) {
			logger.error("caught Exception"
					+ e.getMessage());
		}
		return recordsPerPage;
	}

	/**
	 * set the number of links to be shown in each page. Fetch this count from
	 * properties file.
	 * 
	 * @return linksPerPage  return how many link per page is there.
	 */
	private static int initializeLinkSize() {
		int linksPerPage = 0;
		try {
			linksPerPage = Integer.parseInt(Utility.getValueFromBundle(
					Constants.PAGINATION_LINKS_PER_PAGE, Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		} catch (NumberFormatException e) {
			logger.error("The number is not a valid number format."
					+ "Exception Message: ", e);
			/** if any exception occurs then assign the default value */
			linksPerPage = 10;
		} catch (Exception e) {
			logger.error("caught Exception"
					+ e.getMessage());
		}
		return linksPerPage;
	}
}
