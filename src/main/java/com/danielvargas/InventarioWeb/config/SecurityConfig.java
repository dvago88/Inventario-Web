package com.danielvargas.InventarioWeb.config;

import com.danielvargas.InventarioWeb.controller.FlashMessage;
import com.danielvargas.InventarioWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //El error lo da Intellij por error..
    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    //Fue un intento para poder acceder por medio de postman al api
   /* private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        //Esto es para que se puede acceder al css sin problema, spring mira como directorio base resourses/
        web.ignoring().antMatchers("/css/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/productos").permitAll()
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
                .logoutSuccessUrl("/login")
                .and()
                .csrf().ignoringAntMatchers("/api/productos");
//                .csrf().csrfTokenRepository(csrfTokenRepository());

//        create an SQL DDL update script
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
