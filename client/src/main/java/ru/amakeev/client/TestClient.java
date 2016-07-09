package ru.amakeev.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestClient {
    private static Logger LOGGER = LogManager.getLogger(TestClient.class);


    public static void main(String args[]) {
        LOGGER.info("Start client application");

        Client client = new Client("localhost", 3434);

        for(int i=0;i<10;i++) {
            new Thread(new Caller(client)).start();
        }
    }


}
