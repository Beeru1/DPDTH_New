/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.actions;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.springframework.core.ParameterNameDiscoverer;

import com.ibm.dp.dto.UserSessionContext;
import com.ibm.virtualization.recharge.beans.AuthenticationFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.constants.VFWebConstants;
import com.ibm.virtualization.recharge.validation.ValidatorUtility;
import com.ibm.virtualization.recharge.dto.AuthenticationKeyInCache;

/**
 * Request Preprocessor Class for Virtualization
 * 
 * @author Paras
 */
public class VirtualizationRequestProcessor extends TilesRequestProcessor {

	/* Logger for this class */
	private static Logger logger = Logger
			.getLogger(VirtualizationRequestProcessor.class.getName());

	/**
	 * Default constructor VirtualizationRequestProcessor
	 */
	public VirtualizationRequestProcessor() {
	}

	/**
	 * @see org.apache.struts.action.RequestProcessor#processPreprocess(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected boolean processPreprocess(HttpServletRequest request,
			HttpServletResponse response) {
		//logger.info(" Started... ");
		HttpSession session =request.getSession(true);
		ArrayList<String> totalurls=(ArrayList<String>)session.getAttribute("totalurls");
		boolean flag = true;
		String requestedURI = request.getRequestURI();
		//logger.info("========= requestedURI =======:"+requestedURI);
		//logger.info("reqwuest getRequestURL:"+request.getRequestURL());
		StringBuffer var1=request.getRequestURL();
		String var2=null;
		String qString=null;
		qString=request.getQueryString();
		if(var1!=null)
		{
		var2="."+request.getRequestURL().substring(var1.lastIndexOf("/"));//+"?"+request.getQueryString();
		}
		if(qString!=null)
		{
			var2=var2+"?"+request.getQueryString();
		}
		//logger.info("======= var2 :"+var2);
		//String var2=var1
		// adding by  pratap===
		//logger.info("request.getMethod().equalsIgnoreCase('GET') :"+request.getMethod().equalsIgnoreCase("GET"));
		/*if(request.getMethod().equalsIgnoreCase("GET"))
		{
			logger.info("=== it is get method hitted ===============");
			try
			{
				logger.info("======You Are Not Autherized to Perform This Activity========");
			response.getWriter().println("You Are Not Autherized to Perform This Activity");
			//logger.info("======message pussheddddd========");
			return false;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}*/
		
		
		
		ArrayList<AuthenticationKeyInCache> groupUrlMapping=(ArrayList<AuthenticationKeyInCache>)session.getAttribute("groupurlmapping");
		int groupId;
		AuthenticationKeyInCache cache= new AuthenticationKeyInCache();
		cache.setUrl(var2);
		if(totalurls == null)
		{
			//logger.info(" totalurls is nulll==========");
			}
		else
		{
			try
			{
		//logger.info("totalurls is not nulll========");
		//logger.info("totalurls.contains(var2) :"+totalurls.contains(var2));
		Iterator itr=totalurls.iterator();
		/*while(itr.hasNext())
		{
			System.out.println(itr.next());
		}*/
			}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		}
		if(totalurls!=null ){
		if( totalurls.contains(var2))
		{
			//logger.info("totalurls.contains(var2) :"+totalurls.contains(var2));
			
		if(groupUrlMapping!=null)
		{
		if(session.getAttribute("groupid")!=null){
			groupId = Integer.parseInt(session.getAttribute("groupid").toString());
			
			cache.setGroupId(groupId);
			//logger.info("==== group id  in virtualizationRequestPorocessor.java  :"+groupId+ "comparison of object..");
			//logger.info("=========comparing ,object exist or not ======");
			//logger.info("groupUrlMapping.contains(cache) :"+groupUrlMapping.contains(cache));
			if(groupUrlMapping.contains(cache))
			{
				//logger.info("groupUrlMapping contains the url , : user have the permission :::");
				return true;
			}
			else
			{
				//logger.info("======== object not exist  in list  ==========");
				try
				{
					
					//logger.info("======You Are Not Autherized to Perform This Activity========");
					response.getWriter().println("You Are Not Autherized to Perform This Activity");
					//logger.info("======message pussheddddd========");
					return false;
					//logger.info("You Are Not Autherized to Perform This Activity");
				//session.invalidate();
				//flag=false;
				//logger.info("============333333333333==============333333333333333");
				}
				catch(Exception ex)
				{
					//logger.info("Exception  :"+ex.getMessage());
				ex.printStackTrace();	
				}
			}
		}
		else
		{
			//logger.info("==== group id is null in request processor  =========");
		}
		}
		else
		{
			//logger.info("=== groupUrlMapping is null in virtualizationrequestprocessor.java ==========");
		}
		}
		else
		{
			//logger.info("Link does not belogs to any db mapping link :::");
		}
	}
		
		else
		{
			//logger.info("========the totalurls is null ============");
		}
	
