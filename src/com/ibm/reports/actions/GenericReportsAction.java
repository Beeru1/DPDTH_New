package com.ibm.reports.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.reports.common.ReportConstants;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.reports.beans.GenericReportsFormBean;
import com.ibm.reports.dto.CriteriaDTO;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.dto.ReportDetailDTO;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;

public class GenericReportsAction extends DispatchAction
{

	private static Logger logger = Logger.getLogger(GenericReportsAction.class.getName());
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String INIT_EXCEL = "initExcel";
	private static final String INIT_EXCEL_DEBIT = "initExcel_debit";
	private static final String INIT_EXCEL_RECO = "initExcel_reco";
	private static final String ERROR = "error";
	private static final String ERROR_AGEING = "ERROR_AGEING";
	private static final String REPORT_ID = "reportId";
	private static final String OUTPUT = "output";

	public ActionForward initReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// logger.info("**********inside GenericReports Action ********");
		ActionErrors errors = new ActionErrors();

		try
		{
			// System.out.println("checking for Timings");
			Calendar calendar = new GregorianCalendar();
			String from_time = ResourceReader
					.getCoreResourceBundleValue(Constants.MIS_REPORT_DOWNTIME_FROM);
			String to_time = ResourceReader
					.getCoreResourceBundleValue(Constants.MIS_REPORT_DOWNTIME_TO);

			String am_pm;
			int hour = calendar.get(Calendar.HOUR);
			int minute = calendar.get(Calendar.MINUTE);

			int second = calendar.get(Calendar.SECOND);

			if (calendar.get(Calendar.AM_PM) == 0)
				am_pm = "AM";
			else
				am_pm = "PM";
			// System.out.println("Current Time------------- : " + hour + ":"+
			// minute + ":" + second + " " + am_pm);
			if (am_pm != null && am_pm.equalsIgnoreCase("AM"))
			{
				// System.out.println("hour:::"+hour);
				// System.out.println(hour >=4);
				// System.out.println(hour <=6);
				if (hour >= Integer.parseInt(from_time) && hour <= (Integer.parseInt(to_time) - 1))
				{
					request.setAttribute("errMsg", "Reports are not available between " + from_time
							+ " AM to " + to_time + " AM");
					return mapping.findForward("reportsDownPage");
				}
			}

			GenericReportsFormBean genericReportsFormBean = (GenericReportsFormBean) form;
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			GenericReportsService genericReportsService = GenericReportsServiceImpl.getInstance();
			int groupId = sessionContext.getGroupId();
			int reportId = Integer.valueOf(request.getParameter(REPORT_ID));

			populateCriteria(reportId, genericReportsFormBean, groupId, request);
			populateInitialValues(sessionContext, genericReportsFormBean, reportId);

			ReportDetailDTO reportDetail = genericReportsService.getReportDetails(reportId);

			genericReportsFormBean.setReportId(reportId);
			genericReportsFormBean.setReportName(reportDetail.getReportName());
			genericReportsFormBean.setReportLabel(reportDetail.getReportLabel());
			genericReportsFormBean.setDistData(reportDetail.getDistData());
			genericReportsFormBean.setOtherUserData(reportDetail.getOtherUserData());
			genericReportsFormBean.setDateValidation(getDateValidation(groupId, reportDetail
					.getDistData(), reportDetail.getOtherUserData()));
			genericReportsFormBean.setMsgValidation(getMsgValidation(groupId, reportDetail
					.getDistData(), reportDetail.getOtherUserData()));
			genericReportsFormBean.setLastSchedulerDate(getLastSchedulerDate(groupId, reportDetail
					.getDistData(), reportDetail.getOtherUserData()));
			logger.info("sessionContext.getAccountLevel()::::::::::::::"
					+ sessionContext.getAccountLevel());
			genericReportsFormBean.setAccountLevel(sessionContext.getAccountLevel());
		}
		catch (DAOException se)
		{
			logger.error("EXCEPTION OCCURED ::  " + se.getMessage());
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		catch (DPServiceException se)
		{
			logger.info("EXCEPTION OCCURED ::  " + se.getMessage());
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		catch (Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		return mapping.findForward(INIT_SUCCESS);
	}

	private void populateInitialValues(UserSessionContext sessionContext,
			GenericReportsFormBean genericReportsFormBean, int reportId) throws DPServiceException,
			DAOException
	{

		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
		GenericReportsService reportService = GenericReportsServiceImpl.getInstance();
		List<SelectionCollection> listAllCircles = new ArrayList<SelectionCollection>();
		List<SelectionCollection> listTsm = new ArrayList<SelectionCollection>();
		List<SelectionCollection> listDist = new ArrayList<SelectionCollection>();
		int groupId = sessionContext.getGroupId();
		String accountID = String.valueOf(sessionContext.getId());
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		// HashMap<Integer, String> idMap=new HashMap<Integer, String>();
		genericReportsFormBean.setGroupId(String.valueOf(groupId));
		// Added by Neetika on 31 July 2014 for eligibility report
		List<SelectionCollection> listAllZones = new ArrayList<SelectionCollection>();
		if (genericReportsFormBean.isCircleRequired() && genericReportsFormBean.isInitvalCircle())
		{

			if (reportId == 51 && groupId == Constants.DTH_ADMIN_GROUP_ID)
			{
				listAllCircles = utilityAjaxService.getAllCirclesForDTHAdmin();
				/*
				 * for (int i=0;i<listAllCircles.size();i++){
				 * logger.info("groupId:::::...... circle s"
				 * +listAllCircles.get(i).getStrText()); }
				 */
				genericReportsFormBean.setCircleList(listAllCircles);
			}
			else
			{
				logger.info("groupId:::::" + groupId);
				if (groupId == Constants.ADMIN_GROUP_ID || groupId == Constants.DTH_ADMIN_GROUP_ID)
				{
					listAllCircles = utilityAjaxService.getAllCircles();
					/*
					 * for (int i=0;i<listAllCircles.size();i++){
					 * logger.info("groupId:::::...... circle s"
					 * +listAllCircles.get(i).getStrText()); }
					 */
					genericReportsFormBean.setCircleList(listAllCircles);
				}
				else if (groupId == Constants.TSM_GROUP_ID || groupId == Constants.GROUP_ID_ZBM
						|| groupId == Constants.GROUP_ID_ZSM
						|| groupId == Constants.GROUP_ID_CIRCLE_ADMIN)
				{ // circle
					// admin
					// added
					// by
					// neetika
					// for
					// incident
					// IN1821528
					listAllCircles = utilityAjaxService.getTsmCircles(sessionContext.getId());

					/*
					 * for (int i=0;i<listAllCircles.size();i++){
					 * logger.info("groupId:::::...tsm zsm zbm  circles"
					 * +listAllCircles.get(i).getStrText()); }
					 */
					genericReportsFormBean.setCircleList(listAllCircles);
				}
				else
				{

					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					logger.info("groupId:::::... circle s" + sessionContext.getCircleId());
					listAllCircles.add(sc);
					genericReportsFormBean.setCircleList(listAllCircles);

				}
			}

		}
		if (genericReportsFormBean.isTsmRequired() && genericReportsFormBean.isInitvalTSM())
		{
			logger.info("circleIdSr::::::::::::::::::::::::::::::::::" + circleIdSr);
			if (groupId == Constants.TM)
			{ // if TSM logged in
				SelectionCollection sc1 = new SelectionCollection();
				sc1.setStrValue(accountID);
				sc1.setStrText(sessionContext.getAccountName());
				listTsm = null;
				listTsm = new ArrayList<SelectionCollection>();
				listTsm.add(sc1);
				genericReportsFormBean.setTsmList(listTsm);
			}
			else
			{
				logger.info("grp::::::::::::::::::::::::::::::::::" + groupId
						+ "sessionContext.getId() " + sessionContext.getId());
				// if (groupId == Constants.GROUP_ID_ZBM || groupId ==
				// Constants.GROUP_ID_ZSM ) {
				if (groupId == Constants.GROUP_ID_ZBM || groupId == Constants.GROUP_ID_ZSM)
				{
					listTsm = utilityAjaxService.getAllAccountsMultipleCircle(
							Constants.ACC_LEVEL_TSM, sessionContext.getId());
					genericReportsFormBean.setTsmList(listTsm);
				}
				else if (groupId == Constants.GROUP_ID_CIRCLE_ADMIN)
				{ // changed
					// by
					// neetika
					listTsm = utilityAjaxService.getAllAccountsCircleAdmin(Constants.ACC_LEVEL_TSM,
							sessionContext.getId(), circleIdSr);
					genericReportsFormBean.setTsmList(listTsm);
				}
				else
				{
					listTsm = utilityAjaxService.getAllAccountsSingleCircle(
							Constants.ACC_LEVEL_TSM, circleIdSr);
					genericReportsFormBean.setTsmList(listTsm);
				}
			}

		}
		if (genericReportsFormBean.isDistributorRequired()
				&& genericReportsFormBean.isInitvalDistributor())
		{
			if (groupId == Constants.DISTIBUTOR || groupId == Constants.FOS
					|| groupId == Constants.DEALER)
			{
				/*
				 * SelectionCollection sc1 = new SelectionCollection();
				 * sc1.setStrValue("-1"); sc1.setStrText("--All--");
				 * listDist.add(sc1);
				 */
				SelectionCollection sc1 = new SelectionCollection();
				sc1.setStrValue(Long.toString(sessionContext.getId()));
				sc1.setStrText(sessionContext.getAccountName());
				listDist.add(sc1);
				genericReportsFormBean.setDistId(sc1.getStrValue());
				genericReportsFormBean.setDistList(listDist);
				// listTsm =
				// utilityAjaxService.getAllAccountsSingleCircle(Constants
				// .ACC_LEVEL_TSM,circleIdSr);
				// genericReportsFormBean.setTsmList(listTsm);

				listTsm = utilityAjaxService.getParent(accountID);
				genericReportsFormBean.setTsmList(listTsm);
				genericReportsFormBean.setTsmId(listTsm.get(0).getStrValue());
			}
			if (groupId == Constants.ZSM || groupId == Constants.TM || groupId == Constants.ZBM)
			{
				listDist = utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
				genericReportsFormBean.setDistList(listDist);
			}

		}
		if (genericReportsFormBean.isStbTypeRequired() && genericReportsFormBean.isInitvalSTBType())
		{
			List<DpProductCategoryDto> listProduct = reportService.getProductList(reportId);
			genericReportsFormBean.setProductList(listProduct);

		}
		if (genericReportsFormBean.isProductTypeRequired()
				&& genericReportsFormBean.isInitvalProductType())
		{
			List<DpProductCategoryDto> listProduct = reportService.getProductList(reportId);
			genericReportsFormBean.setProductList(listProduct);

		}
		logger.info("genericReportsFormBean.isBusinessCategoryRequired()::"
				+ genericReportsFormBean.isBusinessCategoryRequired());
		logger.info("genericReportsFormBean.isInitvalBusinessType()::"
				+ genericReportsFormBean.isInitvalBusinessCategory());
		if (genericReportsFormBean.isBusinessCategoryRequired()
				&& genericReportsFormBean.isInitvalBusinessCategory())
		{
			List<DpProductCategoryDto> listBusinessCategory = reportService
					.getTransactionType(reportId);
			logger.info("listBusinessCategory size::" + listBusinessCategory.size());
			genericReportsFormBean.setBusinessCategoryList(listBusinessCategory);
		}
		if (genericReportsFormBean.isCollectionTypeRequired()
				&& genericReportsFormBean.isInitvalCollectionType())
		{
			List<CollectionReportDTO> collectionType = reportService
					.getCollectionTypeMaster(reportId);
			genericReportsFormBean.setCollectionType(collectionType);

		}

		if (genericReportsFormBean.isDateOptionRequired()
				&& genericReportsFormBean.isInitvalDateOption())
		{

			List<GenericOptionDTO> dateOptionList = reportService.getDateOptions(reportId);
			genericReportsFormBean.setDateOptionList(dateOptionList);

		}

		// added by aman
		if (genericReportsFormBean.isActivityRequired()
				&& genericReportsFormBean.isInitvalActivity())
		{

			List<GenericOptionDTO> activityList = reportService.getActivity(reportId);
			genericReportsFormBean.setActivityList(activityList);

		}

		// end of changes by aman

		if (genericReportsFormBean.isPoStatusRequired()
				&& genericReportsFormBean.isInitvalPOStatus())
		{

			List<GenericOptionDTO> poStatusList = reportService.getPOStatusList();
			genericReportsFormBean.setPoStatusList(poStatusList);

		}
		if (genericReportsFormBean.isSearchOptionRequired()
				&& genericReportsFormBean.isInitvalSearchOption())
		{

			List<GenericOptionDTO> searchOptionList = reportService.getSearchOptions(reportId);
			genericReportsFormBean.setSearchOptionList(searchOptionList);

		}

		if (genericReportsFormBean.isPendingAtRequired()
				&& genericReportsFormBean.isInitvalPendingAt())
		{

			List<GenericOptionDTO> pendingAtList = reportService.getPendingOptions(reportId);
			genericReportsFormBean.setPendingAtList(pendingAtList);

		}
		if (genericReportsFormBean.isTransferTypeRequired()
				&& genericReportsFormBean.isInitvaltransferType())
		{

			List<GenericOptionDTO> transferTypeList = reportService
					.getTransferTypeOptions(reportId);
			genericReportsFormBean.setTransferTypeList(transferTypeList);

		}
		if (genericReportsFormBean.isStatusRequired() && genericReportsFormBean.isInitvalStatus())
		{

			List<GenericOptionDTO> statusOptionList = reportService.getStatusOptions(reportId);
			genericReportsFormBean.setStatusOptionList(statusOptionList);

		}
		if (genericReportsFormBean.isRecoStatusRequired()
				&& genericReportsFormBean.isInitvalrecoStatus())
		{

			List<GenericOptionDTO> recoStatusList = reportService.getRecoStatus(reportId);
			genericReportsFormBean.setRecoStatusList(recoStatusList);

		}
		if (genericReportsFormBean.isStbStatusRequired()
				&& genericReportsFormBean.isInitvalStbStatus())
		{

			List<GenericOptionDTO> stbStatusList = reportService.getSTBStatusList(reportId);
			genericReportsFormBean.setStbStatusList(stbStatusList);

		}
		if (genericReportsFormBean.isStockTypeRequired()
				&& genericReportsFormBean.isInitvalStockType())
		{

			List<GenericOptionDTO> stockTypeList = reportService.getStockType();
			genericReportsFormBean.setStockTypeList(stockTypeList);

		}
		if (genericReportsFormBean.isDcStatusRequired()
				&& genericReportsFormBean.isInitvalDCStatus())
		{

			List<GenericOptionDTO> dcStatusList = reportService.getDcStatusList(reportId);
			// System.out.println("size:"+dcStatusList.size());
			genericReportsFormBean.setDcStatusList(dcStatusList);

		}
		if (genericReportsFormBean.isAccountTypeRequired()
				&& genericReportsFormBean.isInitvalaccountType())
		{

			List<GenericOptionDTO> accountTypeList = reportService.getAccountTypeList(reportId,
					groupId);
			genericReportsFormBean.setAccountTypeList(accountTypeList);

		}
		if (genericReportsFormBean.isAccountNameRequired()
				&& genericReportsFormBean.isInitvalaccountName())
		{

		}
		if (genericReportsFormBean.isFseRequired() && genericReportsFormBean.isInitvalFse())
		{
			if (groupId == Constants.DISTIBUTOR)
			{
				List<SelectionCollection> fseList = new ArrayList<SelectionCollection>();

				SelectionCollection sc = new SelectionCollection();
				sc.setStrText(sessionContext.getCircleName());
				sc.setStrValue((Integer.toString(sessionContext.getCircleId())));

				SelectionCollection sc1 = new SelectionCollection();
				sc1.setStrValue(Long.toString(sessionContext.getId()));
				sc1.setStrText(sessionContext.getLoginName());
				fseList = utilityAjaxService.getAllAccountsUnderMultipleAccounts(Long
						.toString(sessionContext.getId()));
				genericReportsFormBean.setFseList(fseList);
				genericReportsFormBean.setHiddendistIds(Long.toString(sessionContext.getId()));
			}

		}

		if (genericReportsFormBean.isRecoRequired() && genericReportsFormBean.isInitvalReco())
		{

			List<GenericOptionDTO> recoPeriodList = reportService.getRecoPeriodList(reportId,
					groupId);
			// System.out.println("recoPeriodList   size in action:::"+
			// recoPeriodList.size());
			genericReportsFormBean.setRecoPeriodList(recoPeriodList);

		}
		// Added by Neetika
		if (genericReportsFormBean.isZoneRequired() && genericReportsFormBean.isInitvalZone())
		{
			// logger.info("groupId:::in Zone::"+groupId);
			if (groupId == Constants.ADMIN_GROUP_ID || groupId == Constants.DTH_ADMIN_GROUP_ID)
			{
				listAllZones = utilityAjaxService.getAllZones();
				/*
				 * for (int i=0;i<listAllCircles.size();i++){
				 * logger.info("groupId:::::...... circle s"
				 * +listAllCircles.get(i).getStrText()); }
				 */
				genericReportsFormBean.setZonesList(listAllZones);
			} /*
			 * else if (groupId == Constants.TSM_GROUP_ID || groupId ==
			 * Constants.GROUP_ID_ZBM || groupId == Constants.GROUP_ID_ZSM ||
			 * groupId==Constants.GROUP_ID_CIRCLE_ADMIN) { //circle admin added
			 * by neetika for incident IN1821528 listAllCircles =
			 * utilityAjaxService.getTsmCircles(sessionContext.getId()); /for
			 * (int i=0;i<listAllCircles.size();i++){
			 * logger.info("groupId:::::...tsm zsm zbm  circles"
			 * +listAllCircles.get(i).getStrText()); }
			 */
			// genericReportsFormBean.setCircleList(listAllCircles);
			/*
			 * } else { SelectionCollection sc = new SelectionCollection();
			 * sc.setStrText(sessionContext.getCircleName());
			 * sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
			 * /
			 * /logger.info("groupId:::::... circle s"+sessionContext.getCircleId
			 * ()); listAllCircles.add(sc);
			 * genericReportsFormBean.setCircleList(listAllCircles); }
			 */

		}
		if (genericReportsFormBean.isPTRequired() && genericReportsFormBean.isInitvalPT())
		{
			// logger.info("groupId:::in PT::"+groupId);
			List<GenericOptionDTO> producttList = reportService.getPTList();
			genericReportsFormBean.setPtList(producttList);

		}
		if (genericReportsFormBean.isDTRequired() && genericReportsFormBean.isInitvalDT())
		{
			// logger.info("groupId:::in DT::"+groupId);
			List<GenericOptionDTO> producttList = reportService.getDTList();
			genericReportsFormBean.setDtList(producttList);

		}
	}

	private void populateCriteria(int reportId, GenericReportsFormBean genericReportsFormBean,
			int groupId, HttpServletRequest request) throws DPServiceException, DAOException
	{
		GenericReportsService genericReportsService = GenericReportsServiceImpl.getInstance();
		List<String> idList = new ArrayList<String>();
		List<CriteriaDTO> criteriaList = genericReportsService
				.getReportCriterias(reportId, groupId);
		for (CriteriaDTO criteriaDTO : criteriaList)
		{

			// System.out.println("*******************************");
			System.out.println("*********" + criteriaDTO.getCriteriaName() + "****************");

			if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_CIRCLE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableCircle(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalCircle(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setCircleRequired(true);
				idList.add(ReportConstants.CRITERIA_CIRCLE);

			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_BUSINESS_CATEGORY))
			{
				System.out.println("Business category....................");
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnablebusinessCategory(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalBusinessCategory(criteriaDTO.isInitvalFlag());
				}

				genericReportsFormBean.setBusinessCategoryRequired(true);
				idList.add(ReportConstants.CRITERIA_BUSINESS_CATEGORY);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_TSM))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableTSM(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalTSM(criteriaDTO.isInitvalFlag());
				}

				genericReportsFormBean.setTsmRequired(true);
				idList.add(ReportConstants.CRITERIA_TSM);

			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_DISTRIBUTOR))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableDistributor(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalDistributor(criteriaDTO.isInitvalFlag());
				}

				genericReportsFormBean.setDistributorRequired(true);
				idList.add(ReportConstants.CRITERIA_DISTRIBUTOR);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_COLLECTION_TYPE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableCollectionType(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalCollectionType(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setCollectionTypeRequired(true);
				idList.add(ReportConstants.CRITERIA_COLLECTION_TYPE);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_STB_TYPE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableSTBType(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalSTBType(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setStbTypeRequired(true);
				idList.add(ReportConstants.CRITERIA_STB_TYPE);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_PRODUCT_TYPE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableProductType(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalProductType(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setProductTypeRequired(true);
				idList.add(ReportConstants.CRITERIA_PRODUCT_TYPE);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_PO_STATUS))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnablePOStatus(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalPOStatus(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setPoStatusRequired(true);
				idList.add(ReportConstants.CRITERIA_PO_STATUS);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_DATEOPTION))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableDateOption(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalDateOption(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setDateOptionRequired(true);
				idList.add(ReportConstants.CRITERIA_DATEOPTION);
			}

			// added by aman

			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_ACTIVITY))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableActivity(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalActivity(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setActivityRequired(true);
				idList.add(ReportConstants.CRITERIA_ACTIVITY);
			}
			// end of changes by aman
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_FROM_DATE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableFromDate(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalFromDate(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setFromDateRequired(true);
				idList.add(ReportConstants.CRITERIA_FROM_DATE);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_TO_DATE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableToDate(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalToDate(criteriaDTO.isInitvalFlag());
				}

				genericReportsFormBean.setToDateRequired(true);
				idList.add(ReportConstants.CRITERIA_TO_DATE);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_SEARCH_OPTION))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableSearchOption(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalSearchOption(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setSearchOptionRequired(true);
				idList.add(ReportConstants.CRITERIA_SEARCH_OPTION);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_SEARCH_CRITERIA))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableSearchCriteria(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalSearchCriteria(criteriaDTO.isInitvalFlag());
				}

				genericReportsFormBean.setSearchCreteriaRequired(true);
				idList.add(ReportConstants.CRITERIA_SEARCH_CRITERIA);
			}
			else if (criteriaDTO.getCriteriaName()
					.equalsIgnoreCase(ReportConstants.CRITERIA_STATUS))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableStatus(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalStatus(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setStatusRequired(true);
				idList.add(ReportConstants.CRITERIA_STATUS);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_PENDING_AT))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnablePendingAt(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalPendingAt(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setPendingAtRequired(true);
				idList.add(ReportConstants.CRITERIA_PENDING_AT);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_TRANSFER_TYPE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableTransferType(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvaltransferType(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setTransferTypeRequired(true);
				idList.add(ReportConstants.CRITERIA_TRANSFER_TYPE);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_RECO_STATUS))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnablerecoStatus(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalrecoStatus(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setRecoStatusRequired(true);
				idList.add(ReportConstants.CRITERIA_RECO_STATUS);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_STB_STATUS))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableStbStatus(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalStbStatus(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setStbStatusRequired(true);
				idList.add(ReportConstants.CRITERIA_STB_STATUS);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_STOCK_TYPE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableStockType(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalStockType(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setStockTypeRequired(true);
				idList.add(ReportConstants.CRITERIA_STOCK_TYPE);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_FSE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableFSE(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalFse(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setFseRequired(true);
				idList.add(ReportConstants.CRITERIA_FSE);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_RETAILER))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableRetailer(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalRetailer(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setRetailerRequired(true);
				idList.add(ReportConstants.CRITERIA_RETAILER);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_DCSTATUS))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableDCStatus(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalDCStatus(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setDcStatusRequired(true);
				idList.add(ReportConstants.CRITERIA_DCSTATUS);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_ACCOUNT_TYPE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableAccountType(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalaccountType(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setAccountTypeRequired(true);
				idList.add(ReportConstants.CRITERIA_ACCOUNT_TYPE);
			}	
			
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_ACCOUNT_NAME))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableAccountName(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalaccountName(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setAccountNameRequired(true);
				idList.add(ReportConstants.CRITERIA_ACCOUNT_NAME);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_DC_NO))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableDCNo(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalDCNo(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setDcNoRequired(true);
				idList.add(ReportConstants.CRITERIA_DC_NO);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(
					ReportConstants.CRITERIA_RECO_PERIOD))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableRecoPeriod(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalRecoPeriod(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setRecoPeriodRequired(true);
				idList.add(ReportConstants.CRITERIA_RECO_PERIOD);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_DATE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableDate(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalDateOption(criteriaDTO.isInitvalFlag());
				}

				genericReportsFormBean.setDateRequired(true);
				idList.add(ReportConstants.CRITERIA_DATE);
			}

			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_RECO))
			{
				// System.out.println("criteriaDTO.getCriteriaName()::::::"+
				// criteriaDTO.getCriteriaName());
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableReco(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalReco(criteriaDTO.isInitvalFlag());
				}

				genericReportsFormBean.setRecoRequired(true);
				idList.add(ReportConstants.CRITERIA_RECO);
				// System.out.println("Reco true*************");
			}

			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_BLANK))
			{
				idList.add(ReportConstants.CRITERIA_BLANK);
			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_ZONE))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableZone(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalZone(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setZoneRequired(true);
				idList.add(ReportConstants.CRITERIA_ZONE);

			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_PT))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnablePT(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalPT(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setPTRequired(true);
				idList.add(ReportConstants.CRITERIA_PT);

			}
			else if (criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_DT))
			{
				if (criteriaDTO.isEnabledFlag())
				{
					genericReportsFormBean.setEnableDT(criteriaDTO.isEnabledFlag());
				}
				if (criteriaDTO.isInitvalFlag())
				{
					genericReportsFormBean.setInitvalDT(criteriaDTO.isInitvalFlag());
				}
				genericReportsFormBean.setDTRequired(true);
				idList.add(ReportConstants.CRITERIA_DT);
			}
			
			
		
		}
		request.setAttribute("idList", idList);
		genericReportsFormBean.setCriteriaList(criteriaList);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws DPServiceException
	 */
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws DPServiceException
	{
		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("Get id from sess " + sessionContext.getId());
		long id = sessionContext.getId();
		ActionErrors errors = new ActionErrors();
		int reportId = 0;
		List circles = new ArrayList();
		GenericReportsFormBean genericFormBean = (GenericReportsFormBean) form;
		List<GenericReportPararmeterDTO> listReturn = new ArrayList<GenericReportPararmeterDTO>();
		try
		{
			logger.info("from date  :" + request.getAttribute("fromDate"));
			logger.info("to date  :" + request.getAttribute("toDate"));
			String businessCat = genericFormBean.getHiddenBusinesscatSelectIds();
			logger.info("businessCat:::::" + businessCat);
			// added by pratap
			if (businessCat != null)
			{
				if (businessCat.equals(""))
				{
					businessCat = "1,2,3,4";
				}
			}
			// end of adding by pratap
			logger.info("businessCat:::::" + businessCat);
			String tsmIds = genericFormBean.getHiddenTsmSelectIds();
			String distIds = genericFormBean.getHiddenDistSelectIds();
			String circleIds = genericFormBean.getHiddenCircleSelectIds();
			String productType = genericFormBean.getHiddenProductTypeSelectIds();
			String fromDate = genericFormBean.getFromDate();
			String zoneIds = genericFormBean.getHiddenZoneSelectIds();
			// Added by Nehil
			// ptID for Commercial or swap
			String ptID = genericFormBean.getPtID();
			// dtID for SSD or SF
			String dtID = genericFormBean.getDtID();
			logger.info("From Date in action :" + fromDate);
			String toDate = genericFormBean.getToDate();
			logger.info("To Date in action :" + toDate);
			String date = genericFormBean.getDate();

			String stbType = genericFormBean.getHiddenSTBTypeSelectIds();
			String collectionType = genericFormBean.getHiddenCollectionTypeSelectIds();

			String accountTypes = genericFormBean.getAccountType();
			String accountNames = genericFormBean.getAccountName();
			String poStatus = genericFormBean.getHiddenPOStatusSelectIds();
			String pendingAt = genericFormBean.getHiddenPendingAtSelectIds();
			String dcStatus = genericFormBean.getHiddenDCStatusSelectIds();

			System.out.println("Account type"+ accountTypes);
			
			
			String dcNo = genericFormBean.getDcNo();
			String dateOption = genericFormBean.getDateOption();
			String activity = genericFormBean.getActivity();
			System.out
					.println("***************************activity********************" + activity);
			String searchOption = genericFormBean.getSearchOption();
			String searchCriteria = genericFormBean.getSearchCriteria();
			String transferType = genericFormBean.getHiddenTrnsfrTypeSelec();
			String stbStatus = genericFormBean.getHiddenStbStatus();
			String stockType = genericFormBean.getHiddenStockTypeSelectIds();
			String recoPeriod = genericFormBean.getRecoPeriod();

			// System.out.println("recoPeriod::::::::"+recoPeriod);
			logger.info("stb status::" + genericFormBean.getHiddenStbStatus());
			logger.info("product type:::" + genericFormBean.getHiddenProductTypeSelectIds());
			logger.info("genericFormBean.getAccountType():::::" + genericFormBean.getAccountType());

			// genericFormBean.getDtID()

			reportId = genericFormBean.getReportId();
			session.setAttribute("Report" + reportId, "inprogress"); // Added by
			// Neetika
			// to
			// set
			// Reports
			// downloading
			// status
			// for
			// BFR
			// 16
			request.setAttribute("reportId", reportId + "");

			if (genericFormBean.getHiddenCircleSelectIds() == null
					|| genericFormBean.getHiddenCircleSelectIds().equals(""))
			{
				// circleIds = String.valueOf(sessionContext.getCircleId());
				// //to be changed
				int groupId = sessionContext.getGroupId();
				if (groupId == Constants.TM || groupId == Constants.GROUP_ID_ZBM
						|| groupId == Constants.GROUP_ID_ZSM
						|| groupId == Constants.GROUP_ID_CIRCLE_ADMIN)
				{
					circles = utilityAjaxService.getCircles(id);
					// logger.info("circles :::"+circles.size());
					for (int i = 1; i <= circles.size(); i++)
					{
						if (i == circles.size())
						{
							circleIds = circleIds + "" + circles.get(i - 1) + "";
							// logger.info("circles :::"+circleIds);
						}
						else
							circleIds = circleIds + "" + circles.get(i - 1) + ",";

					}
				}
				else
				{
					circleIds = String.valueOf(sessionContext.getCircleId());
				}
				logger.info("Get circles list circleIds not from session context ==  " + circleIds);
			}

			if (genericFormBean.getHiddenDistSelectIds() == null
					|| genericFormBean.getHiddenDistSelectIds().equals(""))
			{
				if (sessionContext.getGroupId() == Constants.DISTIBUTOR)
				{
					distIds = Long.toString(sessionContext.getId());
				}
				else
				{
					distIds = "-1";
				}
			}

			if (genericFormBean.getHiddenTsmSelectIds() == null
					|| genericFormBean.getHiddenTsmSelectIds().equals(""))
			{

				if (sessionContext.getGroupId() == Constants.TM)
				{
					tsmIds = Long.toString(sessionContext.getId());
				}
				else
				{
					tsmIds = "-1";
				}

			}

			GenericReportPararmeterDTO genericDTO = new GenericReportPararmeterDTO();

			genericDTO.setStbStatus(stbStatus);

			genericDTO.setTsmIds(tsmIds);
			genericDTO.setDistIds(distIds);
			genericDTO.setCircleIds(circleIds);
			genericDTO.setProductType(productType);
			genericDTO.setFromDate(fromDate);
			genericDTO.setTodate(toDate);
			if (date != null && !date.equalsIgnoreCase(""))
			{
				genericDTO.setFromDate(date);
			}
			genericDTO.setCollectionType(collectionType);
			genericDTO.setStbType(stbType);
			genericDTO.setGroupId(sessionContext.getGroupId());
			genericDTO.setZoneIds(zoneIds);
			genericDTO.setPtId(ptID);
			genericDTO.setDtId(dtID);

			if (genericFormBean.getHiddenStatusSelectIds() != null)
			{
				String statusArray[] = genericFormBean.getHiddenStatusSelectIds().split(",");
				String status = "";
				for (String string : statusArray)
				{
					status = status + "'" + string + "',";
				}
				status = status.substring(0, status.length() - 1);

				genericDTO.setStatus(status);
			}
			if (dcStatus != null)
			{
				dcStatus = dcStatus.replaceAll(",", "','");
				dcStatus = "'" + dcStatus + "'";
			}
			if (poStatus != null)
			{
				poStatus = poStatus.replaceAll(",", "','");
				poStatus = "'" + poStatus + "'";
			}

			genericDTO.setReportId(reportId);

			genericDTO.setAccountType(accountTypes);
			genericDTO.setAccountName(accountNames);
			genericDTO.setPoStatus(poStatus);
			genericDTO.setPendingAt(pendingAt);
			genericDTO.setDcStatus(dcStatus);
			
			genericDTO.setDcNo(dcNo);
			genericDTO.setDateOption(dateOption);
			genericDTO.setActivity(activity);

			if (reportId == 42)
			{
				genericDTO.setSearchOption(stockType);
			}
			else
			{
				genericDTO.setSearchOption(searchOption);
			}
			// System.out.println("reportId : "+reportId+
			// " genericDTO.getSearchOption : "
			// +genericDTO.getSearchOption());
			genericDTO.setSearchCriteria(searchCriteria);
			genericDTO.setTransferType(transferType);
			genericDTO.setDistData(genericFormBean.getDistData());
			genericDTO.setOtherUserData((genericFormBean.getOtherUserData()));
			genericDTO.setAccountLevel(genericFormBean.getAccountLevel());
			genericDTO.setAccountId(sessionContext.getId());
			genericDTO.setRecoPeriod(recoPeriod);
			genericDTO.setBusinessCategory(businessCat);

			GenericReportsService genericService = GenericReportsServiceImpl.getInstance();
			
			if(reportId == ReportConstants.PENDING_FOR_RETURN_DETAILS_REPORT ||
					reportId == ReportConstants.STB_WISE_SERIALIZED_STOCK_REPORT)
			{
				ResourceBundle rb = ResourceBundle.getBundle("com.ibm.dp.resources.DPResources");
				String configId="";
				if(rb.getKeys().equals("configuration.configId"));
				{
					configId = rb.getString("configuration.configId");
				}
				String numberDays = genericService.getAgeingDays(configId);
			if(numberDays == "")
			{
				return mapping.findForward(ERROR);		
			}
			}

			GenericReportsOutputDto genericOutputDto = genericService.exportToExcel(genericDTO);
	
			
			genericFormBean.setHeaders(genericOutputDto.getHeaders());
			genericFormBean.setOutput(genericOutputDto.getOutput());
			request.setAttribute(OUTPUT, genericOutputDto.getOutput());
			request.setAttribute("header", genericOutputDto.getHeaders());
			if (reportId == 43)
			{
				genericFormBean.setReportRecoDataList((genericOutputDto.getRecoList()));
				// System.out.println("---->>>"+genericFormBean.
				// getReportRecoDataList().size());
				request.setAttribute("reportRecoDataList", genericOutputDto.getRecoList());
			}

			// System.out.println("In action class:::"+genericOutputDto.
			// getRecoList().size());
			// ReportDetailDTO reportDetail =
			// genericService.getReportDetails(reportId);
			// genericFormBean.setReportLabel(reportDetail.getReportLabel());
		

		}
		catch (DAOException se)
		{
			logger.error("EXCEPTION OCCURED ::  " + se.getMessage());
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		catch (DPServiceException se)
		{
			logger.info("EXCEPTION OCCURED ::  " + se.getMessage());
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		catch (Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}

		/*
		 * if(reportId == 50) { return mapping.findForward(INIT_EXCEL_DEBIT); }
		 */

		if (reportId != 43)
		{
			return mapping.findForward(INIT_EXCEL);
		}
		else
		{
			return mapping.findForward(INIT_EXCEL_RECO);
		}
	}

	public String getLastSchedulerDate(int groupId, String distData, String otherData)
			throws DPServiceException, DAOException
	{
		GenericReportsService genericService = GenericReportsServiceImpl.getInstance();

		return genericService.getLastSchedulerDate(groupId, distData, otherData);
	}

	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// System.out.println("inside getReportDownloadStatus");
		try
		{
			HttpSession session = request.getSession();
			String reportId = request.getParameter("reportId");
			String status = (String) session.getAttribute("Report" + reportId);
			String result = "";
			if (status != null)
				result = status;
			// logger.info("result:" + result);

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(result);
			writer.flush();
			out.flush();
			// logger.info("out now getReportDownloadStatus");

		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while getReportDownloadStatus  ::  " + e.getMessage());
		}
		return null;
	}

	private int getDateValidation(int groupId, String distData, String otherData)
	{
		if (groupId != Constants.DISTIBUTOR && otherData.equals("0"))
		{
			return 0;
		}
		else if (groupId == Constants.DISTIBUTOR && distData.equals("0"))
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}

	private int getMsgValidation(int groupId, String distData, String otherData)
	{
		if (groupId != Constants.DISTIBUTOR && otherData.equals("0"))
		{
			return 0;
		}
		else if (groupId == Constants.DISTIBUTOR && distData.equals("0"))
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}
}