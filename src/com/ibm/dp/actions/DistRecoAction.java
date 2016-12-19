package com.ibm.dp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.beans.DistRecoBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.service.DistRecoService;
import com.ibm.dp.service.impl.DistRecoServiceImpl;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

public class DistRecoAction extends DispatchAction {	
	private static Logger logger = Logger.getLogger(DistRecoAction.class
			.getName());
	public ActionForward init(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		HttpSession session = request.getSession();
		logger.info("***************************** init() method. of DistRecoAction Called*****************************");
		DistRecoBean formBean =(DistRecoBean)form ;
		DistRecoService distRecoService =new  DistRecoServiceImpl();
		DPMasterService dcProductService = new DPMasterServiceImpl();
		String dLink="";
		if(session.getAttribute("disabledLink")!=null)
		{
			dLink = session.getAttribute("disabledLink").toString();
			request.setAttribute("disabledLink", dLink);
			//session.setAttribute("disabledLink", null);
		}
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		
		logger.info("OLM ID -==== "+sessionContext.getLoginName());
		List<DpProductTypeMasterDto> dcProductTypeList = dcProductService.getProductTypeMaster();
		formBean.setProdTypeList(dcProductTypeList);
		List<RecoPeriodDTO> recoPeriodList= distRecoService.getRecoPeriodList(sessionContext.getId()+"");
		formBean.setRecoPeriodList(recoPeriodList);
		return mapping.findForward("init");

	}
	
	public void extendSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();			
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		long loginUserId = sessionContext.getId();		
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		out.write("Session extended for user ID : "+loginUserId);
		out.flush();		
	}
	
	public ActionForward viewRecoProducts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** viewRecoProducts() method. Called*****************************");
		HttpSession session = request.getSession();
		DistRecoBean formBean =(DistRecoBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String certId=null;
		Long distId = sessionContext.getId();
		String stbType = "-1";   
		String recoPeriod = null;
		String stockType = "-1";
		if(request.getAttribute("onlyOneRecoPeriod") != null ) {
			recoPeriod = (String)request.getAttribute("onlyOneRecoPeriod");
			formBean =  (DistRecoBean) request.getAttribute("uplaodformBean");
			formBean.setProductsLists(-1);
		}else {
			 stbType = request.getParameter("stbType");   
			recoPeriod = request.getParameter("recoPeriod");
			 stockType = request.getParameter("stockType");
			 formBean.setStrmsg(null);
			 formBean.setProductsLists(-1);
		}
		
		int recoPeriodId=Integer.valueOf(recoPeriod.toString()).intValue();
		String productId2 = request.getParameter("productId");
		String productId   = null ;
		System.out.println("Dist id hello=================================================== "+distId +" and Stb Type == "+stbType+" and recoperiod == "+ recoPeriod +"and stockType== "+stockType + "and productId==" +productId2);
		logger.info("Dist id == "+distId +" and Stb Type == "+stbType+" and recoperiod == "+ recoPeriod +"and stockType== "+stockType + "and productId==" +productId2);
		//System.out.println("Dist id == "+distId +" and Stb Type == "+stbType+" and recoperiod == "+ recoPeriod);
		DistRecoService distRecoService =new  DistRecoServiceImpl();
		DPMasterService dcProductService = new DPMasterServiceImpl();
		//*****************getting  new reco go live date * pratap
		boolean result=distRecoService.compareRecoGoLiveDate(recoPeriod);
		logger.info("Rico go live compared     ------         "+result);
		//System.out.println(" coparision status  :"+result);
		formBean.setNewRecoLiveDate(result);
		if(result)
		{
			formBean.setOldOrNewReco("N");
			formBean.setDropDownProductRecoList(distRecoService.getProductList(recoPeriod, distId.toString(),stbType));
			List<DistRecoDto> swapList =  distRecoService.getProductList(recoPeriod, distId.toString(),"0");
			StringBuilder swapProductList   = new StringBuilder();
			for(DistRecoDto distRecoDto  : swapList){
				swapProductList.append(distRecoDto.getProductId()+",");
			}
			formBean.setSwapProductList(swapProductList.toString());
			logger.info("stbType  ==******************************************************** "+stbType +"********Swap product list******"+swapProductList);
		}
		else
		{
			formBean.setOldOrNewReco("O");
		}
		logger.info("Dist id == "+distId.toString() +" and Stb Type == "+stbType+" and recoperiod == "+ recoPeriod +"and stockType== "+stockType + "and productId==" +productId2+"result::::::::::::"+result);
		//********
		List<DistRecoDto>  recoProductList = distRecoService.getRecoProductList(stbType, recoPeriod,distId.toString(),result ,stockType,productId2);
		logger.info("Got List Size == "+recoProductList.size());
		formBean.setProductRecoList(recoProductList);
		String isValidToEnter ="-1";
		if(recoProductList.size()>0){
			certId=recoProductList.get(0).getCertId();
			isValidToEnter = recoProductList.get(0).getIsValidToEnter();
		}else{
			certId ="-1";
		}
		
		
		List<DpProductTypeMasterDto> dcProductTypeList = dcProductService.getProductTypeMaster();
		formBean.setProdTypeList(dcProductTypeList);
		List<RecoPeriodDTO> recoPeriodList= distRecoService.getRecoPeriodList(sessionContext.getId()+"");
		formBean.setRecoPeriodList(recoPeriodList);
		
		formBean.setCertificateId("");
		formBean.setCertId(certId);
		formBean.setIsValidToEnter(isValidToEnter);
		logger.info("Is valid to enter in Action Reco == "+isValidToEnter);
		request.setAttribute("isValidToEnter", isValidToEnter);
		String dLink="";
		if(session.getAttribute("disabledLink")!=null)
		{
		dLink = session.getAttribute("disabledLink").toString();
		logger.info("In viewRecoProducts method in if cond of disabledLink == "+dLink);
		request.setAttribute("disabledLink", dLink);
		//session.setAttribute("disabledLink", null);
		}
		
		return mapping.findForward("init");
	}
