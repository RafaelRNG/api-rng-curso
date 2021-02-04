package com.rng.apirng.config;

import com.rng.apirng.security.JWTAuthenticationFilter;
import com.rng.apirng.security.JWTAuthorizationFilter;
import com.rng.apirng.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            httpSecurity.headers().frameOptions().disable();
        }

        httpSecurity.cors().and().csrf().disable();

        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/produtos/**", "/categorias/**", "/estados/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/clientes", "/auth/forgot/**")
                .permitAll()
                .antMatchers("/h2-console/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        httpSecurity.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));

        httpSecurity.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws  Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(this.bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
    	configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}