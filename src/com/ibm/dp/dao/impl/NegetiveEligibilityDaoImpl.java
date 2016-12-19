package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.ActivationDetailReportDao;
import com.ibm.dp.dao.NegetiveEligibilityDao;
import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.dto.NegetiveEligibilityDTO;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

 
public class NegetiveEligibilityDaoImpl implements  NegetiveEligibilityDao {
	
	private static Logger logger = Logger.getLogger(NegetiveEligibilityDaoImpl.class);	
	private static NegetiveEligibilityDao  negetiveEligibilityDao  = new NegetiveEligibilityDaoImpl();
	private NegetiveEligibilityDaoImpl(){}
	public static NegetiveEligibilityDao getInstance() {
		return negetiveEligibilityDao;
	}
	public List<NegetiveEligibilityDTO> getNegetiveEligibilityReport(String circleIds) throws DAOException {
		PreparedStatement ps = null;
		Connection con = DBConnectionManager.getDBConnection();
		ResultSet rs = null; 		
	
		List<NegetiveEligibilityDTO> reportStockDataList= new ArrayList<NegetiveEligibilityDTO>();				
		try {
					
			String sql = "SELECT distinct Dist_ID,(Select ACCOUNT_NAME from VR_ACCOUNT_DETAILS where Account_ID=Dist_ID) As DIST_NAME,CIRCLE_ID," +
					" (Select LOGIN_NAME from VR_LOGIN_MASTER where Login_ID = DIST_ID) As DIST_OLM_ID, ELIGIBLE_AMOUNT," +
					" (Select Days(max(ELIGIBLE_DATE))-Days(min(ELIGIBLE_DATE)) FROM DP_DIST_DAILY_ELIGIBILITY " +
					" where ELIGIBLE_AMOUNT<0 and DIST_ID=DP.DIST_ID) as Ageing FROM DP_DIST_DAILY_ELIGIBILITY DP" +
					" where ELIGIBLE_DATE =(Select max(ELIGIBLE_DATE) from DP_DIST_DAILY_ELIGIBILITY where DIST_ID = DP.DIST_ID) and ELIGIBLE_AMOUNT<0 ";
			
			if(circleIds != null && !circleIds.equals("")) {		
				if(!circleIds.contains(",")){
					sql+= " AND CIRCLE_ID = "+ circleIds;
				}else{					
					sql+= " AND CIRCLE_ID IN("+ circleIds +")";
				}				
			}
	
			ps =  con.prepareStatement(sql);			
			logger.info(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				NegetiveEligibilityDTO nDTO = new NegetiveEligibilityDTO();
				nDTO.setDistName(rs.getString("DIST_NAME"));
				nDTO.setDistOlmID(rs.getString("DIST_OLM_ID"));
				String eAmt= rs.getString("ELIGIBLE_AMOUNT");
				nDTO.setEligibleAmt(eAmt);
				String againgDaysStr = rs.getString("Ageing");
				int againgDays = 0;
				if(againgDaysStr!=null && !(againgDaysStr.equals(""))){
					againgDays = Integer.parseInt(againgDaysStr);
				}
				if(againgDays<=30){
				nDTO.setFirstThirty(eAmt);		
				}
				else if(againgDays<=60){
					nDTO.setSecondThirty(eAmt);		
					}
				else if(againgDays<=90){
					nDTO.setThirdThirty(eAmt);		
					}
				else if(againgDays<=120){
					nDTO.setFourthThirty(eAmt);		
					}
				else if(againgDays>120){
					nDTO.setFifthThirty(eAmt);		
					}							
				reportStockDataList.add(nDTO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Negative Eligibility Report Details. Exception message: "
					+ e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs );
			DBConnectionManager.releaseResources(con);		
		}
		return reportStockDataList;
		
	}
	
	
}


