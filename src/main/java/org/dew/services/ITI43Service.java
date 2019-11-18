package org.dew.services;

import java.io.IOException;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.soap.SOAPMessage;

import org.dew.auth.AuthAssertion;
import org.dew.auth.AuthContentHandler;

import org.dew.ebxml.rs.RegistryResponse;

import org.dew.xds.IXDSb;
import org.dew.xds.XDRContentHandler;
import org.dew.xds.XDSDocumentRequest;
import org.dew.xds.XDSDocumentResponse;

import org.dew.xds.util.WSUtil;

public
class ITI43Service extends HttpServlet
{
	private static final long serialVersionUID = -2213010474919954474L;
	
	protected static final String REQUEST_NAME = "RetrieveDocumentSetRequest";
	
	protected boolean TRANSMISSION_USING_MTOM_XOP = false;
	
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
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/wsdl/xds-iti43.jsp");
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
		
		XDSDocumentRequest xdsDocumentRequest = null;
		try {
			XDRContentHandler xdrContentHandler = new XDRContentHandler();
			xdrContentHandler.load(soapRequest);
			xdsDocumentRequest = xdrContentHandler.getXDSDocumentRequest();
		}
		catch(Exception ex) {
			WSUtil.sendFault(response, sNsURIEnvelope, 2, "Invalid " + REQUEST_NAME, null);
			return;
		}
		
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
		
		XDSDocumentResponse xdsDocumentResponse = null;
		try {
			IXDSb xdsb = ServicesFactory.getXDSbInstance(handlerClass);
			
			xdsDocumentResponse = xdsb.retrieveDocumentSet(xdsDocumentRequest, WSUtil.toArray(basicAuth, listOfAssertion));
		}
		catch(Exception ex) {
			WSUtil.sendFault(response, sNsURIEnvelope, 4, ex.getMessage(), null);
			return;
		}
		
		String sAttContentId   = null;
		String sAttContentType = null;
		byte[] abAttContent    = null;
		
		StringBuilder sbResponse = new StringBuilder();
		if(xdsDocumentResponse.getDocument() == null) {
			String codeContext = "Document not found";
			RegistryResponse registryResponse = xdsDocumentResponse.getRegistryResponse();
			if(registryResponse != null && registryResponse.getFirstErrorMessage() != null) {
				codeContext = registryResponse.getFirstErrorMessage();
			}
			sbResponse.append("<ns2:RetrieveDocumentSetResponse xmlns:ns6=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns2=\"urn:ihe:iti:xds-b:2007\">");
			sbResponse.append("<ns4:RegistryResponse status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure\"/>");
			sbResponse.append("<ns4:RegistryErrorList>");
			sbResponse.append("<ns4:RegistryError codeContext=\"" + codeContext + "\" errorCode=\"DNF\" severity=\"urn:oasis:names:tc:ebxml-regrep:ErrorSeverityType:Error\">");
			sbResponse.append("</ns4:RegistryError>");
			sbResponse.append("</ns4:RegistryErrorList>");
			sbResponse.append("</ns2:RetrieveDocumentSetResponse>");
		}
		else {
			xdsDocumentResponse.setEnclosedDocument(!TRANSMISSION_USING_MTOM_XOP);
			
			if(TRANSMISSION_USING_MTOM_XOP) {
				String sXopIncludeHref = xdsDocumentResponse.getXopIncludeHref();
				if(sXopIncludeHref != null && sXopIncludeHref.length() > 0) {
					sAttContentId = URLDecoder.decode(sXopIncludeHref, "UTF-8");
					if(sAttContentId.startsWith("cid:") && sAttContentId.length() > 4) {
						sAttContentId = sAttContentId.substring(4);
					}
				}
				sAttContentType = xdsDocumentResponse.getMimeType();
				abAttContent    = xdsDocumentResponse.getDocument();
			}
			
			sbResponse.append("<ns2:RetrieveDocumentSetResponse xmlns:ns6=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\" xmlns:ns5=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns2=\"urn:ihe:iti:xds-b:2007\">");
			sbResponse.append("<ns4:RegistryResponse status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/>");
			sbResponse.append(xdsDocumentResponse.toXML("ns2"));
			sbResponse.append("</ns2:RetrieveDocumentSetResponse>");
		}
		
		if(TRANSMISSION_USING_MTOM_XOP) {
			WSUtil.sendResponse(response, sbResponse, sNsURIEnvelope, sAttContentId, sAttContentType, abAttContent);
		}
		else {
			WSUtil.sendResponse(response, sbResponse.toString(), sNsURIEnvelope, "urn:ihe:iti:2007:RetrieveDocumentSetResponse", sMessageID);
		}
	}
}