package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


public class DBConnection {
    private static final String URL = "jdbc:mariadb://127.0.0.1:3306/hospital";
    private static final String USER = "nipa";
    private static final String PASSWORD = "passwd123";

    public static DSLContext getContext() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return DSL.using(conn, SQLDialect.MARIADB);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
