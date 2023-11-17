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
    public static BlockingQueue<Connection> pool;
    public static List<Connection> sourcePool;



    static {
        loadDriver();
        initConnectionPool();

    }

    private static void loadDriver() {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new FailedToRegisterDriverException(e.getMessage());
        }
    }

    private static void initConnectionPool() {
        var size = Integer.valueOf(PropertiesUtil.get(POOL_SIZE_KEY));
        pool = new ArrayBlockingQueue<Connection>(size);
        sourcePool = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            var connection = open();
            sourcePool.add(connection);
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class}, (proxy, method, args) ->
                    method.getName().equals("close") ? pool.add((Connection) proxy) : method.invoke(connection, args));
            pool.add(proxyConnection);
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
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closePool(){
        for (int i = 0; i < Integer.parseInt(PropertiesUtil.get("POOL_SIZE_KEY")); i++) {
            try {
                sourcePool.get(i).close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
