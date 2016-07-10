package ru.amakeev.common;

import java.io.Serializable;

/**
 * Created by alexey on 09.07.16.
 */
public class Response implements Serializable {

    private String serialNumber;

    private Object result;

    private String error;

    private Boolean isVoid = false;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getVoid() {
        return isVoid;
    }

    public void setVoid(Boolean aVoid) {
        isVoid = aVoid;
    }

    @Override
    public String toString() {
        return "Response{" +
                "serialNumber='" + serialNumber + '\'' +
                ", result=" + result +
                ", error='" + error + '\'' +
                ", isVoid=" + isVoid +
                '}';
    }

}
