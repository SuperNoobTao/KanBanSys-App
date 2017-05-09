package com.sorcererxw.demo.gridwebview;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @description:
 * @author: SuperNoobTao
 * @date: 2017/3/5
 */

public class WebClient {
    private static WebClient sClient;

    public static WebClient getInstance() {
        if (sClient == null) {
            sClient = new WebClient();
        }
        return sClient;
    }

    private WebService mService;

    //    http://16l6861v93.imwork.net:18370/device/111
    private WebClient() {
        mService = new Retrofit.Builder()
                .baseUrl("http://16l6861v93.imwork.net:18370/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WebService.class);
    }

    public Observable<WebData> getUrls(String deviceId) {
        return mService.getUrls(deviceId);
    }
}
