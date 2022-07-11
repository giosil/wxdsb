package org.dew.xds;

public
interface IAffinityDomain
{
  public String getClassDisplayName(String code);
  
  public String getConfidentialityDisplayName(String code);
  
  public String getFormatDisplayName(String code);
  
  public String getTypeDisplayName(String code);
  
  public String getFacilityDisplayName(String code);
  
  public boolean checkHealthcareFacilityTypeCode(String code);
  
  public String getPracticeDisplayName(String code);
  
  public String getContentTypeDisplayName(String code);
  
  public String getClassByType(String type, String defaultValue);
  
  public String getPracticeByType(String type, String defaultValue);
  
  public String getFacilityByType(String type, String defaultValue);
  
  public String getContentTypeByType(String type, String defaultValue);
  
  public String getFormatByType(String type, String mimeType, String defaultValue);
  
  public String getTemplateIdRoot(String type);
  
  public String getTemplateId(String type);
}
