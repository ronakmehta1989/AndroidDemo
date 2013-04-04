package com.abhan.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.abhan.example.model.Result;
import com.abhan.example.model.SearchResponse;
import com.google.gson.Gson;

public class JsonParsingUsingGsonActivity extends Activity {
	private static final String TAG = "TAG";
	String url = "http://search.twitter.com/search.json?q=javacodegeeks";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		InputStream source = retrieveStream(url);
		Gson gson = new Gson();
		Reader reader = new InputStreamReader(source);
		SearchResponse response = gson.fromJson(reader, SearchResponse.class);
		List<Result> results = response.results;

		for (Result result : results) {
			Log.v(TAG, "result: " + result.fromUser);
		}

	}

	private InputStream retrieveStream(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w(getClass().getSimpleName(), "Error " + statusCode
						+ " for URL " + url);
				return null;
			}

			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();

		} catch (IOException e) {
			getRequest.abort();
			Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}

		return null;

	}

}