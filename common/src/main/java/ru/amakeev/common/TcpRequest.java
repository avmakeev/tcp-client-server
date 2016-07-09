package ru.amakeev.common;

import java.io.Serializable;
import java.util.Arrays;

public class TcpRequest implements Serializable {


    private final String serviceName;
    private final String methodName;
    private final Object[] params;

    public TcpRequest(String serviceName, String methodName, Object[] params) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.params = params;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "TcpRequest{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
