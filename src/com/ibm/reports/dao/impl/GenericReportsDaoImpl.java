package com.ibm.reports.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.dp.reports.common.ReportConstants;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.reports.dao.GenericReportsDao;
import com.ibm.reports.dto.CriteriaDTO;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.dto.ReportDetailDTO;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class GenericReportsDaoImpl extends BaseDaoRdbms implements GenericReportsDao
{

	private Logger logger = Logger.getLogger(GenericReportsDaoImpl.class.getName());

	public static final String SQL_GET_REPORT_DETAIL = "SELECT report_id, report_name, report_label, proc_name, active_flag , DIST_DATA , OTHER_USER_DATA  "
			+ "FROM REPORT_MASTER WHERE report_id = ?  with ur";// Reports with
	// ur IN2046752

	public static final String SQL_GET_REPORT_CRITERIA = "SELECT CM.criteria_id, CM.criteria_name, CM.criteria_label, CM.mandatory_flag, CM.type,RCM.initval_flag,RCM.enabled_flag"
			+ " FROM REPORT_CRITERIA_MASTER CM, REPORT_CRITERIA_MAP RCM"
			+ " WHERE CM.criteria_id = RCM.criteria_id AND RCM.report_id = ? and RCM.group_id=? order by RCM.seq with ur"; // Reports
	// with
	// ur
	// IN2046752

	// public static final String SQL_PO_STATUS_LIST =
	// "Select ID,VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=2 with ur"
	// ;

	// public static final String SQL_PENDING_AT_LIST =
	// "Select ID,VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=17 with ur"
	// ;

	public static final String SQL_CONFIGURATION_DETAILS = "Select ID,VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID= ? and ID not in (8,9,12) with ur";

	public static final String SQL_COLLECTION_MST = DBQueries.SQL_COLLECTION_MST;

	public static final String SQL_COLLECTION_MST_WO_CHURN = DBQueries.SQL_COLLECTION_MST_WO_CHURN;

	public static final String SQL_SCH_DATE = "select max(run_date) as SCH_DATE from DP_DATA_TRANS_SCH_LOG with ur";

	private static GenericReportsDao genericReportsDao = new GenericReportsDaoImpl();

	// private GenericReportsDaoImpl(){}
	public static GenericReportsDao getInstance()
	{
		return genericReportsDao;
	}

	/*
	 * public GenericReportsDaoImpl(Connection connection) { super(connection);
	 * }
	 */

	public List<CriteriaDTO> getReportCriterias(int reportId, int groupId) throws DAOException
	{

		List<CriteriaDTO> criteriaListDTO = new ArrayList<CriteriaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		CriteriaDTO criteriaDto = null;
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getMISReportDBConnection();

			pstmt = connection.prepareStatement(SQL_GET_REPORT_CRITERIA);
			pstmt.setInt(1, reportId);
			pstmt.setInt(2, groupId);
			rset = pstmt.executeQuery();
			criteriaListDTO = new ArrayList<CriteriaDTO>();

			while (rset.next())
			{
				criteriaDto = new CriteriaDTO();
				criteriaDto.setCriteriaId(rset.getInt("criteria_id"));
				criteriaDto.setCriteriaName(rset.getString("criteria_name"));
				criteriaDto.setCriteriaLabel(rset.getString("criteria_label"));
				criteriaDto.setMandatory(rset.getBoolean("mandatory_flag"));
				criteriaDto.setType(rset.getString("type"));
				if (rset.getString("initval_flag").equalsIgnoreCase("Y"))
					criteriaDto.setInitvalFlag(true);
				else
					criteriaDto.setInitvalFlag(false);

				if (rset.getString("enabled_flag").equalsIgnoreCase("Y"))
					criteriaDto.setEnabledFlag(false);
				else
					criteriaDto.setEnabledFlag(true);

				criteriaListDTO.add(criteriaDto);

			}

		}
		catch (SQLException sqe)
		{
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return criteriaListDTO;
	}

	public ReportDetailDTO getReportDetails(int reportId) throws DAOException
	{
		ReportDetailDTO reportDetailDTO = new ReportDetailDTO();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;

		try
		{
			connection = DBConnectionManager.getMISReportDBConnection();

			pstmt = connection.prepareStatement(SQL_GET_REPORT_DETAIL);
			pstmt.setInt(1, reportId);
			rset = pstmt.executeQuery();

			
			
			
			while (rset.next())
			{
				reportDetailDTO.setReportId(rset.getInt("report_id"));
				reportDetailDTO.setReportName(rset.getString("report_name"));
				System.out.println("Report Name in dataBase"+rset.getString("report_name"));
				reportDetailDTO.setReportLabel(rset.getString("report_label"));
				System.out.print("Report Label in dataBase"+rset.getString("report_label"));
				reportDetailDTO.setReportProcName(rset.getString("proc_name"));
				reportDetailDTO.setActive(rset.getBoolean("active_flag"));
				reportDetailDTO.setDistData(rset.getString("DIST_DATA"));
				reportDetailDTO.setOtherUserData(rset.getString("OTHER_USER_DATA"));

			}
			
		}
		catch (SQLException sqe)
		{
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return reportDetailDTO;
	}

	protected GenericReportsOutputDto getResultSet(ResultSet rs,
			GenericReportsOutputDto genericOutputDto, int report_id) throws DAOException
	{
		try
		{
			if (rs == null)
			{
				throw new DAOException(
						"Exception Occured!! ResultSet is NULL!!! See below for SQLException message!");
			}

			if (report_id != 43)
			{
				ResultSetMetaData md = rs.getMetaData();
				int count = md.getColumnCount();
				List<String> outputHeaderList = new ArrayList<String>();
				outputHeaderList.add("S. NO.");
				List<String[]> outputList = new ArrayList<String[]>();
				for (int i = 1; i <= count; i++)
				{
					String columnHeader = md.getColumnLabel(i);
					outputHeaderList.add(columnHeader.replaceAll("_", " "));
				}
				
				genericOutputDto.setHeaders(outputHeaderList);
				while (rs.next())
				{
					String[] arr = new String[count];
					for (int i = 1; i <= count; i++)
					{
						arr[i - 1] = Utility.replaceNullBySpace(rs.getString(i));
					}
					outputList.add(arr);
				}
				genericOutputDto.setOutput(outputList);
			}
			else
			{
				genericOutputDto = setDTOForRecoDetail(rs, genericOutputDto);
			}

		}
		catch (SQLException sqe)
		{
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		return genericOutputDto;
	}

	public List<DpProductCategoryDto> getTransactionType(int reportId) throws DAOException
	{

		List<DpProductCategoryDto> transactionList = new ArrayList<DpProductCategoryDto>();
		// List<DpProductCategoryDto> nonSwapProductList = new
		// ArrayList<DpProductCategoryDto>();
		try
		{
			transactionList = DropDownUtility.getInstance().getTransactionType(reportId);
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
		}

		return transactionList;

	}

	public List<DpProductCategoryDto> getProductList(int reportId) throws DAOException
	{
		List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();
		// List<DpProductCategoryDto> nonSwapProductList = new
		// ArrayList<DpProductCategoryDto>();
		try
		{
			productList = DropDownUtility.getInstance().getProductCategoryLst();

			/*
			 * if(reportId ==
			 * ReportConstants.SERIALIZED_STOCK_CONSUMPTION_REPORT || reportId
			 * == ReportConstants.NON_SERIALIZED_STOCK_CONSUMPTION_REPORT ||
			 * reportId ==
			 * ReportConstants.SERIALIZED_STOCK_CONSUMPTION_RETRIALS_REPORT ||
			 * reportId ==
			 * ReportConstants.NON_SERIALIZED_STOCK_CONSUMPTION_RETRIALS_REPORT)
			 * { for (DpProductCategoryDto dto : productList) {
			 * if(dto.getProductCategory().indexOf("SWAP") == -1) {
			 * nonSwapProductList.add(dto); } } return nonSwapProductList; }
			 */
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
		}

		return productList;
	}

	public List<CollectionReportDTO> getCollectionTypeMaster(int reportId) throws DAOException
	{
		List<CollectionReportDTO> collectionListDTO = new ArrayList<CollectionReportDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		CollectionReportDTO collectionDto = null;

		try
		{
			connection = DBConnectionManager.getDBConnection();
			/*
			 * if(reportId == 7) { pstmt =
			 * connection.prepareStatement(SQL_COLLECTION_MST_WO_CHURN); } else
			 * {
			 */
			pstmt = connection.prepareStatement(SQL_COLLECTION_MST);
			// }

			rset = pstmt.executeQuery();
			collectionListDTO = new ArrayList<CollectionReportDTO>();

			while (rset.next())
			{
				collectionDto = new CollectionReportDTO();
				collectionDto.setCollectionId(rset.getString("COLLECTION_ID"));
				collectionDto.setCollectionName(rset.getString("COLLECTION_NAME"));

				collectionListDTO.add(collectionDto);
			}

		}
		catch (SQLException sqe)
		{
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return collectionListDTO;
	}

	public List<GenericOptionDTO> getDateOptions(int reportId) throws DAOException
	{
		List<GenericOptionDTO> dateOptions = new ArrayList<GenericOptionDTO>();

		if (reportId == ReportConstants.INTER_SSD_STOCK_TANSFER_REPORT)
		{
			GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
			genericOptionDTO1.setId("-1");
			genericOptionDTO1.setValue("-Blank-");

			GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
			genericOptionDTO2.setId("INITIATION_DATE");
			genericOptionDTO2.setValue("Initiation Date");

			GenericOptionDTO genericOptionDTO3 = new GenericOptionDTO();
			genericOptionDTO3.setId("TRANSFER_DATE");
			genericOptionDTO3.setValue("Transfer Date");

			GenericOptionDTO genericOptionDTO4 = new GenericOptionDTO();
			genericOptionDTO4.setId("ACCEPTANCE_DATE");
			genericOptionDTO4.setValue("Acceptance Date");

			dateOptions.add(genericOptionDTO1);
			dateOptions.add(genericOptionDTO2);
			dateOptions.add(genericOptionDTO3);
			dateOptions.add(genericOptionDTO4);

		}
		else if (reportId == ReportConstants.PO_STATUS_REPORT
				|| reportId == ReportConstants.PO_DETAIL_REPORT
				|| reportId == ReportConstants.PO_TAT_REPORT)
		{
			GenericOptionDTO genericOptionDTO5 = new GenericOptionDTO();
			genericOptionDTO5.setId("-1");
			genericOptionDTO5.setValue("-Blank-");

			GenericOptionDTO genericOptionDTO6 = new GenericOptionDTO();
			genericOptionDTO6.setId("PO_CREATE_DATE");
			genericOptionDTO6.setValue("PO Create Date");

			GenericOptionDTO genericOptionDTO7 = new GenericOptionDTO();
			genericOptionDTO7.setId("DISPATCH_DATE");
			genericOptionDTO7.setValue("Dispatch Date");

			GenericOptionDTO genericOptionDTO8 = new GenericOptionDTO();
			genericOptionDTO8.setId("PO_ACCEPTANCE_DATE");
			genericOptionDTO8.setValue("PO Acceptance Date");

			dateOptions.add(genericOptionDTO5);
			dateOptions.add(genericOptionDTO6);
			dateOptions.add(genericOptionDTO7);
			dateOptions.add(genericOptionDTO8);

		}
		else if (reportId == ReportConstants.DC_STATUS_DETAILS_REPORT
				|| reportId == ReportConstants.DC_STATUS_REVERSE_REPORT)
		{

			GenericOptionDTO genericOptionDTO11 = new GenericOptionDTO();
			genericOptionDTO11.setId("-1");
			genericOptionDTO11.setValue("-Blank-");

			GenericOptionDTO genericOptionDTO9 = new GenericOptionDTO();
			genericOptionDTO9.setId("DC_DATE");
			genericOptionDTO9.setValue("DC Date");

			GenericOptionDTO genericOptionDTO10 = new GenericOptionDTO();
			genericOptionDTO10.setId("WH_REC_DATE");
			genericOptionDTO10.setValue("WH Receive Date");

			dateOptions.add(genericOptionDTO11);
			dateOptions.add(genericOptionDTO9);
			dateOptions.add(genericOptionDTO10);

		}
		else if (reportId == ReportConstants.SELF_REPAIR_REPORT)
		{

			GenericOptionDTO genericOptionDTO11 = new GenericOptionDTO();
			genericOptionDTO11.setId("-1");
			genericOptionDTO11.setValue("-Blank-");

			GenericOptionDTO genericOptionDTO9 = new GenericOptionDTO();
			genericOptionDTO9.setId("INV_DATE");
			genericOptionDTO9.setValue("Inventory Change Date");

			GenericOptionDTO genericOptionDTO10 = new GenericOptionDTO();
			genericOptionDTO10.setId("COLL_DATE");
			genericOptionDTO10.setValue("Collection Date");

			GenericOptionDTO genericOptionDTO12 = new GenericOptionDTO();
			genericOptionDTO12.setId("REP_DATE");
			genericOptionDTO12.setValue("Repair Date");

			dateOptions.add(genericOptionDTO11);
			dateOptions.add(genericOptionDTO9);
			dateOptions.add(genericOptionDTO10);
			dateOptions.add(genericOptionDTO12);
			return dateOptions;
		}

		else if (reportId == ReportConstants.INVENTORY_CHANGE_REPORT)
		{

			GenericOptionDTO genericOptionDTO11 = new GenericOptionDTO();
			genericOptionDTO11.setId("-1");
			genericOptionDTO11.setValue("-Blank-");

			GenericOptionDTO genericOptionDTO9 = new GenericOptionDTO();
			genericOptionDTO9.setId("INV_DATE");
			genericOptionDTO9.setValue("Inventory Change Date");

			GenericOptionDTO genericOptionDTO10 = new GenericOptionDTO();
			genericOptionDTO10.setId("COLL_DATE");
			genericOptionDTO10.setValue("Collection Date");

			dateOptions.add(genericOptionDTO11);
			dateOptions.add(genericOptionDTO9);
			dateOptions.add(genericOptionDTO10);

		}
		return dateOptions;
	}

	// added by aman for distributor activity

	public List<GenericOptionDTO> getActivityOptions(int reportId) throws DAOException
	{

		List<GenericOptionDTO> activityOptions = new ArrayList<GenericOptionDTO>();

		GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
		genericOptionDTO1.setId("-1");
		genericOptionDTO1.setValue("-Blank-");

		GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
		genericOptionDTO2.setId("PURCHASE");
		genericOptionDTO2.setValue("Purchase");

		GenericOptionDTO genericOptionDTO3 = new GenericOptionDTO();
		genericOptionDTO3.setId("SALE");
		genericOptionDTO3.setValue("Sale");

		GenericOptionDTO genericOptionDTO4 = new GenericOptionDTO();
		genericOptionDTO4.setId("ACTIVATION");
		genericOptionDTO4.setValue("Activation");

		GenericOptionDTO genericOptionDTO5 = new GenericOptionDTO();
		genericOptionDTO5.setId("INVENTORY_CHANGE");
		genericOptionDTO5.setValue("Inventory Change");

		activityOptions.add(genericOptionDTO1);
		activityOptions.add(genericOptionDTO2);
		activityOptions.add(genericOptionDTO3);
		activityOptions.add(genericOptionDTO4);
		activityOptions.add(genericOptionDTO5);

		// System.out.println("**************************************"+
		// activityOptions);

		return activityOptions;

	}

	// end of changes by aman

	public List<GenericOptionDTO> getPOStatusList() throws DAOException
	{
		return getConfigurationDetails(2);
	}

	public List<GenericOptionDTO> getSearchOptions(int reportId) throws DAOException
	{
		List<GenericOptionDTO> poSearchOptions = new ArrayList<GenericOptionDTO>();
		if (reportId == ReportConstants.PO_STATUS_REPORT
				|| reportId == ReportConstants.PO_TAT_REPORT
				|| reportId == ReportConstants.PO_DETAIL_REPORT)
		{
			GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
			genericOptionDTO1.setId("-1");
			genericOptionDTO1.setValue("-Blank-");

			GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
			genericOptionDTO2.setId("PR_NO");
			genericOptionDTO2.setValue("PR No.");

			GenericOptionDTO genericOptionDTO3 = new GenericOptionDTO();
			genericOptionDTO3.setId("PO_NO");
			genericOptionDTO3.setValue("PO No.");

			GenericOptionDTO genericOptionDTO4 = new GenericOptionDTO();
			genericOptionDTO4.setId("DC_NO");
			genericOptionDTO4.setValue("DC No.");

			poSearchOptions.add(genericOptionDTO1);
			poSearchOptions.add(genericOptionDTO2);
			poSearchOptions.add(genericOptionDTO3);
			poSearchOptions.add(genericOptionDTO4);
		}
		else if (reportId == ReportConstants.ACCOUNT_DETAILS_REPORT)
		{
			GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
			genericOptionDTO1.setId("-1");
			genericOptionDTO1.setValue("-Blank-");

			GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
			genericOptionDTO2.setId("LOGIN");
			genericOptionDTO2.setValue("Login Id");

			GenericOptionDTO genericOptionDTO3 = new GenericOptionDTO();
			genericOptionDTO3.setId("ACCOUNT_NAME");
			genericOptionDTO3.setValue("Account Name");

			GenericOptionDTO genericOptionDTO4 = new GenericOptionDTO();
			genericOptionDTO4.setId("MOBILE_NO");
			genericOptionDTO4.setValue("Mobile No.");

			GenericOptionDTO genericOptionDTO5 = new GenericOptionDTO();
			genericOptionDTO5.setId("EMAIL_ID");
			genericOptionDTO5.setValue("Email Id");

			GenericOptionDTO genericOptionDTO6 = new GenericOptionDTO();
			genericOptionDTO6.setId("PIN");
			genericOptionDTO6.setValue("Pin Code");

			GenericOptionDTO genericOptionDTO7 = new GenericOptionDTO();
			genericOptionDTO7.setId("CITY");
			genericOptionDTO7.setValue("City");

			poSearchOptions.add(genericOptionDTO1);
			poSearchOptions.add(genericOptionDTO2);
			poSearchOptions.add(genericOptionDTO3);
			poSearchOptions.add(genericOptionDTO4);
			poSearchOptions.add(genericOptionDTO5);
			poSearchOptions.add(genericOptionDTO6);
			poSearchOptions.add(genericOptionDTO7);

		}

		return poSearchOptions;
	}

	public List<GenericOptionDTO> getPendingOptions(int reportId) throws DAOException
	{
		return getConfigurationDetails(17);
	}

	public List<GenericOptionDTO> getTransferTypeOptions(int reportId) throws DAOException
	{
		List<GenericOptionDTO> transferTypeOptions = new ArrayList<GenericOptionDTO>();

		GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
		genericOptionDTO1.setId("0");
		genericOptionDTO1.setValue("Received");

		GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
		genericOptionDTO2.setId("1");
		genericOptionDTO2.setValue("Sent");

		GenericOptionDTO genericOptionDTO3 = new GenericOptionDTO();
		genericOptionDTO3.setId("2");
		genericOptionDTO3.setValue("Both");

		transferTypeOptions.add(genericOptionDTO1);
		transferTypeOptions.add(genericOptionDTO2);
		transferTypeOptions.add(genericOptionDTO3);

		return transferTypeOptions;
	}

	public List<GenericOptionDTO> getSTBStatusList(int reportId) throws DAOException
	{
		List<GenericOptionDTO> stbStatusList = new ArrayList<GenericOptionDTO>();
		if (reportId == ReportConstants.STB_WISE_SERIALIZED_STOCK_REPORT)
		{
			GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
			genericOptionDTO1.setId("5");
			genericOptionDTO1.setValue("Restricted");
			stbStatusList.add(genericOptionDTO1);

			GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
			genericOptionDTO2.setId("10");
			genericOptionDTO2.setValue("Unassigned Pending");
			stbStatusList.add(genericOptionDTO2);

			GenericOptionDTO genericOptionDTO3 = new GenericOptionDTO();
			genericOptionDTO3.setId("1");
			genericOptionDTO3.setValue("Unassigned Complete");
			stbStatusList.add(genericOptionDTO3);

		}
		else
		{
			PreparedStatement ps = null;
			Connection db2conn = null;
			ResultSet rs = null;

			try
			{
				String sql = "select STATUS_ID, STATUS_DESCRIPTION from DP_CPE_STATUS_MASTER "
						+ "where STATUS_ID>0 and STATUS_ID!=4 order by STATUS_DESCRIPTION with ur";
				db2conn = DBConnectionManager.getDBConnection();
				ps = db2conn.prepareStatement(sql);
				// logger.info(sql);
				rs = ps.executeQuery();
				while (rs.next())
				{
					GenericOptionDTO selCol = new GenericOptionDTO();
					selCol.setId(rs.getString(1));
					selCol.setValue(rs.getString(2));

					stbStatusList.add(selCol);
				}
			}
			catch (SQLException sqe)
			{
				logger.error("SQL exception occured::" + sqe.getMessage());
				logger.error(sqe.getMessage(), sqe);
				throw new DAOException(sqe.getMessage());
			}
			catch (Exception e)
			{
				logger.error("Exception occured::" + e.getMessage());
				logger.error(e.getMessage(), e);
				throw new DAOException(e.getMessage());
			}
			finally
			{
				DBConnectionManager.releaseResources(ps, rs);
				DBConnectionManager.releaseResources(db2conn);
			}
		}
		return stbStatusList;
	}

	public List<GenericOptionDTO> getStatusOptions(int reportId) throws DAOException
	{
		List<GenericOptionDTO> statusOptions = new ArrayList<GenericOptionDTO>();

		switch (reportId)
		{
			case ReportConstants.COLLECTION_DETAILS_REPORT:
				GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
				genericOptionDTO1.setId("-1");
				genericOptionDTO1.setValue("-All-");

				GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
				genericOptionDTO2.setId("REC");
				genericOptionDTO2.setValue("Pending for Receive");

				GenericOptionDTO genericOptionDTO3 = new GenericOptionDTO();
				genericOptionDTO3.setId("COL");
				genericOptionDTO3.setValue("Received by distributor");

				GenericOptionDTO genericOptionDTO4 = new GenericOptionDTO();
				genericOptionDTO4.setId("IDC");
				genericOptionDTO4.setValue("Ready to Dispatch");

				GenericOptionDTO genericOptionDTO5 = new GenericOptionDTO();
				genericOptionDTO5.setId("S2W");
				genericOptionDTO5.setValue("Sent to Warehouse");

				GenericOptionDTO genericOptionDTO6 = new GenericOptionDTO();
				genericOptionDTO6.setId("AIW");
				genericOptionDTO6.setValue("Accepted in Warehouse");

				GenericOptionDTO genericOptionDTO7 = new GenericOptionDTO();
				genericOptionDTO7.setId("MSN");
				genericOptionDTO7.setValue("Not Accepted in Warehouse");

				GenericOptionDTO genericOptionDTO8 = new GenericOptionDTO();
				genericOptionDTO8.setId("ABW");
				genericOptionDTO8.setValue("Added by Warehouse");

				GenericOptionDTO genericOptionDTO9 = new GenericOptionDTO();
				genericOptionDTO9.setId("ERR");
				genericOptionDTO9.setValue("Error in sending to warehouse");

				GenericOptionDTO genericOptionDTO14 = new GenericOptionDTO();
				genericOptionDTO14.setId("REU");
				genericOptionDTO14.setValue("Added In Stock");

				GenericOptionDTO genericOptionDTO15 = new GenericOptionDTO();
				genericOptionDTO15.setId("REP");
				genericOptionDTO15.setValue("Repaired");

				statusOptions.add(genericOptionDTO1);
				statusOptions.add(genericOptionDTO2);
				statusOptions.add(genericOptionDTO3);
				statusOptions.add(genericOptionDTO4);
				statusOptions.add(genericOptionDTO5);
				statusOptions.add(genericOptionDTO6);
				statusOptions.add(genericOptionDTO7);
				statusOptions.add(genericOptionDTO8);
				statusOptions.add(genericOptionDTO9);
				statusOptions.add(genericOptionDTO14);
				statusOptions.add(genericOptionDTO15);

				break;
			case ReportConstants.ACCOUNT_DETAILS_REPORT:
				GenericOptionDTO genericOptionDTO10 = new GenericOptionDTO();
				genericOptionDTO10.setId("-1");
				genericOptionDTO10.setValue("-All-");

				GenericOptionDTO genericOptionDTO11 = new GenericOptionDTO();
				genericOptionDTO11.setId("A");
				genericOptionDTO11.setValue("Active");

				GenericOptionDTO genericOptionDTO12 = new GenericOptionDTO();
				genericOptionDTO12.setId("D");
				genericOptionDTO12.setValue("InActive");

				statusOptions.add(genericOptionDTO10);
				statusOptions.add(genericOptionDTO11);
				statusOptions.add(genericOptionDTO12);

				break;
			default:
		}

		return statusOptions;
	}

	public List<GenericOptionDTO> getRecoStatus(int reportId) throws DAOException
	{
		List<GenericOptionDTO> statusOptions = new ArrayList<GenericOptionDTO>();

		GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
		genericOptionDTO1.setId("0");
		genericOptionDTO1.setValue("--All--");

		GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
		genericOptionDTO2.setId("1");
		genericOptionDTO2.setValue("Pending");

		GenericOptionDTO genericOptionDTO3 = new GenericOptionDTO();
		genericOptionDTO3.setId("2");
		genericOptionDTO3.setValue("Completed");

		statusOptions.add(genericOptionDTO1);
		statusOptions.add(genericOptionDTO2);
		statusOptions.add(genericOptionDTO3);

		return statusOptions;
	}

	public List<GenericOptionDTO> getStockType() throws DAOException
	{
		List<GenericOptionDTO> stockTypeList = new ArrayList<GenericOptionDTO>();

		PreparedStatement ps = null;
		Connection db2conn = null;
		ResultSet rs = null;

		try
		{

			String sql = "select ID, VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=(select CONFIG_ID from DP_CONFIGURATION_MASTER where CONFIG_NAME = 'STOCK_TYPE' ) order by id with ur";

			db2conn = DBConnectionManager.getMISReportDBConnection();
			ps = db2conn.prepareStatement(sql);

			// logger.info(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				GenericOptionDTO selCol = new GenericOptionDTO();
				selCol.setId(rs.getString(1));
				selCol.setValue(rs.getString(2));

				stockTypeList.add(selCol);
			}
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps, rs);
			DBConnectionManager.releaseResources(db2conn);
		}

		return stockTypeList;
	}

	public GenericReportsOutputDto exportToExcel(GenericReportPararmeterDTO genericDTO)
			throws DAOException
	{
		return null;
	}

	public List<GenericOptionDTO> getAccountTypeList(int reportId, int groupId) throws DAOException
	{
		List<GenericOptionDTO> accountOptions = new ArrayList<GenericOptionDTO>();

		if (reportId == ReportConstants.ACCOUNT_DETAILS_REPORT)
		{

			GenericOptionDTO allOption = new GenericOptionDTO();
			allOption.setId("-1");
			allOption.setValue("-All-");
			accountOptions.add(allOption);
			GenericOptionDTO genericOptionDTO = null;
			logger.info("groupId::::::" + groupId);
			logger.info("if:groupId:::::" + groupId);
			if (groupId == 14)
			{
				genericOptionDTO = new GenericOptionDTO();

				genericOptionDTO.setId("1");
				genericOptionDTO.setValue("DTH ADMIN");
				accountOptions.add(genericOptionDTO);

				genericOptionDTO = new GenericOptionDTO();
				genericOptionDTO.setId("2");
				genericOptionDTO.setValue("Circle Admin");
				accountOptions.add(genericOptionDTO);

				genericOptionDTO = new GenericOptionDTO();
				genericOptionDTO.setId("3");
				genericOptionDTO.setValue("ZBM");
				accountOptions.add(genericOptionDTO);

				genericOptionDTO = new GenericOptionDTO();
				genericOptionDTO.setId("4");
				genericOptionDTO.setValue("ZSM");
				accountOptions.add(genericOptionDTO);

				genericOptionDTO = new GenericOptionDTO();
				genericOptionDTO.setId("5");
				genericOptionDTO.setValue("TSM");
				accountOptions.add(genericOptionDTO);

				genericOptionDTO = new GenericOptionDTO();
				genericOptionDTO.setId("6");
				genericOptionDTO.setValue("DISTRIBUTOR");
				accountOptions.add(genericOptionDTO);

				genericOptionDTO = new GenericOptionDTO();
				genericOptionDTO.setId("7");
				genericOptionDTO.setValue("FSE");
				accountOptions.add(genericOptionDTO);

				genericOptionDTO = new GenericOptionDTO();
				genericOptionDTO.setId("8");
				genericOptionDTO.setValue("RETAILER");
				accountOptions.add(genericOptionDTO);

			}

			else
			{
				for (int i = groupId; i <= 8; i++)
				{
					genericOptionDTO = new GenericOptionDTO();
					switch (i)
					{
						case 1:
							genericOptionDTO.setId("1");
							genericOptionDTO.setValue("DTH ADMIN");
							break;
						case 2:
							genericOptionDTO.setId("2");
							genericOptionDTO.setValue("Circle Admin");
							break;
						case 3:
							genericOptionDTO.setId("3");
							genericOptionDTO.setValue("ZBM");
							break;
						case 4:
							genericOptionDTO.setId("4");
							genericOptionDTO.setValue("ZSM");
							break;
						case 5:
							genericOptionDTO.setId("5");
							genericOptionDTO.setValue("TSM");
							break;
						case 6:
							genericOptionDTO.setId("6");
							genericOptionDTO.setValue("DISTRIBUTOR");
							break;
						case 7:
							genericOptionDTO.setId("7");
							genericOptionDTO.setValue("FSE");
							break;
						case 8:
							genericOptionDTO.setId("8");
							genericOptionDTO.setValue("RETAILER");
							break;

						default:
							break;
					}
					accountOptions.add(genericOptionDTO);
				}
			}
		}
		else
		{

			logger.info("report id 50 called::::DAOIMPL:::::::::::::::;asa::::::::::::::::");
			GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
			genericOptionDTO1.setId("1");
			genericOptionDTO1.setValue("Distributor");

			GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
			genericOptionDTO2.setId("2");
			genericOptionDTO2.setValue("FSE");

			GenericOptionDTO genericOptionDTO3 = new GenericOptionDTO();
			genericOptionDTO3.setId("3");
			genericOptionDTO3.setValue("Retailer");

			accountOptions.add(genericOptionDTO1);
			accountOptions.add(genericOptionDTO2);
			accountOptions.add(genericOptionDTO3);
		}
		return accountOptions;
	}

	public List<GenericOptionDTO> getDcStatusList(int reportId) throws DAOException
	{
		List<GenericOptionDTO> dcStatusList = new ArrayList<GenericOptionDTO>();

		PreparedStatement ps = null;
		Connection db2conn = null;
		ResultSet rs = null;

		try
		{

			String sql = "select DD_VALUE, VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=14 order by VALUE with ur";

			// String sql =
			// "select ID, VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=14 order by VALUE with ur"
			// ;

			db2conn = DBConnectionManager.getMISReportDBConnection();
			ps = db2conn.prepareStatement(sql);

			// logger.info(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				GenericOptionDTO selCol = new GenericOptionDTO();
				selCol.setId(rs.getString(1));
				selCol.setValue(rs.getString(2));

				dcStatusList.add(selCol);
			}
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps, rs);
			DBConnectionManager.releaseResources(db2conn);
		}

		/*
		 * logger.info("222222222222222222"); GenericOptionDTO genericOptionDTO1
		 * = new GenericOptionDTO(); genericOptionDTO1.setId("'DRAFT'");
		 * genericOptionDTO1.setValue("Draft"); GenericOptionDTO
		 * genericOptionDTO2 = new GenericOptionDTO();
		 * genericOptionDTO2.setId("'CREATED'");
		 * genericOptionDTO2.setValue("Ready To Dispatch"); GenericOptionDTO
		 * genericOptionDTO3 = new GenericOptionDTO();
		 * genericOptionDTO3.setId("'SUCCESS'");
		 * genericOptionDTO3.setValue("Sent To Warehouse"); GenericOptionDTO
		 * genericOptionDTO4 = new GenericOptionDTO();
		 * genericOptionDTO4.setId("'AIW'");
		 * genericOptionDTO4.setValue("Accepted By Warehouse"); GenericOptionDTO
		 * genericOptionDTO5 = new GenericOptionDTO();
		 * genericOptionDTO5.setId("'AIWM'");
		 * genericOptionDTO5.setValue("Accepted By Warehouse with Modifications"
		 * ); GenericOptionDTO genericOptionDTO6 = new GenericOptionDTO();
		 * genericOptionDTO6.setId("'REJECT'");
		 * genericOptionDTO6.setValue("Rejected by Warehouse"); GenericOptionDTO
		 * genericOptionDTO7 = new GenericOptionDTO();
		 * genericOptionDTO7.setId("'ERROR'");
		 * genericOptionDTO7.setValue("Error in Sending to Warehouse");
		 * DcStatusList.add(genericOptionDTO1);
		 * DcStatusList.add(genericOptionDTO2);
		 * DcStatusList.add(genericOptionDTO3);
		 * DcStatusList.add(genericOptionDTO4);
		 * DcStatusList.add(genericOptionDTO5);
		 * DcStatusList.add(genericOptionDTO6);
		 * DcStatusList.add(genericOptionDTO7); } catch (SQLException sqe) {
		 * logger.error("SQL exception occured::" + sqe.getMessage());
		 * logger.error(sqe.getMessage(), sqe); throw new
		 * DAOException(sqe.getMessage()); }
		 */
		return dcStatusList;
	}

	private List<GenericOptionDTO> getConfigurationDetails(int configId) throws DAOException
	{
		List<GenericOptionDTO> configurations = new ArrayList<GenericOptionDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		GenericOptionDTO genericOptionDTO = null;

		try
		{
			// connection = DBConnectionManager.getDBConnection();
			connection = DBConnectionManager.getMISReportDBConnection();

			pstmt = connection.prepareStatement(SQL_CONFIGURATION_DETAILS);
			pstmt.setInt(1, configId);
			rset = pstmt.executeQuery();

			while (rset.next())
			{
				genericOptionDTO = new GenericOptionDTO();
				genericOptionDTO.setId(rset.getString("ID"));
				genericOptionDTO.setValue(rset.getString("VALUE"));

				configurations.add(genericOptionDTO);
			}

		}
		catch (SQLException sqe)
		{
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return configurations;
	}

	public String getLastSchedulerDate(int groupId, String distData, String otherData)
			throws DAOException
	{
		// TODO Auto-generated method stub
		int dateDiff = 0;
		String dte = "";
		if (groupId != Constants.DISTIBUTOR && otherData.equals("0"))
		{
			dateDiff = 0;
		}
		else if (groupId == Constants.DISTIBUTOR && distData.equals("0"))
		{
			dateDiff = 0;
		}
		else
		{
			dateDiff = -1;
		}
		if (dateDiff == -1)
		{
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			Connection connection = null;

			try
			{
				connection = DBConnectionManager.getMISReportDBConnection();

				pstmt = connection.prepareStatement(SQL_SCH_DATE);
				rset = pstmt.executeQuery();

				while (rset.next())
				{
					Date dt = rset.getDate("SCH_DATE");
					DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
					dte = formatter.format(dt);

				}

			}
			catch (SQLException sqe)
			{
				logger.error("SQL exception occured::" + sqe.getMessage());
				logger.error(sqe.getMessage(), sqe);
				throw new DAOException(sqe.getMessage());
			}
			catch (Exception e)
			{
				logger.error("Exception occured::" + e.getMessage());
				logger.error(e.getMessage(), e);
				throw new DAOException(e.getMessage());
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rset);
				DBConnectionManager.releaseResources(connection);
			}
		}
		else
		{
			Date dt = new Date();
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			dte = formatter.format(dt);
		}
		return dte;
	}

	public List<GenericOptionDTO> getRecoPeriodList(int reportId, int groupId) throws DAOException
	{
		List<GenericOptionDTO> recoOptions = new ArrayList<GenericOptionDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		GenericOptionDTO genericOptionDTO = null;

		try
		{
			// connection = DBConnectionManager.getDBConnection();
			connection = DBConnectionManager.getDBConnection();

			pstmt = connection
					.prepareStatement("SELECT  * FROM DP_RECO_PERIOD where TO_DATE<current date  ORDER BY ID DESC");
			rset = pstmt.executeQuery();
			while (rset.next())
			{
				genericOptionDTO = new GenericOptionDTO();
				String fromDate = com.ibm.utilities.Utility.converDateToString(rset
						.getDate("FROM_DATE"), Constants.DT_FMT_RECO);
				String toDate = com.ibm.utilities.Utility.converDateToString(rset
						.getDate("TO_DATE"), Constants.DT_FMT_RECO);

				genericOptionDTO.setId(rset.getString("ID"));
				genericOptionDTO.setValue(fromDate + " to " + toDate);

				recoOptions.add(genericOptionDTO);
			}

		}
		catch (SQLException sqe)
		{
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return recoOptions;
	}

	public GenericReportsOutputDto setDTOForRecoDetail(ResultSet rs, GenericReportsOutputDto recDTO)
			throws SQLException
	{
		List<GenericReportsOutputDto> recoList = new ArrayList<GenericReportsOutputDto>();
		GenericReportsOutputDto objGenericReportsOutputDto = new GenericReportsOutputDto();

		int sgPenPOOpen = 0;
		int dgPenPOOpen = 0;
		int sgPenDCOpen = 0;
		int dgPenDCOpen = 0;
		int sgSerOpen = 0;
		int dgSerOpen = 0;
		int sgNsrOpen = 0;
		int dgNsrOpen = 0;
		int sgDefOpen = 0;
		int dgDefOpen = 0;
		int sgUpgradeOpen = 0;
		int dgUpgradeOpen = 0;
		int sgChuurnOpen = 0;
		int dgChuurnOpen = 0;
		int sgRecWHStock = 0;
		int dgRecWHStock = 0;
		int sgRecInterSSDOK = 0;
		int dgRecInterSSDOK = 0;
		int sgRecInterSSDDef = 0;
		int dgRecInterSSDDef = 0;
		int sgRecUpgrade = 0;
		int dgRecUpgrade = 0;
		int sgRecChurn = 0;
		int dgRecChurn = 0;
		int sgRetFresh = 0;
		int dgRetFresh = 0;
		int sgRetInterSSDOK = 0;
		int dgRetInterSSDOK = 0;
		int sgRetInterSSDDef = 0;
		int dgRetInterSSDDef = 0;
		int sgRetDOABI = 0;
		int dgRetDOABI = 0;
		int sgRetDOAAI = 0;
		int dgRetDOAAI = 0;
		int sgRetSwap = 0;
		int dgRetSwap = 0;
		int sgRetC2S = 0;
		int dgRetC2S = 0;
		int sgRetChurn = 0;
		int dgRetChurn = 0;
		int sgActivation = 0;
		int dgActivation = 0;
		int sgInvChange = 0;
		int dgInvChange = 0;
		int sgAdjustment = 0;
		int dgAdjustment = 0;
		int sgSerClosing = 0;
		int dgSerClosing = 0;
		int sgNsrClosing = 0;
		int dgNsrClosing = 0;
		int sgDefClosing = 0;
		int dgDefClosing = 0;
		int sgUpgradeClosing = 0;
		int dgUpgradeClosing = 0;
		int sgChuurnClosing = 0;
		int dgChuurnClosing = 0;
		int sgPenPOClosing = 0;
		int dgPenPOClosing = 0;
		int sgPenDCClosing = 0;
		int dgPenDCClosing = 0;
		String status = "";
		int i = 0;
		recDTO = null;
		recoList = null;
		recoList = new ArrayList<GenericReportsOutputDto>();
		while (rs.next())
		{
			status = "";
			i++;
			recDTO = null;
			recDTO = new GenericReportsOutputDto();
			if (rs.getString("RECO_STATUS").equals("INITIATE"))
			{
				status = "Pending";
			}
			else if (rs.getString("RECO_STATUS").equals("SUCCESS"))
			{
				status = "Submitted";
			}
			recDTO.setDistributorId(rs.getString("DISTRIBUTOR_OLM_ID"));
			recDTO.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
			recDTO.setType(rs.getString("TYPE"));
			recDTO.setProductType(rs.getString("PRODUCT_NAME"));

			recDTO.setPenPOOpen(rs.getInt("PENDING_PO_OPENING"));
			recDTO.setPenDcOpen(rs.getInt("PENDING_DC_OPENING"));
			recDTO.setSerOpening(rs.getInt("SER_OPEN_STOCK"));
			recDTO.setNsrOpening(rs.getInt("NSER_OPEN_STOCK"));
			recDTO.setDefOpen(rs.getInt("DEFECTIVE_OPEN_STOCK"));
			recDTO.setUpgardeOpen(rs.getInt("UPGRADE_OPEN_STOCK"));
			recDTO.setChurnOpen(rs.getInt("CHURN_OPEN_STOCK"));

			recDTO.setReceivedWH(rs.getInt("RECEIVED_WH"));
			recDTO.setReceivedInterSSDOk(rs.getInt("RecInterSSDOK"));
			recDTO.setReceivedInterSSDDef(rs.getInt("RECEIVED_INTER_SSD_DEF"));
			recDTO.setReceivedUpgrade(rs.getInt("RECEIVED_UPGRADE"));
			recDTO.setReceivedChurn(rs.getInt("RECEIVED_CHURN"));

			recDTO.setReturnedFresh(rs.getInt("RETURNED_FRESH"));
			recDTO.setReturnedInterSSDOk(rs.getInt("RetInterSSDOK"));
			recDTO.setReturnedInterSSDDef(rs.getInt("RETURNED_INTER_SSD_DEF"));
			recDTO.setReturnedDOABI(rs.getInt("RETURNED_DOA_BI"));
			recDTO.setReturnedDOAAI(rs.getInt("RETURNED_DOA_AI"));
			recDTO.setReturnedSwap(rs.getInt("RETURNED_SWAP"));
			recDTO.setReturnedC2C(rs.getInt("RETURNED_C2S"));
			recDTO.setReturnedChurn(rs.getInt("RETURNED_CHURN"));

			recDTO.setTotalActivation(rs.getInt("ACTIVATION"));
			recDTO.setSerClosing(rs.getInt("SER_CLOSING_STOCK"));
			recDTO.setNsrClosing(rs.getInt("NSER_CLOSING_STOCK"));
			recDTO.setUpgardeClosing(rs.getInt("UPGRADE_CLOSING_STOCK"));
			recDTO.setChurnClosing(rs.getInt("CHURN_CLOSING_STOCK"));
			recDTO.setDefClosing(rs.getInt("DEF_CLOSING_STOCK"));
			recDTO.setPenPOClosing(rs.getInt("PENDING_PO_CLOSING"));
			recDTO.setPenDcClosing(rs.getInt("PENDING_DC_CLOSING"));
			recDTO.setAdjustment(rs.getInt("ADJUSTMENT"));
			recDTO.setInventoryChange(rs.getInt("INVENTORY_CHANGE"));

			if (i % 2 == 0)
			{
				dgPenPOOpen = recDTO.getPenPOOpen();
				dgPenDCOpen = recDTO.getPenDcOpen();
				dgSerOpen = recDTO.getSerOpening();
				dgNsrOpen = recDTO.getNsrOpening();
				dgDefOpen = recDTO.getDefOpen();
				dgUpgradeOpen = recDTO.getUpgardeOpen();
				dgChuurnOpen = recDTO.getChurnOpen();
				dgRecWHStock = recDTO.getReceivedWH();
				dgRecInterSSDOK = recDTO.getReceivedInterSSDOk();
				dgRecInterSSDDef = recDTO.getReceivedInterSSDDef();
				dgRecChurn = recDTO.getReceivedChurn();
				dgRecUpgrade = recDTO.getReceivedUpgrade();
				dgRetFresh = recDTO.getReturnedFresh();
				dgRetInterSSDOK = recDTO.getReturnedInterSSDOk();
				dgRetInterSSDDef = recDTO.getReturnedInterSSDDef();
				dgRetChurn = recDTO.getReturnedChurn();
				dgRetC2S = recDTO.getReturnedC2C();
				dgRetDOAAI = recDTO.getReturnedDOAAI();
				dgRetDOABI = recDTO.getReturnedDOABI();
				dgRetSwap = recDTO.getReturnedSwap();

				dgActivation = recDTO.getTotalActivation();
				dgAdjustment = recDTO.getAdjustment();
				dgInvChange = recDTO.getInventoryChange();
				dgSerClosing = recDTO.getSerClosing();
				dgNsrClosing = recDTO.getNsrClosing();
				dgDefClosing = recDTO.getDefClosing();
				dgChuurnClosing = recDTO.getChurnClosing();
				dgUpgradeClosing = recDTO.getUpgardeClosing();

				dgPenDCClosing = recDTO.getPenDcClosing();
				dgPenPOClosing = recDTO.getPenPOClosing();

				recDTO.setStatus(status);

				recoList.add(recDTO);

				GenericReportsOutputDto recVarianceDTO = new GenericReportsOutputDto();
				recVarianceDTO.setDistributorId(rs.getString("DISTRIBUTOR_OLM_ID"));
				recVarianceDTO.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
				recVarianceDTO.setType("Variance");
				recVarianceDTO.setProductType(rs.getString("PRODUCT_NAME"));

				// ****************************************************
				// /Variance == Partner generated - System Generated
				// **************************************************

				recVarianceDTO.setPenPOOpen(dgPenPOOpen - sgPenPOOpen);
				recVarianceDTO.setPenDcOpen(dgPenDCOpen - sgPenDCOpen);
				recVarianceDTO.setSerOpening(dgSerOpen - sgSerOpen);
				recVarianceDTO.setNsrOpening(dgNsrOpen - sgNsrOpen);
				recVarianceDTO.setDefOpen(dgDefOpen - sgDefOpen);
				recVarianceDTO.setUpgardeOpen(dgUpgradeOpen - sgUpgradeOpen);
				recVarianceDTO.setChurnOpen(dgChuurnOpen - sgChuurnOpen);

				recVarianceDTO.setReceivedWH(dgRecWHStock - sgRecWHStock);
				recVarianceDTO.setReceivedInterSSDOk(dgRecInterSSDOK - sgRecInterSSDOK);
				recVarianceDTO.setReceivedInterSSDDef(dgRecInterSSDDef - sgRecInterSSDDef);
				recVarianceDTO.setReceivedUpgrade(dgRecUpgrade - sgRecUpgrade);
				recVarianceDTO.setReceivedChurn(dgRecChurn - sgRecChurn);

				recVarianceDTO.setReturnedFresh(dgRetFresh - sgRetFresh);
				recVarianceDTO.setReturnedInterSSDOk(dgRetInterSSDOK - sgRetInterSSDOK);
				recVarianceDTO.setReturnedInterSSDDef(dgRetInterSSDDef - sgRetInterSSDDef);
				recVarianceDTO.setReturnedDOABI(dgRetDOABI - sgRetDOABI);
				recVarianceDTO.setReturnedDOAAI(dgRetDOAAI - sgRetDOAAI);
				recVarianceDTO.setReturnedSwap(dgRetSwap - sgRetSwap);
				recVarianceDTO.setReturnedC2C(dgRetC2S - sgRetC2S);
				recVarianceDTO.setReturnedChurn(dgRetChurn - sgRetChurn);

				recVarianceDTO.setTotalActivation(dgActivation - sgActivation);

				recVarianceDTO.setInventoryChange(dgInvChange - sgInvChange);
				recVarianceDTO.setSerClosing(dgSerClosing - sgSerClosing);
				recVarianceDTO.setNsrClosing(dgNsrClosing - sgNsrClosing);
				recVarianceDTO.setUpgardeClosing(dgUpgradeClosing - sgUpgradeClosing);
				recVarianceDTO.setChurnClosing(dgChuurnClosing - sgChuurnClosing);
				recVarianceDTO.setDefClosing(dgDefClosing - sgDefClosing);
				recVarianceDTO.setPenPOClosing(dgPenPOClosing - sgPenPOClosing);
				recVarianceDTO.setPenDcClosing(dgPenDCClosing - sgPenDCClosing);
				recVarianceDTO.setAdjustment(dgAdjustment - sgAdjustment);

				recoList.add(recVarianceDTO);
			}
			else
			{
				sgPenPOOpen = recDTO.getPenPOOpen();
				sgPenDCOpen = recDTO.getPenDcOpen();
				sgSerOpen = recDTO.getSerOpening();
				sgNsrOpen = recDTO.getNsrOpening();
				sgDefOpen = recDTO.getDefOpen();
				sgUpgradeOpen = recDTO.getUpgardeOpen();
				sgChuurnOpen = recDTO.getChurnOpen();
				sgRecWHStock = recDTO.getReceivedWH();
				sgRecInterSSDOK = recDTO.getReceivedInterSSDOk();
				sgRecInterSSDDef = recDTO.getReceivedInterSSDDef();
				sgRecChurn = recDTO.getReceivedChurn();
				sgRecUpgrade = recDTO.getReceivedUpgrade();
				sgRetFresh = recDTO.getReturnedFresh();
				sgRetInterSSDOK = recDTO.getReturnedInterSSDOk();
				sgRetInterSSDDef = recDTO.getReturnedInterSSDDef();
				sgRetChurn = recDTO.getReturnedChurn();
				sgRetC2S = recDTO.getReturnedC2C();
				sgRetDOAAI = recDTO.getReturnedDOAAI();
				sgRetDOABI = recDTO.getReturnedDOABI();
				sgRetSwap = recDTO.getReturnedSwap();

				sgActivation = recDTO.getTotalActivation();
				sgAdjustment = recDTO.getAdjustment();
				sgInvChange = recDTO.getInventoryChange();
				sgSerClosing = recDTO.getSerClosing();
				sgNsrClosing = recDTO.getNsrClosing();
				sgDefClosing = recDTO.getDefClosing();
				sgChuurnClosing = recDTO.getChurnClosing();
				sgUpgradeClosing = recDTO.getUpgardeClosing();

				sgPenDCClosing = recDTO.getPenDcClosing();
				sgPenPOClosing = recDTO.getPenPOClosing();

				recoList.add(recDTO);
			}
		}
		objGenericReportsOutputDto.setRecoList(recoList);
		return objGenericReportsOutputDto;
	}

	public List<GenericReportPararmeterDTO> exportToExcelDebitNote(
			GenericReportPararmeterDTO genericReportsOutputDto) throws DAOException
	{
		GenericReportsOutputDto genericOutputDto = new GenericReportsOutputDto();
		List<GenericReportPararmeterDTO> listReturn = new ArrayList<GenericReportPararmeterDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		String selectedCircles = genericReportsOutputDto.getCircleIds();
		String selectedTSMIds = genericReportsOutputDto.getDistIds();
		String selectedDistIds = genericReportsOutputDto.getTsmIds();
		String selectedRecoPeriod = genericReportsOutputDto.getRecoPeriod();

		try
		{

			logger.info("REPORT=50::::::::::::::::::" + genericReportsOutputDto.getCircleIds());
			logger.info("REPORT=50::::::::::::::::::" + genericReportsOutputDto.getDistIds());
			logger.info("REPORT=50::::::::::::::::::" + genericReportsOutputDto.getTsmIds());
			logger.info("REPORT=50::::::::::::::::::" + genericReportsOutputDto.getRecoPeriod());

			logger
					.info("asasasasa:::::::::::::::::::::::::asasasa:::daoimpl:::::::::::::::::::::::;;exportToExcelDebitNote");
			connection = DBConnectionManager.getDBConnection();
			pstmt = connection
					.prepareStatement("SELECT DISTRIBUTOR_ID as DISTRIBUTOR,CIRCLE_ID,PRODUCT_ID as PRODUCT,SERIAL_NO as SERIAL "
							+ "FROM RECO_SERIALIZED_STOCK RSS, DP_RECO_PERIOD DRP WHERE "
							+ "DEBIT_FLAG='1' "
							+ "and DISTRIBUTOR_ID=169233 "
							+ "and date(RSS.RUNNING_SCHEDULER_DATE) between "
							+ "   (select date(DRP.FROM_DATE) from DP_RECO_PERIOD where ID=161) and (select date(DRP.TO_DATE) from DP_RECO_PERIOD where ID=161) "
							+

							"UNION "
							+

							"SELECT FROM_DIST_ID as DISTRIBUTOR,CIRCLE_ID,NEW_PRODUCT_ID as PRODUCT,NEW_SR_NO as SERIAL "
							+ "FROM RECO_UPGRADE_STOCK RUS, DP_RECO_PERIOD DRP WHERE "
							+ "RUS.DEBIT_FLAG='1' "
							+ "and RUS.FROM_DIST_ID=169233 "
							+ "and date(RUS.RUNNING_SCHEDULER_DATE) between "
							+ "    (select date(DRP.FROM_DATE) from DP_RECO_PERIOD where ID=161) and (select date(DRP.TO_DATE) from DP_RECO_PERIOD where ID=161) "
							+

							"UNION "
							+

							"SELECT FROM_DIST_ID as DISTRIBUTOR,CIRCLE_ID,NEW_PRODUCT_ID as PRODUCT,NEW_SR_NO as SERIAL "
							+ "FROM RECO_DEFECTIVE_STOCK RDS, DP_RECO_PERIOD DRP WHERE "
							+ "RDS.DEBIT_FLAG='1' "
							+ "and RDS.FROM_DIST_ID=169233 "
							+ "and date(RDS.RUNNING_SCHEDULER_DATE) between "
							+ "    (select date(DRP.FROM_DATE) from DP_RECO_PERIOD where ID=161) and (select date(DRP.TO_DATE) from DP_RECO_PERIOD where ID=161) "
							+

							"UNION "
							+

							"SELECT FROM_DIST_ID as DISTRIBUTOR,CIRCLE_ID,DEFECTIVE_PRODUCT_ID as PRODUCT,DEFECTIVE_SR_NO as SERIAL "
							+ "FROM RECO_CHURN_STOCK RCS, DP_RECO_PERIOD DRP WHERE "
							+ "RCS.DEBIT_FLAG='1' " + "and RCS.FROM_DIST_ID=169233 ");// +
			// "and date(RCS.RUNNING_SCHEDULER_DATE) between "+
			// "   (select date(DRP.FROM_DATE) from DP_RECO_PERIOD where ID=161) and (select date(DRP.TO_DATE) from DP_RECO_PERIOD where ID=161)"
			// );
			rset = pstmt.executeQuery();
			GenericReportPararmeterDTO genericReportsOutputDto1 = null;
			while (rset.next())
			{
				genericReportsOutputDto1 = new GenericReportPararmeterDTO();
				genericReportsOutputDto1.setDebitDistName(rset.getString("DISTRIBUTOR"));
				genericReportsOutputDto1.setDebitCircleName(rset.getString("CIRCLE_ID"));
				genericReportsOutputDto1.setDebitProductName(rset.getString("PRODUCT"));
				genericReportsOutputDto1.setDebitSerial(rset.getString("SERIAL"));

				logger.info("asa:::::::::debitdistname:::::::::::::"
						+ genericReportsOutputDto1.getDebitDistName());

				listReturn.add(genericReportsOutputDto1);
				genericReportsOutputDto1 = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Exception while getting circle list for reco report  ::  " + e);
			logger.info("Exception while getting circle list for reco report  ::  "
					+ e.getMessage());
			try
			{
				throw new com.ibm.dp.exception.DAOException(e.getMessage());
			}
			catch (com.ibm.dp.exception.DAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}

	// Added by Neetika
	public List<GenericOptionDTO> getPTList() throws DAOException
	{
		List<GenericOptionDTO> statusOptions = new ArrayList<GenericOptionDTO>();

		GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
		genericOptionDTO1.setId("1");
		genericOptionDTO1.setValue("Commercial");

		GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
		genericOptionDTO2.setId("2");
		genericOptionDTO2.setValue("Swap");

		statusOptions.add(genericOptionDTO1);
		statusOptions.add(genericOptionDTO2);

		return statusOptions;
	}

	// Added by Neetika
	public List<GenericOptionDTO> getDTList() throws DAOException
	{
		List<GenericOptionDTO> statusOptions = new ArrayList<GenericOptionDTO>();

		GenericOptionDTO genericOptionDTO1 = new GenericOptionDTO();
		genericOptionDTO1.setId("1");
		genericOptionDTO1.setValue("SSD");

		GenericOptionDTO genericOptionDTO2 = new GenericOptionDTO();
		genericOptionDTO2.setId("2");
		genericOptionDTO2.setValue("SF");

		statusOptions.add(genericOptionDTO1);
		statusOptions.add(genericOptionDTO2);

		return statusOptions;
	}

	@Override
	public String getAgeingDays(String configId) throws DAOException {
		// TODO Auto-generated method stub
		String ageingDays = "";
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String query = "select CONFIG_VALUE from DP_CONFIGURATION_MASTER where CONFIG_ID=?";
		try{
			connection = DBConnectionManager.getDBConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, configId);
			
			rs = pstmt.executeQuery();			
			if(rs.next())
			{
				ageingDays = rs.getString("CONFIG_VALUE");
			}
			
		}	
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			logger.error(e.getLocalizedMessage());
			//throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(null, rs);
			DBConnectionManager.releaseResources(connection);
		}	
		return ageingDays;
	}
			
	
}