
package com.example.textviewfinder.service;

import java.io.File;

import com.amida.easyhttp.IService;
import com.amida.easyhttp.ResponseListener;
import com.amida.easyhttp.annotations.EasyHttp;
import com.amida.easyhttp.annotations.EasyHttp.ApiType;
import com.example.textviewfinder.Constants;
import com.example.textviewfinder.response.ResponseIpInfo;


public interface TestService extends IService {
    @EasyHttp(
            url = Constants.API_IPINFO,
            dummyData = DummyData.DUMMY_IPINFO
            )
     void getIpInfo(Object request, ResponseListener<ResponseIpInfo> responseListener);
   
    
    @EasyHttp(
            apiType=ApiType.DOWNLOADFILE,
            url = Constants.API_DOWNPIC,
            dummyData=DummyData.DUMMY_PIC
            )
    void downloadPic(Object request, ResponseListener<File> responseListener, File file);
}
