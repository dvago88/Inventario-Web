package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    Usuario findByNombreUsuario(String nombreUsuario);
}
