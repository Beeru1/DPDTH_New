package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.beans.DPCardGrpMstrFormBean;
import com.ibm.dp.beans.DebitAmountMstrFormBean;
import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.CardMstrDto;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;

public interface CardGrpMstrDao {
	
	//public List<DebitAmountMstrFormBean> getProductList() throws DAOException ; 
	public boolean insertCardGrp(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws DAOException ;
	public boolean deleteCardGrp(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws DAOException ;
	public List getCardGroupList() throws DAOException ; 
	public ArrayList<CardMstrDto> select(CardMstrDto dpvpDTO, int lowerBound, int upperBound) throws DAOException;
}
