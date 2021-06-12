package com.agents.violation;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Reporting_Agent {
    static int s_no,rs_no;
    static String agent_id="",rec_agent_id="";
    Reporting_Agent(int sno, int rsno) {
        Reporting_Agent.rs_no=rsno;
        Reporting_Agent.s_no=sno;}
    String violated_agent(){//to fetch agent id from the server
        String query;
               try{
            Class.forName("com.mysql.jdbc.Driver");//Connecting database mysql
            try (Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/traffic_violation","root","2646374")) {
                Statement st=con.createStatement();
                query="SELECT AgentID from rto where S_no = "+s_no;
                try (ResultSet res = st.executeQuery(query)) {
                    while(res.next())
                    {agent_id=res.getString("AgentID");}
                }}}
        catch(ClassNotFoundException | SQLException e){System.out.println(e);}
        return agent_id;}
    String reciever_agent(){//to fetch reporting agent id from the server
        String query;
               try{
            Class.forName("com.mysql.jdbc.Driver");//Connecting database mysql
            try (Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/traffic_violation","root","2646374")) {
                Statement st=con.createStatement();
                query="SELECT AgentID from rto where S_no = "+rs_no;
                try (ResultSet res = st.executeQuery(query)) {
                    while(res.next())
                    {rec_agent_id=res.getString("AgentID");}
                }}}
        catch(ClassNotFoundException | SQLException e){System.out.println(e);}
        return rec_agent_id;}
    void send(String violation_type) throws IOException{/*when violation is committed then this function will be called and the
        reciever agent will report violated agent by sending his details to the server and
        then server will update the database based on violation*/
        System.out.println("CONNECTING TO THE SERVER......");
        InetAddress address=InetAddress.getLocalHost();
        Socket s=new Socket(address,1342);//connecting to the server to send agent details
        BufferedReader input=new BufferedReader(new InputStreamReader(System.in));//to take input from user
        BufferedReader serverinput=new BufferedReader(new InputStreamReader(s.getInputStream()));//to take input from server
        PrintStream serverOutput=new PrintStream(s.getOutputStream());//to send data to server
        System.out.println("SERVER CONNECTED");
        System.out.println("REPORTING THE COMMITTED VIOLATION........");
        serverOutput.println(violation_type);
        serverOutput.println(agent_id); }
    public static void report(){//To send notification through app. Here just displaying message which will be visible on app
        try{
            System.out.println("SENDING NOTIFICATION TO THE USER......."); 
            Class.forName("com.mysql.jdbc.Driver");//Connecting database mysql
            Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/traffic_violation","root","2646374");
            Statement st=con.createStatement();
            String query="SELECT * from "+agent_id;
            ResultSet res = st.executeQuery(query);
            java.util.Date date =new java.util.Date();
            java.sql.Date sqldate =new java.sql.Date(date.getDate());
            java.sql.Time sqltime = new java.sql.Time(date.getTime());
            String violation_type="",name="";int fine=0;
            while(res.next()){
                violation_type=res.getString("Violation_Rule");
                sqldate=res.getDate("Date");
                sqltime=res.getTime("Time");
                fine=res.getInt("Fine_Deducted");}
            query = "SELECT Name from rto where AgentID = '"+agent_id+"'";
            res=st.executeQuery(query);
            while(res.next())
                name=res.getString("Name");
            System.out.println("NAME = "+name);
            System.out.println("ID = "+agent_id);
            System.out.println("VIOLATION TYPE: "+violation_type);
            System.out.println("DATE: "+sqldate);
            System.out.println("TIME: "+sqltime);
            System.out.println("FINE DEDUCTED: "+fine);}
            catch(Exception e){System.out.println(e);}}
}