package com.revature.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import org.apache.log4j.*;
import com.revature.model.User;



public class bankingSystem {
  public static User currentUser = new User();// acts like a placeholder
  dbClass db = new dbClass();
  public static String orgname = null;// check to see if already a user
  public static String orgpass = null;// check to see if password is correct
  public static int orgid = 0;// id on sql table
  public static double orgbalance = 0.0;// balance of user
  private static Logger logger = Logger.getLogger(bankingSystem.class);



  public boolean login() { // log in class, checks to see if you are already a user
    Scanner input = new Scanner(System.in); // my scanner
    boolean auth = false;// default false
    do {
      System.out.print("Enter your username : ");
      String name = input.next();
      System.out.print("Enter your Password : ");
      String pass = input.next();
      orgname = name;
      orgpass = pass;
      auth = db.auth(name, pass);
      if (auth == false) {
        logger.debug("Failed to login");//logging fail to log
        System.out.println("Failed to login");
     
      }
    } while (!auth);
    if (auth == true) {
      this.currentUser.setusername(orgname);
      this.currentUser.setPassword(orgpass);
      try {
        PreparedStatement stmt =
            dbClass.conn.prepareStatement("SELECT * FROM Bank WHERE username = ? AND upass = ?;");
        stmt.setString(1, orgname);
        stmt.setString(2, orgpass);
        stmt.execute();
        ResultSet rs = stmt.getResultSet();
        rs.next();
        this.orgid = rs.getInt(1);
        this.orgbalance = rs.getDouble(3);
      } catch (SQLException e) {

        e.printStackTrace();
      }
      System.out.println("SuccessFully Login");
      System.out.println("Choose From Option");

    }
    return auth;
  }

  public boolean Registration(String uname, String upass) {
    boolean flag = false;
    db.saveUser(uname, upass);
    db.save(uname, "0", "0", "0", "0");
    flag = true;

    return flag;
  }


  public void options() {
    int choice;
    do {
      System.out.println();
      System.out.println();
      Scanner input = new Scanner(System.in);
      System.out.println("press 1 for check Balance:");
      System.out.println("press 2 to withdrawal amount:");
      System.out.println("press 3 for deposit amount:");
      System.out.println("press 5 for Logout:");
      System.out.print("your choice : ");
      choice = input.nextInt();



      if (choice == 1) {
        System.out.println("Your current balance is: " + bankingSystem.orgbalance);

      }



      else if (choice == 2) {
        System.out.println();
        System.out.print("Enter your Withdrawal amount:: ");
        int amount = 0;
        try {
          amount = input.nextInt();
          if (amount > orgbalance) {
            System.out.println("You don't have enough money");
            this.options();
          }

        } catch (Exception e) {
          System.out.println("Invalid number entered returning to main menu");
          this.options();
        }
        this.orgbalance = this.orgbalance - amount;

        try {
          PreparedStatement stmt =
              dbClass.conn.prepareStatement("UPDATE Bank SET balance = ? WHERE id = ?");
          stmt.setDouble(1, this.orgbalance);
          stmt.setInt(2, this.orgid);
          stmt.execute();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        System.out.println("amount added successFully ::: ");
      }



      else if (choice == 3) {
        System.out.println();
        System.out
            .print("how much do you want to deposit in acount (please enter a whole number):: ");
        int amount = 0;
        try {
          amount = input.nextInt();
        } catch (Exception e) {
          System.out.println("Invalid number entered returning to main menu");
          this.options();
        }
        this.orgbalance = this.orgbalance + amount;

        try {
          PreparedStatement stmt =
              dbClass.conn.prepareStatement("UPDATE Bank SET balance = ? WHERE id = ?");
          stmt.setDouble(1, this.orgbalance);
          stmt.setInt(2, this.orgid);
          stmt.execute();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        System.out.println("amount added successFully ::: ");
      } else if (choice == 5) {
        System.out.println("Successfully logout");
      }
    } while (choice != 5);
  }



  public static void main(String[] args) {
    // TODO Auto-generated method stub
    bankingSystem banks = new bankingSystem();
    Scanner input = new Scanner(System.in);
    System.out.println("Please Select From The Following Options");
    System.out.println("Are you already a user? Type yes or no");
    System.out.print("Enter your choice : ");


    String choice = input.nextLine();



    if (choice.equals("y") || choice.equals("Y") || choice.equals("yes") || choice.equals("YES")
        || choice.equals("Yes")) {
      banks.login();
      banks.options();

    } else if (choice.equals("n") || choice.equals("N") || choice.equals("no")
        || choice.equals("No") || choice.equals("NO")) {
      System.out.print("Enter your username : ");
      String name = input.next();
      System.out.print("Enter your Password : ");
      String pass = input.next();
      if (banks.Registration(name, pass) == true) {
        orgname = name;
        System.out.println("Registered SuccessFully");
        banks.options();
      }
    }



  }

}

