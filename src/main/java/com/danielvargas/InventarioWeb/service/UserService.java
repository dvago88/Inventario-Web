package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByNombreUsuario(String nombreUsuario);
}