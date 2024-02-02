package kr.co.yigil.global.config;

import java.util.List;
import kr.co.yigil.auth.application.CustomAuthenticationProvider;
import kr.co.yigil.auth.application.CustomUserDetailsService;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173", "https://yigil.co.kr"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L);
                    return config;
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(customAuthenticationProvider)
                .userDetailsService(customUserDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/api/v1/admins/login").permitAll()
                        .requestMatchers("/admin/api/v1/admins/signup").permitAll()
                        .requestMatchers("/admin/api/v1/admins/test").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationManagerBuilder builder) throws Exception {
        return builder.getObject();
    }

}
