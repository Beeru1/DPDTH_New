package com.ibm.virtualization.ussdactivationweb.exception;

import com.ibm.virtualization.ussdactivationweb.utils.DAOException;

public class VCircleMstrDaoException extends DAOException {

    public VCircleMstrDaoException(String message) {
        super(message);
    }

    public VCircleMstrDaoException(String message, Throwable throwable) {
        super(message,throwable);
    }

}
