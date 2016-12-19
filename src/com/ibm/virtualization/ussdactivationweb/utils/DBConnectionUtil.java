/*
 * Created on Jan 15, 2008
 *
 *
 */
package com.ibm.virtualization.ussdactivationweb.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;



/**
 * @author Ashad
 *
 */

public class DBConnectionUtil {

	// ehllo

	  private static final Logger logger = Logger.getLogger(DBConnectionUtil.class.toString());
	
	
	
	/**
	 * @param con
	 * @param pstmt
	 */
	public static void closeDBResources(Connection con, PreparedStatement pstmt) throws SQLException{
		closeDBResources(con,pstmt,null);        
	}
	
	/**
	 * @param connection
	 * @param preparedStatement
	 */
	public static void closeDBResources( Connection connection, 
										 PreparedStatement preparedStatement, 
										 ResultSet resultSet) throws SQLException{

		        if (resultSet != null){
		            resultSet.close();
		        }
		        if (preparedStatement != null){
		        	preparedStatement.close();
		        }
		        if (connection != null && !connection.isClosed()){
		            connection.close();
		        }
	}
	
	
}
