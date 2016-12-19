package com.ibm.virtualization.recharge.common;

/**
 * Type of receiver's name who will receive the message
 * 
 * @author ashish
 * 
 */
public enum RecieverType {

	SUBSCRIBER, DEALER, DISTRIBUTOR, REQUESTER;

	public String value() {
		switch (this) {
		case SUBSCRIBER:
			return "subscriber";
		case DEALER:
			return "dealer";
		case DISTRIBUTOR:
			return "distributor";
		default:
			return "requester";
		}
	}
}
