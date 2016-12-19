package com.ibm.hbo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



public class HBOZoneMstrAction extends Action {


    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward();
        HttpSession session = request.getSession();
        //TODO Actual Code
        return null;
    }
}
