package org.dew.ebxml.rs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dew.ebxml.IElement;
import org.dew.ebxml.Utils;
import org.dew.xds.XDS;

public 
class RegistryResponse implements IElement, Serializable
{
  private static final long serialVersionUID = -146602987412589521L;
  
  protected String status;
  protected List<RegistryError> registryErrorList = new ArrayList<RegistryError>();
  
  public RegistryResponse()
  {
  }

  public RegistryResponse(boolean success)
  {
    if(success) {
      this.status = XDS.REG_RESP_STATUS_SUCCESS;
    }
    else {
      this.status = XDS.REG_RESP_STATUS_FAILURE;
    }
  }

  public RegistryResponse(boolean success, String... errorMessages)
  {
    if(success) {
      this.status = XDS.REG_RESP_STATUS_SUCCESS;
    }
    else {
      this.status = XDS.REG_RESP_STATUS_FAILURE;
    }
    if(errorMessages == null) return;
    for(int i = 0; i < errorMessages.length; i++) {
      addRegistryError(new RegistryError(errorMessages[i]));
    }
  }

  public RegistryResponse(String... errorMessages)
  {
    if(errorMessages == null || errorMessages.length == 0) {
      this.status = XDS.REG_RESP_STATUS_SUCCESS;
    }
    else {
      this.status = XDS.REG_RESP_STATUS_FAILURE;
      for(int i = 0; i < errorMessages.length; i++) {
        addRegistryError(new RegistryError(errorMessages[i]));
      }
    }
  }

  public RegistryResponse(Exception ex)
  {
    if(ex == null) {
      this.status = XDS.REG_RESP_STATUS_SUCCESS;
    }
    else {
      this.status = XDS.REG_RESP_STATUS_FAILURE;
      String errorMessage = ex.getMessage();
      if(errorMessage == null || errorMessage.length() == 0) {
        errorMessage = ex.toString();
      }
      addRegistryError(new RegistryError(errorMessage));
    }
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<RegistryError> getRegistryErrorList() {
    return registryErrorList;
  }

  public void setRegistryErrorList(List<RegistryError> registryErrorList) {
    this.registryErrorList = registryErrorList;
  }
  
  public void addRegistryError(RegistryError registryError) {
    if(registryError == null) return;
    if(registryErrorList == null) {
      registryErrorList = new ArrayList<RegistryError>();
    }
    registryErrorList.add(registryError);
  }

  public void removeRegistryError(RegistryError registryError) {
    if(registryError  == null) return;
    if(registryErrorList == null) return;
    registryErrorList.remove(registryError);
  }
  
  public void clearRegistryErrorList() {
    if(registryErrorList == null) {
      registryErrorList = new ArrayList<RegistryError>();
    }
    registryErrorList.clear();
  }
  
  public boolean isSuccess() {
    if(status != null) {
      return status.endsWith("Success") || status.endsWith("success");
    }
    return false;
  }
  
  public boolean isFailure() {
    if(status != null) {
      return status.endsWith("Failure") || status.endsWith("failure");
    }
    return false;
  }
  
  public String getFirstErrorMessage() {
    if(registryErrorList == null || registryErrorList.size() == 0) return "";
    RegistryError registryError0 = registryErrorList.get(0);
    String result = registryError0.getCodeContext();
    if(result == null) return "";
    return result;
  }
  
  public String getTagName() {
    return "RegistryResponse";
  }
  
  public String getAttribute(String name) {
    if(name == null) return null;
    if(name.equals("status")) {
      return this.status;
    }
    return null;
  }
  
  public void setAttribute(String name, String value) {
    if(name == null) return;
    if(name.equals("status")) {
      this.status = value;
    }
  }
  
  public String toXML(String namespace) {
    String sNs = null;
    String sDe = null;
    if(namespace == null) {
      sNs = "rs:";
      sDe = "xmlns:rs=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"";
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
    
    if(status == null || status.length() == 0) {
      if(registryErrorList != null && registryErrorList.size() > 0) {
        status = XDS.REG_RESP_STATUS_FAILURE;
      }
      else {
        status = XDS.REG_RESP_STATUS_SUCCESS;
      }
    }
    
    StringBuffer sb = new StringBuffer(400);
    if(sDe != null && sDe.length() > 0) {
      sb.append("<" + sNs + getTagName() + " " + sDe);
    }
    else {
      sb.append("<" + sNs + getTagName());
    }
    if(status != null && status.length() > 0) {
      sb.append(" status=\"" + Utils.normalizeString(status) + "\"");
    }
    sb.append(">");
    if(registryErrorList != null && registryErrorList.size() > 0) {
      sb.append("<" + sNs + "RegistryErrorList>");
      for(RegistryError registryError : registryErrorList) {
        sb.append(registryError.toXML(sNs));
      }
      sb.append("</" + sNs + "RegistryErrorList>");
    }
    sb.append("</" + sNs + getTagName() + ">");
    return sb.toString();
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName", getTagName());
    if(status != null) mapResult.put("status", status);
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof RegistryResponse) {
      String sStatus = ((RegistryResponse) object).getStatus();
      if(sStatus == null && status == null) return true;
      return sStatus != null && sStatus.equals(status);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(status == null) return 0;
    return status.hashCode();
  }
  
  @Override
  public String toString() {
    return "RegistryResponse(" + status + "," + registryErrorList + ")";
  }
}
