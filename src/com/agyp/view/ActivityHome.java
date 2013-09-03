package com.agyp.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.agyp.testapp.Authentication;
import com.agyp.testapp.LoginDialog.AuthDialogListener;

public class ActivityHome extends Activity implements
		android.view.View.OnClickListener {

	Authentication mAuthentication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Button loginBtn = (Button) findViewById(R.id.loginbtn);
		loginBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		mAuthentication = new Authentication(ActivityHome.this,
				new ClientAuthDialogListener());

	}

	// public class AuthListener implements AuthAuthenticationListener {
	//
	// @Override
	// public void onSuccess() {
	// Toast.makeText(ActivityHome.this,
	// "Instagram Authorization Successful", Toast.LENGTH_SHORT)
	// .show();
	// }
	//
	// @Override
	// public void onFail(String error) {
	// Toast.makeText(ActivityHome.this, "Authorization Failed",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// }

	private static final String TAG = ActivityHome.class.getSimpleName();

	public class ClientAuthDialogListener implements AuthDialogListener {

		@Override
		public void onComplete(String token) {
			Log.v(TAG, "ajay accesstoken : " + token);
		}

		@Override
		public void onError(String error) {
			Log.v(TAG, "error: " + error);
		}

	}

}
