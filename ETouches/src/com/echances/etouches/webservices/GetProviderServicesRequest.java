package com.echances.etouches.webservices;

import java.util.HashMap;

import retrofit.http.GET;
import retrofit.http.QueryMap;

import com.echances.etouches.model.GetOneServiceResponse;
import com.echances.etouches.utilities.Constants.ParamsWebService;
import com.echances.etouches.webservices.GetProviderServicesRequest.GetServicesRequestData;
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
public class  GetProviderServicesRequest extends RetrofitSpiceRequest<GetOneServiceResponse, GetServicesRequestData>
{
    private HashMap<String, String> params;

    public GetProviderServicesRequest(HashMap<String, String> params2) {
        super(GetOneServiceResponse.class, GetServicesRequestData.class);
        params=params2;
    }

    @Override
    public GetOneServiceResponse loadDataFromNetwork ()
    {
        return getService().getServices(params);
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
        @GET (ParamsWebService.GET_PROVIDER_SERVICES)
        GetOneServiceResponse getServices(@QueryMap HashMap<String, String> params);
    }

}