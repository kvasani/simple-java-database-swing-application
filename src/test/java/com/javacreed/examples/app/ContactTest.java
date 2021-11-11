package com.javacreed.examples.app;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class ContactTest {

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
    public void testSave() throws SQLException {

        Contact contact = new Contact();
        contact.setName("first name");
        contact.setContacts("email@emaildomain.com");
        contact.save();

        try (Connection connection = DbHelper.getConnection(); Statement stmt = connection.createStatement()) {
            try (ResultSet rs=stmt.executeQuery("SELECT COUNT(*) FROM contacts")) {
                Assert.assertTrue("Count should return at least one row",rs.next());
                Assert.assertEquals(1L, rs.getLong(1));
                Assert.assertFalse("Count should not return more than one row",rs.next());
            }
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM contacts")) {
                Assert.assertTrue("Count should return at least one row",rs.next());
                Assert.assertEquals(1L, rs.getLong("id"));
                Assert.assertEquals("first name", rs.getString("name"));
                Assert.assertEquals("email@emaildomain.com", rs.getString("contacts"));
                Assert.assertFalse("Count should not return more than one row",rs.next());
            }

            contact.setName("updated-name");
            contact.save();

            Assert.assertEquals(1L, contact.getId());
            Assert.assertEquals("updated-name", contact.getName());
            Assert.assertEquals("email@emaildomain.com", contact.getContacts());

            final List<Contact> contacts = ContactsHelper.getInstance().getContacts();
            Assert.assertEquals(1, contacts.size());

            final Contact c = contacts.get(0);
            Assert.assertNotNull(c);
            Assert.assertEquals(1L, c.getId());
            Assert.assertEquals("updated-name", c.getName());
            Assert.assertEquals("email@emaildomain.com", c.getContacts());
        }
    }

    @Test
    public void testDelete() throws SQLException {
        try (Connection con = DbHelper.getConnection(); Statement stmt = con.createStatement()) {
            stmt.execute("INSERT INTO CONTACTS (NAME, CONTACTS) VALUES ('CONTACT-NAME1','CONTACT-1@EMAILSERVER.COM')");
            stmt.execute("INSERT INTO CONTACTS (NAME, CONTACTS) VALUES ('CONTACT-NAME2','CONTACT-2@EMAILSERVER.COM')");
            stmt.execute("INSERT INTO CONTACTS (NAME, CONTACTS) VALUES ('CONTACT-NAME3','CONTACT-3@EMAILSERVER.COM')");
            con.commit();

            List<Contact> contacts = ContactsHelper.getInstance().getContacts();
            Assert.assertEquals(3, contacts.size());

            final Contact contact = contacts.get(1);
            Assert.assertNotEquals(-1, contact.getId());

            contact.delete();
            Assert.assertEquals(-1,contact.getId());

            contacts = ContactsHelper.getInstance().getContacts();
            Assert.assertEquals(2, contacts.size());
            Assert.assertEquals(1L, contacts.get(0).getId());
            Assert.assertEquals(3L, contacts.get(1).getId());
        }
    }
}