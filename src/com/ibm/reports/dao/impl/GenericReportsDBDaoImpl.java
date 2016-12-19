package com.ibm.reports.dao.impl;

import org.apache.log4j.Logger;

import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.reports.dao.GenericReportsDao;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public class GenericReportsDBDaoImpl extends GenericReportsDaoImpl {

	private Logger logger = Logger.getLogger(GenericReportsDBDaoImpl.class.getName());

	private static GenericReportsDao genericReportsDBDao = new GenericReportsDBDaoImpl();
	private GenericReportsDBDaoImpl(){}
	public static GenericReportsDao getInstance() {
		return genericReportsDBDao;
	}
	public GenericReportsOutputDto exportToExcel(GenericReportPararmeterDTO genericDTO) throws DAOException {
		return null;
	}
}
