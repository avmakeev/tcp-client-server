package ru.amakeev.common;

import java.io.Serializable;

/**
 * Created by alexey on 09.07.16.
 */
public class Response implements Serializable {

    private String serialNumber;

    private Object result;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "result=" + result +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
