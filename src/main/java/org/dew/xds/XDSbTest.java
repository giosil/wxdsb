package org.dew.xds;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.dew.auth.AuthAssertion;
import org.dew.auth.AuthUtil;

import org.dew.ebxml.ObjectRef;
import org.dew.ebxml.ObjectRefList;
import org.dew.ebxml.Utils;
import org.dew.ebxml.query.AdhocQueryRequest;
import org.dew.ebxml.query.AdhocQueryResponse;
import org.dew.ebxml.rs.RegistryResponse;

public 
class XDSbTest implements IXDSb 
{
  protected static Map<String,List<XDSDocument>> _register = new HashMap<String,List<XDSDocument>>();
  protected static Map<String,XDSDocument>       _metadata = new HashMap<String,XDSDocument>();
  
  public 
  AdhocQueryResponse registryStoredQuery(AdhocQueryRequest request, AuthAssertion[] arrayOfAssertion) 
  {
    System.out.println("registryStoredQuery");
    String sPatientId = null;
    System.out.println("   request = " + request);
    if(request != null) {
      System.out.println("   request.getServicePath() = " + request.getServicePath());
      sPatientId = request.getAdhocQuery().getPatientId();
      System.out.println("   request.getPatientId() = " + sPatientId);
      List<String> listTypeCodes = request.getAdhocQuery().getTypeCodes();
      System.out.println("   request.getAdhocQuery().getTypeCodes() = " + listTypeCodes);
    }
    if(arrayOfAssertion == null) {
      System.out.println("   arrayOfAssertion = null");
      return new AdhocQueryResponse("Missing identification assertion");
    }
    else
    if(arrayOfAssertion.length == 0) {
      System.out.println("   arrayOfAssertion has 0 items");
      return new AdhocQueryResponse("Missing identification assertion");
    }
    else {
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        System.out.println("   " + i + ") assertion = " + arrayOfAssertion[i]);
        if(arrayOfAssertion[i].isSigned()) {
          System.out.println("      signed by " + arrayOfAssertion[i].getCertificate().getSubjectDN().getName());
        }
        else {
          System.out.println("      NOT signed.");
          // return new AdhocQueryResponse("Invalid assertion");
        }
      }
    }
    
    String patientId = null;
    if(sPatientId != null && sPatientId.length() > 0) {
      patientId = sPatientId;
    }
    else {
      patientId = "RSSMRA75C03F839K";
    }
    
    List<XDSDocument> result = null;
    
    if("RSSMRA75C03F839K".equals(patientId)) {
      result = new ArrayList<XDSDocument>();
      result.add(_buildDummyDocument(patientId, null));
    }
    else {
      result = _register.get(patientId);
    }
    
    int size = result != null ? result.size() : 0;
    XDSDocument[] arrayOfXDSDocument = new XDSDocument[size];
    for(int i = 0; i < size; i++) {
      arrayOfXDSDocument[i] = result.get(i);
    }
    
