package com.agents.violation;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

  public class ServerThread implements Runnable{
    Server s=null;
    Socket client=null;
    int count;
    ServerThread(Socket client,int c,Server ser)throws IOException{
        this.client=client;
        this.s=ser;
        this.count=c;
        System.out.println("CONNECTION ESTABLISHED WITH AGENT "+(count));}
        @Override
        public void run(){
        try{
            BufferedReader input=new BufferedReader(new InputStreamReader(client.getInputStream()));//To recieve data from client
            PrintStream output = new PrintStream(client.getOutputStream());//To send data to the client
            String violation_type=input.readLine();
            String agent_id=input.readLine();
            Class.forName("com.mysql.jdbc.Driver");//Connecting database mysql
            Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/traffic_violation","root","2646374");
            Statement st=con.createStatement(); 
            String query,violation_id=""; int violated_fine=0,current_fine=0;
            query="SELECT Violation_id from violation where Violation_Rule = \""+violation_type+"\"";
            ResultSet res=st.executeQuery(query);
            while(res.next())
            {violation_id = res.getString("Violation_id"); }
            query="SELECT Fine from violation where Violation_Rule = \""+violation_type+"\"";
            res =st.executeQuery(query);
            while(res.next())
            {violated_fine=res.getInt("Fine");}
            query="SELECT Fine from rto where AgentID = '"+agent_id+"'";
            res =st.executeQuery(query);
            while(res.next())
            {current_fine=res.getInt("Fine");}
            int total_fine=current_fine + violated_fine;
            query="UPDATE rto SET Fine = "+total_fine+" where AgentID = \""+agent_id+"\"";
            st.executeUpdate(query);
            DatabaseMetaData dbm =con.getMetaData();
            ResultSet table = dbm.getTables(null,null,agent_id,null);
            if(!(table.next())){
            query="CREATE TABLE "+agent_id+" (Violation_Rule VARCHAR(50), Date DATE, Time TIME, Fine_deducted INTEGER)";
            st.executeUpdate(query);}
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            java.sql.Time sqlTime = new java.sql.Time(date.getTime());
            query="INSERT into "+agent_id+"(Violation_Rule,Date,Time,Fine_Deducted) VALUES(?,?,?,?)";
            PreparedStatement psmt = con.prepareStatement(query);
            psmt.setString(1,violation_type);
            psmt.setDate(2, sqlDate);
            psmt.setTime(3,sqlTime);
            psmt.setInt(4, violated_fine);
            psmt.executeUpdate();
            psmt.close();
            res.close();}
            catch(Exception e){
                System.out.println(e);}
                    try{
            System.out.println("CONNECTION CLOSING FOR Agent "+count);
            client.close();}//closing server connection
            catch(IOException e){//if there's any problem while closing server
            System.out.println("********SOCKET CLOSED ERROR********");}}}