/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alxs1
 */
public class Nodo implements Runnable{

    int puerto = 9999;
    ServerSocket serverSocket = null;
    boolean detenido = false;
    Thread hilo = null;

    public Nodo(int puerto) {
        this.puerto = puerto;
    }
    
    
    private void abrirSocket(){
        try {
            this.serverSocket = new ServerSocket(this.puerto);
        } catch (IOException e) {
            throw new RuntimeException("no se puede abrir el puerto" + this.puerto, e);
        }
    }
    
    public synchronized void parar(){
        this.detenido = true;
        try {
            this.serverSocket.close();  
        } catch (IOException e) {
            throw new RuntimeException("Error cerrando servidor", e);
        }
    }
    
    private synchronized boolean detenido(){
        return this.detenido;
    }
    
    @Override
    public void run() {
        synchronized(this){
            this.hilo = Thread.currentThread();
        }
        abrirSocket();
        while(!detenido()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (detenido()){
                    System.out.println("Servidor Detenido");
                    return;
                }
                throw new RuntimeException("Error en conexion",e);
            }
            String linea;
            try{
                InputStream fis = new FileInputStream("C:\\Users\\alxs1\\Documents\\NetBeansProjects\\chat-servidores\\chatServidores\\src\\chat\\procesos.txt");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                int cont = 0;
                //while(((linea = br.readLine()) != null) && cont <= 4){
                    new Thread(new WorkerRunnable(clientSocket, br)).start();
                    //System.out.println(linea);
                  //  cont++;
                //}
                detenido = true;
            } catch (IOException ex) {
                Logger.getLogger(Nodo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        System.out.println("Servidor Detenido");
    }
    
}
