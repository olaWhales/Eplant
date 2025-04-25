package com.whales.eplant.SecurityConfig;//package com.oracleous.extention_manager.securityConfiguration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Autowired
//    private JwtFilter jwtFilter ;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtFilter jwtFilter)throws Exception{
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(requests -> requests
//                .requestMatchers("/api/farmers/farmers_registration").permitAll()
//                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
//                .cors(cors -> cors.configurationSource(request -> {
//            var config = new CorsConfiguration();
//            config.setAllowedOrigins(List.of("http://localhost:5173")); // Frontend URL
//            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//            config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//            config.setAllowCredentials(true);
//            return config;
//        }));
//        return httpSecurity.build();
//    }
//
//        @Bean
//    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
//        DaoAuthenticationProvider provide = new DaoAuthenticationProvider();
//        provide.setPasswordEncoder(new BCryptPasswordEncoder(12));
//        provide.setUserDetailsService(userDetailsService);
//        return provide;
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
//        return configuration.getAuthenticationManager();
//    }
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public String toString() {
//        return "SecurityConfig{" +
//                "jwtFilter=" + jwtFilter +
//                '}';
//    }
//}
