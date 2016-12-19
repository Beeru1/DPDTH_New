package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.WarehouseMstFrmBotreeDao;
import com.ibm.dp.dto.WarehouseMstFrmBotreeDTO;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;


public class WarehouseMstFrmBotreeDaoImpl extends BaseDaoRdbms implements WarehouseMstFrmBotreeDao {

	Logger logger = Logger.getLogger(WarehouseMstFrmBotreeDaoImpl.class
			.getName());

	public WarehouseMstFrmBotreeDaoImpl(Connection connection) {
		super(connection);
	}

	public int fetchCircleId(String circleCode) throws DAOException {
		int circleId = -1;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = connection.prepareStatement(DBQueries.SQL_Fetch_Circle_ID);
			pstmt.setString(1, circleCode.toUpperCase());
			rset = pstmt.executeQuery();
			while (rset.next()) {
				circleId = rset.getInt("CIRCLE_ID");
			}
			logger.info("circleId == " + circleId);
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();

			logger
					.error(
							"::::::::::::::::::: Error in WarehouseMstFrmBotreeDaoImpl in fetchCircleId WebService -------->",
							e);

			circleId = -1;

			try {
				connection.rollback();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				logger
						.error(
								":::::::::::::::::::  Error in WarehouseMstFrmBotreeDaoImpl in fetchCircleId WebService -------->",
								sqle);
			}
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return circleId;
	}

	
	
