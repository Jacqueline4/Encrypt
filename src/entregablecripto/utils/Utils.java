package entregablecripto.utils;

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
            return hs;

        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());

            return null;
        }

    }

    public static byte[] cifrarSimetrico(File ficheroCifrar, File keyFichero, GenerarClaveSimetrica keyObj, ObjectInputStream clave) throws Exception {
        byte[] fichBytes = null;
        byte[] fichBytesCifrados = null;

        try {
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, keyObj.getClave());
            fichBytes = fileToByteArray(ficheroCifrar.getPath());
            fichBytesCifrados = c.doFinal(fichBytes);
            grabarFichero(ficheroCifrar, fichBytesCifrados, "CifradoSimetrico_");
            System.out.println("Encriptado el fichero...:" + ficheroCifrar.getName());
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

    public static byte[] descifrarSimetrico(byte[] contenido) throws Exception {

        File ficheroDescifrar;
        File keyFichero = new File("miClave.key");
        GenerarClaveSimetrica keyObj;

        FileOutputStream ficheroOut;

        ObjectInputStream clave;
        byte[] fichBytes = null;
        byte[] fichBytesCifrados = null;
        try {
            clave = new ObjectInputStream(new FileInputStream(keyFichero));
            keyObj = (GenerarClaveSimetrica) clave.readObject();

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

    public static void grabarFichero(File fichero, byte[] ficheroBytes, String tipo) {

        File ficheroCifrado;
        BufferedOutputStream fichSalida = null;
        try {
            ficheroCifrado = new File(tipo + fichero.getName());
            fichSalida = new BufferedOutputStream(new FileOutputStream(ficheroCifrado));
            fichSalida.write(ficheroBytes);
            fichSalida.flush();

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado");
        } catch (IOException ex) {
            System.out.println("Error I/O");

        } finally {
            try {
                fichSalida.close();
            } catch (IOException ex) {
                System.out.println("Error I/O");
            }
        }
    }

    public static byte[] cifrarPublicoPrivado(File ficheroCifrar, File keyFicheroPublicPriva, GenerarClavesPublicaPrivada keyObj, ObjectInputStream clave) throws Exception {
//        File ficheroCifrar;
//        File keyFicheroPublicPriva = new File("miClavePrivada.key");
//        GenerarClaves keyObj;

        FileOutputStream ficheroOut;

//        ObjectInputStream clave;
        byte[] fichBytes = null;
        byte[] fichBytesCifrados = null;
//        if (args.length > 0) {
        try {
//                ficheroCifrar = new File(args[0]);
            clave = new ObjectInputStream(new FileInputStream(keyFicheroPublicPriva));
            keyObj = (GenerarClavesPublicaPrivada) clave.readObject();

            // Cifrando byte[] con Cipher.
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.ENCRYPT_MODE, keyObj.getPrivateKey());
            fichBytes = fileToByteArray(ficheroCifrar.getPath());
            fichBytesCifrados = c.doFinal(fichBytes);
            grabarFichero(ficheroCifrar, fichBytesCifrados, "Cifrado_publico_privado");
            System.out.println("Encriptado el fichero...:" + ficheroCifrar.getName());

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

//        } else {
//            System.out.println("No se ha especificado archivo a cifrar.");
//        }
        return null;

    }

    public static byte[] descifrarPublicoPrivado(byte[] contenido) throws Exception {

//        File ficheroDescifrar;
//        File keyFichero = new File("miClavePublica.key");
        File keyFichero = new File("miClavePrivada.key");
        GenerarClavesPublicaPrivada keyObj;

        FileOutputStream ficheroOut;

        ObjectInputStream clave;
        byte[] fichBytes = null;
        byte[] fichBytesCifrados = null;
//        if (args.length > 0) {
        try {
//                ficheroDescifrar = new File(args[0]);
            clave = new ObjectInputStream(new FileInputStream(keyFichero));
            keyObj = (GenerarClavesPublicaPrivada) clave.readObject();

            // Cifrando byte[] con Cipher.
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.DECRYPT_MODE, keyObj.getPublicKey());
            fichBytes = contenido;
//                fichBytes = ficheroBytes(ficheroDescifrar);
            fichBytesCifrados = c.doFinal(fichBytes);
//                grabarFichero(ficheroDescifrar, fichBytesCifrados,"Descifrado_publico_privado");
//                System.out.println("Desencriptado el fichero...:" + ficheroDescifrar.getName());

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

//        } else {
//            System.out.println("No se ha especificado archivo a descifrar.");
//        }
        return null;
    }
    //Cifrar clave publica
    //Descifrar con clave publica
    //Cifrar con clave privada
    //Descifrar con clave privada
}
