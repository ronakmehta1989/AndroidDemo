package com.abhan.example;

public interface AsyncTaskInterface {
	public void onTaskComplete(Object result);
	public void onReceivedError();
}