package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.util.HashMap;

public class BaseDaoRdbms {
	
	public BaseDaoRdbms(){
		
	}
	protected Connection connection = null;
	
	protected BaseDaoRdbms(Connection connection) {
		this.connection = connection;
	}	
	protected HashMap<String, String> queryMap = new HashMap<String, String>();
	
	/* This method retreives the Query on the basis of the key passed as a parameter */
	public String getQuery(String key) {
		return queryMap.get(key);
	}

	/* This method inserts the Key value pair
	 * where value is the actual query 
	 * */
	public void setQuery(String key, String query) {
		queryMap.put(key, query);
	}


}
