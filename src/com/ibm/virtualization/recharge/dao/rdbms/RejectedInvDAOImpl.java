package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibm.dp.dto.RejectedInvoiceDTO;
import com.ibm.dp.dto.RejectedPartnerInvDTO;
import com.ibm.virtualization.recharge.dao.RejectedInvDAO;
import com.ibm.virtualization.recharge.exception.DAOException;

public class RejectedInvDAOImpl extends BaseDaoRdbms implements RejectedInvDAO {

	public RejectedInvDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<RejectedInvoiceDTO> getRejectedInvData(String monthID)
			throws DAOException, SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;
		ResultSet rs3 = null;

		List<RejectedInvoiceDTO> invDTOIds = new ArrayList<RejectedInvoiceDTO>();

		pst = connection
				.prepareStatement("Select * from DP_INVOICE where  MONTH_FR = ? ");
		pst.setString(1, monthID);

		rs1 = pst.executeQuery();
		RejectedInvoiceDTO tempDTO = null;
		while (rs1.next()) {
			tempDTO = new RejectedInvoiceDTO();
			tempDTO.setOlmId(rs1.getString("OLM_ID"));
			tempDTO.setTinNo(rs1.getString("TIN_NO"));
			tempDTO.setSrcvTaxCT(rs1.getString("SRVC_TAX_CT"));
			tempDTO.setInvoiceNumber(rs1.getString("INVOICE_NO"));
			tempDTO.setCpeRecNo(rs1.getInt("CPE_REC_NO"));
			tempDTO.setCpeRate(rs1.getDouble("CPE_REC_RT"));
			tempDTO.setCpeAmount(rs1.getDouble("CPE_AMT"));
			tempDTO.setSrRewNo(rs1.getInt("SR_REWARD_NO"));
			tempDTO.setSrRate(rs1.getDouble("SR_REWARD_RT"));
			tempDTO.setSrRewAmount(rs1.getDouble("SR_REWARD_AMT"));
			tempDTO.setRetenNo(rs1.getInt("RETENTION_NO"));
			tempDTO.setRetenRate(rs1.getDouble("RETENTION_RT"));
			tempDTO.setRetenAmount(rs1.getDouble("RETENTION_AMT"));
			tempDTO.setStatus(rs1.getString("STATUS"));
			tempDTO.setRemarks(rs1.getString("REMARKS"));
			tempDTO.setActionDate(rs1.getString("ACTION_DATE"));
			invDTOIds.add(tempDTO);
		}
		return invDTOIds;

	}

	@Override
	public List<RejectedPartnerInvDTO> getRejectedPartnerInv(String monthID)
			throws DAOException, SQLException {
		// TODO Auto-generated method stub

		PreparedStatement pst1 = null;
		ResultSet rs1 = null;

		List<RejectedPartnerInvDTO> invPartnerDTOids = new ArrayList<RejectedPartnerInvDTO>();

		pst1 = connection
				.prepareStatement("Select * from DP_PARTNER_INVOICE_2 where  MONTH_FR = ? ");
		pst1.setString(1, monthID);

		rs1 = pst1.executeQuery();
		RejectedPartnerInvDTO tempDTO = null;
		while (rs1.next()) {
			tempDTO = new RejectedPartnerInvDTO();
			tempDTO.setOlmId(rs1.getString("OLM_ID"));
			tempDTO.setInvoiceNumber(rs1.getString("INVOICE_NO"));
			tempDTO.setAmount(rs1.getLong("AMOUNT"));
			tempDTO.setHDT1(rs1.getLong("HD_T1"));
			tempDTO.setHDT2(rs1.getLong("HD_T2"));
			tempDTO.setHDT3(rs1.getLong("HD_T3"));
			tempDTO.setMULTIT1(rs1.getLong("MULTI_T1"));
			tempDTO.setMULTIT2(rs1.getLong("MULTI_T2"));
			tempDTO.setMULTIT3(rs1.getLong("MULTI_T3"));
			tempDTO.setMULTIDVRT1(rs1.getLong("MULTI_DVR_T1"));
			tempDTO.setMULTIDVRT2(rs1.getLong("MULTI_DVR_T2"));
			tempDTO.setMULTIDVRT3(rs1.getLong("MULTI_DVR_3"));
			tempDTO.setTOTALACT(rs1.getLong("TOTAL_ACT"));
			tempDTO.setTOTALAmount(rs1.getLong("TOTAL_AMOUNT"));
			tempDTO.setTotalInvAmt(rs1.getLong("TOTAL_INVOICE_AMT"));
			tempDTO.setPartnerName(rs1.getString("PARTNER_NAME"));
			tempDTO.setStatus(rs1.getString("STATUS"));
			tempDTO.setRemarks(rs1.getString("REMARKS"));
			tempDTO.setActionDate(rs1.getString("ACTION_DATE"));
			invPartnerDTOids.add(tempDTO);
		}
		return invPartnerDTOids;

	}

}