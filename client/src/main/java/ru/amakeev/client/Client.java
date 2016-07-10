package ru.amakeev.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.amakeev.common.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Client {

    private static final Logger LOGGER = LogManager.getLogger(Client.class);


    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 3434;

    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    private Socket socket = null;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;

        createSocket();
    }

    private void createSocket() throws IOException {
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            LOGGER.error("Unknown host: " + host);
            throw e;
        } catch (IOException e) {
            LOGGER.error("I/O Error creating socket " + host + ":" + port);
            throw e;
        }
    }

    public Object remoteCall(String serviceName, String methodName, Object[] params) {
        Future<Object> futureResult = executor.submit(() -> {
            try {
                Command command = createCommand(serviceName, methodName, params);

                sendCommand(command);

                return readResponse();

            } catch (IOException e) {
                LOGGER.error("Error send object", e);
            } catch (ClassNotFoundException e) {
                LOGGER.error("Response class not found", e);
            }

                return null;
        });

        try {

            return futureResult.get();

        } catch (InterruptedException e) {
            LOGGER.error(e);
        } catch (ExecutionException e) {
            LOGGER.error(e);
        }

        return null;
    }

    private void sendCommand(Command command) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());

        outputStream.writeObject(command);
    }

    private Object readResponse() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        return objectInputStream.readObject();
    }

    private Command createCommand(String serviceName, String methodName, Object[] params) {
        Command command = new Command();

        command.setSerialNumber(UUID.randomUUID().toString());
        command.setServiceName(serviceName);
        command.setMethodName(methodName);
        command.setParams(params);

        return command;
    }
}
