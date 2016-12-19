package com.ibm.dp.service.impl;

import com.jcraft.jsch.UserInfo;

public class MyUserInfo implements UserInfo {

	
	public String passwd;
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	
	public String getPassphrase() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getPassword() {
		// TODO Auto-generated method stub
		return passwd;
	}

	
	public boolean promptPassphrase(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	
	public boolean promptPassword(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	
	public boolean promptYesNo(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	
	public void showMessage(String arg0) {
		// TODO Auto-generated method stub

	}

}
