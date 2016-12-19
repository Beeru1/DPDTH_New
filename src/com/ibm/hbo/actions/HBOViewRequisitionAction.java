/*
 * Created on Nov 21, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.actions;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.hbo.dto.HBOProductDTO;
import com.ibm.hbo.dto.HBORequisitionDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOCreateRequisitionFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBORequisitionService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.HBORequisitionServiceImpl;

/**
 * @author Admin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOViewRequisitionAction extends DispatchAction {

	private static final Logger logger;

	static {

		logger = Logger.getLogger(HBOViewRequisitionAction.class);
	}

	/* Local Variables */
//	private static String INITCREATEUSER_SUCCESS = "initCreateUser";
//
//	private static String CREATEUSER_SUCCESS = "CreateUserSuccess";
//
//	private static String CREATEUSER_FAILURE = "CreateUserFailure";
//
//	private static String USERCREATED_SUCCESS = "UserCreatedSuccess";
//
//	private static String EDIT_USER_SUCCESS = "EditUserSuccess";
	

	public ActionForward viewRequisition
	(ActionMapping mapping,ActionForm form, 
	HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		HBOCreateRequisitionFormBean requisitionFormBean = (HBOCreateRequisitionFormBean) form;
		HBORequisitionDTO reqDTO = new HBORequisitionDTO();
		HBOUserBean userBean = (HBOUserBean)session.getAttribute("USER_INFO");
		
		int warehouseid = Integer.parseInt(userBean.getWarehouseID());
		int roleid = Integer.parseInt(userBean.getActorId());
		HBORequisitionService reqService = new HBORequisitionServiceImpl();
		//ArrayList requisitionList = reqService.getRequisitions(warehouseid,roleid,"");
		

		//logger.info("#######################"+requisitionList.size());
		//requisitionFormBean.setArrRequisitionList(requisitionList);

//		logger.info("#######################"+requisitionList.size());
//		requisitionFormBean.setArrRequisitionList(requisitionList);

		return mapping.findForward("success");
	}
	
	
}