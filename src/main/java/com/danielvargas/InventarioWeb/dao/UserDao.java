package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    //Extendiendo el CrudRepository no nos toca programar los metodos principales, solo hay que elegir el nombre
    //del metodo y spring sabrá cual es, eso sí, los nombres de los metodos son en ingles.
    //Es tambien la razón por la cual marcamos con repository esta interface y las otras no, también es por eso qee
    // no tenemos la necesidad de crear UserDaoImpl
}
