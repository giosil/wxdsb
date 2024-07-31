<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<definitions xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0" xmlns:ihe="urn:ihe:iti:xds-b:2007" xmlns="http://schemas.xmlsoap.org/wsdl/" name="DocumentRegistry" targetNamespace="urn:ihe:iti:xds-b:2007">
  <documentation>IHE XDS.b Document Registry = ITI-42 adaptor = Register Document Set.b</documentation>
  <types>
    <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0" xmlns:ihe="urn:ihe:iti:xds-b:2007" xmlns="http://schemas.xmlsoap.org/wsdl/" elementFormDefault="qualified">
      <xsd:import namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" schemaLocation="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/xsd/rs.xsd"/>
      <xsd:import namespace="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0" schemaLocation="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/xsd/lcm.xsd"/>
    </xsd:schema>
  </types>
  <message name="RegisterDocumentSet-bResponse_Message">
    <documentation>Register Document Set - b Response</documentation>
    <part element="rs:RegistryResponse" name="body">
    </part>
  </message>
  <message name="RegisterDocumentSet-b_Message">
    <documentation>Register Document Set - b</documentation>
    <part element="lcm:SubmitObjectsRequest" name="body">
    </part>
  </message>
  <portType name="DocumentRegistry_PortType">
    <operation name="DocumentRegistry_RegisterDocumentSet-b">
      <input message="ihe:RegisterDocumentSet-b_Message" wsam:Action="urn:ihe:iti:2007:RegisterDocumentSet-b">
      </input>
      <output message="ihe:RegisterDocumentSet-bResponse_Message" wsam:Action="urn:ihe:iti:2007:RegisterDocumentSet-bResponse">
      </output>
    </operation>
  </portType>
  <binding name="DocumentRegistry_Binding_Soap12" type="ihe:DocumentRegistry_PortType">
    <soap12:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <operation name="DocumentRegistry_RegisterDocumentSet-b">
      <input>
        <soap12:body use="literal"/>
      </input>
      <output>
        <soap12:body use="literal"/>
      </output>
    </operation>
  </binding>
  <service name="DocumentRegistry_Service">
    <port binding="ihe:DocumentRegistry_Binding_Soap12" name="DocumentRegistry_Port_Soap12">
      <soap12:address location="http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/XDSDocumentRegistryRegister/RegisterDocumentSetb"/>
    </port>
  </service>
</definitions>