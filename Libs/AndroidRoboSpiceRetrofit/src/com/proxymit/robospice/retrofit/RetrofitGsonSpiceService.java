package com.proxymit.robospice.retrofit;

import java.io.File;

import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.proxymit.robospice.retrofit.persistence.GsonRetrofitObjectPersisterFactory;

/**
 * A pre-set, easy to use, retrofit service. It will use retrofit for network
 * requests and both networking and caching will use Gson. To use it, just add
 * to your manifest :
 * 
 * <pre>
 * &lt;service
 *   android:name="com.octo.android.robospice.retrofit.RetrofitGsonSpiceService"
 *   android:exported="false" /&gt;
 * </pre>
 * 
 * @author SNI
 */
public abstract class RetrofitGsonSpiceService extends RetrofitSpiceService
{

	@Override
	public CacheManager createCacheManager (Application application) throws CacheCreationException
	{
		CacheManager cacheManager = new CacheManager();
		cacheManager.addPersister(new GsonRetrofitObjectPersisterFactory(application, getCacheFolder()));
		return cacheManager;
	}

	@Override
	protected Converter createConverter ()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.enableComplexMapKeySerialization();
		gsonBuilder.serializeNulls();
		gsonBuilder.serializeSpecialFloatingPointValues();
		Gson gson = gsonBuilder.create();
		return new GsonConverter(gson);
	}

	public File getCacheFolder ()
	{
		return null;
	}
}
