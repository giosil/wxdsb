<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<definitions name="DocumentRepository" targetNamespace="urn:ihe:iti:rmd:2017"
  xmlns="http://schemas.xmlsoap.org/wsdl/" 
  xmlns:xsd="http://www.w3.org/2001/XMLSchema"
  xmlns:xds="urn:ihe:iti:xds-b:2007"
  xmlns:rmd="urn:ihe:iti:rmd:2017"
  xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0"
  xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/"
  xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata">
  <documentation>IHE XDS.b Document Repository = Transaction ITI-86 = Remove Documents</documentation>
  <types>
    <xsd:schema elementFormDefault="qualified" targetNamespace="urn:ihe:iti:rmd:2017">
      <xsd:import namespace="urn:ihe:iti:xds-b:2007" schemaLocation="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/xsd/IHEXDSB.xsd"/>
      <xsd:element name="RemoveDocumentsRequest" type="xds:RetrieveDocumentSetRequestType"/>
    </xsd:schema>
  </types>
  <message name="RemoveDocuments_Message">
    <documentation>Remove Documents Request</documentation>
    <part name="body" element="rmd:RemoveDocumentsRequest"/>
  </message>
  <message name="RemoveDocumentsResponse_Message">
    <documentation>Remove Documents Response</documentation>
    <part name="body" element="rs:RegistryResponse"/>
  </message>
  <portType name="DocumentRepository_PortType">
    <operation name="DocumentRepository_RemoveDocuments">
      <input message="rmd:RemoveDocuments_Message" wsam:Action="urn:ihe:iti:2017:RemoveDocuments"/>
      <output message="rmd:RemoveDocumentsResponse_Message" wsam:Action="urn:ihe:iti:2017:RemoveDocumentsResponse"/>
    </operation>
  </portType>
  <binding name="DocumentRepository_Binding_Soap12" type="rmd:DocumentRepository_PortType">
    <soap12:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <operation name="DocumentRepository_RemoveDocuments">
      <soap12:operation soapActionRequired="false"/>
      <input>
        <soap12:body use="literal"/>
      </input>
      <output>
        <soap12:body use="literal"/>
      </output>
    </operation>
  </binding>
  <service name='DocumentRepository_Service'>
    <port name="DocumentRepository_Port_Soap12" binding="rmd:DocumentRepository_Binding_Soap12">
      <soap12:address location='http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/XDSDocumentRepositoryRemove/RemoveDocumentSet'/>
    </port>
  </service>
</definitions>