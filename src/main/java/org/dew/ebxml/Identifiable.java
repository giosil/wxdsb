package org.dew.ebxml;

import java.io.Serializable;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public 
class Identifiable implements IElement, Serializable
{
  private static final long serialVersionUID = -8075537547112187163L;
  
  protected String home;
  protected String id;
  protected List<Slot> slots;
  
  public Identifiable()
  {
  }
  
  public Identifiable(String id)
  {
    this.id = id;
  }
  
  @SuppressWarnings("rawtypes")
  public Identifiable(Map map)
  {
    if(map == null) return;
    
    Object oHome = map.get("home");
    if(oHome != null) home = oHome.toString();
    Object oId = map.get("id");
    if(oId != null) id = oId.toString();
    Object oSlots = map.get("slots");
    if(oSlots instanceof Map) {
      Map mapSlots = (Map) oSlots;
      slots = new ArrayList<Slot>(mapSlots.size());
      Iterator iterator = mapSlots.entrySet().iterator();
      while(iterator.hasNext()) {
        Map.Entry entry = (Map.Entry) iterator.next();
        slots.add(new Slot(entry.getKey(), entry.getValue()));
      }
    }
    else
    if(oSlots instanceof Collection) {
      Collection col = (Collection) oSlots;
      slots = new ArrayList<Slot>(col.size());
      Iterator iterator = col.iterator();
      while(iterator.hasNext()) {
        Object item = iterator.next();
        if(item instanceof Map) {
          slots.add(new Slot((Map) item));
        }
      }
    }
    else
    if(oSlots != null && oSlots.getClass().isArray()) {
      int length = Array.getLength(oSlots);
      slots = new ArrayList<Slot>(length);
      for(int i = 0; i < length; i++) {
        Object item = Array.get(oSlots, i);
        if(item instanceof Map) {
          slots.add(new Slot((Map) item));
        }
      }
    }
  }
  
  public String getHome() {
    return home;
  }
  
  public void setHome(String home) {
    this.home = home;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public List<Slot> getSlots() {
    return slots;
  }
  
  public void setSlots(List<Slot> slots) {
    this.slots = slots;
  }
  
  public void addSlot(Slot slot) {
    if(slot  == null) return;
    if(slots == null) slots = new ArrayList<Slot>();
    slots.add(slot);
  }
  
  public void removeSlot(Slot slot) {
    if(slot  == null) return;
    if(slots == null) return;
    slots.remove(slot);
  }
  
  public String[] getSlotNames() {
    if(slots == null || slots.size() == 0) return new String[0];
    String[] result = new String[slots.size()];
    for(int i = 0; i < slots.size(); i++) {
      result[i] = slots.get(i).getName();
    }
    return result;
  }
  
  public String[] getSlotValues(String name) {
    if(name  == null || name.length() == 0) return null;
    if(slots == null || slots.size()  == 0) return null;
    for(int i = 0; i < slots.size(); i++) {
      Slot slot = slots.get(i);
      if(name.equals(slot.getName())) {
        List<String> listValues = slot.getValues();
        if(listValues == null || listValues.size() == 0) {
          return new String[0];
        }
        String[] result = new String[listValues.size()];
        for(int j = 0; j < listValues.size(); j++) {
          result[j] = listValues.get(j);
        }
        return result;
      }
    }
    return null;
  }
  
  public String getSlotFirstValue(String name) {
    if(name  == null || name.length() == 0) return null;
    if(slots == null || slots.size()  == 0) return null;
    for(int i = 0; i < slots.size(); i++) {
      Slot slot = slots.get(i);
      if(name.equals(slot.getName())) {
        List<String> listValues = slot.getValues();
        if(listValues == null || listValues.size() == 0) {
          return null;
        }
        return listValues.get(0);
      }
    }
    return null;
  }
  
  public String getSlotFirstValueContains(String name) {
    if(name  == null || name.length() == 0) return null;
    if(slots == null || slots.size()  == 0) return null;
    for(int i = 0; i < slots.size(); i++) {
      Slot slot = slots.get(i);
      String slotName = slot.getName();
      if(slotName != null && slotName.indexOf(name) >= 0) {
        List<String> listValues = slot.getValues();
        if(listValues == null || listValues.size() == 0) {
          return null;
        }
        return listValues.get(0);
      }
    }
    return null;
  }
  
  public int getSlotIntValue(String name) {
    String value = getSlotFirstValue(name);
    if(value == null || value.length() == 0) {
      return 0;
    }
    int iResult = 0;
    try { iResult = Integer.parseInt(value); } catch(Exception ex) {}
    return iResult;
  }
  
  public Date getSlotDateValue(String name) {
    String value = getSlotFirstValue(name);
    if(value == null || value.length() == 0) {
      return null;
    }
    Calendar calendar = Utils.stringToCalendar(value);
    if(calendar == null) return null;
    return calendar.getTime();
  }
  
  public Map<String,List<String>> getSlotsMap() {
    Map<String,List<String>> mapResult = new HashMap<String, List<String>>();
    if(slots == null || slots.size()  == 0) return mapResult;
    for(int i = 0; i < slots.size(); i++) {
      Slot slot = slots.get(i);
      String sName = slot.getName();
      if(sName == null) continue;
      mapResult.put(sName, slot.getValues());
    }
    return mapResult;
  }
  
  public String getTagName() {
    return "Identifiable";
  }
  
  public String getAttribute(String name) {
    if(name == null) return null;
    if(name.equals("id")) {
      return this.id;
    }
    else
    if(name.equals("home")) {
      return this.home;
    }
    return null;
  }
  
  public void setAttribute(String name, String value) {
    if(name == null) return;
    if(name.equals("id")) {
      this.id = value;
    }
    else
    if(name.equals("home")) {
      this.home = value;
    }
  }
  
  public String toXML(String namespace) {
    if(namespace == null || namespace.length() == 0) {
      namespace = "";
    }
    else 
    if(!namespace.endsWith(":")) {
      namespace += ":";
    }
    if(id == null || id.length() == 0) {
      id = "urn:uuid:" + UUID.randomUUID().toString();
    }
    StringBuffer sb = new StringBuffer(500);
    sb.append("<" + namespace + getTagName());
    if(home != null && home.length() > 0) {
      sb.append(" home=\"" + home + "\"");
    }
    if(id != null && id.length() > 0) {
      sb.append(" id=\"" + id + "\"");
    }
    sb.append(">");
    if(slots != null) {
      for(Slot slot : slots) {
        sb.append(slot.toXML(namespace));
      }
    }
    sb.append("</" + namespace + getTagName() + ">");
    return sb.toString();
  }
  
  public Map<String, Object> toMap() {
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put("tagName", getTagName());
    if(id    != null) mapResult.put("id",   id);
    if(home  != null) mapResult.put("home", home);
    if(slots != null) {
      List<Map<String,Object>> listOfMap = new ArrayList<Map<String,Object>>(slots.size());
      for(Slot slot : slots) {
        listOfMap.add(slot.toMap());
      }
    }
    return mapResult;
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof Identifiable) {
      String sId = ((Identifiable) object).getId();
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
    return "Identifiable(" + id + ")";
  }
}
