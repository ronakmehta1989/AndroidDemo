package com.abhan.example;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class BaseAsyncTask extends AsyncTask<String, Void, Object> {

	private final Activity activity;
	private final Abhan abhan;
	private final AsyncTaskInterface callerActivityInterface;
	private ProgressDialog progressDialog;

	public BaseAsyncTask(Context context) {
		this.activity = (Activity) context;
		this.callerActivityInterface = (AsyncTaskInterface) context;
		abhan = (Abhan) context.getApplicationContext();
	}

	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage(abhan.getProgressMessage());
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	@Override
	protected Object doInBackground(String... params) {
		String result = null;
		try {
			URL url = new URL(params[0]);
	        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	        httpURLConnection.setDoOutput(true);
	        httpURLConnection.setConnectTimeout(60000);
	        httpURLConnection.setRequestMethod("POST");
	        httpURLConnection.setRequestProperty("Host", params[1]);
	        httpURLConnection.setRequestProperty("SOAPAction", params[2]);
	        httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
	        httpURLConnection.setRequestProperty("Content-Length", (new Integer(params[3].length())).toString());
	        
	        OutputStream outputStream = httpURLConnection.getOutputStream();
	        outputStream.write(params[3].getBytes());
	        int responseCode = httpURLConnection.getResponseCode();
	        if(outputStream != null) {
	        	outputStream.close();
	        }
	        
	        if(responseCode == HttpURLConnection.HTTP_OK) {
	        	try {
	        		InputStream inputStream = httpURLConnection.getInputStream();
	    			SAXParserFactory mSAXParserFactory = SAXParserFactory.newInstance();
	    			SAXParser mSAXParser = mSAXParserFactory.newSAXParser();
	    			XMLReader mXMLReader = mSAXParser.getXMLReader();
	    			ResponseURLHandler responseURLHandler = new ResponseURLHandler(activity);
	    			mXMLReader.setContentHandler(responseURLHandler);
	    			mXMLReader.parse(new InputSource(inputStream));
	    			if(inputStream != null) {
	    				inputStream.close();
	    			}
	    			if(abhan.getServerResponse() != null) {
	    				JSONObject responseObject = new JSONObject(abhan.getServerResponse().trim());
						final boolean isValid = responseObject.optBoolean("isValid");
						final String message = responseObject.optString("Message");
						abhan.setServerMessage(message);
						abhan.setServerResValid(isValid);
						result = "Done";
	    			}
	    		} catch (Exception e) {
	    			android.util.Log.e(Abhan.TAG, "Exception: " + e.toString());
	    		}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return result;
	}
	
	@Override
	protected void onPostExecute(Object result) {
		if(progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		
		if (result != null) {
			callerActivityInterface.onTaskComplete(result);
		} else {
			callerActivityInterface.onReceivedError();
		}
	}
}