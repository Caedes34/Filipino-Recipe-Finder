package com.filipinofinder;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;


public class Databasehelper {


            public static void main(String[] args) {

                  String url = "jdbc:sqlite:C:/Program Projects/RECIPE FINDER JAVA PROGJECT/my datas.db";

                   String sql = "SELECT * FROM recipeDB"; 
                   
                  try {
                     Connection connection = DriverManager.getConnection(url);
                     Statement statement = connection.createStatement();
                     

                     ResultSet result = statement.executeQuery(sql);
                     
                     ResultSetMetaData metaData = result.getMetaData();
                     int columncount = metaData.getColumnCount();

                     while (result.next()) {
                        for(int i= 1; i <= columncount; i++){
                           System.out.println(metaData.getColumnName(i) + ":" + result.getString(i) + "|");
                        }
                        System.out.println();
                     }
                     
                     
                  } catch (SQLException e) {

                     System.out.println("Error sql database");
                     e.printStackTrace();
                     
            }     
            
 }
}

      