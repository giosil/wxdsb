package org.dew.auth;

import java.io.ByteArrayInputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import org.dew.ebxml.Utils;

import org.dew.xds.util.Base64Coder;

public
class AuthContentHandler implements ContentHandler
{
  protected String sCurrentTag;
  protected String sCurrentValue;
  protected Stack<String> stackElements;
  
  protected String              xmlBinSecToken;
  protected List<String>        listOfXmlSource;
  protected List<AuthAssertion> listOfAssertion;
  
  protected String attributeName;
  protected String listName;
  protected int    indexListItem;
  protected SAMLAssertion          assertion;
  protected WSSBinarySecurityToken wssBinarySecurityToken;
  protected WSSUsernameToken       wssUsernameToken;
  
  public
  void load(byte[] content)
    throws Exception
  {
    listOfXmlSource = AuthUtil.extractTags(content, "Assertion");
    xmlBinSecToken  = AuthUtil.extractTag(content,  "Security", "BinarySecurityToken");
    
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
      byte[] content  = sFile.getBytes();
      listOfXmlSource = AuthUtil.extractTags(content, "Assertion");
      xmlBinSecToken  = AuthUtil.extractTag(content,  "Security", "BinarySecurityToken");
      
      inputSource = new InputSource(new ByteArrayInputStream(content));
    }
    else {
      byte[] content  = AuthUtil.readFile(sFile);
      listOfXmlSource = AuthUtil.extractTags(content, "Assertion");
      xmlBinSecToken  = AuthUtil.extractTag(content,  "Security", "BinarySecurityToken");
      
      inputSource = new InputSource(new ByteArrayInputStream(content));
    }
    XMLReader xmlReader = XMLReaderFactory.createXMLReader();
    xmlReader.setContentHandler(this);
    xmlReader.parse(inputSource);
  }
  
  public
  List<AuthAssertion> getListOfAssertion()
  {
    return listOfAssertion;
  }
  
  public
  AuthAssertion getFirstAssertion()
  {
    if(listOfAssertion == null || listOfAssertion.size() == 0) {
      return null;
    }
    return listOfAssertion.get(0);
  }
  
  public
  AuthAssertion[] getArrayOfAssertion()
  {
    if(listOfAssertion == null || listOfAssertion.size() == 0) {
      return new AuthAssertion[0];
    }
    AuthAssertion[] result = new AuthAssertion[listOfAssertion.size()];
    for(int i = 0; i < listOfAssertion.size(); i++) {
      result[i] = listOfAssertion.get(i);
    }
    return result;
  }
  
  public
  void startDocument()
    throws SAXException
  {
    stackElements   = new Stack<String>();
    listOfAssertion = new ArrayList<AuthAssertion>();
    attributeName   = null;
    listName        = null;
    indexListItem   = 0;
    assertion       = null;
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
    
    if(localName.equals("Assertion")) {
      assertion = new SAMLAssertion();
      for(int i = 0; i < attributes.getLength(); i++) {
        String sAttrLocalName = attributes.getLocalName(i);
        if(sAttrLocalName.equalsIgnoreCase("ID")) {
          assertion.setId(attributes.getValue(i));
        }
        else
          if(sAttrLocalName.equalsIgnoreCase("IssueInstant")) {
            assertion.setIssueInstant(Utils.toDate(attributes.getValue(i)));
          }
      }
      attributeName = null;
      listName      = null;
      indexListItem = 0;
    }
    else if(localName.equals("Conditions")) {
      for(int i = 0; i < attributes.getLength(); i++) {
        String sAttrLocalName = attributes.getLocalName(i);
        if(sAttrLocalName.equalsIgnoreCase("NotBefore")) {
          Calendar calNotBefore = Utils.stringToCalendar(attributes.getValue(i));
          if(calNotBefore != null && assertion != null) {
            assertion.setNotBefore(calNotBefore.getTime());
          }
        }
        else
          if(sAttrLocalName.equalsIgnoreCase("NotOnOrAfter")) {
            Calendar calNotOnOrAfter = Utils.stringToCalendar(attributes.getValue(i));
            if(calNotOnOrAfter != null && assertion != null) {
              assertion.setNotOnOrAfter(calNotOnOrAfter.getTime());
            }
          }
      }
    }
    else if(localName.equals("Attribute")) {
      for(int i = 0; i < attributes.getLength(); i++) {
        String sAttrLocalName = attributes.getLocalName(i);
        if(sAttrLocalName.equalsIgnoreCase("Name") || sAttrLocalName.equalsIgnoreCase("AttributeName")) {
          attributeName = attributes.getValue(i);
        }
      }
    }
    else if(localName.equals("AttributeValue")) {
      if(attributeName == null) {
        listName = null;
      }
      else if(attributeName.indexOf("List") >= 0 || attributeName.indexOf("list") >= 0) {
        if(attributeName.equals(listName)) {
          indexListItem++;
        }
        else {
          listName = attributeName;
          indexListItem = 0;
        }
      }
      else {
        listName = null;
      }
    }
    else if(localName.equals("CF")) {
      if(assertion.getClass().equals(SAMLAssertion.class)) {
        assertion = new SAMLIdentAssertion(assertion);
      }
      if(attributeName != null) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          String sAttrValue     = attributes.getValue(i);
          if(attributeName.equals(listName)) {
            assertion.setAttribute(attributeName + "_" + indexListItem + "_" + sAttrLocalName, sAttrValue);
          }
          else {
            assertion.setAttribute(attributeName + "_" + sAttrLocalName, sAttrValue);
          }
        }
      }
    }
    else if(localName.equals("RDA")) {
      if(assertion.getClass().equals(SAMLAssertion.class)) {
        assertion = new SAMLIdentAssertion(assertion);
      }
      if(attributeName != null) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          String sAttrValue     = attributes.getValue(i);
          if(attributeName.equals(listName)) {
            assertion.setAttribute(attributeName + "_" + indexListItem + "_" + sAttrLocalName, sAttrValue);
          }
          else {
            assertion.setAttribute(attributeName + "_" + sAttrLocalName, sAttrValue);
          }
        }
      }
    }
    else if(sCurrentTag.endsWith("security|usernametoken")) {
      if(wssUsernameToken == null) {
        wssUsernameToken = new WSSUsernameToken();
        listOfAssertion.add(wssUsernameToken);
      }
      for(int i = 0; i < attributes.getLength(); i++) {
        String sAttrLocalName = attributes.getLocalName(i);
        String sAttrValue     = attributes.getValue(i);
        if("id".equalsIgnoreCase(sAttrLocalName)) {
          wssUsernameToken.setId(sAttrValue);
        }
      }
    }
  }
  
  public
  void endElement(String uri, String localName, String qName)
    throws SAXException
  {
    if(sCurrentTag.endsWith("|issuer")) {
      if(assertion != null) {
        assertion.setIssuer(sCurrentValue);
      }
    }
    else if(sCurrentTag.endsWith("|subject|nameid")) {
      if(assertion != null) {
        assertion.setSubjectId(sCurrentValue);
      }
    }
    else if(sCurrentTag.endsWith("|attributestatement|attribute|attributevalue")) {
      if(assertion != null && attributeName != null && attributeName.length() > 0) {
        if(attributeName.startsWith("urn:oasis:names:tc:xacml") || attributeName.startsWith("urn:oasis:names:tc:xspa")) {
          if(assertion.getClass().equals(SAMLAssertion.class)) {
            assertion = new SAMLAttributeAssertion(assertion);
          }
        }
        if(attributeName.equals(listName)) {
          assertion.setAttribute(attributeName + "_" + indexListItem, sCurrentValue);
        }
        else {
          assertion.setAttribute(attributeName, sCurrentValue);
        }
      }
    }
    else if(sCurrentTag.endsWith("assertion|signature|signedinfo|reference|digestvalue")) {
      if(assertion != null && sCurrentValue.length() > 1) {
        try{ assertion.setDigestValue(Base64Coder.decode(sCurrentValue)); } catch(Exception ex) {}
      }
    }
    else if(sCurrentTag.endsWith("assertion|signature|signaturevalue")) {
      if(assertion != null && sCurrentValue.length() > 1) {
        try{ assertion.setSignatureValue(Base64Coder.decodeLines(sCurrentValue)); } catch(Exception ex) {}
      }
    }
    else if(sCurrentTag.endsWith("assertion|signature|keyinfo|x509data|x509certificate")) {
      if(assertion != null && sCurrentValue.length() > 1) {
        try{ assertion.setCertificate(AuthUtil.getX509Certificate(sCurrentValue)); } catch(Exception ex) {}
      }
    }
    else if(sCurrentTag.endsWith("security|timestamp|created")) {
      if(wssBinarySecurityToken == null) wssBinarySecurityToken = new WSSBinarySecurityToken();
      wssBinarySecurityToken.setCreated(Utils.toDate(sCurrentValue));
    }
    else if(sCurrentTag.endsWith("security|timestamp|expires")) {
      if(wssBinarySecurityToken == null) wssBinarySecurityToken = new WSSBinarySecurityToken();
      wssBinarySecurityToken.setExpires(Utils.toDate(sCurrentValue));
    }
    else if(sCurrentTag.endsWith("security|binarysecuritytoken")) {
      if(wssBinarySecurityToken == null) wssBinarySecurityToken = new WSSBinarySecurityToken();
      try{ wssBinarySecurityToken.setCertificate(AuthUtil.getX509Certificate(sCurrentValue)); } catch(Exception ex) {}
      if(xmlBinSecToken != null && xmlBinSecToken.length() > 2) {
        wssBinarySecurityToken.setXmlSource(xmlBinSecToken);
      }
      listOfAssertion.add(wssBinarySecurityToken);
    }
    else if(sCurrentTag.endsWith("security|usernametoken|username")) {
      if(wssUsernameToken == null) {
        wssUsernameToken = new WSSUsernameToken();
        listOfAssertion.add(wssUsernameToken);
      }
      wssUsernameToken.setSubjectId(sCurrentValue);
    }
    else if(sCurrentTag.endsWith("security|usernametoken|password")) {
      if(wssUsernameToken != null) wssUsernameToken.setPassword(sCurrentValue);
    }
    else if(sCurrentTag.endsWith("security|usernametoken|nonce")) {
      if(wssUsernameToken != null) wssUsernameToken.setNonce(sCurrentValue);
    }
    else if(sCurrentTag.endsWith("security|usernametoken|created")) {
      if(wssUsernameToken != null) wssUsernameToken.setCreated(Utils.toDate(sCurrentValue));
    }
    else if(localName.equals("Assertion")) {
      if(assertion != null) {
        assertion.checkAttributes();
        if(listOfXmlSource != null && listOfXmlSource.size() > listOfAssertion.size()) {
          assertion.setXmlSource(listOfXmlSource.get(listOfAssertion.size()));
        }
        listOfAssertion.add(assertion);
      }
      assertion = null;
      attributeName = null;
    }
    else if(localName.equals("Attribute")) {
      attributeName = null;
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
