package org.dew.auth;

import java.util.Date;
import java.util.Map;

import org.dew.ebxml.Utils;
import org.dew.xds.util.Base64Coder;

public 
class WSSUsernameToken extends AuthAssertion
{
	private static final long serialVersionUID = 3370933167780505608L;
	
	protected String password;
	protected String passwordDigest;
	protected String nonce;
	protected Date   created;
	
	public WSSUsernameToken()
	{
	}
	
	public WSSUsernameToken(String subjectId)
	{
		this.subjectId = subjectId;
	}
	
	public WSSUsernameToken(Map<String, Object> map)
	{
		super(map);
		if(map == null) return;
		this.password = (String) map.get("password");
		this.nonce    = (String) map.get("nonce");
		this.created  = Utils.toDate(map.get("created"));
	}
	
	public WSSUsernameToken(String subjectId, String password)
	{
		this.subjectId = subjectId;
		this.password  = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNonce() {
		return nonce;
	}
	
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String getPasswordDigest() {
		return passwordDigest;
	}
	
	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}
	
	public void generateNonce() {
		byte[] abNonce = new byte[16];
		for(int i = 0; i < abNonce.length; i++) {
			abNonce[i] = (byte) (Byte.MAX_VALUE * Math.random());
		}
		this.nonce = new String(Base64Coder.encode(abNonce));
	}
	
	public void generatePasswordDigest() {
		// Password_Digest = Base64 ( SHA-1 ( nonce + created + password ) )
		if(nonce == null || nonce.length() == 0) {
			generateNonce();
		}
		if(created  == null) created  = new Date();
		if(password == null) password = "";
		String sToken = nonce + Utils.formatISO8601_Z(created, false) + password;
		try {
			this.passwordDigest = DSigUtil.computeDigestSHA1(sToken);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean checkPasswordDigest(String sPassword) {
		String sToken = "";
		if(nonce     != null) sToken += nonce;
		if(created   != null) sToken += Utils.formatISO8601_Z(created, false);
		if(sPassword != null) sToken += sPassword;
		String sPasswordDigest = null;
		try {
			sPasswordDigest = DSigUtil.computeDigestSHA1(sToken);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		if(sPasswordDigest == null) return false;
		return sPasswordDigest.equals(password) || sPasswordDigest.equals(passwordDigest);
	}
	
	public 
	Map<String, Object> toMap() 
	{
		Map<String, Object> mapResult = super.toMap();
		if(nonce   != null) mapResult.put("nonce",   nonce);
		if(created != null) mapResult.put("created", created);
		return mapResult;
	}
	
	public
	String getSecurityHeader()
	{
		String sXml = toXML("wsse");
		if(sXml == null || sXml.length() < 2) return "";
		String result = "<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">";
		result += sXml;
		result += "</wsse:Security>";
		return result;
	}
	
	public 
	String toXML(String namespace) 
	{
		if(xmlSource != null && xmlSource.length() > 2) {
			return xmlSource;
		}
		
		if(created == null) created = new Date();
		
		if(namespace == null) namespace = "wsse";
		if(!namespace.endsWith(":")) {
			namespace += ":";
		}
		
		StringBuffer sb = new StringBuffer();
		if(id == null || id.length() == 0) {
			sb.append("<" + namespace + "UsernameToken>");
		}
		else {
			sb.append("<" + namespace + "UsernameToken wsu:Id=\"" + Utils.normalizeString(id) + "\">");
		}
		sb.append("<" + namespace + "Username>" + Utils.normalizeString(subjectId) + "</" + namespace + "Username>");
		if(passwordDigest != null && passwordDigest.length() > 0) {
			sb.append("<" + namespace + "Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + Utils.normalizeString(passwordDigest) + "</" + namespace + "Password>");
		}
		else
		if(password == null) {
			sb.append("<" + namespace + "Password></" + namespace + "Password>");
		}
		else {
			sb.append("<" + namespace + "Password>" + Utils.normalizeString(password)  + "</" + namespace + "Password>");
		}
		if(nonce != null && nonce.length() > 0) {
			sb.append("<" + namespace + "Nonce>" + Utils.normalizeString(nonce)  + "</" + namespace + "Nonce>");
		}
		sb.append("<wsu:Created>" + Utils.formatISO8601_Z(created, false) + "</wsu:Created>");
		sb.append("</" + namespace + "UsernameToken>");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof WSSUsernameToken) {
			String sSubjectId = ((WSSUsernameToken) object).getSubjectId();
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
		return "WSSUsernameToken(" + subjectId + ")";
	}
}
