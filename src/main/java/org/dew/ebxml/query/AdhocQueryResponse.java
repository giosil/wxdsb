package org.dew.ebxml.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dew.ebxml.IElement;
import org.dew.ebxml.RegistryObjectList;
import org.dew.ebxml.rs.RegistryError;
import org.dew.xds.XDS;
import org.dew.xds.XDSDocument;

public 
class AdhocQueryResponse implements IElement, Serializable 
{
	private static final long serialVersionUID = -7672298596222229487L;
	
	protected String status;
	protected int startIndex;
	protected int totalResultCount;
	protected List<RegistryError> registryErrorList;
	protected RegistryObjectList registryObjectList;
	
	public AdhocQueryResponse()
	{
		this.status = XDS.REG_RESP_STATUS_SUCCESS;
		this.registryErrorList  = new ArrayList<RegistryError>();
		this.registryObjectList = new RegistryObjectList();
	}
	
	public AdhocQueryResponse(List<RegistryError> registryErrorList)
	{
		this.registryErrorList  = registryErrorList;
		this.registryObjectList = new RegistryObjectList();
		if(registryErrorList != null && registryErrorList.size() > 0) {
			this.status = XDS.REG_RESP_STATUS_FAILURE;
		}
		else {
			this.status = XDS.REG_RESP_STATUS_SUCCESS;
		}
	}
	
	public AdhocQueryResponse(List<RegistryError> registryErrorList, RegistryObjectList registryObjectList)
	{
		this.registryErrorList  = registryErrorList;
		this.registryObjectList = registryObjectList;
		if(registryObjectList != null) {
			this.totalResultCount = registryObjectList.getTotalResultCount();
		}
		if(registryErrorList != null && registryErrorList.size() > 0) {
			this.status = XDS.REG_RESP_STATUS_FAILURE;
		}
		else {
			this.status = XDS.REG_RESP_STATUS_SUCCESS;
		}
	}
	
	public AdhocQueryResponse(List<RegistryError> registryErrorList, RegistryObjectList registryObjectList, int startIndex)
	{
		this(registryErrorList, registryObjectList);
		this.startIndex = startIndex;
	}
	
	public AdhocQueryResponse(List<RegistryError> registryErrorList, RegistryObjectList registryObjectList, AdhocQueryRequest request)
	{
		this(registryErrorList, registryObjectList);
		this.startIndex = request != null ? request.getStartIndex() : 0;
	}
	
	public AdhocQueryResponse(String errorMessage)
	{
		this.registryErrorList  = new ArrayList<RegistryError>();
		if(errorMessage != null && errorMessage.length() > 0) {
			this.status = XDS.REG_RESP_STATUS_FAILURE;
			registryErrorList.add(new RegistryError(errorMessage));
		}
		else {
			this.status = XDS.REG_RESP_STATUS_SUCCESS;
		}
		this.registryObjectList = new RegistryObjectList();
	}
	
	public AdhocQueryResponse(XDSDocument xdsDocument)
	{
		this.status = XDS.REG_RESP_STATUS_SUCCESS;
		if(xdsDocument == null) {
			this.registryErrorList  = new ArrayList<RegistryError>();
			this.registryObjectList = new RegistryObjectList();
		}
		else {
			this.totalResultCount   = 1;
			this.registryErrorList  = new ArrayList<RegistryError>();
			this.registryObjectList = new RegistryObjectList();
			this.registryObjectList.addRegistryObject(xdsDocument.getRegistryObject());
		}
	}
	
	public AdhocQueryResponse(XDSDocument[] arrayOfXDSDocument)
	{
		if(arrayOfXDSDocument == null || arrayOfXDSDocument.length == 0) {
			this.status             = XDS.REG_RESP_STATUS_FAILURE;
			this.registryErrorList  = new ArrayList<RegistryError>();
			this.registryErrorList.add(new RegistryError("No results from the query", "QND1", XDS.ERR_SEVERITY_WARNING));
			this.registryObjectList = new RegistryObjectList();
		}
		else {
			this.status             = XDS.REG_RESP_STATUS_SUCCESS;
			this.totalResultCount   = arrayOfXDSDocument.length;
			this.registryErrorList  = new ArrayList<RegistryError>();
			this.registryObjectList = new RegistryObjectList();
			for(int i = 0; i < arrayOfXDSDocument.length; i++) {
				XDSDocument xdsDocument = arrayOfXDSDocument[i];
				this.registryObjectList.addRegistryObject(xdsDocument.getRegistryObject());
			}
		}
	}
	
	public AdhocQueryResponse(XDSDocument[] arrayOfXDSDocument, int startIndex)
	{
		this(arrayOfXDSDocument);
		this.startIndex = startIndex;
	}
	
