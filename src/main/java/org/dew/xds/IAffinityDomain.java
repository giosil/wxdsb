package org.dew.xds;

public
interface IAffinityDomain
{
	public String getClassDisplayName(String code);
	
	public String getConfidentialityDisplayName(String code);
	
	public String getFormatDisplayName(String code);
	
	public String getTypeDisplayName(String code);
	
	public String getFacilityDisplayName(String code);
	
	public String getPracticeDisplayName(String code);
	
	public String getContentTypeDisplayName(String code);
}
