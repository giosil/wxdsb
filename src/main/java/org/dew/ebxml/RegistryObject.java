package org.dew.ebxml;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public 
class RegistryObject extends Identifiable
{
  private static final long serialVersionUID = -8197929433210837094L;
  
  protected String description;
  protected String lid;
  protected String name;
  protected String objectType;
  protected String status;
  protected String versionInfo;
  
  protected List<Classification>     classifications;
  protected List<ExternalIdentifier> externalIdentifiers;
  
  protected String  xopIncludeHref;
  
  public RegistryObject()
  {
  }
  
  public RegistryObject(RegistryObject registryObject)
  {
    if(registryObject == null) return;
    // Identifiable
    this.id     = registryObject.getId();
    this.home   = registryObject.getHome();
    List<Slot> listOfSlot = registryObject.getSlots();
    if(listOfSlot == null) {
      this.slots = null;
    }
    else {
      this.slots = new ArrayList<Slot>(listOfSlot.size());
      for(Slot slot : listOfSlot) {
        this.slots.add(new Slot(slot));
      }
    }
    // RegistryObject
    this.description = registryObject.getDescription();
    this.lid         = registryObject.getLid();
    this.name        = registryObject.getName();
    this.objectType  = registryObject.getObjectType();
    this.status      = registryObject.getStatus();
    this.versionInfo = registryObject.getVersionInfo();
    List<Classification> listOfClassification = registryObject.getClassifications();
    if(listOfClassification == null) {
      this.classifications = null;
    }
    else {
      this.classifications = new ArrayList<Classification>(listOfClassification.size());
      for(Classification classification : listOfClassification) {
        this.classifications.add(new Classification(classification));
      }
    }
    List<ExternalIdentifier> listOfExternalIdentifier = registryObject.getExternalIdentifiers();
    if(listOfExternalIdentifier == null) {
      this.externalIdentifiers = null;
    }
    else {
      this.externalIdentifiers = new ArrayList<ExternalIdentifier>(listOfExternalIdentifier.size());
      for(ExternalIdentifier externalIdentifier : listOfExternalIdentifier) {
        this.externalIdentifiers.add(new ExternalIdentifier(externalIdentifier));
      }
    }
    this.xopIncludeHref = registryObject.getXopIncludeHref();
  }
  
  @SuppressWarnings("unchecked")
  public RegistryObject(Map<String, Object> map)
  {
    super(map);
    
    if(map == null) return;
    
    Object oDescription = map.get("description");
    if(oDescription != null) description = oDescription.toString();
    Object oLid = map.get("lid");
    if(oLid != null) lid = oLid.toString();
    Object oName = map.get("name");
    if(oName != null) name = oName.toString();
    Object oObjectType = map.get("objectType");
    if(oObjectType != null) objectType = oObjectType.toString();
    Object oStatus = map.get("status");
    if(oStatus != null) status = oStatus.toString();
    Object oVersionInfo = map.get("versionInfo");
    if(oVersionInfo != null) versionInfo = oVersionInfo.toString();
    
    Object oClassifications = map.get("classifications");
    if(oClassifications instanceof Collection) {
      Collection<?> col = (Collection<?>) oClassifications;
      classifications = new ArrayList<Classification>(col.size());
      Iterator<?> iterator = col.iterator();
      while(iterator.hasNext()) {
        Object item = iterator.next();
        if(item instanceof Map) {
          classifications.add(new Classification((Map<String, Object>) item));
        }
      }
    }
    else if(oClassifications != null && oClassifications.getClass().isArray()) {
      int length = Array.getLength(oClassifications);
      classifications = new ArrayList<Classification>(length);
      for(int i = 0; i < length; i++) {
        Object item = Array.get(oClassifications, i);
        if(item instanceof Map) {
          classifications.add(new Classification((Map<String, Object>) item));
        }
      }
    }
    
    Object oExternalIdentifiers = map.get("externalIdentifiers");
    if(oExternalIdentifiers instanceof Collection) {
      Collection<?> col = (Collection<?>) oExternalIdentifiers;
      externalIdentifiers = new ArrayList<ExternalIdentifier>(col.size());
      Iterator<?> iterator = col.iterator();
      while(iterator.hasNext()) {
        Object item = iterator.next();
        if(item instanceof Map) {
          externalIdentifiers.add(new ExternalIdentifier((Map<String, Object>) item));
        }
      }
    }
    else if(oExternalIdentifiers != null && oExternalIdentifiers.getClass().isArray()) {
      int length = Array.getLength(oExternalIdentifiers);
      externalIdentifiers = new ArrayList<ExternalIdentifier>(length);
      for(int i = 0; i < length; i++) {
        Object item = Array.get(oExternalIdentifiers, i);
        if(item instanceof Map) {
          externalIdentifiers.add(new ExternalIdentifier((Map<String, Object>) item));
        }
      }
    }
  }
  
