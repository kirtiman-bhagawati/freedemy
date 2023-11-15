package com.freedemy.api.config;

import com.freedemy.api.filters.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName("_csrf");
        httpSecurity.securityContext().requireExplicitSave(false) //need to dig more to understand
                .and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))//need to dig more to understand
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); //list of servers from where the API will be called
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);

                        return config;
                    }
                })
                //.and().csrf().ignoringRequestMatchers("/signup") //get methods are not protected against csrf, hence can be omitted from the list, we can ignore csrf protection against public APIs like signup etc
                //.and().csrf().disable() //disabling csrf is never recommended
                .and().csrf((csrf)->csrf.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler).ignoringRequestMatchers("/signup")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())) //withHttpOnlyFalse() is used to make sure Angular JS frontend can read the values
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)//execute csrfcookiefilter after basicautehnticationfilter
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/addcourse","/myBalance","/myLoans","/myCards").authenticated()
                        .requestMatchers("/courses","/contact","/signup").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
