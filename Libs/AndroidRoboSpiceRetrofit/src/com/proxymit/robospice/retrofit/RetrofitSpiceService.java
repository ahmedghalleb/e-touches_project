package com.proxymit.robospice.retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import android.util.Log;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proxymit.robospice.retrofit.request.RetrofitSpiceRequest;
import com.squareup.okhttp.OkHttpClient;

public abstract class RetrofitSpiceService extends SpiceService
{
	protected abstract String getServerUrl ();

	protected abstract Converter createConverter ();

	private Map<Class<?>, Object>	retrofitInterfaceToServiceMap	= new HashMap<Class<?>, Object>();
	private RestAdapter.Builder		builder;
	private RestAdapter				restAdapter;
	protected List<Class<?>>		retrofitInterfaceList			= new ArrayList<Class<?>>();
	private Converter				mConverter;
	private OkHttpClient			okHttpClient;

	@Override
	public void onCreate ()
	{
		super.onCreate();

		builder = createRestAdapterBuilder();
		// TODO disable LOG
		roboguice.util.temp.Ln.getConfig().setLoggingLevel(Log.VERBOSE);
		builder.setLogLevel(LogLevel.FULL);
		restAdapter = builder.build();
	}

	protected RestAdapter.Builder createRestAdapterBuilder ()
	{
		okHttpClient = new OkHttpClient();
		RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
		restAdapterBuilder.setClient(new OkClient(okHttpClient));
		restAdapterBuilder.setEndpoint(getServerUrl());
		restAdapterBuilder.setConverter(getConverter());
		return restAdapterBuilder;
	}

	protected final Converter getConverter ()
	{
		if (mConverter == null)
			mConverter = createConverter();
		return mConverter;
	}

	@SuppressWarnings ("unchecked")
	protected <T> T getRetrofitService (Class<T> serviceClass)
	{
		T service = (T) retrofitInterfaceToServiceMap.get(serviceClass);
		if (service == null) {
			service = restAdapter.create(serviceClass);
			retrofitInterfaceToServiceMap.put(serviceClass, service);
		}
		return service;
	}

	@SuppressWarnings ({ "rawtypes", "unchecked" })
	@Override
	public void addRequest (CachedSpiceRequest<?> request, Set<RequestListener<?>> listRequestListener)
	{
		if (request.getSpiceRequest() instanceof RetrofitSpiceRequest) {
			RetrofitSpiceRequest retrofitSpiceRequest = (RetrofitSpiceRequest) request.getSpiceRequest();
			retrofitSpiceRequest.setService(getRetrofitService(retrofitSpiceRequest.getRetrofitedInterfaceClass()));
			retrofitSpiceRequest.setOkHttpClient(okHttpClient);
		}
		super.addRequest(request, listRequestListener);
	}

	public final List<Class<?>> getRetrofitInterfaceList ()
	{
		return retrofitInterfaceList;
	}

	protected void addRetrofitInterface (Class<?> serviceClass)
	{
		retrofitInterfaceList.add(serviceClass);
	}
}
