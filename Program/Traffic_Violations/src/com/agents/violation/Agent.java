package com.agents.violation;

import static com.agents.violation.Seat_Belt.checkSeatBelt;
import static com.agents.violation.SpeedLimit.check_Speed;
import static com.agents.violation.Uninsured_Vehicle.getInsuranceDetail;
import static com.agents.violation.WrongDirection.check_Direction;
import java.io.IOException;
import java.util.List;

public class Agent {
    public static void main(String args[])throws IOException{
        final long NANO_PER_SEC = 10001*1000*1000;
       long startTime = System.nanoTime();
       int count_seat=0,count_direction=0,count_speed=0,count_insurance=0;
       while((System.nanoTime()-startTime)<60*60*NANO_PER_SEC){//checking each violation by calling each clas for 1hrs
                //SEAT BELT
                System.out.println("CHECKING FOR SEAT BELT VIOLATION....");
                Seat_Belt obj1=new Seat_Belt();
                List<Object> check_seat_belt=checkSeatBelt();
                int seat=(int) check_seat_belt.get(0);
                int engine=(int) check_seat_belt.get(3);
                if(seat==0&&engine==1){
                    System.out.println("AGENT "+check_seat_belt.get(1)+" HAS VIOLATED SEAT BELT RULE AND "
                            + "THIS VIOLATION HAS BEEN REPORTED BY AGENT "+check_seat_belt.get(2)
                            +" AND A NOTIFICATION ABOUT THIS VIOLATION HAS BEEN SENT TO THE USER SUCCESSFULLY");
                count_seat++;}
                //WRONG DIRECTION
                System.out.println("-------------------------------------------------------------------");
                System.out.println("CHECKING FOR WRONG DIRECTION VIOLATION....");
                    WrongDirection obj2 = new WrongDirection(); 
                    List<Object> check_wrong_direction = check_Direction();
                    int flag = (int)check_wrong_direction.get(0); 
                    String area = (String)check_wrong_direction.get(1);
                    if(flag == 0){
                        System.out.println("NO AGENT HAS BEEN FOUND IN THE VICINITY OF AGENT "+check_wrong_direction.get(3));}
                    else if(flag == 1){
                        System.out.println("AGENT "+check_wrong_direction.get(2)+" HAS VIOLATED WRONG DIRECTION RULE AND "
                            + "THIS VIOLATION HAS BEEN REPORTED BY AGENT "+check_wrong_direction.get(3)+" IN "+area+" RAJAJINAGAR"
                         +" AND A NOTIFICATION ABOUT THIS VIOLATION HAS BEEN SENT TO THE USER SUCCESSFULLY"); count_direction++;}
                    else
                        System.out.println("AGENT'S DIRECTION IS SAME AS THE PERMITTED DIRECTION!!\n"
                                + "NO VIOLATING AGENT HAS BEEN FOUND IN THE VICINITY");
                //OVERSPEEDING
                System.out.println("-------------------------------------------------------------------");
                System.out.println("CHECKING FOR OVERSPEEDING VIOLATION....");
                SpeedLimit obj3 = new SpeedLimit(); 
                    List<Object> check_overspeeding = check_Speed();
                    int flag1 = (int)check_overspeeding.get(0); 
                    String area1 = (String)check_overspeeding.get(1); 
                    if(flag1 == 0){
                        System.out.println("NO AGENT HAS BEEN FOUND IN THE VICINITY OF AGENT "+check_overspeeding.get(3));}
                    else if(flag1 == 1){
                        System.out.println("AGENT "+check_overspeeding.get(2)+" HAS VIOLATED OVERSPEEDING RULE AND "
                            + "THIS VIOLATION HAS BEEN REPORTED BY AGENT "+check_overspeeding.get(3)+" IN  "+area1+" RAJAJINAGAR"
                        +" AND A NOTIFICATION ABOUT THIS VIOLATION HAS BEEN SENT TO THE USER SUCCESSFULLY"); count_speed++;}
                    else System.out.println("NO VIOLATING AGENT HAS BEEN FOUND IN THE VICINITY ");
                //UNINSURED VEHICLE
                System.out.println("-------------------------------------------------------------------");
                System.out.println("CHECKING INSURANCE OF THE VEHICLE....");
                Uninsured_Vehicle obj4=new Uninsured_Vehicle();
                List<Object> check_insurance = getInsuranceDetail();
                int check= (int) check_insurance.get(1);
                if(check==1)
                System.out.println("AGENT "+check_insurance.get(0)+" HAS A VALID INSURANCE DATE.\nTHUS EXPIRY IS YET TO COME!!");
                else{
                  System.out.println("AGENT "+check_insurance.get(0)+" HAS VIOLATED INSURANCE VIOLATION WHICH HAS BEEN "
                + "REPORTED TO THE SERVER AND A NOTIFICATION ABOUT THIS VIOLATION HAS BEEN SENT TO THE USER SUCCESSFULLY");count_insurance++;}
                System.out.println("-------------------------------------------------------------------");}
                int total_agents=count_seat+count_direction+count_speed+count_insurance;
                System.out.println("NUMBER OF TIMES SEATBELT VIOLATION COMMITTED AFTER SIMULATING FOR 1 HOUR: "+count_seat);
                System.out.println("NUMBER OF TIMES WRONG DIRECTION VIOLATION COMMITTED AFTER SIMULATING FOR 1 HOUR: "+count_direction);
                System.out.println("NUMBER OF TIMES OVERSPEEDING VIOLATION COMMITTED AFTER SIMULATING FOR 1 HOUR: "+count_speed);
                System.out.println("NUMBER OF TIMES UNINSURED VEHICLE VIOLATION COMMITTED AFTER SIMULATING FOR 1 HOUR: "+count_insurance);
                System.out.println("TOTAL NUMBER OF AGENTS THAT WERE REPORTED FOR COMMITING VIOLATION AFTER SIMULATING FOR 1 HOURS: "+total_agents);
       }}