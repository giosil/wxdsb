package org.dew.xds;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.UUID;

import javax.net.ssl.SSLSocketFactory;

import javax.xml.soap.SOAPMessage;

import org.dew.auth.AuthAssertion;
import org.dew.auth.AuthContentHandler;
import org.dew.auth.BasicAssertion;

import org.dew.ebxml.Association;
import org.dew.ebxml.ExtrinsicObject;
import org.dew.ebxml.ObjectRefList;
import org.dew.ebxml.RIM;
import org.dew.ebxml.RIMContentHandler;
import org.dew.ebxml.RegistryObject;
import org.dew.ebxml.RegistryObjectList;
import org.dew.ebxml.RegistryPackage;
import org.dew.ebxml.query.AdhocQueryRequest;
import org.dew.ebxml.query.AdhocQueryResponse;
import org.dew.ebxml.rs.RegistryError;
import org.dew.ebxml.rs.RegistryResponse;
import org.dew.ebxml.rs.XRRContentHandler;

import org.dew.xds.util.Base64Coder;
import org.dew.xds.util.WSUtil;

public
class XDSbClient implements IXDSb
{
  private static final byte[] CRLF = {13, 10};
  
  protected String urlITI18;
  protected String urlITI41;
  protected String urlITI42;
  protected String urlITI43;
  protected String urlITI57;
  protected String urlITI62;
  
  protected int connTimeout = 90 * 1000;
  protected int readTimeout = 90 * 1000;
  
  protected String basicAuth;
  
  protected OutputStream tracerRequest;
  protected OutputStream tracerResponse;
  protected String       tracerReqName;
  protected String       tracerResName;
  protected String       tracerPrefix;
  
  protected SSLSocketFactory sslSocketFactory;
  
  public XDSbClient()
  {
  }
  
  public XDSbClient(String sURL)
  {
    this.urlITI18 = sURL;
    this.urlITI41 = sURL;
    this.urlITI42 = sURL;
    this.urlITI43 = sURL;
    this.urlITI57 = sURL;
    this.urlITI62 = sURL;
  }
  
  public XDSbClient(String sURLITI18, String sURLITI43)
  {
    this.urlITI18 = sURLITI18;
    this.urlITI43 = sURLITI43;
  }
  
  public XDSbClient(String sURLITI18, String sURLITI41, String sURLITI42, String sURLITI43)
  {
    this.urlITI18 = sURLITI18;
    this.urlITI41 = sURLITI41;
    this.urlITI42 = sURLITI42;
    this.urlITI43 = sURLITI43;
  }
  
  public XDSbClient(String sURLITI18, String sURLITI41, String sURLITI42, String sURLITI43, String sURLITI62)
  {
    this.urlITI18 = sURLITI18;
    this.urlITI41 = sURLITI41;
    this.urlITI42 = sURLITI42;
    this.urlITI43 = sURLITI43;
    this.urlITI62 = sURLITI62;
  }
  
  public XDSbClient(String sURLITI18, String sURLITI41, String sURLITI42, String sURLITI43, String sURLITI62, String sURLITI57)
  {
    this.urlITI18 = sURLITI18;
    this.urlITI41 = sURLITI41;
    this.urlITI42 = sURLITI42;
    this.urlITI43 = sURLITI43;
    this.urlITI62 = sURLITI62;
    this.urlITI57 = sURLITI57;
  }
  
  public OutputStream getTracerRequest() {
    return tracerRequest;
  }
  
  public void setTracerRequest(OutputStream tracerRequest) {
    this.tracerRequest = tracerRequest;
  }
  
  public void setTracerRequest(String filePath) {
    if(this.tracerRequest != null) {
      try { this.tracerRequest.flush(); } catch(Exception ex) {}
      try { this.tracerRequest.close(); } catch(Exception ex) {}
    }
    if(filePath == null || filePath.length() == 0) {
      this.tracerRequest = null;
      this.tracerReqName = null;
    }
    else {
      try {
        if(tracerPrefix != null && tracerPrefix.length() > 0) {
          int iLastSep = filePath.lastIndexOf('/');
          if(iLastSep < 0) {
            iLastSep = filePath.lastIndexOf('\\');
          }
          if(iLastSep >= 0) {
            this.tracerRequest = new FileOutputStream(filePath.substring(0, iLastSep+1) + tracerPrefix + "_" + filePath.substring(iLastSep+1));
          }
          else {
            this.tracerRequest = new FileOutputStream(tracerPrefix + "_" + filePath);
          }
        }
        else {
          this.tracerRequest = new FileOutputStream(filePath);
        }
        this.tracerReqName = filePath;
      }
      catch(Exception ex) {
        ex.printStackTrace();
        this.tracerRequest = null;
        this.tracerReqName = null;
      }
    }
  }
  
  public OutputStream getTracerResponse() {
    return tracerResponse;
  }
  
  public void setTracerResponse(OutputStream tracerResponse) {
    this.tracerResponse = tracerResponse;
  }
  
  public void setTracerResponse(String filePath) {
    if(this.tracerResponse != null) {
      try { this.tracerResponse.flush(); } catch(Exception ex) {}
      try { this.tracerResponse.close(); } catch(Exception ex) {}
    }
    if(filePath == null || filePath.length() == 0) {
      this.tracerResponse = null;
      this.tracerResName  = null;
    }
    else {
      try {
        if(tracerPrefix != null && tracerPrefix.length() > 0) {
          int iLastSep = filePath.lastIndexOf('/');
          if(iLastSep < 0) {
            iLastSep = filePath.lastIndexOf('\\');
          }
          if(iLastSep >= 0) {
            this.tracerResponse = new FileOutputStream(filePath.substring(0, iLastSep+1) + tracerPrefix + "_" + filePath.substring(iLastSep+1));
          }
          else {
            this.tracerResponse = new FileOutputStream(tracerPrefix + "_" + filePath);
          }
        }
        else {
          this.tracerResponse = new FileOutputStream(filePath);
        }
        this.tracerResName  = filePath;
      }
      catch(Exception ex) {
        ex.printStackTrace();
        this.tracerResponse = null;
        this.tracerResName  = null;
      }
    }
  }
  
