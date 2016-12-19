package com.ibm.dp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.InvoiceDetails;
import com.ibm.dp.beans.InvoiceForm;
import com.ibm.dp.beans.InvoiceListForm;
import com.ibm.dp.dto.DpInvoiceDto;
import com.ibm.dp.dto.PartnerDetails;
import com.ibm.dp.dto.RateDTO;
import com.ibm.dp.service.DpInvoiceUploadService;
import com.ibm.dp.service.impl.DpInvoiceUploadServiceImpl;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class ViewListInvoiceAction extends DispatchAction {
	
	
	private static Logger logger=Logger.getLogger(ViewListInvoiceAction.class);
	
	public ActionForward listInvoices(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
	{
		InvoiceListForm listinvForm = (InvoiceListForm)form;
		List<InvoiceDetails> invList=null;
		
		DpInvoiceUploadService invServc=new DpInvoiceUploadServiceImpl();
		UserSessionContext userSession=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		try {
			invList=invServc.listInvoices(userSession.getLoginName());
			logger.info("LOGIN->"+userSession.getLoginName());
		} catch (Exception e) {
			logger.info("Error in loading invoices");
			logger.info("LOGIN->"+userSession.getLoginName());
			ActionMessages messages=new ActionMessages();
			messages.add("message",new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);
			
			invList=new ArrayList<InvoiceDetails>();
			
			listinvForm.setInvoiceList(invList);
		//	request.setAttribute("InvoiceListForm", listinvForm);
			return mapping.findForward("errorListingInv");
			
		}
		
		listinvForm.setInvoiceList(invList);
		//request.setAttribute("InvoiceListForm", listinvForm);
		return mapping.findForward("listInvoiceSuccess");
		
	}
	
	public ActionForward listInvoicesSTB(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		InvoiceListForm listinvForm = (InvoiceListForm)form;
		List<InvoiceDetails> invList = null;

		DpInvoiceUploadService invServc = new DpInvoiceUploadServiceImpl();
		UserSessionContext userSession = (UserSessionContext) request
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		try {
			invList = invServc.listInvoicesSTB(userSession.getLoginName());
			logger.info("LOGIN->" + userSession.getLoginName());
			logger.info("List size is "+ invList.size());
			logger.info("Partner Name -"+invList.get(0).getPartnerNm());
		} catch (Exception e) {
			logger.info("Error in loading invoices");
			logger.info("LOGIN->" + userSession.getLoginName());
			ActionMessages messages = new ActionMessages();
			messages.add("message", new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);

			invList = new ArrayList<InvoiceDetails>();

			listinvForm.setInvoiceList(invList);
			//request.setAttribute("InvoiceListForm", listinvForm);
			return mapping.findForward("errorListingInv");

		}

		listinvForm.setInvoiceList(invList);
		logger.info("Form me kya hai--->"+listinvForm.getInvoiceList().get(0).getBillNo());
	//	request.setAttribute("InvoiceListForm", listinvForm);
		return mapping.findForward("listInvoiceSuccess");

	}
	
	protected ActionForward unspecified (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int billNo=Integer.parseInt(request.getParameter("billNo").substring(0,request.getParameter("billNo").indexOf("/")));
		int flag=0,distFlag=0;
		DpInvoiceUploadService invServc=new DpInvoiceUploadServiceImpl();
		String address= invServc.fetchAddress();
		UserSessionContext sessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		int groupId=sessionContext.getGroupId();
		if(groupId==15){
			request.setAttribute("Finlogin", "Y");
		}
		logger.info(billNo + "is the bill No");
		try{
		DpInvoiceDto invDto;
		if(request.getParameter("billNo").contains("CPE_SLA"))
		{
			 invDto=invServc.partnerInvoice(billNo);
			 flag=1;
		}
		else
			 invDto=invServc.partnerInvoiceSTB(billNo);
		
		String olmId=invDto.getOlmId();
		PartnerDetails partnerDet=invServc.partnerDetails(olmId);
		RateDTO rateDto=invServc.fetchRates(invDto.getBilDt());
		
		request.setAttribute("partner", partnerDet);
		request.setAttribute("rates",rateDto);
		
		
		/*DpInvoiceBeanCollection invCol = new DpInvoiceBeanCollection();
		invCol.setInvDto(invDto);
		invCol.setPartnerDet(partnerDet);
		invCol.setRateDto(rateDto);
		HttpSession session=request.getSession();
		session.setAttribute("DpInvoiceCollection", invCol);
		*/
		
		if(groupId==7){
			if(invDto.getStatus().equalsIgnoreCase("U")){
				distFlag=1;
			}
		}
		if(distFlag==0){
			request.setAttribute("Finlogin", "Y");
		}
		
		logger.info("Partner "+ invDto.getPartnerNm());
		logger.info("OLM_ID "+invDto.getOlmId());
		request.setAttribute("invoice", invDto);
		request.setAttribute("address", address);
		
		}catch(Exception exp){
			exp.printStackTrace();
			logger.info("Exception in retrieving invoice data");
			ActionMessages messages=new ActionMessages();
			messages.add("message",new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);
			
			return mapping.findForward("viewInvoiceSuccess");
		}
		
        if(flag==1)
        	return mapping.findForward("viewInvoiceSuccess");
        else
        	return mapping.findForward("viewInvoiceSuccessSTB");
	}
	public ActionForward acceptInvoice (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		
		String statusMsg="";
		DpInvoiceUploadService dpinvServ=new DpInvoiceUploadServiceImpl();
		InvoiceForm inform=(InvoiceForm) form;
		int billNo=inform.getBilNo();
		String invoiceNo=inform.getInvoiceNo();
		String remarks=inform.getPartnerRem();
		UserSessionContext sessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		String address= dpinvServ.fetchAddress();
		String loginName = sessionContext.getLoginName();
		/*HttpSession session = request.getSession();
		DpInvoiceBeanCollection invCol = (DpInvoiceBeanCollection) session.getAttribute("DpInvoiceCollection");
		
		
		invCol.getInvDto().setInvNo(invoiceNo);
		session.setAttribute("DpInvoiceCollection", invCol);*/
		DpInvoiceDto invDto = dpinvServ.partnerInvoice(billNo);
		RateDTO rateDto = dpinvServ.fetchRates(invDto.getBilDt());
		PartnerDetails partnerDet = dpinvServ.partnerDetails(loginName);
		String dt = invDto.getBilDt().toString().substring(5, 7);
		dt = dt+"_"+invDto.getBilDt().toString().substring(0, 4);
		String fileName = loginName+"_"+dt+"_"+invoiceNo+".pdf";
		
		
		
		try{
			String filePath = ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.CREATE_PDF)+fileName;
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			  document.open();
			  PdfPTable t = new PdfPTable(1);
			  PdfPTable t1 = new PdfPTable(2);
			  
			  Phrase p1 = new Phrase("NAME OF THE CUSTOMER",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c1 = new PdfPCell(p1);
			  c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t1.addCell(c1);
			  Phrase p2 = new Phrase("SERVICE FRACNHISEE NAME & ADDRESS",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c2 = new PdfPCell(p2);
			  c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t1.addCell(c2);
			  Phrase p3 = new Phrase("Bharti Telemedia Limited",FontFactory.getFont(FontFactory.TIMES, 8));
			  t1.addCell(p3);
			  Phrase NOT = new Phrase(" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  Phrase p4 = new Phrase(partnerDet.getAccountAddress()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t1.addCell(p4);
			  logger.info("ADDRESS**** "+address);
			  Phrase p5 = new Phrase(address ,FontFactory.getFont(FontFactory.TIMES, 8));
			  t1.addCell(p5);
			  Phrase p6 = new Phrase(partnerDet.getAccAddress2()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  
			  t1.addCell(p6);
			  
			  PdfPTable t2 = new PdfPTable(2);
			  Phrase p7 = new Phrase("Bank Details of Service Providers for E-Payments",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  t2.addCell(p7);
			  t2.addCell(NOT);
			  PdfPTable t3 = new PdfPTable(2);
			  Phrase p8 = new Phrase("Sales Code.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p8);
			  t3.addCell(NOT);
			  Phrase p9 = new Phrase("Vendor Code.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p9);
			  t3.addCell(NOT);
			  Phrase p10 = new Phrase("Bank Details",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p10);
			  t3.addCell(NOT);
			  Phrase p11 = new Phrase("Name Of the Bank",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p11);
			  t3.addCell(NOT);
			  Phrase p12 = new Phrase("Branch Name",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p12);
			  t3.addCell(NOT);
			  Phrase p13 = new Phrase("IFSC Code",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p13);
			  t3.addCell(NOT);
			  Phrase p14 = new Phrase("Account Number",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p14);
			  t3.addCell(NOT);
			  
			  PdfPTable t4 = new PdfPTable(2);
			  Phrase p15 = new Phrase("Bill No.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p15);
			  Phrase p16 = new Phrase(invoiceNo+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p16);
			  Phrase p17 = new Phrase("Bill Date",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p17);
			  Phrase p18 = new Phrase(invDto.getBilDt().toString()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p18);
			  Phrase p19 = new Phrase("Period",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p19);
			  Phrase p211 = new Phrase(invDto.getMonthFr()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p211);
			  Phrase p20 = new Phrase("Tin No.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p20);
			  Phrase p21 = new Phrase(partnerDet.getTinNo()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p21);
			  Phrase p22 = new Phrase("PAN Number",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p22);
			  Phrase p23 = new Phrase(partnerDet.getPanNo()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p23);
			  Phrase p24 = new Phrase("Service Tax Regn. No.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p24);
			  Phrase p25 = new Phrase(partnerDet.getServcTxNo()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p25);
			  Phrase p26 = new Phrase("Service Tax Category",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p26);
			  t4.addCell(NOT);
			  
			  t2.addCell(t3);
			  t2.addCell(t4);
			  
			  PdfPTable t5 = new PdfPTable(2);
			  PdfPTable t6 = new PdfPTable(1);
			  PdfPTable t7 = new PdfPTable(3);
			  
			  Phrase p27 = new Phrase("COMPONENT",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c3 = new PdfPCell(p27);
			  c3.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t6.addCell(c3);
			  Phrase p28 = new Phrase("CPE Recovery",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p28);
			  Phrase p29 = new Phrase("SR Reward",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p29);
			  Phrase p30 = new Phrase("Retention",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p30);
			  Phrase p31 = new Phrase("PTR",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p31);
			  Phrase p32 = new Phrase("Others",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p32);
			  Phrase p100=new Phrase("Others1",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p100);
			  t6.addCell(NOT);
			  t6.addCell(NOT);
			  Phrase p33 = new Phrase("Total",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p33);
			  
			  t5.addCell(t6);
			  
			  Phrase p34 = new Phrase("NOs",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c4 = new PdfPCell(p34);
			  c4.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t7.addCell(c4);
			  Phrase p35 = new Phrase("RATE",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c5 = new PdfPCell(p35);
			  c5.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t7.addCell(c5);
			  Phrase p36 = new Phrase("AMOUNT",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c6 = new PdfPCell(p36);
			  c6.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t7.addCell(c6);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p37 = new Phrase(Integer.toString((int) invDto.getCperecAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c7 = new PdfPCell(p37);
			  c7.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c7.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c7);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p38 = new Phrase(Integer.toString((int) invDto.getSrrwdAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c8 = new PdfPCell(p38);
			  c8.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c8.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c8);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p39 = new Phrase(Integer.toString((int) invDto.getRetAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c9 = new PdfPCell(p39);
			  c9.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c9.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c9);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p40 = new Phrase(Integer.toString((int) invDto.getOthAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c10 = new PdfPCell(p40);
			  c10.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c10.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c10);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p80 = new Phrase(Integer.toString((int) invDto.getOthers())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c30 = new PdfPCell(p80);
			  c30.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c30.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c30);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p81 = new Phrase(Integer.toString((int) invDto.getOthers1())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c31 = new PdfPCell(p81);
			  c31.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c31.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c31);
			  
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  t7.addCell(NOT);

			  t7.addCell(NOT);
			  Phrase p41 = new Phrase(Integer.toString((int) invDto.getTotal())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c11 = new PdfPCell(p41);
			  c11.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c11.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c11);
			  
			  t5.addCell(t7);
			  
			  PdfPTable t8 = new PdfPTable(2);
			  PdfPTable t9 = new PdfPTable(1);
			  PdfPTable t10 = new PdfPTable(3);
			  
			  Phrase p42 = new Phrase("Service Tax",FontFactory.getFont(FontFactory.TIMES, 8));
			  t9.addCell(p42);
			  Phrase p43 = new Phrase("Swachcha Bharat Cess",FontFactory.getFont(FontFactory.TIMES, 8));
			  t9.addCell(p43);
			  Phrase p44 = new Phrase("Krishi Kalyan Cess",FontFactory.getFont(FontFactory.TIMES, 8));
			  t9.addCell(p44);
			  /*Phrase p44 = new Phrase("Higher Educational Cess",FontFactory.getFont(FontFactory.TIMES, 8));
			  t9.addCell(p44);
*/			  Phrase p45 = new Phrase("GRAND TOTAL",FontFactory.getFont(FontFactory.TIMES, 8));
			  t9.addCell(p45);
			  
			  t8.addCell(t9);
			  
			  t10.addCell(NOT);
			  Phrase p46 = new Phrase(Double.toString((double) rateDto.getServiceTx())+"% ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c12 = new PdfPCell(p46);
			  c12.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c12.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c12);
			  logger.info("Service Tax Rate :::"+rateDto.getServiceTx());
			  Phrase p47 = new Phrase(Integer.toString((int) invDto.getSrvTxAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c13 = new PdfPCell(p47);
			  c13.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c13.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c13);
			  t10.addCell(NOT);
			  logger.info("Service tax amt :::"+invDto.getSrvTxAmt());
			  logger.info("Not created  --    ");
			  /*Phrase p48 = new Phrase(Integer.toString((int) rateDto.getEduCess())+"% ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c14 = new PdfPCell(p48);
			  c14.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c14.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c14);
			  logger.info("Rate created  --    ");
			  Phrase p49 = new Phrase(Integer.toString((int) invDto.getEduCesAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c15 = new PdfPCell(p49);
			  c15.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c15.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c15);*/
			  logger.info("Amount created created  --    ");
			  
			  //t10.addCell(NOT);
			  logger.info("Not created  --    ");
			  Phrase p50 = new Phrase(Double.toString((double) rateDto.getEduCess())+"% ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c16 = new PdfPCell(p50);
			  c16.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c16.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c16);
			  logger.info("Rate created   --    "+rateDto.getEduCess());
			  Phrase p51 = new Phrase(Integer.toString((int) invDto.getEduCesAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c17 = new PdfPCell(p51);
			  c17.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c17.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c17);
			  logger.info("Amount created created  --    "+invDto.getEduCesAmt());
			  
			  
			  logger.info("Not created  --    ");
			  //t10.addCell(NOT);
			  logger.info("Not created  --    ");
			  //kkc
			  
			  t10.addCell(NOT);
			  Phrase p56 = new Phrase(Double.toString((double) rateDto.getKkcess())+"% ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c21 = new PdfPCell(p56);
			  c21.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c21.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c21);
			  logger.info("KKC Rate created  --    "+rateDto.getKkcess());
			  Phrase p57 = new Phrase(Integer.toString((int) invDto.getKkcamt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c22 = new PdfPCell(p57);
			  c22.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c22.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c22);
			  logger.info("KKC Amount created created  --    "+invDto.getKkcamt());
			  
			  			  
			  
			  
			  //kkc
			  
			  t10.addCell(NOT);
			  logger.info("Not created  --    ");
			  t10.addCell(NOT);
			  logger.info("Not created  --    ");
			  Phrase p52 = new Phrase(Integer.toString((int) invDto.getGrndTotal())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c18 = new PdfPCell(p52);
			  c18.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c18.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c18);
			  logger.info("Total created  --    "+invDto.getGrndTotal());
			  
			  t8.addCell(t10);
			  
			  t.setSpacingBefore(25);
			  t.setSpacingAfter(25);
			  
			  Phrase p53 = new Phrase(partnerDet.getAccountName(),FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c19 = new PdfPCell(p53);
			  c19.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c19.setVerticalAlignment(Element.ALIGN_MIDDLE);
		      t.addCell(c19);
			  Phrase p54 = new Phrase("INVOICE",FontFactory.getFont(FontFactory.TIMES_BOLD, 8));
			  PdfPCell c20 = new PdfPCell(p54);
			  c20.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c20.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t.addCell(c20);
			  t.addCell(t1);
			  t.addCell(NOT);
			  t.addCell(t2);
			  t.addCell(NOT);
			  t.addCell(t5);
			  t.addCell(NOT);
			  t.addCell(t8);
			  Phrase p55 = new Phrase("Amount In words(Rs.)   "+invDto.getAmountInWords(),FontFactory.getFont(FontFactory.TIMES, 8));
			  t.addCell(p55);
			  
			  Chapter chapter1 = new Chapter(1);
			  chapter1.add(t);
			  document.add(chapter1);
			  document.close();
			  
			  boolean flag = dpinvServ.sftpInvoiceUpload(filePath, loginName);	
			  request.setAttribute("fileName", fileName);
			  request.setAttribute("address", address);
			  
		
		statusMsg=dpinvServ.acceptInvoice(billNo, invoiceNo,remarks);
		}catch(Exception exp){
			exp.printStackTrace();
			logger.info("Exception in updating invoice data");
			ActionMessages messages=new ActionMessages();
			messages.add("message",new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);
			
			return mapping.findForward("viewInvoiceSuccess");
		}
		inform.reset(mapping, request);
		inform.setBilNo(billNo);
		inform.setInvoiceMessage(statusMsg);
		
		return mapping.findForward(statusMsg);
		
	}
	public ActionForward rejectInvoice (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){ 
		
		String statusMsg="";
		DpInvoiceUploadService dpinvServ=new DpInvoiceUploadServiceImpl();
		InvoiceForm inform=(InvoiceForm) form;
		int billNo=inform.getBilNo();
		String remarks=inform.getPartnerRem();
		String invoiceNo=inform.getInvoiceNo();
		try{
		statusMsg=dpinvServ.rejectInvoice(billNo, invoiceNo,remarks);
		}catch(Exception exp){
			exp.printStackTrace();
			logger.info("Exception in updating invoice data");
			ActionMessages messages=new ActionMessages();
			messages.add("message",new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);
			
			return mapping.findForward("viewInvoiceSuccess");
		}
		inform.reset(mapping, request);
		inform.setBilNo(billNo);
		inform.setInvoiceMessage(statusMsg);
		return mapping.findForward(statusMsg);
		
	}
	public ActionForward listRejectedInvoices(ActionMapping mapping, ActionForm form,
											HttpServletRequest request,HttpServletResponse response){
		
		
		
		InvoiceListForm listinvForm=new InvoiceListForm();
		List<InvoiceDetails> invList=null;
		
		DpInvoiceUploadService invServc=new DpInvoiceUploadServiceImpl();
		UserSessionContext userSession=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		try {
			invList=invServc.listRejectedInvoices();
			logger.info("LOGIN->"+userSession.getLoginName());
		} catch (Exception e) {
			logger.info("Error in loading invoices");
			logger.info("LOGIN->"+userSession.getLoginName());
			ActionMessages messages=new ActionMessages();
			messages.add("message",new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);
			
			invList=new ArrayList<InvoiceDetails>();
			
			listinvForm.setInvoiceList(invList);
			request.setAttribute("InvoiceListForm", listinvForm);
			return mapping.findForward("errorListingInv");
			
		}
		
		listinvForm.setInvoiceList(invList);
		request.setAttribute("InvoiceListForm", listinvForm);
		return mapping.findForward("listInvoiceSuccess");
	}
	
	public ActionForward acceptInvoiceSTB (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		
		String statusMsg="";
		DpInvoiceUploadService dpinvServ=new DpInvoiceUploadServiceImpl();
		InvoiceForm inform=(InvoiceForm) form;
		int billNo=inform.getBilNo();
		String invoiceNo=inform.getInvoiceNo();
		String remarks=inform.getPartnerRem();
		UserSessionContext sessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		String loginName = sessionContext.getLoginName();
		String address= dpinvServ.fetchAddress();
		
		/*HttpSession session = request.getSession();
		DpInvoiceBeanCollection invCol = (DpInvoiceBeanCollection) session.getAttribute("DpInvoiceCollection");
		invCol.getInvDto().setInvNo(invoiceNo);
		session.setAttribute("DpInvoiceCollection", invCol);
		DpInvoiceDto invDto = invCol.getInvDto();
		RateDTO rateDto = invCol.getRateDto();
		PartnerDetails partnerDet = invCol.getPartnerDet();
		*/
		
		DpInvoiceDto invDto = dpinvServ.partnerInvoiceSTB(billNo);
		RateDTO rateDto = dpinvServ.fetchRates(invDto.getBilDt());
		PartnerDetails partnerDet = dpinvServ.partnerDetails(loginName);
		
		String dt = invDto.getBilDt().toString().substring(5, 7);
		dt = dt+"_"+invDto.getBilDt().toString().substring(0, 4);
		String fileName = loginName+"_"+dt+"_"+invoiceNo+".pdf";
		try{
			
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			String filePath = ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.CREATE_PDF)+fileName;
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			  document.open();
			  PdfPTable t = new PdfPTable(1);
			  PdfPTable t1 = new PdfPTable(2);
			  
			  Phrase p1 = new Phrase("NAME OF THE CUSTOMER",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c1 = new PdfPCell(p1);
			  c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t1.addCell(c1);
			  Phrase p2 = new Phrase("SERVICE FRACNHISEE NAME & ADDRESS",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c2 = new PdfPCell(p2);
			  c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t1.addCell(c2);
			  Phrase p3 = new Phrase("Bharti Telemedia Limited",FontFactory.getFont(FontFactory.TIMES, 8));
			  t1.addCell(p3);
			  Phrase NOT = new Phrase(" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  Phrase p4 = new Phrase(partnerDet.getAccountAddress()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t1.addCell(p4);
			  logger.info("ADDRESS**** "+address);
			  Phrase p5 = new Phrase(address,FontFactory.getFont(FontFactory.TIMES, 8));
			  t1.addCell(p5);
			  Phrase p6 = new Phrase(partnerDet.getAccAddress2()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  
			  t1.addCell(p6);
			  //t1.addCell(NOT);
			  //t1.addCell(NOT);
			  //t1.addCell(NOT);
			  //t1.addCell(NOT);
			  			  
			  PdfPTable t2 = new PdfPTable(2);
			  Phrase p7 = new Phrase("Bank Details of Service Providers for E-Payments",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  t2.addCell(p7);
			  t2.addCell(NOT);
			  PdfPTable t3 = new PdfPTable(2);
			  Phrase p8 = new Phrase("Sales Code.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p8);
			  t3.addCell(NOT);
			  Phrase p9 = new Phrase("Vendor Code.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p9);
			  t3.addCell(NOT);
			  Phrase p10 = new Phrase("Bank Details",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p10);
			  t3.addCell(NOT);
			  Phrase p11 = new Phrase("Name Of the Bank",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p11);
			  t3.addCell(NOT);
			  Phrase p12 = new Phrase("Branch Name",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p12);
			  t3.addCell(NOT);
			  Phrase p13 = new Phrase("IFSC Code",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p13);
			  t3.addCell(NOT);
			  Phrase p14 = new Phrase("Account Number",FontFactory.getFont(FontFactory.TIMES, 8));
			  t3.addCell(p14);
			  t3.addCell(NOT);
			  
			  PdfPTable t4 = new PdfPTable(2);
			  Phrase p15 = new Phrase("Bill No.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p15);
			  Phrase p16 = new Phrase(invoiceNo+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p16);
			  Phrase p17 = new Phrase("Bill Date",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p17);
			  if(invDto.getBilDt()!=null)
			  {
				  Phrase p18 = new Phrase(invDto.getBilDt().toString(),FontFactory.getFont(FontFactory.TIMES, 8));
				  t4.addCell(p18);
			  }
			  else
			  {
				  t4.addCell(NOT);
			  }
			  Phrase p19 = new Phrase("Period",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p19);
			  Phrase p211 = new Phrase(invDto.getMonthFr()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p211);
			  Phrase p20 = new Phrase("Tin No.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p20);
			  Phrase p21 = new Phrase(partnerDet.getTinNo()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p21);
			  Phrase p22 = new Phrase("PAN Number",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p22);
			  Phrase p23 = new Phrase(partnerDet.getPanNo()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p23);
			  Phrase p24 = new Phrase("PAN Service Tax Regn. No.",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p24);
			  Phrase p25 = new Phrase(partnerDet.getServcTxNo()+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p25);
			  Phrase p26 = new Phrase("Service Tax Category",FontFactory.getFont(FontFactory.TIMES, 8));
			  t4.addCell(p26);
			  t4.addCell(NOT);
			  
			  t2.addCell(t3);
			  t2.addCell(t4);
			  
			  PdfPTable t5 = new PdfPTable(2);
			  PdfPTable t6 = new PdfPTable(1);
			  PdfPTable t7 = new PdfPTable(3);
			  
			  Phrase p27 = new Phrase("COMPONENT",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c3 = new PdfPCell(p27);
			  c3.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t6.addCell(c3);
			  Phrase p28 = new Phrase("Installation Call Standard STB/HD (Tier-1)",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p28);
			  Phrase p80 = new Phrase("Installation Call Standard STB/HD (Tier-2)",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p80);
			  Phrase p81 = new Phrase("Installation Call Standard STB/HD (Tier-3)",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p81);
			  Phrase p29 = new Phrase("Installation Call HD DVR STB (Tier-1)",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p29);
			  Phrase p82 = new Phrase("Installation Call HD DVR STB (Tier-2)",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p82);
			  Phrase p83 = new Phrase("Installation Call HD DVR STB (Tier-3)",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p83);
			  Phrase p30 = new Phrase("Installation Call Multi Conn HD DVR STB (Tier-1/2/3) ",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p30);
			  Phrase p31 = new Phrase("Installation Call Multi Connection STB (Tier-1/2/3)",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p31);
			  Phrase p32 = new Phrase("Upgradation to HD",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p32);
			  Phrase p33 = new Phrase("Upgradation to DVR",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p33);
			  Phrase p34 = new Phrase("Field Repairs and Relocation",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p34);
			  Phrase p35 = new Phrase("Weak Geography Payout",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p35);
			  Phrase p36 = new Phrase("Service Request payable after One year",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p36);
			  Phrase p37 = new Phrase("Additional Installation Payout Hilly Area",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p37);
			  Phrase p38 = new Phrase("ARP",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p38);
			  Phrase p39 = new Phrase("Others",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p39);
			  Phrase p391 = new Phrase("Others1",FontFactory.getFont(FontFactory.TIMES, 8));
			  t6.addCell(p391);
			  
			  t5.addCell(t6);
			  
			  Phrase p41 = new Phrase("NOs",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c4 = new PdfPCell(p41);
			  c4.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t7.addCell(c4);
			  Phrase p42 = new Phrase("RATE",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c5 = new PdfPCell(p42);
			  c5.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t7.addCell(c5);
			  Phrase p43 = new Phrase("AMOUNT",FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c6 = new PdfPCell(p43);
			  c6.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t7.addCell(c6);
			  
			  
			  int tier=invDto.getTier();
			  //int noHdStb = invDto.getHdextT1()+invDto.getHdextT2()+invDto.getHdextT3();
			  Phrase p45 = new Phrase(Integer.toString((int) invDto.getHdextT1())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c8 = new PdfPCell(p45);
			  c8.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c8.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c8);
			  /*int insHdStb = 0;
			  if(tier==1){insHdStb = rateDto.getHdRateT1();}
			  if(tier==2){insHdStb = rateDto.getHdRateT2();}
			  if(tier==3){insHdStb = rateDto.getHdRateT3();}*/
			  if(rateDto.getHdRateT1()!=0){
			  Phrase p46 = new Phrase(Integer.toString((int) rateDto.getHdRateT1())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c9 = new PdfPCell(p46);
			  c9.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c9.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c9);
			  }
			  else{
				  t7.addCell(NOT);
			  }
			  Phrase p47 = new Phrase(Integer.toString((int) invDto.getHdextT1()*rateDto.getHdRateT1())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c10 = new PdfPCell(p47);
			  c10.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c10.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c10);
			  
			  Phrase p89 = new Phrase(Integer.toString((int) invDto.getHdextT2())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c65 = new PdfPCell(p89);
			  c65.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c65.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c65);
			  /*int insHdStb = 0;
			  if(tier==1){insHdStb = rateDto.getHdRateT1();}
			  if(tier==2){insHdStb = rateDto.getHdRateT2();}
			  if(tier==3){insHdStb = rateDto.getHdRateT3();}*/
			  if(rateDto.getHdRateT2()!=0){
			  Phrase p46 = new Phrase(Integer.toString((int) rateDto.getHdRateT2())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c9 = new PdfPCell(p46);
			  c9.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c9.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c9);
			  }
			  else{
				  t7.addCell(NOT);
			  }
			  Phrase p85 = new Phrase(Integer.toString((int) invDto.getHdextT2()*rateDto.getHdRateT2())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c60 = new PdfPCell(p85);
			  c60.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c60.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c60);
			  
			  Phrase p86 = new Phrase(Integer.toString((int) invDto.getHdextT3())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c61 = new PdfPCell(p86);
			  c61.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c61.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c61);
			  /*int insHdStb = 0;
			  if(tier==1){insHdStb = rateDto.getHdRateT1();}
			  if(tier==2){insHdStb = rateDto.getHdRateT2();}
			  if(tier==3){insHdStb = rateDto.getHdRateT3();}*/
			  if(rateDto.getHdRateT3()!=0){
			  Phrase p87 = new Phrase(Integer.toString((int) rateDto.getHdRateT3())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c62 = new PdfPCell(p87);
			  c62.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c62.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c62);
			  }
			  else{
				  t7.addCell(NOT);
			  }
			  Phrase p88 = new Phrase(Integer.toString((int) invDto.getHdextT3()*rateDto.getHdRateT3())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c63 = new PdfPCell(p88);
			  c63.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c63.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c63);
			  
			  
			  
			  //int noDvrStb = invDto.getDvrT1()+invDto.getDvrT2()+invDto.getDvrT3();
			  Phrase p48 = new Phrase(Integer.toString((int) invDto.getDvrT1())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c11 = new PdfPCell(p48);
			  c11.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c11.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c11);
			  /*int insDvrStb = 0;
			  if(tier==1){insDvrStb = rateDto.getDvrRateT1();}
			  if(tier==2){insDvrStb = rateDto.getDvrRateT2();}
			  if(tier==3){insDvrStb = rateDto.getDvrRateT3();}*/
			  if(rateDto.getDvrRateT1()!=0){
			  Phrase p49 = new Phrase(Integer.toString((int) rateDto.getDvrRateT1())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c12 = new PdfPCell(p49);
			  c12.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c12.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c12);
			  }
			  else{
				  t7.addCell(NOT);
			  }
			  Phrase p50 = new Phrase(Integer.toString((int) invDto.getDvrT1()*rateDto.getDvrRateT1())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c13 = new PdfPCell(p50);
			  c13.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c13.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c13);
			  
			  Phrase p90 = new Phrase(Integer.toString((int) invDto.getDvrT2())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c64 = new PdfPCell(p90);
			  c64.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c64.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c64);
			  /*int insDvrStb = 0;
			  if(tier==1){insDvrStb = rateDto.getDvrRateT1();}
			  if(tier==2){insDvrStb = rateDto.getDvrRateT2();}
			  if(tier==3){insDvrStb = rateDto.getDvrRateT3();}*/
			  if(rateDto.getDvrRateT2()!=0){
			  Phrase p91 = new Phrase(Integer.toString((int) rateDto.getDvrRateT2())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c66 = new PdfPCell(p91);
			  c66.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c66.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c66);
			  }
			  else{
				  t7.addCell(NOT);
			  }
			  Phrase p92 = new Phrase(Integer.toString((int) invDto.getDvrT2()*rateDto.getDvrRateT2())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c67 = new PdfPCell(p92);
			  c67.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c67.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c67);
			  
			  Phrase p93 = new Phrase(Integer.toString((int) invDto.getDvrT3())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c68 = new PdfPCell(p93);
			  c68.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c68.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c68);
			  /*int insDvrStb = 0;
			  if(tier==1){insDvrStb = rateDto.getDvrRateT1();}
			  if(tier==2){insDvrStb = rateDto.getDvrRateT2();}
			  if(tier==3){insDvrStb = rateDto.getDvrRateT3();}*/
			  if(rateDto.getDvrRateT3()!=0){
			  Phrase p94 = new Phrase(Integer.toString((int) rateDto.getDvrRateT3())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c69 = new PdfPCell(p94);
			  c69.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c69.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c69);
			  }
			  else{
				  t7.addCell(NOT);
			  }
			  Phrase p95 = new Phrase(Integer.toString((int) invDto.getDvrT3()*rateDto.getDvrRateT3())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c70 = new PdfPCell(p95);
			  c70.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c70.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c70);
			  
			  
			  
			  
			  int noMultiDvr = invDto.getMultidvrT1()+invDto.getMultidvrT2()+invDto.getMultidvrT3();
			  Phrase p51 = new Phrase(Integer.toString((int) noMultiDvr)+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c14 = new PdfPCell(p51);
			  c14.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c14.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c14);
			  int insMultidvrStb = 0;
			  if(tier==1){insMultidvrStb = rateDto.getMultiDvrT1();}
			  if(tier==2){insMultidvrStb = rateDto.getMultiDvrT2();}
			  if(tier==3){insMultidvrStb = rateDto.getMultiDvrT3();}
			  if(insMultidvrStb!=0){
			  Phrase p52 = new Phrase(Integer.toString((int) insMultidvrStb)+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c15 = new PdfPCell(p52);
			  c15.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c15.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c15);
			  }
			  else{
				  t7.addCell(NOT);
			  }
			  Phrase p53 = new Phrase(Integer.toString((int) noMultiDvr*insMultidvrStb)+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c16 = new PdfPCell(p53);
			  c16.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c16.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c16);
			  
			  int noMulti = invDto.getMultiT1()+invDto.getMultiT2()+invDto.getMultiT3();
			  Phrase p511 = new Phrase(Integer.toString((int) noMulti)+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c141 = new PdfPCell(p511);
			  c141.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c141.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c141);
			  int insMulti = 0;
			  if(tier==1){insMulti = rateDto.getStbRateT1();}
			  if(tier==2){insMulti = rateDto.getStbRateT2();}
			  if(tier==3){insMulti = rateDto.getStbRateT3();}
			  if(insMulti!=0){
			  Phrase p52 = new Phrase(Integer.toString((int) insMulti)+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c15 = new PdfPCell(p52);
			  c15.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c15.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c15);
			  }
			  else{
				  t7.addCell(NOT);
			  }
			  Phrase p531 = new Phrase(Integer.toString((int) noMulti*insMulti)+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c161 = new PdfPCell(p531);
			  c161.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c161.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c161);
			  
			  
			  Phrase p54 = new Phrase(Integer.toString((int) invDto.getHdplusExt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c17 = new PdfPCell(p54);
			  c17.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c17.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c17);
			  Phrase p55 = new Phrase(Integer.toString((Integer) rateDto.getUpgradeHd())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c18 = new PdfPCell(p55);
			  c18.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c18.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c18);
			  Phrase p56 = new Phrase(Integer.toString((int) invDto.getHdplusExt()*rateDto.getUpgradeHd())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c19 = new PdfPCell(p56);
			  c19.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c19.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c19);
			  
			  Phrase p57 = new Phrase(Integer.toString((int) invDto.getDvr())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c20 = new PdfPCell(p57);
			  c20.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c20.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c20);
			  Phrase p58 = new Phrase(Integer.toString((Integer) rateDto.getUpgradeDvr())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c21 = new PdfPCell(p58);
			  c21.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c21.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c21);
			  Phrase p59 = new Phrase(Integer.toString((int) invDto.getDvr()*rateDto.getUpgradeDvr())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c22 = new PdfPCell(p59);
			  c22.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c22.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c22);
			  
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p591 = new Phrase(Integer.toString((int) invDto.getFieldreprelAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c221 = new PdfPCell(p591);
			  c221.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c221.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c221);
			  
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p592 = new Phrase(Integer.toString((int) invDto.getWeakGeo())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c222 = new PdfPCell(p592);
			  c222.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c222.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c222);
			  
			  Phrase p593 = new Phrase(Integer.toString((int) invDto.getOneyrCount())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c223 = new PdfPCell(p593);
			  c223.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c223.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c223);
			  
			  Phrase p599 = new Phrase(Integer.toString((int)rateDto.getHdeuCess())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c323 = new PdfPCell(p599);
			  c323.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c323.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c323);
			  
			  //t7.addCell(NOT);
			  Phrase p594 = new Phrase(Integer.toString((int) invDto.getOneyrAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c224 = new PdfPCell(p594);
			  c224.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c224.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c224);	  
			  
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p595 = new Phrase(Integer.toString((int) invDto.getHillyArea())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c225 = new PdfPCell(p595);
			  c225.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c225.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c225);	  
			  
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p63 = new Phrase(Integer.toString((int) invDto.getArpPayout())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c26 = new PdfPCell(p63);
			  c26.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c26.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c26);
			 
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p631 = new Phrase(Integer.toString((int) invDto.getOthers())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c261 = new PdfPCell(p631);
			  c261.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c261.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c261);
			  
			  t7.addCell(NOT);
			  t7.addCell(NOT);
			  Phrase p632 = new Phrase(Integer.toString((int) invDto.getOthers1())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c262 = new PdfPCell(p632);
			  c262.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c262.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t7.addCell(c262);
			  
			  t5.addCell(t7);
			  
			  PdfPTable t20 = new PdfPTable(1);
			  PdfPTable t21 = new PdfPTable(1);
			  PdfPTable t22 = new PdfPTable(1);
			  PdfPTable t23 = new PdfPTable(3);
			  
			  Phrase p392 = new Phrase("Finance Remarks",FontFactory.getFont(FontFactory.TIMES, 8));
			  t20.addCell(p392);
			  
			  Phrase p40 = new Phrase(invDto.getFinRemarks(),FontFactory.getFont(FontFactory.TIMES, 9));
			  PdfPCell c269 = new PdfPCell(p40);
			  c269.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c269.setVerticalAlignment(Element.ALIGN_CENTER);
			  t21.addCell(c269);
			  
			  
			  Phrase p401 = new Phrase("Total",FontFactory.getFont(FontFactory.TIMES, 9));
			  t22.addCell(p401);
			  
			  t23.addCell(NOT);
			  t23.addCell(NOT);
			  Phrase p64 = new Phrase(Integer.toString((int) invDto.getTotbasAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c27 = new PdfPCell(p64);
			  c27.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c27.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t23.addCell(c27);
			  
			  t5.addCell(t20);
			  t5.addCell(t21);
			  t5.addCell(t22);
			  t5.addCell(t23);
			  
			  
			  
			  PdfPTable t8 = new PdfPTable(2);
			  PdfPTable t9 = new PdfPTable(1);
			  PdfPTable t10 = new PdfPTable(3);
			  
			  Phrase p65 = new Phrase("Service Tax",FontFactory.getFont(FontFactory.TIMES, 8));
			  t9.addCell(p65);
			  Phrase p66 = new Phrase("Swachcha Bharat Cess",FontFactory.getFont(FontFactory.TIMES, 8));
			  t9.addCell(p66);
			  
			  Phrase p79 = new Phrase("Krishi Kalyan Cess",FontFactory.getFont(FontFactory.TIMES, 8));
			  t9.addCell(p79);
			  
			  
			  Phrase p68 = new Phrase("GRAND TOTAL",FontFactory.getFont(FontFactory.TIMES, 8));
			  t9.addCell(p68);
			  
			  t8.addCell(t9);
			  
			  t10.addCell(NOT);
			  Phrase p69 = new Phrase(Double.toString((double) rateDto.getServiceTx())+"% ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c28 = new PdfPCell(p69);
			  c28.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c28.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c28);
			  Phrase p70 = new Phrase(Integer.toString((int) invDto.getServTx())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c29 = new PdfPCell(p70);
			  c29.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c29.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c29);
			  
			  t10.addCell(NOT);
			  Phrase p71 = new Phrase(Double.toString((double) rateDto.getEduCess())+"% ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c30 = new PdfPCell(p71);
			  c30.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c30.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c30);
			  Phrase p72 = new Phrase(Integer.toString((int) invDto.getEduCes())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c31 = new PdfPCell(p72);
			  c31.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c31.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c31);
			  	  
			  
			  //t10.addCell(NOT);
              //kkc
			  t10.addCell(NOT);
			  Phrase p84 = new Phrase(Double.toString((double) rateDto.getKkcess())+"% ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c37 = new PdfPCell(p84);
			  c37.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c37.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c37);
			  logger.info("KKCess----->>>>>>"+rateDto.getKkcess());
			  Phrase p87 = new Phrase(Integer.toString((int) invDto.getKkc())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c38 = new PdfPCell(p87);
			  c38.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c38.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c38);
			  logger.info("KKCess value------>>>>>"+invDto.getKkc());
			  
			 	  
			  
			  
			  //kkc	
			  
			  t10.addCell(NOT);
			  t10.addCell(NOT);
			  Phrase p75 = new Phrase(Integer.toString((int) invDto.getTotinvAmt())+" ",FontFactory.getFont(FontFactory.TIMES, 8));
			  PdfPCell c34 = new PdfPCell(p75);
			  c34.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      c34.setVerticalAlignment(Element.ALIGN_RIGHT);
			  t10.addCell(c34);
			  
			  t8.addCell(t10);
			  
			  t.setSpacingBefore(25);
			  t.setSpacingAfter(25);
			  
			  Phrase p76 = new Phrase(partnerDet.getAccountName(),FontFactory.getFont(FontFactory.TIMES_BOLD, 8, new CMYKColor(255, 255, 0,0)));
			  PdfPCell c35 = new PdfPCell(p76);
			  c35.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c35.setVerticalAlignment(Element.ALIGN_MIDDLE);
		      t.addCell(c35);
			  Phrase p77 = new Phrase("INVOICE",FontFactory.getFont(FontFactory.TIMES_BOLD, 8));
			  PdfPCell c36 = new PdfPCell(p77);
			  c36.setHorizontalAlignment(Element.ALIGN_CENTER);
		      c36.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  t.addCell(c36);
			  t.addCell(t1);
			  t.addCell(NOT);
			  t.addCell(t2);
			  t.addCell(NOT);
			  t.addCell(t5);
			  t.addCell(NOT);
			  t.addCell(t8);
			  Phrase p78 = new Phrase("Amount In words(Rs.) : "+invDto.getAmountInWords(),FontFactory.getFont(FontFactory.TIMES, 8));
			  t.addCell(p78);
			  
			  Chapter chapter1 = new Chapter(1);
			  chapter1.add(t);
			  document.add(chapter1);
			  document.close();
			  
			  boolean flag = dpinvServ.sftpInvoiceUpload(filePath, loginName);
			  request.setAttribute("fileName", fileName);
			  request.setAttribute("address", address);
			  
		statusMsg=dpinvServ.acceptInvoiceSTB(billNo, invoiceNo,remarks);
		}catch(Exception exp){
			exp.printStackTrace();
			logger.info("Exception in updating invoice data");
			ActionMessages messages=new ActionMessages();
			messages.add("message",new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);
			
			return mapping.findForward("viewInvoiceSuccess");
		}
		inform.reset(mapping, request);
		inform.setBilNo(billNo);
		inform.setInvoiceMessage(statusMsg);
		return mapping.findForward(statusMsg);
		
	}
	
	public ActionForward rejectInvoiceSTB (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){ 
		
		String statusMsg="";
		DpInvoiceUploadService dpinvServ=new DpInvoiceUploadServiceImpl();
		InvoiceForm inform=(InvoiceForm) form;
		int billNo=inform.getBilNo();
		String invoiceNo=inform.getInvoiceNo();
		String remarks=inform.getPartnerRem();
		try{
		statusMsg=dpinvServ.rejectInvoiceSTB(billNo, invoiceNo,remarks);
		}catch(Exception exp){
			exp.printStackTrace();
			logger.info("Exception in updating invoice data");
			ActionMessages messages=new ActionMessages();
			messages.add("message",new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);
			
			return mapping.findForward("viewInvoiceSuccess");
		}
		inform.reset(mapping, request);
		inform.setBilNo(billNo);
		inform.setInvoiceMessage(statusMsg);
		return mapping.findForward(statusMsg);
		
	}
	

	public ActionForward listInvoicesSTBFin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		InvoiceListForm listinvForm = (InvoiceListForm)form;
		List<InvoiceDetails> invList = null;

		DpInvoiceUploadService invServc = new DpInvoiceUploadServiceImpl();
		UserSessionContext userSession = (UserSessionContext) request
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		try {
			invList = invServc.listInvoicesAcc("R");
			logger.info("LOGIN->" + userSession.getLoginName());
			logger.info("List size is "+ invList.size());
			logger.info("Partner Name -"+invList.get(0).getPartnerNm());
		} catch (Exception e) {
			logger.info("Error in loading invoices");
			logger.info("LOGIN->" + userSession.getLoginName());
			ActionMessages messages = new ActionMessages();
			messages.add("message", new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);

			invList = new ArrayList<InvoiceDetails>();

			listinvForm.setInvoiceList(invList);
			//request.setAttribute("InvoiceListForm", listinvForm);
			return mapping.findForward("errorListingInv");

		}

		listinvForm.setInvoiceList(invList);
		logger.info("Form me kya hai--->"+listinvForm.getInvoiceList().get(0).getBillNo());
	//	request.setAttribute("InvoiceListForm", listinvForm);
		return mapping.findForward("listInvoiceSuccess");

	}
	
	public ActionForward listInvoicesFin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		InvoiceListForm listinvForm = (InvoiceListForm) form;
		List<InvoiceDetails> invList = null;

		DpInvoiceUploadService invServc = new DpInvoiceUploadServiceImpl();
		UserSessionContext userSession = (UserSessionContext) request
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		try {
			invList = invServc.listInvoicesAcc("A");
			logger.info("LOGIN->" + userSession.getLoginName());
		} catch (Exception e) {
			logger.info("Error in loading invoices");
			logger.info("LOGIN->" + userSession.getLoginName());
			ActionMessages messages = new ActionMessages();
			messages.add("message", new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);

			invList = new ArrayList<InvoiceDetails>();

			listinvForm.setInvoiceList(invList);
			// request.setAttribute("InvoiceListForm", listinvForm);
			return mapping.findForward("errorListingInv");

		}

		listinvForm.setInvoiceList(invList);
		// request.setAttribute("InvoiceListForm", listinvForm);
		return mapping.findForward("listInvoiceSuccess");

}
	public ActionForward listInvoicesDist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		InvoiceListForm listinvForm = (InvoiceListForm) form;
		List<InvoiceDetails> invList = null;
		String stat=request.getParameter("statFlag");
		Set<String> billSet=new HashSet<String>();
		DpInvoiceUploadService invServc = new DpInvoiceUploadServiceImpl();
		UserSessionContext userSession = (UserSessionContext) request
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		try {
			invList = invServc.listInvoicesDist(userSession.getLoginName(),stat);
			logger.info("LOGIN->" + userSession.getLoginName());
		} catch (Exception e) {
			logger.info("Error in loading invoices");
			logger.info("LOGIN->" + userSession.getLoginName());
			ActionMessages messages = new ActionMessages();
			messages.add("message", new ActionMessage("Invoice.list.error"));
			saveMessages(request, messages);

			invList = new ArrayList<InvoiceDetails>();
			
			
			

			listinvForm.setInvoiceList(invList);
			// request.setAttribute("InvoiceListForm", listinvForm);
			return mapping.findForward("errorListingInv");

		}

		listinvForm.setInvoiceList(invList);
		// request.setAttribute("InvoiceListForm", listinvForm);
		return mapping.findForward("listInvoiceSuccess");

	}
	
	public ActionForward listAllInvoicesDist(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		
		InvoiceListForm listinvForm = (InvoiceListForm) form;
		UserSessionContext sessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		int groupId=sessionContext.getGroupId();
		if(groupId==15){
			
		}
		else if(groupId==7){
			 String loginNm=sessionContext.getLoginName();
		}
		else{
			
		}
		List<InvoiceDetails> invList=new ArrayList<InvoiceDetails>();
		List<String> signedList=new ArrayList<String>();
		DpInvoiceUploadService invServc = new DpInvoiceUploadServiceImpl();
		invList=invServc.listAllInvoicesDist(sessionContext.getLoginName());
		signedList=invServc.listSignedInvoices(sessionContext.getLoginName());
		
		listinvForm.setInvoiceList(invList);
		listinvForm.setSignedInvoice(signedList);
	
		
		
		return mapping.findForward("listAllSuccess");
		
	}
	public void downloadInvoice(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		/*UserSessionContext sessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		String loginName = sessionContext.getLoginName();
		HttpSession session = request.getSession();
		DpInvoiceBeanCollection invCol = (DpInvoiceBeanCollection) session.getAttribute("DpInvoiceCollection");
		DpInvoiceDto invDto = invCol.getInvDto();
		String invoiceNo = invDto.getInvNo();
	//	String dtTemp = invDto.getBilDt().toString();
	//	String dt = invDto.getBilDt().toString().substring(5, 7);
	//	dt = dt+"_"+invDto.getBilDt().toString().substring(0, 4);
		String dt = "2015_12";
		String fileName = loginName+"_"+dt+"_"+invoiceNo+".pdf";*/
		String fileName=(String) request.getParameter("fileName");
		
		String filePath = ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.CREATE_PDF)+fileName;
		
				File f = new File(filePath);
				
				response.setContentLength((int) f.length());
				String name = fileName.substring(0, fileName.length());

				response.setHeader("Pragma", "public");
				response.setHeader("Content-Disposition","attachment; filename=" + name);
				response.setHeader("Cache-Control", "max-age=0");
				byte[] buf = new byte[8192];
				FileInputStream inStream = new FileInputStream(f);
				OutputStream outStream = response.getOutputStream();
				int sizeRead = 0;
				while ((sizeRead = inStream.read(buf, 0, buf.length)) > 0) {
					outStream.write(buf, 0, sizeRead);
				}
				outStream.close();
				inStream.close();
	}
	
}
