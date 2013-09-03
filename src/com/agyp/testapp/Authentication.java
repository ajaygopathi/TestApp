package com.agyp.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.agyp.http.HttpRequestConstants;
import com.agyp.testapp.LoginDialog.AuthDialogListener;

public class Authentication {

	private static final String TAG = Authentication.class.getSimpleName();
	// private SessionStore mSessionStore;

	private String mAccessTokenString;
	private String mToken;

	private AuthAuthenticationListener mAuthAuthenticationListener;

	private ProgressDialog mProgressDialog;
	private Context mContext;

	public Authentication(Context context) {
		mContext = context;
		// mSessionStore = new SessionStore(context);

		ClientAuthDialogListener instaAuthDialogListener = new ClientAuthDialogListener();

		// mAccessTokenString = mSessionStore.getInstaAccessToken();

		LoginDialog instaLoginDialog = new LoginDialog(context,
				HttpRequestConstants.STRING_AUTHURL, instaAuthDialogListener);

		instaLoginDialog.show();
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setTitle("Please Wait");
		mProgressDialog.setCancelable(false);

	}

	public void setAuthAuthenticationListener(
			AuthAuthenticationListener authAuthenticationListener) {
		this.mAuthAuthenticationListener = authAuthenticationListener;
	}

	public interface AuthAuthenticationListener {
		public abstract void onSuccess();

		public abstract void onFail(String error);
	}

	public class ClientAuthDialogListener implements AuthDialogListener {

		@Override
		public void onComplete(String token) {
			getAccessToken(token);
		}

		@Override
		public void onError(String error) {

		}

	}

	private void getAccessToken(String token) {
		this.mToken = token;
		Log.v(TAG, "accesstoken : " + token);
		// new GetInstagramTokenAsyncTask().execute();
	}

	// public class GetInstagramTokenAsyncTask extends AsyncTask<Void, Void,
	// Void> {
	// @Override
	// protected Void doInBackground(Void... params) {
	// try {
	// URL url = new URL(HttpRequestConstants.STRING_AUTHURL);
	// HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
	// .openConnection();
	// httpsURLConnection.setRequestMethod("POST");
	// httpsURLConnection.setDoInput(true);
	// httpsURLConnection.setDoOutput(true);
	//
	// OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
	// httpsURLConnection.getOutputStream());
	// outputStreamWriter.write("client_id="
	// + TestAppConstants.CLIENT_ID + "&client_secret="
	// + TestAppConstants.CLIENT_SECRET
	// + "&grant_type=authorization_code" + "&redirect_uri="
	// + TestAppConstants.CALLBACKURL + "&code=" + mToken);
	//
	// outputStreamWriter.flush();
	//
	// // Response would be a JSON response sent by instagram
	// String response = streamToString(httpsURLConnection
	// .getInputStream());
	// // Log.e("USER Response", response);
	// JSONObject jsonObject = (JSONObject) new JSONTokener(response)
	// .nextValue();
	//
	// // Your access token that you can use to make future request
	// mAccessTokenString = jsonObject.getString("access_token");
	// // Log.e(TAG, mAccessTokenString);
	//
	// // User details like, name, id, tagline, profile pic etc.
	// JSONObject userJsonObject = jsonObject.getJSONObject("user");
	// // Log.e("USER DETAIL", userJsonObject.toString());
	//
	// // User ID
	// String id = userJsonObject.getString("id");
	// // Log.e(TAG, id);
	//
	// // Username
	// String username = userJsonObject.getString("username");
	// // Log.e(TAG, username);
	//
	// // User full name
	// String name = userJsonObject.getString("full_name");
	// // Log.e(TAG, name);
	//
	// // //TO?DO
	// // mSessionStore.saveInstagramSession(mAccessTokenString, id,
	// // username, name);
	// showResponseDialog(name, mAccessTokenString);
	// } catch (Exception e) {
	// mAuthAuthenticationListener
	// .onFail("Failed to get access token");
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void result) {
	// dismissDialog();
	// mAuthAuthenticationListener.onSuccess();
	// super.onPostExecute(result);
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// showDialog("Getting Access Token..");
	// super.onPreExecute();
	// }
	//
	// }
	//

	//
	// public String streamToString(InputStream is) throws IOException {
	// String string = "";
	//
	// if (is != null) {
	// StringBuilder stringBuilder = new StringBuilder();
	// String line;
	//
	// try {
	// BufferedReader reader = new BufferedReader(
	// new InputStreamReader(is));
	//
	// while ((line = reader.readLine()) != null) {
	// stringBuilder.append(line);
	// }
	//
	// reader.close();
	// } finally {
	// is.close();
	// }
	//
	// string = stringBuilder.toString();
	// }
	//
	// return string;
	// }
	//
	// public void showDialog(String message) {
	// mProgressDialog.setMessage(message);
	// mProgressDialog.show();
	// }
	//
	// public void dismissDialog() {
	// if (mProgressDialog.isShowing()) {
	// mProgressDialog.dismiss();
	// }
	// }
	//
	// public void showResponseDialog(String name, String accessToken) {
	// // Intent broadcastIntent = new Intent();
	// // broadcastIntent.setAction(ResponseListener.ACTION_RESPONSE);
	// // broadcastIntent.putExtra(ResponseListener.EXTRA_NAME, name);
	// // broadcastIntent.putExtra(ResponseListener.EXTRA_ACCESS_TOKEN,
	// // accessToken);
	// // mContext.sendBroadcast(broadcastIntent);
	// }

}
