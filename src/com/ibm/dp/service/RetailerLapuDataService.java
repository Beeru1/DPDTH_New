/**
 * 
 */
package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.RetailerLapuDataDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;


/**
 * @author Nehil Parashar
 *
 */
public interface RetailerLapuDataService 
{
	public List<RetailerLapuDataDto> getAllRetailerLapuData()throws VirtualizationServiceException;
	
	public List<RetailerLapuDataDto> validateExcel(InputStream inputstream) throws DPServiceException;
	
	public String updateLapuNumbers(List<RetailerLapuDataDto> lapuData) throws DPServiceException;
}
