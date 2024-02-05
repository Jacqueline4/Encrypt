package entregablecripto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    
    //Cifrar con clave simetrica (publica y privada).
    //Descifrar con  con clave simetrica (publica y privada).4
    //Cifrar clave publica
    //Descifrar con clave publica
    //Cifrar con clave privada
    //Descifrar con clave privada
}
