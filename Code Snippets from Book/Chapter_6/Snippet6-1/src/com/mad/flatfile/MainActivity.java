package com.mad.flatfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Bundle;
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
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput("userinput.txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(text);
			outputStreamWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getFileContent() {
		String fileContent = "";
		try {
			InputStream inputStream = openFileInput("userinput.txt");
			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String line = "";
				StringBuilder stringBuilder = new StringBuilder();
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
				inputStream.close();
				fileContent = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
