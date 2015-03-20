package com.amida.easylog;

public class StackUtil {

    public static StackInfo getStackInfo() {
        StackInfo stackInfo = new StackInfo();
        StackTraceElement elements[] = new Throwable().getStackTrace();
        if (elements == null || elements.length < 2) {
            return stackInfo;
        }
        
        
        stackInfo.setClassName(elements[2].getClassName());
        stackInfo.setMethodName(elements[2].getMethodName());
        stackInfo.setLineNumber(elements[2].getLineNumber());
        
        return stackInfo;
    }
}
