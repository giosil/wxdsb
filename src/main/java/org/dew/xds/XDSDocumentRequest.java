package org.dew.xds;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.dew.ebxml.Utils;
import org.dew.ebxml.IElement;

public 
class XDSDocumentRequest implements IElement, Serializable
{
  private static final long serialVersionUID = 2824961899874606273L;
  
  protected String homeCommunityId;
  protected String repositoryUniqueId;
  protected String documentUniqueId;
  // TRANSMISSION_USING_MTOM_XOP
  protected boolean enclosedDocument;
  
  public XDSDocumentRequest()
  {
  }

  public XDSDocumentRequest(String documentUniqueId)
  {
    this.documentUniqueId = documentUniqueId;
  }

  public XDSDocumentRequest(String repositoryUniqueId, String documentUniqueId)
  {
    this.repositoryUniqueId = repositoryUniqueId;
    this.documentUniqueId   = documentUniqueId;
  }

  public XDSDocumentRequest(String homeCommunityId, String repositoryUniqueId, String documentUniqueId)
  {
    this.homeCommunityId    = homeCommunityId;
    this.repositoryUniqueId = repositoryUniqueId;
    this.documentUniqueId   = documentUniqueId;
  }

  public XDSDocumentRequest(XDSDocument xdsDocument)
  {
    if(xdsDocument == null) return;
    this.homeCommunityId    = xdsDocument.getHomeCommunityId();
    this.repositoryUniqueId = xdsDocument.getRepositoryUniqueId();
    this.documentUniqueId   = xdsDocument.getUniqueId();
  }

  public String getHomeCommunityId() {
    return homeCommunityId;
  }

  public void setHomeCommunityId(String homeCommunityId) {
    this.homeCommunityId = homeCommunityId;
  }

  public String getRepositoryUniqueId() {
    return repositoryUniqueId;
  }

  public void setRepositoryUniqueId(String repositoryUniqueId) {
    this.repositoryUniqueId = repositoryUniqueId;
  }

  public String getDocumentUniqueId() {
    return documentUniqueId;
  }

  public void setDocumentUniqueId(String documentUniqueId) {
    this.documentUniqueId = documentUniqueId;
  }
  
  public boolean isEnclosedDocument() {
    return enclosedDocument;
  }

  public void setEnclosedDocument(boolean enclosedDocument) {
    this.enclosedDocument = enclosedDocument;
  }

  public String getTagName() {
    return "DocumentRequest";
  }
  
  public String getAttribute(String name) {
    if(name == null) return null;
    if(name.equalsIgnoreCase("homeCommunityId")) {
      return this.homeCommunityId;
    }
    else if(name.equalsIgnoreCase("repositoryUniqueId")) {
      return this.repositoryUniqueId;
    }
    else if(name.equalsIgnoreCase("documentUniqueId")) {
      return this.documentUniqueId;
    }
    else if(name.equalsIgnoreCase("enclosedDocument")) {
      return String.valueOf(enclosedDocument);
    }
    return null;
  }
  
  public void setAttribute(String name, String value) {
    if(name == null) return;
    if(name.equalsIgnoreCase("homeCommunityId")) {
      this.homeCommunityId = value;
    }
    else if(name.equalsIgnoreCase("repositoryUniqueId")) {
      this.repositoryUniqueId = value;
    }
    else if(name.equalsIgnoreCase("documentUniqueId")) {
      this.documentUniqueId = value;
    }
    else if(name.equalsIgnoreCase("enclosedDocument")) {
      this.enclosedDocument = value != null && value.equalsIgnoreCase("true");
    }
  }
  
  public String toXML(String namespace) {
    if(namespace == null || namespace.length() == 0) {
      namespace = "";
    }
    else if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    StringBuffer sb = new StringBuffer(500);
    sb.append("<" + namespace + getTagName());
    sb.append(">");
    if(homeCommunityId != null && homeCommunityId.length() > 0) {
      sb.append("<" + namespace + "HomeCommunityId>" + Utils.normalizeString(homeCommunityId) + "</" + namespace + "HomeCommunityId>");
    }
    if(repositoryUniqueId != null && repositoryUniqueId.length() > 0) {
      sb.append("<" + namespace + "RepositoryUniqueId>" + Utils.normalizeString(repositoryUniqueId) + "</" + namespace + "RepositoryUniqueId>");
    }
    if(documentUniqueId != null && documentUniqueId.length() > 0) {
      sb.append("<" + namespace + "DocumentUniqueId>" + Utils.normalizeString(documentUniqueId) + "</" + namespace + "DocumentUniqueId>");
    }
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName",            getTagName());
    mapResult.put("homeCommunityId",    homeCommunityId);
    mapResult.put("repositoryUniqueId", repositoryUniqueId);
    mapResult.put("documentUniqueId",   documentUniqueId);
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof XDSDocumentRequest) {
      String sHomeCommunityId    = ((XDSDocumentRequest) object).getHomeCommunityId();
      String sRepositoryUniqueId = ((XDSDocumentRequest) object).getRepositoryUniqueId();
      String sDocumentUniqueId   = ((XDSDocumentRequest) object).getDocumentUniqueId();
      String state  = homeCommunityId  + "^" + repositoryUniqueId  + "^" + documentUniqueId;
      String sState = sHomeCommunityId + "^" + sRepositoryUniqueId + "^" + sDocumentUniqueId;
      return state.equals(sState);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    String state = homeCommunityId + "^" + repositoryUniqueId + "^" + documentUniqueId;
    return state.hashCode();
  }
  
  @Override
  public String toString() {
    String state = homeCommunityId + "^" + repositoryUniqueId + "^" + documentUniqueId;
    return "XDSDocumentRequest(" + state + ")";
  }
}
