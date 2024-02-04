/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entregablecripto.clase;

import java.io.Serializable;


public class FicheroEnvio implements Serializable {

    private byte[] contenido;
    private int error;
    private byte[] hash;

    public FicheroEnvio(byte[] contenido, int error, byte[] hash) {
        this.contenido = contenido;
        this.error = error;
        this.hash = hash;
    }

    public FicheroEnvio(byte[] contenido, int error) {
        this.contenido = contenido;
        this.error = error;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "FicheroEnvio{" + "contenido=" + contenido + ", error=" + error + ", hash=" + hash + '}';
    }


    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

}
