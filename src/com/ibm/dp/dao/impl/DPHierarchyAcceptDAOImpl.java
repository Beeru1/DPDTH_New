package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPHierarchyAcceptDAO;
import com.ibm.dp.dto.DPHierarchyAcceptDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPHierarchyAcceptDAOImpl extends BaseDaoRdbms implements DPHierarchyAcceptDAO 
{
	public static Logger logger = Logger.getLogger(DPOpenStockDepleteDaoImpl.class.getName());
	
	public DPHierarchyAcceptDAOImpl(Connection connection) 
	{
		super(connection);
	}

	public List<DPHierarchyAcceptDTO> getHierarchyTransferInitDao(long loginUserId) throws DAOException 
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<DPHierarchyAcceptDTO> listReturn = new ArrayList<DPHierarchyAcceptDTO>();
		
		try
		{
			pstmt = connection.prepareStatement(DBQueries.SQL_HIER_ACCEPT);
			pstmt.setLong(1, loginUserId);
			pstmt.setString(2, Constants.HIER_TRANS_FORWARD);
			rset = pstmt.executeQuery();
			
			DPHierarchyAcceptDTO acceptDTO = null;
			StringBuffer strTransferType = null;
			String strTransSubType = null;
			
			while(rset.next())
			{
				acceptDTO = new DPHierarchyAcceptDTO();

				acceptDTO.setStrTRNO(rset.getString("TR_NO"));
				
				strTransferType = new StringBuffer(rset.getString("REQUEST_TYPE"));
				
				strTransSubType = rset.getString("REQUEST_SUB_TYPE");
				
				if(strTransSubType!=null && !strTransSubType.trim().equals(""))
				{
					strTransferType.append(" ( ");
					strTransferType.append(strTransSubType);
					strTransferType.append(" )");
				}
				
				acceptDTO.setStrTransferType(strTransferType.toString());
				
				acceptDTO.setStrRequestBy(rset.getString("REQUEST_BY"));
				
				acceptDTO.setStrRequestOn(rset.getString("REQUEST_ON"));
				
				acceptDTO.setStrTransferBy(rset.getString("TRANSFER_BY"));
				
				acceptDTO.setStrTransferOn(rset.getString("TRANSFER_ON"));
				
				acceptDTO.setIntTransferQty(rset.getInt("TRNS_QTY"));
				
				acceptDTO.setIntTrnsBy(rset.getInt("TRNS_ID"));
				
				acceptDTO.setTsTrnsTime(rset.getTimestamp("TRNS_TIME"));
				
				acceptDTO.setStrTransferReq(rset.getString("TR_NO")+"$"+rset.getString("TRNS_ID")+"$"+rset.getString("TRNS_TIME"));
				
				listReturn.add(acceptDTO);
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("::::::::::::::::::: Error in getHierarchyTransferInitDao -------->",e);
			return listReturn;
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}

	public List<DPHierarchyAcceptDTO> viewHierarchyAcceptList(String strTRNo, long longLoginId, Integer intTransferBy, String strTrnsTime) throws DAOException {
		List<DPHierarchyAcceptDTO> viewHierarchyListDTO	= new ArrayList<DPHierarchyAcceptDTO>();
		PreparedStatement ps = null;
		ResultSet rs		 = null;
		DPHierarchyAcceptDTO  viewHierarchyDto = null;
				
		try
		{
			if(intTransferBy != 0)
			{
				ps = connection.prepareStatement(DBQueries.SQL_HIER_ACCEPT_VIEW);
				ps.setString(1, strTRNo);
				ps.setLong(2, longLoginId);
				ps.setInt(3, intTransferBy);
				ps.setString(4, Constants.HIER_TRANS_FORWARD);
				ps.setString(5, strTrnsTime);
			}
			else
			{
				ps = connection.prepareStatement(DBQueries.SQL_HIER_ACCEPT_VIEW_ALL);
				ps.setString(1, strTRNo);
			}
			
			
			rs = ps.executeQuery();
			viewHierarchyListDTO = new ArrayList<DPHierarchyAcceptDTO>();
			
			while(rs.next())
			{
				viewHierarchyDto = new DPHierarchyAcceptDTO();
				viewHierarchyDto.setStrTRNO(rs.getString("TR_NO"));
				viewHierarchyDto.setAccount_id(rs.getInt("ACCOUNT_ID"));
				viewHierarchyDto.setStrRole(rs.getString("ROLE"));
				viewHierarchyDto.setStrAccountName(rs.getString("ACCOUNT_NAME"));
				viewHierarchyDto.setStrContactName(rs.getString("CONTACT_NAME"));
				viewHierarchyDto.setStrMobileNo(rs.getString("MOBILE_NUMBER"));
				viewHierarchyDto.setStrZone(rs.getString("ZONE"));
				viewHierarchyDto.setStrCity(rs.getString("CITY"));
				viewHierarchyDto.setStrTransferBy(rs.getString("TRANSFER_BY"));
				viewHierarchyDto.setTsTrnsTime(rs.getTimestamp("TRNS_TIME"));
				
				if(intTransferBy != 0)
				{
					viewHierarchyDto.setRequest_type("transBy");
				}
				else
				{
					viewHierarchyDto.setRequest_type("ALL");
				}
				
				
				viewHierarchyListDTO.add(viewHierarchyDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps, rs);
		}
		return viewHierarchyListDTO;
	}

	public List<DPHierarchyAcceptDTO> acceptHierarchyDao(String[] arrCheckedTR, long loginUserId) throws DAOException 
	{
		PreparedStatement pstmtHierDtlUpd 	= null;
		PreparedStatement pstmtHierDtlReq 	= null;
		PreparedStatement pstmtAccountUpd 	= null;
		PreparedStatement pstmtFSEStockUpd 	= null;
		PreparedStatement pstmtRetStockUpd 	= null;
		
		ResultSet rset = null;
		String strReqType = null;
		
		List<DPHierarchyAcceptDTO> listReturn = new ArrayList<DPHierarchyAcceptDTO>();
		
		try
		{
			connection.setAutoCommit(false);
			
			pstmtHierDtlUpd  = connection.prepareStatement(DBQueries.SQL_HIER_ACCEPT_DTL_UPD);
			pstmtHierDtlReq  = connection.prepareStatement(DBQueries.SQL_HIER_ACCEPT_REQ_DTL);
			pstmtAccountUpd  = connection.prepareStatement(DBQueries.SQL_HIER_ACCOUNT_UPD);
			pstmtFSEStockUpd = connection.prepareStatement(DBQueries.SQL_HIER_FSE_STOCK_UPD);
			pstmtRetStockUpd = connection.prepareStatement(DBQueries.SQL_HIER_RET_STOCK_UPD);
			
			String strTRNO, strTrnsBy, strTrnsOn = null;
			StringTokenizer tokenizer = null;
			
			for(int i=0; i<arrCheckedTR.length; i++)
			{
				tokenizer = new StringTokenizer(arrCheckedTR[i], "$");
				
				strTRNO = tokenizer.nextToken();
				logger.info("strTRNO  ::  "+strTRNO);
				
				strTrnsBy = tokenizer.nextToken();
				logger.info("strTrnsBy  ::  "+strTrnsBy);
				
				strTrnsOn = tokenizer.nextToken();
				logger.info("strTrnsOn  ::  "+strTrnsOn);
				
				pstmtHierDtlReq.setString(1, strTRNO);
				pstmtHierDtlReq.setString(2, Constants.HIER_TRANS_FORWARD);
				pstmtHierDtlReq.setLong(3, loginUserId);
				pstmtHierDtlReq.setString(4, strTrnsBy);
				pstmtHierDtlReq.setString(5, strTrnsOn);
				
				rset = pstmtHierDtlReq.executeQuery();
				
				while(rset.next())
				{
					strReqType = rset.getString("TRANS_TYPE");
					
					if(strReqType.equals(Constants.HEIR_TRANS_TYPE_FSE))
					{
						//Updating Distributor for FSE
						pstmtAccountUpd.setLong(1, loginUserId);
						pstmtAccountUpd.setInt(2, rset.getInt("TRNS_ACCOUNT_ID"));
						pstmtAccountUpd.executeUpdate();
						
						//Transferring FSE's stock to new Distributor
						pstmtFSEStockUpd.setLong(1, loginUserId);
						pstmtFSEStockUpd.setInt(2, rset.getInt("TRNS_ACCOUNT_ID"));
						pstmtFSEStockUpd.executeUpdate();
					}
					else
					{
						//Updating FSE for Retailer
						pstmtAccountUpd.setInt(1, rset.getInt("TO_FSE"));
						pstmtAccountUpd.setInt(2, rset.getInt("TRNS_ACCOUNT_ID"));
						pstmtAccountUpd.executeUpdate();
						
						//Transferring retailers's stock to new FSE
						pstmtRetStockUpd.setLong(1, loginUserId);
						pstmtRetStockUpd.setInt(2, rset.getInt("TO_FSE"));
						pstmtRetStockUpd.setInt(3, rset.getInt("TRNS_ACCOUNT_ID"));
						pstmtRetStockUpd.executeUpdate();
					}
				}
				
				pstmtHierDtlUpd.setString(1, Constants.HEIR_TRANS_STATUS_ACPT);
				pstmtHierDtlUpd.setString(2, strTRNO);
				pstmtHierDtlUpd.setLong(3, loginUserId);
				pstmtHierDtlUpd.setString(4, Constants.HIER_TRANS_FORWARD);
				pstmtHierDtlUpd.setString(5, strTrnsBy);
				pstmtHierDtlUpd.setString(6, strTrnsOn);
				
				pstmtHierDtlUpd.executeUpdate();
			}
			
			connection.commit();
			
			listReturn = getHierarchyTransferInitDao(loginUserId);			
		}
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
						
			e.printStackTrace();
			logger.error("Exception occured while Accepting Hierarchy Transfer  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtHierDtlUpd, rset);
			DBConnectionManager.releaseResources(pstmtHierDtlReq, rset);
			DBConnectionManager.releaseResources(pstmtAccountUpd, rset);
			DBConnectionManager.releaseResources(pstmtFSEStockUpd, rset);
			DBConnectionManager.releaseResources(pstmtRetStockUpd, rset);
		}
		return listReturn;
	}
	
	public List<DPHierarchyAcceptDTO> getStockDetails(String account_id, String role) throws DPServiceException 
	{
		PreparedStatement pstmtHierDtlUpd = null;
		ResultSet rs = null;
		String strReqType = null;
		List<DPHierarchyAcceptDTO> listReturn = new ArrayList<DPHierarchyAcceptDTO>();
		
		List<DPHierarchyAcceptDTO> viewHierarchyListDTO	= new ArrayList<DPHierarchyAcceptDTO>();
		DPHierarchyAcceptDTO  viewHierarchyDto = null;
		
		
		try
		{
			if(role.equalsIgnoreCase(Constants.INTER_SSD_FSE_TRANSFER))
			{
				pstmtHierDtlUpd = connection.prepareStatement(DBQueries.SQL_STOCK_DETAILS_FSE);
				pstmtHierDtlUpd.setInt(1, new Integer(account_id));
				pstmtHierDtlUpd.setInt(2, new Integer(account_id));
			}
			else
			{
				pstmtHierDtlUpd = connection.prepareStatement(DBQueries.SQL_STOCK_DETAILS_RET);
				pstmtHierDtlUpd.setInt(1, new Integer(account_id));
			}
			
			rs= pstmtHierDtlUpd.executeQuery();
			viewHierarchyListDTO = new ArrayList<DPHierarchyAcceptDTO>();
			while(rs.next())
			{
				viewHierarchyDto = new DPHierarchyAcceptDTO();
				viewHierarchyDto.setStockQty(rs.getInt("STOCK"));
				viewHierarchyDto.setStrAccountName(rs.getString("ACCOUNT_NAME"));
				viewHierarchyDto.setProductName(rs.getString("PRODUCT_NAME"));
				viewHierarchyDto.setStrRole(rs.getString("ACCOUNT_LEVEL"));
				
				listReturn.add(viewHierarchyDto);
				viewHierarchyDto = null;
			}
			
			
			/*for(int i=0; i<arrCheckedTR.length; i++)
			{
				pstmtHierDtlReq.setString(1, arrCheckedTR[i]);
				pstmtHierDtlReq.setString(2, Constants.HIER_TRANS_FORWARD);
				pstmtHierDtlReq.setLong(3, loginUserId);
				rset = pstmtHierDtlReq.executeQuery();
				
				while(rset.next())
				{
					strReqType = rset.getString("TRANS_TYPE");
					
					if(strReqType.equals(Constants.HEIR_TRANS_TYPE_FSE))
					{
						pstmtAccountUpd.setLong(1, loginUserId);
						pstmtAccountUpd.setInt(2, rset.getInt("TRNS_ACCOUNT_ID"));
						
						pstmtAccountUpd.executeUpdate();
					}
					else
					{
						pstmtAccountUpd.setInt(1, rset.getInt("TO_FSE"));
						pstmtAccountUpd.setInt(2, rset.getInt("TRNS_ACCOUNT_ID"));
						
						pstmtAccountUpd.executeUpdate();
					}
				}
				
				pstmtHierDtlUpd.setString(1, Constants.HEIR_TRANS_STATUS_ACPT);
				pstmtHierDtlUpd.setString(2, arrCheckedTR[i]);
				pstmtHierDtlUpd.setLong(3, loginUserId);
				pstmtHierDtlUpd.setString(4, Constants.HIER_TRANS_FORWARD);
				
				pstmtHierDtlUpd.executeUpdate();
			}*/
			
			
			//listReturn = getHierarchyTransferInitDao(loginUserId);			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while Accepting Hierarchy Transfer  ::  "+e.getMessage());
			throw new DPServiceException(e.getMessage());			
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtHierDtlUpd, rs);
		}
		return listReturn;
	}
	
}
