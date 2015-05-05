package com.echances.etouches.webservices;

import java.util.HashMap;

import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;

import com.echances.etouches.model.LoginResponse;
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
public class  LoginRequest extends RetrofitSpiceRequest<LoginResponse, LoginRequestData>
{
    private HashMap<String, String>    params;

    public LoginRequest(HashMap<String, String> params2) {
        super(LoginResponse.class, LoginRequestData.class);
        params=params2;
    }

    @Override
    public LoginResponse loadDataFromNetwork ()
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
        @GET (ParamsWebService.LOGIN)
        LoginResponse getLogin(@QueryMap HashMap<String, String> params);
    }

}