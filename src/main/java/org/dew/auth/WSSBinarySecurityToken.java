package org.dew.auth;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.dew.ebxml.Utils;
import org.dew.xds.util.Base64Coder;

public 
class WSSBinarySecurityToken extends AuthAssertion 
{
  private static final long serialVersionUID = 8466552441794739980L;
  
  protected Date created;
  protected Date expires;
  
  public WSSBinarySecurityToken()
  {
  }
  
  public WSSBinarySecurityToken(String subjectId)
  {
    this.subjectId = subjectId;
  }
  
  public WSSBinarySecurityToken(Map<String, Object> map)
  {
    super(map);
    if(map == null) return;
    this.created = Utils.toDate(map.get("created"));
    this.expires = Utils.toDate(map.get("expires"));
  }
  
  public String getSubjectId() {
    if(subjectId != null && subjectId.length() > 0) {
      return subjectId;
    }
    if(certificate != null) {
      Principal principal = certificate.getSubjectDN();
      if(principal != null) {
        return principal.getName();
      }
    }
    return null;
  }
  
  // java.security.Principal
  public String getName() {
    if(subjectId != null && subjectId.length() > 0) {
      return subjectId;
    }
    if(certificate != null) {
      Principal principal = certificate.getSubjectDN();
      if(principal != null) {
        return principal.getName();
      }
    }
    return null;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getExpires() {
    return expires;
  }

  public void setExpires(Date expires) {
    this.expires = expires;
  }
  
  public 
  Map<String, Object> toMap() 
  {
    Map<String, Object> mapResult = super.toMap();
    if(created != null) mapResult.put("created", created);
    if(expires != null) mapResult.put("expires", expires);
    return mapResult;
  }
  
  public
  String getSecurityHeader()
  {
    String sXml = toXML("wsse");
    if(sXml == null || sXml.length() < 2) return "";
    String result = "<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">";
    result += sXml;
    result += "</wsse:Security>";
    return result;
  }

  public 
  String toXML(String namespace) 
  {
    if(xmlSource != null && xmlSource.length() > 2) {
      return xmlSource;
    }
    
    if(privateKey  == null || certificate == null) return "";
    
    if(created == null) {
      Calendar cal = Calendar.getInstance();
      created = cal.getTime();
      cal.add(Calendar.MINUTE, 5);
      expires = cal.getTime();
    }
    if(expires == null) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.MINUTE, 5);
      expires = cal.getTime();
    }
    
    if(namespace == null) namespace = "wsse";
    if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    
    String sDataToSign = "<wsu:Timestamp xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"timestamp\">";
    sDataToSign += "<wsu:Created>" + Utils.formatISO8601_Z(created, false) + "</wsu:Created>";
    sDataToSign += "<wsu:Expires>" + Utils.formatISO8601_Z(expires, false) + "</wsu:Expires>";
    sDataToSign += "</wsu:Timestamp>";
    
    String keyInfo = "<" + namespace + "SecurityTokenReference>";
    keyInfo += "<"  + namespace + "Reference URI=\"#cert\" ValueType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3\"/>";
    keyInfo += "</" + namespace + "SecurityTokenReference>";
    
    String sXmlSignature = null;
    try {
      sXmlSignature = DSigUtil.signXmlAssertion(sDataToSign, "timestamp", privateKey, keyInfo, false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    
    StringBuffer sb = new StringBuffer();
    sb.append("<wsu:Timestamp wsu:Id=\"timestamp\">");
    sb.append("<wsu:Created>" + Utils.formatISO8601_Z(created, false) + "</wsu:Created>");
    sb.append("<wsu:Expires>" + Utils.formatISO8601_Z(expires, false) + "</wsu:Expires>");
    sb.append("</wsu:Timestamp>");
    sb.append("<" + namespace + "BinarySecurityToken EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\" ValueType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3\" wsu:Id=\"cert\">");
    String sCertificate = null;
    try{ sCertificate = Base64Coder.encodeLines(certificate.getEncoded()); } catch(Exception ex) {}
    if(sCertificate != null && sCertificate.length() > 0) {
      sb.append(sCertificate);
    }
    sb.append("</" + namespace + "BinarySecurityToken>");
    if(sXmlSignature != null && sXmlSignature.length() > 0) {
      sb.append(sXmlSignature);
    }
    return sb.toString();
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof WSSBinarySecurityToken) {
      String sSubjectId = ((WSSBinarySecurityToken) object).getSubjectId();
      String sThisSubId = getSubjectId();
      return sThisSubId != null && sThisSubId.equals(sSubjectId);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(certificate == null) return 0;
    return certificate.hashCode();
  }
  
  @Override
  public String toString() {
    if(certificate != null) {
      Principal principal = certificate.getSubjectDN();
      if(principal != null) {
        return "WSSBinarySecurityToken(" + principal.getName() + ")";
      }
    }
    return "WSSBinarySecurityToken(" + subjectId + ")";
  }
}
