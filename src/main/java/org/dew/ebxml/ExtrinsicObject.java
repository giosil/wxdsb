package org.dew.ebxml;

import java.util.Map;
import java.util.UUID;

import org.dew.xds.XDS;

public
class ExtrinsicObject extends RegistryObject
{
  private static final long serialVersionUID = -6130701561435454067L;
  
  protected String  contentVersionInfo;
  protected boolean opaque;
  protected String  mimeType;
  
  public ExtrinsicObject()
  {
    this.objectType = XDS.TYPE_XDS_DOCUMENT_ENTRY;
  }
  
  public ExtrinsicObject(String id, String mimeType, String status)
  {
    this.objectType = XDS.TYPE_XDS_DOCUMENT_ENTRY;
    this.id = id;
    this.mimeType = mimeType;
    this.status = status;
  }
  
  public ExtrinsicObject(String id, String lid, String mimeType, String status)
  {
    this.objectType = XDS.TYPE_XDS_DOCUMENT_ENTRY;
    this.id = id;
    this.lid = lid;
    this.mimeType = mimeType;
    this.status = status;
  }
  
  public ExtrinsicObject(RegistryObject registryObject)
  {
    super(registryObject);
    this.objectType = XDS.TYPE_XDS_DOCUMENT_ENTRY;
    if(registryObject instanceof ExtrinsicObject) {
      this.contentVersionInfo = ((ExtrinsicObject) registryObject).getContentVersionInfo();
      this.opaque             = ((ExtrinsicObject) registryObject).isOpaque();
      this.mimeType           = ((ExtrinsicObject) registryObject).getMimeType();
    }
  }
  
  @SuppressWarnings("rawtypes")
  public ExtrinsicObject(Map map)
  {
    super(map);
    
    if(map == null) return;
    
    Object oContentVersionInfo = map.get("contentVersionInfo");
    if(oContentVersionInfo != null) contentVersionInfo = oContentVersionInfo.toString();
    Object oMimeType = map.get("mimeType");
    if(oMimeType != null) mimeType = oMimeType.toString();
    Object oOpaque = map.get("opaque");
    if(oOpaque == null) {
      oOpaque = map.get("isOpaque");
    }
    opaque = Utils.toBoolean(oOpaque, false);
    
    if(objectType == null || objectType.length() == 0) {
      this.objectType = XDS.TYPE_XDS_DOCUMENT_ENTRY;
    }
  }
  
  public String getContentVersionInfo() {
    return contentVersionInfo;
  }
  
  public void setContentVersionInfo(String contentVersionInfo) {
    this.contentVersionInfo = contentVersionInfo;
  }
  
  public boolean isOpaque() {
    return opaque;
  }
  
  public void setOpaque(boolean opaque) {
    this.opaque = opaque;
  }
  
  public String getMimeType() {
    return mimeType;
  }
  
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }
  
  @Override
  public String getTagName() {
    return "ExtrinsicObject";
  }
  
  @Override
  public String getAttribute(String name) {
    if(name == null) return null;
    String result = super.getAttribute(name);
    if(result != null) return result;
    if(name.equals("contentVersionInfo")) {
      return this.contentVersionInfo;
    }
    else if(name.equals("isOpaque")) {
      return String.valueOf(this.opaque);
    }
    else if(name.equals("mimeType")) {
      return this.mimeType;
    }
    return null;
  }
  
  @Override
  public void setAttribute(String name, String value) {
    if(name == null) return;
    super.setAttribute(name, value);
    if(name.equals("contentVersionInfo")) {
      this.contentVersionInfo = value;
    }
    else if(name.equals("isOpaque")) {
      if(value != null && value.length() > 0) {
        this.opaque = "tysTYS1".indexOf(value.charAt(0)) >= 0;
      }
      else {
        this.opaque = false;
      }
    }
    else if(name.equals("opaque")) {
      if(value != null && value.length() > 0) {
        this.opaque = "tysTYS1".indexOf(value.charAt(0)) >= 0;
      }
      else {
        this.opaque = false;
      }
    }
    else if(name.equals("mimeType")) {
      this.mimeType = value;
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
    }
    StringBuffer sb = new StringBuffer(120 * 5 + 600 * 5 + 500 * 3);
    if(home != null && home.length() > 0) {
      sb.append("<" + namespace + getTagName() + " home=\"" + home + "\" id=\"" + id + "\" isOpaque=\"" + opaque + "\"");
    }
    else {
      sb.append("<" + namespace + getTagName() + " id=\"" + id + "\" isOpaque=\"" + opaque + "\"");
    }
    if(lid != null && lid.length() > 0) {
      sb.append(" lid=\"" + lid + "\"");
    }
    if(mimeType != null && mimeType.length() > 0) {
      sb.append(" mimeType=\"" + mimeType + "\"");
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
    if(contentVersionInfo != null && contentVersionInfo.length() > 0) {
      sb.append("<"  + namespace + "ContentVersionInfo versionName=\"" + contentVersionInfo + "\">");
      sb.append("</" + namespace + "ContentVersionInfo>");
    }
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  @Override
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = super.toMap();
    mapResult.put("tagName", getTagName());
    mapResult.put("opaque",  opaque);
    if(contentVersionInfo != null) mapResult.put("contentVersionInfo", contentVersionInfo);
    if(mimeType != null) mapResult.put("mimeType", mimeType);
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof ExtrinsicObject) {
      String sId = ((ExtrinsicObject) object).getId();
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
    return "ExtrinsicObject(" + id + ")";
  }
}
