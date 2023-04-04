package org.dew.xds;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dew.ebxml.Association;
import org.dew.ebxml.Classification;
import org.dew.ebxml.Utils;
import org.dew.ebxml.ExternalIdentifier;
import org.dew.ebxml.ExtrinsicObject;
import org.dew.ebxml.RIM;
import org.dew.ebxml.RegistryObject;
import org.dew.ebxml.RegistryPackage;
import org.dew.ebxml.Slot;

public
class XDSDocument implements Serializable
{
  private static final long serialVersionUID = -2532291016686379265L;
  
  protected static final List<String> SLOT_NAMES = Arrays.asList(new String[]{"creationTime", "hash", "languageCode", "homeCommunityId", "repositoryUniqueId", "serviceStartTime", "serviceStopTime", "size", "sourcePatientId", "sourcePatientInfo", "legalAuthenticator", "versionNumber", "originalConfidenzialityCode"});
  
  protected IAffinityDomain affinityDomain = AffinityDomainFactory.getDefaultInstance();
  
  protected String    uniqueId;
  protected String    setId;
  
  protected Date      creationTime;
  protected String    hash;
  protected String    languageCode;
  protected String    homeCommunityId;
  protected String    repositoryUniqueId;
  protected Date      serviceStartTime;
  protected Date      serviceStopTime;
  protected int       size;
  protected boolean   zeroSize;
  
  protected boolean   opaque;
  protected String    mimeType;
  
  protected XDSPerson patient;
  protected XDSPerson author;
  protected XDSPerson legalAuthenticator;
  
  protected String    versionNumber;
  protected String    originalConfidenzialityCode;
  protected String    statusCode;
  protected String    title;
  protected String    description;
  
  protected String    classCode;
  protected String    classCodeScheme;
  protected String    confidentialityCode;
  protected String    confidentialityCodeScheme;
  protected String    formatCode;
  protected String    formatCodeScheme;
  protected String    typeCode;
  protected String    typeCodeScheme;
  
  protected String    facilityCode;
  protected String    facilityCodeScheme;
  protected String    practiceCode;
  protected String    practiceCodeScheme;
  
  protected List<String> eventCodeList;
  protected List<String> eventCodeSchemeList;
  
  protected String    home;
  protected String    registryObjectId;
  protected String    registryObjectLid;
  protected String    registryObjectStatus;
  protected String    registryPackageId;
  protected String    packageUniqueId;
  protected String    packageSourceId;
  protected String    packageName;
  protected Date      submissionTime;
  protected String    contentTypeCode;
  protected String    contentTypeCodeScheme;
  
  protected String    relatedUUID;
  protected String    relatedType;
  
  protected RegistryObject  registryObject;
  protected RegistryPackage registryPackage;
  protected Association     association;
  
  protected Map<String, Object> attributes;
  
  protected byte[] content;
  // extra
  protected String servicePath;
  protected String contentURI;
  protected Date   insertTime;
  // FSE 2.0
  protected String  repositoryType;        // urn:ita:2017:repository-type
  protected Boolean documentSigned;        // urn:ita:2022:documentSigned
  protected String  descriptionContent;    // urn:ita:2022:description
  protected String  administrativeRequest; // urn:ita:2022:administrativeRequest
  
  public XDSDocument()
  {
  }
  
  public XDSDocument(String uniqueId)
  {
    this.uniqueId = uniqueId;
  }
  
  public XDSDocument(RegistryObject registryObject)
  {
    setRegistryObject(registryObject);
  }
  
  public XDSDocument(XDSDocumentResponse xdsDocumentResponse)
  {
    if(xdsDocumentResponse == null) return;
    this.homeCommunityId    = xdsDocumentResponse.getHomeCommunityId();
    this.repositoryUniqueId = xdsDocumentResponse.getRepositoryUniqueId();
    this.uniqueId           = xdsDocumentResponse.getDocumentUniqueId();
    this.mimeType           = xdsDocumentResponse.getMimeType();
    
    byte[] document = xdsDocumentResponse.getDocument();
    if(document != null && document.length > 0) {
      this.content = document;
    }
  }
  
