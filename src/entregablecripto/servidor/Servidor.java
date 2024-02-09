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
        File claveS = new File("miClave.key");
        if (!claveS.exists()) {
            gcs.generarClaveSimetrica();
        } else {
            System.out.println("La clave simetrica ya existe");
        }
       
        //clave simetrica
        GenerarClaveSimetrica keyObj = null;
        ObjectInputStream clave = null;
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
                //de la clave simetrica
                HiloServidor hs = new HiloServidor(cliente, claveS, keyObj, clave);
                hs.start();
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
