package com.proxymit.robospice.retrofit.request;

import com.octo.android.robospice.request.SpiceRequest;
import com.squareup.okhttp.OkHttpClient;

public abstract class OkHttpSpiceRequest<T> extends SpiceRequest<T>
{
	private OkHttpClient	okHttpClient;

	public OkHttpSpiceRequest(Class<T> clazz)
	{
		super(clazz);
	}

	public void setOkHttpClient (OkHttpClient okHttpClient)
	{
		this.okHttpClient = okHttpClient;
	}

	public OkHttpClient getOkHttpClient ()
	{
		return okHttpClient;
	}

}
