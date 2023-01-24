package com.example.jwtcoworkingdental.security.config;


import com.example.jwtcoworkingdental.security.jwt.JwtEntryPoint;
import com.example.jwtcoworkingdental.security.jwt.JwtTokenFilter;
import com.example.jwtcoworkingdental.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurty  {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    AuthenticationManager authenticationManager;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
        authenticationManager = builder.build();

        http.authenticationManager(authenticationManager);
        http.csrf().disable();
        http.cors();

        //login
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/auth").permitAll();//autorizado cualquiera para un nuevo registro
        http.authorizeRequests().antMatchers("/auth/login").permitAll();//autorizado cualquiera para logearse

        //busqueda
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/auth/getall").hasRole("ADMIN");//solo los administradores tienen acceso a todos los usuarios
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/auth/username/{username}").hasAnyRole("ADMIN","USER");//solo administrador puede buscar usuario por nombre
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/auth/email/{email}").hasAnyRole("ADMIN","USER");//solo administrador puede buscar usuario por nombre
        http.authorizeRequests().antMatchers("/clinicas/**").hasRole("ADMIN");//administrador tiene acceso a todas los servicios de las clinicas
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/clinicas/{id}").hasRole("USER");//busqueda de clinicas con role de usuario
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/clientes").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/auth/username").hasRole("ADMIN");//solo administrador puede borrar usuario por nombre

        //delete
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/auth/{username}").hasRole("ADMIN");//solo administrador puede borrar usuario por nombre
        //http.authorizeRequests().antMatchers("/auth/delete/email").hasAnyRole("ADMIN","USER");//admin y user pueden actualizar

        //update
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/auth/username").hasAnyRole("ADMIN","USER");//solo administrador puede borrar usuario por nombre
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/auth/email").hasAnyRole("ADMIN","USER");//solo administrador puede borrar usuario por nombre


        http.exceptionHandling().authenticationEntryPoint(jwtEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();



    }

//    @Bean
//    public JwtTokenFilter jwtTokenFilter(){
//        return new JwtTokenFilter();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }
//
//    protected MainSecurty() {
//        super();
//    }
//
//    protected MainSecurty(boolean disableDefaults) {
//        super(disableDefaults);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/auth/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
}
