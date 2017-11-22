package com.danielvargas.InventarioWeb.config;

import com.danielvargas.InventarioWeb.controller.FlashMessage;
import com.danielvargas.InventarioWeb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //El error lo da Intellij por error..
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //Esto es para que se puede acceder al css sin problema, spring mira como directorio base resourses/
        web.ignoring().antMatchers("/static");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().hasRole("USER")//Acá puede estar el error
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFaliureHandler())
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login");
    }

    private AuthenticationSuccessHandler loginSuccessHandler() {
        return ((request, response, authentication) -> response.sendRedirect("/productos"));
    }

    private AuthenticationFailureHandler loginFaliureHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("flash", new FlashMessage("Nombre de usuario o contraseña incorrectos", FlashMessage.Status.FAILURE));
            response.sendRedirect("/login");
        };
    }
}
