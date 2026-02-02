package org.dew.xds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dew.ebxml.Utils;

public 
class XDSPerson implements Serializable
{
  private static final long serialVersionUID = 3090406057812164221L;
  
  protected String id;
  protected String codingScheme;
  protected String code;
  protected String givenName;
  protected String familyName;
  protected String prefix;
  protected String gender;
  protected Date   dateOfBirth;
  protected String email;
  protected String role;
  protected String qualification;
  protected String institution;
  protected String institutionName;
  protected String address;
  protected String city;
  protected String postalCode;
  protected String country;
  
  public XDSPerson()
  {
  }
  
  public XDSPerson(String id)
  {
    this.id           = Utils.normalizePersonId(id);
    this.codingScheme = Utils.extractCodingScheme(id);
  }
  
  public XDSPerson(String id, String familyName, String givenName)
  {
    this.id           = Utils.normalizePersonId(id);
    this.codingScheme = Utils.extractCodingScheme(id);
    this.familyName   = familyName;
    this.givenName    = givenName;
  }
  
  public XDSPerson(String id, String familyName, String givenName, String prefix)
  {
    this.id           = Utils.normalizePersonId(id);
    this.codingScheme = Utils.extractCodingScheme(id);
    this.familyName   = familyName;
    this.givenName    = givenName;
    this.prefix       = prefix;
  }

  public XDSPerson(String id, String familyName, String givenName, String prefix, String role)
  {
    this.id           = Utils.normalizePersonId(id);
    this.codingScheme = Utils.extractCodingScheme(id);
    this.familyName   = familyName;
    this.givenName    = givenName;
    this.prefix       = prefix;
    this.role         = role;
  }
  
  /**
   * PID-3|RSSMRA75C03F839J^^^&2.16.840.1.113883.2.9.4.3.2&ISO   (Opt.)
   * PID-5|Rossi^Mario^^^
   * PID-7|19750303
   * PID-8|M
   * PID-11|Via Alessandro Manzoni^^Napoli^^80100^Italia
   */
  public XDSPerson(String[] sourcePatientInfo)
  {
    if(sourcePatientInfo == null || sourcePatientInfo.length == 0) {
      return;
    }
    for(int i = 0; i < sourcePatientInfo.length; i++) {
      setPID(sourcePatientInfo[i]);
    }
  }
  
  /**
   * PID-3|RSSMRA75C03F839J^^^&2.16.840.1.113883.2.9.4.3.2&ISO   (Opt.)
   * PID-5|Rossi^Mario^^^
   * PID-7|19750303
   * PID-8|M
   * PID-11|Via Alessandro Manzoni^^Napoli^^80100^Italia
   */
  public XDSPerson(List<String> sourcePatientInfo)
  {
    if(sourcePatientInfo == null || sourcePatientInfo.size() == 0) {
      return;
    }
    for(int i = 0; i < sourcePatientInfo.size(); i++) {
      setPID(sourcePatientInfo.get(i));
    }
  }
  
  /**
   * PID-3|RSSMRA75C03F839J^^^&2.16.840.1.113883.2.9.4.3.2&ISO   (Opt.)
   * PID-5|Rossi^Mario^^^
   * PID-7|19750303
   * PID-8|M
   * PID-11|Via Alessandro Manzoni^^Napoli^^80100^Italia
   */
  public XDSPerson(String id, String[] sourcePatientInfo)
  {
    this(sourcePatientInfo);
    if(id != null && id.length() > 0) {
      this.id = id;
    }
  }
  
