package com.ibm.dp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.dto.CheckRetailerBalanceDto;
import com.ibm.virtualization.recharge.dto.AccountBalance;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface CheckRetailerBalanceDao {
	
	
	public ArrayList getDetails( long loginUserId) throws VirtualizationServiceException, DAOException;
	
}
