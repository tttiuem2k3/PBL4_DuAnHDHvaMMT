package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectSqlServer 
{
    String driverName;
    String url;
    String dbname;
    String username;
    String password;
    static Connection connection;
    
    public ConnectSqlServer()
    {
        connection = null;
    }
    
    private void CreateConnection()
    {
        try{

            url = "jdbc:mysql://localhost:3306/csmdatabase";
            username = "root";
            password = "1234";
            

            connection = DriverManager.getConnection(url, username, password);
           
        }catch(Exception ex){
            ex.printStackTrace();
        }
         
    }
    
    public Connection getConnetion()
    {
        if(connection == null)
        {
            CreateConnection();
        }
        return connection;
    }
            
    public void closeConnection() throws SQLException
    {
        if(connection != null)
        {
            connection.close();
        }
    }
}
