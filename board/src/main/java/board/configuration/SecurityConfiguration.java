package board.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import board.security.JwtRequestFilter;
import board.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/loginProc", "/joinProc").permitAll()
            .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
            .anyRequest().authenticated()
        );
        
        http.formLogin(auth -> auth.disable());
        
        // 개발단계에서 임시적으로 Disable
        http.csrf(auth -> auth.disable());
        
        http.sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        // http.sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        // CSRF 기능을 차단하면 POST 방식으로 로그아웃을 해야 하므로, 만약 GET 방식으로 로그아웃을 처리하려면 아래와 같은 설정을 추가해야 함
        http.logout(auth -> auth.disable());
        
        // 요청 헤더에 JWT 토큰 포함 여부와 토큰 검증을 수행하는 필터를 추가
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        // WebMvcConfigurer의 CORS 설정을 허용하도록 수정
        http.cors(Customizer.withDefaults());
        
        return http.build();
    }
    
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    	AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    	authManagerBuilder.userDetailsService(customUserDetailsService)
    					.passwordEncoder(bCryptPasswordEncoder());
    	return authManagerBuilder.build();
    }
}