/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entregablecripto.servidor;

import entregablecripto.utils.GenerarClaveSimetrica;
import entregablecripto.utils.GenerarClavesPublicaPrivada;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author jacqueline
 */
public class Servidor {

    public static void main(String[] args) {
        Socket cliente;
        GenerarClaveSimetrica gcs=new GenerarClaveSimetrica();
        GenerarClavesPublicaPrivada gcpp=new GenerarClavesPublicaPrivada();
        File claveS= new File("miClave.key");
        File clavePub= new File("miClavePublica.key");
        File clavePriv= new File("miClavePrivada.key");
        if (!claveS.exists()) {
            gcs.generarClaveSimetrica();
        }else{
            System.out.println("La clave simetrica ya existe");
        }
        if (!clavePub.exists()&& !clavePriv.exists()) {
           gcpp.generarClavePublicaPrivada();
        }else{
            System.out.println("Las claves públicas y privadas ya existen");
        }     
        
        
        //leer la clave 1 vez por fichero
        try (ServerSocket ss = new ServerSocket(6666)) {
            do {
                cliente = ss.accept();
                HiloServidor hs = new HiloServidor(cliente);
                hs.start();
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
