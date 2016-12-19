
package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;

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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.ChurnDCGenerationBean;
import com.ibm.dp.beans.DpDcCreationBean;
import com.ibm.dp.dto.ChurnDCGenerationDTO;
import com.ibm.dp.service.ChurnDCGenerationService;
import com.ibm.dp.service.DPDcCreationService;
import com.ibm.dp.service.impl.ChurnDCGenerationServiceImpl;
import com.ibm.dp.service.impl.DPDcCreationServiceImpl;
import com.ibm.hbo.common.HBOUser;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * Controller class for reverse collection  management 
 * @author Shah Rabbani
 */
public class ChurnDCGenerationAction extends DispatchAction 
{
	/* Logger for this Action class. */
	private static Logger logger = Logger.getLogger(ChurnDCGenerationAction.class.getName());

	public static final String INIT_SUCCESS = "initsuccess";
	
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)	throws Exception 
	{
		//logger.info("***************************** initReverseCollection() method.*****************************");
		//ChurnDCGenerationBean reverseCollectionBean = (ChurnDCGenerationBean)form;
							
		ActionErrors errors = new ActionErrors();
		try 
		{	
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		
		    ChurnDCGenerationBean churnBean = (ChurnDCGenerationBean)form;   
		    ChurnDCGenerationService churnDCService = new ChurnDCGenerationServiceImpl();
		    
		    HBOUser hboUser = new HBOUser(userSessionContext);
		    
		    ArrayList<ChurnDCGenerationDTO> porList = new ArrayList<ChurnDCGenerationDTO>();
		   // ArrayList<ChurnDCGenerationDTO> porListN = new ArrayList<ChurnDCGenerationDTO>();

			ArrayList<ArrayList<ChurnDCGenerationDTO>> arrPendingSTB = 
				(ArrayList<ArrayList<ChurnDCGenerationDTO>>) churnDCService.viewChurnPendingForDCList(hboUser.getId());
			
			//Security Observation Implementation by pratap on 22 july 2013
			logger.info("Saving token in initi method ::::::");
			saveToken(request);
			
			if(arrPendingSTB!=null && !arrPendingSTB.isEmpty())
			{
				porList = arrPendingSTB.get(0);
				//porListN = arrPendingSTB.get(1);
			}
			//logger.info(" Number of Refurbishment STB  ::  "+porList.size());
			//logger.info(" Number of SWAP STB  ::  "+porListN.size());
			
			churnBean.setPoList(porList); 	//List for STB for Refurbisment
			//churnBean.setPoListN(porListN); //List for SWAP STB
			
			request.setAttribute("poList", porList); //request.setAttribute("PAGES", noofpages);
			//request.setAttribute("porListN", porListN);
		}		
		catch(Exception e)
		{
			e.printStackTrace();				
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));				
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward performDcCreation(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		//logger.info("***************************** performDcCreation() method. Called*****************************");
		
		HttpSession session = request.getSession();
		ChurnDCGenerationBean churnBean = (ChurnDCGenerationBean)form;
		ActionErrors errors = new ActionErrors();
		
		String strMessage ="";
		String strDCNO = "";
		try 
		{	
			 //Security Observation Implementation by prata on 22 july 2013
			logger.info("validating  token in performDcCreation method ::::::");
			if(!isTokenValid(request))
			 {
				logger.info("in if block  token in performDcCreation method ::::::");
				return mapping.findForward("invalidoperation");
			  }
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long loginUserId = sessionContext.getId();
			int circleId = sessionContext.getCircleId();
			
			String[] arrCheckedSTBRefrb = request.getParameterValues("strCheckedChurn");
			//String[] arrCheckedSTBSWAP = request.getParameterValues("strCheckedChurnN");
			
			ArrayList<String> allCheckedSTB = new ArrayList<String>();
			
			if(arrCheckedSTBRefrb!=null && arrCheckedSTBRefrb.length>0)
			{
				for(int i=0; i<arrCheckedSTBRefrb.length; i++)
					allCheckedSTB.add(arrCheckedSTBRefrb[i]);
			}
			
			/*if(arrCheckedSTBSWAP!=null && arrCheckedSTBSWAP.length>0)
			{
				for(int i=0; i<arrCheckedSTBSWAP.length; i++)
					allCheckedSTB.add(arrCheckedSTBSWAP[i]);
			}*/
			
			//logger.info ("number of checked STBs  :::  "+allCheckedSTB.size());
			
			ChurnDCGenerationService churnDCService = new ChurnDCGenerationServiceImpl();
			
			String strRemarks = churnBean.getRemarks();		
			String courierAgency = churnBean.getCourierAgency();
			String docketNumber = churnBean.getDocketNumber();
			
			ChurnDCGenerationDTO churnDTO = new ChurnDCGenerationDTO();
			
			churnDTO.setAllCheckedSTB(allCheckedSTB);
			churnDTO.setRemarks(strRemarks);
			churnDTO.setCourierAgency(courierAgency);
			churnDTO.setDocketNumber(docketNumber);
			churnDTO.setIntCircleID(circleId);
			churnDTO.setLnUserID(loginUserId);
			
			ArrayList<ArrayList<ChurnDCGenerationDTO>> arrPendingSTB = churnDCService.reportDCCreation(churnDTO);
			
		    ArrayList<ChurnDCGenerationDTO> porList = new ArrayList<ChurnDCGenerationDTO>();
		   // ArrayList<ChurnDCGenerationDTO> porListN = new ArrayList<ChurnDCGenerationDTO>();
		    ArrayList<ChurnDCGenerationDTO> arrMessage = new ArrayList<ChurnDCGenerationDTO>();

			if(arrPendingSTB!=null && !arrPendingSTB.isEmpty())
			{
				porList = arrPendingSTB.get(0);
				arrMessage = arrPendingSTB.get(1);
				//porListN = arrPendingSTB.get(1);
				//arrMessage = arrPendingSTB.get(2);
			}
			//logger.info(" Number of Refurbishment STB  ::  "+porList.size() + " and arrMessage == "+arrMessage.get(0).getMessage());
			//logger.info(" Number of SWAP STB  ::  "+porListN.size());
			
			if(arrMessage.size()>0)
			{
				strMessage = arrMessage.get(0).getMessage();
				strDCNO = arrMessage.get(0).getDcNo();
			}
			else
			{
				strMessage = "Internal error occured. Please try again.";
				strDCNO = "";
			}
			
			churnBean.setPoList(porList); 	//List for STB for Refurbisment
			//churnBean.setPoListN(porListN); //List for SWAP STB
			churnBean.setMessage(strMessage);
			churnBean.setHidstrDcNo(strDCNO);
			churnBean.setDocketNumber("");
			churnBean.setCourierAgency("");
			churnBean.setRemarks("");
			
			request.setAttribute("porList", porList); //request.setAttribute("PAGES", noofpages);
			//request.setAttribute("porListN", porListN);
			request.setAttribute("message", strMessage);
			request.setAttribute("hidstrDcNo", strDCNO);
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

	
	public ActionForward checkERR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ChurnDCGenerationBean reverseCollectionBean = (ChurnDCGenerationBean)form;
		ActionErrors errors = new ActionErrors();
		String strERR ="";
		String strSerialNo = request.getParameter("strSerialNo");
		ChurnDCGenerationService dcCollectionService =  new ChurnDCGenerationServiceImpl(); 
		strERR = dcCollectionService.checkERR(strSerialNo);
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
		 //Security Observation Implementation by prata on 22 july 2013
		logger.info("validating  token in checkERR method ::::::");
		if(!isTokenValid(request))
		 {
			logger.info("in if block  token in checkERR method ::::::");
			return mapping.findForward("invalidoperation");
		  }
			optionElement = root.addElement("option");
			optionElement.addAttribute("strERR", strERR);
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();			
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		return null;
	}


}

