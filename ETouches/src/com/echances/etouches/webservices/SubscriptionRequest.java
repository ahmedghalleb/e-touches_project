package com.echances.etouches.webservices;

import java.util.HashMap;

import retrofit.http.EncodedQuery;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;

import com.echances.etouches.model.Response;
import com.echances.etouches.utilities.Constants.ParamsWebService;
import com.echances.etouches.webservices.SubscriptionRequest.SubscriptionRequestData;
import com.proxymit.robospice.retrofit.request.RetrofitSpiceRequest;
/**
 * 
 * @file SubscriptionRequest.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @date  26/04/2015
 * @brief * class to request login web services.
 * @details *
 * 
 */
public class  SubscriptionRequest extends RetrofitSpiceRequest<Response, SubscriptionRequestData>
{
    private HashMap<String, String>    params;

    public SubscriptionRequest(HashMap<String, String> params2) {
        super(Response.class, SubscriptionRequestData.class);
        params=params2;
    }

    @Override
    public Response loadDataFromNetwork ()
    {
        return getService().subscription(params);
    }

    /**
     * 
     * @file SubscriptionRequestData.java
     * @author Ahmed Ghalleb
     * @version 1.0
     * @date  26/04/2015
     * @brief * interface of Login web services call.
     * @details *
     * 
     */
    public interface SubscriptionRequestData
    {
    	@GET (ParamsWebService.SUBSCRIPTION)
        Response subscription(@QueryMap HashMap<String, String> params);
    	
    }

}