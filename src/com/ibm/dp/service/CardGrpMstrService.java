package com.ibm.dp.service;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.beans.DPCardGrpMstrFormBean;
import com.ibm.dp.dto.CardMstrDto;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;


public interface CardGrpMstrService {

	//public List<DebitAmountMstrFormBean> getProductList() throws DPServiceException ; 
	public boolean insertCardGrp(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws DPServiceException ;
	public boolean deleteCardGrp(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws DPServiceException ;
	public List getCardGroupList() throws DPServiceException ; 
	public ArrayList<CardMstrDto> select(CardMstrDto dpvpDTO, int lowerBound, int upperBound) throws DPServiceException;
}

