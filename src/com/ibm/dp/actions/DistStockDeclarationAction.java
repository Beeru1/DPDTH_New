package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.regex.Pattern;

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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DistStockAcceptTransferBean;
import com.ibm.dp.beans.DistStockDeclarationDetailsBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DistStockDeclarationDao;
import com.ibm.dp.dao.impl.DistStockDeclarationDaoImpl;
import com.ibm.dp.dto.DPDeliveryChallanAcceptDTO;
import com.ibm.dp.dto.DistStockAccpDisplayDTO;
import com.ibm.dp.dto.DistStockDecOptionsDTO;
import com.ibm.dp.dto.DiststockAccpTransferDTO;
import com.ibm.dp.service.DPDeliveryChallanAcceptService;
import com.ibm.dp.service.DistStockDeclService;
import com.ibm.dp.service.impl.DPDeliveryChallanAcceptServiceImpl;
import com.ibm.dp.service.impl.DistStockDeclServiceImpl;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.dp.beans.DistStockDeclarationBean;
/**
 * DPStockDeclarationAction class is add details about closing stock on particular product
 *
 * @author Arvind Gupta
 */
public class DistStockDeclarationAction extends DispatchAction {
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DistStockDeclarationAction.class.getName());
	
	private static final String REGEX_COMMA = ",";
	
	private static final String INIT_SUCCESS = "initSuccess";
	
	private static final String INIT_VIEW_SUCCESS = "initViewSerialAndProduct";
	
	private static final String INIT_SUCCESS_ACCEPT = "initSuccessAccept";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc initial information which fetch the category from the table and display in category dropdown [infront end screen].
	 */
	public ActionForward initStockDec(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		    DistStockDeclarationBean stockDeclarationBean = (DistStockDeclarationBean) form;
		    logger.info("-----------------  initStockDec ACTION CALLED  -----------------");
			ActionErrors errors = new ActionErrors();
			try
			{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			String circle_id = sessionContext.getCircleId() + "";
			
			
			ArrayList<DistStockDecOptionsDTO> stockDecOptionsDTOList = new ArrayList<DistStockDecOptionsDTO>();
			
			DistStockDeclarationDetailsBean stockDeclarationDetailsBean = new DistStockDeclarationDetailsBean();
			
			stockDeclarationDetailsBean.setCircleId(Integer.parseInt(circle_id));
			
			DistStockDeclService stockService=new DistStockDeclServiceImpl();
			stockDecOptionsDTOList=stockService.fetchProdIdNameList(stockDeclarationDetailsBean);
			
			/*
			StockDecOptionsDTO stockDecOptionsDTO1 = new StockDecOptionsDTO();
			stockDecOptionsDTO1.setOptionValue(1001);
			stockDecOptionsDTO1.setOptionText("Sim1");
			stockDecOptionsDTOList.add(stockDecOptionsDTO1);
			
			StockDecOptionsDTO stockDecOptionsDTO2 = new StockDecOptionsDTO();
			stockDecOptionsDTO2.setOptionValue(1002);
			stockDecOptionsDTO2.setOptionText("Sim2");
			stockDecOptionsDTOList.add(stockDecOptionsDTO2);
			*/
			stockDeclarationBean.setProductList(stockDecOptionsDTOList);
			}
			catch(Exception e)
			{
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS);
			}
		
			return mapping.findForward(INIT_SUCCESS);
			
	}
	
	public ActionForward performStockDeclaration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DistStockDeclarationBean stockDeclarationBean = (DistStockDeclarationBean) form;
		logger.info("-----------------  performStockDeclaration ACTION CALLED  -----------------");
		ActionErrors errors = new ActionErrors();
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);

			String distId = sessionContext.getId() + "";
			
			
			
			Pattern commaPattern = Pattern.compile(REGEX_COMMA);
			
			String[] arrProdId = commaPattern.split(stockDeclarationBean.getArrProductId()[0]);
			ArrayList<Integer> productIdList = new ArrayList<Integer>();
			for (String s : arrProdId) {
				productIdList.add(Integer.valueOf(s));
			}

			String[] arrClosingStock = commaPattern.split(stockDeclarationBean.getArrClosingStock()[0]);
			ArrayList<Integer> closingStockList = new ArrayList<Integer>();
			for (String s : arrClosingStock) {
				closingStockList.add(Integer.valueOf(s));
			}
			
			String[] arrComments = commaPattern.split(stockDeclarationBean.getArrComments()[0]);
			ArrayList<String> commentsList = new ArrayList<String>();
			for (String s : arrComments) {
				commentsList.add(s);
			}

			ListIterator<Integer> productIdListItr = productIdList.listIterator();
			ListIterator<Integer> closingStockListItr = closingStockList.listIterator();
			ListIterator<String> commentsListItr = commentsList.listIterator();
			
			ArrayList<DistStockDeclarationDetailsBean> stockDeclarationDetailsBeanList = new ArrayList<DistStockDeclarationDetailsBean>();
			
			while(productIdListItr.hasNext()&&closingStockListItr.hasNext()&&commentsListItr.hasNext()) {
				DistStockDeclarationDetailsBean stockDeclarationDetailsBean = new DistStockDeclarationDetailsBean();
				stockDeclarationDetailsBean.setProductId(productIdListItr.next());
				stockDeclarationDetailsBean.setClosingStock(closingStockListItr.next());
				stockDeclarationDetailsBean.setComments(commentsListItr.next());
				stockDeclarationDetailsBean.setDistId(Integer.parseInt(distId));
				stockDeclarationDetailsBeanList.add(stockDeclarationDetailsBean);
			}
			
			ListIterator<DistStockDeclarationDetailsBean> stockDeclarationDetailsBeanListItr = stockDeclarationDetailsBeanList.listIterator();
			
			
			DistStockDeclService stockService=new DistStockDeclServiceImpl();
			stockService.insertStockDecl(stockDeclarationDetailsBeanListItr);
			
			/*
			while(stockDeclarationDetailsBeanListItr.hasNext()) {
				StockDeclarationDetailsBean stockDeclarationDetailsBean = stockDeclarationDetailsBeanListItr.next();
				System.out.println("-------------------");
				System.out.println(stockDeclarationDetailsBean.getProductId());
				System.out.println(stockDeclarationDetailsBean.getClosingStock());
				System.out.println(stockDeclarationDetailsBean.getComments());
				System.out.println(stockDeclarationDetailsBean.getDistId());
			}
			*/
			
			 stockDeclarationBean.setMessage("Stock Declaration is successfully Submitted!");
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
	
			return mapping.findForward(INIT_SUCCESS);
			
	}

	public ActionForward initStockAcceptTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DistStockAcceptTransferBean stockAcceptTransBean = (DistStockAcceptTransferBean) form;
		logger.info("-----------------  initStockAcceptTransfer ACTION CALLED  -----------------");
		ActionErrors errors = new ActionErrors();
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			String account_id = sessionContext.getId() + "";
			
			
			ArrayList<DiststockAccpTransferDTO> stockAccpTransOptionsDTOList = new ArrayList<DiststockAccpTransferDTO>();

			stockAcceptTransBean.setAccount_id(Integer.parseInt(account_id));
			
			
			DistStockDeclService stockAccpTransService=new DistStockDeclServiceImpl();
			stockAccpTransOptionsDTOList=stockAccpTransService.stockAccpDisplayList(stockAcceptTransBean);
			
			stockAcceptTransBean.setStockTransferAccepList(stockAccpTransOptionsDTOList);
	}
			catch(Exception e)
			{
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS);
			}
		
			return mapping.findForward(INIT_SUCCESS);
			
	}
	
	public ActionForward viewSerialsAndProductList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String DCNo_value="";
		DistStockAcceptTransferBean stockAcceptTransBean = (DistStockAcceptTransferBean) form;
		logger.info("-----------------  viewSerialsAndProductList ACTION CALLED  -----------------");
		ActionErrors errors = new ActionErrors();
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			
			
			ArrayList<DiststockAccpTransferDTO> stockAccpTransOptionsDTOList = new ArrayList<DiststockAccpTransferDTO>();

		     DCNo_value = request.getParameter("DCNo");
		 
			DistStockDeclService stockAccpTransService=new DistStockDeclServiceImpl();
			stockAccpTransOptionsDTOList=stockAccpTransService.viewAllSerialNoOfStock(DCNo_value);
			
			stockAcceptTransBean.setStockTransferAccepList(stockAccpTransOptionsDTOList);
		   
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_VIEW_SUCCESS);
		}
			return mapping.findForward(INIT_VIEW_SUCCESS);
			
	}
	
	
	public ActionForward acceptStockTransferSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String account_id="";
		DistStockAcceptTransferBean stockAcceptTransBean = (DistStockAcceptTransferBean) form;
		logger.info("-----------------  acceptStockTransferSubmit ACTION CALLED  -----------------");
		ActionErrors errors = new ActionErrors();
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			 account_id = sessionContext.getId() + "";
			

			String[] arrCheckedSTA = request.getParameterValues("strCheckedSTA");
			
			DistStockDeclService stockAccpTransService=new DistStockDeclServiceImpl();
			stockAccpTransService.updateAcceptStockTransfer(arrCheckedSTA ,account_id);
			
			}
			catch(Exception e)
			{
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS_ACCEPT);
			}
			return mapping.findForward(INIT_SUCCESS_ACCEPT);
			
	}
	
	
	
	
	
	public ActionForward getInitDCNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;

			String selected = "";
			selected = request.getParameter("selected");
			int selectedValue = Integer.parseInt(selected.trim());
			ActionErrors errors = new ActionErrors();
		//	DPPurchaseOrderService ddpo = new DPPurchaseOrderServiceImpl();
			DistStockDeclService stockAccpTransService=new DistStockDeclServiceImpl();
			DistStockDecOptionsDTO dppoDTO = new DistStockDecOptionsDTO();
			ArrayList<DistStockDecOptionsDTO> sdoList = new ArrayList<DistStockDecOptionsDTO>(); // for product name

			try {
				HttpSession session = request.getSession();
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			//	String account_id = sessionContext.getAccountCode() + "";
				
				sdoList = stockAccpTransService.getDCNoList(42,selectedValue);
				for (int counter = 0; counter < sdoList.size(); counter++) {
					optionElement = root.addElement("option");
					dppoDTO = (DistStockDecOptionsDTO) sdoList.get(counter);
					if (dppoDTO != null) {
				//		optionElement.addAttribute("value", dppoDTO.getOptionValueAccp());//change to product_id
				//		optionElement.addAttribute("text", dppoDTO.getOptionText());
					} else {
						optionElement.addAttribute("value", "None");
						optionElement.addAttribute("text", "None");
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				OutputFormat outputFormat = OutputFormat.createCompactFormat();
				XMLWriter writer = new XMLWriter(out);
	
				writer.write(document);
				writer.flush();
				out.flush();
			
			}catch (Exception e) {
				e.printStackTrace();
				return null;
				
			}
		return null;
	}
	
	public ActionForward stockAcceptDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			HttpSession session = request.getSession();
			ActionErrors errors = new ActionErrors();
			try {
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				//session.getAttribute("USER_INFO");
				String account_id = sessionContext.getId() + "";
				System.out.println("Acount Code"+account_id);
				
				DistStockAcceptTransferBean stockAcceptTransBean = (DistStockAcceptTransferBean) form;
				HashMap<String ,ArrayList<DistStockAccpDisplayDTO>> stockAccpAccpDispDTOMap = new HashMap<String ,ArrayList<DistStockAccpDisplayDTO>>();
				ArrayList<DistStockAccpDisplayDTO> stockAccpDispTransDTOList = new ArrayList<DistStockAccpDisplayDTO>();
				ArrayList<DistStockAccpDisplayDTO> stockAccpDispProdDTOList = new ArrayList<DistStockAccpDisplayDTO>();
				//stockAcceptTransBean.setAccount_id(Integer.parseInt(account_id));
				
//	stockAcceptTransBean.setAccount_id(42);
				
//	DistStockDeclService stockAccpTransService=new DistStockDeclServiceImpl();
//	stockAccpAccpDispDTOMap=stockAccpTransService.stockAcceptInfo(stockAcceptTransBean);
				

//	stockAccpDispTransDTOList=stockAccpAccpDispDTOMap.get("Trans");
//	stockAccpDispProdDTOList=stockAccpAccpDispDTOMap.get("Prod");
				
//ListIterator<DistStockAccpDisplayDTO> stockAccpDispTransDTOListItr = stockAccpDispTransDTOList.listIterator();
				
/*	
				while(stockAccpDispTransDTOListItr.hasNext()) {
				
					System.out.println("-------------------");
					stockAcceptTransBean.setDCDate("10/Oct/2010");
					stockAcceptTransBean.setQuantity("20");
					stockAcceptTransBean.setTransTo("BSNL");
					
				}
*/	
				DistStockAccpDisplayDTO stockDecOptionsDTO1 = new DistStockAccpDisplayDTO();
				stockDecOptionsDTO1.setProdName("Sim1");
				stockDecOptionsDTO1.setSerialNo("S001");
				stockAccpDispProdDTOList.add(stockDecOptionsDTO1);
				
				DistStockAccpDisplayDTO stockDecOptionsDTO2 = new DistStockAccpDisplayDTO();
				stockDecOptionsDTO1.setProdName("Card");
				stockDecOptionsDTO1.setSerialNo("C001");
				stockAccpDispProdDTOList.add(stockDecOptionsDTO2);
				
				
//	stockAcceptTransBean.setProdNameList(stockAccpDispProdDTOList);
				
//		// testing purpose
//	System.out.println("from   "+stockAcceptTransBean.getTransFrom());
//	System.out.println("DC No  "+stockAcceptTransBean.getTransDCNo());
				
//	stockAcceptTransBean.setDCDate("10/Oct/2010");
//	stockAcceptTransBean.setQuantity("20");
//	stockAcceptTransBean.setTransTo("BSNL");
				
				
				// cose testing
				
				saveToken(request);
			} catch (RuntimeException e) 
			{
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS);
			}
			return mapping.findForward(INIT_SUCCESS);
			
	}// end initPO
	
	
	
	
	}//END
