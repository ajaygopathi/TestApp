package com.agyp.testapp;

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

		LoginDialog loginDialog = new LoginDialog(context, mAuthUrl,
				mClientAuthDialogListener);

		loginDialog.show();
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setTitle("Please Wait");
		mProgressDialog.setCancelable(false);

	}

	public class GetInstagramTokenAsyncTask extends AsyncTask<Void, Void, Void> {
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
				outputStreamWriter.write("client_id="
						+ TestAppConstants.CLIENT_ID + "&client_secret="
						+ TestAppConstants.CLIENT_SECRET
						+ "&grant_type=authorization_code" + "&redirect_uri="
						+ TestAppConstants.CALLBACKURL + "&code=" + mToken);

				outputStreamWriter.flush();

				
//				mTokenUrl = HttpRequestConstants.TOKENURL + "?client_id="
//						+ TestAppConstants.CLIENT_ID + "&client_secret="
//						+ TestAppConstants.CLIENT_SECRET + "&redirect_uri="
//						+ TestAppConstants.CALLBACKURL
//						+ "&grant_type=authorization_code" + "&code=" + mToken;
				
				
				
				// Response would be a JSON response sent by instagram
				String response = streamToString(httpsURLConnection
						.getInputStream());
				// Log.e("USER Response", response);
				JSONObject jsonObject = (JSONObject) new JSONTokener(response)
						.nextValue();

				// Your access token that you can use to make future request
				mAccessTokenString = jsonObject.getString("access_token");
				// Log.e(TAG, mAccessTokenString);

				// User details like, name, id, tagline, profile pic etc.
				JSONObject userJsonObject = jsonObject.getJSONObject("user");
				// Log.e("USER DETAIL", userJsonObject.toString());

				// User ID
				String id = userJsonObject.getString("id");
				// Log.e(TAG, id);

				// Username
				String username = userJsonObject.getString("username");
				// Log.e(TAG, username);

				// User full name
				String name = userJsonObject.getString("full_name");
				// Log.e(TAG, name);

				// //TO?DO
				// mSessionStore.saveInstagramSession(mAccessTokenString, id,
				// username, name);
				showResponseDialog(name, mAccessTokenString);
			} catch (Exception e) {
				mAuthAuthenticationListener
						.onFail("Failed to get access token");
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			dismissDialog();
			mAuthAuthenticationListener.onSuccess();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			showDialog("Getting Access Token..");
			super.onPreExecute();
		}

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
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
	}

	public void dismissDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	public void showResponseDialog(String name, String accessToken) {
		// Intent broadcastIntent = new Intent();
		// broadcastIntent.setAction(ResponseListener.ACTION_RESPONSE);
		// broadcastIntent.putExtra(ResponseListener.EXTRA_NAME, name);
		// broadcastIntent.putExtra(ResponseListener.EXTRA_ACCESS_TOKEN,
		// accessToken);
		// mContext.sendBroadcast(broadcastIntent);
	}

}
