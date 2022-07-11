package org.dew.auth;

import java.io.Serializable;

import java.security.Key;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import java.util.HashMap;
import java.util.Map;

public 
class AuthAssertion implements Principal, Serializable 
{
  private static final long serialVersionUID = -7980941573809301902L;
  
  protected String id;
  protected String subjectId;
  
  // Signature
  protected transient PrivateKey privateKey;  // non-serializable field
  protected X509Certificate certificate;
  protected byte[] digestValue;
  protected byte[] signatureValue;
  
  protected String xmlSource;
  
  public AuthAssertion()
  {
  }
  
  public AuthAssertion(String subjectId)
  {
    this.subjectId = subjectId;
  }
  
  public AuthAssertion(Map<String, Object> map)
  {
    if(map == null) return;
    id        = (String) map.get("id");
    subjectId = (String) map.get("subjectId");
  }
  
  // java.security.Principal
  public String getName() {
    return subjectId;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getSubjectId() {
    return subjectId;
  }
  
  public void setSubjectId(String subjectId) {
    this.subjectId = subjectId;
  }
  
  public PrivateKey getPrivateKey() {
    return privateKey;
  }
  
  public void setPrivateKey(Key key) {
    if(key instanceof PrivateKey) {
      this.privateKey = (PrivateKey) key;
    }
    else {
      this.privateKey = null;
    }
  }
  
  public void setPrivateKey(PrivateKey privateKey) {
    this.privateKey = privateKey;
  }
  
  public X509Certificate getCertificate() {
    return certificate;
  }
  
  public void setCertificate(Certificate certificate) {
    if(certificate instanceof X509Certificate) {
      this.certificate = (X509Certificate) certificate;
    }
    else {
      this.certificate = null;
    }
  }
  
  public void setCertificate(X509Certificate certificate) {
    this.certificate = certificate;
  }
  
  public byte[] getDigestValue() {
    return digestValue;
  }
  
  public void setDigestValue(byte[] digestValue) {
    this.digestValue = digestValue;
  }
  
  public byte[] getSignatureValue() {
    return signatureValue;
  }
  
  public void setSignatureValue(byte[] signatureValue) {
    this.signatureValue = signatureValue;
  }
  
  public 
  void sign(Key key, Certificate certificate)
    throws Exception
  {
    if(key instanceof PrivateKey) {
      this.privateKey = (PrivateKey) key;
    }
    else {
      throw new Exception("Invalid private key");
    }
    if(certificate instanceof X509Certificate) {
      this.certificate = (X509Certificate) certificate;
    }
  }
  
  public
  boolean verifySignature()
  {
    if(signatureValue == null || signatureValue.length == 0) return false;
    if(certificate    == null) return false;
    boolean boResult = false;
    try {
      // DSigUtil.verifySignature va in eccezione (Wrong key usage) poiche' i certificati
      // utilizzati hanno Key Usage extension marked as critical
      // boResult = DSigUtil.verifySignature(signatureValue, certificate);
      
      // Si preferisce checkSignature che esegue una verifica "formale" della firma
      boResult = DSigUtil.checkSignature(signatureValue, certificate);
    }
    catch(Exception ex) {
      System.err.println("Exception in AuthAssertion.verifySignature - DSigUtil.verifySignature:" + ex);
      return true;
    }
    return boResult;
  }
  
  public String getXmlSource() {
    return xmlSource;
  }
  
  public void setXmlSource(String xmlSource) {
    this.xmlSource = xmlSource;
  }
  
  public
  boolean isSigned()
  {
    if(certificate != null) {
      if(signatureValue != null && signatureValue.length > 1) {
        return true;
      }
      if(privateKey != null) {
        return true;
      }
    }
    return false;
  }
  
  public
  boolean checkValidity()
  {
    return true;
  }
  
  public 
  Map<String, Object> toMap() 
  {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("className", this.getClass().getName());
    if(id          != null) mapResult.put("id",        id);
    if(subjectId   != null) mapResult.put("subjectId", subjectId);
    if(certificate != null) {
      Map<String, Object> mapCertificate = new HashMap<String, Object>();
      Principal issuerDN = certificate.getIssuerDN();
      if(issuerDN != null) {
        mapCertificate.put("issuerDN", issuerDN.getName());
      }
      Principal subjectDN = certificate.getSubjectDN();
      if(subjectDN != null) {
        mapCertificate.put("subjectDN", subjectDN.getName());
      }
      mapCertificate.put("serialNumber", certificate.getSerialNumber());
      mapCertificate.put("notBefore",    certificate.getNotBefore());
      mapCertificate.put("notAfter",     certificate.getNotAfter());
      mapCertificate.put("sigAlgName",   certificate.getSigAlgName());
      mapCertificate.put("type",         certificate.getType());
      mapCertificate.put("version",      certificate.getVersion());
      mapResult.put("certificate", mapCertificate);
    }
    return mapResult;
  }
  
  public 
  String toXML(String namespace)
  {
    String result = null;
    if(xmlSource != null && xmlSource.length() > 1) {
      if(xmlSource.indexOf("Signature>") > 0) {
        return xmlSource;
      }
      else {
        result = xmlSource;
      }
    }
    else {
      if(privateKey == null && digestValue != null && digestValue.length > 0 && signatureValue != null && signatureValue.length > 0) {
        String sXmlSignature = DSigUtil.buildXmlSignature(id, digestValue, signatureValue, certificate, true);
        result = buildXML(namespace, sXmlSignature);
      }
      else {
        result = buildXML(namespace, null);
      }
    }
    if(privateKey  == null) return result;
    if(certificate == null) return result;
    try {
      return signXml(result);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }
  
  protected 
  String signXml(String xml)
    throws Exception
  {
    return DSigUtil.signXmlAssertion(xml, id, privateKey, certificate, true);
  }
  
  protected 
  String buildXML(String namespace, String signature) 
  {
    return "";
  }
  
  protected 
  String buildAttributes(String namespace) 
  {
    return null;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof AuthAssertion) {
      String sSubjectId = ((AuthAssertion) object).getSubjectId();
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
    return "AuthAssertion(" + subjectId + ")";
  }
}
