package com.ibm.hbo.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.hbo.dto.HBOReportDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOReportFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBOReportsService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.HBOReportServiceImpl;
import com.ibm.hbo.services.impl.HBORequisitionServiceImpl;

/**
 * @author Parul 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOReportsAction extends DispatchAction {
	/*
	 * Logger for the class.
	 */
	private static final Logger logger;

	static {
		logger = Logger.getLogger(HBOReportsAction.class);
	}

	public ActionForward getData(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		HBOUserBean userBean = (HBOUserBean) session.getAttribute("USER_INFO");
		String actorId = null;
		String userId = userBean.getUserLoginId();
		HBOReportFormBean repFormBean = (HBOReportFormBean) form;
		actorId = userBean.getActorId();
		String message = "";
		HBOMasterService mstrService = new HBOMasterServiceImpl();
		ArrayList prodList = null;

		int reportId = Integer.parseInt(request.getParameter("reportId"));

		// Report  : Added By parul for Stock In hand Report
		if (reportId == 1) {
			repFormBean.setReport("CentralSIH");
			request.setAttribute("Report", repFormBean.getReport());
			prodList = null;
			ArrayList circleList = null;
			prodList = mstrService.getMasterList(userId, "product", null);
			if (actorId.equalsIgnoreCase("1") || actorId.equalsIgnoreCase("3"))
				circleList = mstrService.getMasterList(userId, "circle", null);
			else
				circleList =
					mstrService.getMasterList(
						userId,
						"CentralSIH",
						"WM.warehouse_id =" + userBean.getWarehouseID());

			repFormBean.setProdList(prodList);
			repFormBean.setCircleLIst(circleList);
			//forward = mapping.findForward("success");						
			message = "success";
		}
		//		Report  : Added By parul for Airtel Distributor  Sales
		if (reportId == 6) {
			repFormBean.setReport("ADSales");
			request.setAttribute("Report", repFormBean.getReport());
			prodList = null;
			ArrayList circleList = null;
			prodList = mstrService.getMasterList(userId, "product", null);
			if (actorId.equalsIgnoreCase("1"))
				circleList = mstrService.getMasterList(userId, "circle", null);
			else
				circleList =
					mstrService.getMasterList(
						userId,
						"ADSales",
						"WM.warehouse_id =" + userBean.getWarehouseID());

			repFormBean.setProdList(prodList);
			repFormBean.setCircleLIst(circleList);
			//forward = mapping.findForward("success");	
			message = "success";
		}
		//		Report  : Added By parul for Product Wise Stock Sales Report
		if (reportId == 10) {
			repFormBean.setReport("ProductWiseStockSale");
			request.setAttribute("Report", repFormBean.getReport());
			prodList = null;
			ArrayList circleList = null;
			prodList = mstrService.getMasterList(userId, "product", null);
			if (actorId.equalsIgnoreCase("1"))
				circleList = mstrService.getMasterList(userId, "circle", null);
			else
				circleList =
					mstrService.getMasterList(
						userId,
						"ProductWiseStockSale",
						"WM.warehouse_id =" + userBean.getWarehouseID());
			logger.info("circleList.size-----" +circleList.size());
			repFormBean.setProdList(prodList);
			repFormBean.setCircleLIst(circleList);
			//forward = mapping.findForward("success");
			message = "success";
		}

		if (reportId == 2) {
			String condition = " WAM.ROLE_ID = 3 ";
			repFormBean.setReport("viewRequisition");

			ArrayList productList = null;

			ArrayList centralWarehouseList =
				mstrService.getMasterList(userId, "warehouse", condition);
			repFormBean.setWarehouseList(centralWarehouseList);
			message = "success1";
		}
		if (reportId == 3) {

			repFormBean.setReport("viewRequisitionSummary");
			ArrayList productList =
				mstrService.getMasterList(userId, "product", null);
			repFormBean.setProdList(productList);
			message = "success2";
		}
		if (reportId == 11) {
			repFormBean.setReport("primarySalesReport");
			ArrayList productList =
				mstrService.getMasterList(userId, "product", null);
			repFormBean.setProdList(productList);
			message = "success3";
		}
		if (reportId == 12) {
			repFormBean.setReport("imeaReport");
			prodList = mstrService.getMasterList(userId, "product", null);
			repFormBean.setProdList(prodList);
			message = "success4";
		}
		if (reportId == 91) {
			repFormBean.setReport("simReport");
			message = "success5";
		}
		// Reports added by Aditya 
		if (reportId == 13) {
			repFormBean.setReport("viewDamged");
			prodList = mstrService.getMasterList(userId, "product", null);
			logger.info("PRODUCT LIST********" + prodList.size());
			repFormBean.setProdList(prodList);
			logger.info(
				"repFormBean.get......" + repFormBean.getProdList().size());
			message = "successReport13";
		}
		if (reportId == 14) {

			repFormBean.setReport("minReorder");
			prodList = mstrService.getMasterList(userId, "product", null);
			logger.info("PRODUCT LIST********" + prodList.size());
			repFormBean.setProdList(prodList);
			//logger.info("repFormBean.get"+repFormBean.getProdList().size());
			message = "successReport14";
		}
		if (reportId == 8) {

			repFormBean.setReport("projection");
			prodList = mstrService.getMasterList(userId, "product", null);
			logger.info("PRODUCT LIST********" + prodList.size());
			repFormBean.setProdList(prodList);
			logger.info(
				"repFormBean.get" + repFormBean.getProdList().size());
			message = "successReport8";
		}
		if (reportId == 82) {

			repFormBean.setReport("projecAnnual");
			prodList = mstrService.getMasterList(userId, "product", null);
			logger.info("PRODUCT LIST********" + prodList.size());
			repFormBean.setProdList(prodList);
			logger.info(
				"repFormBean.get" + repFormBean.getProdList().size());
			message = "successReport82";
		}
		if (reportId == 9) {

			repFormBean.setReport("variance");
			prodList = mstrService.getMasterList(userId, "product", null);
			logger.info("variance LIST********" + prodList.size());
			repFormBean.setProdList(prodList);
			logger.info(
				"variance repFormBean.get" + repFormBean.getProdList().size());
			message = "successReport9";
		}

		forward = mapping.findForward(message);
		return (forward);
	}

	public ActionForward getReportData(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionForward forward = new ActionForward();
		String message = "";
		HBOUserBean userBean = (HBOUserBean) session.getAttribute("USER_INFO");
		ArrayList reportData = null;
		ArrayList reportData1 = null;
		HBOReportFormBean repFormBean = (HBOReportFormBean) form;
		HBOReportsService repService = new HBOReportServiceImpl();
		HBOReportDTO reportDTO = new HBOReportDTO();
		
		if (("projecAnnual".equalsIgnoreCase(repFormBean.getReport()))
							&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
							repFormBean.setMcode(request.getParameter("mcode"));
						}
		if (("minReorder".equalsIgnoreCase(repFormBean.getReport()))
					&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
					repFormBean.setMcode(request.getParameter("mcode"));
				}
			
		if (("variance".equalsIgnoreCase(repFormBean.getReport()))
						&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
						repFormBean.setMcode(request.getParameter("mcode"));
			}
		if (("projection".equalsIgnoreCase(repFormBean.getReport()))
			&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
			repFormBean.setMcode(request.getParameter("mcode"));
		}
		if (("imeaReport".equalsIgnoreCase(repFormBean.getReport()))
			&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
			repFormBean.setMcode(request.getParameter("mcode"));
			repFormBean.setFromDate(request.getParameter("dt1"));
			repFormBean.setToDate(request.getParameter("dt2"));
		}
		
		if (("viewRequisition".equalsIgnoreCase(repFormBean.getReport()))
			&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
			repFormBean.setMcode(request.getParameter("warehouseId"));
			repFormBean.setFromDate(request.getParameter("dt1"));
			repFormBean.setToDate(request.getParameter("dt2"));
		}
		if (("viewRequisitionSummary".equalsIgnoreCase(repFormBean.getReport()))
			&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
			repFormBean.setMcode(request.getParameter("mcode"));
			repFormBean.setFromDate(request.getParameter("dt1"));
			repFormBean.setToDate(request.getParameter("dt2"));
		}
		if (("primarySalesReport".equalsIgnoreCase(repFormBean.getReport()))
					&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
					repFormBean.setMcode(request.getParameter("mcode"));
					repFormBean.setFromDate(request.getParameter("dt1"));
					repFormBean.setToDate(request.getParameter("dt2"));
				}
		if (("viewDamged".equalsIgnoreCase(repFormBean.getReport()))
			&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
			repFormBean.setMcode(request.getParameter("mcode"));
			repFormBean.setFromDate(request.getParameter("dt1"));
			repFormBean.setToDate(request.getParameter("dt2"));
		}
		if (("CentralSIH".equalsIgnoreCase(repFormBean.getReport()))
			&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
			repFormBean.setMcode(request.getParameter("mcode"));
			repFormBean.setCircleId(request.getParameter("circle"));
		}
		if (("ADSales".equalsIgnoreCase(repFormBean.getReport()))
				&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
				repFormBean.setMcode(request.getParameter("mcode"));
				repFormBean.setCircleId(request.getParameter("circle"));
			}
		if (("ProductWiseStockSale".equalsIgnoreCase(repFormBean.getReport()))
				&& (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
				repFormBean.setMcode(request.getParameter("mcode"));
				repFormBean.setCircleId(request.getParameter("circle"));
			}	
		BeanUtils.copyProperties(reportDTO, repFormBean);

		if (("CentralSIH".equalsIgnoreCase(repFormBean.getReport()))
			|| ("CentralSIH".equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {

				reportData = repService.getReportData(userBean, 1, reportDTO);
				logger.info(
					"reportData.size()>>>>>>>>>>>>>>>>>>>>>>>>>"
						+ reportData.size());
			}

			message = "successReport1";
		} else if (
			("ADSales".equalsIgnoreCase(repFormBean.getReport()))
				|| ("ADSales".equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
				reportData = repService.getReportData(userBean, 6, reportDTO);
			}
			message = "successReport1";
		} else if (
			("ProductWiseStockSale".equalsIgnoreCase(repFormBean.getReport()))
				|| ("ProductWiseStockSale"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
				reportData = repService.getReportData(userBean, 10, reportDTO);
			}
			message = "successReport1";
		}

		// Reports For Sidharth

		else if (
			("viewRequisition".equalsIgnoreCase(repFormBean.getReport()))
				|| ("viewRequisition"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
				//logger.info("here>>>>>>>>>>>>>>>>>");
				reportData = repService.getReportData(userBean, 2, reportDTO);

			}
			message = "successReport2";

		} else if (
			("viewRequisitionSummary"
				.equalsIgnoreCase(repFormBean.getReport()))
				|| ("viewRequisitionSummary"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)) {
				logger.info("here>>>>>>>>>>>>>>>>>");
				//reportType = "viewRequisitionSummary";
				reportData = repService.getReportData(userBean, 3, reportDTO);
				if (mapping.getPath().equalsIgnoreCase("/Report")) {
					//response.setContentType("text/html");
					//request.setAttribute("repType","j");
					//request.setAttribute("reportId","3");
				} else if (
					mapping.getPath().equalsIgnoreCase("/reportExcel")) {
					response.setContentType("application/vnd.ms-excel");
					request.setAttribute("repType", "j");
				}
				logger.info("Back----------" + reportData.size());

			}
			message = "successReport3";
		} else if (
			("primarySalesReport".equalsIgnoreCase(repFormBean.getReport()))
				|| ("primarySalesReport"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
				logger.info("primarysaleaction>>>>>>>>>");
				reportData = repService.getReportData(userBean, 111, reportDTO);
				reportData1 =
					repService.getReportData(userBean, 112, reportDTO);
				logger.info("Back----------" + reportData.size());
			}
			message = "successReport4";
		} else if (
			("imeaReport".equalsIgnoreCase(repFormBean.getReport()))
				|| ("imeaReport"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
				logger.info("imeareport>>>>>>>>>");
				reportDTO.setWarehouse_to(userBean.getWarehouseID());
				reportData = repService.getReportData(userBean, 12, reportDTO);
				logger.info("Back----------" + reportData.size());
			}
			message = "successReport5";
		} else if (
			("simReport".equalsIgnoreCase(repFormBean.getReport()))
				|| ("simReport"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {
				logger.info(
					"simreport>>>>>>>>>" + repFormBean.getReport());
				reportData = repService.getReportData(userBean, 91, reportDTO);
				logger.info(
					"reportData.size()>>>>>>>>>" + reportData.size());
			}
			message = "successReport6";
		} // By Aditya 
		else if (
			(repFormBean.getReport().equalsIgnoreCase("viewDamged"))
				|| ("viewDamged"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {

				reportData = repService.getReportData(userBean, 13, reportDTO);
				logger.info(
					"viewDamged...reportData.size......." + reportData.size());
			}

			message = "successReport13";

		} else if (
			(repFormBean.getReport().equalsIgnoreCase("minReorder"))
				|| ("minReorder"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {

				reportData = repService.getReportData(userBean, 14, reportDTO);
				logger.info(
					"minReorder.....reportData.size----------"
						+ reportData.size());
			}

			message = "successReport14";

		} else if (
			(repFormBean.getReport().equalsIgnoreCase("projection"))
				|| ("projection"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {

				reportData = repService.getReportData(userBean, 81, reportDTO);
				logger.info(
					"reportData-----------------" + reportData.size());
				int totalCurrMonth = 0;
				int totalNextMonth = 0;
				int totalNxtnxtMonth = 0;
				String oldmcode = "";
				String newmcode = "";
				ArrayList total = new ArrayList();
				for (int i = 0; i < reportData.size(); i++) {
					reportDTO = (HBOReportDTO) reportData.get(i);
					newmcode = reportDTO.getMcode();

					if (newmcode.equalsIgnoreCase(oldmcode)
						|| oldmcode.equalsIgnoreCase("")) {

						totalCurrMonth
							+= Integer.parseInt(reportDTO.getCurrMonth());
						totalNextMonth
							+= Integer.parseInt(reportDTO.getNextMonth());
						totalNxtnxtMonth
							+= Integer.parseInt(reportDTO.getNxtnxtMonth());
						oldmcode = newmcode;

					} else {
						HBOReportDTO reportDTO2 = new HBOReportDTO();
						reportDTO2.setTotalCurrMonth(totalCurrMonth);
						reportDTO2.setTotalNextMonth(totalNextMonth);
						reportDTO2.setTotalNxtnxtMonth(totalNxtnxtMonth);
						total.add(reportDTO2);

						totalCurrMonth = 0;
						totalNextMonth = 0;
						totalNxtnxtMonth = 0;
						totalCurrMonth =
							Integer.parseInt(reportDTO.getCurrMonth());
						totalNextMonth =
							Integer.parseInt(reportDTO.getNextMonth());
						totalNxtnxtMonth =
							Integer.parseInt(reportDTO.getNxtnxtMonth());
						oldmcode = newmcode;

					}
				}
				HBOReportDTO reportDTO2 = new HBOReportDTO();
				reportDTO2.setTotalCurrMonth(totalCurrMonth);
				reportDTO2.setTotalNextMonth(totalNextMonth);
				reportDTO2.setTotalNxtnxtMonth(totalNxtnxtMonth);
				total.add(reportDTO2);
				
				request.setAttribute("total", total);
				repFormBean.setTotal(total);
				logger.info("total.size----------" + total.size());

			}

			message = "successReport8";

		} else if (
			(repFormBean.getReport().equalsIgnoreCase("variance"))
				|| ("variance"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {

				reportData = repService.getReportData(userBean, 9, reportDTO);
				logger.info(
					"variance---reportData.size---" + reportData.size());
			}

			message = "successReport9";

		} else if (
			(repFormBean.getReport().equalsIgnoreCase("projecAnnual"))
				|| ("projecAnnual"
					.equalsIgnoreCase(request.getParameter("report")))) {
			if ((mapping.getPath() != null)
				&& (mapping.getPath().equalsIgnoreCase("/Report"))
				|| (mapping.getPath().equalsIgnoreCase("/reportExcel"))) {

				reportData = repService.getReportData(userBean, 82, reportDTO);
				logger.info(
					"projecAnnual--reportData.size---" + reportData.size());
			}

			message = "successReport82";

		}
		if(reportData.size()==0){
			messages.add("NO_RECORD",new ActionMessage("no.record.found"));
			saveMessages(request, (ActionMessages) messages);
		}
		repFormBean.setReportData(reportData);
		request.setAttribute("reportlist", reportData);
		forward = mapping.findForward(message);
		return (forward);

	

	}

}