package com.abhan.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements AsyncTaskInterface, AlertDialogInterface {
	
	private BaseAsyncTask baseAsyncTask = null;
	private Abhan abhan;
	
	private TextView txtStatus = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		abhan = (Abhan) this.getApplication();
		abhan.registerAllReceivers();
		
		txtStatus = (TextView) findViewById(R.id.main_txtStatus);
		
		final String param0 = "http://172.16.8.143/mEcpWebService/VisitRegistration_SMS.asmx";
		final String param1 = "172.16.8.143";
		final String param2 = "http://localhost/mEcpWebService/SendMobile";
		final String param3 = getTopOfHeader()
				+ "<SendMobile xmlns=\"http://localhost/mEcpWebService\">"
				+ "<Number>9510147597</Number>" + "</SendMobile>"
				+ getBottomOfHeader();
		abhan.setProgressMessage("Please Wait...");
		baseAsyncTask = new BaseAsyncTask(MainActivity.this);
		baseAsyncTask.execute(param0, param1, param2, param3);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		abhan.unRegisterAllReceivers();
	}

	@Override
	public void onTaskComplete(Object result) {
		android.util.Log.i(Abhan.TAG, "Result: " + result.toString());
		if(result.toString().equals("Done") && abhan.isServerResValid()) {
			android.util.Log.i(Abhan.TAG, "Result:isValid " + abhan.isServerResValid());
			android.util.Log.i(Abhan.TAG, "Result:message " + abhan.getServerMessage());
			txtStatus.setText(abhan.getServerMessage());
		}
	}

	@Override
	public void onReceivedError() {
		txtStatus.setText("Webservice Not Executed Properly. Please Try After Sometimes.");
		AlertActivity.startActivity(MainActivity.this, 
				getString(R.string.app_name), 
				"Webservice Not Executed Properly. Please Try After Sometimes.", 
				false, false,
				"Dismiss", 
				"Cancel");
	}
	
	private String getTopOfHeader() {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
				+ "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>";
	}
	
	private String getBottomOfHeader() {
		return "</soap:Body>" + "</soap:Envelope>";
	}

	@Override
	public void okButtonClicked() {}

	@Override
	public void cancelButtonClicked() {}
}