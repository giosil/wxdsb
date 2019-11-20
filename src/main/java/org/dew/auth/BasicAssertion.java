package org.dew.auth;

import java.util.Map;

public 
class BasicAssertion extends AuthAssertion 
{
  private static final long serialVersionUID = 3816062789034041965L;
  
  protected String password;
  
  public BasicAssertion()
  {
  }
  
  public BasicAssertion(String subjectId)
  {
    this.subjectId = subjectId;
  }
  
  public BasicAssertion(Map<String, Object> map)
  {
    super(map);
    if(map == null) return;
    this.password = (String) map.get("password");
  }
  
  public BasicAssertion(String subjectId, String password)
  {
    this.subjectId = subjectId;
    this.password  = password;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public 
  String toXML(String namespace) 
  {
    return "";
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof BasicAssertion) {
      String sSubjectId = ((BasicAssertion) object).getSubjectId();
      if(subjectId == null && sSubjectId == null) return true;
      return subjectId != null && subjectId.equals(sSubjectId);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(subjectId == null) return 0;
    return subjectId.hashCode();
  }
  
  @Override
  public String toString() {
    return "BasicAssertion(" + subjectId + ")";
  }
}
