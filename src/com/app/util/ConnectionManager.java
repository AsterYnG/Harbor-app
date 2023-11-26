package com.app.util;

import com.app.dao.FreighterDao;
import com.app.exceptions.FailedToConnectDataBaseException;
import com.app.exceptions.FailedToRegisterDriverException;
import lombok.experimental.UtilityClass;
import org.postgresql.Driver;


import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@UtilityClass
public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.poolsize";



    static {
        loadDriver();

    }

    private static void loadDriver() {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new FailedToRegisterDriverException(e.getMessage());
        }
    }



    private static Connection open() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY), PropertiesUtil.get(USERNAME_KEY), PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new FailedToConnectDataBaseException(e);
        }
    }

    public static Connection get() {
            return open();
    }


}
