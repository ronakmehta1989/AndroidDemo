package com.abhan.example;

public class Users {
	private String id;
	private String name;
	private String code;
	private String numOfItems;
	private boolean isChecked;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getNumOfItems() {
		return numOfItems;
	}

	public void setNumOfItems(String numOfItems) {
		this.numOfItems = numOfItems;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}