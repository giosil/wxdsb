package org.dew.ebxml;

public
interface RIM 
{
  public static final String VERSION_INFO            = "1.1";
  
  public static final String STATUS_APPROVED         = "urn:oasis:names:tc:ebxml-regrep:StatusType:Approved";
  public static final String STATUS_DEPRECATED       = "urn:oasis:names:tc:ebxml-regrep:StatusType:Deprecated";
  public static final String STATUS_SUBMITTED        = "urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted";
  public static final String STATUS_WITHDRAWN        = "urn:oasis:names:tc:ebxml-regrep:StatusType:Withdrawn";
  
  public static final String ASS_TYPE_HAS_MEMBER     = "urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember";
  
  public static final String TYPE_ASSOCIATION        = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Association";
  public static final String TYPE_CLASSIFICATION     = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification";
  public static final String TYPE_EXTERNALIDENTIFIER = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier";
  public static final String TYPE_EXTERNALLINK       = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalLink";
  public static final String TYPE_REGISTRYPACKAGE    = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:RegistryPackage";
}
