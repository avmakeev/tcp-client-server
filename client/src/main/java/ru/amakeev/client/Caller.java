package ru.amakeev.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Caller implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Caller.class);

    private Client client;

    public Caller(Client c) {
        this.client = c;
    }

    public void run() {

        while(true) {
            Object response = client.remoteCall("service1", "sleep", new Object[]{new Long(1000)});

            LOGGER.info("Current Date is:" + response);
        }

    }
}