  @SuppressWarnings("unchecked")
  public XDSDocument(Map<String, Object> map)
  {
    if(map == null) return;
    
    this.uniqueId              = (String) map.get("uniqueId");
    this.setId                 = (String) map.get("setId");
    
    this.creationTime          = Utils.toDate(map.get("creationTime"));
    this.hash                  = (String) map.get("hash");
    this.languageCode          = (String) map.get("languageCode");
    this.homeCommunityId       = (String) map.get("homeCommunityId");
    this.repositoryUniqueId    = (String) map.get("repositoryUniqueId");
    this.serviceStartTime      = Utils.toDate(map.get("serviceStartTime"));
    this.serviceStopTime       = Utils.toDate(map.get("serviceStopTime"));
    this.size                  = Utils.toInt(map.get("size"));
    
    this.mimeType              = (String) map.get("mimeType");
    this.opaque                = Utils.toBoolean(map.get("opaque"), false);
    
    this.versionNumber         = (String) map.get("versionNumber");
    this.statusCode            = (String) map.get("statusCode");
    this.title                 = (String) map.get("title");
    this.description           = (String) map.get("description");
    
    this.classCode             = (String) map.get("classCode");
    this.classCodeScheme       = (String) map.get("classCodeScheme");
    
    this.confidentialityCode       = (String) map.get("confidentialityCode");
    this.confidentialityCodeScheme = (String) map.get("confidentialityCodeScheme");
    
    this.formatCode            = (String) map.get("formatCode");
    this.formatCodeScheme      = (String) map.get("formatCodeScheme");
    
    this.typeCode              = (String) map.get("typeCode");
    this.typeCodeScheme        = (String) map.get("typeCodeScheme");
    
    this.facilityCode          = (String) map.get("facilityCode");
    this.facilityCodeScheme    = (String) map.get("facilityCodeScheme");
    
    this.practiceCode          = (String) map.get("practiceCode");
    this.practiceCodeScheme    = (String) map.get("practiceCodeScheme");
    
    this.eventCodeList         = Utils.toListOfString(map.get("eventCodeList"));
    this.eventCodeSchemeList   = Utils.toListOfString(map.get("eventCodeSchemeList"));
    
    this.originalConfidenzialityCode = (String) map.get("originalConfidenzialityCode");
    
    this.home                  = (String) map.get("home");
    this.registryObjectId      = (String) map.get("registryObjectId");
    this.registryObjectLid     = (String) map.get("registryObjectLid");
    this.registryObjectStatus  = (String) map.get("registryObjectStatus");
    this.registryPackageId     = (String) map.get("registryPackageId");
    this.packageUniqueId       = (String) map.get("packageUniqueId");
    this.packageSourceId       = (String) map.get("packageSourceId");
    this.packageName           = (String) map.get("packageName");
    this.submissionTime        = Utils.toDate(map.get("submissionTime"));
    
    this.contentTypeCode       = (String) map.get("contentTypeCode");
    this.contentTypeCodeScheme = (String) map.get("contentTypeCodeScheme");
    
    this.relatedUUID           = (String) map.get("relatedUUID");
    this.relatedType           = (String) map.get("relatedType");
    
    this.servicePath           = (String) map.get("servicePath");
    this.contentURI            = (String) map.get("contentURI");
    this.insertTime            = Utils.toDate(map.get("insertTime"));
    
    // FSE 2.0
    this.repositoryType        = (String) map.get("repositoryType");
    this.documentSigned        = Utils.toBooleanObj(map.get("documentSigned"), null);
    this.descriptionContent    = (String) map.get("descriptionContent");
    this.administrativeRequest = (String) map.get("administrativeRequest");
    
    setReferenceIdList(map.get("referenceIdList"));
    
    Object oRegistryPackage = map.get("registryPackage");
    if(oRegistryPackage instanceof Map) {
      this.registryPackage = new RegistryPackage((Map<String, Object>) oRegistryPackage);
    }
    else if(oRegistryPackage instanceof RegistryPackage) {
      this.registryPackage = (RegistryPackage) oRegistryPackage;
    }
    
    Object oAssociation = map.get("association");
    if(oAssociation instanceof Map) {
      this.association = new Association((Map<String, Object>) oAssociation);
    }
    else if(oAssociation instanceof Association) {
      this.association = (Association) oAssociation;
    }
    
    String patientId = (String) map.get("patientId");
    if(patientId != null && patientId.length() > 0) {
      this.patient = new XDSPerson(patientId);
    }
    String authorId = (String) map.get("authorId");
    if(authorId != null && authorId.length() > 0) {
      this.author = new XDSPerson(authorId);
      String authorRole = (String) map.get("authorRole");
      if(authorRole != null && authorRole.length() > 0) {
        this.author.setRole(authorRole);
      }
      String authorInstitution = (String) map.get("authorInstitution");
      if(authorInstitution != null && authorInstitution.length() > 0) {
        this.author.setInstitution(authorInstitution);
      }
      String authorInstitutionName = (String) map.get("authorInstitutionName");
      if(authorInstitutionName != null && authorInstitutionName.length() > 0) {
        this.author.setInstitutionName(authorInstitutionName);
      }
    }
    String legalAuthenticatorId = (String) map.get("legalAuthenticatorId");
    if(legalAuthenticatorId != null && legalAuthenticatorId.length() > 0) {
      this.legalAuthenticator = new XDSPerson(legalAuthenticatorId);
    }
    
    Iterator<String> iterator = map.keySet().iterator();
    while(iterator.hasNext()) {
      String key = iterator.next().toString();
      if(key.indexOf('.') >= 0 && key.indexOf(':') >= 0) {
        if(attributes == null) attributes = new HashMap<String, Object>();
        attributes.put(key, map.get(key));
      }
    }
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    
    if(registryObjectId == null || registryObjectId.length() == 0) {
      if(registryObject != null) {
        registryObjectId = registryObject.getId();
      }
    }
    if(registryObjectId == null || registryObjectId.length() == 0) {
      registryObjectId = "urn:uuid:" + UUID.randomUUID().toString();
    }
    if(registryObjectLid == null || registryObjectLid.length() == 0) {
      if(registryObject != null) {
        registryObjectLid = registryObject.getLid();
      }
    }
    if(registryObjectLid == null || registryObjectLid.length() == 0) {
      registryObjectLid = registryObjectId;
    }
    if(registryPackageId == null || registryPackageId.length() == 0) {
      registryPackageId = "urn:uuid:" + UUID.randomUUID().toString();
    }
    
    if(attributes != null) {
      Iterator<Map.Entry<String, Object>> iterator = attributes.entrySet().iterator();
      while(iterator.hasNext()) {
        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
        String sKey = entry.getKey();
        Object oVal = entry.getValue();
        mapResult.put(Utils.normalizeAttributeName(sKey), oVal);
      }
    }
    
    mapResult.put("uniqueId",                  uniqueId);
    mapResult.put("setId",                     setId);
    
    mapResult.put("creationTime",              creationTime);
    mapResult.put("hash",                      hash);
    mapResult.put("languageCode",              languageCode);
    mapResult.put("homeCommunityId",           homeCommunityId);
    mapResult.put("repositoryUniqueId",        repositoryUniqueId);
    mapResult.put("serviceStartTime",          serviceStartTime);
    mapResult.put("serviceStopTime",           serviceStopTime);
    mapResult.put("size",                      size);
    
    mapResult.put("mimeType",                  mimeType);
    mapResult.put("opaque",                    opaque);
    
    mapResult.put("versionNumber",             versionNumber);
    mapResult.put("statusCode",                statusCode);
    mapResult.put("title",                     title);
    mapResult.put("description",               description);
    
    mapResult.put("classCode",                 classCode);
    mapResult.put("classCodeScheme",           classCodeScheme);
    
    mapResult.put("confidentialityCode",       confidentialityCode);
    mapResult.put("confidentialityCodeScheme", confidentialityCodeScheme);
    
    mapResult.put("formatCode",                formatCode);
    mapResult.put("formatCodeScheme",          formatCodeScheme);
    
    mapResult.put("typeCode",                  typeCode);
    mapResult.put("typeCodeScheme",            typeCodeScheme);
    
    mapResult.put("facilityCode",              facilityCode);
    mapResult.put("facilityCodeScheme",        facilityCodeScheme);
    
    mapResult.put("practiceCode",              practiceCode);
    mapResult.put("practiceCodeScheme",        practiceCodeScheme);
    
    mapResult.put("eventCodeList",             eventCodeList);
    mapResult.put("eventCodeSchemeList",       eventCodeSchemeList);
    
    mapResult.put("originalConfidenzialityCode", originalConfidenzialityCode);
    
    mapResult.put("home",                      home);
    mapResult.put("registryObjectId",          registryObjectId);
    mapResult.put("registryObjectLid",         registryObjectLid);
    mapResult.put("registryObjectStatus",      registryObjectStatus);
    mapResult.put("registryPackageId",         registryPackageId);
    mapResult.put("packageUniqueId",           packageUniqueId);
    mapResult.put("packageSourceId",           packageSourceId);
    mapResult.put("packageName",               packageName);
    mapResult.put("submissionTime",            submissionTime);
    
    mapResult.put("contentTypeCode",           contentTypeCode);
    mapResult.put("contentTypeCodeScheme",     contentTypeCodeScheme);
    
    mapResult.put("relatedUUID",               relatedUUID);
    mapResult.put("relatedType",               relatedType);
    
    mapResult.put("servicePath",               servicePath);
    mapResult.put("contentURI",                contentURI);
    mapResult.put("insertTime",                insertTime);
    
    // FSE 2.0
    mapResult.put("repositoryType",            repositoryType);
    mapResult.put("documentSigned",            documentSigned);
    mapResult.put("descriptionContent",        descriptionContent);
    mapResult.put("administrativeRequest",     administrativeRequest);
    
    List<String> referenceIdList = getReferenceIdList();
    if(referenceIdList != null) {
      mapResult.put("referenceIdList", referenceIdList);
    }
    
    if(registryObject != null) {
      mapResult.put("registryObject", registryObject.toMap());
    }
    else {
      mapResult.put("registryObject", null);
    }
    if(registryPackage != null) {
      mapResult.put("registryPackage", registryPackage.toMap());
    }
    else {
      mapResult.put("registryPackage", null);
    }
    if(association != null) {
      mapResult.put("association", association.toMap());
    }
    else {
      mapResult.put("association", null);
    }
    if(patient != null) {
      mapResult.put("patientId", patient.getId());
    }
    else {
      mapResult.put("patientId", null);
    }
    if(author != null) {
      mapResult.put("authorId", author.getId());
    }
    else {
      mapResult.put("authorId", null);
    }
    if(legalAuthenticator != null) {
      mapResult.put("legalAuthenticatorId", legalAuthenticator.getId());
    }
    else {
      mapResult.put("legalAuthenticatorId", null);
    }
    return mapResult;
  }
  
  public IAffinityDomain getAffinityDomain() {
    return affinityDomain;
  }
  
  public void setAffinityDomain(IAffinityDomain affinityDomain) {
    this.affinityDomain = affinityDomain;
  }
  
