package org.dew.auth;

import java.util.Map;

import org.dew.ebxml.Utils;

/**
 * SAML Attribute Assertion.
 * See http://docs.oasis-open.org/security/xspa/v1.0/saml-xspa-1.0-cs01.html
 */
public 
class SAMLAttributeAssertion extends SAMLAssertion 
{
	private static final long serialVersionUID = -2498895539819332472L;
	// XACML
	protected String  subjectRole;    // urn:oasis:names:tc:xacml:2.0:subject:role
	protected String  resourceId;     // urn:oasis:names:tc:xacml:1.0:resource:resource-id
	protected String  actionId;       // urn:oasis:names:tc:xacml:1.0:action:action-id
	// XSPA
	protected String  locality;       // urn:oasis:names:tc:xspa:1.0:environment:locality
	protected String  purposeOfUse;   // urn:oasis:names:tc:xspa:1.0:subject:purposeofuse
	protected String  organization;   // urn:oasis:names:tc:xspa:1.0:subject:organization
	protected String  organizationId; // urn:oasis:names:tc:xspa:1.0:subject:organization-id
	protected String  subjectNpi;     // urn:oasis:names:tc:xspa:2.0:subject:npi
	protected boolean patientConsent; // urn:oasis:names:tc:xspa:1.0:resource:patient:consent
	// HL7
	protected String  documentType;   // urn:oasis:names:tc:xspa:1.0:resource:hl7:type
	protected String  permission;     // urn:oasis:names:tc:xspa:1.0:subject:hl7:permission
	
	public SAMLAttributeAssertion()
	{
		super();
	}
	
	public SAMLAttributeAssertion(String subjectId)
	{
		super(subjectId);
	}
	
	public SAMLAttributeAssertion(Map<String, Object> map)
	{
		super(map);
		if(map == null) return;
		this.subjectRole    = (String) map.get("subjectRole");
		this.resourceId     = (String) map.get("resourceId");
		this.actionId       = (String) map.get("actionId");
		
		this.locality       = (String) map.get("locality");
		this.purposeOfUse   = (String) map.get("purposeOfUse");
		this.organization   = (String) map.get("organization");
		this.organizationId = (String) map.get("organizationId");
		this.subjectNpi     = (String) map.get("subjectNpi");
		this.patientConsent = Utils.toBoolean(map.get("patientConsent"), false);
		
		this.documentType   = (String) map.get("documentType");
		this.permission     = (String) map.get("permission");
	}
	
	public SAMLAttributeAssertion(SAMLAssertion samlAssertion)
	{
		if(samlAssertion == null) return;
		// AuthAssertion
		this.id             = samlAssertion.getId();
		this.subjectId      = samlAssertion.getSubjectId();
		this.privateKey     = samlAssertion.getPrivateKey();
		this.certificate    = samlAssertion.getCertificate();
		this.digestValue    = samlAssertion.getDigestValue();
		this.signatureValue = samlAssertion.getSignatureValue();
		this.xmlSource      = samlAssertion.getXmlSource();
		// SAMLAssertion
		this.issueInstant   = samlAssertion.getIssueInstant();
		this.issuer         = samlAssertion.getIssuer();
		this.notBefore      = samlAssertion.getNotBefore();
		this.notOnOrAfter   = samlAssertion.getNotOnOrAfter();
		this.authnInstant   = samlAssertion.getAuthnInstant();
		this.authnContextClassRef = samlAssertion.getAuthnContextClassRef();
		this.attributes     = samlAssertion.getAttributes();
		if(samlAssertion instanceof SAMLAttributeAssertion) {
			SAMLAttributeAssertion attributeAssertion = (SAMLAttributeAssertion) samlAssertion;
			this.subjectRole    = attributeAssertion.getSubjectRole();
			this.resourceId     = attributeAssertion.getResourceId();
			this.actionId       = attributeAssertion.getActionId();
			
			this.locality       = attributeAssertion.getLocality();
			this.purposeOfUse   = attributeAssertion.getPurposeOfUse();
			this.organization   = attributeAssertion.getOrganization();
			this.organizationId = attributeAssertion.getOrganizationId();
			this.subjectNpi     = attributeAssertion.getSubjectNpi();
			this.patientConsent = attributeAssertion.isPatientConsent();
			
			this.documentType   = attributeAssertion.getDocumentType();
			this.permission     = attributeAssertion.getPermission();
		}
		else {
			checkAttributes();
		}
	}
	
	public String getSubjectRole() {
		return subjectRole;
	}
	
	public void setSubjectRole(String subjectRole) {
		this.subjectRole = subjectRole;
		setAttribute("urn:oasis:names:tc:xacml:2.0:subject:role", subjectRole);
	}
	
