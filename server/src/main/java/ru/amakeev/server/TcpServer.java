package ru.amakeev.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.amakeev.common.TcpRequest;
import ru.amakeev.common.TcpResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(TcpServer.class);

    private static final int DEFAULT_PORT = 3434;

    private final Socket socket;

    public static void main(String[] args) {
        LOGGER.info("Start server application");

        int serverPort = DEFAULT_PORT;

        try {
            int i = 0;

            ServerSocket server = new ServerSocket(serverPort);

            LOGGER.info("server is started");

            while (true) {
                new TcpServer(server.accept());
                i++;
            }
        } catch (Exception e) {
            LOGGER.error("init error: ", e);
        }

    }

    public TcpServer(Socket socket) {
        this.socket = socket;

        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    @Override
    public void run() {
        try {

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            TcpRequest request = (TcpRequest) objectInputStream.readObject();

//            objectInputStream.close();

            LOGGER.info("Object from client: " + request);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            TcpResponse response = new TcpResponse();
            response.setString("ТЕСТОВЫЙ ОТВЕТ");

            outputStream.writeObject(response);

//            outputStream.close();

            socket.close();

        }
        catch(Exception e) {
            LOGGER.error("Error read from input stream", e);
        }
    }
}