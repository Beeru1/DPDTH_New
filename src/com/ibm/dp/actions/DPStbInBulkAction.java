package com.ibm.dp.actions;


import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DPStbInBulkBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPSTBHistDTO;
import com.ibm.dp.dto.DPStbInBulkDTO;
import com.ibm.dp.service.DPStbInBulkService;
import com.ibm.dp.service.impl.DPStbInBulkServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class DPStbInBulkAction extends DispatchAction {

	private Logger logger = Logger.getLogger(DPStbInBulkAction.class.getName());

	private static final String SUCCESS = "success";

	private final long MAX_SIZE_FILES = 1024*1024;

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//logger.info("**********1. DPStbInBulkAction Started*********");
		
		
		HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,(AuthorizationConstants.ROLE_STB_IN_BULK )))
		{
			logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		} 
		
		DPStbInBulkBean stbInBulkBean = (DPStbInBulkBean) form;
		//HttpSession session = request.getSession();
		//ActionErrors errors = new ActionErrors();
		try {
		//	UserSessionContext sessionContext = (UserSessionContext) session
			//		.getAttribute(Constants.AUTHENTICATED_USER);
			stbInBulkBean.setTransMessage("");
			request.setAttribute("transMessage", "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("@1.EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS);
		}
		//logger.info("2.End of DPStbInBulkAction init method********");
		return mapping.findForward(SUCCESS);
	}

	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//logger
			//	.info("**********1. DPStbInBulkAction uploadExcel Started*********");
		DPStbInBulkBean stbInBulkBean = (DPStbInBulkBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		DPStbInBulkService stbService = new DPStbInBulkServiceImpl();
		DPStbInBulkDTO dpStbInBulkDTO = null;
		ActionMessages messages = new ActionMessages();
		session.setAttribute("normalexcel", "normalExcel");
//		Added by Neetika 13Aug BFR 16
		
		session.setAttribute("ReportSTB", "inprogress"); 
		
		//logger
			//	.info("****************uploadExcel information**********************");
		FormFile file = stbInBulkBean.getStbFile();
		//logger.info("****************File name*********************"
			//	+ file.getFileName());
		
		InputStream myxls = file.getInputStream();
		String fileExtension = (file.getFileName().toString()).substring(file
				.getFileName().toString().lastIndexOf(".") + 1, file
				.getFileName().toString().length());
		//logger.info("****************File extension :**********************"
		//		+ fileExtension);
		//logger.info("File Size : " + file.getFileSize());

		if (!fileExtension.equalsIgnoreCase("csv")) {
			//logger
			//		.info("****************STB_FILE_TYPE_WRONG :**********************");
			messages.add("STB_FILE_TYPE_WRONG", new ActionMessage(
					"stb.file.type"));
			saveMessages(request, messages);
			return mapping.findForward(SUCCESS);
		} else if (file.getFileSize() > MAX_SIZE_FILES) {
			//logger
			//		.info("****************STB_Size wrong :**********************");
			messages.add("STB_FILE_SIZE_WRONG", new ActionMessage(
					"stb.file.size"));
			saveMessages(request, messages);
			return mapping.findForward(SUCCESS);
		} else {
			List list = stbService.uploadExcel(file);
			session.removeAttribute("error_file");
			session.setAttribute("error_file", list);
			String stbList = "";
			boolean checkValidate = true;

			if (list.size() > 0) {
				Iterator itt = list.iterator();
				while (itt.hasNext()) {
					dpStbInBulkDTO = (DPStbInBulkDTO) itt.next();
					logger.info("1111111111111::::"+dpStbInBulkDTO.getErr_msg());
					if (dpStbInBulkDTO.getErr_msg() != null
							&& !dpStbInBulkDTO.getErr_msg().equalsIgnoreCase(
									"SUCCESS")) {
						checkValidate = false;
						break;
					}
				}
				if (checkValidate) {
					stbInBulkBean.setError_flag("false");
					DPStbInBulkDTO dpStbInBulk = new DPStbInBulkDTO();
					try {
						if (list.size() > 0) {
							Iterator iter = list.iterator();
							while (iter.hasNext()) {
								dpStbInBulkDTO = (DPStbInBulkDTO) iter.next();
								List stbSrno = dpStbInBulkDTO.getList_srno();
								if (stbSrno != null && stbSrno.size() > 0) {
									for (int i = 0; i < stbSrno.size(); i++) {
										stbList += "'" + stbSrno.get(i) + "',";
										if (i == 99) {
											break;
										}
									}
								}
							}

						}
						//logger
							//	.info("****************STB list *****************"
								//		+ stbList);
						stbList = stbList
								.substring(0, stbList.lastIndexOf(','));
						//logger
							//	.info("****************STB list *****************"
									//	+ stbList);
						dpStbInBulk.setSerial_no(stbList);
						stbInBulkBean = headerbuilder(stbInBulkBean);
						ArrayList serialNoList = stbService.getAvailableSerialNos(dpStbInBulk);
						//logger
							//	.info("****************serialNoList size*****************"
								//		+ serialNoList.size());
						stbInBulkBean.setAvailableSerialNosList(serialNoList);
						request.setAttribute("serialNoList", serialNoList);
					} catch (Exception e) {
						e.printStackTrace();
//Added by Neetika 13Aug BFR 16
						session.setAttribute("ReportSTB", ""); 
					}

				} else {
					stbInBulkBean.setError_flag("true");
					return mapping.findForward(SUCCESS);
				}
			}
		}
		// return mapping.findForward(SUCCESS);
		return mapping.findForward("uploadExcel");

	}

	
	//added by aman for STB Bulk History
	public ActionForward exportToExcelHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("******************************************in exportToExcelHistory action************************* ");
		//logger
		//	.info("**********1. DPStbInBulkAction uploadExcel Started*********");
	DPStbInBulkBean stbInBulkBean = (DPStbInBulkBean) form;
	HttpSession session = request.getSession();
	UserSessionContext sessionContext = (UserSessionContext) session
			.getAttribute(Constants.AUTHENTICATED_USER);
	DPStbInBulkService stbService = new DPStbInBulkServiceImpl();
	DPSTBHistDTO dpStbInBulkDTO = null;
	session.setAttribute("normalexcel", "HistExcel");
	ActionMessages messages = new ActionMessages();
