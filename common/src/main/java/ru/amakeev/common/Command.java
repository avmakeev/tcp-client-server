package ru.amakeev.common;

import java.io.Serializable;
import java.util.Arrays;

public class Command implements Serializable {

    private static final long serialVersionUID = 2980894964104901321L;

    private String serialNumber;
    private String serviceName;
    private String methodName;
    private Object[] params;

    public Command() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Command{" +
                "serialNumber='" + serialNumber + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }

}
