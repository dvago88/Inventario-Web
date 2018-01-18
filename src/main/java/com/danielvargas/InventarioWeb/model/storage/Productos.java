package com.danielvargas.InventarioWeb.model.storage;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by Daniel on 20/08/2017
 */
@Entity
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int cantidad;

    @NotNull
    @Size(min = 1, max = 35, message = "El nombre debe tener entre {min} y {max} caracteres")
    @Column(unique = true)
    private String nombre;

    /**
     * Seguir acá:
     * la respuesta está acá: https://teamtreehouse.com/library/displaying-validation-messages
     * toca cambiar el mensaje en messages.properties y luego ir al app.config y hacer otros cambios
     */
    @NotNull
    @Range(min = 10, max = 10000000, message = "El valor tiene que estar entre ${min} y $10.000.000")
    private double precio;

    @NotNull
    @Range(min = 10, max = 10000000, message = "El valor tiene que estar entre ${min} y $10.000.000")
    private double precioEntrada;

    @Size(max = 200, message = "Maximo 200 caracteres para la descripción")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    @JsonIgnore //Esto hace que no de error pero no me deja ver el proveedor con json
    private Proveedor proveedor;

    /*@Version
    private long version;*/


    private LocalDateTime dateUploaded = LocalDateTime.now();
    private int cantidadVendido;
    private int cantidadComprado;


    public Productos() {
    }

    public Productos(ProductosBuilder builder) {
        this.nombre = builder.getNombre();
        this.id = builder.getId();
        this.cantidad = builder.getCantidad();
        this.precio = builder.getPrecio();
        this.precioEntrada = builder.getPrecioEntrada();
        this.descripcion = builder.getDescripcion();
        this.proveedor = builder.getProveedor();
        this.cantidadVendido=builder.getCantidadVendido();
        this.cantidadComprado=builder.getCantidadComprado();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDateTime getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(LocalDateTime dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public int getCantidadVendido() {
        return cantidadVendido;
    }

    public void setCantidadVendido(int cantidadVendido) {
        this.cantidadVendido = cantidadVendido;
    }

    public int getCantidadComprado() {
        return cantidadComprado;
    }

    public void setCantidadComprado(int cantidadComprado) {
        this.cantidadComprado = cantidadComprado;
    }
}
