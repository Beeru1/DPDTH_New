package com.ibm.dp.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;



public class GetProperties extends HttpServlet {
	private static Logger logger = Logger.getLogger(GetProperties.class.getName());
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException 
	{
			try
			{
				IEncryption crypt = new Encryption();
				Properties prop = new Properties();
				//prop.load(new FileInputStream("D:\\DP-DTH\\DPDTH_AV_Tracking\\DPSCM\\lib\\RechargeCore.properties"));
				prop.load(new FileInputStream("/home/dpportal/DPSCM/lib/RechargeCore.properties"));
				
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				
				String userName=prop.getProperty("database.username");
				String encryptpassword=prop.getProperty("database.password");
				String password=crypt.decrypt(prop.getProperty("database.password"));
				
				
				out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n<HTML>\n<HEAD><TITLE>Hello WWW</TITLE></HEAD>\n" +
						"<BODY><table border=1><tr><td>UserName::</td><td>"+ userName +" </td></tr> " + 
						"<tr><td> Password::</td><td>"+encryptpassword +"</td></tr><tr><td>Password ::</td><td> "+ password +" </td></tr></table></BODY></HTML>");
				
				/*out.println("UserName::"++"    ");
				out.println("\n\nPassword::"+prop.getProperty("database.password")+"    ");
				out.println("\n\nPassword::"+crypt.decrypt(prop.getProperty("database.password"))+"    ");*/
			}
			catch (Exception e) {
				System.out.println(e);
				logger.info(e);
			}
	}

}
