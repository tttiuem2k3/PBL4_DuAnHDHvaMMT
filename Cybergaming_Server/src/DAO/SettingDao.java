 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import database.ConnectSqlServer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingDao {

    public SettingDao() {
    }
    public int getNumofpc()
    {
        int pc=0;
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("SELECT * FROM setting WHERE id='%s'", 1);
                ResultSet r = statement.executeQuery(sql);
                while(r.next())
                {
                    pc= r.getInt("numofpc");
                }
                
        } catch (SQLException e) {
        }
        return pc;
    }
    public long getCost()
    {
        long cost=0;
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("SELECT * FROM setting WHERE id='%s'", 1);
                ResultSet r = statement.executeQuery(sql);
                while(r.next())
                {
                    cost= r.getInt("cost");
                }
                
        } catch (SQLException e) {
        }
        return cost;
    }
    public long getCostUser()
    {
        long cost=0;
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("SELECT * FROM setting WHERE id='%s'", 1);
                ResultSet r = statement.executeQuery(sql);
                while(r.next())
                {
                    cost= r.getInt("costuser");
                }
                
        } catch (SQLException e) {
        }
        return cost;
    }
     public void setting(int numofpc, long cost, long costuser)
    {
        try { 
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("UPDATE setting SET numofpc='%s',cost='%s',costuser='%s'  WHERE id='%s'",numofpc,cost,costuser,1);
                int affectedRows = statement.executeUpdate(sql);

                } catch (SQLException e) {
                    
                }
    }
}
