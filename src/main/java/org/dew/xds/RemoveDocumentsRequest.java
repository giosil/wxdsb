package org.dew.xds;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dew.ebxml.IElement;

public 
class RemoveDocumentsRequest implements IElement, Serializable
{
  private static final long serialVersionUID = -6694604139995344703L;
  
  List<XDSDocumentRequest> listOfXDSDocumentRequest = new ArrayList<>();
  
  public RemoveDocumentsRequest()
  {
  }
  
  public RemoveDocumentsRequest(List<XDSDocumentRequest> listOfXDSDocumentRequest)
  {
    this.listOfXDSDocumentRequest = listOfXDSDocumentRequest;
    if(this.listOfXDSDocumentRequest == null) {
      this.listOfXDSDocumentRequest = new ArrayList<XDSDocumentRequest>();
    }
  }
  
  public RemoveDocumentsRequest(XDSDocumentRequest xdsDocumentRequest)
  {
    if(xdsDocumentRequest != null) {
      this.listOfXDSDocumentRequest.add(xdsDocumentRequest);
    }
  }
  
  public RemoveDocumentsRequest(String documentUniqueId)
  {
    this.listOfXDSDocumentRequest.add(new XDSDocumentRequest(documentUniqueId));
  }
  
  public RemoveDocumentsRequest(String repositoryUniqueId, String documentUniqueId)
  {
    this.listOfXDSDocumentRequest.add(new XDSDocumentRequest(repositoryUniqueId, documentUniqueId));
  }
  
  public RemoveDocumentsRequest(String homeCommunityId, String repositoryUniqueId, String documentUniqueId)
  {
    this.listOfXDSDocumentRequest.add(new XDSDocumentRequest(homeCommunityId, repositoryUniqueId, documentUniqueId));
  }
  
  public List<XDSDocumentRequest> getListOfXDSDocumentRequest() {
    return listOfXDSDocumentRequest;
  }
  
  public void setListOfXDSDocumentRequest(List<XDSDocumentRequest> listOfXDSDocumentRequest) {
    this.listOfXDSDocumentRequest = listOfXDSDocumentRequest;
  }
  
  public void add(XDSDocumentRequest xdsDocumentRequest) {
    if(xdsDocumentRequest == null) return;
    this.listOfXDSDocumentRequest.add(xdsDocumentRequest);
  }
  
  public void add(String documentUniqueId) {
    if(documentUniqueId == null || documentUniqueId.length() == 0) return;
    this.listOfXDSDocumentRequest.add(new XDSDocumentRequest(documentUniqueId));
  }
  
  public void add(String repositoryUniqueId, String documentUniqueId) {
    if(documentUniqueId == null || documentUniqueId.length() == 0) return;
    this.listOfXDSDocumentRequest.add(new XDSDocumentRequest(repositoryUniqueId, documentUniqueId));
  }
  
  public void add(String homeCommunityId, String repositoryUniqueId, String documentUniqueId) {
    if(documentUniqueId == null || documentUniqueId.length() == 0) return;
    this.listOfXDSDocumentRequest.add(new XDSDocumentRequest(homeCommunityId, repositoryUniqueId, documentUniqueId));
  }
  
  public XDSDocumentRequest remove(int index) {
    if(this.listOfXDSDocumentRequest.size() <= index) return null;
    return this.listOfXDSDocumentRequest.remove(index);
  }
  
  public boolean remove(XDSDocumentRequest xdsDocumentRequest) {
    if(xdsDocumentRequest == null) return false;
    return this.listOfXDSDocumentRequest.remove(xdsDocumentRequest);
  }
  
  public boolean remove(String documentUniqueId) {
    if(documentUniqueId == null || documentUniqueId.length() == 0) return false;
    return this.listOfXDSDocumentRequest.remove(new XDSDocumentRequest(documentUniqueId));
  }
  
  public boolean remove(String repositoryUniqueId, String documentUniqueId) {
    if(documentUniqueId == null || documentUniqueId.length() == 0) return false;
    return this.listOfXDSDocumentRequest.remove(new XDSDocumentRequest(repositoryUniqueId, documentUniqueId));
  }
  
  public boolean remove(String homeCommunityId, String repositoryUniqueId, String documentUniqueId) {
    if(documentUniqueId == null || documentUniqueId.length() == 0) return false;
    return this.listOfXDSDocumentRequest.remove(new XDSDocumentRequest(homeCommunityId, repositoryUniqueId, documentUniqueId));
  }
  
  public XDSDocumentRequest getXDSDocumentRequest(int index) {
    if(this.listOfXDSDocumentRequest.size() <= index) return null;
    return this.listOfXDSDocumentRequest.get(index);
  }
  
  public int size() {
    return listOfXDSDocumentRequest.size();
  }
  
  public String getTagName() {
    return "RemoveDocumentsRequest";
  }
  
  public String getAttribute(String name) {
    return null;
  }
  
  public void setAttribute(String name, String value) {
  }
  
  public String toXML(String namespace) {
    String namespaceXDS = "xds";
    if(namespace != null) {
      int sep = namespace.indexOf('^');
      if(sep >= 0) {
        namespaceXDS = namespace.substring(sep+1);
        namespace    = namespace.substring(0,sep);
      }
    }
    if(namespace == null || namespace.length() == 0) {
      namespace = "";
    }
    else if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    
    StringBuffer sb = new StringBuffer(500);
    sb.append("<" + namespace + getTagName());
    sb.append(">");
    for(int i = 0; i < listOfXDSDocumentRequest.size(); i++) {
      XDSDocumentRequest xdsDocumentRequest = listOfXDSDocumentRequest.get(i);
      sb.append(xdsDocumentRequest.toXML(namespaceXDS));
    }
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName",             getTagName());
    mapResult.put("documentRequestList", listOfXDSDocumentRequest);
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof RemoveDocumentsRequest) {
      return object.hashCode() == this.hashCode();
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return listOfXDSDocumentRequest.toString().hashCode();
  }
  
  @Override
  public String toString() {
    return "RemoveDocumentsRequest(" + listOfXDSDocumentRequest + ")";
  }
}
