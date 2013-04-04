package com.abhan.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class ReadePub extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		AssetManager assetManager = getAssets();
		try {
			InputStream epubInputStream = assetManager
					.open("books/draculax.epub");

			Book book = (new EpubReader()).readEpub(epubInputStream);
			Log.i("epublib", "author(s): " + book.getMetadata().getAuthors());
			Log.i("epublib", "title: " + book.getTitle());

			Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage()
					.getInputStream());
			Log.i("epublib", "Coverimage is " + coverImage.getWidth() + " by "
					+ coverImage.getHeight() + " pixels");

			logTableOfContents(book.getTableOfContents().getTocReferences(), 0);
		} catch (IOException e) {
			Log.e("epublib", e.getMessage());
		}
	}

	private void logTableOfContents(List<TOCReference> tocReferences, int depth) {
		if (tocReferences == null) {
			return;
		}

		for (TOCReference tocReference : tocReferences) {
			StringBuilder tocString = new StringBuilder();
			for (int i = 0; i < depth; i++) {
				tocString.append("\t");
			}
			tocString.append(tocReference.getTitle());
			Log.i("epublib", tocString.toString());

			logTableOfContents(tocReference.getChildren(), depth + 1);
		}
	}
}