/*	public ActionForward viewRecoProducts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** viewRecoProducts() method. Called*****************************");
		HttpSession session = request.getSession();
		DistRecoBean formBean =(DistRecoBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
		String stbType = request.getParameter("stbType");   
		String recoPeriod = request.getParameter("recoPeriod"); 
		logger.info("Dist id == "+distId +" and Stb Type == "+stbType+" and recoperiod == "+ recoPeriod);
		DistRecoService distRecoService =new  DistRecoServiceImpl();
		List<DistRecoDto>  recoProductList = distRecoService.getRecoProductList(stbType, recoPeriod,distId.toString());
					
		formBean.setProductRecoList(recoProductList);
//			 Ajax start
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");

		Element optionElement1;
		Element optionElement2;
		logger.info("Size of list got == "+recoProductList.size());
		
		if(recoProductList.size()==0){
			optionElement1 = root.addElement("optionProduct");
			//Added by shilpa on 2-5-12 for UAT Observation
				optionElement1.addAttribute("isValidToEnter", "-1");
			
		}
		Iterator iterProduct = recoProductList.iterator();
		DistRecoDto productRecoDto=null;
		String certId=null;
		while (iterProduct.hasNext()) 
		{
			 productRecoDto = (DistRecoDto) iterProduct.next();
			optionElement1 = root.addElement("optionProduct");
			//Added by shilpa on 2-5-12 for UAT Observation
			if(productRecoDto.getIsValidToEnter().equals("0")){
				optionElement1.addAttribute("isValidToEnter", "0");
			}else if(productRecoDto.getIsValidToEnter().equals("1")){
			optionElement1.addAttribute("isValidToEnter", "1");
			}
			if(productRecoDto.getDataTypaId().equals("0")){
				
				optionElement1.addAttribute("dataTypa", "System Generated");
				
			}else {
				optionElement1.addAttribute("dataTypa", "Partner Figure");
			}
			logger.info("productRecoDto.getDataTypaId() ==== "+productRecoDto.getDataTypaId());
			optionElement1.addAttribute("dataTypaId", productRecoDto.getDataTypaId());
			optionElement1.addAttribute("productId", productRecoDto.getProductId());
			optionElement1.addAttribute("productName", productRecoDto.getProductName());
			
			
			optionElement1.addAttribute("serialisedOpenStock", productRecoDto.getSerialisedOpenStock());
			optionElement1.addAttribute("nonSerialisedOpenStock", productRecoDto.getNonSerialisedOpenStock());
			optionElement1.addAttribute("defectiveOpenStock",productRecoDto.getDefectiveOpenStock());
			optionElement1.addAttribute("upgradeOpenStock",productRecoDto.getUpgradeOpenStock());
			optionElement1.addAttribute("churnOpenStock",productRecoDto.getChurnOpenStock());
			optionElement1.addAttribute("pendingPOIntransit",productRecoDto.getPendingPOIntransit());
			
			optionElement1.addAttribute("receivedWh", productRecoDto.getReceivedWh());
			optionElement1.addAttribute("receivedInterSSDOk", productRecoDto.getReceivedInterSSDOK());
			optionElement1.addAttribute("receivedInterSSDDef", productRecoDto.getReceivedInterSSDDef());
			optionElement1.addAttribute("receivedHierarchyTrans",productRecoDto.getReceivedHierarchyTrans());
			
			
			optionElement1.addAttribute("receivedDefective", productRecoDto.getReceivedDefective());
			optionElement1.addAttribute("returnedFresh",productRecoDto.getReturnedFresh());
			optionElement1.addAttribute("pendingDCIntransit",productRecoDto.getPendingDCIntransit());
			optionElement1.addAttribute("returnedHierarchyTrans",productRecoDto.getReturnedHierarchyTrans());
			optionElement1.addAttribute("returnedChurn",productRecoDto.getReturnedChurn());
			optionElement1.addAttribute("flushOutOk",productRecoDto.getFlushOutOk());
			optionElement1.addAttribute("flushOutDef",productRecoDto.getFlushOutDefective());
			optionElement1.addAttribute("recoQty",productRecoDto.getRecoQty());
			optionElement1.addAttribute("defectiveClosingStock",productRecoDto.getDefectiveClosingStock());
			
			optionElement1.addAttribute("upgradeClosingStock",productRecoDto.getUpgradeClosingStock());
			optionElement1.addAttribute("churnClosingStock",productRecoDto.getChurnClosingStock());
			
			
			optionElement1.addAttribute("receivedUpgrade", productRecoDto.getReceivedUpgrade());
			optionElement1.addAttribute("receivedChurn", productRecoDto.getReceivedChurn());
			optionElement1.addAttribute("returnedInterSSDOk", productRecoDto.getReturnedInterSSDOk());
			optionElement1.addAttribute("returnedInterSSDDEF", productRecoDto.getReturnedInterSSDDEF());
			optionElement1.addAttribute("returnedDoaBI", productRecoDto.getReturnedDoaBI());
			optionElement1.addAttribute("returnedDoaAi", productRecoDto.getReturnedDoaAi());
			
			
			optionElement1.addAttribute("returnedDefectiveSwap", productRecoDto.getReturnedDefectiveSwap());
			optionElement1.addAttribute("returnedC2S", productRecoDto.getReturnedC2S());
			optionElement1.addAttribute("serialisedActivation", productRecoDto.getSerialisedActivation());
			optionElement1.addAttribute("nonSerialisedActivation", productRecoDto.getNonSerialisedActivation());
			optionElement1.addAttribute("serializedClosingStock", productRecoDto.getSerialisedClosingStock());
			optionElement1.addAttribute("nonSerializedClosingStock", productRecoDto.getNonSerialisedClosingStock());
			
			optionElement1.addAttribute("isPartnerEntered", productRecoDto.getIsPartnerEntered());
			optionElement1.addAttribute("productType", productRecoDto.getProductType().trim());
			
			optionElement1.addAttribute("pendingPOIntransitOpen", productRecoDto.getPendingPOIntransitOpen());
			optionElement1.addAttribute("openingPendgDCIntrnsit", productRecoDto.getOpeningPendgDCIntrnsit());
			certId=productRecoDto.getCertId();
		//}
	}
		optionElement2 = root.addElement("optionCertId");
		formBean.setCertificateId("");
		optionElement2.addAttribute("certificateId", "-1");
		formBean.setCertId(certId);
		optionElement2.addAttribute("certId", certId);
		
		
		
		// For ajax
		response.setHeader("Cache-Control", "No-Cache");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
		// End for ajax
		
		return null;
	}
	*/
	
	
	
	
	public ActionForward submitDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** submitDetail() method. Called*****************************");
		HttpSession session = request.getSession();
		DistRecoBean formBean =(DistRecoBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
		String circleId = String.valueOf(sessionContext.getCircleId());
		String stbType = request.getParameter("stbType");   
		String recoPeriodId = request.getParameter("recoPeriod"); 
		String productId=  request.getParameter("productId");
		//Open Stocks
		String pendingPOOpening=  request.getParameter("pendingPOOpening");
		String pendingDCOpen=  request.getParameter("pendingDCOpen"); 
		String serOpen=  request.getParameter("serOpen");
		String nsrOpen=  request.getParameter("nsrOpen"); 
		String defOpen=  request.getParameter("defOpen");
		String upgaradeOpen=  request.getParameter("upgaradeOpen"); 
		String churnOpen=  request.getParameter("churnOpen");
		//Ends open Stock Here
		
		//Received Stcok
		String rcvdWH=  request.getParameter("rcvdWH");
		String recInterSSdOK=  request.getParameter("recInterSSdOK"); 
		String receivedHierarchyTrans=  "0"; 
		String recInterSSDDef=  request.getParameter("recInterSSDDef"); 
		String recUpgrade=  request.getParameter("recUpgrade"); 
		String recChurn=  request.getParameter("recChurn"); 
		//End Recvd Stock
		
		//Return Stock 
		String returnedOK=  request.getParameter("returnedOK");
		String retInterssdOk=  request.getParameter("retInterssdOk"); 
		String returnedHierarchyTrans= "0";
		String retInterssdDef=  request.getParameter("retInterssdDef"); 
		String retDoaBI=  request.getParameter("retDoaBI"); 
		String retDoaAi=  request.getParameter("retDoaAi"); 
		String retC2s=  request.getParameter("retC2s");
		String retChurn=  request.getParameter("retChurn"); 
		String retDefSwap=  request.getParameter("retDefSwap"); 
		//End Return Stock
		String activation=  request.getParameter("activation"); 
		
		String invChange=  request.getParameter("invChange"); 
		
		//Closing Stock
		String serClosing=  request.getParameter("serClosing");
		String nserClosing= request.getParameter("nserClosing");
		String defClosing=  request.getParameter("defClosing");
		String upgradeClosing=request.getParameter("upgradeClosing");
		String churnClosing= request.getParameter("churnClosing");
		String penPOClosing=  request.getParameter("penPOClosing");
		String penDCClosing=  request.getParameter("penDCClosing"); 
		String oldOrNewReco= request.getParameter("oldOrNewReco");//Added By RAM
		//End Closing Stock
		String remarks= request.getParameter("remarks");  //Added By Sadiqua
		logger.info("REMARKS------->" + remarks);
		// End Of Summary
		
		logger.info("Dist id == "+distId +" and Stb Type == "+stbType+" and recoperiod == "+ recoPeriodId+" and product id == "+productId+"  oldOrNewReco :"+oldOrNewReco);
		DistRecoDto distRecoDto =new DistRecoDto();
		distRecoDto.setOldOrNewReco(oldOrNewReco);
		distRecoDto.setRemarks(remarks);                //Added By Sadiqua
		distRecoDto.setDistId(distId.toString());
		distRecoDto.setPendingPOIntransitOpen(pendingPOOpening);
		distRecoDto.setOpeningPendgDCIntrnsit(pendingDCOpen);
		distRecoDto.setSerialisedOpenStock(serOpen);
		distRecoDto.setNonSerialisedOpenStock(nsrOpen);
		distRecoDto.setDefectiveOpenStock(defOpen);
		distRecoDto.setUpgradeOpenStock(upgaradeOpen);
		distRecoDto.setChurnOpenStock(churnOpen);
		distRecoDto.setReceivedWh(rcvdWH);
		distRecoDto.setReceivedInterSSDOK(recInterSSdOK);
		distRecoDto.setReceivedInterSSDDef(recInterSSDDef);
		distRecoDto.setReceivedHierarchyTrans(receivedHierarchyTrans);
		distRecoDto.setReceivedUpgrade(recUpgrade);
		distRecoDto.setReceivedChurn(recChurn);
		distRecoDto.setReturnedFresh(returnedOK);
		distRecoDto.setReturnedInterSSDDEF(retInterssdDef);
		distRecoDto.setReturnedHierarchyTrans(returnedHierarchyTrans);
		distRecoDto.setReturnedInterSSDOk(retInterssdOk);
		distRecoDto.setReturnedDoaAi(retDoaAi);
		distRecoDto.setReturnedDoaBI(retDoaBI);
		distRecoDto.setReturnedChurn(retChurn);
		distRecoDto.setReturnedDefectiveSwap(retDefSwap);
		distRecoDto.setReturnedC2S(retC2s);
		distRecoDto.setSerialisedClosingStock(serClosing);
		distRecoDto.setNonSerialisedClosingStock(nserClosing);
		distRecoDto.setDefectiveClosingStock(defClosing);
		distRecoDto.setUpgradeClosingStock(upgradeClosing);
		distRecoDto.setChurnClosingStock(churnClosing);
		distRecoDto.setPendingPOIntransit(penPOClosing);
		distRecoDto.setPendingDCIntransit(penDCClosing);
		distRecoDto.setFlushOutOk("0");
		distRecoDto.setFlushOutDefective("0");
		distRecoDto.setRecoQty("0");
		
		
		
		
		distRecoDto.setSerialisedActivation(activation);
		distRecoDto.setNonSerialisedActivation("0");
		distRecoDto.setInventoryChange(invChange);
		distRecoDto.setProductId(productId);
		distRecoDto.setRecoPeriodId(recoPeriodId);
		distRecoDto.setCircleId(circleId);
		
		
		
		
		DistRecoService distRecoService =new  DistRecoServiceImpl();
		String certificateId = distRecoService.submitDetail(distRecoDto);
	
		DPMasterService dcProductService = new DPMasterServiceImpl();
		//*****************getting  new reco go live date * pratap
		boolean result=distRecoService.compareRecoGoLiveDate(recoPeriodId);
		logger.info(" coparision status  :"+result);
		//********
		List<DistRecoDto>  recoProductList = distRecoService.getRecoProductList(stbType, recoPeriodId,distId.toString(),result ,null,null);
		formBean.setProductRecoList(recoProductList);
		List<DpProductTypeMasterDto> dcProductTypeList = dcProductService.getProductTypeMaster();
		formBean.setProdTypeList(dcProductTypeList);
		List<RecoPeriodDTO> recoPeriodList= distRecoService.getRecoPeriodList(sessionContext.getId()+"");
		formBean.setRecoPeriodList(recoPeriodList);
		String dLink="";
		logger.info("In submit details === Certificate id =="+certificateId);
		if(!certificateId.equals("-1")){
			formBean.setCertificateId(certificateId);
			dLink =null;
			request.setAttribute("disabledLink", dLink);
			session.setAttribute("disabledLink", null);
		}else{
			formBean.setCertificateId("");
			if(session.getAttribute("disabledLink")!=null)
			{
				dLink = session.getAttribute("disabledLink").toString();
				logger.info("In viewRecoProducts method in if cond of disabledLink == "+dLink);
				request.setAttribute("disabledLink", dLink);
				//session.setAttribute("disabledLink", null);
			}
		}
		formBean.setCertId("-1");
		//logger.info("In submit details === RESULT NEETIKA =="+result);
		
		formBean.setNewRecoLiveDate(result);//For submit button problem of showing new and old reco
		if(result)
		{
			formBean.setOldOrNewReco("N");
			formBean.setDropDownProductRecoList(distRecoService.getProductList(recoPeriodId, distId.toString(),stbType));
			List<DistRecoDto> swapList =  distRecoService.getProductList(recoPeriodId, distId.toString(),"0");
			StringBuilder swapProductList   = new StringBuilder();
			for(DistRecoDto distRecoDto1  : swapList){
				swapProductList.append(distRecoDto1.getProductId()+",");
			}
			formBean.setSwapProductList(swapProductList.toString());
			logger.info("stbType  ==******************************************************** "+stbType +"********Swap product list******"+swapProductList);
			formBean.setStrmsg(null);
		}
		else
		{
			formBean.setOldOrNewReco("O");
		}
		return mapping.findForward("init");
	}
	
	
	/*public ActionForward submitDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** submitDetail() method. Called*****************************");
		HttpSession session = request.getSession();
		DistRecoBean formBean =(DistRecoBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
		String circleId = String.valueOf(sessionContext.getCircleId());
		String stbType = request.getParameter("stbType");   
		String recoPeriodId = request.getParameter("recoPeriod"); 
		String productId=  request.getParameter("productId");
		//Open Stocks
		String pendingPOOpening=  request.getParameter("pendingPOOpening");
		String pendingDCOpen=  request.getParameter("pendingDCOpen"); 
		String serOpen=  request.getParameter("serOpen");
		String nsrOpen=  request.getParameter("nsrOpen"); 
		String defOpen=  request.getParameter("defOpen");
		String upgaradeOpen=  request.getParameter("upgaradeOpen"); 
		String churnOpen=  request.getParameter("churnOpen");
		//Ends open Stock Here
		
		//Received Stcok
		String rcvdWH=  request.getParameter("rcvdWH");
		String recInterSSdOK=  request.getParameter("recInterSSdOK"); 
		String receivedHierarchyTrans=  "0"; 
		String recInterSSDDef=  request.getParameter("recInterSSDDef"); 
		String recUpgrade=  request.getParameter("recUpgrade"); 
		String recChurn=  request.getParameter("recChurn"); 
		//End Recvd Stock
		
		//Return Stock 
		String returnedOK=  request.getParameter("returnedOK");
		String retInterssdOk=  request.getParameter("retInterssdOk"); 
		String returnedHierarchyTrans= "0";
		String retInterssdDef=  request.getParameter("retInterssdDef"); 
		String retDoaBI=  request.getParameter("retDoaBI"); 
		String retDoaAi=  request.getParameter("retDoaAi"); 
		String retC2s=  request.getParameter("retC2s");
		String retChurn=  request.getParameter("retChurn"); 
		String retDefSwap=  request.getParameter("retDefSwap"); 
		//End Return Stock
		String activation=  request.getParameter("activation"); 
		
		String invChange=  request.getParameter("invChange"); 
		
		//Closing Stock
		String serClosing=  request.getParameter("serClosing");
		String nserClosing= request.getParameter("nserClosing");
		String defClosing=  request.getParameter("defClosing");
		String upgradeClosing=request.getParameter("upgradeClosing");
		String churnClosing= request.getParameter("churnClosing");
		String penPOClosing=  request.getParameter("penPOClosing");
		String penDCClosing=  request.getParameter("penDCClosing"); 
		
		//End Closing Stock
		
		logger.info("Dist id == "+distId +" and Stb Type == "+stbType+" and recoperiod == "+ recoPeriodId+" and product id == "+productId);
		DistRecoDto distRecoDto =new DistRecoDto();
		
		distRecoDto.setDistId(distId.toString());
		distRecoDto.setPendingPOIntransitOpen(pendingPOOpening);
		distRecoDto.setOpeningPendgDCIntrnsit(pendingDCOpen);
		distRecoDto.setSerialisedOpenStock(serOpen);
		distRecoDto.setNonSerialisedOpenStock(nsrOpen);
		distRecoDto.setDefectiveOpenStock(defOpen);
		distRecoDto.setUpgradeOpenStock(upgaradeOpen);
		distRecoDto.setChurnOpenStock(churnOpen);
		distRecoDto.setReceivedWh(rcvdWH);
		distRecoDto.setReceivedInterSSDOK(recInterSSdOK);
		distRecoDto.setReceivedInterSSDDef(recInterSSDDef);
		distRecoDto.setReceivedHierarchyTrans(receivedHierarchyTrans);
		distRecoDto.setReceivedUpgrade(recUpgrade);
		distRecoDto.setReceivedChurn(recChurn);
		distRecoDto.setReturnedFresh(returnedOK);
		distRecoDto.setReturnedInterSSDDEF(retInterssdDef);
		distRecoDto.setReturnedHierarchyTrans(returnedHierarchyTrans);
		distRecoDto.setReturnedInterSSDOk(retInterssdOk);
		distRecoDto.setReturnedDoaAi(retDoaAi);
		distRecoDto.setReturnedDoaBI(retDoaBI);
		distRecoDto.setReturnedChurn(retChurn);
		distRecoDto.setReturnedDefectiveSwap(retDefSwap);
		distRecoDto.setReturnedC2S(retC2s);
		distRecoDto.setSerialisedClosingStock(serClosing);
		distRecoDto.setNonSerialisedClosingStock(nserClosing);
		distRecoDto.setDefectiveClosingStock(defClosing);
		distRecoDto.setUpgradeClosingStock(upgradeClosing);
		distRecoDto.setChurnClosingStock(churnClosing);
		distRecoDto.setPendingPOIntransit(penPOClosing);
		distRecoDto.setPendingDCIntransit(penDCClosing);
		distRecoDto.setFlushOutOk("0");
		distRecoDto.setFlushOutDefective("0");
		distRecoDto.setRecoQty("0");
		
		
		
		
		distRecoDto.setSerialisedActivation(activation);
		distRecoDto.setNonSerialisedActivation("0");
		distRecoDto.setInventoryChange(invChange);
		distRecoDto.setProductId(productId);
		distRecoDto.setRecoPeriodId(recoPeriodId);
		distRecoDto.setCircleId(circleId);
		
		
		
		
		DistRecoService distRecoService =new  DistRecoServiceImpl();
		String certificateId = distRecoService.submitDetail(distRecoDto);
	
		
		List<DistRecoDto>  recoProductList = distRecoService.getRecoProductList(stbType, recoPeriodId,distId.toString());
					
		formBean.setProductRecoList(recoProductList);
//			 Ajax start
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");

		Element optionElement1;
		Element optionElement2;
		logger.info("Size of list got == "+recoProductList.size());
		Iterator iterProduct = recoProductList.iterator();
		while (iterProduct.hasNext()) 
		{
			DistRecoDto productRecoDto = (DistRecoDto) iterProduct.next();
			optionElement1 = root.addElement("optionProduct");
			if(productRecoDto.getDataTypaId().equals("0")){
				
				optionElement1.addAttribute("dataTypa", "System Generated");
				
			}else {
				optionElement1.addAttribute("dataTypa", "Partner Figure");
			}
			logger.info("productRecoDto.getDataTypaId() ==== "+productRecoDto.getDataTypaId());
			optionElement1.addAttribute("dataTypaId", productRecoDto.getDataTypaId());
			optionElement1.addAttribute("productId", productRecoDto.getProductId());
			optionElement1.addAttribute("productName", productRecoDto.getProductName());
			
			
			optionElement1.addAttribute("serialisedOpenStock", productRecoDto.getSerialisedOpenStock());
			optionElement1.addAttribute("nonSerialisedOpenStock", productRecoDto.getNonSerialisedOpenStock());
			optionElement1.addAttribute("defectiveOpenStock",productRecoDto.getDefectiveOpenStock());
			optionElement1.addAttribute("upgradeOpenStock",productRecoDto.getUpgradeOpenStock());
			optionElement1.addAttribute("churnOpenStock",productRecoDto.getChurnOpenStock());
			optionElement1.addAttribute("pendingPOIntransit",productRecoDto.getPendingPOIntransit());
			
			optionElement1.addAttribute("receivedWh", productRecoDto.getReceivedWh());
			optionElement1.addAttribute("receivedInterSSDOk", productRecoDto.getReceivedInterSSDOK());
			optionElement1.addAttribute("receivedInterSSDDef", productRecoDto.getReceivedInterSSDDef());
			optionElement1.addAttribute("receivedHierarchyTrans",productRecoDto.getReceivedHierarchyTrans());
			
			
			optionElement1.addAttribute("receivedDefective", productRecoDto.getInventoryChange());
			optionElement1.addAttribute("returnedFresh",productRecoDto.getReturnedFresh());
			optionElement1.addAttribute("pendingDCIntransit",productRecoDto.getPendingDCIntransit());
			optionElement1.addAttribute("returnedHierarchyTrans",productRecoDto.getReturnedHierarchyTrans());
			optionElement1.addAttribute("returnedChurn",productRecoDto.getReturnedChurn());
			optionElement1.addAttribute("flushOutOk",productRecoDto.getFlushOutOk());
			optionElement1.addAttribute("flushOutDef",productRecoDto.getFlushOutDefective());
			optionElement1.addAttribute("recoQty",productRecoDto.getRecoQty());
			optionElement1.addAttribute("defectiveClosingStock",productRecoDto.getDefectiveClosingStock());
			
			optionElement1.addAttribute("upgradeClosingStock",productRecoDto.getUpgradeClosingStock());
			optionElement1.addAttribute("churnClosingStock",productRecoDto.getChurnClosingStock());
			
			
			optionElement1.addAttribute("receivedUpgrade", productRecoDto.getReceivedUpgrade());
			optionElement1.addAttribute("receivedChurn", productRecoDto.getReceivedChurn());
			optionElement1.addAttribute("returnedInterSSDOk", productRecoDto.getReturnedInterSSDOk());
			optionElement1.addAttribute("returnedInterSSDDEF", productRecoDto.getReturnedInterSSDDEF());
			optionElement1.addAttribute("returnedDoaBI", productRecoDto.getReturnedDoaBI());
			optionElement1.addAttribute("returnedDoaAi", productRecoDto.getReturnedDoaAi());
			
			
			optionElement1.addAttribute("returnedDefectiveSwap", productRecoDto.getReturnedDefectiveSwap());
			optionElement1.addAttribute("returnedC2S", productRecoDto.getReturnedC2S());
			optionElement1.addAttribute("serialisedActivation", productRecoDto.getSerialisedActivation());
			optionElement1.addAttribute("nonSerialisedActivation", productRecoDto.getNonSerialisedActivation());
			optionElement1.addAttribute("serializedClosingStock", productRecoDto.getSerialisedClosingStock());
			optionElement1.addAttribute("nonSerializedClosingStock", productRecoDto.getNonSerialisedClosingStock());
			
			optionElement1.addAttribute("isPartnerEntered", productRecoDto.getIsPartnerEntered());
			optionElement1.addAttribute("productType", productRecoDto.getProductType().trim());
			
			optionElement1.addAttribute("pendingPOIntransitOpen", productRecoDto.getPendingPOIntransitOpen());
			optionElement1.addAttribute("openingPendgDCIntrnsit", productRecoDto.getOpeningPendgDCIntrnsit());
			
		}
		optionElement2 = root.addElement("optionCertId");
		if(!certificateId.equals("-1")){
			formBean.setCertificateId(certificateId);
			optionElement2.addAttribute("certificateId", certificateId);
		}else{
			formBean.setCertificateId("");
			optionElement2.addAttribute("certificateId", "-1");
			
		}
		formBean.setCertId("-1");
		optionElement2.addAttribute("certId", "-1");
		
		// For ajax
		response.setHeader("Cache-Control", "No-Cache");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
		// End for ajax
		
		return null;
	}*/
	public ActionForward printRecoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{		ActionErrors errors = new ActionErrors();
				
				String certifcateId = null;
				
			try 
			{
				HttpSession session = request.getSession();
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				Long distId = sessionContext.getId();
				String distName = sessionContext.getAccountName()+"  ("+sessionContext.getLoginName()+")";
		
				logger.info("Dist id == "+distId +" Account Name -- "+sessionContext.getAccountName() +" and  Contact Name == "+sessionContext.getContactName() );
				
				DistRecoBean formBean =(DistRecoBean)form ;
				certifcateId = request.getParameter("CERT_ID");
				formBean.setDistName(distName);
				DistRecoService distRecoService =new  DistRecoServiceImpl();
				List<PrintRecoDto> printRecoList = distRecoService.getRecoPrintList(certifcateId);
				formBean.setPrintRecoList(printRecoList);
				logger.info("SIZE OF LIST  ::::  "+printRecoList.size());
				formBean.setRefNo(printRecoList.get(0).getRefNo());
				formBean.setCertDate(printRecoList.get(0).getCertDate());
				logger.info("REF No ==  ::::  "+printRecoList.get(0).getRefNo());
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward("initsuccess");
			}
			
			
				return mapping.findForward("initsuccess");
			
	}
	/*--------------------- Adding by Ram  ------------*/
	public ActionForward downloadDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
		DistRecoBean formBean =(DistRecoBean)form ;
		String productId=(String)request.getParameter("productId");
		String tabId=(String)request.getParameter("tabId");
		String columnId=(String)request.getParameter("columnId");
		String recoPeriodId=(String)request.getParameter("recoPeriodId");
		//add by Beeru
		String productName  = (String)request.getParameter("prodectName");
		productName = productName != null?productName.replaceAll("\\s+",""):"";
		if(productName !=null && productName.length() >0) {
			request.setAttribute("buttonName", productName);
		}else {
			request.setAttribute("buttonName", "recoPageDetails");
		}
		
		//end by Beeru
		formBean.setColumnId(columnId);
		formBean.setTabId(tabId);
		logger.info("=================== in distrecoaction > downloadDetails******productId:"+productId+"    distId:"+distId+"   tabId:"+tabId+"  recoPeriodId:"+recoPeriodId);
		DistRecoService distRecoService =new  DistRecoServiceImpl();
		List<DistRecoDto> detailsList = distRecoService.getDetailsList(productId, columnId, distId, tabId,recoPeriodId);
		request.setAttribute("detailedReport", detailsList);
		formBean.setDetailsList(detailsList);
		return mapping.findForward("detailedReportExcel");
			}
	
	
	public ActionForward downloadClosingStockDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
		logger.info("============in downloadClosingStockDetails of DistRecoAction.java  =======");
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
		DistRecoBean formBean =(DistRecoBean)form ;
		String productId=(String)request.getParameter("productId");
		String tabId=(String)request.getParameter("tabId");
		String columnId=(String)request.getParameter("columnId");
		String recoPeriodId=(String)request.getParameter("recoPeriodId");
		//add by Beeru
		String productName  = (String)request.getParameter("prodectName");
		productName = productName != null?productName.replaceAll("\\s+",""):"";
		//end by Beeru
		formBean.setColumnId(columnId);
		formBean.setTabId(tabId);
		logger.info("=================== in downloadClosingStockDetails of DistRecoAction.java******productId:"+productId+"    distId:"+distId+"   tabId:"+tabId+"  recoPeriodId:"+recoPeriodId);
		try
	     {
			//ArrayList<DuplicateSTBDTO> sTBDtoList=new ArrayList<DuplicateSTBDTO>();
			//sTBDtoList.add(new DuplicateSTBDTO());
			DistRecoService distRecoService =new  DistRecoServiceImpl();
			List<DistRecoDto> detailsList = distRecoService.getDetailsList(productId, columnId, distId, tabId,recoPeriodId);
			String filePath = (String) getResources(request).getMessage("UPLOAD_PATH_DAMAGE") + "/" + "ErrorStbtemp.xls";
		    ServletOutputStream out = response.getOutputStream();
		    //logger.info("filePath"+filePath);
			response.setHeader( "Pragma", "public" );
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Cache-Control", "must-revalidate");
		    InputStream in = null;
		    String fpath=null;
		    HSSFWorkbook workbook=null;
		    HSSFSheet sheet=null;
		    Iterator rows=null;
		    int rowNumber;
		    HSSFRow row=null;
		    HSSFCell cell=null;
		    DistRecoDto distRecoDTO=null;
		   String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
		   
		   // ******************** serialized closing stock ****************
		   if(columnId.equals("1")){ 
			   response.setHeader("Content-Disposition", "attachment;filename="+productName+"ClosingDetailOfSerializedStock.xls");
			   logger.info("*********************Its for columnId=1***************************************");
			fpath=path  + "ClosingDetailsOfSerializedStock.xls";
			logger.info("fpath=downloadClosingStockDetails ="+fpath);
			in = new FileInputStream(fpath);
	        // Create a workbook 
            workbook = new HSSFWorkbook(in);
           logger.info("fpath=in downloadClosingStockDetails after workbook ="+fpath);
            sheet = workbook.getSheetAt(0);
			rows = sheet.rowIterator();
			rowNumber = 0;
			//row = (HSSFRow) rows.next();
			//added by Beeru
			CellStyle unlockedCellStyle = workbook.createCellStyle();
			unlockedCellStyle.setLocked(false); 
			//end by Beeru
			if(detailsList.size() > 0){
			while (rowNumber < detailsList.size() ) 
			{
				distRecoDTO = detailsList.get(rowNumber);
				 row = sheet.createRow(++rowNumber);
	             	            
	             	cell = row.createCell(0);
	        		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        		cell.setCellValue(rowNumber);
       			cell = row.createCell(1);
       			
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistOlmId()) || null == distRecoDTO.getDistOlmId())
	        			cell.setCellValue(" ");
	        		else
	        			cell.setCellValue(distRecoDTO.getDistOlmId());
       			
       			cell = row.createCell(2);
       			
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistName()) || null == distRecoDTO.getDistName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDistName());
       			
       			cell = row.createCell(3);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getTsmName()) || null == distRecoDTO.getTsmName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getTsmName());
       			
       			cell = row.createCell(4);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCircleName()) || null == distRecoDTO.getCircleName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCircleName());
       			
       			cell = row.createCell(5);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getPrNo()) || null == distRecoDTO.getPrNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getPrNo()); 

	             	cell = row.createCell(6);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getPoNo()) || null == distRecoDTO.getPoNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getPoNo());

       			cell = row.createCell(7);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getProductName()) || null == distRecoDTO.getProductName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getProductName());
       			
       			cell = row.createCell(8);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getProductSerialNo()) || null == distRecoDTO.getProductSerialNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getProductSerialNo());
       			
       			cell = row.createCell(9);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistPurchageDate()) || null == distRecoDTO.getDistPurchageDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDistPurchageDate());
       			
       			cell = row.createCell(10);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getFscName()) || null == distRecoDTO.getFscName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getFscName()); 

	             	cell = row.createCell(11);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getFscPurchageDate()) || null == distRecoDTO.getFscPurchageDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getFscPurchageDate());
	        		
	        		cell = row.createCell(12);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getRetailerName()) || null == distRecoDTO.getRetailerName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getRetailerName());
	        		
	        		cell = row.createCell(13);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getRetailerPurchageDate()) || null == distRecoDTO.getRetailerPurchageDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getRetailerPurchageDate());
	        		
	        		cell = row.createCell(14);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getStatusDesc()) || null == distRecoDTO.getStatusDesc())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getStatusDesc());
	        		
	        		cell = row.createCell(15);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("YES");
	        		
	        		
	        		cell = row.createCell(16);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("This is ok");
	        		
	        		cell = row.createCell(17);
	        		cell.setCellStyle(unlockedCellStyle);
	        		cell.setCellValue("");
	        		
	        		
			  }
				 
		//	System.out.println("rowNumber == in dist reco action "+rowNumber);
			//Neetika for removing extra rows..
			while (rowNumber >= detailsList.size() && rowNumber<65535 ) 
			{
			 row =sheet.createRow(++rowNumber);
			 sheet.removeRow(row)	;	 
			}
			
		   }
			else
			{
				 row = sheet.createRow(++rowNumber);
				 cell = row.createCell(10);
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		cell.setCellValue(" No Records found");
	        		
	        		while (rowNumber>=0 && rowNumber<65535 ) 
	    			{
	        	     row =sheet.createRow(++rowNumber);
	    			 sheet.removeRow(row)	;	 
	    			}
			}
		   }
		   
		   
		   //************** Defective closing stock ******************** 
		   else if(columnId.equals("2")){
			   response.setHeader("Content-Disposition", "attachment;filename="+productName+"ClosingDetailsOfDefectiveStock.xls");
			   logger.info("*********************Its for columnId=2***************************************");
			fpath=path  + "ClosingDetailsOfDefectiveStock.xls";
			logger.info("fpath=downloadClosingStockDetails ="+fpath);
			in = new FileInputStream(fpath);
	        // Create a workbook 
            workbook = new HSSFWorkbook(in);
           logger.info("fpath=in downloadClosingStockDetails after workbook ="+fpath);
            sheet = workbook.getSheetAt(0);
          //added by Beeru
			CellStyle unlockedCellStyle = workbook.createCellStyle();
			unlockedCellStyle.setLocked(false); 
			//end by Beeru
			rows = sheet.rowIterator();
			rowNumber = 0;
			row = (HSSFRow) rows.next(); 
			if(detailsList.size() > 0){
			while (rowNumber < detailsList.size() ) 
			{
				distRecoDTO = detailsList.get(rowNumber);
				 row = sheet.createRow(++rowNumber);
	             	            
	             	cell = row.createCell(0);
	        		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        		cell.setCellValue(rowNumber);

       			cell = row.createCell(1);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistOlmId()) || null == distRecoDTO.getDistOlmId())
	        			cell.setCellValue(" ");
	        		else
	        			cell.setCellValue(distRecoDTO.getDistOlmId());
       			
       			cell = row.createCell(2);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistName()) || null == distRecoDTO.getDistName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDistName());
       			
       			cell = row.createCell(3);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getTsmName()) || null == distRecoDTO.getTsmName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getTsmName());
       			
       			cell = row.createCell(4);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCircleName()) || null == distRecoDTO.getCircleName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCircleName());
       			
       			cell = row.createCell(5);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCollectionType()) || null == distRecoDTO.getCollectionType())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCollectionType()); 

	             	cell = row.createCell(6);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getProductName()) || null == distRecoDTO.getProductName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getProductName());

       			cell = row.createCell(7);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCollectionDate()) || null == distRecoDTO.getCollectionDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCollectionDate());
       			
       			cell = row.createCell(8);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getInventoryChangeDate()) || null == distRecoDTO.getInventoryChangeDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getInventoryChangeDate());
       			
       			cell = row.createCell(9);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getRecoveredStbNo()) || null == distRecoDTO.getRecoveredStbNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getRecoveredStbNo());
       			
       			cell = row.createCell(10);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getInstalledStbNo()) || null == distRecoDTO.getInstalledStbNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getInstalledStbNo()); 

	             	cell = row.createCell(11);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDefectType()) || null == distRecoDTO.getDefectType())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDefectType());
	        		
	        		cell = row.createCell(12);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getAging()) || null == distRecoDTO.getAging())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getAging());
	        		
	        			        		
	        		cell = row.createCell(13);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("YES");
	        		
	        		
	        		cell = row.createCell(14);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("This is ok");
	        		
	        		//added by ABU
	        		cell = row.createCell(15);
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end
	        		logger.info("Here ends the defective construction...!!!");
			  }
			//Neetika for removing extra rows..
			while (rowNumber >= detailsList.size() && rowNumber<65535 ) 
			{
			 row =sheet.createRow(++rowNumber);
			 sheet.removeRow(row)	;	 
			}
		   }
			else
			{
				 row = sheet.createRow(++rowNumber);
				 cell = row.createCell(10);
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		cell.setCellValue(" No Records found");
	        		while (rowNumber>=0 && rowNumber<65535 ) 
	    			{
	        	     row =sheet.createRow(++rowNumber);
	    			 sheet.removeRow(row)	;	 
	    			}
			}
		   }
		   
		   /*************************upgrade closing stock******************************************/
		   else if(columnId.equals("3")){
			   response.setHeader("Content-Disposition", "attachment;filename="+productName+"ClosingDetailsOfUpgradeStock.xls");
			   logger.info("*********************Its for columnId=3***************************************");
			fpath=path  + "ClosingDetailsOfUpgradeStock.xls";
			logger.info("fpath=downloadClosingStockDetails ="+fpath);
			in = new FileInputStream(fpath);
	        // Create a workbook 
            workbook = new HSSFWorkbook(in);
           logger.info("fpath=in downloadClosingStockDetails after workbook ="+fpath);
            sheet = workbook.getSheetAt(0);
            //added by Beeru
			CellStyle unlockedCellStyle = workbook.createCellStyle();
			unlockedCellStyle.setLocked(false); 
			//end by Beeru
			rows = sheet.rowIterator();
			rowNumber = 0;
			row = (HSSFRow) rows.next(); 
			if(detailsList.size() > 0){
			while (rowNumber < detailsList.size() ) 
			{
				distRecoDTO = detailsList.get(rowNumber);
				 row = sheet.createRow(++rowNumber);
	             	            
	             	cell = row.createCell(0);
	        		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        		cell.setCellValue(rowNumber);

       			cell = row.createCell(1);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistOlmId()) || null == distRecoDTO.getDistOlmId())
	        			cell.setCellValue(" ");
	        		else
	        			cell.setCellValue(distRecoDTO.getDistOlmId());
       			
       			cell = row.createCell(2);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistName()) || null == distRecoDTO.getDistName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDistName());
       			
       			cell = row.createCell(3);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getTsmName()) || null == distRecoDTO.getTsmName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getTsmName());
       			
       			cell = row.createCell(4);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCircleName()) || null == distRecoDTO.getCircleName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCircleName());
       			
       			cell = row.createCell(5);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCollectionType()) || null == distRecoDTO.getCollectionType())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCollectionType()); 

	             	cell = row.createCell(6);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getProductName()) || null == distRecoDTO.getProductName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getProductName());

       			cell = row.createCell(7);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCollectionDate()) || null == distRecoDTO.getCollectionDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCollectionDate());
       			
       			cell = row.createCell(8);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getInventoryChangeDate()) || null == distRecoDTO.getInventoryChangeDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getInventoryChangeDate());
       			
       			cell = row.createCell(9);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getRecoveredStbNo()) || null == distRecoDTO.getRecoveredStbNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getRecoveredStbNo());
       			
	        		
       			cell = row.createCell(10);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getInstalledStbNo()) || null == distRecoDTO.getInstalledStbNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getInstalledStbNo()); 

	             	cell = row.createCell(11);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDefectType()) || null == distRecoDTO.getDefectType())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDefectType());
	        		
	        		cell = row.createCell(12);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getAging()) || null == distRecoDTO.getAging())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getAging());
	        		
	        			        		
	        		cell = row.createCell(13);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("YES");
	        		
	        		
	        		cell = row.createCell(14);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("This is ok");
	        		
	        		//added by ABU
	        		cell = row.createCell(15);
	        		cell.setCellStyle(unlockedCellStyle);
	        		cell.setCellValue("");
	        		//end by ABU
			  }
			//Neetika for removing extra rows..
			while (rowNumber >= detailsList.size() && rowNumber<65535 ) 
			{
			 row =sheet.createRow(++rowNumber);
			 sheet.removeRow(row)	;	 
			}
		   }
			else
			{
				 row = sheet.createRow(++rowNumber);
				 cell = row.createCell(10);
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		cell.setCellValue(" No Records found");
	        		while (rowNumber>=0 && rowNumber<65535 ) 
	    			{
	        	     row =sheet.createRow(++rowNumber);
	    			 sheet.removeRow(row)	;	 
	    			}
			}
		   }
		   
		   /*************************Churned closing stock******************************************/
		   else if(columnId.equals("4")){
			   response.setHeader("Content-Disposition", "attachment;filename="+productName+"ClosingDetailsOfChurnedStock.xls");
			   logger.info("*********************Its for columnId=4***************************************");
			fpath=path  + "ClosingDetailsOfChurnedStock.xls";
			logger.info("fpath=downloadClosingStockDetails ="+fpath);
			in = new FileInputStream(fpath);
	        // Create a workbook 
            workbook = new HSSFWorkbook(in);
           logger.info("fpath=in downloadClosingStockDetails after workbook ="+fpath);
            sheet = workbook.getSheetAt(0);
            //added by Beeru
			CellStyle unlockedCellStyle = workbook.createCellStyle();
			unlockedCellStyle.setLocked(false); 
			//end by Beeru
			rows = sheet.rowIterator();
			rowNumber = 0;
			row = (HSSFRow) rows.next(); 
			if(detailsList.size() > 0){
			while (rowNumber < detailsList.size() ) 
			{
				distRecoDTO = detailsList.get(rowNumber);
				 row = sheet.createRow(++rowNumber);
	             	            
	             	cell = row.createCell(0);
	        		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        		cell.setCellValue(rowNumber);

       			cell = row.createCell(1);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistOlmId()) || null == distRecoDTO.getDistOlmId())
	        			cell.setCellValue(" ");
	        		else
	        			cell.setCellValue(distRecoDTO.getDistOlmId());
       			
       			cell = row.createCell(2);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistName()) || null == distRecoDTO.getDistName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDistName());
       			
       			cell = row.createCell(3);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getTsmName()) || null == distRecoDTO.getTsmName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getTsmName());
       			
       			cell = row.createCell(4);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCircleName()) || null == distRecoDTO.getCircleName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCircleName());
       			
       			cell = row.createCell(5);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCollectionType()) || null == distRecoDTO.getCollectionType())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCollectionType()); 

	             	cell = row.createCell(6);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getProductName()) || null == distRecoDTO.getProductName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getProductName());

       			cell = row.createCell(7);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCollectionDate()) || null == distRecoDTO.getCollectionDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCollectionDate());
       			       			
       			cell = row.createCell(8);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getRecoveredStbNo()) || null == distRecoDTO.getRecoveredStbNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getRecoveredStbNo());
       			
	        		cell = row.createCell(9);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getAging()) || null == distRecoDTO.getAging())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getAging());
	        		
	        			        		
	        		cell = row.createCell(10);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("YES");
	        		
	        		
	        		cell = row.createCell(11);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("This is ok");
	        		
	        		//added by ABU
	        		cell = row.createCell(12);
	        		cell.setCellStyle(unlockedCellStyle);
	        		cell.setCellValue("");
	        		//end by ABU
	        		//Neetika for removing extra rows..
	    			while (rowNumber >= detailsList.size() && rowNumber<65535 ) 
	    			{
	    			 row =sheet.createRow(++rowNumber);
	    			 sheet.removeRow(row);	 
	    			}
	        		
			  }
		   }
			else
			{
				 row = sheet.createRow(++rowNumber);
				 cell = row.createCell(10);
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		cell.setCellValue(" No Records found");
	        		//System.out.println(rowNumber);
	        		
	        		while (rowNumber>=0 && rowNumber<65535 ) 
	    			{
	        	     row =sheet.createRow(++rowNumber);
	    			 sheet.removeRow(row)	;	 
	    			}
			}
		   }
		   
		   
		   /*************************Pending PO closing stock******************************************/
		   else if(columnId.equals("5")){
			   response.setHeader("Content-Disposition", "attachment;filename="+productName+"ClosingDetailsOfPendingPoStock.xls");
			   logger.info("*********************Its for columnId=4***************************************");
			fpath=path  + "ClosingDetailsOfPendingPoStock.xls";
			logger.info("fpath=downloadClosingStockDetails ="+fpath);
			in = new FileInputStream(fpath);
	        // Create a workbook 
            workbook = new HSSFWorkbook(in);
           logger.info("fpath=in downloadClosingStockDetails after workbook ="+fpath);
            sheet = workbook.getSheetAt(0);
            //added by Beeru
			CellStyle unlockedCellStyle = workbook.createCellStyle();
			unlockedCellStyle.setLocked(false); 
			//end by Beeru
			rows = sheet.rowIterator();
			rowNumber = 0;
			row = (HSSFRow) rows.next(); 
			if(detailsList.size() > 0){
			while (rowNumber < detailsList.size() ) 
			{
				distRecoDTO = detailsList.get(rowNumber);
				 row = sheet.createRow(++rowNumber);
	             	            
	             	cell = row.createCell(0);
	        		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        		cell.setCellValue(rowNumber);

       			cell = row.createCell(1);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistOlmId()) || null == distRecoDTO.getDistOlmId())
	        			cell.setCellValue(" ");
	        		else
	        			cell.setCellValue(distRecoDTO.getDistOlmId());
       			
       			cell = row.createCell(2);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistName()) || null == distRecoDTO.getDistName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDistName());
       			
       			cell = row.createCell(3);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getTsmName()) || null == distRecoDTO.getTsmName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getTsmName());
       			
       			cell = row.createCell(4);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCircleName()) || null == distRecoDTO.getCircleName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCircleName());
       			
	        		cell = row.createCell(5);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getPoNo()) || null == distRecoDTO.getPoNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getPoNo());
	        		
	        		cell = row.createCell(6);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getPoDate()) || null == distRecoDTO.getPoDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getPoDate());
	        		
	        		cell = row.createCell(7);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDcNo()) || null == distRecoDTO.getDcNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDcNo());
	        		
	        		cell = row.createCell(8);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDcDate()) || null == distRecoDTO.getDcDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDcDate());
	        		
	             	cell = row.createCell(9);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getProductName()) || null == distRecoDTO.getProductName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getProductName());
	        		
	        		cell = row.createCell(10);
	        		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        		cell.setCellValue(distRecoDTO.getDispatchedQtyPerDc());

       			cell = row.createCell(11);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getProductSerialNo()) || null == distRecoDTO.getProductSerialNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getProductSerialNo());
	        		cell = row.createCell(12);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getPoStatus()) || null == distRecoDTO.getPoStatus())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getPoStatus()); 			
	        		
			  }
		   }
			else
			{
				 row = sheet.createRow(++rowNumber);
				 cell = row.createCell(6);
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		cell.setCellValue(" No Records found");
			}
		   }
		   
		   /*************************Pending DC closing stock******************************************/
		   else if(columnId.equals("6")){
			   response.setHeader("Content-Disposition", "attachment;filename="+productName+"ClosingDetailsOfPendingDcStock.xls");
			   logger.info("*********************Its for columnId=4***************************************");
			fpath=path  + "ClosingDetailsOfPendingDcStock.xls";
			logger.info("fpath=downloadClosingStockDetails ="+fpath);
			in = new FileInputStream(fpath);
	        // Create a workbook 
            workbook = new HSSFWorkbook(in);
           logger.info("fpath=in downloadClosingStockDetails after workbook ="+fpath);
            sheet = workbook.getSheetAt(0);
            //added by Beeru
			CellStyle unlockedCellStyle = workbook.createCellStyle();
			unlockedCellStyle.setLocked(false); 
			//end by Beeru
			rows = sheet.rowIterator();
			rowNumber = 0;
			row = (HSSFRow) rows.next(); 
			if(detailsList.size() > 0){
			while (rowNumber < detailsList.size() ) 
			{
				distRecoDTO = detailsList.get(rowNumber);
				 row = sheet.createRow(++rowNumber);
	             	            
	             	cell = row.createCell(0);
	        		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        		cell.setCellValue(rowNumber);

       			cell = row.createCell(1);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistOlmId()) || null == distRecoDTO.getDistOlmId())
	        			cell.setCellValue(" ");
	        		else
	        			cell.setCellValue(distRecoDTO.getDistOlmId());
       			
       			cell = row.createCell(2);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDistName()) || null == distRecoDTO.getDistName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDistName());
       			
       			cell = row.createCell(3);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getTsmName()) || null == distRecoDTO.getTsmName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getTsmName());
       			
       			cell = row.createCell(4);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCircleName()) || null == distRecoDTO.getCircleName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCircleName());
       			
	        		cell = row.createCell(5);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDcNo()) || null == distRecoDTO.getDcNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDcNo());
	        		
	             	cell = row.createCell(6);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDcDate()) || null == distRecoDTO.getDcDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDcDate());

       			cell = row.createCell(7);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getCollectionType()) || null == distRecoDTO.getCollectionType())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getCollectionType());
       			       
	        		cell = row.createCell(8);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getProductName()) || null == distRecoDTO.getProductName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getProductName());
	        		
	        		cell = row.createCell(9);
	        		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        		cell.setCellValue(distRecoDTO.getDispatchedQtyPerDc());
	        		
	        		cell = row.createCell(10);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getProductSerialNo()) || null == distRecoDTO.getProductSerialNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getProductSerialNo());
	        		
	        		cell = row.createCell(11);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(distRecoDTO.getDcStbStatus()) || null == distRecoDTO.getDcStbStatus())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(distRecoDTO.getDcStbStatus());
	        		
	        		/*cell = row.createCell(12);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("YES");
	        		
	        		cell = row.createCell(13);
	        		//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		//added by Beeru
	        		cell.setCellStyle(unlockedCellStyle);
	        		//end by Beeru
	        		cell.setCellValue("This is ok");*/
			  }
		   }
			else
			{
				 row = sheet.createRow(++rowNumber);
				 cell = row.createCell(10);
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		cell.setCellValue(" No Records found");
			}
		   }
		  
			logger.info("Excel written...");
			((HSSFSheet) sheet).protectSheet("Protected");
		     FileOutputStream fos  = new FileOutputStream(new File(filePath));
				workbook.write(fos);			
				logger.info("Excel writtennn..n.");
				in = new FileInputStream(filePath);
				byte[] outputByte = new byte[4096];
				while (in.read(outputByte, 0, 4096) != -1) {
					out.write(outputByte, 0, 4096);
				}
				out.flush();
				out.close();
				
		     //logger.info("Excel written...");
	     }catch (IOException ioe) { 
	    	
	    	 logger.info("Message from FLUSHOUT =================================="+ioe.getMessage());
	    	 ioe.printStackTrace();
			  // if this happens there is probably no way to report the error to the user
			  if (!response.isCommitted()) {
			    response.setContentType("text/html");
			    logger.error("Error in writing XLS file");
			  }
	     }
	     catch (Throwable t){
	     logger.info("Message from FLUSHOUT =================================="+t.getMessage()+" CAUSE IS :: EXCEPTION "+t.getCause());
	     t.printStackTrace();
	     }
		return null;
			}

	public ActionForward uploadClosingStockDetailsXls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
		ActionErrors errors = new ActionErrors();
		DistRecoBean formBean  = null;
		
		PrintWriter out=null;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			formBean =(DistRecoBean)form ;
			
			FormFile file = formBean.getUploadedFile();
			//String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			DistRecoService bulkupload = new DistRecoServiceImpl();
			logger.info("**  "+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			logger.info("***** arr :"+arr);
			/**************************************commented by pratap ------------*/
				String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				boolean found=false;
				boolean found2=false;
				logger.info("***** arr1 :"+arr);
				if(!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx"))
				{
					formBean.setStrmsg("Only Excel File is allowed here.");
					found=true;
				}
				//logger.info("***** arr12 :"+arr);
				 if(found==false)
				{
					 //logger.info("***** arr13 :"+arr);
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
					  // logger.info("***** arr133 :"+arr);
					   formBean.setStrmsg("Special Characters are not allowed in File Name.");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 formBean.setStrmsg("Two consecutive dots are not allowed in File Name.");
				}
				}
				 String productsListsText = request.getParameter("productsListsText");
				 String stockTypeText = request.getParameter("stockTypeText");
				// if(found2==false && found==false){
				 
				logger.info("Found2    ==    "+found2+"   ******   Found   ---   "+found);
				 if(found2==false && found==false){
					 String indexes=ResourceReader.getWebResourceBundleValue("UPLOAD.CLOSING.STOCK."+request.getParameter("columnId"));
					 logger.info("index of stock  for "+"UPLOAD.CLOSING.STOCK."+request.getParameter("columnId")+" is :"+indexes);
					 String excelMsg = bulkupload.uploadClosingStockDetailsXls(file,Integer.parseInt(request.getParameter("columnId")),Integer.parseInt(request.getParameter("productId")),distId,indexes,Integer.parseInt(request.getParameter("recoPeriodId")));
					if(excelMsg !=null && excelMsg != ""){
						formBean.setStrmsg(excelMsg);
					}else {
						
						formBean.setStrmsg("File Uploaded successfully for "+stockTypeText+" of "+productsListsText);
					
					 
					}
				 }
				
				
				if(request.getParameter("recoPeriodId") !="-1") {
					 request.setAttribute("onlyOneRecoPeriod", request.getParameter("recoPeriodId"));
					 request.setAttribute("uplaodformBean", formBean);
				 }
				 logger.info("index of stock  for =="+request.getParameter("recoPeriodId"));
		//}
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			e.printStackTrace();
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
		}
		 		return mapping.findForward("successuploadExl");
		 	}
	/*--------------------- END of Adding by Ram  ------------*/
	
	
	
	public ActionForward showUploadClosingStockDetailsWindow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
		DistRecoBean distRecoBean=(DistRecoBean)form;
		String tabId=request.getParameter("tabId");
		String columnId=request.getParameter("columnId");
		String lebelname=request.getParameter("browseButtonName");
		String productId=request.getParameter("productId");
		String recoId=request.getParameter("recoPeriodId");
		distRecoBean.setTabId(tabId);
		distRecoBean.setColumnId(columnId);
		distRecoBean.setStbTypeId(productId);
		distRecoBean.setBrowseButtonName(lebelname);
		distRecoBean.setRecoPeriodId(recoId);
		
		logger.info("************:tabId:"+tabId+" columnId:"+columnId+" lebelname:"+lebelname+" productId:"+productId+"  recoId :"+recoId);
		return mapping.findForward("uploadExlFileWindow");
			}

	//modify by vishwas
	public ActionForward findNotOkStbs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
		int recoPeriodId=Integer.parseInt(request.getParameter("recoPeriodId").toString());
		//added by vishwas
		String productId=request.getParameter("productId").toString();
		
		//System.out.println("hello vishwas=================================== "+productId);
		//end by vishwas
		PrintWriter out=null;
		String result=null;
		
		try
		{
		DistRecoService service = new DistRecoServiceImpl();
		logger.info("distId :"+distId+"  recoPeriodId :"+recoPeriodId);
		result=service.findNotOkStbs(distId,recoPeriodId,productId);
		//result=service.findNotOkStbs(distId,recoPeriodId);
		logger.info("Marked not ok stbs Result : "+result);
		response.setHeader("Cache-Control", "No-Cache");
		out=response.getWriter();
		out.println(result);
		out.flush();
		}
		catch(Exception ex)
		{
			logger.info("Exception while finding not ok stbs :"+ex.getMessage());
			ex.printStackTrace();
		}
		return null;
		}
