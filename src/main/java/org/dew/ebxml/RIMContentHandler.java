package org.dew.ebxml;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.*;

import org.dew.xds.util.Base64Coder;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public
class RIMContentHandler implements ContentHandler
{
  protected String sCurrentTag;
  protected String sCurrentValue;
  protected Stack<String> stackElements;
  
  protected List<Identifiable> listOfIdentifiable = new ArrayList<Identifiable>();
  
  protected RegistryObject registryObject;
  protected Slot slot;
  protected Classification classification;
  protected ExternalIdentifier externalIdentifier;
  protected ObjectRef objectRef;
  protected String documentId;
  
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
  RegistryObjectList getRegistryObjectList()
  {
    return new RegistryObjectList(listOfIdentifiable);
  }
  
  public 
  ObjectRefList getObjectRefList() 
  {
    ObjectRefList result = new ObjectRefList();
    if(listOfIdentifiable == null || listOfIdentifiable.size() == 0) {
      return result;
    }
    for(Identifiable identifiable : listOfIdentifiable) {
      if(identifiable instanceof ObjectRef) {
        result.addObjectRef((ObjectRef) identifiable); 
      }
    }
    return result;
  }
  
  public
  void startDocument()
    throws SAXException
  {
    stackElements = new Stack<String>();
    
    listOfIdentifiable = new ArrayList<Identifiable>();
    
    registryObject     = null;
    
    slot               = null;
    classification     = null;
    externalIdentifier = null;
    objectRef          = null;
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
    if(localName.equals("ExtrinsicObject")) {
      registryObject = new ExtrinsicObject();
      element = registryObject;
      classification = null;
      externalIdentifier = null;
      slot = null;
      objectRef = null;
    }
    else if(localName.equals("Classification")) {
      classification = new Classification();
      element = classification;
      if(registryObject == null) {
        registryObject = classification;
      }
    }
    else if(localName.equals("ExternalIdentifier")) {
      externalIdentifier = new ExternalIdentifier();
      element = externalIdentifier;
      if(registryObject == null) {
        registryObject = externalIdentifier;
      }
    }
    else if(localName.equals("Association")) {
      registryObject = new Association();
      element = registryObject;
    }
    else if(localName.equals("RegistryPackage")) {
      registryObject = new RegistryPackage();
      element = registryObject;
    }
    else if(localName.equals("Slot")) {
      slot = new Slot();
      element = slot;
    }
    else if(localName.equals("ObjectRef")) {
      objectRef = new ObjectRef();
      element = objectRef;
    }
    else if(localName.equals("VersionInfo")) {
      if(classification != null) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          if(sAttrLocalName.equals("versionName")) {
            classification.setVersionInfo(attributes.getValue(i));
          }
        }
      }
      else if(externalIdentifier != null) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          if(sAttrLocalName.equals("versionName")) {
            externalIdentifier.setVersionInfo(attributes.getValue(i));
          }
        }
      }
      else if(registryObject != null) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          if(sAttrLocalName.equals("versionName")) {
            registryObject.setVersionInfo(attributes.getValue(i));
          }
        }
      }
    }
    else if(localName.equals("LocalizedString")) {
      if(classification != null) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          if(sAttrLocalName.equals("value")) {
            classification.setName(attributes.getValue(i));
          }
        }
      }
      else if(externalIdentifier != null) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          if(sAttrLocalName.equals("value")) {
            externalIdentifier.setName(attributes.getValue(i));
          }
        }
      }
      else if(registryObject != null) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          if(sAttrLocalName.equals("value")) {
            registryObject.setName(attributes.getValue(i));
          }
        }
      }
    }
    else if(localName.equals("ContentVersionInfo")) {
      if(registryObject instanceof ExtrinsicObject) {
        for(int i = 0; i < attributes.getLength(); i++) {
          String sAttrLocalName = attributes.getLocalName(i);
          if(sAttrLocalName.equals("versionName")) {
            ((ExtrinsicObject) registryObject).setContentVersionInfo(attributes.getValue(i));
          }
        }
      }
    }
    else if(localName.equalsIgnoreCase("Document")) {
      for(int i = 0; i < attributes.getLength(); i++) {
        String sAttrLocalName = attributes.getLocalName(i);
        if(sAttrLocalName.equals("id")) {
          documentId = attributes.getValue(i);
        }
      }
    }
    else if(sCurrentTag.endsWith("|document|include")) {
      for(int i = 0; i < attributes.getLength(); i++) {
        String sAttrLocalName = attributes.getLocalName(i);
        if(sAttrLocalName.equals("href")) {
          String sXopIncludeHref = attributes.getValue(i);
          if(sXopIncludeHref != null && sXopIncludeHref.startsWith("cid:") && sXopIncludeHref.length() > 4) {
            sXopIncludeHref = sXopIncludeHref.substring(4);
          }
          if(documentId != null && documentId.length() > 0) {
            for(Identifiable identifiable : listOfIdentifiable) {
              if(identifiable instanceof RegistryObject) {
                if(documentId.equals(identifiable.getId())) {
                  ((RegistryObject) identifiable).setXopIncludeHref(sXopIncludeHref);
                }
              }
            }
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
    else if(localName.equals("ExtrinsicObject")) {
      if(registryObject instanceof ExtrinsicObject) {
        listOfIdentifiable.add(registryObject);
        registryObject = null;
      }
    }
    else if(localName.equals("Slot")) {
      if(slot != null) {
        if(classification != null) {
          classification.addSlot(slot);
        }
        else if(externalIdentifier != null) {
          externalIdentifier.addSlot(slot);
        }
        else if(registryObject != null) {
          registryObject.addSlot(slot);
        }
      }
      slot = null;
    }
    else if(localName.equals("Classification")) {
      if(registryObject instanceof Classification) {
        listOfIdentifiable.add(registryObject);
        registryObject = null;
      }
      else if(classification != null && registryObject != null) {
        registryObject.addClassification(classification);
      }
      classification = null;
    }
    else if(localName.equals("ExternalIdentifier")) {
      if(registryObject instanceof ExternalIdentifier) {
        listOfIdentifiable.add(registryObject);
        registryObject = null;
      }
      else if(externalIdentifier != null && registryObject != null) {
        registryObject.addExternalIdentifier(externalIdentifier);
      }
      externalIdentifier = null;
    }
    else if(localName.equals("Association")) {
      if(registryObject instanceof Association) {
        listOfIdentifiable.add(registryObject);
        registryObject = null;
      }
    }
    else if(localName.equals("RegistryPackage")) {
      if(registryObject instanceof RegistryPackage) {
        listOfIdentifiable.add(registryObject);
        registryObject = null;
      }
    }
    else if(localName.equals("ObjectRef")) {
      if(objectRef != null) {
        listOfIdentifiable.add(objectRef);
      }
      objectRef = null;
    }
    else if(localName.equals("Document")) {
      if(sCurrentValue != null && sCurrentValue.length() > 0) {
        byte[] content = null;
        try {
          content = Base64Coder.decode(sCurrentValue.trim());
        }
        catch(Exception ex) {
          System.err.println("[RIMContentHandler] Exception in decode Document: " + ex);
        }
        if(registryObject != null) {
          registryObject.setContent(content);
        }
        else if(listOfIdentifiable != null) {
          ExtrinsicObject lastExtrinsicObject = null;
          for(int i = 0; i < listOfIdentifiable.size(); i++) {
            Identifiable item = listOfIdentifiable.get(i);
            if(item instanceof ExtrinsicObject) {
              lastExtrinsicObject = (ExtrinsicObject) item;
            }
          }
          if(lastExtrinsicObject != null) {
            lastExtrinsicObject.setContent(content);
          }
        }
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