//	Added by Neetika 13Aug BFR 16	
	session.setAttribute("ReportSTB", "inprogress"); 
	//logger
		//	.info("****************uploadExcel information**********************");
	FormFile file = stbInBulkBean.getStbFile();
	//logger.info("****************File name*********************"
		//	+ file.getFileName());
	InputStream myxls = file.getInputStream();
	String fileExtension = (file.getFileName().toString()).substring(file
			.getFileName().toString().lastIndexOf(".") + 1, file
			.getFileName().toString().length());
	//logger.info("****************File extension :**********************"
	//		+ fileExtension);
	//logger.info("File Size : " + file.getFileSize());

	if (!fileExtension.equalsIgnoreCase("csv")) {
		//logger
		//		.info("****************STB_FILE_TYPE_WRONG :**********************");
		messages.add("STB_FILE_TYPE_WRONG", new ActionMessage(
				"stb.file.type"));
		saveMessages(request, messages);
		return mapping.findForward(SUCCESS);
	} else if (file.getFileSize() > MAX_SIZE_FILES) {
		//logger
		//		.info("****************STB_Size wrong :**********************");
		messages.add("STB_FILE_SIZE_WRONG", new ActionMessage(
				"stb.file.size"));
		saveMessages(request, messages);
		return mapping.findForward(SUCCESS);
	} else {
		List list = stbService.uploadExcelHistory(file);
		session.removeAttribute("error_file");
		session.setAttribute("error_file", list);
		String stbList = "";
		boolean checkValidate = true;

		if (list.size() > 0) {
			Iterator itt = list.iterator();
			while (itt.hasNext()) {
				dpStbInBulkDTO = (DPSTBHistDTO) itt.next();
				logger.info("");
				if (dpStbInBulkDTO.getErr_msg() != null
						&& !dpStbInBulkDTO.getErr_msg().equalsIgnoreCase(
								"SUCCESS")) {
					checkValidate = false;
					break;
				}
			}
			if (checkValidate) {
				stbInBulkBean.setError_flag("false");
				DPSTBHistDTO dpStbInBulk = new DPSTBHistDTO();
				try {
					if (list.size() > 0) {
						Iterator iter = list.iterator();
						while (iter.hasNext()) {
							dpStbInBulkDTO = (DPSTBHistDTO) iter.next();
							List stbSrno = dpStbInBulkDTO.getList_srno();
							if (stbSrno != null && stbSrno.size() > 0) {
								for (int i = 0; i < stbSrno.size(); i++) {
									stbList += "'" + stbSrno.get(i) + "',";
									if (i == 99) {
										break;
									}
								}
							}
						}

					}
					//logger
						//	.info("****************STB list *****************"
							//		+ stbList);
					stbList = stbList
							.substring(0, stbList.lastIndexOf(','));
					//logger
						//	.info("****************STB list *****************"
								//	+ stbList);
					dpStbInBulk.setSerial_no(stbList);
					stbInBulkBean = headerbuilder(stbInBulkBean);
					ArrayList<DPSTBHistDTO> serialNoList = stbService.getSerialNosHist(dpStbInBulk);
					System.out.println("serialNosList size in action::"+serialNoList.size());
					//logger
						//	.info("****************serialNoList size*****************"
							//		+ serialNoList.size());
//					stbInBulkBean.setserialNoList(serialNoList);
					request.setAttribute("availableSerialNosList", serialNoList);
					stbInBulkBean.setAvailableSerialNosList(serialNoList);
				} catch (Exception e) {
					e.printStackTrace();
//					Added by Neetika 13Aug BFR 16
					
					session.setAttribute("ReportSTB", ""); 
				}

			} else {
				stbInBulkBean.setError_flag("true");
				return mapping.findForward(SUCCESS);
			}
		}
	}
	// return mapping.findForward(SUCCESS);
	return mapping.findForward("uploadExcel");

}
		
	
	
	 
	public DPStbInBulkBean headerbuilder(DPStbInBulkBean DPStbInBulkBean) {

		List<String> outputHeaderListRowOne = new ArrayList<String>();
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");

		outputHeaderListRowOne.add("Forward Details");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");
		outputHeaderListRowOne.add("");

		outputHeaderListRowOne.add("Reverse Details");
		outputHeaderListRowOne.add("");
		DPStbInBulkBean.setHeadersOne(outputHeaderListRowOne);

		List<String> outputHeaderListRowTwo = new ArrayList<String>();
		outputHeaderListRowTwo.add("Serial No.");
		outputHeaderListRowTwo.add("STB Type");
		outputHeaderListRowTwo.add("STB Status");

		outputHeaderListRowTwo.add("Distributor ID");
		outputHeaderListRowTwo.add("Distributor Name");
		outputHeaderListRowTwo.add("Circle");
		outputHeaderListRowTwo.add("PO No.");
		outputHeaderListRowTwo.add("PO Date");
		outputHeaderListRowTwo.add("DC No");
		outputHeaderListRowTwo.add("DC Date");
		outputHeaderListRowTwo.add("Acceptance Date");
		outputHeaderListRowTwo.add("FSE Name");
		outputHeaderListRowTwo.add("Retailer Name");
		outputHeaderListRowTwo.add("Status");
		outputHeaderListRowTwo.add("Activation Date");
		outputHeaderListRowTwo.add("Customer Id");

		outputHeaderListRowTwo.add("Distributor ID");
		outputHeaderListRowTwo.add("Distributor Name");
		outputHeaderListRowTwo.add("Circle");
		outputHeaderListRowTwo.add("Inventory Change Date");
		outputHeaderListRowTwo.add("Invenytory Change Type");
		outputHeaderListRowTwo.add("Defect Type");
		outputHeaderListRowTwo.add("DC No");
		outputHeaderListRowTwo.add("DC Date");
		outputHeaderListRowTwo.add("DC Status");
		outputHeaderListRowTwo.add("Status");
		DPStbInBulkBean.setHeadersTwo(outputHeaderListRowTwo);
		return DPStbInBulkBean;

	}

	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DPStbInBulkBean DPStbInBulkBean = (DPStbInBulkBean) form;
		HttpSession session = request.getSession();
		session.removeAttribute("normalExcel");
		return mapping.findForward("errorFileSTB");
	}
	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		//System.out.println("inside getReportDownloadStatus");
		try
		{
			HttpSession session = request.getSession();
			
			String status = (String) session.getAttribute("ReportSTB");
			//System.out.println("stb value from session "+status);
			String result = "";
			if(status != null)
				result = status;
			//logger.info("result:" + result);

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(result);
			writer.flush();
			out.flush();
			//logger.info("out now getReportDownloadStatus");

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getReportDownloadStatus  ::  "+e.getMessage());
		}
		return null;
	}
	//Added by sugandha to show error for search STB history on 22-August-2013 for BFR-5
	public ActionForward showHistErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DPStbInBulkBean DPStbInBulkBean = (DPStbInBulkBean) form;
		HttpSession session = request.getSession();
		session.removeAttribute("HistExcel");
		return mapping.findForward("errorFileSTBHist");
	}
}