	public AdhocQueryResponse(XDSDocument[] arrayOfXDSDocument, AdhocQueryRequest request)
	{
		this(arrayOfXDSDocument);
		this.startIndex = request != null ? request.getStartIndex() : 0;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public int getTotalResultCount() {
		return totalResultCount;
	}
	
	public void setTotalResultCount(int totalResultCount) {
		this.totalResultCount = totalResultCount;
	}
	
	public List<RegistryError> getRegistryErrorList() {
		return registryErrorList;
	}
	
	public void setRegistryErrorList(List<RegistryError> registryErrorList) {
		this.registryErrorList = registryErrorList;
		if(registryErrorList != null && registryErrorList.size() > 0) {
			this.status = XDS.REG_RESP_STATUS_FAILURE;
		}
		else {
			this.status = XDS.REG_RESP_STATUS_SUCCESS;
		}
	}
	
	public RegistryObjectList getRegistryObjectList() {
		return registryObjectList;
	}
	
	public void setRegistryObjectList(RegistryObjectList registryObjectList) {
		this.registryObjectList = registryObjectList;
	}
	
	public String getTagName() {
		return "AdhocQueryResponse";
	}
	
	public String getAttribute(String name) {
		if(name == null) return null;
		if(name.equals("status")) {
			return status;
		}
		else
		if(name.equals("startIndex")) {
			return String.valueOf(this.startIndex);
		}
		else
		if(name.equals("totalResultCount")) {
			return String.valueOf(this.totalResultCount);
		}
		return null;
	}
	
	public void setAttribute(String name, String value) {
		if(name == null) return;
		if(name.equals("status")) {
			this.status = value;
		}
		else
		if(name.equals("startIndex")) {
			try {
				this.startIndex = Integer.parseInt(value);
			}
			catch(Exception ex) {
				this.startIndex = 0;
			}
		}
		else
		if(name.equals("totalResultCount")) {
			try {
				this.totalResultCount = Integer.parseInt(value);
			}
			catch(Exception ex) {
				this.totalResultCount = 0;
			}
		}
	}
	
	public String toXML(String namespace) {
		StringBuffer sb = new StringBuffer();
		if(registryErrorList != null && registryErrorList.size() > 0) {
			if(status == null || status.length() == 0) {
				status = XDS.REG_RESP_STATUS_FAILURE;
			}
			sb.append("<ns6:AdhocQueryResponse xmlns:ns6=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\" xmlns:ns4=\"urn:ihe:iti:xds-b:2007\" xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" status=\"" + status + "\">");
			sb.append("<ns3:RegistryErrorList>");
			for(int i = 0; i < registryErrorList.size(); i++) {
				sb.append(registryErrorList.get(i).toXML("ns3"));
			}
			sb.append("</ns3:RegistryErrorList>");
		}
		else {
			if(status == null || status.length() == 0) {
				status = XDS.REG_RESP_STATUS_SUCCESS;
			}
			if(startIndex < 0) startIndex = 0;
			if(totalResultCount < 1) {
				totalResultCount = registryObjectList != null ? registryObjectList.getTotalResultCount() : 0;
			}
			sb.append("<ns6:AdhocQueryResponse xmlns:ns6=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\" xmlns:ns4=\"urn:ihe:iti:xds-b:2007\" xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" status=\"" + status + "\" startIndex=\"" + startIndex + "\" totalResultCount=\"" + totalResultCount + "\">");
		}
		if(registryObjectList != null) {
			sb.append(registryObjectList.toXML("ns2"));
		}
		else {
			sb.append("<ns2:RegistryObjectList>");
			sb.append("</ns2:RegistryObjectList>");
		}
		sb.append("</ns6:AdhocQueryResponse>");
		return sb.toString();
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		mapResult.put("tagName",            getTagName());
		mapResult.put("status",             status);
		mapResult.put("startIndex",         startIndex);
		mapResult.put("totalResultCount",   totalResultCount);
		mapResult.put("registryErrorList",  registryErrorList);
		mapResult.put("registryObjectList", registryObjectList);
		return mapResult;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof AdhocQueryResponse) {
			RegistryObjectList oRegistryObjectList = ((AdhocQueryResponse) object).getRegistryObjectList();
			if(oRegistryObjectList == null && registryObjectList == null) return true;
			return oRegistryObjectList != null && oRegistryObjectList.equals(registryObjectList);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if(registryErrorList != null && registryErrorList.size() > 0) {
			return registryErrorList.hashCode();
		}
		if(registryObjectList != null) {
			return registryObjectList.hashCode();
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "AdhocQueryResponse(" + registryErrorList + "," + registryObjectList + ")";
	}
}
