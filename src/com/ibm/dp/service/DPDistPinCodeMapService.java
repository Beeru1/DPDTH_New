package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.DPDistPinCodeMapDto;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.exception.DPServiceException;


public interface DPDistPinCodeMapService {

	List<DPDistPinCodeMapDto> getALLDistPinList()throws DPServiceException;

	List uploadExcel(InputStream inputstream)throws DPServiceException;

	String addDistPinCodeMap(List list)throws DPServiceException;

}
