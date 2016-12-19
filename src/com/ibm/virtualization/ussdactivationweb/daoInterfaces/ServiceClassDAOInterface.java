/**************************************************************************
* File      : SerciceClassDAO.java
 * Author   : Parvinder
 * Created  : June 2, 2008
 * Modified : June 2, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		June 2, 2008 	Parvinder	First Cut.
 * V0.2		June 2, 2008 	Parvinder	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.daoInterfaces;


import java.util.ArrayList;

import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.dto.ServiceClassDTO;

/*********************************************************************************
 * This interface list all methods used in Creating and Modifying ServiceClass.
 * 
 * 
 * @author Ashad
 * @version 1.0
 ********************************************************************************************/


public interface ServiceClassDAOInterface  {
	
	
    /*******	Queries for  Service  Class  ******/
	
	 public static final String COUNT_SERVICECLASS = new StringBuffer(500)
	 .append("SELECT COUNT(*) FROM ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SERVICECLASS_MSTR S, ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SC_CATEGORY_MSTR C ") 
	.append("WHERE C.CATEGORY_ID IN (SELECT CATEGORY_ID ")
	 .append("FROM ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SERVICECLASS_MSTR where CIRCLE_CODE =? AND CATEGORY_ID is not null) AND ") 
	 .append("C.CATEGORY_ID=S.CATEGORY_ID AND CIRCLE_CODE =? ")
	 .append(" WITH UR").toString();
	 
	public static final  String CREATE_SERVICE_CLASS=new StringBuffer(500)
	.append(" INSERT INTO ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SERVICECLASS_MSTR(SERVICECLASS_ID,SERVICECLASS_NAME,CIRCLE_CODE,")
	.append(" CREATED_DT,CREATED_BY,MODIFIED_DT,MODIFIED_BY,SPECIAL_SC_CHECK,CATEGORY_ID,TARIFF_MESSAGE) values(?,?,?,current timestamp,?,?,?,?,?,?) ")
	.append(" WITH UR").toString();

