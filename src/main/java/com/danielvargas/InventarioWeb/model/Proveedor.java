package com.danielvargas.InventarioWeb.model;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 1, max = 35, message = "El nombre debe tener entre {min} y {max} caracteres")
    @Column(name = "nombre", unique = true)
    private String nombreP;

    @OneToMany(mappedBy = "proveedor")
    private List<Productos> productos = new ArrayList<>();

    private String direccion;

    private long telefono;

    @Size(max = 150, message = "Maximo 150 caracteres para la descripción")
    @Column(name = "descripcion")
    private String descripcionP;

    public Proveedor() {
    }

    //Este constructor fue creado para poder testear
    public Proveedor(String nombreP) {
        this.nombreP = nombreP;
    }

    @Override
    public String toString() {
        return nombreP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public List<Productos> getProductos() {
        return productos;
    }

    public void setProductos(List<Productos> productos) {
        this.productos = productos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getDescripcionP() {
        return descripcionP;
    }

    public void setDescripcionP(String descripcionP) {
        this.descripcionP = descripcionP;
    }
}
