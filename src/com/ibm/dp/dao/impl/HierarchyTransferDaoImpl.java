package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.HierarchyTransferDao;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPStbInBulkDTO;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DpDcChangeStatusDto;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.dto.FseDTO;
import com.ibm.dp.dto.HierarchyTransferDto;
import com.ibm.dp.dto.RetailorDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.utilities.DcCreation;
import com.ibm.utilities.TRNumberCreate;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.exception.DAOException;

public class HierarchyTransferDaoImpl  extends BaseDaoRdbms implements HierarchyTransferDao{

	public static Logger logger = Logger.getLogger(HierarchyTransferDaoImpl.class.getName());
	
	public static final String SQL_ALL_DISTS 			= DBQueries.GET_ALL_DISTRIBUTOR_LIST;
	//public static final String SQL_REV_FSE_LIST 		= DBQueries.GET_REV_FSE_LIST;
	//public static final String SQL_REV_RETAILER_LIST 	= DBQueries.GET_REV_RETAILER_LIST;
	public static final String SQL_HIER_FSE_LIST 		= DBQueries.GET_HIER_FSE_LIST;
	public static final String SQL_HIER_RETAILER_LIST 		= DBQueries.GET_HIER_RETAILER_LIST;
	public static final String SQL_CIRCLE_MST 	= DBQueries.SQL_SELECT_CIRCLES;

		public HierarchyTransferDaoImpl(Connection connection) 
		{
			super(connection);
		}
		
				
		
