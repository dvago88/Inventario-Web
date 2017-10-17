package com.danielvargas.InventarioWeb.model;


public class ProductosBuilder {
    private int id;
    private int cantidad;
    private String nombre;
    private double precio;
    private double precioEntrada;
    private String descripcion;
    private Proveedor proveedor;

    public ProductosBuilder(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ProductosBuilder conCantidad(int cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public ProductosBuilder conPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    public ProductosBuilder conPrecioEntrada(double precio) {
        this.precioEntrada = precioEntrada;
        return this;
    }

    public ProductosBuilder conDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public ProductosBuilder conProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public Productos build() {
        return new Productos(this);
    }

    public int getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }
}
