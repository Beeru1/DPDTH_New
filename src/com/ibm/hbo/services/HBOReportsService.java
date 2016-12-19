/*
 * Created on Nov 12,2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services;

import java.util.ArrayList;

import com.ibm.hbo.dto.HBOReportDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOReportFormBean;

/**
 * @author Parul
 * @version 2.0
 * 
 */
public interface HBOReportsService {

	/**
	 * @param string
	 * @return
	 */
	ArrayList getReportData(HBOUserBean userBean,int redpId,HBOReportDTO reportDTO)throws HBOException;
	
	
	
	
}

 