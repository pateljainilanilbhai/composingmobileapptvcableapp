package com.app3c.webserviceconsumer;

public interface WebServiceFinishedListener {
	
	/**
	 * On completion of the network call this method is called.
	 * @param object - The Response object, result of the network call
	 */
	public void onNetworkCallComplete(WebResponse object);
	/**
	 * On cancellation of the network call this method is called.
	 */
	public void onNetworkCallCancel();


}
