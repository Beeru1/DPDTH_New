package com.ibm.dp.actions;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.service.impl.DpInvoiceUploadServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;

public class DownloadSignedPdf extends Action {
	
	
	public ActionForward execute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		
		
		
		
	    String fileName=(String) request.getParameter("fileName");
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition","attachment;filename="+fileName);
	    UserSessionContext sessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);   
	    File file=null;
	    FileInputStream in=null;
	    
	     try 
	     {
	       	//Get it from file system
	    	 
	       	
	        DpInvoiceUploadServiceImpl dpinSrvc=new DpInvoiceUploadServiceImpl();
	        file=dpinSrvc.getSignedInvoices(sessionContext.getLoginName(), fileName);
	        in=new FileInputStream(file);
	        //Get it from web path
	        //jndi:/localhost/StrutsExample/upload/superfish.zip
	        //URL url = getServlet().getServletContext()
	        //               .getResource("upload/superfish.zip");
	        //InputStream in = url.openStream();
	        	
	        //Get it from bytes array
	        //byte[] bytes = new byte[4096];
	        //InputStream in = new ByteArrayInputStream(bytes);

	        ServletOutputStream out = response.getOutputStream();
	        	 
	        byte[] outputByte = new byte[20480];
	        //copy binary content to output stream
	        while(in.read(outputByte, 0, 20480) != -1){
	        	out.write(outputByte, 0, 20480);
	        }
	        in.close();
	        out.flush();
	        out.close();
	        file.delete();

	     }catch(Exception e){
	    	e.printStackTrace();
	   }
		
		return null;
		
	}
}
