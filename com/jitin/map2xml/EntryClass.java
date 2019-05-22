package com.jitin.map2xml;

import java.util.LinkedHashMap;
import java.util.Map;
 
import com.jitin.map2xml.utility.Map2XmlCreator;
 
public class EntryClass {
 
 public static void main(String[] args) {
  Map<String, Object> map=new LinkedHashMap<String,Object>();
  map.put("college", "ABCD College of Management Technology");
  map.put("name", "Narendra Kumar");
  map.put("rollnumber", 17);
  map.put("percentage", 86.78);
  map.put("isPass", true);
  Map<String, Object> addressMap=new LinkedHashMap<String,Object>();
  addressMap.put("housenumber", 10);
  addressMap.put("Street", "Xyz Street");
  addressMap.put("city", "Dehradoon");
  addressMap.put("state", "Uttrakhand");
  map.put("address", addressMap);
  String xml=Map2XmlCreator.createXML(map, "data", Boolean.TRUE);
  System.out.println(xml);
 }
 
}
