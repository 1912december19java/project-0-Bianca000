package com.revature.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dbClass{

  //Logging into the database
  
    public static Connection conn;
    public dbClass() {
        try {
          conn = DriverManager.getConnection(
          System.getenv("connstring"), System.getenv("username"), System.getenv("password"));
          System.out.println("Connected to Database");
        } catch (SQLException e) {
          System.out.println("Failed to Connect to Database:Try again.");
          e.printStackTrace();
        }
    
    }
    

    public void save(String name,String balance,String withdrawal,String deposit,String upass)
    {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement("INSERT INTO Bank(username, balance, withdrawal, deposit,upass) VALUES (?,?,?,?,?)");
      stmt.setString(1, name);
      stmt.setString(2, balance);
      stmt.setString(3, withdrawal);
      stmt.setString(4, deposit);
      stmt.setString(5, upass);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    }
    
    public void saveUser(String name,String pass)
    {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement(
          "INSERT INTO Bank(username, upass) VALUES (?,?);");
      stmt.setString(1, name);
      stmt.setString(2, pass);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    }
    
    public boolean auth(String uname,String pass)
    {
        boolean flag=false;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
          stmt = conn.prepareStatement("SELECT * FROM Bank WHERE username = ? AND upass=?");
      
          stmt.setString(1, uname);
          stmt.setString(2, pass);
          if (stmt.execute()) {
            rs = stmt.getResultSet();
            while(rs.next())
            {
                flag=true;
            }
          }
    }
        catch (SQLException e) {
          }
        
        return flag;
    }
    
    
    
    
}

