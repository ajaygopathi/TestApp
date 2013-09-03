package com.agyp.http;

/**
 * This class is used to define web service request ids and request urls
 * 
 * @author ajay
 */
public final class HttpRequestConstants {

	private final static String TAG = HttpRequestConstants.class
			.getSimpleName();

	// Registered app with Instagram Developers
	private final static String ClientID = "a4c40703e0e14002a60d1c5dde0854e6";
	private final static String ClientSecret = "2dc853e752d94616bb0e088e0089896b";
	private final static String WebsiteURL = "http://www.testapp.com";
	private final static String RedirectURI = "http://www.google.com";
	
	private final static String AuthenticateURL = "https://api.instagram.com/oauth/authorize/";
	private static final String TOKENURL ="https://api.instagram.com/oauth/access_token";
	public static final String APIURL = "https://api.instagram.com/v1";

}
