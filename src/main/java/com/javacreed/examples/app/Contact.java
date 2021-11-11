package com.javacreed.examples.app;

import java.sql.*;

public class Contact {
    private long id = -1;
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
        String SQL_INSERT = "INSERT INTO contacts (name, contacts) VALUES (?, ?)";
        String SQL_UPDATE = "UPDATE CONTACTS set name = ?, contacts = ? where ID = ?";

        // save new contact
        if (id == -1) {
            try(Connection connection = DbHelper.getConnection();
                PreparedStatement pStmt = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pStmt.setString(1,this.name);
                pStmt.setString(2,this.contacts);

                pStmt.execute();

                try (ResultSet rs = pStmt.getGeneratedKeys()) {
                    rs.next();
                    id = rs.getLong(1);
                }
            }
        } else {
            try(Connection connection = DbHelper.getConnection();
                PreparedStatement pStmt = connection.prepareStatement(SQL_UPDATE)) {
                pStmt.setString(1,this.name);
                pStmt.setString(2,this.contacts);
                pStmt.setLong(3, this.id);
                pStmt.execute();
            }
        }
    }

    public void delete() throws SQLException {
        final String SQL = "DELETE FROM CONTACTS WHERE ID = ?";

        // ensure we are deleting record from database only if valid ID is present
        if (id != -1) {
            try (Connection connection = DbHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(SQL)) {
                pstmt.setLong(1, this.id);
                pstmt.execute();

                // reset ID to indicate current object content is not in database
                id = -1;
            }
        }
    }
}
