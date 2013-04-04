package com.abhan.example;

import android.graphics.Bitmap;

public class Laptop {
	private int id;
	private String brand;
	private double price;
	private Bitmap imageBitmap;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Bitmap getImageBitmap() {
		return imageBitmap;
	}

	public void setImageBitmap(Bitmap imageBitmap) {
		this.imageBitmap = imageBitmap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}