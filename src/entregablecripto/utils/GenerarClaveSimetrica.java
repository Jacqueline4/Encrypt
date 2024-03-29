package entregablecripto.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author enrique
 */
public class GenerarClaveSimetrica implements Serializable {

    private SecretKey clave;

    public Key getClave() {
        return clave;
    }

    public void setClave(SecretKey clave) {
        this.clave = clave;
    }

    public GenerarClaveSimetrica() {
    }

    /**
     *
     */
    public void generarClaveSimetrica(){
        ObjectOutputStream claveObj = null;
        File fichero = null;
        GenerarClaveSimetrica key = null;
        KeyGenerator keyGen;

        try {
            key = new GenerarClaveSimetrica();

            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            key.setClave(keyGen.generateKey());
            fichero = new File("miClave.key");
            claveObj = new ObjectOutputStream(new FileOutputStream(fichero));
            claveObj.writeObject(key);
//            System.out.println("Clave generada de tipo:" + key.getClave().getAlgorithm());
//            System.out.println("Clave format:" + key.getClave().getFormat());
//            System.out.println("Clave Encoded:" + key.getClave().getEncoded());

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (claveObj != null) {
                    claveObj.close();
                }
            } catch (IOException ex) {
             
            }
        }

    }
    

   

}
