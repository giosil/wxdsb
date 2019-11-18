<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<definitions xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" xmlns:ihe="urn:ihe:iti:xds-b:2007" xmlns="http://schemas.xmlsoap.org/wsdl/" name="DocumentRepository" targetNamespace="urn:ihe:iti:xds-b:2007">
<documentation>IHE XDS.b Document Repository = ITI-41 adaptor = Provide And Register Document Set.b</documentation>
<types>
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" xmlns:ihe="urn:ihe:iti:xds-b:2007" xmlns="http://schemas.xmlsoap.org/wsdl/" elementFormDefault="qualified">
<xsd:import namespace="urn:ihe:iti:xds-b:2007" schemaLocation="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/xsd/IHEXDSB.xsd"/>
<xsd:import namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" schemaLocation="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/xsd/rs.xsd"/>
</xsd:schema>
</types>
<message name="ProvideAndRegisterDocumentSet-b_Message">
<documentation>Provide and Register Document Set</documentation>
<part element="ihe:ProvideAndRegisterDocumentSetRequest" name="body">
</part>
</message>
<message name="ProvideAndRegisterDocumentSet-bResponse_Message">
<documentation>Provide And Register Document Set Response</documentation>
<part element="rs:RegistryResponse" name="body">
</part>
</message>
<portType name="DocumentRepository_PortType">
<operation name="DocumentRepository_ProvideAndRegisterDocumentSet-b">
<input message="ihe:ProvideAndRegisterDocumentSet-b_Message" wsam:Action="urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b">
</input>
<output message="ihe:ProvideAndRegisterDocumentSet-bResponse_Message" wsam:Action="urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-bResponse">
</output>
</operation>
</portType>
<binding name="DocumentRepository_Binding_Soap12" type="ihe:DocumentRepository_PortType">
<soap12:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
<operation name="DocumentRepository_ProvideAndRegisterDocumentSet-b">
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
<soap12:address location="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/XDSDocumentRegistryProvideAndRegister/ProvideAndRegisterDocumentSetb"/>
</port>
</service>
</definitions>