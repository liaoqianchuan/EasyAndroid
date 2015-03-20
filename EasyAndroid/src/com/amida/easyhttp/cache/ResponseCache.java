package com.amida.easyhttp.cache;

import com.amida.easydb.DbTable;
import com.amida.easydb.annotaions.Column;

public class ResponseCache extends DbTable{
    public static final String COLUMN_TYPE= "Type";
    @Column(name = COLUMN_TYPE)
    private String mType;
    @Column(name = "Response")
    private String mResponse;
    @Column(name = "UpdateDate")
    private long mUpdateDate;
    
    public String getType() {
        return mType;
    }
    public void setType(String type) {
        mType = type;
    }
    public String getResponse() {
        return mResponse;
    }
    public void setResponse(String response) {
        mResponse = response;
    }
    public long getUpdateDate() {
        return mUpdateDate;
    }
    public void setUpdateDate(long updateDate) {
        mUpdateDate = updateDate;
    }
    
}
