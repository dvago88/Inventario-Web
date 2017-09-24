package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.Productos;
import com.danielvargas.InventarioWeb.model.Proveedor;
import com.danielvargas.InventarioWeb.service.ProductosService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

//estas importadas de esta forma es para no tener que estar copiando esto cada vez, solo copiar después del punto por ej:
// Mockito.when(.....); es solo wher(....);

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductosControllerTest {
    private MockMvc mockMvc;

    // El @InjectMocks crea una instancia del controler usando el constructor por defecto
    // e inyecta to.do lo que este anotado con @Mock en ese controlador
    @InjectMocks
    private ProductosController controller;

    @Mock
    private ProductosService service;

    @Before
    public void setup() {
//        controller = new ProductosController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void principalDebeIncluirProductos() throws Exception {
        //Como importamos de forma estatica el MockMvcRequestBuilders y el MockMvcResultMatchers
        // no lo tenemos que poner antes del get y del view, sino seria así:
        // mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.view().name("productos/principal"));


        //Organizamos el comportamiento del Mock
        List<Productos> productos = Arrays.asList(
                new Productos(12,
                        "test1",
                        2000,
                        1500,
                        "no hay",
                        new Proveedor()),
                new Productos(15,
                        "test2",
                        3000,
                        2000,
                        "n/a",
                        new Proveedor())
        );
        when(service.todosLosProductos()).thenReturn(productos);

        //Act (perform the MVC request) and Assert results
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("productos/principal"))
                .andExpect(model().attribute("productos", productos));
        //Esto verifica que sí se llame al metodo todosLosProductos()
        verify(service).todosLosProductos();
    }

    @Test
    public void agregarProducto() throws Exception {
        //Organizamos el comportamiento del Mock
        doAnswer(invocation -> {
            Productos p = (Productos) invocation.getArguments()[0];
            p.setProveedor(new Proveedor("ProveTest"));
            return null;
        }).when(service).agregarProducto(any(Productos.class));

        //Act (perform the MVC request) and Assert results
        mockMvc.perform(post("/agregar")
                .param("nombre", "Test01")
                .param("precio", "1200")
        ).andExpect(redirectedUrl("/agregar"));
        verify(service).agregarProducto(any(Productos.class));
    }
}
