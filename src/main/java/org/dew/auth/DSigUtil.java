package org.dew.auth;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Signature;
import java.security.cert.X509Certificate;

import java.util.Enumeration;

import javax.crypto.Cipher;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;

import org.dew.xds.util.Base64Coder;

public 
class DSigUtil 
{
  private static Provider providerXmlDSig;
  
  public static
  Provider getProviderXmlDSig()
    throws Exception
  {
    if(providerXmlDSig != null) {
      return providerXmlDSig;
    }
    providerXmlDSig = (Provider) Class.forName("org.jcp.xml.dsig.internal.dom.XMLDSigRI").newInstance();
    return providerXmlDSig;
  }
  
  public static
  String signXmlAssertion(String assertion, String sID, PrivateKey privateKey, String keyInfo, boolean boEnveloped)
    throws Exception
  {
    if(assertion  == null || assertion.length() == 0) return "";
    if(privateKey == null) return "";
    if(sID == null) sID = "";
    
    // assertion must already canonicalizated
    String sDigestValue = computeDigestSHA1(assertion);
    
    String sSInfo = "<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></ds:CanonicalizationMethod>";
    sSInfo += "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>";
    sSInfo += "<ds:Reference URI=\"#" + sID + "\">";
    sSInfo += "<ds:Transforms>";
    if(boEnveloped) {
      sSInfo += "<ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></ds:Transform>";
    }
    sSInfo += "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\">";
    sSInfo += "<ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"xs\"></ec:InclusiveNamespaces>";
    sSInfo += "</ds:Transform>";
    sSInfo += "</ds:Transforms>";
    sSInfo += "<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod>";
    sSInfo += "<ds:DigestValue>" + sDigestValue + "</ds:DigestValue>";
    sSInfo += "</ds:Reference>";
    
    // xml-exc-c14n                             ec:InclusiveNamespaces                          PrefixList="xs"
    String sSignedInfoToSign  = "<ds:SignedInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">";
    sSignedInfoToSign += sSInfo;
    sSignedInfoToSign += "</ds:SignedInfo>";
    
    String sSignatureValue = signSHA1withRSA(sSignedInfoToSign, privateKey);
    
    String sSignature = "<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">";
    sSignature += "<ds:SignedInfo>";
    sSignature += sSInfo;
    sSignature += "</ds:SignedInfo>";
    sSignature += "<ds:SignatureValue>";
    sSignature += sSignatureValue;
    sSignature += "</ds:SignatureValue>";
    if(keyInfo != null && keyInfo.length() > 0) {
      sSignature += "<ds:KeyInfo>";
      sSignature += keyInfo;
      sSignature += "</ds:KeyInfo>";
    }
    sSignature += "</ds:Signature>";
    
    if(!boEnveloped) return sSignature;
    
    int iIdxIns = assertion.lastIndexOf("Issuer>");
    if(iIdxIns > 0) {
      iIdxIns = iIdxIns + 7;
    }
    else {
      iIdxIns = assertion.lastIndexOf("</");
      if(iIdxIns < 0) return "";
    }
    return assertion.substring(0,iIdxIns) + sSignature + assertion.substring(iIdxIns);
  }
  
