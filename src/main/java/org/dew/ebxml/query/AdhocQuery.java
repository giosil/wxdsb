package org.dew.ebxml.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dew.ebxml.RegistryObject;
import org.dew.ebxml.Slot;
import org.dew.ebxml.Utils;
import org.dew.xds.OID;
import org.dew.xds.XDS;

public 
class AdhocQuery extends RegistryObject 
{
	private static final long serialVersionUID = 8340126571308426527L;
	
	public AdhocQuery()
	{
		setId(XDS.SQ_FIND_DOCUMENTS);
	}
	
	public AdhocQuery(String patientId)
	{
		setId(XDS.SQ_FIND_DOCUMENTS);
		setPatientId(patientId);
	}
	
	@SuppressWarnings("rawtypes")
	public AdhocQuery(Map map)
	{
		super(map);
	}
	
	public 
	void clear()
	{
		if(slots != null) {
			slots.clear();
		}
	}
	
	public String getPatientId() {
		String value = getValue("$XDSDocumentEntryPatientId");
		return Utils.normalizePersonId(value);
	}
	
	public void setPatientId(String value) {
		if(value == null || value.length() == 0) return;
		boolean wrapped = value.startsWith("'") && value.endsWith("'");
		if(wrapped) value = value.substring(1, value.length()-1);
		String codingScheme = Utils.extractCodingScheme(value);
		if(codingScheme == null || codingScheme.length() < 2) {
			codingScheme = OID.PERSON_ID;
		}
		value = Utils.normalizePersonId(value);
		if(wrapped) {
			addSlot(new Slot("$XDSDocumentEntryPatientId", "'" + value + "^^^&" + codingScheme + "&ISO'"));
		}
		else {
			addSlot(new Slot("$XDSDocumentEntryPatientId", value + "^^^&" + codingScheme + "&ISO"));
		}
	}
	
	public void setPatientId(String value, String codingScheme) {
		if(value == null || value.length() == 0) return;
		boolean wrapped = value.startsWith("'") && value.endsWith("'");
		if(wrapped) value = value.substring(1, value.length()-1);
		value = Utils.normalizePersonId(value);
		if(codingScheme == null || codingScheme.length() < 2) {
			codingScheme = OID.PERSON_ID;
		}
		if(wrapped) {
			addSlot(new Slot("$XDSDocumentEntryPatientId", "'" + value + "^^^&" + codingScheme + "&ISO'"));
		}
		else {
			addSlot(new Slot("$XDSDocumentEntryPatientId", value + "^^^&" + codingScheme + "&ISO"));
		}
	}
	
	public Date getCreationTime() {
		return getDateValue("creationTime");
	}
	
	public void setCreationTime(Object value) {
		if(value == null) return;
		Date dateTime = Utils.toDate(value);
		addSlot(new Slot("creationTime", Utils.formatTime(dateTime)));
	}
	
	public Date getCreationTimeFrom() {
		return getDateValue("$XDSDocumentEntryCreationTimeFrom");
	}
	
	public void setCreationTimeFrom(Object value) {
		if(value == null) return;
		Date dateTime = Utils.toDate(value);
		addSlot(new Slot("$XDSDocumentEntryCreationTimeFrom", Utils.formatTime(dateTime)));
	}
	
	public Date getCreationTimeTo() {
		return getDateValue("$XDSDocumentEntryCreationTimeTo");
	}
	
	public void setCreationTimeTo(Object value) {
		if(value == null) return;
		Date dateTime = Utils.toDate(value);
		addSlot(new Slot("$XDSDocumentEntryCreationTimeFrom", Utils.formatTime(dateTime)));
	}
	
	public List<String> getStatusValues() {
		return getValues("$XDSDocumentEntryStatus");
	}
	
	public void setStatusValues(List<String> listValues) {
		if(listValues == null || listValues.size() == 0) return;
		String sValues = "";
		for(int i = 0; i < listValues.size(); i++) {
			String value = listValues.get(i);
			value = Utils.normalizeStatus(value);
			if(value == null || value.length() == 0) continue;
			sValues += ",'" + value + "'";
		}
		if(sValues.length() == 0) return;
		addSlot(new Slot("$XDSDocumentEntryStatus", "(" + sValues.substring(1) + ")"));
	}
	
	public void setStatusValues(String... arrayOfString) {
		if(arrayOfString == null || arrayOfString.length == 0) return;
		String sValues = "";
		for(int i = 0; i < arrayOfString.length; i++) {
			String value = arrayOfString[i];
			value = Utils.normalizeStatus(value);
			if(value == null || value.length() == 0) continue;
			sValues += ",'" + value + "'";
		}
		if(sValues.length() == 0) return;
		addSlot(new Slot("$XDSDocumentEntryStatus", "(" + sValues.substring(1) + ")"));
	}
	
