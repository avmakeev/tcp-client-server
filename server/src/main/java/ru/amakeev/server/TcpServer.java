package ru.amakeev.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;

public class TcpServer {

    private static final Logger LOGGER = LogManager.getLogger(TcpServer.class);

    private static final int DEFAULT_PORT = 3434;

    public static void main(String[] args) {
        LOGGER.info("Start server application");

        //        ExecutorService service = Executors.newFixedThreadPool();


        int serverPort = DEFAULT_PORT;

        try {
            ServerSocket server = new ServerSocket(serverPort);

            while (true) {
                new ClientSession(server.accept());

                LOGGER.info("join new client");
            }
        } catch (Exception e) {
            LOGGER.error("init error: ", e);
        }

    }

}