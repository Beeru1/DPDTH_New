package com.ibm.hbo.dao.impl;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.dao.HBOReportsDAO;
import com.ibm.hbo.dto.HBOReportDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.DAOException;

public class HBOReportsDAOImpl implements HBOReportsDAO {
	/** 
		* Logger for this class. Use logger.log(message) for logging. Refer to @link http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html for logging options and configuration.
	**/

	private static Logger logger =
		Logger.getLogger(HBOStockDAOImpl.class.toString());

	/* (non-Javadoc)
	 * @see com.ibm.hbo.dao.HBOReportsDAO#findByPrimaryKey(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ArrayList findByPrimaryKey(HBOUserBean userBean, int repId, HBOReportDTO reportDTO) throws DAOException {
		
		ArrayList arrReportData = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		final StringBuffer SQL_SELECT = new StringBuffer("");
		
		CallableStatement cstmt = null;

		String inputData = getInputString(repId,reportDTO);
		logger.info("findByPrimaryKey------------repId 11111111          " +repId);
		logger.info("inputData 11111111------------" +inputData);
		try {
			 logger.info(">>>>>>>>findbyprimary");
			  con = DBConnection.getDBConnection();
			  cstmt = con.prepareCall("{ call PKG_reports.getReportData(?,?,?, ?) }" );
			  cstmt.setInt(1, repId);
			  cstmt.setString(2, userBean.getUserLoginId());
			
			  cstmt.setString(3, inputData);
			  cstmt.registerOutParameter(4, OracleTypes.CURSOR);
			  //cstmt.setInt(2, 104);
		
			  cstmt.execute();
			
			  rs = (ResultSet) cstmt.getObject(4);
			  arrReportData = fetchResult(repId,rs);
			  logger.info("arrReportData----------++++++++"+arrReportData.size());
			  
			  while (rs.next()) {
				  logger.info(rs.getString(1) + "#" + rs.getString(2) + "#" + rs.getString(3));
			  }
			return arrReportData;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured : Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured : Exception Message: "+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (cstmt != null)
					cstmt.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
			
		}
	}

	/**
	 * @param rs
	 * @return
	 */
	private ArrayList fetchResult(int repId,ResultSet rs) throws SQLException {
		HBOReportDTO reportDTO = null;
		ArrayList arrData = new ArrayList();
					
		while (rs.next()) {
			reportDTO = new HBOReportDTO();
			switch(repId) {
				case 1:
						reportDTO.setMcode(rs.getString("model_code"));
						reportDTO.setCircleName(rs.getString("circle_name"));
						reportDTO.setWarehouseName(rs.getString("warehouse_name"));
						reportDTO.setRoleId(rs.getString("Role_Id"));
						reportDTO.setBundleSIH(rs.getString("bundled_sih"));
						reportDTO.setUnbundleSIH(rs.getString("unbundled_sih"));
						//reportDTO.setTotaleSIH(rs.getString("totalStock"));	
						reportDTO.setTotaleSIH(Integer.parseInt(rs.getString("bundled_sih")) + Integer.parseInt(rs.getString("unbundled_sih")) +"");		
						arrData.add(reportDTO);
						break;	
						
				case 2:	
						reportDTO.setDate(rs.getString("created_dt"));
						reportDTO.setMname(rs.getString("model_name"));
						reportDTO.setRequisitionId(rs.getString("requisition_id"));
						reportDTO.setRequisition_qty(rs.getString("requisition_qty"));
						reportDTO.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
						reportDTO.setFor_month(getMonthName(rs.getInt("for_month")));
						reportDTO.setQty_received(rs.getString("Qty_Received"));
						reportDTO.setInvoice_dt(rs.getString("invoice_dt"));
						reportDTO.setVariance(rs.getString("Difference"));			
						arrData.add(reportDTO);
						break;	
				
				case 3: 
						reportDTO.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
						reportDTO.setMcode(rs.getString("MODEL_CODE"));
						reportDTO.setMname(rs.getString("MODEL_NAME"));
						reportDTO.setRequisitionId(rs.getString("REQUISITION_NO"));
						reportDTO.setCreated_dt(rs.getString("REQUISITION_DT"));
						reportDTO.setRequisition_qty(rs.getString("REQUISITION_QTY"));
						reportDTO.setQty_received(rs.getString("UPLOADED_QUANTITY"));
						reportDTO.setPrimary_sales(rs.getString("PRIMARY_SALES"));
						reportDTO.setDamage_stock(rs.getString("DAMAGE_STOCK"));
						arrData.add(reportDTO);
						break;	
			
					case 6:
							reportDTO.setMcode(rs.getString("model_code"));
							reportDTO.setCircleName(rs.getString("circle_name"));
							reportDTO.setWarehouseName(rs.getString("warehouse_name"));
							reportDTO.setWarehouseCity(rs.getString("warehouse_city"));
							reportDTO.setRoleId(rs.getString("Role_Id"));
							reportDTO.setStockInHand(rs.getString("SIH"));
							reportDTO.setPrimarySale(rs.getString("PrimarySale"));
							reportDTO.setSecondarySale(rs.getString("Secondarysale"));							  
							arrData.add(reportDTO);
							break;		
					case 10:
							reportDTO.setMcode(rs.getString("model_code"));
							reportDTO.setCircleName(rs.getString("circle_name"));
							reportDTO.setWarehouseName(rs.getString("warehouse_name"));
							reportDTO.setApr_sale(rs.getString("apr"));
							reportDTO.setMay_sale(rs.getString("may"));
							reportDTO.setJun_sale(rs.getString("jun"));
							reportDTO.setJul_sale(rs.getString("jul"));
							reportDTO.setAug_sale(rs.getString("aug"));
							reportDTO.setSep_sale(rs.getString("sep"));
							reportDTO.setOct_sale(rs.getString("oct"));
							reportDTO.setNov_sale(rs.getString("nov"));
							reportDTO.setDec_sale(rs.getString("dec1"));
							reportDTO.setJan_sale(rs.getString("jan"));
							reportDTO.setFeb_sale(rs.getString("feb"));
							reportDTO.setMar_sale(rs.getString("mar"));
							arrData.add(reportDTO);
							break;
				
//				
//				case 11: 
//						logger.info(">>>>>>>>fetchresult");
//						reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
//						reportDTO.setImei_no(rs.getString("IMEI_NO"));
//						reportDTO.setSim_no(rs.getString("SIM_NO"));
//						reportDTO.setMsidn_no(rs.getString("MSIDN_NO"));
//						reportDTO.setInvoice_dt(rs.getString("INVOICE_DT").substring(0,10));
//						reportDTO.setRequisitionId(rs.getString("REQUISITION_ID"));
//						arrData.add(reportDTO);
//				break;	
				
	
					case 111: 
	
							reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
							reportDTO.setImei_no(rs.getString("IMEI_SIM_NO"));
							reportDTO.setSim_no(rs.getString("SIM_NO"));
							reportDTO.setMsidn_no(rs.getString("MSIDN_NO"));
							reportDTO.setInvoice_dt(rs.getString("INVOICE_DT"));
							reportDTO.setRequisitionId(rs.getString("REQUISITION_ID"));
							arrData.add(reportDTO);
					break;
		
					case 112: 
							reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
							reportDTO.setImei_no(rs.getString("IMEI_SIM_NO"));
							reportDTO.setSim_no(rs.getString("SIM_NO"));
							reportDTO.setMsidn_no(rs.getString("MSIDN_NO"));
							reportDTO.setInvoice_dt(rs.getString("INVOICE_DT"));
							reportDTO.setRequisitionId(rs.getString("REQUISITION_ID"));
							arrData.add(reportDTO);
					break;		
		
					case 12: 
									logger.info(">>>>>>>>fetchresult");
									reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
									reportDTO.setImei_no(rs.getString("IMEI_NO"));
									reportDTO.setSim_no(rs.getString("SIM_NO"));
									reportDTO.setMsidn_no(rs.getString("MSIDN_NO"));
									reportDTO.setInvoice_dt(rs.getString("INVOICE_DT"));
									reportDTO.setRequisitionId(rs.getString("REQUISITION_ID"));
									arrData.add(reportDTO);
							break;		

							case 91: 
									logger.info(">>>>>>>>fetchresult");
									reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
									reportDTO.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
									reportDTO.setQty_received(rs.getString("stock_recieved"));
									reportDTO.setQty_used(rs.getString("used_qty"));
									reportDTO.setQty_available(rs.getString("avail_qty"));
									arrData.add(reportDTO);
							break;		
				//////////////
				
				case 13:
						reportDTO.setMcode(rs.getString("REQUISITION_PRODUCT_CODE"));	
						reportDTO.setRequisitionID(rs.getString("REQUISITION_ID"));
						reportDTO.setInvoiceDt(rs.getString("INVOICE_DT"));
						reportDTO.setImeiNO(rs.getString("IMEI_NO"));
						reportDTO.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
						reportDTO.setSimNo(rs.getString("SIM_NO"));
						reportDTO.setMsidnNo(rs.getString("MSIDN_NO"));
						reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
						arrData.add(reportDTO);
					break;	
	
					case 14:
						reportDTO.setMcode(rs.getString("PRODUCT_CODE"));	
						reportDTO.setMinReorderQty(rs.getString("MIN_REORDER_LEVEL_QTY"));
						reportDTO.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
						reportDTO.setWarehouseCity(rs.getString("WAREHOUSE_CITY"));
						reportDTO.setStckInHand(rs.getString("SIH"));
						reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
						arrData.add(reportDTO);
						break;
					case 81:
						reportDTO.setMcode(rs.getString("PRODUCT_CODE"));	
						reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
						reportDTO.setCurrMonth(rs.getString("SUM(X.MONTH1_QTY)"));
						reportDTO.setNextMonth(rs.getString("SUM(X.MONTH2_QTY)"));
						reportDTO.setNxtnxtMonth(rs.getString("SUM(X.MONTH3_QTY)"));
						arrData.add(reportDTO);
					break;
					case 82:
						reportDTO.setMcode(rs.getString("MODEL_CODE"));	
						reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
						reportDTO.setApr_sale(rs.getString("APR"));
						reportDTO.setMay_sale(rs.getString("MAY"));
						reportDTO.setJun_sale(rs.getString("JUN"));
						reportDTO.setJul_sale(rs.getString("JUL"));
						reportDTO.setAug_sale(rs.getString("AUG"));
						reportDTO.setSep_sale(rs.getString("SEP"));
						reportDTO.setOct_sale(rs.getString("OCT"));
						reportDTO.setNov_sale(rs.getString("NOV"));
						reportDTO.setDec_sale(rs.getString("DEC1"));
						reportDTO.setJan_sale(rs.getString("JAN"));
						reportDTO.setFeb_sale(rs.getString("FEB"));
						reportDTO.setMar_sale(rs.getString("MAR"));
						arrData.add(reportDTO);
					break;
					case 9:
						int totaldays = Calendar.getInstance().getActualMaximum(Calendar.DATE); 
						Date dt= new Date();
						int date=dt.getDate();
						DecimalFormat df = new DecimalFormat("0.00");
						reportDTO.setMcode(rs.getString("MODEL_CODE"));	
						reportDTO.setCircleName(rs.getString("CIRCLE_NAME"));
						reportDTO.setProjectionQty(rs.getString("PROJECTION_QTY"));
						reportDTO.setStckInHand(rs.getString("SIH"));
						reportDTO.setPrimarySale(rs.getString("PRIMARYSALE"));
						reportDTO.setSecondarySale(rs.getString("SECONDARYSALE"));
						float projection=rs.getFloat("PROJECTION_QTY");
						float weightedVar=rs.getFloat("PRIMARYSALE")-(projection*date/totaldays);
						reportDTO.setWeightedVar(df.format(weightedVar));
						if(projection!=0){
							float weightedVarPer=weightedVar/projection*100;
							reportDTO.setWeightedVarPer(df.format(weightedVarPer));
						}
						else{
							reportDTO.setWeightedVarPer("--");
						}
						arrData.add(reportDTO);
					break;
				
			}
		}
		return arrData;
	}

