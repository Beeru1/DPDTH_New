package com.ibm.dp.webServices;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.text.DateFormat;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.ibm.hbo.exception.HBOException;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class WebservicePasswordChange {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String pwdChangeDate = ResourceReader.getPwdChangeParams(Constants.WEBSERVICE_PWD_CHANGE_DATE);
			//System.out.println("pwdChangeDate"+pwdChangeDate);
			Date dt=new Date();
			
			try{
				DateFormat ss = new SimpleDateFormat("MM/dd/yyyy");
				Date changeDt = ss.parse(pwdChangeDate);
				
				Calendar cal = Calendar.getInstance();
				//cal1.add(Calendar.DAY_OF_MONTH,-43);
				
				Calendar cal1 = Calendar.getInstance();
				cal1.add(Calendar.DAY_OF_MONTH,-43);
				
				Calendar cal2 = Calendar.getInstance();
				cal2.add(Calendar.DAY_OF_MONTH,-44);
				
				Calendar cal3 = Calendar.getInstance();
				cal3.add(Calendar.DAY_OF_MONTH,-45);
				
				String currentDt = ss.format(cal.getTime());
				Date currentDate = ss.parse(currentDt);
				
				String date1 = ss.format(cal1.getTime());
				Date pastDt1 = ss.parse(date1);
				
				String date2 = ss.format(cal1.getTime());
				Date pastDt2 = ss.parse(date2);
				
				String date3 = ss.format(cal1.getTime());
				Date pastDt3 = ss.parse(date3);
				
				//System.out.println(changeDt);
				/*System.out.println(currentDt);
				System.out.println(changeDt.equals(currentDt));*/
				WebservicePasswordChange pwdChange = new WebservicePasswordChange();
				if(changeDt.equals(pastDt1)){
					pwdChange.sendMail(3);
					System.out.println("inside if");
				}else if(changeDt.equals(pastDt2)){
					pwdChange.sendMail(2);
				}else if(changeDt.equals(pastDt3)){
					pwdChange.sendMail(1);
				}
				else if(changeDt.equals(currentDate)){
					//System.out.println("inside else");
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			/*Format format = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");*/
			/*String today = format.format(dt);
			Date changedDt = df.parse(pwdChangeDate); 
			Date changedDate= new Date(pwdChangeDate);
			dt.setHours(0);
			dt.setMinutes(0);
			dt.setSeconds(0);*/
			/*System.out.println("changedDate"+changedDate);
			System.out.println("date"+dt);
			System.out.println("compare"+changedDate.getTime());
			System.out.println("compare"+dt.getTime());*/
			
			/*int mnthToday = Integer.parseInt(today.substring(0,today.indexOf("/"))); 
			int dayToday = Integer.parseInt(today.substring(today.indexOf("/")+1,today.lastIndexOf("/")));
			int yearToday = Integer.parseInt(today.substring(today.lastIndexOf("/")+1,today.length()));
			
			int mnthChangedDt = Integer.parseInt(today.substring(0,today.indexOf("/"))); 
			int dayChangedDt = Integer.parseInt(today.substring(today.indexOf("/")+1,today.lastIndexOf("/")));
			int yearChangedDt = Integer.parseInt(today.substring(today.lastIndexOf("/")+1,today.length()));*/
		} catch (VirtualizationServiceException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public String sendMail(int days)
	throws HBOException, DAOException {
		String message = "success";
		//String receiver_add = sendMail.getReceiverId();
		//String textMessage = sendMail.getMessage_Sent();
		try {
		String receiver_add = ResourceReader.getPwdChangeParams(Constants.WEBSERVICE_PWD_RECEIVER_ADD);
		String textMessage1 = ResourceReader.getPwdChangeParams(Constants.WEBSERVICE_PWD_MSG1);
		String textMessage2 = ResourceReader.getPwdChangeParams(Constants.WEBSERVICE_PWD_MSG2);
		String senderName = ResourceReader.getPwdChangeParams(Constants.WEBSERVICE_PWD_SENDER);
		//System.out.println("days"+days);
		String strHost = ResourceReader.getPwdChangeParams(Constants.WEBSERVICE_SMTP);
		String strFromEmail = ResourceReader.getPwdChangeParams(Constants.WEBSERVICE_PWD_FROM_EMAIL);
		String ccAdd = ResourceReader.getPwdChangeParams(Constants.WEBSERVICE_PWD_CC_EMAIL);
		StringBuffer str = new StringBuffer();
		str.append(textMessage1);
		str.append(" "+days+" "+textMessage2);
		//str.append("\n");
		str.append("\n");
		str.append("Regards,");
		str.append("\n");
		str.append(senderName);
		str.append("\n");
		
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", strHost);
		//logger.info("Host Email"+strHost);
		Session ses = Session.getDefaultInstance(prop, null);
		MimeMessage msg = new MimeMessage(ses);
		msg.setFrom(new InternetAddress(strFromEmail));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				receiver_add));
		msg.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAdd));
		msg.setSubject("Password about to expire");
		msg.setText(str.toString());
		msg.setSentDate(new Date());
		Transport.send(msg);
		}catch (VirtualizationServiceException e) {
			message = "failure";
			e.printStackTrace();
			//logger.error("Exception occured in sendEmailService():"
			//		+ e.getMessage());
			//throw new HBOException(e.getMessage());
		}catch (Exception e) {
			message = "failure";
			e.printStackTrace();
			//logger.error("Exception occured in sendEmailService():"
			//		+ e.getMessage());
			//throw new HBOException(e.getMessage());
		}
		return message;
		}
}
