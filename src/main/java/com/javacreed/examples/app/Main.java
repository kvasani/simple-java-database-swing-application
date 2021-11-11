package com.javacreed.examples.app;

import com.googlecode.flyway.core.Flyway;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        //init dbhelper
        DbHelper.getInstance().init();

        try {
            Contact contact = new Contact();
            contact.setName("first name");
            contact.setContacts("email@emaildomain.com");
            contact.save();

            //list all contacts from database
            List<Contact> contactList = ContactsHelper.getInstance().getContacts();

            LOGGER.debug("count of contacts="+contactList.size());
            for (Contact c : contactList) {
                LOGGER.debug(" >> [{}] {} ({})", c.getId(), c.getName(), c.getContacts());
            }
        } catch (final SQLException e) {
            LOGGER.error("failed to process contacts", e);
        }

        // cleanup dbhelper
        DbHelper.getInstance().close();
        LOGGER.info("done");
    }
}
