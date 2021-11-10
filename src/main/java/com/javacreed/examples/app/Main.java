package com.javacreed.examples.app;

import com.googlecode.flyway.core.Flyway;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        //init dbhelper
        DbHelper.getInstance().init();

        Contact contact = new Contact();
        contact.setName("first name");
        contact.setContacts("email@emaildomain.com");
        contact.save();

        //list all contacts from database
        try(Connection con = DbHelper.getConnection(); Statement stmt = con.createStatement()){
            try(ResultSet rs = stmt.executeQuery("SELECT * FROM contacts")) {
                while (rs.next()) {
                    LOGGER.debug(" >> [{}] {} ({})", new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("contacts")
                    });
                }
            }
        }

        // cleanup dbhelper
        DbHelper.getInstance().close();
        LOGGER.info("done");
    }
}
