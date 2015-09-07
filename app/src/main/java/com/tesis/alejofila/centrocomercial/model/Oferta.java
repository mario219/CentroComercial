package com.tesis.alejofila.centrocomercial.model;

/**
 * Created by alejofila on 5/09/15.
 */
public class Oferta {
    private String producto;
    private String precio;
    private String imagen;

    public Oferta(String producto, String precio , String imagen){
        this.precio = precio;
        this.imagen = imagen;
        this.producto = producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
