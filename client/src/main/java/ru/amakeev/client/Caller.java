package ru.amakeev.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.amakeev.common.Response;

public class Caller implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Caller.class);

    private Client client;

    public Caller(Client client) {
        this.client = client;
    }

    public void run() {

        while(true) {
            client.remoteCall("service1", "sleep", new Object[]{new Long(1000)});

            LOGGER.info("Current Date is:" + client.remoteCall("service1", "getCurrentDate", new Object[]{}));
        }

    }
}
