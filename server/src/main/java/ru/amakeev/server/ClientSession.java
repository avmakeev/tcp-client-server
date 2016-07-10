package ru.amakeev.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.amakeev.common.Command;
import ru.amakeev.common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientSession extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(ClientSession.class);

    private final Socket socket;

    public ClientSession(Socket socket) {
        this.socket = socket;

        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    @Override
    public void run() {
        try {

            while (true) {

                Command command = getCommand();

                LOGGER.info("Object from client: " + command);

                sleep(5000);

                sendResponse(createTestResponse(command));

            }

        } catch(SocketException e) {
            LOGGER.info("Client error", e);

            try {
                socket.close();
            } catch (IOException ioEx) {
                LOGGER.error("cant close socket", ioEx);
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }
    }

    private Response createTestResponse(Command command) {
        Response response = new Response();
        response.setSerialNumber(command.getSerialNumber());
        response.setResult("ТЕСТОВЫЙ ОТВЕТ");
        return response;
    }

    private void sendResponse(Response response) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(response);
    }

    private Command getCommand() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        return (Command) objectInputStream.readObject();
    }
}
