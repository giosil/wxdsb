package org.dew.services;

import java.util.HashMap;
import java.util.Map;

import org.dew.xds.IXDSb;
import org.dew.xds.XDSbTest;

public 
class ServicesFactory 
{
	protected static Map<String,Object> mapInstances = new HashMap<String, Object>();
	
	public static 
	IXDSb getXDSbInstance(String name)
		throws Exception
	{
		if(name == null) name = "";
		Object result = mapInstances.get(name);
		if(result instanceof IXDSb) {
			return (IXDSb) result;
		}
		if(name.length() == 0) {
			result = new XDSbTest();
			mapInstances.put(name, result);
			return (IXDSb) result;
		}
		try {
			result = Class.forName(name).newInstance();
			if(result instanceof IXDSb) {
				mapInstances.put(name, result);
				return (IXDSb) result;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		result = new XDSbTest();
		mapInstances.put(name, result);
		return (IXDSb) result;
	}
	
	public static 
	IXDSb getXDSbProxy(String name)
		throws Exception
	{
		if(name == null) name = "";
		Object result = mapInstances.get(name);
		if(result instanceof IXDSb) {
			return (IXDSb) result;
		}
		if(name.length() == 0) {
			result = new XDSbTest();
			mapInstances.put(name, result);
			return (IXDSb) result;
		}
		try {
			result = Class.forName(name).newInstance();
			if(result instanceof IXDSb) {
				mapInstances.put(name, result);
				return (IXDSb) result;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		result = new XDSbTest();
		mapInstances.put(name, result);
		return (IXDSb) result;
	}
}
