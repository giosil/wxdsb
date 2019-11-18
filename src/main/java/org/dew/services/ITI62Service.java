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

import org.dew.ebxml.ObjectRefList;
import org.dew.ebxml.RIMContentHandler;
import org.dew.ebxml.rs.RegistryResponse;

import org.dew.xds.IXDSb;

import org.dew.xds.util.WSUtil;

public
class ITI62Service extends HttpServlet
{
	private static final long serialVersionUID = -1074870832189646609L;
	
	protected static final String REQUEST_NAME = "RemoveObjectsRequest";
	
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
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/wsdl/xds-iti62.jsp");
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
		
		ObjectRefList objectRefList = null;
		try {
			RIMContentHandler rimContentHandler = new RIMContentHandler();
			rimContentHandler.load(soapRequest);
			objectRefList = rimContentHandler.getObjectRefList();
		}
		catch(Exception ex) {
			WSUtil.sendFault(response, sNsURIEnvelope, 2, "Invalid " + REQUEST_NAME, null);
			return;
		}
		
		if(objectRefList == null) {
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
		
		RegistryResponse result;
		try {
			IXDSb xdsb = ServicesFactory.getXDSbInstance(handlerClass);
			
			result = xdsb.deleteDocumentSet(objectRefList, WSUtil.toArray(basicAuth, listOfAssertion));
			
			if(result == null) result = new RegistryResponse(false);
		}
		catch(Exception ex) {
			WSUtil.sendFault(response, sNsURIEnvelope, 4, ex.getMessage(), null);
			return;
		}
		
		WSUtil.sendResponse(response, result.toXML(null), sNsURIEnvelope, "urn:ihe:iti:xds-b:2010:XDSDeletetWS:DocumentRegistry_DeleteDocumentSetResponse", sMessageID);
	}
}