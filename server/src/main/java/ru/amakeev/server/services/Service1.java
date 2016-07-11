package ru.amakeev.server.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class Service1 {

    private static final Logger LOGGER = LogManager.getLogger(Service1.class);

    public void sleep(Long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }

    public Date getCurrentDate() {
        return new Date();
    }
}
