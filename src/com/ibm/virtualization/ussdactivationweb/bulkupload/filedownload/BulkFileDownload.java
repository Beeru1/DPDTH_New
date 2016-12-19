/**************************************************************************
 * File     : BulkFileDownload.java
 * Author   : Abhipsa
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Abhipsa	First Cut.
 * V0.2		Dec 8, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.bulkupload.filedownload;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 *
 */
public class BulkFileDownload extends HttpServlet{
	private static Logger logger = Logger.getLogger(BulkFileDownload.class
			.getName());

	/**
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Entering PrepaidFileDownload doPost().. ");
		String fileName = null;
		String fileStoragePath = null;
		try {			
			fileName = req.getParameter("fileName");
			fileStoragePath = Utility.getValueFromBundle(
					Constants.FILE_STORAGE_PATH,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
		
			resp.setContentType("application/x-download");
			resp.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
			ServletOutputStream out = resp.getOutputStream();

			BufferedInputStream bufferedinputstream = new BufferedInputStream(
					new FileInputStream(fileStoragePath+fileName));
			int i;
			byte abyte0[] = new byte[4096];
			while ((i = bufferedinputstream.read(abyte0, 0, 4096)) != -1)
				out.write(abyte0, 0, i);
			bufferedinputstream.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.debug("Exception in PrepaidFileDownload: " + e.getMessage());
			e.printStackTrace();

		}
	}
}
