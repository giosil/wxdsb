package org.dew.auth;

import java.io.ByteArrayInputStream;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.opensaml.DefaultBootstrap;

import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.impl.AssertionMarshaller;

import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.security.x509.X509KeyInfoGeneratorFactory;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.util.XMLHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public 
class SAMLUtil 
{
	static {
		try {
			DefaultBootstrap.bootstrap();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static
	Assertion getAssertion(SAMLAssertion samlAssertion)
		throws Exception
	{
		if(samlAssertion == null) return null;
		
		BasicParserPool basicParserPool = new BasicParserPool();
		basicParserPool.setNamespaceAware(true);
		
		Document document = basicParserPool.parse(new ByteArrayInputStream(samlAssertion.toXML(null).getBytes()));
		Element  element  = document.getDocumentElement();
		
		UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
		Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(element);
		
		return (Assertion) unmarshaller.unmarshall(element);
	}
	
	public static
	Assertion getAssertion(String assertion)
		throws Exception
	{
		if(assertion == null || assertion.length() == 0) return null;
		
		BasicParserPool basicParserPool = new BasicParserPool();
		basicParserPool.setNamespaceAware(true);
		
		Document document = basicParserPool.parse(new ByteArrayInputStream(assertion.getBytes()));
		Element  element  = document.getDocumentElement();
		
		UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
		Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(element);
		
		return (Assertion) unmarshaller.unmarshall(element);
	}
	
	public static
	String toString(Assertion assertion)
		throws Exception
	{
		if(assertion == null) return null;
		
		AssertionMarshaller assertionMarshaller = new AssertionMarshaller();
		
		String result = XMLHelper.nodeToString(assertionMarshaller.marshall(assertion));
		
		if(result == null) return null;
		
		int iStart = result.indexOf("?>");
		if(iStart > 0) result = result.substring(iStart + 2);
		
		return result;
	}
	
	public static
	String signXmlAssertion(String sAssertion, PrivateKey privateKey, X509Certificate certificate)
		throws Exception
	{
		Assertion assertion = getAssertion(sAssertion);
		
		signAssertion(assertion, privateKey, certificate);
		
		return toString(assertion);
	}
	
	public static
	void signAssertion(Assertion assertion, PrivateKey privateKey, X509Certificate certificate)
		throws Exception
	{
		BasicX509Credential signingCredential = new BasicX509Credential();
		signingCredential.setPrivateKey(privateKey);
		signingCredential.setEntityCertificate(certificate);
		
		KeyInfo keyInfo = null;
		try {
			X509KeyInfoGeneratorFactory x509KeyInfoGeneratorFactory = new X509KeyInfoGeneratorFactory();
			x509KeyInfoGeneratorFactory.setEmitEntityCertificate(true);
			keyInfo = x509KeyInfoGeneratorFactory.newInstance().generate(signingCredential);
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		Signature signature = (Signature) Configuration.getBuilderFactory()
				.getBuilder(Signature.DEFAULT_ELEMENT_NAME)
				.buildObject(Signature.DEFAULT_ELEMENT_NAME);
		
		signature.setSigningCredential(signingCredential);
		signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
		signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
		signature.setKeyInfo(keyInfo);
		
		assertion.setSignature(signature);
		
		Configuration.getMarshallerFactory().getMarshaller(assertion).marshall(assertion);
		
		Signer.signObject(signature);
	}
	
	public static
	boolean verifySignature(Assertion assertion, X509Certificate certificate)
	{
		if(assertion == null) return false;
		
		Signature signature = assertion.getSignature();
		if(signature == null) return false;
		try {
			BasicX509Credential signingCredential = new BasicX509Credential();
			signingCredential.setEntityCertificate(certificate);
			
			SignatureValidator signatureValidator = new SignatureValidator(signingCredential);
			signatureValidator.validate(signature);
			
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
