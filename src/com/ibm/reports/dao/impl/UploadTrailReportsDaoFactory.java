package com.ibm.reports.dao.impl;

import com.ibm.dp.common.Constants;
import com.ibm.reports.dao.UploadTrailReportsDao;

public class UploadTrailReportsDaoFactory {

	public static UploadTrailReportsDao  getUploadTrailReportDao(int groupId) {
		switch (groupId) {
		case Constants.DISTIBUTOR:
			return UploadtrailReportsDaoIml.getInstance();
	default:
			return UploadtrailReportsDaoIml.getInstance();
		
		}
	}

}
