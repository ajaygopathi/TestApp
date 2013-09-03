package com.agyp.http;

import com.agyp.utils.TestAppConstants;

/**
 * This class is used to define web service request ids and request urls
 * 
 * @author ajay
 */
public final class HttpRequestConstants {

	private final static String TAG = HttpRequestConstants.class
			.getSimpleName();

	private final static String AUTHURL = "https://api.instagram.com/oauth/authorize/";
	private static final String TOKENURL = "https://api.instagram.com/oauth/access_token";
	private static final String APIURL = "https://api.instagram.com/v1";

	public static final String STRING_AUTHURL = AUTHURL
			+ "?client_id="
			+ TestAppConstants.CLIENT_ID
			+ "&redirect_uri="
			+ TestAppConstants.CALLBACKURL
			+ "&response_type=code&display=touch&scope=likes+comments+relationships";

	public static final String STRING_TOKENURL = TOKENURL + "?client_id="
			+ TestAppConstants.CLIENT_ID + "&client_secret="
			+ TestAppConstants.CLIENT_SECRET + "&redirect_uri="
			+ TestAppConstants.CALLBACKURL + "&grant_type=authorization_code";

}
