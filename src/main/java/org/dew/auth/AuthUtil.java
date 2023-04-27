package org.dew.auth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

import org.dew.xds.util.Base64Coder;

public 
class AuthUtil 
{
  public static String CFG_FOLDER = System.getProperty("user.home") + File.separator + "cfg";
  
  public static
  X509Certificate getX509Certificate(String base64)
  {
    if(base64 == null || base64.length() < 2) return null;
    byte[] abX509Certificate = null;
    try {
      abX509Certificate = Base64Coder.decodeLines(base64);
      if(abX509Certificate == null || abX509Certificate.length < 2) {
        return null;
      }
    }
    catch(Throwable th) {
      System.err.println("getX509Certificate 0: " + th);
      return null;
    }
    X509Certificate x509certificate = null;
    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(abX509Certificate);
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      x509certificate = (X509Certificate) cf.generateCertificate(bais);
    }
    catch(Throwable th) {
      System.err.println("getX509Certificate 1: " + th);
    }
    return x509certificate;
  }
  
  public static
  String getTaxCode(X509Certificate x509certificate)
  {
    if(x509certificate == null) return null;
    Principal subjectDN   = x509certificate.getSubjectDN();
    String sSubjectDNName = subjectDN.getName();
    String sKey = "SERIALNUMBER";
    int iBegin = sSubjectDNName.indexOf(sKey + "=");
    if(iBegin >= 0) {
      int iEnd = sSubjectDNName.indexOf(',', iBegin);
      if(iEnd < 0) iEnd = sSubjectDNName.length();
      iBegin += sKey.length() + 1;
      String sSerialNumber = sSubjectDNName.substring(iBegin, iEnd);
      int iSep = sSerialNumber.indexOf(':');
      if(iSep >= 0) {
        return sSerialNumber.substring(iSep + 1);
      }
      else {
        return sSerialNumber;
      }
    }
    else {
      sKey = "CN";
      iBegin = sSubjectDNName.indexOf(sKey + "=");
      if(iBegin >= 0) {
        int iEnd = sSubjectDNName.indexOf(',', iBegin);
        if(iEnd < 0) iEnd = sSubjectDNName.length();
        iBegin += sKey.length() + 1;
        String sCN = sSubjectDNName.substring(iBegin, iEnd);
        if(sCN.startsWith("\"")) {
          sCN = sCN.substring(1);
        }
        if(sCN.length() > 16) {
          sCN = sCN.substring(0, 16);
        }
        return sCN;
      }
    }
    return null;
  }
  
  public static
  String loadTextFile(String sFile)
  {
    try {
      int iFileSep = sFile.indexOf('/');
      if(iFileSep < 0) iFileSep = sFile.indexOf('\\');
      InputStream is = null;
      if(iFileSep < 0) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(sFile);
        if(url == null) return null;
        is = url.openStream();
      }
      else {
        is = new FileInputStream(sFile);
      }
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try {
        int n;
        byte[] buff = new byte[1024];
        while((n = is.read(buff)) > 0) baos.write(buff, 0, n);
      }
      finally {
        if(is != null) try{ is.close(); } catch(Exception ex) {}
      }
      return new String(baos.toByteArray());
    }
    catch(Exception ex) {
      System.err.println("Exception during load " + sFile + ": + " + ex.getMessage());
    }
    return null;
  }
  
  public static
  byte[] readFile(String sFile)
    throws Exception
  {
    int iFileSep = sFile.indexOf('/');
    if(iFileSep < 0) iFileSep = sFile.indexOf('\\');
    InputStream is = null;
    if(iFileSep < 0) {
      URL url = Thread.currentThread().getContextClassLoader().getResource(sFile);
      is = url.openStream();
    }
    else {
      is = new FileInputStream(sFile);
    }
    try {
      int n;
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buff = new byte[1024];
      while((n = is.read(buff)) > 0) baos.write(buff, 0, n);
      return baos.toByteArray();
    }
    finally {
      if(is != null) try{ is.close(); } catch(Exception ex) {}
    }
  }
  
  public static
  X509Certificate loadCertificate(String sFile)
    throws Exception
  {
    InputStream is = null;
    // Si cerca prima nella cartella di configurazione
    File file = new File(CFG_FOLDER + File.separator + sFile);
    if(file.exists()) {
      is = new FileInputStream(file);
    }
    else {
      URL url = null;
      try {
        // Poi si cerca la versione ext
        url = Thread.currentThread().getContextClassLoader().getResource("ext_" + sFile);
      }
      catch(Exception ex) {
      }
      if(url == null) {
        try {
          // Infine si cerca all'interno del pacchetto
          url = Thread.currentThread().getContextClassLoader().getResource(sFile);
        }
        catch(Exception ex) {
        }
      }
      if(url != null) {
        is = url.openStream();
      }
      else {
        return null;
      }
    }
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      int n;
      byte[] buff = new byte[1024];
      while((n = is.read(buff)) > 0) baos.write(buff, 0, n);
    }
    finally {
      if(is != null) try{ is.close(); } catch(Exception ex) {}
    }
    byte[] content = baos.toByteArray();
    if(content == null || content.length < 4) {
      throw new Exception("Invalid file");
    }
    if(content[0] == 45 && content[1] == 45 && content[2] == 45) {
      String sContent = new String(content);
      int iStart = sContent.indexOf("ATE-----");
      if(iStart > 0) {
        int iEnd = sContent.indexOf("-----END");
        if(iEnd > 0) {
          String sBase64 = sContent.substring(iStart+8, iEnd).trim();
          content = Base64Coder.decodeLines(sBase64);
        }
      }
    }
    ByteArrayInputStream bais = new ByteArrayInputStream(content);
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    return (X509Certificate) cf.generateCertificate(bais);
  }
  
  public static
  PrivateKey loadPrivateKey(String sFile)
    throws Exception
  {
    InputStream is = null;
    // Si cerca prima nella cartella di configurazione
    File file = new File(CFG_FOLDER + File.separator + sFile);
    if(file.exists()) {
      is = new FileInputStream(file);
    }
    else {
      URL url = null;
      try {
        // Poi si cerca la versione ext
        url = Thread.currentThread().getContextClassLoader().getResource("ext_" + sFile);
      }
      catch(Exception ex) {
      }
      if(url == null) {
        try {
          // Infine si cerca all'interno del pacchetto
          url = Thread.currentThread().getContextClassLoader().getResource(sFile);
        }
        catch(Exception ex) {
        }
      }
      if(url != null) {
        is = url.openStream();
      }
      else {
        return null;
      }
    }
    
    PEMReader pemReader = null;
    try {
      Security.addProvider(new BouncyCastleProvider());
      
      pemReader = new PEMReader(new InputStreamReader(is));
      
      Object pemObject = pemReader.readObject();
      if(pemObject instanceof KeyPair) {
        return ((KeyPair) pemObject).getPrivate();
      }
      else if(pemObject instanceof PrivateKey) {
        return (PrivateKey) pemObject;
      }
      
      throw new Exception("Invalid pem file " + sFile);
    }
    finally {
      if(is != null) try{ is.close(); } catch(Exception ex) {}
      if(pemReader != null) try{ pemReader.close(); } catch(Exception ex) {}
    }
  }
  
  public static
  SSLSocketFactory getSSLSocketFactoryMutualAuth(String keystoreFile, String password)
    throws Exception
  {
    return getSSLSocketFactoryMutualAuth(keystoreFile, password, password);
  }
  
  public static
  SSLSocketFactory getSSLSocketFactoryMutualAuth(String keystoreFile, String password, String keyPassword)
    throws Exception
  {
    InputStream is = null;
    // Si cerca prima nella cartella di configurazione
    File file = new File(CFG_FOLDER + File.separator + keystoreFile);
    if(file.exists()) {
      is = new FileInputStream(file);
    }
    else {
      URL url = null;
      try {
        // Poi si cerca la versione ext
        url = Thread.currentThread().getContextClassLoader().getResource("ext_" + keystoreFile);
      }
      catch(Exception ex) {
      }
      if(url == null) {
        try {
          // Infine si cerca all'interno del pacchetto
          url = Thread.currentThread().getContextClassLoader().getResource(keystoreFile);
        }
        catch(Exception ex) {
        }
      }
      if(url != null) {
        is = url.openStream();
      }
      else {
        return null;
      }
    }
    
    Security.addProvider(new BouncyCastleProvider());
    
    KeyStore keystore = null;
    if(keystoreFile.endsWith(".p12")) {
      keystore = KeyStore.getInstance("PKCS12", "BC");
    }
    else {
      keystore = KeyStore.getInstance("JKS");
    }
    
    keystore.load(is, password != null ? password.toCharArray() : new char[0]);
    
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
    keyManagerFactory.init(keystore, keyPassword != null ? keyPassword.toCharArray() : new char[0]);
    KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
    
    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(keyManagers, null, new SecureRandom());
    
    return sslContext.getSocketFactory();
  }
  
  public static 
  String formatISO8601(Calendar cal, boolean offset) 
  {
    if(cal == null) return "";
    
    int iZoneOffset = cal.get(Calendar.ZONE_OFFSET);
    int iDST_Offset = cal.get(Calendar.DST_OFFSET);
    int iTot_Offset = iZoneOffset + iDST_Offset;
    String sOSSign  = "+";
    if(iTot_Offset < 0) {
      iTot_Offset = iTot_Offset * -1;
      sOSSign = "-";
    }
    int iOffset_HH = iTot_Offset / 3600000;
    int iOffset_MM = iTot_Offset % 3600000;
    String sOSHH   = iOffset_HH < 10 ? "0" + iOffset_HH : String.valueOf(iOffset_HH);
    String sOSMM   = iOffset_MM < 10 ? "0" + iOffset_MM : String.valueOf(iOffset_MM);
    
    int iYear   = cal.get(Calendar.YEAR);
    int iMonth  = cal.get(Calendar.MONTH) + 1;
    int iDay    = cal.get(Calendar.DATE);
    int iHour   = cal.get(Calendar.HOUR_OF_DAY);
    int iMin    = cal.get(Calendar.MINUTE);
    int iSec    = cal.get(Calendar.SECOND);
    int iMill   = cal.get(Calendar.MILLISECOND);
    
    String sYear   = String.valueOf(iYear);
    String sMonth  = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
    String sDay    = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
    String sHour   = iHour  < 10 ? "0" + iHour  : String.valueOf(iHour);
    String sMin    = iMin   < 10 ? "0" + iMin   : String.valueOf(iMin);
    String sSec    = iSec   < 10 ? "0" + iSec   : String.valueOf(iSec);
    String sMill   = String.valueOf(iMill);
    if(iYear < 10) {
      sYear = "000" + sYear;
    }
    else if(iYear < 100) {
      sYear = "00" + sYear;
    }
    else if(iYear < 1000) {
      sYear = "0" + sYear;
    }
    if(iMill < 10) {
      sMill = "00" + sMill; 
    }
    else if(iMill < 100) {
      sMill = "0" + sMill; 
    }
    if(offset) {
      return sYear + "-" + sMonth + "-" + sDay + "T" + sHour + ":" + sMin + ":" + sSec + "." + sMill + sOSSign + sOSHH + ":" + sOSMM;
    }
    return sYear + "-" + sMonth + "-" + sDay + "T" + sHour + ":" + sMin + ":" + sSec + "." + sMill;
  }
  
  public static
  String extractTag(byte[] xml, String tagName)
  {
    List<String> listResult = extractTags(xml, tagName);
    if(listResult == null || listResult.size() == 0) {
      return null;
    }
    return listResult.get(0);
  }
  
  public static
  String extractTag(byte[] xml, String tagName, String textContained)
  {
    List<String> listResult = extractTags(xml, tagName, textContained);
    if(listResult == null || listResult.size() == 0) {
      return null;
    }
    return listResult.get(0);
  }
  
  public static
  List<String> extractTags(byte[] xml, String tagName)
  {
    return extractTags(xml, tagName, null);
  }
  
  public static
  List<String> extractTags(byte[] xml, String tagName, String textContained)
  {
    if(xml == null || xml.length < 5) return new ArrayList<String>(0);
    List<String> result = new ArrayList<String>();
    try {
      boolean boClosingTag = false;
      int iOpeningTag = -1;
      int iClosingTag = -1;
      int iStartTag   = -1;
      String sTagName = null;
      for(int i = 0; i < xml.length; i++) {
        byte b = xml[i];
        if(b == '<' && i < xml.length - 1) {
          iStartTag = i;
          byte b1 = xml[i + 1];
          if(Character.isLetter((char) b1)) {
            boClosingTag = false;
          }
          if(b1 == '/') {
            boClosingTag = true;
          }
        }
        else if(b == '>') {
          if(iStartTag >= 0) {
            byte[] tag  = Arrays.copyOfRange(xml, iStartTag, i+1);
            String sTag = new String(tag);
            if(sTag.length() > 2) {
              sTagName = sTag.substring(1, sTag.length()-1);
            }
            else {
              sTagName = "";
            }
            int iSep = sTagName.indexOf(' ');
            if(iSep > 0) {
              sTagName = sTagName.substring(0,iSep);
            }
            iSep = sTagName.indexOf(':');
            if(iSep > 0) {
              sTagName = sTagName.substring(iSep+1);
            }
            if(sTagName.equalsIgnoreCase(tagName)) {
              if(boClosingTag) {
                iClosingTag = iStartTag + tag.length;
                byte[] extract = Arrays.copyOfRange(xml, iOpeningTag, iClosingTag);
                
                String sExtract = new String(extract);
                if(textContained != null && textContained.length() > 0) {
                  if(sExtract.indexOf(textContained) >= 0) {
                    result.add(sExtract);
                  }
                }
                else {
                  result.add(sExtract);
                }
              }
              else {
                iOpeningTag = iStartTag;
              }
            }
          }
          iStartTag = -1;
        }
      }
    }
    catch(Throwable th) {
      th.printStackTrace();
    }
    return result;
  }
  
  public static
  String getUserId(AuthAssertion[] arrayOfAssertion) 
  {
    if(arrayOfAssertion == null || arrayOfAssertion.length == 0) return null;
    AuthAssertion assertion0 = arrayOfAssertion[0];
    if(assertion0 == null) return null;
    
    String prefix = "";
    if(assertion0 instanceof SAMLAttributeAssertion) {
      String sOrgId = ((SAMLAttributeAssertion) assertion0).getOrganizationId();
      if(sOrgId == null || sOrgId.length() == 0) {
        sOrgId = ((SAMLAttributeAssertion) assertion0).getIssuer();
      }
      if(sOrgId != null && sOrgId.length() > 0) {
        prefix = sOrgId + "_";
      }
    }
    else if(assertion0 instanceof SAMLAssertion) {
      String sIssuer = ((SAMLAssertion) assertion0).getIssuer();
      if(sIssuer != null && sIssuer.length() > 0) {
        prefix = sIssuer + "_";
      }
    }
    String sResult = prefix + assertion0.getSubjectId();
    int iSep = sResult.indexOf('^');
    if(iSep > 0) sResult = sResult.substring(0, iSep);
    return sResult;
  }
  
  public static
  String getPatientId(AuthAssertion[] arrayOfAssertion) 
  {
    String resourceId = getXACMLResourceId(arrayOfAssertion);
    
    if(resourceId == null) return null;
    
    resourceId = resourceId.trim().toUpperCase();
    
    int sep = resourceId.indexOf('^');
    
    String patientId = sep > 0 ? resourceId.substring(0, sep) : resourceId;
    
    if(patientId.length() == 16) return patientId;
    
    return null;
  }
  
  public static
  String getXACMLResourceId(AuthAssertion[] arrayOfAssertion) 
  {
    if(arrayOfAssertion == null || arrayOfAssertion.length == 0) {
      return null;
    }
    
    String result = null;
    for(int i = 0; i < arrayOfAssertion.length; i++) {
      AuthAssertion assertion = arrayOfAssertion[i];
      if(assertion instanceof SAMLAttributeAssertion) {
        result = ((SAMLAttributeAssertion) assertion).getResourceId();
      }
      else if(assertion instanceof SAMLAssertion) {
        if(result == null || result.length() == 0) {
          result = ((SAMLAssertion) assertion).getAttribute("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
        }
      }
    }
    
    return result;
  }
}
