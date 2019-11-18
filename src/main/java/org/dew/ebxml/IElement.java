package org.dew.ebxml;

import java.util.Map;

public 
interface IElement 
{
	public String getTagName();
	
	public String getAttribute(String name);
	
	public void setAttribute(String name, String value);
	
	public String toXML(String namespace);
	
	public Map<String,Object> toMap();
}
