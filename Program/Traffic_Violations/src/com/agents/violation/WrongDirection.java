package com.agents.violation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WrongDirection {
    static int violation;
    static String violation_type="Wrong direction";
    public static void buffer1(){
       final long NANO_PER_SEC = 10001*1000*1000;
       long startTime = System.nanoTime();
       while((System.nanoTime()-startTime)<1*10*NANO_PER_SEC){}}
         public static List<Object> check_Direction() throws IOException{
            String agent_id="",rec_agent_id="",area = "";
            Random r=new Random();
            int flag = 0;
            int reporting_agent_coordinate = r.nextInt(10) + 1 ;//Generate random co-ordinate number for reporting agent 
            int violating_agent_coordinate = r.nextInt(10) + 1 ;//Generate random co-ordinate number for violating agent 
            int sno  = r.nextInt(10)+1;
            int rsno = r.nextInt(10)+1;
            while(sno==rsno) 
             rsno=r.nextInt(10)+1;
            Reporting_Agent obj1=new Reporting_Agent(sno,rsno);
            agent_id=obj1.violated_agent();
            rec_agent_id=obj1.reciever_agent();
            System.out.println(rec_agent_id+ "AGENT SEARCHING FOR AGENTS NEARBY......");
            //To check if the reporting agent and violating agent are in the same vicinity
            if(reporting_agent_coordinate != violating_agent_coordinate){flag = 0;}
            else if (reporting_agent_coordinate == violating_agent_coordinate){
                System.out.println("FOUND AN AGENT "+agent_id+"\nFETCHING ITS DIRECTION....");
            //Generating random direction for violating agent 
                String [] rD = {"North","South","West","East"};
                int rn = r.nextInt(4); 
                String randomDirection = rD[rn];
                System.out.println("CHECKING AGENT'S DIRECTION WITH PERMITTED DIRECTION....");
            //To fetch details from gps class   
                gps obj = new gps(); 
                String permittedDirection = obj.getDirection(reporting_agent_coordinate);
                area = obj.Area(reporting_agent_coordinate); 
            //To check if the fetched direction from the database is equal to the random direction generated
                if(!randomDirection.equals(permittedDirection)){
                    flag = 1; 
                    System.out.println("CAR WITH AGENT ID "+agent_id+" IS GOING IN WRONG DIRECTION");
                    System.out.println("*********WRONG DIRECTION VIOLATION*********");
            //Reporting_Agent obj1=new Reporting_Agent(sno,rsno);
                    agent_id=obj1.violated_agent();
                    rec_agent_id=obj1.reciever_agent();
                    obj1.send(violation_type);
                    buffer1();
                    Reporting_Agent.report();}
                else flag = 2; }
                return Arrays.asList(flag,area,agent_id,rec_agent_id );}
}