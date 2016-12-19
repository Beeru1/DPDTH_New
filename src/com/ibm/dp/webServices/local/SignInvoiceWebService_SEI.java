package com.ibm.dp.webServices.local;


public interface SignInvoiceWebService_SEI extends java.rmi.Remote
{
  public java.lang.String[] updateStatus(java.lang.String invoice_No,java.lang.String dist_olmId,java.lang.String userid,java.lang.String password);
}