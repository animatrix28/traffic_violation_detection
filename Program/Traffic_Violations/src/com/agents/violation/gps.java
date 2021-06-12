package com.agents.violation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class gps {
    String Area(int v){//To fetch name of the area in which violation has been comitted 
        String areaName = ""; 
        try{Class.forName("com.mysql.jdbc.Driver");//Connecting database mysql
            try (Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/traffic_violation","root","2646374")) {
                Statement st=con.createStatement();String query="select area_name from gps where Sno ="+v;
                try (ResultSet res = st.executeQuery(query)) {
                    while(res.next()){areaName = res.getString("Area_name");}}}}
        catch(ClassNotFoundException | SQLException e){System.out.println(e);}
        return areaName ; }
    String getDirection(int v){//To fetch permitted direction from the database to check against the direction in which the agent is moving
        String direction = ""; 
        try{Class.forName("com.mysql.jdbc.Driver");//Connecting database mysql
            try (Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/traffic_violation","root","2646374")) {
                Statement st=con.createStatement();String query="select permitted_direction from gps where Sno ="+v;
                try (ResultSet res = st.executeQuery(query)) {
                    while(res.next()){direction=res.getString("Permitted_Direction");}}}}
        catch(ClassNotFoundException | SQLException e){System.out.println(e);}
        return direction; }
    int getSpeed(int v){//To fetch the speed limit from the database to check against the speed in which the agent is moving
        int speed = 0; 
        try{Class.forName("com.mysql.jdbc.Driver");//Connecting database mysql
            try (Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/traffic_violation","root","2646374")) {
                Statement st=con.createStatement();String query="select Speed_Limit from gps where Sno ="+v;
                try (ResultSet res = st.executeQuery(query)) {
                    while(res.next()){speed=res.getInt("Speed_Limit");}}}}
        catch(ClassNotFoundException | SQLException e){System.out.println(e);}
        return speed; }
}