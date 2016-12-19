package com.ibm.dp.actions;

import java.sql.Connection;
import java.sql.DriverManager;

import com.ibm.db2.jcc.b.SqlException;


public class MarkAsDamage {


 public	static Connection connectionManager(){
		Connection conn=null;
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			conn=DriverManager.getConnection("jdbc:db2://10.1.65.168:50000/DP", "DP","welcome2ibm");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return conn;
	}


}
