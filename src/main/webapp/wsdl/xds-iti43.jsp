<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<definitions xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:ihe="urn:ihe:iti:xds-b:2007" xmlns="http://schemas.xmlsoap.org/wsdl/" name="DocumentRepository" targetNamespace="urn:ihe:iti:xds-b:2007">
<documentation>IHE XDS.b Document Repository = ITI-43 adaptor = Retrieve Document Set</documentation>
<types>
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:ihe="urn:ihe:iti:xds-b:2007" xmlns="http://schemas.xmlsoap.org/wsdl/" elementFormDefault="qualified">
<xsd:import namespace="urn:ihe:iti:xds-b:2007" schemaLocation="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/xsd/IHEXDSB.xsd"/>
</xsd:schema>  
</types>
<message name="RetrieveDocumentSetResponse_Message">
<documentation>Retrieve Document Set Response</documentation>
<part element="ihe:RetrieveDocumentSetResponse" name="body">
</part>
</message>
<message name="RetrieveDocumentSet_Message">
<documentation>Retrieve Document Set</documentation>
<part element="ihe:RetrieveDocumentSetRequest" name="body">
</part>
</message>
<portType name="DocumentRepository_PortType">
<operation name="DocumentRepository_RetrieveDocumentSet">
<input message="ihe:RetrieveDocumentSet_Message" wsam:Action="urn:ihe:iti:2007:RetrieveDocumentSet">
</input>
<output message="ihe:RetrieveDocumentSetResponse_Message" wsam:Action="urn:ihe:iti:2007:RetrieveDocumentSetResponse">
</output>
</operation>
</portType>
<binding name="DocumentRepository_Binding_Soap12" type="ihe:DocumentRepository_PortType">
<soap12:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
<operation name="DocumentRepository_RetrieveDocumentSet">
<input>
<soap12:body use="literal"/>
</input>
<output>
<soap12:body use="literal"/>
</output>
</operation>
</binding>
<service name="DocumentRepository_Service">
<port binding="ihe:DocumentRepository_Binding_Soap12" name="DocumentRepository_Port_Soap12">
<soap12:address location="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/XDSDocumentRepositoryRetrieve/RetrieveDocumentSet"/>
</port>
</service>
</definitions>