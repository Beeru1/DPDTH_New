/*
 * Created on Aug 12, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services;

import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.HBOException;

/**
 * @author Anju
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface HBOProjectionBulkUploadService {
	public String projectQty(String userId,Object formBean,String master,HBOUserBean hboUserBean) throws HBOException;

}
