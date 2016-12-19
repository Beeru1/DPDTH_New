package com.ibm.dp.webServices.local;


public interface AuthenticateUserId_SEI extends java.rmi.Remote
{
  public java.lang.String[] authenticate(java.lang.String dpUser,java.lang.String dpPass,java.lang.String wsUser,java.lang.String wsPass);
}