		//logger.info("======= executing after pratap ===========");
			//logger.info("===== it is post not the get=============");
		// end of adding by pratap
		String requestMapping = requestedURI.substring(requestedURI
				.lastIndexOf("/") + 1, requestedURI.lastIndexOf("."));
		logger.info("requested URI = " + requestMapping);
		boolean isReqMethodGET = request.getMethod().equalsIgnoreCase("GET");
		/* Check if the request is coming from an authenticated user */
		boolean isUserAuthenticated = request.getSession().getAttribute(
				Constants.AUTHENTICATED_USER) != null;
		boolean isMethodTypePOST = !isReqMethodGET;
		// checks the request is POST and user is authenticated
		if (isMethodTypePOST) {
			//System.out.println();
			if ((!VFWebConstants.CONS_LOGIN_ACTION
					.equalsIgnoreCase(requestMapping))
					&& (!VFWebConstants.CONS_CHANGE_PWD_ACTION
							.equalsIgnoreCase(requestMapping))
					&& (!VFWebConstants.INIT_FORGOT_PASSWORD
							.equalsIgnoreCase(requestMapping))
					&& (!VFWebConstants.FORGOT_PASSWORD
							.equalsIgnoreCase(requestMapping))
					&& (!VFWebConstants.CONS_INVALID_SESSION_ACTION
							.equalsIgnoreCase(requestMapping) && !isUserAuthenticated)) {
				flag = false;
				fwdToInvalidSessionPage(request, response);
				//logger.info("  Un-Authenticated Request recieved."					+ requestedURI);
			}

		} else {
			if (VFWebConstants.BATCH_DETAILS_ACTION
					.equalsIgnoreCase(requestMapping)
					|| VFWebConstants.MARK_DAMAGED_DETAILS
							.equalsIgnoreCase(requestMapping)
					|| requestMapping.equalsIgnoreCase("viewBatchDetails")
					|| requestMapping.equalsIgnoreCase("bulkDataAction")
					|| requestMapping
							.equalsIgnoreCase("viewBundledStockDetails")
					|| requestMapping.equalsIgnoreCase("viewDistStockSummary")
					|| requestMapping.equalsIgnoreCase("status")
					|| requestMapping.equalsIgnoreCase("statusDetails")
					|| requestMapping.equalsIgnoreCase("initAssignADStock")
					|| requestMapping.equalsIgnoreCase("reportAction")
					|| requestMapping.equalsIgnoreCase("viewSerialNoList")
					
					|| requestMapping.equalsIgnoreCase("viewSerialNoListforWH")
					
					|| requestMapping.equalsIgnoreCase("stockAcceptTransfer")
					
					|| requestMapping.equalsIgnoreCase("printDCDetails")
					
					|| requestMapping.equalsIgnoreCase("ErrorPO") //Added by Kundan
					
					|| requestMapping.equalsIgnoreCase("StbFlushOut") //Added by Kundan					
					
					|| requestMapping.equalsIgnoreCase("viewStockSerialNoList") //Added by Aslam
					
					|| requestMapping.equalsIgnoreCase("interssdtransfer")   // Added by Naveen
					
					|| requestMapping.equalsIgnoreCase("PoAcceptanceBulk")   // Added by Naveen
					
					|| requestMapping.equalsIgnoreCase("DeliveryChallanAccept")
					
					|| requestMapping.equalsIgnoreCase("ReverseChurnCollection")
					
					|| requestMapping.equalsIgnoreCase("dpHierarchyAccept")
					
					|| requestMapping.equalsIgnoreCase("nsBulkupload") //Added by Shilpa
					
					|| requestMapping.equalsIgnoreCase("viewStbList") //Added by Shilpa
					
					|| requestMapping.equalsIgnoreCase("ViewPurchaseOrder") //Added by Shilpa for view Po status
					
					|| requestMapping.equalsIgnoreCase("printRecoDetails") //Added by Shilpa
					
					|| requestMapping.equalsIgnoreCase("dcDamageStatus")  // Added by Naveen
					
					|| requestMapping.equalsIgnoreCase("recoPeriodConf")  // Added by Priya
					
					|| requestMapping.equalsIgnoreCase("recoPeriodConfPagination")  // Added by Priya
					
					|| requestMapping.equalsIgnoreCase("LoginAction") //Added By Nidhi
					
					|| requestMapping.equalsIgnoreCase("DistReco") // Added By Nidhi
					
					|| requestMapping.equalsIgnoreCase("dcFreshStatus")  // Added by Digvijay Singh
					
					|| requestMapping.equalsIgnoreCase("recoSummaryPaginationAction")  // Added by Digvijay Singh
					
					|| requestMapping.equalsIgnoreCase("missingStockApproval") // Added By Nidhi
					
					|| requestMapping.equalsIgnoreCase("recoDetailReport") // Added By naveen
					
					|| requestMapping.equalsIgnoreCase("DPPrintBulkAcceptanceAction")
					|| requestMapping.equalsIgnoreCase("DPPrintReverseCollectionAction")
					|| requestMapping.equalsIgnoreCase("distavStockUpload") //Added by Amandeep Singh for NSER to SER conversion
					|| requestMapping.equalsIgnoreCase("StbFlushOut")
					|| requestMapping.equalsIgnoreCase("ViewStockEligibility")// Added by Amandeep to export Eligibility excel
					|| requestMapping.equalsIgnoreCase("avStockUpload")// Added by Sugandha
		
					|| requestMapping.equalsIgnoreCase("dpSecurityDepostLoanBulkupload") // Added By ARTI
					|| requestMapping.equalsIgnoreCase("dpProductSecurityListBulkupload") // Added By ARTI
					|| requestMapping.equalsIgnoreCase("dpStockLevelBulkupload") // Added By ARTI
					|| requestMapping.equalsIgnoreCase("dpPendingListTransferBulkupload") //Added By Sanjay
					|| requestMapping.equalsIgnoreCase("dpInterSSDTransferBulkupload") //Added By Sanjay
					|| requestMapping.equalsIgnoreCase("whdistmappupload") // Added By ARTI
					|| requestMapping.equalsIgnoreCase("dpInitiateFnfAction")//Added by sugandha to view stock in FNF
					|| requestMapping.equalsIgnoreCase("dpDistPinCodeMapAction")//Added by sugandha
					|| requestMapping.equalsIgnoreCase("retailerLapuDataAction")//Added by Nehil
					|| requestMapping.equalsIgnoreCase("ViewPartnerInvoice")
					|| requestMapping.equalsIgnoreCase("UploadInv")
					|| requestMapping.equalsIgnoreCase("downloadPdfSign")	
					|| requestMapping.equalsIgnoreCase("checkRetailerBalance")
					|| requestMapping.equalsIgnoreCase("closeInactivePartners")) {

				flag = true;
			} else if (VFWebConstants.CONS_INVALID_SESSION_REDIRECT
					.indexOf(requestMapping) == -1
					&& VFWebConstants.CONS_CHANGE_PWD_ACTION
							.indexOf(requestMapping) == -1
					&& VFWebConstants.CONS_LOGOUT_ACTION
							.indexOf(requestMapping) == -1) {
				flag = fwdToInvalidSessionPage(request, response);
			}
		}
		// Method to Validate Non - JavaScript tag presence in source code
		
