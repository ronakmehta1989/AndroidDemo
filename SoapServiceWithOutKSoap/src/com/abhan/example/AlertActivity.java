package com.abhan.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class AlertActivity extends Activity {
	private static AlertDialogInterface alertDialogInterface = null;
	private boolean isTwoButton = false, hasInterface = false;
	private String title = null, message = null, okButtonTitle = null, cancelButtonTitle = null;
	
	public static void startActivity(Context context, String alertTitle, String alertMessage, 
			final boolean hasTwoButtonInAlert, final boolean hasInterfaceUsed,
			final String alertOKButtonTitle, final String alertCancelButtonTitle) {
		alertDialogInterface = (AlertDialogInterface) context;
		Bundle extras = new Bundle();
		extras.putString("title", alertTitle);
		extras.putString("message", alertMessage);
		extras.putBoolean("twoButton", hasTwoButtonInAlert);
		extras.putBoolean("hasInterface", hasInterfaceUsed);
		extras.putString("ok", alertOKButtonTitle);
		extras.putString("cancel", alertCancelButtonTitle);
		
		final Intent intent = new Intent(context, AlertActivity.class);
        intent.putExtras(extras);
        context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null) {
			title = getIntent().getStringExtra("title");
			message = getIntent().getStringExtra("message");
			isTwoButton = getIntent().getBooleanExtra("twoButton", false);
			hasInterface = getIntent().getBooleanExtra("hasInterface", false);
			okButtonTitle = getIntent().getStringExtra("ok");
			if(isTwoButton) {
				cancelButtonTitle = getIntent().getStringExtra("cancel");
			}
			showAlertDialog();
		}
	}
	
	private void showAlertDialog() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(AlertActivity.this);
		alertDialog.setCancelable(false);
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setPositiveButton(okButtonTitle,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						AlertActivity.this.finish();
						if(hasInterface) {
							alertDialogInterface.okButtonClicked();
						}
					}
				});
		if(isTwoButton) {
			alertDialog.setNegativeButton(cancelButtonTitle,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(hasInterface) {
						alertDialogInterface.cancelButtonClicked();
					}
					dialog.dismiss();
					AlertActivity.this.finish();
				}
			});
		}
		alertDialog.show();
	}
}