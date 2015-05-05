package com.echances.etouches.webservices;

import java.util.HashMap;

import retrofit.http.GET;
import retrofit.http.QueryMap;

import com.echances.etouches.model.GetServicesResponse;
import com.echances.etouches.utilities.Constants.ParamsWebService;
import com.echances.etouches.webservices.GetServicesRequest.GetServicesRequestData;
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
public class  GetServicesRequest extends RetrofitSpiceRequest<GetServicesResponse, GetServicesRequestData>
{
    private HashMap<String, String>    params;

    public GetServicesRequest(HashMap<String, String> params2) {
        super(GetServicesResponse.class, GetServicesRequestData.class);
        params=params2;
    }

    @Override
    public GetServicesResponse loadDataFromNetwork ()
    {
        return getService().getServices();
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
        @GET (ParamsWebService.GETSERVICES)
        GetServicesResponse getServices();
    }

}