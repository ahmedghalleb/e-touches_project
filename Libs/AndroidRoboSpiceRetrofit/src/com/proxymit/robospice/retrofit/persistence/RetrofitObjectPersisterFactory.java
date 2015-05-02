package com.proxymit.robospice.retrofit.persistence;

import java.io.File;
import java.util.List;

import retrofit.converter.Converter;
import android.app.Application;

import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.file.InFileObjectPersister;
import com.octo.android.robospice.persistence.file.InFileObjectPersisterFactory;

public class RetrofitObjectPersisterFactory extends InFileObjectPersisterFactory
{

	// ----------------------------------
	// ATTRIBUTES
	// ----------------------------------
	private Converter	converter;

	// ----------------------------------
	// CONSTRUCTORS
	// ----------------------------------
	public RetrofitObjectPersisterFactory(Application application, Converter converter, File cacheFolder) throws CacheCreationException
	{
		// this(application, converter, null, cacheFolder);
		super(application, cacheFolder);
		this.converter = converter;
	}

	public RetrofitObjectPersisterFactory(Application application, Converter converter, List<Class<?>> listHandledClasses, File cacheFolder) throws CacheCreationException
	{
		super(application, listHandledClasses, cacheFolder);
		this.converter = converter;
	}

	public RetrofitObjectPersisterFactory(Application application, Converter converter) throws CacheCreationException
	{
		// this(application, converter, null, null);
		super(application);
		this.converter = converter;
	}

	public RetrofitObjectPersisterFactory(Application application, Converter converter, List<Class<?>> listHandledClasses) throws CacheCreationException
	{
		// this(application, converter, listHandledClasses, null);
		super(application, listHandledClasses);
		this.converter = converter;
	}

	// ----------------------------------
	// API
	// ----------------------------------
	@Override
	public <DATA> InFileObjectPersister<DATA> createInFileObjectPersister (Class<DATA> clazz, File cacheFolder) throws CacheCreationException
	{
		return new RetrofitObjectPersister<DATA>(getApplication(), converter, clazz, cacheFolder);
	}

}
