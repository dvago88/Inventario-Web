package com.danielvargas.InventarioWeb.Service;

import com.danielvargas.InventarioWeb.dao.ProductosDao;
import com.danielvargas.InventarioWeb.model.Productos;
import com.danielvargas.InventarioWeb.service.ProductosService;
import com.danielvargas.InventarioWeb.service.ProductosServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductosServiceTest {

    @Mock
    private ProductosDao productosDao;

    @InjectMocks
    private ProductosService productosService = new ProductosServiceImpl();

    @Test
    public void todos_ShouldReturnSomething() throws Exception {
        List<Productos> productos = Arrays.asList(
                new Productos(),
                new Productos()
        );

        when(productosDao.todosLosProductos()).thenReturn(productos);

        assertEquals("Obtener todos debe devolver 2", 2, productosService.todosLosProductos().size());
        verify(productosDao).todosLosProductos();
    }

    @Test
    public void obtenerPorCodigo_DeberiaDevolverUno() throws Exception {
        when(productosDao.obtenerPorCodigo(1)).thenReturn(new Productos());

        assertThat(productosService.obtenerPorCodigo(1), instanceOf(Productos.class));
        verify(productosDao).obtenerPorCodigo(1);
    }

    @Test
    public void obtenerPorCodigo_DeberiaTirarException() throws Exception {
        when(productosDao.obtenerPorCodigo(1)).thenReturn(null);

        try {
            productosService.obtenerPorCodigo(1);
            verify(productosDao).obtenerPorCodigo(1);
            fail("No se lanzó excepción al pedir un un código que no está");
        } catch (RuntimeException ex) {

        }

    }

}
