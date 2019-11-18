package org.dew.ebxml;

import java.util.Map;
import java.util.UUID;

public 
class ExternalLink extends RegistryObject
{
	private static final long serialVersionUID = -8934565303636001908L;

	protected String externalURI;
	
	public ExternalLink()
	{
		this.objectType = RIM.TYPE_EXTERNALLINK;
	}
	
	public ExternalLink(String id, String externalURI)
	{
		this.objectType = RIM.TYPE_EXTERNALLINK;
		this.id = id;
		this.externalURI = externalURI;
	}
	
	@SuppressWarnings("rawtypes")
	public ExternalLink(Map map)
	{
		super(map);
		
		if(map == null) return;
		
		Object oExternalURI = map.get("externalURI");
		if(oExternalURI != null) externalURI = oExternalURI.toString();
		
		if(objectType == null || objectType.length() == 0) {
			this.objectType = RIM.TYPE_EXTERNALLINK;
		}
	}
	
	public String getExternalURI() {
		return externalURI;
	}
	
	public void setExternalURI(String externalURI) {
		this.externalURI = externalURI;
	}
	
	@Override
	public String getTagName() {
		return "ExternalLink";
	}
	
	@Override
	public String getAttribute(String name) {
		if(name == null) return null;
		String result = super.getAttribute(name);
		if(result != null) return result;
		if(name.equals("externalURI")) {
			return this.externalURI;
		}
		return null;
	}
	
	@Override
	public void setAttribute(String name, String value) {
		if(name == null) return;
		super.setAttribute(name, value);
		if(name.equals("externalURI")) {
			this.externalURI = value;
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
		if(externalURI != null && externalURI.length() > 0) {
			sb.append(" externalURI=\"" + externalURI + "\"");
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
		if(externalURI != null) mapResult.put("externalURI", externalURI);
		return mapResult;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof ExternalLink) {
			String sId = ((ExternalLink) object).getId();
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
		return "ExternalLink(" + id + ")";
	}
}
