package com.example.reservas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configurações de segurança da API.
 * Utiliza autenticação Basic em memória para proteger operações sensíveis.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Define a cadeia de filtros de segurança. Configura quais endpoints exigem autenticação.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // desabilita CSRF para simplificar chamadas de API
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (consultas)
                        .requestMatchers(HttpMethod.GET, "/salas/**", "/reservas/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/h2-console/**").permitAll()
                        // Acesso a consola H2
                        .requestMatchers("/h2-console/**").permitAll()
                        // Todas as requisições POST e PUT em /reservas requerem autenticação
                        .requestMatchers(HttpMethod.POST, "/reservas/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/reservas/**").authenticated()
                        // Outros endpoints POST/PUT podem ser protegidos conforme necessidade
                        .anyRequest().permitAll()
                )
                // Configura autenticação HTTP Basic
                .httpBasic(Customizer.withDefaults())
                // Permite frames para o console H2
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }

    /**
     * Configura usuários em memória para autenticação Basic.
     * Em um ambiente real, recomenda‑se utilizar uma base de dados e senhas criptografadas.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails usuario = User.withUsername("admin")
                .password(passwordEncoder.encode("senha123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(usuario);
    }

    /**
     * Define o codificador de senhas utilizado no armazenamento em memória.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
