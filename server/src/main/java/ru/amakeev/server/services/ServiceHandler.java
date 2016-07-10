package ru.amakeev.server.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.amakeev.common.Command;
import ru.amakeev.common.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceHandler {
    private static final Logger LOGGER = LogManager.getLogger(ServiceHandler.class);

    private static final ServiceHandler INSTANCE = new ServiceHandler();

    private final Properties property = new Properties();
    private final Map<String, Object> servicesDict = new HashMap<>();

    public static ServiceHandler getInstance() {
        return INSTANCE;
    }

    private ServiceHandler() {
        try {

            property.load(getClass().getClassLoader().getResourceAsStream("server.properties"));

        } catch (IOException e) {
            LOGGER.error("Error loading server properties", e);
        }

        loadServices();

        System.out.println(servicesDict);
    }

    private void loadServices() {

        for (String serverName : property.stringPropertyNames()) {

            String serviceClassName = this.property.getProperty(serverName);

            Class<?> clazz = null;
            try {
                clazz = Class.forName(serviceClassName);
                Object service = clazz.newInstance();

                servicesDict.put(serverName, service);

            } catch (ClassNotFoundException e) {
                LOGGER.error("Class with name: " + serviceClassName + " not found", e);
            } catch (InstantiationException e) {
                LOGGER.error(e);
            } catch (IllegalAccessException e) {
                LOGGER.error(e);
            }

        }

    }

    public Response callMethod(Command command) {
        Response response = new Response();
        response.setSerialNumber(command.getSerialNumber());

        Object service = servicesDict.get(command.getServiceName());

        if (service == null) {
            response.setError("Service not found");

            return response;
        }

        Class<?> cls = service.getClass();

        Class params[] = new Class[command.getParams().length];
        for (int i=0; i< command.getParams().length; i++) {
            params[i] = command.getParams()[i].getClass();
        }

        Method method = null;
        try {
            method = cls.getDeclaredMethod(command.getMethodName(), params);
        } catch (NoSuchMethodException e) {
            LOGGER.debug("Method with name: " + command.getMethodName() + " not found", e);

            response.setError("Method not found");

            return response;
        }

        try {

            Object result = method.invoke(service, command.getParams());

            if (result == null) {
                response.setVoid(true);
            } else {
                response.setResult(result);
            }

        } catch (IllegalAccessException e) {
            LOGGER.debug("Method with name: " + command.getMethodName() + " wrong access", e);

            response.setError("Method not found");

            return response;
        } catch (InvocationTargetException e) {
            LOGGER.debug("Method with params: " + command.getParams() + " not found", e);

            response.setError("Method not found");

            return response;
        }

        return response;

    }
}