    return new AdhocQueryResponse(arrayOfXDSDocument, request);
  }
  
  public 
  RegistryResponse provideAndRegisterDocumentSet(XDSDocument[] arrayOfXDSDocument, AuthAssertion[] arrayOfAssertion) 
  {
    System.out.println("provideAndRegisterDocumentSet");
    if(arrayOfXDSDocument == null) {
      System.out.println("   arrayOfXDSDocument = null");
      return new RegistryResponse(false, "Missing metdata");
    }
    else
    if(arrayOfXDSDocument.length == 0) {
      System.out.println("   arrayOfXDSDocument has 0 items");
      return new RegistryResponse(false, "Missing metdata");
    }
    else {
      for(int i = 0; i < arrayOfXDSDocument.length; i++) {
        XDSDocument xdsDocument = arrayOfXDSDocument[i];
        if(xdsDocument == null) {
          System.out.println("   " + i + ") xdsdocument = " + xdsDocument);
        }
        else {
          _writeMetadata(xdsDocument);
          
          byte[] content = xdsDocument.getContent();
          if(content == null) {
            System.out.println("   " + i + ") xdsdocument = " + xdsDocument.toMap() + " content is null");
            return new RegistryResponse(false, "Missing document content");
          }
          else
          if(content.length == 0) {
            System.out.println("   " + i + ") xdsdocument = " + xdsDocument.toMap() + " content is empty");
            return new RegistryResponse(false, "Missing document content");
          }
          if(content.length < 100) {
            System.out.println("   " + i + ") xdsdocument = " + xdsDocument.toMap() + " (" + new String(content) + ")");
            return new RegistryResponse(false, "Invalid document content");
          }
          else {
            System.out.println("   " + i + ") xdsdocument = " + xdsDocument.toMap() + " (" + content.length + " bytes)");
          }
        }
      }
    }
    if(arrayOfAssertion == null) {
      System.out.println("   arrayOfAssertion = null");
      return new RegistryResponse(false, "Missing identification assertion");
    }
    else
    if(arrayOfAssertion.length == 0) {
      System.out.println("   arrayOfAssertion has 0 items");
      return new RegistryResponse(false, "Missing identification assertion");
    }
    else {
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        System.out.println("   " + i + ") assertion = " + arrayOfAssertion[i]);
        if(arrayOfAssertion[i].isSigned()) {
          System.out.println("      signed by " + arrayOfAssertion[i].getCertificate().getSubjectDN().getName());
        }
        else {
          System.out.println("      NOT signed.");
          // return new RegistryResponse(false, "Invalid assertion");
        }
      }
    }
    
    System.out.println("provideAndRegisterDocumentSet -> Success");
    
    return new RegistryResponse(true);
  }
  
  public 
  RegistryResponse registerDocumentSet(XDSDocument[] arrayOfXDSDocument, AuthAssertion[] arrayOfAssertion) 
  {
    System.out.println("registerDocumentSet");
    if(arrayOfXDSDocument == null) {
      System.out.println("   arrayOfXDSDocument = null");
      return new RegistryResponse(false, "Missing metdata");
    }
    else
    if(arrayOfXDSDocument.length == 0) {
      System.out.println("   arrayOfXDSDocument has 0 items");
      return new RegistryResponse(false, "Missing metdata");
    }
    else {
      for(int i = 0; i < arrayOfXDSDocument.length; i++) {
        XDSDocument xdsDocument = arrayOfXDSDocument[i];
        if(xdsDocument == null) {
          System.out.println("   " + i + ") xdsdocument = " + xdsDocument);
        }
        else {
          _writeMetadata(xdsDocument);
          
          System.out.println("   " + i + ") xdsdocument = " + xdsDocument.toMap());
          XDSPerson patient = xdsDocument.getPatient();
          if(patient != null) {
            System.out.println("      patient = " + patient.getId() + " " + patient.getFamilyName() + " " + patient.getGivenName() + " " + Utils.formatDate(patient.getDateOfBirth()) + " " + patient.getGender() + " " + patient.getAddress());
          }
          XDSPerson author = xdsDocument.getAuthor();
          if(author != null) {
            System.out.println("      author  = " + author.getId()  + " " + author.getFamilyName()  + " " + author.getGivenName() + " " + author.getPrefix() + " " + author.getEmail() + " " + author.getInstitution());
          }
        }
      }
    }
    if(arrayOfAssertion == null) {
      System.out.println("   arrayOfAssertion = null");
      return new RegistryResponse(false, "Missing identification assertion");
    }
    else
    if(arrayOfAssertion.length == 0) {
      System.out.println("   arrayOfAssertion has 0 items");
      return new RegistryResponse(false, "Missing identification assertion");
    }
    else {
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        System.out.println("   " + i + ") assertion = " + arrayOfAssertion[i]);
        if(arrayOfAssertion[i].isSigned()) {
          System.out.println("      signed by " + arrayOfAssertion[i].getCertificate().getSubjectDN().getName());
        }
        else {
          System.out.println("      NOT signed.");
          // return new RegistryResponse(false, "Invalid assertion");
        }
      }
    }
    
    System.out.println("registerDocumentSet -> Success");
    
    return new RegistryResponse(true);
  }
  
  public 
  XDSDocumentResponse retrieveDocumentSet(XDSDocumentRequest request, AuthAssertion[] arrayOfAssertion) 
  {
    System.out.println("retrieveDocumentSet");
    System.out.println("   request = " + request);
    if(arrayOfAssertion == null) {
      System.out.println("   arrayOfAssertion = null");
      return new XDSDocumentResponse(new RegistryResponse(false, "Missing identification assertion"));
    }
    else
    if(arrayOfAssertion.length == 0) {
      System.out.println("   arrayOfAssertion has 0 items");
      return new XDSDocumentResponse(new RegistryResponse(false, "Missing identification assertion"));
    }
    else {
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        System.out.println("   " + i + ") assertion = " + arrayOfAssertion[i]);
        if(arrayOfAssertion[i].isSigned()) {
          System.out.println("      signed by " + arrayOfAssertion[i].getCertificate().getSubjectDN().getName());
        }
        else {
          System.out.println("      NOT signed.");
          // return new XDSDocumentResponse(new RegistryResponse(false, "Invalid identification assertion"));
        }
      }
    }
    
    String patientId  = "RSSMRA75C03F839K";
    String documentId = request != null ? request.getDocumentUniqueId() : "";
    
    String dummyDoc   = AuthUtil.loadTextFile("dummy_doc.xml");
    if(dummyDoc == null || dummyDoc.length() == 0) {
      dummyDoc = "";
    }
    else {
      dummyDoc.replace("$patientId",  patientId);
      dummyDoc.replace("$documentId", Utils.extractCode(documentId));
    }
    
    XDSDocument xdsDocument = _readMetadata(documentId);
    if(xdsDocument == null) {
      xdsDocument = _buildDummyDocument(patientId, documentId);
    }
    
    xdsDocument.setContent(dummyDoc.getBytes());
    
    System.out.println("retrieveDocumentSet -> " + xdsDocument.toMap());
    
    return new XDSDocumentResponse(xdsDocument);
  }
  
  public 
  RegistryResponse updateDocumentSet(XDSDocument[] arrayOfXDSDocument, AuthAssertion[] arrayOfAssertion)
  {
    System.out.println("updateDocumentSet");
    if(arrayOfXDSDocument == null) {
      System.out.println("   arrayOfXDSDocument = null");
      return new RegistryResponse(false, "Missing metadata");
    }
    else
    if(arrayOfXDSDocument.length == 0) {
      System.out.println("   arrayOfXDSDocument has 0 items");
      return new RegistryResponse(false, "Missing metadata");
    }
    else {
      for(int i = 0; i < arrayOfXDSDocument.length; i++) {
        XDSDocument xdsDocument = arrayOfXDSDocument[i];
        System.out.println("   " + i + ") " + xdsDocument + " " + xdsDocument.getRelatedUUID() + " " + xdsDocument.getRelatedType());
        
        _writeMetadata(xdsDocument);
      }
    }
    if(arrayOfAssertion == null) {
      System.out.println("   arrayOfAssertion = null");
      return new RegistryResponse(false, "Missing identification assertion");
    }
    else
    if(arrayOfAssertion.length == 0) {
      System.out.println("   arrayOfAssertion has 0 items");
      return new RegistryResponse(false, "Missing identification assertion");
    }
    else {
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        System.out.println("   " + i + ") assertion = " + arrayOfAssertion[i]);
        if(arrayOfAssertion[i].isSigned()) {
          System.out.println("      signed by " + arrayOfAssertion[i].getCertificate().getSubjectDN().getName());
        }
        else {
          System.out.println("      NOT signed.");
        }
      }
    }
    
    System.out.println("updateDocumentSet -> Success");
    
    return new RegistryResponse(true);
  }
  
  public 
  RegistryResponse deleteDocumentSet(ObjectRefList objectRefList, AuthAssertion[] arrayOfAssertion)
  {
    System.out.println("deleteDocumentSet");
    if(objectRefList == null) {
      System.out.println("   objectRefList = null");
      return new RegistryResponse(false, "Missing ObjectRefList");
    }
    else
    if(objectRefList.size() == 0) {
      System.out.println("   objectRefList has 0 items");
      return new RegistryResponse(false, "ObjectRefList is empty");
    }
    else {
      for(int i = 0; i < objectRefList.size(); i++) {
        ObjectRef objectRef = objectRefList.getObjectRef(i);
        System.out.println("   " + i + ") " + objectRef);
      }
    }
    if(arrayOfAssertion == null) {
      System.out.println("   arrayOfAssertion = null");
      return new RegistryResponse(false, "Missing identification assertion");
    }
    else
    if(arrayOfAssertion.length == 0) {
      System.out.println("   arrayOfAssertion has 0 items");
      return new RegistryResponse(false, "Missing identification assertion");
    }
    else {
      for(int i = 0; i < arrayOfAssertion.length; i++) {
        System.out.println("   " + i + ") assertion = " + arrayOfAssertion[i]);
        if(arrayOfAssertion[i].isSigned()) {
          System.out.println("      signed by " + arrayOfAssertion[i].getCertificate().getSubjectDN().getName());
        }
        else {
          System.out.println("      NOT signed.");
          // return new RegistryResponse(false, "Invalid assertion");
        }
      }
    }
    
    System.out.println("deleteDocumentSet -> Success");
    
    return new RegistryResponse(true);
  }
  
  protected XDSDocument _readMetadata(String documentId) {
    if(documentId == null || documentId.length() == 0) return null;
    return _metadata.get(documentId);
  }
  
  protected boolean _writeMetadata(XDSDocument xdsDocument) {
    if(xdsDocument == null || xdsDocument.getPatient() == null) return false;
    
    XDSPerson xdsPerson = xdsDocument.getPatient();
    String patientId = xdsPerson.getId();
    if(patientId == null || patientId.length() == 0) return false;
    
    List<XDSDocument> listDocuments = _register.get(patientId);
    if(listDocuments == null) {
      listDocuments = new ArrayList<XDSDocument>();
      _register.put(patientId, listDocuments);
    }
    listDocuments.add(xdsDocument);
    String documentId = xdsDocument.getUniqueId();
    if(documentId != null && documentId.length() > 0) {
      _metadata.put(documentId, xdsDocument);
    }
    return true;
  }
  
  protected XDSDocument _buildDummyDocument(String patientId, String documentId) {
    XDSPerson author = new XDSPerson("VRDMRC67T20I257E");
    author.setRole("APR");
    author.setQualification("Medicina generale");
    author.setInstitution("ULSS N - TEST^^^^^&2.16.840.1.113883.2.9.4.1.3&ISO^^^^999109");
    author.setEmail("marco.verdi@healthcare.org");
    
    XDSDocument xdsDocument = new XDSDocument();
    xdsDocument.setRegistryObjectId("urn:uuid:803ead41-b925-4d24-8eb5-ab9d8068aa44");
    xdsDocument.setUniqueId("2.16.840.1.113883.2.9.2.120.4.4^0000000001");
    xdsDocument.setTypeCode("60591-5^" + OID.TYPE_CODES);
    xdsDocument.setHash("ZuFYdA+BgNxQyzdwXOdi8qKqb5o=");
    xdsDocument.setSize(12018);
    if(documentId != null && documentId.length() > 0) {
      xdsDocument.setUniqueId(documentId);
    }
    xdsDocument.setRepositoryUniqueId("2.16.840.1.113883.2.9.2.180.4.5.1");
    xdsDocument.setCreationTime(new Date());
    xdsDocument.setLanguageCode("it-IT");
    xdsDocument.setClassCode("REF^" + OID.CLASS_CODES);
    xdsDocument.setFormatCode("2.16.840.1.113883.2.9.10.1.1^" + OID.FORMAT_CODES);
    xdsDocument.setFacilityCode("Ospedale^"  + OID.FACILITY_CODES);
    xdsDocument.setPracticeCode("AD_PSC131^" + OID.PRACTICE_CODES);
    xdsDocument.setContentTypeCode("ERP^"    + OID.CON_TYPE_CODES);
    xdsDocument.setConfidentialityCode("N");
    xdsDocument.setPatient(new XDSPerson(patientId));
    xdsDocument.setAuthor(author);
    xdsDocument.setMimeType("text/xml");
    xdsDocument.setHome("urn:oid:2.16.840.1.113883.2.9.2.999");
    xdsDocument.setServiceStartTime(new Date());
    xdsDocument.setServiceStopTime(new Date());
    
    return xdsDocument;
  }
}
