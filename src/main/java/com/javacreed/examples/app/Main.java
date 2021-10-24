package com.javacreed.examples.app;

import com.googlecode.flyway.core.Flyway;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:db/test-db");
        ds.setUsername("sa");
        ds.setPassword("");

        try {
            Flyway flyway = new Flyway();
            flyway.setDataSource(ds);
            flyway.migrate();

            try(Connection connection = ds.getConnection(); Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT INTO contacts (name, contacts) values ( 'first name','email@email.com' )");
                System.out.println("reading from Contacts table");

                try (ResultSet rs = statement.executeQuery("select * from contacts")) {
                    while (rs.next()){
                        System.out.printf(" >> [%d] %s (%s)%n", rs.getInt("id"), rs.getString("name"), rs.getString("contacts"));

                    }
                }
            }
        } finally {
            ds.close();
        }

    }
}
