/* 
 * ArcUserMstrDao.java
 * Created: September 25, 2007
 * 
 * 
 */

package com.ibm.hbo.dao;
import java.util.ArrayList;

import com.ibm.hbo.dto.HBOReportDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.forms.HBOReportFormBean;
 
public interface HBOReportsDAO {

	
	/**
	 * @param repId
	 * @param modelcode
	 * @param circleId
	 * @return
	 */
	ArrayList findByPrimaryKey(HBOUserBean userBean, int repId, HBOReportDTO  reportDTO)throws DAOException;
	


}

