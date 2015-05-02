package com.echances.etouches.webservices;

import com.echances.etouches.utilities.Constants.ParamsWebService;
import com.proxymit.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * @file WebServiceDigiScoolService.java
 * @author Bilel Gaabel
 * @version 1.0
 * @date  11/08/2014
 * @brief * service class that contains all web services set.
 * @details *
 */

public class WebServiceEchancesService extends RetrofitGsonSpiceService
{
    @Override
    public void onCreate ()
    {
        super.onCreate();

//        // Appel au WS login
//        addRetrofitInterface(LoginRequest.class);
//        // Appel au WS Subscription
//        addRetrofitInterface(SubscriptionRequest.class);

        
    }

    @Override
    protected String getServerUrl ()
    {
        return ParamsWebService.SERVER_NAME;
    }

}
