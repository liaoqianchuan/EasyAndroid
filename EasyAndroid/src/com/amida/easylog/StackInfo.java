
package com.amida.easylog;

public class StackInfo {
    private String mClassName;
    private String mMethodName;
    private int mLineNumber;

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        this.mClassName = className;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public void setMethodName(String methodName) {
        mMethodName = methodName;
    }

    public int getLineNumber() {
        return mLineNumber;
    }

    public void setLineNumber(int lineNumber) {
        mLineNumber = lineNumber;
    }
}