	/**
	 * Method to create the Input String concatenated by hypen and passed to Report Procedure
	 * @author Sanjay
	 * @param repId
	 * @param reportDTO
	 * @return
	 */
	private String getInputString(int repId,HBOReportDTO reportDTO) {
		StringBuffer inputString = new StringBuffer("");
		switch(repId) {
			case 1:
				inputString.append(reportDTO.getMcode());
				inputString.append("#");
				inputString.append(reportDTO.getCircleId());
				break;
			case 6:
				inputString.append(reportDTO.getMcode());
				inputString.append("#");
				inputString.append(reportDTO.getCircleId());
				break;
			case 10:
				inputString.append(reportDTO.getMcode());
				inputString.append("#");
				inputString.append(reportDTO.getCircleId());
				break;				
			case 2:
				inputString.append(reportDTO.getWarehouse_to());
				logger.info("-------------->>>>>>>>>"+reportDTO.getWarehouse_to());
				inputString.append("#");
				//Array[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				//int monthId = Integer.parseInt(reportDTO.getFromDate().substring(reportDTO.getFromDate().indexOf("/")+1,reportDTO.getFromDate().lastIndexOf("/")));
				inputString.append(reportDTO.getFromDate());
				logger.info("monthfrom>>>>>>>>>>>>"+reportDTO.getFromDate());
				
				inputString.append("#");
				inputString.append(reportDTO.getToDate());
				logger.info("monthto>>>>>>>>>>>>"+reportDTO.getToDate());
				logger.info("--------------"+inputString);
				break;
				
			case 3:
				logger.info(">>>>>>>>getinputstring");
				inputString.append(reportDTO.getMcode());
				inputString.append("#");
				inputString.append(reportDTO.getFromDate());
				inputString.append("#");
				inputString.append(reportDTO.getToDate());
				logger.info("----------------->>>>>>>>>>"+inputString);
				break;
				
			case 11:
				logger.info(">>>>>>>>getinputstring");
				inputString.append(reportDTO.getMcode());
				inputString.append("#");
				inputString.append(reportDTO.getFromDate());
				inputString.append("#");
				inputString.append(reportDTO.getToDate());
				logger.info("----------------->>>>>>>>>>"+inputString);
				break;
							
			case 111:
				inputString.append(reportDTO.getMcode());
				inputString.append("#");
				inputString.append(reportDTO.getFromDate());
				inputString.append("#");
				inputString.append(reportDTO.getToDate());
			
				break;
			
			case 112:
				inputString.append(reportDTO.getMcode());
				inputString.append("#");
				inputString.append(reportDTO.getFromDate());
				inputString.append("#");
				inputString.append(reportDTO.getToDate());
			
				break;
				
			case 12:
				inputString.append(reportDTO.getMcode());
				inputString.append("#");
				inputString.append(reportDTO.getFromDate().trim());
				inputString.append("#");
				inputString.append(reportDTO.getToDate().trim());
				inputString.append("#");
				inputString.append(reportDTO.getWarehouse_to());
				break;
			
			case 91:
				logger.info(">>>>>>>>getinputstring");
				logger.info("----------------->>>>>>>>>>"+inputString);
				break;
			case 13:
				inputString.append(reportDTO.getMcode());
				inputString.append("#");
				inputString.append(reportDTO.getFromDate());
				inputString.append("#");
				inputString.append(reportDTO.getToDate());
				break;
			case 14:
			inputString.append(reportDTO.getMcode());
			break;
			case 81:
			inputString.append(reportDTO.getMcode());
			break;
			case 82:
			inputString.append(reportDTO.getMcode());
			break;	
			case 9:
			inputString.append(reportDTO.getMcode());
			break;			
				
		}
		return inputString.toString();
	}
	public String getMonthName(int monthId){ 
	   String[] month = {"January","February","March","April","May","June","July","August","September","October","November","December"};
		return month[monthId-1];
   }
}