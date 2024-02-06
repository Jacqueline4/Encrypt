/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entregablecripto.servidor;

import entregablecripto.utils.GenerarClaveSimetrica;
import entregablecripto.utils.GenerarClavesPublicaPrivada;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jacqueline
 */
public class Servidor {

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Socket cliente;
        GenerarClaveSimetrica gcs = new GenerarClaveSimetrica();
        GenerarClavesPublicaPrivada gcpp = new GenerarClavesPublicaPrivada();
        File claveS = new File("miClave.key");
        File clavePub = new File("miClavePublica.key");
        File clavePriv = new File("miClavePrivada.key");
        if (!claveS.exists()) {
            gcs.generarClaveSimetrica();
        } else {
            System.out.println("La clave simetrica ya existe");
        }
        if (!clavePub.exists() && !clavePriv.exists()) {
            gcpp.generarClavePublicaPrivada();
        } else {
            System.out.println("Las claves públicas y privadas ya existen");
        }

        GenerarClaveSimetrica keyObj=null;
        ObjectInputStream clave=null;
        try {
            clave = new ObjectInputStream(new FileInputStream(claveS));
            keyObj = (GenerarClaveSimetrica) clave.readObject();
        } catch (IOException ex) {
            System.out.println("Error I/O");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        //leer la clave 1 vez por fichero
        try (ServerSocket ss = new ServerSocket(6666)) {
            do {
                cliente = ss.accept();
                HiloServidor hs = new HiloServidor(cliente,claveS, keyObj, clave);
               // (File ficheroCifrar, File keyFichero, GenerarClaveSimetrica keyObj, ObjectInputStream clave
                hs.start();
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
