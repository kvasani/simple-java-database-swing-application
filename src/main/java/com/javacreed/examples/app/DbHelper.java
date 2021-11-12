package com.javacreed.examples.app;

import com.googlecode.flyway.core.Flyway;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbHelper.class);
    private static final DbHelper INSTANCE = new DbHelper();
    private BasicDataSource ds;

    public static DbHelper getInstance(){
        return INSTANCE;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getDataSource().getConnection();
    }

    public DataSource getDataSource() {
        return ds;
    }
    public void init() {
        LOGGER.debug("Creating the datasource");
        ds = new BasicDataSource();

        // TODO read parameters from properties file
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:db/test-db");
        ds.setUsername("sa");
        ds.setPassword("");

        LOGGER.debug("Performing Database migration");
        // setup flyway and allow it to update database to latest version
        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        flyway.migrate();
    }

    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                DbHelper.LOGGER.debug("Inside Shutdown hook, closing datasource");
                close();
            }
        }));
    }

    public void close() {
        if (ds != null) {
            LOGGER.debug("Closing datasource");
            try {
                ds.close();
            } catch (SQLException e) {
                LOGGER.debug("Exception while closing connection");
            }
            ds = null;
        }
    }
    private DbHelper() {

    }
}
