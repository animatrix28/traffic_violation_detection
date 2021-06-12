package com.agents.violation;

import static com.agents.violation.Reporting_Agent.agent_id;
import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Uninsured_Vehicle {
   static String violation_type="Insurance violation";
   static Date expiry_date;
   static int year,month,date;
   public static void buffer1(){
       final long NANO_PER_SEC = 10001*1000*1000;
       long startTime = System.nanoTime();
       while((System.nanoTime()-startTime)<1*10*NANO_PER_SEC){}}
    public static String Update_Insurance(){
        Random random=new Random();
        String query;
        Calendar calendar = new GregorianCalendar();
          calendar.setTime(expiry_date);
          int updated_year = year+1;
          int updated_date = random.nextInt(13-10+1)+10;
          int updated_month = expiry_date.getMonth();
          query="UPDATE rto set Insurance_expiry_date = '"+updated_year+"/"+updated_month+"/"+updated_date+"' where AgentID = '"+agent_id+"'";
          System.out.println("INSURANCE IS PAID AND UPDATED SUCCESSFULLY AND THE NEW EXPIRY DATE FOR THE AGENT "
                  +agent_id+" IS '"+updated_year+"/"+updated_month+"/"+updated_date+"'");
          return query;}
    public static List<Object> getInsuranceDetail() throws IOException{
       int check=1;
       Random random=new Random();
       int sno=random.nextInt(10)+1;
       int rsno=random.nextInt(10)+1;
       while(sno==rsno) rsno=random.nextInt(10)+1;
       Reporting_Agent obj=new Reporting_Agent(sno,rsno);
       String agent_id=obj.violated_agent();
       try{
       Class.forName("com.mysql.jdbc.Driver");
       Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/traffic_violation","root","2646374");
       Statement st=con.createStatement();
       String query="SELECT Insurance_expiry_date from rto where AgentID = '"+agent_id+"'";
       expiry_date = new Date();
       ResultSet res = st.executeQuery(query);
       while(res.next()){
        expiry_date = res.getDate("Insurance_expiry_date");}
       year = random.nextInt(2021-2018+1)+2018;//generating year randomly
       month = random.nextInt(13)+1;//generating month randomly
       date = 10;
       Calendar cal=Calendar.getInstance();
       cal.set(Calendar.YEAR,year);
       cal.set(Calendar.MONTH,month);
       cal.set(Calendar.DAY_OF_MONTH,date);
       Date current_date = cal.getTime();//current date with date fixed as 10th and month and year are random
       System.out.println("CURRENT DATE: "+current_date+"\nEXPIRY DATE: "+expiry_date);
       int c = current_date.compareTo(expiry_date);//comparing insurance expiry date with 10th of the current month
       if(c>0){//if insurance is expired
         System.out.println("INSURANCE OF AGENT "+agent_id+" IS EXPIRED!!\n"
                 + "WARNING THE AGENT BY SENDING NOTIFICATION TO UPDATE THE INUSRANCE WITHIN 3 DAYS\n"
                 + "SENDING NOTIFICATION TO THE AGENT....\nNOTIFICATION SENT!!");
         buffer1();//three days buffer to be given
         System.out.println("CHECKING INSURANCE EXPIRY DATE AFTER GIVING THREE DAYS WARNING TO THE USER!!");
         int r = random.nextInt(2);//generating random numbers and assuming that if insurance updated then r=1 otherwise r=0
         if(r==0){
          System.out.println("INSURANCE IS STILL NOT UPDATED EVEN AFTER GIVING THREE DAYS WARNING TO THE USER!!");
          System.out.println("*********UNINSURED VEHICLE VIOLATION*********");
          System.out.println("SENDING AGENT ID AND VIOLATION TYPE TO THE SERVER");
          obj.send(violation_type);
          buffer1();
          Reporting_Agent.report();
          check=0;}
         else{query = Update_Insurance();
          st.executeUpdate(query);}}
       else if(c==0)//if insurance will be expired today
       {System.out.println("INSURANCE OF AGENT "+agent_id+" WILL GET EXPIRED TODAY!!\n"
                 + "WARNING THE AGENT BY SENDING NOTIFICATION TO UPDATE THE INUSRANCE WITHIN 3 DAYS\n"
                 + "SENDING NOTIFICATION TO THE AGENT....\nNOTIFICATION SENT!!");
         buffer1();
         System.out.println("CHECKING INSURANCE EXPIRY DATE AFTER GIVING THREE DAYS WARNING TO THE USER!!");
         int r = random.nextInt(2);//generating random numbers and assuming that if insurance updated then r=1 otherwise r=0
         if(r==0){
          System.out.println("INSURANCE IS STILL NOT UPDATED EVEN AFTER GIVING THREE DAYS WARNING TO THE USER!!");
          System.out.println("*********UNINSURED VEHICLE VIOLATION*********");
          System.out.println("SENDING AGENT ID AND VIOLATION TYPE TO THE SERVER");
          obj.send(violation_type);
          buffer1();
          Reporting_Agent.report();
          check=0;}
         else{query = Update_Insurance();
          st.executeUpdate(query);}}
       else {//if insurance is not expired and is valid
       System.out.println("INSURANCE IS VALID!!");}}
       catch(Exception e){System.out.println(e);}
       return Arrays.asList(agent_id,check);}
        }