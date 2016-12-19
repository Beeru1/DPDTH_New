package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import com.ibm.dp.beans.DPCardGrpMstrFormBean;
import com.ibm.dp.dao.CardGrpMstrDao;

import com.ibm.dp.dto.CardMstrDto;

import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

 
public class CardGrpMstrDaoImpl implements  CardGrpMstrDao{
	
	private static Logger logger = Logger.getLogger(CardGrpMstrDaoImpl.class);	

	private static CardGrpMstrDao cardGrpMstrDao = new CardGrpMstrDaoImpl();
	private CardGrpMstrDaoImpl(){}
	public static CardGrpMstrDao getInstance() {
		return cardGrpMstrDao;
	}
	
	
	public boolean insertCardGrp(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws com.ibm.dp.exception.DAOException {

		boolean booMessage=false;
		int intMessage=0;
	
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			
			logger.info("asa:::::card grp master::::::::::::::::::::::");
			connection = DBConnectionManager.getDBConnection();
	
			if(cardExists(dpCardGrpMstrFormBean))
				
			{
				logger.info("asa::::::::;;already exists");
				
				
				if(checkDuplicateCard(dpCardGrpMstrFormBean))
				{
					booMessage=false;
					dpCardGrpMstrFormBean.setCheckDuplicateFlag(true);
					logger.info("asa:::::::::is duploicate??:::"+dpCardGrpMstrFormBean.isCheckDuplicateFlag());
					return booMessage;
				}
				
				else
				{	
					pstmt = connection.prepareStatement("update DP_CARDGROUP_MASTER set CARDGROUPNAME=?, UPDATED_ON=current timestamp, UPDATED_BY =? where CARDGROUPID=? with ur");
					
					pstmt.setString(1, dpCardGrpMstrFormBean.getCardgrpName());
					pstmt.setString(2, dpCardGrpMstrFormBean.getCreatedBy());
					pstmt.setString(3, dpCardGrpMstrFormBean.getCardgrpId());
					intMessage=pstmt.executeUpdate();
				}
			}
			
			else
			
			{
				
			logger.info("asa::::::new cardgrp:::::::");
			/*List<Integer> al=new ArrayList<Integer>();
			pstmt2 = connection.prepareStatement("select vr.CIRCLE_ID from VR_CIRCLE_MASTER vr with ur");
			rset=pstmt2.executeQuery();
			while(rset.next())
			{
				al.add(rset.getInt(1));
			}
		//	System.out.println("hello vishwas"+al.size()+":"+al);
			if(al.size()>0)
			{
				for(Integer s:al)
				{
					String query="INSERT INTO DP_CARDGROUP_MASTER(CARDGROUPID,CARDGROUPNAME,CREATEDDATE,CREATEDBY,STATUS,CIRCLEID,BT_CARDGROUPID) values (?,?,current timestamp,?,'A',"+s+",CARDGROUP_ID.NEXTVAL) WITH UR";
			//		System.out.println("hello vishwas query"+query);
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, dpCardGrpMstrFormBean.getCardgrpId());
					pstmt.setString(2, dpCardGrpMstrFormBean.getCardgrpName());
					pstmt.setString(3, dpCardGrpMstrFormBean.getCreatedBy());
					intMessage=pstmt.executeUpdate();
				}
			}*/
			pstmt = connection.prepareStatement("INSERT INTO DP_CARDGROUP_MASTER(CARDGROUPID,CARDGROUPNAME,CREATEDDATE,CREATEDBY,STATUS) values (?,?,current timestamp,?,'A') WITH UR");
			pstmt.setString(1, dpCardGrpMstrFormBean.getCardgrpId());
			pstmt.setString(2, dpCardGrpMstrFormBean.getCardgrpName());
			pstmt.setString(3, dpCardGrpMstrFormBean.getCreatedBy());
			intMessage=pstmt.executeUpdate();
			}
			
			
			
			//intMessage=pstmt.executeUpdate();
			
			if(intMessage==1)
			{
				booMessage=true;
			}
			
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while inserting amount for card grp Master  ::  "+e);
			logger.info("Exception while  inserting amount for card grp Master ::  "+e.getMessage());
			throw new com.ibm.dp.exception.DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return booMessage;
	}
	
	
	
	
	public boolean deleteCardGrp(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws com.ibm.dp.exception.DAOException {

		boolean booMessage=false;
		int intMessage=0;
		float amount;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			
			logger.info("asa:::::card grp master-----delete::::::::::::::::::::::");
			connection = DBConnectionManager.getDBConnection();
				
				pstmt = connection.prepareStatement("update DP_CARDGROUP_MASTER set STATUS=?,updated_by=?, updated_on=current timestamp where CARDGROUPID=? with ur");
				pstmt.setString(1, dpCardGrpMstrFormBean.getStatus());
				pstmt.setString(2, dpCardGrpMstrFormBean.getCreatedBy());
				pstmt.setString(3, dpCardGrpMstrFormBean.getCardgrpId());
				
				intMessage=pstmt.executeUpdate();
				
				logger.info("asa:::::::::::intMessage:::::::::"+intMessage);
				if(intMessage>0)
				booMessage=true;
						
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while deleting Card Group Master Master  ::  "+e);
			logger.info("Exception while deleting Card Group Master Master  ::  "+e.getMessage());
			throw new com.ibm.dp.exception.DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return booMessage;
	}
	//ADDED BY VISHWAS
public ArrayList<CardMstrDto> select(CardMstrDto dpvpDTO, int lowerBound, int upperBound) throws com.ibm.dp.exception.DAOException {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		CardMstrDto dto = null;
		ArrayList<CardMstrDto> productList = new ArrayList<CardMstrDto>();
		Connection con = null;
		StringBuffer sql = null;
		int pscount=0;
		try {
		
			con = DBConnectionManager.getDBConnection();
				pst = con.prepareStatement("SELECT  CARDGROUPID,CARDGROUPNAME,STATUS FROM (select t.*,row_number() over (order by id asc) as rownumber from DP_CARDGROUP_MASTER t) where rownumber between ? and ? with ur");
				pst.setInt(++pscount, lowerBound);
				pst.setInt(++pscount, upperBound);
				rs = pst.executeQuery();				
				while(rs.next())
				{
					
					dto= new CardMstrDto();
					dto.setCardgrpId(rs.getString("CARDGROUPID"));
					dto.setCardgrpName(rs.getString("CARDGROUPNAME"));
					dto.setStatus(rs.getString("STATUS"));
					
					productList.add(dto);
					
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
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(pst, rs);
		}
		logger.info("asa:::::::::cardMstrList::::::"+productList);
		
		return productList;
		}

	
	//END BY VISHWAS
	public List getCardGroupList() throws com.ibm.dp.exception.DAOException {

	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		List<CardMstrDto> cardMstrList= new ArrayList<CardMstrDto>();
		CardMstrDto objCardMstrDto=null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
	
			
			pstmt = connection.prepareStatement("SELECT CARDGROUPID,CARDGROUPNAME,STATUS FROM DP_CARDGROUP_MASTER with ur");
			rset=pstmt.executeQuery();
			
			while(rset.next())
			{
				objCardMstrDto=new CardMstrDto();
				objCardMstrDto.setCardgrpId(rset.getString("CARDGROUPID"));
				objCardMstrDto.setCardgrpName(rset.getString("CARDGROUPNAME"));
				objCardMstrDto.setStatus(rset.getString("STATUS"));
				
				cardMstrList.add(objCardMstrDto);
				
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
		logger.info("asa:::::::::cardMstrList::::::"+cardMstrList);
		return cardMstrList;
	}
	
	
	
	public boolean cardExists(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws com.ibm.dp.exception.DAOException {

		boolean cardExists=false;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			logger.info(":::::::::::::::::::::inside cardExists method::::::::::");
			connection = DBConnectionManager.getDBConnection();
	
			
			pstmt = connection.prepareStatement("SELECT * FROM DP_CARDGROUP_MASTER WHERE CARDGROUPID=? WITH UR");
			pstmt.setString(1, dpCardGrpMstrFormBean.getCardgrpId());
			rset=pstmt.executeQuery();
			
			while(rset.next())
			{
				cardExists=true;
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
		logger.info("asa::::::::::cardExists::::::"+cardExists);
		return cardExists;
	}
	
	

	public boolean checkDuplicateCard(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws com.ibm.dp.exception.DAOException {

		boolean isDuplicateCard=false;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			logger.info(":::::::::::::::::::::inside cardExists method::::::::::");
			connection = DBConnectionManager.getDBConnection();
	
			
			pstmt = connection.prepareStatement("SELECT * FROM DP_CARDGROUP_MASTER WHERE CARDGROUPID=? and CARDGROUPNAME=? WITH UR");
			pstmt.setString(1, dpCardGrpMstrFormBean.getCardgrpId());
			pstmt.setString(2, dpCardGrpMstrFormBean.getCardgrpName());
			rset=pstmt.executeQuery();
			
			while(rset.next())
			{
				isDuplicateCard=true;
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
		logger.info("asa::::::::::cardExists::::::"+isDuplicateCard);
		return isDuplicateCard;
	}
	
}


