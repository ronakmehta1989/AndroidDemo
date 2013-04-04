package com.abhan.example;

import it.sauronsoftware.ftp4j.FTPClient;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.content.Context;

public class FTPSUpload {

	private Context context;
	String SFTPHOST = "";// Your Host
	String SFTPUSER = "";// Username
	String SFTPPASS = "";// Password

	public FTPSUpload(Context context) {
		this.context = context;
	}

	public boolean uploadFile(final String workingDir, String filePath) {
		boolean isUploaded = false;
		try {
			TrustManager[] trustManager = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			} };

			SSLContext sslContext = null;
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustManager, new SecureRandom());
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			FTPClient client = new FTPClient();
			client.setSSLSocketFactory(sslSocketFactory);
			client.setSecurity(FTPClient.SECURITY_FTPS);
			client.connect(SFTPHOST, 990);
			client.login(SFTPUSER, SFTPPASS);
			client.changeDirectory(workingDir);
			client.upload(new java.io.File(filePath));
			client.disconnect(true);
			isUploaded = true;
		} catch (Exception e) {
			e.printStackTrace();
			isUploaded = false;
		}

		return isUploaded;
	}
}