  public String getUniqueId() {
    return uniqueId;
  }
  
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }
  
  public String getSetId() {
    return setId;
  }
  
  public void setSetId(String setId) {
    this.setId = setId;
  }
  
  public Date getCreationTime() {
    return creationTime;
  }
  
  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }
  
  public String getHash() {
    return hash;
  }
  
  public void setHash(String hash) {
    this.hash = hash;
  }
  
  public String getLanguageCode() {
    return languageCode;
  }
  
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }
  
  public String getHomeCommunityId() {
    return homeCommunityId;
  }
  
  public void setHomeCommunityId(String homeCommunityId) {
    this.homeCommunityId = homeCommunityId;
  }
  
  public String getRepositoryUniqueId() {
    return repositoryUniqueId;
  }
  
  public void setRepositoryUniqueId(String repositoryUniqueId) {
    this.repositoryUniqueId = repositoryUniqueId;
  }
  
  public Date getServiceStartTime() {
    return serviceStartTime;
  }
  
  public void setServiceStartTime(Date serviceStartTime) {
    this.serviceStartTime = serviceStartTime;
  }
  
  public Date getServiceStopTime() {
    return serviceStopTime;
  }
  
  public void setServiceStopTime(Date serviceStopTime) {
    this.serviceStopTime = serviceStopTime;
  }
  
  public int getSize() {
    return size;
  }
  
  public void setSize(int size) {
    this.size = size;
  }
  
  public boolean isZeroSize() {
    return zeroSize;
  }
  
  public void setZeroSize(boolean zeroSize) {
    this.zeroSize = zeroSize;
  }
  
  public boolean isOpaque() {
    return opaque;
  }
  
  public void setOpaque(boolean opaque) {
    this.opaque = opaque;
  }
  
  public String getMimeType() {
    return mimeType;
  }
  
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }
  
  public XDSPerson getPatient() {
    return patient;
  }
  
  public void setPatient(XDSPerson patient) {
    this.patient = patient;
  }
  
  public XDSPerson getAuthor() {
    return author;
  }
  
  public void setAuthor(XDSPerson author) {
    this.author = author;
  }
  
  public XDSPerson getLegalAuthenticator() {
    return legalAuthenticator;
  }
  
  public void setLegalAuthenticator(XDSPerson legalAuthenticator) {
    this.legalAuthenticator = legalAuthenticator;
  }
  
  public String getVersionNumber() {
    return versionNumber;
  }
  
  public void setVersionNumber(String versionNumber) {
    this.versionNumber = versionNumber;
  }
  
  public String getOriginalConfidenzialityCode() {
    return originalConfidenzialityCode;
  }
  
  public void setOriginalConfidenzialityCode(String originalConfidenzialityCode) {
    this.originalConfidenzialityCode = originalConfidenzialityCode;
  }
  
  public String getStatusCode() {
    return statusCode;
  }
  
  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }
  
  public String getTitle() {
    if(title == null || title.length() == 0) {
      if(registryObject != null) return registryObject.getName();
    }
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
    if(registryObject != null && title != null && title.length() > 0) {
      registryObject.setName(title);
    }
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getClassCode() {
    return classCode;
  }
  
  public void setClassCode(String classCode) {
    this.classCode       = Utils.extractCode(classCode);
    String sCodingScheme = Utils.extractCodingScheme(classCode);
    if(sCodingScheme != null && sCodingScheme.length() > 0) {
      this.classCodeScheme = sCodingScheme;
    }
  }
  
  public String getClassDisplayName() {
    if(registryObject != null) {
      String result = registryObject.getClassificationName(XDS.CLS_CLASS_CODE, null);
      if(result != null && result.length() > 0) {
        return result;
      }
    }
    if(affinityDomain == null) return confidentialityCode;
    return affinityDomain.getClassDisplayName(classCode, classCode);
  }
  
  public String getClassCodeScheme() {
    return classCodeScheme;
  }
  
  public void setClassCodeScheme(String classCodeScheme) {
    this.classCodeScheme = classCodeScheme;
  }
  
  public String getConfidentialityCode() {
    return confidentialityCode;
  }
  
  public void setConfidentialityCode(String confidentialityCode) {
    this.confidentialityCode = Utils.extractCode(confidentialityCode);
    String sCodingScheme     = Utils.extractCodingScheme(confidentialityCode);
    if(sCodingScheme != null && sCodingScheme.length() > 0) {
      this.confidentialityCodeScheme = sCodingScheme;
    }
    if(confidentialityCode != null && confidentialityCode.length() == 1) {
      if(confidentialityCodeScheme == null || confidentialityCodeScheme.length() == 0) {
        confidentialityCodeScheme = OID.CONF_CODES;
      }
    }
  }
  
  public String getConfidentialityDisplayName() {
    if(registryObject != null) {
      String result = registryObject.getClassificationName(XDS.CLS_CONFIDENTIALITY, null);
      if(result != null && result.length() > 0) {
        return result;
      }
    }
    if(affinityDomain == null) return confidentialityCode;
    return affinityDomain.getConfidentialityDisplayName(confidentialityCode, confidentialityCode);
  }
  
  public String getConfidentialityCodeScheme() {
    return confidentialityCodeScheme;
  }
  
  public void setConfidentialityCodeScheme(String confidentialityCodeScheme) {
    this.confidentialityCodeScheme = confidentialityCodeScheme;
  }
  
  public String getFormatCode() {
    return formatCode;
  }
  
  public void setFormatCode(String formatCode) {
    this.formatCode      = Utils.extractCode(formatCode);
    String sCodingScheme = Utils.extractCodingScheme(formatCode);
    if(sCodingScheme != null && sCodingScheme.length() > 0) {
      this.formatCodeScheme = sCodingScheme;
    }
  }
  
  public String getFormatDisplayName() {
    if(registryObject != null) {
      String result = registryObject.getClassificationName(XDS.CLS_FORMAT_CODE, null);
      if(result != null && result.length() > 0) {
        return result;
      }
    }
    if(affinityDomain == null) return formatCode;
    return affinityDomain.getFormatDisplayName(formatCode, formatCode);
  }
  
  public String getFormatCodeScheme() {
    return formatCodeScheme;
  }
  
  public void setFormatCodeScheme(String formatCodeScheme) {
    this.formatCodeScheme = formatCodeScheme;
  }
  
  public String getTypeCode() {
    return typeCode;
  }
  
  public void setTypeCode(String typeCode) {
    this.typeCode        = Utils.extractCode(typeCode);
    String sCodingScheme = Utils.extractCodingScheme(typeCode);
    if(sCodingScheme != null && sCodingScheme.length() > 0) {
      this.typeCodeScheme = sCodingScheme;
    }
  }
  
  public String getTypeDisplayName() {
    if(description != null && description.length() > 0) {
      return description;
    }
    if(registryObject != null) {
      String result = registryObject.getClassificationName(XDS.CLS_TYPE_CODE, null);
      if(result != null && result.length() > 0) {
        return result;
      }
    }
    if(affinityDomain == null) return typeCode;
    return affinityDomain.getTypeDisplayName(typeCode, "Document");
  }
  
  public String getTypeCodeScheme() {
    return typeCodeScheme;
  }
  
  public void setTypeCodeScheme(String typeCodeScheme) {
    this.typeCodeScheme = typeCodeScheme;
  }
  
  public String getFacilityCode() {
    return facilityCode;
  }
  
  public void setFacilityCode(String facilityCode) {
    this.facilityCode    = Utils.extractCode(facilityCode);
    String sCodingScheme = Utils.extractCodingScheme(facilityCode);
    if(sCodingScheme != null && sCodingScheme.length() > 0) {
      this.facilityCodeScheme = sCodingScheme;
    }
  }
  
  public String getFacilityDisplayName() {
    if(registryObject != null) {
      String result = registryObject.getClassificationName(XDS.CLS_FACILITY_CODE, null);
      if(result != null && result.length() > 0) {
        return result;
      }
    }
    if(affinityDomain == null) return facilityCode;
    return affinityDomain.getFacilityDisplayName(facilityCode, facilityCode);
  }
  
  public String getFacilityCodeScheme() {
    return facilityCodeScheme;
  }
  
  public void setFacilityCodeScheme(String facilityCodeScheme) {
    this.facilityCodeScheme = facilityCodeScheme;
  }
  
  public String getPracticeCode() {
    return practiceCode;
  }
  
  public void setPracticeCode(String practiceCode) {
    this.practiceCode    = Utils.extractCode(practiceCode);
    String sCodingScheme = Utils.extractCodingScheme(practiceCode);
    if(sCodingScheme != null && sCodingScheme.length() > 0) {
      this.practiceCodeScheme = sCodingScheme;
    }
  }
  
  public String getPracticeDisplayName() {
    if(registryObject != null) {
      String result = registryObject.getClassificationName(XDS.CLS_PRACTICE_CODE, null);
      if(result != null && result.length() > 0) {
        return result;
      }
    }
    if(affinityDomain == null) return practiceCode;
    return affinityDomain.getPracticeDisplayName(practiceCode, practiceCode);
  }
  
  public String getPracticeCodeScheme() {
    return practiceCodeScheme;
  }
  
  public void setPracticeCodeScheme(String practiceCodeScheme) {
    this.practiceCodeScheme = practiceCodeScheme;
  }
  
  public List<String> getEventCodeList() {
    return eventCodeList;
  }
  
  public String getEventCode(int index) {
    if(eventCodeList == null || eventCodeList.size() <= index) return null;
    return eventCodeList.get(index);
  }
  
  public void setEventCodeList(List<String> eventCodeList) {
    this.eventCodeList = eventCodeList;
    if(eventCodeList == null) {
      this.eventCodeSchemeList = null;
    }
    else if(eventCodeList.size() == 0) {
      this.eventCodeSchemeList = new ArrayList<String>();
    }
  }
  
  public List<String> getEventCodeSchemeList() {
    return eventCodeSchemeList;
  }
  
  public String getEventCodeScheme(int index) {
    if(eventCodeSchemeList == null || eventCodeSchemeList.size() <= index) return null;
    return eventCodeSchemeList.get(index);
  }
  
  public void setEventCodeSchemeList(List<String> eventCodeSchemeList) {
    this.eventCodeSchemeList = eventCodeSchemeList;
  }
  
  public void addEventCode(String eventCode) {
    if(eventCode == null || eventCode.length() == 0) {
      return;
    }
    if(this.eventCodeList == null) {
      this.eventCodeList       = new ArrayList<String>();
      this.eventCodeSchemeList = new ArrayList<String>();
    }
    this.eventCodeList.add(Utils.extractCode(eventCode));
    this.eventCodeSchemeList.add(Utils.extractCodingScheme(eventCode));
  }
  
  public List<Slot> getSlots() {
    if(registryObject != null) {
      return registryObject.getSlots();
    }
    return Utils.toListOfSlot(attributes);
  }
  
  public List<Slot> getSlots(List<String> include) {
    if(registryObject != null) {
      return registryObject.getSlots(include);
    }
    return Utils.toListOfSlot(attributes, include);
  }
  
  public void addSlots(List<Slot> slotsToAdd) {
    if(slotsToAdd == null || slotsToAdd.size() == 0) {
      return;
    }
    for(int i = 0; i < slotsToAdd.size(); i++) {
      this.addSlot(slotsToAdd.get(i));
    }
  }
  
  public void addSlot(Slot slot) {
    if(slot == null) return;
    String name = slot.getName();
    if(name == null || name.length() == 0) return;
    List<String> values = slot.getValues();
    if(values == null || values.size() == 0) return;
    
    if(registryObject != null) {
      registryObject.addSlot(slot);
    }
    if(attributes == null) {
      attributes = new HashMap<String, Object>();
    }
    if(slot.isHidden()) {
      if(values.size() == 1) {
        attributes.put(name + "_", values.get(0));
      }
      else {
        attributes.put(name + "_", values);
      }
    }
    else {
      if(values.size() == 1) {
        attributes.put(name, values.get(0));
      }
      else {
        attributes.put(name, values);
      }
    }
  }
  
  public boolean removeSlot(String slotName) {
    if(slotName  == null || slotName.length() == 0) return false;
    boolean r0 = false;
    boolean r1 = false;
    boolean r2 = false;
    if(registryObject != null) {
      r0 = registryObject.removeSlot(slotName);
    }
    if(attributes != null) {
      Object o1 = attributes.remove(slotName);
      Object o2 = attributes.remove(slotName + "_");
      r1 = o1 != null;
      r2 = o2 != null;
    }
    return r0 || r1 || r2;
  }
  
  public String getHome() {
    if(home == null || home.length() == 0) {
      if(registryObject != null) {
        return registryObject.getHome();
      }
    }
    return home;
  }
  
  public void setHome(String home) {
    this.home = home;
    if(registryObject != null && home != null && home.length() > 0) {
      registryObject.setHome(home);
    }
  }
  
  public String getRegistryObjectId() {
    if(registryObjectId == null || registryObjectId.length() == 0) {
      if(registryObject != null) return registryObject.getId();
    }
    return registryObjectId;
  }
  
  public void setRegistryObjectId(String registryObjectId) {
    this.registryObjectId = registryObjectId;
    if(registryObject != null && registryObjectId != null && registryObjectId.length() > 0) {
      registryObject.setId(registryObjectId);
    }
    if(association != null && registryObjectId != null && registryObjectId.length() > 0) {
      association.setSourceObject(registryObjectId);
    }
  }
  
  public String getRegistryObjectLid() {
    if(registryObjectLid == null || registryObjectLid.length() == 0) {
      if(registryObject != null) return registryObject.getLid();
    }
    return registryObjectLid;
  }
  
  public void setRegistryObjectLid(String registryObjectLid) {
    this.registryObjectLid = registryObjectLid;
    if(registryObject != null && registryObjectLid != null && registryObjectLid.length() > 0) {
      registryObject.setLid(registryObjectLid);
    }
  }
  
  public String getRegistryObjectStatus() {
    if(registryObjectStatus == null || registryObjectStatus.length() == 0) {
      if(registryObject != null) return registryObject.getStatus();
    }
    return registryObjectStatus;
  }
  
  public void setRegistryObjectStatus(String registryObjectStatus) {
    this.registryObjectStatus = registryObjectStatus;
    if(registryObject != null && registryObjectStatus != null && registryObjectStatus.length() > 0) {
      registryObject.setStatus(registryObjectStatus);
    }
  }
  
  public String getRegistryPackageId() {
    if(registryPackageId == null || registryPackageId.length() == 0) {
      if(registryPackage != null) return registryPackage.getId();
    }
    return registryPackageId;
  }
  
  public void setRegistryPackageId(String registryPackageId) {
    this.registryPackageId = registryPackageId;
    if(registryPackage != null && registryPackageId != null && registryPackageId.length() > 0) {
      registryPackage.setId(registryPackageId);
    }
  }
  
  public String getPackageUniqueId() {
    if(packageUniqueId == null || packageUniqueId.length() == 0) {
      if(registryPackage != null) {
        ExternalIdentifier externalIdentifier = registryPackage.getExternalIdentifier("XDSSubmissionSet.uniqueId");
        if(externalIdentifier != null) return externalIdentifier.getValue();
      }
    }
    return packageUniqueId;
  }
  
  public void setPackageUniqueId(String packageUniqueId) {
    this.packageUniqueId = packageUniqueId;
    if(registryPackage != null && packageUniqueId != null && packageUniqueId.length() > 0) {
      ExternalIdentifier externalIdentifier = new ExternalIdentifier("urn:uuid:9f184dc0-c190-4569-8de4-cdeb75f6df37", registryPackage.getId());
      externalIdentifier.setName("XDSSubmissionSet.uniqueId");
      externalIdentifier.setValue(packageUniqueId);
      registryPackage.addExternalIdentifier(externalIdentifier);
    }
  }
  
  public String getPackageSourceId() {
    if(packageSourceId == null || packageSourceId.length() == 0) {
      if(registryPackage != null) {
        ExternalIdentifier externalIdentifier = registryPackage.getExternalIdentifier("XDSSubmissionSet.sourceId");
        if(externalIdentifier != null) return externalIdentifier.getValue();
      }
    }
    return packageSourceId;
  }
  
  public void setPackageSourceId(String packageSourceId) {
    this.packageSourceId = packageSourceId;
    if(registryPackage != null && packageSourceId != null && packageSourceId.length() > 0) {
      ExternalIdentifier externalIdentifier = new ExternalIdentifier("urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832", registryPackage.getId());
      externalIdentifier.setName("XDSSubmissionSet.sourceId");
      externalIdentifier.setValue(packageSourceId);
      registryPackage.addExternalIdentifier(externalIdentifier);
    }
  }
  
  public String getPackageName() {
    if(packageName == null || packageName.length() == 0) {
      if(registryPackage != null) return registryPackage.getName();
    }
    return packageName;
  }
  
  public void setPackageName(String packageName) {
    this.packageName = packageName;
    if(registryPackage != null && packageName != null && packageName.length() > 0) {
      registryPackage.setName(packageName);
    }
  }
  
  public Date getSubmissionTime() {
    return submissionTime;
  }
  
  public void setSubmissionTime(Date submissionTime) {
    this.submissionTime = submissionTime;
    if(registryPackage != null && submissionTime != null) {
      registryPackage.addSlot(new Slot("submissionTime", submissionTime));
    }
  }
  
  public String getContentTypeCode() {
    return contentTypeCode;
  }
  
  public void setContentTypeCode(String contentTypeCode) {
    this.contentTypeCode = Utils.extractCode(contentTypeCode);
    String sCodingScheme = Utils.extractCodingScheme(contentTypeCode);
    if(sCodingScheme != null && sCodingScheme.length() > 0) {
      this.contentTypeCodeScheme = sCodingScheme;
    }
  }
  
  public String getContentTypeDisplayName() {
    if(affinityDomain == null) return contentTypeCode;
    return affinityDomain.getContentTypeDisplayName(contentTypeCode, contentTypeCode);
  }
  
  public String getContentTypeCodeScheme() {
    return contentTypeCodeScheme;
  }
  
  public void setContentTypeCodeScheme(String contentTypeCodeScheme) {
    this.contentTypeCodeScheme = contentTypeCodeScheme;
  }
  
  public String getRelatedUUID() {
    return relatedUUID;
  }
  
  public void setRelatedUUID(String relatedUUID) {
    this.relatedUUID = relatedUUID;
  }
  
  public String getRelatedType() {
    return relatedType;
  }
  
  public void setRelatedType(String relatedType) {
    if(relatedType != null && relatedType.length() > 0 && !relatedType.startsWith("urn:")) {
      char c0 = relatedType.charAt(0);
      if(c0 == 'x' || c0 == 'X' || c0 == 't' || c0 == 'T') relatedType = XDS.ASS_TYPE_XFRM_IHE; else
      if(c0 == 'r' || c0 == 'R' || c0 == 'u' || c0 == 'U') relatedType = XDS.ASS_TYPE_RPLC_IHE; else
      if(c0 == 'a' || c0 == 'A' || c0 == 'n' || c0 == 'N') relatedType = XDS.ASS_TYPE_APND_IHE; else
      if(c0 == 'y' || c0 == 'Y') relatedType = XDS.ASS_TYPE_XFRM_RPLC_IHE; else
      if(c0 == 'd' || c0 == 'D') relatedType = XDS.ASS_TYPE_DEPR_IHE;      else
      if(c0 == 's' || c0 == 'S') relatedType = XDS.ASS_TYPE_SIGNS_IHE;
    }
    this.relatedType = relatedType;
  }
  
  public RegistryObject getRegistryObject() {
    if(registryObject == null) {
      return buildRegistryObject();
    }
    return registryObject;
  }
  
  public ExtrinsicObject getExtrinsicObject() {
    if(registryObject == null) {
      return buildRegistryObject();
    }
    if(registryObject instanceof ExtrinsicObject) {
      return (ExtrinsicObject) registryObject;
    }
    else if(registryObject != null) {
      ExtrinsicObject result = new ExtrinsicObject(registryObject);
      result.setMimeType(mimeType);
      result.setOpaque(opaque);
      return result;
    }
    return null;
  }
  
  public void setRegistryObject(RegistryObject registryObject) {
    this.registryObject = registryObject;
    if(registryObject == null) return;
    
    this.home                 = registryObject.getHome();
    this.registryObjectId     = registryObject.getId();
    this.registryObjectLid    = registryObject.getLid();
    this.registryObjectStatus = registryObject.getStatus();
    this.title                = registryObject.getName();
    
    ExternalIdentifier externalIdentifier = registryObject.getExternalIdentifier("XDSDocumentEntry.uniqueId", "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab");
    if(externalIdentifier != null) {
      uniqueId = externalIdentifier.getValue();
    }
    else {
      externalIdentifier = registryObject.getExternalIdentifier("XDSDocumentEntry.uniqueID");
      if(externalIdentifier != null) {
        uniqueId = externalIdentifier.getValue();
      }
    }
    externalIdentifier = registryObject.getExternalIdentifier("XDSDocumentEntry.setId");
    if(externalIdentifier != null) {
      setId = externalIdentifier.getValue();
    }
    
    this.creationTime       = registryObject.getSlotDateValue("creationTime");
    this.hash               = registryObject.getSlotFirstValue("hash");
    this.languageCode       = registryObject.getSlotFirstValue("languageCode");
    this.homeCommunityId    = registryObject.getSlotFirstValue("homeCommunityId");
    this.repositoryUniqueId = registryObject.getSlotFirstValue("repositoryUniqueId");
    this.serviceStartTime   = registryObject.getSlotDateValue("serviceStartTime");
    this.serviceStopTime    = registryObject.getSlotDateValue("serviceStopTime");
    this.size               = registryObject.getSlotIntValue("size");
    
    // FSE 2.0
    this.repositoryType        = registryObject.getSlotFirstValue("urn:ita:2017:repository-type");
    this.documentSigned        = registryObject.getSlotBooleanValue("urn:ita:2022:documentSigned");
    this.descriptionContent    = registryObject.getSlotFirstValue("urn:ita:2022:description");
    this.administrativeRequest = registryObject.getSlotFirstValue("urn:ita:2022:administrativeRequest");
    
    if(registryObject instanceof ExtrinsicObject) {
      this.mimeType = ((ExtrinsicObject) registryObject).getMimeType();
      this.opaque   = ((ExtrinsicObject) registryObject).isOpaque();
    }
    
    String sPatientId = Utils.getPatientId(registryObject);
    if(sPatientId != null && sPatientId.length() > 0) {
      String[] sourcePatientInfo = registryObject.getSlotValues("sourcePatientInfo");
      this.patient = new XDSPerson(sPatientId, sourcePatientInfo);
    }
    String authorPerson = Utils.getAuthorPerson(registryObject);
    if(authorPerson != null && authorPerson.length() > 0) {
      this.author = new XDSPerson();
      this.author.setITI_TF3_Person(authorPerson);
    }
    String sLegalAuthenticatorId = Utils.getLegalAuthenticatorId(registryObject);
    if(sLegalAuthenticatorId != null && sLegalAuthenticatorId.length() > 0) {
      this.legalAuthenticator = new XDSPerson(sLegalAuthenticatorId);
    }
    if(author != null) {
      String[] asAuthorInstitution = Utils.getAuthorInstitution(registryObject);
      if(asAuthorInstitution != null && asAuthorInstitution.length > 1) {
        author.setInstitution(asAuthorInstitution[0]);
        author.setInstitutionName(asAuthorInstitution[1]);
      }
      String sAuthorRole = Utils.getAuthorRole(registryObject);
      if(sAuthorRole != null && sAuthorRole.length() > 0) {
        author.setRole(sAuthorRole);
      }
      String sAuthorEmail = Utils.getAuthorEmail(registryObject);
      if(sAuthorEmail != null && sAuthorEmail.length() > 0) {
        author.setEmail(sAuthorEmail);
      }
    }
    this.versionNumber = registryObject.getSlotFirstValue("versionNumber");
    this.originalConfidenzialityCode = registryObject.getSlotFirstValue("originalConfidenzialityCode");
    // This is not status of Registry Object
    Classification clsStatusCode = registryObject.getClassification("*:statuscode");
    if(clsStatusCode != null) {
      this.statusCode = clsStatusCode.getNodeRepresentation();
    }
    // Classification Confidentiality
    Classification clsConfidentiality = registryObject.getClassification(XDS.CLS_CONFIDENTIALITY);
    if(clsConfidentiality != null) {
      String sConfidentiality = clsConfidentiality.getNodeRepresentation();
      if(sConfidentiality != null && sConfidentiality.length() > 0) {
        this.confidentialityCode       = Utils.extractCode(sConfidentiality);
        if(confidentialityCode != null && confidentialityCode.length() == 1) {
          this.confidentialityCodeScheme = OID.CONF_CODES;
        }
        else {
          this.confidentialityCodeScheme = clsConfidentiality.getCodingScheme();
        }
      }
    }
    // Classification class code
    Classification clsClassCode = registryObject.getClassification(XDS.CLS_CLASS_CODE);
    if(clsClassCode != null) {
      this.classCode = Utils.extractCode(clsClassCode.getNodeRepresentation());
      if(classCode != null && classCode.length() > 0) {
        if(Character.isDigit(classCode.charAt(0)) && classCode.indexOf('-') == 5 && classCode.length() == 7) {
          this.classCodeScheme = OID.LOINC;
        }
        else {
          this.classCodeScheme = clsClassCode.getCodingScheme();
        }
      }
    }
    // Classification format code
    Classification clsFormatCode = registryObject.getClassification(XDS.CLS_FORMAT_CODE);
    if(clsFormatCode != null) {
      this.formatCode       = clsFormatCode.getNodeRepresentation();
      this.formatCodeScheme = clsFormatCode.getCodingScheme();
    }
    // Classification facility code
    Classification clsFacilityCode = registryObject.getClassification(XDS.CLS_FACILITY_CODE);
    if(clsFacilityCode != null) {
      this.facilityCode       = clsFacilityCode.getNodeRepresentation();
      this.facilityCodeScheme = clsFacilityCode.getCodingScheme();
    }
    // Classification Practice Setting Code
    Classification clsPracticeCode = registryObject.getClassification(XDS.CLS_PRACTICE_CODE);
    if(clsPracticeCode != null) {
      this.practiceCode       = clsPracticeCode.getNodeRepresentation();
      this.practiceCodeScheme = clsPracticeCode.getCodingScheme();
    }
    // Classification type code
    Classification clsTypeCode = registryObject.getClassification(XDS.CLS_TYPE_CODE);
    if(clsTypeCode != null) {
      this.typeCode = clsTypeCode.getNodeRepresentation();
      if(typeCode != null && typeCode.length() > 0) {
        if(Character.isDigit(typeCode.charAt(0)) && typeCode.indexOf('-') == 5 && typeCode.length() == 7) {
          this.typeCodeScheme = OID.LOINC;
        }
        else {
          this.typeCodeScheme = clsTypeCode.getCodingScheme();
        }
      }
      this.description = clsTypeCode.getName();
    }
    // Classification eventCodeList
    List<Classification> lcEventCodeList = registryObject.getClassifications(XDS.CLS_EVENT_CODE_LIST);
    if(lcEventCodeList != null && lcEventCodeList.size() > 0) {
      eventCodeList       = new ArrayList<String>(lcEventCodeList.size());
      eventCodeSchemeList = new ArrayList<String>(lcEventCodeList.size());
      for(Classification clsEventCode : lcEventCodeList) {
        String sEventCode = clsEventCode.getNodeRepresentation();
        if(sEventCode != null && sEventCode.length() > 0) {
          eventCodeList.add(sEventCode);
          eventCodeSchemeList.add(clsEventCode.getCodingScheme());
        }
      }
    }
    else {
      eventCodeList = null;
      eventCodeSchemeList = null;
    }
    // Attributes
    String[] asSlotNames = registryObject.getSlotNames();
    if(asSlotNames != null) {
      for(int i = 0; i < asSlotNames.length; i++) {
        String sSlotName = asSlotNames[i];
        if(SLOT_NAMES.contains(sSlotName)) continue;
        if(attributes == null) attributes = new HashMap<String, Object>();
        attributes.put(sSlotName, registryObject.getSlotFirstValue(sSlotName));
      }
    }
    // Content
    byte[] content = registryObject.getContent();
    if(content != null && content.length > 0) {
      this.content = content;
    }
  }
  
  public RegistryPackage getRegistryPackage() {
    if(registryPackage == null) {
      return buildRegistryPackage();
    }
    return registryPackage;
  }
  
  public void setRegistryPackage(RegistryPackage registryPackage) {
    this.registryPackage = registryPackage;
    if(registryPackage == null) return;
    this.registryPackageId = registryPackage.getId();
    this.packageName       = registryPackage.getName();
    ExternalIdentifier eidUniqueId = registryPackage.getExternalIdentifier("XDSSubmissionSet.uniqueId");
    if(eidUniqueId != null) {
      this.packageUniqueId = eidUniqueId.getValue();
    }
    ExternalIdentifier eidSourceId = registryPackage.getExternalIdentifier("XDSSubmissionSet.sourceId");
    if(eidSourceId != null) {
      this.packageSourceId = eidSourceId.getValue();
    }
    this.submissionTime = registryPackage.getSlotDateValue("submissionTime");
    // Classification Content Type Code
    Classification clsContentTypeCode = registryPackage.getClassification(XDS.CLS_PKG_CONT_TYPE_CODE);
    if(clsContentTypeCode != null) {
      this.contentTypeCode       = clsContentTypeCode.getNodeRepresentation();
      this.contentTypeCodeScheme = clsContentTypeCode.getCodingScheme();
    }
  }
  
  public Association getAssociation() {
    if(association == null) {
      if(relatedUUID != null && relatedUUID.length() > 0 && relatedType != null && relatedType.length() > 0) {
        if(registryObjectId == null || registryObjectId.length() == 0) {
          if(registryObject != null) {
            registryObjectId = registryObject.getId();
          }
        }
        if(registryObjectId == null || registryObjectId.length() == 0) {
          registryObjectId = "urn:uuid:" + UUID.randomUUID().toString();
        }
        Association result = new Association();
        result.setSourceObject(registryObjectId);
        result.setTargetObject(relatedUUID);
        result.setAssociationType(relatedType);
        return result;
      }
    }
    else {
      if(registryObjectId != null && registryObjectId.length() > 0) {
        association.setSourceObject(registryObjectId);
      }
    }
    return association;
  }
  
  public void setAssociation(Association association) {
    this.association = association;
    if(association != null) {
      this.relatedUUID = association.getTargetObject();
      this.relatedType = association.getAssociationType();
    }
  }
  
  public boolean checkAssociationDeprecate() {
    Association oAssociation = getAssociation();
    if(oAssociation == null) return false;
    String sAssociationType = oAssociation.getAssociationType();
    if(sAssociationType == null || sAssociationType.length() == 0) return false;
    return sAssociationType.endsWith("Deprecate") || sAssociationType.endsWith("deprecate");
  }
  
  public String getServicePath() {
    return servicePath;
  }
  
  public void setServicePath(String servicePath) {
    this.servicePath = servicePath;
  }
  
  public String getContentURI() {
    return contentURI;
  }
  
  public void setContentURI(String contentURI) {
    this.contentURI = contentURI;
  }
  
  public Date getInsertTime() {
    return insertTime;
  }
  
  public void setInsertTime(Date insertTime) {
    this.insertTime = insertTime;
  }
  
  public Boolean getDocumentSigned() {
    return documentSigned;
  }
  
  public void setDocumentSigned(Boolean documentSigned) {
    this.documentSigned = documentSigned;
    if(documentSigned != null) {
      this.addSlot(new Slot("urn:ita:2022:documentSigned", documentSigned, "Documento firmato", "Documento non firmato"));
    }
    else {
      this.removeSlot("urn:ita:2022:documentSigned");
    }
  }
  
  public String getRepositoryType() {
    return repositoryType;
  }
  
  public void setRepositoryType(String repositoryType) {
    this.repositoryType = repositoryType;
    if(repositoryType != null && repositoryType.length() > 0) {
      this.addSlot(new Slot("urn:ita:2017:repository-type", repositoryType));
    }
    else {
      this.removeSlot("urn:ita:2017:repository-type");
    }
  }
  
  public String getDescriptionContent() {
    return descriptionContent;
  }
  
  public void setDescriptionContent(String descriptionContent) {
    this.descriptionContent = descriptionContent;
    if(descriptionContent != null && descriptionContent.length() > 0) {
      this.addSlot(new Slot("urn:ita:2022:description", descriptionContent));
    }
    else {
      this.removeSlot("urn:ita:2022:description");
    }
  }
  
  public String getAdministrativeRequest() {
    return administrativeRequest;
  }
  
  public void setAdministrativeRequest(String administrativeRequest) {
    this.administrativeRequest = administrativeRequest;
    if(administrativeRequest != null && administrativeRequest.length() > 0) {
      this.addSlot(new Slot("urn:ita:2022:administrativeRequest", administrativeRequest));
    }
    else {
      this.removeSlot("urn:ita:2022:administrativeRequest");
    }
  }
  
  public Map<String, Object> getAttributes() {
    return attributes;
  }
  
  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }
  
  public byte[] getContent() {
    return content;
  }
  
  public void setContent(byte[] content) {
    this.content = content;
  }
  
  public void setAttribute(String name, Object value) {
    if(name == null || name.length() == 0) return;
    if(attributes == null) attributes = new HashMap<String, Object>();
    attributes.put(name, value);
  }
  
  public Object getAttribute(String name) {
    if(name == null || name.length() == 0) return null;
    if(attributes == null) return null;
    return attributes.get(name);
  }
  
  public void setReferenceIdList(List<String> listValues) {
    if(attributes == null) attributes = new HashMap<String, Object>();
    if(listValues == null || listValues.size() == 0) {
      attributes.remove("urn:ihe:iti:xds:2013:referenceIdList");
    }
    else {
      List<String> listReferenceId = new ArrayList<String>(listValues.size());
      for(int i = 0; i < listValues.size(); i++) {
        String value = listValues.get(i);
        if(value == null || value.length() == 0) continue;
        if(value.indexOf('^') >= 0) {
          listReferenceId.add(value);
        }
        else {
          listReferenceId.add(value + "^^^&2.16.840.1.113883.2.9.4.3.8&ISO^urn:ihe:iti:xds:2013:order");
        }
      }
      attributes.put("urn:ihe:iti:xds:2013:referenceIdList", listReferenceId);
    }
  }
  
  @SuppressWarnings("unchecked")
  public void setReferenceIdList(Object values) {
    if(values instanceof List) {
      setReferenceIdList((List<String>) values);
    }
    else {
      setReferenceIdList(Utils.toListOfString(values));
    }
  }
  
  @SuppressWarnings("unchecked")
  public List<String> getReferenceIdList() {
    if(attributes == null) return null;
    Object values = attributes.get("urn:ihe:iti:xds:2013:referenceIdList");
    if(values == null) return null;
    if(values instanceof List) {
      return (List<String>) values;
    }
    return Utils.toListOfString(values);
  }
  
  public Object getAttributeContains(String name) {
    if(name == null || name.length() == 0) return null;
    if(attributes == null) return null;
    Iterator<String> iterator = attributes.keySet().iterator();
    while(iterator.hasNext()) {
      String key = iterator.next();
      if(key.indexOf(name) >= 0) {
        return attributes.get(key);
      }
    }
    return null;
  }
  
  public String getAttributeNameContains(String name) {
    if(name == null || name.length() == 0) return null;
    if(attributes == null) return null;
    Iterator<String> iterator = attributes.keySet().iterator();
    while(iterator.hasNext()) {
      String key = iterator.next();
      if(key.indexOf(name) >= 0) {
        return key;
      }
    }
    return null;
  }
  
  public Object removeAttribute(String name) {
    if(name == null || name.length() == 0) return null;
    if(attributes == null) return null;
    return attributes.remove(name);
  }
  
  public String getPatientId() {
    if(patient == null) return null;
    return patient.getId();
  }
  
  public String getAuthorId() {
    if(author == null) return null;
    return author.getId();
  }
  
  public String getAuthorRole() {
    if(author == null) return null;
    return author.getRole();
  }
  
  public String getAuthorInstitution() {
    if(author == null) return null;
    return author.getInstitution();
  }
  
  public String getAuthorInstitutionName() {
    if(author == null) return null;
    return author.getInstitutionName();
  }
  
  public String getLegalAuthenticatorId() {
    if(legalAuthenticator == null) return null;
    return legalAuthenticator.getId();
  }
  
  protected ExtrinsicObject buildRegistryObject() {
    if(registryObjectId == null || registryObjectId.length() == 0) {
      registryObjectId = "urn:uuid:" + UUID.randomUUID().toString();
    }
    if(registryObjectLid == null || registryObjectLid.length() == 0) {
      registryObjectLid = registryObjectId;
    }
    
    ExtrinsicObject result = new ExtrinsicObject();
    result.setId(registryObjectId);
    result.setLid(registryObjectLid);
    result.setMimeType(mimeType);
    result.setOpaque(opaque);
    result.setHome(home);
    result.setName(title);
    if(registryObjectStatus == null || registryObjectStatus.length() == 0) {
      result.setStatus(RIM.STATUS_APPROVED);
    }
    else {
      result.setStatus(registryObjectStatus);
    }
    if(creationTime != null) {
      result.addSlot(new Slot("creationTime", creationTime));
    }
    if(hash != null && hash.length() > 0) {
      result.addSlot(new Slot("hash", hash));
    }
    if(languageCode != null && languageCode.length() > 0) {
      result.addSlot(new Slot("languageCode", languageCode));
    }
    if(homeCommunityId != null && homeCommunityId.length() > 0) {
      result.addSlot(new Slot("homeCommunityId", homeCommunityId));
    }
    if(repositoryUniqueId != null && repositoryUniqueId.length() > 0) {
      result.addSlot(new Slot("repositoryUniqueId", repositoryUniqueId));
    }
    if(serviceStartTime != null) {
      result.addSlot(new Slot("serviceStartTime", serviceStartTime));
    }
    if(serviceStopTime != null) {
      result.addSlot(new Slot("serviceStopTime", serviceStopTime));
    }
    if(size > 0) {
      result.addSlot(new Slot("size", size));
    }
    else if(zeroSize) {
      result.addSlot(new Slot("size", 0));
    }
    if(patient != null) {
      result.addSlot(new Slot("sourcePatientId", patient.getITI_TF3_Id()));
      List<String> sourcePatientInfo = patient.getITI_TF3_Info(false);
      if(sourcePatientInfo != null && sourcePatientInfo.size() > 0) {
        result.addSlot(new Slot("sourcePatientInfo", sourcePatientInfo));
      }
    }
    if(legalAuthenticator != null) {
      result.addSlot(new Slot("legalAuthenticator", legalAuthenticator.getITI_TF3_Id()));
    }
    if(versionNumber != null && versionNumber.length() > 0) {
      result.addSlot(new Slot("versionNumber", versionNumber));
    }
    if(originalConfidenzialityCode != null && originalConfidenzialityCode.length() > 0) {
      result.addSlot(new Slot("originalConfidenzialityCode", originalConfidenzialityCode));
    }
    // FSE 2.0
    if(repositoryType != null && repositoryType.length() > 0) {
      result.addSlot(new Slot("urn:ita:2017:repository-type", repositoryType));
    }
    if(documentSigned != null) {
      result.addSlot(new Slot("urn:ita:2022:documentSigned", documentSigned, "Documento firmato", "Documento non firmato"));
    }
    if(descriptionContent != null && descriptionContent.length() > 0) {
      result.addSlot(new Slot("urn:ita:2022:description", descriptionContent));
    }
    if(administrativeRequest != null && administrativeRequest.length() > 0) {
      result.addSlot(new Slot("urn:ita:2022:administrativeRequest", administrativeRequest));
    }
    // end FSE 2.0
    if(attributes != null) {
      Iterator<Map.Entry<String, Object>> iterator = attributes.entrySet().iterator();
      while(iterator.hasNext()) {
        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
        String sKey = entry.getKey();
        // hidden 
        if(sKey.endsWith("_")) continue;
        // standard 
        if(SLOT_NAMES.contains(sKey)) continue;
        Object oVal = entry.getValue();
        result.addSlot(new Slot(sKey, oVal));
      }
    }
    if(author != null) {
      Classification classification = new Classification(XDS.CLS_AUTHOR, registryObjectId);
      classification.setHome(home);
      String sITI_TF3_Institution = author.getITI_TF3_Institution();
      if(sITI_TF3_Institution != null && sITI_TF3_Institution.length() > 0) {
        classification.addSlot(new Slot("authorInstitution", sITI_TF3_Institution));
      }
      String sITI_TF3_Person = author.getITI_TF3_Person();
      if(sITI_TF3_Person != null && sITI_TF3_Person.length() > 0) {
        classification.addSlot(new Slot("authorPerson", sITI_TF3_Person));
      }
      String sRole = author.getRole();
      if(sRole != null && sRole.length() > 0) {
        classification.addSlot(new Slot("authorRole", sRole));
      }
      String sQualification = author.getQualification();
      if(sQualification != null && sQualification.length() > 0) {
        classification.addSlot(new Slot("authorSpecialty", sQualification));
      }
      String sEmail = author.getEmail();
      if(sEmail != null && sEmail.length() > 3 && sEmail.indexOf('@') > 0) {
        classification.addSlot(new Slot("authorTelecommunication", "^^Internet^" + sEmail));
      }
      classification.setNodeRepresentation("");
      result.addClassification(classification);
    }
    if(classCode != null && classCode.length() > 0) {
      Classification classification = new Classification(XDS.CLS_CLASS_CODE, registryObjectId);
      classification.setHome(home);
      if(classCodeScheme != null && classCodeScheme.length() > 0) {
        classification.addSlot(new Slot("codingScheme", classCodeScheme));
        classification.setName(getClassDisplayName());
      }
      else {
        classification.addSlot(new Slot("codingScheme", "Classi documento"));
        classification.setName("Codice");
      }
      classification.setNodeRepresentation(classCode);
      result.addClassification(classification);
    }
    if(confidentialityCode != null && confidentialityCode.length() > 0) {
      Classification classification = new Classification(XDS.CLS_CONFIDENTIALITY, registryObjectId);
      classification.setId("ConfidentialityCode01");
      classification.setHome(home);
      if(confidentialityCodeScheme != null && confidentialityCodeScheme.length() > 0) {
        classification.addSlot(new Slot("codingScheme", confidentialityCodeScheme));
        classification.setName(getConfidentialityDisplayName());
      }
      else {
        classification.addSlot(new Slot("codingScheme", "Livelli di confidenza"));
        classification.setName("Codice");
      }
      classification.setNodeRepresentation(confidentialityCode);
      result.addClassification(classification);
    }
    if(formatCode != null && formatCode.length() > 0) {
      Classification classification = new Classification(XDS.CLS_FORMAT_CODE, registryObjectId);
      classification.setHome(home);
      if(formatCodeScheme != null && formatCodeScheme.length() > 0) {
        classification.addSlot(new Slot("codingScheme", formatCodeScheme));
        classification.setName(getFormatDisplayName());
      }
      else {
        classification.addSlot(new Slot("codingScheme", "Formattazione"));
        classification.setName("Codice");
      }
      classification.setNodeRepresentation(formatCode);
      result.addClassification(classification);
    }
    if(typeCode != null && typeCode.length() > 0) {
      Classification classification = new Classification(XDS.CLS_TYPE_CODE, registryObjectId);
      classification.setHome(home);
      if(typeCodeScheme != null && typeCodeScheme.length() > 0) {
        classification.addSlot(new Slot("codingScheme", typeCodeScheme));
        if(description != null && description.length() > 0) {
          classification.setName(description);
        }
        else {
          classification.setName(getTypeDisplayName());
        }
      }
      else {
        classification.addSlot(new Slot("codingScheme", "Tipo documento"));
        classification.setName("Codice");
      }
      classification.setNodeRepresentation(typeCode);
      result.addClassification(classification);
    }
    if(facilityCode != null && facilityCode.length() > 0) {
      Classification classification = new Classification(XDS.CLS_FACILITY_CODE, registryObjectId);
      classification.setHome(home);
      if(facilityCodeScheme != null && facilityCodeScheme.length() > 0) {
        classification.addSlot(new Slot("codingScheme", facilityCodeScheme));
      }
      else {
        classification.addSlot(new Slot("codingScheme", "Tipo Struttura"));
      }
      classification.setName(facilityCode);
      classification.setNodeRepresentation(facilityCode);
      result.addClassification(classification);
    }
    if(practiceCode != null && practiceCode.length() > 0) {
      boolean boNumeric = Character.isDigit(practiceCode.charAt(0));
      Classification classification = new Classification(XDS.CLS_PRACTICE_CODE, registryObjectId);
      classification.setHome(home);
      if(boNumeric) {
        if(practiceCodeScheme != null && practiceCodeScheme.length() > 0) {
          classification.addSlot(new Slot("codingScheme", practiceCodeScheme));
        }
        else {
          classification.addSlot(new Slot("codingScheme", "practiceSettingCode"));
        }
        classification.setName(getPracticeDisplayName());
      }
      else {
        if(practiceCodeScheme != null && practiceCodeScheme.length() > 0) {
          classification.addSlot(new Slot("codingScheme", practiceCodeScheme));
        }
        else {
          classification.addSlot(new Slot("codingScheme", "Branche"));
        }
        classification.setName(getPracticeDisplayName());
      }
      classification.setNodeRepresentation(practiceCode);
      result.addClassification(classification);
    }
    if(eventCodeList != null && eventCodeList.size() > 0) {
      for(int i = 0; i < eventCodeList.size(); i++) {
        Classification classification = new Classification(XDS.CLS_EVENT_CODE_LIST, registryObjectId);
        classification.setHome(home);
        if(eventCodeSchemeList != null && eventCodeSchemeList.size() > i) {
          classification.addSlot(new Slot("codingScheme", eventCodeSchemeList.get(i)));
        }
        classification.setName(eventCodeList.get(i));
        classification.setNodeRepresentation(eventCodeList.get(i));
        result.addClassification(classification);
      }
    }
    if(patient != null) {
      String sPatientId = patient.getId();
      if(sPatientId != null && sPatientId.length() > 0) {
        ExternalIdentifier externalIdentifier = new ExternalIdentifier(XDS.EID_PATIENT_ID, registryObjectId);
        externalIdentifier.setHome(home);
        externalIdentifier.setName("XDSDocumentEntry.patientId");
        externalIdentifier.setValue(patient.getITI_TF3_Id());
        result.addExternalIdentifier(externalIdentifier);
      }
    }
    if(uniqueId != null && uniqueId.length() > 0) {
      ExternalIdentifier externalIdentifier = new ExternalIdentifier(XDS.EID_UNIQUE_ID, registryObjectId);
      externalIdentifier.setHome(home);
      externalIdentifier.setName("XDSDocumentEntry.uniqueId");
      externalIdentifier.setValue(uniqueId);
      result.addExternalIdentifier(externalIdentifier);
    }
    if(setId != null && setId.length() > 0) {
      ExternalIdentifier externalIdentifier = new ExternalIdentifier(XDS.EID_SET_ID, registryObjectId);
      externalIdentifier.setHome(home);
      externalIdentifier.setName("XDSDocumentEntry.setId");
      externalIdentifier.setValue(setId);
      result.addExternalIdentifier(externalIdentifier);
    }
    return result;
  }
  
  public RegistryPackage buildRegistryPackage() {
    String sRegistryPackageId = registryPackageId;
    if(sRegistryPackageId == null || sRegistryPackageId.length() == 0) {
      sRegistryPackageId = "submissionSet01";
    }
    RegistryPackage registryPackage = new RegistryPackage(sRegistryPackageId);
    if(registryObjectStatus == null || registryObjectStatus.length() == 0) {
      registryPackage.setStatus(RIM.STATUS_APPROVED);
    }
    else {
      registryPackage.setStatus(registryObjectStatus);
    }
    if(submissionTime != null) {
      registryPackage.addSlot(new Slot("submissionTime", submissionTime));
    }
    else if(creationTime != null) {
      registryPackage.addSlot(new Slot("submissionTime", creationTime));
    }
    else {
      registryPackage.addSlot(new Slot("submissionTime", new Date()));
    }
    if(packageName != null && packageName.length() > 0) {
      registryPackage.setName(packageName);
    }
    else {
      registryPackage.setName("Submission Set 01");
    }
    if(author != null) {
      Classification classification = new Classification(XDS.CLS_PKG_AUTHOR, sRegistryPackageId);
      String sAuthInstitution = author.getInstitution();
      if(sAuthInstitution != null && sAuthInstitution.length() > 0) {
        classification.addSlot(new Slot("authorInstitution", sAuthInstitution));
      }
      String sITI_TF3_Person = author.getITI_TF3_Person();
      if(sITI_TF3_Person != null && sITI_TF3_Person.length() > 0) {
        classification.addSlot(new Slot("authorPerson", sITI_TF3_Person));
      }
      String sRole = author.getRole();
      if(sRole != null && sRole.length() > 0) {
        classification.addSlot(new Slot("authorRole", sRole));
      }
      String sQualification = author.getQualification();
      if(sQualification != null && sQualification.length() > 0) {
        classification.addSlot(new Slot("authorSpecialty", sQualification));
      }
      String sEmail = author.getEmail();
      if(sEmail != null && sEmail.length() > 3 && sEmail.indexOf('@') > 0) {
        classification.addSlot(new Slot("authorTelecommunication", "^^Internet^" + sEmail));
      }
      classification.setName("XDSSubmissionSet.author");
      classification.setNodeRepresentation("");
      registryPackage.addClassification(classification);
    }
    if(contentTypeCode != null) {
      Classification classification = new Classification(XDS.CLS_PKG_CONT_TYPE_CODE, sRegistryPackageId);
      if(contentTypeCodeScheme != null && contentTypeCodeScheme.length() > 0) {
        classification.addSlot(new Slot("codingScheme", contentTypeCodeScheme));
        classification.setName(getContentTypeDisplayName());
      }
      else {
        classification.addSlot(new Slot("codingScheme", "ContentTypeCodes"));
        classification.setName("Codice");
      }
      classification.setNodeRepresentation(contentTypeCode);
      registryPackage.addClassification(classification);
    }
    else if(classCode != null) {
      Classification classification = new Classification(XDS.CLS_PKG_CONT_TYPE_CODE, sRegistryPackageId);
      if(classCodeScheme != null && classCodeScheme.length() > 0) {
        classification.addSlot(new Slot("codingScheme", classCodeScheme));
        classification.setName(getContentTypeDisplayName());
      }
      else {
        classification.addSlot(new Slot("codingScheme", "ContentTypeCodes"));
        classification.setName("Codice");
      }
      classification.setNodeRepresentation(classCode);
      registryPackage.addClassification(classification);
    }
    if(patient != null) {
      ExternalIdentifier externalIdentifier = new ExternalIdentifier(XDS.EID_PKG_PATIENT_ID, sRegistryPackageId);
      externalIdentifier.setName("XDSSubmissionSet.patientId");
      externalIdentifier.setValue(patient.getITI_TF3_Id());
      registryPackage.addExternalIdentifier(externalIdentifier);
    }
    String sPackageUniqueId = null;
    if(packageUniqueId != null && packageUniqueId.length() > 0) {
      sPackageUniqueId = packageUniqueId;
    }
    else if(uniqueId != null && uniqueId.length() > 0) {
      sPackageUniqueId = uniqueId + "P";
    }
    else {
      sPackageUniqueId = UUID.randomUUID().toString();
    }
    if(packageSourceId != null && packageSourceId.length() > 0) {
      ExternalIdentifier edSourceId = new ExternalIdentifier(XDS.EID_PKG_SOURCE_ID, sRegistryPackageId);
      edSourceId.setName("XDSSubmissionSet.sourceId");
      edSourceId.setValue(packageSourceId);
      registryPackage.addExternalIdentifier(edSourceId);
    }
    ExternalIdentifier edUniqueId = new ExternalIdentifier(XDS.EID_PKG_UNIQUE_ID, sRegistryPackageId);
    edUniqueId.setName("XDSSubmissionSet.uniqueId");
    edUniqueId.setValue(sPackageUniqueId);
    registryPackage.addExternalIdentifier(edUniqueId);
    return registryPackage;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof XDSDocument) {
      String sUniqueId = ((XDSDocument) object).getUniqueId();
      if(sUniqueId == null && uniqueId == null) return true;
      return sUniqueId != null && sUniqueId.equals(uniqueId);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(uniqueId == null) return 0;
    return uniqueId.hashCode();
  }
  
  @Override
  public String toString() {
    return "XDSDocument(" + repositoryUniqueId + "," + uniqueId + "," + typeCode + "," + description + "," + Utils.formatDateTime(creationTime) + ")";
  }
}
