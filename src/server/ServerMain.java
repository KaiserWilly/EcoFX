package server;

import rsc.Values;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created 12/27/15
 * Software Development
 * TSA Conference, 2016
 * ServerMain: Class containing code that starts the server
 */
public class ServerMain {
    static Boolean serverGo = true;
    public static void startServer() {
        int ID = 0;

        try {
            ServerFile.showTimeStamp("Starting Server");
            Values.consoleQueue.add("Starting Server");
            ServerFile.showTimeStamp("Creating Listening Socket on Port 1180");
            Values.consoleQueue.add("Creating Listening Socket on Port 1180");
            ServerSocket listeningSocket = new ServerSocket(1180); // Starts The Server
            ServerFile.showTimeStamp("Socket Created");
            Values.consoleQueue.add("Socket Created");
            server.engine.EcoEngine.initializeEngine(50); //Starts the Engine with 50 stocks
            ServerFile.showTimeStamp("Engine Initialized");
            Values.consoleQueue.add("Engine Initialized");
            ServerTimer.startTimer(); //Starts the timer
            ServerFile.showTimeStamp("Update Control Started, game beginning in 1 second");


            while (serverGo) {
                Socket socketToClient = listeningSocket.accept(); //Accepts the Client
                new ServerClientHandler(socketToClient, ID).start(); //Starts a thread of the server
                ID++; //Increases the number of clients tracked
            }
        } catch (BindException e) {
            ServerFile.showTimeStamp("ERROR: Port in use / Not able to bind to port. Terminating server");
            Values.consoleQueue.add("Error: Port in use / Can not bind port");
            Values.consoleQueue.add("(Server may already be running)");
            Values.consoleQueue.add("Terminating Server");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stopServer(){
        serverGo = false;
    }
}
