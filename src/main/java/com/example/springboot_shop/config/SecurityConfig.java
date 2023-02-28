package com.example.springboot_shop.config;//package com.nbcamp.gamematching.matchingservice.config;
//
//
//import com.nbcamp.gamematching.matchingservice.jwt.JwtAuthFilter;
//import com.nbcamp.gamematching.matchingservice.jwt.JwtUtil;
//import com.nbcamp.gamematching.matchingservice.security.PrincipalOauth2UserService;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsUtils;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true)
//public class WebSecurityConfig implements WebMvcConfigurer {
//
//    private final JwtUtil jwtUtil;
//
//    @Autowired
//    private PrincipalOauth2UserService principalOauth2UserService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console())
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable().cors();
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeHttpRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers("/**").permitAll()
//                .requestMatchers("/GGTalk/**").permitAll()
//                .requestMatchers("/pub/**").permitAll()
//                .requestMatchers("/sub/**").permitAll()//테스트용
//                .anyRequest().authenticated()
//                .and().addFilterBefore(new JwtAuthFilter(jwtUtil),
//                            UsernamePasswordAuthenticationFilter.class)
//                .oauth2Login()
//                .loginPage("/login")
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService);
//
//        return http.build();
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
//                .allowedMethods("*")
//                .exposedHeaders("*")
//                .allowCredentials(false);
//    }
//
//    @Configuration
//    public class BeanConfig {
//
//        @PersistenceContext
//        private EntityManager entityManager; // 엔티티를 관리하는 클래스
//
//        @Bean
//        public JPAQueryFactory jpaQueryFactory() { // JPAQueryFactory Bean 등록
//            return new JPAQueryFactory(entityManager);
//        }
//
//    }
//}
