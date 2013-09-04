package com.agyp.http;

/**
 * @author ajay
 * 
 */
public interface HttpCallbackListener {

	/**
	 * Called when web service request execution is completed successfully.
	 * 
	 */
	public abstract void onRequestComplete();

	
	/**
	 * Called when the error is incurred while executing the web service
	 * request.
	 * 
	 */
	public abstract void onError();

}
