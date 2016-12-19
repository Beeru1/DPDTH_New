package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.core.exception.DAOException;
import com.ibm.dp.beans.DebitAmountMstrFormBean;
import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DebitAmountMasterDao;
import com.ibm.dp.dao.RecoDetailReportDao;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

 
public class DebitAmountMasterDaoImpl implements  DebitAmountMasterDao{
	
	private static Logger logger = Logger.getLogger(DebitAmountMasterDaoImpl.class);	
	//public static final String SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL = DBQueries.SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL_RECO;
	//public static final String SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL1 = DBQueries.SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL_RECO1;
	//public static final String SQL_SELECT_ALL_DIST_CIRCLE="select ACCOUNT_ID from VR_ACCOUNT_DETAILS where CIRCLE_ID=? and ACCOUNT_LEVEL=6 with ur";
	
	private static DebitAmountMasterDao debitAmountMasterDao = new DebitAmountMasterDaoImpl();
	private DebitAmountMasterDaoImpl(){}
	public static DebitAmountMasterDao getInstance() {
		return debitAmountMasterDao;
	}
	
	public List<DebitAmountMstrFormBean> getProductList() throws com.ibm.dp.exception.DAOException 
	{
		List<DebitAmountMstrFormBean> listReturn = new ArrayList<DebitAmountMstrFormBean>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			pstmt = connection.prepareStatement("SELECT ID,VALUE FROM DP_CONFIGURATION_DETAILS WHERE CONFIG_ID=8 WITH UR");
			rset = pstmt.executeQuery();
			DebitAmountMstrFormBean debitAmountMstrFormBean = null;
			while(rset.next())
			{
				debitAmountMstrFormBean = new DebitAmountMstrFormBean();
				debitAmountMstrFormBean.setProductId(rset.getString("ID"));
				debitAmountMstrFormBean.setProductType(rset.getString("VALUE"));
				
				listReturn.add(debitAmountMstrFormBean);
				debitAmountMstrFormBean = null;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while getting circle list for reco report  ::  "+e);
			logger.info("Exception while getting circle list for reco report  ::  "+e.getMessage());
			throw new com.ibm.dp.exception.DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}

	public boolean insertDebitAmount(DebitAmountMstrFormBean debitAmountMstrFormBean) throws com.ibm.dp.exception.DAOException {

		boolean booMessage=false;
		int intMessage=0;
		float amount;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
	
			amount=Float.parseFloat(debitAmountMstrFormBean.getAmount());
			
			
			if(prodExists(debitAmountMstrFormBean))
				
			{
				
				pstmt = connection.prepareStatement("update DEBIT_AMOUNT_MSTR set DEBIT_AMOUNT=?, UPDATED_DT=current timestamp, UPDATED_BY =? where PRODUCT_TYPE=? with ur");
				
				pstmt.setFloat(1, amount);
				pstmt.setString(2, debitAmountMstrFormBean.getUpdatedBy());
				pstmt.setString(3, debitAmountMstrFormBean.getProductType());
			}
			
			else
			{
				
			pstmt = connection.prepareStatement("INSERT INTO DEBIT_AMOUNT_MSTR(PRODUCT_TYPE,DEBIT_AMOUNT,UPDATED_DT,UPDATED_BY) values (?,?,current timestamp,?) WITH UR");
			pstmt.setString(1, debitAmountMstrFormBean.getProductType());
			pstmt.setFloat(2, amount);
			pstmt.setString(3, debitAmountMstrFormBean.getUpdatedBy());
			
			}
			
			
			intMessage=pstmt.executeUpdate();
			
			if(intMessage==1)
			{
				booMessage=true;
			}
			
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while inserting amount for Debit Amount Master  ::  "+e);
			logger.info("Exception while  inserting amount for Debit Amount Master ::  "+e.getMessage());
			throw new com.ibm.dp.exception.DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return booMessage;
	}
	
	
	public String getDebitAmount(DebitAmountMstrFormBean debitAmountMstrFormBean) throws com.ibm.dp.exception.DAOException {

		String amount="";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
	
			
			pstmt = connection.prepareStatement("SELECT * FROM DEBIT_AMOUNT_MSTR WHERE PRODUCT_TYPE=? WITH UR");
			pstmt.setString(1, debitAmountMstrFormBean.getProductType());
			rset=pstmt.executeQuery();
			
			while(rset.next())
			{
				amount=rset.getString("DEBIT_AMOUNT");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while inserting amount for Debit Amount Master  ::  "+e);
			logger.info("Exception while  inserting amount for Debit Amount Master ::  "+e.getMessage());
			throw new com.ibm.dp.exception.DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return amount;
	}
	
	
	
	public boolean prodExists(DebitAmountMstrFormBean debitAmountMstrFormBean) throws com.ibm.dp.exception.DAOException {

		boolean prodExists=false;
		String amount="";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			logger.info(":::::::::::::::::::::inside prodExists method::::::::::");
			connection = DBConnectionManager.getDBConnection();
	
			
			pstmt = connection.prepareStatement("SELECT PRODUCT_TYPE,DEBIT_AMOUNT FROM DEBIT_AMOUNT_MSTR WHERE PRODUCT_TYPE=? WITH UR");
			pstmt.setString(1, debitAmountMstrFormBean.getProductType());
			rset=pstmt.executeQuery();
			
			while(rset.next())
			{
				amount=rset.getString("DEBIT_AMOUNT");
				prodExists=true;
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while inserting amount for Debit Amount Master  ::  "+e);
			logger.info("Exception while  inserting amount for Debit Amount Master ::  "+e.getMessage());
			throw new com.ibm.dp.exception.DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return prodExists;
	}
	
	
}


