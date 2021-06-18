package org.dew.test;

import java.io.File;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.dew.auth.AuthAssertion;
import org.dew.auth.AuthUtil;
import org.dew.auth.SAMLAssertion;
import org.dew.auth.SAMLAttributeAssertion;

import org.dew.ebxml.RIM;
import org.dew.ebxml.Utils;
import org.dew.ebxml.query.AdhocQuery;
import org.dew.ebxml.query.AdhocQueryRequest;
import org.dew.ebxml.rs.RegistryError;

import org.dew.xds.XDSDocument;
import org.dew.xds.XDSDocumentRequest;
import org.dew.xds.XDSDocumentResponse;
import org.dew.xds.XDSbClient;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestWXDSb extends TestCase {
  
  public static final String sOPERATOR_ID   = "VRDMRC67T20I257E";
  public static final String sPATIENT_ID    = "RSSMRA75C03F839K";
  public static final String sREPOSITORY_ID = "2.16.840.1.113883.2.9.2.180.4.5.1";
  public static final String sDOC_UNIQUE_ID = "2.16.840.1.113883.2.9.2.120.4.4^0000000001";
  
  public static final String sXDSB_HOST_PORT = "http://localhost:8080/wxdsb";
  
  public static final String sITI18  = sXDSB_HOST_PORT + "/XDSDocumentRegistryQuery/RegistryStoredQuery";
  public static final String sITI41  = sXDSB_HOST_PORT + "/XDSDocumentRegistryProvideAndRegister/ProvideAndRegisterDocumentSetb";
  public static final String sITI42  = sXDSB_HOST_PORT + "/XDSDocumentRegistryRegister/RegisterDocumentSetb";
  public static final String sITI43  = sXDSB_HOST_PORT + "/XDSDocumentRepositoryRetrieve/RetrieveDocumentSet";
  public static final String sITI62  = sXDSB_HOST_PORT + "/XDSDocumentRegistryDelete/DeleteDocumentSet";
  
  public static final String sFOLDER_TRACES = System.getProperty("user.home") + File.separator + "log" + File.separator;
  
  public TestWXDSb(String testName) {
    super(testName);
    
    try {
      File folder = new File(sFOLDER_TRACES);
      if(!folder.exists()) folder.mkdirs();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    
    // Trust all certificates
    try {
      TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
          public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null;}
          public void checkClientTrusted(X509Certificate[] certs, String authType) {}
          public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        }
      };
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      
      HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
    }
    catch(Throwable th) {
      th.printStackTrace();
      return;
    }
    
    // Disable Server Name Indication (SNI) extension of Transport Layer Security (TLS) 
    System.setProperty("jsse.enableSNIExtension", "false");
  }
  
  public static Test suite() {
    return new TestSuite(TestWXDSb.class);
  }
  
  public void testApp() throws Exception {
    
    String sOperation = System.getProperty("dew.test.op", "");
    
    if(sOperation == null || sOperation.length() == 0) {
      System.out.println("dew.test.op not setted (ex. -Ddew.test.op=findDocuments)");
    }
    else if(sOperation.equalsIgnoreCase("findDocuments")) {
      findDocuments(sPATIENT_ID);
    }
    else if(sOperation.equalsIgnoreCase("retrieveDocumentSet")) {
      retrieveDocumentSet(sPATIENT_ID, sREPOSITORY_ID, sDOC_UNIQUE_ID);
    }
    else {
      System.out.println("Unknow dew.test.op=" + sOperation);
    }
    
  }
  
  public static 
  void findDocuments(String patientId) 
    throws Exception 
  {
    System.out.println("findDocuments(" + patientId + ")...");
    
    if(patientId == null || patientId.length() < 16) {
      patientId = sPATIENT_ID;
    }
    
    XDSbClient xdsbClient = new XDSbClient(sITI18, sITI41, sITI42, sITI43, sITI62);
    // xdsbClient.setSSLSocketFactory(AuthUtil.getSSLSocketFactoryMutualAuth("authentication.jks", "password"));
    xdsbClient.setTracerRequest(sFOLDER_TRACES + "query_"  + patientId + "_req.xml");
    xdsbClient.setTracerResponse(sFOLDER_TRACES + "query_" + patientId + "_res.xml");
    
    SAMLAttributeAssertion attribAssertion = new SAMLAttributeAssertion(sOPERATOR_ID + "^^^&2.16.840.1.113883.2.9.4.3.2&ISO");
    attribAssertion.setIssuer("000");
    attribAssertion.setAuthnContextClassRef(SAMLAssertion.AUTH_CONTEXT_CLASS_KERBEROS);
    attribAssertion.setSubjectRole("APR");
    attribAssertion.setLocality("000000");
    attribAssertion.setPurposeOfUse("TREATMENT");
    attribAssertion.setDocumentType("('57833-6^^2.16.840.1.113883.6.1','57832-8^^2.16.840.1.113883.6.1','29304-3^^2.16.840.1.113883.6.1')");
    attribAssertion.setOrganizationId("000");
    attribAssertion.setOrganization("Organization");
    attribAssertion.setResourceId(patientId + "^^^&2.16.840.1.113883.2.9.4.3.2&ISO");
    attribAssertion.setPatientConsent(true);
    attribAssertion.setActionId("READ");
    
    PrivateKey      privateKey  = AuthUtil.loadPrivateKey("authentication.pem");
    X509Certificate certificate = AuthUtil.loadCertificate("authentication.crt");
    
    attribAssertion.sign(privateKey, certificate);
    
    AuthAssertion[] arrayOfAssertion = new AuthAssertion[]{attribAssertion};
    
    AdhocQueryRequest request = new AdhocQueryRequest();
    AdhocQuery adhocQuery = request.getAdhocQuery();
    adhocQuery.setPatientId("'" + patientId + "^^^&2.16.840.1.113883.2.9.4.3.2&ISO'");
    adhocQuery.setStatusValues(RIM.STATUS_APPROVED);
    adhocQuery.setTypeCodes("57833-6^^2.16.840.1.113883.6.1", "57832-8^^2.16.840.1.113883.6.1", "29304-3^^2.16.840.1.113883.6.1");
    try {
      System.out.println("xdsbClient.findDocuments...");
      XDSDocument[] result = xdsbClient.findDocuments(request, arrayOfAssertion);
      
      List<RegistryError> registryErrorList = request.getRegistryErrorList();
      if(registryErrorList != null && registryErrorList.size() > 0) {
        System.out.println("Registry Errors:");
        for(int i = 0; i < registryErrorList.size(); i++) {
          System.out.println("  " + registryErrorList.get(i).getCodeContext());
        }
      }
      if(result == null) {
        System.out.println("findDocuments -> null");
      }
      else if(result.length == 0) {
        System.out.println("findDocuments -> empty");
      }
      else {
        int iSizeResult = result != null ? result.length : 0;
        System.out.println("findDocuments -> " + iSizeResult + " items:");
        for(int i = 0; i < result.length; i++) {
          System.out.println("   [" + i + "] " + result[i]);
        }
      }
    }
    catch(Throwable th) {
      System.out.println(th.getMessage());
    }
    System.out.println("----------------------------");
  }
  
  public static 
  void retrieveDocumentSet(String patientId, String repositoryId, String uniqueId) 
    throws Exception 
  {
    System.out.println("retrieveDocumentSet(" + patientId + "," + repositoryId + "," + uniqueId + ")...");
    
    if(patientId == null || patientId.length() < 16) {
      patientId = sPATIENT_ID;
    }
    if(repositoryId == null || repositoryId.length() == 0) {
      repositoryId = sREPOSITORY_ID;
    }
    if(uniqueId == null || uniqueId.length() == 0) {
      uniqueId = sDOC_UNIQUE_ID;
    }
    
    XDSbClient xdsbClient = new XDSbClient(sITI18, sITI41, sITI42, sITI43, sITI62);
    // xdsbClient.setSSLSocketFactory(AuthUtil.getSSLSocketFactoryMutualAuth("authentication.jks", "password"));
    xdsbClient.setTracerRequest(sFOLDER_TRACES + "retrieve_"  + Utils.extractIdFromUniqueId(uniqueId) + "_" + patientId + "_req.xml");
    xdsbClient.setTracerResponse(sFOLDER_TRACES + "retrieve_" + Utils.extractIdFromUniqueId(uniqueId) + "_" + patientId + "_res.xml");
    
    SAMLAttributeAssertion attribAssertion = new SAMLAttributeAssertion(sOPERATOR_ID + "^^^&2.16.840.1.113883.2.9.4.3.2&ISO");
    attribAssertion.setIssuer("000");
    attribAssertion.setAuthnContextClassRef(SAMLAssertion.AUTH_CONTEXT_CLASS_KERBEROS);
    attribAssertion.setSubjectRole("ASS");
    attribAssertion.setLocality("------");
    attribAssertion.setPurposeOfUse("PERSONAL");
    attribAssertion.setOrganizationId("000");
    attribAssertion.setOrganization("Organization");
    attribAssertion.setResourceId(patientId + "^^^&2.16.840.1.113883.2.9.4.3.2&ISO");
    attribAssertion.setPatientConsent(true);
    attribAssertion.setActionId("READ");
    
    PrivateKey      privateKey  = AuthUtil.loadPrivateKey("authentication.pem");
    X509Certificate certificate = AuthUtil.loadCertificate("authentication.crt");
    
    attribAssertion.sign(privateKey, certificate);
    
    AuthAssertion[] arrayOfAssertion = new AuthAssertion[]{attribAssertion};
    try {
      XDSDocumentResponse xdsDoc = xdsbClient.retrieveDocumentSet(new XDSDocumentRequest(repositoryId, uniqueId), arrayOfAssertion);
      
      if(xdsDoc == null) {
        System.out.println("retrieveDocumentSet -> null");
      }
      else {
        System.out.println("retrieveDocumentSet:");
        String sDocument = new String(xdsDoc.getDocument());
        sDocument = sDocument.replace("\n", "").replace("\r", "");
        System.out.println(new String(xdsDoc.getDocument()));
      }
    }
    catch(Throwable th) {
      System.out.println(th.getMessage());
    }
    System.out.println("----------------------------");
  }
}
