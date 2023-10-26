package org.dew.ebxml;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public 
class RegistryObjectList implements IElement, Serializable
{
  private static final long serialVersionUID = 5171653942049615341L;
  
  protected List<Identifiable> listOfIdentifiable = new ArrayList<Identifiable>();
  
  public RegistryObjectList()
  {
  }
  
  public RegistryObjectList(List<Identifiable> listOfIdentifiable)
  {
    this.listOfIdentifiable = listOfIdentifiable;
  }
  
  public Identifiable findRegistryObject(String id) {
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return null;
    }
    for(Identifiable identifiable : listOfIdentifiable) {
      String sId = identifiable.getId();
      if(sId != null && sId.equals(id)) {
        return identifiable;
      }
    }
    return null;
  }
  
  public List<ExtrinsicObject> getExtrinsicObjects() {
    List<ExtrinsicObject> listResult = new ArrayList<ExtrinsicObject>();
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return listResult;
    }
    for(Identifiable identifiable : listOfIdentifiable) {
      if(identifiable instanceof ExtrinsicObject) {
        listResult.add((ExtrinsicObject) identifiable);
      }
    }
    return listResult;
  }
  
  public List<RegistryPackage> getRegistryPackages() {
    List<RegistryPackage> listResult = new ArrayList<RegistryPackage>();
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return listResult;
    }
    for(Identifiable identifiable : listOfIdentifiable) {
      if(identifiable instanceof RegistryPackage) {
        listResult.add((RegistryPackage) identifiable);
      }
    }
    return listResult;
  }
  
  public RegistryPackage getRegistryPackage(String registryPackageId) {
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return null;
    }
    for(Identifiable identifiable : listOfIdentifiable) {
      if(identifiable instanceof RegistryPackage) {
        RegistryPackage registryPackage = (RegistryPackage) identifiable;
        String sId = registryPackage.getId();
        if(sId != null && sId.equals(registryPackageId)) {
          return registryPackage;
        }
      }
    }
    return null;
  }
  
  public Map<String,RegistryPackage> getRegistryPackagesMap() {
    Map<String,RegistryPackage> result = new HashMap<String,RegistryPackage>();
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return result;
    }
    for(Identifiable identifiable : listOfIdentifiable) {
      if(identifiable instanceof Association) {
        Association association = (Association) identifiable;
        String sAssType = association.getAssociationType();
        if(sAssType == null) continue;
        if(sAssType.endsWith("Member") || sAssType.endsWith("member")) {
          String sourceObject = association.getSourceObject();
          RegistryPackage registryPackage = getRegistryPackage(sourceObject);
          if(registryPackage == null) continue;
          String targetObject = association.getTargetObject();
          if(targetObject == null || targetObject.length() == 0) continue;
          result.put(targetObject, registryPackage);
        }
      }
    }
    return result;
  }
  
  public Map<String,Association> getAssociationsMap() {
    Map<String,Association> result = new HashMap<String,Association>();
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return result;
    }
    for(Identifiable identifiable : listOfIdentifiable) {
      if(identifiable instanceof Association) {
        Association association = (Association) identifiable;
        String sSrcObject = association.getSourceObject();
        String sAssType   = association.getAssociationType();
        if(sSrcObject == null || sSrcObject.length() == 0) continue;
        if(sAssType   == null || sAssType.length()   == 0) continue;
        if(sAssType.endsWith("Member") || sAssType.endsWith("member")) {
          continue;
        }
        result.put(sSrcObject, association);
      }
    }
    return result;
  }
  
  public List<Classification> getClassifications(String classifiedObject) {
    List<Classification> listResult = new ArrayList<Classification>();
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return listResult;
    }
    for(Identifiable identifiable : listOfIdentifiable) {
      if(identifiable instanceof Classification) {
        Classification classification = (Classification) identifiable;
        if(classifiedObject != null && classifiedObject.equals(classification.getClassifiedObject())) {
          listResult.add(classification);
        }
      }
    }
    return listResult;
  }
  
  public Association getAssociation(String sourceObject, String targetObject) {
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return null;
    }
    for(Identifiable identifiable : listOfIdentifiable) {
      if(identifiable instanceof Association) {
        Association association = (Association) identifiable;
        if(sourceObject != null && !sourceObject.equals(association.getSourceObject())) {
          continue;
        }
        if(targetObject != null && !targetObject.equals(association.getTargetObject())) {
          continue;
        }
        return association;
      }
    }
    return null;
  }
  
  public void addRegistryObject(Identifiable identifiable) {
    if(identifiable == null) return;
    if(listOfIdentifiable == null) listOfIdentifiable = new ArrayList<Identifiable>();
    if(listOfIdentifiable.contains(identifiable)) return;
    listOfIdentifiable.add(identifiable);
  }
  
  public Identifiable getRegistryObject(int i) {
    if(listOfIdentifiable == null || listOfIdentifiable.size() <= i) return null;
    return listOfIdentifiable.get(i);
  }
  
  public List<Identifiable> getListOfIdentifiable() {
    return listOfIdentifiable;
  }
  
  public int size() {
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return 0;
    }
    return listOfIdentifiable.size();
  }
  
  public int getTotalResultCount() {
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return 0;
    }
    int result = 0;
    for(Identifiable identifiable : listOfIdentifiable) {
      if(identifiable instanceof ExtrinsicObject) {
        result++;
      }
      else if(identifiable instanceof RegistryPackage) {
        result++;
      }
    }
    return result;
  }
  
  public String getTagName() {
    return "RegistryObjectList";
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
    else if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    int iSize = listOfIdentifiable != null ? listOfIdentifiable.size() : 0;
    StringBuilder sb = new StringBuilder(50 + iSize * 3000);
    sb.append("<" + namespace + getTagName() + ">");
    if(listOfIdentifiable != null) {
      for(Identifiable identifiable : listOfIdentifiable) {
        sb.append(identifiable.toXML(namespace));
      }
    }
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  public String toXMLObjectRefList(String namespace) {
    if(namespace == null || namespace.length() == 0) {
      namespace = "";
    }
    else if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    int iSize = listOfIdentifiable != null ? listOfIdentifiable.size() : 0;
    StringBuilder sb = new StringBuilder(50 + iSize * 63);
    sb.append("<" + namespace + getTagName() + ">");
    if(listOfIdentifiable != null) {
      for(Identifiable identifiable : listOfIdentifiable) {
        sb.append(identifiable.toXMLObjectRef(namespace));
      }
    }
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName",            getTagName());
    mapResult.put("registryObjectList", listOfIdentifiable);
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof RegistryObjectList) {
      List<Identifiable> l = ((RegistryObjectList) object).getListOfIdentifiable();
      if(listOfIdentifiable == null && l == null) return true;
      return listOfIdentifiable != null && listOfIdentifiable.equals(l);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(listOfIdentifiable == null) return 0;
    return listOfIdentifiable.hashCode();
  }
  
  @Override
  public String toString() {
    return "RegistryObjectList(" + listOfIdentifiable + ")";
  }
}
