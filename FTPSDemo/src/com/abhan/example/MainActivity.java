package com.abhan.example;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final String workingDirectory = "/home/upload";
		final String filePath = Environment.getExternalStorageDirectory()
				+ File.separator + "YourFileToBeUploadOnServer";

		FTPSUpload ftpsUpload = new FTPSUpload(MainActivity.this);
		ftpsUpload.uploadFile(workingDirectory, filePath);
	}

}