  public void setId(String id) {
    super.setId(id);
    if(classifications != null) {
      for(Classification classification : classifications) {
        classification.setClassifiedObject(id);
      }
    }
    if(externalIdentifiers != null) {
      for(ExternalIdentifier externalIdentifier : externalIdentifiers) {
        externalIdentifier.setRegistryObject(id);
      }
    }
  }
  
  public List<Classification> getClassifications() {
    return classifications;
  }
  
  public void setClassifications(List<Classification> classifications) {
    this.classifications = classifications;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public List<ExternalIdentifier> getExternalIdentifiers() {
    return externalIdentifiers;
  }
  
  public void setExternalIdentifiers(List<ExternalIdentifier> externalIdentifiers) {
    this.externalIdentifiers = externalIdentifiers;
  }
  
  public String getLid() {
    return lid;
  }
  
  public void setLid(String lid) {
    this.lid = lid;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getObjectType() {
    return objectType;
  }
  
  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getVersionInfo() {
    return versionInfo;
  }
  
  public void setVersionInfo(String versionInfo) {
    this.versionInfo = versionInfo;
  }
  
  public String getXopIncludeHref() {
    return xopIncludeHref;
  }
  
  public void setXopIncludeHref(String xopIncludeHref) {
    this.xopIncludeHref = xopIncludeHref;
  }
  
  public void addClassification(Classification classification) {
    if(classification  == null) return;
    if(classifications == null) classifications = new ArrayList<Classification>();
    classification.setClassifiedObject(id);
    classifications.add(classification);
  }
  
  public void removeClassification(Classification classification) {
    if(classification  == null) return;
    if(classifications == null) return;
    classifications.remove(classification);
  }
  
  public void addExternalIdentifier(ExternalIdentifier externalIdentifier) {
    if(externalIdentifier  == null) return;
    if(externalIdentifiers == null) externalIdentifiers = new ArrayList<ExternalIdentifier>();
    externalIdentifier.setRegistryObject(id);
    externalIdentifiers.add(externalIdentifier);
  }
  
  public void removeExternalIdentifier(ExternalIdentifier externalIdentifier) {
    if(externalIdentifier  == null) return;
    if(externalIdentifiers == null) return;
    externalIdentifiers.remove(externalIdentifier);
  }
  
  public Classification getClassification(String classificationScheme) {
    if(classificationScheme == null || classificationScheme.length() == 0) {
      return null;
    }
    if(classifications == null) return null;
    for(Classification classification : classifications) {
      String sClassificationScheme = classification.getClassificationScheme();
      if(classificationScheme.startsWith("*") && classificationScheme.length() > 1) {
        if(sClassificationScheme != null && sClassificationScheme.endsWith(classificationScheme.substring(1))) {
          return classification;
        }
      }
      if(classificationScheme.endsWith("*") && classificationScheme.length() > 1) {
        if(sClassificationScheme != null && sClassificationScheme.startsWith(classificationScheme.substring(0, classificationScheme.length()-1))) {
          return classification;
        }
      }
      if(classificationScheme.equals(sClassificationScheme)) {
        return classification;
      }
    }
    return null;
  }
  
  public String getClassificationName(String classificationScheme, String defaultValue) {
    if(classificationScheme == null || classificationScheme.length() == 0) {
      return defaultValue;
    }
    if(classifications == null) return null;
    for(Classification classification : classifications) {
      String sClassificationScheme = classification.getClassificationScheme();
      if(classificationScheme.startsWith("*") && classificationScheme.length() > 1) {
        if(sClassificationScheme != null && sClassificationScheme.endsWith(classificationScheme.substring(1))) {
          String name = classification.getName();
          if(name == null) return defaultValue;
          return name;
        }
      }
      if(classificationScheme.endsWith("*") && classificationScheme.length() > 1) {
        if(sClassificationScheme != null && sClassificationScheme.startsWith(classificationScheme.substring(0, classificationScheme.length()-1))) {
          String name = classification.getName();
          if(name == null) return defaultValue;
          return name;
        }
      }
      if(classificationScheme.equals(sClassificationScheme)) {
        String name = classification.getName();
        if(name == null) return defaultValue;
        return name;
      }
    }
    return defaultValue;
  }
  
  public List<Classification> getClassifications(String classificationScheme) {
    List<Classification> result = new ArrayList<Classification>();
    if(classificationScheme == null || classificationScheme.length() == 0) {
      return result;
    }
    if(classifications == null) return result;
    for(Classification classification : classifications) {
      String sClassificationScheme = classification.getClassificationScheme();
      if(classificationScheme.startsWith("*") && classificationScheme.length() > 1) {
        if(sClassificationScheme != null && sClassificationScheme.endsWith(classificationScheme.substring(1))) {
          result.add(classification);
        }
      }
      if(classificationScheme.endsWith("*") && classificationScheme.length() > 1) {
        if(sClassificationScheme != null && sClassificationScheme.startsWith(classificationScheme.substring(0, classificationScheme.length()-1))) {
          result.add(classification);
        }
      }
      if(classificationScheme.equals(sClassificationScheme)) {
        result.add(classification);
      }
    }
    return result;
  }
  
  public ExternalIdentifier getExternalIdentifier(String name) {
    if(name == null || name.length() == 0) {
      return null;
    }
    if(externalIdentifiers == null) return null;
    for(ExternalIdentifier externalIdentifier : externalIdentifiers) {
      String sName = externalIdentifier.getName();
      String sIdScheme = externalIdentifier.getIdentificationScheme();
      if(name.equals(sName)) return externalIdentifier;
      if(name.equals(sIdScheme)) return externalIdentifier;
    }
    return null;
  }
  
  public ExternalIdentifier getExternalIdentifier(String name, String scheme) {
    if(externalIdentifiers == null) return null;
    for(ExternalIdentifier externalIdentifier : externalIdentifiers) {
      String sName   = externalIdentifier.getName();
      String sScheme = externalIdentifier.getIdentificationScheme();
      if(name != null && name.equals(sName)) return externalIdentifier;
      if(scheme != null && scheme.equals(sScheme)) return externalIdentifier;
    }
    return null;
  }
  
  public List<ObjectRef> getObjectRefs() {
    List<ObjectRef> listResult = new ArrayList<ObjectRef>();
    if(classifications != null) {
      for(Classification classification : classifications) {
        String sClassificationScheme = classification.getClassificationScheme();
        if(sClassificationScheme != null && sClassificationScheme.length() > 0) {
          listResult.add(new ObjectRef(sClassificationScheme));
        }
      }
    }
    if(externalIdentifiers != null) {
      for(ExternalIdentifier externalIdentifier : externalIdentifiers) {
        String sIdentificationScheme = externalIdentifier.getIdentificationScheme();
        if(sIdentificationScheme != null && sIdentificationScheme.length() > 0) {
          listResult.add(new ObjectRef(sIdentificationScheme));
        }
      }
    }
    return listResult;
  }
  
  @Override
  public String getTagName() {
    return "RegistryObject";
  }
  
  @Override
  public String getAttribute(String name) {
    if(name == null) return null;
    if(name.equals("id")) {
      return this.id;
    }
    else if(name.equals("home")) {
      return this.home;
    }
    else if(name.equals("lid")) {
      return this.lid;
    }
    else if(name.equals("objectType")) {
      return this.objectType;
    }
    else if(name.equals("status")) {
      return this.status;
    }
    else if(name.equals("description")) {
      return this.description;
    }
    else if(name.equals("name")) {
      return this.name;
    }
    else if(name.equals("versionInfo")) {
      return this.versionInfo;
    }
    return null;
  }
  
  @Override
  public void setAttribute(String name, String value) {
    if(name == null) return;
    if(name.equals("id")) {
      this.id = value;
    }
    else if(name.equals("home")) {
      this.home = value;
    }
    else if(name.equals("lid")) {
      this.lid = value;
    }
    else if(name.equals("objectType")) {
      this.objectType = value;
    }
    else if(name.equals("status")) {
      this.status = value;
    }
    else if(name.equals("description")) {
      this.description = value;
    }
    else if(name.equals("name")) {
      this.name = value;
    }
    else if(name.equals("versionInfo")) {
      this.versionInfo = value;
    }
  }
  
  @Override
  public String toXML(String namespace) {
    if(namespace == null || namespace.length() == 0) {
      namespace = "";
    }
    else if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    if(id == null || id.length() == 0) {
      id = "urn:uuid:" + UUID.randomUUID().toString();
      if(classifications != null) {
        for(Classification classification : classifications) {
          classification.setClassifiedObject(id);
        }
      }
      if(externalIdentifiers != null) {
        for(ExternalIdentifier externalIdentifier : externalIdentifiers) {
          externalIdentifier.setRegistryObject(id);
        }
      }
    }
    StringBuffer sb = new StringBuffer(500);
    sb.append("<" + namespace + getTagName());
    if(home != null && home.length() > 0) {
      sb.append(" home=\"" + home + "\"");
    }
    if(id != null && id.length() > 0) {
      sb.append(" id=\"" + id + "\"");
    }
    if(lid != null && lid.length() > 0) {
      sb.append(" lid=\"" + lid + "\"");
    }
    if(objectType != null && objectType.length() > 0) {
      sb.append(" objectType=\"" + objectType + "\"");
    }
    if(status != null && status.length() > 0) {
      sb.append(" status=\"" + status + "\"");
    }
    sb.append(">");
    if(slots != null) {
      for(Slot slot : slots) {
        sb.append(slot.toXML(namespace));
      }
    }
    if(name != null && name.length() > 0) {
      sb.append("<"  + namespace + "Name>");
      sb.append("<"  + namespace + "LocalizedString value=\"" + Utils.normalizeString(name) + "\"></" + namespace + "LocalizedString>");
      sb.append("</" + namespace + "Name>");
    }
    if(versionInfo != null && versionInfo.length() > 0) {
      sb.append("<rim:VersionInfo versionName=\"" + versionInfo + "\">");
      sb.append("</rim:VersionInfo>");
    }
    if(classifications != null) {
      for(Classification classification : classifications) {
        sb.append(classification.toXML(namespace));
      }
    }
    if(externalIdentifiers != null) {
      for(ExternalIdentifier externalIdentifier : externalIdentifiers) {
        sb.append(externalIdentifier.toXML(namespace));
      }
    }
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  @Override
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName", getTagName());
    if(id          != null) mapResult.put("id",          id);
    if(home        != null) mapResult.put("home",        home);
    if(lid         != null) mapResult.put("lid",         lid);
    if(objectType  != null) mapResult.put("objectType",  objectType);
    if(status      != null) mapResult.put("status",      status);
    if(description != null) mapResult.put("description", description);
    if(name        != null) mapResult.put("name",        name);
    if(versionInfo != null) mapResult.put("versionInfo", versionInfo);
    if(slots != null) {
      List<Map<String,Object>> listOfMap = new ArrayList<Map<String,Object>>(slots.size());
      for(Slot slot : slots) {
        listOfMap.add(slot.toMap());
      }
      mapResult.put("slots", listOfMap);
    }
    if(classifications != null) {
      List<Map<String,Object>> listOfMap = new ArrayList<Map<String,Object>>(classifications.size());
      for(Classification classification : classifications) {
        listOfMap.add(classification.toMap());
      }
      mapResult.put("classifications", listOfMap);
    }
    if(externalIdentifiers != null) {
      List<Map<String,Object>> listOfMap = new ArrayList<Map<String,Object>>(externalIdentifiers.size());
      for(ExternalIdentifier externalIdentifier : externalIdentifiers) {
        listOfMap.add(externalIdentifier.toMap());
      }
      mapResult.put("externalIdentifiers", listOfMap);
    }
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof RegistryObject) {
      String sId = ((RegistryObject) object).getId();
      if(sId == null && id == null) return true;
      return sId != null && sId.equals(id);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(id == null) return 0;
    return id.hashCode();
  }
  
  @Override
  public String toString() {
    return "RegistryObject(" + id + ")";
  }
}
