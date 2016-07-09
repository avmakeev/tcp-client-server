package ru.amakeev.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class TestClient {
    private static Logger LOGGER = LogManager.getLogger(TestClient.class);


    public static void main(String args[]) {
        LOGGER.info("Start application");

        Client client = null;


        try {
            client = new Client("localhost", 2323);
        } catch (IOException e) {
            LOGGER.error("Error create client", e);

            System.exit(-1);
        }

        for(int i=0;i<10;i++) {
            new Thread(new Caller(client)).start();
        }
    }


}
