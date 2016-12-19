package com.ibm.virtualization.ussdactivationweb.exception;

import com.ibm.virtualization.ussdactivationweb.utils.DAOException;

public class VSubscriberMstrDaoException extends DAOException {

    public VSubscriberMstrDaoException(String message) {
        super(message);
    }

    public VSubscriberMstrDaoException(String message, Throwable throwable) {
        super(message,throwable);
    }

}
