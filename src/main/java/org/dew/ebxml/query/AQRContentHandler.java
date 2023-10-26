package org.dew.ebxml.query;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import org.dew.ebxml.IElement;
import org.dew.ebxml.Slot;

public
class AQRContentHandler implements ContentHandler
{
  protected String sCurrentTag;
  protected String sCurrentValue;
  protected Stack<String> stackElements;
  
  protected AdhocQueryRequest adhocQueryRequest; 
  
  protected ResponseOption responseOption;
  protected AdhocQuery adhocQuery;
  protected Slot slot;
  
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
  AdhocQueryRequest getAdhocQueryRequest()
  {
    return adhocQueryRequest;
  }
  
  public
  void startDocument()
    throws SAXException
  {
    stackElements = new Stack<String>();
    
    adhocQueryRequest = new AdhocQueryRequest();
    
    responseOption = null;
    adhocQuery     = null;
    slot           = null;
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
    sCurrentTag = sCurrentTag.substring(1).toLowerCase();
    sCurrentValue = "";
    
    IElement element = null;
    if(localName.equals("ResponseOption")) {
      responseOption = new ResponseOption();
      element = responseOption;
    }
    else if(localName.equals("AdhocQuery")) {
      adhocQuery = new AdhocQuery();
      element = adhocQuery;
    }
    if(localName.equals("Slot")) {
      slot = new Slot();
      element = slot;
    }
    else if(localName.equals("LocalizedString")) {
      if(adhocQuery != null) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          if(sAttrLocalName.equals("value")) {
            adhocQuery.setName(attributes.getValue(i));
          }
        }
      }
    }
    
    if(element != null) {
      for(int i = 0; i < attributes.getLength(); i++) {
        element.setAttribute(attributes.getLocalName(i), attributes.getValue(i));
      }
    }
  }
  
  public
  void endElement(String uri, String localName, String qName)
    throws SAXException
  {
    if(sCurrentTag.endsWith("|slot|valuelist|value")) {
      if(slot != null) slot.addValue(sCurrentValue);
    }
    else if(localName.equals("ResponseOption")) {
      if(responseOption != null && adhocQueryRequest != null) {
        adhocQueryRequest.setResponseOption(responseOption);
      }
    }
    else if(localName.equalsIgnoreCase("SQLQuery")) {
      if(adhocQueryRequest != null) {
        adhocQueryRequest.setSqlQuery(sCurrentValue);
      }
    }
    else if(localName.equals("AdhocQuery")) {
      if(adhocQuery != null && adhocQueryRequest != null) {
        adhocQueryRequest.setAdhocQuery(adhocQuery);
      }
    }
    else if(localName.equals("Slot")) {
      if(slot != null) {
        if(adhocQuery != null) {
          adhocQuery.addSlot(slot);
        }
      }
      slot = null;
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