  public void disableTracer() {
    if(this.tracerRequest != null) {
      try { this.tracerRequest.flush(); } catch(Exception ex) {}
      try { this.tracerRequest.close(); } catch(Exception ex) {}
    }
    this.tracerRequest = null;
    if(this.tracerResponse != null) {
      try { this.tracerResponse.flush(); } catch(Exception ex) {}
      try { this.tracerResponse.close(); } catch(Exception ex) {}
    }
    this.tracerResponse = null;
  }
  
  public void setTracerPrefix(String sPrefix) {
    this.tracerPrefix = sPrefix;
    this.setTracerRequest(this.tracerReqName);
    this.setTracerResponse(this.tracerResName);
  }
  
  public String getUrlITI18() {
    return urlITI18;
  }
  
  public void setUrlITI18(String urlITI18) {
    this.urlITI18 = urlITI18;
  }
  
  public String getUrlITI41() {
    return urlITI41;
  }
  
  public void setUrlITI41(String urlITI41) {
    this.urlITI41 = urlITI41;
  }
  
  public String getUrlITI42() {
    return urlITI42;
  }
  
  public void setUrlITI42(String urlITI42) {
    this.urlITI42 = urlITI42;
  }
  
  public String getUrlITI43() {
    return urlITI43;
  }
  
  public void setUrlITI43(String urlITI43) {
    this.urlITI43 = urlITI43;
  }
  
  public String getUrlITI57() {
    return urlITI57;
  }
  
  public void setUrlITI57(String urlITI57) {
    this.urlITI57 = urlITI57;
  }
  
  public String getUrlITI62() {
    return urlITI62;
  }
  
  public void setUrlITI62(String urlITI62) {
    this.urlITI62 = urlITI62;
  }
  
  public int getConnTimeout() {
    return connTimeout;
  }
  
  public void setConnTimeout(int connTimeout) {
    this.connTimeout = connTimeout;
  }
  
