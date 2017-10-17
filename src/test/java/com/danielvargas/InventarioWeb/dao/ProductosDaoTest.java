package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.InventarioWebWebApplication;
import com.danielvargas.InventarioWeb.model.Productos;
import com.danielvargas.InventarioWeb.model.ProductosBuilder;
import com.danielvargas.InventarioWeb.model.Proveedor;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(InventarioWebWebApplication.class)
@DatabaseSetup("classpath:productos.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class ProductosDaoTest {

    @Autowired
    private ProductosDao productosDao;

    @Before
    public void setup() {
        Productos productos = new Productos();
        productos.setId(1);
    }

    @Test
    public void ObtenerTodosDevuelveTres() throws Exception {
        assertThat(productosDao.todosLosProductos(), hasSize(3));
    }

    //No pasa este test porque hay que persistir primero el proverdor antes que el producto
    @Test
    public void salvarDebePersistirLosEntities() throws Exception {
        Productos prod = new ProductosBuilder(5,"prod5")
                .conCantidad(5)
                .conPrecio(1000)
                .conPrecioEntrada(500)
                .conDescripcion("bla")
                .conProveedor(new Proveedor())
                .build();
        productosDao.agregarProducto(prod);
        assertThat(productosDao.obtenerPorCodigo(5), notNullValue(Productos.class));
    }
}
