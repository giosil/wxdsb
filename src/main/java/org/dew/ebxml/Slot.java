package org.dew.ebxml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public 
class Slot implements IElement, Serializable 
{
  private static final long serialVersionUID = 69829771850255519L;
  
  protected String name;
  protected String slotType;
  protected List<String> values;
  
  public Slot()
  {
    this.values = new ArrayList<String>();
  }
  
  public Slot(String name, String value)
  {
    this.name   = name;
    this.values = new ArrayList<String>();
    if(value != null) {
      this.values.add(value);
    }
  }
  
  public Slot(String name, Date value)
  {
    this.name   = name;
    this.values = new ArrayList<String>();
    if(value != null) {
      this.values.add(Utils.formatDateTime(value));
    }
  }
  
  public Slot(String name, Date value, boolean hhmm, boolean ss)
  {
    this.name   = name;
    this.values = new ArrayList<String>();
    if(value != null) {
      this.values.add(Utils.formatDateTime(value, hhmm, ss));
    }
  }
  
  public Slot(String name, int value)
  {
    this.name   = name;
    this.values = new ArrayList<String>();
    this.values.add(String.valueOf(value));
  }
  
  public Slot(String name, String value0, String value1)
  {
    this.name   = name;
    this.values = new ArrayList<String>();
    if(value0 != null) {
      this.values.add(value0);
    }
    if(value1 != null) {
      this.values.add(value1);
    }
  }
  
  public Slot(Object name, Object values)
  {
    this.name   = name != null ? name.toString() : null;
    this.values = Utils.toListOfString(values);
    if(this.values == null) {
      this.values = new ArrayList<String>();
    }
  }
  
  public Slot(Slot slot)
  {
    if(slot == null) return;
    this.name     = slot.getName();
    this.slotType = slot.getSlotType();
    List<String> listOfValues = slot.getValues();
    if(listOfValues == null) {
      this.values = null;
    }
    else {
      this.values = new ArrayList<String>(listOfValues);
    }
  }
  
  public Slot(Map<String, Object> map)
  {
    if(map == null) return;
    this.name   = Utils.toString(map.get("name"), null);
    this.values = Utils.toListOfString(map.get("values"));
    if(this.values == null) {
      this.values = new ArrayList<String>();
    }
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getSlotType() {
    return slotType;
  }
  
  public void setSlotType(String slotType) {
    this.slotType = slotType;
  }
  
  public List<String> getValues() {
    return values;
  }
  
  public void setValues(List<String> values) {
    this.values = values;
  }
  
  public void addValue(String value) {
    if(value  == null) value  = "";
    if(values == null) values = new ArrayList<String>();
    values.add(value);
  }
  
  public String getTagName() {
    return "Slot";
  }
  
  public String getAttribute(String name) {
    if(name == null) return null;
    if(name.equals("name")) {
      return this.name;
    }
    else if(name.equals("slotType")) {
      return this.slotType;
    }
    return null;
  }
  
  public void setAttribute(String name, String value) {
    if(name == null) return;
    if(name.equals("name")) {
      this.name = value;
    }
    else if(name.equals("slotType")) {
      this.slotType = value;
    }
  }
  
  public String toXML(String namespace) {
    if(name == null || name.length() == 0) return "";
    if(namespace == null || namespace.length() == 0) {
      namespace = "";
    }
    else if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    StringBuffer sb = new StringBuffer(120);
    if(slotType != null && slotType.length() > 0) {
      sb.append("<" + namespace + "Slot name=\"" + name + "\" slotType=\"" + slotType + "\">");
    }
    else {
      sb.append("<" + namespace + "Slot name=\"" + name + "\">");
    }
    sb.append("<" + namespace + "ValueList>");
    if(values != null && values.size() > 0) {
      for(String value : values) {
        sb.append("<" + namespace + "Value>");
        sb.append(Utils.normalizeString(value));
        sb.append("</" + namespace + "Value>");
      }
    }
    sb.append("</" + namespace + "ValueList>");
    sb.append("</" + namespace + "Slot>");
    return sb.toString();
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName", getTagName());
    if(name     != null) mapResult.put("name",     name);
    if(slotType != null) mapResult.put("slotType", slotType);
    if(values   != null) mapResult.put("values",   values);
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof Slot) {
      String sName = ((Slot) object).getName();
      if(sName == null && name == null) return true;
      return sName != null && sName.equals(name);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(name == null) return 0;
    return name.hashCode();
  }
  
  @Override
  public String toString() {
    return "Slot(" + name + ")";
  }
}
