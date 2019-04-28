package com.app3c.webserviceconsumer;

public class WebResponse {


	private Object response;
	private ResponseCode responseCode;
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	public ResponseCode getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}	
}