		public List<CircleDto> getAllCircleList() throws DAOException 
		{
			List<CircleDto> circleListDTO	= new ArrayList<CircleDto>();
			PreparedStatement pstmt = null;
			ResultSet rset			= null;
			CircleDto  circleDto = null;
					
			try
			{
				pstmt = connection.prepareStatement(SQL_CIRCLE_MST);
				rset = pstmt.executeQuery();
				circleListDTO = new ArrayList<CircleDto>();
				
				while(rset.next())
				{
					circleDto = new CircleDto();
					circleDto.setCircleId(rset.getString("CIRCLE_ID"));
					circleDto.setCircleName(rset.getString("CIRCLE_NAME"));
					
									
					circleListDTO.add(circleDto);
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			finally
			{
				pstmt = null;
				rset = null;
			}
			return circleListDTO;
		}
		
		
		
		
		
		public List<DistributorDTO> getAllDistList(String strLoginId,String strAccLevel) throws DAOException {
			logger.info("in HierarchyTransferDaoImpl -> getAllDistList()  lngCrBy=="+strLoginId);
			List<DistributorDTO> distListDto = new ArrayList<DistributorDTO>();
			PreparedStatement pstmt = null;
			ResultSet rset			= null;
			DistributorDTO distDto=null;
			try
			{
				if(strAccLevel.equals("5")){
					pstmt = connection.prepareStatement(SQL_ALL_DISTS);
				}else{
					pstmt = connection.prepareStatement(DBQueries.GET_DISTRIBUTOR_LIST);
				}
				
				pstmt.setString(1, strLoginId);
				rset = pstmt.executeQuery();
				
				distListDto = new ArrayList<DistributorDTO>();
				Integer count=0;
				while(rset.next())
				{
					count++;
					distDto = new DistributorDTO();
					distDto.setAccountId(rset.getString("account_id"));
					distDto.setAccountName(rset.getString("account_name"));
					distListDto.add(distDto);
				}
				logger.info("in HierarchyTransferDaoImpl -> getAllDistList()  Query ends here ==");
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			finally
			{
				pstmt = null;
				rset = null;
			}
			return distListDto;
			
		}
		
		
		public List<FseDTO> getAllFSeList(String strLoginId) throws DAOException {
			logger.info("in HierarchyTransferDaoImpl -> getAllDistList()  lngCrBy=="+strLoginId);
			List<FseDTO> distListDto = new ArrayList<FseDTO>();
			PreparedStatement pstmt = null;
			ResultSet rset			= null;
			FseDTO distDto=null;
			try
			{
//				Commented by Nazim Hussain because showing FSE whose transfer request is already raised
//				pstmt = connection.prepareStatement(SQL_ALL_DISTS);
				pstmt = connection.prepareStatement(SQL_HIER_FSE_LIST);		// Added by nazim hussain
				pstmt.setString(1, strLoginId);
				pstmt.setString(2, strLoginId);
				rset = pstmt.executeQuery();
				
				distListDto = new ArrayList<FseDTO>();
				Integer count=0;
				while(rset.next())
				{
					count++;
					distDto = new FseDTO();
					String strFseId = rset.getString("account_id") +"," +rset.getString("RET_NO") +","+rset.getString("COUNTPENDING");
					logger.info(" FSE Id  =====***********"+strFseId);
					distDto.setAccountId(strFseId);
					distDto.setAccountName(rset.getString("account_name"));
					distListDto.add(distDto);
				}
				logger.info("in HierarchyTransferDaoImpl -> getAllDistList()  Query ends here ==");
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			finally
			{
				pstmt = null;
				rset = null;
			}
			return distListDto;
			
		}
		
		public List<RetailorDTO> getAllRetlerList(String strLoginId) throws DAOException {
			logger.info("in HierarchyTransferDaoImpl -> getAllDistList()  lngCrBy=="+strLoginId);
			List<RetailorDTO> distListDto = new ArrayList<RetailorDTO>();
			PreparedStatement pstmt = null;
			ResultSet rset			= null;
			RetailorDTO distDto=null;
			try
			{
//				Commented by Nazim Hussain because showing Retailers whose transfer request is already raised
//				pstmt = connection.prepareStatement(SQL_ALL_DISTS);
				pstmt = connection.prepareStatement(SQL_HIER_RETAILER_LIST);		// Added by nazim hussain
				pstmt.setString(1, strLoginId);
				pstmt.setString(2, strLoginId);
				
				rset = pstmt.executeQuery();
				
				distListDto = new ArrayList<RetailorDTO>();
				Integer count=0;
				while(rset.next())
				{
					count++;
					distDto = new RetailorDTO();
					distDto.setAccountId(rset.getString("account_id"));
					distDto.setAccountName(rset.getString("account_name"));
					distListDto.add(distDto);
				}
				logger.info("in HierarchyTransferDaoImpl -> getAllDistList()  Query ends here ==");
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			finally
			{
				pstmt = null;
				rset = null;
			}
			return distListDto;
			
		}
		
		public String insertHierarchyTransfer(HierarchyTransferDto transfDto,String crcleId) throws DAOException
		{
			logger.info("in HierarchyTransferDaoImpl -> insertHierarchyTransfer()  lngCrBy==");
			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			PreparedStatement ps4 = null;
			PreparedStatement ps5 = null;
			PreparedStatement ps6 = null;
			PreparedStatement ps7 = null;
			PreparedStatement ps8 = null;
			ResultSet rset			= null;
			String strTrNo ="";
			String[] strSelectedFses=null;
			List StockHistory = new ArrayList();
			//Map StockHistory = new HashMap();
			try 
			{
				connection.setAutoCommit(false);
				TRNumberCreate dcCreation = TRNumberCreate.getTRNumberCreateObject();
				strTrNo = dcCreation.generateTRNo(connection);
				//String strRdbTransType = "";
				Integer intRdbTransType = 0;
				//String strTransSubType ="";
				Integer intTransSubType =0;
				Integer intUnitSelected =0;
				String strCrBy = transfDto.getStrLoginId();
				String strFromFSE = null;
				String  strToFSE=null;
				String strAccountRole ="";
				if(transfDto.getRdbTransfer().equals("1")){
					//strRdbTransType ="FSE";
					intRdbTransType =1;
					//strTransSubType = transfDto.getRdbWithAndWithoutRtlr();
					//intTransSubType = Integer.parseInt(transfDto.getRdbWithAndWithoutRtlr());
					intTransSubType=1;
					strSelectedFses = transfDto.getHiddenFSESelecIds().split(",");
					intUnitSelected=strSelectedFses.length;
					strAccountRole="7";
				}else if(transfDto.getRdbTransfer().equals("2")){
					//strRdbTransType ="RET";
					intRdbTransType=2;
					strSelectedFses = transfDto.getHiddenRetlrSelecIds().split(",");
					intUnitSelected=strSelectedFses.length;
					strFromFSE = transfDto.getFromFSEIdForRtlrTrans().split(",")[0].trim();
					strToFSE = transfDto.getToFSEIdForRtlrTrans().split(",")[0].trim();
					logger.info("From FSE RETAILOR ===== "+transfDto.getFromFSEIdForRtlrTrans());
					strAccountRole="8";
				}
				logger.info("From Dist ===== "+transfDto.getFromDistributorId());
				
				
				
				
				ps = connection.prepareStatement(DBQueries.INSERT_HERARCHY_HEADERS);
				//ps1 = connection.prepareStatement(DBQueries.INSERT_HERARCHY_DETAIL);
				logger.info("*********************INSERT_HERARCHY_HEADERS*****"+DBQueries.INSERT_HERARCHY_HEADERS);
				ps2 =connection.prepareStatement(DBQueries.UPDATE_ACCOUNT_DETAILS_HERARCHY);
				logger.info("***********UPDATE_ACCOUNT_DETAILS_HERARCHY********"+DBQueries.UPDATE_ACCOUNT_DETAILS_HERARCHY);
				ps3 =connection.prepareStatement(DBQueries.UPDATE_STOCK_INVENTORY_HERARCHY);
				logger.info("**********************UPDATE_STOCK_INVENTORY_HERARCHY*******"+DBQueries.UPDATE_STOCK_INVENTORY_HERARCHY);
				ps4 =connection.prepareStatement(DBQueries.UPDATE_STOCK_INVENTORY_HERARCHY_RET);
				logger.info("***********************UPDATE_STOCK_INVENTORY_HERARCHY_RET*********"+DBQueries.UPDATE_STOCK_INVENTORY_HERARCHY_RET);
				ps7 =connection.prepareStatement(DBQueries.INSERT_STOCK_INVENTORY_HERARCHY);
				logger.info("***********************INSERT_STOCK_INVENTORY_HERARCHY******"+DBQueries.INSERT_STOCK_INVENTORY_HERARCHY);
				
				ps8 =connection.prepareStatement(DBQueries.INSERT_STOCK_INVENTORY_HERARCHY_RET);
				logger.info("*******************INSERT_STOCK_INVENTORY_HERARCHY_RET******"+DBQueries.INSERT_STOCK_INVENTORY_HERARCHY_RET);
				
				//To insert values in HIERARCHY_TRANSFER_HDR
				//(TR_NO, TRANS_TYPE, TRANS_SUB_TYPE, FROM_DIST, FROM_FSE, CIRCLE_ID, INIT_UNIT, CREATED_BY, CREATED_ON)
				//TR_NO,TRANS_TYPE,FROM_DIST,TO_DIST,TRANS_SUB_TYPE,TRNS_ACCOUNT_ID,FROM_FSE,TO_FSE,CIRCLE_ID,CREATED_BY,CREATED_ON
				
				 for(int count=0;count<intUnitSelected;count++){
				
				ps.setString(1, strTrNo);
				 //ps.setString(2, strRdbTransType);
				 ps.setInt(2, intRdbTransType);
				 ps.setString(3, transfDto.getFromDistributorId());
				 ps.setString(4, transfDto.getToDistId());
				 ps.setInt(5, intTransSubType);
				 ps.setString(6, strSelectedFses[count]);
				 ps.setString(7, strFromFSE);
				 ps.setString(8, strToFSE);
				 ps.setString(9, transfDto.getCircleIdNew());
				 ps.setString(10, strCrBy);
				 //logger.info(" Queryy === "+ps);
				 
			
					 //ps1.setString(1, strTrNo);
					 //ps1.setString(2, strSelectedFses[count]);
					 //ps1.setString(3, strAccountRole);
					 //ps1.setString(4, Constants.HEIR_TRANS_STATUS_INIT);
					 //ps1.executeUpdate();
					 ps.executeUpdate();
				 }
				
				 
					
					
				 if(transfDto.getRdbTransfer().equals("1"))
				 {
					 String fseIds =transfDto.getHiddenFSESelecIds();
					 String query =DBQueries.SELECT_STOCK_INVENTORY_HERARCHY + fseIds+")";
					 ps5 =connection.prepareStatement(query);
					 //ps5.setString(1,transfDto.getHiddenFSESelecIds());
					 logger.info("fsi ids  " +fseIds + "query "+ query);
					 ps5.setString(1,transfDto.getFromDistributorId());
					 rset = ps5.executeQuery();
					 HierarchyTransferDto dpStockHistory = new HierarchyTransferDto();
					 
					 while(rset.next())
						{
							dpStockHistory = new HierarchyTransferDto();
							dpStockHistory.setSerialNo(rset.getString("SERIAL_NO"));
							dpStockHistory.setProductId(rset.getInt("PRODUCT_ID"));
							dpStockHistory.setFromDist(rset.getInt("DISTRIBUTOR_ID"));
							dpStockHistory.setFromFse(rset.getInt("FSE_ID"));
							dpStockHistory.setFromRetailer(rset.getInt("RETAILER_ID"));
							StockHistory.add(dpStockHistory);
						}
					 
					 	 Iterator itr3 = StockHistory.iterator();
							while (itr3.hasNext()) {
								HierarchyTransferDto dpStockHistoryDto = (HierarchyTransferDto) itr3.next();
								dpStockHistoryDto.setTodist(Integer.parseInt(transfDto.getToDistId()));
								dpStockHistoryDto.setTransfer_by(Integer.parseInt(strCrBy));
								dpStockHistoryDto.setTransId(strTrNo);
								
							} 
					
					 //insert command
							  itr3 = StockHistory.iterator();
								while (itr3.hasNext()) {
									HierarchyTransferDto dpStockHistoryDto = (HierarchyTransferDto) itr3.next();
									ps7.setString(1, dpStockHistoryDto.getSerialNo());
									ps7.setInt(2, dpStockHistoryDto.getProductId());
									ps7.setInt(3, dpStockHistoryDto.getFromDist());
									ps7.setInt(4, dpStockHistoryDto.getTodist());
									ps7.setInt(5, dpStockHistoryDto.getFromFse());
									ps7.setInt(6, dpStockHistoryDto.getTransfer_by());
									ps7.setString(7, dpStockHistoryDto.getTransId());;
									ps7.executeUpdate();
								} 
						 
					 
					 for(int count=0;count<intUnitSelected;count++){
//						 to resolve the retailer transfer issue.
						 ps2.setString(1,transfDto.getToDistId());
						 //System.out.println("updating root level id ");
						 ps2.setString(2,transfDto.getToDistId());
						 ps2.setString(3,strSelectedFses[count]);
						 ps2.executeUpdate();
					 }
					 for(int count=0;count<intUnitSelected;count++){
						 ps3.setString(1,transfDto.getToDistId());
						 ps3.setString(2,strSelectedFses[count]);
						 ps3.executeUpdate();
					 }
				  }else{
					  	 String retailer =transfDto.getHiddenRetlrSelecIds();
						  for(int count=0;count<intUnitSelected;count++){
							  logger.info("Inside New "+strSelectedFses[count]);
						 String query =DBQueries.SELECT_STOCK_INVENTORY_HERARCHY_RET + strSelectedFses[count]+")";
						 ps6 =connection.prepareStatement(query);
						 logger.info("fsi ids  " +retailer + "query "+ query);
						 ps6.setString(2,strFromFSE);
						 ps6.setString(1,transfDto.getFromDistributorId());
						 //ps6.setString(1,transfDto.getHiddenRetlrSelecIds());
						 rset = ps6.executeQuery();
						 HierarchyTransferDto dpStockHistory = new HierarchyTransferDto();
						 
					
					/*	 while(rset.next())
							{
								dpStockHistory = new HierarchyTransferDto();
								dpStockHistory.setSerialNo(rset.getString("SERIAL_NO"));
								dpStockHistory.setProductId(rset.getInt("PRODUCT_ID"));
								dpStockHistory.setFromDist(rset.getInt("DISTRIBUTOR_ID"));
								dpStockHistory.setFromFse(rset.getInt("FSE_ID"));
								dpStockHistory.setFromRetailer(rset.getInt("RETAILER_ID"));
								 logger.info("SERIAL_NO  "+ rset.getString("SERIAL_NO"));
								StockHistory.add(dpStockHistory);
							}
						 
						 	 Iterator itr3 = StockHistory.iterator();
								while (itr3.hasNext()) {
									HierarchyTransferDto dpStockHistoryDto = (HierarchyTransferDto) itr3.next();
									dpStockHistoryDto.setTodist(Integer.parseInt(transfDto.getToDistId()));
									dpStockHistoryDto.setToFse(Integer.parseInt(strToFSE));
									dpStockHistoryDto.setTransfer_by(Integer.parseInt(strCrBy));
									dpStockHistoryDto.setTransId(strTrNo);
									
								} 
						
						 //insert command
								itr3 = StockHistory.iterator();
								while (itr3.hasNext()) {
									HierarchyTransferDto dpStockHistoryDto = (HierarchyTransferDto) itr3.next();
									logger.info(dpStockHistoryDto.getSerialNo()+""+dpStockHistoryDto.getProductId()+"distfrom "+dpStockHistoryDto.getFromDist()+" to dist "+dpStockHistoryDto.getTodist()+""+ dpStockHistoryDto.getFromFse()+"to fse"
								+	dpStockHistoryDto.getToFse()+"from ret  "+	dpStockHistoryDto.getFromRetailer()+" transfer by"+dpStockHistoryDto.getTransfer_by()+" trans id "+dpStockHistoryDto.getTransId()	);
									ps8.setString(1, dpStockHistoryDto.getSerialNo());
									ps8.setInt(2, dpStockHistoryDto.getProductId());
									ps8.setInt(3, dpStockHistoryDto.getFromDist());
									ps8.setInt(4, dpStockHistoryDto.getTodist());
									ps8.setInt(5, dpStockHistoryDto.getFromFse());
									ps8.setInt(6, dpStockHistoryDto.getToFse());
									ps8.setInt(7, dpStockHistoryDto.getFromRetailer());
									ps8.setInt(8, dpStockHistoryDto.getTransfer_by());
									ps8.setString(9, dpStockHistoryDto.getTransId());;
									ps8.executeUpdate();
								}
					*/		
						 logger.info("Inside 123"+strToFSE);
							
						 if(!rset.next())
						 {
				//	  for(int count=0;count<intUnitSelected;count++){
						  logger.info("Inside "+strSelectedFses[count]);
							 ps2.setString(1,strToFSE);
							 //System.out.println("updating root level id ");
							 ps2.setString(2,transfDto.getToDistId());
							 ps2.setString(3,strSelectedFses[count]);
							 ps2.executeUpdate();
						 }
					  
					  connection.commit();
						 }
				/* for(int count=0;count<intUnitSelected;count++){
							 ps4.setString(1,transfDto.getToDistId());
							 ps4.setString(2,strToFSE);
							 ps4.setString(3,strSelectedFses[count]);
							 ps4.executeUpdate();
						 } */
				  }	 
				
				
			}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally
			{
				ps = null;
				ps1=null;
			
			
			}
			
			return strTrNo;
		}
		
		/* Added By Parnika */
		
		
		public boolean isMutuallyExclusive(String fromDistributorId, String toDistId) throws  DAOException{
			logger.info("in HierarchyTransferDaoImpl -> isMutuallyExclusive() ");
			
			boolean isExclusive = true;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String value = null;
			try
			{

				pstmt = connection.prepareStatement(DBQueries.SQL_GET_DISTRIBUTORS_TRANSACTION);		
				pstmt.setInt(1, Integer.parseInt(fromDistributorId));
				pstmt.setInt(2, Integer.parseInt(toDistId));
				rset = pstmt.executeQuery();
				if(rset.next())
				{
					isExclusive = false; // They are not mutually Exclusive

				}
				logger.info("in HierarchyTransferDaoImpl -> isMutuallyExclusive()  Query ends here ==");
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			finally
			{
				pstmt = null;
				rset = null;
			}
			return isExclusive;
			
		}
		
		/* End of changes By Parnika */
		//Neetika
		public List<CircleDto> getAllCircleListCircleAdmin(long id) throws DAOException 
		{
			List<CircleDto> circleListDTO	= new ArrayList<CircleDto>();
			PreparedStatement pstmt = null;
			ResultSet rset			= null;
			CircleDto  circleDto = null;
					
			try
			{
				
				String sql="select map.circle_id, a.circle_name from vr_circle_master a, dp_account_circle_map map where map.circle_id=a.circle_id and map.account_id=? with ur";
				pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, id);
				rset = pstmt.executeQuery();
				circleListDTO = new ArrayList<CircleDto>();
				int circle=-1;
				while(rset.next())
				{
					if(Integer.parseInt(rset.getString("CIRCLE_ID"))!=0)
					{
					circleDto = new CircleDto();
					circleDto.setCircleId(rset.getString("CIRCLE_ID"));
					circleDto.setCircleName(rset.getString("CIRCLE_NAME"));	
					circleListDTO.add(circleDto);
					}
					else
					{
					circle=0;	
					}
				}
				if(circle==0)
				{
					String strQuery = "SELECT CIRCLE_ID, CIRCLE_NAME FROM VR_CIRCLE_MASTER where STATUS=? AND CIRCLE_ID !=0 ORDER BY CIRCLE_NAME";
					
					
						pstmt = connection.prepareStatement(strQuery);
						pstmt.setString(1, Constants.ACCOUNT_ACTIVE_STATUS);
						rset = pstmt.executeQuery();
						
						CircleDto selCol = null;
						while(rset.next())
						{
							selCol = new CircleDto();
							selCol.setCircleId(rset.getString(1));
							selCol.setCircleName(rset.getString(2));
							
							circleListDTO.add(selCol);
						}
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			finally
			{
				pstmt = null;
				rset = null;
			}
			return circleListDTO;
		}
		
}
