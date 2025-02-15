package board.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import board.security.CustomAuthenticationSuccessHandler;
import board.security.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;
    
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/home", "/join", "/joinProc").permitAll()
            .requestMatchers("/board", "/board/**", "/api/**").hasAnyRole("ADMIN", "USER")
            .anyRequest().authenticated()
        );
        http.formLogin(auth -> auth
            .loginPage("/login")
            .loginProcessingUrl("/loginProc")
            .permitAll()
            //.defaultSuccessUrl("/board")  // 로그인 성공 시 특정 URL로 이동하도록 설정
            .successHandler(successHandler)
        );
        
        // 개발단계에서 임시적으로 Disable
        http.csrf(auth -> auth.disable());
        
        http.sessionManagement(auth -> auth
        	/* 세션 고정 방어 */
    		// changeSessionId() : 기본값. 현재 세션 ID를 새 세션 ID로 변경 (기존 세션 속성은 그대로 유지)
    		// newSession() : 새 세션을 생성하고 기존 세션은 무효화 (기존 세션 속성은 복사되지 않음)
    		// migrateSession() : 새 세션을 생성하고 기존 세션의 속성을 새 세션으로 복사 (기존 세션은 무효화 처리)
    		// none() : 세션 고정을 방어할 수 없음 (권장하지 않음)
            .sessionFixation(ses -> ses.newSession())
            /* 다중 로그인 방어 */
	        // maximumSessions(int) : 하나의 아이디에 대해 다중 로그인 허용 개수
	        // maxSessionsPreventsLogin(boolean) : 다중 로그인 개수를 초과한 경우 처리 방법
	        // - true : 초과 시 새로운 로그인을 차단
	        // - false : 초과 시 기존 세션 하나를 삭제
            .maximumSessions(1)
            .maxSessionsPreventsLogin(true)
        );
        
        // http.sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        // CSRF 기능을 차단하면 POST 방식으로 로그아웃을 해야 하므로, 만약 GET 방식으로 로그아웃을 처리하려면 아래와 같은 설정을 추가해야 함
        http.logout(auth -> auth.logoutUrl("/logout").logoutSuccessUrl("/"));
        
        // 요청 헤더에 JWT 토큰 포함 여부와 토큰 검증을 수행하는 필터를 추가
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}