package demo.PetCarePro.persistence.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            //.cors().and()
            .authorizeHttpRequests(auth -> auth
                // rutas públicas
                .requestMatchers(HttpMethod.POST, "/register/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/clientes/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/veterinarios/login").permitAll()

                // rutas protegidas por rol
                .requestMatchers(HttpMethod.POST, "/citas/reservar").hasAuthority("CLIENTE")   
                .requestMatchers(HttpMethod.GET,"/mascotas/cliente").hasAuthority("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/mascotas").hasAuthority("VETERINARIO")
                .requestMatchers(HttpMethod.POST, "/mascotas").hasAuthority("VETERINARIO")
                .requestMatchers(HttpMethod.GET, "/veterinarios").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/veterinarios/pendientes").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/veterinarios/validar/**").hasAuthority("ADMIN")

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Añadimos el filtro JWT antes del filtro de autenticación de usuario por defecto
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
