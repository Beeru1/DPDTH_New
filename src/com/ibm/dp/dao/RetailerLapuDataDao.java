/**
 * 
 */
package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.RetailerLapuDataDto;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * @author Nehil Parashar
 *
 */
public interface RetailerLapuDataDao 
{
	public List<RetailerLapuDataDto> getAllRetailerLapuData() throws DAOException;
	public List<RetailerLapuDataDto> validateExcel(InputStream inputstream) throws DAOException;
	public String updateLapuNumbers(List<RetailerLapuDataDto> lapuData) throws DAOException;
}
