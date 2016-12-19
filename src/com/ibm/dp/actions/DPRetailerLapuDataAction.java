/**
 * 
 */
package com.ibm.dp.actions;

import java.io.InputStream;
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
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.beans.DPProductSecurityListBean;
import com.ibm.dp.beans.DPRetailerLapuDataBean;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.dto.RetailerLapuDataDto;
import com.ibm.dp.service.DPProductSecurityListService;
import com.ibm.dp.service.RetailerLapuDataService;
import com.ibm.dp.service.impl.DPProductSecurityListServiceImpl;
import com.ibm.dp.service.impl.RetailerLapuDataServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;


/**
 * @author Nehil Parashar
 *
 */
public class DPRetailerLapuDataAction extends DispatchAction 
{
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	private static final String ERROR_FILE = "errorFile";
	
	private Logger logger = Logger.getLogger(DPRetailerLapuDataAction.class.getName());
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		return mapping.findForward(INIT_SUCCESS);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		DPRetailerLapuDataBean dpRetailerLapuDataBean = (DPRetailerLapuDataBean)form;
		
		RetailerLapuDataService service = new RetailerLapuDataServiceImpl();
		List<RetailerLapuDataDto> lapuData = service.getAllRetailerLapuData();
		
		dpRetailerLapuDataBean.setLapuDataList(lapuData);
		
		return mapping.findForward(SUCCESS_EXCEL);
	}
	
	/**
	 * 
	 * @param fileName
	 * @param dpRetailerLapuDataBean
	 * @return
	 * @throws VirtualizationServiceException
	 */
	private boolean validateUploadedFile(String fileName, DPRetailerLapuDataBean dpRetailerLapuDataBean) throws VirtualizationServiceException
	{
		String ext= fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
	
		boolean isXls=true;
		boolean hasSpecialChar=false;
		
		if(!ext.equalsIgnoreCase("xls"))
		{
			dpRetailerLapuDataBean.setStrmsg("Only XLS File is allowed here");
			isXls=false;
		}
		if(isXls)
		{
			String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
			
			for (int i=0; i<special.length(); i++) 
			{
				if (fileName.indexOf(special.charAt(i)) > 0) 
				{
					dpRetailerLapuDataBean.setStrmsg("Special Characters are not allowed in File Name");
					hasSpecialChar=true;
					break;
				}
			}
			if (fileName.indexOf("..")>-1)
			{
				hasSpecialChar=true;	
				dpRetailerLapuDataBean.setStrmsg("2 consecutive dots are not allowed in File Name");
			}
		}
		
		return isXls && !hasSpecialChar;
	}

	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------uploadExcel Called-----------------");
		ActionErrors errors = new ActionErrors();
		DPRetailerLapuDataBean dpRetailerLapuDataBean = null;
		try 
		{	
			dpRetailerLapuDataBean = (DPRetailerLapuDataBean) form;
			
			HttpSession session = request.getSession();
			
			FormFile file = dpRetailerLapuDataBean.getUploadedFile();
			InputStream myxls = file.getInputStream();
			logger.info("**  "+file.getFileName());
			
			boolean isValidFile = validateUploadedFile(file.getFileName(), dpRetailerLapuDataBean);
			
			if(isValidFile)
			{
				RetailerLapuDataService bulkupload = new RetailerLapuDataServiceImpl();
				List<RetailerLapuDataDto> list = bulkupload.validateExcel(myxls);
				
				session.removeAttribute("error_file");
				session.setAttribute("error_file",list);
				
				RetailerLapuDataDto retailerLapuDataDto = null;
				boolean checkValidate = true;
				if(list.size() > 0)
				{
					logger.info("Size of list in action::"+list.size());
					
					Iterator<RetailerLapuDataDto> itr = list.iterator();
					while(itr.hasNext())
					{
						retailerLapuDataDto = itr.next();
						if(retailerLapuDataDto.getErr_msg()!= null && !retailerLapuDataDto.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						}
					}
					if(checkValidate)
					{
						dpRetailerLapuDataBean.setError_flag("false");
						System.out.println("***File is correct. Now goes for DB transaction****");
						System.out.println(list.size() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						/*for(RetailerLapuDataDto dto  : list)
						{
							System.out.println(dto.getLoginId());
							System.out.println(dto.getAccountName());
							System.out.println(dto.getMobile1());
							System.out.println(dto.getMobile2());
							System.out.println(dto.getMobile3());
							System.out.println(dto.getMobile4());
							System.out.println(dto.getLapuNumber());
						}*/
						String strMessage = bulkupload.updateLapuNumbers(list);
						dpRetailerLapuDataBean.setStrmsg(strMessage);
						return mapping.findForward(INIT_UPLOAD_SUCCESS);
					}
					else
					{
						dpRetailerLapuDataBean.setError_flag("true");
						dpRetailerLapuDataBean.setStrmsg("");
						return mapping.findForward(INIT_UPLOAD_SUCCESS);	
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_UPLOAD_SUCCESS);
		}
		return mapping.findForward(INIT_UPLOAD_SUCCESS);
	}
	
	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		return mapping.findForward(ERROR_FILE);
	}
}
