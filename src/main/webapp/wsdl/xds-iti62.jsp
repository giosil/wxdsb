<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<definitions name='XDSDeletetWSService' targetNamespace='urn:ihe:iti:xds-b:2010'
	xmlns='http://schemas.xmlsoap.org/wsdl/' xmlns:ns1='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0'
	xmlns:ns2='urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0' xmlns:ns3='urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0'
	xmlns:ns4='http://www.w3.org/2004/08/xop/include' xmlns:ns5='urn:ihe:iti:xds-b:2007'
	xmlns:soap12='http://schemas.xmlsoap.org/wsdl/soap12/' xmlns:tns='urn:ihe:iti:xds-b:2010'
	xmlns:xsd='http://www.w3.org/2001/XMLSchema'>
	<types>
		<xs:schema targetNamespace='urn:ihe:iti:xds-b:2010' version='1.0'
			xmlns:tns='urn:ihe:iti:xds-b:2010' xmlns:xs='http://www.w3.org/2001/XMLSchema'>
			<xs:complexType name='stringMatcher'>
				<xs:sequence />
			</xs:complexType>
			<xs:complexType name='valueSetParser'>
				<xs:complexContent>
					<xs:extension base='tns:stringMatcher'>
						<xs:sequence />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
		</xs:schema>
		<xs:schema elementFormDefault='qualified'
			targetNamespace='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0'
			version='1.0' xmlns:ns1='urn:ihe:iti:xds-b:2010' xmlns:ns2='urn:ihe:iti:xds-b:2007'
			xmlns:tns='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0' xmlns:xs='http://www.w3.org/2001/XMLSchema'>
			<xs:import namespace='urn:ihe:iti:xds-b:2010' />
			<xs:import namespace='http://www.w3.org/XML/1998/namespace' />
			<xs:import namespace='urn:ihe:iti:xds-b:2007' />
			<xs:element name='Action' type='tns:ActionType' />
			<xs:element name='AdhocQuery' type='tns:AdhocQueryType' />
			<xs:element name='Association' type='tns:AssociationType1' />
			<xs:element name='AuditableEvent' type='tns:AuditableEventType' />
			<xs:element name='Classification' type='tns:ClassificationType' />
			<xs:element name='ClassificationNode' type='tns:ClassificationNodeType' />
			<xs:element name='ClassificationScheme' type='tns:ClassificationSchemeType' />
			<xs:element name='ConformanceProfileType' type='tns:ConformanceProfileType' />
			<xs:element name='EmailAddress' type='tns:EmailAddressType' />
			<xs:element name='ExternalIdentifier' type='tns:ExternalIdentifierType' />
			<xs:element name='ExternalLink' type='tns:ExternalLinkType' />
			<xs:element name='ExtrinsicObject' type='tns:ExtrinsicObjectType' />
			<xs:element name='Federation' type='tns:FederationType' />
			<xs:element name='Identifiable' type='tns:IdentifiableType' />
			<xs:element name='InternationalStringType' type='tns:InternationalStringType' />
			<xs:element name='LocalizedString' type='tns:LocalizedStringType' />
			<xs:element name='Notification' type='tns:NotificationType' />
			<xs:element name='NotifyAction' type='tns:NotifyActionType' />
			<xs:element name='ObjectRef' type='tns:ObjectRefType' />
			<xs:element name='ObjectRefList' type='tns:ObjectRefListType' />
			<xs:element name='Organization' type='tns:OrganizationType' />
			<xs:element name='Person' type='tns:PersonType' />
			<xs:element name='PersonName' type='tns:PersonNameType' />
			<xs:element name='PostalAddressType' type='tns:PostalAddressType' />
			<xs:element name='QueryExpression' type='tns:QueryExpressionType' />
			<xs:element name='Registry' type='tns:RegistryType' />
			<xs:element name='RegistryObject' type='tns:RegistryObjectType' />
			<xs:element name='RegistryObjectList' type='tns:RegistryObjectListType' />
			<xs:element name='RegistryPackage' type='tns:RegistryPackageType' />
			<xs:element name='Service' type='tns:ServiceType' />
			<xs:element name='ServiceBinding' type='tns:ServiceBindingType' />
			<xs:element name='Slot' type='tns:SlotType1' />
			<xs:element name='SlotList' type='tns:SlotListType' />
			<xs:element name='SpecificationLink' type='tns:SpecificationLinkType' />
			<xs:element name='Subscription' type='tns:SubscriptionType' />
			<xs:element name='TelephoneNumber' type='tns:TelephoneNumberType' />
			<xs:element name='TelephoneNumberListType' type='tns:TelephoneNumberListType' />
			<xs:element name='User' type='tns:UserType' />
			<xs:element name='ValueList' type='tns:ValueListType' />
			<xs:element name='VersionInfoType' type='tns:VersionInfoType' />
			<xs:complexType name='AdhocQueryType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element minOccurs='0' ref='tns:QueryExpression' />
						</xs:sequence>
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='RegistryObjectType'>
				<xs:complexContent>
					<xs:extension base='tns:IdentifiableType'>
						<xs:sequence>
							<xs:element minOccurs='0' name='Name'
								type='tns:InternationalStringType' />
							<xs:element minOccurs='0' name='Description'
								type='tns:InternationalStringType' />
							<xs:element minOccurs='0' name='VersionInfo' type='tns:VersionInfoType' />
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:Classification' />
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:ExternalIdentifier' />
						</xs:sequence>
						<xs:attribute name='lid' type='xs:string' />
						<xs:attribute name='objectType' type='xs:string' />
						<xs:attribute name='status' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='IdentifiableType'>
				<xs:complexContent>
					<xs:extension base='ns1:stringMatcher'>
						<xs:sequence>
							<xs:element maxOccurs='unbounded' minOccurs='0' ref='tns:Slot' />
						</xs:sequence>
						<xs:attribute name='home' type='xs:string' />
						<xs:attribute name='id' type='xs:string' use='required' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType mixed='true' name='QueryExpressionType'>
				<xs:sequence>
					<xs:any maxOccurs='unbounded' minOccurs='0' namespace='##other'
						processContents='skip' />
				</xs:sequence>
				<xs:attribute name='queryLanguage' type='xs:string'
					use='required' />
			</xs:complexType>
			<xs:complexType name='InternationalStringType'>
				<xs:sequence>
					<xs:element maxOccurs='unbounded' minOccurs='0'
						ref='tns:LocalizedString' />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name='LocalizedStringType'>
				<xs:sequence />
				<xs:attribute name='charset' type='xs:string' />
				<xs:attribute ref='xml:lang' />
				<xs:attribute name='value' type='xs:string' use='required' />
			</xs:complexType>
			<xs:complexType name='VersionInfoType'>
				<xs:sequence />
				<xs:attribute name='comment' type='xs:string' />
				<xs:attribute name='versionName' type='xs:string' />
			</xs:complexType>
			<xs:complexType name='ClassificationType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence />
						<xs:attribute name='classificationNode' type='xs:string' />
						<xs:attribute name='classificationScheme' type='xs:string' />
						<xs:attribute name='classifiedObject' type='xs:string'
							use='required' />
						<xs:attribute name='nodeRepresentation' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='ExternalIdentifierType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence />
						<xs:attribute name='identificationScheme' type='xs:string'
							use='required' />
						<xs:attribute name='registryObject' type='xs:string'
							use='required' />
						<xs:attribute name='value' type='xs:string' use='required' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='SlotType1'>
				<xs:complexContent>
					<xs:extension base='ns1:stringMatcher'>
						<xs:sequence>
							<xs:element ref='tns:ValueList' />
						</xs:sequence>
						<xs:attribute name='name' type='xs:string' use='required' />
						<xs:attribute name='slotType' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='ValueListType'>
				<xs:complexContent>
					<xs:extension base='ns1:valueSetParser'>
						<xs:sequence>
							<xs:element maxOccurs='unbounded' minOccurs='0' name='Value'
								type='xs:string' />
						</xs:sequence>
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='AssociationType1'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence />
						<xs:attribute name='associationType' type='xs:string'
							use='required' />
						<xs:attribute name='sourceObject' type='xs:string'
							use='required' />
						<xs:attribute name='targetObject' type='xs:string'
							use='required' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='AuditableEventType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element name='affectedObjects' type='tns:ObjectRefListType' />
						</xs:sequence>
						<xs:attribute name='eventType' type='xs:string' use='required' />
						<xs:attribute name='requestId' type='xs:string' use='required' />
						<xs:attribute name='timestamp' type='xs:string' use='required' />
						<xs:attribute name='user' type='xs:string' use='required' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='ObjectRefListType'>
				<xs:sequence>
					<xs:element maxOccurs='unbounded' minOccurs='0'
						ref='tns:ObjectRef' />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name='ObjectRefType'>
				<xs:complexContent>
					<xs:extension base='tns:IdentifiableType'>
						<xs:sequence />
						<xs:attribute name='createReplica' type='xs:boolean' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='ClassificationNodeType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:ClassificationNode' />
						</xs:sequence>
						<xs:attribute name='code' type='xs:string' />
						<xs:attribute name='parent' type='xs:string' />
						<xs:attribute name='path' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='ClassificationSchemeType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:ClassificationNode' />
						</xs:sequence>
						<xs:attribute name='isInternal' type='xs:boolean'
							use='required' />
						<xs:attribute name='nodeType' type='xs:string' use='required' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='EmailAddressType'>
				<xs:sequence />
				<xs:attribute name='address' type='xs:string' use='required' />
				<xs:attribute name='type' type='xs:string' />
			</xs:complexType>
			<xs:complexType name='ExternalLinkType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence />
						<xs:attribute name='externalURI' type='xs:string'
							use='required' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='ExtrinsicObjectType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element minOccurs='0' name='ContentVersionInfo'
								type='tns:VersionInfoType' />
							<xs:element minOccurs='0' ref='ns2:Document' />
						</xs:sequence>
						<xs:attribute name='isOpaque' type='xs:boolean' />
						<xs:attribute name='mimeType' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='RegistryObjectListType'>
				<xs:sequence>
					<xs:choice maxOccurs='unbounded' minOccurs='0'>
						<xs:element name='Identifiable' type='tns:IdentifiableType' />
						<xs:element name='Association' type='tns:AssociationType1' />
						<xs:element name='AuditableEvent' type='tns:AuditableEventType' />
						<xs:element name='Classification' type='tns:ClassificationType' />
						<xs:element name='ClassificationNode' type='tns:ClassificationNodeType' />
						<xs:element name='ClassificationScheme' type='tns:ClassificationSchemeType' />
						<xs:element name='ExternalIdentifier' type='tns:ExternalIdentifierType' />
						<xs:element name='ExternalLink' type='tns:ExternalLinkType' />
						<xs:element name='ExtrinsicObject' type='tns:ExtrinsicObjectType' />
						<xs:element name='Federation' type='tns:FederationType' />
						<xs:element name='ObjectRef' type='tns:ObjectRefType' />
						<xs:element name='Organization' type='tns:OrganizationType' />
						<xs:element name='Person' type='tns:PersonType' />
						<xs:element name='Registry' type='tns:RegistryType' />
						<xs:element name='RegistryObject' type='tns:RegistryObjectType' />
						<xs:element name='RegistryPackage' type='tns:RegistryPackageType' />
						<xs:element name='Service' type='tns:ServiceType' />
						<xs:element name='ServiceBinding' type='tns:ServiceBindingType' />
						<xs:element name='SpecificationLink' type='tns:SpecificationLinkType' />
						<xs:element name='Subscription' type='tns:SubscriptionType' />
						<xs:element name='User' type='tns:UserType' />
					</xs:choice>
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name='SlotListType'>
				<xs:sequence>
					<xs:element maxOccurs='unbounded' minOccurs='0' ref='tns:Slot' />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name='FederationType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence />
						<xs:attribute name='replicationSyncLatency' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='NotificationType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element ref='tns:RegistryObjectList' />
						</xs:sequence>
						<xs:attribute name='subscription' type='xs:string'
							use='required' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='NotifyActionType'>
				<xs:complexContent>
					<xs:extension base='tns:ActionType'>
						<xs:sequence />
						<xs:attribute name='endPoint' type='xs:string' use='required' />
						<xs:attribute name='notificationOption' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType abstract='true' name='ActionType'>
				<xs:sequence />
			</xs:complexType>
			<xs:complexType name='OrganizationType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element maxOccurs='unbounded' minOccurs='0' name='Address'
								type='tns:PostalAddressType' />
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:TelephoneNumber' />
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:EmailAddress' />
						</xs:sequence>
						<xs:attribute name='parent' type='xs:string' />
						<xs:attribute name='primaryContact' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='PostalAddressType'>
				<xs:sequence />
				<xs:attribute name='city' type='xs:string' />
				<xs:attribute name='country' type='xs:string' />
				<xs:attribute name='postalCode' type='xs:string' />
				<xs:attribute name='stateOrProvince' type='xs:string' />
				<xs:attribute name='street' type='xs:string' />
				<xs:attribute name='streetNumber' type='xs:string' />
			</xs:complexType>
			<xs:complexType name='TelephoneNumberType'>
				<xs:sequence />
				<xs:attribute name='areaCode' type='xs:string' />
				<xs:attribute name='countryCode' type='xs:string' />
				<xs:attribute name='extension' type='xs:string' />
				<xs:attribute name='number' type='xs:string' />
				<xs:attribute name='phoneType' type='xs:string' />
			</xs:complexType>
			<xs:complexType name='PersonNameType'>
				<xs:sequence />
				<xs:attribute name='firstName' type='xs:string' />
				<xs:attribute name='lastName' type='xs:string' />
				<xs:attribute name='middleName' type='xs:string' />
			</xs:complexType>
			<xs:complexType name='PersonType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element maxOccurs='unbounded' minOccurs='0' name='Address'
								type='tns:PostalAddressType' />
							<xs:element minOccurs='0' ref='tns:PersonName' />
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:TelephoneNumber' />
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:EmailAddress' />
						</xs:sequence>
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='RegistryPackageType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element minOccurs='0' ref='tns:RegistryObjectList' />
						</xs:sequence>
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='RegistryType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence />
						<xs:attribute name='catalogingLatency' type='xs:string' />
						<xs:attribute name='conformanceProfile' type='tns:ConformanceProfileType' />
						<xs:attribute name='operator' type='xs:string' use='required' />
						<xs:attribute name='replicationSyncLatency' type='xs:string' />
						<xs:attribute name='specificationVersion' type='xs:string'
							use='required' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='ServiceBindingType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:SpecificationLink' />
						</xs:sequence>
						<xs:attribute name='accessURI' type='xs:string' />
						<xs:attribute name='service' type='xs:string' use='required' />
						<xs:attribute name='targetBinding' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='SpecificationLinkType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element minOccurs='0' name='UsageDescription'
								type='tns:InternationalStringType' />
							<xs:element maxOccurs='unbounded' minOccurs='0'
								name='UsageParameter' type='xs:string' />
						</xs:sequence>
						<xs:attribute name='serviceBinding' type='xs:string'
							use='required' />
						<xs:attribute name='specificationObject' type='xs:string'
							use='required' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='ServiceType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element maxOccurs='unbounded' minOccurs='0'
								ref='tns:ServiceBinding' />
						</xs:sequence>
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='SubscriptionType'>
				<xs:complexContent>
					<xs:extension base='tns:RegistryObjectType'>
						<xs:sequence>
							<xs:element maxOccurs='unbounded' minOccurs='0' name='Action'
								type='tns:ActionType' />
						</xs:sequence>
						<xs:attribute name='endTime' type='xs:string' />
						<xs:attribute name='notificationInterval' type='xs:string' />
						<xs:attribute name='selector' type='xs:string' use='required' />
						<xs:attribute name='startTime' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='TelephoneNumberListType'>
				<xs:sequence>
					<xs:element maxOccurs='unbounded' minOccurs='0'
						ref='tns:TelephoneNumber' />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name='UserType'>
				<xs:complexContent>
					<xs:extension base='tns:PersonType'>
						<xs:sequence />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:simpleType name='ConformanceProfileType'>
				<xs:restriction base='xs:string'>
					<xs:enumeration value='registryFull' />
					<xs:enumeration value='registryLite' />
				</xs:restriction>
			</xs:simpleType>
		</xs:schema>
		<xs:schema targetNamespace='http://www.w3.org/XML/1998/namespace'
			version='1.0' xmlns:xs='http://www.w3.org/2001/XMLSchema'>
			<xs:attribute name='lang' type='xs:string' />
		</xs:schema>
		<xs:schema elementFormDefault='qualified' targetNamespace='urn:ihe:iti:xds-b:2007'
			version='1.0' xmlns:ns1='http://www.w3.org/2004/08/xop/include'
			xmlns:ns2='urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0' xmlns:ns3='urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0'
			xmlns:tns='urn:ihe:iti:xds-b:2007' xmlns:xs='http://www.w3.org/2001/XMLSchema'>
			<xs:import namespace='http://www.w3.org/2004/08/xop/include' />
			<xs:import namespace='urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0' />
			<xs:import namespace='urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0' />
			<xs:element name='Document' type='tns:Document_._type' />
			<xs:element name='DocumentRequest_._type' type='tns:DocumentRequest_._type' />
			<xs:element name='DocumentResponse_._type' type='tns:DocumentResponse_._type' />
			<xs:element name='Document_._type' type='tns:Document_._type' />
			<xs:element name='ProvideAndRegisterDocumentSetRequest'
				type='tns:ProvideAndRegisterDocumentSetRequestType' />
			<xs:element name='RetrieveDocumentSetRequest' type='tns:RetrieveDocumentSetRequestType' />
			<xs:element name='RetrieveDocumentSetResponse' type='tns:RetrieveDocumentSetResponseType' />
			<xs:complexType mixed='true' name='Document_._type'>
				<xs:sequence>
					<xs:element maxOccurs='unbounded' minOccurs='0'
						ref='ns1:Include' />
				</xs:sequence>
				<xs:attribute name='id' type='xs:string' use='required' />
			</xs:complexType>
			<xs:complexType name='DocumentRequest_._type'>
				<xs:sequence>
					<xs:element minOccurs='0' name='HomeCommunityId' type='xs:string' />
					<xs:element name='RepositoryUniqueId' type='xs:string' />
					<xs:element name='DocumentUniqueId' type='xs:string' />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name='DocumentResponse_._type'>
				<xs:sequence>
					<xs:element minOccurs='0' name='HomeCommunityId' type='xs:string' />
					<xs:element name='RepositoryUniqueId' type='xs:string' />
					<xs:element name='DocumentUniqueId' type='xs:string' />
					<xs:element minOccurs='0' name='NewRepositoryUniqueId'
						type='xs:string' />
					<xs:element minOccurs='0' name='NewDocumentUniqueId'
						type='xs:string' />
					<xs:element name='mimeType' type='xs:string' />
					<xs:element name='Document' ns4:expectedContentTypes='application/octet-stream'
						type='xs:base64Binary' xmlns:ns4='http://www.w3.org/2005/05/xmlmime' />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name='ProvideAndRegisterDocumentSetRequestType'>
				<xs:sequence>
					<xs:element ref='ns2:SubmitObjectsRequest' />
					<xs:element maxOccurs='unbounded' minOccurs='0' name='Document'
						type='tns:Document_._type' />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name='RetrieveDocumentSetRequestType'>
				<xs:sequence>
					<xs:element maxOccurs='unbounded' name='DocumentRequest'
						type='tns:DocumentRequest_._type' />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name='RetrieveDocumentSetResponseType'>
				<xs:sequence>
					<xs:element ref='ns3:RegistryResponse' />
					<xs:element maxOccurs='unbounded' minOccurs='0'
						name='DocumentResponse' type='tns:DocumentResponse_._type' />
				</xs:sequence>
			</xs:complexType>
		</xs:schema>
		<xs:schema elementFormDefault='qualified'
			targetNamespace='http://www.w3.org/2004/08/xop/include' version='1.0'
			xmlns:tns='http://www.w3.org/2004/08/xop/include' xmlns:xs='http://www.w3.org/2001/XMLSchema'>
			<xs:element name='Include' type='tns:Include' />
			<xs:complexType name='Include'>
				<xs:sequence />
				<xs:attribute name='href' type='xs:string' use='required' />
			</xs:complexType>
		</xs:schema>
		<xs:schema elementFormDefault='qualified'
			targetNamespace='urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0' version='1.0'
			xmlns:ns1='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0' xmlns:tns='urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0'
			xmlns:xs='http://www.w3.org/2001/XMLSchema'>
			<xs:import namespace='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0' />
			<xs:element name='RegistryError' type='tns:RegistryError_._type' />
			<xs:element name='RegistryErrorList' type='tns:RegistryErrorList_._type' />
			<xs:element name='RegistryRequest' type='tns:RegistryRequestType' />
			<xs:element name='RegistryResponse' type='tns:RegistryResponseType' />
			<xs:complexType name='RegistryRequestType'>
				<xs:sequence>
					<xs:element minOccurs='0' name='RequestSlotList' type='ns1:SlotListType' />
				</xs:sequence>
				<xs:attribute name='comment' type='xs:string' />
				<xs:attribute name='id' type='xs:string' />
			</xs:complexType>
			<xs:complexType name='RegistryResponseType'>
				<xs:sequence>
					<xs:element minOccurs='0' name='ResponseSlotList' type='ns1:SlotListType' />
					<xs:element minOccurs='0' ref='tns:RegistryErrorList' />
				</xs:sequence>
				<xs:attribute name='requestId' type='xs:string' />
				<xs:attribute name='status' type='xs:string' use='required' />
			</xs:complexType>
			<xs:complexType name='RegistryErrorList_._type'>
				<xs:sequence>
					<xs:element maxOccurs='unbounded' ref='tns:RegistryError' />
				</xs:sequence>
				<xs:attribute name='highestSeverity' type='xs:string' />
			</xs:complexType>
			<xs:complexType name='RegistryError_._type'>
				<xs:simpleContent>
					<xs:extension base='xs:string'>
						<xs:attribute name='codeContext' type='xs:string'
							use='required' />
						<xs:attribute name='errorCode' type='xs:string' use='required' />
						<xs:attribute name='location' type='xs:string' />
						<xs:attribute name='severity' type='xs:string' />
					</xs:extension>
				</xs:simpleContent>
			</xs:complexType>
		</xs:schema>
		<xs:schema elementFormDefault='qualified'
			targetNamespace='urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0'
			version='1.0' xmlns:ns1='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0'
			xmlns:ns2='urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0' xmlns:tns='urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0'
			xmlns:xs='http://www.w3.org/2001/XMLSchema'>
			<xs:import namespace='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0' />
			<xs:import namespace='urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0' />
			<xs:element name='RemoveObjectsRequest' type='tns:RemoveObjectsRequest_._type' />
			<xs:element name='SubmitObjectsRequest' type='tns:SubmitObjectsRequest_._type' />
			<xs:complexType name='RemoveObjectsRequest_._type'>
				<xs:complexContent>
					<xs:extension base='ns2:RegistryRequestType'>
						<xs:sequence>
							<xs:element minOccurs='0' ref='ns1:AdhocQuery' />
							<xs:element minOccurs='0' ref='ns1:ObjectRefList' />
						</xs:sequence>
						<xs:attribute name='deletionScope' type='xs:string' />
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
			<xs:complexType name='SubmitObjectsRequest_._type'>
				<xs:complexContent>
					<xs:extension base='ns2:RegistryRequestType'>
						<xs:sequence>
							<xs:element ref='ns1:RegistryObjectList' />
						</xs:sequence>
					</xs:extension>
				</xs:complexContent>
			</xs:complexType>
		</xs:schema>
	</types>
	<message name='XDSDeletetWS_DocumentRegistry_DeleteDocumentSetResponse'>
		<part element='ns2:RegistryResponse' name='body'></part>
	</message>
	<message name='XDSDeletetWS_DocumentRegistry_DeleteDocumentSet'>
		<part element='ns3:RemoveObjectsRequest' name='body'></part>
	</message>
	<portType name='XDSDeletetWS'>
		<operation name='DocumentRegistry_DeleteDocumentSet'
			parameterOrder='body'>
			<input message='tns:XDSDeletetWS_DocumentRegistry_DeleteDocumentSet'></input>
			<output message='tns:XDSDeletetWS_DocumentRegistry_DeleteDocumentSetResponse'></output>
		</operation>
	</portType>
	<binding name='XDSDeletetWSBinding' type='tns:XDSDeletetWS'>
		<soap12:binding style='document'
			transport='http://schemas.xmlsoap.org/soap/http' />
		<operation name='DocumentRegistry_DeleteDocumentSet'>
			<soap12:operation soapAction='' />
			<input>
				<soap12:body use='literal' />
			</input>
			<output>
				<soap12:body use='literal' />
			</output>
		</operation>
	</binding>
	<service name='XDSDeletetWSService'>
		<port binding='tns:XDSDeletetWSBinding' name='XDSDeletetWSSPort'>
			<soap12:address location='http://<%= request.getServerName() %>:<%= request.getServerPort() %>/wxdsb/XDSDocumentRegistryDelete/DeleteDocumentSet'/>
		</port>
	</service>
</definitions>