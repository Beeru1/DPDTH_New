package com.ibm.hbo.services;

import com.ibm.hbo.dto.TransactionAuditDTO;
import com.ibm.hbo.exception.DAOException;

public interface TransactionAuditService {

	void insertintoHistory(TransactionAuditDTO secondaryDto) throws DAOException;

}
