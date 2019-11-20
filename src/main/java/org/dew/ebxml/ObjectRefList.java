package org.dew.ebxml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dew.xds.XDSDocument;

public 
class ObjectRefList implements IElement, Serializable
{
  private static final long serialVersionUID = -3101804734312925340L;
  
  protected List<ObjectRef> listOfObjectRef = new ArrayList<ObjectRef>();
  
  public ObjectRefList()
  {
  }
  
  public ObjectRefList(String... asRegistryObjectId)
  {
    if(asRegistryObjectId == null) return;
    for(int i = 0; i < asRegistryObjectId.length; i++) {
      addObjectRef(asRegistryObjectId[i]);
    }
  }
  
  public ObjectRefList(XDSDocument... arrayOfXDSDocument)
  {
    if(arrayOfXDSDocument == null) return;
    for(int i = 0; i < arrayOfXDSDocument.length; i++) {
      addObjectRef(arrayOfXDSDocument[i].getRegistryObjectId());
    }
  }
  
  public ObjectRefList(List<ObjectRef> listOfObjectRef)
  {
    this.listOfObjectRef = listOfObjectRef;
  }
  
  public void addObjectRef(String registryObjectId) {
    if(registryObjectId == null || registryObjectId.length() == 0) {
      return;
    }
    addObjectRef(new ObjectRef(registryObjectId));
  }
  
  public void addObjectRef(ObjectRef objectRef) {
    if(objectRef == null) return;
    if(listOfObjectRef == null) listOfObjectRef = new ArrayList<ObjectRef>();
    if(listOfObjectRef.contains(objectRef)) return;
    listOfObjectRef.add(objectRef);
  }
  
  public ObjectRef getObjectRef(int i) {
    if(listOfObjectRef == null || listOfObjectRef.size() <= i) return null;
    return listOfObjectRef.get(i);
  }
  
  public List<ObjectRef> getListOfObjectRef() {
    return listOfObjectRef;
  }
  
  public int size() {
    return listOfObjectRef.size();
  }
  
  public String getTagName() {
    return "ObjectRefList";
  }
  
  public String getAttribute(String name) {
    return null;
  }
  
  public void setAttribute(String name, String value) {
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
    sb.append("<" + namespace + getTagName() + ">");
    if(listOfObjectRef != null) {
      for(ObjectRef objectRef : listOfObjectRef) {
        sb.append(objectRef.toXML(namespace));
      }
    }
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName",       getTagName());
    mapResult.put("objectRefList", listOfObjectRef);
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof ObjectRefList) {
      List<ObjectRef> l = ((ObjectRefList) object).getListOfObjectRef();
      if(listOfObjectRef == null && l == null) return true;
      return listOfObjectRef != null && listOfObjectRef.equals(l);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(listOfObjectRef == null) return 0;
    return listOfObjectRef.hashCode();
  }
  
  @Override
  public String toString() {
    return "ObjectRefList(" + listOfObjectRef + ")";
  }
}