  public int getReadTimeout() {
    return readTimeout;
  }
  
  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }
  
  public void setBasicAuth(String username, String password) {
    if(username == null || username.length() == 0) {
      basicAuth = null;
    }
    else {
      if(password == null) password = "";
      basicAuth = Base64Coder.encodeString(username + ":" + password);
    }
  }
  
  public void setBasicAuth(BasicAssertion assertion) {
    if(assertion == null) {
      basicAuth = null;
      return;
    }
    setBasicAuth(assertion.getSubjectId(), assertion.getPassword());
  }
  
  public 
  void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
    this.sslSocketFactory = sslSocketFactory;
  }
  
  public
  AdhocQueryResponse registryStoredQuery(AdhocQueryRequest request, AuthAssertion[] arrayOfAssertion)
  {
    if(request == null) return new AdhocQueryResponse();
    
    if(urlITI18 == null || urlITI18.length() == 0) {
      throw new RuntimeException("Invalid URL RegistryStoredQuery (urlITI18=" + urlITI18 + ")");
    }
    
    StringBuilder sbRequest = new StringBuilder(800);
    sbRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
    sbRequest.append("<soap:Header>");
    sbRequest.append("<To xmlns=\"http://www.w3.org/2005/08/addressing\">" + urlITI18 + "</To>");
    sbRequest.append("<Action xmlns=\"http://www.w3.org/2005/08/addressing\">urn:ihe:iti:2007:RegistryStoredQuery</Action>");
    sbRequest.append("<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</ReplyTo>");
    sbRequest.append("<FaultTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</FaultTo>");
    sbRequest.append("<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:" + UUID.randomUUID() + "</MessageID>");
    
    if(arrayOfAssertion != null && arrayOfAssertion.length > 0) {
      boolean boSecurityAdded = false;
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        AuthAssertion assertion  = arrayOfAssertion[i];
        if(assertion == null) continue;
        if(assertion instanceof BasicAssertion) {
          setBasicAuth((BasicAssertion) assertion);
        }
        else {
          String sXml = assertion.toXML(null);
          if(sXml != null && sXml.length() > 1) {
            if(!boSecurityAdded) {
              boSecurityAdded = true;
              sbRequest.append("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">");
            }
            sbRequest.append(sXml);
          }
        }
      }
      if(boSecurityAdded) {
        sbRequest.append("</wsse:Security>");
      }
    }
    sbRequest.append("</soap:Header>");
    sbRequest.append("<soap:Body>");
    sbRequest.append(request.toXML(null));
    sbRequest.append("</soap:Body>");
    sbRequest.append("</soap:Envelope>");
    
    SOAPMessage soapResponse = null;
    byte[] response = null;
    try {
      if(tracerRequest != null) try{ tracerRequest.write(sbRequest.toString().getBytes()); tracerRequest.write(CRLF); } catch(Exception ex) {}
      
      if(sslSocketFactory != null) {
        soapResponse = WSUtil.sendRequest(urlITI18, sslSocketFactory, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      else {
        soapResponse = WSUtil.sendRequest(urlITI18, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      
      response = WSUtil.getSOAPPartContent(soapResponse);
      
      if(tracerResponse != null) try{ tracerResponse.write(response); tracerResponse.write(CRLF); } catch(Exception ex) {}
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    
    List<RegistryError> registryErrorList;
    String fault;
    try {
      XRRContentHandler xrrContentHandler = new XRRContentHandler();
      xrrContentHandler.load(response);
      
      registryErrorList = xrrContentHandler.getRegistryErrorList();
      request.setRegistryErrorList(registryErrorList);
      
      fault = xrrContentHandler.getFault();
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    if(fault != null && fault.length() > 0) {
      throw new RuntimeException("SOAPFault " + fault);
    }
    
    RegistryObjectList registryObjectList = null;
    try {
      RIMContentHandler rimContentHandler = new RIMContentHandler();
      rimContentHandler.load(response);
      registryObjectList = rimContentHandler.getRegistryObjectList();
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    
    List<AuthAssertion> listOfResponseAssertion = null;
    try {
      AuthContentHandler samlContentHandler = new AuthContentHandler();
      samlContentHandler.load(response);
      listOfResponseAssertion = samlContentHandler.getListOfAssertion();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    
    return new AdhocQueryResponse(registryErrorList, registryObjectList, request, listOfResponseAssertion);
  }
  
  public
  XDSDocument[] findDocuments(AdhocQueryRequest request, AuthAssertion[] arrayOfAssertion)
  {
    if(request == null) return new XDSDocument[0];
    
    if(urlITI18 == null || urlITI18.length() == 0) {
      throw new RuntimeException("Invalid URL RegistryStoredQuery (urlITI18=" + urlITI18 + ")");
    }
    
    StringBuilder sbRequest = new StringBuilder(800);
    sbRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
    sbRequest.append("<soap:Header>");
    sbRequest.append("<To xmlns=\"http://www.w3.org/2005/08/addressing\">" + urlITI18 + "</To>");
    sbRequest.append("<Action xmlns=\"http://www.w3.org/2005/08/addressing\">urn:ihe:iti:2007:RegistryStoredQuery</Action>");
    sbRequest.append("<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</ReplyTo>");
    sbRequest.append("<FaultTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</FaultTo>");
    sbRequest.append("<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:" + UUID.randomUUID() + "</MessageID>");
    
    if(arrayOfAssertion != null && arrayOfAssertion.length > 0) {
      boolean boSecurityAdded = false;
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        AuthAssertion assertion  = arrayOfAssertion[i];
        if(assertion == null) continue;
        if(assertion instanceof BasicAssertion) {
          setBasicAuth((BasicAssertion) assertion);
        }
        else {
          String sXml = assertion.toXML(null);
          if(sXml != null && sXml.length() > 1) {
            if(!boSecurityAdded) {
              boSecurityAdded = true;
              sbRequest.append("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">");
            }
            sbRequest.append(sXml);
          }
        }
      }
      if(boSecurityAdded) {
        sbRequest.append("</wsse:Security>");
      }
    }
    sbRequest.append("</soap:Header>");
    sbRequest.append("<soap:Body>");
    sbRequest.append(request.toXML(null));
    sbRequest.append("</soap:Body>");
    sbRequest.append("</soap:Envelope>");
    
    SOAPMessage soapResponse = null;
    byte[] response = null;
    try {
      if(tracerRequest != null) try{ tracerRequest.write(sbRequest.toString().getBytes()); tracerRequest.write(CRLF); } catch(Exception ex) {}
      
      if(sslSocketFactory != null) {
        soapResponse = WSUtil.sendRequest(urlITI18, sslSocketFactory, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      else {
        soapResponse = WSUtil.sendRequest(urlITI18, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      
      response = WSUtil.getSOAPPartContent(soapResponse);
      
      if(tracerResponse != null) try{ tracerResponse.write(response); tracerResponse.write(CRLF); } catch(Exception ex) {}
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    
    List<RegistryError> registryErrorList;
    String fault;
    try {
      XRRContentHandler xrrContentHandler = new XRRContentHandler();
      xrrContentHandler.load(response);
      
      registryErrorList = xrrContentHandler.getRegistryErrorList();
      request.setRegistryErrorList(registryErrorList);
      
      fault = xrrContentHandler.getFault();
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    if(fault != null && fault.length() > 0) {
      throw new RuntimeException("SOAPFault " + fault);
    }
    
    List<ExtrinsicObject> listOfExtrinsicObject = null;
    try {
      RIMContentHandler rimContentHandler = new RIMContentHandler();
      rimContentHandler.load(response);
      RegistryObjectList registryObjectList = rimContentHandler.getRegistryObjectList();
      if(registryObjectList != null) {
        listOfExtrinsicObject = registryObjectList.getExtrinsicObjects();
      }
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    if(listOfExtrinsicObject == null || listOfExtrinsicObject.size() == 0) {
      return new XDSDocument[0];
    }
    
    XDSDocument[] result = new XDSDocument[listOfExtrinsicObject.size()];
    for(int i = 0; i < listOfExtrinsicObject.size(); i++) {
      result[i] = new XDSDocument(listOfExtrinsicObject.get(i));
    }
    return result;
  }
  
  public 
  RegistryResponse provideAndRegisterDocumentSet(XDSDocument[] arrayOfXDSDocument, AuthAssertion[] arrayOfAssertion)
  {
    if(arrayOfXDSDocument == null || arrayOfXDSDocument.length == 0) {
      throw new RuntimeException("Invalid arrayOfXDSDocument");
    }
    if(urlITI41 == null || urlITI41.length() == 0) {
      throw new RuntimeException("Invalid URL ProvideAndRegisterDocumentSet (urlITI41=" + urlITI41 + ")");
    }
    // Check Documents
    for(int i = 0; i < arrayOfXDSDocument.length; i++) {
      XDSDocument xdsDocument = arrayOfXDSDocument[i];
      if(xdsDocument == null) {
        throw new RuntimeException("Invalid arrayOfXDSDocument[" + i + "]");
      }
      byte[] content = xdsDocument.getContent();
      if(content == null || content.length == 0) {
        throw new RuntimeException("Invalid arrayOfXDSDocument[" + i + "].content");
      }
    }
    
    String boundary = "uuid:" + UUID.randomUUID();
    String sContId  = UUID.randomUUID().toString();
    String sXOPRef  = sContId + "@urn%3Aihe%3Aiti%3Axds-b%3A2007";
    String sXOPAtt  = sContId + "@urn:ihe:iti:xds-b:2007";
    
    SOAPMessage soapResponse = null;
    byte[] response = null;
    try {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      // Messaggio
      os.write(("--" + boundary).getBytes());
      os.write(CRLF);
      os.write(("Content-Type: application/xop+xml; charset=UTF-8; type=\"application/soap+xml\";").getBytes());
      os.write(CRLF);
      os.write(("Content-Transfer-Encoding: binary").getBytes());
      os.write(CRLF);
      os.write(("Content-ID: <root.message>").getBytes());
      os.write(CRLF);
      os.write(CRLF);
      os.write(("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">").getBytes());
      os.write(("<soap:Header>").getBytes());
      
      os.write(("<To xmlns=\"http://www.w3.org/2005/08/addressing\">" + urlITI41 + "</To>").getBytes());
      os.write(("<Action xmlns=\"http://www.w3.org/2005/08/addressing\">urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b</Action>").getBytes());
      os.write(("<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">").getBytes());
      os.write(("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>").getBytes());
      os.write(("</ReplyTo>").getBytes());
      os.write(("<FaultTo xmlns=\"http://www.w3.org/2005/08/addressing\">").getBytes());
      os.write(("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>").getBytes());
      os.write(("</FaultTo>").getBytes());
      os.write(("<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:" + UUID.randomUUID() + "</MessageID>").getBytes());
      
      if(arrayOfAssertion != null && arrayOfAssertion.length > 0) {
        boolean boSecurityAdded = false;
        for(int i = 0; i < arrayOfAssertion.length; i++) {
          AuthAssertion assertion  = arrayOfAssertion[i];
          if(assertion == null) continue;
          if(assertion instanceof BasicAssertion) {
            setBasicAuth((BasicAssertion) assertion);
          }
          else {
            String sXml = assertion.toXML(null);
            if(sXml != null && sXml.length() > 1) {
              if(!boSecurityAdded) {
                boSecurityAdded = true;
                os.write(("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">").getBytes());
              }
              os.write(sXml.getBytes());
            }
          }
        }
        if(boSecurityAdded) {
          os.write(("</wsse:Security>").getBytes());
        }
      }
      os.write(("</soap:Header>").getBytes());
      os.write(("<soap:Body>").getBytes());
      os.write(("<ns3:ProvideAndRegisterDocumentSetRequest xmlns:ns3=\"urn:ihe:iti:xds-b:2007\" xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\">").getBytes());
      os.write(("<ns4:SubmitObjectsRequest>").getBytes());
      os.write(("<RegistryObjectList>").getBytes());
      // ExtrinsicObjects
      for(int i = 0; i < arrayOfXDSDocument.length; i++) {
        XDSDocument xdsDocument = arrayOfXDSDocument[i];
        RegistryObject registryObject = xdsDocument.getRegistryObject();
        boolean assDeprecated = xdsDocument.checkAssociationDeprecate();
        if(!assDeprecated || xdsDocument.getCreationTime() != null) {
          os.write((registryObject.toXML(null)).getBytes());
        }
      }
      // RegistryPackage
      XDSDocument xdsDocument0 = arrayOfXDSDocument[0];
      RegistryPackage registryPackage = xdsDocument0.getRegistryPackage();
      os.write((registryPackage.toXML(null)).getBytes());
      // Classification (of submission set) 
      os.write(("<Classification classificationNode=\"" + XDS.CLS_NODE_SUBMISSION_SET + "\" classifiedObject=\"" + registryPackage.getId() + "\" id=\"urn:uuid:" + UUID.randomUUID() + "\"/>").getBytes());
      // Associations (HasMember)
      for(int i = 0; i < arrayOfXDSDocument.length; i++) {
        XDSDocument xdsDocument = arrayOfXDSDocument[i];
        RegistryObject registryObject = xdsDocument.getRegistryObject();
        os.write(("<Association associationType=\"" + RIM.ASS_TYPE_HAS_MEMBER + "\" id=\"urn:uuid:" + UUID.randomUUID() + "\" sourceObject=\"" + registryPackage.getId() + "\" targetObject=\"" + registryObject.getId() + "\"><Slot name=\"SubmissionSetStatus\"><ValueList><Value>Original</Value></ValueList></Slot></Association>").getBytes());
        Association association = xdsDocument.getAssociation();
        if(association != null) {
          String sXmlAssociation = association.toXML(null);
          if(sXmlAssociation != null && sXmlAssociation.length() > 0) {
            os.write(sXmlAssociation.getBytes());
          }
        }
      }
      os.write(("</RegistryObjectList>").getBytes());
      os.write(("</ns4:SubmitObjectsRequest>").getBytes());
      // Documents
      for(int i = 0; i < arrayOfXDSDocument.length; i++) {
        XDSDocument xdsDocument = arrayOfXDSDocument[i];
        os.write(("<ns3:Document id=\"" + xdsDocument.getRegistryObjectId() + "\">").getBytes());
        os.write(("<xop:Include href=\"cid:" + sXOPRef + "\" xmlns:xop=\"http://www.w3.org/2004/08/xop/include\" />").getBytes());
        os.write(("</ns3:Document>").getBytes());
      }
      os.write(("</ns3:ProvideAndRegisterDocumentSetRequest>").getBytes());
      os.write(("</soap:Body>").getBytes());
      os.write(("</soap:Envelope>").getBytes());
      for(int i = 0; i < arrayOfXDSDocument.length; i++) {
        XDSDocument xdsDocument = arrayOfXDSDocument[i];
        String sMimeType = xdsDocument.getMimeType();
        // Documento
        os.write(CRLF);
        os.write(("--" + boundary).getBytes());
        os.write(CRLF);
        if(sMimeType != null && sMimeType.length() > 0) {
          os.write(("Content-Type: " + sMimeType + ";").getBytes());
          os.write(CRLF);
        }
        os.write(("Content-Transfer-Encoding: binary").getBytes());
        os.write(CRLF);
        os.write(("Content-ID: <" + sXOPAtt + ">").getBytes());
        os.write(CRLF);
        os.write(CRLF);
        os.write(xdsDocument.getContent());
      }
      // Chiusura 
      os.write(CRLF);
      os.write(("--" + boundary + "--").getBytes());
      os.write(CRLF);
      os.flush();
      
      String sContentType = "multipart/related; type=\"application/xop+xml\"; boundary=\"" + boundary + "\"; start=\"<root.message>\"; start-info=\"application/soap+xml\"; action=\"urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b\"";
      
      if(tracerRequest != null) try{ tracerRequest.write(os.toByteArray()); tracerRequest.write(CRLF); } catch(Exception ex) {}
      
      if(sslSocketFactory != null) {
        soapResponse = WSUtil.sendRequest(urlITI41, sslSocketFactory, basicAuth, connTimeout, readTimeout, sContentType, os.toByteArray());
      }
      else {
        soapResponse = WSUtil.sendRequest(urlITI41, basicAuth, connTimeout, readTimeout, sContentType, os.toByteArray());
      }
      
      response = WSUtil.getSOAPPartContent(soapResponse);
      
      if(tracerResponse != null) try{ tracerResponse.write(response); tracerResponse.write(CRLF); } catch(Exception ex) {}
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    
    RegistryResponse registryResponse;
    String fault;
    try {
      XRRContentHandler xrrContentHandler = new XRRContentHandler();
      xrrContentHandler.load(response);
      
      registryResponse = xrrContentHandler.getRegistryResponse();
      
      fault = xrrContentHandler.getFault();
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    if(registryResponse != null) {
      return registryResponse;
    }
    if(fault != null && fault.length() > 0) {
      throw new RuntimeException("SOAPFault " + fault);
    }
    return null;
  }
  
  public 
  RegistryResponse registerDocumentSet(XDSDocument[] arrayOfXDSDocument, AuthAssertion[] arrayOfAssertion)
  {
    if(arrayOfXDSDocument == null || arrayOfXDSDocument.length == 0) {
      throw new RuntimeException("Invalid arrayOfXDSDocument");
    }
    
    if(urlITI42 == null || urlITI42.length() == 0) {
      throw new RuntimeException("Invalid URL ProvideAndRegisterDocumentSet (urlITI42=" + urlITI42 + ")");
    }
    
    StringBuilder sbRequest = new StringBuilder(650);
    sbRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
    sbRequest.append("<soap:Header>");
    sbRequest.append("<To xmlns=\"http://www.w3.org/2005/08/addressing\">" + urlITI42 + "</To>");
    sbRequest.append("<Action xmlns=\"http://www.w3.org/2005/08/addressing\">urn:ihe:iti:2007:RegisterDocumentSet-b</Action>");
    sbRequest.append("<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</ReplyTo>");
    sbRequest.append("<FaultTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</FaultTo>");
    sbRequest.append("<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:" + UUID.randomUUID() + "</MessageID>");
    
    if(arrayOfAssertion != null && arrayOfAssertion.length > 0) {
      boolean boSecurityAdded = false;
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        AuthAssertion assertion  = arrayOfAssertion[i];
        if(assertion == null) continue;
        if(assertion instanceof BasicAssertion) {
          setBasicAuth((BasicAssertion) assertion);
        }
        else {
          String sXml = assertion.toXML(null);
          if(sXml != null && sXml.length() > 1) {
            if(!boSecurityAdded) {
              boSecurityAdded = true;
              sbRequest.append("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">");
            }
            sbRequest.append(sXml);
          }
        }
      }
      if(boSecurityAdded) {
        sbRequest.append("</wsse:Security>");
      }
    }
    sbRequest.append("</soap:Header>");
    sbRequest.append("<soap:Body>");
    sbRequest.append("<ns4:SubmitObjectsRequest xmlns:ns3=\"urn:ihe:iti:xds-b:2007\" xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\">");
    sbRequest.append("<RegistryObjectList>");
    // ExtrinsicObjects
    for(int i = 0; i < arrayOfXDSDocument.length; i++) {
      XDSDocument xdsDocument = arrayOfXDSDocument[i];
      RegistryObject registryObject = xdsDocument.getRegistryObject();
      boolean assDeprecated = xdsDocument.checkAssociationDeprecate();
      if(!assDeprecated || xdsDocument.getCreationTime() != null) {
        sbRequest.append(registryObject.toXML(null));
      }
    }
    // RegistryPackage
    XDSDocument xdsDocument0 = arrayOfXDSDocument[0];
    RegistryPackage registryPackage = xdsDocument0.getRegistryPackage();
    sbRequest.append(registryPackage.toXML(null));
    // Classification (of submission set)
    sbRequest.append("<Classification classificationNode=\"" + XDS.CLS_NODE_SUBMISSION_SET + "\" classifiedObject=\"" + registryPackage.getId() + "\" id=\"urn:uuid:" + UUID.randomUUID() + "\"/>");
    // Associations (HasMember)
    for(int i = 0; i < arrayOfXDSDocument.length; i++) {
      XDSDocument xdsDocument = arrayOfXDSDocument[i];
      RegistryObject registryObject = xdsDocument.getRegistryObject();
      sbRequest.append("<Association associationType=\"" + RIM.ASS_TYPE_HAS_MEMBER + "\" id=\"urn:uuid:" + UUID.randomUUID() + "\" sourceObject=\"" + registryPackage.getId() + "\" targetObject=\"" + registryObject.getId() + "\"><Slot name=\"SubmissionSetStatus\"><ValueList><Value>Original</Value></ValueList></Slot></Association>");
      Association association = xdsDocument.getAssociation();
      if(association != null) {
        String sXmlAssociation = association.toXML(null);
        if(sXmlAssociation != null && sXmlAssociation.length() > 0) {
          sbRequest.append(sXmlAssociation);
        }
      }
    }
    sbRequest.append("</RegistryObjectList>");
    sbRequest.append("</ns4:SubmitObjectsRequest>");
    sbRequest.append("</soap:Body>");
    sbRequest.append("</soap:Envelope>");
    
    SOAPMessage soapResponse = null;
    byte[] response = null;
    try {
      if(tracerRequest != null) try{ tracerRequest.write(sbRequest.toString().getBytes()); tracerRequest.write(CRLF); } catch(Exception ex) {}
      
      if(sslSocketFactory != null) {
        soapResponse = WSUtil.sendRequest(urlITI42, sslSocketFactory, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      else {
        soapResponse = WSUtil.sendRequest(urlITI42, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      
      response = WSUtil.getSOAPPartContent(soapResponse);
      
      if(tracerResponse != null) try{ tracerResponse.write(response); tracerResponse.write(CRLF); } catch(Exception ex) {}
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    
    RegistryResponse registryResponse;
    String fault;
    try {
      XRRContentHandler xrrContentHandler = new XRRContentHandler();
      xrrContentHandler.load(response);
      
      registryResponse = xrrContentHandler.getRegistryResponse();
      
      fault = xrrContentHandler.getFault();
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    if(registryResponse != null) {
      return registryResponse;
    }
    if(fault != null && fault.length() > 0) {
      throw new RuntimeException("SOAPFault " + fault);
    }
    return null;
  }
  
  public 
  XDSDocumentResponse retrieveDocumentSet(XDSDocumentRequest request, AuthAssertion[] arrayOfAssertion)
  {
    if(request == null) return null;
    
    if(urlITI43 == null || urlITI43.length() == 0) {
      throw new RuntimeException("Invalid URL RetrieveDocumentSet (urlITI43=" + urlITI43 + ")");
    }
    
    StringBuilder sbRequest = new StringBuilder(650);
    sbRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
    sbRequest.append("<soap:Header>");
    sbRequest.append("<To xmlns=\"http://www.w3.org/2005/08/addressing\">" + urlITI43 + "</To>");
    sbRequest.append("<Action xmlns=\"http://www.w3.org/2005/08/addressing\">urn:ihe:iti:2007:RetrieveDocumentSet</Action>");
    sbRequest.append("<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</ReplyTo>");
    sbRequest.append("<FaultTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</FaultTo>");
    sbRequest.append("<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:" + UUID.randomUUID() + "</MessageID>");
    
    if(arrayOfAssertion != null && arrayOfAssertion.length > 0) {
      boolean boSecurityAdded = false;
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        AuthAssertion assertion  = arrayOfAssertion[i];
        if(assertion == null) continue;
        if(assertion instanceof BasicAssertion) {
          setBasicAuth((BasicAssertion) assertion);
        }
        else {
          String sXml = assertion.toXML(null);
          if(sXml != null && sXml.length() > 1) {
            if(!boSecurityAdded) {
              boSecurityAdded = true;
              sbRequest.append("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">");
            }
            sbRequest.append(sXml);
          }
        }
      }
      if(boSecurityAdded) {
        sbRequest.append("</wsse:Security>");
      }
    }
    sbRequest.append("</soap:Header>");
    sbRequest.append("<soap:Body>");
    sbRequest.append("<RetrieveDocumentSetRequest xmlns=\"urn:ihe:iti:xds-b:2007\" xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\" xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\">");
    sbRequest.append(request.toXML(null));
    sbRequest.append("</RetrieveDocumentSetRequest>");
    sbRequest.append("</soap:Body>");
    sbRequest.append("</soap:Envelope>");
    
    SOAPMessage soapResponse = null;
    byte[] response = null;
    byte[] attachment  = null;
    try {
      if(tracerRequest != null) try{ tracerRequest.write(sbRequest.toString().getBytes()); tracerRequest.write(CRLF); } catch(Exception ex) {}
      
      if(sslSocketFactory != null) {
        soapResponse = WSUtil.sendRequest(urlITI43, sslSocketFactory, basicAuth, connTimeout, readTimeout, null, "urn:ihe:iti:2007:RetrieveDocumentSet", sbRequest.toString().getBytes());
      }
      else {
        soapResponse = WSUtil.sendRequest(urlITI43, basicAuth, connTimeout, readTimeout, null, "urn:ihe:iti:2007:RetrieveDocumentSet", sbRequest.toString().getBytes());
      }
      
      response     = WSUtil.getSOAPPartContent(soapResponse);
      
      if(tracerResponse != null) try{ tracerResponse.write(response); tracerResponse.write(CRLF); } catch(Exception ex) {}
      
      attachment   = WSUtil.getFirstAttachment(soapResponse);
    }
    catch(Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
    
    RegistryResponse registryResponse;
    String fault;
    try {
      XRRContentHandler xrrContentHandler = new XRRContentHandler();
      xrrContentHandler.load(response);
      
      registryResponse = xrrContentHandler.getRegistryResponse();
      
      fault = xrrContentHandler.getFault();
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    if(registryResponse != null && registryResponse.isFailure()) {
      String sFirstErrorMessage = registryResponse.getFirstErrorMessage();
      if(sFirstErrorMessage != null && sFirstErrorMessage.length() > 0) {
        throw new RuntimeException("RegistryError " + sFirstErrorMessage);
      }
    }
    if(fault != null && fault.length() > 0) {
      throw new RuntimeException("SOAPFault " + fault);
    }
    
    XDSDocumentResponse xdsDocumentResponse = null;
    try {
      XDRContentHandler xdrContentHandler = new XDRContentHandler();
      xdrContentHandler.load(response);
      xdsDocumentResponse = xdrContentHandler.getXDSDocumentResponse();
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    
    if(xdsDocumentResponse == null) {
      return null;
    }
    
    XDSDocument xdsDocument = null;
    try {
      xdsDocument = new XDSDocument(xdsDocumentResponse);
      if(attachment != null && attachment.length > 0) {
        xdsDocument.setContent(attachment);
      }
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    return new XDSDocumentResponse(xdsDocument);
  }
  
  public 
  RegistryResponse updateDocumentSet(XDSDocument[] arrayOfXDSDocument, AuthAssertion[] arrayOfAssertion)
  {
    if(arrayOfXDSDocument == null || arrayOfXDSDocument.length == 0) {
      throw new RuntimeException("Invalid arrayOfXDSDocument");
    }
    
    if(urlITI57 == null || urlITI57.length() == 0) {
      throw new RuntimeException("Invalid URL UpdateDocumentSet (urlITI57=" + urlITI57 + ")");
    }
    
    StringBuilder sbRequest = new StringBuilder(650);
    sbRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
    sbRequest.append("<soap:Header>");
    sbRequest.append("<To xmlns=\"http://www.w3.org/2005/08/addressing\">" + urlITI57 + "</To>");
    sbRequest.append("<Action xmlns=\"http://www.w3.org/2005/08/addressing\">urn:ihe:iti:xds-b:2010:urn:ihe:iti:2010:UpdateDocumentSet</Action>");
    sbRequest.append("<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</ReplyTo>");
    sbRequest.append("<FaultTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</FaultTo>");
    sbRequest.append("<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:" + UUID.randomUUID() + "</MessageID>");
    
    if(arrayOfAssertion != null && arrayOfAssertion.length > 0) {
      boolean boSecurityAdded = false;
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        AuthAssertion assertion  = arrayOfAssertion[i];
        if(assertion == null) continue;
        if(assertion instanceof BasicAssertion) {
          setBasicAuth((BasicAssertion) assertion);
        }
        else {
          String sXml = assertion.toXML(null);
          if(sXml != null && sXml.length() > 1) {
            if(!boSecurityAdded) {
              boSecurityAdded = true;
              sbRequest.append("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">");
            }
            sbRequest.append(sXml);
          }
        }
      }
      if(boSecurityAdded) {
        sbRequest.append("</wsse:Security>");
      }
    }
    sbRequest.append("</soap:Header>");
    sbRequest.append("<soap:Body>");
    sbRequest.append("<ns4:SubmitObjectsRequest xmlns:ns3=\"urn:ihe:iti:xds-b:2007\" xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\">");
    sbRequest.append("<RegistryObjectList>");
    // ExtrinsicObjects
    for(int i = 0; i < arrayOfXDSDocument.length; i++) {
      XDSDocument xdsDocument = arrayOfXDSDocument[i];
      RegistryObject registryObject = xdsDocument.getRegistryObject();
      boolean assDeprecated = xdsDocument.checkAssociationDeprecate();
      if(!assDeprecated || xdsDocument.getCreationTime() != null) {
        sbRequest.append(registryObject.toXML(null));
      }
    }
    // RegistryPackage
    XDSDocument xdsDocument0 = arrayOfXDSDocument[0];
    RegistryPackage registryPackage = xdsDocument0.getRegistryPackage();
    sbRequest.append(registryPackage.toXML(null));
    // Classification (of submission set)
    sbRequest.append("<Classification classificationNode=\"" + XDS.CLS_NODE_SUBMISSION_SET + "\" classifiedObject=\"" + registryPackage.getId() + "\" id=\"urn:uuid:" + UUID.randomUUID() + "\"/>");
    // Associations
    for(int i = 0; i < arrayOfXDSDocument.length; i++) {
      XDSDocument xdsDocument = arrayOfXDSDocument[i];
      RegistryObject registryObject = xdsDocument.getRegistryObject();
      sbRequest.append("<Association associationType=\"" + RIM.ASS_TYPE_HAS_MEMBER + "\" id=\"urn:uuid:" + UUID.randomUUID() + "\" sourceObject=\"" + registryPackage.getId() + "\" targetObject=\"" + registryObject.getId() + "\"><Slot name=\"SubmissionSetStatus\"><ValueList><Value>Original</Value></ValueList></Slot></Association>");
      Association association = xdsDocument.getAssociation();
      if(association != null) {
        String sXmlAssociation = association.toXML(null);
        if(sXmlAssociation != null && sXmlAssociation.length() > 0) {
          sbRequest.append(sXmlAssociation);
        }
      }
    }
    sbRequest.append("</RegistryObjectList>");
    sbRequest.append("</ns4:SubmitObjectsRequest>");
    sbRequest.append("</soap:Body>");
    sbRequest.append("</soap:Envelope>");
    
    SOAPMessage soapResponse = null;
    byte[] response = null;
    try {
      if(tracerRequest != null) try{ tracerRequest.write(sbRequest.toString().getBytes()); tracerRequest.write(CRLF); } catch(Exception ex) {}
      
      if(sslSocketFactory != null) {
        soapResponse = WSUtil.sendRequest(urlITI57, sslSocketFactory, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      else {
        soapResponse = WSUtil.sendRequest(urlITI57, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      
      response = WSUtil.getSOAPPartContent(soapResponse);
      
      if(tracerResponse != null) try{ tracerResponse.write(response); tracerResponse.write(CRLF); } catch(Exception ex) {}
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    
    RegistryResponse registryResponse;
    String fault;
    try {
      XRRContentHandler xrrContentHandler = new XRRContentHandler();
      xrrContentHandler.load(response);
      
      registryResponse = xrrContentHandler.getRegistryResponse();
      
      fault = xrrContentHandler.getFault();
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    if(registryResponse != null) {
      return registryResponse;
    }
    if(fault != null && fault.length() > 0) {
      throw new RuntimeException("SOAPFault " + fault);
    }
    return null;
  }
  
  public 
  RegistryResponse deleteDocumentSet(ObjectRefList objectRefList, AuthAssertion[] arrayOfAssertion)
  {
    if(objectRefList == null || objectRefList.size() == 0) {
      throw new RuntimeException("Invalid objectRefList");
    }
    
    if(urlITI62 == null || urlITI62.length() == 0) {
      throw new RuntimeException("Invalid URL DocumentRegistry_DeleteDocumentSetRequest (urlITI62=" + urlITI62 + ")");
    }
    
    StringBuilder sbRequest = new StringBuilder(800);
    sbRequest.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
    sbRequest.append("<soap:Header>");
    sbRequest.append("<To xmlns=\"http://www.w3.org/2005/08/addressing\">" + urlITI62 + "</To>");
    sbRequest.append("<Action xmlns=\"http://www.w3.org/2005/08/addressing\">urn:ihe:iti:xds-b:2010:XDSDeletetWS:DocumentRegistry_DeleteDocumentSetRequest</Action>");
    sbRequest.append("<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</ReplyTo>");
    sbRequest.append("<FaultTo xmlns=\"http://www.w3.org/2005/08/addressing\">");
    sbRequest.append("<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>");
    sbRequest.append("</FaultTo>");
    sbRequest.append("<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:" + UUID.randomUUID() + "</MessageID>");
    
    if(arrayOfAssertion != null && arrayOfAssertion.length > 0) {
      boolean boSecurityAdded = false;
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        AuthAssertion assertion  = arrayOfAssertion[i];
        if(assertion == null) continue;
        if(assertion instanceof BasicAssertion) {
          setBasicAuth((BasicAssertion) assertion);
        }
        else {
          String sXml = assertion.toXML(null);
          if(sXml != null && sXml.length() > 1) {
            if(!boSecurityAdded) {
              boSecurityAdded = true;
              sbRequest.append("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">");
            }
            sbRequest.append(sXml);
          }
        }
      }
      if(boSecurityAdded) {
        sbRequest.append("</wsse:Security>");
      }
    }
    sbRequest.append("</soap:Header>");
    sbRequest.append("<soap:Body>");
    sbRequest.append("<ns5:RemoveObjectsRequest xmlns=\"urn:ihe:iti:xds-b:2007\" xmlns:ns2=\"http://www.w3.org/2004/08/xop/include\" xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\">");
    sbRequest.append(objectRefList.toXML("ns3"));
    sbRequest.append("</ns5:RemoveObjectsRequest>");
    sbRequest.append("</soap:Body>");
    sbRequest.append("</soap:Envelope>");
    
    SOAPMessage soapResponse = null;
    byte[] response = null;
    try {
      if(tracerRequest != null) try{ tracerRequest.write(sbRequest.toString().getBytes()); tracerRequest.write(CRLF); } catch(Exception ex) {}
      
      if(sslSocketFactory != null) {
        soapResponse = WSUtil.sendRequest(urlITI62, sslSocketFactory, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      else {
        soapResponse = WSUtil.sendRequest(urlITI62, basicAuth, connTimeout, readTimeout, null, sbRequest.toString().getBytes());
      }
      
      response = WSUtil.getSOAPPartContent(soapResponse);
      
      if(tracerResponse != null) try{ tracerResponse.write(response); tracerResponse.write(CRLF); } catch(Exception ex) {}
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    
    RegistryResponse registryResponse;
    String fault;
    try {
      XRRContentHandler xrrContentHandler = new XRRContentHandler();
      xrrContentHandler.load(response);
      
      registryResponse = xrrContentHandler.getRegistryResponse();
      
      fault = xrrContentHandler.getFault();
    }
    catch(Exception ex) {
      throw new RuntimeException(ex);
    }
    if(registryResponse != null) {
      return registryResponse;
    }
    if(fault != null && fault.length() > 0) {
      throw new RuntimeException("SOAPFault " + fault);
    }
    return null;
  }
}
