/*
 * Created on Jul 18, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;
import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
/**
 * @author IBM
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PopulateComboDAO {
	/** 
	 * Logger for this class. Use logger.log(message) for logging. Refer to @link http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html for logging options and configuration.
	 **/ 

	private static final Logger logger = Logger.getLogger(PopulateComboDAO.class);
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList buildGroupList() throws Exception{
		ArrayList groupList=new ArrayList();
		Connection conn=null;
		Statement stmt=null;
		ResultSet resultSet=null;
		try{
			conn=DBConnection.getDBConnection();
			stmt=conn.createStatement();
			boolean flag=true;
			resultSet=stmt.executeQuery("select * from V_GROUP_MSTR ");
			if(flag){
				while((resultSet.next())){		
					LabelValueBean lvBean = new LabelValueBean(resultSet.getString("GROUP_ID"),resultSet.getString("GROUP_NAME"));
					groupList.add(lvBean);
				} 
				flag=false;
			}else{
				throw new Exception("No Group Exist in V_GROUP_MSTR.");
			}
		}finally{
			try{
				if(resultSet != null ){resultSet.close();}
				if(stmt != null){stmt.close();}
				if(conn != null){conn.close();}
			}catch(SQLException sqe){
				throw new SQLException("Exception occur while closing resources."+sqe.toString());
			}
		}



		return groupList;
	}  // buildCircleList



	/*	*//**
	 * This method makes a HashMap for status combobox
	 * in V_USER_MSTR FORM
	 * @return HashMap
	 * @throws Exception
	 *//*
	public ArrayList buildStatusList() throws Exception{
		ArrayList statusList=new ArrayList();

	//	HashMap statusMap=new HashMap();
		LabelValueBean lvBean1 = new LabelValueBean("A","Active");
		LabelValueBean lvBean2 = new LabelValueBean("D","De-Active");
		statusList.add(lvBean1);
		statusList.add(lvBean2);
		return statusList;
	}
	  */

	/*public ArrayList buildCircleList() throws Exception{

	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	ArrayList circleList=new ArrayList();
	try{
		conn=ConnectionFactory.getDBConnection();
		stmt=conn.createStatement();
		boolean flag=true;
		rs=stmt.executeQuery("select * from V_CIRCLE_MSTR ");
		if(flag){

			while((rs.next())){		
				logger.info("buildCircleList............"+rs.getString("CIRCLE_ID")+"....CIRCLE NAME..........."+rs.getString("CIRCLE_NAME"));
			//circleMap.put(rs.getString("CIRCLE_ID"),rs.getString("CIRCLE_NAME"));
				LabelValueBean lvBean = new LabelValueBean(rs.getString("CIRCLE_ID"),rs.getString("CIRCLE_NAME"));
				circleList.add(lvBean);
			} 
			flag=false;// while ends
		}else{
			throw new Exception("No Circle Exist in V_CIRCLE_MSTR.");
		}
	}finally{
		if(rs!=null){
			try{
				rs.close();
			}catch(SQLException sqe){
				System.err.println(sqe.getMessage());

			}rs=null;
		}
		if(stmt!=null){
			try{
				stmt.close();
			}catch(SQLException sqe){
				System.err.println(sqe.getMessage());

			}stmt=null;
		}
		if(conn!=null){
			try{
				conn.close();
			}catch(SQLException sqe){
				System.err.println(sqe.getMessage());

			}conn=null;
		}

	}// finally
	return circleList;
}  // buildCircleList
	 */	
}