	public String getUniqueId() {
		String result = getValue("$XDSDocumentEntryUniqueId");
		if(result == null || result.length() == 0) {
			result = getValue("uniqueId");
		}
		return result;
	}
	
	public void setUniqueId(String value) {
		if(value == null || value.length() == 0) return;
		addSlot(new Slot("$XDSDocumentEntryUniqueId", "('" + value + "')"));
	}
	
	public String getUUID() {
		return getValue("$XDSDocumentEntryEntryUUID");
	}
	
	public void setUUID(String value) {
		if(value == null || value.length() == 0) return;
		addSlot(new Slot("$XDSDocumentEntryEntryUUID", "('" + value + "')"));
	}
	
	public Date getServiceStartTimeFrom() {
		return getDateValue("$XDSDocumentEntryServiceStartTimeFrom");
	}
	
	public Date getServiceStartTimeTo() {
		return getDateValue("$XDSDocumentEntryServiceStartTimeTo");
	}
	
	public Date getServiceStopTimeFrom() {
		return getDateValue("$XDSDocumentEntryServiceStopTimeFrom");
	}
	
	public Date getServiceStopTimeTo() {
		return getDateValue("$XDSDocumentEntryServiceStopTimeTo");
	}
	
	public List<String> getClassCodes() {
		List<String> listValues = getValues("$XDSDocumentEntryClassCode");
		if(listValues == null || listValues.size() == 0) return listValues;
		for(int i = 0; i < listValues.size(); i++) {
			String value = listValues.get(i);
			listValues.set(i, Utils.extractCode(value));
		}
		return listValues;
	}
	
	public List<String> getConfidentialityCodes() {
		List<String> listValues = getValues("$XDSDocumentEntryConfidentialityCode");
		if(listValues == null || listValues.size() == 0) return listValues;
		for(int i = 0; i < listValues.size(); i++) {
			String value = listValues.get(i);
			listValues.set(i, Utils.extractCode(value));
		}
		return listValues;
	}
	
	public List<String> getFormatCodes() {
		List<String> listValues = getValues("$XDSDocumentEntryFormatCode");
		if(listValues == null || listValues.size() == 0) return listValues;
		for(int i = 0; i < listValues.size(); i++) {
			String value = listValues.get(i);
			listValues.set(i, Utils.extractCode(value));
		}
		return listValues;
	}
	
	public List<String> getTypeCodes() {
		List<String> listValues = getValues("$XDSDocumentEntryTypeCode");
		if(listValues == null || listValues.size() == 0) return listValues;
		for(int i = 0; i < listValues.size(); i++) {
			String value = listValues.get(i);
			listValues.set(i, Utils.extractCode(value));
		}
		return listValues;
	}
	
	public List<String> getEventCodes() {
		List<String> listValues = getValues("$XDSDocumentEntryEventCodeList");
		if(listValues == null || listValues.size() == 0) return listValues;
		for(int i = 0; i < listValues.size(); i++) {
			String value = listValues.get(i);
			listValues.set(i, Utils.extractCode(value));
		}
		return listValues;
	}
	
	public void setClassCodes(List<String> listValues) {
		if(listValues == null || listValues.size() == 0) return;
		String sValues = "";
		for(int i = 0; i < listValues.size(); i++) {
			String value = listValues.get(i);
			if(value == null || value.length() == 0) continue;
			if(value.charAt(0) == '\'' && value.charAt(value.length()-1) == '\'') {
				value = value.substring(1, value.length()-1).trim();
			}
			if(value.charAt(0) == '"' && value.charAt(value.length()-1) == '"') {
				value = value.substring(1, value.length()-1).trim();
			}
			String sCode = Utils.extractCode(value);
			if(sCode == null || sCode.length() == 0) continue;
			String sCodingScheme = Utils.extractCodingScheme(value);
			if(sCodingScheme != null && sCodingScheme.length() > 0) {
				sValues += ",'" + sCode + "^^" + sCodingScheme + "'";
			}
			else {
				sValues += ",'" + sCode + "^^" + OID.LOINC + "'";
			}
		}
		if(sValues.length() == 0) return;
		addSlot(new Slot("$XDSDocumentEntryClassCode", "(" + sValues.substring(1) + ")"));
	}
	
	public void setClassCodes(String... arrayOfString) {
		if(arrayOfString == null || arrayOfString.length == 0) return;
		setClassCodes(Arrays.asList(arrayOfString));
	}
	
