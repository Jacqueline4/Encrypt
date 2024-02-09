package entregablecripto.servidor;

import entregablecripto.clase.FicheroEnvio;
import entregablecripto.utils.GenerarClaveSimetrica;
import entregablecripto.utils.GenerarClavesPublicaPrivada;
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
    File file;
    GenerarClaveSimetrica keyObj;
    ObjectInputStream clave;
    GenerarClavesPublicaPrivada keyPP;

    public HiloServidor(Socket cliente) {

        this.cliente = cliente;

    }

    public HiloServidor(Socket cliente, File file, ObjectInputStream clave, GenerarClavesPublicaPrivada keyPP) {
        this.cliente = cliente;
        this.file = file;
        this.clave = clave;
        this.keyPP = keyPP;
    }
    
    public HiloServidor(Socket cliente, File file, GenerarClaveSimetrica keyObj, ObjectInputStream clave) {
        this.cliente = cliente;
        this.file = file;
        this.keyObj = keyObj;
        this.clave = clave;
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
                            //de la clave simetrica
                            byte[] contenidoCifrado=Utils.cifrarSimetrico(file, this.file, keyObj, clave);
                            //de las dos claves
//                            byte[] contenidoCifrado=Utils.cifrarPublicoPrivado(file, this.file,clave ,keyPP);
                          
                            fichero.setContenido(contenidoCifrado);
                            //(File ficheroCifrar, File keyFichero, GenerarClaveSimetrica keyObj, ObjectInputStream clave
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