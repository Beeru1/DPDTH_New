package com.ibm.dp.dao;

import com.ibm.hbo.dto.TransactionAuditDTO;
import com.ibm.hbo.exception.DAOException;


public interface TransactionAuditDao {
	
	void insertintoHistory(TransactionAuditDTO secondaryDto) throws DAOException;
}
