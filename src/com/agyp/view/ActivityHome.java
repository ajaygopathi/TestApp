package com.agyp.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityHome extends Activity implements
		android.view.View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Button loginBtn = (Button) findViewById(R.id.loginbtn);
		loginBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

	}

}
