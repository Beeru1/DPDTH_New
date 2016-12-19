package com.ibm.hbo.dto;

import java.io.Serializable;

/**
 * @author Avadesh Created On Tue Sep 25 16:53:33 IST 2007 Data Trasnfer Object
 *         class for table ARC_CIRCLE_MSTR
 * 
 */
public class HBOCircleDTO implements Serializable {

	private String circleName;

	private String circleId;

	

	/** Creates a dto for the ARC_CIRCLE_MSTR table */
	public HBOCircleDTO() {
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

		

	
}
