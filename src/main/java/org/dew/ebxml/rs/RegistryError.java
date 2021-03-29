package org.dew.ebxml.rs;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.dew.ebxml.IElement;
import org.dew.ebxml.Utils;

import org.dew.xds.XDS;

public 
class RegistryError implements IElement, Serializable
{
  private static final long serialVersionUID = -1166029158350233700L;
  
  protected String codeContext;
  protected String errorCode;
  protected String severity;
  
  public RegistryError()
  {
    this.errorCode   = "XDSRegistryError";
    this.severity    = XDS.ERR_SEVERITY_ERROR;
  }

  public RegistryError(String codeContext)
  {
    this.codeContext = codeContext;
    this.errorCode   = "XDSRegistryError";
    this.severity    = XDS.ERR_SEVERITY_ERROR;
  }

  public RegistryError(String codeContext, String errorCode)
  {
    this.codeContext = codeContext;
    this.errorCode   = errorCode;
    this.severity    = XDS.ERR_SEVERITY_ERROR;
  }

  public RegistryError(String codeContext, String errorCode, String severity)
  {
    this.codeContext = codeContext;
    if(errorCode == null || errorCode.length() == 0) {
      this.errorCode = "XDSRegistryError";
    }
    else {
      this.errorCode = errorCode;
    }
    if(severity == null || severity.length() == 0) {
      this.severity = XDS.ERR_SEVERITY_ERROR;
    }
    else if(severity.length() < 25) {
      if(severity.endsWith("Warning") || severity.endsWith("warning")) {
        this.severity = XDS.ERR_SEVERITY_WARNING;
      }
      else {
        this.severity = XDS.ERR_SEVERITY_ERROR;
      }
    }
    else {
      this.severity = severity;
    }
  }

  public String getCodeContext() {
    return codeContext;
  }

  public void setCodeContext(String codeContext) {
    this.codeContext = codeContext;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    if(severity == null || severity.length() == 0) {
      this.severity = XDS.ERR_SEVERITY_ERROR;   
    }
    else if(severity.length() < 25) {
      if(severity.endsWith("Warning") || severity.endsWith("warning")) {
        this.severity = XDS.ERR_SEVERITY_WARNING;
      }
      else {
        this.severity = XDS.ERR_SEVERITY_ERROR;
      }
    }
    else {
      this.severity = severity;
    }
  }
  
  public String getTagName() {
    return "RegistryError";
  }
  
  public String getAttribute(String name) {
    if(name == null) return null;
    if(name.equals("codeContext")) {
      return this.codeContext;
    }
    else if(name.equals("errorCode")) {
      return this.errorCode;
    }
    else if(name.equals("severity")) {
      return this.severity;
    }
    return null;
  }
  
  public void setAttribute(String name, String value) {
    if(name == null) return;
    if(name.equals("codeContext")) {
      this.codeContext = value;
    }
    else if(name.equals("errorCode")) {
      this.errorCode = value;
    }
    else if(name.equals("severity")) {
      this.severity = value;
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
    if(codeContext != null && codeContext.length() > 0) {
      sb.append(" codeContext=\"" + Utils.normalizeString(codeContext) + "\"");
    }
    if(errorCode != null && errorCode.length() > 0) {
      sb.append(" errorCode=\"" + Utils.normalizeString(errorCode) + "\"");
    }
    if(severity != null && severity.length() > 0) {
      sb.append(" severity=\"" + Utils.normalizeString(severity) + "\"");
    }
    sb.append(">");
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName", getTagName());
    if(codeContext != null) mapResult.put("codeContext", codeContext);
    if(errorCode   != null) mapResult.put("errorCode",   errorCode);
    if(severity    != null) mapResult.put("severity",    severity);
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof RegistryError) {
      String sCodeContext = ((RegistryError) object).getCodeContext();
      if(sCodeContext == null && codeContext == null) return true;
      return sCodeContext != null && sCodeContext.equals(codeContext);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(codeContext == null) return 0;
    return codeContext.hashCode();
  }
  
  @Override
  public String toString() {
    return "RegistryError(" + codeContext + ")";
  }
}
