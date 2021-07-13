package org.dew.ebxml;

import java.util.Map;
import java.util.UUID;

public 
class RegistryPackage extends RegistryObject 
{
  private static final long serialVersionUID = -7667790648383243455L;
  
  public RegistryPackage()
  {
    this.objectType = RIM.TYPE_REGISTRYPACKAGE;
  }
  
  public RegistryPackage(String id)
  {
    this.objectType = RIM.TYPE_REGISTRYPACKAGE;
    this.id = id;
  }
  
  public RegistryPackage(String id, String status)
  {
    this.objectType = RIM.TYPE_REGISTRYPACKAGE;
    this.id = id;
    this.status = status;
  }
  
  public RegistryPackage(RegistryObject registryObject)
  {
    super(registryObject);
    this.objectType = RIM.TYPE_REGISTRYPACKAGE;
  }
  
  public RegistryPackage(Map<String, Object> map)
  {
    super(map);
    
    if(objectType == null || objectType.length() == 0) {
      this.objectType = RIM.TYPE_REGISTRYPACKAGE;
    }
  }
  
  @Override
  public String getTagName() {
    return "RegistryPackage";
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
    }
    StringBuffer sb = new StringBuffer(120 * 5 + 600 * 5 + 500 * 3);
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
      sb.append("<"  + namespace + "VersionInfo versionName=\"" + versionInfo + "\">");
      sb.append("</" + namespace + "VersionInfo>");
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
    Map<String, Object> mapResult = super.toMap();
    mapResult.put("tagName", getTagName());
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof RegistryPackage) {
      String sId = ((RegistryPackage) object).getId();
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
    return "RegistryPackage(" + id + ")";
  }
}
