package com.ibm.dp.service;

import java.util.List;
import java.util.Map;


import com.ibm.dp.dto.ManufacturerDetailsDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface ManufacturerService {
	 public List<ManufacturerDetailsDto> getManufacturerData() throws DPServiceException;
	 public void  saveManufacturerData(String manuList,String flag) throws DPServiceException;
}
