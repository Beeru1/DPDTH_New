package com.ibm.hbo.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * Servlet implementation class for Servlet: ExportAssignStockServlet
 *
 */
 public class ExportAssignStockServlet extends HttpServlet {
   
	 
	 
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -978840568553027351L;

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ExportAssignStockServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		// TODO Auto-generated method stub
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		StringBuffer SQL_ASSIGN_STOCK = new StringBuffer();
		StringBuffer SQL_SECONDARY_SALES = new StringBuffer();
		HttpSession session = request.getSession();
		String reportType = request.getParameter("reportType");
		String productId = request.getParameter("product");
		String catCode = request.getParameter("busCat");
		int paramCount=1;
		if(reportType.equalsIgnoreCase("1")){
			SQL_SECONDARY_SALES.append("select parent.account_name as FSE,details.ACCOUNT_CODE,details.ACCOUNT_NAME,stock.SERIAL_NO,stock.REMARKS, product.PRODUCT_NAME,product.SR_MRP " +
					" from VR_ACCOUNT_DETAILS parent,VR_ACCOUNT_DETAILS details,DP_STOCK_INVENTORY stock,DP_PRODUCT_MASTER product where parent.ACCOUNT_LEVEL=7 and details.PARENT_ACCOUNT = parent.ACCOUNT_ID " +
					" and product.PRODUCT_ID = stock.PRODUCT_ID and parent.PARENT_ACCOUNT=? and parent.ACCOUNT_ID = stock.FSE_ID "+
					" and stock.MARK_DAMAGED='N' and details.account_id = stock.RETAILER_ID and date(stock.RETAILER_PURCHASE_DATE) between ? and ? ");
		}
		else if(reportType.equalsIgnoreCase("2")){
			SQL_SECONDARY_SALES.append("select details.account_name,product.BT_PRODUCT_CODE||' '||product.PRODUCT_NAME product_code,stock.SERIAL_NO,date(stock.FSE_PURCHASE_DATE)FSE_PURCHASE_DATE" +
					" from VR_ACCOUNT_DETAILS details, DP_PRODUCT_MASTER product, " +
					" DP_STOCK_INVENTORY stock where details.ACCOUNT_ID=stock.FSE_ID and details.PARENT_ACCOUNT=? " +
					" and stock.PRODUCT_ID = product.PRODUCT_ID and date(FSE_PURCHASE_DATE) between ? " +
					" and ? and stock.MARK_DAMAGED='N' and details.account_level=7 ");
		}
		if(!catCode.equalsIgnoreCase("A")){
			if(!productId.equalsIgnoreCase("A")){
				
				SQL_SECONDARY_SALES.append(" and stock.PRODUCT_ID=? ");
			}
				SQL_SECONDARY_SALES.append(" and product.CATEGORY_CODE= ? ");
		}
		SQL_SECONDARY_SALES.append(" order by stock.SERIAL_NO with ur");
//		SQL_ASSIGN_STOCK.append("select st.*,pm.PRODUCT_NAME from DP_STOCK_INVENTORY st,DP_PRODUCT_MASTER pm,VR_ACCOUNT_DETAILS ad where " +
//				" pm.PRODUCT_ID = st.PRODUCT_ID and ad.ACCOUNT_ID=st.FSE_ID and ad.PARENT_ACCOUNT=? and st.PRODUCT_ID=? " +
//				" and st.MARK_DAMAGED='N' and date(FSE_PURCHASE_DATE) between ? " +
//				" and ? order by st.SERIAL_NO with ur");
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String distId =sessionContext.getId()+"";
		String distCode = sessionContext.getAccountCode();
		try{
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(SQL_SECONDARY_SALES.toString());
			String startDt = request.getParameter("fromDt");
			String endDt = request.getParameter("toDt");
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date startDate = formatter.parse(startDt);
            Date endDate = formatter.parse(endDt);
            ps.setInt(paramCount++, Integer.parseInt(distId));
 			ps.setDate(paramCount++, new java.sql.Date(startDate.getTime()));
 			ps.setDate(paramCount++, new java.sql.Date(endDate.getTime()));
			if(!catCode.equalsIgnoreCase("A")){
				if(!productId.equalsIgnoreCase("A")){
					ps.setInt(paramCount++, Integer.parseInt(productId));
				}
					ps.setInt(paramCount++, Integer.parseInt(catCode));
			}
			
			Date dt=new Date();
			Format format = new SimpleDateFormat("MM-dd-yyyy--HH-mm-ss");
			String today = format.format(dt);
			String filePath = "C:/Aditya/Files/"+distId + "_" + today+"_dp.csv";
			File newFile = new File(filePath);
			FileWriter fileWriter = new FileWriter(newFile);
			BufferedWriter bufWriter = new BufferedWriter(fileWriter);
			rs = ps.executeQuery();
			if(reportType.equalsIgnoreCase("1")){
				bufWriter.write("Name of FSE,Retailer Code, Retailer Name,Bill No. & Date,Product Name,Serial No.,Amount");
				while(rs.next()){
//					double amount =0.0;
//					if(catCode.equalsIgnoreCase("2"));{
//					amount = amount+rs.getDouble("SR_MRP");
//					}
					bufWriter.write("\n");
					bufWriter.write(rs.getString("FSE")+","+rs.getString("ACCOUNT_CODE")+","+rs.getString("ACCOUNT_NAME")+","+rs.getString("REMARKS")+","+rs.getString("PRODUCT_NAME")+","+rs.getString("SERIAL_NO")+","+rs.getDouble("SR_MRP"));
				}
			}
			else if(reportType.equalsIgnoreCase("2")){
				bufWriter.write("Name of FSE,Product COde & Name,Serial No.,Date of Allocation");
				while(rs.next()){
					bufWriter.write("\n");
					bufWriter.write(rs.getString("ACCOUNT_NAME")+","+rs.getString("PRODUCT_CODE")+","+rs.getString("SERIAL_NO")+","+rs.getString("FSE_PURCHASE_DATE"));
				}
			}
		bufWriter.close();
		filePath = "/reportFile/7_03-24-2009--11-56-13_dp.csv";
		//request.setAttribute("filePath",newFile.getAbsolutePath());
		//request.setAttribute("fileName",newFile.getName());
		RequestDispatcher ch = request.getRequestDispatcher(filePath);
		ch.forward(request,response);
		/*
		
		try{
			String fileFullPath = newFile.getAbsolutePath();
			File file = new File(fileFullPath);
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition","attachment; filename="+newFile.getName());
			ServletOutputStream sos     = response.getOutputStream();
			java.io.FileInputStream fis = new java.io.FileInputStream(file);
			byte[] buf = new byte[fis.available()];
			fis.read(buf);
			fis.close();
			sos.write(buf);
			sos.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
*/		
	//		java.io.BufferedInputStream br = null;
			//File abc = new File("C:/Documents and Settings/Admin/Desktop/export_satyendra.txt");
/*			java.io.File f = new java.io.File(newFile.getAbsolutePath());
			try {
				br = new java.io.BufferedInputStream(
						new java.io.FileInputStream(newFile));
				byte[] content = new byte[(int) newFile.length()];
				br.read(content);
				//response.setHeader("Content-Disposition",
					//	"attachment; filename=\"" + f.getName() + "\"");
				response.setContentType("application/octet- stream");
				response.getOutputStream().write(content);
				//response.getOutputStream().println();
				//response.getOutputStream().flush();
				if (br != null) {
					br.close();
				}
			}catch(Exception e){
			e.printStackTrace();
		} */
	}
	catch(Exception e){
			e.printStackTrace();
	}
}
}