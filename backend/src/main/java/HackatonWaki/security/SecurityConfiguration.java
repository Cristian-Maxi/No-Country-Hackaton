package HackatonWaki.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(SecurityFilter securityFilter, UserDetailsService userDetailsService) {
        this.securityFilter = securityFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        //Autenticacion Controller
                        .requestMatchers(HttpMethod.POST, "/api/**","/api/login").permitAll()
                        //Cliente Controller
                        .requestMatchers(HttpMethod.POST, "/api/cliente/add").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cliente/getAll", "/api/cliente/{id}", "/api/cliente/getAllPages", "/api/cliente/getPages").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/cliente/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/cliente/{id}").authenticated()
                        //Admin Controller
                        .requestMatchers(HttpMethod.POST, "/api/admin/add").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/getAll", "/api/cliente/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/admin/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/admin/{id}").authenticated()
                        //Swagger
                        .requestMatchers(HttpMethod.GET,"/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
