package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.ibm.dp.common.DBQueries;
import com.ibm.virtualization.recharge.beans.MinReorderFormBean;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.MinReorderDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;


/**
 * @author vivek kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class MinReorderDaoImpl extends BaseDaoRdbms implements MinReorderDao {
	protected static Logger logger = Logger.getLogger(MinReorderDaoImpl.class.getName());
	
	protected static final String SQL_DISTRIBUTOR_KEY = "SQL_DISTRIBUTOR";
	protected static final String SQL_DISTRIBUTOR = DBQueries.SQL_DISTRIBUTOR;
	
	protected static final String SQL_PRODUCT_KEY = "SQL_PRODUCT";
	protected static final String SQL_PRODUCT = DBQueries.SQL_PRODUCT_REORDER;
	
	protected static final String SQL_ASSIGN_CHECK_KEY = "SQL_ASSIGN_CHECK";
	protected static final String SQL_ASSIGN_CHECK = DBQueries.SQL_PRODUCT_CHECK;
	 
	protected static final String SQL_ASSIGN_REORDER_KEY = "SQL_ASSIGN_REORDER";
	protected static final String SQL_ASSIGN_REORDER = DBQueries.SQL_PRODUCT_ASSIGN_REORDER;
	
	protected static final String SQL_ASSIGN_UPDATE_KEY = "SQL_ASSIGN_UPDATE";
	protected static final String SQL_ASSIGN_UPDATE = DBQueries.SQL_PRODUCT_ORDER_UPDATE;

	

	public MinReorderDaoImpl(Connection con) {
		super(con);
		queryMap.put(SQL_DISTRIBUTOR_KEY,SQL_DISTRIBUTOR);
		queryMap.put(SQL_PRODUCT_KEY, SQL_PRODUCT);
		queryMap.put(SQL_ASSIGN_REORDER_KEY, SQL_ASSIGN_REORDER);
		queryMap.put(SQL_ASSIGN_CHECK_KEY, SQL_ASSIGN_CHECK);
		queryMap.put(SQL_ASSIGN_UPDATE_KEY, SQL_ASSIGN_UPDATE);
	}
	

	public void insert (MinReorderFormBean minmreodr)throws DAOException {
	
		PreparedStatement pst = null;
		
		
		try {
			
			DAOFactory factory= DAOFactory.getDAOFactory(DAOFactory.DB2) ;
	
			 int pscount = 0;
			 pst = connection.prepareStatement(queryMap.get(SQL_ASSIGN_CHECK_KEY));
			 pst.setInt(++pscount, minmreodr.getAccountid());
			 pst.setInt(++pscount, minmreodr.getProductid());
			 ResultSet rst = pst.executeQuery();
			 pscount = 0;
			 if(!rst.next()){
				 
				 pst = connection.prepareStatement(queryMap.get(SQL_ASSIGN_REORDER_KEY));
			     pst.setInt(++pscount, minmreodr.getAccountid());
        		 pst.setInt(++pscount,minmreodr.getProductid());
				 pst.setInt(++pscount, minmreodr.getMinreorder());
				 pst.setString(++pscount, minmreodr.getCreatedby());
				
					  
					  			
					 pst.executeUpdate();	
			 }
			 else {
				 pst =  connection.prepareStatement(queryMap.get(SQL_ASSIGN_UPDATE_KEY));
				 int pscount1 = 0;
				 pst.setInt(++pscount1, minmreodr.getMinreorder());
				 pst.setString(++pscount1, minmreodr.getCreatedby());
				 pst.setInt(++pscount1, minmreodr.getAccountid());
				  
				  			
				 pst.executeUpdate();
			 }
									  
				  connection.commit();
				  
		
		}
		catch (SQLException e) {
			logger.error("Exception occured while reteriving.Assign Product"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}finally{	
				DBConnectionManager.releaseResources((Connection) pst);
				DBConnectionManager.releaseResources(connection);
				
				
			}
}
			
			
			

	public ArrayList getDistributorDao(String circleId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		ArrayList list=null;
		try {
	
			DAOFactory factory= DAOFactory.getDAOFactory(DAOFactory.DB2) ;
			pstmt = connection.prepareStatement(queryMap.get(SQL_DISTRIBUTOR_KEY));
			pstmt.setString(1,circleId);
			rs = pstmt.executeQuery();
			list =  fetchSingleResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while selecting distributor."
					+ "Exception Message: "
					+ e.getMessage());
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while selecting Distributor List."
					+ "Exception Message: "
					+ e.getMessage());
		} finally {
			try {
				DBConnectionManager.releaseResources(pstmt, rs);
			} catch (Exception e) {
				logger.error("**error in closing rs or stmt or connection for selecting Distributor List");
			}
		}
		return list;
	}
protected ArrayList fetchSingleResult(ResultSet rs) throws Exception {

		
		ArrayList<LabelValueBean> arrMasterData = new ArrayList();
		LabelValueBean lvb=null;
		while (rs.next()) {
			 lvb=new LabelValueBean(rs.getString("ACCOUNT_NAME"),rs.getString("ACCOUNT_ID"));
            arrMasterData.add(lvb);
		}  

		return arrMasterData;
	}

	public ArrayList getProductListDao(String userId) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		ArrayList list=null;

		
		
		try {
	
			DAOFactory factory= DAOFactory.getDAOFactory(DAOFactory.DB2) ;
			stmt = connection.createStatement();
			
		
			rs = stmt.executeQuery(queryMap.get(SQL_PRODUCT_KEY));
			list =  fetchProductResult(rs);
		
			
			connection.commit();
			
		
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while selecting distributor."
					+ "Exception Message: "
					+ e.getMessage());
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while selecting Distributor List."
					+ "Exception Message: "
					+ e.getMessage());
			
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt= null;
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				logger.error("**error in closing rs or stmt or connection for selecting Product List");
			}
		}
		return list;
	}
protected ArrayList fetchProductResult(ResultSet rs) throws Exception {

		
		ArrayList arrMasterData = new ArrayList();
		LabelValueBean lvb=null;
		while (rs.next()) {
			
			 lvb=new LabelValueBean(rs.getString("PRODUCT_NAME"),rs.getString("PRODUCT_ID"));
            arrMasterData.add(lvb);
	
		}  

		return arrMasterData;
	}


}
