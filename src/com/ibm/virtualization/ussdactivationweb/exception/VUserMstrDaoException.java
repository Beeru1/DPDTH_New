package com.ibm.virtualization.ussdactivationweb.exception;

import com.ibm.virtualization.ussdactivationweb.utils.DAOException;

public class VUserMstrDaoException extends DAOException {

    public VUserMstrDaoException(String message) {
        super(message);
    }

    public VUserMstrDaoException(String message, Throwable throwable) {
        super(message,throwable);
    }

}
