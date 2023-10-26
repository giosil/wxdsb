package org.dew.auth;

public 
interface ISAMLAttributeQueryClient 
{
  public SAMLAttributeQueryResponse attributeQuery(String nameID);
}
