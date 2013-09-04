package com.agyp.view;

import android.app.Activity;
import android.os.Bundle;

import com.agyp.http.HttpCallbackListener;
import com.agyp.http.NetworkCallExecutorTask;

public abstract class BaseHttpActivity extends Activity implements
		HttpCallbackListener {
	private final String TAG = BaseHttpActivity.class.getSimpleName();

	protected void onExecute(final Bundle requestData) {
		new NetworkCallExecutorTask(requestData, BaseHttpActivity.this)
				.execute();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
