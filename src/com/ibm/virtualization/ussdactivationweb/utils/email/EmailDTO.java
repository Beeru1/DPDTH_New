/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.utils.email;

/**
 * @author abhipsa
 *
 */
public class EmailDTO {

		protected String to;
		protected String from;
		protected String message;
		protected String subject;
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public String toString() {
			return new StringBuffer (500)
				.append("EmailDTO - to: ").append(to)
				.append(", from: ").append(from)
				.append(", message: ").append(message)
				.append(", subject: ").append(subject)
				.toString();
			
		}
}
