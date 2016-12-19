package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.beans.DebitAmountMstrFormBean;
import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;

public interface DebitAmountMasterDao {
	
	public List<DebitAmountMstrFormBean> getProductList() throws DAOException ; 
	public boolean insertDebitAmount(DebitAmountMstrFormBean debitAmountMstrFormBean) throws DAOException ; 
	public String getDebitAmount(DebitAmountMstrFormBean debitAmountMstrFormBean) throws DAOException ; 
}
