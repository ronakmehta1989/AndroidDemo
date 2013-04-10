package com.abhan.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.content.Context;

public class ResponseURLHandler extends DefaultHandler {
	
	private boolean inSendMobileResponse = false, inSendMobileResult = false;
	private Abhan abhan;
	
	public ResponseURLHandler(Context context) {
		abhan = (Abhan) context.getApplicationContext();
	}
	
	@Override
	public void startDocument() throws SAXException {}
	
	@Override
	public void endDocument() throws SAXException {}
	
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if(localName.equals("SendMobileResponse")) {
			inSendMobileResponse = true;
		} else if(localName.equals("SendMobileResult")) {
			inSendMobileResult = true;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if(localName.equals("SendMobileResponse")) {
			inSendMobileResponse = false;
		} else if(localName.equals("SendMobileResult")) {
			inSendMobileResult = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) {
		if(inSendMobileResponse && inSendMobileResult) {
			final String response = new String(ch, start, length);
			abhan.setServerResponse(response);
		}
	}
}