package org.dew.ebxml;

import java.util.Map;
import java.util.UUID;

public 
class Association extends RegistryObject
{
	private static final long serialVersionUID = -347470138876328680L;

	protected String associationType;
	protected String sourceObject;
	protected String targetObject;
	
	public Association()
	{
		this.objectType = RIM.TYPE_ASSOCIATION;
	}
	
	public Association(String id, String associationType, String sourceObject, String targetObject)
	{
		this.objectType = RIM.TYPE_ASSOCIATION;
		this.id = id;
		this.associationType = associationType;
		this.sourceObject = sourceObject;
		this.targetObject = targetObject;
	}
	
	@SuppressWarnings("rawtypes")
	public Association(Map map)
	{
		super(map);
		
		if(map == null) return;
		
		Object oAssociationType = map.get("associationType");
		if(oAssociationType != null) associationType = oAssociationType.toString();
		Object oSourceObject = map.get("sourceObject");
		if(oSourceObject != null) sourceObject = oSourceObject.toString();
		Object oTargetObject = map.get("targetObject");
		if(oTargetObject != null) targetObject = oTargetObject.toString();

		if(objectType == null || objectType.length() == 0) {
			this.objectType = RIM.TYPE_ASSOCIATION;
		}
	}

	public String getAssociationType() {
		return associationType;
	}

	public void setAssociationType(String associationType) {
		this.associationType = associationType;
	}

	public String getSourceObject() {
		return sourceObject;
	}

	public void setSourceObject(String sourceObject) {
		this.sourceObject = sourceObject;
	}

	public String getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}
	
	@Override
	public String getTagName() {
		return "Association";
	}

	@Override
	public String getAttribute(String name) {
		if(name == null) return null;
		String result = super.getAttribute(name);
		if(result != null) return result;
		if(name.equals("associationType")) {
			return this.associationType;
		}
		else
		if(name.equals("sourceObject")) {
			return this.sourceObject;
		}
		else
		if(name.equals("targetObject")) {
			return this.targetObject;
		}
		return null;
	}
	
	@Override
	public void setAttribute(String name, String value) {
		if(name == null) return;
		super.setAttribute(name, value);
		if(name.equals("associationType")) {
			this.associationType = value;
		}
		else
		if(name.equals("sourceObject")) {
			this.sourceObject = value;
		}
		else
		if(name.equals("targetObject")) {
			this.targetObject = value;
		}
	}

	@Override
	public String toXML(String namespace) {
		if(namespace == null || namespace.length() == 0) {
			namespace = "";
		}
		else 
		if(!namespace.endsWith(":")) {
			namespace += ":";
		}
		if(id == null || id.length() == 0) {
			id = "urn:uuid:" + UUID.randomUUID().toString();
		}
		StringBuffer sb = new StringBuffer(500);
		sb.append("<" + namespace + getTagName());
		if(associationType != null && associationType.length() > 0) {
			sb.append(" associationType=\"" + associationType + "\"");
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
		if(objectType != null && objectType.length() > 0) {
			sb.append(" objectType=\"" + objectType + "\"");
		}
		if(sourceObject != null && sourceObject.length() > 0) {
			sb.append(" sourceObject=\"" + sourceObject + "\"");
		}
		if(status != null && status.length() > 0) {
			sb.append(" status=\"" + status + "\"");
		}
		if(targetObject != null && targetObject.length() > 0) {
			sb.append(" targetObject=\"" + targetObject + "\"");
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
		if(associationType != null) mapResult.put("associationType", associationType);
		if(sourceObject    != null) mapResult.put("sourceObject",    sourceObject);
		if(targetObject    != null) mapResult.put("targetObject",    targetObject);
		return mapResult;
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof Association) {
			String sId = ((Association) object).getId();
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
		return "Association(" + id + ")";
	}
}
