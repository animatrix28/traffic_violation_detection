package com.agents.violation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpeedLimit {
    static int violation;
    static String violation_type="Speed limit violation";
      public static void buffer1(){
       final long NANO_PER_SEC = 10001*1000*1000;
       long startTime = System.nanoTime();
       while((System.nanoTime()-startTime)<1*10*NANO_PER_SEC){}}
       public static List<Object> check_Speed() throws IOException{
            String agent_id="",rec_agent_id="",area = "";
            Random r=new Random();
            int flag = 0;
            int reporting_agent_coordinate = r.nextInt(10) + 1 ; //Generate random co-ordinate number for reporting agent 
            int violating_agent_coordinate = r.nextInt(10) + 1 ; //Generate random co-ordinate number for violating agent 
            int sno  = r.nextInt(10)+1;//Generate random serial number of the violating agent
            int rsno = r.nextInt(10)+1;//Generate random serial number of the reporting agent
            while(sno==rsno) 
                rsno=r.nextInt(10)+1;
            Reporting_Agent obj1=new Reporting_Agent(sno,rsno);
            agent_id=obj1.violated_agent();
            rec_agent_id=obj1.reciever_agent();
            System.out.println(rec_agent_id+ "AGENT SEARCHING FOR AGENTS NEARBY......");
            //To check if the reporting agent and violating agent are in the same vicinity
            if(reporting_agent_coordinate != violating_agent_coordinate){flag = 0; }
            else if (reporting_agent_coordinate == violating_agent_coordinate){
                System.out.println("FOUND AN AGENT "+agent_id+"\nFETCHING ITS CURRENT SPEED....");
            //Generating random speed for violating agent 
                int  current_random_speed = r.nextInt(100)+1;
                System.out.println("CHECKING AGENT'S SPEED WITH PERMITTED SPEED....");
            //To fetch details from gps class    
                gps obj = new gps(); 
                int permitted_speed = obj.getSpeed(reporting_agent_coordinate);
                area = obj.Area(reporting_agent_coordinate);
            //To check if the random speed generated is greater than the fetched speed limit from database  
                if(current_random_speed >= permitted_speed){
                    System.out.println("CAR'S CURRENT SPEED IS MORE THAN THE SPEED LIMIT!!");
                    System.out.println("GIVING BUFFER TIME OF 10SEC TO DRIVER TO MAINTAIN THE SPEED LIMIT");
                    buffer1();
                    current_random_speed = r.nextInt(100)+1;
                    if(current_random_speed >= permitted_speed){
                    flag = 1;
                    System.out.println("CAR WITH AGENT ID "+agent_id+" IS GOING MORE THAN THE SPEED LIMIT EVEN AFTER GIVING BUFFER"); 
                    System.out.println("*********OVERSPEEDING VIOLATION*********");
            //If the speed is greater than speed limit, it has to be reported   
                    agent_id=obj1.violated_agent();
                    rec_agent_id=obj1.reciever_agent();
                    obj1.send(violation_type);
                    buffer1();
                    Reporting_Agent.report(); }
                    else flag=2; }
                else flag = 2;}
                return Arrays.asList(flag,area,agent_id,rec_agent_id );}
}