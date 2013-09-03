package com.agyp.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.agyp.testapp.Authentication;
import com.agyp.testapp.Authentication.AuthAuthenticationListener;

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
				new AuthListener());

	}

	public class AuthListener implements AuthAuthenticationListener {

		@Override
		public void onSuccess() {
			Toast.makeText(ActivityHome.this,
					"Instagram Authorization Successful", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onFail(String error) {
			Toast.makeText(ActivityHome.this, "Authorization Failed",
					Toast.LENGTH_SHORT).show();
		}

	}

}
