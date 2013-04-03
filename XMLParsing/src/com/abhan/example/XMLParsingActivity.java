package com.abhan.example;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;

public class XMLParsingActivity extends Activity 
{
	private static final String TAG = "XMLParsingActivity";
	public static ArrayList<String> mInnerAttributes = new ArrayList<String>();
	public static ArrayList<String> mMyTagValues = new ArrayList<String>();
	public static ArrayList<String> mAnotherAttributes = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		usingXmlResourceParser();
		usingSAXParser();
		usingDOMParser();
	}

	private void usingXmlResourceParser() 
	{
		Log.v(TAG, "-----------------------");
		Log.v(TAG, "UsingXMLResourceParser");
		Log.v(TAG, "-----------------------");
		mInnerAttributes.clear();
		mMyTagValues.clear();
		mAnotherAttributes.clear();
		
		try 
		{
			XmlResourceParser mXmlResourceParser = getResources().getXml(R.xml.order);
			mXmlResourceParser.next();
			int eventType = mXmlResourceParser.getEventType();
			boolean isMyTag = false;
			boolean isInnerTag = false;
			boolean isProduct = false;
			boolean isID = false;
			boolean isName = false;
			boolean isValue = false;

			while (eventType != XmlPullParser.END_DOCUMENT) 
			{
				if (eventType == XmlPullParser.START_DOCUMENT) 
				{
				} 
				else if (eventType == XmlPullParser.START_TAG) 
				{
					if (mXmlResourceParser.getName().equalsIgnoreCase("order")) 
					{
						isInnerTag = true;
						Log.v(TAG, "OrderTabAttributes: " + mXmlResourceParser.getAttributeValue(null, "macAddress"));
						Log.v(TAG, "OrderTabAttributes: " + mXmlResourceParser.getAttributeValue(null, "tableID"));
						Log.v(TAG, "OrderTabAttributes: " + mXmlResourceParser.getAttributeValue(null, "salesmanID"));
						Log.v(TAG, "OrderTabAttributes: " + mXmlResourceParser.getAttributeValue(null, "total"));
					}
					if (mXmlResourceParser.getName().equalsIgnoreCase("category")) 
					{
						isMyTag = true;
						Log.v(TAG, "CategoryTabAttributes: " + mXmlResourceParser.getAttributeValue(null, "name"));
					}
					if (mXmlResourceParser.getName().equalsIgnoreCase("product")) 
					{
						isProduct = true;
					}
					if (mXmlResourceParser.getName().equalsIgnoreCase("id")) 
					{
						isID = true;
					}
					if (mXmlResourceParser.getName().equalsIgnoreCase("name")) 
					{
						isName = true;
					}
					if (mXmlResourceParser.getName().equalsIgnoreCase("value")) 
					{
						isValue = true;
					}
				}
				else if (eventType == XmlPullParser.END_TAG) 
				{
					if (mXmlResourceParser.getName().equalsIgnoreCase("mytag")) 
					{
						isMyTag = false;
					}
					if (mXmlResourceParser.getName().equalsIgnoreCase("product")) 
					{
						isProduct = false;
					}
					if (mXmlResourceParser.getName().equalsIgnoreCase("id")) 
					{
						isID = false;
					}
					if (mXmlResourceParser.getName().equalsIgnoreCase("name")) 
					{
						isName = false;
					}
					if (mXmlResourceParser.getName().equalsIgnoreCase("value")) 
					{
						isValue = false;
					}

				} 
				else if (eventType == XmlPullParser.TEXT) 
				{
					if(isInnerTag && isID)
					{
						//mMyTagValues.add(mXmlResourceParser.getText());
						Log.v(TAG, "ID: " + mXmlResourceParser.getText());
					}
					else if(isInnerTag && isName)
					{
						//mMyTagValues.add(mXmlResourceParser.getText());
						Log.v(TAG, "Name: " + mXmlResourceParser.getText());
					}
					else if(isInnerTag && isValue)
					{
						//mMyTagValues.add(mXmlResourceParser.getText());
						Log.v(TAG, "Value: " + mXmlResourceParser.getText());
					}
				}
				eventType = mXmlResourceParser.next();
			}
		}
		catch (Exception e) 
		{
			Log.v(TAG, "Exception: " + e.toString());
		}
		for(int i = 0; i < mInnerAttributes.size(); i++)
		{
			Log.v(TAG, "InnerAttributes: " + mInnerAttributes.get(i));
			Log.v(TAG, "MyTagValues: " + mMyTagValues.get(i));
			Log.v(TAG, "AnotherAttributes: " + mAnotherAttributes.get(i));
		}
		Log.v(TAG, "-----------------------");
	}
	
	private void usingSAXParser()
	{
		Log.v(TAG, "-----------------------");
		Log.v(TAG, "usingSAXParser");
		Log.v(TAG, "-----------------------");
		mInnerAttributes.clear();
		mMyTagValues.clear();
		mAnotherAttributes.clear();
		
		try 
		{
			//URL mURL = new URL("YOUR SERVER URL");
			SAXParserFactory mSAXParserFactory = SAXParserFactory.newInstance();
			SAXParser mSAXParser = mSAXParserFactory.newSAXParser();
			XMLReader mXMLReader = mSAXParser.getXMLReader();
			ExampleHandler mExampleHandler = new ExampleHandler();
			mXMLReader.setContentHandler(mExampleHandler);
			//mXMLReader.parse(new InputSource(mURL.openStream()));//From Net
			mXMLReader.parse(new InputSource(getAssets().open("order.xml")));//Local from Asset folder
		}
		catch (Exception e) 
		{
			Log.v(TAG, "Exception: " + e.toString());
        }
		for(int i = 0; i < mInnerAttributes.size(); i++)
		{
			Log.v(TAG, "InnerAttributes: " + mInnerAttributes.get(i));
			Log.v(TAG, "MyTagValues: " + mMyTagValues.get(i));
			Log.v(TAG, "AnotherAttributes: " + mAnotherAttributes.get(i));
		}
		Log.v(TAG, "-----------------------");
	}
	
	private void usingDOMParser()
	{
		Log.v(TAG, "-----------------------");
		Log.v(TAG, "usingDOMParser");
		Log.v(TAG, "-----------------------");
		mInnerAttributes.clear();
		mMyTagValues.clear();
		mAnotherAttributes.clear();
		
		try 
		{
			URL mURL = new URL("http://joomlavogue.in/staff/Ketan/PhotoUpload/example.xml");
			DocumentBuilderFactory mDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder mDocumentBuilder = mDocumentBuilderFactory.newDocumentBuilder();
			Document mDocument = mDocumentBuilder.parse(new InputSource(mURL.openStream()));//From Internet
			//Document mDocument = mDocumentBuilder.parse(new InputSource(getAssets().open("example.xml")));//From Assets
			mDocument.getDocumentElement().normalize();
			NodeList mNodeList = mDocument.getElementsByTagName("innertag");
			for (int i = 0; i < mNodeList.getLength(); i++) 
			{
				Node mNode = mNodeList.item(i);
				Element mElement = (Element) mNode;
				
				NodeList nameList = mElement.getElementsByTagName("innertag");
				Element nameElement = (Element) nameList.item(0);
				nameList = nameElement.getChildNodes();
				mInnerAttributes.add(nameElement.getAttribute("sampleattribute"));
				
				NodeList name1List = mElement.getElementsByTagName("mytag");
				Element name1Element = (Element) name1List.item(0);
				name1List = name1Element.getChildNodes();
				mMyTagValues.add(((Node) name1List.item(0)).getNodeValue());
				
				NodeList name2List = mElement.getElementsByTagName("tagwithnumber");
				Element name2Element = (Element) name2List.item(0);
				name2List = name2Element.getChildNodes();
				mAnotherAttributes.add(name2Element.getAttribute("thenumber"));
			}
		}
		catch (Exception e) 
		{
			Log.v(TAG, "Exception: " + e.toString());
        }
		for(int i = 0; i < mInnerAttributes.size(); i++)
		{
			Log.v(TAG, "InnerAttributes: " + mInnerAttributes.get(i));
			Log.v(TAG, "MyTagValues: " + mMyTagValues.get(i));
			Log.v(TAG, "AnotherAttributes: " + mAnotherAttributes.get(i));
		}
		Log.v(TAG, "-----------------------");
	}
	
}