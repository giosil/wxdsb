package org.dew.xds.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.HttpsURLConnection;

import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.dew.auth.AuthAssertion;
import org.dew.auth.BasicAssertion;

public 
class WSUtil
{
	public static final byte[] CRLF = {13, 10};
	
	public static boolean TRACE = false;
	
	public static
	AuthAssertion getBasicAuth(HttpServletRequest request)
	{
		String sAuthorization = request.getHeader("Authorization");
		if(sAuthorization == null || !sAuthorization.startsWith("Basic ")) {
			return null;
		}
		String sB64Credentials = sAuthorization.substring(6);
		if(sB64Credentials.length() < 2) {
			return null;
		}
		AuthAssertion assertion = null;
		try {
			String sCredentials = Base64Coder.decodeString(sB64Credentials);
			int iSep = sCredentials.indexOf(':');
			if(iSep <= 0) return null;
			
			String sUsername = sCredentials.substring(0,iSep);
			String sPassword = sCredentials.substring(iSep+1);
			
			assertion = new BasicAssertion(sUsername, sPassword);
		}
		catch(Throwable th) {
			System.err.println("getBasicAuth: " + th);
		}
		return assertion;
	}
	
	public static
	SOAPMessage getSOAPMessage(HttpServletRequest request)
		throws Exception
	{
		boolean boMultiPart = false;
		
		String sContentType = null;
		String sSOAPAction  = null;
		
		MimeHeaders headers = new MimeHeaders();
		Enumeration<String> enumHeaderNames = request.getHeaderNames();
		if(enumHeaderNames != null) {
			while(enumHeaderNames.hasMoreElements()) {
				String sHeaderName  = enumHeaderNames.nextElement();
				if("Content-Type".equalsIgnoreCase(sHeaderName)) {
					sContentType = request.getHeader(sHeaderName);
					if(sContentType != null && sContentType.length() > 0) {
						if(sContentType.indexOf("boundary=") >= 0 || sContentType.indexOf("start=") >= 0) {
							headers.addHeader(sHeaderName, sContentType);
							boMultiPart = true;
						}
					}
				}
				else
				if("SOAPAction".equalsIgnoreCase(sHeaderName)) {
					sSOAPAction = request.getHeader(sHeaderName);
					headers.addHeader(sHeaderName, sSOAPAction);
				}
			}
		}
		
		MessageFactory messageFactory = null;
		if(boMultiPart) {
			messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		}
		else {
			if(sContentType != null && sContentType.startsWith("text/xml") && sSOAPAction != null && sSOAPAction.length() > 0) {
				headers.addHeader("Content-Type", sContentType);
				messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			}
			else {
				messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
			}
		}
		
		return messageFactory.createMessage(headers, request.getInputStream());
	}
	
	public static
	byte[] getSOAPPartContent(SOAPMessage soapMessage)
		throws Exception
	{
		if(soapMessage == null) return new byte[0];
		
		Source source = soapMessage.getSOAPPart().getContent();
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(baos);
		transformer.transform(source, result);
		
		byte[] abResult = baos.toByteArray();
		
		if(TRACE) System.out.println("[REQUEST] " + new String(abResult));
		
		return abResult;
	}
	
	public static
	byte[] getSOAPMessageContent(SOAPMessage soapMessage)
		throws Exception
	{
		if(soapMessage == null) return new byte[0];
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		soapMessage.writeTo(baos);
		
		byte[] abResult = baos.toByteArray();
		
		if(TRACE) System.out.println("[REQUEST] " + new String(abResult));
		
		return abResult;
	}
	
	public static
	String getMessageID(SOAPMessage soapMessage)
		throws Exception
	{
		SOAPHeader soapHeader = soapMessage.getSOAPHeader();
		if(soapHeader != null) {
			NodeList nodeList = soapHeader.getChildNodes();
			if(nodeList != null) {
				int iLength = nodeList.getLength();
				for(int i = 0; i < iLength; i++) {
					Node node = nodeList.item(i);
					String localName = node.getLocalName();
					if(localName != null && localName.equalsIgnoreCase("MessageID")) {
						return node.getTextContent();
					}
				}
			}
		}
		return "";
	}
	