  public static
  String signXmlAssertion(String assertion, String sID, PrivateKey privateKey, X509Certificate x509Certificate, boolean boEnveloped)
    throws Exception
  {
    if(assertion  == null || assertion.length() == 0) return "";
    if(privateKey == null) return "";
    if(sID == null) sID = "";
    
    // assertion must already canonicalizated
    String sDigestValue = computeDigestSHA1(assertion);
    
    String sSInfo = "<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></ds:CanonicalizationMethod>";
    sSInfo += "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>";
    sSInfo += "<ds:Reference URI=\"#" + sID + "\">";
    sSInfo += "<ds:Transforms>";
    if(boEnveloped) {
      sSInfo += "<ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></ds:Transform>";
    }
    sSInfo += "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"xs\"></ec:InclusiveNamespaces></ds:Transform>";
    sSInfo += "</ds:Transforms>";
    sSInfo += "<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod>";
    sSInfo += "<ds:DigestValue>" + sDigestValue + "</ds:DigestValue>";
    sSInfo += "</ds:Reference>";
    
    String sSignedInfoToSign  = "<ds:SignedInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">";
    sSignedInfoToSign += sSInfo;
    sSignedInfoToSign += "</ds:SignedInfo>";
    
    String sSignatureValue = signSHA1withRSA(sSignedInfoToSign, privateKey);
    
    String sSignature = "<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">";
    sSignature += "<ds:SignedInfo>";
    sSignature += sSInfo;
    sSignature += "</ds:SignedInfo>";
    sSignature += "<ds:SignatureValue>";
    sSignature += sSignatureValue;
    sSignature += "</ds:SignatureValue>";
    if(x509Certificate != null) {
      String sCertificate = null;
      try{ sCertificate = Base64Coder.encodeLines(x509Certificate.getEncoded()); } catch(Exception ex) {}
      if(sCertificate != null && sCertificate.length() > 0) {
        sSignature += "<ds:KeyInfo><ds:X509Data><ds:X509Certificate>";
        sSignature += sCertificate;
        sSignature += "</ds:X509Certificate></ds:X509Data></ds:KeyInfo>";
      }
    }
    sSignature += "</ds:Signature>";
    
    if(!boEnveloped) return sSignature;
    
    int iIdxIns = assertion.lastIndexOf("Issuer>");
    if(iIdxIns > 0) {
      iIdxIns = iIdxIns + 7;
    }
    else {
      iIdxIns = assertion.lastIndexOf("</");
      if(iIdxIns < 0) return "";
    }
    return assertion.substring(0,iIdxIns) + sSignature + assertion.substring(iIdxIns);
  }
  
  public static
  String buildXmlSignature(String sID, byte[] digestValue, byte[] signatureValue, X509Certificate x509Certificate, boolean boEnveloped)
  {
    if(digestValue    == null || digestValue.length    == 0) return "";
    if(signatureValue == null || signatureValue.length == 0) return "";
    if(sID == null) sID = "";
    
    String sDigestValue = String.valueOf(Base64Coder.encode(digestValue));
    
    String sSInfo = "<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></ds:CanonicalizationMethod>";
    sSInfo += "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>";
    sSInfo += "<ds:Reference URI=\"#" + sID + "\">";
    sSInfo += "<ds:Transforms>";
    if(boEnveloped) {
      sSInfo += "<ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></ds:Transform>";
    }
    sSInfo += "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"xs\"></ec:InclusiveNamespaces></ds:Transform>";
    sSInfo += "</ds:Transforms>";
    sSInfo += "<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod>";
    sSInfo += "<ds:DigestValue>" + sDigestValue + "</ds:DigestValue>";
    sSInfo += "</ds:Reference>";
    
    String sSignatureValue = String.valueOf(Base64Coder.encode(signatureValue));
    
    String sSignature = "<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">";
    sSignature += "<ds:SignedInfo>";
    sSignature += sSInfo;
    sSignature += "</ds:SignedInfo>";
    sSignature += "<ds:SignatureValue>";
    sSignature += sSignatureValue;
    sSignature += "</ds:SignatureValue>";
    if(x509Certificate != null) {
      String sCertificate = null;
      try{ sCertificate = Base64Coder.encodeLines(x509Certificate.getEncoded()); } catch(Exception ex) {}
      if(sCertificate != null && sCertificate.length() > 0) {
        sSignature += "<ds:KeyInfo><ds:X509Data><ds:X509Certificate>";
        sSignature += sCertificate;
        sSignature += "</ds:X509Certificate></ds:X509Data></ds:KeyInfo>";
      }
    }
    sSignature += "</ds:Signature>";
    return sSignature;
  }
  
