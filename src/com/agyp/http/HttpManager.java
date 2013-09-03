package com.agyp.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * This class executes the Http request and sends the response.
 * 
 * 
 */

public final class HttpManager {

	protected static final String TAG = HttpManager.class.getSimpleName();
	
	/**
	 * DefaultHttpClient Object.
	 */
	private static DefaultHttpClient mHttpClient;

	static {
		final HttpParams params = new BasicHttpParams();

		ConnManagerParams.setTimeout(params, 60 * 1000);
		ConnManagerParams.setMaxConnectionsPerRoute(params,
				new ConnPerRouteBean(25));
		ConnManagerParams.setMaxTotalConnections(params,
				25);

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");

		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
		HttpConnectionParams.setSoTimeout(params, 60 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		
		HttpClientParams.setRedirecting(params, false);
		HttpProtocolParams.setUserAgent(params, "TestApp");
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		ClientConnectionManager manager = new ThreadSafeClientConnManager(
				params, schemeRegistry);
		mHttpClient = new DefaultHttpClient(manager, params);
	}

	public static HttpResponse execute(HttpRequestBase request)
			throws IOException {
		if (mHttpClient == null) {
			return null;
		} else {
			return mHttpClient.execute(request);
		}

	}

	public static void clear() {
		if (mHttpClient != null) {
			mHttpClient = null;
		}
	}
}