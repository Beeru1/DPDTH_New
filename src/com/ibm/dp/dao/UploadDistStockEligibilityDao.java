package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;
public interface UploadDistStockEligibilityDao {
	public List<ViewStockEligibilityDTO> getRegionList() throws DAOException;
	public List<ViewStockEligibilityDTO> getCircleList(int regionId) throws DAOException;
	String uploadExcel(InputStream inputstream,int productType,int distType,long dthadminid,int regionId) throws DAOException;
}
