package com.ibm.virtualization.recharge.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ibm.dp.dto.RejectedInvoiceDTO;
import com.ibm.dp.dto.RejectedPartnerInvDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface RejectedInvDAO {
	public List<RejectedInvoiceDTO> getRejectedInvData(String monthID) throws DAOException,SQLException;
	public List<RejectedPartnerInvDTO> getRejectedPartnerInv(String monthID) throws DAOException,SQLException;

}
