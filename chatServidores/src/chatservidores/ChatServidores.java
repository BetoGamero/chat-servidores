package chatservidores;

import chat.Nodo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServidores {

    public static void main(String[] args) {
        
        Nodo servidor = new Nodo(9999);
        new Thread(servidor).start();
        
        String linea = "";
        
        Scanner sc = new Scanner(System.in);        
        
        String valor = sc.nextLine();
        
        if (valor.equals("si")){
        
            while (!linea.equals("chau")){
                try {
                    Socket socket = new Socket("192.168.43.106", 9999);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    linea = br.readLine();
                    System.out.println("server: " + linea);
                    if (linea.contains("ejecutar"))
                        ejecutar(linea.substring(9));
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChatServidores.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
//        try {
//            Thread.sleep(20 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("Stopping Server");
        servidor.parar();
    }
    
    private static int ejecutar(String comando){
        
        String[] comandos = comando.split(" ");
        for (int i = 0; i < comandos.length; i++){
            try {
                System.out.println("Ejecutando " + comandos[i]);
                Process p = Runtime.getRuntime().exec(comandos[i]);
                return 1;
            } catch (IOException ex) {
                Logger.getLogger(ChatServidores.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
        }
        return 1;
    }
}
