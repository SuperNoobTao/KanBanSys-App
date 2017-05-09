package com.sorcererxw.demo.gridwebview;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @description:
 * @author: SuperNoobTao
 * @date: 2017/3/5
 */

public interface WebService {
    @GET("device/{device}")
    Observable<WebData> getUrls(@Path("device") String deviceId);
}
