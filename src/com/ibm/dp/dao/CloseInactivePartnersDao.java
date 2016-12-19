package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.MultiKeyMap;
import com.ibm.dp.dto.CloseInactivePartnersDto;
import com.ibm.dp.dto.DpInvoiceDto;

public interface CloseInactivePartnersDao {
	
	public ArrayList uploadInvoiceSheet(Map<String, CloseInactivePartnersDto> cipMap) throws Exception;
	

}
