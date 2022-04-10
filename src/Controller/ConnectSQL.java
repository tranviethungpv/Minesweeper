package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import View.saveInfo;

public class ConnectSQL {

    static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/minesweeper";
    static final String USER = "root";
    static final String PASS = "";

    public Connection getConn() {
        return conn;
    }

    private Connection conn;

    public ConnectSQL() {
        try {
            Class.forName(DRIVER_CLASS);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public void add() {

        String sql = "INSERT INTO datagames(Name, Status, Time, Date) " + "VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, saveInfo.getplayerName());
            ps.setString(2, saveInfo.getStatus());
            ps.setDouble(3, (double) (saveInfo.getEndTime() - saveInfo.getStartTime()) / 1000);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date = dateFormat.format(saveInfo.getDate());
            ps.setString(4, date);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
