package org.dew.services;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.soap.SOAPMessage;

import org.dew.auth.AuthAssertion;
import org.dew.auth.AuthContentHandler;

import org.dew.ebxml.query.AQRContentHandler;
import org.dew.ebxml.query.AdhocQueryRequest;
import org.dew.ebxml.query.AdhocQueryResponse;

import org.dew.xds.IXDSb;

import org.dew.xds.util.WSUtil;

public
class ITI18Service extends HttpServlet
{
  private static final long serialVersionUID = 1146569014077258798L;
  
  protected static final String REQUEST_NAME = "AdhocQueryRequest";
  
  protected String handlerClass = null;
  
  public
  void init()
    throws ServletException
  {
    handlerClass = getServletContext().getInitParameter("XDS");
  }
  
  protected
  void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    boolean isWSDLRequest = WSUtil.isWSDLRequest(request);
    if(isWSDLRequest) {
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/wsdl/xds-iti18.jsp");
      requestDispatcher.forward(request, response);
    }
    else {
      WSUtil.sendHTMLPage(response, REQUEST_NAME, this.getClass().getName());
    }
  }
  
  protected
  void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    AuthAssertion basicAuth = WSUtil.getBasicAuth(request);
    
    String sMessageID     = null;
    String sNsURIEnvelope = null;
    String requestName    = null;
    byte[] soapRequest    = null;
    try {
      SOAPMessage soapMessage = WSUtil.getSOAPMessage(request);
      
      sMessageID = WSUtil.getMessageID(soapMessage);
      
      sNsURIEnvelope = WSUtil.getNamespaceURIEnvelope(soapMessage);
      
      requestName = WSUtil.getRequestName(soapMessage);
      
      soapRequest = WSUtil.getSOAPPartContent(soapMessage);
    }
    catch(Exception ex) {
      ex.printStackTrace();
      WSUtil.sendFault(response, sNsURIEnvelope, 0, "Invalid request", null);
      return;
    }
    
    if(!REQUEST_NAME.equalsIgnoreCase(requestName)) {
      WSUtil.sendFault(response, sNsURIEnvelope, 1, "Invalid " + REQUEST_NAME, null);
      return;
    }
    
    AdhocQueryRequest adhocQueryRequest = null;
    try {
      AQRContentHandler aqrContentHandler = new AQRContentHandler();
      aqrContentHandler.load(soapRequest);
      adhocQueryRequest = aqrContentHandler.getAdhocQueryRequest();
    }
    catch(Exception ex) {
      WSUtil.sendFault(response, sNsURIEnvelope, 2, "Invalid " + REQUEST_NAME, null);
      return;
    }
    
    if(adhocQueryRequest == null) adhocQueryRequest = new AdhocQueryRequest();
    adhocQueryRequest.setServicePath(request.getServletPath());
    
    List<AuthAssertion> listOfAssertion = null;
    try {
      AuthContentHandler samlContentHandler = new AuthContentHandler();
      samlContentHandler.load(soapRequest);
      listOfAssertion = samlContentHandler.getListOfAssertion();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    
    if(listOfAssertion != null) {
      for(int i = 0; i < listOfAssertion.size(); i++) {
        AuthAssertion assertion = listOfAssertion.get(i);
        if(assertion.isSigned()) {
          if(!assertion.verifySignature()) {
            WSUtil.sendFault(response, sNsURIEnvelope, 3, "Invalid Assertion", null);
            return;
          }
        }
      }
    }
    
    AdhocQueryResponse adhocQueryResponse = null;
    try {
      IXDSb xdsb = ServicesFactory.getXDSbInstance(handlerClass);
      
      adhocQueryResponse = xdsb.registryStoredQuery(adhocQueryRequest, WSUtil.toArray(basicAuth, listOfAssertion));
      
      if(adhocQueryRequest.checkReturnTypeObjectRef()) {
        adhocQueryResponse.setObjectRefList(true);
      }
    }
    catch(Exception ex) {
      WSUtil.sendFault(response, sNsURIEnvelope, 4, ex.getMessage(), null);
      return;
    }
    
    if(adhocQueryResponse == null) adhocQueryResponse = new AdhocQueryResponse();
    
    WSUtil.sendResponse(response, adhocQueryResponse.toXML(null), sNsURIEnvelope, "urn:ihe:iti:2007:RegistryStoredQueryResponse", sMessageID);
  }
}