	public void setFormatCodes(List<String> listValues) {
		if(listValues == null || listValues.size() == 0) return;
		String sValues = "";
		for(int i = 0; i < listValues.size(); i++) {
			String value = listValues.get(i);
			if(value == null || value.length() == 0) continue;
			if(value.charAt(0) == '\'' && value.charAt(value.length()-1) == '\'') {
				value = value.substring(1, value.length()-1).trim();
			}
			if(value.charAt(0) == '"' && value.charAt(value.length()-1) == '"') {
				value = value.substring(1, value.length()-1).trim();
			}
			String sCode = Utils.extractCode(value);
			if(sCode == null || sCode.length() == 0) continue;
			String sCodingScheme = Utils.extractCodingScheme(value);
			if(sCodingScheme != null && sCodingScheme.length() > 0) {
				sValues += ",'" + sCode + "^^" + sCodingScheme + "'";
			}
			else {
				sValues += ",'" + sCode + "^^" + OID.FORMAT_CODES + "'";
			}
		}
		if(sValues.length() == 0) return;
		addSlot(new Slot("$XDSDocumentEntryFormatCode", "(" + sValues.substring(1) + ")"));
	}
	
	public void setFormatCodes(String... arrayOfString) {
		if(arrayOfString == null || arrayOfString.length == 0) return;
		setFormatCodes(Arrays.asList(arrayOfString));
	}
	
	public void setTypeCodes(List<String> listValues) {
		if(listValues == null || listValues.size() == 0) return;
		String sValues = "";
		for(int i = 0; i < listValues.size(); i++) {
			String value = listValues.get(i);
			if(value == null || value.length() == 0) continue;
			if(value.charAt(0) == '\'' && value.charAt(value.length()-1) == '\'') {
				value = value.substring(1, value.length()-1).trim();
			}
			if(value.charAt(0) == '"' && value.charAt(value.length()-1) == '"') {
				value = value.substring(1, value.length()-1).trim();
			}
			String sCode = Utils.extractCode(value);
			if(sCode == null || sCode.length() == 0) continue;
			String sCodingScheme = Utils.extractCodingScheme(value);
			if(sCodingScheme != null && sCodingScheme.length() > 0) {
				sValues += ",'" + sCode + "^^" + sCodingScheme + "'";
			}
			else {
				sValues += ",'" + sCode + "^^" + OID.LOINC + "'";
			}
		}
		if(sValues.length() == 0) return;
		addSlot(new Slot("$XDSDocumentEntryTypeCode", "(" + sValues.substring(1) + ")"));
	}
	
	public void setTypeCodes(String... arrayOfString) {
		if(arrayOfString == null || arrayOfString.length == 0) return;
		setTypeCodes(Arrays.asList(arrayOfString));
	}
	
	public String getClassCodeScheme() {
		return getValue("$XDSDocumentEntryClassCodeScheme");
	}
	
	public String getConfidentialityCodeScheme() {
		return getValue("$XDSDocumentEntryConfidentialityCodeScheme");
	}
	
	public List<String> getHealthcareFacilityTypes() {
		return getValues("$XDSDocumentEntryHealthcareFacilityTypeCode");
	}
	
	public String getHealthcareFacilityTypeCodeScheme() {
		return getValue("$XDSDocumentEntryHealthcareFacilityTypeCodeScheme");
	}
	
	public List<String> getPracticeSettingCodes() {
		return getValues("$XDSDocumentEntryPracticeSettingCode");
	}
	
	public String getPracticeSettingCodeScheme() {
		return getValue("$XDSDocumentEntryPracticeSettingCodeScheme");
	}
	
	public List<String> getEventCodeList() {
		return getValues("$XDSDocumentEntryEventCodeList");
	}
	
	public String getEventCodeListScheme() {
		return getValue("$XDSDocumentEntryEventCodeListScheme");
	}
	
	public Date getDateValue(String slotName) {
		String result = getSlotFirstValue(slotName);
		if(result == null) return null;
		if(result.charAt(0) == '(' && result.charAt(result.length()-1) == ')') {
			result = result.substring(1, result.length()-1).trim();
		}
		if(result.charAt(0) == '\'' && result.charAt(result.length()-1) == '\'') {
			result = result.substring(1, result.length()-1).trim();
		}
		if(result.charAt(0) == '"' && result.charAt(result.length()-1) == '"') {
			result = result.substring(1, result.length()-1).trim();
		}
		int iSep = result.indexOf("','");
		if(iSep < 0) {
			iSep = result.indexOf("\",\"");
		}
		if(iSep > 0) {
			result = result.substring(0, iSep).trim();
		}
		Calendar calendar = Utils.stringToCalendar(result.trim());
		if(calendar == null) return null;
		return calendar.getTime();
	}
	
	public String getValue(String slotName) {
		String result = getSlotFirstValue(slotName);
		if(result == null) return null;
		if(result.charAt(0) == '(' && result.charAt(result.length()-1) == ')') {
			result = result.substring(1, result.length()-1).trim();
		}
		if(result.charAt(0) == '\'' && result.charAt(result.length()-1) == '\'') {
			result = result.substring(1, result.length()-1).trim();
		}
		if(result.charAt(0) == '"' && result.charAt(result.length()-1) == '"') {
			result = result.substring(1, result.length()-1).trim();
		}
		int iSep = result.indexOf("','");
		if(iSep < 0) {
			iSep = result.indexOf("\",\"");
		}
		if(iSep > 0) {
			result = result.substring(0, iSep).trim();
		}
		return result.trim();
	}
	
