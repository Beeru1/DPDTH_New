package com.ibm.hbo.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Avadesh Created On Tue Sep 25 16:53:33 IST 2007 Form Bean class for
 *         table ARC_CIRCLE_MSTR
 * 
 */
public class HBOCircleMstrFormBean extends ActionForm {

	private String circleName;

	private String circleId;

	private java.sql.Timestamp createdDt;

	private String createdBy;

	private String status;

	/** Creates a dto for the ARC_CIRCLE_MSTR table */
	public HBOCircleMstrFormBean() {
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		// TODO Replace with Actual code.This method is called if validate for
		// this action mapping is set to true. and iff errors is not null and
		// not emoty it forwards to the page defined in input attritbute of the
		// ActionMapping
		return errors;
	}

	/**
	 * Get circleName associated with this object.
	 * 
	 * @return circleName
	 */

	public String getCircleName() {
		return circleName;
	}

	/**
	 * Set circleName associated with this object.
	 * 
	 * @param circleName
	 *            The circleName value to set
	 */

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * Get circleId associated with this object.
	 * 
	 * @return circleId
	 */

	public String getCircleId() {
		return circleId;
	}

	/**
	 * Set circleId associated with this object.
	 * 
	 * @param circleId
	 *            The circleId value to set
	 */

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/**
	 * Get createdDt associated with this object.
	 * 
	 * @return createdDt
	 */

	public java.sql.Timestamp getCreatedDt() {
		return createdDt;
	}

	/**
	 * Set createdDt associated with this object.
	 * 
	 * @param createdDt
	 *            The createdDt value to set
	 */

	public void setCreatedDt(java.sql.Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	/**
	 * Get createdBy associated with this object.
	 * 
	 * @return createdBy
	 */

	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set createdBy associated with this object.
	 * 
	 * @param createdBy
	 *            The createdBy value to set
	 */

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get status associated with this object.
	 * 
	 * @return status
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * Set status associated with this object.
	 * 
	 * @param status
	 *            The status value to set
	 */

	public void setStatus(String status) {
		this.status = status;
	}

}
