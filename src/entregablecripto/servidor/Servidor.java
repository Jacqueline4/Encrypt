package entregablecripto.servidor;

import entregablecripto.utils.GenerarClaveSimetrica;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author jacqueline
 */
public class Servidor {

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Socket cliente;

        File claveS = new File("miClave.key");
        
        if (claveS.exists()) {
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
            
        }else{
            System.out.println("No existe el fichero.key");
        }
       
    }
}
