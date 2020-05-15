package org.dew.ebxml.query;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dew.ebxml.IElement;
import org.dew.ebxml.Utils;

import org.dew.ebxml.rs.RegistryError;

import org.dew.xds.XDS;

public 
class AdhocQueryRequest implements IElement, Serializable
{
  private static final long serialVersionUID = 3985556758931088168L;
  
  protected boolean federated;
  protected String  federation;
  protected int     maxResults = -1;
  protected int     startIndex =  0;
  
  protected ResponseOption responseOption;
  protected String         sqlQuery;
  protected AdhocQuery     adhocQuery;
  
  // extra
  protected String servicePath;
  protected List<RegistryError> registryErrorList;
  
  public AdhocQueryRequest()
  {
    responseOption = new ResponseOption();
    adhocQuery     = new AdhocQuery();
  }

  public AdhocQueryRequest(boolean returnComposedObjects, String returnType)
  {
    responseOption = new ResponseOption(returnComposedObjects, returnType);
    adhocQuery     = new AdhocQuery();
  }

  public AdhocQueryRequest(boolean returnComposedObjects, String returnType, String adhocQueryId)
  {
    responseOption = new ResponseOption(returnComposedObjects, returnType);
    adhocQuery     = new AdhocQuery();
    if(adhocQueryId != null && adhocQueryId.length() > 0 && adhocQueryId.startsWith("urn:uuid:")) {
      adhocQuery.setId(adhocQueryId);
    }
  }

  public AdhocQueryRequest(String patientId)
  {
    responseOption = new ResponseOption();
    adhocQuery     = new AdhocQuery(patientId);
  }

  public AdhocQueryRequest(String patientId, String adhocQueryId)
  {
    responseOption = new ResponseOption();
    adhocQuery     = new AdhocQuery(patientId);
    if(adhocQueryId != null && adhocQueryId.length() > 0 && adhocQueryId.startsWith("urn:uuid:")) {
      adhocQuery.setId(adhocQueryId);
    }
  }

  public boolean isFederated() {
    return federated;
  }

  public void setFederated(boolean federated) {
    this.federated = federated;
  }

  public String getFederation() {
    return federation;
  }

