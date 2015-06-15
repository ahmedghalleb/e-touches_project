package com.echances.etouches.webservices;

import java.util.HashMap;

import retrofit.http.GET;
import retrofit.http.QueryMap;

import com.echances.etouches.model.GetOneServiceResponse;
import com.echances.etouches.model.Response;
import com.echances.etouches.utilities.Constants.ParamsWebService;
import com.echances.etouches.webservices.RemoveImageRequest.GetServicesRequestData;
import com.proxymit.robospice.retrofit.request.RetrofitSpiceRequest;

/**
 * 
 * @file GetServicesRequest.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @date  26/04/2015
 * @brief * class to request login web services.
 * @details *
 * 
 */
public class  RemoveImageRequest extends RetrofitSpiceRequest<Response, GetServicesRequestData>
{
    private HashMap<String, String>    params;

    public RemoveImageRequest(HashMap<String, String> params2) {
        super(Response.class, GetServicesRequestData.class);
        params=params2;
    }

    @Override
    public Response loadDataFromNetwork ()
    {
        return getService().addServices(params);
    }

    /**
     * 
     * @file GetServicesRequestData.java
     * @author Ahmed Ghalleb
     * @version 1.0
     * @date  26/04/2015
     * @brief * interface of GetServices web services call.
     * @details *
     * 
     */
    public interface GetServicesRequestData
    {
        @GET (ParamsWebService.DELETE_IMAGE)
        Response addServices(@QueryMap HashMap<String, String> params);
    }

}