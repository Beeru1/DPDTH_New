/**
 * 
 */
package com.ibm.virtualization.recharge.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Utility;

/**
 * This is a Generic Utility Class which provides helper methods for
 * Virtualization Recharge.
 * 
 * @author Mohit
 * 
 */
public class VirtualizationUtils {

	/*
	 * Logger for this class.
	 * 
	 */
	private static Logger logger = Logger.getLogger(VirtualizationUtils.class
			.getName());

	/**
	 * setting the lower and upper bound for Pagination
	 * 
	 * @param request
	 * @param noofpages
	 */
	public static Pagination setPaginationinRequest(HttpServletRequest request) {
		Pagination pagination = new Pagination();
		int lb = 0, ub = 0, pg = 0;

		/* initialize pagination size */
		int recordsPerPage = Utility.initializePaginationSize();
		int linksPerPage=Utility.initializeLinkSize();
		
		

		/* variables used in paging */
		String selected_page = request.getParameter("page");
		if (selected_page == null || selected_page == ""
				|| selected_page.equals("")) {
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
		
		if (request.getParameter("page") != null && !request.getParameter("page").equalsIgnoreCase("")) {
			nextPage = (Integer.parseInt(request.getParameter("page")) + 1);
			prevPage = (Integer.parseInt(request.getParameter("page")) - 1);
		} else {
			nextPage = 2;
			prevPage = 0;
		}
		request.setAttribute("NEXT", nextPage);
		request.setAttribute("PRE", prevPage);
		request.setAttribute("SELECTED_PAGE", selected_page);
		request.setAttribute("LinksPerPage", linksPerPage);
		
		pagination.setUpperBound(ub);
		pagination.setLowerBound(lb);

		return pagination;
	}
}
