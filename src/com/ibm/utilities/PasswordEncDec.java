package com.ibm.utilities;

import java.util.ResourceBundle;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class PasswordEncDec 
{
	
	public static void main(String[] args) throws Exception 
	{
		String s = "air@2015";
		//String s1 = new String("Dsl/HU5GuD7a/YUOscGdvw==").intern();
		///System.out.println(s.equals(s1));
		//System.out.println(s==s1);
		
		//String result = "Distributor Details Saved Successfully For :DN4680024";
		//System.out.println("Output answer : " + result.substring(result.indexOf("For :")+5, result.length()));
				
		
		//String password ="ouMmTH5R+WjOMijKdwfnug==";// new password//dp@123dp
		String password=encPassword(s);
        System.out.println(" Encrypted password : "+password);
        //String password= decPassword(s);
        //System.out.println(" Decrypted password : "+password);
        
      /*  IEncryption encrypt = new Encryption();
		// for I-password
		String encryptedPassword = encrypt.generateDigest("");
		String decryptedPassword = encrypt.decrypt("ouMmTH5R+WjOMijKdwfnug==");
		System.out.println("decryptedPassword  ::  "+decryptedPassword);*/
		
		
        
	}
	
	public static String encPassword(String password) 
	{
		String encPassword="";
		try
		{
			
			IEncryption crypt = new Encryption();
			encPassword = crypt.encrypt(password);
			//System.out.println("Encrypted String - "+crypt.encrypt(password));
		}
		catch (EncryptionException e)
		{
			e.printStackTrace();
		}
		return encPassword;
	}	
	
	
	public static String decPassword(String password) 
	{
		String decPassword="";		
		try 
		{
		IEncryption crypt = new Encryption();
		decPassword= crypt.decrypt(password);
		}
		catch (EncryptionException e)
		{
			e.printStackTrace();
		}
		return decPassword;
	}

   public static String getWebservicesPassword()throws VirtualizationServiceException 
   {
	ResourceBundle webserviceResourceBundle = null;
	String value = "";	
		try {
			if (webserviceResourceBundle == null) {
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}
			value = webserviceResourceBundle.getString("webservices.user.password");
			value=decPassword(value);	
	} catch (Exception e) {		
	e.printStackTrace();
	}
	return value;
	}

}
	


