package com.amida.easyhttp;

public interface IService {
    void setNextService(IService service);
    IService getNextService();
}
