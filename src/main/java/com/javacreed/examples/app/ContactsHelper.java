package com.javacreed.examples.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactsHelper {

    private static final ContactsHelper INSTANCE = new ContactsHelper();

    public static ContactsHelper getInstance(){
        return INSTANCE;
    }

    private ContactsHelper() {

    }

    public List<Contact> getContacts() throws SQLException {

        List<Contact> contacts = new ArrayList<>();

        String sql = "SELECT * FROM CONTACTS ORDER BY ID";
        try(Connection con = DbHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getLong("id"));
                contact.setName(rs.getString("name"));
                contact.setContacts(rs.getString("contacts"));

                contacts.add(contact);

            }

        }

        return contacts;
    }
}
