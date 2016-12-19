package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface UploadDistStockEligibilityService {
	List<ViewStockEligibilityDTO> getRegionList() throws  VirtualizationServiceException ;
	List<ViewStockEligibilityDTO> getCircleList(int regionId) throws  VirtualizationServiceException ;
	String uploadExcel(InputStream inputstream,int productType,int distType,long dthadminid,int regionId) throws DPServiceException;
}