//end by vishwas
	public ActionForward agreeWithSystemFigure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
		System.out.println("***********stockType:"+request.getParameter("stockType").toString());
		System.out.println("************productID:"+request.getParameter("productId").toString());
		return null;
			}
	
	
	
	
	//============added by vishwas for check xls file in data base
	public ActionForward uploadClosingStockDetailsXlscheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
		
		ActionErrors errors = new ActionErrors();
		DistRecoBean formBean  = null;
		String Validation=null;
		PrintWriter out=null;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			
			formBean =(DistRecoBean)form ;
			
			FormFile file = formBean.getUploadedFile();
			
			//String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			DistRecoService bulkupload = new DistRecoServiceImpl();
			
			/**************************************commented by pratap ------------*/
			String okWithSystemStock = request.getParameter("okWithSystemStock");
			//logger.info(okWithSystemStock);
			
			 Validation=bulkupload.Uploadvalidation(distId, Integer.parseInt(request.getParameter("recoPeriodId")), request.getParameter("productId"),Integer.parseInt(request.getParameter("columnId")),okWithSystemStock);
			 if(Validation !=null && Validation.equals("success")) {
				 request.setAttribute("onlyOneRecoPeriod", request.getParameter("recoPeriodId")); //// for ok with System generated clasing stock
				 if(okWithSystemStock.equalsIgnoreCase("5"))
				 formBean.setStrmsg(null);
				 request.setAttribute("uplaodformBean", formBean);
				
				 return mapping.findForward("successuploadExl");
			 }
	        response.setContentType("text/html;charset=utf-8");
	        response.setHeader("cache-control", "no-cache");
	        out=response.getWriter();
	        out.write(Validation);
	        out.flush();
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			e.printStackTrace();
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
		}
		 return null;
		 		//return null;
	}

	
	public ActionForward uploadClosingStockDetailsXlsagain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
		
		ActionErrors errors = new ActionErrors();
		DistRecoBean formBean  = null;
		String Validation=null;
		Boolean confirm=false;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			formBean =(DistRecoBean)form ;
			
			FormFile file = formBean.getUploadedFile();
			//String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			DistRecoService bulkupload = new DistRecoServiceImpl();
			logger.info("**  "+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			logger.info("***** arr :"+arr);
			/**************************************commented by pratap ------------*/
				String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				
				boolean found=false;
				boolean found2=false;
				if(!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx"))
				{
					formBean.setStrmsg("Only Excel File is allowed here.");
					found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
				     
					   formBean.setStrmsg("Special Characters are not allowed in File Name.");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 formBean.setStrmsg("Two consecutive dots are not allowed in File Name.");
				}
				}
				 if(found2==false && found==false){
					 String indexes=ResourceReader.getWebResourceBundleValue("UPLOAD.CLOSING.STOCK."+request.getParameter("columnId"));
					 String productsListsText = request.getParameter("productsListsText");
					 String stockTypeText = request.getParameter("stockTypeText");
					 String excelMsg = bulkupload.uploadClosingStockDetailsXlsagain(file,Integer.parseInt(request.getParameter("columnId")),Integer.parseInt(request.getParameter("productId")),distId,indexes,Integer.parseInt(request.getParameter("recoPeriodId")));
//Added by Neetika on 21 May for the issue reported for secodn time uploads where the message was not setting properly
					 if(excelMsg !=null && excelMsg != ""){
							formBean.setStrmsg(excelMsg);
						}
					 	else {
									
					 	formBean.setStrmsg("File Uploaded successfully for "+stockTypeText+" of "+productsListsText);
							 
						}
//end by neetika on 21 May
					
					 

				 }
				 if(request.getParameter("recoPeriodId") !="-1") {
					 request.setAttribute("onlyOneRecoPeriod", request.getParameter("recoPeriodId"));
					 request.setAttribute("uplaodformBean", formBean);
				 }
				 
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::In Again  "+e.getMessage());
			e.printStackTrace();
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
		}
		return mapping.findForward("successuploadExl");
		//return null;
	}

	}
