/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entregablecripto.cliente;

import entregablecripto.clase.FicheroEnvio;
import entregablecripto.utils.Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author jacqueline
 */
public class Cliente {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String ruta = "";
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try (Socket s = new Socket("localhost", 6666)) {
            do {
                System.out.print("Introduce la ruta absoluta del archivo: ");
                ruta = sc.nextLine();
                String[] trozos = ruta.split("\\\\");
                String fileName = trozos[trozos.length - 1];

                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(ruta);

                if (!ruta.equals("exit")) {
                    ois = new ObjectInputStream(s.getInputStream());
                    FicheroEnvio fichero = (FicheroEnvio) ois.readObject();
                    if (fichero.getError() == 0) {
//                        String content = new String(fichero.getContenido());
//                        System.out.println("Contenido del archivo-> " + content + ", CodError-> " + fichero.getError());
                        String rutaDestino = "C:\\FTP\\carpe\\" + fileName;
                        byte[] hashServ = fichero.getHash();
                        byte[] hashFichServ = Utils.GetHash(fichero.getContenido());
//                        System.out.println(" hash del servidor-->" + hashFichServ);
//                        System.out.println("Mensaje hash: " + new String(hashFichServ));

                        String hashFichServStr = new String(hashFichServ);
                        String hashServStr = new String(hashServ);
                        if (hashFichServStr.equalsIgnoreCase(hashServStr)) {

                            Utils.byteArrayToFile(rutaDestino, fichero.getContenido());
                            System.out.println("Descarga completada");
                        } else {
                            System.out.println("Hash diferente, no se ha descargado nada");
                        }
                    } else {
                        System.out.println("Error en la descarga del fichero. Código: " + fichero.getError());
                    }
                }

            } while (!ruta.equals("exit"));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cierre abrupto del servidor");
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
