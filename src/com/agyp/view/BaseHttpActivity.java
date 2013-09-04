package com.agyp.view;

import android.app.Activity;
import android.os.Bundle;

import com.agyp.http.HttpCallbackListener;
import com.agyp.http.NetworkCallExecutorTask;

public abstract class BaseHttpActivity extends Activity implements
		HttpCallbackListener {
	private final String TAG = BaseHttpActivity.class.getSimpleName();
	NetworkCallExecutorTask mNetworkTask;

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
		mNetworkTask = null;
	}

	protected void onExecute(final Bundle requestData,
			final HttpCallbackListener listener) {
		mNetworkTask = new NetworkCallExecutorTask(requestData,
				BaseHttpActivity.this, listener);
		mNetworkTask.execute();
	}

	protected void showProgressDialog(String msg) {
		if (mNetworkTask != null)
			mNetworkTask.showDialog(msg);
	}

	protected void dismissProgressDialog() {
		if (mNetworkTask != null)
			mNetworkTask.dismissDialog();
	}

	protected void unregisterListener() {
		if (mNetworkTask != null)
			mNetworkTask.unregisterCallbackListener();
	}
}
