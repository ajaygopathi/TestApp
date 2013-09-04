package com.agyp.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.agyp.utils.TestAppConstants;
import com.agyp.utils.Utils;

public class NetworkCallExecutorTask extends AsyncTask<Void, Void, Bundle> {

	private static final String TAG = NetworkCallExecutorTask.class
			.getSimpleName();
	private String mAccessTokenString;
	private String mToken;
	private ProgressDialog mProgressDialog;
	private HttpCallbackListener mlistener;
	private HttpEntity mHttpEntity;
	private Bundle mRequestBundle;

	public NetworkCallExecutorTask(Bundle requestBundle, Context context,
			HttpCallbackListener listener) {
		mlistener = listener;
		mRequestBundle = requestBundle;

		mToken = requestBundle.getString("token");

		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setTitle("Please Wait");
		mProgressDialog.setCancelable(false);
	}

	@Override
	protected Bundle doInBackground(Void... params) {
		try {

			// Response would be a JSON response sent by instagram
			HttpURLConnection httpsURLConnection = getUserDetails(mToken);
			String response = Utils.streamToString(httpsURLConnection
					.getInputStream());
			JSONObject jsonObject = (JSONObject) new JSONTokener(response)
					.nextValue();
			Log.v(TAG, "response: ---- " + response);

		} catch (SocketTimeoutException timeout) {
			timeout.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (mHttpEntity != null) {
				try {
					mHttpEntity.consumeContent();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bundle result) {
		dismissDialog();
		// mAuthAuthenticationListener.onSuccess();
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		showDialog("Autherizing...");
		super.onPreExecute();
	}

	public void unregisterCallbackListener() {
		mlistener = null;
	}

	public void showDialog(String message) {
		if (mProgressDialog == null)
			return;
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
	}

	public void dismissDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	public void showResponseDialog(String name, String accessToken) {

		Log.v(TAG, "response name: " + name);
		Log.v(TAG, "response accessToken: " + accessToken);

		if (mlistener != null) {
			mlistener.onRequestComplete();
		}
	}

	private HttpURLConnection getUserDetails(String token) throws IOException {

		String mTokenUrl = HttpRequestConstants.TOKENURL + "?client_id="
				+ TestAppConstants.CLIENT_ID + "&client_secret="
				+ TestAppConstants.CLIENT_SECRET + "&redirect_uri="
				+ TestAppConstants.CALLBACKURL
				+ "&grant_type=authorization_code";

		Log.v(TAG, "token url: ---- " + mTokenUrl);

		URL url = new URL(mTokenUrl);
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
				.openConnection();
		httpsURLConnection.setRequestMethod("POST");
		httpsURLConnection.setDoInput(true);
		httpsURLConnection.setDoOutput(true);

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				httpsURLConnection.getOutputStream());
		outputStreamWriter.write("client_id=" + TestAppConstants.CLIENT_ID
				+ "&client_secret=" + TestAppConstants.CLIENT_SECRET
				+ "&grant_type=authorization_code" + "&redirect_uri="
				+ TestAppConstants.CALLBACKURL + "&code=" + mToken);

		outputStreamWriter.flush();
		return httpsURLConnection;

		// mAccessTokenString = jsonObject.getString("access_token");
		//
		// // User details like, name, id, tagline, profile pic etc.
		// JSONObject userJsonObject = jsonObject.getJSONObject("user");
		// String id = userJsonObject.getString("id");
		// String username = userJsonObject.getString("username");
		// String name = userJsonObject.getString("full_name");
		// // mSessionStore.saveInstagramSession(mAccessTokenString, id,
		// // username, name);
		// Log.v(TAG, "" + id + " " + username + " " + name);
		// showResponseDialog(name, mAccessTokenString);
	}

}
