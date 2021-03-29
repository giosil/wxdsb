package org.dew.xds;

public 
class AffinityDomainFactory 
{
  private static IAffinityDomain defaultInstance;
  
  public static
  IAffinityDomain getDefaultInstance() 
  {
    if(defaultInstance == null) {
      defaultInstance = new AffinityDomainIT();
    }
    
    return defaultInstance;
  }
  
  public static
  IAffinityDomain getInstance(String locale) 
  {
    if(locale != null && locale.equalsIgnoreCase("it")) {
      return new AffinityDomainIT();
    }
    
    return getDefaultInstance();
  }
  
  public static
  void setDefaultInstance(IAffinityDomain affinityDomain) 
  {
    defaultInstance = affinityDomain;
  }
}
