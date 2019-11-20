package org.dew.ebxml.query;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.dew.ebxml.IElement;

public 
class ResponseOption implements IElement, Serializable 
{
  private static final long serialVersionUID = 4472486712257063220L;

  protected boolean returnComposedObjects;
  protected String  returnType;
  
  public ResponseOption()
  {
    returnComposedObjects = false;
    returnType = "LeafClass";
  }

  public boolean isReturnComposedObjects() {
    return returnComposedObjects;
  }

  public void setReturnComposedObjects(boolean returnComposedObjects) {
    this.returnComposedObjects = returnComposedObjects;
  }

  public String getReturnType() {
    return returnType;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }
  
  public String getTagName() {
    return "ResponseOption";
  }
  
  public String getAttribute(String name) {
    if(name == null) return null;
    if(name.equals("returnComposedObjects")) {
      return String.valueOf(this.returnComposedObjects);
    }
    else
    if(name.equals("returnType")) {
      return this.returnType;
    }
    return null;
  }
  
  public void setAttribute(String name, String value) {
    if(name == null) return;
    if(name.equals("returnComposedObjects")) {
      if(value != null && value.length() > 0) {
        this.returnComposedObjects = "tysTYS1".indexOf(value.charAt(0)) >= 0;
      }
      else {
        this.returnComposedObjects = false;
      }
    }
    else
    if(name.equals("returnType")) {
      this.returnType = value;
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
    StringBuffer sb = new StringBuffer(100);
    sb.append("<" + namespace + "ResponseOption");
    sb.append(" returnComposedObjects=\"" + returnComposedObjects + "\"");
    if(returnType != null && returnType.length() > 0) {
      sb.append(" returnType=\"" + returnType + "\"");
    }
    sb.append(">");
    sb.append("</" + namespace + "ResponseOption>");
    return sb.toString();    
  }

  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName",               getTagName());
    mapResult.put("returnComposedObjects", returnComposedObjects);
    mapResult.put("returnType",            returnType);
    return mapResult;
  }

  @Override
  public boolean equals(Object object) {
    if(object instanceof ResponseOption) {
      String  sReturnType = ((ResponseOption) object).getReturnType();
      boolean bRetTypeCOb = ((ResponseOption) object).isReturnComposedObjects();
      if(sReturnType == null && returnType == null && bRetTypeCOb == returnComposedObjects) return true;
      return sReturnType != null && sReturnType.equals(returnType) && bRetTypeCOb == returnComposedObjects;
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(returnType == null) return 0;
    int iReturnComposedObjects = returnComposedObjects ? 1 : 0;
    return returnType.hashCode() + iReturnComposedObjects;
  }
  
  @Override
  public String toString() {
    return "ResponseOption(" + returnType + ")";
  }
}