  public void setFederation(String federation) {
    this.federation = federation;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  public int getMaxResults() {
    return maxResults;
  }

  public void setMaxResults(int maxResults) {
    this.maxResults = maxResults;
  }

  public ResponseOption getResponseOption() {
    return responseOption;
  }

  public void setResponseOption(ResponseOption responseOption) {
    this.responseOption = responseOption;
  }

  public String getSqlQuery() {
    return sqlQuery;
  }

  public void setSqlQuery(String sqlQuery) {
    this.sqlQuery = sqlQuery;
  }

  public AdhocQuery getAdhocQuery() {
    if(adhocQuery == null) adhocQuery = new AdhocQuery();
    return adhocQuery;
  }

  public void setAdhocQuery(AdhocQuery adhocQuery) {
    this.adhocQuery = adhocQuery;
  }
  
  public String getServicePath() {
    return servicePath;
  }

  public void setServicePath(String servicePath) {
    this.servicePath = servicePath;
  }

  public List<RegistryError> getRegistryErrorList() {
    return registryErrorList;
  }

  public void setRegistryErrorList(List<RegistryError> registryErrorList) {
    this.registryErrorList = registryErrorList;
  }
  
  // Response Options
  
  public void setReturnComposedObjects(boolean returnComposedObjects) {
    if(this.responseOption == null) {
      this.responseOption = new ResponseOption(returnComposedObjects);
    }
    else {
      this.responseOption.setReturnComposedObjects(returnComposedObjects);
    }
  }

  public void setReturnType(String returnType) {
    if(this.responseOption == null) {
      this.responseOption = new ResponseOption(returnType);
    }
    else {
      this.responseOption.setReturnType(returnType);
    }
  }
  
  // Utility
  
  public AdhocQuery buildFindDocuments(String patientId) {
    if(responseOption == null) responseOption = new ResponseOption();
    responseOption.setReturnComposedObjects(false);
    responseOption.setReturnType(ResponseOption.TYPE_LEAF_CLASS);
    
    if(adhocQuery == null) adhocQuery = new AdhocQuery();
    adhocQuery.setId(XDS.SQ_FIND_DOCUMENTS);
    adhocQuery.setPatientId(patientId);
    return adhocQuery;
  }

  public AdhocQuery buildGetDocuments(String uniqueId) {
    if(responseOption == null) responseOption = new ResponseOption();
    responseOption.setReturnComposedObjects(true);
    responseOption.setReturnType(ResponseOption.TYPE_OBJECT_REF);
    
    if(adhocQuery == null) adhocQuery = new AdhocQuery();
    adhocQuery.setId(XDS.SQ_GET_DOCUMENTS);
    adhocQuery.setUniqueId(uniqueId);
    return adhocQuery;
  }

  public String getTagName() {
    return "AdhocQueryRequest";
  }
  
  public String getAttribute(String name) {
    if(name == null) return null;
    if(name.equals("federated")) {
      return String.valueOf(this.federated);
    }
    else if(name.equals("federation")) {
      return this.federation;
    }
    else if(name.equals("maxResults")) {
      return String.valueOf(this.maxResults);
    }
    else if(name.equals("startIndex")) {
      return String.valueOf(this.startIndex);
    }
    return null;
  }
  
  public void setAttribute(String name, String value) {
    if(name == null) return;
    if(name.equals("federated")) {
      if(value != null && value.length() > 0) {
        this.federated = "tysTYS1".indexOf(value.charAt(0)) >= 0;
      }
      else {
        this.federated = false;
      }
    }
    else if(name.equals("federation")) {
      this.federation = value;
    }
    else if(name.equals("maxResults")) {
      try {
        this.maxResults = Integer.parseInt(value);
      }
      catch(Exception ex) {
        this.maxResults = 0;
      }
    }
    else if(name.equals("startIndex")) {
      try {
        this.startIndex = Integer.parseInt(value);
      }
      catch(Exception ex) {
        this.startIndex = 0;
      }
    }
  }
  
  public String toXML(String namespace) {
    String sNs = null;
    String sDe = null;
    if(namespace == null) {
      sNs = "ns3:";
      sDe = "xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\"";
    }
    else if(namespace.length() == 0) {
      sNs = "";
    }
    else if(namespace.endsWith(":")) {
      sNs = namespace;
    }
    else {
      sNs = namespace + ":";
    }
    
    StringBuffer sb = new StringBuffer(500);
    sb.append("<" + sNs + getTagName());
    if(sDe != null && sDe.length() > 0) {
      sb.append(" " + sDe);
      sb.append(" xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"");
      sb.append(" xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"");
      sb.append(" xmlns:ns4=\"urn:ihe:iti:xds-b:2007\"");
      sb.append(" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\"");
    }
    if(federated) {
      sb.append(" federated=\"" + federated + "\"");
    }
    if(federation != null && federation.length() > 0) {
      sb.append(" federation=\"" + federation + "\"");
    }
    if(maxResults > 0) {
      sb.append(" maxResults=\"" + maxResults + "\"");
    }
    if(startIndex > 0) {
      sb.append(" startIndex=\"" + startIndex + "\"");
    }
    sb.append(">");
    if(responseOption != null) {
      sb.append(responseOption.toXML(sNs));
    }
    if(sqlQuery != null && sqlQuery.length() > 1) {
      sb.append("<" + sNs + "SQLQuery>");
      sb.append(Utils.normalizeString(sqlQuery));
      sb.append("</" + sNs + "SQLQuery>");
    }
    if(adhocQuery != null) {
      sb.append(adhocQuery.toXML(""));
    }
    sb.append("</" + sNs + getTagName() + ">");
    return sb.toString();   
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName",        getTagName());
    mapResult.put("federated",      federated);
    mapResult.put("maxResults",     maxResults);
    mapResult.put("startIndex",     startIndex);
    mapResult.put("responseOption", responseOption);
    mapResult.put("adhocQuery",     adhocQuery);
    mapResult.put("servicePath",    servicePath); // extra
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof AdhocQueryRequest) {
      AdhocQuery oAdhocQuery = ((AdhocQueryRequest) object).getAdhocQuery();
      if(oAdhocQuery == null && adhocQuery == null) return true;
      return oAdhocQuery != null && oAdhocQuery.equals(adhocQuery);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(adhocQuery == null) return 0;
    return adhocQuery.hashCode();
  }
  
  @Override
  public String toString() {
    return "AdhocQueryRequest(" + adhocQuery + ")";
  }
}
