package org.dew.auth;

import java.io.Serializable;

public 
class SAMLAttributeQueryResponse implements Serializable
{
	private static final long serialVersionUID = -5834281749353258460L;
	
	protected String id;
	protected String statusCode;
	protected String statusMessage;
	protected String assertion;
	
	public SAMLAttributeQueryResponse()
	{
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
	
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public String getAssertion() {
		return assertion;
	}
	
	public void setAssertion(String assertion) {
		this.assertion = assertion;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof SAMLAttributeQueryResponse) {
			String sId = ((SAMLAttributeQueryResponse) object).getId();
			if(sId == null && id == null) return true;
			return sId != null && sId.equals(id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if(id == null) return 0;
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		return "SAMLAttributeQueryResponse(" + id + ")";
	}
}
