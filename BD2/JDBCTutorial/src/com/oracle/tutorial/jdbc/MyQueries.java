package com.oracle.tutorial.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class MyQueries {
  
  Connection con;
  JDBCUtilities settings;  
  
  public MyQueries(Connection connArg, JDBCUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

  public static void getMyData(String supplierName, Connection con) throws SQLException {
    Statement stmt = null;
    String query = "select s.SUP_NAME as SNAME, COUNT(c.COF_NAME) as CNAME from COFFEES as c, SUPPLIERS as s where s.SUP_ID = c.SUP_ID and s.SUP_NAME like '%" + supplierName +"%' group by s.SUP_NAME";
    
    try {
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      System.out.println("Coffees bought from " + supplierName + ": ");
      while (rs.next()) {
        String supplierNames = rs.getString("SNAME");
        int typesOfCoffeeSold = rs.getInt("CNAME");
        System.out.println("     " + supplierNames + " count: " + typesOfCoffeeSold);
      }
    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }

  public static void populateTable(Connection con) throws SQLException, IOException {
    String create = "INSERT INTO debito (numero_debito, valor_debito, motivo_debito, data_debito, numero_conta, nome_agencia, nome_cliente) " +
    "VALUES(?, ?, ?, ?, ?, ?, ?);";
    PreparedStatement stmt = null;

    try {
      con.prepareStatement("TRUNCATE TABLE debito;").executeUpdate();
      stmt = con.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
      
      BufferedReader inputStream = null;
      Scanner scanned_line = null;
      String line;
      String[] value = new String[7];

      inputStream = new BufferedReader(new FileReader("../../../../../debito-populate-table.txt"));

      while ((line = inputStream.readLine()) != null) { 
          int countv = 0;

          //split fields separated by tab delimiters 
          scanned_line = new Scanner(line);
          scanned_line.useDelimiter("\t");

          while (scanned_line.hasNext()) { 
            value[countv++] = scanned_line.next();
          }

          if (scanned_line != null) { 
              scanned_line.close();
          }

          int numero_debito = Integer.parseInt(value[0]);
          Double valor_debito = Double.parseDouble(value[1]);
          int motivo_debito = Integer.parseInt(value[2]);
          int numero_conta = Integer.parseInt(value[4]);

          stmt.setInt(1, numero_debito);
          stmt.setDouble(2, valor_debito);
          stmt.setInt(3, motivo_debito);
          stmt.setDate(4, Date.valueOf(value[3]));
          stmt.setInt(5, numero_conta);
          stmt.setString(6, value[5]);
          stmt.setString(7, value[6]);

          stmt.executeUpdate();
      }

      
    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {       
      if (stmt != null) { 
        stmt.close(); 
      }     
    }   
  }


  public static void main(String[] args) throws IOException {
    JDBCUtilities myJDBCUtilities;
    Connection myConnection = null;
    if (args[0] == null) {
      System.err.println("Properties file not specified at command line");
      return;
    } else {
      try {
        myJDBCUtilities = new JDBCUtilities(args[0]);
      } catch (Exception e) {
        System.err.println("Problem reading properties file " + args[0]);
        e.printStackTrace();
        return;
      }
    }

    try {
      myConnection = myJDBCUtilities.getConnection();

 	    //MyQueries.getMyData("Superior Coffee", myConnection);
      populateTable(myConnection);
    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      JDBCUtilities.closeConnection(myConnection);
    }

  }
}
