package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPCommonDAO;
import com.ibm.dp.dao.ViewStockEligibilityDAO;
import com.ibm.dp.dto.UploadedStockEligibilityDto;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.dp.service.DPCommonService;
import com.ibm.dp.service.impl.DPCommonServiceImpl;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class ViewStockEligibilityDaoImpl implements ViewStockEligibilityDAO
{

	private static Logger logger = Logger.getLogger(ViewStockEligibilityDaoImpl.class.getName());

	public static final String FETCH_PRODUCT = "select PM.PRODUCT_NAME,PM.PRODUCT_ID,PM.PRODUCT_CATEGORY,PM.PRODUCT_TYPE from VR_ACCOUNT_DETAILS AD,DP_PRODUCT_MASTER PM "
			+ " where AD.CIRCLE_ID = PM.CIRCLE_ID "
			+ " and account_id =? and  PM.CATEGORY_CODE=1 and PM.CM_STATUS='A' WITH UR ";
	public static final String FETCH_SECURITY_LOAN = "select DS.SECURITY_AMOUNT, DS.LOAN_AMOUNT, AD.ACCOUNT_NAME from DP_DIST_SECURITY_LOAN DS inner join VR_ACCOUNT_DETAILS AD on DS.DIST_ID=AD.ACCOUNT_ID where DIST_ID=? with ur";
	public static final String FETCH_PRODUCT_SECURITY = "Select PRODUCT_SECURITY from DP_PRODUCT_SECURITY where PRODUCT_CATEGORY =? and PRODUCT_TYPE =? with ur";

	public static final String QUERY_FOR_COMMERCIAL = "select  SECURITY_ELIGIBILITY, SECURITY_BALANCE, SD_ELIG, HD_ELIG, HDDVR_ELIG, SD_ELIG, PROD_ELIG_1 "
			+ " from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= ? "
			+ " and CREATEDDATE=(select max(a.CREATEDDATE) from "
			+ " (select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= ? group by login_name "
			+ " union "
			+ " select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= ? group by login_name "
			+ " ) as a  group by LOGIN_NAME ) "

			+ " union "

			+ " select  SECURITY_ELIGIBILITY, SECURITY_BALANCE, SD_ELIG, HD_ELIG, HDDVR_ELIG,  SD_ELIG, PROD_ELIG_1 "
			+ " from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= ? "
			+ " and CREATEDDATE=(select max(a.CREATEDDATE) from ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_COMMERCIAL_SF where LOGIN_NAME= ? group by login_name "
			+ " union "
			+ " select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_COMMERCIAL_SSD where LOGIN_NAME= ? group by login_name "
			+ " ) as a  group by LOGIN_NAME  ) with ur ";

	public static final String QUERY_FOR_SWAP = "select SECURITY_ELIGIBILITY , SECURITY_DEPOSIT as BALANCE, SD_ELIG AS SD_ELIGIBILITY, HD_ELIG as HD_ELIGIBILITY, PVR_ELIG as PVR_ELIGIBILITY, HDDVR_ELIG AS HDDVR_ELIGIBILITY, SDPLUS_ELIG as SDPLUS_ELIGIBILITY,PROD_ELIG_1 as CAM_ELIGIBILITY "
			+ " from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= ?  "
			+ " and CREATEDDATE=( select max(a.CREATEDDATE) from  ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= ? group by login_name "
			+ " union "
			+ " select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= ? group by login_name ) as a  group by LOGIN_NAME  ) "

			+ " union "

			+ " select  0 as SECURITY_ELIGIBILITY, 0 as BALANCE, SD_ELIG AS SD_ELIGIBILITY, HD_ELIG as HD_ELIGIBILITY, PVR_ELIG as PVR_ELIGIBILITY, HDDVR_ELIG AS HDDVR_ELIGIBILITY, SDPLUS_ELIG as SDPLUS_ELIGIBILITY,PROD_ELIG_1 as CAM_ELIGIBILITY "
			+ " from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= ? and CREATEDDATE=( select max(a.CREATEDDATE) from  ( select max(CREATEDDATE) as CREATEDDATE,login_name from DP_ELIGIBILITY_SWAP_SSD where LOGIN_NAME= ? group by login_name "
			+ " union "
			+ " select max(CREATEDDATE) as CREATEDDATE ,login_name from DP_ELIGIBILITY_SWAP_SF where LOGIN_NAME= ? group by login_name ) as a  group by LOGIN_NAME  ) "
			+ " with ur ";

	public List<ViewStockEligibilityDTO> getStockEligibility(int distID) throws DAOException
	{

		List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		PreparedStatement pstmt2 = null;
		ResultSet rset2 = null;
		ViewStockEligibilityDTO viewStockDto = null;
		Connection con = null;
		int productId = 0;

		try
		{
			// logger.info("\n\ndistID"+distID);
			DPCommonService dpComService = DPCommonServiceImpl.getInstance();
			DPCommonDAO dpComDao = DPCommonDAOImpl.getInstance();
			con = DBConnectionManager.getDBConnection(); // db2
			pstmt = con.prepareStatement(FETCH_PRODUCT);
			pstmt.setInt(1, distID);
			rset = pstmt.executeQuery();
			Integer prodSecurity;
			pstmt2 = con.prepareStatement(FETCH_PRODUCT_SECURITY);
			ViewStockEligibilityDTO balanceDto = getStockEligibilityMainDtl(distID);
			Map<Integer, Integer> distTotalStockMap = dpComService.getDistTotalStock(distID);

			Integer balance = Integer.parseInt(balanceDto.getBalance());
			String balanceSecProdQty = "0";
			Double balanceSecProdQtyDB = 0.0;
			Integer eligibilty;
			Integer maxPoProductQty;
			String prodCat = "";
			String prodType = "";
			// Updated by ARTI
			while (rset.next())
			{
				prodSecurity = 0;
				balanceSecProdQty = "0";
				eligibilty = 0;
				maxPoProductQty = 0;
				balanceSecProdQtyDB = 0.0;
				viewStockDto = new ViewStockEligibilityDTO();
				viewStockDto.setProductName(rset.getString("PRODUCT_NAME"));
				productId = rset.getInt("PRODUCT_ID");
				prodCat = rset.getString("PRODUCT_CATEGORY");
				prodType = rset.getString("PRODUCT_TYPE");
				// logger.info("\n\n productId == "+productId +
				// " and prodCat == "+prodCat+ " and prodType == "+prodType);
				viewStockDto.setAvailableStock(distTotalStockMap.get(productId).toString());

				pstmt2.setString(1, prodCat.trim());
				pstmt2.setString(2, prodType.trim());
				rset2 = pstmt2.executeQuery();
				while (rset2.next())
				{
					prodSecurity = rset2.getInt("PRODUCT_SECURITY");
				}
				rset2.close();
				rset2 = null;
				pstmt2.clearParameters();
				viewStockDto.setProductWiseSecurity(prodSecurity.toString());
				maxPoProductQty = dpComService.getDistMaxPOQty(distID, productId);
				if (maxPoProductQty < 0)
				{
					maxPoProductQty = 0;
				}
				eligibilty = maxPoProductQty;
				viewStockDto.setMaxPoProductQty(maxPoProductQty.toString());
				viewStockDto
						.setMinStock(dpComService.getDistStockEligibilityMin(distID, productId));
				viewStockDto
						.setMaxStock(dpComService.getDistStockEligibilityMax(distID, productId));
				viewStockDto.setMinDays(dpComDao.getMinDays(distID, productId, con));
				viewStockDto.setMaxDays(dpComDao.getMaxDays(distID, productId, con));
				balanceSecProdQtyDB = (Double.parseDouble(balance.toString()))
						/ (Double.parseDouble(prodSecurity.toString()));
				if (balanceSecProdQtyDB < 0)
				{
					balanceSecProdQtyDB = 0.0;
				}
				balanceSecProdQty = String.valueOf((Math.round(balanceSecProdQtyDB)));
				if (balanceSecProdQtyDB < maxPoProductQty)
				{
					eligibilty = Integer.parseInt(balanceSecProdQty);
				}
				viewStockDto.setBalanceSecurityQty(balanceSecProdQty);
				//System.out.println("eligibilty :" + eligibilty);
				viewStockDto.setEligibilty(eligibilty.toString());
				viewStockList.add(viewStockDto);
				//	
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			try
			{
				if (rset != null)
					rset.close();
				if (pstmt != null)
					pstmt.close();
				if (rset2 != null)
					rset2.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl " + e.getMessage());
			}
		}
		logger.info("viewStockListreturn::::" + viewStockList.size());
		return viewStockList;

	}

	public ViewStockEligibilityDTO getStockEligibilityMainDtl(int distID) throws DAOException
	{

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ViewStockEligibilityDTO viewStockDto = null;
		Connection con = null;

		try
		{
			// logger.info("\n\ndistID"+distID);
			DPCommonService dpComService = DPCommonServiceImpl.getInstance();
			con = DBConnectionManager.getDBConnection(); // db2
			pstmt = con.prepareStatement(FETCH_SECURITY_LOAN);
			pstmt.setInt(1, distID);
			rset = pstmt.executeQuery();
			int security = 0;
			int loan = 0;
			// Updated by ARTI

			viewStockDto = new ViewStockEligibilityDTO();
			while (rset.next())
			{
				viewStockDto.setSecurity(rset.getString("SECURITY_AMOUNT"));
				security = rset.getInt("SECURITY_AMOUNT");
				viewStockDto.setLoan(rset.getString("LOAN_AMOUNT"));
				loan = rset.getInt("LOAN_AMOUNT");
				viewStockDto.setStrDistName(rset.getString("ACCOUNT_NAME"));

				// logger.info("SECURITY_AMOUNT =="+security +
				// " and loan amnt==="+loan);
			}
			Integer totSecLoan = security + loan;

			Map<Integer, Double> productEffectivePrice = dpComService
					.getDistTotalStockEffectPrice(distID);
			Double totalEffectivePrice = 0.0;
			if (productEffectivePrice.size() > 0)
			{
				for (Map.Entry<Integer, Double> entry1 : productEffectivePrice.entrySet())
				{
					// logger.info("Product === "+entry1.getKey());

					totalEffectivePrice += entry1.getValue();
				}
			}
			// logger.info("totalEffectivePrice == "+totalEffectivePrice);
			Integer totalEfPrice = totalEffectivePrice.intValue();
			// logger.info("totSecLoan == "+totSecLoan+
			// " and  totalEfPrice integer on == "+totalEfPrice);
			Integer balance = totSecLoan - totalEfPrice;
			viewStockDto.setBalance(balance.toString());
			// logger.info(
			// "In ViewStockEligibilityDaoImpl getStockEligibilityMainDtl()  Query ends here"
			// );

		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			try
			{
				if (rset != null)
					rset.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("Error Occured in getStockEligibilityMainDtl " + e.getMessage());
			}
		}
		return viewStockDto;

	}

	public List<ViewStockEligibilityDTO> getStockEligibilityAdminDoa(String strOLMId)
			throws DAOException
	{
		List<ViewStockEligibilityDTO> listReturn = new ArrayList<ViewStockEligibilityDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection con = null;
		try
		{
			con = DBConnectionManager.getDBConnection(); // db2

			pstmt = con.prepareStatement(DBQueries.VALIDATE_DIST_OLMID);
			pstmt.setString(1, strOLMId);
			rset = pstmt.executeQuery();
			if (rset.next())
			{
				ViewStockEligibilityDTO tempDTO = new ViewStockEligibilityDTO();
				tempDTO.setStrStatus("SUCCESS");
				tempDTO.setStrMessage("Valid Distributor OLM ID");
				listReturn.add(tempDTO);
				tempDTO = null;

				int intDistID = rset.getInt(1);
				tempDTO = getStockEligibilityMainDtl(intDistID);
				listReturn.add(tempDTO);
				tempDTO = null;

				List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();
				viewStockList = getStockEligibility(intDistID);
				logger.info("viewStockList---->getStockEligibilityAdminDoa000::"
						+ viewStockList.size());
				listReturn.addAll(listReturn.size(), viewStockList);
				logger.info("viewStockList---->getStockEligibilityAdminDoa1111 listReturn::"
						+ listReturn.size());
			}
			else
			{
				ViewStockEligibilityDTO tempDTO = new ViewStockEligibilityDTO();
				tempDTO.setStrStatus("FAIL");
				tempDTO.setStrMessage("Invalid Distributor OLM ID");
				listReturn.add(tempDTO);
				tempDTO = null;
				return listReturn;
			}
		}
		catch (SQLException sqle)
		{
			logger.info("Error while getting Stock Eligibility by Admin  ::  " + sqle);
			logger.info("Error while getting Stock Eligibility by Admin  ::  " + sqle.getMessage());
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}
		catch (Exception e)
		{
			logger.info("Error while getting Stock Eligibility by Admin  ::  " + e);
			logger.info("Error while getting Stock Eligibility by Admin  ::  " + e.getMessage());
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		logger.info("viewStockList---->getStockEligibilityAdminDoa::" + listReturn.size());
		return listReturn;
	}

	// Added by neetika for BFR 2
	public List<ViewStockEligibilityDTO> getStockEligibilityUploaded(int distID)
			throws DAOException
	{

		List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		ViewStockEligibilityDTO viewStockDto = null;
		Connection con = null;
		int productId = 0;

		try
		{
			// logger.info("\n\ndistID"+distID);
			// DPCommonService dpComService = DPCommonServiceImpl.getInstance();
			DPCommonDAO dpComDao = DPCommonDAOImpl.getInstance();

			con = DBConnectionManager.getDBConnection(); // db2
			pstmt = con.prepareStatement(FETCH_PRODUCT);
			pstmt.setInt(1, distID);
			rset = pstmt.executeQuery();

			Integer eligibilty = 0;
			String prodCat = "";
			String prodType = "";
			// Updated by ARTI
			while (rset.next())
			{

				viewStockDto = new ViewStockEligibilityDTO();
				viewStockDto.setProductName(rset.getString("PRODUCT_NAME"));
				productId = rset.getInt("PRODUCT_ID");
				prodCat = rset.getString("PRODUCT_CATEGORY");
				prodType = rset.getString("PRODUCT_TYPE");
				// logger.info("\n\n productId == "+productId +
				// " and prodCat == "+prodCat+
				// " and prodType == "+prodType+"eligibilty"+eligibilty);
				eligibilty = dpComDao.getUploadedEligibility(distID, productId);

				viewStockDto.setEligibilty(eligibilty.toString());
				// logger.info("\n\n productId == "+productId +
				// " and prodCat == "+prodCat+
				// " and prodType == "+prodType+"eligibilty"+eligibilty);
				viewStockList.add(viewStockDto);
				//	
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			try
			{
				if (rset != null)
					rset.close();
				if (pstmt != null)
					pstmt.close();

				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl " + e.getMessage());
			}
		}
		logger.info("viewStockListreturn::::" + viewStockList.size());
		return viewStockList;

	}

	/**
	 * The functionality where a distributor can his/her eligibility
	 * 
	 * @param olmid
	 * @return
	 */
	public UploadedStockEligibilityDto getUploadedStockEligibility(String olmid)
	{
		PreparedStatement pstmt = null;
		PreparedStatement pstmtSwap = null;
		ResultSet rsetCommercial = null;
		ResultSet rsetSwap = null;
		UploadedStockEligibilityDto uploadedStockDto = null;
		Connection con = null;

		try
		{
			con = DBConnectionManager.getDBConnection(); // db2
			pstmt = con.prepareStatement(QUERY_FOR_COMMERCIAL);

			for (int i = 1; i < 7; i++)
			{
				pstmt.setString(i, olmid);
			}

			rsetCommercial = pstmt.executeQuery();

			uploadedStockDto = new UploadedStockEligibilityDto();

			while (rsetCommercial.next())
			{
				
				uploadedStockDto.setSecurityEligibilityBalanceCommercial(rsetCommercial
						.getDouble("SECURITY_BALANCE"));
				uploadedStockDto.setSecurityDepositEligibility(rsetCommercial.getInt("SECURITY_ELIGIBILITY"));
				uploadedStockDto.setSdBoxSdPlusEligibility(rsetCommercial.getInt("SD_ELIG"));
				uploadedStockDto.setHdBoxEligibility(rsetCommercial.getInt("HD_ELIG"));
				uploadedStockDto.setHdDvrBoxEligibility(rsetCommercial.getInt("HDDVR_ELIG"));
				uploadedStockDto.setCamEligibilityCommerical(rsetCommercial.getInt("PROD_ELIG_1"));
			}

			pstmtSwap = con.prepareStatement(QUERY_FOR_SWAP);

			for (int i = 1; i < 7; i++)
			{
				pstmtSwap.setString(i, olmid);
			}

			rsetSwap = pstmtSwap.executeQuery();
			
			while (rsetSwap.next())
			{
				uploadedStockDto.setSecurityDepositAvailableQty(rsetSwap.getInt("SECURITY_ELIGIBILITY"));
				uploadedStockDto.setSecurityEligibilityBalanceSwap(rsetSwap.getDouble("BALANCE"));
				uploadedStockDto.setSwapSdEligibility(rsetSwap.getInt("SD_ELIGIBILITY"));
				uploadedStockDto.setSwapHdEligibility(rsetSwap.getInt("HD_ELIGIBILITY"));
				uploadedStockDto.setSwapPVREligibilityProductQty(rsetSwap.getInt("PVR_ELIGIBILITY"));
				uploadedStockDto.setSwapHdDvrEligibility(rsetSwap.getInt("HDDVR_ELIGIBILITY"));
				uploadedStockDto.setSwapHdPlusEligibility(rsetSwap.getInt("SDPLUS_ELIGIBILITY"));
				uploadedStockDto.setCamEligibilitySwap(rsetSwap.getInt("CAM_ELIGIBILITY"));
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rsetCommercial != null)
					rsetCommercial.close();
				if (pstmt != null)
					pstmt.close();
				if (rsetSwap != null)
					rsetCommercial.close();
				if (pstmtSwap != null)
					pstmt.close();
				if (con != null)
					con.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error("Error Occured in ViewStockEligibilityDaoImpl " + e.getMessage());
			}
		}
		return uploadedStockDto;
	}
}