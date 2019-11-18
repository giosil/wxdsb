package org.dew.services;

import java.io.IOException;

import java.net.URLDecoder;

import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.soap.SOAPMessage;

import org.dew.auth.AuthAssertion;
import org.dew.auth.AuthContentHandler;

import org.dew.ebxml.Association;
import org.dew.ebxml.ExtrinsicObject;
import org.dew.ebxml.RIMContentHandler;
import org.dew.ebxml.RegistryObjectList;
import org.dew.ebxml.RegistryPackage;
import org.dew.ebxml.rs.RegistryResponse;

import org.dew.xds.IXDSb;
import org.dew.xds.XDSDocument;

import org.dew.xds.util.WSUtil;

public
class ITI41Service extends HttpServlet
{
	private static final long serialVersionUID = -7293806358639988942L;
	
	protected static final String REQUEST_NAME = "ProvideAndRegisterDocumentSetRequest";
	
	protected String handlerClass = null;
	
	protected boolean TRANSMISSION_USING_MTOM_XOP = false;
	
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
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/wsdl/xds-iti41.jsp");
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
		Map<String, byte[]> attachments = null;
		try {
			SOAPMessage soapMessage = WSUtil.getSOAPMessage(request);
			
			sMessageID = WSUtil.getMessageID(soapMessage);
			
			sNsURIEnvelope = WSUtil.getNamespaceURIEnvelope(soapMessage);
			
			requestName = WSUtil.getRequestName(soapMessage);
			
			soapRequest = WSUtil.getSOAPPartContent(soapMessage);
			
			attachments = WSUtil.getAttachments(soapMessage);
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
		
		List<ExtrinsicObject>       listOfExtrinsicObject = null;
		Map<String,RegistryPackage> mapRegistryPackages   = null;
		Map<String,Association>     mapAssociations       = null;
		try {
			RIMContentHandler rimContentHandler = new RIMContentHandler();
			rimContentHandler.load(soapRequest);
			RegistryObjectList registryObjectList = rimContentHandler.getRegistryObjectList();
			if(registryObjectList != null) {
				listOfExtrinsicObject = registryObjectList.getExtrinsicObjects();
				mapRegistryPackages   = registryObjectList.getRegistryPackagesMap();
				mapAssociations       = registryObjectList.getAssociationsMap();
			}
		}
		catch(Exception ex) {
			WSUtil.sendFault(response, sNsURIEnvelope, 2, "Invalid " + REQUEST_NAME, null);
			return;
		}
		
		if(listOfExtrinsicObject == null || listOfExtrinsicObject.size() == 0) {
			WSUtil.sendFault(response, sNsURIEnvelope, 2, "Invalid " + REQUEST_NAME, null);
			return;
		}
		
		XDSDocument[] arrayOfXDSDocument = new XDSDocument[listOfExtrinsicObject.size()];
		for(int i = 0; i < listOfExtrinsicObject.size(); i++) {
			ExtrinsicObject extrinsicObject = listOfExtrinsicObject.get(i);
			
			XDSDocument xdsDocument = new XDSDocument(extrinsicObject);
			xdsDocument.setServicePath(request.getServletPath());
			
			String registryObjectId = extrinsicObject.getId();
			if(registryObjectId != null) {
				RegistryPackage registryPackage = mapRegistryPackages.get(registryObjectId);
				xdsDocument.setRegistryPackage(registryPackage);
				
				Association association = mapAssociations.get(registryObjectId);
				xdsDocument.setAssociation(association);
			}
			
			String sXopIncludeHref  = extrinsicObject.getXopIncludeHref();
			if(sXopIncludeHref == null) continue;
			if(sXopIncludeHref.startsWith("cid:") && sXopIncludeHref.length() > 4) {
				sXopIncludeHref = sXopIncludeHref.substring(4);
			}
			String sAttContentId = "<" + URLDecoder.decode(sXopIncludeHref, "UTF-8") + ">";
			xdsDocument.setContent(attachments.get(sAttContentId));
			arrayOfXDSDocument[i] = xdsDocument;
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
		
		RegistryResponse result;
		try {
			IXDSb xdsb = ServicesFactory.getXDSbInstance(handlerClass);
			
			result = xdsb.provideAndRegisterDocumentSet(arrayOfXDSDocument, WSUtil.toArray(basicAuth, listOfAssertion));
			
			if(result == null) result = new RegistryResponse(false);
		}
		catch(Exception ex) {
			WSUtil.sendFault(response, sNsURIEnvelope, 4, ex.getMessage(), null);
			return;
		}
		
		StringBuilder sbResponse = new StringBuilder();
		sbResponse.append(result.toXML(null));
		
		if(TRANSMISSION_USING_MTOM_XOP) {
			WSUtil.sendResponse(response, sbResponse, sNsURIEnvelope, null, null, null);
		}
		else {
			WSUtil.sendResponse(response, sbResponse.toString(), sNsURIEnvelope, "urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-bResponse", sMessageID);
		}
	}
}
