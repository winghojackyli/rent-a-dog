package com.example.rentadog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

//    private final String[] SECURITY_IGNORED_ENDPOINTS = {
//           "/h2-console/**", "/h2/**", "/webjars/**"
//    };

    private final String[] SECURITY_IGNORED_ENDPOINTS = {"/phpmyadmin/", "/phpmyadmin/**"};


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from user where username=?")
                .authoritiesByUsernameQuery("select username, role from user where username=?")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/", "/index", "/formCustomerRegister", "/addDog", "/saveCustomer", "/listDogs", "/formDogRegister", "/registerCustomer", "/registerDog","/*.png","/*.jpg","/*.css","/rentalForm","reviewForm").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
        ;

        http.csrf().disable();

    }


    //  code that restrict only profile page needs login
//    http.authorizeRequests()
//            .antMatchers("/profile/**").authenticated()//Makes / and /index to be authenthicated
//                .anyRequest().permitAll()//Makes any other allow without authentication. Or if you want a group use antMatchers here too.
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();


}
