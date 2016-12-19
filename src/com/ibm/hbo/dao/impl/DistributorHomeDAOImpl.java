package com.ibm.hbo.dao.impl;

import com.ibm.hbo.common.DBQueries;
import com.ibm.hbo.dao.DistributorHomeDAO;
import com.ibm.hbo.dto.DistributorHomeDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;


public class DistributorHomeDAOImpl implements DistributorHomeDAO {
	private static Logger logger = Logger.getLogger(DistributorHomeDAOImpl.class);
	public ArrayList findByPrimaryKey(long distId) throws DAOException {
		
		ArrayList arrDistStock = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final StringBuffer SQL_SELECT = new StringBuffer("");
		SQL_SELECT.append(DBQueries.viewDistStockSumm);	
		try {
			con = DBConnectionManager.getDBConnection(); 
			ps = con.prepareStatement(SQL_SELECT.toString());
			ps.setLong(1, distId);
			ps.setLong(2, distId);
			rs = ps.executeQuery();
			arrDistStock = fetchSingleResult(rs);
			if (arrDistStock.size() == 0) {
				
			}
			
			return arrDistStock;
		} catch (SQLException e) {
			e.printStackTrace();

			logger.error("SQL Exception occured in findByPrimaryKey."+ "Exception Message: "+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in findByPrimaryKey."+ "Exception Message: "+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}

/*	public ArrayList findByPrimaryKey(long distId, String string) throws HBOException, DAOException {
		ArrayList arrDistStock = new ArrayList();
		DistributorHomeDTO tempDTO = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final StringBuffer SQL_SELECT = new StringBuffer("");
		SQL_SELECT.append(DBQueries.viewProdStockSumm);	
		int oldProdId=0;
		int newProdId=0;
		long openingBalance=0,returnn=0,sales=0,closingBalance=0,receipt=0;
		
		try {
			con = DBConnectionManager.getDBConnection(); 
			ps = con.prepareStatement(SQL_SELECT.toString());
			ps.setLong(1, distId);
			ps.setLong(2, distId);
			rs = ps.executeQuery();
		if(rs.next()==true){
			String oldproduct = "";
			while (rs.next()) {
				String newproduct = "";
				newProdId=rs.getInt("PROD_ID");
				if(newProdId!=oldProdId){				//first time check
					if(oldProdId!=0){
					tempDTO = new DistributorHomeDTO();
					tempDTO.setDistName("TOTAL");
					tempDTO.setProdname(" ");
					tempDTO.setOpeningBalence(String.valueOf(openingBalance));
					tempDTO.setReceiptFrBotree(String.valueOf(receipt));
					tempDTO.setReceiptFrFSE(String.valueOf(returnn));
					tempDTO.setSalesToFSE(String.valueOf(sales));
					tempDTO.setClosingbalence(String.valueOf(closingBalance));
					arrDistStock.add(tempDTO);
					}
					//Changing Back to 0 
					openingBalance=0;
					returnn=0;
					sales=0;
					closingBalance=0;
					receipt=0;
					
					
				}
				tempDTO = new DistributorHomeDTO();
				tempDTO.setDistName(rs.getString("ACCOUNT_NAME"));
				newproduct = rs.getString("PRODUCT_NAME");
				tempDTO.setProdname(newproduct);
				if(oldproduct.equals(newproduct)){
					tempDTO.setProdname("-----");
				}else{
					oldproduct = tempDTO.getProdname();
					tempDTO.setProdname(rs.getString("PRODUCT_NAME"));
				}
				tempDTO.setOpeningBalence(String.valueOf(rs.getLong("OPENINGBALANCE")));
				tempDTO.setReceiptFrBotree(String.valueOf(rs.getLong("RECEIPT")));
				tempDTO.setReceiptFrFSE(String.valueOf(rs.getLong("RETURN")));
				tempDTO.setSalesToFSE(String.valueOf(rs.getLong("SALES")));
				tempDTO.setClosingbalence(String.valueOf(rs.getLong("CLOSINGBALANCE")));
				//Summation
				openingBalance=openingBalance+rs.getLong("OPENINGBALANCE");
				receipt=receipt+rs.getLong("RECEIPT");
				returnn=returnn+rs.getLong("RETURN");
				sales=sales+rs.getLong("SALES"); 
				closingBalance=closingBalance+rs.getLong("CLOSINGBALANCE");
				oldProdId=newProdId;
				arrDistStock.add(tempDTO);
			
			}
			tempDTO = new DistributorHomeDTO();
			tempDTO.setDistName("TOTAL");
			tempDTO.setProdname(" ");
			tempDTO.setOpeningBalence(String.valueOf(openingBalance));
			tempDTO.setReceiptFrBotree(String.valueOf(receipt));
			tempDTO.setReceiptFrFSE(String.valueOf(returnn));
			tempDTO.setSalesToFSE(String.valueOf(sales));
			tempDTO.setClosingbalence(String.valueOf(closingBalance));
			arrDistStock.add(tempDTO);
			}
			if (arrDistStock.size() == 0) {
								
				
			}
			
			return arrDistStock;
		} catch (SQLException e) {
			e.printStackTrace();

			logger.error("SQL Exception occured in findByPrimaryKey."+ "Exception Message: "+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in findByPrimaryKey."+ "Exception Message: "+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}
	*/

		private ArrayList fetchSingleResult(ResultSet rs)throws SQLException {
		DistributorHomeDTO tempDTO = null;
		ArrayList arrData = new ArrayList();
		int i = 0;
		String lstatus = null;
		String olddist = "";
		while (rs.next()) {
			String newdist="";
			tempDTO = new DistributorHomeDTO();

			newdist = rs.getString("ACCOUNT_NAME");
			tempDTO.setDistName(newdist);
			if(olddist.equals(newdist)){
				tempDTO.setDistName("-----");
			}
			else{
				olddist = tempDTO.getDistName();
				tempDTO.setDistName(rs.getString("ACCOUNT_NAME"));
			}
			tempDTO.setProdname(rs.getString("PRODUCT_NAME"));
			tempDTO.setOpeningBalence(String.valueOf(rs.getLong("OPENINGBALANCE")));
			tempDTO.setReceiptFrBotree(String.valueOf(rs.getLong("RECEIPT")));
			tempDTO.setReceiptFrFSE(String.valueOf(rs.getLong("RETURN")));
			tempDTO.setSalesToFSE(String.valueOf(rs.getLong("SALES")));
			//rajiv jha mod start for ret
			tempDTO.setReceiptFrRET(String.valueOf(rs.getLong("RETURN")));
			tempDTO.setSalesToRET(String.valueOf(rs.getLong("SALES")));
			//rajiv jha mod end for ret
			
			tempDTO.setClosingbalence(String.valueOf(rs.getLong("CLOSINGBALANCE")));
			// code for damaged stock
			tempDTO.setDamagedStock(String.valueOf(rs.getLong("DAMAGED_STOCK")));
			arrData.add(tempDTO);
		}
		return arrData;
		}

		public ArrayList findByPrimaryKey(long distId, String string) throws HBOException, DAOException {
			ArrayList arrDistStock = new ArrayList();
			DistributorHomeDTO tempDTO = null;
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			final StringBuffer SQL_SELECT = new StringBuffer("");
			SQL_SELECT.append(DBQueries.viewProdStockSumm);	
			int oldProdId=0;
			int newProdId=0;
			long openingBalance=0,returnn=0,sales=0,closingBalance=0,receipt=0;
			
			try {
				con = DBConnectionManager.getDBConnection(); 
				ps = con.prepareStatement(SQL_SELECT.toString());
				
				ps.setLong(1, distId);
				ps.setLong(2, distId);
				rs = ps.executeQuery();
				int y=0;
				String oldproduct = "";
				while (rs.next()) {
					String newproduct = "";
					newProdId=rs.getInt("PROD_ID");
					if(newProdId!=oldProdId){				//first time check
						if(oldProdId!=0){
						tempDTO = new DistributorHomeDTO();
						tempDTO.setDistName("TOTAL");
						tempDTO.setProdname(" ");
						tempDTO.setOpeningBalence(String.valueOf(openingBalance));
						tempDTO.setReceiptFrBotree(String.valueOf(receipt));
						tempDTO.setReceiptFrFSE(String.valueOf(returnn));
						tempDTO.setSalesToFSE(String.valueOf(sales));
						tempDTO.setClosingbalence(String.valueOf(closingBalance));
						arrDistStock.add(tempDTO);
						}
						//Changing Back to 0 
						openingBalance=0;
						returnn=0;
						sales=0;
						closingBalance=0;
						receipt=0;
						
						
					}
					tempDTO = new DistributorHomeDTO();
					tempDTO.setDistName(rs.getString("ACCOUNT_NAME"));
					newproduct = rs.getString("PRODUCT_NAME");
					tempDTO.setProdname(newproduct);
					if(oldproduct.equals(newproduct)){
						tempDTO.setProdname("-----");
					}else{
						oldproduct = tempDTO.getProdname();
						tempDTO.setProdname(rs.getString("PRODUCT_NAME"));
					}
					tempDTO.setOpeningBalence(String.valueOf(rs.getLong("OPENINGBALANCE")));
					tempDTO.setReceiptFrBotree(String.valueOf(rs.getLong("RECEIPT")));
					tempDTO.setReceiptFrFSE(String.valueOf(rs.getLong("RETURN")));
					tempDTO.setSalesToFSE(String.valueOf(rs.getLong("SALES")));
					tempDTO.setClosingbalence(String.valueOf(rs.getLong("CLOSINGBALANCE")));
					//Summation
					openingBalance=openingBalance+rs.getLong("OPENINGBALANCE");
					receipt=receipt+rs.getLong("RECEIPT");
					returnn=returnn+rs.getLong("RETURN");
					sales=sales+rs.getLong("SALES"); 
					closingBalance=closingBalance+rs.getLong("CLOSINGBALANCE");
					oldProdId=newProdId;
					arrDistStock.add(tempDTO);
					y++;
				
				}
				if(y!=0){
				tempDTO = new DistributorHomeDTO();
				tempDTO.setDistName("TOTAL");
				tempDTO.setProdname(" ");
				tempDTO.setOpeningBalence(String.valueOf(openingBalance));
				tempDTO.setReceiptFrBotree(String.valueOf(receipt));
				tempDTO.setReceiptFrFSE(String.valueOf(returnn));
				tempDTO.setSalesToFSE(String.valueOf(sales));
				tempDTO.setClosingbalence(String.valueOf(closingBalance));
				arrDistStock.add(tempDTO);
				}
				
				return arrDistStock;
			} catch (SQLException e) {
				e.printStackTrace();

				logger.error("SQL Exception occured in findByPrimaryKey."+ "Exception Message: "+ e.getMessage());
				throw new DAOException("SQLException: " + e.getMessage(), e);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occured in findByPrimaryKey."+ "Exception Message: "+ e.getMessage());
				throw new DAOException("Exception: " + e.getMessage(), e);
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
				}
			}
		}


}