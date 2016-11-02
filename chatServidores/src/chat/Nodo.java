/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            new Thread(new WorkerRunnable(clientSocket, "Servidor")).start();
        }
        System.out.println("Servidor Detenido");
    }
    
}
