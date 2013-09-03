package com.agyp.http;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import com.agyp.utils.TestAppConstants;

/**
 * This class is used to prepare Http request object.
 * 
 * @author ajay
 * 
 */
public final class HttpRequestMaker {

	private final static String TAG = HttpRequestMaker.class.getSimpleName();

	/**
	 * HttpRequestBase Object.
	 */
	private static HttpRequestBase mRequestObject;

	/**
	 * Makes Request object using the following parameters and returns.
	 * 
	 * @return
	 */

	public static HttpRequestBase getRequest() {

		return null;
	}

	private static HttpRequestBase getUserDetailsRequest(String token) {

		String url = HttpRequestConstants.TOKENURL + "?client_id="
				+ TestAppConstants.CLIENT_ID + "&client_secret="
				+ TestAppConstants.CLIENT_SECRET + "&redirect_uri="
				+ TestAppConstants.CALLBACKURL
				+ "&grant_type=authorization_code" + "&code=" + token;
		mRequestObject = new HttpPost(url);

		return mRequestObject;
	}

}
