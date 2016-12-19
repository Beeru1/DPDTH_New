package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.ibm.dp.beans.StbFlushOutFormBean;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.exception.DPServiceException;

public interface STBFlushOutService 
{
	/**
	 * This method can be used to get List of STB details
	 */
	public StbFlushOutFormBean getSTBList(StbFlushOutFormBean stbFlushOutFormBean)throws DPServiceException;
	
	Map uploadFreshExcel(InputStream inputstream) throws DPServiceException;
	
	//String flushDPSerialNumbers(List<DuplicateSTBDTO> uploadList,List<DuplicateSTBDTO> queryList) throws Exception;
	
	String flushDPSerialNumbers(List<DuplicateSTBDTO> uploadList,List<DuplicateSTBDTO> queryList, String loginUserId) throws Exception;
	
	
}