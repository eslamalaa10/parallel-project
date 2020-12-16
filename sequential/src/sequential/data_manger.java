/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sequential;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eslam Alaa
 */
public class data_manger {

    private static Connection con;
    private static boolean hasData;

    public data_manger() {
        try {
            getconnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(data_manger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(data_manger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getconnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:parallel.db");
    }

    public ResultSet display() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getconnection();
        }
        Statement state;
        state = con.createStatement();
        ResultSet result = state.executeQuery("select * from data");
        return result;
    }
    public void add_data(String title, String content) {
        try {
            PreparedStatement prep = con.prepareStatement("insert into data values(?,?);");
            prep.setString(1, title);
            prep.setString(2, content);
            prep.execute();
        } catch (SQLException ex) {
            Logger.getLogger(data_manger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