	public String getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
		setAttribute("urn:oasis:names:tc:xacml:1.0:resource:resource-id", resourceId);
	}
	
	public String getActionId() {
		return actionId;
	}
	
	public void setActionId(String actionId) {
		this.actionId = actionId;
		setAttribute("urn:oasis:names:tc:xacml:1.0:action:action-id", actionId);
	}
	
	public String getLocality() {
		return locality;
	}
	
	public void setLocality(String locality) {
		this.locality = locality;
		setAttribute("urn:oasis:names:tc:xspa:1.0:environment:locality", locality);
	}
	
	public String getPurposeOfUse() {
		return purposeOfUse;
	}
	
	public void setPurposeOfUse(String purposeOfUse) {
		this.purposeOfUse = purposeOfUse;
		setAttribute("urn:oasis:names:tc:xspa:1.0:subject:purposeofuse", purposeOfUse);
	}
	
	public String getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
		setAttribute("urn:oasis:names:tc:xspa:1.0:resource:hl7:type", documentType);
	}
	
	public String getOrganization() {
		return organization;
	}
	
	public void setOrganization(String organization) {
		this.organization = organization;
		setAttribute("urn:oasis:names:tc:xspa:1.0:subject:organization", organization);
	}
	
	public String getOrganizationId() {
		return organizationId;
	}
	
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
		setAttribute("urn:oasis:names:tc:xspa:1.0:subject:organization-id", organizationId);
	}
	
	public String getSubjectNpi() {
		return subjectNpi;
	}
	
	public void setSubjectNpi(String subjectNpi) {
		this.subjectNpi = subjectNpi;
		setAttribute("urn:oasis:names:tc:xspa:2.0:subject:npi", subjectNpi);
	}
	
	public boolean isPatientConsent() {
		return patientConsent;
	}
	
	public void setPatientConsent(boolean patientConsent) {
		this.patientConsent = patientConsent;
		setAttribute("urn:oasis:names:tc:xspa:1.0:resource:patient:consent", String.valueOf(patientConsent));
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
		setAttribute("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission", permission);
	}
	
	public int checkAttributes() {
		if(attributes == null) return 0;
		
		this.subjectRole    = attributes.get("urn:oasis:names:tc:xacml:2.0:subject:role");
		this.resourceId     = attributes.get("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
		this.actionId       = attributes.get("urn:oasis:names:tc:xacml:1.0:action:action-id");
		
		this.locality       = attributes.get("urn:oasis:names:tc:xspa:1.0:environment:locality");
		this.purposeOfUse   = attributes.get("urn:oasis:names:tc:xspa:1.0:subject:purposeofuse");
		this.organization   = attributes.get("urn:oasis:names:tc:xspa:1.0:subject:organization");
		this.organizationId = attributes.get("urn:oasis:names:tc:xspa:1.0:subject:organization-id");
		this.subjectNpi     = attributes.get("urn:oasis:names:tc:xspa:2.0:subject:npi");
		this.patientConsent = Utils.toBoolean(attributes.get("urn:oasis:names:tc:xspa:1.0:resource:patient:consent"), false);
		
		this.documentType   = attributes.get("urn:oasis:names:tc:xspa:1.0:resource:hl7:type");
		this.permission     = attributes.get("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission");
		
		return attributes.size();
	}
	
	public 
	Map<String, Object> toMap() 
	{
		Map<String, Object> mapResult = super.toMap();
		if(subjectRole    != null) mapResult.put("subjectRole",    subjectRole);
		if(resourceId     != null) mapResult.put("resourceId",     resourceId);
		if(actionId       != null) mapResult.put("actionId",       actionId);
		
		if(locality       != null) mapResult.put("locality",       locality);
		if(purposeOfUse   != null) mapResult.put("purposeOfUse",   purposeOfUse);
		if(organization   != null) mapResult.put("organization",   organization);
		if(organizationId != null) mapResult.put("organizationId", organizationId);
		if(subjectNpi     != null) mapResult.put("subjectNpi",     subjectNpi);
		mapResult.put("patientConsent", patientConsent);
		
		if(documentType   != null) mapResult.put("documentType",   documentType);
		if(permission     != null) mapResult.put("permission",     permission);
		return mapResult;
	}
	
	@Override
	protected 
	String buildAttributes(String namespace) 
	{
		StringBuffer sb = new StringBuffer();
		if(namespace == null || namespace.length() == 0) {
			namespace = "";
		}
		else 
		if(!namespace.endsWith(":")) {
			namespace += ":";
		}
		if(subjectRole != null && subjectRole.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xacml:2.0:subject:role\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(subjectRole) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(subjectNpi != null && subjectNpi.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xspa:2.0:subject:npi\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(subjectNpi) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(locality != null && locality.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xspa:1.0:environment:locality\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(locality) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(purposeOfUse != null && purposeOfUse.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(purposeOfUse) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(documentType != null && documentType.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xspa:1.0:resource:hl7:type\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(documentType) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(permission != null && permission.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xspa:1.0:subject:hl7:permission\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(permission) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(organizationId != null && organizationId.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xspa:1.0:subject:organization-id\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(organizationId) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(subjectId != null && subjectId.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(subjectId) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(organization != null && organization.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xspa:1.0:subject:organization\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(organization) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(resourceId != null && resourceId.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(resourceId) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(patientConsent) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xspa:1.0:resource:patient:consent\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + patientConsent + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		if(actionId != null && actionId.length() > 0) {
			sb.append("<" + namespace + "Attribute Name=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\">");
			sb.append("<" + namespace + "AttributeValue xsi:type=\"xs:string\">" + Utils.normalizeString(actionId) + "</" + namespace + "AttributeValue>");
			sb.append("</" + namespace + "Attribute>");
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof SAMLAttributeAssertion) {
			String sSubjectId = ((SAMLAttributeAssertion) object).getSubjectId();
			if(subjectId == null && sSubjectId == null) return true;
			return subjectId != null && subjectId.equals(sSubjectId);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if(subjectId == null) return 0;
		return subjectId.hashCode();
	}
	
	@Override
	public String toString() {
		return "SAMLAttributeAssertion(" + subjectId + ")";
	}
}
