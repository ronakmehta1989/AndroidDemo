package com.abhan.example;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class MainActivity extends Activity {
	String SFTPHOST = "";// Server Host
	int SFTPPORT = 22;// SFTP Port
	String SFTPUSER = "";// UserName
	String SFTPPASS = "";// Password
	String SFTPWORKINGDIR = "/home/upload";// Server working directory where you
											// need to upload your file

	Session session = null;
	Channel channel = null;
	ChannelSftp channelSftp = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String filePath = Environment.getExternalStorageDirectory()
				+ File.separator + "DCIM" + File.separator + "Camera"
				+ File.separator + "IMG_20120910_143432.jpg";
		Log.d("TAG", "filePath: " + filePath);

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			File file = new File(filePath);
			channelSftp.put(new FileInputStream(file), file.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}