package com.agyp.testapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agyp.utils.TestAppConstants;

public class LoginDialog extends Dialog {

	private static final FrameLayout.LayoutParams FILL_LAYOUT_PARAMS = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT);
	static final int margin = 4;
	static final int padding = 2;

	// static final float[] LANDSCAPE = { 460, 260 };
	// static final float[] PORTRAIT = { 380, 460 };

	static final float[] LANDSCAPE = { 460, 260 };
	static final float[] PORTRAIT = { 300, 400 };
	public static final String TAG = LoginDialog.class.getSimpleName();

	private ProgressDialog mProgressDialog;
	private LinearLayout linearLayout;
	private TextView textView;
	private WebView webView;

	private String authUrl;
	private AuthDialogListener mAuthDialogListener;
	private boolean isShowing = false;

	public LoginDialog(Context context, String url, AuthDialogListener listener) {
		super(context);
		this.authUrl = url;
		this.mAuthDialogListener = listener;
	}

	public void showDialog(String message) {
		if (mProgressDialog == null) {
			return;
		}
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
	}

	public void dismissDialog() {
		if (mProgressDialog == null) {
			return;
		}
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		mProgressDialog = new ProgressDialog(getContext());
		mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		textView = new TextView(getContext());
		textView.setText("Instagram Login");
		textView.setTextColor(Color.WHITE);
		textView.setTypeface(Typeface.DEFAULT_BOLD);
		textView.setBackgroundColor(Color.BLACK);
		textView.setPadding(margin + padding, margin, margin, margin);
		linearLayout.addView(textView);

		webView = new WebView(getContext());
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		webView.setWebViewClient(new AuthWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSavePassword(false);
		webView.loadUrl(authUrl);
		webView.setLayoutParams(FILL_LAYOUT_PARAMS);
		linearLayout.addView(webView);

		float scale = getContext().getResources().getDisplayMetrics().density;
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		float[] dimensions = (width < height) ? PORTRAIT : LANDSCAPE;

		addContentView(linearLayout, new FrameLayout.LayoutParams(
				(int) (dimensions[0] * scale + 0.5f), (int) (dimensions[1]
						* scale + 0.5f)));

		CookieSyncManager.createInstance(getContext());
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();

	}

	public class AuthWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			if (url.startsWith(TestAppConstants.CALLBACKURL)) {
				System.out.println(url);
				String urls[] = url.split("=");
				mAuthDialogListener.onComplete(urls[1]);
				LoginDialog.this.dismiss();
				return true;
			}
			return false;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			String title = webView.getTitle();
			if (title != null && title.length() > 0) {
				textView.setText(title);
			}
			Log.d(TAG, "On Page Finished URL: " + url);
			Log.e(TAG, "Called");
			LoginDialog.this.dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			if (!isShowing) {
				showDialog("Loading..");
			}

		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			LoginDialog.this.dismiss();
			mAuthDialogListener.onError(description);
		}

	}

	public interface AuthDialogListener {

		public void onComplete(String token);

		public void onError(String error);
	}

	@Override
	public void dismiss() {
		Log.v(TAG, "dialog dismissed........................");
		if (webView != null) {
			webView.stopLoading();
		}
		super.dismiss();
	}
}
