package com.perfume.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 🔐 Configuration des règles de sécurité HTTP
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ✅ OBLIGATOIRE pour Angular (CORS)
            .cors(Customizer.withDefaults())

            // ✅ API REST → CSRF désactivé
            .csrf(csrf -> csrf.disable())

            // 🔐 Règles d'accès
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()


                // Endpoints publics
                .requestMatchers(
                        "/api/oils/**",
                        "/api/perfumes/**",
                        "/api/pdf/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                

                // Endpoints admin protégés
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Tout le reste
                .anyRequest().authenticated()
            )

            // 🔑 Authentification Basic
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * 👤 Utilisateur admin en mémoire (projet académique)
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    /**
     * 🔑 Encoder de mot de passe
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
