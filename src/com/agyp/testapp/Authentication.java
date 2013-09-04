package com.agyp.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.agyp.http.HttpRequestConstants;
import com.agyp.testapp.LoginDialog.AuthDialogListener;
import com.agyp.utils.TestAppConstants;

public class Authentication {

	private static final String TAG = Authentication.class.getSimpleName();
	// private SessionStore mSessionStore;

	private String mAccessTokenString;
	private String mToken;
	private AuthDialogListener mClientAuthDialogListener;
	private ProgressDialog mProgressDialog;
	private Context mContext;
	private String mAuthUrl;
	private String mTokenUrl;
	private LoginDialog mLoginDialog;

	public Authentication(Context context,
			AuthDialogListener clientAuthDialogListener) {
		mContext = context;
		this.mClientAuthDialogListener = clientAuthDialogListener;
		// mSessionStore = new SessionStore(context);

		mAuthUrl = HttpRequestConstants.AUTHURL
				+ "?client_id="
				+ TestAppConstants.CLIENT_ID
				+ "&redirect_uri="
				+ TestAppConstants.CALLBACKURL
				+ "&response_type=code&display=touch&scope=likes+comments+relationships";

		mTokenUrl = HttpRequestConstants.TOKENURL + "?client_id="
				+ TestAppConstants.CLIENT_ID + "&client_secret="
				+ TestAppConstants.CLIENT_SECRET + "&redirect_uri="
				+ TestAppConstants.CALLBACKURL
				+ "&grant_type=authorization_code";

		Log.v(TAG, "mTokenUrl url: " + mTokenUrl);

		// mAccessTokenString = mSessionStore.getInstaAccessToken();

		mLoginDialog = new LoginDialog(context, mAuthUrl,
				mClientAuthDialogListener);

		mLoginDialog.show();
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setTitle("Please Wait");
		mProgressDialog.setCancelable(false);

	}

	public void dismissLoginDialog() {
		if (mLoginDialog != null && !mLoginDialog.isShowing()) {
			mLoginDialog.dismiss();
		}
	}

	public void dismissLoginDialog(String token) {
		if (mLoginDialog != null && !mLoginDialog.isShowing()) {
			mLoginDialog.dismiss();
		}
	}

}