	/*public static final  String RETRIEVE_SERVICE_CLASS =new StringBuffer(500)
	.append(" SELECT R.SERVICECLASS_ID,R.SERVICECLASS_NAME,R.CREATED_BY,R.CREATED_DT,R.STATUS,SPECIAL_SC_CHECK,CATEGORY_ID")
	.append(" FROM ").append("PRODCAT.PC_SERVICECLASS_MSTR_NICK R WHERE CIRCLE_CODE=?")
	.append(" WITH UR").toString();*/
	
	
	public static final  String RETRIEVE_SERVICE_CLASS_WITH_PAGEINATION =new StringBuffer(500)
	 .append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT S.SERVICECLASS_ID,S.SERVICECLASS_NAME,S.CREATED_BY,S.CREATED_DT,S.STATUS,S.SPECIAL_SC_CHECK,S.CATEGORY_ID,C.CATEGORY_DESC")
    .append(" FROM ")
    .append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SC_CATEGORY_MSTR C ,")
    .append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SERVICECLASS_MSTR S ")
	.append(" WHERE C.CATEGORY_ID IN (SELECT CATEGORY_ID FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SERVICECLASS_MSTR where CIRCLE_CODE =? AND CATEGORY_ID is not null) AND C.CATEGORY_ID=S.CATEGORY_ID AND CIRCLE_CODE =? ) a )b ")
	 .append("WHERE rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static final  String RETRIEVE_SERVICE_CLASS=new StringBuffer(500)
	 .append("SELECT S.SERVICECLASS_ID,S.SERVICECLASS_NAME,S.CREATED_BY,S.CREATED_DT,S.STATUS,S.SPECIAL_SC_CHECK,S.CATEGORY_ID,C.CATEGORY_DESC")
   .append(" FROM ")
   .append(SQLConstants.PRODCAT_SCHEMA)
   .append(".PC_SC_CATEGORY_MSTR C ,")
   .append(SQLConstants.PRODCAT_SCHEMA)
   .append(".PC_SERVICECLASS_MSTR S ")
	.append(" WHERE C.CATEGORY_ID IN (SELECT CATEGORY_ID FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
   .append(".PC_SERVICECLASS_MSTR where CIRCLE_CODE =? AND CATEGORY_ID is not null) AND C.CATEGORY_ID=S.CATEGORY_ID AND CIRCLE_CODE =?  ")
	.append(" WITH UR").toString();
	
	public static final  String RETRIEVE_SERVICE_CLASS_FOR_DROP_DOWN=new StringBuffer(500)
	.append("SELECT S.SERVICECLASS_ID,S.SERVICECLASS_NAME,S.CREATED_BY,S.CREATED_DT,S.STATUS,S.SPECIAL_SC_CHECK,S.CATEGORY_ID,C.CATEGORY_DESC")
	.append(" FROM ")
    .append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SC_CATEGORY_MSTR C ,")
    .append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SERVICECLASS_MSTR S ")
	.append(" WHERE C.CATEGORY_ID IN (SELECT CATEGORY_ID FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SERVICECLASS_MSTR where CIRCLE_CODE =? AND CATEGORY_ID is not null) AND C.CATEGORY_ID=S.CATEGORY_ID AND CIRCLE_CODE =? AND S.STATUS=? ")
	.append(" WITH UR").toString();


	
	
	
	public static final  String RETRIEVE_SERVICE_CLASS_BY_ID=new StringBuffer(500)
	.append(" SELECT R.SERVICECLASS_ID,R.SERVICECLASS_NAME,R.CIRCLE_CODE,R.STATUS,R.SPECIAL_SC_CHECK,R.CATEGORY_ID,R.TARIFF_MESSAGE FROM ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SERVICECLASS_MSTR R WHERE R.SERVICECLASS_ID=? AND CIRCLE_CODE=? ")
	.append(" WITH UR").toString();
	
	public static final  String RETRIEVE_ACTIVE_SERVICE_CLASS_BY_ID=new StringBuffer(500)
	.append(" SELECT R.SERVICECLASS_ID,R.SERVICECLASS_NAME,R.CIRCLE_CODE,R.STATUS,R.SPECIAL_SC_CHECK,R.CATEGORY_ID FROM ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SERVICECLASS_MSTR R WHERE R.SERVICECLASS_ID=? AND CIRCLE_CODE=? AND STATUS=? ")
	.append(" WITH UR").toString();
	
	public static final  String RETRIEVE_SERVICE_CLASS_BY_ID_FOR_ALL_CIRCEL=new StringBuffer(500)
	.append(" SELECT COUNT(SERVICECLASS_ID) FROM ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SERVICECLASS_MSTR  WHERE SERVICECLASS_ID=?")
	.append(" WITH UR").toString();
	
	public static final  String UPDATE_SERVICE_CLASS=new StringBuffer(500)
	  .append("UPDATE ")
	  .append(SQLConstants.PRODCAT_SCHEMA)
	  .append(".PC_SERVICECLASS_MSTR SET  STATUS=?,MODIFIED_DT=current timestamp,MODIFIED_BY=?,SERVICECLASS_NAME=?,SPECIAL_SC_CHECK=?,CATEGORY_ID=?,TARIFF_MESSAGE=?" )
	  .append(" WHERE SERVICECLASS_ID=? AND CIRCLE_CODE=? WITH UR").toString();
	
	public static final  String VALIDATE_SERVICECLASS_NAME=new StringBuffer(500)
		.append("SELECT SERVICECLASS_NAME FROM ")
		 .append(SQLConstants.PRODCAT_SCHEMA)
		.append(".PC_SERVICECLASS_MSTR WHERE SERVICECLASS_NAME= ? AND CIRCLE_CODE=?")
		.append(" WITH UR").toString();
	
	public static final  String VALIDATE_SERVICECLASS_EXCLUDE_ID=new StringBuffer(500)
	   .append("SELECT SERVICECLASS_NAME  FROM ")
	    .append(SQLConstants.PRODCAT_SCHEMA)
		.append(".PC_SERVICECLASS_MSTR WHERE UPPER(SERVICECLASS_NAME)= ? ")
	   .append(" WITH UR").toString();
	
	
	public static final  String RETRIEVE_SC_CATEGORY_LIST =new StringBuffer(500)
	.append(" SELECT CATEGORY_ID,CATEGORY_DESC FROM ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SC_CATEGORY_MSTR")
	.append(" WITH UR").toString();
	
	
	
	public static final  String RETRIEVE_SERVICE_CLASS_NAME_BY_ID=new StringBuffer()
	.append(" SELECT SERVICECLASS_NAME FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
    .append(".PC_SERVICECLASS_MSTR  WHERE SERVICECLASS_ID=?")
	.append(" WITH UR").toString();
	
	/**
	 * This method is used for Creating of ServiceClass using createServiceClass(ServiceClassDTO serviceDTO)method.
	 * 
	 * @ param		serviceDTO object containing data for creating ServiceClass
	 * 	
	 * @ throws		DAOException
	*/
	public abstract void createServiceClass(ServiceClassDTO serviceDTO) throws DAOException ;
	/**
	 * This method is used to get ServiceClass  information for selected circle and displaying it for view . 
	 * 
	 * @ param		circleCode object for retrieving ServiceClass Information 
	 * @ param		statusId   the Active/Inactive status of ServiceClass 
	 * @ return		an Arraylist containing list of ServiceClass to be displayed for viewing
	 * 
	 * @ throws		DAOException
	 */
	
	public abstract ArrayList getServiceClassList(String circleCode,int intLb, int intUb)throws DAOException ;
	/**
	 * This method is used to get Service Class Information and than displaying it for editing.
	 * 
	 * @param		circleCode 	   the circleCode of serviceClass selected for editing
	 * @param		serviceClassId the ServiceClassId of ServiceClass selected for editing
	 * @return		an object containing list of ServiceClass to be edited
	 * 				
	 * @throws		DAOException
	 */
	public abstract ServiceClassDTO getServiceClassListByIdCode(int serviceClassId,String circleCode)throws DAOException ;
	/**
	 * This method is used to modify Service Class Information. 
	 * 
	 * 	@param		serviceDTO object containing modified service class data.
	 *	@param		Id 	       service class Id used for identifying service class to be modified		
	 *
	 *	@throws		DAOException
	*/
	public abstract void updateServiceClass(ServiceClassDTO serviceDTO,int Id) throws DAOException ;
	

}
