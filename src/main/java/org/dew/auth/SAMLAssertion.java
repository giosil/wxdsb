package org.dew.auth;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dew.ebxml.Utils;

public 
class SAMLAssertion extends AuthAssertion
{
  private static final long serialVersionUID = 852070488042699476L;
  
  public static final String AUTH_CONTEXT_CLASS_X509      = "urn:oasis:names:tc:SAML:2.0:ac:classes:X509";
  public static final String AUTH_CONTEXT_CLASS_PASSWORD  = "urn:oasis:names:tc:SAML:2.0:ac:classes:Password";
  public static final String AUTH_CONTEXT_CLASS_PPT       = "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport";
  public static final String AUTH_CONTEXT_CLASS_KERBEROS  = "urn:oasis:names:tc:SAML:2.0:ac:classes:Kerberos";
  public static final String AUTH_CONTEXT_CLASS_TLSCLIENT = "urn:oasis:names:tc:SAML:2.0:ac:classes:TLSClient";
  public static final String AUTH_CONTEXT_CLASS_SMARTCARD = "urn:oasis:names:tc:SAML:2.0:ac:classes:Smartcard";
  
  protected Date   issueInstant;
  protected String issuer;
  protected Date   notBefore;
  protected Date   notOnOrAfter;
  protected Date   authnInstant;
  protected String authnContextClassRef;
  
  protected Map<String,String> attributes;
  
  public SAMLAssertion()
  {
  }
  
  public SAMLAssertion(String subjectId)
  {
    this.subjectId = subjectId;
  }
  
  @SuppressWarnings("unchecked")
  public SAMLAssertion(Map<String, Object> map)
  {
    super(map);
    if(map == null) return;
    this.issueInstant = Utils.toDate(map.get("issueInstant"));
    this.issuer       = (String) map.get("issuer");
    this.notBefore    = Utils.toDate(map.get("notBefore"));
    this.notOnOrAfter = Utils.toDate(map.get("notOnOrAfter"));
    this.authnInstant = Utils.toDate(map.get("authnInstant"));
    this.authnContextClassRef = (String) map.get("authnContextClassRef");
    Object oAttributes = map.get("attributes");
    if(oAttributes instanceof Map) {
      this.attributes = (Map<String, String>) oAttributes;
    }
  }
  
  public Date getIssueInstant() {
    return issueInstant;
  }
  
  public void setIssueInstant(Date issueInstant) {
    this.issueInstant = issueInstant;
  }
  
