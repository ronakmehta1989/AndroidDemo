package com.abhan.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ExampleHandler extends DefaultHandler {
	private boolean inOrder = false, inCategory = false, inProduct = false;
	private boolean inId = false, inName = false, inValue = false;

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("order")) {
			this.inOrder = true;
			Log.v("Ketan", "SAXOrderAttributes: " + atts.getValue("macAddress"));
			Log.v("Ketan", "SAXOrderAttributes: " + atts.getValue("tableID"));
			Log.v("Ketan", "SAXOrderAttributes: " + atts.getValue("salesmanID"));
			Log.v("Ketan", "SAXOrderAttributes: " + atts.getValue("total"));
		} else if (localName.equals("category")) {
			this.inCategory = true;
			Log.v("Ketan", "SAXCategoryAttributes: " + atts.getValue("name"));
		} else if (localName.equals("product")) {
			inProduct = true;
		} else if (localName.equals("id")) {
			inId = true;
		} else if (localName.equals("name")) {
			inName = true;
		} else if (localName.equals("value")) {
			inValue = true;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("order")) {
			this.inOrder = false;
		} else if (localName.equals("category")) {
			this.inCategory = false;
		} else if (localName.equals("product")) {
			inProduct = false;
		} else if (localName.equals("id")) {
			inId = false;
		} else if (localName.equals("name")) {
			inName = false;
		} else if (localName.equals("value")) {
			inValue = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		if (this.inId) {
			// XMLParsingActivity.mMyTagValues.add(new String(ch, start,
			// length));
			Log.v("Ketan", "SAXID: " + new String(ch, start, length));
		} else if (this.inName) {
			Log.v("Ketan", "SAXNAme: " + new String(ch, start, length));
		} else if (this.inValue) {
			Log.v("Ketan", "SAXValue: " + new String(ch, start, length));
		}
	}
}