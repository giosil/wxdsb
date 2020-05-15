package org.dew.ebxml;

import java.util.Map;
import java.util.UUID;

public 
class Classification extends RegistryObject 
{
  private static final long serialVersionUID = -6770497967609802010L;

  protected String classificationScheme;
  protected String classificationNode;
  protected String classifiedObject;
  protected String nodeRepresentation;
  
  public Classification()
  {
    this.objectType = RIM.TYPE_CLASSIFICATION;
  }
  
  public Classification(String classificationScheme, String classifiedObject)
  {
    this.objectType = RIM.TYPE_CLASSIFICATION;
    this.classificationScheme = classificationScheme;
    this.classifiedObject = classifiedObject;
  }

  public Classification(String classificationScheme, String classifiedObject, String nodeRepresentation)
  {
    this.objectType = RIM.TYPE_CLASSIFICATION;
    this.classificationScheme = classificationScheme;
    this.classifiedObject = classifiedObject;
    this.nodeRepresentation = nodeRepresentation;
  }
  
  public Classification(Classification classification)
  {
    super((RegistryObject) classification);
    this.objectType = RIM.TYPE_CLASSIFICATION;
    if(classification == null) return;
    this.classificationScheme = classification.getClassificationScheme();
    this.classificationNode   = classification.getClassificationNode();
    this.classifiedObject     = classification.getClassifiedObject();
    this.nodeRepresentation   = classification.getNodeRepresentation();
  }
  
  @SuppressWarnings("rawtypes")
  public Classification(Map map)
  {
    super(map);
    
    if(map == null) return;
    
    Object oClassificationScheme = map.get("classificationScheme");
    if(oClassificationScheme != null) classificationScheme = oClassificationScheme.toString();
    Object oClassificationNode = map.get("classificationNode");
    if(oClassificationNode != null) classificationNode = oClassificationNode.toString();
    Object oClassifiedObject = map.get("classifiedObject");
    if(oClassifiedObject != null) classifiedObject = oClassifiedObject.toString();
    Object oNodeRepresentation = map.get("nodeRepresentation");
    if(oNodeRepresentation != null) nodeRepresentation = Utils.toString(oNodeRepresentation, "");
    
    if(objectType == null || objectType.length() == 0) {
      this.objectType = RIM.TYPE_CLASSIFICATION;
    }
  }

  public String getClassificationScheme() {
    return classificationScheme;
  }

  public void setClassificationScheme(String classificationScheme) {
    this.classificationScheme = classificationScheme;
  }

  public String getClassificationNode() {
    return classificationNode;
  }

  public void setClassificationNode(String classificationNode) {
    this.classificationNode = classificationNode;
  }

  public String getClassifiedObject() {
    return classifiedObject;
  }

  public void setClassifiedObject(String classifiedObject) {
    this.classifiedObject = classifiedObject;
  }

  public String getNodeRepresentation() {
    return nodeRepresentation;
  }

  public void setNodeRepresentation(String nodeRepresentation) {
    this.nodeRepresentation = nodeRepresentation;
  }
  
  public String getCodingScheme() {
    String[] asValues = getSlotValues("codingScheme");
    if(asValues == null || asValues.length == 0) return null;
    return asValues[0];
  }
  
  @Override
  public String getTagName() {
    return "Classification";
  }

  @Override
  public String getAttribute(String name) {
    if(name == null) return null;
    String result = super.getAttribute(name);
    if(result != null) return result;
    if(name.equals("classificationScheme")) {
      return this.classificationScheme;
    }
    else if(name.equals("classificationNode")) {
      return this.classificationNode;
    }
    else if(name.equals("classifiedObject")) {
      return this.classifiedObject;
    }
    else if(name.equals("nodeRepresentation")) {
      return this.nodeRepresentation;
    }
    return null;
  }
  
  @Override
  public void setAttribute(String name, String value) {
    if(name == null) return;
    super.setAttribute(name, value);
    if(name.equals("classificationScheme")) {
      this.classificationScheme = value;
    }
    else if(name.equals("classificationNode")) {
      this.classificationNode = value;
    }
    else if(name.equals("classifiedObject")) {
      this.classifiedObject = value;
    }
    else if(name.equals("nodeRepresentation")) {
      this.nodeRepresentation = value;
    }
  }

  @Override
  public String toXML(String namespace) {
    if(id == null || id.length() == 0) {
      id = "urn:uuid:" + UUID.randomUUID().toString();
    }
    if(namespace == null || namespace.length() == 0) {
      namespace = "";
    }
    else if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    StringBuffer sb = new StringBuffer(600);
    sb.append("<" + namespace + getTagName());
    if(classificationNode != null && classificationNode.length() > 0) {
      sb.append(" classificationNode=\"" + classificationNode + "\"");
    }
    if(classificationScheme != null && classificationScheme.length() > 0) {
      sb.append(" classificationScheme=\"" + classificationScheme + "\"");
    }
    if(classifiedObject != null && classifiedObject.length() > 0) {
      sb.append(" classifiedObject=\"" + classifiedObject + "\"");
    }
    if(home != null && home.length() > 0) {
      sb.append(" home=\"" + home + "\"");
    }
    if(id != null && id.length() > 0) {
      sb.append(" id=\"" + id + "\"");
    }
    if(lid != null && lid.length() > 0) {
      sb.append(" lid=\"" + lid + "\"");
    }
    if(nodeRepresentation != null) {
      // nodeRepresentation can be empty
      sb.append(" nodeRepresentation=\"" + Utils.normalizeString(nodeRepresentation) + "\"");
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
    if(classificationScheme != null) mapResult.put("classificationScheme", classificationScheme);
    if(classificationNode   != null) mapResult.put("classificationNode",   classificationNode);
    if(classifiedObject     != null) mapResult.put("classifiedObject",     classifiedObject);
    if(nodeRepresentation   != null) mapResult.put("nodeRepresentation",   nodeRepresentation);
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof Classification) {
      String sId = ((Classification) object).getId();
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
    return "Classification(" + id + ")";
  }
}