  public String getIssuer() {
    return issuer;
  }
  
  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }
  
  public Date getNotBefore() {
    return notBefore;
  }
  
  public void setNotBefore(Date notBefore) {
    this.notBefore = notBefore;
  }
  
  public Date getNotOnOrAfter() {
    return notOnOrAfter;
  }
  
  public void setNotOnOrAfter(Date notOnOrAfter) {
    this.notOnOrAfter = notOnOrAfter;
  }
  
  public Date getAuthnInstant() {
    return authnInstant;
  }
  
  public void setAuthnInstant(Date authnInstant) {
    this.authnInstant = authnInstant;
  }
  
  public String getAuthnContextClassRef() {
    return authnContextClassRef;
  }
  
  public void setAuthnContextClassRef(String authnContextClassRef) {
    this.authnContextClassRef = authnContextClassRef;
  }
  
  public Map<String, String> getAttributes() {
    return attributes;
  }
  
  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }
  
  public void setAttribute(String name, String value) {
    if(name == null || name.length() == 0) return;
    if(this.attributes == null) this.attributes = new HashMap<String, String>();
    this.attributes.put(name, value);
  }
  
  public String getAttribute(String name) {
    if(name == null || name.length() == 0) return null;
    if(this.attributes == null) return null;
    return this.attributes.get(name);
  }
  
  public String removeAttribute(String name) {
    if(name == null || name.length() == 0) return null;
    if(this.attributes == null) return null;
    return this.attributes.remove(name);
  }
  
  public 
  int checkAttributes() 
  {
    if(attributes == null) return 0;
    return attributes.size();
  }
  
  @Override
  public 
  boolean checkValidity() 
  {
    long currentTime   = System.currentTimeMillis();
    long lNotBefore    = this.notBefore    != null ? this.notBefore.getTime()    : currentTime;
    long lNotOnOrAfter = this.notOnOrAfter != null ? this.notOnOrAfter.getTime() : currentTime + 30000;
    lNotBefore = lNotBefore - 30000;
    if(currentTime < lNotBefore)    return false;
    if(currentTime > lNotOnOrAfter) return false;
    return true;
  }
  
  public 
  Map<String, Object> toMap() 
  {
    Map<String, Object> mapResult = super.toMap();
    if(issueInstant != null) mapResult.put("issueInstant", issueInstant);
    if(issuer       != null) mapResult.put("issuer",       issuer);
    if(notBefore    != null) mapResult.put("notBefore",    notBefore);
    if(notOnOrAfter != null) mapResult.put("notOnOrAfter", notOnOrAfter);
    if(authnInstant != null) mapResult.put("authnInstant", authnInstant);
    if(authnContextClassRef != null) mapResult.put("authnContextClassRef", authnContextClassRef);
    if(attributes   != null) mapResult.put("attributes",   attributes);
    return mapResult;
  }
  
  protected 
  String signXml(String xml)
    throws Exception
  {
    return SAMLUtil.signXmlAssertion(xml, privateKey, certificate);
    // return DSigUtil.signXmlAssertion(xml, id, privateKey, certificate, true);
  }
  
  protected 
  String buildXML(String namespace, String signature) 
  {
    Calendar calNotBefore = Calendar.getInstance();
    if(notBefore != null) {
      calNotBefore.setTimeInMillis(notBefore.getTime());
    }
    else {
      notBefore = calNotBefore.getTime();
    }
    Calendar calNotOnOrAfter = Calendar.getInstance();
    if(notOnOrAfter != null) {
      calNotOnOrAfter.setTimeInMillis(notOnOrAfter.getTime());
    }
    else {
      if(notBefore != null) {
        calNotOnOrAfter.setTimeInMillis(notBefore.getTime());
      }
      calNotOnOrAfter.add(Calendar.DATE, 1);
      notOnOrAfter = calNotOnOrAfter.getTime();
    }
    
    if(id == null || id.length() == 0) {
      StringBuffer sbID = new StringBuffer(33);
      sbID.append('_');
      String ALFABETO = "0123456789abcdef";
      for(int i = 0; i < 32; i++) {
        int index = (int) (16 * Math.random());
        sbID.append(ALFABETO.charAt(index));
      }
      id = sbID.toString();
    }
    
    String sDecNs = " xmlns=\"urn:oasis:names:tc:SAML:2.0:assertion\"";
    if(namespace == null) namespace = "saml2";
    if(!namespace.endsWith(":")) {
      sDecNs = " xmlns:" + namespace + "=\"urn:oasis:names:tc:SAML:2.0:assertion\"";
      namespace += ":";
    }
    else {
      sDecNs = "";
    }
    Calendar calIssueInstant = null;
    if(issueInstant != null) {
      calIssueInstant = Calendar.getInstance();
      calIssueInstant.setTimeInMillis(issueInstant.getTime());
    }
    else {
      calIssueInstant = calNotBefore;
    }
    
    StringBuilder sb = new StringBuilder(520);
    sb.append("<" + namespace + "Assertion");
    sb.append(sDecNs);
    sb.append(" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"");
    sb.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
    sb.append(" ID=\"" + id + "\"");
    sb.append(" IssueInstant=\"" + Utils.formatISO8601_Z(calIssueInstant) + "\"");
    sb.append(" Version=\"2.0\"");
    sb.append(" xsi:schemaLocation=\"urn:oasis:names:tc:SAML:2.0:assertion saml-schema-assertion-2.0.xsd\"");
    sb.append(">");
    if(issuer != null && issuer.length() > 0) {
      sb.append("<" + namespace + "Issuer>" + Utils.normalizeString(issuer) + "</" + namespace + "Issuer>");
    }
    if(signature != null && signature.length() > 0) {
      sb.append(signature);
    }
    sb.append("<"  + namespace + "Subject>");
    sb.append("<"  + namespace + "NameID>" + Utils.normalizeString(subjectId) + "</" + namespace + "NameID>");
    sb.append("</" + namespace + "Subject>");
    sb.append("<"  + namespace + "Conditions NotBefore=\"" + Utils.formatISO8601_Z(calNotBefore) + "\" NotOnOrAfter=\"" + Utils.formatISO8601_Z(calNotOnOrAfter) + "\"></" + namespace + "Conditions>");
    if(authnContextClassRef != null && authnContextClassRef.length() > 0) {
      Calendar calAuthnInstant = Calendar.getInstance();
      if(authnInstant != null) calAuthnInstant.setTimeInMillis(authnInstant.getTime());
      sb.append("<"  + namespace + "AuthnStatement AuthnInstant=\"" + Utils.formatISO8601_Z(calAuthnInstant) + "\">");
      sb.append("<"  + namespace + "AuthnContext>");
      sb.append("<"  + namespace + "AuthnContextClassRef>" + authnContextClassRef + "</saml2:AuthnContextClassRef>");
      sb.append("</" + namespace + "AuthnContext>");
      sb.append("</" + namespace + "AuthnStatement>");
    }
    String sAttributes = buildAttributes(namespace);
    if(sAttributes != null && sAttributes.length() > 0) {
      sb.append("<" + namespace + "AttributeStatement>");
      sb.append(sAttributes);
      sb.append("</" + namespace + "AttributeStatement>");
    }
    sb.append("</" + namespace + "Assertion>");
    return sb.toString();
  }
  
  protected 
  String buildAttributes(String namespace) 
  {
    return null;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof SAMLAssertion) {
      String sSubjectId = ((SAMLAssertion) object).getSubjectId();
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
    return "SAMLAssertion(" + subjectId + ")";
  }
}
