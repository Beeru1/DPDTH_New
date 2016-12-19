package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

import com.ibm.dp.beans.DistStockAcceptTransferBean;
import com.ibm.dp.beans.DistStockDeclarationDetailsBean;
import com.ibm.dp.dto.DistStockAccpDisplayDTO;
import com.ibm.dp.dto.DistStockDecOptionsDTO;
import com.ibm.dp.dto.DiststockAccpTransferDTO;
import com.ibm.dp.exception.DAOException;

public interface DistStockDeclarationDao {

	public void insertStockDecl(ListIterator<DistStockDeclarationDetailsBean> stockDeclarationDetailsBeanListItr) throws DAOException;
	public ArrayList<DistStockDecOptionsDTO> fetchProdIdNameList(DistStockDeclarationDetailsBean stockDeclarationDetailsBean)throws DAOException;
	public ArrayList<DistStockDecOptionsDTO>fetchTransFormList(DistStockAcceptTransferBean stockAcceptTransBean)throws DAOException;
	public ArrayList<DistStockDecOptionsDTO>getDCNoList(int accountId , int selectedValue)throws DAOException;
	public HashMap<String ,ArrayList<DistStockAccpDisplayDTO>>stockAcceptInfo(DistStockAcceptTransferBean stockAcceptTransBean)throws DAOException;
	public ArrayList<DiststockAccpTransferDTO>stockAccpDisplayList(DistStockAcceptTransferBean stockAcceptTransBean)throws DAOException;
	public ArrayList<DiststockAccpTransferDTO>viewAllSerialNoOfStock(String dcNo )throws DAOException;
	public void updateAcceptStockTransfer(String[] arrCheckedSTA , String account_id)throws DAOException;
}
