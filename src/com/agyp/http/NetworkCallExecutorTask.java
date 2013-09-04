package com.agyp.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.agyp.utils.TestAppConstants;

public class NetworkCallExecutorTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = NetworkCallExecutorTask.class
			.getSimpleName();
	private String mAccessTokenString;
	private String mToken;
	private ProgressDialog mProgressDialog;
	private String mTokenUrl;

	public NetworkCallExecutorTask(Bundle requestBundle, Context context) {
		mToken = requestBundle.getString("token");
		mTokenUrl = HttpRequestConstants.TOKENURL + "?client_id="
				+ TestAppConstants.CLIENT_ID + "&client_secret="
				+ TestAppConstants.CLIENT_SECRET + "&redirect_uri="
				+ TestAppConstants.CALLBACKURL
				+ "&grant_type=authorization_code";
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setTitle("Please Wait");
		mProgressDialog.setCancelable(false);
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
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

			// mTokenUrl = HttpRequestConstants.TOKENURL + "?client_id="
			// + TestAppConstants.CLIENT_ID + "&client_secret="
			// + TestAppConstants.CLIENT_SECRET + "&redirect_uri="
			// + TestAppConstants.CALLBACKURL
			// + "&grant_type=authorization_code" + "&code=" + mToken;

			// Response would be a JSON response sent by instagram
			String response = streamToString(httpsURLConnection
					.getInputStream());
			JSONObject jsonObject = (JSONObject) new JSONTokener(response)
					.nextValue();

			mAccessTokenString = jsonObject.getString("access_token");

			// User details like, name, id, tagline, profile pic etc.
			JSONObject userJsonObject = jsonObject.getJSONObject("user");

			String id = userJsonObject.getString("id");

			String username = userJsonObject.getString("username");

			String name = userJsonObject.getString("full_name");
			// mSessionStore.saveInstagramSession(mAccessTokenString, id,
			// username, name);
			showResponseDialog(name, mAccessTokenString);
		} catch (Exception e) {
			// mAuthAuthenticationListener
			// .onFail("Failed to get access token");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		dismissDialog();
		// mAuthAuthenticationListener.onSuccess();
		super.onPostExecute(result);
		
	}

	@Override
	protected void onPreExecute() {
		showDialog("Autherizing...");
		super.onPreExecute();
	}

	public String streamToString(InputStream is) throws IOException {
		String string = "";

		if (is != null) {
			StringBuilder stringBuilder = new StringBuilder();
			String line;

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));

				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}

				reader.close();
			} finally {
				is.close();
			}

			string = stringBuilder.toString();
		}

		return string;
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
	}

}
