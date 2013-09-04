package com.agyp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.agyp.testapp.Authentication;
import com.agyp.testapp.LoginDialog.AuthDialogListener;

public class ActivityHome extends BaseHttpActivity implements
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

	private static final String TAG = ActivityHome.class.getSimpleName();

	public class ClientAuthDialogListener implements AuthDialogListener {

		@Override
		public void onComplete(String token) {
			Log.v(TAG, "ajay accesstoken : " + token);
			mAuthentication.dismissLoginDialog();

			Bundle requestData = new Bundle();
			requestData.putString("token", token);

			onExecute(requestData);
		}

		@Override
		public void onError(String error) {
			Log.v(TAG, "error: " + error);
			mAuthentication.dismissLoginDialog();
		}

	}

	@Override
	public void onRequestComplete() {

	}

	@Override
	public void onError() {
	}

}