	public static
	String getRequestName(SOAPMessage soapMessage)
		throws Exception
	{
		SOAPBody soapBody = soapMessage.getSOAPBody();
		if(soapBody != null) {
			NodeList nodeList = soapBody.getChildNodes();
			if(nodeList != null) {
				int iLength = nodeList.getLength();
				for(int i = 0; i < iLength; i++) {
					String localName = nodeList.item(i).getLocalName();
					if(localName != null) return localName;
				}
			}
		}
		return "";
	}
	
	public static
	String getNamespaceURIEnvelope(SOAPMessage soapMessage)
		throws Exception
	{
		if(soapMessage == null) {
			return SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;
		}
		SOAPPart soapPart = soapMessage.getSOAPPart();
		if(soapPart == null) {
			return SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;
		}
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		if(soapEnvelope == null) {
			return SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;
		}
		String result = soapEnvelope.getNamespaceURI();
		if(result == null || result.length() < 7) {
			return SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static
	Map<String, byte[]> getAttachments(SOAPMessage soapMessage)
		throws Exception
	{
		Map<String, byte[]> mapResult = new HashMap<String, byte[]>();
		Iterator iterator = soapMessage.getAttachments();
		if(iterator == null) return mapResult;
		int i = 0;
		while(iterator.hasNext()) {
			Object attachment = iterator.next();
			if(attachment instanceof AttachmentPart) {
				AttachmentPart attachmentPart = (AttachmentPart) attachment;
				String sContentId = attachmentPart.getContentId();
				if(sContentId == null || sContentId.length() == 0) {
					sContentId = String.valueOf(i);
				}
				mapResult.put(sContentId, attachmentPart.getRawContentBytes());
			}
			i++;
		}
		return mapResult;
	}
	
	@SuppressWarnings("rawtypes")
	public static
	byte[] getFirstAttachment(SOAPMessage soapMessage)
		throws Exception
	{
		Iterator iterator = soapMessage.getAttachments();
		if(iterator == null) return null;
		while(iterator.hasNext()) {
			Object attachment = iterator.next();
			if(attachment instanceof AttachmentPart) {
				AttachmentPart attachmentPart = (AttachmentPart) attachment;
				return attachmentPart.getRawContentBytes();
			}
		}
		return null;
	}
	
	public static
	byte[] getFirstAttachment(byte[] message)
		throws Exception
	{
		if(message == null || message.length == 0) {
			return new byte[0];
		}
		
		String sUUIDPart     = null;
		boolean isSecondPart = false;
		boolean isContent    = false;
		ByteArrayOutputStream baosContent = new ByteArrayOutputStream();
		
		InputStream is  = new ByteArrayInputStream(message);
		StringBuffer sb = new StringBuffer();
		int b = 0;
		while((b = is.read()) != -1) {
			if(isContent) {
				baosContent.write((byte) b);
				continue;
			}
			if(b >= 32 && b <= 127) {
				sb.append((char) b);
				continue;
			}
			if(b == 10) {
				String sLine = sb.toString();
				if(isSecondPart) {
					int iSep = sLine.indexOf(':');
					if(iSep <= 0) {
						isContent = sLine.length() == 0;
					}
				}
				System.out.println(sLine);
				if(sLine.startsWith("--uuid:")) {
					if(sUUIDPart == null) {
						sUUIDPart = sLine;
					}
					else 
					if(sUUIDPart.equals(sLine)){
						isSecondPart = true;
					}
				}
				// new line
				sb = new StringBuffer();
			}
		}
		byte[] content = baosContent.toByteArray();
		if(sUUIDPart != null) {
			int iBytesToRemove = (sUUIDPart + "--").length()+2;
			if(content.length <= iBytesToRemove) {
				return new byte[0];
			}
			else
			if(content.length > iBytesToRemove) {
				return Arrays.copyOf(content, content.length - iBytesToRemove);
			}
		}
		return new byte[0];
	}
	
	public static
	boolean isWSDLRequest(HttpServletRequest request)
	{
		String sWsdl = request.getParameter("wsdl");
		if(sWsdl != null) return true;
		sWsdl = request.getParameter("WSDL");
		if(sWsdl != null) return true;
		sWsdl = request.getParameter("Wsdl");
		if(sWsdl != null) return true;
		return false;
	}
	
	public static 
	void sendFault(HttpServletResponse response, String sNsURIEnvelope, int code, String message, String detail)
		throws IOException
	{
		if(message == null) message = "Service exception";
		message = normalizeString(message);
		if(detail == null) detail = "";
		detail  = normalizeString(detail);
		if(sNsURIEnvelope == null || sNsURIEnvelope.length() == 0) {
			sNsURIEnvelope = SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;
		}
		StringBuilder sb = new StringBuilder(555 + message.length() + detail.length());
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<soap:Envelope xmlns:soap=\"" + sNsURIEnvelope + "\">");
		sb.append("<soap:Header><To xmlns=\"http://www.w3.org/2005/08/addressing\">http://www.w3.org/2005/08/addressing/anonymous</To><Action xmlns=\"http://www.w3.org/2005/08/addressing\">http://www.w3.org/2005/08/addressing/fault</Action></soap:Header>");
		sb.append("<soap:Body><soap:Fault><faultcode>");
		sb.append(code);
		sb.append("</faultcode><faultstring>");
		sb.append(message);
		sb.append("</faultstring>");
		if(detail != null && detail.length() > 0) {
			sb.append("<detail>");
			sb.append(detail);
			sb.append("</detail>");
		}
		else {
			sb.append("<detail></detail>");
		}
		sb.append("</soap:Fault></soap:Body></soap:Envelope>");
		
		response.addHeader("Content-Type",      "text/xml; charset=\"utf-8\"");
		response.addHeader("Transfer-Encoding", "chunked");
		PrintWriter out = response.getWriter();
		out.write(sb.toString(), 0, sb.length());
		out.flush();
		
		if(TRACE) System.out.println("[FAULT] " + sb);
	}
	
	public static
	void sendResponse(HttpServletResponse response, String soapResponse, String sNsURIEnvelope)
		throws IOException
	{
		if(soapResponse == null) soapResponse = "";
		if(sNsURIEnvelope == null || sNsURIEnvelope.length() == 0) {
			sNsURIEnvelope = SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;
		}
		String sContentType = "";
		if(sNsURIEnvelope.startsWith("http://schemas")) {
			sContentType = "text/xml; charset=\"utf-8\""; // 1.1
		}
		else {
			sContentType = "application/soap+xml; charset=\"utf-8\""; // 1.2
		}
		StringBuilder sbResponse = new StringBuilder(107 + soapResponse.length());
		sbResponse.append("<soap:Envelope xmlns:soap=\"" + sNsURIEnvelope + "\">");
		sbResponse.append("<soap:Body>");
		if(soapResponse != null && soapResponse.length() > 0) {
			sbResponse.append(soapResponse);
		}
		sbResponse.append("</soap:Body>");
		sbResponse.append("</soap:Envelope>");
		
		response.addHeader("Content-Type", sContentType);
		PrintWriter out = response.getWriter();
		out.write(sbResponse.toString(), 0, sbResponse.length());
		out.flush();
		
		if(TRACE) System.out.println("[RESPONSE] " + sbResponse);
	}
	
	public static
	void sendResponse(HttpServletResponse response, String soapResponse, String sNsURIEnvelope, String sAction, String sMessageID)
		throws IOException
	{
		if(soapResponse == null) soapResponse = "";
		if(sNsURIEnvelope == null || sNsURIEnvelope.length() == 0) {
			sNsURIEnvelope = SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;
		}
		String sContentType = "";
		if(sNsURIEnvelope.startsWith("http://schemas")) {
			sContentType = "text/xml; charset=\"utf-8\""; // 1.1
		}
		else {
			sContentType = "application/soap+xml; charset=\"utf-8\""; // 1.2
		}
		StringBuilder sbResponse = new StringBuilder(320 + soapResponse.length());
		sbResponse.append("<soap:Envelope xmlns:soap=\"" + sNsURIEnvelope + "\">");
		if(sAction != null && sAction.length() > 0) {
			sbResponse.append("<soap:Header>");
			sbResponse.append("<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://www.w3.org/2005/08/addressing/anonymous</To>");
			sbResponse.append("<Action xmlns=\"http://www.w3.org/2005/08/addressing\">" + sAction + "</Action>");
			if(sMessageID != null && sMessageID.length() > 0) {
				sbResponse.append("<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:" + UUID.randomUUID() + "</MessageID>");
				sbResponse.append("<RelatesTo xmlns=\"http://www.w3.org/2005/08/addressing\">" + sMessageID + "</RelatesTo>");
			}
			sbResponse.append("</soap:Header>");
		}
		sbResponse.append("<soap:Body>");
		if(soapResponse != null && soapResponse.length() > 0) {
			sbResponse.append(soapResponse);
		}
		sbResponse.append("</soap:Body>");
		sbResponse.append("</soap:Envelope>");
		
		response.addHeader("Content-Type", sContentType);
		PrintWriter out = response.getWriter();
		out.write(sbResponse.toString(), 0, sbResponse.length());
		out.flush();
		
		if(TRACE) System.out.println("[RESPONSE] " + sbResponse);
	}
	
	public static
	void sendResponse(HttpServletResponse response, StringBuilder soapResponse, String sNsURIEnvelope, String sAttContentId, String sAttContentType, byte[] abAttContent)
		throws IOException
	{
		if(soapResponse == null) soapResponse = new StringBuilder();
		if(sNsURIEnvelope == null || sNsURIEnvelope.length() == 0) {
			sNsURIEnvelope = SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;
		}
		
		String boundary = "uuid:" + UUID.randomUUID();
		String root_msg = "<root.message>";
		
		StringBuilder sbResponse = new StringBuilder(107 + soapResponse.length());
		sbResponse.append("<soap:Envelope xmlns:soap=\"" + sNsURIEnvelope + "\">");
		sbResponse.append("<soap:Body>");
		if(soapResponse != null && soapResponse.length() > 0) {
			sbResponse.append(soapResponse);
		}
		sbResponse.append("</soap:Body>");
		sbResponse.append("</soap:Envelope>");
		
		response.addHeader("Content-Type", "multipart/related; type=\"application/xop+xml\"; boundary=\"" + boundary + "\"; start=\"" + root_msg + "\"; start-info=\"application/soap+xml\"");
		
		ServletOutputStream out = response.getOutputStream();
		out.write(("--" + boundary).getBytes());
		out.write(CRLF);
		out.write("Content-Type: application/xop+xml; charset=UTF-8; type=\"application/soap+xml\";".getBytes());
		out.write(CRLF);
		out.write("Content-Transfer-Encoding: binary".getBytes());
		out.write(CRLF);
		out.write(("Content-ID: " + root_msg).getBytes());
		out.write(CRLF);
		out.write(CRLF);
		out.write(sbResponse.toString().getBytes());
		out.write(CRLF);
		if(abAttContent != null && abAttContent.length > 0) {
			out.write(("--" + boundary).getBytes());
			out.write(CRLF);
			if(sAttContentType != null && sAttContentType.length() > 0) {
				out.write(("Content-Type: " + sAttContentType).getBytes());
				out.write(CRLF);
			}
			out.write("Content-Transfer-Encoding: binary".getBytes());
			out.write(CRLF);
			if(sAttContentId != null && sAttContentId.length() > 0) {
				out.write(("Content-ID: <" + sAttContentId + ">").getBytes());
				out.write(CRLF);
			}
			out.write(CRLF);
			out.write(abAttContent);
			out.write(CRLF);
		}
		out.write(("--" + boundary + "--").getBytes());
		out.flush();
		
		if(TRACE) System.out.println("[RESPONSE] " + sbResponse);
	}
	
	public static
	SOAPMessage sendRequest(String sURL, String basicAuth, int connTimeout, int readTimeout, String sContentType, byte[] request)
		throws Exception
	{
		URL url = new URL(sURL);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if(sContentType != null && sContentType.length() > 0) {
			conn.addRequestProperty("Content-Type", sContentType);
		}
		else {
			conn.addRequestProperty("Content-Type", "application/soap+xml");
		}
		if(basicAuth != null && basicAuth.length() > 0) {
			conn.addRequestProperty("Authorization", "Basic " + basicAuth);
		}
		conn.setRequestMethod("POST");
		if(connTimeout > 0) conn.setConnectTimeout(connTimeout);
		if(readTimeout > 0) conn.setReadTimeout(readTimeout);
		
		return sendRequest(conn, request);
	}
	
	public static
	SOAPMessage sendRequest(String sURL, String basicAuth, int connTimeout, int readTimeout, String sContentType, String sSOAPAction, byte[] request)
		throws Exception
	{
		URL url = new URL(sURL);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if(sContentType != null && sContentType.length() > 0) {
			conn.addRequestProperty("Content-Type", sContentType);
		}
		else {
			conn.addRequestProperty("Content-Type", "application/soap+xml");
		}
		if(sSOAPAction != null && sSOAPAction.length() > 0) {
			conn.addRequestProperty("SOAPAction", sSOAPAction);
		}
		if(basicAuth != null && basicAuth.length() > 0) {
			conn.addRequestProperty("Authorization", "Basic " + basicAuth);
		}
		conn.setRequestMethod("POST");
		if(connTimeout > 0) conn.setConnectTimeout(connTimeout);
		if(readTimeout > 0) conn.setReadTimeout(readTimeout);
		
		return sendRequest(conn, request);
	}
	
	public static
	SOAPMessage sendRequest(String sURL, SSLSocketFactory sslSocketFactory, int connTimeout, int readTimeout, String sContentType, byte[] request)
		throws Exception
	{
		URL url = new URL(sURL);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if(sContentType != null && sContentType.length() > 0) {
			conn.addRequestProperty("Content-Type", sContentType);
		}
		else {
			conn.addRequestProperty("Content-Type", "application/soap+xml");
		}
		if(sslSocketFactory != null) {
			if(conn instanceof HttpsURLConnection) {
				((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
			}
		}
		conn.setRequestMethod("POST");
		if(connTimeout > 0) conn.setConnectTimeout(connTimeout);
		if(readTimeout > 0) conn.setReadTimeout(readTimeout);
		
		return sendRequest(conn, request);
	}
	
	public static
	SOAPMessage sendRequest(String sURL, SSLSocketFactory sslSocketFactory, int connTimeout, int readTimeout, String sContentType, String sSOAPAction, byte[] request)
		throws Exception
	{
		URL url = new URL(sURL);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if(sContentType != null && sContentType.length() > 0) {
			conn.addRequestProperty("Content-Type", sContentType);
		}
		else {
			conn.addRequestProperty("Content-Type", "application/soap+xml");
		}
		if(sSOAPAction != null && sSOAPAction.length() > 0) {
			conn.addRequestProperty("SOAPAction", sSOAPAction);
		}
		if(sslSocketFactory != null) {
			if(conn instanceof HttpsURLConnection) {
				((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
			}
		}
		conn.setRequestMethod("POST");
		if(connTimeout > 0) conn.setConnectTimeout(connTimeout);
		if(readTimeout > 0) conn.setReadTimeout(readTimeout);
		
		return sendRequest(conn, request);
	}
	
	public static
	SOAPMessage sendRequest(HttpURLConnection conn, byte[] request)
		throws Exception
	{
		int statusCode = 0;
		List<String> listHeader = new ArrayList<String>();
		ByteArrayOutputStream baos = null;
		OutputStream os = null;
		try {
			if(request != null && request.length > 0) {
				conn.setDoOutput(true);
			}
			conn.connect();
			
			if(request != null && request.length > 0) {
				os = conn.getOutputStream();
				os.write(request);
				os.flush();
				os.close();
				os = null;
			}
			
			statusCode = conn.getResponseCode();
			if(statusCode != HttpURLConnection.HTTP_OK && statusCode != HttpURLConnection.HTTP_INTERNAL_ERROR && statusCode != HttpURLConnection.HTTP_BAD_REQUEST) {
				throw new IOException("Unexpected status code returned: " + statusCode);
			}
			
			try {
				int i = 0; 
				while(true) {
					i++;
					String sHeaderFieldKey = conn.getHeaderFieldKey(i);
					String sHeaderField    = conn.getHeaderField(i);
					if(sHeaderField == null || sHeaderField.length() == 0) break;
					listHeader.add(sHeaderFieldKey + ": " + sHeaderField);
					if(i > 100) break;
				}
			}
			catch(Throwable th) {
				th.printStackTrace();
			}
			
			baos = new ByteArrayOutputStream();
			InputStream in = null;
			try {
				if(statusCode == HttpURLConnection.HTTP_OK) {
					in = conn.getInputStream();
				}
				else {
					in = conn.getErrorStream();
				}
				byte[] buff = new byte[1024];
				int n;
				while ((n = in.read(buff)) > 0) {
					baos.write(buff, 0, n);
				}
				baos.flush();
				baos.close();
			} 
			finally {
				if(in != null) try{ in.close(); } catch(Exception ex) {}
			}
		}
		finally {
			if(os != null) try{ os.close(); } catch(Exception ex) {}
		}
		
		MimeHeaders headers = new MimeHeaders();
		for(int i = 0; i < listHeader.size(); i++) {
			String sHeaderLine = listHeader.get(i);
			int iSep = sHeaderLine.indexOf(':');
			if(iSep <= 0) continue;
			String sHeaderName  = sHeaderLine.substring(0,iSep).trim();
			String sHeaderValue = sHeaderLine.substring(iSep+1).trim();
			if("Content-Type".equalsIgnoreCase(sHeaderName)) {
				if(sHeaderValue != null && sHeaderValue.length() > 0) {
					if(sHeaderValue.indexOf("boundary=") >= 0 || sHeaderValue.indexOf("start=") >= 0) {
						headers.addHeader(sHeaderName, sHeaderValue);
					}
				}
			}
			else
			if("SOAPAction".equalsIgnoreCase(sHeaderName)) {
				headers.addHeader(sHeaderName, sHeaderValue);
			}
		}
		
		byte[] response = baos.toByteArray();
		if(response == null || response.length == 0) {
			if(statusCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
				throw new IOException("HTTP status code returned : " + statusCode);
			}
			else {
				throw new IOException("No response message received");
			}
		}
		else {
			if(statusCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
				if(response[0] != '<') {
					throw new IOException(new String(response));
				}
			}
		}
		
		MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		
		return messageFactory.createMessage(headers, new ByteArrayInputStream(response));
	}
	
	public static
	void sendHTMLPage(HttpServletResponse response, String title, String body)
		throws IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		if(title != null && title.length() > 0) {
			out.println("<title>" + title + "</title>");
		}
		out.println("</head>");
		out.println("<body>");
		if(body != null && body.length() > 0) {
			out.println(body);
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	public static 
	String normalizeString(String sValue) 
	{
		if(sValue == null) return "null";
		int iLength = sValue.length();
		if(iLength == 0) return "";
		StringBuilder sb = new StringBuilder(iLength);
		for(int i = 0; i < iLength; i++) {
			char c = sValue.charAt(i);
			if(c == '<')  sb.append("&lt;");   else
			if(c == '>')  sb.append("&gt;");   else
			if(c == '&')  sb.append("&amp;");  else
			if(c == '"')  sb.append("&quot;"); else
			if(c == '\'') sb.append("&apos;"); else
			if(c > 127) {
				int code = (int) c;
				sb.append("&#" + code + ";");
			}
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static
	AuthAssertion[] toArray(AuthAssertion first, List<AuthAssertion> listOfAssertion)
	{
		int iLength = 0;
		if(first != null) {
			iLength++;
		}
		if(listOfAssertion != null) {
			iLength += listOfAssertion.size();
		}
		AuthAssertion[] result = new AuthAssertion[iLength];
		if(first != null) {
			result[0] = first;
		}
		for(int i = 0; i < listOfAssertion.size(); i++) {
			int index = first != null ? i + 1: i;
			result[index] = listOfAssertion.get(i);
		}
		return result;
	}
}
