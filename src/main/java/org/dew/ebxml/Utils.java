package org.dew.ebxml;

import java.lang.reflect.Array;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dew.xds.XDS;
import org.dew.xds.util.Base64Coder;

public 
class Utils 
{
  public static 
  String normalizeString(String sValue) 
  {
    if(sValue == null) return "null";
    int iLength = sValue.length();
    if(iLength == 0) return "";
    StringBuffer sb = new StringBuffer(iLength);
    for(int i = 0; i < iLength; i++) {
      char c = sValue.charAt(i);
      if(c == '<')  sb.append("&lt;");
      else if(c == '>')  sb.append("&gt;");
      else if(c == '&')  sb.append("&amp;");
      else if(c == '"')  sb.append("&quot;");
      else if(c == '\'') sb.append("&apos;");
      else if(c > 126) {
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
  Identifiable createRegistryObject(Map<String, Object> map) 
  {
    if(map == null || map.isEmpty()) return null;
    String sTagName = (String) map.get("tagName");
    if("Association".equals(sTagName) || check(map, "associationType", "sourceObject", "targetObject")) {
      return new Association(map);
    }
    else if("Classification".equals(sTagName) || check(map, "classificationScheme", "classifiedObject", "nodeRepresentation")) {
      return new Classification(map);
    }
    else if("ExternalIdentifier".equals(sTagName) || check(map, "identificationScheme", "registryObject")) {
      return new ExternalIdentifier(map);
    }
    else if("ExternalLink".equals(sTagName) || check(map, "externalURI")) {
      return new ExternalLink(map);
    }
    else if("ExtrinsicObject".equals(sTagName) || check(map, "mimeType", "opaque", "isOpaque")) {
      return new ExtrinsicObject(map);
    }
    else if("RegistryPackage".equals(sTagName) || check(map, "status")) {
      return new RegistryPackage(map);
    }
    else if("ObjectRef".equals(sTagName)) {
      return new ObjectRef(map);
    }
    else if("Identifiable".equals(sTagName)) {
      return new Identifiable(map);
    }
    return null;
  }
  
  public static 
  String getPatientId(RegistryObject registryObject) 
  {
    if(registryObject == null) return null;
    String sPatientId = registryObject.getSlotFirstValue("sourcePatientId");
    if(sPatientId != null && sPatientId.length() > 0) {
      sPatientId = normalizePersonId(sPatientId);
    }
    if(sPatientId == null || sPatientId.length() == 0) {
      ExternalIdentifier externalIdentifier = registryObject.getExternalIdentifier("XDSDocumentEntry.patientId");
      if(externalIdentifier != null) {
        sPatientId = externalIdentifier.getValue();
        if(sPatientId != null && sPatientId.length() > 0) {
          sPatientId = normalizePersonId(sPatientId);
        }
      }
    }
    return sPatientId;
  }
  
  public static 
  String getAuthorId(RegistryObject registryObject) 
  {
    if(registryObject == null) return null;
    String sAuthorId = null;
    Classification classification = registryObject.getClassification(XDS.CLS_AUTHOR);
    if(classification != null) {
      sAuthorId = classification.getSlotFirstValue("authorPerson");
      if(sAuthorId != null && sAuthorId.length() > 0) {
        sAuthorId = normalizePersonId(sAuthorId);
      }
    }
    return sAuthorId;
  }
  
  public static 
  String getAuthorPerson(RegistryObject registryObject) 
  {
    if(registryObject == null) return null;
    String result = null;
    Classification classification = registryObject.getClassification(XDS.CLS_AUTHOR);
    if(classification != null) {
      result = classification.getSlotFirstValue("authorPerson");
    }
    return result;
  }
  
  public static 
  String[] getAuthorInstitution(String value) 
  {
    // ULSS N - NAME^^^^^&2.16.840.1.113883.2.9.4.1.3&ISO^^^^999109
    if(value != null && value.length() > 0) {
      int iSepL = value.lastIndexOf('^');
      if(iSepL >= 0 && iSepL < value.length() - 1) {
        String sCode = value.substring(iSepL+1);
        int iSepF = value.indexOf('^');
        if(iSepF < 0) iSepF = iSepL;
        String sName = value.substring(0,iSepF);
        return new String[] { sCode, sName };
      }
      if(value.indexOf(' ') > 0) {
        return new String[] { "", value };
      }
      return new String[] { value, "" };
    }
    return null;
  }
  
  public static 
  String[] getAuthorInstitution(RegistryObject registryObject) 
  {
    if(registryObject == null) return null;
    Classification classification = registryObject.getClassification(XDS.CLS_AUTHOR);
    if(classification != null) {
      // ULSS N - NAME^^^^^&2.16.840.1.113883.2.9.4.1.3&ISO^^^^999109
      String result = classification.getSlotFirstValue("authorInstitution");
      if(result != null && result.length() > 0) {
        int iSepL = result.lastIndexOf('^');
        if(iSepL >= 0 && iSepL < result.length() - 1) {
          String sCode = result.substring(iSepL+1);
          int iSepF = result.indexOf('^');
          if(iSepF < 0) iSepF = iSepL;
          String sName = result.substring(0,iSepF);
          return new String[] { sCode, sName };
        }
        if(result.indexOf(' ') > 0) {
          return new String[] { "", result };
        }
        return new String[] { result, "" };
      }
    }
    return null;
  }
  
  public static 
  String getAuthorInstitutionCode(RegistryObject registryObject) 
  {
    if(registryObject == null) return null;
    Classification classification = registryObject.getClassification(XDS.CLS_AUTHOR);
    if(classification != null) {
      // ULSS N - NAME^^^^^&2.16.840.1.113883.2.9.4.1.3&ISO^^^^999109
      String result = classification.getSlotFirstValue("authorInstitution");
      if(result != null && result.length() > 0) {
        int iSep = result.lastIndexOf('^');
        if(iSep >= 0 && iSep < result.length() - 1) {
          result = result.substring(iSep + 1);
        }
        return result;
      }
    }
    return null;
  }
  
  public static 
  String getAuthorInstitutionName(RegistryObject registryObject) 
  {
    if(registryObject == null) return null;
    Classification classification = registryObject.getClassification(XDS.CLS_AUTHOR);
    if(classification != null) {
      // ULSS N - TEST^^^^^&2.16.840.1.113883.2.9.4.1.3&ISO^^^^999109
      String result = classification.getSlotFirstValue("authorInstitution");
      if(result != null && result.length() > 0) {
        int iSep = result.indexOf('^');
        if(iSep >= 0) result = result.substring(0, iSep);
        return result;
      }
    }
    return null;
  }
  
  public static 
  String getAuthorRole(RegistryObject registryObject) 
  {
    if(registryObject == null) return null;
    Classification classification = registryObject.getClassification(XDS.CLS_AUTHOR);
    if(classification != null) {
      // ULSS N - TEST^^^^^&2.16.840.1.113883.2.9.4.1.3&ISO^^^^999109
      String result = classification.getSlotFirstValue("authorRole");
      if(result != null && result.length() > 0) {
        int iSep = result.lastIndexOf('^');
        if(iSep >= 0 && iSep < result.length() - 1) {
          result = result.substring(iSep + 1);
        }
        return result;
      }
    }
    return null;
  }
  
  public static 
  String getAuthorEmail(RegistryObject registryObject) 
  {
    if(registryObject == null) return null;
    Classification classification = registryObject.getClassification(XDS.CLS_AUTHOR);
    if(classification != null) {
      String result = classification.getSlotFirstValue("authorTelecommunication");
      if(result != null && result.length() > 4) {
        int iSep = result.lastIndexOf('^');
        if(iSep > 0 && iSep < result.length() - 1) {
          result = result.substring(iSep + 1);
        }
        if(result.length() > 4 && result.indexOf('@') > 0) {
          return result.toLowerCase();
        }
      }
    }
    return null;
  }
  
  public static 
  String getLegalAuthenticatorId(RegistryObject registryObject) 
  {
    if(registryObject == null) return null;
    String sLegalAuthenticatorId = registryObject.getSlotFirstValue("legalAuthenticator");
    if(sLegalAuthenticatorId != null && sLegalAuthenticatorId.length() > 0) {
      sLegalAuthenticatorId = normalizePersonId(sLegalAuthenticatorId);
    }
    return sLegalAuthenticatorId;
  }
  
  public static 
  String normalizePersonId(String value) 
  {
    if(value == null) return null;
    int iSep = value.indexOf('^');
    if(iSep >= 0) value = value.substring(0,iSep);
    iSep = value.lastIndexOf('.');
    if(iSep >= 0) value = value.substring(iSep+1);
    // "VRDMRC67T20I257E"^^^^^^^^&"2.16.840.1.113883.2.9.4.3.2"&"ISO"
    // 'VRDMRC67T20I257E^^^&2.16.840.1.113883.2.9.4.3.2&ISO'
    if(value.startsWith("\"") || value.startsWith("'")) {
      value = value.substring(1);
    }
    if(value.endsWith("\"") || value.endsWith("'")) {
      value = value.substring(0,value.length()-1);
    }
    return value.trim().toUpperCase();
  }
  
  public static 
  String normalizeStatus(String value) 
  {
    if(value == null || value.length() == 0) return null;
    String sValueLC = value.toLowerCase();
    if(sValueLC.indexOf("app") >= 0) {
      return RIM.STATUS_APPROVED;
    }
    else if(sValueLC.indexOf("dep") >= 0) {
      return RIM.STATUS_DEPRECATED;
    }
    else if(sValueLC.indexOf("sub") >= 0) {
      return RIM.STATUS_SUBMITTED;
    }
    else if(sValueLC.indexOf("wit") >= 0) {
      return RIM.STATUS_WITHDRAWN;
    }
    else if(value.startsWith("urn:")) {
      return value;
    }
    return RIM.STATUS_APPROVED;
  }
  
  public static 
  String extractIdFromUniqueId(String documentUniqueId) 
  {
    if(documentUniqueId == null || documentUniqueId.length() == 0) {
      return documentUniqueId;
    }
    // 2.16.840.1.113883.2.9.2.999.4.4^012345
    int iSep = documentUniqueId.lastIndexOf('^');
    if(iSep > 0) {
      return documentUniqueId.substring(iSep+1);
    }
    // 2.16.840.1.113883.2.9.2.999.4.4.012345
    iSep = documentUniqueId.lastIndexOf('.');
    if(iSep > 0) {
      return documentUniqueId.substring(iSep+1);
    }
    return documentUniqueId;
  }
  
  public static 
  String extractRootFromUniqueId(String documentUniqueId) 
  {
    if(documentUniqueId == null || documentUniqueId.length() == 0) {
      return documentUniqueId;
    }
    // 2.16.840.1.113883.2.9.2.999.4.4^012345
    int iSep = documentUniqueId.indexOf('^');
    if(iSep > 0) {
      return documentUniqueId.substring(0, iSep);
    }
    // 2.16.840.1.113883.2.9.2.999.4.4.012345
    iSep = documentUniqueId.lastIndexOf('.');
    if(iSep > 0) {
      return documentUniqueId.substring(0, iSep);
    }
    return "";
  }
  
  public static 
  String extractCode(String value) 
  {
    if(value == null || value.length() == 0) return value;
    int iSep = value.indexOf('^');
    if(iSep >= 0) {
      String sRight = value.substring(iSep+1);
      String sLeft  = value.substring(0,iSep);
      if(sLeft.length() > sRight.length() && sLeft.indexOf('.') > 0 && sRight.length() <= 10) {
        // 2.16.840.1.113883.2.9.2.999.4.4^123456
        value = sRight;
      }
      else {
        value = sLeft;
      }
      // "VRDMRC67T20I257E"^^^^^^^^&"2.16.840.1.113883.2.9.4.3.2"&"ISO"
      if(value.startsWith("\"") && value.endsWith("\"")) {
        value = value.substring(1,value.length()-1);
      }
      return value;
    }
    iSep = value.lastIndexOf('.');
    if(iSep >= 0) value = value.substring(iSep+1);
    return value;
  }
  
  public static 
  String extractCodingScheme(String value) 
  {
    if(value == null || value.length() == 0) return "";
    // Name^^^^^2.16.840.1.113883.2.9.4.1.3&ISO^^^^120267
    // VRDMRC67T20I257E^^^^^^^^&2.16.840.1.113883.2.9.4.3.2&ISO
    // 60591-5^2.16.840.1.113883.6.1
    // 60591-5^^2.16.840.1.113883.6.1
    int iFirst = value.indexOf('^');
    int iISO   = value.lastIndexOf("&ISO");
    if(iISO > iFirst) value = value.substring(0,iISO);
    int iSep = value.lastIndexOf('^');
    if(iSep >= 0) {
      String sRight = value.substring(iSep+1);
      String sLeft  = value.substring(0,iSep);
      String result = null;
      if(sLeft.length() > sRight.length() && sLeft.indexOf('.') > 0 && sRight.length() <= 10) {
        // 2.16.840.1.113883.2.9.2.999.4.4^123456
        result = sLeft;
      }
      else {
        result = sRight;
      }
      if(result.startsWith("&")) {
        result = result.substring(1);
      }
      int iEnd = result.indexOf('&');
      if(iEnd > 0) result = result.substring(0,iEnd);
      // "VRDMRC67T20I257E"^^^^^^^^&"2.16.840.1.113883.2.9.4.3.2"&"ISO"
      if(result.startsWith("\"") && result.endsWith("\"")) {
        result = result.substring(1,result.length()-1);
      }
      return result;
    }
    // 2.16.840.1.113883.6.1.60591-5
    iSep = value.lastIndexOf('.');
    if(iSep > 0) {
      return value.substring(0, iSep);
    }
    return "";
  }
  
  public static 
  String normalizeAttributeName(String sName) 
  {
    if(sName == null || sName.length() == 0) return "N_A";
    StringBuffer sb = new StringBuffer(sName.length());
    for(int i = 0; i < sName.length(); i++) {
      char c = sName.charAt(i);
      if(c < 48) {
        sb.append('_');
      }
      else if(c > 122) {
        sb.append('_');
      }
      else if(Character.isDigit(c)) {
        sb.append(c);
      }
      else if(Character.isLetter(c)) {
        sb.append(c);
      }
      else {
        sb.append('_');
      }
    }
    return sb.toString();
  }
  
  public static 
  List<String> toListOfString(Object object) 
  {
    if(object == null) return null;
    if(object instanceof Collection) {
      Collection<?> collection = (Collection<?>) object;
      List<String> result = new ArrayList<String>(collection.size());
      Iterator<?> iterator = collection.iterator();
      while(iterator.hasNext()) {
        result.add(toString(iterator.next(), ""));
      }
      return result;
    }
    if(object.getClass().isArray()) {
      int length = Array.getLength(object);
      List<String> result = new ArrayList<String>(length);
      for(int i = 0; i < length; i++) {
        Object item = Array.get(object, i);
        result.add(toString(item, ""));
      }
      return result;
    }
    return stringToList(object.toString());
  }
  
  public static 
  String toString(Object object, String sDefault) 
  {
    if(object == null) return sDefault;
    if(object instanceof String) {
      return (String) object;
    }
    if(object instanceof java.util.Date) {
      return formatDateTime((java.util.Date) object); 
    }
    if(object instanceof java.util.Calendar) {
      return formatDateTime((java.util.Calendar) object);
    }
    if(object instanceof byte[]) {
      return new String((byte[]) object);
    }
    if(object instanceof char[]) {
      return new String((char[]) object);
    }
    return object.toString();
  }
  
  public static 
  String formatDate(Date date) 
  {
    if(date == null) return "";
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    int iYear  = cal.get(Calendar.YEAR);
    int iMonth = cal.get(Calendar.MONTH) + 1;
    int iDay   = cal.get(Calendar.DATE);
    String sMonth = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
    String sDay   = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
    return iYear + sMonth + sDay;
  }
  
  public static 
  String formatXsDate(Date date) 
  {
    if(date == null) return "";
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    int iYear  = cal.get(Calendar.YEAR);
    int iMonth = cal.get(Calendar.MONTH) + 1;
    int iDay   = cal.get(Calendar.DATE);
    String sMonth = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
    String sDay   = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
    return iYear + "-" + sMonth + "-" + sDay;
  }
  
  public static 
  String formatDateTime(Date date) 
  {
    if(date == null) return "";
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    int iYear  = cal.get(Calendar.YEAR);
    int iMonth = cal.get(Calendar.MONTH) + 1;
    int iDay   = cal.get(Calendar.DATE);
    int iHour  = cal.get(Calendar.HOUR_OF_DAY);
    int iMin   = cal.get(Calendar.MINUTE);
    int iSec   = cal.get(Calendar.SECOND);
    String sMonth = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
    String sDay   = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
    String sHour  = iHour  < 10 ? "0" + iHour  : String.valueOf(iHour);
    String sMin   = iMin   < 10 ? "0" + iMin   : String.valueOf(iMin);
    String sSec   = iSec   < 10 ? "0" + iSec   : String.valueOf(iSec);
    if(iHour == 0 && iMin == 0 && iSec == 0) {
      return iYear + sMonth + sDay;
    }
    return iYear + sMonth + sDay + sHour + sMin + sSec;
  }
  
  public static 
  String formatTime(Date date) 
  {
    if(date == null) return "";
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    int iYear  = cal.get(Calendar.YEAR);
    int iMonth = cal.get(Calendar.MONTH) + 1;
    int iDay   = cal.get(Calendar.DATE);
    int iHour  = cal.get(Calendar.HOUR_OF_DAY);
    int iMin   = cal.get(Calendar.MINUTE);
    int iSec   = cal.get(Calendar.SECOND);
    String sMonth = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
    String sDay   = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
    String sHour  = iHour  < 10 ? "0" + iHour  : String.valueOf(iHour);
    String sMin   = iMin   < 10 ? "0" + iMin   : String.valueOf(iMin);
    String sSec   = iSec   < 10 ? "0" + iSec   : String.valueOf(iSec);
    return iYear + sMonth + sDay + sHour + sMin + sSec;
  }
  
  public static 
  String formatDateTime(Calendar cal) 
  {
    if(cal == null) return "";
    int iYear  = cal.get(Calendar.YEAR);
    int iMonth = cal.get(Calendar.MONTH) + 1;
    int iDay   = cal.get(Calendar.DATE);
    int iHour  = cal.get(Calendar.HOUR_OF_DAY);
    int iMin   = cal.get(Calendar.MINUTE);
    int iSec   = cal.get(Calendar.SECOND);
    String sMonth = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
    String sDay   = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
    String sHour  = iHour  < 10 ? "0" + iHour  : String.valueOf(iHour);
    String sMin   = iMin   < 10 ? "0" + iMin   : String.valueOf(iMin);
    String sSec   = iSec   < 10 ? "0" + iSec   : String.valueOf(iSec);
    if(iHour == 0 && iMin == 0 && iSec == 0) {
      return iYear + sMonth + sDay;
    }
    return iYear + sMonth + sDay + sHour + sMin + sSec;
  }
  
  public static 
  String formatTime(Calendar cal) 
  {
    if(cal == null) return "";
    int iYear  = cal.get(Calendar.YEAR);
    int iMonth = cal.get(Calendar.MONTH) + 1;
    int iDay   = cal.get(Calendar.DATE);
    int iHour  = cal.get(Calendar.HOUR_OF_DAY);
    int iMin   = cal.get(Calendar.MINUTE);
    int iSec   = cal.get(Calendar.SECOND);
    String sMonth = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
    String sDay   = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
    String sHour  = iHour  < 10 ? "0" + iHour  : String.valueOf(iHour);
    String sMin   = iMin   < 10 ? "0" + iMin   : String.valueOf(iMin);
    String sSec   = iSec   < 10 ? "0" + iSec   : String.valueOf(iSec);
    return iYear + sMonth + sDay + sHour + sMin + sSec;
  }
  
  public static 
  String formatISO8601_Z(Date date) 
  {
    if(date == null) return "";
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    return formatISO8601_Z(cal, true);
  }
  
  public static 
  String formatISO8601_Z(Date date, boolean millis) 
  {
    if(date == null) return "";
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    return formatISO8601_Z(cal, millis);
  }
  
  public static 
  String formatISO8601_Z(Calendar cal) 
  {
    return formatISO8601_Z(cal, true);
  }
  
  public static 
  String formatISO8601_Z(Calendar dateTime, boolean millis) 
  {
    if(dateTime == null) return "";
    
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(dateTime.getTimeInMillis());
    
    int iZoneOffset = cal.get(Calendar.ZONE_OFFSET);
    cal.add(Calendar.MILLISECOND, -iZoneOffset);
    int iDST_Offset = cal.get(Calendar.DST_OFFSET);
    cal.add(Calendar.MILLISECOND, -iDST_Offset);
    
    int iYear  = cal.get(Calendar.YEAR);
    int iMonth = cal.get(Calendar.MONTH) + 1;
    int iDay   = cal.get(Calendar.DATE);
    int iHour  = cal.get(Calendar.HOUR_OF_DAY);
    int iMin   = cal.get(Calendar.MINUTE);
    int iSec   = cal.get(Calendar.SECOND);
    int iMill  = cal.get(Calendar.MILLISECOND);
    String sYear   = String.valueOf(iYear);
    String sMonth  = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
    String sDay    = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
    String sHour   = iHour  < 10 ? "0" + iHour  : String.valueOf(iHour);
    String sMin    = iMin   < 10 ? "0" + iMin   : String.valueOf(iMin);
    String sSec    = iSec   < 10 ? "0" + iSec   : String.valueOf(iSec);
    String sMill   = String.valueOf(iMill);
    if(iYear < 10) {
      sYear = "000" + sYear;
    }
    else if(iYear < 100) {
      sYear = "00" + sYear;
    }
    else if(iYear < 1000) {
      sYear = "0" + sYear;
    }
    if(iMill < 10) {
      sMill = "00" + sMill; 
    }
    else if(iMill < 100) {
      sMill = "0" + sMill; 
    }
    if(millis) {
      return sYear + "-" + sMonth + "-" + sDay + "T" + sHour + ":" + sMin + ":" + sSec + "." + sMill + "Z";
    }
    return sYear + "-" + sMonth + "-" + sDay + "T" + sHour + ":" + sMin + ":" + sSec + "Z";
  }
  
  public static 
  Date getCurrentDate() 
  {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE,      0);
    cal.set(Calendar.SECOND,      0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }
  
  public static
  boolean toBoolean(Object object, boolean bDefault) 
  {
    if(object == null) return bDefault;
    if(object instanceof Boolean) {
      return ((Boolean) object).booleanValue();
    }
    if(object instanceof Number) {
      return ((Number) object).intValue() != 0;
    }
    String sValue = object.toString();
    if(sValue.length() == 0) return bDefault;
    char c0 = sValue.charAt(0);
    return "YySsTt1Jj".indexOf(c0) >= 0;
  }
  
  public static 
  Date toDate(Object object) 
  {
    if(object == null) return null;
    if(object instanceof Date) {
      return (Date) object;
    }
    if(object instanceof Calendar) {
      return ((Calendar) object).getTime();
    }
    String sValue = object.toString();
    Calendar calendar = stringToCalendar(sValue);
    if(calendar == null) return null;
    return calendar.getTime();
  }
  
  public static
  java.sql.Date toSQLDate(Object object, Object oDefault)
  {
    if(object == null) {
      if(oDefault == null) return null;
      return toSQLDate(oDefault, null);
    }
    if(object instanceof java.sql.Date) {
      return (java.sql.Date) object;
    }
    if(object instanceof java.util.Date) {
      return new java.sql.Date(((java.util.Date) object).getTime());
    }
    if(object instanceof java.util.Calendar) {
      return new java.sql.Date(((java.util.Calendar) object).getTimeInMillis());
    }
    if(object instanceof Long) {
      return new java.sql.Date(((Long) object).longValue());
    }
    if(object instanceof Number) {
      int iDate = ((Number) object).intValue();
      if(iDate < 10000) return toSQLDate(oDefault, null);
      int iYear  = iDate / 10000;
      int iMonth = (iDate % 10000) / 100;
      int iDay   = (iDate % 10000) % 100;
      return new java.sql.Date(new GregorianCalendar(iYear, iMonth-1, iDay).getTimeInMillis());
    }
    Calendar cal = stringToCalendar(object.toString());
    if(cal == null) return toSQLDate(oDefault, null);
    return new java.sql.Date(cal.getTimeInMillis());
  }
  
  public static
  java.sql.Timestamp toSQLTimestamp(Object object, Object oDefault)
  {
    if(object == null) {
      if(oDefault == null) return null;
      return toSQLTimestamp(oDefault, null);
    }
    if(object instanceof java.sql.Timestamp) {
      return (java.sql.Timestamp) object;
    }
    if(object instanceof java.util.Date) {
      return new java.sql.Timestamp(((java.util.Date) object).getTime());
    }
    if(object instanceof java.util.Calendar) {
      return new java.sql.Timestamp(((java.util.Calendar) object).getTimeInMillis());
    }
    if(object instanceof Long) {
      return new java.sql.Timestamp(((Long) object).longValue());
    }
    if(object instanceof Number) {
      int iDate = ((Number) object).intValue();
      if(iDate < 10000) return toSQLTimestamp(oDefault, null);
      int iYear  = iDate / 10000;
      int iMonth = (iDate % 10000) / 100;
      int iDay   = (iDate % 10000) % 100;
      return new java.sql.Timestamp(new GregorianCalendar(iYear, iMonth-1, iDay).getTimeInMillis());
    }
    Calendar cal = stringToCalendar(object.toString());
    if(cal == null) return toSQLTimestamp(oDefault, null);
    return new java.sql.Timestamp(cal.getTimeInMillis());
  } 
  
  public static 
  int toInt(Object object) 
  {
    if(object == null) return 0;
    if(object instanceof Number) {
      return ((Number) object).intValue();
    }
    String sValue = object.toString();
    int iResult = 0;
    try{ iResult = Integer.parseInt(sValue); } catch(Exception ex) {}
    return iResult;
  }
  
  public static
  java.util.Calendar stringToCalendar(String sTime)
  {
    if(sTime == null) return null;
    int iLength = sTime.length();
    if(iLength ==  0) return null;
    if(iLength > 19 && sTime.endsWith("Z")) {
      int iYear  = Integer.parseInt(sTime.substring( 0,  4));
      int iMonth = Integer.parseInt(sTime.substring( 5,  7));
      int iDay   = Integer.parseInt(sTime.substring( 8, 10));
      int iHour  = Integer.parseInt(sTime.substring(11, 13));
      int iMin   = Integer.parseInt(sTime.substring(14, 16));
      int iSec   = Integer.parseInt(sTime.substring(17, 19));
      int iMill  = 0;
      if(sTime.length() > 23) {
        iMill  = Integer.parseInt(sTime.substring(20, 23));
      }
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR,        iYear);
      c.set(Calendar.MONTH,       iMonth-1);
      c.set(Calendar.DATE,        iDay);
      c.set(Calendar.HOUR_OF_DAY, iHour);
      c.set(Calendar.MINUTE,      iMin);
      c.set(Calendar.SECOND,      iSec);
      c.set(Calendar.MILLISECOND, iMill);
      int iZoneOffset = c.get(Calendar.ZONE_OFFSET);
      c.add(Calendar.MILLISECOND, iZoneOffset);
      int iDST_Offset = c.get(Calendar.DST_OFFSET);
      c.add(Calendar.MILLISECOND, iDST_Offset);
      
      Calendar r = new GregorianCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
      r.set(Calendar.MILLISECOND,c.get(Calendar.MILLISECOND));
      return r;
    }
    String sDate = null;
    if(iLength > 7) {
      sDate = normalizeStringDate(sTime);
    }
    String sNorm = normalizeStringTime(sTime, sDate != null);
    if(sNorm == null || sNorm.length() < 4) return null;
    if(sNorm.equals("000000") && sTime.length() < 5 && !Character.isDigit(sTime.charAt(0))) {
      return null;
    }
    int iHH = 0;
    int iMM = 0;
    int iSS = 0;
    int iMS = 0;
    try { iHH = Integer.parseInt(sNorm.substring(0, 2)); } catch(Throwable th) {}
    try { iMM = Integer.parseInt(sNorm.substring(2, 4)); } catch(Throwable th) {}
    if(sNorm.length() > 5) {
      try { iSS = Integer.parseInt(sNorm.substring(4, 6)); } catch(Throwable th) {}
    }
    if(sNorm.length() > 8) {
      try { iMS = Integer.parseInt(sNorm.substring(6, 9)); } catch(Throwable th) {}
    }
    Calendar calendar = Calendar.getInstance();
    if(iLength > 10 || sNorm.equals("000000")) {
      if(sDate != null && sDate.length() > 0) {
        int iDate  = 0;
        try {
          iDate = Integer.parseInt(sDate);
          int iYear  = iDate / 10000;
          int iMonth = (iDate % 10000) / 100;
          int iDay   = (iDate % 10000) % 100;
          calendar.set(Calendar.YEAR,  iYear);
          calendar.set(Calendar.MONTH, iMonth-1);
          calendar.set(Calendar.DATE,  iDay);
        }
        catch(Throwable th) {
        }
      }
    }
    calendar.set(Calendar.HOUR_OF_DAY, iHH);
    calendar.set(Calendar.MINUTE,      iMM);
    calendar.set(Calendar.SECOND,      iSS);
    calendar.set(Calendar.MILLISECOND, iMS);
    return calendar;
  }
  
  public static
  String normalizeStringDate(String sValue)
  {
    if(sValue == null) return null;
    sValue = sValue.trim();
    int iLength = sValue.length();
    if(iLength == 0) return null;
    if(iLength >= 28 && Character.isLetter(sValue.charAt(0))) {
      // Ad es. Tue Jan 01 00:00:00 CET 2013
      String sYear = sValue.substring(sValue.length() - 4);
      try { Integer.parseInt(sYear); } catch(Throwable th) { return null; }
      String sMonth = getMonth(sValue.substring(4, 7));
      if(sMonth == null || sMonth.length() < 2) return null;
      String sDay = sValue.substring(8, 10);
      int iDay = 0;
      try { iDay = Integer.parseInt(sDay); } catch(Throwable th) { return null; }
      if(iDay < 1 || iDay > 31) return null;
      return sYear + sMonth + sDay;
    }
    int iFirstNumber = -1;
    for(int i = 0; i < iLength; i++) {
      if(Character.isDigit(sValue.charAt(i))) {
        iFirstNumber = i;
        break;
      }
    }
    if(iFirstNumber < 0) return null;
    if(iFirstNumber > 0) {
      sValue = sValue.substring(iFirstNumber);
    }
    int iFirstSep = sValue.indexOf('/');
    if(iFirstSep < 0) {
      iFirstSep = sValue.indexOf('-');
      if(iFirstSep <= 0 || iFirstSep > 13) {
        iFirstSep = sValue.indexOf('.');
        if(iFirstSep <= 0) {
          // YYYYMMDDHHMMSS[+/-OFFS]
          if(sValue.length() >= 14) {
            try {
              int iDate = Integer.parseInt(sValue.substring(0, 8));
              if(iDate >= 10000101 && iDate <= 99991231) {
                return sValue.substring(0, 8);
              }
            }
            catch(Throwable th) {
            }
          }
          if(sValue.length() > 8) {
            long lValue = 0;
            try { lValue = Long.parseLong(sValue); } catch(Throwable th) { return null; }
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(lValue);
            int iDate = cal.get(Calendar.YEAR)*10000 +(cal.get(Calendar.MONTH)+1)*100 + cal.get(Calendar.DAY_OF_MONTH);
            return String.valueOf(iDate);
          }
          if(sValue.length() != 8) return null;
          try { Integer.parseInt(sValue); } catch(Throwable th) { return null; }
          return sValue;
        }
      }
    }
    int iSecondSep = sValue.indexOf('/', iFirstSep + 1);
    if(iSecondSep < 0) {
      iSecondSep = sValue.indexOf('-', iFirstSep + 1);
      if(iSecondSep < 0 || iSecondSep > 13) {
        iSecondSep = sValue.indexOf('.', iFirstSep + 1);
        if(iSecondSep < 0) return null;
      }
    }
    String sDay   = null;
    String sMonth = null;
    String sYear  = null;
    if(iFirstSep >= 4) {
      // year - month - day
      sYear  = sValue.substring(0, iFirstSep).trim();
      sMonth = sValue.substring(iFirstSep + 1, iSecondSep).trim();
      sDay   = sValue.substring(iSecondSep + 1).trim();
      if(sDay.length() > 2) sDay = sDay.substring(0, 2);
    }
    else {
      // day - month - year
      sDay   = sValue.substring(0, iFirstSep).trim();
      sMonth = sValue.substring(iFirstSep + 1, iSecondSep).trim();
      sYear  = sValue.substring(iSecondSep + 1).trim();
      if(sYear.length() > 4) sYear = sYear.substring(0, 4);
    }
    // Check Day
    if(sDay.length() == 0) sDay = "01";
    else if(sDay.length() == 1) sDay = "0" + sDay;
    int iDay = 0;
    try { iDay = Integer.parseInt(sDay); } catch(Throwable th) { return null; }
    if(iDay < 1 || iDay > 31) return null;
    // Check Month
    if(sMonth.length() > 2 && Character.isLetter(sMonth.charAt(0))) {
      sMonth = getMonth(sMonth);
      if(sMonth == null) return null;
    }
    if(sMonth.length() == 0) sMonth = "01";
    else if(sMonth.length() == 1) sMonth = "0" + sMonth;
    int iMonth = 0;
    try { iMonth = Integer.parseInt(sMonth); } catch(Throwable th) { return null; }
    if(iMonth < 1 || iMonth > 12) return null;
    // Check Year
    int iYear = 0;
    try{ iYear = Integer.parseInt(sYear); } catch(Throwable th) { return null; }
    if(iYear < 1000 || iYear > 9999) return null;
    return sYear + sMonth + sDay;
  }
  
  public static
  String getMonth(String sMonth)
  {
    String sMonthLC = sMonth.toLowerCase();
    if(sMonthLC.startsWith("jan") || sMonthLC.startsWith("ge"))  return "01";
    if(sMonthLC.startsWith("f"))   return "02";
    if(sMonthLC.startsWith("mar")) return "03";
    if(sMonthLC.startsWith("ap"))  return "04";
    if(sMonthLC.startsWith("may") || sMonthLC.startsWith("mag")) return "05";
    if(sMonthLC.startsWith("jun") || sMonthLC.startsWith("gi"))  return "06";
    if(sMonthLC.startsWith("jul") || sMonthLC.startsWith("lu"))  return "07";
    if(sMonthLC.startsWith("au")  || sMonthLC.startsWith("ag"))  return "08";
    if(sMonthLC.startsWith("s")) return "09";
    if(sMonthLC.startsWith("o")) return "10";
    if(sMonthLC.startsWith("n")) return "11";
    if(sMonthLC.startsWith("d")) return "12";
    return null;
  }
  
  public static
  String normalizeStringTime(String sValue)
  {
    return normalizeStringTime(sValue, false);
  }
  
  public static
  String normalizeStringTime(String sValue, boolean boValueContainsDate)
  {
    if(sValue == null) return null;
    sValue = sValue.trim();
    if(sValue.length() == 0) return null;
    int iFirstSep = sValue.indexOf(':');
    if(iFirstSep < 0) {
      iFirstSep = sValue.indexOf(',');
      if(iFirstSep <= 0) {
        iFirstSep = sValue.indexOf('.');
        if(iFirstSep <= 0) {
          int iCut = -1;
          boolean boAtLeastANumber = false;
          boolean boAllNumbers     = true;
          for(int i = 0; i < sValue.length(); i++) {
            char c = sValue.charAt(i);
            if(!Character.isDigit(c)) {
              if(i > 11 &&(c == '+' || c == '-' || c == '.')) {
                iCut = i;
                break;
              }
              boAllNumbers = false;
            }
            else {
              boAtLeastANumber = true;
            }
          }
          if(!boAtLeastANumber) return null;
          if(!boAllNumbers) {
            if(boValueContainsDate) {
              return "000000";
            }
            return null;
          }
          if(iCut > 0) sValue = sValue.substring(0, iCut);
          // YYYYMMDDHHMMSS
          if(sValue.length() == 14) return sValue.substring(8, 14);
          // YYYYMMDDHHMM
          if(sValue.length() == 12) return sValue.substring(8, 12) + "00";
          // YYYYMMDD
          if(sValue.length() == 8)  return "000000"; // Is a date
          // HHMMSS?
          if(sValue.length() >  6)  return sValue.substring(0, 6);
          // HHMM
          if(sValue.length() == 1) sValue = "000"  + sValue;
          if(sValue.length() == 2) sValue = "00"   + sValue;
          if(sValue.length() == 3) sValue = "0"    + sValue;
          if(sValue.length() == 4) sValue = sValue +   "00";
          // HHMMSS
          if(sValue.length() == 5) sValue = "0"    + sValue;
          return sValue;
        }
      }
    }
    int iSecondSep = sValue.indexOf(':', iFirstSep + 1);
    if(iSecondSep < 0) {
      iSecondSep = sValue.indexOf(',', iFirstSep + 1);
      if(iSecondSep < 0) {
        iSecondSep = sValue.indexOf('.', iFirstSep + 1);
      }
    }
    int iMillis   = 0;
    int iThirdSep = -1;
    if(iSecondSep > 0) {
      iThirdSep = sValue.indexOf('.', iSecondSep + 1);
      if(iThirdSep > 0) {
        try { iMillis = Integer.parseInt(sValue.substring(iThirdSep+1,iThirdSep+4)); } catch(Throwable th) {}
        sValue = sValue.substring(0, iThirdSep);
      }
    }
    String sHH = "";
    String sMM = "";
    String sSS = "";
    if(iFirstSep > 0) {
      int iBegin = 0;
      if(iFirstSep > 5) iBegin = iFirstSep - 2;
      sHH = sValue.substring(iBegin, iFirstSep).trim();
      if(iSecondSep > 0) {
        sMM = sValue.substring(iFirstSep + 1, iSecondSep).trim();
        sSS = sValue.substring(iSecondSep + 1).trim();
        if(sSS.length() == 4) {
          int iSS = 0;
          try{ iSS = Integer.parseInt(sSS); } catch(Throwable ex) {}
          // Is a year
          if(iSS >= 1000) return "000000";
        }
        if(sSS.length() > 2) sSS = sSS.substring(0, 2);
      }
      else {
        sMM = sValue.substring(iFirstSep + 1).trim();
        sSS = "00";
        if(sMM.length() > 2) sMM = sMM.substring(0, 2);
      }
    }
    // Check Hour
    if(sHH.length() > 0) {
      char c0 = sHH.charAt(0);
      if(c0 != ' ' && !Character.isDigit(c0)) return null;
    }
    if(sHH.length() == 0) sHH = "00";
    else if(sHH.length() == 1) sHH = "0" + sHH;
    int iHH = 0;
    try { iHH = Integer.parseInt(sHH); } catch(Throwable th) { return "000000"; }
    if(iHH < 0 || iHH > 23) return "000000";
    // Check Minutes
    if(sMM.length() == 0) sMM = "00";
    else if(sMM.length() == 1) sMM = "0" + sMM;
    int iMM = 0;
    try { iMM = Integer.parseInt(sMM); } catch(Throwable th) { return "000000"; }
    if(iMM < 0 || iMM > 59) return "000000";
    // Check Seconds
    if(sSS.length() == 0) sSS = "00";
    else if(sSS.length() == 1) sSS = "0" + sSS;
    int iSS = 0;
    try { iSS = Integer.parseInt(sSS); } catch(Throwable th) { return "000000"; }
    if(iSS < 0 || iSS > 59) return "000000";
    String sMillis = null;
    if(iMillis < 10) {
      sMillis = "00" + iMillis;
    }
    else if(iMillis < 100) {
      sMillis = "0" + iMillis;
    }
    else {
      sMillis = String.valueOf(iMillis);
    }
    if(iMillis > 0) {
      return sHH + sMM + sSS + sMillis;
    }
    return sHH + sMM + sSS;
  }
  
  private static 
  boolean check(Map<String, Object> map, String... keys) 
  {
    if(map == null) return false;
    for(String key : keys) {
      Object value = map.get(key);
      if(value != null) return true;
    }
    return false;
  }
  
  public static
  List<String> stringToList(String text)
  {
    if(text == null || text.length() == 0) {
      return new ArrayList<String>(0);
    }
    if(text.startsWith("[") && text.endsWith("]")) {
      text = text.substring(1, text.length()-1);
    }
    List<String> listResult = new ArrayList<String>();
    int iIndexOf = 0;
    int iBegin   = 0;
    iIndexOf     = text.indexOf(',');
    while(iIndexOf >= 0) {
      listResult.add(text.substring(iBegin, iIndexOf).trim());
      iBegin = iIndexOf + 1;
      iIndexOf = text.indexOf(',', iBegin);
    }
    listResult.add(text.substring(iBegin).trim());
    return listResult;
  }
  
  @SuppressWarnings("unchecked")
  public static
  boolean isBlank(Object oValue)
  {
    if(oValue == null) return true;
    if(oValue instanceof Collection) {
      return ((Collection<?>) oValue).isEmpty();
    }
    if(oValue instanceof Map) {
      return ((Map<String, Object>) oValue).isEmpty();
    }
    String sValue = oValue.toString();
    if(sValue == null || sValue.trim().length() == 0 || sValue.equals("null")) {
      return true;
    }
    return false;
  }
  
  public static
  String getHashSHA1(byte[] content)
  {
    if(content == null) return null;
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA-1");
    }
    catch(NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
    return new String(Base64Coder.encode(md.digest(content)));
  }
  
  public static
  String getHashSHA1Hex(byte[] content)
  {
    if(content == null) return null;
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA-1");
    }
    catch(NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
    byte[] result = md.digest(content);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < result.length; i++) {
      String hex = Integer.toHexString(0xff & result[i]);
      if (hex.length() == 1) sb.append('0');
      sb.append(hex);
    }
    return sb.toString();
  }
}
