package org.dew.xds;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.dew.ebxml.Utils;
import org.dew.ebxml.rs.RegistryResponse;
import org.dew.xds.util.Base64Coder;
import org.dew.ebxml.IElement;

public 
class XDSDocumentResponse implements IElement, Serializable
{
  private static final long serialVersionUID = -3041891635540529904L;
  
  protected String homeCommunityId;
  protected String repositoryUniqueId;
  protected String documentUniqueId;
  protected String mimeType;
  protected byte[] document;
  protected String xopIncludeHref = "cid:" + UUID.randomUUID().toString() + "@urn%3Aihe%3Aiti%3Axds-b%3A2007";
  // Error messages
  protected RegistryResponse registryResponse;
  // TRANSMISSION_USING_MTOM_XOP
  protected boolean enclosedDocument;
  
  public XDSDocumentResponse()
  {
  }
  
  public XDSDocumentResponse(String documentUniqueId)
  {
    this.documentUniqueId = documentUniqueId;
  }
  
  public XDSDocumentResponse(String repositoryUniqueId, String documentUniqueId)
  {
    this.repositoryUniqueId = repositoryUniqueId;
    this.documentUniqueId = documentUniqueId;
  }
  
  public XDSDocumentResponse(String homeCommunityId, String repositoryUniqueId, String documentUniqueId)
  {
    this.homeCommunityId = homeCommunityId;
    this.repositoryUniqueId = repositoryUniqueId;
    this.documentUniqueId = documentUniqueId;
  }
  
  public XDSDocumentResponse(XDSDocument xdsDocument)
  {
    if(xdsDocument == null) return;
    this.homeCommunityId = xdsDocument.getHomeCommunityId();
    this.repositoryUniqueId = xdsDocument.getRepositoryUniqueId();
    this.documentUniqueId = xdsDocument.getUniqueId();
    this.mimeType = xdsDocument.getMimeType();
    this.document = xdsDocument.getContent();
    
    String sRegistryObjectId = xdsDocument.getRegistryObjectId();
    if(sRegistryObjectId == null || sRegistryObjectId.length() == 0) {
      sRegistryObjectId = UUID.randomUUID().toString();
    }
    xopIncludeHref = "cid:" + sRegistryObjectId + "@urn%3Aihe%3Aiti%3Axds-b%3A2007";
  }
  
  public XDSDocumentResponse(RegistryResponse registryResponse)
  {
    this.registryResponse = registryResponse;
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

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public byte[] getDocument() {
    return document;
  }

  public void setDocument(byte[] document) {
    this.document = document;
  }

  public String getXopIncludeHref() {
    return xopIncludeHref;
  }

  public void setXopIncludeHref(String xopIncludeHref) {
    this.xopIncludeHref = xopIncludeHref;
  }

  public RegistryResponse getRegistryResponse() {
    return registryResponse;
  }

  public void setRegistryResponse(RegistryResponse registryResponse) {
    this.registryResponse = registryResponse;
  }

  public boolean isEnclosedDocument() {
    return enclosedDocument;
  }

  public void setEnclosedDocument(boolean enclosedDocument) {
    this.enclosedDocument = enclosedDocument;
  }

  public String getTagName() {
    return "DocumentResponse";
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
    else if(name.equalsIgnoreCase("mimeType")) {
      return this.mimeType;
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
    else if(name.equalsIgnoreCase("mimeType")) {
      this.mimeType = value;
    }
  }
  
  public String toXML(String namespace) {
    if(namespace == null || namespace.length() == 0) {
      namespace = "";
    }
    else 
    if(!namespace.endsWith(":")) {
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
    if(mimeType != null && mimeType.length() > 0) {
      sb.append("<" + namespace + "mimeType>" + Utils.normalizeString(mimeType) + "</" + namespace + "mimeType>");
    }
    if(enclosedDocument && document != null && document.length > 0) {
      sb.append("<" + namespace + "Document>");
      sb.append(new String(Base64Coder.encode(document)));
      sb.append("</" + namespace + "Document>");
    }
    if(!enclosedDocument && xopIncludeHref != null && xopIncludeHref.length() > 0) {
      sb.append("<" + namespace + "Document>");
      sb.append("<xop:Include xmlns:xop=\"http://www.w3.org/2004/08/xop/include\" href=\"" + xopIncludeHref + "\"/>");
      sb.append("</" + namespace + "Document>");
    }
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName",              getTagName());
    mapResult.put("homeCommunityId",      homeCommunityId);
    mapResult.put("repositoryUniqueId",   repositoryUniqueId);
    mapResult.put("documentUniqueId",     documentUniqueId);
    mapResult.put("mimeType",             mimeType);
    if(registryResponse != null) {
      mapResult.put("registryResponse", registryResponse.toMap());
    }
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof XDSDocumentResponse) {
      String sHomeCommunityId    = ((XDSDocumentResponse) object).getHomeCommunityId();
      String sRepositoryUniqueId = ((XDSDocumentResponse) object).getRepositoryUniqueId();
      String sDocumentUniqueId   = ((XDSDocumentResponse) object).getDocumentUniqueId();
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
    return "XDSDocumentResponse(" + state + ")";
  }
}
