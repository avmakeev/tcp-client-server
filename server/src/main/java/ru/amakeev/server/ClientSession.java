package ru.amakeev.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.amakeev.common.Command;
import ru.amakeev.common.Response;
import ru.amakeev.server.services.ServiceHandler;

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

                Response response = ServiceHandler.getInstance().callMethod(command);

                sendResponse(response);

            }

        } catch(SocketException e) {
            LOGGER.info("Client error", e);

            try {
                socket.close();
            } catch (IOException ioEx) {
                LOGGER.error("cant close socket", ioEx);
            }
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }
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
