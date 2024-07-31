<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<definitions xmlns:xsd="http://www.w3.org/2001/XMLSchema"
             xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata"
             xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/"
             xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
             xmlns:query="urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0"
             xmlns:ihe="urn:ihe:iti:xds-b:2007"
             xmlns="http://schemas.xmlsoap.org/wsdl/" name="DocumentRegistry"
             targetNamespace="urn:ihe:iti:xds-b:2007">
  <documentation>IHE XDS.b Document Registry = ITI-18 adaptor = Registry Stored Query</documentation>
  <types>
    <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata"
                xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/"
                xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:query="urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0"
                xmlns:ihe="urn:ihe:iti:xds-b:2007"
                xmlns="http://schemas.xmlsoap.org/wsdl/" elementFormDefault="qualified">
      <xsd:import namespace="urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0" schemaLocation="http://<%=request.getServerName()%>:<%=request.getServerPort()%>/wxdsb/xsd/query.xsd"/>
    </xsd:schema>
  </types>
  <message name="RegistryStoredQueryResponse_Message">
    <documentation>Registry Stored Query Response</documentation>
    <part element="query:AdhocQueryResponse" name="body">
    </part>
  </message>
  <message name="RegistryStoredQuery_Message">
    <documentation>Registry Stored Query</documentation>
    <part element="query:AdhocQueryRequest" name="body">
    </part>
  </message>
  <portType name="DocumentRegistry_PortType">
    <operation name="DocumentRegistry_RegistryStoredQuery">
      <input message="ihe:RegistryStoredQuery_Message" wsam:Action="urn:ihe:iti:2007:RegistryStoredQuery">
      </input>
      <output message="ihe:RegistryStoredQueryResponse_Message" wsam:Action="urn:ihe:iti:2007:RegistryStoredQueryResponse">
      </output>
    </operation>
  </portType>
  <binding name="DocumentRegistry_Binding_Soap12" type="ihe:DocumentRegistry_PortType">
    <soap12:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <operation name="DocumentRegistry_RegistryStoredQuery">
      <input>
        <soap12:body use="literal"/>
      </input>
      <output>
        <soap12:body use="literal"/>
      </output>
    </operation>
  </binding>
  <binding name="DocumentRegistry_Binding_Soap11" type="ihe:DocumentRegistry_PortType">
    <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <operation name="DocumentRegistry_RegistryStoredQuery">
      <input>
        <soap:body use="literal"/>
      </input>
      <output>
        <soap:body use="literal"/>
      </output>
    </operation>
  </binding>
  <service name="DocumentRegistry_Service">
    <port binding="ihe:DocumentRegistry_Binding_Soap12" name="DocumentRegistry_Port_Soap12">
      <soap12:address location="http://<%=request.getServerName()%>:<%=request.getServerPort()%>/wxdsb/XDSDocumentRegistryQuery/RegistryStoredQuery"/>
    </port>
    <port binding="ihe:DocumentRegistry_Binding_Soap11" name="DocumentRegistry_Port_Soap11">
      <soap:address location="http://<%=request.getServerName()%>:<%=request.getServerPort()%>/wxdsb/XDSDocumentRegistryQuery/RegistryStoredQuery"/>
    </port>
  </service>
</definitions>
</definitions>