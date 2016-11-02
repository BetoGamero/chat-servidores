package chatservidores;

import chat.Nodo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServidores {

    public static void main(String[] args) {
        
        Nodo servidor = new Nodo(9999);
        new Thread(servidor).start();
        
        String linea = "";
        while (!linea.equals("chau")){
            try {
                Socket socket = new Socket("localhost", 9999);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                linea = br.readLine();
                System.out.println("server: " + linea);
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ChatServidores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        servidor.parar();
    }
    
}