  /**
   * PID-3|RSSMRA75C03F839J^^^&2.16.840.1.113883.2.9.4.3.2&ISO   (Opt.)
   * PID-5|Rossi^Mario^^^
   * PID-7|19750303
   * PID-8|M
   * PID-11|Via Alessandro Manzoni^^Napoli^^80100^Italia
   */
  public XDSPerson(String id, List<String> sourcePatientInfo)
  {
    this(sourcePatientInfo);
    if(id != null && id.length() > 0) {
      this.id = id;
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCodingScheme() {
    return codingScheme;
  }

  public void setCodingScheme(String codingScheme) {
    this.codingScheme = codingScheme;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getQualification() {
    return qualification;
  }

  public void setQualification(String qualification) {
    this.qualification = qualification;
  }

  public String getInstitution() {
    return institution;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public String getInstitutionName() {
    return institutionName;
  }

  public void setInstitutionName(String institutionName) {
    this.institutionName = institutionName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * PID-3|RSSMRA75C03F839J^^^&2.16.840.1.113883.2.9.4.3.2&ISO   (Opt.)
   * PID-5|Rossi^Mario^^^
   * PID-7|19750303
   * PID-8|M
   * PID-11|Via Alessandro Manzoni^^Napoli^^80100^Italia
   * 
   * @return List of String
   */
  public List<String> getITI_TF3_Info(boolean includePID3) {
    List<String> result = new ArrayList<String>();
    if(includePID3) {
      String sPID3 = "";
      String sId   = getITI_TF3_Id();
      if(sId != null && sId.length() > 1) {
        sPID3 = "PID-3|" + sId;
      }
      if(sPID3 != null && sPID3.length() > 0) {
        result.add(sPID3);
      }
    }
    String sPID5 = "";
    if(familyName != null && familyName.length() > 0) {
      sPID5 = "PID-5|" + familyName + "^";
      if(givenName != null && givenName.length() > 0) {
        sPID5 += givenName + "^^^";
      }
      else {
        sPID5 += "^^^";
      }
    }
    if(sPID5 != null && sPID5.length() > 1) {
      result.add(sPID5);
    }
    String sPID7 = "";
    if(dateOfBirth != null) {
      String sDateOfBirth = Utils.formatDate(dateOfBirth);
      if(sDateOfBirth != null && sDateOfBirth.length() > 1) {
        sPID7 = "PID-7|" + sDateOfBirth;
      }
    }
    if(sPID7 != null && sPID7.length() > 1) {
      result.add(sPID7);
    }
    String sPID8 = "";
    String sGender = gender != null ? gender.trim().toUpperCase() : null;
    if(sGender != null && sGender.length() > 0 && !sGender.equals("-")) {
      if(sGender.length() > 1) {
        sGender = gender.substring(0,1);
      }
      if(sGender.equals("F") || sGender.equals("2")) {
        sGender = "F";
      }
      else {
        sGender = "M";
      }
      sPID8 = "PID-8|" + sGender; 
    }
    if(sPID8 != null && sPID8.length() > 1) {
      result.add(sPID8);
    }
    String sPID11 = "";
    if(address != null && address.length() > 1) {
      sPID11 = "PID-11|" + address + "^^";
      if(city != null && city.length() > 1) {
        sPID11 += city + "^^";
      }
      else {
        sPID11 += "^^";
      }
      if(postalCode != null && postalCode.length() > 1) {
        sPID11 += postalCode + "^";
      }
      else {
        sPID11 += "^";
      }
      if(country != null && country.length() > 0) {
        sPID11 += country;
      }
    }
    if(sPID11 != null && sPID11.length() > 1) {
      result.add(sPID11);
    }
    return result;
  }

  /**
   * Field HL7 V2 Message Segment
   * @return String
   */
  public String getITI_TF3_Id() {
    if(id == null || id.length() == 0) return "";
    StringBuffer sbResult = new StringBuffer(55);
    String sId = Utils.normalizePersonId(id);
    if(sId != null) {
      sbResult.append(sId + "^"); 
    }
    else {
      sbResult.append("^");
    }
    sbResult.append("^");
    sbResult.append("^");
    if(codingScheme != null && codingScheme.length() > 0) {
      sbResult.append("&" + codingScheme + "&ISO");
    }
    else if(sId != null && sId.length() < 13) {
      sbResult.append("&" + OID.VAT_NUMBER + "&ISO");
    }
    else {
      sbResult.append("&" + OID.PERSON_ID + "&ISO");
    }
    return sbResult.toString();
  }
  
  /**
   * HL7 V2 Extended Person Name
   * 
   * VRDMRC67T20I257E^VERDI^MARCO^^^DOTT.^^^&amp;2.16.840.1.113883.2.9.4.3.2&amp;ISO
   * 
   * @return String
   */
  public String getITI_TF3_Person() {
    if(id == null || id.length() == 0) return "";
    StringBuffer sbResult = new StringBuffer(60);
    String sId = Utils.normalizePersonId(id);
    if(sId != null) {
      sbResult.append(sId + "^"); // XCN.1 (Identifier)
    }
    else {
      sbResult.append("^");
    }
    if(familyName != null) {
      sbResult.append(familyName + "^"); // XCN.2 (Last Name)
    }
    else {
      sbResult.append("^");
    }
    if(givenName != null) {
      sbResult.append(givenName + "^"); // XCN.3 (First Name)
    }
    else {
      sbResult.append("^");
    }
    sbResult.append("^"); // XCN.4 (Second and Further Given Names)
    sbResult.append("^"); // XCN.5 (Suffix)
    if(prefix != null) {
      sbResult.append(prefix + "^"); // XCN.6 (Prefix)
    }
    else {
      sbResult.append("^");
    }
    sbResult.append("^"); // XCN.7 (Assigning Authority)
    sbResult.append("^"); // XCN.8 (Empty component)
    if(codingScheme != null && codingScheme.length() > 0) {
      sbResult.append("&" + codingScheme + "&ISO"); // XCN.9 (codingScheme)
    }
    else if(sId != null && sId.length() < 13) {
      sbResult.append("&" + OID.VAT_NUMBER + "&ISO");
    }
    else {
      sbResult.append("&" + OID.PERSON_ID + "&ISO");
    }
    return sbResult.toString();
  }
  
  /**
   * ULSS N - TEST^^^^^&2.16.840.1.113883.2.9.4.1.3&ISO^^^^999109
   */
  public String getITI_TF3_Institution() {
    if(institution == null || institution.length() == 0) {
      return "";
    }
    int iSep = institution.indexOf('^');
    if(iSep >= 0) {
      return institution;
    }
    if(institution.length() == 6) {
      if(institutionName != null && institutionName.length() > 0) {
        return institutionName + "^^^^^&" + OID.STS11 + "&ISO^^^^" + institution;
      }
      return "^^^^^&" + OID.STS11 + "&ISO^^^^" + institution;
    }
    else if(institution.length() == 7 && institution.endsWith("R")) {
      if(institutionName != null && institutionName.length() > 0) {
        return institutionName + "^^^^^&" + OID.RIA11 + "&ISO^^^^" + institution;
      }
      return "^^^^^&" + OID.RIA11 + "&ISO^^^^" + institution;
    }
    else if(institution.length() == 11) {
      if(institutionName != null && institutionName.length() > 0) {
        return institutionName + "^^^^^&" + OID.STS11 + "&ISO^^^^" + institution;
      }
      return "^^^^^&" + OID.STS11 + "&ISO^^^^" + institution;
    }
    if(institutionName != null && institutionName.length() > 0) {
      return institutionName + "^^^^^&" + OID.HSP11 + "&ISO^^^^" + institution;
    }
    return "^^^^^&" + OID.HSP11 + "&ISO^^^^" + institution;
  }
  
  /**
   * VRDMRC67T20I257E^VERDI^MARCO^^^DOTT.^^^&amp;2.16.840.1.113883.2.9.4.3.2&amp;ISO
   * @param itiTF3Person ITI TF3 Format Person
   */
  public void setITI_TF3_Person(String itiTF3Person) {
    if(itiTF3Person == null || itiTF3Person.length() == 0) {
      return;
    }
    List<String> fields = getFields(itiTF3Person);
    if(fields != null && fields.size() > 0) {
      this.id = Utils.normalizePersonId(fields.get(0));
      if(fields.size() > 1) {
        this.familyName = fields.get(1);
      }
      if(fields.size() > 2) {
        this.givenName = fields.get(2);
      }
      if(fields.size() > 5) {
        this.prefix = fields.get(5);
      }
    }
  }
  
  public void setPID(String sPID) {
    if(sPID == null || sPID.length() == 0) {
      return;
    }
    if(sPID.startsWith("PID-3|")) {
      // PID-3|RSSMRA75C03F839J^^^&2.16.840.1.113883.2.9.4.3.2&ISO
      List<String> fields = getFields(sPID);
      if(fields != null && fields.size() > 0) {
        String value = fields.get(0);
        this.id           = Utils.extractCode(value);
        this.codingScheme = Utils.extractCodingScheme(value);
      }
    }
    else if(sPID.startsWith("PID-5|")) {
      // PID-5|Rossi^Mario^^^
      List<String> fields = getFields(sPID);
      if(fields != null && fields.size() > 0) {
        this.familyName = fields.get(0);
        if(fields.size() > 1) {
          this.givenName = fields.get(1);
        }
      }
    }
    else if(sPID.startsWith("PID-7|")) {
      // PID-7|19750303
      List<String> fields = getFields(sPID);
      if(fields != null && fields.size() > 0) {
        this.dateOfBirth = Utils.toDate(fields.get(0));
      }
    }
    else if(sPID.startsWith("PID-8|")) {
      // PID-8|M
      List<String> fields = getFields(sPID);
      if(fields != null && fields.size() > 0) {
        this.gender = fields.get(0);
      }
    }
    else if(sPID.startsWith("PID-11|")) {
      // PID-11|Via Alessandro Manzoni^^Napoli^^80100^Italia
      List<String> fields = getFields(sPID);
      if(fields != null && fields.size() > 0) {
        this.address = fields.get(0);
        if(fields.size() > 2) {
          this.city = fields.get(2);
        }
        if(fields.size() > 4) {
          this.postalCode = fields.get(4);
        }
        if(fields.size() > 5) {
          this.country = fields.get(5);
        }
      }
    }
  }
  
  protected List<String> getFields(String sPID) {
    // PID-3|RSSMRA75C03F839J^^^&2.16.840.1.113883.2.9.4.3.2&ISO   (Opt.)
    // PID-5|Rossi^Mario^^^
    // PID-7|19750303
    // PID-8|M
    // PID-11|Via Alessandro Manzoni^^Napoli^^80100^Italia
    if(sPID == null || sPID.length() == 0) {
      return new ArrayList<String>(0);
    }
    List<String> result = new ArrayList<String>();
    int iSep = sPID.indexOf('|');
    if(iSep >= 0) {
      sPID = sPID.substring(iSep+1);
    }
    int iIndexOf = 0;
    int iBegin   = 0;
    iIndexOf     = sPID.indexOf('^');
    while(iIndexOf >= 0) {
      result.add(sPID.substring(iBegin, iIndexOf));
      iBegin = iIndexOf + 1;
      iIndexOf = sPID.indexOf('^', iBegin);
    }
    result.add(sPID.substring(iBegin));
    return result;
  }

  @Override
  public boolean equals(Object object) {
    if(object instanceof XDSPerson) {
      String sId = ((XDSPerson) object).getId();
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
    return "XDSPerson(" + id + ")";
  }
}
