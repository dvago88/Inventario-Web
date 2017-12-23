package com.danielvargas.InventarioWeb.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idProducto;
    private int idProveedor;
    private int fechaEntera;

    //Estos 2 se utilizan en caso tal que el producto orginal ya no exista.
    private String nombreProducto;
    private String nombreProveedor;

    private LocalDateTime localDateTime;

    public BaseEntity() {
        localDateTime = LocalDateTime.now();
        fechaEntera = localDateTime.getYear() * 10000
                + localDateTime.getMonthValue() * 100
                + localDateTime.getDayOfMonth();
    }

    public BaseEntity(int idProducto, int idProveedor, String nombreProducto, String nombreProveedor) {
        this();
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.nombreProducto = nombreProducto;
        this.nombreProveedor = nombreProveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getFechaEntera() {
        return fechaEntera;
    }

    public void setFechaEntera(int fechaEntera) {
        this.fechaEntera = fechaEntera;
    }
}
