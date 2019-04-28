package com.app3c.webserviceconsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.os.AsyncTask;
import android.util.Log;

public class WebServiceHitter extends AsyncTask<Object, Void, WebResponse> {
	WebServiceFinishedListener webServiceFinishedListener = null;
	
	public WebServiceHitter(WebServiceFinishedListener webServiceFinishedListener){
		this.webServiceFinishedListener = webServiceFinishedListener;
	}

	
	
	@Override
	protected WebResponse doInBackground(Object... params) {
		WebResponse webResponse = new WebResponse();
		StringBuilder jsonStr = new StringBuilder();
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, AppConfig.CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters, AppConfig.SOCKET_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParameters);
		
		try {
			HttpResponse response = client.execute((HttpPost)params[0]);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			
			while ((line = rd.readLine()) != null) {
				jsonStr = jsonStr.append(line);
			}
			webResponse.setResponse(jsonStr.toString());
			webResponse.setResponseCode(ResponseCode.SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			webResponse.setResponse(e.getMessage());
			webResponse.setResponseCode(ResponseCode.FAILURE);
		} 
		return webResponse;
	}	
	@Override
	protected void onPostExecute(WebResponse result) {
		super.onPostExecute(result);
		
		if(result.getResponseCode().compareTo(ResponseCode.SUCCESS)==0)
			webServiceFinishedListener.onNetworkCallComplete(result);
		else
			webServiceFinishedListener.onNetworkCallCancel();
	}
}
