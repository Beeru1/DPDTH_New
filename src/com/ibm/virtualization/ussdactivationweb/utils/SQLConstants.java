/*
 * Created on July 23, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.virtualization.ussdactivationweb.utils;

/**
 * @author Ashad
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SQLConstants {
	
	
	/*
	 * The file which will have the database connection information for the Product Catalogue aaplication.
	 */
	public static String FTA_SQL_RESOURCE_BUNDLE = "com.ibm.virtualization.ussdactivationweb.resources.Fta_en_US";
	public static  String DB_JNDIKEY_FTA = "fta.jndi.name";	
	public static  String DB_SCHEMA_FTA = "fta.schema.name";
	public static  String DB_JNDIKEY_PRODCAT = "prodcat.jndi.name";		
	public static  String DB_SCHEMA_PRODCAT = "prodcat.schema.name";
	public static  String DB_JNDIKEY_DISTPORTAL = "distportal.jndi.name";		
	public static  String DB_SCHEMA_DISTPORTAL = "distportal.schema.name";
		
	public static final String FTA_JNDI_NAME ;
	public static final String FTA_SCHEMA ;
	public static final String PRODCAT_JNDI_NAME ;
	public static final String PRODCAT_SCHEMA ;
	public static final String DISTPORTAL_JNDI_NAME ;
	public static final String DISTPORTAL_SCHEMA ;
	
	//	JNDI names and Schema values initilized from the configuration file.
	static {
		
		FTA_JNDI_NAME = Utility.getValueFromBundle(
				DB_JNDIKEY_FTA,FTA_SQL_RESOURCE_BUNDLE);
		FTA_SCHEMA = Utility.getValueFromBundle(
				DB_SCHEMA_FTA,FTA_SQL_RESOURCE_BUNDLE);
		PRODCAT_JNDI_NAME = Utility.getValueFromBundle(
				DB_JNDIKEY_PRODCAT,FTA_SQL_RESOURCE_BUNDLE);
		PRODCAT_SCHEMA = Utility.getValueFromBundle(
				DB_SCHEMA_PRODCAT,FTA_SQL_RESOURCE_BUNDLE);
		DISTPORTAL_JNDI_NAME = Utility.getValueFromBundle(
				DB_JNDIKEY_DISTPORTAL,FTA_SQL_RESOURCE_BUNDLE);
		DISTPORTAL_SCHEMA = Utility.getValueFromBundle(
				DB_SCHEMA_DISTPORTAL,FTA_SQL_RESOURCE_BUNDLE);		
	}
	
	/*
	 * The file which will have the database connection information for the Admin GUI aaplication.
	 */
	public static String PREPAID_SQL_RESOURCE_BUNDLE = "Prodcat";
	
	
	
//	 Database related Constants
	public static  String DATASOURCE_NAME = "datasource_name";
	
	public static  String MIS_REPORT_DATASOURCE_NAME = "datasource_name_RDB";

	public static  String DB_CONNECTION_TYPE = "connection_type";

	public static  String DB_DRIVER_NAME = "database.driver";

	public static  String DB_URL = "database.url";

	public static  String DB_USERNAME = "database.username";

	public static  String DB_PASSWORD = "database.password";

	public static  String DB_POOL_MAX_ACTIVE = "database.pool.maxActive";

	public static  String DB_POOL_MAX_IDLE = "database.pool.maxIdle";

	public static  String DB_POOL_MAX_WAIT = "database.pool.maxWait";

	public static  String DB_POOL_MIN_IDLE = "database.pool.minIdle";
}