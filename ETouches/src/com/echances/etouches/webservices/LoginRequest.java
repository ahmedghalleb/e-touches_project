package com.echances.etouches.webservices;

import java.util.HashMap;

import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

import com.echances.etouches.model.Response;
import com.echances.etouches.utilities.Constants.ParamsWebService;
import com.echances.etouches.webservices.LoginRequest.LoginRequestData;
import com.proxymit.robospice.retrofit.request.RetrofitSpiceRequest;
/**
 * 
 * @file LoginRequest.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @date  26/04/2015
 * @brief * class to request login web services.
 * @details *
 * 
 */
public class  LoginRequest extends RetrofitSpiceRequest<Response, LoginRequestData>
{
    private HashMap<String, String>    params;

    public LoginRequest(HashMap<String, String> params2) {
        super(Response.class, LoginRequestData.class);
        params=params2;
    }

    @Override
    public Response loadDataFromNetwork ()
    {
        return getService().getLogin(params);
    }

    /**
     * 
     * @file LoginRequestData.java
     * @author Ahmed Ghalleb
     * @version 1.0
     * @date  26/04/2015
     * @brief * interface of Login web services call.
     * @details *
     * 
     */
    public interface LoginRequestData
    {
        @FormUrlEncoded
        @POST (ParamsWebService.LOGIN)
        Response getLogin(@FieldMap HashMap<String, String> params);
    }

}