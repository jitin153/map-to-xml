package com.jitin.map2xml.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Map2XmlCreator {
	public static String createXML(Map<String, Object> map, String rootElement, Boolean isFormatted) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<").append(rootElement).append(">");
		String xml = marshal(map);
		stringBuilder.append(xml).append("</").append(rootElement).append(">");
		if (isFormatted) {
			return formatXml(stringBuilder.toString());
		} else {
			return stringBuilder.toString();
		}
	}

	private static String marshal(Map<String, Object> map) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			stringBuilder.append("<").append(key).append(">");
			if (null != value) {
				if (value instanceof Map) {
					String xmlOfChildMap = marshal((Map<String, Object>) value);
					stringBuilder.append(xmlOfChildMap);
				} else {
					stringBuilder.append(value.toString());
				}
			} else {
				stringBuilder.append("");
			}
			stringBuilder.append("</").append(key).append(">");
		}
		return stringBuilder.toString();
	}

	private static String formatXml(String xml) {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(false);
		DocumentBuilder documentBuilder;
		Document document = null;
		Writer writer = new StringWriter();
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new BufferedReader(new StringReader(xml)));
			document = documentBuilder.parse(inputSource);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(new DOMSource(document), new StreamResult(writer));
		} catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
			System.out.println(e);
		}
		return writer.toString();
	}
}