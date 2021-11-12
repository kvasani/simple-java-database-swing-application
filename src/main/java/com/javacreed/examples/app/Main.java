package com.javacreed.examples.app;

import com.googlecode.flyway.core.Flyway;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        //init dbhelper
        DbHelper.getInstance().init();
        DbHelper.getInstance().registerShutdownHook();

        // launch GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main.LOGGER.debug("Starting application");
                Application application = new Application();

                application.setTitle("Simple Java Database Swing Application");
                application.setSize(800,600);
                application.setLocationRelativeTo(null);
                application.setDefaultCloseOperation(Application.EXIT_ON_CLOSE);
                application.setVisible(true);

//                // close database connection at time of exiting application
//                // COMMENTED AS SHUTDOWN HOOK IS EMPLOYED NOW
//                application.addWindowListener(new WindowAdapter() {
//                    @Override
//                    public void windowClosing(WindowEvent e) {
//                        super.windowClosing(e);
//                        Main.LOGGER.debug("Window is closing, hence closing Datasource");
//                        DbHelper.getInstance().close();
//                    }
//                });
            }
        });

//        try {
//            Contact contact = new Contact();
//            contact.setName("first name");
//            contact.setContacts("email@emaildomain.com");
//            contact.save();
//
//            //list all contacts from database
//            List<Contact> contactList = ContactsHelper.getInstance().getContacts();
//
//            LOGGER.debug("count of contacts="+contactList.size());
//            for (Contact c : contactList) {
//                LOGGER.debug(" >> [{}] {} ({})", c.getId(), c.getName(), c.getContacts());
//            }
//        } catch (final SQLException e) {
//            LOGGER.error("failed to process contacts", e);
//        }
//
//        // cleanup dbhelper
//        DbHelper.getInstance().close();
//        LOGGER.info("done");
    }
}
