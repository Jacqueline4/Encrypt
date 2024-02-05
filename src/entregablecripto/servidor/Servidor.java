/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entregablecripto.servidor;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author jacqueline
 */
public class Servidor {

    public static void main(String[] args) {
        Socket cliente;
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
