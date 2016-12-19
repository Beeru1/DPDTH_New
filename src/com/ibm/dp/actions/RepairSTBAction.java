package com.ibm.dp.actions;

import java.util.ArrayList;
import java.util.List;
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
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.RepairSTBBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.RepairSTBDto;
import com.ibm.dp.service.RepairSTBService;
import com.ibm.dp.service.impl.RepairSTBServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class RepairSTBAction extends DispatchAction{
	private static Logger logger = Logger.getLogger(RepairSTBAction.class.getName());
	
	private static final String INIT_SUCCESS = "initSuccess";
	private static final String STATUS_ERROR = "error";
	private static final String REGEX_COMMA = ",";
	

	private void setDefaultValue(RepairSTBBean reverseCollectionBean,
			HttpServletRequest request,String circleId) throws Exception {
		RepairSTBService stbService = new RepairSTBServiceImpl();
			List<ProductMasterDto> productList = stbService.getProductTypeMaster(circleId);

		reverseCollectionBean.setProductList(productList);
		request.setAttribute("productList", productList);
		
	}
	
	public ActionForward viewStockCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** viewStockCollection() method. Called*****************************");
		HttpSession session = request.getSession();
		RepairSTBBean reverseCollectionBean = (RepairSTBBean)form;
		ActionErrors errors = new ActionErrors();
		String strMessage = "";
		String strIsValid = "N"; 
		
		try
		{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			RepairSTBService stbRepairService =  new RepairSTBServiceImpl(); 
			strIsValid = stbRepairService.isValid(distId);
			if(!strIsValid.equals("Y"))
			{
				strMessage = "Distributor has no rights for 'Self Repair' !!";
			}
			
			String circleId = String.valueOf(sessionContext.getCircleId());
			setDefaultValue(reverseCollectionBean, request,circleId);
			List<RepairSTBDto>  dcStockCollectionList = stbRepairService.getStockCollectionList(distId);
						
			reverseCollectionBean.setReverseStockInventoryList(dcStockCollectionList);
			reverseCollectionBean.setIsValidUser(strIsValid);
			request.setAttribute("reverseStockInventoryList", dcStockCollectionList);
			request.setAttribute("isValidUser", strIsValid);
		}
		catch(Exception e)
		{	
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);	
			strMessage = e.getMessage();
			return mapping.findForward(STATUS_ERROR);
		}finally{
			reverseCollectionBean.setMessage(strMessage);
		
		}
		
		return mapping.findForward(INIT_SUCCESS);
	}

	
	public ActionForward performStbRepair(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** performStbRepair() method. Called*****************************");
		
		HttpSession session = request.getSession();
		RepairSTBBean reverseCollectionBean = (RepairSTBBean)form;
		String strMessage ="";
		try{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String circleId = String.valueOf(sessionContext.getCircleId());
			logger.info(" distId =="+distId+" circleId == "+circleId );
			setDefaultValue(reverseCollectionBean, request,circleId);
			
			Pattern commaPattern = Pattern.compile(REGEX_COMMA);
		
			String[] arrNewProdId = commaPattern.split(reverseCollectionBean.getHidStrProductId()[0]);
			String[] arrSrNo = commaPattern.split(reverseCollectionBean.getHidStrSerialNo()[0]);
			String[] arrOldProductId = commaPattern.split(reverseCollectionBean.getHidStrOldProductId()[0]);
			String[] arrNewRemarks= commaPattern.split(reverseCollectionBean.getHidStrNewRemarks()[0]);
			String[] arrOldRemarks= commaPattern.split(reverseCollectionBean.getHidStrOldRemarks()[0]);
			logger.info(" arrProdId  =="+arrNewProdId[0]+" arrProdId length == "+arrSrNo.length + " arrOldRemarks length == " +arrOldRemarks.length);
		
			ArrayList<RepairSTBDto> stbRepairDtoList = new ArrayList<RepairSTBDto>();
			String strSuccessMessage = "";
			for(int count=0;count<arrNewProdId.length;count++){
				RepairSTBDto stbRepairDto = new RepairSTBDto();
				logger.info(" productIdListItr.next() =="+arrNewProdId[count] + "  sr no == "+arrSrNo[count]);
				stbRepairDto.setProdId(arrNewProdId[count]);
				stbRepairDto.setStrSerialNo(arrSrNo[count]);
				stbRepairDto.setStrNewRemarks(arrNewRemarks[count]);
				if(null ==arrOldRemarks[count] || "null".equals(arrOldRemarks[count])){
					stbRepairDto.setStrRemarks("");
				}else{
					stbRepairDto.setStrRemarks(arrOldRemarks[count]);
				}
				stbRepairDto.setStrOldProductId(arrOldProductId[count]);
				stbRepairDtoList.add(stbRepairDto);
			
			}
	
			ListIterator<RepairSTBDto> repairStbDtoListItr = stbRepairDtoList.listIterator();
		
			RepairSTBService stbRepairService =  new RepairSTBServiceImpl(); 
			strSuccessMessage=stbRepairService.insertStockCollectionRepair(repairStbDtoListItr, distId, circleId);
			if(strSuccessMessage.equals("SUCCESS"))
			strMessage = "STB Repair have been done successfully  ";
			
			List<RepairSTBDto>  dcStockCollectionList = stbRepairService.getStockCollectionList(distId);
						
			reverseCollectionBean.setReverseStockInventoryList(dcStockCollectionList);
			request.setAttribute("reverseStockInventoryList", dcStockCollectionList);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			strMessage =ex.getMessage();
			return mapping.findForward(STATUS_ERROR);
		}finally{
			reverseCollectionBean.setMessage(strMessage);
			reverseCollectionBean.setStrRemarks("");
		
			
		}
		return mapping.findForward(INIT_SUCCESS);
	}
}
