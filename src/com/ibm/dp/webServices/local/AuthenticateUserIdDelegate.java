package com.ibm.dp.webServices.local;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.virtualization.recharge.beans.UserBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.LoginService;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;
import com.ibm.virtualization.recharge.service.impl.LoginServiceImpl;


@javax.jws.WebService (targetNamespace="http://local.webServices.dp.ibm.com/", serviceName="AuthenticateUserIdService", portName="AuthenticateUserIdPort", wsdlLocation="WEB-INF/wsdl/AuthenticateUserIdService.wsdl")
public class AuthenticateUserIdDelegate{

    com.ibm.dp.webServices.local.AuthenticateUserId _authenticateUserId = new com.ibm.dp.webServices.local.AuthenticateUserId();

    public String[] authenticate(String dpUser, String dpPass, String wsUser, String wsPass) {
        return _authenticateUserId.authenticate(dpUser,dpPass,wsUser,wsPass);
    }

}