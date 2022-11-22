package pl.yspar.cage.database;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import pl.yspar.cage.CagePlugin;
import pl.yspar.cage.utils.Logger;
import pl.yspar.cage.utils.TimeUtil;

import java.sql.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    private static String host;
    private static String user;
    private static String pass;
    private static String name;
    private static String prefix;
    private static int port;
    private static Connection conn;
    private static long time;
    private static Executor executor;
    private static AtomicInteger ai;

    public Database(final String host, final int port, final String user, final String pass, final String name, final String prefix) {
        Database.host = host;
        Database.port = port;
        Database.user = user;
        Database.pass = pass;
        Database.name = name;
        Database.prefix = prefix;
        Database.executor = Executors.newSingleThreadExecutor();
        Database.time = System.currentTimeMillis();
        Database.ai = new AtomicInteger();
        connect();
        Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin) CagePlugin.getPlugin(), () -> {
            query("SELECT CURTIME()");
            Logger.warning("Odswiezono mysql");
        }, (long) TimeUtil.MINUTE.getTick(15), (long) TimeUtil.MINUTE.getTick(15));
    }

    public static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Logger.info("jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.name, Database.user, Database.pass);
            Database.conn = DriverManager.getConnection("jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.name, Database.user, Database.pass);
            Logger.info("Connected to the MySQL server!");
        } catch (ClassNotFoundException var1) {
            Logger.warning("JDBC driver not found!", "Error: " + var1.getMessage());
            var1.printStackTrace();
        } catch (SQLException var2) {
            Logger.warning("Can not connect to a MySQL server!", "Error: " + var2.getMessage());
            var2.printStackTrace();
        }
    }

    public static void update(final boolean now, final String update) {
        Database.time = System.currentTimeMillis();
        final Runnable r = () -> {
            try {
                Database.conn.createStatement().executeUpdate(update.replace("{P}", Database.prefix));
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
            return;
        };
        if (now) {
            r.run();
        } else {
            Database.executor.execute(r);
        }
    }

    public static ResultSet update(final String update) {
        try {
            final Statement statement = Database.conn.createStatement();
            statement.executeUpdate(update.replace("{P}", Database.prefix), 1);
            final ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs;
            }
        } catch (SQLException var3) {
            Logger.warning("An error occurred with given query '" + update.replace("{P}", Database.prefix) + "'!", "Error: " + var3.getMessage());
            var3.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        if (Database.conn != null) {
            try {
                Database.conn.close();
            } catch (SQLException var1) {
                Logger.warning("Can not close the connection to the MySQL server!", "Error: " + var1.getMessage());
                var1.printStackTrace();
            }
        }
    }

    public static void reconnect() {
        connect();
    }

    public static boolean isConnected() {
        try {
            return !Database.conn.isClosed() || Database.conn == null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet query(final String query) {
        try {
            return Database.conn.createStatement().executeQuery(query.replace("{P}", Database.prefix));
        } catch (SQLException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static void query1(final String query) {
        new Thread(() -> {
            try {
                Database.conn.createStatement().executeQuery(query.replace("{P}", Database.prefix));
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }, "MySQL Thread #" + Database.ai.getAndIncrement()).start();
    }

    public static void disableMySQL() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException var1) {
            var1.printStackTrace();
        }
        if (isConnected()) {
            disconnect();
        }
    }

    public Connection getConnection() {
        return Database.conn;
    }
}
