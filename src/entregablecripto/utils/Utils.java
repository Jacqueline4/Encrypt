package entregablecripto.utils;

import static entregablecripto.utils.Utils.fileToByteArray;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Utils {

    public static byte[] fileToByteArray(String rutaOrigenFichero) throws Exception {
        File file = new File(rutaOrigenFichero);
        byte[] byteArray = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(byteArray);

        } catch (Exception e) {
            throw e;
        }
        return byteArray;
    }

    public static void byteArrayToFile(String rutaDestinoFichero, byte[] byteArray) throws Exception {
        File fichero = new File(rutaDestinoFichero);
        try (FileOutputStream fos = new FileOutputStream(fichero)) {
            fos.write(byteArray);
        } catch (Exception e) {
            throw e;
        }
    }

    public static byte[] GetHash(byte[] contenido) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            md.update(contenido);
            byte[] hs = md.digest();
//            System.out.println("hash: " + (hs.toString()));
            return hs;

        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());

            return null;
        }

    }

    public static byte[] cifrarSimetrico(File ficheroCifrar, File keyFichero) throws Exception {
        FileOutputStream ficheroOut;

        ObjectInputStream clave;
        byte[] fichBytes = null;
        byte[] fichBytesCifrados = null;
        GenerarClaveSimetrica keyObj;

        try {
            clave = new ObjectInputStream(new FileInputStream(keyFichero));
            keyObj = (GenerarClaveSimetrica) clave.readObject();

            // Cifrando byte[] con Cipher.
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, keyObj.getClave());
            fichBytes = fileToByteArray(ficheroCifrar.getPath());
            fichBytesCifrados = c.doFinal(fichBytes);
            return fichBytesCifrados;
        } catch (IOException ex) {
            System.out.println("Error I/O");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (InvalidKeyException ex) {
            System.out.println("Clave no valida");
        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex.getMessage());
        } catch (BadPaddingException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchPaddingException ex) {
            System.out.println(ex.getMessage());
        } catch (java.lang.IllegalArgumentException ex) {

        }

        return null;

    }

    public static byte[] descifrarSimetrico(byte[] contenido, File keyFichero) throws Exception {

        File ficheroDescifrar;
        GenerarClaveSimetrica keyObj;

        FileOutputStream ficheroOut;

        ObjectInputStream clave;
        byte[] fichBytes = null;
        byte[] fichBytesCifrados = null;

        try {
            clave = new ObjectInputStream(new FileInputStream(keyFichero));
            keyObj = (GenerarClaveSimetrica) clave.readObject();

            // Cifrando byte[] con Cipher.
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, keyObj.getClave());
            fichBytes = contenido;
            fichBytesCifrados = c.doFinal(fichBytes);
            return fichBytesCifrados;
        } catch (IOException ex) {
            System.out.println("Error I/O");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (InvalidKeyException ex) {
            System.out.println("Clave no valida");
        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex.getMessage());
        } catch (BadPaddingException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchPaddingException ex) {
            System.out.println(ex.getMessage());
        } catch (java.lang.IllegalArgumentException ex) {

        }
        return null;
    }
    
}

//Cifrar con clave simetrica (publica y privada).
//Descifrar con  con clave simetrica (publica y privada).4
//Cifrar clave publica
//Descifrar con clave publica
//Cifrar con clave privada
//Descifrar con clave privada

