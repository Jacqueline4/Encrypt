/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entregablecripto.servidor;

import entregablecripto.clase.FicheroEnvio;
import entregablecripto.utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author jacqueline
 */
public class HiloServidor extends Thread {
    
    Socket cliente;
//    String name;

    public HiloServidor(Socket cliente) {
        
        this.cliente = cliente;
        
    }
    
    public void run() {
        String ruta = "";
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            do {
                ois = new ObjectInputStream(cliente.getInputStream());
                ruta = (String) ois.readObject();
                
                if (!ruta.equals("exit")) {
                    File file = new File(ruta);
                    FicheroEnvio fichero;
                    if (file.exists()) {
                        if (Utils.fileToByteArray(ruta) != null) {
                            fichero = new FicheroEnvio(Utils.fileToByteArray(ruta), 0);
                            byte[] contenido = fichero.getContenido();
                            System.out.println("Contenido: " + new String(contenido));
                          
                            fichero.setHash(Utils.GetHash(contenido));                      
                            System.out.println("Mensaje hash: " + new String(fichero.getHash()));
                            
                        } else {
                            System.out.println("No se encontró el fichero");
                            fichero = new FicheroEnvio(null, 1);
                        }
                    } else {
                        System.out.println("El archivo no existe: " + ruta);
                        fichero = new FicheroEnvio(null, 2);
                    }
                    oos = new ObjectOutputStream(cliente.getOutputStream());
                    oos.writeObject(fichero);
                   
                    
                }
                
            } while (!ruta.equals("exit"));
//            System.out.println("Cierre controlado del cliente);

        } catch (Exception ex) {
//            System.out.println(" Cierre abrupto del cliente ");
            ex.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    System.out.println("Error cierre ois");
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    System.out.println("Error cierre oos");
                }
            }
            
        }
    }
}
