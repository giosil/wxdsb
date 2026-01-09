package org.dew.xds;

public
interface IAffinityDomain
{
  public String getClassDisplayName(String code, String defaultValue);
  
  public String getConfidentialityDisplayName(String code, String defaultValue);
  
  public String getFormatDisplayName(String code, String defaultValue);
  
  public String getTypeDisplayName(String code, String defaultValue);
  
  public String getFacilityDisplayName(String code, String defaultValue);
  
  public String getPracticeDisplayName(String code, String defaultValue);
  
  public String getContentTypeDisplayName(String code, String defaultValue);
  
  public String getEventDisplayName(String code, String defaultValue);
  
  public String getEventCodingScheme(String code, String defaultValue);
  
  public String getAdministrativeRequest(String code, String defaultValue);
  
  public String getAdministrativeRequestDisplayName(String code, String defaultValue);
  
  public String getClassByType(String type, String defaultValue);
  
  public String getPracticeByType(String type, String defaultValue);
  
  public String getFacilityByType(String type, String defaultValue);
  
  public String getContentTypeByType(String type, String defaultValue);
  
  public String getFormatByType(String type, String mimeType, String defaultValue);
  
  public String getTemplateIdRoot(String type);
  
  public String getTemplateId(String type);
}