  public static
  String computeDigestSHA1(String content)
    throws Exception
  {
    if(content == null || content.length() == 0) return "";
    
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    md.update(content.getBytes());
    return String.valueOf(Base64Coder.encode(md.digest()));
  }
  
  public static
  String signSHA1withRSA(String content, PrivateKey privateKey)
    throws Exception
  {
    if(content == null || content.length() == 0) return "";
    
    Signature signature = Signature.getInstance("SHA1withRSA");
    signature.initSign(privateKey);
    signature.update(content.getBytes());
    byte[] signatureValue = signature.sign();
    return String.valueOf(Base64Coder.encode(signatureValue));
  }
  
  /**
   * Verifica formale della firma.
   * ATTENZIONE: initVerify potrebbe dare l'eccezione Wrong key usage.
   * Talvolta i certificati per firmare le asserzioni hanno "Key Usage extension marked as critical".
   * 
   * @param signatureValue byte[]
   * @param certificate X509Certificate
   * @return boolean
   * @throws Exception
   */
  public static
  boolean verifySignature(byte[] signatureValue, X509Certificate certificate)
    throws Exception
  {
    if(signatureValue == null || signatureValue.length == 0) return false;
    if(certificate    == null) return false;
    
    // ATTENZIONE: octectString contiene il digest dell'elemento firmato.
    // Tuttavia il digestValue presente nelle asserzioni NON E' tale digest.
    // Esso e' piuttosto il digest del riferimento (Reference) presente nel SignedInfo.
    // Il digest estratto dalla firma invece viene calcolato dal SignedInfo.
    // Per questo motivo nella verifica della firma non viene passato il digest
    // (al piu' bisognerebbe passare il contenuto esatto e canonicalizzato del SignedInfo).
    
    Signature signature = Signature.getInstance("SHA1withRSA");
    signature.initVerify(certificate);
    signature.verify(signatureValue);
    return true;
  }
  
  /**
   * Verifica formale della firma.
   * Questo metodo a differenza di verifySignature non controlla il Key Usage del cerficato.
   * 
   * @param signatureValue byte[]
   * @param certificate X509Certificate
   * @return boolean
   * @throws Exception
   */
  @SuppressWarnings("rawtypes")
  public static
  boolean checkSignature(byte[] signatureValue, X509Certificate certificate)
    throws Exception
  {
    if(signatureValue == null || signatureValue.length == 0) return false;
    if(certificate    == null) return false;
    
    // signatureValue = Encryption RSA of (ASN.1 of SHA1 digest)
    
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, certificate);
    byte[] asn1Digest = cipher.doFinal(signatureValue);
    
    // asn1Digest = 
    //     SEQUENCE(2 elem)
    //         SEQUENCE(2 elem)
    //             OBJECT IDENTIFIER 1.3.14.3.2.26   (ovvero SHA1)
    //             NULL
    //         OCTET STRING(20 byte) (ovvero digest)
    
    ASN1InputStream asn1InputStream = new ASN1InputStream(asn1Digest);
    Object derObject = asn1InputStream.readObject();
    asn1InputStream.close();
    
    DEROctetString octectString = null;
    if(derObject instanceof ASN1Sequence) {
      Enumeration enumeration = ((ASN1Sequence) derObject).getObjects();
      while(enumeration.hasMoreElements()) {
        Object element = enumeration.nextElement();
        if(element instanceof DEROctetString) {
          octectString = (DEROctetString) element;
          break;
        }
      }
    }
    
    // ATTENZIONE: octectString contiene il digest dell'elemento firmato.
    // Tuttavia il digestValue presente nelle asserzioni NON E' tale digest.
    // Esso e' piuttosto il digest del riferimento (Reference) presente nel SignedInfo.
    // Il digest estratto dalla firma invece viene calcolato dal SignedInfo.
    // Per questo motivo nella verifica della firma non viene passato il digest
    // (al piu' bisognerebbe passare il contenuto esatto e canonicalizzato del SignedInfo).
    
    return octectString != null && octectString.getOctets().length > 0;
  }
}