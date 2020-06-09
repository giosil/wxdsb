package org.dew.xds;

public 
interface XDS 
{
  public static final String TYPE_XDS_DOCUMENT_ENTRY = "urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1";
  
  public static final String ASS_TYPE_XFRM           = "urn:uuid:ede379e6-1147-4374-a943-8fcdcf1cd620"; // Annulment
  public static final String ASS_TYPE_RPLC           = "urn:uuid:60fd13eb-b8f6-4f11-8f28-9ee000184339"; 
  public static final String ASS_TYPE_APND           = "urn:uuid:917dc511-f7da-4417-8664-de25b34d3def"; // Conf. Restriction
  public static final String ASS_TYPE_XFRM_RPLC      = "urn:uuid:b76a27c7-af3c-4319-ba4c-b90c1dc45408";
  public static final String ASS_TYPE_SIGNS          = "urn:uuid:8ea93462-ad05-4cdc-8e54-a8084f6aff94";
  
  public static final String ASS_TYPE_XFRM_IHE       = "urn:ihe:iti:2007:AssociationType:XFRM"; // Annulment
  public static final String ASS_TYPE_RPLC_IHE       = "urn:ihe:iti:2007:AssociationType:RPLC"; 
  public static final String ASS_TYPE_APND_IHE       = "urn:ihe:iti:2007:AssociationType:APND"; // Conf. Restriction
  public static final String ASS_TYPE_SIGNS_IHE      = "urn:ihe:iti:2007:AssociationType:signs";
  public static final String ASS_TYPE_XFRM_RPLC_IHE  = "urn:ihe:iti:2007:AssociationType:XFRM_RPLC";
  public static final String ASS_TYPE_DEPR_IHE       = "urn:ihe:iti:2008:AssociationType:Deprecate";
  
  public static final String CLS_AUTHOR              = "urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d";
  public static final String CLS_CONFIDENTIALITY     = "urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f";
  public static final String CLS_CLASS_CODE          = "urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a";
  public static final String CLS_FORMAT_CODE         = "urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d";
  public static final String CLS_TYPE_CODE           = "urn:uuid:f0306f51-975f-434e-a61c-c59651d33983";
  public static final String CLS_FACILITY_CODE       = "urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1";
  public static final String CLS_PRACTICE_CODE       = "urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead";
  public static final String CLS_EVENT_CODE_LIST     = "urn:uuid:2c6b8cb7-8b2a-4051-b291-b1ae6a575ef4";
  
  public static final String EID_PATIENT_ID          = "urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427";
  public static final String EID_UNIQUE_ID           = "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab";
  public static final String EID_SET_ID              = "urn:uuid:fcd05c2d-6c47-4da2-a31f-bbd7677bc612";
  
  public static final String CLS_NODE_SUBMISSION_SET = "urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd";
  
  public static final String CLS_PKG_AUTHOR          = "urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d";
  public static final String CLS_PKG_CONT_TYPE_CODE  = "urn:uuid:aa543740-bdda-424e-8c96-df4873be8500";
  
  public static final String EID_PKG_PATIENT_ID      = "urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446";
  public static final String EID_PKG_SOURCE_ID       = "urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832";
  public static final String EID_PKG_UNIQUE_ID       = "urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8";
  
  // StoredQuery
  public static final String SQ_FIND_DOCUMENTS       = "urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d";
  public static final String SQ_FIND_SUBMISSION_SETS = "urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9";
  public static final String SQ_FIND_FOLDERS         = "urn:uuid:958f3006-baad-4929-a4de-ff1114824431";
  public static final String SQ_GET_ALL              = "urn:uuid:10b545ea-725c-446d-9b95-8aeb444eddf3";
  public static final String SQ_GET_DOCUMENTS        = "urn:uuid:5c4f972b-d56b-40ac-a5fc-c8ca9b40b9d4";
  public static final String SQ_GET_SUBMISSION_SETS  = "urn:uuid:51224314-5390-4169-9b91-b1980040715a";
  public static final String SQ_GET_FOLDERS          = "urn:uuid:5737b14c-8a1a-4539-b659-e03a34a5e1e4";
  public static final String SQ_GET_ASSOCIATIONS     = "urn:uuid:a7ae438b-4bc2-4642-93e9-be891f7bb155";
  
  public static final String ERR_SEVERITY_ERROR      = "urn:oasis:names:tc:ebxml-regrep:ErrorSeverityType:Error";
  public static final String ERR_SEVERITY_WARNING    = "urn:oasis:names:tc:ebxml-regrep:ErrorSeverityType:Warning";
  
