/*
 * Created on Nov 13, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;


import com.ibm.hbo.dao.HBOReportsDAO;
import com.ibm.hbo.dao.impl.HBOReportsDAOImpl;
import com.ibm.hbo.dto.HBOReportDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.services.HBOReportsService;


/**
 * @author Parul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author Parul
 * @version 2.0
 * 
 */
public class HBOReportServiceImpl implements HBOReportsService {
	public static Logger logger = Logger.getRootLogger();

	public ArrayList getReportData(HBOUserBean userBean,int repId,HBOReportDTO reportDTO) throws HBOException {
		ArrayList arrReportData = new ArrayList();
			HBOReportsDAO reportDAO = new HBOReportsDAOImpl();
			
			try {
				logger.info("Report Service: getReportData");
				arrReportData = reportDAO.findByPrimaryKey(userBean,repId,reportDTO);
				logger.info("Report Service: arrReportData.size()" +arrReportData.size());
			} catch (Exception e) {
				if (e instanceof DAOException) {
					
					logger.error(
						"DAOException occured in getBatchList(String UserID,String Cond):"
							+ e.getMessage());
					throw new HBOException(e.getMessage());
				} else {
					logger.error(
						"Exception occured in getBatchList(String UserID,String Cond):"
							+ e.getMessage());
				}
			}
			return arrReportData;
		}


}

