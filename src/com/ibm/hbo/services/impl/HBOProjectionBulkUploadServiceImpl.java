/*
 * Created on Aug 12, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services.impl;

import org.apache.log4j.Logger;

import com.ibm.hbo.dao.HBOBulkUploadDAO;
import com.ibm.hbo.dao.impl.HBOBulkUploadDAOImpl;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOProjectionBulkUploadFormBean;
import com.ibm.hbo.services.HBOProjectionBulkUploadService;

/**
 * @author Anju
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOProjectionBulkUploadServiceImpl implements HBOProjectionBulkUploadService{
	
	 public static Logger logger = Logger.getLogger(HBOProjectionBulkUploadServiceImpl.class);
	/* (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOProjectionBulkUploadService#projectQty(java.lang.String, java.lang.Object, java.lang.String, com.ibm.hbo.dto.HBOUserBean)
	 */
	public String projectQty(String userId, Object formBean, String master, HBOUserBean hboUserBean) throws HBOException {
		String message="success";
		
		HBOBulkUploadDAO stockDAO = new HBOBulkUploadDAOImpl();
		try{
			HBOProjectionBulkUploadFormBean upload = (HBOProjectionBulkUploadFormBean)formBean;
			String circleId = hboUserBean.getCircleId();
			String fileName = upload.getFileName();
			String UploadfilePath = upload.getFilePath();
		//	stockDAO.insertBulkUploadData(circleId,fileName,userId,UploadfilePath,null);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getWarehouseList():" + e.getMessage());
				message="failure"; // changes on 12/5/2008 by sachin
				//throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getWarehouseList():" + e.getMessage());
			}
			message="failure";
		}
		return null;
	}


}
