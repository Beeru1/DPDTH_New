package com.ibm.dp.actions;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class FileReaderForMarkDamage {

	public static void main(String arg[]) {
		String fName = "C:/DPUploadFiles/";
		String thisLine;
		FileInputStream fis = null;
		DataInputStream myInput = null;
		String fileName = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String[] changedFilename = new String[100];
		String exactFileName = "";
		try {
			File f1 = new File(fName);
			if (f1.isDirectory()) {
				File fileDir[] = f1.listFiles();
				if (fileDir.length > 0) {
					fileDir[1].getName();
					String query = "select FILE_ID,FILE_PATH,CHANGED_FILE_NAME from DP_BULK_UPLOAD_FILE where FILE_TYPE='Damaged' and PROCESSED_STATUS='N' "
							+ " and CIRCLE_ID=1 ";

					con = MarkAsDamage.connectionManager();
					ps = con.prepareStatement(query);
					rs = ps.executeQuery();
					int val = 0;
					while (rs.next()) {
						exactFileName = rs.getString("CHANGED_FILE_NAME");
						changedFilename[val] = rs.getString("FILE_PATH")+ exactFileName;
						val++;
						
					}

				}

				for (int i = 0; i < fileDir.length; i++) {

					fileName = changedFilename[i];
					fis = new FileInputStream(fileName+".csv");

					myInput = new DataInputStream(fis);

					while ((thisLine = myInput.readLine()) != null) {
						String strar[] = thisLine.split(",", -1);
						if ((strar[0].length() != 0)
								&& (strar[1].length() != 0)) {
							
							int rowsupdated=updateDamageFlag(strar[0],strar[1],con,exactFileName);							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// attempt the close the file
				fis.close();
				myInput.close();
				con.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}catch(SQLException e){
				
			}
		}
	}
	
 	private static int updateDamageFlag(String startno, String endno, Connection con,String fileName) {
 		 		
		PreparedStatement ps = null;
		ResultSet rs=null;
		HashMap <String,Integer> distNoMap = null;
		ArrayList distNoList = new ArrayList();
		
		String updateDamageFlag="update DP_STOCK_INVENTORY set MARK_DAMAGED='Y',DAMAGE_REMARKS='HLR DELETION' where serial_no between ? and ? and MARK_DAMAGED='N' ";
		
		String distNos="select count(*)NOS ,DISTRIBUTOR_ID,PRODUCT_ID from DP_STOCK_INVENTORY where SERIAL_NO between ? and ? and DAMAGE_REMARKS is null " +
						" group by DISTRIBUTOR_ID,PRODUCT_ID ";
		int rows=0;
		try{
			ps=con.prepareStatement(distNos);
			ps.setString(1, startno);
			ps.setString(2, endno);
			rs=ps.executeQuery();
			while(rs.next()){
				distNoMap = new HashMap();
				distNoMap.put("NOS", rs.getInt("NOS"));
				distNoMap.put("DISTRIBUTOR_ID", rs.getInt("DISTRIBUTOR_ID"));
				distNoMap.put("PRODUCT_ID", rs.getInt("PRODUCT_ID"));
				distNoList.add(distNoMap);
			}
			ps.clearParameters();
			ps=con.prepareStatement(updateDamageFlag);
			ps.setString(1, startno);
			ps.setString(2, endno);
			rows=ps.executeUpdate();
				
		if(distNoList!=null){
			if(distNoList.size()>0){
				updateStockSummary(distNoList,con,fileName);
			}
		}
		}catch(SQLException e){
			try {
				con.rollback();
				String updateFileProcessStatus = "update DP_BULK_UPLOAD_FILE set PROCESS_STATUS='Y',STATUS='Y',STATUS_DETAILS='"+"Serial nos from "+startno+" to "+endno+" are not processed."+"',PROCESSED_DT = current timestamp  where changed_file_name='"+fileName+"'";
				ps=con.prepareStatement(updateFileProcessStatus);
				ps.executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try{
			ps.close();
			}catch(SQLException e){
				
			}
		}
		return rows;
	}

	private static void updateStockSummary(ArrayList distNoList,Connection con, String fileName) {
		PreparedStatement ps = null;
		int rows=0;
		int nos=0;
		int prodId=0;
		int distId=0;
		String updateDistStock="update DIST_STOCK_SUMMARY set CLOSINGBALANCE=CLOSINGBALANCE-? where DIST_ID=? and PROD_ID = ? " +
				" and CURR_DATE =(Select max(Curr_Date) from DIST_STOCK_Summary where DIST_ID =? and PROD_ID=?)";
		String updateFileProcessStatus = "update DP_BULK_UPLOAD_FILE set PROCESS_STATUS='Y',STATUS='Y',STATUS_DETAILS='Damaged',PROCESSED_DT = current timestamp  where changed_file_name='"+fileName+"'";
		try{
		ps=con.prepareStatement(updateDistStock);
		for(int adi=0;adi<distNoList.size();adi++){
			nos=(Integer)(((HashMap)distNoList.get(adi)).get("NOS"));
			distId=(Integer)((HashMap)distNoList.get(adi)).get("DISTRIBUTOR_ID");
			prodId=(Integer)((HashMap)distNoList.get(adi)).get("PRODUCT_ID");
			ps.setInt(1, nos);
			ps.setInt(2, distId);
			ps.setInt(3, prodId);
			ps.setInt(4, distId);
			ps.setInt(5, prodId);
			rows=rows+ps.executeUpdate();
			ps.clearParameters();
		}
		ps = con.prepareStatement(updateFileProcessStatus);
		ps.executeUpdate();
	}catch(SQLException e){
		try {
			con.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.printStackTrace();
	}finally{
		try {
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
}