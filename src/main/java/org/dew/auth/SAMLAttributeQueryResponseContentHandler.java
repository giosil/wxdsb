package org.dew.auth;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public
class SAMLAttributeQueryResponseContentHandler implements ContentHandler
{
	protected String sCurrentTag;
	protected String sCurrentValue;
	protected Stack<String> stackElements;
	
	protected SAMLAttributeQueryResponse response;
	
	protected String faultCode;
	protected String faultString;
	protected String faultDetail;
	
	public
	void load(byte[] content)
			throws Exception
	{
		InputSource inputSource = new InputSource(new ByteArrayInputStream(content));
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
		xmlReader.setContentHandler(this);
		xmlReader.parse(inputSource);
	}
	
	public
	void load(String sFile)
			throws Exception
	{
		int iTag = sFile.indexOf('<');
		InputSource inputSource = null;
		if(iTag >= 0) {
			inputSource = new InputSource(new ByteArrayInputStream(sFile.getBytes()));
		}
		else {
			inputSource = new InputSource(new FileInputStream(sFile));
		}
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
		xmlReader.setContentHandler(this);
		xmlReader.parse(inputSource);
	}
	
	public
	SAMLAttributeQueryResponse getResponse() 
	{
		return response;
	}

	public
	String getFaultCode() 
	{
		return faultCode;
	}

	public
	String getFaultString() 
	{
		return faultString;
	}

	public
	String getFaultDetail() 
	{
		return faultDetail;
	}

	public
	String getFault() 
	{
		boolean boFaultCodeEmpty   = faultCode   == null || faultCode.length()   == 0;
		boolean boFaultStringEmpty = faultString == null || faultString.length() == 0;
		if(boFaultCodeEmpty && boFaultStringEmpty) {
			return null;
		}
		String sFault = "";
		if(faultCode != null && faultCode.length() > 0) {
			sFault += "[" + faultCode + "] ";
		}
		if(faultString != null && faultString.length() > 0) {
			sFault += faultString;
		}
		if(faultDetail != null && faultDetail.length() > 0) {
			sFault += " - " + faultDetail;
		}
		return sFault;
	}
	
	public
	void startDocument()
		throws SAXException
	{
		stackElements = new Stack<String>();
		
		response      = null;
		faultCode     = null;
		faultString   = null;
		faultDetail   = null;
	}
	
	public
	void endDocument()
		throws SAXException
	{
	}
	
	public
	void startElement(String uri, String localName, String qName, Attributes attributes)
		throws SAXException
	{
		stackElements.push(localName);
		int iStackSize = stackElements.size();
		sCurrentTag = "";
		for (int i = 0; i < iStackSize; i++) {
			sCurrentTag += "|" + stackElements.get(i);
		}
		sCurrentTag   = sCurrentTag.substring(1).toLowerCase();
		sCurrentValue = "";
		
		if(sCurrentTag.endsWith("|response")) {
			String id = null;
			for(int i = 0; i < attributes.getLength(); i++) {
				String sAttrLocalName = attributes.getLocalName(i);
				if(sAttrLocalName.equalsIgnoreCase("ID")) {
					id = attributes.getValue(i);
				}
			}
			if(response == null) response = new SAMLAttributeQueryResponse();
			response.setId(id);
		}
		else
			if(sCurrentTag.endsWith("|response|status|statuscode")) {
				String statusCode = null;
				for(int i = 0; i < attributes.getLength(); i++) {
					String sAttrLocalName = attributes.getLocalName(i);
					if(sAttrLocalName.equalsIgnoreCase("Value")) {
						statusCode = attributes.getValue(i);
					}
				}
				if(response == null) response = new SAMLAttributeQueryResponse();
				response.setStatusCode(statusCode);
			}
	}
	
	public
	void endElement(String uri, String localName, String qName)
		throws SAXException
	{
		if(sCurrentTag.endsWith("|response|status|statusmessage")) {
			if(response == null) response = new SAMLAttributeQueryResponse();
			response.setStatusMessage(sCurrentValue);
		}
		else
		if(localName.equalsIgnoreCase("faultcode")) {
			this.faultCode = sCurrentValue;
		}
		else
		if(localName.equalsIgnoreCase("faultstring")) {
			this.faultString = sCurrentValue;
		}
		else
		if(localName.equalsIgnoreCase("detail")) {
			if(sCurrentValue != null && sCurrentValue.length() > 0) {
				this.faultDetail = sCurrentValue;
			}
		}
		else
		if(localName.equalsIgnoreCase("detailEntry")) {
			if(sCurrentValue != null && sCurrentValue.length() > 0) {
				this.faultDetail = sCurrentValue;
			}
		}
		else
		if(sCurrentTag.endsWith("fault|code|value")) {
			this.faultCode = sCurrentValue;
		}
		else
		if(sCurrentTag.endsWith("fault|reason|text")) {
			this.faultString = sCurrentValue;
		}
		
		if(!stackElements.isEmpty()) stackElements.pop();
		sCurrentTag = "";
		for (int i = 0; i < stackElements.size(); i++) {
			sCurrentTag += "|" + stackElements.get(i);
		}
		sCurrentTag = sCurrentTag.length() > 0 ? sCurrentTag.substring(1) : "";
		sCurrentTag = sCurrentTag.toLowerCase();
	}
	
	public
	void characters(char[] ch, int start, int length)
		throws SAXException
	{
		sCurrentValue += new String(ch, start, length);
	}
	
	public void setDocumentLocator(Locator locator) {}
	public void startPrefixMapping(String prefix, String uri) throws SAXException {}
	public void endPrefixMapping(String prefix) throws SAXException {}
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
	public void processingInstruction(String target, String data) throws SAXException {}
	public void skippedEntity(String name) throws SAXException {}
}

