package com.example.reservas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                // Define as regras de acesso dos endpoints
                .authorizeHttpRequests(auth -> auth
                        // Libera acesso ao console do H2
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        // Libera acesso à documentação da API
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()

                        // Permite consultas públicas de salas e reservas
                        .requestMatchers(new AntPathRequestMatcher("/salas/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/reservas/**", "GET")).permitAll()

                        // Exige autenticação para criar ou alterar reservas
                        .requestMatchers(new AntPathRequestMatcher("/reservas/**", "POST")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/reservas/**", "PUT")).authenticated()

                        // Libera qualquer outra requisição não tratada acima
                        .anyRequest().permitAll()
                )
                // Usa autenticação HTTP Basic
                .httpBasic(Customizer.withDefaults())
                // Permite que o console H2 seja exibido em frame no navegador
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

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
