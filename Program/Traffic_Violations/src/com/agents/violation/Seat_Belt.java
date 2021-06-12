package com.agents.violation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Seat_Belt {
    static int violation;
    static String violation_type="SeatBelt violation";
   public static void buffer1(){
       final long NANO_PER_SEC = 10001*1000*1000;
       long startTime = System.nanoTime();
       while((System.nanoTime()-startTime)<1*10*NANO_PER_SEC){}}
   public static void buffer2(){
       final long NANO_PER_SEC = 10001*1000*1000;
       long startTime = System.nanoTime();
       while((System.nanoTime()-startTime)<1*15*NANO_PER_SEC){}}
   public static List<Object> checkSeatBelt() throws IOException{
       Random random=new Random();
       int engine=1;
       int seat=random.nextInt(2);
       int sno=random.nextInt(10)+1;
       int rsno=random.nextInt(10)+1;
       while(sno==rsno) rsno=random.nextInt(10)+1;
       Reporting_Agent obj=new Reporting_Agent(sno,rsno);
       String agent_id=obj.violated_agent();
       String rec_agent_id=obj.reciever_agent();
       System.out.println("CHECKING AGENT "+agent_id+" FOR SEAT BELT VIOLATION");
       System.out.println("BUFFER OF 10 MIN IS GIVEN FROM THE TIME ENGINE IS SWITCHED ON");
       buffer1();//buffer time for 10min
       while(engine!=0){
           if(seat==1){//when the driver has put on his seat belt
               System.out.println("DRIVER IS WEARING A SEAT BELT");
               System.out.println("BUFFER OF 15 MIN IS GIVEN TO CHECK AGAIN");
               buffer2();//buffer time for 15min
              engine=random.nextInt(2);
              if(engine==1){ seat=random.nextInt(2);}
              else{ System.out.println("ENGINE STOPPED"); break;}
           }//seat==1
           else{//when the driver hasn't put his seat belt
              System.out.println("DRIVER IS NOT WEARING A SEAT BELT");
              System.out.println("AN ALERT OF 5 BEEPS IS GIVEN TO THE DRIVER TO WEAR THE SEAT BELT");
              System.out.println("BUFFER OF 15 MIN IS GIVEN AFTER WARNING THE DRIVER");
              buffer1();//10min buffer time given
              engine =random.nextInt(2);
              seat = random.nextInt(2);
              if(engine==1){ 
                if(seat==0){//after giving warnings still seat belt is off then report
                 System.out.println("DRIVER DIDN'T WEAR THE SEAT BELT EVEN AFTER THE WARNING\n*******SEAT BELT VIOLATION*******");
                 System.out.println("SEARCHING FOR NEARBY AGENTS.....\nFOUND AN AGENT "+ rec_agent_id);
                 System.out.println("SENDING AGENT ID AND VIOLATION TYPE TO NEARBY AGENT i.e. "+rec_agent_id+" THROUGH WIFI MODULE");
                 obj.send(violation_type);
                 buffer1();
                 Reporting_Agent.report();
                break;}}
              else{ System.out.println("ENGINE STOPPED"); break;}
           }//seat==0
       } 
       return Arrays.asList(seat,agent_id,rec_agent_id,engine);}
}