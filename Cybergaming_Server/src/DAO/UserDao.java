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
import java.util.ArrayList;
import java.util.List;
import model_structure.User;


public class UserDao {
    
    public UserDao() {
        
    }
    public void setUserlogout(long time, String username)
    {
        try { // cài lại thời gian và trang thái khi client logout
                    ConnectSqlServer conn = new ConnectSqlServer();
                    Connection connection = conn.getConnetion();
                    Statement statement = connection.createStatement();

                    String sql = String.format("UPDATE user SET time='%s',status='%s'  WHERE username='%s'",time,"OFFLINE",username);
                    int affectedRows = statement.executeUpdate(sql);
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setUserwhenlogin(String username)
    {
        try { // cài user thành trạng thái hoạt động khi đăng nhập
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("UPDATE user SET status='%s'  WHERE username='%s'","ONLINE",username);
                int affectedRows = statement.executeUpdate(sql);

                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
    public String checkPasslogin(String username)
    {
        String password="";
        
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("SELECT * FROM user WHERE username='%s'", username);
                ResultSet r = statement.executeQuery(sql);
               
                if(r.next())
                {
                    password = r.getString("password");

                }
        } catch (Exception e) {
            password=null;
        }
  
        
        return password;
    }
    public String checkStatuslogin(String username)
    {
        String status = null;
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("SELECT * FROM user WHERE username='%s'", username);
                ResultSet r = statement.executeQuery(sql);
                
                if(r.next())
                {
                    status = r.getString("status");

                }
        } catch (Exception e) {
        }
        return status;
    } 
    public long gettimeUser(String username)
    {
        long time=0;
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("SELECT * FROM user WHERE username='%s'", username);
                ResultSet r = statement.executeQuery(sql);
                while(r.next())
                {
                    time= r.getLong("time");
                }
                
        } catch (Exception e) {
        }
        return time;
    }
    public int getidUser(String username)
    {
        int id=0;
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("SELECT * FROM user WHERE username='%s'", username);
                ResultSet r = statement.executeQuery(sql);
                while(r.next())
                {
                    id= r.getInt("id");
                }
                
        } catch (SQLException e) {
        }
        return id;
    }
    public List<User> getallUser()
    {
        List<User> users= new ArrayList<User>();
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("SELECT * FROM user");
                ResultSet r = statement.executeQuery(sql);
                while(r.next())
                {
                    User user= new User();
                    user.setId(r.getInt("id"));
                    user.setUsername(r.getString("username"));
                    user.setPassword(r.getString("password"));
                    user.setName(r.getString("name"));
                    user.setPhone(r.getString("phone"));
                    user.setTime(r.getLong("time"));
                    user.setStatus(r.getString("status"));
                    users.add(user);
                }
                
        } catch (SQLException e) {
        }
        return users;
    }
    public List<User> getUserfind(int option,String username)
    {
        List<User> users= new ArrayList<User>();
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();
                String sql="";
                if(option==1)
                {
                    sql = String.format("SELECT * FROM user WHERE username='%s'", username);
                }
                else if(option==2)
                {
                    sql= String.format("SELECT * FROM user WHERE status='%s'", "ONLINE");
                }
                else if(option==3)
                {
                    sql= String.format("SELECT * FROM user WHERE status='%s'", "BLOCKED");
                }
                else if(option==4)
                {
                    sql= String.format("SELECT * FROM user WHERE time='%s'", 0);
                }
                else
                {
                    sql = String.format("SELECT * FROM user");
                }
                ResultSet r = statement.executeQuery(sql);
                while(r.next())
                {
                    User user= new User();
                    user.setId(r.getInt("id"));
                    user.setUsername(r.getString("username"));
                    user.setPassword(r.getString("password"));
                    user.setName(r.getString("name"));
                    user.setPhone(r.getString("phone"));
                    user.setTime(r.getLong("time"));
                    user.setStatus(r.getString("status"));
                    users.add(user);
                }
                
        } catch (SQLException e) {
        }
        return users;
    }
    public int insertuser(String username,String password,String fullname, String sodienthoai,long sotien)
    {
        int n=0;
        try {
            ConnectSqlServer conn = new ConnectSqlServer();
                    Connection connection = conn.getConnetion();
                    Statement statement = connection.createStatement();

                    String sql = String.format("INSERT INTO user(username, password, name, phone, time, status) VALUES ('%s', '%s', '%s', '%s','%s', '%s')"
                        , username, password, fullname, sodienthoai,sotien,"OFFLINE");

                    n = statement.executeUpdate(sql);
        } catch (SQLException e) {
        }
        return n;
    }
    public void settimeuser(String username, long time) // nap tien vào tài khoản
    {
        try {
            ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("UPDATE user SET time='%s'  WHERE username='%s'",time,username);
                int affectedRows = statement.executeUpdate(sql);
                
        } catch (SQLException e) {
        }
    }
    public void updateuser(String username,String password,String name, String sodienthoai,long sotien, String status, int id)
    {
        try {
            ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("UPDATE user SET username='%s',password='%s',name='%s',phone='%s',time='%s',status='%s'  WHERE id='%s'",username,password,name,sodienthoai,sotien,status,id);
                int affectedRows = statement.executeUpdate(sql);
        } catch (SQLException e) {
        }
    }
    public void setstatususer(String status, String username)
    {
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("UPDATE user SET status='%s'  WHERE username='%s'",status,username);
                int affectedRows = statement.executeUpdate(sql);
                
        } catch (SQLException e) {
        }
    }
    public void deleteuser(String username)
    {
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("DELETE FROM user WHERE username='%s'",username);
                int affectedRows = statement.executeUpdate(sql);
                
        } catch (SQLException e) {
        }
    }
    public void changepass(String username, String password)
    {
        try {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("UPDATE user SET password='%s'  WHERE username='%s'",password,username);
                int affectedRows = statement.executeUpdate(sql);
                
        } catch (SQLException e) {
        }
    }
}
