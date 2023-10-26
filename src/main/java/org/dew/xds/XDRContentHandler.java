package org.dew.xds;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import org.dew.xds.util.Base64Coder;

public
class XDRContentHandler implements ContentHandler
{
  protected String sCurrentTag;
  protected String sCurrentValue;
  protected Stack<String> stackElements;
  
  protected String homeCommunityId;
  protected String repositoryUniqueId;
  protected String documentUniqueId;
  protected String mimeType;
  protected byte[] document;
  
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
  XDSDocumentRequest getXDSDocumentRequest()
  {
    return new XDSDocumentRequest(homeCommunityId, repositoryUniqueId, documentUniqueId);
  }
  
  public
  XDSDocumentResponse getXDSDocumentResponse()
  {
    XDSDocumentResponse xdsDocumentResponse = new XDSDocumentResponse(homeCommunityId, repositoryUniqueId, documentUniqueId);
    if(mimeType != null && mimeType.length() > 0) {
      xdsDocumentResponse.setMimeType(mimeType);
    }
    if(document != null && document.length > 0) {
      xdsDocumentResponse.setDocument(document);
    }
    return xdsDocumentResponse;
  }
  
  public
  void startDocument()
    throws SAXException
  {
    stackElements = new Stack<String>();
    
    homeCommunityId    = null;
    repositoryUniqueId = null;
    documentUniqueId   = null;
    mimeType           = null;
    document           = null;
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
  }
  
  public
  void endElement(String uri, String localName, String qName)
    throws SAXException
  {
    if(localName.equalsIgnoreCase("HomeCommunityId")) {
      homeCommunityId = sCurrentValue;
    }
    else if(localName.equalsIgnoreCase("RepositoryUniqueId")) {
      repositoryUniqueId = sCurrentValue;
    }
    else if(localName.equalsIgnoreCase("DocumentUniqueId")) {
      documentUniqueId = sCurrentValue;
    }
    else if(localName.equalsIgnoreCase("mimeType")) {
      mimeType = sCurrentValue;
    }
    else if(localName.equalsIgnoreCase("Document")) {
      String sBase64 = sCurrentValue != null ? sCurrentValue.trim() : "";
      if(sBase64 != null && sBase64.length() > 3) {
        try{ document = Base64Coder.decodeLines(sCurrentValue); } catch(Throwable th) {} 
      }
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