	public String getValueContains(String slotName) {
		String result = getSlotFirstValueContains(slotName);
		if(result == null) return null;
		if(result.charAt(0) == '(' && result.charAt(result.length()-1) == ')') {
			result = result.substring(1, result.length()-1).trim();
		}
		if(result.charAt(0) == '\'' && result.charAt(result.length()-1) == '\'') {
			result = result.substring(1, result.length()-1).trim();
		}
		if(result.charAt(0) == '"' && result.charAt(result.length()-1) == '"') {
			result = result.substring(1, result.length()-1).trim();
		}
		int iSep = result.indexOf("','");
		if(iSep < 0) {
			iSep = result.indexOf("\",\"");
		}
		if(iSep > 0) {
			result = result.substring(0, iSep).trim();
		}
		return result.trim();
	}
	
	public void setValue(String slotName, String value) {
		if(slotName == null || slotName.length() == 0) return;
		if(value == null || value.length() == 0) return;
		if(value.charAt(0) == '(' && value.charAt(value.length()-1) == ')') {
			value = value.substring(1, value.length()-1).trim();
		}
		if(value.charAt(0) == '\'' && value.charAt(value.length()-1) == '\'') {
			value = value.substring(1, value.length()-1).trim();
		}
		if(value.charAt(0) == '"' && value.charAt(value.length()-1) == '"') {
			value = value.substring(1, value.length()-1).trim();
		}
		int iSep = value.indexOf("','");
		if(iSep < 0) {
			iSep = value.indexOf("\",\"");
		}
		if(iSep > 0) {
			value = value.substring(0, iSep).trim();
		}
		addSlot(new Slot(slotName, "'" + value.trim() + "'"));
	}
	
	public List<String> getValues(String slotName) {
		List<String> result = new ArrayList<String>();
		String sValues = getSlotFirstValue(slotName);
		if(sValues == null || sValues.length() == 0) {
			return result;
		}
		if(sValues.charAt(0) == '(' && sValues.charAt(sValues.length()-1) == ')') {
			sValues = sValues.substring(1, sValues.length()-1).trim();
		}
		int iIndexOf = 0;
		int iBegin   = 0;
		iIndexOf     = sValues.indexOf(',');
		while(iIndexOf >= 0) {
			String sValue = sValues.substring(iBegin, iIndexOf);
			if(sValue.charAt(0) == '\'' && sValue.charAt(sValue.length()-1) == '\'') {
				sValue = sValue.substring(1, sValue.length()-1).trim();
			}
			if(sValue.charAt(0) == '"' && sValue.charAt(sValue.length()-1) == '"') {
				sValue = sValue.substring(1, sValue.length()-1).trim();
			}
			result.add(sValue.trim());
			iBegin = iIndexOf + 1;
			iIndexOf = sValues.indexOf(',', iBegin);
		}
		String sValue = sValues.substring(iBegin);
		if(sValue.charAt(0) == '\'' && sValue.charAt(sValue.length()-1) == '\'') {
			sValue = sValue.substring(1, sValue.length()-1).trim();
		}
		if(sValue.charAt(0) == '"' && sValue.charAt(sValue.length()-1) == '"') {
			sValue = sValue.substring(1, sValue.length()-1).trim();
		}
		result.add(sValue.trim());
		return result;
	}
	
	public void setValues(String slotName, List<String> values) {
		if(slotName == null || slotName.length() == 0) return;
		if(values   == null || values.size()     == 0) return;
		String sValues = "";
		for(int i = 0; i < values.size(); i++) {
			String value = values.get(i);
			if(value == null || value.length() == 0) continue;
			if(value.charAt(0) == '\'' && value.charAt(value.length()-1) == '\'') {
				value = value.substring(1, value.length()-1).trim();
			}
			if(value.charAt(0) == '"' && value.charAt(value.length()-1) == '"') {
				value = value.substring(1, value.length()-1).trim();
			}
			sValues += ",'" + value.trim() + "'";
		}
		if(sValues.length() == 0) return;
		addSlot(new Slot(slotName, "(" + sValues.substring(1) + "'"));
	}
	
	@Override
	public String getTagName() {
		return "AdhocQuery";
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof AdhocQuery) {
			String sId = ((AdhocQuery) object).getId();
			if(sId == null && id == null) return true;
			return sId != null && sId.equals(id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if(id == null) return 0;
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		return "AdhocQuery(" + id + "," + getSlotsMap() + ")";
	}
}
