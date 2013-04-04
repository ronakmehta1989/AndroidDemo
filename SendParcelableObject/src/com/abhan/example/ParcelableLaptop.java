package com.abhan.example;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableLaptop implements Parcelable {
	private Laptop laptop;

	public Laptop getLaptop() {
		return laptop;
	}

	public ParcelableLaptop(Laptop laptop) {
		super();
		this.laptop = laptop;
	}

	private ParcelableLaptop(Parcel in) {
		laptop = new Laptop();
		laptop.setId(in.readInt());
		laptop.setBrand(in.readString());
		laptop.setPrice(in.readDouble());
		laptop.setImageBitmap((Bitmap) in.readParcelable(Bitmap.class
				.getClassLoader()));
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(laptop.getId());
		parcel.writeString(laptop.getBrand());
		parcel.writeDouble(laptop.getPrice());
		parcel.writeParcelable(laptop.getImageBitmap(),
				PARCELABLE_WRITE_RETURN_VALUE);
	}

	public static final Parcelable.Creator<ParcelableLaptop> CREATOR = new Parcelable.Creator<ParcelableLaptop>() {
		public ParcelableLaptop createFromParcel(Parcel in) {
			return new ParcelableLaptop(in);
		}

		public ParcelableLaptop[] newArray(int size) {
			return new ParcelableLaptop[size];
		}
	};
}