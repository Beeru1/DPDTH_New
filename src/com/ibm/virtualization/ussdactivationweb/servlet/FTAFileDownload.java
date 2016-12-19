/**************************************************************************
 * File     : PrepaidFileDownload.java
 * Author   : Ankit Kanwar
 * Created  : May 14, 2008
 * Modified : May 14, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		May 14, 2008	Ankit Kanwar   First Cut.
 * V0.2		May 14, 2008	Ankit Kanwar   First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.dto.FileUploadDTO;
import com.ibm.virtualization.ussdactivationweb.reports.dao.MisReportDaoImpl;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class is used to read the file and display it in browser or to save it.
 * 
 * @author Ankit Kanwar
 * @version 1.0
 ******************************************************************************/
public class FTAFileDownload extends HttpServlet {

	private static final long serialVersionUID = 1497182296462436052L;
	private static Logger logger = Logger.getLogger(FTAFileDownload.class
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
		String fileName = null;
		String filePath = null;
		String fileIDStr = null;
		String orgFileName = null;
		String reportType = "0";
		// Properties props = new Properties();
		
		// String reportpath = "C:/";
		logger.debug("###Method PrepaidFileDownload doPost() called");
		try {
			
			fileIDStr = req.getParameter("fileId");
			reportType = req.getParameter("reportType1");
			
			if(reportType=="1" ||reportType=="2" ){
				fileName = req.getParameter("fileName");
				filePath = Utility.getValueFromBundle(
						Constants.FILE_STORAGE_PATH,
						Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
				orgFileName = fileName;
			}else if(reportType.equalsIgnoreCase("SUB_REPORT")){
				fileName = req.getParameter("fileName");
				filePath = Utility.getValueFromBundle(
						Constants.KEY_SUBS_REPORT_FILE_STORAGE_PATH,
						Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
				orgFileName = fileName;
			}else if(reportType.equalsIgnoreCase("PENDING_REPORT")){
				fileName = req.getParameter("fileName");
				filePath = Utility.getValueFromBundle(
						Constants.KEY_MIS_REPORT_FILE_STORAGE_PATH,
						Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
				orgFileName = fileName;
			}else {
				logger.debug("doPost(): fileId is - " + fileIDStr);
				int fileID = (fileIDStr != null) ? Integer.parseInt(fileIDStr) : -1;
				MisReportDaoImpl misReportDaoImpl = new MisReportDaoImpl();
				FileUploadDTO fileUploadDTO = misReportDaoImpl.getFileInfoByFileId(fileID);
				fileName = fileUploadDTO.getFileName();
				filePath = fileUploadDTO.getFilePath();
				orgFileName = fileName;
			}

			//logger.info("###filePath + fileName=" + filePath + fileName);
			
			resp.setContentType("application/x-download");
			resp.setHeader("Content-Disposition", "attachment; filename="
					+ orgFileName);
			ServletOutputStream out = resp.getOutputStream();

			BufferedInputStream bufferedinputstream = new BufferedInputStream(
					new FileInputStream(filePath +fileName));
			// new FileInputStream(reportpath + fileName));
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