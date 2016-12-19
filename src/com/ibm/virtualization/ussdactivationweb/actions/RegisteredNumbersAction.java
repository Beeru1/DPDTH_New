/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.actions;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import com.ibm.virtualization.ussdactivationweb.beans.RegistrationOfAllBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.RegisteredNumbersDAO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;

/**
 * @author abhipsa
 * 
 */
public class RegisteredNumbersAction extends DispatchAction {
	private static final Logger logger = Logger
	.getLogger(RegisteredNumbersAction.class);

	/**
	 * 
	 * This method is called when we press submit button from
	 * RegisteredNumbers.jsp It inserts a new registration number for the
	 * selected user in tha data base.
	 * 
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 */
	public ActionForward insertRegNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger
		.debug("Entering insertRegNum() method of RegisteredNumbersAction.");
		RegistrationOfAllBean bean = (RegistrationOfAllBean) form;
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
		.getAttribute(Constants.USER_INFO);
		boolean bolStatus = false;
		try {
			int intUserId = Integer.parseInt(userBean.getUserId());
			ActionErrors errors = new ActionErrors();
			bolStatus = (new RegisteredNumbersDAO()).insert(bean, intUserId);
			if (bolStatus) {
				logger
				.debug("Registation Number has been successfully inserted.");
				logger
				.info("Registation Number has been successfully inserted for user "+ userBean.getLoginId());

			} else {
				errors.clear();
				errors.add(Constants.SQL_EXCEPTION, new ActionError("ErrorKey",
				"Number already registered."));
				saveErrors(request, errors);
				logger.debug("Registation Number has not been inserted.");
				// forward to error.jsp
				return mapping.findForward(Constants.ERROR);
			}
			ActionMessages messages = new ActionMessages();
			ActionMessage actionMsg = new ActionMessage("Saved");
			messages.add(ActionMessages.GLOBAL_MESSAGE, actionMsg);
			saveMessages(request, messages);
			// forward to success.jsp
			return mapping.findForward(Constants.SUCCESS);
		} catch (DAOException ex) {
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.SQL_EXCEPTION, new ActionError(
					Constants.SQL_EXCEPTION));
			saveErrors(request, errors);
			return mapping.findForward(Constants.ERROR);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION
					+ "in insertRegNum method of RegisteredNumberAction.", e);
			// forward to error.jsp
			logger
			.debug("Exiting insertRegNum() method of RegisteredNumbersAction.");
			return mapping.findForward(Constants.ERROR);
		}
	}

	/**
	 * 
	 * This method called when you press submit button on
	 * ViewEditRegisteredNumber.jsp It will show you the list of registered
	 * numbers under the seleted user. The same page will be called again.
	 * 
	 * 
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 */
	public ActionForward searchRegNumbers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse res)
	throws Exception {
		logger
		.debug("Entering searchRegNumbers() method of RegisteredNumbersAction.");
		ActionErrors errors = new ActionErrors();
		RegistrationOfAllBean bean = (RegistrationOfAllBean) form;
		RegisteredNumbersDAO regDAO = new RegisteredNumbersDAO();
		List regnolist = new ArrayList();
		int intDealerId = Integer.parseInt(bean.getBussinessUserId());

		try {
			regnolist = regDAO.getRegisteredNumbers(intDealerId, bean
					.getTypeOfUserId());
			bean.setRegNumberList(regnolist);
			if (regnolist.isEmpty()) {

				errors.add("registeredNumber.Notfound", new ActionError(
				"registeredNumber.Notfound"));
				saveErrors(request, errors);
				return mapping.findForward("showsviewreg");
			}
		} catch (DAOException ex) {

			errors.add(Constants.SQL_EXCEPTION, new ActionError(
					Constants.SQL_EXCEPTION));
			saveErrors(request, errors);
			logger
			.error(Constants.EXCEPTION
					+ "in searchRegNumbers method of RegisteredNumbersAction class"
					+ ex);
			return mapping.findForward(Constants.SUCCESS1);
		} catch (Exception e) {
			logger
			.error(
					"Exception occured in searchRegNumbers() method of class RegisteredNumbersAction.",
					e);
			return mapping.findForward(Constants.ERROR);
		}
		logger
		.debug("Exiting searchRegNumbers() method of RegisteredNumbersAction.");
		return mapping.findForward("showsviewreg");
	}

	/**
	 * 
	 * This method is called when you click edit link from the list of
	 * registered Numbers. It will redirect you to EditRegisteredNumber.jsp And
	 * will show you the details of that registered Numbers.
	 * 
	 * 
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 */
	public ActionForward initUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse res)
	throws Exception {

		logger
		.debug("Entering initUpdate() method of RegisteredNumbersAction.");
		RegistrationOfAllBean bean = (RegistrationOfAllBean) form;
		RegisteredNumbersDAO regDAO = new RegisteredNumbersDAO();

		try {
			String strMobileId = request.getParameter("regNumberId") == null ? ""
					: request.getParameter("regNumberId");
			int intId = Integer.parseInt(strMobileId);
			int typeofuser = bean.getTypeOfUserId();
			RegistrationOfAllBean updateBean = regDAO
			.getRegisteredSingleNumber(intId, typeofuser);
			bean.setCircleCode(updateBean.getCircleCode());
			bean.setBussinessUserId(updateBean.getBussinessUserId());
			bean.setRegNumber(updateBean.getRegNumber());
			bean.setOldRegNumber(updateBean.getRegNumber());
			bean.setRegNumberId(updateBean.getRegNumberId());
			bean.setStatus(updateBean.getStatus());
			bean.setOldStatus(updateBean.getStatus());
			bean.setTypeOfUserId(typeofuser);

		} catch (Exception e) {
			logger.error(
					"Exception has occur in Dealer RegNumberAction class.", e);
			return mapping.findForward(Constants.ERROR);
		}
		// forward to editRegisterdNumber.jsp
		logger.debug("Exiting initUpdate() method of RegisteredNumbersAction.");
		return mapping.findForward("showeditpage");
	}

	/**
	 * 
	 * This method is called when you press submit button from
	 * EditRegisteredNumber.jsp This updates the number in the database.
	 * 
	 * 
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.debug("Entering update() method of RegisteredNumbersAction.");
		ActionErrors errors = new ActionErrors();
		RegistrationOfAllBean bean = (RegistrationOfAllBean) form;
		RegisteredNumbersDAO regDAO = new RegisteredNumbersDAO();
		UserDetailsBean userBean = (UserDetailsBean) (req.getSession(true))
		.getAttribute(Constants.USER_INFO);
		int intUserId = Integer.parseInt(userBean.getUserId());
		boolean status = false;
		try {
			if (bean.getStatus().equalsIgnoreCase("A")) {
				status = regDAO.update(bean, intUserId, false);
				if (status) {
					logger
					.debug("Status inside update() of reg number action: "
							+ status);
				} else {
					errors.clear();
					errors.add(Constants.SQL_EXCEPTION, new ActionError(
							"ErrorKey", "Number is already in active state."));
					saveErrors(req, errors);
					return mapping.findForward(Constants.ERROR);
				}
			} else {
				regDAO.update(bean, intUserId, true);
			}
			ActionMessages messages = new ActionMessages();
			ActionMessage actionMsg = new ActionMessage("Saved");
			messages.add(ActionMessages.GLOBAL_MESSAGE, actionMsg);
			saveMessages(req, messages);
			logger.debug("Exiting update() method of RegisteredNumbersAction.");
			return mapping.findForward(Constants.SUCCESS);
		} catch (DAOException ex) {
			errors.add(Constants.SQL_EXCEPTION, new ActionError(
					Constants.SQL_EXCEPTION));
			saveErrors(req, errors);
			logger.error(Constants.EXCEPTION
					+ "in update method of RegNumberAction class.", ex);
			return mapping.findForward("showeditpage");
		} catch (Exception e) {
			logger
			.error(
					"Exception occured in update() method of class RegNumberAction.",
					e);
			return mapping.findForward(Constants.ERROR);
		}
	}

}
