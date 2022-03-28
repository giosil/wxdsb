package org.dew.auth;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dew.ebxml.Utils;

public 
class SAMLIdentAssertion extends SAMLAssertion 
{
  private static final long serialVersionUID = 4962697741632489961L;
  
  protected String xmlnsIdent = "http://www.fascicolosanitario.gov.it/identificazione";
  protected List<IdentityItem> cfList;
  protected List<IdentityItem> rdaList;
  
  public SAMLIdentAssertion()
  {
    super();
  }
  
  public SAMLIdentAssertion(String subjectId)
  {
    super(subjectId);
  }
  
  public SAMLIdentAssertion(Map<String, Object> map)
  {
    super(map);
  }
  
  public SAMLIdentAssertion(SAMLAssertion samlAssertion)
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
    if(samlAssertion instanceof SAMLIdentAssertion) {
      SAMLIdentAssertion identityAssertion = (SAMLIdentAssertion) samlAssertion;
      this.cfList   = identityAssertion.getCfList();
      this.rdaList  = identityAssertion.getRdaList();
    }
    else {
      checkAttributes();
    }
  }
  
  public String getXmlnsIdent() {
    return xmlnsIdent;
  }

  public void setXmlnsIdent(String xmlnsIdent) {
    this.xmlnsIdent = xmlnsIdent;
  }

  public List<IdentityItem> getCfList() {
    return cfList;
  }

  public void setCfList(List<IdentityItem> cfList) {
    this.cfList = cfList;
  }

  public List<IdentityItem> getRdaList() {
    return rdaList;
  }

  public void setRdaList(List<IdentityItem> rdaList) {
    this.rdaList = rdaList;
  }

  public
  void addCF(String value, boolean current, Date validity)
  {
    if(value == null || value.length() == 0) return;
    if(cfList == null) cfList = new ArrayList<SAMLIdentAssertion.IdentityItem>();
    cfList.add(new IdentityItem(value, current, validity, null));
  }

  public
  void addRDA(String value, boolean current, Date beginDate, Date endDate)
  {
    if(value == null || value.length() == 0) return;
    if(rdaList == null) rdaList = new ArrayList<SAMLIdentAssertion.IdentityItem>();
    rdaList.add(new IdentityItem(value, current, beginDate, endDate));
  }
  
  public int checkAttributes() {
    if(attributes == null) return 0;
    if(cfList != null && cfList.size() > 0) {
      return attributes.size();
    }
    cfList = new ArrayList<IdentityItem>();
    for(int i = 0; i < 100; i++) {
      String sValue   = attributes.get("CF_List_" + i);
      if(sValue == null || sValue.length() == 0) break;
      String sCurrent  = attributes.get("CF_List_" + i + "_CurrentStatus");
      String sValidity = attributes.get("CF_List_" + i + "_Validity");
      cfList.add(new IdentityItem(sValue, sCurrent, null, sValidity));
    }
    rdaList = new ArrayList<IdentityItem>();
    for(int i = 0; i < 100; i++) {
      String sValue   = attributes.get("RDA_List_" + i);
      if(sValue == null || sValue.length() == 0) break;
      String sCurrent   = attributes.get("RDA_List_" + i + "_Current");
      String sBeginDate = attributes.get("RDA_List_" + i + "_BeginDate");
      String sEndDate   = attributes.get("RDA_List_" + i + "_EndDate");
      rdaList.add(new IdentityItem(sValue, sCurrent, sBeginDate, sEndDate));
    }
    return attributes.size();
  }
  
  public 
  Map<String, Object> toMap() 
  {
    Map<String, Object> mapResult = super.toMap();
    if(cfList != null) {
      List<Map<String, Object>> listOfMap = new ArrayList<Map<String, Object>>(cfList.size());
      for(int i = 0; i < cfList.size(); i++) {
        IdentityItem identityItem = cfList.get(i);
        if(identityItem != null) {
          listOfMap.add(identityItem.toMap());
        }
      }
      mapResult.put("cfList", listOfMap);
    }
    if(rdaList != null) {
      List<Map<String, Object>> listOfMap = new ArrayList<Map<String, Object>>(rdaList.size());
      for(int i = 0; i < rdaList.size(); i++) {
        IdentityItem identityItem = rdaList.get(i);
        if(identityItem != null) {
          listOfMap.add(identityItem.toMap());
        }
      }
      mapResult.put("rdaList", listOfMap);
    }
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
    else if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    String sDecNsId = "";
    if(xmlnsIdent != null && xmlnsIdent.length() > 0) {
      sDecNsId = " xmlns:ident=\"" + xmlnsIdent + "\"";
    }
    if(cfList != null && cfList.size() > 0) {
      sb.append("<" + namespace + "Attribute Name=\"CF_List\">");
      for(int i = 0; i < cfList.size(); i++) {
        IdentityItem item = cfList.get(i);
        if(item == null) continue;
        String sValue = item.getValue();
        if(sValue == null || sValue.length() == 0) continue;
        Date dValidity = item.getBeginDate();
        sb.append("<" + namespace + "AttributeValue xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-istance\" xsi:type=\"ident:CFtype\">");
        sb.append("<ident:CF" + sDecNsId + " CurrentStatus=\"" + item.isCurrent() + "\"");
        if(dValidity != null) {
          sb.append(" Validity=\"" + Utils.formatISO8601_Z(dValidity) + "\"");
        }
        sb.append(">" + sValue + "</ident:CF>");
        sb.append("</" + namespace + "AttributeValue>");
      }
      sb.append("</" + namespace + "Attribute>");
    }
    if(rdaList != null && rdaList.size() > 0) {
      sb.append("<" + namespace + "Attribute Name=\"RDA_List\">");
      for(int i = 0; i < rdaList.size(); i++) {
        IdentityItem item = rdaList.get(i);
        if(item == null) continue;
        String sValue = item.getValue();
        if(sValue == null || sValue.length() == 0) continue;
        Date dBeginDate = item.getBeginDate();
        Date dEndDate   = item.getEndDate();
        sb.append("<" + namespace + "AttributeValue xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-istance\" xsi:type=\"ident:RDAtype\">");
        sb.append("<ident:RDA" + sDecNsId);
        if(dBeginDate != null) {
          sb.append(" BeginDate=\"" + Utils.formatISO8601_Z(dBeginDate) + "\"");
        }
        sb.append(" Current=\"" + item.isCurrent() + "\"");
        if(dEndDate != null) {
          sb.append(" EndDate=\"" + Utils.formatISO8601_Z(dEndDate) + "\"");
        }
        sb.append(">" + sValue + "</ident:RDA>");
        
        sb.append("</" + namespace + "AttributeValue>");
      }
      sb.append("</" + namespace + "Attribute>");
    }
    return sb.toString();
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof SAMLIdentAssertion) {
      String sSubjectId = ((SAMLIdentAssertion) object).getSubjectId();
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
    return "SAMLIdentAssertion(" + subjectId + ")";
  }
  
  class IdentityItem implements Serializable
  {
    private static final long serialVersionUID = 6723495336157668240L;
    
    protected String  value;
    protected boolean current;
    protected Date    beginDate;
    protected Date    endDate;
    
    public IdentityItem()
    {
    }
    
    public IdentityItem(String value, String current, String beginDate, String endDate)
    {
      this.value     = value;
      this.current   = Utils.toBoolean(current, false);
      this.beginDate = Utils.toDate(beginDate);
      this.endDate   = Utils.toDate(endDate);
    }
    
    public IdentityItem(String value, boolean current, Date beginDate, Date endDate)
    {
      this.value     = value;
      this.current   = current;
      this.beginDate = beginDate;
      this.endDate   = endDate;
    }
    
    public IdentityItem(Map<String, Object> map)
    {
      if(map == null) return;
      this.value     = (String) map.get("value");
      this.current   = Utils.toBoolean(map.get("current"), false);
      this.beginDate = Utils.toDate(map.get("beginDate"));
      this.endDate   = Utils.toDate(map.get("endDate"));
    }
    
    public String getValue() {
      return value;
    }
    
    public void setValue(String value) {
      this.value = value;
    }
    
    public boolean isCurrent() {
      return current;
    }
    
    public void setCurrent(boolean current) {
      this.current = current;
    }
    
    public Date getBeginDate() {
      return beginDate;
    }
    
    public void setBeginDate(Date beginDate) {
      this.beginDate = beginDate;
    }
    
    public Date getEndDate() {
      return endDate;
    }
    
    public void setEndDate(Date endDate) {
      this.endDate = endDate;
    }
    
    public
    Map<String, Object> toMap()
    {
      Map<String, Object> mapResult = new HashMap<String, Object>();
      if(value     != null) mapResult.put("value",     value);
      if(beginDate != null) mapResult.put("beginDate", beginDate);
      if(endDate   != null) mapResult.put("subjectId", endDate);
      mapResult.put("current", current);
      return mapResult;
    }
    
    @Override
    public boolean equals(Object object) {
      if(object instanceof IdentityItem) {
        String sValue    = ((IdentityItem) object).getValue();
        boolean bCurrent = ((IdentityItem) object).isCurrent();
        Date dBeginDate  = ((IdentityItem) object).getBeginDate();
        Date dEndDate    = ((IdentityItem) object).getEndDate();
        String sValues = sValue + ":" + bCurrent + ":" + dBeginDate + ":" + dEndDate;
        String values  = value  + ":" + current  + ":" + beginDate  + ":" + endDate;
        return sValues.equals(values);
      }
      return false;
    }
    
    @Override
    public int hashCode() {
      String values = value + ":" + current + ":" + beginDate + ":" + endDate;
      return values.hashCode();
    }
    
    @Override
    public
    String toString()
    {
      return "IdentityItem(" + value + ")";
    }
  }
}