  public static final String REG_RESP_STATUS_FAILURE = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure";
  public static final String REG_RESP_STATUS_SUCCESS = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success";

//  XDSSubmissionSet
//  UUID                                           Name                              Type                    Req for Source/Query
//  urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd  XDSSubmissionSet                  ClassificationNode      R/R
//  urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d  XDSSubmissionSet.author           External Class. Scheme  R2/R
//  urn:uuid:aa543740-bdda-424e-8c96-df4873be8500  XDSSubmissionSet.contentTypeCode  External Class. Scheme  R/R
//  urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446  XDSSubmissionSet.patientId        External Identifier     R/R
//  urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832  XDSSubmissionSet.sourceId         External Identifer      R/R
//  urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8  XDSSubmissionSet.uniqueId         External Identifer      R/R
//  urn:uuid:5003a9db-8d8d-49e6-bf0c-990e34ac7707  XDSSubmissionSet.limitedMetadata  ClassificationNode      Metadata Limited
//
//  XDSDocumentEntry
//  UUID                                           Name                                         Type                    Req for Source/Query
//  urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1  XDSDocumentEntry                             ClassificationNode      R/R
//  urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d  XDSDocumentEntry.author                      External Class. Scheme  R2/R
//  urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a  XDSDocumentEntry.classCode                   External Class. Scheme  R/R
//  urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f  XDSDocumentEntry.confidentialityCode         External Class. Scheme  R/P
//  urn:uuid:2c6b8cb7-8b2a-4051-b291-b1ae6a575ef4  XDSDocumentEntry.eventCodeList               External Class. Scheme  O/R
//  urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d  XDSDocumentEntry.formatCode                  External Class. Scheme  R/R
//  urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1  XDSDocumentEntry.healthcareFacilityTypeCode  External Class. Scheme  R/R
//  urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427  XDSDocumentEntry.patientId                   External Identifier     R/R
//  urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead  XDSDocumentEntry.practiceSettingCode         External Class. Scheme  R/R
//  urn:uuid:f0306f51-975f-434e-a61c-c59651d33983  XDSDocumentEntry.typeCode                    External Class. Scheme  R/R
//  urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab  XDSDocumentEntry.uniqueId                    External Identifier     R/R
//  urn:uuid:ab9b591b-83ab-4d03-8f5d-f93b1fb92e85  XDSDocumentEntry.limitedMetadata             ClassificationNode      Metadata Limited
//
//  XDSFolder
//  UUID                                           Name                        Type                    Req for Source/Query
//  urn:uuid:d9d542f3-6cc4-48b6-8870-ea235fbc94c2  XDSFolder                   ClassificationNode      R/R
//  urn:uuid:1ba97051-7806-41a8-a48b-8fce7af683c5  XDSFolder.codeList          External Class. Scheme  R/R
//  urn:uuid:f64ffdf0-4b97-4e06-b79f-a52b38ec2f8a  XDSFolder.patientId         External Identifier     R/R
//  urn:uuid:75df8f67-9973-4fbe-a900-df66cefecc5a  XDSFolder.uniqueId          External Identifier     R/R
//  urn:uuid:2c144a76-29a9-4b7c-af54-b25409fe7d03  XDSFolder.limitedMetadata   ClassificationNode      Metadata Limited
//
//  Document Relationships
//  UUID                                           Name          Type
//  urn:uuid:917dc511-f7da-4417-8664-de25b34d3def  APND          ClassificationNode
//  urn:uuid:60fd13eb-b8f6-4f11-8f28-9ee000184339  RPLC          ClassificationNode
//  urn:uuid:ede379e6-1147-4374-a943-8fcdcf1cd620  XFRM          ClassificationNode
//  urn:uuid:b76a27c7-af3c-4319-ba4c-b90c1dc45408  XFRM_RPLC     ClassificationNode
//  urn:uuid:8ea93462-ad05-4cdc-8e54-a8084f6aff94  signs         ClassificationNode
//
//  Other
//  UUID                                           Name                   Type
//  urn:uuid:10aa1a4b-715a-4120-bfd0-9760414112c8  XDSDocumentEntryStub   ClassificationNode
//  urn:uuid:abd807a3-4432-4053-87b4-fd82c643d1f3  Association Document.  ClassificationNode
//
//  Stored Queries
//  UUID                                           Name
//  urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d  FindDocuments
//  urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9  FindSubmissionSets
//  urn:uuid:958f3006-baad-4929-a4de-ff1114824431  FindFolders
//  urn:uuid:10b545ea-725c-446d-9b95-8aeb444eddf3  GetAll
//  urn:uuid:5c4f972b-d56b-40ac-a5fc-c8ca9b40b9d4  GetDocuments
//  urn:uuid:5737b14c-8a1a-4539-b659-e03a34a5e1e4  GetFolders
//  urn:uuid:a7ae438b-4bc2-4642-93e9-be891f7bb155  GetAssociations
//  urn:uuid:bab9529a-4a10-40b3-a01f-f68a615d247a  GetDocumentsAndAssociations
//  urn:uuid:51224314-5390-4169-9b91-b1980040715a  GetSubmissionSets
//  urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83  GetSubmissionSetAndContents
//  urn:uuid:b909a503-523d-4517-8acf-8e5834dfc4c7  GetFolderAndContents
//  urn:uuid:10cae35a-c7f9-4cf5-b61e-fc3278ffb578  GetFoldersForDocument
//  urn:uuid:d90e5407-b356-4d91-a89f-873917b4b0e6  GetRelatedDocuments
//
//  Multipatient Stored Queries
//  UUID                                           Name  
//  urn:uuid:3d1bdb10-39a2-11de-89c2-2f44d94eaa9f  FindDocumentsForMultiplePatients
//  urn:uuid:50d3f5ac-39a2-11de-a1ca-b366239e58df  FindFoldersForMultiplePatients
}