	public boolean validateWareHouseCode(String wareHouseCode)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		boolean flag = false;
		try{
			
			logger.info("inside --- validateWareHouseCode == "+wareHouseCode);
			pstmt = connection.prepareStatement(DBQueries.SQL_SELECT_WAREHOUSE_CODE);
			pstmt.setString(1,wareHouseCode);
			rs =pstmt.executeQuery();
			if(rs.next()){
				
				count = rs.getInt(1);
			}
			if(count >=1)
				flag =  true;
			else
				flag =  false;
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateWareHouseCode method--"+e.getMessage());
		}
		return flag;
	}
	
	
	public String[] updateWhtMasterData(WarehouseMstFrmBotreeDTO whDTO)throws DAOException 
	{
		String[] servMsg = new String[2];
		servMsg[0]="SUCCESS";
		servMsg[1]="";
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		ResultSet rs=null;
		boolean flag=true;
		try
		{
			int circleId = fetchCircleId(whDTO.getCircleCode());
		
			if(circleId != -1)
			{
				if(whDTO.getIsEdit().equalsIgnoreCase("0"))
				{
					if(!validateWareHouseCode(whDTO.getWhCode().trim())){
						logger.info("idEdit == " + whDTO.getIsEdit());
						pstmt = connection.prepareStatement(DBQueries.SQL_INSERT_WHT_MST);
						
						pstmt.setString(1, whDTO.getWhCode());
						pstmt.setString(2, whDTO.getWhName());
						pstmt.setString(3, whDTO.getWhAddress1());
						pstmt.setString(4, whDTO.getWhAddress2());
						pstmt.setString(5, whDTO.getWhPhone());
						pstmt.setString(6, whDTO.getWhFax());
						pstmt.setString(7, whDTO.getTinNumber());
						pstmt.setInt(8, whDTO.getWhType());
						pstmt.setInt(9, circleId);
						pstmt.setString(10, whDTO.getCompCode());
						pstmt.setString(11, whDTO.getArea());
						pstmt.setString(12, whDTO.getSubArea());
						pstmt.setString(13, whDTO.getSrcType());
						pstmt.setString(14, whDTO.getDefCompCode());
						pstmt.setString(15, whDTO.getDefArea());
						pstmt.setString(16, whDTO.getDefSubArea());
						pstmt.setString(17, whDTO.getDefSrcType());
						pstmt.setString(18, whDTO.getChrunCompCode());
						pstmt.setString(19, whDTO.getChrunArea());
						pstmt.setString(20, whDTO.getChrunSubArea());
						pstmt.setString(21, whDTO.getChrunSrcType());
						pstmt.setString(22, whDTO.getInTransCompCode());
						pstmt.setString(23, whDTO.getInTransArea());
						pstmt.setString(24, whDTO.getInTransSubArea());
						pstmt.setString(25, whDTO.getInTransSrcType());
						pstmt.setString(26, whDTO.getNonRepCompCode());
						pstmt.setString(27, whDTO.getNonRepArea());
						pstmt.setString(28, whDTO.getNonRepSubArea());
						pstmt.setString(29, whDTO.getNonRepSrcType());
						pstmt.setString(30, whDTO.getIsActive());
						pstmt.setTimestamp(31, Utility.getCurrentTimeStamp());
						pstmt.setTimestamp(32, Utility.getCurrentTimeStamp());
						pstmt.setString(33, whDTO.getIsEdit());
						pstmt.setString(34, whDTO.getEmployee());
						pstmt.executeUpdate();
						servMsg[0] ="SUCCESS";
						servMsg[1] ="SUCCESS";
					}else{
						servMsg[0] ="FAILURE";
						servMsg[1] =com.ibm.dp.common.Constants.WH_MST_BOtree_WS_EXIST;
					}
					
				}
				else if(whDTO.getIsEdit().equalsIgnoreCase("1"))
				{
					logger.info("idEdit == " + whDTO.getIsEdit());
					if(whDTO.getIsActive() != null && whDTO.getIsActive().equalsIgnoreCase("0"))
					{
						pstmt5 = connection.prepareStatement(DBQueries.SQL_CHECK_WH_MASTER);
						pstmt5.setString(1, whDTO.getWhCode());
						rs=pstmt5.executeQuery();
						if(rs.next())
						{
							servMsg[0] ="FAILURE";
							servMsg[1] =com.ibm.dp.common.Constants.WH_MST_BOtree_WS_MAP_DIST;
							flag=false;
						}
						DBConnectionManager.releaseResources(null, rs);
					}
					
					if(flag)  {
							pstmt2 = connection
							.prepareStatement(DBQueries.SQL_UPDATE_WHT_MST);
							pstmt2.setString(1, whDTO.getWhName());
							pstmt2.setString(2, whDTO.getWhAddress1());
							pstmt2.setString(3, whDTO.getWhAddress2());
							pstmt2.setString(4, whDTO.getWhPhone());
							pstmt2.setString(5, whDTO.getWhFax());
							pstmt2.setString(6, whDTO.getTinNumber());
							pstmt2.setInt(7, whDTO.getWhType());
							pstmt2.setInt(8, circleId);
							pstmt2.setString(9, whDTO.getCompCode());
							pstmt2.setString(10, whDTO.getArea());
							pstmt2.setString(11, whDTO.getSubArea());
							pstmt2.setString(12, whDTO.getSrcType());
							pstmt2.setString(13, whDTO.getDefCompCode());
							pstmt2.setString(14, whDTO.getDefArea());
							pstmt2.setString(15, whDTO.getDefSubArea());
							pstmt2.setString(16, whDTO.getDefSrcType());
							pstmt2.setString(17, whDTO.getChrunCompCode());
							pstmt2.setString(18, whDTO.getChrunArea());
							pstmt2.setString(19, whDTO.getChrunSubArea());
							pstmt2.setString(20, whDTO.getChrunSrcType());
							pstmt2.setString(21, whDTO.getInTransCompCode());
							pstmt2.setString(22, whDTO.getInTransArea());
							pstmt2.setString(23, whDTO.getInTransSubArea());
							pstmt2.setString(24, whDTO.getInTransSrcType());
							pstmt2.setString(25, whDTO.getNonRepCompCode());
							pstmt2.setString(26, whDTO.getNonRepArea());
							pstmt2.setString(27, whDTO.getNonRepSubArea());
							pstmt2.setString(28, whDTO.getNonRepSrcType());
							pstmt2.setString(29, whDTO.getIsActive());
							pstmt2.setTimestamp(30, Utility.getCurrentTimeStamp());
							pstmt2.setString(31, whDTO.getIsEdit());
							pstmt2.setString(32, whDTO.getEmployee());
							pstmt2.setString(33, whDTO.getWhCode());
							int updaterowcount = pstmt2.executeUpdate();
							logger.info("updaterowcount == " + updaterowcount);
							if(updaterowcount>=1)
							{
								servMsg[0] ="SUCCESS";
								servMsg[1] =com.ibm.dp.common.Constants.WH_MST_BOtree_WS_STATUS_1;
								//return servMsg;
							}
							else
							{
								servMsg[0] ="FAILURE";
								servMsg[1] =com.ibm.dp.common.Constants.WH_MST_BOtree_WS_NOT_EXIST;
							//return servMsg;
							}
					}
				}
				if(servMsg[0].equalsIgnoreCase("SUCCESS"))
				{
					logger.info("ARTI((((((***********111111111111111*******"+whDTO.getEmployee());
					if(!whDTO.getEmployee().equalsIgnoreCase("") && !whDTO.getEmployee().equalsIgnoreCase("null"))
					{
						if(whDTO.getEmployee().contains(","))
						{
							logger.info("ARTI((((((*********not*22222222222222********"+whDTO.getEmployee());
							String []empCode = whDTO.getEmployee().split(",");
							pstmt3 = connection.prepareStatement(DBQueries.SQL_DELETE_WHE_MST);
							pstmt3.setString(1, whDTO.getWhCode());
							pstmt3.executeUpdate();
							for(int i =0;i<empCode.length;i++)
							{
								String empCod = empCode[i];
								pstmt4 = connection.prepareStatement(DBQueries.SQL_INSERT_WHE_MST);
								pstmt4.setString(1, whDTO.getWhCode());
								pstmt4.setString(2, empCod);
								pstmt4.executeUpdate();
							}
						}
						else
						{
							logger.info("ARTI((((((***********333333333333333*******"+whDTO.getEmployee());
							pstmt4 = connection.prepareStatement(DBQueries.SQL_INSERT_WHE_MST);
							pstmt4.setString(1, whDTO.getWhCode());
							pstmt4.setString(2, whDTO.getEmployee());
							pstmt4.executeUpdate();
						}
					}
				}
			}
			else
			{
				servMsg[0] = "FAILURE";
				servMsg[1] = com.ibm.dp.common.Constants.WH_MST_BOtree_CIRCLE_NOT_EXIST;
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			servMsg[0] = "FAILURE";
			servMsg[1] = e.getMessage();
		}
		catch (Exception e) 
		{
			e.printStackTrace();

			logger.error("::::::::::::::::::: Error in WarehouseMstFrmBotreeDaoImpl in updateWhtMasterData WebService -------->",e);
			servMsg[0] = "OTHERS";
		} 
		finally 
		{
			DBConnectionManager.releaseResources(pstmt, null);
			DBConnectionManager.releaseResources(pstmt2, null);
			DBConnectionManager.releaseResources(pstmt3, null);
			DBConnectionManager.releaseResources(pstmt4, null);
			DBConnectionManager.releaseResources(pstmt5, null);
		}
		return servMsg;
	}
}
