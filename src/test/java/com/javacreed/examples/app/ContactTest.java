package com.javacreed.examples.app;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

        }
    }
}