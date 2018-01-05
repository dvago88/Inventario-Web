package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.UserDao;
import com.danielvargas.InventarioWeb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByNombreUsuario(String nombreUsuario) {
        return userDao.findByUsername(nombreUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Cargar el user desde la base de datos (tiramos excepción si no lo encontramos)
        User user = userDao.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User no encontrado");

        //Devolvemos el objeto
//        return user;

        //No sé si esto sea necesario o solo devolviendo el user como arriba está bien
        return new org.springframework.security.core.userdetails.User(
                user.getUsername()
                , user.getPassword()
                , user.getAuthorities());
    }

    //Si quito la seguridad csrf y pongo la contraseña y el password en los params me deja entrar, tengo que mirar
    //es como hacerlo con csrf y sin poner la contraseña o usuario en los params pues esto no es seguro
}
