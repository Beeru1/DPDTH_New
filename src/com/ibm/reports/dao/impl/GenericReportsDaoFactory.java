package com.ibm.reports.dao.impl;

import com.ibm.dp.common.Constants;
import com.ibm.reports.dao.GenericReportsDao;

public class GenericReportsDaoFactory {

	public static GenericReportsDao getGenericReportDao(int groupId) {
		switch (groupId) {
		case Constants.DISTIBUTOR:
			return GenericReportsProcDaoImpl.getInstance();
			//return GenericReportsDBDaoImpl.getInstance();
		default:
			return GenericReportsProcDaoImpl.getInstance();
		}
	}
}
