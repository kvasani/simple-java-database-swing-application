package com.javacreed.examples.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Contact {
    private long id;
    private String name;
    private String contacts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public void save() throws SQLException {
        String sql = "INSERT INTO contacts (name, contacts) VALUES (?, ?)";
        try(Connection connection = DbHelper.getConnection(); PreparedStatement pStmt = connection.prepareStatement(sql)){
            pStmt.setString(1,this.name);
            pStmt.setString(2,this.contacts);

            pStmt.execute();
        }
    }
}
