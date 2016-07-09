package ru.amakeev.common;

import java.io.Serializable;

/**
 * Created by alexey on 09.07.16.
 */
public class TcpResponse implements Serializable {

    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "TcpResponse{" +
                "string='" + string + '\'' +
                '}';
    }
}
