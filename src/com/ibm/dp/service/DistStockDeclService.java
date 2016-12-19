package com.ibm.dp.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

import com.ibm.dp.beans.DistStockAcceptTransferBean;
import com.ibm.dp.beans.DistStockDeclarationDetailsBean;
import com.ibm.dp.dto.DistStockAccpDisplayDTO;
import com.ibm.dp.dto.DistStockDecOptionsDTO;
import com.ibm.dp.dto.DiststockAccpTransferDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DistStockDeclService {
	public void insertStockDecl(ListIterator<DistStockDeclarationDetailsBean> stockDeclarationDetailsBeanListItr) throws VirtualizationServiceException , DAOException;
	public ArrayList<DistStockDecOptionsDTO> fetchProdIdNameList(DistStockDeclarationDetailsBean stockDeclarationDetailsBean)throws VirtualizationServiceException , DAOException;
	public ArrayList<DistStockDecOptionsDTO>fetchTransFormList(DistStockAcceptTransferBean stockAcceptTransBean)throws VirtualizationServiceException , DAOException;
	public ArrayList<DistStockDecOptionsDTO>getDCNoList(int accountId , int selectedValue)throws VirtualizationServiceException , DAOException;
	public HashMap<String ,ArrayList<DistStockAccpDisplayDTO>>stockAcceptInfo(DistStockAcceptTransferBean stockAcceptTransBean)throws  VirtualizationServiceException ,DAOException;
	public ArrayList<DiststockAccpTransferDTO>stockAccpDisplayList(DistStockAcceptTransferBean stockAcceptTransBean)throws VirtualizationServiceException ,DAOException;
	public ArrayList<DiststockAccpTransferDTO>viewAllSerialNoOfStock(String dcNo )throws  VirtualizationServiceException ,DAOException;
	public void updateAcceptStockTransfer(String[] arrCheckedSTA , String account_id)throws  VirtualizationServiceException ,DAOException;
}

