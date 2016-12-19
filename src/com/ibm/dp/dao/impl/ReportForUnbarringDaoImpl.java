package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.ReportForUnbarringDao;
import com.ibm.dp.dto.ReportForUnbarringDto;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class ReportForUnbarringDaoImpl extends BaseDaoRdbms implements ReportForUnbarringDao{

	private Logger logger = Logger.getLogger(ReportForUnbarringDaoImpl.class.getName());
	
	public ReportForUnbarringDaoImpl(Connection con) {
		super(con);
	}
	
	public ArrayList getReportData() throws DAOException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ReportForUnbarringDto> serialNos = null;
		con = DBConnectionManager.getDBConnection(); // db2
		ReportForUnbarringDto unbarring = null;
		try {
			serialNos = new ArrayList<ReportForUnbarringDto>();
			ps = con.prepareStatement(DBQueries.GET_UNBARRED_SERIAL_NOS);
			rs = ps.executeQuery();
			while(rs.next()){
				unbarring = new ReportForUnbarringDto();
				unbarring.setSerialNo(rs.getString("IMEI_SIM_NO"));
				serialNos.add(unbarring);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				/* Close the statement, resultset */
				DBConnectionManager.releaseResources(ps, rs);
				//Added by Shilpa khanna to release DB connection
				DBConnectionManager.releaseResources(con);
			}
		return serialNos;
	}
	public void updateUnbarredStatus(ArrayList serialNo) throws DAOException{
		Connection con = null;
		PreparedStatement ps = null;
		String serialNos = null;
		int i = 0;
		con = DBConnectionManager.getDBConnection(); // db2
		try {
			for(i=0; i < serialNo.size(); i++){
				serialNos = ((ReportForUnbarringDto)serialNo.get(i)).getSerialNo();
				ps = con.prepareStatement(DBQueries.UPDATE_UNBARRED_STATUS);
				ps.setString(1, serialNos);
				int result = ps.executeUpdate();
			}
			con.commit();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			DBConnectionManager.releaseResources(ps, null);
//			Added by Shilpa khanna to release DB connection
			DBConnectionManager.releaseResources(con);
		}
	}
}
