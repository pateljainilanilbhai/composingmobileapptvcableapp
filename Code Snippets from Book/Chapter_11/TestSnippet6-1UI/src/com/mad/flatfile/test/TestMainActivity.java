package com.mad.flatfile.test;



import com.mad.flatfile.MainActivity;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestMainActivity extends
		ActivityInstrumentationTestCase2<MainActivity> {
	
	private MainActivity activity;
	private TextView textView1, textView2, readTextView;
	private EditText inputEditText;
	private Button buttonRead, buttonWrite;
	
	
//	private Instrumentation instrumentation;
	
	public TestMainActivity()
	{
		super("com.mad.flatfile", MainActivity.class);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		activity=getActivity();
		textView1=(TextView)activity.findViewById(com.mad.flatfile.R.id.textView1);
		textView2=(TextView)activity.findViewById(com.mad.flatfile.R.id.textView2);
		readTextView=(TextView)activity.findViewById(com.mad.flatfile.R.id.readTv);	
		inputEditText=(EditText)activity.findViewById(com.mad.flatfile.R.id.inputEt);
		buttonRead=(Button)activity.findViewById(com.mad.flatfile.R.id.btnRead);
		buttonWrite=(Button)activity.findViewById(com.mad.flatfile.R.id.btnWrite);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		activity=null;
		textView1=null;
		textView2=null;
		readTextView=null;
		
		inputEditText=null;
		buttonRead=null;
		buttonWrite=null;
	}

	
	public void testInitializations() {
		assertNotNull("Activity not initialized", activity);
		assertNotNull("textView1 not initialized", textView1);
		assertNotNull("textView2 not initialized", textView2);
		assertNotNull("readTextView not initialized", readTextView);
		assertNotNull("inputEditText not initialized", inputEditText);
		assertNotNull("buttonRead not initialized", buttonRead);
		assertNotNull("buttonWrite not initialized", buttonWrite);
	}
	
	public void testValues() {
		assertEquals("textView1 value not proper", "Input text:", textView1.getText().toString());
		assertEquals("textView2 value not proper", "Text in file :", textView2.getText().toString());
		assertEquals("readTextView value not proper", "< text from file >", readTextView.getText().toString());
		assertEquals("inputEditText value not proper", "", inputEditText.getText().toString());
		assertEquals("buttonRead value not proper", "Read from file", buttonRead.getText().toString());
		assertEquals("buttonWrite value not proper", "Write to file", buttonWrite.getText().toString());
	}
	
	public void testemptyFileContent() {
		TouchUtils.tapView(this, inputEditText);
		TouchUtils.clickView(this, buttonWrite);
		TouchUtils.clickView(this, buttonRead);
		assertEquals("Empty content not being saved","", readTextView.getText().toString());
	}
	
	public void testWithSomeText() {
		TouchUtils.tapView(this, inputEditText);
		String message="This is a test string to be saved in file";
		
		getInstrumentation().sendStringSync(message);
		
		TouchUtils.clickView(this, buttonWrite);
		TouchUtils.clickView(this, buttonRead);
		assertEquals("Correct content not being saved to file",message, readTextView.getText().toString());
	}
		
	public void testonPauseResumeImpactOnApp() {
		TouchUtils.tapView(this, inputEditText);
		String message="This is a test string to be saved in file";
		getInstrumentation().sendStringSync(message);
		getInstrumentation().callActivityOnPause(activity);
		getInstrumentation().callActivityOnResume(activity);
		assertEquals("Test case failed: value changed during transition", message, inputEditText.getText().toString());
		
	}
	
	public void testSuccessfulFileSave() {
		getInstrumentation().runOnMainSync(new Runnable() {
			@SuppressLint("NewApi")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String message="This is a test string to be saved in file";
				inputEditText.setText(message);
				buttonWrite.callOnClick();
				buttonRead.callOnClick();
				assertEquals(message, readTextView.getText().toString());
			}
		});
	}
}
