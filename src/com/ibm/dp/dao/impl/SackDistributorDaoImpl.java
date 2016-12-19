package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.SackDistributorDao;
import com.ibm.dp.dto.SackDistributorDetailDto;
import com.ibm.dp.dto.TSMDto;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class SackDistributorDaoImpl extends BaseDaoRdbms implements SackDistributorDao{

	public static Logger logger = Logger.getLogger(SackDistributorDaoImpl.class.getName());
	
	public static final String SQL_ALL_TSM 			= DBQueries.SELECT_ALL_TSM;
	public static final String SQL_ALL_DIST_LIST 		= DBQueries.SELECT_ALL_DISTRIBUTOR_LIST;
	public static final String SQL_DIST_LIST 	= DBQueries.SELECT_DISTRIBUTOR_LIST;
	public static final String SQL_CHECK_STOCK 	= DBQueries.CHECK_DISTRIBUTOR_STOCK;
	public static final String SQL_UPDATE_DISTRIBUTOR_STATUS 	= DBQueries.UPDATE_DISTRIBUTOR_STATUS;
		public SackDistributorDaoImpl(Connection connection) 
		{
			super(connection);
		}
		
		
		
		public List<TSMDto> getTSMMaster(String strLoginId) throws DAOException {
			List<TSMDto> tsmListDto = new ArrayList<TSMDto>();
			PreparedStatement pstmt = null;
			ResultSet rset			= null;
			TSMDto tsmDto=null;
			try
			{
					pstmt = connection.prepareStatement(SQL_ALL_TSM);
				
				//pstmt.setString(1, strLoginId);
				rset = pstmt.executeQuery();
				
				tsmListDto = new ArrayList<TSMDto>();
				Integer count=0;
				while(rset.next())
				{
					count++;
					tsmDto = new TSMDto();
					tsmDto.setAccountId(rset.getString("account_id"));
					tsmDto.setAccountName(rset.getString("account_name"));
					tsmListDto.add(tsmDto);
				}
				logger.info("in SackDistributorDaoImpl -> getTSMMaster()  Query ends here ==");
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rset);
			}
			return tsmListDto;
			
		}
		
		
		public List<SackDistributorDetailDto> getDistDetailList(String tsmId,String circleId)  throws DAOException 
		{
			List<SackDistributorDetailDto> distListDTO	= new ArrayList<SackDistributorDetailDto>();
			PreparedStatement pstmt = null;
			ResultSet rset			= null;
			ResultSet rset1			= null;
			SackDistributorDetailDto  distDto = null;
			Integer stockCount=0;
			Integer fseCount=0;
			try
			{
				if(tsmId.equals("0")){
					System.out.println("circleId"+circleId);
					pstmt = connection.prepareStatement(SQL_ALL_DIST_LIST);
					//pstmt.setString(1, circleId);
				}else{
					pstmt = connection.prepareStatement(SQL_DIST_LIST);
					pstmt.setString(1, tsmId);
				}
				rset = pstmt.executeQuery();
				distListDTO = new ArrayList<SackDistributorDetailDto>();
				int j =0;
				while(rset.next())
				{
					j++;
					String strDistId = rset.getString("ACCOUNT_ID");
					rset1=getDistStockCount(strDistId);
					distDto = new SackDistributorDetailDto();
					distDto.setAccountId(strDistId);
					distDto.setAccountName(rset.getString("ACCOUNT_NAME"));
					distDto.setCircleId(circleId);
					distDto.setCityName(rset.getString("CITY_NAME"));
					distDto.setContactName(rset.getString("CONTACT_NAME"));
					distDto.setMobileNumber(rset.getString("MOBILE_NUMBER"));
					distDto.setZone(rset.getString("ZONE_NAME"));
					while(rset1.next())
					{
						if(rset1.getString(2).equals("FSE")){
							distDto.setFseCount(rset1.getInt(1));
							fseCount=rset1.getInt(1);
						}else if(rset1.getString(2).equals("STOCK")){
							distDto.setStockCount(rset1.getInt(1));
							stockCount=rset1.getInt(1);
						}
							
						
					}
					logger.info("strDistId  ==== "+strDistId + " || stockCount==="+stockCount + "  || fseCount =="+fseCount );
					distListDTO.add(distDto);
				}
				logger.info("List size ==== "+distListDTO.size());
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
			return distListDTO;
		}
		
		private ResultSet getDistStockCount(String distId)  throws DAOException 
		{
			PreparedStatement pstmt = null;
			ResultSet rset			= null;
			try
			{
					pstmt = connection.prepareStatement(SQL_CHECK_STOCK);
					pstmt.setString(1, distId);
					pstmt.setString(2, distId);
					rset = pstmt.executeQuery();
					
			
				
			}		
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			finally
			{
			//Added by Shilpa Khanna to relese resource
			pstmt = null;
			//rset = null;
				
			}
			return rset;
		}
		
		
		 public String sackDistributor(ListIterator<SackDistributorDetailDto> distDetailDtoListItr)throws DAOException
		   {
				PreparedStatement ps = null;
				String strMessage ="";
				try 
					{
						connection.setAutoCommit(false);
						SackDistributorDetailDto distDetailDto = null;
						
						String strAccountId = "";
						ps = connection.prepareStatement(SQL_UPDATE_DISTRIBUTOR_STATUS);
							 
						while(distDetailDtoListItr.hasNext()) 
						{	
							distDetailDto = distDetailDtoListItr.next();
							 strAccountId = distDetailDto.getAccountId();
							 //To Update IS_DISABLE status values in VR_ACCOUNT_DETAIL
							 
							 ps.setString(1, distDetailDto.getStrRemarks());
							 ps.setString(2, strAccountId);
							
							 ps.executeUpdate();
							 
							
						}
						
						strMessage ="SUCCESS";
						connection.commit();
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
						
					
					}
				return strMessage;
					
				 }
		   
}
