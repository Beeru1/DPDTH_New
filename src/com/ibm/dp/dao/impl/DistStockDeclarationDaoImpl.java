package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DistStockAcceptTransferBean;
import com.ibm.dp.beans.DistStockDeclarationDetailsBean;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DistStockDeclarationDao;
import com.ibm.dp.dto.DistStockAccpDisplayDTO;
import com.ibm.dp.dto.DistStockDecOptionsDTO;
import com.ibm.dp.dto.DiststockAccpTransferDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DistStockDeclarationDaoImpl extends BaseDaoRdbms implements DistStockDeclarationDao
{
	  private Logger logger = Logger.getLogger(DistStockDeclarationDaoImpl.class.getName());
	  public DistStockDeclarationDaoImpl(Connection con) {
			 super(con);
	}
	
	  
   public void insertStockDecl(ListIterator<DistStockDeclarationDetailsBean> stockDeclarationDetailsBeanListItr)throws DAOException
   {
			Connection con = null;
			PreparedStatement ps = null;
			PreparedStatement pstmtDPStock = null;
			PreparedStatement pstmtOpenStock = null;
			ResultSet rset = null;
			try 
			{
				con = DBConnectionManager.getDBConnection(); // db2
				con.setAutoCommit(false);
				//Commented by nazim hussain to enter OPEN AND DP STOCK DETAILS
				
//				while(stockDeclarationDetailsBeanListItr.hasNext()) {
//					DistStockDeclarationDetailsBean stockDeclarationDetailsBean = stockDeclarationDetailsBeanListItr.next();
//				ps = con.prepareStatement(DBQueries.SQL__INSERT_STOCK_DECL);
//				ps.setInt(1, stockDeclarationDetailsBean.getProductId());
//				ps.setInt(2, stockDeclarationDetailsBean.getClosingStock());
//				ps.setString(3, stockDeclarationDetailsBean.getComments());
//				ps.setInt(4, stockDeclarationDetailsBean.getDistId());
//				ps.executeUpdate();
//				 con.commit();
//				}
				DistStockDeclarationDetailsBean stockDeclarationDetailsBean = null;
				int[] intDPAndOpenStock = null;
				Integer intDistID, intProdID;
				
				pstmtDPStock = con.prepareStatement(DBQueries.SQL_STOCK_DECL_DP);
				pstmtOpenStock =  con.prepareStatement(DBQueries.SQL_STOCK_DECL_OPEN);
				
				while(stockDeclarationDetailsBeanListItr.hasNext()) 
				{	
					stockDeclarationDetailsBean = stockDeclarationDetailsBeanListItr.next();
					intDistID = stockDeclarationDetailsBean.getDistId();
					intProdID = stockDeclarationDetailsBean.getProductId();
						
					intDPAndOpenStock = getDPAndOpenStock(intDistID, intProdID, pstmtDPStock, pstmtOpenStock, rset);
					ps = con.prepareStatement(DBQueries.SQL__INSERT_STOCK_DECL);
					ps.setInt(1, intProdID);
					ps.setInt(2, stockDeclarationDetailsBean.getClosingStock());
					ps.setString(3, stockDeclarationDetailsBean.getComments());
					ps.setInt(4, intDistID);
					ps.setInt(5, intDPAndOpenStock[0]);
					ps.setInt(6, intDPAndOpenStock[1]);
					
					ps.executeUpdate();
				}
				
				con.commit();
			}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally{
				try {
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in insertStockDecl "+e.getMessage());
				}
			}
			
		 }
   
   private int[] getDPAndOpenStock(Integer intDistID, Integer intProdID, PreparedStatement pstmtDPStock, 
		   PreparedStatement pstmtOpenStock, ResultSet rset)throws Exception 
   {
		int[] intReturn = {0, 0};
		
		pstmtDPStock.setInt(1, intDistID);
		pstmtDPStock.setInt(2, intProdID);
		rset = pstmtDPStock.executeQuery();
		if(rset.next())
		{
			intReturn[0] = rset.getInt("DP_STOCK");
		}
		
		pstmtOpenStock.setInt(1, intDistID);
		pstmtOpenStock.setInt(2, intProdID);
		
		rset = pstmtOpenStock.executeQuery();
		
		if(rset.next())
		{
			intReturn[1] = rset.getInt("OPEN_STOCK");
		}
		
		pstmtDPStock.clearParameters();		
		pstmtOpenStock.clearParameters();
		
		return intReturn;
   }


public ArrayList<DistStockDecOptionsDTO> fetchProdIdNameList(DistStockDeclarationDetailsBean stockDeclarationDetailsBean)throws DAOException
   {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			ArrayList<DistStockDecOptionsDTO> stockDecOptionsDTOList = new ArrayList<DistStockDecOptionsDTO>();
			
			try {
				con = DBConnectionManager.getDBConnection(); // db2
				
				ps = con.prepareStatement(DBQueries.SQL__GET_Prod_ID_NAME);
				ps.setInt(1,stockDeclarationDetailsBean.getCircleId());
				rs=ps.executeQuery();
				while(rs.next())
				{
					DistStockDecOptionsDTO stockdto = new DistStockDecOptionsDTO();
				  	stockdto.setOptionValue(rs.getInt("PRODUCT_ID"));
				  	stockdto.setOptionText(rs.getString("PRODUCT_NAME"));
				  	stockDecOptionsDTOList.add(stockdto);
				}
				
				}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally{
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in insertStockDecl "+e.getMessage());
				}
			}
		return	stockDecOptionsDTOList ;
		 } 
   
   public ArrayList<DistStockDecOptionsDTO> fetchTransFormList(DistStockAcceptTransferBean stockAcceptTransBean)throws DAOException
   {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			ArrayList<DistStockDecOptionsDTO> stockAccpTransOptionsDTOList = new ArrayList<DistStockDecOptionsDTO>();
			
			try {
				con = DBConnectionManager.getDBConnection(); // db2
				
				ps = con.prepareStatement(DBQueries.SQL__GET_DIST);
			//	ps.setInt(1,stockAcceptTransBean.getAccount_id());
				rs=ps.executeQuery();
				while(rs.next())
				{
					DistStockDecOptionsDTO stockdto = new DistStockDecOptionsDTO();
				//  	stockdto.setOptionValue(rs.getInt("FROM_DIST_ID"));
				//  	stockdto.setOptionText(rs.getString("ACCOUNT_NAME"));
				  	stockAccpTransOptionsDTOList.add(stockdto);
				}
				
				}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally{
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in insertStockDecl "+e.getMessage());
				}
			}
		return	stockAccpTransOptionsDTOList ;
		 } 
   
   public ArrayList<DistStockDecOptionsDTO> getDCNoList(int accountId , int selectedValue)throws DAOException
   {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			ArrayList<DistStockDecOptionsDTO> stockAccpTransOptionsDTOList = new ArrayList<DistStockDecOptionsDTO>();
			
			try {
				con = DBConnectionManager.getDBConnection(); // db2
				
				ps = con.prepareStatement(DBQueries.SQL__GET_DCNO);
				ps.setInt(1,selectedValue);
				ps.setInt(2,accountId);
				rs=ps.executeQuery();
				while(rs.next())
				{
					DistStockDecOptionsDTO stockdto = new DistStockDecOptionsDTO();
				  	stockdto.setOptionValueAccp(rs.getString("DC_NO"));
				  	stockdto.setOptionText(rs.getString("DC_NO"));
				  	stockAccpTransOptionsDTOList.add(stockdto);
				}
				
				}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally{
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in insertStockDecl "+e.getMessage());
				}
			}
		return	stockAccpTransOptionsDTOList ;
		 } 
   
   public HashMap<String ,ArrayList<DistStockAccpDisplayDTO>> stockAcceptInfo(DistStockAcceptTransferBean stockAcceptTransBean)throws DAOException
   {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			HashMap<String,ArrayList<DistStockAccpDisplayDTO>> stockAccpAccpDispDTOMap = new HashMap<String ,ArrayList<DistStockAccpDisplayDTO>>();
			ArrayList<DistStockAccpDisplayDTO> stockAccpDispTransDTOList = new ArrayList<DistStockAccpDisplayDTO>();
			ArrayList<DistStockAccpDisplayDTO> stockAccpDispProdDTOList = new ArrayList<DistStockAccpDisplayDTO>();
			try {
				con = DBConnectionManager.getDBConnection(); // db2
				
				ps = con.prepareStatement(DBQueries.SQL__GET_Trans_Details);
	//	ps.setInt(1,Integer.parseInt(stockAcceptTransBean.getTransFrom()));
	//			ps.setString(2,stockAcceptTransBean.getTransDCNo());
				rs=ps.executeQuery();
				while(rs.next())
				{
					DistStockAccpDisplayDTO stockTransDTO = new DistStockAccpDisplayDTO();
					stockTransDTO.setQuantity(rs.getString("Quantity"));
					stockTransDTO.setDCDate(rs.getString("Date"));
					stockTransDTO.setTransTo(rs.getString("Transto"));
					stockAccpDispTransDTOList.add(stockTransDTO);
				}
				
				ps = con.prepareStatement(DBQueries.SQL__GET_Trans_Product_Details);
			//	ps.setInt(1,Integer.parseInt(stockAcceptTransBean.getTransFrom()));
			//	ps.setString(2,stockAcceptTransBean.getTransDCNo());
				rs=ps.executeQuery();
				while(rs.next())
				{
					DistStockAccpDisplayDTO stockProdDTO = new DistStockAccpDisplayDTO();
					stockProdDTO.setQuantity(rs.getString("Product_Name"));
					stockProdDTO.setDCDate(rs.getString("Serial_No"));
					stockAccpDispProdDTOList.add(stockProdDTO);
				}
				
				
				stockAccpAccpDispDTOMap.put("Trans", stockAccpDispTransDTOList);
				stockAccpAccpDispDTOMap.put("Prod", stockAccpDispProdDTOList);
				
				
				}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally{
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in insertStockDecl "+e.getMessage());
				}
			}
		return	stockAccpAccpDispDTOMap ;
		 } 
   
   public ArrayList<DiststockAccpTransferDTO> stockAccpDisplayList(DistStockAcceptTransferBean stockAcceptTransBean)throws DAOException
   {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			ArrayList<DiststockAccpTransferDTO> stockAccpTransOptionsDTOList = new ArrayList<DiststockAccpTransferDTO>();
			
			try {
				con = DBConnectionManager.getDBConnection(); // db2
				
				ps = con.prepareStatement(DBQueries.SQL__GET_TransferForm_DCNo_Details);
				ps.setInt(1,stockAcceptTransBean.getAccount_id());
				rs=ps.executeQuery();
				while(rs.next())
				{
					DiststockAccpTransferDTO stockdto = new DiststockAccpTransferDTO();
				  	stockdto.setFromDist_Id(rs.getInt("FROM_DIST_ID"));
				  	stockdto.setStrTransFrom(rs.getString("FROM_DIST_NAME"));
				  	stockdto.setStrDCNo(rs.getString("DC_NO"));
				  	 SimpleDateFormat sdfOutput =new SimpleDateFormat("dd/MM/yyyy") ;  
				     java.sql.Date textDate = rs.getDate("DC_DATE"); 
				     stockdto.setStrDCDate(sdfOutput.format(textDate));
				  	 stockAccpTransOptionsDTOList.add(stockdto);
				}
				
				}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally{
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in insertStockDecl "+e.getMessage());
				}
			}
		return	stockAccpTransOptionsDTOList ;
		 } 
   
   
   
   public ArrayList<DiststockAccpTransferDTO> viewAllSerialNoOfStock(String dcNo)throws DAOException
   {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			ArrayList<DiststockAccpTransferDTO> stockAccpTransOptionsDTOList = new ArrayList<DiststockAccpTransferDTO>();
			
			try {
				con = DBConnectionManager.getDBConnection(); 
				ps = con.prepareStatement(DBQueries.SQL__GET_All_SerialNo_ProdName);
				ps.setString(1,dcNo);
				rs=ps.executeQuery();
				while(rs.next())
				{
					DiststockAccpTransferDTO stockdto = new DiststockAccpTransferDTO();
				  	stockdto.setSerial_No(rs.getString("SERIAL_NO"));
				  	stockdto.setProdName(rs.getString("PRODUCT_NAME"));
				  	stockdto.setProdId(rs.getInt("PRODUCT_ID"));
				  	
				  	stockAccpTransOptionsDTOList.add(stockdto);

				}
				
				}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally{
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in viewAllSerialNoOfStock "+e.getMessage());
				}
			}
		return	stockAccpTransOptionsDTOList ;
		 } 
   
   
   public void updateAcceptStockTransfer(String[] arrCheckedSTA , String account_id)throws DAOException
   {
			Connection con = null;
			PreparedStatement ps_status = null;
			PreparedStatement ps_action = null;
			PreparedStatement ps_distId = null;
			int intAcceptSTA=0;
			ArrayList<DiststockAccpTransferDTO> stockAccpTransOptionsDTOList = new ArrayList<DiststockAccpTransferDTO>();
			
			try {
				con = DBConnectionManager.getDBConnection(); 
		        intAcceptSTA = arrCheckedSTA.length;
				ps_status = con.prepareStatement(DBQueries.SQL__UPDATE_STATUS_STOCK_ACCEPT);
				ps_action = con.prepareStatement(DBQueries.SQL__UPDATE_ACTION_STOCK_ACCEPT);
				ps_distId = con.prepareStatement(DBQueries.SQL__UPDATE_DIST_ID_STOCK_ACCEPT);

				
				for(int i=0; i<intAcceptSTA; i++)
				{	
					stockAccpTransOptionsDTOList=viewAllSerialNoOfStock(arrCheckedSTA[i]);
					ListIterator<DiststockAccpTransferDTO> productIdListItr = stockAccpTransOptionsDTOList.listIterator();
					
					while(productIdListItr.hasNext())
					{
						DiststockAccpTransferDTO stockdto = new DiststockAccpTransferDTO();
						stockdto=productIdListItr.next();
						ps_distId.setInt(1, Integer.parseInt(account_id));
						ps_distId.setString(2, stockdto.getSerial_No());
						ps_distId.setInt(3, stockdto.getProdId());
						ps_distId.executeUpdate();
					}
					
					ps_status.setInt(1, Integer.parseInt(account_id));
					ps_status.setString(2, arrCheckedSTA[i]);
					ps_action.setString(1, arrCheckedSTA[i]);
					ps_status.executeUpdate();
					ps_action.executeUpdate();
					 
					
				}
				con.commit();
				}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally{
				try {
					if (ps_distId != null)
						ps_distId.close();
					if (ps_status != null)
						ps_status.close();
					if (ps_action != null)
						ps_action.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in Update_STATUS_ACTION_DIST_ID_ACCEPT_TRANSFER "+e.getMessage());
				}
			}
		
		 } 
   
}
