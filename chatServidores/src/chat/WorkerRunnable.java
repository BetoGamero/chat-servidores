/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author alxs1
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**

 */
public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;
    protected BufferedReader br = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }
    
    public WorkerRunnable(Socket clientSocket, BufferedReader b) {
        this.clientSocket = clientSocket;
        this.br = b;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            
            //String linea = "";
            
            //while(!linea.equals("chau")){
                /*Scanner sc = new Scanner(System.in);
                linea = sc.nextLine();*/
                
           
                    //if (linea.isEmpty())
                    //linea = "enter";
                    //System.out.println(serverText);
                    //StringBuilder sb = new StringBuilder();
                    String linea;
                    String s = "\n";
                    while(((linea = br.readLine()) != null)){
                        //System.out.println(linea);
                        output.write(linea.getBytes());
                        
                        
                    }
                    
                    
                
            
            //}
            
            
            
            //long time = System.currentTimeMillis();
            /*output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
                this.serverText + " - " +
                time +
                "").getBytes());*/
            output.close();
            input.close();
            //System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
