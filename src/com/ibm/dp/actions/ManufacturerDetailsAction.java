package com.ibm.dp.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.ManufacturerDetailsBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ManufacturerDetailsDto;
import com.ibm.dp.service.ManufacturerService;
import com.ibm.dp.service.impl.ManufacturerServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class ManufacturerDetailsAction extends DispatchAction{

private static Logger logger = Logger.getLogger(ManufacturerDetailsAction.class.getName());
	
	private static final String INIT_SUCCESS = "success";
	private static final String VIEW_MANUFACTURER = "viewManufacturer";
	
	public ActionForward getManufacturerDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		ManufacturerDetailsBean manufactureBean = (ManufacturerDetailsBean)form;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		ManufacturerService  mse = new ManufacturerServiceImpl();
		List<ManufacturerDetailsDto> listData= mse.getManufacturerData();
		for(int i=0; i<listData.size();i++){
			//System.out.println("listData.get(i).getStatus()"+listData.get(i).getManufacturerId());
		if(listData.get(i).getStatus().equals("A")){
			manufactureBean.setManufacturerList(listData);
			request.setAttribute("manufacturerList", listData);
			
		}
		}
		//listData.get(0);
		return mapping.findForward(INIT_SUCCESS);
	}	
	
	
	public ActionForward saveManufacturerDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		HttpSession session = request.getSession();
		ManufacturerDetailsBean mBean = (ManufacturerDetailsBean)form;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String manufacturerIdList= request.getParameter("manufacturerId");
		String flag= request.getParameter("selectionFlag");
		ManufacturerService  ms = new ManufacturerServiceImpl();
		ms.saveManufacturerData(manufacturerIdList,flag);
		List<ManufacturerDetailsDto> listData= ms.getManufacturerData();
		for(int i=0; i<listData.size();i++){
			//System.out.println("listData.get(i).getStatus()"+listData.get(i).getManufacturerId());
		if(listData.get(i).getStatus().equals("A")){
			mBean.setManufacturerList(listData);
			request.setAttribute("manufacturerList", listData);
			
		}
		}
		
		return mapping.findForward(VIEW_MANUFACTURER);
	}	
	
	
	public ActionForward viewManufacturerDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		ManufacturerDetailsBean manufactureBean = (ManufacturerDetailsBean)form;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		ManufacturerService  mse = new ManufacturerServiceImpl();
		List<ManufacturerDetailsDto> listData= mse.getManufacturerData();
		for(int i=0; i<listData.size();i++){
			//System.out.println("listData.get(i).getStatus()"+listData.get(i).getManufacturerId());
		if(listData.get(i).getStatus().equals("A")){
			manufactureBean.setManufacturerList(listData);
			request.setAttribute("manufacturerList", listData);
			
		}
		}
		//listData.get(0);
		return mapping.findForward(VIEW_MANUFACTURER);
	}	
	
	
	
}
