package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.UsuarioDao;
import com.danielvargas.InventarioWeb.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioDao usuarioDao;

    @Override
    public Usuario encontrarPorNombreUsuario(String nombreUsuario) {
        return usuarioDao.findByNombreUsuario(nombreUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Cargar el usuario desde la base de datos (tiramos excepción si no lo encontramos)
        Usuario usuario = usuarioDao.findByNombreUsuario(username);
        if (usuario == null) throw new UsernameNotFoundException("Usuario no encontrado");

        //Devolvemos el objeto
        return usuario;
    }
}
