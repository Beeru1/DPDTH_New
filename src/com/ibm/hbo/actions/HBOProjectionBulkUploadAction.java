/*
 * Created on Aug 7, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.actions;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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

import com.ibm.hbo.dto.HBOMonthDTO;
import com.ibm.hbo.dto.HBOProjectionBulkUploadDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOProjectionBulkUploadFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBOProjectionBulkUploadService;
import com.ibm.hbo.services.HBOStockService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.HBOProjectionBulkUploadServiceImpl;
import com.ibm.hbo.services.impl.HBOStockServiceImpl;

/**
 * @author vivek kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOProjectionBulkUploadAction extends DispatchAction {
	private static final Logger logger;
	ArrayList dataList = new ArrayList();

		static {
			logger = Logger.getLogger(HBOMasterAction.class);
		}
	public ActionForward getValues(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{		
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		ArrayList arrMasterList = new ArrayList(); 
		HBOUserBean hboUserBean = (HBOUserBean) session.getAttribute("USER_INFO");
		String userId = hboUserBean.getUserLoginId();

		String roleId=hboUserBean.getActorId();
		HBOProjectionBulkUploadFormBean bulkUploadBean = (HBOProjectionBulkUploadFormBean) form;
		
		if("/initProjectionBulkUpload".equalsIgnoreCase(mapping.getPath()))
		{
			forward = mapping.findForward("ProjectionBulkUpload");
		}
//		if("/ProjectionBulkUpload".equalsIgnoreCase(mapping.getPath()))
//		{
		HBOMasterService masterService = new HBOMasterServiceImpl();
		String condition = "";
		
		Date dt = new Date();
		int mth=dt.getMonth();
		Calendar calendar = new GregorianCalendar() ;
		
		String [] month={"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};
		
		Map list = new HashMap();
		ArrayList arrMonth = new ArrayList();
		HBOMonthDTO monthDTO = null;
		for(int i=0;i<=2;i++)
		{
			if(mth>11)
			{
				mth=0;
			}
			monthDTO = new HBOMonthDTO();
			monthDTO.setMonthName(month[mth]);
			monthDTO.setMonthId(mth+1);
			arrMonth.add(monthDTO);
			mth++;
			logger.info("----------------"+monthDTO.getMonthId());
					
		}
		
		//GET YEAR 
		ArrayList arrYear = new ArrayList();
		
		if(dt.getMonth()==11||dt.getMonth()==10)
		{
			for(int i=0;i<2;i++){
			monthDTO =new HBOMonthDTO();
			//monthDTO.setCurrYear(calendar.get(Calendar.YEAR));
			monthDTO.setNextYear((calendar.get(Calendar.YEAR))+i);
			arrYear.add(monthDTO);
			}
		}
		else{			
			monthDTO =new HBOMonthDTO();
			monthDTO.setNextYear((calendar.get(Calendar.YEAR)));
			arrYear.add(monthDTO);
			
		}
		
		logger.info("months are: "+list);
		
		bulkUploadBean.setCircle("");
		bulkUploadBean.setBusiness_catg("");
		bulkUploadBean.setMonth("");
		bulkUploadBean.setYear("");
	
			ArrayList arrCircleList = null;
//			ArrayList arrBusinessList = null;
			if(bulkUploadBean.getArrCircle() == null ){
				if(roleId.equals("1")){
				arrCircleList = masterService.getMasterList(userId,"circle",condition);
				bulkUploadBean.setArrCircle(arrCircleList);
				}
				else if(roleId .equals("2")){
					String circleid=hboUserBean.getCircleId();
					
				condition=" circle_id=(select warehouse_circle_id from user_master um, warehouse_master wmi where um.WAREHOUSE_ID= wmi.warehouse_id and um.USER_LOGIN_ID = '"+userId +"')";
					
					arrCircleList = masterService.getMasterList(userId,"circle",condition);
					bulkUploadBean.setArrCircle(arrCircleList);
					condition="";
				}
			}
			
			if(bulkUploadBean.getArrMonth() == null ) {
				bulkUploadBean.setArrMonth(arrMonth);
			}
			if(bulkUploadBean.getArrYear() == null ) {
				bulkUploadBean.setArrYear(arrYear);
			}
//		forward = mapping.findForward("success");
//		}
		return (forward);
	}				
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward insertFile(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
			{
				ActionMessages messages = new ActionMessages();
				HttpSession session = request.getSession();
				String userid="";
				ActionErrors errors = new ActionErrors();
				ActionForward forward = new ActionForward();
				HBOProjectionBulkUploadFormBean uploadFormBean = (HBOProjectionBulkUploadFormBean) form;
				HBOProjectionBulkUploadDTO upload = new HBOProjectionBulkUploadDTO(); 
				logger.info("HBOProjectionBulkUploadFormBean"+uploadFormBean.getCircle()+"--"+uploadFormBean.getYear()+"---"+uploadFormBean.getMonth());
				HBOStockService uploadService = new HBOStockServiceImpl();
				HBOUserBean hboUserBean = (HBOUserBean) session.getAttribute("USER_INFO");
				userid = hboUserBean.getUserID();
				BeanUtils.copyProperties(upload,uploadFormBean);
				upload.setUploadedBy(Long.parseLong(userid));
				String message="";
				//message = uploadService.projectQty(userid,upload,"projectionQty",hboUserBean);
				logger.info("Message after Project Qty:"+message);
				if (message.equalsIgnoreCase("success")){
					uploadFormBean.setMessage("success");
					messages.add("message", new ActionMessage("actioninMessage.updateSuccess"));
					uploadFormBean.setUploadedBy(userid);
					uploadFormBean.setCircleId(hboUserBean.getCircleId());
					uploadFormBean.setCircle("");
					uploadFormBean.setMonth("");
					uploadFormBean.setYear("");						
					messages.add("INSERT_SUCCESS",new ActionMessage("insert.success"));
					saveMessages(request, (ActionMessages) messages);
				}
				else if (message.equalsIgnoreCase("failure"))
				{
					errors.add("ERROR_OCCURED",new ActionError("error.occured"));
					saveErrors(request, (ActionErrors) errors);			
				}
				return mapping.findForward(message);
			}
			
	public void upLoadFile(
	HBOProjectionBulkUploadFormBean bulkUploadBean,
			HBOUserBean hboUserBean,
			HttpServletRequest request,
			FormFile myFile) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat stf = new SimpleDateFormat("HHmmss");
				java.util.Date nowDate = new java.util.Date();
				String filePath = null;
				String today = sdf.format(nowDate);
				String time = stf.format(nowDate);
				String filepath =
				hboUserBean.getCircleId()
						+ "-"
						+ today
						+ "-"
						+ time
						+ "-"
						+ myFile.getFileName();
				String filename =
				hboUserBean.getCircleId() + today + time + myFile.getFileName();
			
				filePath =
					(String) getResources(request).getMessage("UPLOAD_CAL_PATH");
				File file = new File(filePath + filename);
				byte[] fileData = myFile.getFileData();
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				raf.write(fileData);
				raf.close();

				bulkUploadBean.setFileName(filename);
				bulkUploadBean.setFilePath(filepath);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}


}
