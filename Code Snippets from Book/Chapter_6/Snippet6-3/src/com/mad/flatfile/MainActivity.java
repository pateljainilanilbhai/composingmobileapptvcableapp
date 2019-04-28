package com.mad.flatfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText inputEt;
	Button btnRead, btnWrite;
	TextView readTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inputEt = (EditText) findViewById(R.id.inputEt);
		readTv = (TextView) findViewById(R.id.readTv);
		btnRead = (Button) findViewById(R.id.btnRead);
		btnWrite = (Button) findViewById(R.id.btnWrite);
		btnRead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				readTv.setText(getFileContent());
			}
		});
		btnWrite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				writeToFile(inputEt.getText().toString());
			}
		});
	}

	private void writeToFile(String text) {
		try {
			File sdCard = Environment.getExternalStorageDirectory();
			if (sdCard.exists() && sdCard.canWrite()) {
				File newFolder = new File(sdCard.getAbsolutePath()
						+ "/FlatFile app folder");
				newFolder.mkdir();
				if (newFolder.exists() && newFolder.canWrite()) {
					File textFile = new File(newFolder.getAbsolutePath()
							+ "/userinput.txt");
					textFile.createNewFile();
					if (textFile.exists() && textFile.canWrite()) {
						FileWriter fileWriter = new FileWriter(textFile);
						fileWriter.write(text);
						fileWriter.flush();
						fileWriter.close();

					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getFileContent() {
		String fileContent = "";
		File sdCard = Environment.getExternalStorageDirectory();
		if (sdCard.exists() && sdCard.canRead()) {
			File appFolder = new File(sdCard.getAbsolutePath()
					+ "/FlatFile app folder");

			if (appFolder.exists() && appFolder.canRead()) {
				File textFile = new File(appFolder.getAbsolutePath()
						+ "/userinput.txt");
				FileInputStream fileInputStream;
				try {
					fileInputStream = new FileInputStream(textFile);
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(fileInputStream));
					String line = "";
					StringBuilder stringBuilder = new StringBuilder();
					while ((line = bufferedReader.readLine()) != null) {
						stringBuilder.append(line);
					}
					bufferedReader.close();
					fileContent = stringBuilder.toString();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		return fileContent;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
