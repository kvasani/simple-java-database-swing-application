package com.javacreed.examples.app;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class ContactsHelperTest {

    @Before
    public void setUp() throws Exception {
        DbHelper.getInstance().init();

        try (Connection connection = DbHelper.getConnection(); Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE contacts");
            stmt.execute("ALTER TABLE contacts ALTER COLUMN id RESTART WITH 1");
        }
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.getInstance().close();
    }

    @Test
    public void testGetContacts() throws SQLException {

        List<Contact> contacts = ContactsHelper.getInstance().getContacts();
        Assert.assertNotNull(contacts);
        Assert.assertTrue(contacts.isEmpty());

        try(Connection con = DbHelper.getConnection(); Statement stmt = con.createStatement()) {
            stmt.execute("INSERT INTO CONTACTS (NAME, CONTACTS) VALUES ('CONTACT-NAME1','CONTACT-1@EMAILSERVER.COM')");
            stmt.execute("INSERT INTO CONTACTS (NAME, CONTACTS) VALUES ('CONTACT-NAME2','CONTACT-2@EMAILSERVER.COM')");
            stmt.execute("INSERT INTO CONTACTS (NAME, CONTACTS) VALUES ('CONTACT-NAME3','CONTACT-3@EMAILSERVER.COM')");
            con.commit();

            contacts = ContactsHelper.getInstance().getContacts();
            Assert.assertNotNull(contacts);
            Assert.assertEquals(3L, contacts.size());

            Contact contact = contacts.get(0);
            Assert.assertNotNull(contact);
            Assert.assertEquals(1L, contact.getId());
            Assert.assertEquals("CONTACT-NAME1", contact.getName());
            Assert.assertEquals("CONTACT-1@EMAILSERVER.COM", contact.getContacts());

            contact = contacts.get(1);
            Assert.assertNotNull(contact);
            Assert.assertEquals(2L, contact.getId());
            Assert.assertEquals("CONTACT-NAME2", contact.getName());
            Assert.assertEquals("CONTACT-2@EMAILSERVER.COM", contact.getContacts());

            contact = contacts.get(2);
            Assert.assertNotNull(contact);
            Assert.assertEquals(3L, contact.getId());
            Assert.assertEquals("CONTACT-NAME3", contact.getName());
            Assert.assertEquals("CONTACT-3@EMAILSERVER.COM", contact.getContacts());


        }
    }
}