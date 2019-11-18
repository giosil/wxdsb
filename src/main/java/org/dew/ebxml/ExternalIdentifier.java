package org.dew.ebxml;

import java.util.Map;
import java.util.UUID;

public 
class ExternalIdentifier extends RegistryObject
{
	private static final long serialVersionUID = 1683899571388745276L;

	protected String identificationScheme;
	protected String registryObject;
	protected String value;
	
	public ExternalIdentifier()
	{
		this.objectType = RIM.TYPE_EXTERNALIDENTIFIER;
	}
	
	public ExternalIdentifier(String identificationScheme, String registryObject)
	{
		this.objectType = RIM.TYPE_EXTERNALIDENTIFIER;
		this.identificationScheme = identificationScheme;
		this.registryObject = registryObject;
	}
	
	public ExternalIdentifier(String identificationScheme, String registryObject, String value)
	{
		this.objectType = RIM.TYPE_EXTERNALIDENTIFIER;
		this.identificationScheme = identificationScheme;
		this.registryObject = registryObject;
		this.value = value;
	}
	
	public ExternalIdentifier(ExternalIdentifier externalIdentifier)
	{
		super((RegistryObject) externalIdentifier);
		this.objectType = RIM.TYPE_EXTERNALIDENTIFIER;
		if(externalIdentifier == null) return;
		this.identificationScheme = externalIdentifier.getIdentificationScheme();
		this.registryObject       = externalIdentifier.getRegistryObject();
		this.value                = externalIdentifier.getValue();
	}
	
	@SuppressWarnings("rawtypes")
	public ExternalIdentifier(Map map)
	{
		super(map);
		
		if(map == null) return;
		
		Object oIdentificationScheme = map.get("identificationScheme");
		if(oIdentificationScheme != null) identificationScheme = oIdentificationScheme.toString();
		Object oRegistryObject = map.get("registryObject");
		if(oRegistryObject != null) registryObject = oRegistryObject.toString();
		Object oValue = map.get("value");
		if(oValue != null) value = Utils.toString(oValue, "");

		if(objectType == null || objectType.length() == 0) {
			this.objectType = RIM.TYPE_EXTERNALIDENTIFIER;
		}
	}

	public String getIdentificationScheme() {
		return identificationScheme;
	}

	public void setIdentificationScheme(String identificationScheme) {
		this.identificationScheme = identificationScheme;
	}

	public String getRegistryObject() {
		return registryObject;
	}

	public void setRegistryObject(String registryObject) {
		this.registryObject = registryObject;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String getTagName() {
		return "ExternalIdentifier";
	}

	@Override
	public String getAttribute(String name) {
		if(name == null) return null;
		String result = super.getAttribute(name);
		if(result != null) return result;
		if(name.equals("identificationScheme")) {
			return this.identificationScheme;
		}
		else
		if(name.equals("registryObject")) {
			return this.registryObject;
		}
		else
		if(name.equals("value")) {
			return this.value;
		}
		return null;
	}
	
	@Override
	public void setAttribute(String name, String value) {
		if(name == null) return;
		super.setAttribute(name, value);
		if(name.equals("identificationScheme")) {
			this.identificationScheme = value;
		}
		else
		if(name.equals("registryObject")) {
			this.registryObject = value;
		}
		else
		if(name.equals("value")) {
			this.value = value;
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
		if(home != null && home.length() > 0) {
			sb.append(" home=\"" + home + "\"");
		}
		if(id != null && id.length() > 0) {
			sb.append(" id=\"" + id + "\"");
		}
		if(identificationScheme != null && identificationScheme.length() > 0) {
			sb.append(" identificationScheme=\"" + identificationScheme + "\"");
		}
		if(lid != null && lid.length() > 0) {
			sb.append(" lid=\"" + lid + "\"");
		}		
		if(objectType != null && objectType.length() > 0) {
			sb.append(" objectType=\"" + objectType + "\"");
		}
		if(registryObject != null && registryObject.length() > 0) {
			sb.append(" registryObject=\"" + registryObject + "\"");
		}
		if(status != null && status.length() > 0) {
			sb.append(" status=\"" + status + "\"");
		}
		if(value != null) {
			// value can be empty
			sb.append(" value=\"" + Utils.normalizeString(value) + "\"");
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
		if(identificationScheme != null) mapResult.put("identificationScheme", identificationScheme);
		if(registryObject       != null) mapResult.put("registryObject",       registryObject);
		if(value                != null) mapResult.put("value",                value);
		return mapResult;
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof ExternalIdentifier) {
			String sId = ((ExternalIdentifier) object).getId();
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
		return "ExternalIdentifier(" + id + ")";
	}
}
