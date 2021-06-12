package com.agents.violation;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    int port;//port number
ServerSocket Server=null;//server object
Socket client=null;//client object
ExecutorService pool=null;//to execute the program with threads
int count=0;//Client number
Server(int port){//create threads in server to keep the count of clients and then send the client address to the serve
        this.port=port;
        pool=Executors.newFixedThreadPool(5);}
     public void startServer() throws IOException{//Server will be established for each client
        Server=new ServerSocket(1342);//Server is created and then wait for client to join
        System.out.println("********SERVER STARTED********");
        while(true){//Loop is used for multiple client. Whenever client runs, new connection is established with the same server
            client=Server.accept();//After client joins, server is established between client and server
            count++;
            ServerThread runnable=new ServerThread(client,count,this);
            pool.execute(runnable);
            }}
    public static void main(String[] args) throws IOException {
        Server obj=new Server(1342);
        obj.startServer();}  
}
