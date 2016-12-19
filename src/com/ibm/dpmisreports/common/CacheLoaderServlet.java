package com.ibm.dpmisreports.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.UserSessionContext;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class CacheLoaderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	 public void init() throws ServletException {
	     
		 Connection connection = null;
			try {
				 connection = DBConnectionManager.getDBConnection();
				 //getAllCircleList(connection);
					
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	   }

	 /*
	@Override
	public void service(HttpServletRequest request, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	*/
	public void getAllCircleList(Connection connection) throws Exception {
		List<SelectionCollection> allCirclesList = DropDownUtility.getInstance().getAllCircles();
		for (SelectionCollection circleObject : allCirclesList) {
			String strCircleId = circleObject.getStrValue();
			getAllTsmListForEachCircle(strCircleId,connection);
		}
	}
	
	public void getAllTsmListForEachCircle(String circleId,Connection connection) throws Exception {
		//List<SelectionCollection> tsmList = DropDownUtility.getInstance().getAllTsmSingleCircle(Integer.valueOf(circleId),connection);
		List<SelectionCollection> tsmList = DropDownUtility.getInstance().getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,Integer.valueOf(circleId),connection);
		for (SelectionCollection tsmObject : tsmList) {
			String strTsmAccountID = tsmObject.getStrValue();
			getAllDistributorListForEachTsm(strTsmAccountID,connection);
		}
	}
	
	public void getAllDistributorListForEachTsm(String strTsmAccountID,Connection connection) throws Exception {
		List<SelectionCollection> distributorList = DropDownUtility.getInstance().getAllAccountsUnderSingleAccount(strTsmAccountID,connection);
		/*for (SelectionCollection distObject : distributorList) {
			String strDistAccountID = distObject.getStrValue();
			//getAllFseListForEachDistributor(strDistAccountID,connection);
		}*/
	}
	
	/*public void getAllFseListForEachDistributor(String strDistAccountID,Connection connection) throws Exception {
		List<SelectionCollection> FseList = DropDownUtility.getInstance().getAllAccountsUnderSingleAccount((String) strDistAccountID,connection);
		for (SelectionCollection fseObject : FseList) {
			String strFseAccountID = fseObject.getStrValue();
			getAllRetailerListForEachFse(strFseAccountID,connection);
		}
	}
	
	public void getAllRetailerListForEachFse(String strFseAccountID,Connection connection) throws Exception {
		DropDownUtility.getInstance().getAllAccountsUnderSingleAccount((String) strFseAccountID,connection);
	}*/

}