		// requestedURI check kar lena anju
		if (flag == true
				&& !(requestMapping.equalsIgnoreCase("initCreateAccount")
						|| requestMapping.equalsIgnoreCase("initCreateAccountItHelp") 
						|| requestMapping
								.equalsIgnoreCase("DPCreateProductAction") || requestMapping
						.equalsIgnoreCase("DPEditProductAction") || requestMapping
						.equalsIgnoreCase("ReverseCollection") || requestMapping
						.equalsIgnoreCase("dcCreation")|| requestMapping
						.equalsIgnoreCase("repairSTB") )) {   
			flag = scriptValidate(request, response);
			if (flag == false) {
				try {
					response.sendRedirect("./jsp/common/spCharerror.jsp");
				} catch (Exception e) {
					logger.error("  Exception occured  ", e);
					flag = false;

				}
			}
		}
		// End of method
		// response.setHeader("Cache-Control", "no-store");
		// logger.info(" Executed... ");
		// logger.info("flag------"+flag);
	//}
		//logger.info("========== returnig flag is  :"+flag);
		return flag;
	}

	public boolean scriptValidate(HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = true;
		Enumeration paramNames = request.getParameterNames();
		String paramName = "";
		String[] paramValues;
		String paramValue = "";
		while (paramNames.hasMoreElements()) {
			paramName = (String) paramNames.nextElement();
			paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				paramValue = paramValues[0];
				if (paramValue.length() > 0)
					// Check Index Here
					flag = ValidatorUtility.isJunkFree(paramValue);
				if (flag == false)
					break;
			} else {
				for (int i = 0; i < paramValues.length; i++) {
					paramValue = paramValues[i];
					// Check Index Here
					flag = ValidatorUtility.isJunkFree(paramValue);
					if (flag == false)
						break;
				}
				if (flag == false)
					break;
			}
		}
		return flag;

	}

	/**
	 * Any unauthenticated user or in-valid request will be forwarded to the
	 * InvalidSession page which whill invalidate the current session
	 */
	private boolean fwdToInvalidSessionPage(HttpServletRequest request,
			HttpServletResponse response) {
		boolean reqOk = true;
		try {
			reqOk = false;
			doForward(VFWebConstants.CONS_INVALID_SESSION_REDIRECT, request,
					response);
		} catch (Exception exp) {
			logger.error("  Exception occured  ", exp);
			reqOk = false;
		}
		return reqOk;
	}
}