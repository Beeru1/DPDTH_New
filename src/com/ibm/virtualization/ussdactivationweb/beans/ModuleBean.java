/*
 * Created on Jul 7, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.ussdactivationweb.beans;



import java.util.ArrayList;

import org.apache.struts.action.ActionForm;


/**
 * @author Ashad
 *
 */
public class ModuleBean extends ActionForm 
{
   private String moduleName="";
   private String moduleUrl="";
   private String section="";
   private ArrayList moduleList=null;
   
	
/**
 * @return Returns the moduleList.
 */
public ArrayList getModuleList() {
	return moduleList;
}
/**
 * @param moduleList The moduleList to set.
 */
public void setModuleList(ArrayList moduleList) {
	this.moduleList = moduleList;
}
/**
 * @return Returns the moduleUrl.
 */
public String getModuleUrl() {
	return moduleUrl;
}
/**
 * @param moduleUrl The moduleUrl to set.
 */
public void setModuleUrl(String moduleUrl) {
	this.moduleUrl = moduleUrl;
}
/**
 * @return Returns the section.
 */
public String getSection() {
	return section;
}
/**
 * @param section The section to set.
 */
public void setSection(String section) {
	this.section = section;
}

/**
 * @return Returns the moduleName.
 */
public String getModuleName() {
	return moduleName;
}
/**
 * @param moduleName The moduleName to set.
 */
public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}
}	