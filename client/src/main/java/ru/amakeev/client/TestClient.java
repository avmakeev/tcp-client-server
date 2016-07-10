package ru.amakeev.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class TestClient {
    private static Logger LOGGER = LogManager.getLogger(TestClient.class);


    public static void main(String args[]) {
        LOGGER.info("Start client application");

        Client client = null;
        try {

            client = new Client("localhost", 3434);

        } catch (IOException e) {
            LOGGER.error("Error create client", e);
        }

        for(int i=0; i<10; i++) {
            new Thread(new Caller(client)).start();
        }
    }


}
