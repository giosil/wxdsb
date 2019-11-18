package org.dew.ebxml;

import java.util.Map;

public 
class ObjectRef extends Identifiable
{
	private static final long serialVersionUID = -9116183709315025713L;
	
	protected boolean createReplica;
	
	public ObjectRef()
	{
	}
	
	public ObjectRef(String id)
	{
		this.id = id;
	}
	
	@SuppressWarnings("rawtypes")
	public ObjectRef(Map map)
	{
		super(map);
	}
	
	public boolean isCreateReplica() {
		return createReplica;
	}
	
	public void setCreateReplica(boolean createReplica) {
		this.createReplica = createReplica;
	}
	
	@Override
	public String getTagName() {
		return "ObjectRef";
	}
	
	@Override
	public String getAttribute(String name) {
		if(name == null) return null;
		String result = super.getAttribute(name);
		if(result != null) return result;
		if(name.equals("createReplica")) {
			return String.valueOf(this.createReplica);
		}
		return null;
	}
	
	@Override
	public void setAttribute(String name, String value) {
		if(name == null) return;
		super.setAttribute(name, value);
		if(name.equals("createReplica")) {
			if(value != null && value.length() > 0) {
				this.createReplica = "tysTYS1".indexOf(value.charAt(0)) >= 0;
			}
			else {
				this.createReplica = false;
			}
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
			return "";
		}
		StringBuffer sb = new StringBuffer(90);
		sb.append("<" + namespace + getTagName());
		if(createReplica) {
			sb.append(" createReplica=\"" + createReplica + "\"");
		}
		if(home != null && home.length() > 0) {
			sb.append(" home=\"" + home + "\"");
		}
		if(id != null && id.length() > 0) {
			sb.append(" id=\"" + id + "\"");
		}
		sb.append(">");
		if(slots != null) {
			for(Slot slot : slots) {
				sb.append(slot.toXML(namespace));
			}
		}
		sb.append("</" + namespace + getTagName() + ">");
		return sb.toString();
	}
	
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> mapResult = super.toMap();
		mapResult.put("tagName",       getTagName());
		mapResult.put("createReplica", createReplica);
		return mapResult;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof ObjectRef) {
			String sId = ((ObjectRef) object).getId();
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
		return "ObjectRef(" + id + ")";
	}
}
